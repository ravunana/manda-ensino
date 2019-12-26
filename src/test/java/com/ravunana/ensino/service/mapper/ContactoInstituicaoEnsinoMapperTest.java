package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ContactoInstituicaoEnsinoMapperTest {

    private ContactoInstituicaoEnsinoMapper contactoInstituicaoEnsinoMapper;

    @BeforeEach
    public void setUp() {
        contactoInstituicaoEnsinoMapper = new ContactoInstituicaoEnsinoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(contactoInstituicaoEnsinoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contactoInstituicaoEnsinoMapper.fromId(null)).isNull();
    }
}
