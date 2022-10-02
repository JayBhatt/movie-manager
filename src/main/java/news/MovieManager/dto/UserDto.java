package news.MovieManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import news.MovieManager.annotation.Password;
import news.MovieManager.persistence.entity.MovieUser;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Password
public class UserDto extends BaseDto implements PasswordForm {

    @NotEmpty(message = "Missing value of required parameter \"Email\".")
    @Email(message = "Invalid value for parameter \"Email\".")
    private String email;

    @NotEmpty(message = "Missing value of required parameter \"Password\".")
    private String password;

    @NotEmpty(message = "Missing value of required parameter \"Confirm Password\".")
    private String confirmPassword;

    private String role;

    public MovieUser toMovieUser() {
        MovieUser movieUser = new MovieUser();
        movieUser.setId(this.getId());
        movieUser.setEmail(this.getEmail());
        movieUser.setPassword(this.getPassword());
        return movieUser;
    }

    public static UserDto toUserDto(MovieUser user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().getRole());
        userDto.setCreatedOn(LocalDateTime.now());
        return userDto;
    }

}
