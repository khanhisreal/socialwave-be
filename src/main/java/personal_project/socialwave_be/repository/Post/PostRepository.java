package personal_project.socialwave_be.repository.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal_project.socialwave_be.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, CustomizedPostRepository {

    List<Post> findAllByOrderByCreateTimeDesc();

}
