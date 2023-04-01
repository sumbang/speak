package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Examen;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.ExamenRepository;

@Service
public class ExamenService extends AbstractService<Examen, Long> {

    @Autowired
    private ExamenRepository examenRepository;

    @Override
    protected JpaRepository<Examen, Long> getRepository() {
        return examenRepository;
    }
}
