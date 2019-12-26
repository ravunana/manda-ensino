package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.PessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    PessoaDTO toDto(Pessoa pessoa);

    @Mapping(target = "moradaPessoas", ignore = true)
    @Mapping(target = "removeMoradaPessoa", ignore = true)
    @Mapping(target = "contactoPessoas", ignore = true)
    @Mapping(target = "removeContactoPessoa", ignore = true)
    @Mapping(target = "documentacaoPessoas", ignore = true)
    @Mapping(target = "removeDocumentacaoPessoa", ignore = true)
    @Mapping(source = "utilizadorId", target = "utilizador")
    Pessoa toEntity(PessoaDTO pessoaDTO);

    default Pessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        return pessoa;
    }
}
