package tv.wouri.speak.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.Audio;
import tv.wouri.speak.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user where login = ?1 and deleted = 0", nativeQuery = true)
    User findByLogin (String username);

    @Query(value = "select * from user where reset_token = ?1", nativeQuery = true)
    User findByResetToken (String token);

    @Query(value = "select * from user where auth_token = ?1", nativeQuery = true)
    User findByAuthToken (String token);

    @Query(value = "select * from user where role_id_role = ?1", nativeQuery = true)
    List<User> findByRole (Long role);

    @Query(value = "select * from user where login like %?1% and nom like %?2% and role_id_role = ?3 and added_date like %?4%", nativeQuery = true)
    Page<User> findByAll (String email, String nom, Long role, String date, Pageable pageable);

    @Query(value = "select * from user where login like %?1% and nom like %?2% and role_id_role = ?3", nativeQuery = true)
    Page<User> findByAllNoDate (String email, String nom, Long role,Pageable pageable);

    @Query(value = "select * from user where login like %?1% and nom like %?2% and added_date like %?3%", nativeQuery = true)
    Page<User> findByAllNoRole (String email, String nom, String date, Pageable pageable);

    @Query(value = "select * from user where login like %?1% and role_id_role = ?2 and added_date like %?3%", nativeQuery = true)
    Page<User> findByAllNoNom (String email, Long role, String date, Pageable pageable);

    @Query(value = "select * from user where login like %?1% and nom like %?2% ", nativeQuery = true)
    Page<User> findByAllEmailNom (String email, String nom, Pageable pageable);

    @Query(value = "select * from user where login like %?1% and role_id_role = ?2 ", nativeQuery = true)
    Page<User> findByAllEmailRole (String email, Long role, Pageable pageable);

    @Query(value = "select * from user where login like %?1% and added_date like %?2%", nativeQuery = true)
    Page<User> findByAllEmailDate (String email, String date, Pageable pageable);

    @Query(value = "select * from user where login like %?1% ", nativeQuery = true)
    Page<User> findByAllEmail (String email, Pageable pageable);

    @Query(value = "select * from user where nom like %?1%", nativeQuery = true)
    Page<User> findByAllNom (String nom, Pageable pageable);

    @Query(value = "select * from user where role_id_role =?1 ", nativeQuery = true)
    Page<User> findByAllRole (Long role, Pageable pageable);

    @Query(value = "select * from user where added_date like %?4%", nativeQuery = true)
    Page<User> findByAllDate (String date, Pageable pageable);

    @Query(value = "select * from user where nom like %?1% and role_id_role = ?2 and added_date like %?3%", nativeQuery = true)
    Page<User> findByAllNoEmail (String nom, Long role, String date, Pageable pageable);

    @Query(value = "select * from user where nom like %?1% and role_id_role = ?2 ", nativeQuery = true)
    Page<User> findByAllNomRole (String nom, Long role, Pageable pageable);

    @Query(value = "select * from user where nom like %?1% and added_date like %?2%", nativeQuery = true)
    Page<User> findByAllNomDate (String nom, String date, Pageable pageable);

    @Query(value = "select * from user where role_id_role = ?1 and added_date like %?2%", nativeQuery = true)
    Page<User> findByAllRoleDate ( Long role, String date, Pageable pageable);

    @Query(value = "select * from user", nativeQuery = true)
    Page<User> findBy (Pageable pageable);




}
