package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.EmolumentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Emolumento} and its DTO {@link EmolumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CursoMapper.class, ClasseMapper.class})
public interface EmolumentoMapper extends EntityMapper<EmolumentoDTO, Emolumento> {

    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(source = "curso.nome", target = "cursoNome")
    @Mapping(source = "classe.id", target = "classeId")
    @Mapping(source = "classe.descricao", target = "classeDescricao")
    EmolumentoDTO toDto(Emolumento emolumento);

    @Mapping(target = "detalhePagamentos", ignore = true)
    @Mapping(target = "removeDetalhePagamento", ignore = true)
    @Mapping(source = "cursoId", target = "curso")
    @Mapping(source = "classeId", target = "classe")
    Emolumento toEntity(EmolumentoDTO emolumentoDTO);

    default Emolumento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Emolumento emolumento = new Emolumento();
        emolumento.setId(id);
        return emolumento;
    }
}
