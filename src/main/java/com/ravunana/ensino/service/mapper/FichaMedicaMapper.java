package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.FichaMedicaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FichaMedica} and its DTO {@link FichaMedicaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlunoMapper.class, UserMapper.class})
public interface FichaMedicaMapper extends EntityMapper<FichaMedicaDTO, FichaMedica> {

    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.numeroProcesso", target = "alunoNumeroProcesso")
    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    FichaMedicaDTO toDto(FichaMedica fichaMedica);

    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(source = "utilizadorId", target = "utilizador")
    FichaMedica toEntity(FichaMedicaDTO fichaMedicaDTO);

    default FichaMedica fromId(Long id) {
        if (id == null) {
            return null;
        }
        FichaMedica fichaMedica = new FichaMedica();
        fichaMedica.setId(id);
        return fichaMedica;
    }
}
