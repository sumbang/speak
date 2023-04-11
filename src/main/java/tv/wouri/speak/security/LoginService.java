package tv.wouri.speak.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tv.wouri.speak.models.User;
import tv.wouri.speak.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Login loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new EntityNotFoundException("User non trouvé");
        }

        else if(!user.getStatus())  {
            throw new EntityNotFoundException("Votre compte est désactivé, veuillez contacter l'administrateur");
        }

        /*else if(!user.getActivated())  {
            throw new EntityNotFoundException("Votre compte est désactivé, veuillez contacter l'administrateur");
        }*/

        return new Login(user);
    }

}

