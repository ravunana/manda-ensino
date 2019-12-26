package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.DocumentacaoPessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentacaoPessoa} and its DTO {@link DocumentacaoPessoaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class})
public interface DocumentacaoPessoaMapper extends EntityMapper<DocumentacaoPessoaDTO, DocumentacaoPessoa> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "pessoa.nome", target = "pessoaNome")
    DocumentacaoPessoaDTO toDto(DocumentacaoPessoa documentacaoPessoa);

    @Mapping(source = "pessoaId", target = "pessoa")
    DocumentacaoPessoa toEntity(DocumentacaoPessoaDTO documentacaoPessoaDTO);

    default DocumentacaoPessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentacaoPessoa documentacaoPessoa = new DocumentacaoPessoa();
        documentacaoPessoa.setId(id);
        return documentacaoPessoa;
    }
}
