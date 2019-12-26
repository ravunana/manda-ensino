package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.SalaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sala} and its DTO {@link SalaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalaMapper extends EntityMapper<SalaDTO, Sala> {


    @Mapping(target = "turmas", ignore = true)
    @Mapping(target = "removeTurma", ignore = true)
    Sala toEntity(SalaDTO salaDTO);

    default Sala fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sala sala = new Sala();
        sala.setId(id);
        return sala;
    }
}
