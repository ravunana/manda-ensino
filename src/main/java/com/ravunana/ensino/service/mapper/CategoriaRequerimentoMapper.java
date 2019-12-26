package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.CategoriaRequerimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriaRequerimento} and its DTO {@link CategoriaRequerimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaRequerimentoMapper extends EntityMapper<CategoriaRequerimentoDTO, CategoriaRequerimento> {


    @Mapping(target = "requerimentos", ignore = true)
    @Mapping(target = "removeRequerimento", ignore = true)
    CategoriaRequerimento toEntity(CategoriaRequerimentoDTO categoriaRequerimentoDTO);

    default CategoriaRequerimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoriaRequerimento categoriaRequerimento = new CategoriaRequerimento();
        categoriaRequerimento.setId(id);
        return categoriaRequerimento;
    }
}
