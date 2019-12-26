package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.AssinaturaDigitalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssinaturaDigital} and its DTO {@link AssinaturaDigitalDTO}.
 */
@Mapper(componentModel = "spring", uses = {InstituicaoEnsinoMapper.class})
public interface AssinaturaDigitalMapper extends EntityMapper<AssinaturaDigitalDTO, AssinaturaDigital> {

    @Mapping(source = "instituicao.id", target = "instituicaoId")
    @Mapping(source = "instituicao.nome", target = "instituicaoNome")
    AssinaturaDigitalDTO toDto(AssinaturaDigital assinaturaDigital);

    @Mapping(source = "instituicaoId", target = "instituicao")
    AssinaturaDigital toEntity(AssinaturaDigitalDTO assinaturaDigitalDTO);

    default AssinaturaDigital fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssinaturaDigital assinaturaDigital = new AssinaturaDigital();
        assinaturaDigital.setId(id);
        return assinaturaDigital;
    }
}
