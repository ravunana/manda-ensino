package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.NotaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nota} and its DTO {@link NotaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DisciplinaMapper.class, TurmaMapper.class, CategoriaValiacaoMapper.class, MatriculaMapper.class})
public interface NotaMapper extends EntityMapper<NotaDTO, Nota> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nome", target = "disciplinaNome")
    @Mapping(source = "turma.id", target = "turmaId")
    @Mapping(source = "turma.descricao", target = "turmaDescricao")
    @Mapping(source = "categoriaAvaliacao.id", target = "categoriaAvaliacaoId")
    @Mapping(source = "categoriaAvaliacao.nome", target = "categoriaAvaliacaoNome")
    @Mapping(source = "matricula.id", target = "matriculaId")
    @Mapping(source = "matricula.numero", target = "matriculaNumero")
    NotaDTO toDto(Nota nota);

    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "disciplinaId", target = "disciplina")
    @Mapping(source = "turmaId", target = "turma")
    @Mapping(source = "categoriaAvaliacaoId", target = "categoriaAvaliacao")
    @Mapping(source = "matriculaId", target = "matricula")
    Nota toEntity(NotaDTO notaDTO);

    default Nota fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nota nota = new Nota();
        nota.setId(id);
        return nota;
    }
}
