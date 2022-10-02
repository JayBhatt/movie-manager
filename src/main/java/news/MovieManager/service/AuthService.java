package news.MovieManager.service;

import news.MovieManager.model.Status;
import news.MovieManager.persistence.entity.MovieUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private static final String ROLE_PREFIX = "ROLE_";
    private final UserServiceImpl userService;

    @Autowired
    public AuthService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MovieUser> user = this.userService.findDistinctByEmailAndActive(username, Status.ACTIVE.getStatus());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user found for the given username");
        }
        MovieUser fetchedUser = user.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.format("%s%s", AuthService.ROLE_PREFIX, fetchedUser.getRole().getRole())));
        return new User(fetchedUser.getEmail(), fetchedUser.getPassword(), authorities);
    }

}
