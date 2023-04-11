package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.MyAbonnement;
import tv.wouri.speak.models.Paiement;

import java.util.List;

public interface MyAbonnementRepository  extends JpaRepository<MyAbonnement, Long> {

    @Query(value = "select * from myabonnement where payeur = ?1 and fin >= ?2", nativeQuery = true)
    List<MyAbonnement> findByChild(Long payeur,String jour);
}
