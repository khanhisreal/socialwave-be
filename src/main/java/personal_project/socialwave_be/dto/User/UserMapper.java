package personal_project.socialwave_be.dto.User;

import org.springframework.stereotype.Service;
import personal_project.socialwave_be.dto.Post.PostDTO;
import personal_project.socialwave_be.entity.Post;
import personal_project.socialwave_be.entity.User;

import java.util.stream.Collectors;

@Service
public class UserMapper {

    public User toEntity(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUserId(userRegistrationDTO.getUserId());
        user.setName(userRegistrationDTO.getName());
        user.setUserName(userRegistrationDTO.getUserName());
        user.setAvatarSource(userRegistrationDTO.getAvatarSource());
        user.setPassword(userRegistrationDTO.getPassword());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setBio(userRegistrationDTO.getBio());
        return user;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setUserName(userDTO.getUserName());
        user.setAvatarSource(userDTO.getAvatarSource());
        user.setBio(userDTO.getBio());
        return user;
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getUserName(),
                user.getAvatarSource(),
                user.getBio(),
                user.getFollowerUsers().size(),
                user.getFollowingUsers().size(),
                user.getPosts().stream().map(
                        post -> convertPostToDTO(post)
                ).collect(Collectors.toList())
        );
    }

    public UserRegistrationDTO toRegistrationDTO(UserDTO userDTO) {
        return new UserRegistrationDTO(
                userDTO.getUserId(),
                userDTO.getName(),
                userDTO.getUserName(),
                userDTO.getAvatarSource(),
                null,  // Password is not available in UserDTO
                null,  // Email is not available in UserDTO
                userDTO.getBio()
        );
    }

    public PostDTO convertPostToDTO(Post post) {
        return new PostDTO(
                post.getPostId(),
                post.getImageSource(),
                post.getCaption(),
                post.getLikeCount(),
                post.getUser().getUserId()
        );
    }

}
