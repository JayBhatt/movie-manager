package news.MovieManager.exception.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorResponse {

    private final HttpStatus status;

    private final String message;

    public ErrorResponse(Throwable ex, HttpStatus status) {
        this.status = status;
        this.message = ex.getMessage();
    }

    public ErrorResponse(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
    }

}
