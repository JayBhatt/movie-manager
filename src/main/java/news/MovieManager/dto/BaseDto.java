package news.MovieManager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BaseDto {

    private Long id;

    private boolean active = true;

    private LocalDateTime createdOn = LocalDateTime.now();

}
