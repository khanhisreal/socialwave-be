package personal_project.socialwave_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import personal_project.socialwave_be.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, CustomizedUserRepository {
}
