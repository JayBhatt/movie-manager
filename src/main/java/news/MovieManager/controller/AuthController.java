package news.MovieManager.controller;

import news.MovieManager.dto.LoginDto;
import news.MovieManager.dto.UserDto;
import news.MovieManager.model.Status;
import news.MovieManager.persistence.entity.MovieUser;
import news.MovieManager.service.UserServiceImpl;
import news.MovieManager.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenUtil tokenUtil;

    private final UserServiceImpl userService;

    @Autowired
    public AuthController(AuthenticationManager authManager, TokenUtil tokenUtil, UserServiceImpl userService) {
        this.authManager = authManager;
        this.tokenUtil = tokenUtil;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid LoginDto request) {
        Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = (UserDetails) authenticate.getPrincipal();
        Optional<MovieUser> fetchedUser = this.userService.findDistinctByEmailAndActive(user.getUsername(), Status.ACTIVE.getStatus());
        if (fetchedUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, tokenUtil.generateToken(user)).body(UserDto.toUserDto(fetchedUser.get()));
    }

}
