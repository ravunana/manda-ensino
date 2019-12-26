package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SituacaoAcademicaMapperTest {

    private SituacaoAcademicaMapper situacaoAcademicaMapper;

    @BeforeEach
    public void setUp() {
        situacaoAcademicaMapper = new SituacaoAcademicaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(situacaoAcademicaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(situacaoAcademicaMapper.fromId(null)).isNull();
    }
}
