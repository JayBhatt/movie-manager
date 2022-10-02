package news.MovieManager.exception.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import news.MovieManager.exception.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
/**
 * @NOTE: This class handles two types of exceptions. Used overloading rather than using generics because type erasure makes difficult to check types of generics at run time.
 */
public class ValidationErrorResponse {

    private final HttpStatus status;

    private List<ValidationError> message = new ArrayList<>();

    public ValidationErrorResponse(ConstraintViolationException exception, HttpStatus errorCode) {
        this.status = errorCode;
        this.populateErrors(exception);
    }

    public ValidationErrorResponse(MethodArgumentNotValidException exception, HttpStatus errorCode) {
        this.status = errorCode;
        this.populateErrors(exception);
    }

    private void populateErrors(MethodArgumentNotValidException exception) {
        exception.getBindingResult().getAllErrors().forEach(objectError -> this.message.add(new ValidationError(objectError.getObjectName(), objectError.getDefaultMessage())));
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> this.message.add(new ValidationError(fieldError.getObjectName(), fieldError.getDefaultMessage())));
    }

    private void populateErrors(ConstraintViolationException exception) {
        for (ConstraintViolation violation : exception.getConstraintViolations()) {
            this.message.add(new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()));
        }
    }

}
