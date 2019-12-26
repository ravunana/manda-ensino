package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CategoriaValiacaoMapperTest {

    private CategoriaValiacaoMapper categoriaValiacaoMapper;

    @BeforeEach
    public void setUp() {
        categoriaValiacaoMapper = new CategoriaValiacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(categoriaValiacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(categoriaValiacaoMapper.fromId(null)).isNull();
    }
}
