package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.wouri.speak.models.Resultat;

public interface ResultatRepository extends JpaRepository<Resultat, Long> {
}
