package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.Abonnement;

import java.util.List;

public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {

}
