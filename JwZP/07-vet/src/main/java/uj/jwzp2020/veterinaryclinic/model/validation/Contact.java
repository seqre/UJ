package uj.jwzp2020.veterinaryclinic.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ContactValidator.class})
public @interface Contact {
    String message() default "{uj.jwzp2020.veterinaryclinic.model.validation.Contact.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
