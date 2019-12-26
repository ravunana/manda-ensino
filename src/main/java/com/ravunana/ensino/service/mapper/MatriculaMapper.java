package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.MatriculaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Matricula} and its DTO {@link MatriculaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AlunoMapper.class, CategoriaAlunoMapper.class, TurmaMapper.class})
public interface MatriculaMapper extends EntityMapper<MatriculaDTO, Matricula> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.numeroProcesso", target = "alunoNumeroProcesso")
    @Mapping(source = "confirmacao.id", target = "confirmacaoId")
    @Mapping(source = "confirmacao.numero", target = "confirmacaoNumero")
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    @Mapping(source = "turma.id", target = "turmaId")
    @Mapping(source = "turma.descricao", target = "turmaDescricao")
    MatriculaDTO toDto(Matricula matricula);

    @Mapping(target = "notas", ignore = true)
    @Mapping(target = "removeNota", ignore = true)
    @Mapping(target = "matriculas", ignore = true)
    @Mapping(target = "removeMatricula", ignore = true)
    @Mapping(target = "ocorrencias", ignore = true)
    @Mapping(target = "removeOcorrencia", ignore = true)
    @Mapping(target = "documentoMatriculas", ignore = true)
    @Mapping(target = "removeDocumentoMatricula", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(source = "confirmacaoId", target = "confirmacao")
    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(source = "turmaId", target = "turma")
    Matricula toEntity(MatriculaDTO matriculaDTO);

    default Matricula fromId(Long id) {
        if (id == null) {
            return null;
        }
        Matricula matricula = new Matricula();
        matricula.setId(id);
        return matricula;
    }
}
