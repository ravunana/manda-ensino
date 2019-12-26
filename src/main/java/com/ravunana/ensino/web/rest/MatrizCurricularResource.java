package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.MatrizCurricularService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.MatrizCurricularDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.MatrizCurricular}.
 */
@RestController
@RequestMapping("/api")
public class MatrizCurricularResource {

    private final Logger log = LoggerFactory.getLogger(MatrizCurricularResource.class);

    private static final String ENTITY_NAME = "matrizCurricular";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatrizCurricularService matrizCurricularService;

    public MatrizCurricularResource(MatrizCurricularService matrizCurricularService) {
        this.matrizCurricularService = matrizCurricularService;
    }

    /**
     * {@code POST  /matriz-curriculars} : Create a new matrizCurricular.
     *
     * @param matrizCurricularDTO the matrizCurricularDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matrizCurricularDTO, or with status {@code 400 (Bad Request)} if the matrizCurricular has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matriz-curriculars")
    public ResponseEntity<MatrizCurricularDTO> createMatrizCurricular(@Valid @RequestBody MatrizCurricularDTO matrizCurricularDTO) throws URISyntaxException {
        log.debug("REST request to save MatrizCurricular : {}", matrizCurricularDTO);
        if (matrizCurricularDTO.getId() != null) {
            throw new BadRequestAlertException("A new matrizCurricular cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatrizCurricularDTO result = matrizCurricularService.save(matrizCurricularDTO);
        return ResponseEntity.created(new URI("/api/matriz-curriculars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matriz-curriculars} : Updates an existing matrizCurricular.
     *
     * @param matrizCurricularDTO the matrizCurricularDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matrizCurricularDTO,
     * or with status {@code 400 (Bad Request)} if the matrizCurricularDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matrizCurricularDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matriz-curriculars")
    public ResponseEntity<MatrizCurricularDTO> updateMatrizCurricular(@Valid @RequestBody MatrizCurricularDTO matrizCurricularDTO) throws URISyntaxException {
        log.debug("REST request to update MatrizCurricular : {}", matrizCurricularDTO);
        if (matrizCurricularDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatrizCurricularDTO result = matrizCurricularService.save(matrizCurricularDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matrizCurricularDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /matriz-curriculars} : get all the matrizCurriculars.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matrizCurriculars in body.
     */
    @GetMapping("/matriz-curriculars")
    public ResponseEntity<List<MatrizCurricularDTO>> getAllMatrizCurriculars(Pageable pageable) {
        log.debug("REST request to get a page of MatrizCurriculars");
        Page<MatrizCurricularDTO> page = matrizCurricularService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matriz-curriculars/:id} : get the "id" matrizCurricular.
     *
     * @param id the id of the matrizCurricularDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matrizCurricularDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matriz-curriculars/{id}")
    public ResponseEntity<MatrizCurricularDTO> getMatrizCurricular(@PathVariable Long id) {
        log.debug("REST request to get MatrizCurricular : {}", id);
        Optional<MatrizCurricularDTO> matrizCurricularDTO = matrizCurricularService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matrizCurricularDTO);
    }

    /**
     * {@code DELETE  /matriz-curriculars/:id} : delete the "id" matrizCurricular.
     *
     * @param id the id of the matrizCurricularDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matriz-curriculars/{id}")
    public ResponseEntity<Void> deleteMatrizCurricular(@PathVariable Long id) {
        log.debug("REST request to delete MatrizCurricular : {}", id);
        matrizCurricularService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/matriz-curriculars?query=:query} : search for the matrizCurricular corresponding
     * to the query.
     *
     * @param query the query of the matrizCurricular search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/matriz-curriculars")
    public ResponseEntity<List<MatrizCurricularDTO>> searchMatrizCurriculars(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MatrizCurriculars for query {}", query);
        Page<MatrizCurricularDTO> page = matrizCurricularService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
