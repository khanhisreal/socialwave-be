package personal_project.socialwave_be.repository.Post;

import personal_project.socialwave_be.dto.Post.PostDTO;

import java.util.List;

public interface CustomizedPostRepository {

    List<PostDTO> findPostsByUserId(Integer theId);

}
