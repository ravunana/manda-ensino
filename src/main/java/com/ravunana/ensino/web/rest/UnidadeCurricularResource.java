package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.UnidadeCurricularService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.UnidadeCurricularDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.UnidadeCurricular}.
 */
@RestController
@RequestMapping("/api")
public class UnidadeCurricularResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeCurricularResource.class);

    private static final String ENTITY_NAME = "unidadeCurricular";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeCurricularService unidadeCurricularService;

    public UnidadeCurricularResource(UnidadeCurricularService unidadeCurricularService) {
        this.unidadeCurricularService = unidadeCurricularService;
    }

    /**
     * {@code POST  /unidade-curriculars} : Create a new unidadeCurricular.
     *
     * @param unidadeCurricularDTO the unidadeCurricularDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadeCurricularDTO, or with status {@code 400 (Bad Request)} if the unidadeCurricular has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidade-curriculars")
    public ResponseEntity<UnidadeCurricularDTO> createUnidadeCurricular(@Valid @RequestBody UnidadeCurricularDTO unidadeCurricularDTO) throws URISyntaxException {
        log.debug("REST request to save UnidadeCurricular : {}", unidadeCurricularDTO);
        if (unidadeCurricularDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadeCurricular cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeCurricularDTO result = unidadeCurricularService.save(unidadeCurricularDTO);
        return ResponseEntity.created(new URI("/api/unidade-curriculars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidade-curriculars} : Updates an existing unidadeCurricular.
     *
     * @param unidadeCurricularDTO the unidadeCurricularDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeCurricularDTO,
     * or with status {@code 400 (Bad Request)} if the unidadeCurricularDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadeCurricularDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidade-curriculars")
    public ResponseEntity<UnidadeCurricularDTO> updateUnidadeCurricular(@Valid @RequestBody UnidadeCurricularDTO unidadeCurricularDTO) throws URISyntaxException {
        log.debug("REST request to update UnidadeCurricular : {}", unidadeCurricularDTO);
        if (unidadeCurricularDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadeCurricularDTO result = unidadeCurricularService.save(unidadeCurricularDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadeCurricularDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidade-curriculars} : get all the unidadeCurriculars.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadeCurriculars in body.
     */
    @GetMapping("/unidade-curriculars")
    public ResponseEntity<List<UnidadeCurricularDTO>> getAllUnidadeCurriculars(Pageable pageable) {
        log.debug("REST request to get a page of UnidadeCurriculars");
        Page<UnidadeCurricularDTO> page = unidadeCurricularService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unidade-curriculars/:id} : get the "id" unidadeCurricular.
     *
     * @param id the id of the unidadeCurricularDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadeCurricularDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidade-curriculars/{id}")
    public ResponseEntity<UnidadeCurricularDTO> getUnidadeCurricular(@PathVariable Long id) {
        log.debug("REST request to get UnidadeCurricular : {}", id);
        Optional<UnidadeCurricularDTO> unidadeCurricularDTO = unidadeCurricularService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadeCurricularDTO);
    }

    /**
     * {@code DELETE  /unidade-curriculars/:id} : delete the "id" unidadeCurricular.
     *
     * @param id the id of the unidadeCurricularDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidade-curriculars/{id}")
    public ResponseEntity<Void> deleteUnidadeCurricular(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeCurricular : {}", id);
        unidadeCurricularService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unidade-curriculars?query=:query} : search for the unidadeCurricular corresponding
     * to the query.
     *
     * @param query the query of the unidadeCurricular search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/unidade-curriculars")
    public ResponseEntity<List<UnidadeCurricularDTO>> searchUnidadeCurriculars(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnidadeCurriculars for query {}", query);
        Page<UnidadeCurricularDTO> page = unidadeCurricularService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
