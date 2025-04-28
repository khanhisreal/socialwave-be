package personal_project.socialwave_be.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import personal_project.socialwave_be.dto.Auth.AuthRequestForm;
import personal_project.socialwave_be.dto.Auth.AuthResponseDTO;
import personal_project.socialwave_be.dto.User.UserRegistrationDTO;
import personal_project.socialwave_be.dto.Auth.UserRegistrationForm;
import personal_project.socialwave_be.security.JWTGenerator;
import personal_project.socialwave_be.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping("/auth/track")
    public boolean userNameIsAvailable(@RequestParam String username) {
        return userService.checkUserAvailability(username);
    }

    @PostMapping(path = "/auth/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@ModelAttribute tells Spring to populate the UserRegistrationForm
    //from a multipart/form-data request
    public ResponseEntity<String> registerUser(@ModelAttribute UserRegistrationForm form) {
        //handle avatar upload
        String avatarSource = null;
        MultipartFile avatar = form.getAvatar();
        if (avatar != null && !avatar.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + avatar.getOriginalFilename();
                Path uploadDir = Paths.get("C:/socialwave/uploads/");
                Files.createDirectories(uploadDir);
                Path target = uploadDir.resolve(fileName);
                Files.copy(avatar.getInputStream(), target);
                avatarSource = "/uploads/" + fileName;
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        //map the form to the DTO object
        UserRegistrationDTO dto = new UserRegistrationDTO(
                0,
                form.getName(),
                form.getUsername(),
                passwordEncoder.encode(form.getPassword()),
                form.getEmail(),
                avatarSource,
                form.getBio()
        );

        //save and return the response
        userService.save(dto);
        return ResponseEntity.ok("User created successfully");
    }

    // ? - the wildcard type: unspecified type
    // T - the generic type: specified type, for eg: UserDTO
    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@ModelAttribute AuthRequestForm request) {
        try {
            // Authenticate the user using the provided username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Set the authentication context in SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate the JWT token
            String token = jwtGenerator.generateToken(authentication);

            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
