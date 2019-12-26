package com.ravunana.ensino.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DocumentoMatriculaMapperTest {

    private DocumentoMatriculaMapper documentoMatriculaMapper;

    @BeforeEach
    public void setUp() {
        documentoMatriculaMapper = new DocumentoMatriculaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(documentoMatriculaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentoMatriculaMapper.fromId(null)).isNull();
    }
}
