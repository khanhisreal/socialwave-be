package personal_project.socialwave_be.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal_project.socialwave_be.dto.User.UserDTO;
import personal_project.socialwave_be.dto.User.UserMapper;
import personal_project.socialwave_be.dto.User.UserRegistrationDTO;
import personal_project.socialwave_be.entity.User;
import personal_project.socialwave_be.repository.User.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository, UserMapper theUserMapper) {
        userRepository = theUserRepository;
        userMapper = theUserMapper;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(
                user -> userMapper.toDTO(user)
        ).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Integer theId) {

        Optional<User> result = userRepository.findById(theId);

        if (result.isEmpty()) {
            throw new RuntimeException("User not found - " + theId);
        }

        return userMapper.toDTO(result.get());
    }

    @Override
    public User findEntityById(Integer theId) {
        Optional<User> result = userRepository.findById(theId);
        if(!result.isPresent()) {
            throw new RuntimeException("User not found id - " + theId);
        }
        return result.get();
    }

    @Override
    public UserDTO findByUsername(String username) {

        User result = userRepository.findUserByUsername(username);

        return userMapper.toDTO(result);
    }

    @Override
    public boolean checkUserAvailability(String name) {
        return userRepository.checkUserAvailability(name);
    }

    @Override
    public UserDTO findByUsernameAndPassword(String username, String password) {

        User userDb = userRepository.findByUsernameAndPassword(username, password);

        if(userDb == null) return null;

        return userMapper.toDTO(userDb);
    }

    @Override
    public UserDTO save(UserRegistrationDTO theUser) {
        //set update time only for new user
        User user;
        if (theUser.getUserId() == 0) {
            //new user
            user = userMapper.toEntity(theUser);
            user.setCreateTime(LocalDateTime.now());
        } else {
            //existing user
            Optional<User> userDbOpt = userRepository.findById(theUser.getUserId());
            if (userDbOpt.isEmpty()) {
                throw new RuntimeException("User not found - " + theUser.getUserId());
            }
            User userDb = userDbOpt.get();
            user = userMapper.toEntity(theUser);
            user.setCreateTime(userDb.getCreateTime()); //preserve createTime
            user.setUpdateTime(LocalDateTime.now());
        }
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void deleteById(Integer theId) {
        userRepository.deleteById(theId);
    }

}
