package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.LookupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lookup} and its DTO {@link LookupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LookupMapper extends EntityMapper<LookupDTO, Lookup> {


    @Mapping(target = "lookupItems", ignore = true)
    @Mapping(target = "removeLookupItem", ignore = true)
    Lookup toEntity(LookupDTO lookupDTO);

    default Lookup fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lookup lookup = new Lookup();
        lookup.setId(id);
        return lookup;
    }
}
