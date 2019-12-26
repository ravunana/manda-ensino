package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CategoriaRequerimentoMapperTest {

    private CategoriaRequerimentoMapper categoriaRequerimentoMapper;

    @BeforeEach
    public void setUp() {
        categoriaRequerimentoMapper = new CategoriaRequerimentoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(categoriaRequerimentoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(categoriaRequerimentoMapper.fromId(null)).isNull();
    }
}
