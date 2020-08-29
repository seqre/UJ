package uj.jwzp2020.veterinaryclinic.model.validation;

import uj.jwzp2020.veterinaryclinic.model.client.dto.ClientCreationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ContactValidator implements ConstraintValidator<Contact, ClientCreationDTO> {
    @Override
    public boolean isValid(ClientCreationDTO value, ConstraintValidatorContext context) {
        return (value.getEmail() != null && !value.getEmail().equals("")) || (value.getTelephoneNumber() != null && !value.getTelephoneNumber().equals(""));
    }
}
