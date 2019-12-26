package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class LocalizacaoInstituicaoEnsinoMapperTest {

    private LocalizacaoInstituicaoEnsinoMapper localizacaoInstituicaoEnsinoMapper;

    @BeforeEach
    public void setUp() {
        localizacaoInstituicaoEnsinoMapper = new LocalizacaoInstituicaoEnsinoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(localizacaoInstituicaoEnsinoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(localizacaoInstituicaoEnsinoMapper.fromId(null)).isNull();
    }
}
