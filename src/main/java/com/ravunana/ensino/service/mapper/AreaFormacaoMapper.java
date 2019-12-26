package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.AreaFormacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AreaFormacao} and its DTO {@link AreaFormacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AreaFormacaoMapper extends EntityMapper<AreaFormacaoDTO, AreaFormacao> {


    @Mapping(target = "cursos", ignore = true)
    @Mapping(target = "removeCurso", ignore = true)
    @Mapping(target = "categoriaValiacaos", ignore = true)
    @Mapping(target = "removeCategoriaValiacao", ignore = true)
    AreaFormacao toEntity(AreaFormacaoDTO areaFormacaoDTO);

    default AreaFormacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        AreaFormacao areaFormacao = new AreaFormacao();
        areaFormacao.setId(id);
        return areaFormacao;
    }
}
