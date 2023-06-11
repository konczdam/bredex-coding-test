package hu.konczdam.forma1.security.services;

import hu.konczdam.forma1.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = userRepository.findById(username);
        if(user.isPresent()){
            return UserDetailsImpl.build(user.get());
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
