package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DocumentoMatriculaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoMatriculaDTO.class);
        DocumentoMatriculaDTO documentoMatriculaDTO1 = new DocumentoMatriculaDTO();
        documentoMatriculaDTO1.setId(1L);
        DocumentoMatriculaDTO documentoMatriculaDTO2 = new DocumentoMatriculaDTO();
        assertThat(documentoMatriculaDTO1).isNotEqualTo(documentoMatriculaDTO2);
        documentoMatriculaDTO2.setId(documentoMatriculaDTO1.getId());
        assertThat(documentoMatriculaDTO1).isEqualTo(documentoMatriculaDTO2);
        documentoMatriculaDTO2.setId(2L);
        assertThat(documentoMatriculaDTO1).isNotEqualTo(documentoMatriculaDTO2);
        documentoMatriculaDTO1.setId(null);
        assertThat(documentoMatriculaDTO1).isNotEqualTo(documentoMatriculaDTO2);
    }
}
