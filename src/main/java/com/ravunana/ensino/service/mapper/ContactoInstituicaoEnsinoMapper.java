package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.ContactoInstituicaoEnsinoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactoInstituicaoEnsino} and its DTO {@link ContactoInstituicaoEnsinoDTO}.
 */
@Mapper(componentModel = "spring", uses = {InstituicaoEnsinoMapper.class})
public interface ContactoInstituicaoEnsinoMapper extends EntityMapper<ContactoInstituicaoEnsinoDTO, ContactoInstituicaoEnsino> {

    @Mapping(source = "instituicaoEnsino.id", target = "instituicaoEnsinoId")
    @Mapping(source = "instituicaoEnsino.nome", target = "instituicaoEnsinoNome")
    ContactoInstituicaoEnsinoDTO toDto(ContactoInstituicaoEnsino contactoInstituicaoEnsino);

    @Mapping(source = "instituicaoEnsinoId", target = "instituicaoEnsino")
    ContactoInstituicaoEnsino toEntity(ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO);

    default ContactoInstituicaoEnsino fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactoInstituicaoEnsino contactoInstituicaoEnsino = new ContactoInstituicaoEnsino();
        contactoInstituicaoEnsino.setId(id);
        return contactoInstituicaoEnsino;
    }
}
