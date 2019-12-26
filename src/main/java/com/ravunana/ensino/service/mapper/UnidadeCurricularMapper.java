package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.UnidadeCurricularDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnidadeCurricular} and its DTO {@link UnidadeCurricularDTO}.
 */
@Mapper(componentModel = "spring", uses = {DisciplinaMapper.class, ClasseMapper.class})
public interface UnidadeCurricularMapper extends EntityMapper<UnidadeCurricularDTO, UnidadeCurricular> {

    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nome", target = "disciplinaNome")
    @Mapping(source = "classe.id", target = "classeId")
    @Mapping(source = "classe.descricao", target = "classeDescricao")
    @Mapping(source = "herarquia.id", target = "herarquiaId")
    @Mapping(source = "herarquia.unidade", target = "herarquiaUnidade")
    UnidadeCurricularDTO toDto(UnidadeCurricular unidadeCurricular);

    @Mapping(target = "unidadeCurriculars", ignore = true)
    @Mapping(target = "removeUnidadeCurricular", ignore = true)
    @Mapping(source = "disciplinaId", target = "disciplina")
    @Mapping(source = "classeId", target = "classe")
    @Mapping(source = "herarquiaId", target = "herarquia")
    UnidadeCurricular toEntity(UnidadeCurricularDTO unidadeCurricularDTO);

    default UnidadeCurricular fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadeCurricular unidadeCurricular = new UnidadeCurricular();
        unidadeCurricular.setId(id);
        return unidadeCurricular;
    }
}
