package news.MovieManager.vendor.imdb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ImdbException extends RuntimeException {

    public ImdbException(String message){
        super(message);
    }

}
