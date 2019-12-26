package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DocumentacaoPessoaMapperTest {

    private DocumentacaoPessoaMapper documentacaoPessoaMapper;

    @BeforeEach
    public void setUp() {
        documentacaoPessoaMapper = new DocumentacaoPessoaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(documentacaoPessoaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentacaoPessoaMapper.fromId(null)).isNull();
    }
}
