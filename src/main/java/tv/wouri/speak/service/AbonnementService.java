package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.repositories.AbonnementRepository;

@Service
public class AbonnementService extends AbstractService<Abonnement, Long> {

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Override
    protected JpaRepository<Abonnement, Long> getRepository() {
        return abonnementRepository;
    }
}
