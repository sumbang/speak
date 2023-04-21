package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.User;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.UserRepository;
import tv.wouri.speak.search.UserSearch;

import java.util.List;

@Service
public class UserService extends AbstractService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByResetToken(String token){
        return userRepository.findByResetToken(token);
    }

    public User findByAuthToken(String token){
        return userRepository.findByAuthToken(token);
    }

    public List<User> findByRole(Long role){
        return userRepository.findByRole(role);
    }

    public Page<User> searchUser(UserSearch search, Pageable pageable) {

        if(search.getLogin() != null && search.getNom()!= null && search.getRole() != null && search.getDateadded()!= null)  return userRepository.findByAll(search.getLogin(),search.getNom(), search.getRole(), search.getDateadded(),pageable);

        else if(search.getLogin()!= null && search.getNom()!= null && search.getRole() != null && search.getDateadded()== null)  return userRepository.findByAllNoDate(search.getLogin(),search.getNom(), search.getRole(),pageable);

        else if(search.getLogin()!= null && search.getNom()!= null && search.getRole() == null && search.getDateadded()!= null)  return userRepository.findByAllNoRole(search.getLogin(),search.getNom(), search.getDateadded(),pageable);

        else if(search.getLogin()!= null && search.getNom()== null && search.getRole() != null && search.getDateadded()!= null)  return userRepository.findByAllNoNom(search.getLogin(), search.getRole(), search.getDateadded(),pageable);

        else if(search.getLogin()== null && search.getNom()!= null && search.getRole() != null && search.getDateadded()!= null)  return userRepository.findByAllNoEmail(search.getNom(), search.getRole(), search.getDateadded(),pageable);

        else if(search.getLogin()!= null && search.getNom()!= null && search.getRole() == null && search.getDateadded() == null)  return userRepository.findByAllEmailNom(search.getLogin(),search.getNom(),pageable);

        else  if(search.getLogin()!= null && search.getNom()== null && search.getRole() != null && search.getDateadded()== null)  return userRepository.findByAllEmailRole(search.getLogin(), search.getRole(),pageable);

        else if(search.getLogin()!= null && search.getNom()== null && search.getRole() == null && search.getDateadded()!= null)  return userRepository.findByAllEmailDate(search.getLogin(), search.getDateadded(),pageable);

        else if(search.getLogin()== null && search.getNom()!= null && search.getRole() != null && search.getDateadded()== null)  return userRepository.findByAllNomRole(search.getNom(), search.getRole(), pageable);

        else if(search.getLogin()== null && search.getNom()!= null && search.getRole() == null && search.getDateadded()!= null)  return userRepository.findByAllNomDate(search.getNom(),search.getDateadded(),pageable);

        else if(search.getLogin()== null && search.getNom()== null && search.getRole() != null && search.getDateadded()!= null)  return userRepository.findByAllRoleDate( search.getRole(), search.getDateadded(),pageable);

        else if(search.getLogin()!= null && search.getNom()== null && search.getRole() == null && search.getDateadded()== null)  return userRepository.findByAllEmail( search.getLogin(),pageable);

        else if(search.getLogin()== null && search.getNom()!= null && search.getRole() == null && search.getDateadded()== null)  return userRepository.findByAllNom( search.getNom(),pageable);

        else if(search.getLogin()== null && search.getNom()== null && search.getRole() != null && search.getDateadded()== null)  return userRepository.findByAllRole( search.getRole(),pageable);

        else if(search.getLogin()== null && search.getNom()== null && search.getRole() == null && search.getDateadded()!= null)  return userRepository.findByAllDate( search.getDateadded(),pageable);

        else return userRepository.findAll(pageable);
    }
}
