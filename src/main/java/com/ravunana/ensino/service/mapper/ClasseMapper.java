package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.ClasseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Classe} and its DTO {@link ClasseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClasseMapper extends EntityMapper<ClasseDTO, Classe> {


    @Mapping(target = "turmas", ignore = true)
    @Mapping(target = "removeTurma", ignore = true)
    @Mapping(target = "planoCurriculars", ignore = true)
    @Mapping(target = "removePlanoCurricular", ignore = true)
    @Mapping(target = "unidadeCurriculars", ignore = true)
    @Mapping(target = "removeUnidadeCurricular", ignore = true)
    @Mapping(target = "emolumentos", ignore = true)
    @Mapping(target = "removeEmolumento", ignore = true)
    @Mapping(target = "dossificacaos", ignore = true)
    @Mapping(target = "removeDossificacao", ignore = true)
    Classe toEntity(ClasseDTO classeDTO);

    default Classe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Classe classe = new Classe();
        classe.setId(id);
        return classe;
    }
}
