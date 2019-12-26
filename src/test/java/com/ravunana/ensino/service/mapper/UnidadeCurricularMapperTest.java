package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class UnidadeCurricularMapperTest {

    private UnidadeCurricularMapper unidadeCurricularMapper;

    @BeforeEach
    public void setUp() {
        unidadeCurricularMapper = new UnidadeCurricularMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(unidadeCurricularMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(unidadeCurricularMapper.fromId(null)).isNull();
    }
}
