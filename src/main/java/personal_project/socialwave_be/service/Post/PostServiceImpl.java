package personal_project.socialwave_be.service.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal_project.socialwave_be.dto.Post.PostDTO;
import personal_project.socialwave_be.dto.Post.PostMapper;
import personal_project.socialwave_be.entity.Post;
import personal_project.socialwave_be.repository.Post.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private PostMapper postMapper;

    @Autowired
    PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public List<PostDTO> findAll() {
        return postMapper.toDTOList(postRepository.findAllByOrderByCreateTimeDesc());
    }

    @Override
    public PostDTO save(Post post) {
        return postMapper.toDTO(postRepository.save(post));
    }

    @Override
    public PostDTO findById(Integer theId) {

        Optional<Post> result = postRepository.findById(theId);
        Post post;

        if (result.isPresent()) {
            post = result.get();
        } else {
            throw new RuntimeException("Did not find post id - " + theId);
        }

        return postMapper.toDTO(post);
    }

    @Override
    public void deleteById(Integer theId) {
        postRepository.deleteById(theId);
    }

    @Override
    public List<PostDTO> findPostsByUserId(Integer theId) {
        return postRepository.findPostsByUserId(theId);
    }

}
