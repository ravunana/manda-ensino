package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.InstituicaoEnsinoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InstituicaoEnsino} and its DTO {@link InstituicaoEnsinoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface InstituicaoEnsinoMapper extends EntityMapper<InstituicaoEnsinoDTO, InstituicaoEnsino> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "hierarquia.id", target = "hierarquiaId")
    @Mapping(source = "hierarquia.nome", target = "hierarquiaNome")
    InstituicaoEnsinoDTO toDto(InstituicaoEnsino instituicaoEnsino);

    @Mapping(target = "instituicaoEnsinos", ignore = true)
    @Mapping(target = "removeInstituicaoEnsino", ignore = true)
    @Mapping(target = "localizacaoInstituicaoEnsinos", ignore = true)
    @Mapping(target = "removeLocalizacaoInstituicaoEnsino", ignore = true)
    @Mapping(target = "contactoInstituicaoEnsinos", ignore = true)
    @Mapping(target = "removeContactoInstituicaoEnsino", ignore = true)
    @Mapping(target = "licencaSoftwares", ignore = true)
    @Mapping(target = "removeLicencaSoftware", ignore = true)
    @Mapping(target = "assinaturaDigitals", ignore = true)
    @Mapping(target = "removeAssinaturaDigital", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "hierarquiaId", target = "hierarquia")
    @Mapping(target = "coordenadaBancarias", ignore = true)
    @Mapping(target = "removeCoordenadaBancaria", ignore = true)
    InstituicaoEnsino toEntity(InstituicaoEnsinoDTO instituicaoEnsinoDTO);

    default InstituicaoEnsino fromId(Long id) {
        if (id == null) {
            return null;
        }
        InstituicaoEnsino instituicaoEnsino = new InstituicaoEnsino();
        instituicaoEnsino.setId(id);
        return instituicaoEnsino;
    }
}
