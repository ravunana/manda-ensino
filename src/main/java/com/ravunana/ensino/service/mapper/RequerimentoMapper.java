package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.RequerimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Requerimento} and its DTO {@link RequerimentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoriaRequerimentoMapper.class, AlunoMapper.class})
public interface RequerimentoMapper extends EntityMapper<RequerimentoDTO, Requerimento> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.numeroProcesso", target = "alunoNumeroProcesso")
    RequerimentoDTO toDto(Requerimento requerimento);

    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(source = "alunoId", target = "aluno")
    Requerimento toEntity(RequerimentoDTO requerimentoDTO);

    default Requerimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Requerimento requerimento = new Requerimento();
        requerimento.setId(id);
        return requerimento;
    }
}
