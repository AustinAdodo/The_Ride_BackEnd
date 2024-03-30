package the_ride.the_ride_backend.Utiities;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
* defines the logic to check whether the field value is either "true" or "false".
*/
public class BooleanStringValidator implements ConstraintValidator<BooleanString, String> {

    @Override
    public void initialize(BooleanString constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Consider null as valid or adjust based on your requirements
        }
        return value.equals("true") || value.equals("false");
    }
}
