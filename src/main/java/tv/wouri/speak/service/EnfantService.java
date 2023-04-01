package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Enfant;
import tv.wouri.speak.models.User;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.EnfantRepository;

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
}
