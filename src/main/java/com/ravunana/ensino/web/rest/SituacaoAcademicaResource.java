package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.SituacaoAcademicaService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.SituacaoAcademicaDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.SituacaoAcademica}.
 */
@RestController
@RequestMapping("/api")
public class SituacaoAcademicaResource {

    private final Logger log = LoggerFactory.getLogger(SituacaoAcademicaResource.class);

    private static final String ENTITY_NAME = "situacaoAcademica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituacaoAcademicaService situacaoAcademicaService;

    public SituacaoAcademicaResource(SituacaoAcademicaService situacaoAcademicaService) {
        this.situacaoAcademicaService = situacaoAcademicaService;
    }

    /**
     * {@code POST  /situacao-academicas} : Create a new situacaoAcademica.
     *
     * @param situacaoAcademicaDTO the situacaoAcademicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situacaoAcademicaDTO, or with status {@code 400 (Bad Request)} if the situacaoAcademica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situacao-academicas")
    public ResponseEntity<SituacaoAcademicaDTO> createSituacaoAcademica(@Valid @RequestBody SituacaoAcademicaDTO situacaoAcademicaDTO) throws URISyntaxException {
        log.debug("REST request to save SituacaoAcademica : {}", situacaoAcademicaDTO);
        if (situacaoAcademicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new situacaoAcademica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SituacaoAcademicaDTO result = situacaoAcademicaService.save(situacaoAcademicaDTO);
        return ResponseEntity.created(new URI("/api/situacao-academicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situacao-academicas} : Updates an existing situacaoAcademica.
     *
     * @param situacaoAcademicaDTO the situacaoAcademicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situacaoAcademicaDTO,
     * or with status {@code 400 (Bad Request)} if the situacaoAcademicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situacaoAcademicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situacao-academicas")
    public ResponseEntity<SituacaoAcademicaDTO> updateSituacaoAcademica(@Valid @RequestBody SituacaoAcademicaDTO situacaoAcademicaDTO) throws URISyntaxException {
        log.debug("REST request to update SituacaoAcademica : {}", situacaoAcademicaDTO);
        if (situacaoAcademicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SituacaoAcademicaDTO result = situacaoAcademicaService.save(situacaoAcademicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situacaoAcademicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /situacao-academicas} : get all the situacaoAcademicas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situacaoAcademicas in body.
     */
    @GetMapping("/situacao-academicas")
    public ResponseEntity<List<SituacaoAcademicaDTO>> getAllSituacaoAcademicas(Pageable pageable) {
        log.debug("REST request to get a page of SituacaoAcademicas");
        Page<SituacaoAcademicaDTO> page = situacaoAcademicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /situacao-academicas/:id} : get the "id" situacaoAcademica.
     *
     * @param id the id of the situacaoAcademicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situacaoAcademicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situacao-academicas/{id}")
    public ResponseEntity<SituacaoAcademicaDTO> getSituacaoAcademica(@PathVariable Long id) {
        log.debug("REST request to get SituacaoAcademica : {}", id);
        Optional<SituacaoAcademicaDTO> situacaoAcademicaDTO = situacaoAcademicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(situacaoAcademicaDTO);
    }

    /**
     * {@code DELETE  /situacao-academicas/:id} : delete the "id" situacaoAcademica.
     *
     * @param id the id of the situacaoAcademicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situacao-academicas/{id}")
    public ResponseEntity<Void> deleteSituacaoAcademica(@PathVariable Long id) {
        log.debug("REST request to delete SituacaoAcademica : {}", id);
        situacaoAcademicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/situacao-academicas?query=:query} : search for the situacaoAcademica corresponding
     * to the query.
     *
     * @param query the query of the situacaoAcademica search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/situacao-academicas")
    public ResponseEntity<List<SituacaoAcademicaDTO>> searchSituacaoAcademicas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SituacaoAcademicas for query {}", query);
        Page<SituacaoAcademicaDTO> page = situacaoAcademicaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
