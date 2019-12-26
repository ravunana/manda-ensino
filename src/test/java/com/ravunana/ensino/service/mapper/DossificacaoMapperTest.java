package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DossificacaoMapperTest {

    private DossificacaoMapper dossificacaoMapper;

    @BeforeEach
    public void setUp() {
        dossificacaoMapper = new DossificacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(dossificacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dossificacaoMapper.fromId(null)).isNull();
    }
}
