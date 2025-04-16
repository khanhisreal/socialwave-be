package personal_project.socialwave_be.repository;

import personal_project.socialwave_be.dto.User.UserDTO;
import personal_project.socialwave_be.entity.User;

public interface CustomizedUserRepository {

    User findUserByUsername(String username);

    boolean checkUserAvailability(String username);

    User findByUsernameAndPassword(String username, String password);

}
