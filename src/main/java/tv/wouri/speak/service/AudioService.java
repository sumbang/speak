package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Audio;
import tv.wouri.speak.models.User;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.AudioRepository;

import java.util.List;

@Service
public class AudioService extends AbstractService<Audio, Long> {

    @Autowired
    private AudioRepository audioRepository;

    @Override
    protected JpaRepository<Audio, Long> getRepository() {
        return audioRepository;
    }

    public List<Audio> findByCategorie(Long categorie){
        return audioRepository.findByCategorie(categorie);
    }

    public List<Audio> findByCategorieLangue(Long categorie,Long langue){
        return audioRepository.findByCategorieLangue(categorie,langue);
    }
}
