package news.MovieManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateEntityInsertionException extends RuntimeException{

    public DuplicateEntityInsertionException(String message){
        super(message);
    }

}
