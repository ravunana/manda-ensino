package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.DocumentoMatriculaService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.DocumentoMatriculaDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.DocumentoMatricula}.
 */
@RestController
@RequestMapping("/api")
public class DocumentoMatriculaResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoMatriculaResource.class);

    private static final String ENTITY_NAME = "documentoMatricula";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentoMatriculaService documentoMatriculaService;

    public DocumentoMatriculaResource(DocumentoMatriculaService documentoMatriculaService) {
        this.documentoMatriculaService = documentoMatriculaService;
    }

    /**
     * {@code POST  /documento-matriculas} : Create a new documentoMatricula.
     *
     * @param documentoMatriculaDTO the documentoMatriculaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentoMatriculaDTO, or with status {@code 400 (Bad Request)} if the documentoMatricula has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documento-matriculas")
    public ResponseEntity<DocumentoMatriculaDTO> createDocumentoMatricula(@Valid @RequestBody DocumentoMatriculaDTO documentoMatriculaDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentoMatricula : {}", documentoMatriculaDTO);
        if (documentoMatriculaDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentoMatricula cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentoMatriculaDTO result = documentoMatriculaService.save(documentoMatriculaDTO);
        return ResponseEntity.created(new URI("/api/documento-matriculas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documento-matriculas} : Updates an existing documentoMatricula.
     *
     * @param documentoMatriculaDTO the documentoMatriculaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentoMatriculaDTO,
     * or with status {@code 400 (Bad Request)} if the documentoMatriculaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentoMatriculaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documento-matriculas")
    public ResponseEntity<DocumentoMatriculaDTO> updateDocumentoMatricula(@Valid @RequestBody DocumentoMatriculaDTO documentoMatriculaDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentoMatricula : {}", documentoMatriculaDTO);
        if (documentoMatriculaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentoMatriculaDTO result = documentoMatriculaService.save(documentoMatriculaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentoMatriculaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documento-matriculas} : get all the documentoMatriculas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentoMatriculas in body.
     */
    @GetMapping("/documento-matriculas")
    public ResponseEntity<List<DocumentoMatriculaDTO>> getAllDocumentoMatriculas(Pageable pageable) {
        log.debug("REST request to get a page of DocumentoMatriculas");
        Page<DocumentoMatriculaDTO> page = documentoMatriculaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documento-matriculas/:id} : get the "id" documentoMatricula.
     *
     * @param id the id of the documentoMatriculaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentoMatriculaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documento-matriculas/{id}")
    public ResponseEntity<DocumentoMatriculaDTO> getDocumentoMatricula(@PathVariable Long id) {
        log.debug("REST request to get DocumentoMatricula : {}", id);
        Optional<DocumentoMatriculaDTO> documentoMatriculaDTO = documentoMatriculaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentoMatriculaDTO);
    }

    /**
     * {@code DELETE  /documento-matriculas/:id} : delete the "id" documentoMatricula.
     *
     * @param id the id of the documentoMatriculaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documento-matriculas/{id}")
    public ResponseEntity<Void> deleteDocumentoMatricula(@PathVariable Long id) {
        log.debug("REST request to delete DocumentoMatricula : {}", id);
        documentoMatriculaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/documento-matriculas?query=:query} : search for the documentoMatricula corresponding
     * to the query.
     *
     * @param query the query of the documentoMatricula search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/documento-matriculas")
    public ResponseEntity<List<DocumentoMatriculaDTO>> searchDocumentoMatriculas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentoMatriculas for query {}", query);
        Page<DocumentoMatriculaDTO> page = documentoMatriculaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
