package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.DisciplinaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Disciplina} and its DTO {@link DisciplinaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DisciplinaMapper extends EntityMapper<DisciplinaDTO, Disciplina> {


    @Mapping(target = "planoCurriculars", ignore = true)
    @Mapping(target = "removePlanoCurricular", ignore = true)
    @Mapping(target = "horarios", ignore = true)
    @Mapping(target = "removeHorario", ignore = true)
    @Mapping(target = "notas", ignore = true)
    @Mapping(target = "removeNota", ignore = true)
    @Mapping(target = "planoAulas", ignore = true)
    @Mapping(target = "removePlanoAula", ignore = true)
    @Mapping(target = "dossificacaos", ignore = true)
    @Mapping(target = "removeDossificacao", ignore = true)
    @Mapping(target = "unidadeCurriculars", ignore = true)
    @Mapping(target = "removeUnidadeCurricular", ignore = true)
    @Mapping(target = "situacaoAcademicas", ignore = true)
    @Mapping(target = "removeSituacaoAcademica", ignore = true)
    Disciplina toEntity(DisciplinaDTO disciplinaDTO);

    default Disciplina fromId(Long id) {
        if (id == null) {
            return null;
        }
        Disciplina disciplina = new Disciplina();
        disciplina.setId(id);
        return disciplina;
    }
}
