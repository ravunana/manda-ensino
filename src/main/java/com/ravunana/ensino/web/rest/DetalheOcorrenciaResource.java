package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.DetalheOcorrenciaService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.DetalheOcorrenciaDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.DetalheOcorrencia}.
 */
@RestController
@RequestMapping("/api")
public class DetalheOcorrenciaResource {

    private final Logger log = LoggerFactory.getLogger(DetalheOcorrenciaResource.class);

    private static final String ENTITY_NAME = "detalheOcorrencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalheOcorrenciaService detalheOcorrenciaService;

    public DetalheOcorrenciaResource(DetalheOcorrenciaService detalheOcorrenciaService) {
        this.detalheOcorrenciaService = detalheOcorrenciaService;
    }

    /**
     * {@code POST  /detalhe-ocorrencias} : Create a new detalheOcorrencia.
     *
     * @param detalheOcorrenciaDTO the detalheOcorrenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalheOcorrenciaDTO, or with status {@code 400 (Bad Request)} if the detalheOcorrencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalhe-ocorrencias")
    public ResponseEntity<DetalheOcorrenciaDTO> createDetalheOcorrencia(@Valid @RequestBody DetalheOcorrenciaDTO detalheOcorrenciaDTO) throws URISyntaxException {
        log.debug("REST request to save DetalheOcorrencia : {}", detalheOcorrenciaDTO);
        if (detalheOcorrenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalheOcorrencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalheOcorrenciaDTO result = detalheOcorrenciaService.save(detalheOcorrenciaDTO);
        return ResponseEntity.created(new URI("/api/detalhe-ocorrencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalhe-ocorrencias} : Updates an existing detalheOcorrencia.
     *
     * @param detalheOcorrenciaDTO the detalheOcorrenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalheOcorrenciaDTO,
     * or with status {@code 400 (Bad Request)} if the detalheOcorrenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalheOcorrenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalhe-ocorrencias")
    public ResponseEntity<DetalheOcorrenciaDTO> updateDetalheOcorrencia(@Valid @RequestBody DetalheOcorrenciaDTO detalheOcorrenciaDTO) throws URISyntaxException {
        log.debug("REST request to update DetalheOcorrencia : {}", detalheOcorrenciaDTO);
        if (detalheOcorrenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetalheOcorrenciaDTO result = detalheOcorrenciaService.save(detalheOcorrenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalheOcorrenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detalhe-ocorrencias} : get all the detalheOcorrencias.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalheOcorrencias in body.
     */
    @GetMapping("/detalhe-ocorrencias")
    public ResponseEntity<List<DetalheOcorrenciaDTO>> getAllDetalheOcorrencias(Pageable pageable) {
        log.debug("REST request to get a page of DetalheOcorrencias");
        Page<DetalheOcorrenciaDTO> page = detalheOcorrenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detalhe-ocorrencias/:id} : get the "id" detalheOcorrencia.
     *
     * @param id the id of the detalheOcorrenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalheOcorrenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalhe-ocorrencias/{id}")
    public ResponseEntity<DetalheOcorrenciaDTO> getDetalheOcorrencia(@PathVariable Long id) {
        log.debug("REST request to get DetalheOcorrencia : {}", id);
        Optional<DetalheOcorrenciaDTO> detalheOcorrenciaDTO = detalheOcorrenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalheOcorrenciaDTO);
    }

    /**
     * {@code DELETE  /detalhe-ocorrencias/:id} : delete the "id" detalheOcorrencia.
     *
     * @param id the id of the detalheOcorrenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalhe-ocorrencias/{id}")
    public ResponseEntity<Void> deleteDetalheOcorrencia(@PathVariable Long id) {
        log.debug("REST request to delete DetalheOcorrencia : {}", id);
        detalheOcorrenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/detalhe-ocorrencias?query=:query} : search for the detalheOcorrencia corresponding
     * to the query.
     *
     * @param query the query of the detalheOcorrencia search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/detalhe-ocorrencias")
    public ResponseEntity<List<DetalheOcorrenciaDTO>> searchDetalheOcorrencias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DetalheOcorrencias for query {}", query);
        Page<DetalheOcorrenciaDTO> page = detalheOcorrenciaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
