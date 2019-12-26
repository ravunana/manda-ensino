package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.PlanoCurricularDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoCurricular} and its DTO {@link PlanoCurricularDTO}.
 */
@Mapper(componentModel = "spring", uses = {DisciplinaMapper.class, ClasseMapper.class})
public interface PlanoCurricularMapper extends EntityMapper<PlanoCurricularDTO, PlanoCurricular> {

    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nome", target = "disciplinaNome")
    @Mapping(source = "classe.id", target = "classeId")
    @Mapping(source = "classe.descricao", target = "classeDescricao")
    PlanoCurricularDTO toDto(PlanoCurricular planoCurricular);

    @Mapping(source = "disciplinaId", target = "disciplina")
    @Mapping(source = "classeId", target = "classe")
    PlanoCurricular toEntity(PlanoCurricularDTO planoCurricularDTO);

    default PlanoCurricular fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoCurricular planoCurricular = new PlanoCurricular();
        planoCurricular.setId(id);
        return planoCurricular;
    }
}
