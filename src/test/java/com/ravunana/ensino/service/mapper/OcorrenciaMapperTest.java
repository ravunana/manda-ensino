package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class OcorrenciaMapperTest {

    private OcorrenciaMapper ocorrenciaMapper;

    @BeforeEach
    public void setUp() {
        ocorrenciaMapper = new OcorrenciaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(ocorrenciaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ocorrenciaMapper.fromId(null)).isNull();
    }
}
