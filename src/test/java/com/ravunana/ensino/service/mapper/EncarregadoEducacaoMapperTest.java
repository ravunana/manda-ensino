package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EncarregadoEducacaoMapperTest {

    private EncarregadoEducacaoMapper encarregadoEducacaoMapper;

    @BeforeEach
    public void setUp() {
        encarregadoEducacaoMapper = new EncarregadoEducacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(encarregadoEducacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(encarregadoEducacaoMapper.fromId(null)).isNull();
    }
}
