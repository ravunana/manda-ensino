package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class FichaMedicaMapperTest {

    private FichaMedicaMapper fichaMedicaMapper;

    @BeforeEach
    public void setUp() {
        fichaMedicaMapper = new FichaMedicaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(fichaMedicaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fichaMedicaMapper.fromId(null)).isNull();
    }
}
