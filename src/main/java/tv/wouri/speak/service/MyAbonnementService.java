package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.MyAbonnement;
import tv.wouri.speak.models.User;
import tv.wouri.speak.repositories.MyAbonnementRepository;

import java.util.List;

@Service
public class MyAbonnementService extends AbstractService<MyAbonnement, Long> {

    @Autowired
    private MyAbonnementRepository myAbonnementRepository;

    @Override
    protected JpaRepository<MyAbonnement, Long> getRepository() {
        return myAbonnementRepository;
    }

    public List<MyAbonnement> findByChild(Long payeur,String jour){
        return myAbonnementRepository.findByChild(payeur,jour);
    }
}
