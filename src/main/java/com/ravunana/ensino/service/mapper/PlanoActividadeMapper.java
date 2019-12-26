package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.PlanoActividadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoActividade} and its DTO {@link PlanoActividadeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PlanoActividadeMapper extends EntityMapper<PlanoActividadeDTO, PlanoActividade> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    PlanoActividadeDTO toDto(PlanoActividade planoActividade);

    @Mapping(source = "utilizadorId", target = "utilizador")
    PlanoActividade toEntity(PlanoActividadeDTO planoActividadeDTO);

    default PlanoActividade fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoActividade planoActividade = new PlanoActividade();
        planoActividade.setId(id);
        return planoActividade;
    }
}
