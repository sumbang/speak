package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user where login = ?1", nativeQuery = true)
    User findByLogin (String username);

    @Query(value = "select * from user where reset_token = ?1", nativeQuery = true)
    User findByResetToken (String token);

    @Query(value = "select * from user where auth_token = ?1", nativeQuery = true)
    User findByAuthToken (String token);

    @Query(value = "select * from user where role_id_role = ?1", nativeQuery = true)
    List<User> findByRole (Long role);

}
