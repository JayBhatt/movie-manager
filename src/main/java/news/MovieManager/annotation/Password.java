package news.MovieManager.annotation;

import news.MovieManager.validator.PasswordConstraintValidation;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static java.lang.annotation.ElementType.*;

@Documented
@Target( { TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidation.class)
public @interface Password {

    String message() default "\"Password\" should not contain whitespaces, length should be between 6 - 10 characters, it should contain at least one special character and it's value should match \"Confirm Password\".";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
