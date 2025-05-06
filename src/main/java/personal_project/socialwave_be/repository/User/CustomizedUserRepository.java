package personal_project.socialwave_be.repository.User;

import personal_project.socialwave_be.entity.User;

public interface CustomizedUserRepository {

    User findUserByUsername(String username);

    boolean checkUserAvailability(String username);

    User findByUsernameAndPassword(String username, String password);

}
