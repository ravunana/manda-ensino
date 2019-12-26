package com.ravunana.ensino.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ravunana.ensino.web.rest.TestUtil;

public class DocumentacaoPessoaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentacaoPessoaDTO.class);
        DocumentacaoPessoaDTO documentacaoPessoaDTO1 = new DocumentacaoPessoaDTO();
        documentacaoPessoaDTO1.setId(1L);
        DocumentacaoPessoaDTO documentacaoPessoaDTO2 = new DocumentacaoPessoaDTO();
        assertThat(documentacaoPessoaDTO1).isNotEqualTo(documentacaoPessoaDTO2);
        documentacaoPessoaDTO2.setId(documentacaoPessoaDTO1.getId());
        assertThat(documentacaoPessoaDTO1).isEqualTo(documentacaoPessoaDTO2);
        documentacaoPessoaDTO2.setId(2L);
        assertThat(documentacaoPessoaDTO1).isNotEqualTo(documentacaoPessoaDTO2);
        documentacaoPessoaDTO1.setId(null);
        assertThat(documentacaoPessoaDTO1).isNotEqualTo(documentacaoPessoaDTO2);
    }
}
