package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DetalhePagamentoMapperTest {

    private DetalhePagamentoMapper detalhePagamentoMapper;

    @BeforeEach
    public void setUp() {
        detalhePagamentoMapper = new DetalhePagamentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(detalhePagamentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(detalhePagamentoMapper.fromId(null)).isNull();
    }
}
