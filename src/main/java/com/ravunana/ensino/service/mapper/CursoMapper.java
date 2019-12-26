package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.CursoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Curso} and its DTO {@link CursoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AreaFormacaoMapper.class})
public interface CursoMapper extends EntityMapper<CursoDTO, Curso> {

    @Mapping(source = "areaFormacao.id", target = "areaFormacaoId")
    @Mapping(source = "areaFormacao.nome", target = "areaFormacaoNome")
    CursoDTO toDto(Curso curso);

    @Mapping(target = "turmas", ignore = true)
    @Mapping(target = "removeTurma", ignore = true)
    @Mapping(target = "matrizCurriculars", ignore = true)
    @Mapping(target = "removeMatrizCurricular", ignore = true)
    @Mapping(target = "emolumentos", ignore = true)
    @Mapping(target = "removeEmolumento", ignore = true)
    @Mapping(source = "areaFormacaoId", target = "areaFormacao")
    @Mapping(target = "dossificacaos", ignore = true)
    @Mapping(target = "removeDossificacao", ignore = true)
    Curso toEntity(CursoDTO cursoDTO);

    default Curso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Curso curso = new Curso();
        curso.setId(id);
        return curso;
    }
}
