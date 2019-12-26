package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.ContratoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contrato} and its DTO {@link ContratoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlunoMapper.class})
public interface ContratoMapper extends EntityMapper<ContratoDTO, Contrato> {

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.numeroProcesso", target = "alunoNumeroProcesso")
    ContratoDTO toDto(Contrato contrato);

    @Mapping(source = "alunoId", target = "aluno")
    Contrato toEntity(ContratoDTO contratoDTO);

    default Contrato fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contrato contrato = new Contrato();
        contrato.setId(id);
        return contrato;
    }
}
