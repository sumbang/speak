package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Resultat;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.ResultatRepository;

@Service
public class ResultatService extends AbstractService<Resultat, Long> {

    @Autowired
    private ResultatRepository resultatRepository;

    @Override
    protected JpaRepository<Resultat, Long> getRepository() {
        return resultatRepository;
    }
}
