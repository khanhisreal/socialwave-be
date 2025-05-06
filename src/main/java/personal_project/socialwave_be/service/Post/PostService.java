package personal_project.socialwave_be.service.Post;

import personal_project.socialwave_be.dto.Post.PostDTO;
import personal_project.socialwave_be.entity.Post;

import java.util.List;

public interface PostService {

    List<PostDTO> findAll();

    PostDTO save(Post post);

    PostDTO findById(Integer theId);

    void deleteById(Integer theId);

    List<PostDTO> findPostsByUserId(Integer theId);

}
