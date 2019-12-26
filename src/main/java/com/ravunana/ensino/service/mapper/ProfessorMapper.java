package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.ProfessorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Professor} and its DTO {@link ProfessorDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class, UserMapper.class})
public interface ProfessorMapper extends EntityMapper<ProfessorDTO, Professor> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    ProfessorDTO toDto(Professor professor);

    @Mapping(source = "pessoaId", target = "pessoa")
    @Mapping(target = "horarios", ignore = true)
    @Mapping(target = "removeHorario", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    Professor toEntity(ProfessorDTO professorDTO);

    default Professor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Professor professor = new Professor();
        professor.setId(id);
        return professor;
    }
}
