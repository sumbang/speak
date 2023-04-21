package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Paiement;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.PaiementRepository;
import tv.wouri.speak.search.PaiementSearch;

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

    public Page<Paiement> searchUser(PaiementSearch search, Pageable pageable) {

        if(!search.getDatepaiement().isEmpty() && !search.getRefIn().isEmpty() && !search.getRefOut().isEmpty()  && search.getMontant() != null && !search.getModep().isEmpty()  && search.getPayeur() != null)  return paiementRepository.searchUser(search.getDatepaiement(),search.getRefIn(), search.getRefOut(), search.getMontant(), search.getModep(), search.getPayeur(),pageable);

        else if(!search.getDatepaiement().isEmpty() && search.getRefIn().isEmpty() && search.getRefOut().isEmpty()  && search.getMontant() == null && search.getModep().isEmpty()  && search.getPayeur() == null)  return paiementRepository.searchUserDate(search.getDatepaiement(),pageable);

        else if(search.getDatepaiement().isEmpty() && !search.getRefIn().isEmpty() && search.getRefOut().isEmpty()  && search.getMontant() == null && search.getModep().isEmpty()  && search.getPayeur() == null)  return paiementRepository.searchUserIn(search.getRefIn(),pageable);

        else if(search.getDatepaiement().isEmpty() && search.getRefIn().isEmpty() && !search.getRefOut().isEmpty()  && search.getMontant() == null && search.getModep().isEmpty()  && search.getPayeur() == null)  return paiementRepository.searchUserOut(search.getRefOut(), pageable);

        else if(search.getDatepaiement().isEmpty() && search.getRefIn().isEmpty() && search.getRefOut().isEmpty()  && search.getMontant() != null && search.getModep().isEmpty()  && search.getPayeur() == null)  return paiementRepository.searchUserMode(search.getModep(), pageable);

        else if(search.getDatepaiement().isEmpty() && search.getRefIn().isEmpty() && search.getRefOut().isEmpty() && search.getMontant() == null && search.getModep().isEmpty()  && search.getPayeur() != null)  return paiementRepository.searchUserPayeur(search.getPayeur(),pageable);

        else return paiementRepository.searchAll(pageable);
    }
}
