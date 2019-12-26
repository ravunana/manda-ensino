package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.LicencaSoftwareDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LicencaSoftware} and its DTO {@link LicencaSoftwareDTO}.
 */
@Mapper(componentModel = "spring", uses = {SoftwareMapper.class, InstituicaoEnsinoMapper.class})
public interface LicencaSoftwareMapper extends EntityMapper<LicencaSoftwareDTO, LicencaSoftware> {

    @Mapping(source = "software.id", target = "softwareId")
    @Mapping(source = "software.nome", target = "softwareNome")
    @Mapping(source = "instituicaoEnsino.id", target = "instituicaoEnsinoId")
    @Mapping(source = "instituicaoEnsino.nome", target = "instituicaoEnsinoNome")
    LicencaSoftwareDTO toDto(LicencaSoftware licencaSoftware);

    @Mapping(source = "softwareId", target = "software")
    @Mapping(source = "instituicaoEnsinoId", target = "instituicaoEnsino")
    LicencaSoftware toEntity(LicencaSoftwareDTO licencaSoftwareDTO);

    default LicencaSoftware fromId(Long id) {
        if (id == null) {
            return null;
        }
        LicencaSoftware licencaSoftware = new LicencaSoftware();
        licencaSoftware.setId(id);
        return licencaSoftware;
    }
}
