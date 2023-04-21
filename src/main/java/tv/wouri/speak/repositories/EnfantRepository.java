package tv.wouri.speak.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.Enfant;
import tv.wouri.speak.models.User;

import java.util.List;

public interface EnfantRepository extends JpaRepository<Enfant, Long> {

    @Query(value = "select * from enfant where user = ?1 and visible = 1", nativeQuery = true)
    List<Enfant> findByUser (Long userid);

    @Query(value = "select * from enfant where prenom like %?1% and user = ?2 and datnaiss like %?3% and sexe = ?4", nativeQuery = true)
    Page<Enfant> findByAll (String prenom, Long parent, String datnaiss, String sexe, Pageable pageable);

    @Query(value = "select * from enfant where prenom like %?1% and user = ?2 and datnaiss like %?3%", nativeQuery = true)
    Page<Enfant> findByAllNoSexe (String prenom, Long parent, String datnaiss, Pageable pageable);

    @Query(value = "select * from enfant where prenom like %?1% and user = ?2 and sexe = ?3", nativeQuery = true)
    Page<Enfant> findByAllNoDatnaiss (String prenom, Long parent, String sexe, Pageable pageable);

    @Query(value = "select * from enfant where prenom like %?1% and datnaiss like %?2% and sexe = ?3", nativeQuery = true)
    Page<Enfant> findByAllNoParent (String prenom, String datnaiss, String sexe, Pageable pageable);

    @Query(value = "select * from enfant where user = ?1 and datnaiss like %?2% and sexe = ?3", nativeQuery = true)
    Page<Enfant> findByAllNoPrenom ( Long parent, String datnaiss, String sexe, Pageable pageable);

    @Query(value = "select * from enfant where prenom like %?1% and user = ?2 ", nativeQuery = true)
    Page<Enfant> findByAllPrenomParent (String prenom, Long parent, Pageable pageable);

    @Query(value = "select * from enfant where prenom like %?1% and datnaiss like %?2% ", nativeQuery = true)
    Page<Enfant> findByAllPrenomDatnaiss (String prenom, String datnaiss, Pageable pageable);

    @Query(value = "select * from enfant where prenom like %?1%  and sexe = ?2", nativeQuery = true)
    Page<Enfant> findByAllPrenomSexe (String prenom, String sexe, Pageable pageable);

    @Query(value = "select * from enfant where prenom like %?1%", nativeQuery = true)
    Page<Enfant> findByAllPrenom (String prenom, Pageable pageable);

    @Query(value = "select * from enfant where user = ?1 ", nativeQuery = true)
    Page<Enfant> findByAllParent (Long parent, Pageable pageable);

    @Query(value = "select * from enfant where datnaiss like %?1% ", nativeQuery = true)
    Page<Enfant> findByAllDatnaiss ( String datnaiss, Pageable pageable);

    @Query(value = "select * from enfant where sexe = ?1", nativeQuery = true)
    Page<Enfant> findByAllSexe (String sexe, Pageable pageable);

    @Query(value = "select * from enfant where user = ?1 and datnaiss like %?2% ", nativeQuery = true)
    Page<Enfant> findByAllParentDate (Long parent, String datnaiss, Pageable pageable);

    @Query(value = "select * from enfant where user = ?2 and sexe = ?2", nativeQuery = true)
    Page<Enfant> findByAllParentSexe (Long parent, String sexe, Pageable pageable);

    @Query(value = "select * from enfant where and datnaiss like %?1% and sexe = ?2", nativeQuery = true)
    Page<Enfant> findByAllSexeDate (String datnaiss, String sexe, Pageable pageable);

    @Query(value = "select * from enfant ", nativeQuery = true)
    Page<Enfant> findByAllEmpty(Pageable pageable);
}
