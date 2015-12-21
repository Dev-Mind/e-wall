package fr.emse.ewall.service.user;

import fr.emse.ewall.model.Authority;
import fr.emse.ewall.model.Role;
import fr.emse.ewall.model.User;
import fr.emse.ewall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manage {@link fr.emse.ewall.model.User}
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 21/12/15.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findOrCreateUser(String emseId){
        //We verify if user exist
        User user = userRepository.findByEsmeid(emseId);
        if(user==null){
            user = userRepository.save(new User().setEsmeid(emseId).addAuthority(new Authority().setName(Role.PUBLIC)));
        }
        return user;
    }
}
