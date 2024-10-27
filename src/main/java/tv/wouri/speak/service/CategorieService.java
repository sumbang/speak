package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Categorie;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.CategorieRepository;

import java.util.List;

@Service
public class CategorieService extends AbstractService<Categorie, Long> {

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    protected JpaRepository<Categorie, Long> getRepository() {
        return categorieRepository;
    }

    public List<Categorie> findById() {
        return categorieRepository.findById();
    }
}
