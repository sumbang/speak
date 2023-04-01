package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.Role;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.RoleRepository;

@Service
public class RoleService extends AbstractService<Role, Long> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected JpaRepository<Role, Long> getRepository() {
        return roleRepository;
    }
}
