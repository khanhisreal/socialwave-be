package personal_project.socialwave_be.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import personal_project.socialwave_be.dto.User.UserDTO;
import personal_project.socialwave_be.dto.User.UserMapper;
import personal_project.socialwave_be.dto.User.UserRegistrationDTO;
import personal_project.socialwave_be.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public UserController(UserService theUserService, ObjectMapper theObjectMapper, UserMapper theUserMapper) {
        userService = theUserService;
        objectMapper = theObjectMapper;
        userMapper = theUserMapper;
    }

    @GetMapping("/users")
    public List<UserDTO> getAll() {
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    public UserDTO getUser(@PathVariable int userId) {
        return userService.findById(userId);
    }

    @GetMapping("/users/track")
    public boolean userNameIsAvailable(@RequestParam String username) {
        return userService.checkUserAvailability(username);
    }

    //@RequestBody expects a data sent as a raw JSON payload
    //best for data in the object of key-value format
    @PostMapping("/users")
    public UserDTO addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        //prevent updating user when accidentally passing id
        userRegistrationDTO.setUserId(0);
        return userService.save(userRegistrationDTO);
    }

    //@RequestParam expects data sent as a multipart/form-data
    //best for data from the formData object that contains file
    @PostMapping("/users/signup")
    public ResponseEntity<UserRegistrationDTO> registerUser(
            @RequestParam("name") String name,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam(value = "bio", required = false) String bio,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException {

        String avatarSource = null;

        //create a folder if not exists
        Path uploadDirPath = Paths.get("C:/socialwave/uploads/");
        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }

        //create a new file
        if (avatar != null && !avatar.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + avatar.getOriginalFilename();
                Path filePath = uploadDirPath.resolve(fileName);
                Files.copy(avatar.getInputStream(), filePath);
                avatarSource = "/uploads/" + fileName;
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        //create DTO object - 0: create new
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(0, name, username, password, email, avatarSource, bio);
        userService.save(userRegistrationDTO);
        return ResponseEntity.ok(userRegistrationDTO);
    }

    // ? - the wildcard type: unspecified type
    // T - the generic type: specified type, for eg: UserDTO
    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestParam("username") String username,
                                       @RequestParam("password") String password) {

        UserDTO userDTO = userService.findByUsernameAndPassword(username, password);

        if (userDTO != null) return ResponseEntity.ok(userDTO);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Incorrect username or password.");
    }

    @PutMapping("/users")
    public UserDTO updateUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return userService.save(userRegistrationDTO);
    }

    @PatchMapping("/users/{userId}")
    public UserDTO patchUser(@RequestBody Map<String, Object> patchPayload,
                             @PathVariable int userId) {

        UserDTO existingUserDB = userService.findById(userId);

        if (existingUserDB == null) {
            throw new RuntimeException("User id not found - " + userId);
        }

        //the id field is not allowed in the payload, forced remain primary key
        if (patchPayload.containsKey("userId")) {
            throw new RuntimeException("userId field not allowed in a request body - " + userId);
        }

        UserRegistrationDTO patchedUser = apply(patchPayload,
                userMapper.toRegistrationDTO(existingUserDB));

        return userService.save(patchedUser);
    }

    private UserRegistrationDTO apply(Map<String, Object> patchPayload, UserRegistrationDTO userRegistrationDTO) {

        //convert userDTO to JSON object node
        ObjectNode userNode = objectMapper.convertValue(userRegistrationDTO, ObjectNode.class);

        //convert the patchPayload map to a JSON object node
        ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

        //merge the patch updates into the user node
        /**
         * passing all updates to the user
         */
        userNode.setAll(patchNode);

        //convert back to java object
        return objectMapper.convertValue(userNode, UserRegistrationDTO.class);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
        return "Remove user id - " + userId;
    }

}
