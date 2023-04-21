package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Enfant;
import tv.wouri.speak.models.Paiement;
import tv.wouri.speak.models.User;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.EnfantRepository;
import tv.wouri.speak.search.EnfantSearch;
import tv.wouri.speak.search.PaiementSearch;

import java.util.List;

@Service
public class EnfantService extends AbstractService<Enfant, Long> {

    @Autowired
    private EnfantRepository enfantRepository;

    @Override
    protected JpaRepository<Enfant, Long> getRepository() {
        return enfantRepository;
    }

    public List<Enfant> findByUser(Long userid){
        return enfantRepository.findByUser(userid);
    }

    public Page<Enfant> searchEnfant(EnfantSearch search, Pageable pageable) {

        if(!search.getPrenom().isEmpty() && search.getParent() != null && !search.getSexe().isEmpty() && !search.getDatenaiss().isEmpty())  return enfantRepository.findByAll(search.getPrenom(),search.getParent(), search.getDatenaiss(), search.getSexe(),pageable);

        else if(!search.getPrenom().isEmpty() && search.getParent() != null && search.getSexe().isEmpty() && !search.getDatenaiss().isEmpty())  return enfantRepository.findByAllNoSexe(search.getPrenom(),search.getParent(), search.getDatenaiss(),pageable);

        else if(!search.getPrenom().isEmpty() && search.getParent() != null && !search.getSexe().isEmpty() && search.getDatenaiss().isEmpty())  return enfantRepository.findByAllNoDatnaiss(search.getPrenom(),search.getParent(), search.getSexe(),pageable);

        else if(!search.getPrenom().isEmpty() && search.getParent() == null && !search.getSexe().isEmpty() && !search.getDatenaiss().isEmpty())  return enfantRepository.findByAllNoParent(search.getPrenom(), search.getDatenaiss(), search.getSexe(),pageable);

        else if(search.getPrenom().isEmpty() && search.getParent() != null && !search.getSexe().isEmpty() && !search.getDatenaiss().isEmpty())  return enfantRepository.findByAllNoPrenom(search.getParent(), search.getDatenaiss(), search.getSexe(),pageable);

       else if(!search.getPrenom().isEmpty() && search.getParent() == null && search.getSexe().isEmpty() && search.getDatenaiss().isEmpty())  return enfantRepository.findByAllPrenom(search.getPrenom(),pageable);

       else if(search.getPrenom().isEmpty() && search.getParent() != null && search.getSexe().isEmpty() && search.getDatenaiss().isEmpty())  return enfantRepository.findByAllParent(search.getParent(),pageable);

       else if(search.getPrenom().isEmpty() && search.getParent() == null && !search.getSexe().isEmpty() && search.getDatenaiss().isEmpty())  return enfantRepository.findByAllSexe(search.getSexe(),pageable);

        else if(search.getPrenom().isEmpty() && search.getParent() == null && search.getSexe().isEmpty() && !search.getDatenaiss().isEmpty())  return enfantRepository.findByAllDatnaiss(search.getDatenaiss(),pageable);

        else return enfantRepository.findByAllEmpty(pageable);
    }
}
