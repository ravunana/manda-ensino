package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.PlanoAulaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoAula} and its DTO {@link PlanoAulaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TurmaMapper.class, DisciplinaMapper.class, DossificacaoMapper.class})
public interface PlanoAulaMapper extends EntityMapper<PlanoAulaDTO, PlanoAula> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nome", target = "disciplinaNome")
    @Mapping(source = "dossificacao.id", target = "dossificacaoId")
    PlanoAulaDTO toDto(PlanoAula planoAula);

    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(target = "removeTurma", ignore = true)
    @Mapping(source = "disciplinaId", target = "disciplina")
    @Mapping(source = "dossificacaoId", target = "dossificacao")
    @Mapping(target = "aulas", ignore = true)
    @Mapping(target = "removeAula", ignore = true)
    PlanoAula toEntity(PlanoAulaDTO planoAulaDTO);

    default PlanoAula fromId(Long id) {
        if (id == null) {
            return null;
        }
        PlanoAula planoAula = new PlanoAula();
        planoAula.setId(id);
        return planoAula;
    }
}
