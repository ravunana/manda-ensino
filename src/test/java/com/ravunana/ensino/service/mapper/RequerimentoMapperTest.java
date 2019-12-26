package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class RequerimentoMapperTest {

    private RequerimentoMapper requerimentoMapper;

    @BeforeEach
    public void setUp() {
        requerimentoMapper = new RequerimentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(requerimentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(requerimentoMapper.fromId(null)).isNull();
    }
}
