package com.ravunana.ensino.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DocumentacaoPessoaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentacaoPessoa.class);
        DocumentacaoPessoa documentacaoPessoa1 = new DocumentacaoPessoa();
        documentacaoPessoa1.setId(1L);
        DocumentacaoPessoa documentacaoPessoa2 = new DocumentacaoPessoa();
        documentacaoPessoa2.setId(documentacaoPessoa1.getId());
        assertThat(documentacaoPessoa1).isEqualTo(documentacaoPessoa2);
        documentacaoPessoa2.setId(2L);
        assertThat(documentacaoPessoa1).isNotEqualTo(documentacaoPessoa2);
        documentacaoPessoa1.setId(null);
        assertThat(documentacaoPessoa1).isNotEqualTo(documentacaoPessoa2);
    }
}
