package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SalaMapperTest {

    private SalaMapper salaMapper;

    @BeforeEach
    public void setUp() {
        salaMapper = new SalaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(salaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(salaMapper.fromId(null)).isNull();
    }
}
