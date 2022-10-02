package news.MovieManager.controller;

import news.MovieManager.dto.UserDto;
import news.MovieManager.exception.DuplicateEntityInsertionException;
import news.MovieManager.model.Role;
import news.MovieManager.model.Status;
import news.MovieManager.persistence.entity.MovieUser;
import news.MovieManager.persistence.entity.UserRole;
import news.MovieManager.service.UserRoleServiceImpl;
import news.MovieManager.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;
    private final UserRoleServiceImpl userRoleService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserController(UserServiceImpl userService, UserRoleServiceImpl userRoleService, PasswordEncoder encoder) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.encoder = encoder;
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<MovieUser> getUserById(@PathVariable("id") Long id) {
        MovieUser user = this.userService.findDistinctByIdAndActive(id, Status.ACTIVE.getStatus());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieUser> createUser(@Validated @RequestBody UserDto userDto) {
        MovieUser user = userDto.toMovieUser();
        Optional<MovieUser> existingUser = this.userService.findDistinctByEmailAndActive(user.getEmail(), Status.ACTIVE.getStatus());
        if (existingUser.isPresent()) {
            throw new DuplicateEntityInsertionException("User already exists.");
        }
        UserRole role = this.userRoleService.findDistinctByRoleAndActive(Role.USER.getRole(), Status.ACTIVE.getStatus());
        /**
         * @NOTE: At present, we don't allow creation of new admins and all users created defaults to User role.
         */
        user.setRole(role);
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(this.userService.save(user));
    }


}
