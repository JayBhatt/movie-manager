package news.MovieManager.validator;

import news.MovieManager.annotation.Password;
import news.MovieManager.dto.PasswordForm;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @NOTE: Writing a generic custom validator, instead of using a libraries such as Passay.
 */
public class PasswordConstraintValidation implements ConstraintValidator<Password, PasswordForm> {

    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 10;
    public static final String PATTERN_SPECIAL_CHARS = "[^A-Za-z0-9]";
    public static final String PATTERN_WHITE_SPACE = "\\s+";

    @Override
    public boolean isValid(PasswordForm user, ConstraintValidatorContext context) {
        if (user == null || StringUtils.isBlank(user.getPassword()) || user.getPassword().length() < PasswordConstraintValidation.PASSWORD_MIN_LENGTH || user.getPassword().length() > PasswordConstraintValidation.PASSWORD_MAX_LENGTH) {
            return false;
        }
        /**
         * Negative matching for special characters supports wide range of special characters but also opens up the door for SQL injections. Would be good to restrict this to only certain special characters.
         */
        Pattern patternSpecialChars = Pattern.compile(PasswordConstraintValidation.PATTERN_SPECIAL_CHARS, Pattern.CASE_INSENSITIVE);
        Pattern patternWhiteSpace = Pattern.compile(PasswordConstraintValidation.PATTERN_WHITE_SPACE, Pattern.CASE_INSENSITIVE);
        if (!patternSpecialChars.matcher(user.getPassword()).find() || patternWhiteSpace.matcher(user.getPassword()).find()) {
            return false;
        }
        return !StringUtils.isBlank(user.getConfirmPassword()) && user.getConfirmPassword().equals(user.getPassword());
    }
}
