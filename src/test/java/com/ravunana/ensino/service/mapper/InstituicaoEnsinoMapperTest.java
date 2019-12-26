package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class InstituicaoEnsinoMapperTest {

    private InstituicaoEnsinoMapper instituicaoEnsinoMapper;

    @BeforeEach
    public void setUp() {
        instituicaoEnsinoMapper = new InstituicaoEnsinoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(instituicaoEnsinoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(instituicaoEnsinoMapper.fromId(null)).isNull();
    }
}
