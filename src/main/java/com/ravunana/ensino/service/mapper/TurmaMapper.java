package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.TurmaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Turma} and its DTO {@link TurmaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SalaMapper.class, ClasseMapper.class, CursoMapper.class})
public interface TurmaMapper extends EntityMapper<TurmaDTO, Turma> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "sala.id", target = "salaId")
    @Mapping(source = "sala.numero", target = "salaNumero")
    @Mapping(source = "classe.id", target = "classeId")
    @Mapping(source = "classe.descricao", target = "classeDescricao")
    @Mapping(source = "curso.id", target = "cursoId")
    @Mapping(source = "curso.nome", target = "cursoNome")
    TurmaDTO toDto(Turma turma);

    @Mapping(target = "horarios", ignore = true)
    @Mapping(target = "removeHorario", ignore = true)
    @Mapping(target = "notas", ignore = true)
    @Mapping(target = "removeNota", ignore = true)
    @Mapping(target = "aulas", ignore = true)
    @Mapping(target = "removeAula", ignore = true)
    @Mapping(target = "matriculas", ignore = true)
    @Mapping(target = "removeMatricula", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "salaId", target = "sala")
    @Mapping(source = "classeId", target = "classe")
    @Mapping(source = "cursoId", target = "curso")
    @Mapping(target = "planoAulas", ignore = true)
    @Mapping(target = "removePlanoAula", ignore = true)
    Turma toEntity(TurmaDTO turmaDTO);

    default Turma fromId(Long id) {
        if (id == null) {
            return null;
        }
        Turma turma = new Turma();
        turma.setId(id);
        return turma;
    }
}
