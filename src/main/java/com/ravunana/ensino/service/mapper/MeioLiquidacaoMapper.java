package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.MeioLiquidacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeioLiquidacao} and its DTO {@link MeioLiquidacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MeioLiquidacaoMapper extends EntityMapper<MeioLiquidacaoDTO, MeioLiquidacao> {


    @Mapping(target = "depositos", ignore = true)
    @Mapping(target = "removeDeposito", ignore = true)
    MeioLiquidacao toEntity(MeioLiquidacaoDTO meioLiquidacaoDTO);

    default MeioLiquidacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        MeioLiquidacao meioLiquidacao = new MeioLiquidacao();
        meioLiquidacao.setId(id);
        return meioLiquidacao;
    }
}
