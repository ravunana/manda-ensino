package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.CategoriaAlunoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriaAluno} and its DTO {@link CategoriaAlunoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaAlunoMapper extends EntityMapper<CategoriaAlunoDTO, CategoriaAluno> {


    @Mapping(target = "matriculas", ignore = true)
    @Mapping(target = "removeMatricula", ignore = true)
    CategoriaAluno toEntity(CategoriaAlunoDTO categoriaAlunoDTO);

    default CategoriaAluno fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoriaAluno categoriaAluno = new CategoriaAluno();
        categoriaAluno.setId(id);
        return categoriaAluno;
    }
}
