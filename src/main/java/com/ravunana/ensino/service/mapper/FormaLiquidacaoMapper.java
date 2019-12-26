package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.FormaLiquidacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FormaLiquidacao} and its DTO {@link FormaLiquidacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FormaLiquidacaoMapper extends EntityMapper<FormaLiquidacaoDTO, FormaLiquidacao> {


    @Mapping(target = "pagamentos", ignore = true)
    @Mapping(target = "removePagamento", ignore = true)
    FormaLiquidacao toEntity(FormaLiquidacaoDTO formaLiquidacaoDTO);

    default FormaLiquidacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        FormaLiquidacao formaLiquidacao = new FormaLiquidacao();
        formaLiquidacao.setId(id);
        return formaLiquidacao;
    }
}
