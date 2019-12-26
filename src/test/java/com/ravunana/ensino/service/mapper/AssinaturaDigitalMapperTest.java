package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AssinaturaDigitalMapperTest {

    private AssinaturaDigitalMapper assinaturaDigitalMapper;

    @BeforeEach
    public void setUp() {
        assinaturaDigitalMapper = new AssinaturaDigitalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(assinaturaDigitalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(assinaturaDigitalMapper.fromId(null)).isNull();
    }
}
