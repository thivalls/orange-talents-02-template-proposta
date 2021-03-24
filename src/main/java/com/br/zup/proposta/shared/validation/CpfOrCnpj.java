package com.br.zup.proposta.shared.validation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR)
public @interface CpfOrCnpj {
    String message() default "This field is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
