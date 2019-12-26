package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.DossificacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dossificacao} and its DTO {@link DossificacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {CursoMapper.class, ClasseMapper.class, DisciplinaMapper.class})
public interface DossificacaoMapper extends EntityMapper<DossificacaoDTO, Dossificacao> {

    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nome", target = "disciplinaNome")
    DossificacaoDTO toDto(Dossificacao dossificacao);

    @Mapping(target = "planoAulas", ignore = true)
    @Mapping(target = "removePlanoAula", ignore = true)
    @Mapping(target = "removeCurso", ignore = true)
    @Mapping(target = "removeClasse", ignore = true)
    @Mapping(source = "disciplinaId", target = "disciplina")
    Dossificacao toEntity(DossificacaoDTO dossificacaoDTO);

    default Dossificacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dossificacao dossificacao = new Dossificacao();
        dossificacao.setId(id);
        return dossificacao;
    }
}
