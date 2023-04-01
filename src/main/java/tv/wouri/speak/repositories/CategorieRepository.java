package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.wouri.speak.models.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
