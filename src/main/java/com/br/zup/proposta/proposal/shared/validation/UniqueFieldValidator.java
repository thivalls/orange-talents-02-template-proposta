package com.br.zup.proposta.proposal.shared.validation;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {
    private String fieldName;
    private Class<?> domainClass;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
        domainClass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Query query = em.createQuery("select 1 from " + domainClass.getName() + " where " + fieldName + " = :value");
        query.setParameter("value", o);

        List resultList = query.getResultList();

        Assert.state(resultList.size() <= 1, "Entidade " + domainClass + " já existe");

        return resultList.isEmpty();
    }


}
