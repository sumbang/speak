package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.Enfant;
import tv.wouri.speak.models.User;

import java.util.List;

public interface EnfantRepository extends JpaRepository<Enfant, Long> {

    @Query(value = "select * from enfant where user = ?1 and visible = 1", nativeQuery = true)
    List<Enfant> findByUser (Long userid);


}
