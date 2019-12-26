package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.DetalheOcorrenciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalheOcorrencia} and its DTO {@link DetalheOcorrenciaDTO}.
 */
@Mapper(componentModel = "spring", uses = {OcorrenciaMapper.class})
public interface DetalheOcorrenciaMapper extends EntityMapper<DetalheOcorrenciaDTO, DetalheOcorrencia> {

    @Mapping(source = "ocorrencia.id", target = "ocorrenciaId")
    @Mapping(source = "ocorrencia.numero", target = "ocorrenciaNumero")
    DetalheOcorrenciaDTO toDto(DetalheOcorrencia detalheOcorrencia);

    @Mapping(source = "ocorrenciaId", target = "ocorrencia")
    DetalheOcorrencia toEntity(DetalheOcorrenciaDTO detalheOcorrenciaDTO);

    default DetalheOcorrencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalheOcorrencia detalheOcorrencia = new DetalheOcorrencia();
        detalheOcorrencia.setId(id);
        return detalheOcorrencia;
    }
}
