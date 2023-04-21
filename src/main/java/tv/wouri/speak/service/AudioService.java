package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Audio;
import tv.wouri.speak.models.Enfant;
import tv.wouri.speak.models.User;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.AudioRepository;
import tv.wouri.speak.search.AudioSearch;
import tv.wouri.speak.search.EnfantSearch;

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

    public Page<Audio> searchAudio(AudioSearch search, Pageable pageable) {

        if (search.getTitle()!= null && search.getCategorie() != null && search.getLangue() != null)
            return audioRepository.findByAll(search.getCategorie(), search.getLangue(), search.getTitle(), pageable);

        else if (search.getTitle()== null && search.getCategorie() != null && search.getLangue() != null)
            return audioRepository.findByCategoriLangue(search.getCategorie(), search.getLangue(), pageable);

        else if (search.getTitle()!= null && search.getCategorie() == null && search.getLangue() != null)
            return audioRepository.findByLangueTitle(search.getLangue(), search.getTitle(), pageable);

        else if (search.getTitle()!= null && search.getCategorie() != null && search.getLangue() == null)
            return audioRepository.findByCategorieTitle(search.getCategorie(),search.getTitle(), pageable);

        else if (search.getTitle()!= null && search.getCategorie() == null && search.getLangue() == null)
            return audioRepository.findByTitle(search.getTitle(), pageable);

        else if (search.getTitle()== null && search.getCategorie() != null && search.getLangue() == null)
            return audioRepository.findByCategori(search.getCategorie(), pageable);

        else if (search.getTitle()== null && search.getCategorie() == null && search.getLangue() != null)
            return audioRepository.findByLangue(search.getLangue(), pageable);

        else return audioRepository.findByAllEmpty(pageable);
    }
}
