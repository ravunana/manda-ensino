package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.CoordenadaBancariaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CoordenadaBancaria} and its DTO {@link CoordenadaBancariaDTO}.
 */
@Mapper(componentModel = "spring", uses = {InstituicaoEnsinoMapper.class})
public interface CoordenadaBancariaMapper extends EntityMapper<CoordenadaBancariaDTO, CoordenadaBancaria> {


    @Mapping(target = "depositos", ignore = true)
    @Mapping(target = "removeDeposito", ignore = true)
    @Mapping(target = "removeInstituicaoEnsino", ignore = true)
    CoordenadaBancaria toEntity(CoordenadaBancariaDTO coordenadaBancariaDTO);

    default CoordenadaBancaria fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoordenadaBancaria coordenadaBancaria = new CoordenadaBancaria();
        coordenadaBancaria.setId(id);
        return coordenadaBancaria;
    }
}
