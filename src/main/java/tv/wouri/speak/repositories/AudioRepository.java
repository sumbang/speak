package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.Audio;
import tv.wouri.speak.models.Categorie;

import java.util.List;

public interface AudioRepository extends JpaRepository<Audio, Long> {

    @Query(value = "select * from audio where categorie = ?1 ", nativeQuery = true)
    List<Audio> findByCategorie (Long categorie);

    @Query(value = "select * from audio where categorie = ?1 and langue =?2", nativeQuery = true)
    List<Audio> findByCategorieLangue (Long categorie,Long langue);

}
