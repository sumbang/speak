package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Paiement;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.PaiementRepository;

import java.util.List;

@Service
public class PaiementService extends AbstractService<Paiement, Long> {

    @Autowired
    private PaiementRepository paiementRepository;

    @Override
    protected JpaRepository<Paiement, Long> getRepository() {
        return paiementRepository;
    }

    public Paiement findByRefOut(String refOut) {
        return paiementRepository.findByRefOut(refOut);
    }

    public Paiement findByRefin(String refIn) {
        return paiementRepository.findByRefIn(refIn);
    }

    public List<Paiement> findByStatus(int status) {
        return paiementRepository.findByStatus(status);
    }
}
