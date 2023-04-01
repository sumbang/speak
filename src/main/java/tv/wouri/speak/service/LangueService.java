package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Langue;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.LangueRepository;

@Service
public class LangueService extends AbstractService<Langue, Long> {

    @Autowired
    private LangueRepository langueRepository;

    @Override
    protected JpaRepository<Langue, Long> getRepository() {
        return langueRepository;
    }
}
