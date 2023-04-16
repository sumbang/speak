package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Ecoute;
import tv.wouri.speak.models.User;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.EcouteRepository;

import java.util.List;

@Service
public class EcouteService extends AbstractService<Ecoute, Long> {

    @Autowired
    private EcouteRepository ecouteRepository;

    @Override
    protected JpaRepository<Ecoute, Long> getRepository() {
        return ecouteRepository;
    }

    public void deleteByEnfant(Long enfant){
        ecouteRepository.deleteByEnfant(enfant);
    }

    public List<Ecoute> findByAudioChild(Long audio,Long enfant) {
        return ecouteRepository.findByAudioChild(audio,enfant);
    }

    public void deleteByEnfantAudio(Long enfant,Long audio){
        ecouteRepository.deleteByEnfantAudio(enfant,audio);
    }
}
