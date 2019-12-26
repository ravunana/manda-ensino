package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DocumentoMatriculaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentoMatricula.class);
        DocumentoMatricula documentoMatricula1 = new DocumentoMatricula();
        documentoMatricula1.setId(1L);
        DocumentoMatricula documentoMatricula2 = new DocumentoMatricula();
        documentoMatricula2.setId(documentoMatricula1.getId());
        assertThat(documentoMatricula1).isEqualTo(documentoMatricula2);
        documentoMatricula2.setId(2L);
        assertThat(documentoMatricula1).isNotEqualTo(documentoMatricula2);
        documentoMatricula1.setId(null);
        assertThat(documentoMatricula1).isNotEqualTo(documentoMatricula2);
    }
}
