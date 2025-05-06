package personal_project.socialwave_be.repository.Post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import personal_project.socialwave_be.dto.Post.PostDTO;
import personal_project.socialwave_be.dto.Post.PostMapper;
import personal_project.socialwave_be.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

public class CustomizedPostRepositoryImpl implements CustomizedPostRepository {

    private EntityManager entityManager;
    private PostMapper postMapper;

    @Autowired
    public CustomizedPostRepositoryImpl(EntityManager entityManager, PostMapper postMapper) {
        this.entityManager = entityManager;
        this.postMapper = postMapper;
    }

    @Override
    public List<PostDTO> findPostsByUserId(Integer theId) {
        TypedQuery<Post> posts = entityManager.createQuery("SELECT p FROM Post p WHERE p.user.userId = :theId ORDER BY createTime DESC", Post.class);
        posts.setParameter("theId", theId);

        List<Post> listEntities = posts.getResultList();

        return listEntities.stream().map(
                postDTO -> postMapper.toDTO(postDTO)
        ).collect(Collectors.toList());
    }

}
