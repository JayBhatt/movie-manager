package news.MovieManager.advice;

import lombok.extern.slf4j.Slf4j;
import news.MovieManager.exception.DuplicateEntityInsertionException;
import news.MovieManager.exception.EntityNotFoundException;
import news.MovieManager.exception.response.ErrorResponse;
import news.MovieManager.exception.response.ValidationErrorResponse;
import news.MovieManager.vendor.imdb.exception.ImdbException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice extends ResponseEntityExceptionHandler {

    public static final String GENERIC_ERROR = "An error occurred while processing your request. Please try again after sometime.";

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity<ValidationErrorResponse> onConstraintValidationException(ConstraintViolationException ex) {
        this.logError(ex);
        final ValidationErrorResponse errorResponse = new ValidationErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(DuplicateEntityInsertionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> onConstraintValidationException(DuplicateEntityInsertionException ex) {
        this.logError(ex);
        final ErrorResponse errorResponse = new ErrorResponse(ex, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        this.logError(ex);
        final ValidationErrorResponse errorResponse = new ValidationErrorResponse(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> onEntityNotFoundException(EntityNotFoundException ex) {
        this.logError(ex);
        final ErrorResponse errorResponse = new ErrorResponse(ex, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> onUserNotFoundException(UsernameNotFoundException ex) {
        this.logError(ex);
        final ErrorResponse errorResponse = new ErrorResponse(ex, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> onInternalServerErrorException(Exception ex) {
        this.logError(ex);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorHandlingControllerAdvice.GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(ImdbException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> onImdbErrorException(ImdbException ex) {
        this.logError(ex);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorHandlingControllerAdvice.GENERIC_ERROR, HttpStatus.SERVICE_UNAVAILABLE);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> onAccessDeniedErrorException(AccessDeniedException ex) {
        this.logError(ex);
        final ErrorResponse errorResponse = new ErrorResponse("Access Denied.", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletResponse response){
        this.logError(ex);
        final ErrorResponse errorResponse = new ErrorResponse("Not Authorised.", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        this.logError(ex);
        final ErrorResponse errorResponse = new ErrorResponse("Not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    private void logError(Throwable ex) {
        log.error("An error occurred while processing the request. Error: {}, Trace: {}", ex.getMessage(), ex.getStackTrace());
    }

}
