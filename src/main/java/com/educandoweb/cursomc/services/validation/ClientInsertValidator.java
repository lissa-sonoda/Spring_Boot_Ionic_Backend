package com.educandoweb.cursomc.services.validation;

import com.educandoweb.cursomc.domain.Client;
import com.educandoweb.cursomc.domain.enums.ClientType;
import com.educandoweb.cursomc.dto.ClientNewDTO;
import com.educandoweb.cursomc.repositories.ClientRepository;
import com.educandoweb.cursomc.resources.exceptions.FieldMessage;
import com.educandoweb.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

    @Autowired
    private ClientRepository repo;

    @Override
    public void initialize(ClientInsert ann) {
    }

    @Override
    public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (objDto.getType().equals(ClientType.NATURALPERSON.getCod()) && !BR.isValidSsn(objDto.getSsnOrEin())) {
            list.add(new FieldMessage("ssnOrEin", "Invalid SSN"));
        }

        if (objDto.getType().equals(ClientType.LEGALENTITY.getCod()) && !BR.isValidEin(objDto.getSsnOrEin())) {
            list.add(new FieldMessage("ssnOrEin", "Invalid EIN"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}