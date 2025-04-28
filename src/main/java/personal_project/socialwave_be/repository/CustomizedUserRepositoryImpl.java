package personal_project.socialwave_be.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import personal_project.socialwave_be.dto.User.UserDTO;
import personal_project.socialwave_be.entity.User;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {

    private EntityManager entityManager;

    @Autowired
    public CustomizedUserRepositoryImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public User findUserByUsername(String username) {
        TypedQuery<User> user = entityManager.createQuery("SELECT u FROM User u WHERE u.userName = :username ", User.class);
        user.setParameter("username", username);

        return user.getSingleResult();
    }

    @Override
    public boolean checkUserAvailability(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class);
        query.setParameter("username", username);

        try {
            User user = query.getSingleResult();
            return false;
        } catch (NoResultException e) {
            return true;
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.userName = :username AND u.password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
