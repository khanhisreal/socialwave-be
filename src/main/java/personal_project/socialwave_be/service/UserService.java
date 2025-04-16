package personal_project.socialwave_be.service;

import org.springframework.http.ResponseEntity;
import personal_project.socialwave_be.dto.Post.PostDTO;
import personal_project.socialwave_be.dto.User.UserDTO;
import personal_project.socialwave_be.dto.User.UserRegistrationDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO save(UserRegistrationDTO theUser);

    UserDTO findById(Integer theId);

    void deleteById(Integer theId);

    UserDTO findByUsername(String username);

    boolean checkUserAvailability(String name);

    UserDTO findByUsernameAndPassword(String username, String password);
}
