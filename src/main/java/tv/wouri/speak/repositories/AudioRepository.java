package tv.wouri.speak.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select * from audio where categorie = ?1 and langue =?2 and title like %?3%", nativeQuery = true)
    Page<Audio> findByAll (Long categorie, Long langue, String titre, Pageable pageable);

    @Query(value = "select * from audio where categorie = ?1 and langue =?2", nativeQuery = true)
    Page<Audio> findByCategoriLangue (Long categorie, Long langue, Pageable pageable);

    @Query(value = "select * from audio where categorie = ?1 and title like %?2%", nativeQuery = true)
    Page<Audio> findByCategorieTitle(Long categorie,String titre, Pageable pageable);

    @Query(value = "select * from audio where langue =?1 and title like %?2%", nativeQuery = true)
    Page<Audio> findByLangueTitle (Long langue, String titre, Pageable pageable);

    @Query(value = "select * from audio where langue =?1", nativeQuery = true)
    Page<Audio> findByLangue (Long langue, Pageable pageable);

    @Query(value = "select * from audio where categorie =?1", nativeQuery = true)
    Page<Audio> findByCategori (Long langue, Pageable pageable);

    @Query(value = "select * from audio where title like %?1%", nativeQuery = true)
    Page<Audio> findByTitle (String title, Pageable pageable);

    @Query(value = "select * from audio ", nativeQuery = true)
    Page<Audio> findByAllEmpty (Pageable pageable);

}
