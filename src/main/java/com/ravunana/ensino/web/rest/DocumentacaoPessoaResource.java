package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.DocumentacaoPessoaService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.DocumentacaoPessoaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.ravunana.ensino.domain.DocumentacaoPessoa}.
 */
@RestController
@RequestMapping("/api")
public class DocumentacaoPessoaResource {

    private final Logger log = LoggerFactory.getLogger(DocumentacaoPessoaResource.class);

    private static final String ENTITY_NAME = "documentacaoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentacaoPessoaService documentacaoPessoaService;

    public DocumentacaoPessoaResource(DocumentacaoPessoaService documentacaoPessoaService) {
        this.documentacaoPessoaService = documentacaoPessoaService;
    }

    /**
     * {@code POST  /documentacao-pessoas} : Create a new documentacaoPessoa.
     *
     * @param documentacaoPessoaDTO the documentacaoPessoaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentacaoPessoaDTO, or with status {@code 400 (Bad Request)} if the documentacaoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentacao-pessoas")
    public ResponseEntity<DocumentacaoPessoaDTO> createDocumentacaoPessoa(@Valid @RequestBody DocumentacaoPessoaDTO documentacaoPessoaDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentacaoPessoa : {}", documentacaoPessoaDTO);
        if (documentacaoPessoaDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentacaoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentacaoPessoaDTO result = documentacaoPessoaService.save(documentacaoPessoaDTO);
        return ResponseEntity.created(new URI("/api/documentacao-pessoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documentacao-pessoas} : Updates an existing documentacaoPessoa.
     *
     * @param documentacaoPessoaDTO the documentacaoPessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentacaoPessoaDTO,
     * or with status {@code 400 (Bad Request)} if the documentacaoPessoaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentacaoPessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentacao-pessoas")
    public ResponseEntity<DocumentacaoPessoaDTO> updateDocumentacaoPessoa(@Valid @RequestBody DocumentacaoPessoaDTO documentacaoPessoaDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentacaoPessoa : {}", documentacaoPessoaDTO);
        if (documentacaoPessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentacaoPessoaDTO result = documentacaoPessoaService.save(documentacaoPessoaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentacaoPessoaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documentacao-pessoas} : get all the documentacaoPessoas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentacaoPessoas in body.
     */
    @GetMapping("/documentacao-pessoas")
    public ResponseEntity<List<DocumentacaoPessoaDTO>> getAllDocumentacaoPessoas(Pageable pageable) {
        log.debug("REST request to get a page of DocumentacaoPessoas");
        Page<DocumentacaoPessoaDTO> page = documentacaoPessoaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documentacao-pessoas/:id} : get the "id" documentacaoPessoa.
     *
     * @param id the id of the documentacaoPessoaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentacaoPessoaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentacao-pessoas/{id}")
    public ResponseEntity<DocumentacaoPessoaDTO> getDocumentacaoPessoa(@PathVariable Long id) {
        log.debug("REST request to get DocumentacaoPessoa : {}", id);
        Optional<DocumentacaoPessoaDTO> documentacaoPessoaDTO = documentacaoPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentacaoPessoaDTO);
    }

    /**
     * {@code DELETE  /documentacao-pessoas/:id} : delete the "id" documentacaoPessoa.
     *
     * @param id the id of the documentacaoPessoaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentacao-pessoas/{id}")
    public ResponseEntity<Void> deleteDocumentacaoPessoa(@PathVariable Long id) {
        log.debug("REST request to delete DocumentacaoPessoa : {}", id);
        documentacaoPessoaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/documentacao-pessoas?query=:query} : search for the documentacaoPessoa corresponding
     * to the query.
     *
     * @param query the query of the documentacaoPessoa search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/documentacao-pessoas")
    public ResponseEntity<List<DocumentacaoPessoaDTO>> searchDocumentacaoPessoas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentacaoPessoas for query {}", query);
        Page<DocumentacaoPessoaDTO> page = documentacaoPessoaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
