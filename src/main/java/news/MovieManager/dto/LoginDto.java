package news.MovieManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "Missing required parameter \"Username\"")
    private String username;

    @NotBlank(message = "Missing required parameter \"Password\"")
    private String password;

}
