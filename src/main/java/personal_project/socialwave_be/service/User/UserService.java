package personal_project.socialwave_be.service.User;

import personal_project.socialwave_be.dto.User.UserDTO;
import personal_project.socialwave_be.dto.User.UserRegistrationDTO;
import personal_project.socialwave_be.entity.User;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO save(UserRegistrationDTO theUser);

    UserDTO findById(Integer theId);

    User findEntityById(Integer theId);

    void deleteById(Integer theId);

    UserDTO findByUsername(String username);

    boolean checkUserAvailability(String name);

    UserDTO findByUsernameAndPassword(String username, String password);
}
