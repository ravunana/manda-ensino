package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.HorarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Horario} and its DTO {@link HorarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, TurmaMapper.class, ProfessorMapper.class, DisciplinaMapper.class})
public interface HorarioMapper extends EntityMapper<HorarioDTO, Horario> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "turma.id", target = "turmaId")
    @Mapping(source = "turma.descricao", target = "turmaDescricao")
    @Mapping(source = "professor.id", target = "professorId")
    @Mapping(source = "professor.numeroAgente", target = "professorNumeroAgente")
    @Mapping(source = "disciplina.id", target = "disciplinaId")
    @Mapping(source = "disciplina.nome", target = "disciplinaNome")
    HorarioDTO toDto(Horario horario);

    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "turmaId", target = "turma")
    @Mapping(source = "professorId", target = "professor")
    @Mapping(source = "disciplinaId", target = "disciplina")
    Horario toEntity(HorarioDTO horarioDTO);

    default Horario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Horario horario = new Horario();
        horario.setId(id);
        return horario;
    }
}
