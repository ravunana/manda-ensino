package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.EncarregadoEducacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EncarregadoEducacao} and its DTO {@link EncarregadoEducacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface EncarregadoEducacaoMapper extends EntityMapper<EncarregadoEducacaoDTO, EncarregadoEducacao> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    EncarregadoEducacaoDTO toDto(EncarregadoEducacao encarregadoEducacao);

    @Mapping(source = "pessoaId", target = "pessoa")
    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "removeAluno", ignore = true)
    EncarregadoEducacao toEntity(EncarregadoEducacaoDTO encarregadoEducacaoDTO);

    default EncarregadoEducacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        EncarregadoEducacao encarregadoEducacao = new EncarregadoEducacao();
        encarregadoEducacao.setId(id);
        return encarregadoEducacao;
    }
}
