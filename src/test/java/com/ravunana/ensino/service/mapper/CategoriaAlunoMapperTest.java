package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CategoriaAlunoMapperTest {

    private CategoriaAlunoMapper categoriaAlunoMapper;

    @BeforeEach
    public void setUp() {
        categoriaAlunoMapper = new CategoriaAlunoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(categoriaAlunoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(categoriaAlunoMapper.fromId(null)).isNull();
    }
}
