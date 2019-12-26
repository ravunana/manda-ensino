package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MatrizCurricularMapperTest {

    private MatrizCurricularMapper matrizCurricularMapper;

    @BeforeEach
    public void setUp() {
        matrizCurricularMapper = new MatrizCurricularMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(matrizCurricularMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(matrizCurricularMapper.fromId(null)).isNull();
    }
}
