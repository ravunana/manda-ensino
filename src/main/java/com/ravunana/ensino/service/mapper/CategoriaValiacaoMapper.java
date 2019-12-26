package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.CategoriaValiacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriaValiacao} and its DTO {@link CategoriaValiacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AreaFormacaoMapper.class})
public interface CategoriaValiacaoMapper extends EntityMapper<CategoriaValiacaoDTO, CategoriaValiacao> {

    @Mapping(source = "areaFormacao.id", target = "areaFormacaoId")
    @Mapping(source = "areaFormacao.nome", target = "areaFormacaoNome")
    CategoriaValiacaoDTO toDto(CategoriaValiacao categoriaValiacao);

    @Mapping(target = "notas", ignore = true)
    @Mapping(target = "removeNota", ignore = true)
    @Mapping(source = "areaFormacaoId", target = "areaFormacao")
    CategoriaValiacao toEntity(CategoriaValiacaoDTO categoriaValiacaoDTO);

    default CategoriaValiacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoriaValiacao categoriaValiacao = new CategoriaValiacao();
        categoriaValiacao.setId(id);
        return categoriaValiacao;
    }
}
