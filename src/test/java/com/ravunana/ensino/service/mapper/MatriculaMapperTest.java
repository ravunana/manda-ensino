package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MatriculaMapperTest {

    private MatriculaMapper matriculaMapper;

    @BeforeEach
    public void setUp() {
        matriculaMapper = new MatriculaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(matriculaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(matriculaMapper.fromId(null)).isNull();
    }
}
