package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    @Query(value = "select * from paiement where detail_paiement = ?1", nativeQuery = true)
    Paiement findByRefOut(String refOut);

    @Query(value = "select * from paiement where ref_in_paiement = ?1", nativeQuery = true)
    Paiement findByRefIn(String refIn);
}
