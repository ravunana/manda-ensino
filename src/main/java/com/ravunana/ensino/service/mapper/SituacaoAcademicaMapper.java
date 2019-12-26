package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.SituacaoAcademicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SituacaoAcademica} and its DTO {@link SituacaoAcademicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlunoMapper.class, DisciplinaMapper.class})
public interface SituacaoAcademicaMapper extends EntityMapper<SituacaoAcademicaDTO, SituacaoAcademica> {

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.numeroProcesso", target = "alunoNumeroProcesso")
    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nome", target = "disciplinaNome")
    SituacaoAcademicaDTO toDto(SituacaoAcademica situacaoAcademica);

    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(source = "disciplinaId", target = "disciplina")
    SituacaoAcademica toEntity(SituacaoAcademicaDTO situacaoAcademicaDTO);

    default SituacaoAcademica fromId(Long id) {
        if (id == null) {
            return null;
        }
        SituacaoAcademica situacaoAcademica = new SituacaoAcademica();
        situacaoAcademica.setId(id);
        return situacaoAcademica;
    }
}
