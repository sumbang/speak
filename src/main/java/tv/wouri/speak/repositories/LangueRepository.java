package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.wouri.speak.models.Langue;

public interface LangueRepository extends JpaRepository<Langue, Long> {
}
