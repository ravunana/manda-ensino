package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ContratoMapperTest {

    private ContratoMapper contratoMapper;

    @BeforeEach
    public void setUp() {
        contratoMapper = new ContratoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(contratoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contratoMapper.fromId(null)).isNull();
    }
}
