package tv.wouri.speak.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.Abonnement;
import tv.wouri.speak.models.User;
import tv.wouri.speak.repositories.AbonnementRepository;
import tv.wouri.speak.repositories.UserRepository;

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
}
