package personal_project.socialwave_be.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import personal_project.socialwave_be.dto.User.UserDTO;
import personal_project.socialwave_be.dto.User.UserMapper;
import personal_project.socialwave_be.dto.User.UserRegistrationDTO;
import personal_project.socialwave_be.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserMapper userMapper;
    private ObjectMapper objectMapper;
    private UserService userService;

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

    @GetMapping("/users/fetch/{username}")
    public UserDTO fetchUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    //@RequestBody expects a data sent as a raw JSON payload
    //use this if the form is of type: application/json
    @PostMapping("/users")
    public UserDTO addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        //prevent updating user when accidentally passing id
        userRegistrationDTO.setUserId(0);
        return userService.save(userRegistrationDTO);
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
