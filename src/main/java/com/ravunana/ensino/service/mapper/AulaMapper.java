package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.AulaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aula} and its DTO {@link AulaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PlanoAulaMapper.class, TurmaMapper.class})
public interface AulaMapper extends EntityMapper<AulaDTO, Aula> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "turma.id", target = "turmaId")
    @Mapping(source = "turma.descricao", target = "turmaDescricao")
    AulaDTO toDto(Aula aula);

    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(target = "removePlanoAula", ignore = true)
    @Mapping(source = "turmaId", target = "turma")
    Aula toEntity(AulaDTO aulaDTO);

    default Aula fromId(Long id) {
        if (id == null) {
            return null;
        }
        Aula aula = new Aula();
        aula.setId(id);
        return aula;
    }
}
