package tv.wouri.speak.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.wouri.speak.models.Ecoute;
import tv.wouri.speak.models.User;

import java.util.List;

public interface EcouteRepository extends JpaRepository<Ecoute, Long> {

    @Query(value = "delete from ecoute where enfant = ?1", nativeQuery = true)
    void deleteByEnfant (Long enfant);

    @Query(value = "select * from ecoute where audio = ?1 and enfant = ?2", nativeQuery = true)
    List<Ecoute> findByAudioChild(Long audio, Long enfant);

    @Query(value = "delete from ecoute where enfant = ?1 and audio = ?2", nativeQuery = true)
    void deleteByEnfantAudio (Long enfant,Long audio);

}
