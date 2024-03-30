package the_ride.the_ride_backend.Utiities;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = BooleanStringValidator.class)
public @interface BooleanString {

    String message() default "The value must be 'true' or 'false'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
