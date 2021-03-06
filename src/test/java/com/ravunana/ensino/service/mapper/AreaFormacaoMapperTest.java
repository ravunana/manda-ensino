package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AreaFormacaoMapperTest {

    private AreaFormacaoMapper areaFormacaoMapper;

    @BeforeEach
    public void setUp() {
        areaFormacaoMapper = new AreaFormacaoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(areaFormacaoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(areaFormacaoMapper.fromId(null)).isNull();
    }
}
