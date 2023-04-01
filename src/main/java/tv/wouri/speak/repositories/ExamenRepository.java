package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.wouri.speak.models.Examen;

public interface ExamenRepository  extends JpaRepository<Examen, Long> {
}
