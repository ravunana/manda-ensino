package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.MatrizCurricularDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatrizCurricular} and its DTO {@link MatrizCurricularDTO}.
 */
@Mapper(componentModel = "spring", uses = {CursoMapper.class})
public interface MatrizCurricularMapper extends EntityMapper<MatrizCurricularDTO, MatrizCurricular> {

    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(source = "curso.nome", target = "cursoNome")
    MatrizCurricularDTO toDto(MatrizCurricular matrizCurricular);

    @Mapping(source = "cursoId", target = "curso")
    MatrizCurricular toEntity(MatrizCurricularDTO matrizCurricularDTO);

    default MatrizCurricular fromId(Long id) {
        if (id == null) {
            return null;
        }
        MatrizCurricular matrizCurricular = new MatrizCurricular();
        matrizCurricular.setId(id);
        return matrizCurricular;
    }
}
