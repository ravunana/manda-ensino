package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.LocalizacaoInstituicaoEnsinoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocalizacaoInstituicaoEnsino} and its DTO {@link LocalizacaoInstituicaoEnsinoDTO}.
 */
@Mapper(componentModel = "spring", uses = {InstituicaoEnsinoMapper.class})
public interface LocalizacaoInstituicaoEnsinoMapper extends EntityMapper<LocalizacaoInstituicaoEnsinoDTO, LocalizacaoInstituicaoEnsino> {

    @Mapping(source = "instituicaoEnsino.id", target = "instituicaoEnsinoId")
    @Mapping(source = "instituicaoEnsino.nome", target = "instituicaoEnsinoNome")
    LocalizacaoInstituicaoEnsinoDTO toDto(LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino);

    @Mapping(source = "instituicaoEnsinoId", target = "instituicaoEnsino")
    LocalizacaoInstituicaoEnsino toEntity(LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO);

    default LocalizacaoInstituicaoEnsino fromId(Long id) {
        if (id == null) {
            return null;
        }
        LocalizacaoInstituicaoEnsino localizacaoInstituicaoEnsino = new LocalizacaoInstituicaoEnsino();
        localizacaoInstituicaoEnsino.setId(id);
        return localizacaoInstituicaoEnsino;
    }
}
