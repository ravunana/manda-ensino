package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.OcorrenciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ocorrencia} and its DTO {@link OcorrenciaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, MatriculaMapper.class})
public interface OcorrenciaMapper extends EntityMapper<OcorrenciaDTO, Ocorrencia> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "matricula.id", target = "matriculaId")
    @Mapping(source = "matricula.numero", target = "matriculaNumero")
    OcorrenciaDTO toDto(Ocorrencia ocorrencia);

    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "matriculaId", target = "matricula")
    Ocorrencia toEntity(OcorrenciaDTO ocorrenciaDTO);

    default Ocorrencia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setId(id);
        return ocorrencia;
    }
}
