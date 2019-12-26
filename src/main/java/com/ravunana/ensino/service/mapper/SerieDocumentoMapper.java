package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.SerieDocumentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SerieDocumento} and its DTO {@link SerieDocumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {InstituicaoEnsinoMapper.class})
public interface SerieDocumentoMapper extends EntityMapper<SerieDocumentoDTO, SerieDocumento> {

    @Mapping(source = "instituicaoEnsino.id", target = "instituicaoEnsinoId")
    @Mapping(source = "instituicaoEnsino.nome", target = "instituicaoEnsinoNome")
    SerieDocumentoDTO toDto(SerieDocumento serieDocumento);

    @Mapping(source = "instituicaoEnsinoId", target = "instituicaoEnsino")
    SerieDocumento toEntity(SerieDocumentoDTO serieDocumentoDTO);

    default SerieDocumento fromId(Long id) {
        if (id == null) {
            return null;
        }
        SerieDocumento serieDocumento = new SerieDocumento();
        serieDocumento.setId(id);
        return serieDocumento;
    }
}
