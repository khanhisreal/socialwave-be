package personal_project.socialwave_be.dto.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal_project.socialwave_be.dto.User.UserDTO;
import personal_project.socialwave_be.dto.User.UserPostDTO;
import personal_project.socialwave_be.entity.Post;
import personal_project.socialwave_be.service.User.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostMapper {

    private UserService userService;

    @Autowired
    public PostMapper(UserService userService) {
        this.userService = userService;
    }

    public PostDTO toDTO(Post post) {
        return new PostDTO(
                post.getPostId(),
                post.getImageSource(),
                post.getCaption(),
                post.getLikeCount(),
                post.getUser().getUserId()
        );
    }



    public List<PostDTO> toDTOList(List<Post> list) {
        return list.stream().map(
                post -> toDTO(post)
        ).collect(Collectors.toList());
    }

    public List<PostUserDTO> toPostUserDTOs(List<PostDTO> postsDb) {
        return postsDb.stream().map(
                postDTO -> toPostUserDTO(postDTO)
        ).collect(Collectors.toList());
    }

    private PostUserDTO toPostUserDTO(PostDTO postDTO) {
        //fetch user
        UserDTO userDb = userService.findById(postDTO.getUserId());

        //map user
        UserPostDTO tempUser = new UserPostDTO();
        tempUser.setUserId(userDb.getUserId());
        tempUser.setUserName(userDb.getUserName());
        tempUser.setAvatarSource(userDb.getAvatarSource());

        //map post
        PostUserDTO temp = new PostUserDTO();
        temp.setPostId(postDTO.getPostId());
        temp.setCaption(postDTO.getCaption());
        temp.setImageSource(postDTO.getImageSource());
        temp.setLikeCount(postDTO.getLikeCount());
        temp.setUserPostDTO(tempUser);

        return temp;
    }
}
