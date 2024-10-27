package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.Categorie;
import tv.wouri.speak.models.Ecoute;

import java.util.List;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    @Query(value = "select * from categorie order by libelle asc ", nativeQuery = true)
    List<Categorie> findById();
}
