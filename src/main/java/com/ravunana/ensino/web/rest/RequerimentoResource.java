package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.RequerimentoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.RequerimentoDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.ravunana.ensino.domain.Requerimento}.
 */
@RestController
@RequestMapping("/api")
public class RequerimentoResource {

    private final Logger log = LoggerFactory.getLogger(RequerimentoResource.class);

    private static final String ENTITY_NAME = "requerimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequerimentoService requerimentoService;

    public RequerimentoResource(RequerimentoService requerimentoService) {
        this.requerimentoService = requerimentoService;
    }

    /**
     * {@code POST  /requerimentos} : Create a new requerimento.
     *
     * @param requerimentoDTO the requerimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requerimentoDTO, or with status {@code 400 (Bad Request)} if the requerimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requerimentos")
    public ResponseEntity<RequerimentoDTO> createRequerimento(@RequestBody RequerimentoDTO requerimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Requerimento : {}", requerimentoDTO);
        if (requerimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new requerimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequerimentoDTO result = requerimentoService.save(requerimentoDTO);
        return ResponseEntity.created(new URI("/api/requerimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requerimentos} : Updates an existing requerimento.
     *
     * @param requerimentoDTO the requerimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requerimentoDTO,
     * or with status {@code 400 (Bad Request)} if the requerimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requerimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requerimentos")
    public ResponseEntity<RequerimentoDTO> updateRequerimento(@RequestBody RequerimentoDTO requerimentoDTO) throws URISyntaxException {
        log.debug("REST request to update Requerimento : {}", requerimentoDTO);
        if (requerimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequerimentoDTO result = requerimentoService.save(requerimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requerimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /requerimentos} : get all the requerimentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requerimentos in body.
     */
    @GetMapping("/requerimentos")
    public ResponseEntity<List<RequerimentoDTO>> getAllRequerimentos(Pageable pageable) {
        log.debug("REST request to get a page of Requerimentos");
        Page<RequerimentoDTO> page = requerimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requerimentos/:id} : get the "id" requerimento.
     *
     * @param id the id of the requerimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requerimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requerimentos/{id}")
    public ResponseEntity<RequerimentoDTO> getRequerimento(@PathVariable Long id) {
        log.debug("REST request to get Requerimento : {}", id);
        Optional<RequerimentoDTO> requerimentoDTO = requerimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requerimentoDTO);
    }

    /**
     * {@code DELETE  /requerimentos/:id} : delete the "id" requerimento.
     *
     * @param id the id of the requerimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requerimentos/{id}")
    public ResponseEntity<Void> deleteRequerimento(@PathVariable Long id) {
        log.debug("REST request to delete Requerimento : {}", id);
        requerimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/requerimentos?query=:query} : search for the requerimento corresponding
     * to the query.
     *
     * @param query the query of the requerimento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/requerimentos")
    public ResponseEntity<List<RequerimentoDTO>> searchRequerimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Requerimentos for query {}", query);
        Page<RequerimentoDTO> page = requerimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
