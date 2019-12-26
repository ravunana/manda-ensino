package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DetalheOcorrenciaMapperTest {

    private DetalheOcorrenciaMapper detalheOcorrenciaMapper;

    @BeforeEach
    public void setUp() {
        detalheOcorrenciaMapper = new DetalheOcorrenciaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(detalheOcorrenciaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(detalheOcorrenciaMapper.fromId(null)).isNull();
    }
}
