package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.CategoriaRequerimentoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.CategoriaRequerimentoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.CategoriaRequerimento}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaRequerimentoResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaRequerimentoResource.class);

    private static final String ENTITY_NAME = "categoriaRequerimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaRequerimentoService categoriaRequerimentoService;

    public CategoriaRequerimentoResource(CategoriaRequerimentoService categoriaRequerimentoService) {
        this.categoriaRequerimentoService = categoriaRequerimentoService;
    }

    /**
     * {@code POST  /categoria-requerimentos} : Create a new categoriaRequerimento.
     *
     * @param categoriaRequerimentoDTO the categoriaRequerimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaRequerimentoDTO, or with status {@code 400 (Bad Request)} if the categoriaRequerimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-requerimentos")
    public ResponseEntity<CategoriaRequerimentoDTO> createCategoriaRequerimento(@RequestBody CategoriaRequerimentoDTO categoriaRequerimentoDTO) throws URISyntaxException {
        log.debug("REST request to save CategoriaRequerimento : {}", categoriaRequerimentoDTO);
        if (categoriaRequerimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoriaRequerimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaRequerimentoDTO result = categoriaRequerimentoService.save(categoriaRequerimentoDTO);
        return ResponseEntity.created(new URI("/api/categoria-requerimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-requerimentos} : Updates an existing categoriaRequerimento.
     *
     * @param categoriaRequerimentoDTO the categoriaRequerimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaRequerimentoDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaRequerimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaRequerimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-requerimentos")
    public ResponseEntity<CategoriaRequerimentoDTO> updateCategoriaRequerimento(@RequestBody CategoriaRequerimentoDTO categoriaRequerimentoDTO) throws URISyntaxException {
        log.debug("REST request to update CategoriaRequerimento : {}", categoriaRequerimentoDTO);
        if (categoriaRequerimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaRequerimentoDTO result = categoriaRequerimentoService.save(categoriaRequerimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaRequerimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categoria-requerimentos} : get all the categoriaRequerimentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaRequerimentos in body.
     */
    @GetMapping("/categoria-requerimentos")
    public ResponseEntity<List<CategoriaRequerimentoDTO>> getAllCategoriaRequerimentos(Pageable pageable) {
        log.debug("REST request to get a page of CategoriaRequerimentos");
        Page<CategoriaRequerimentoDTO> page = categoriaRequerimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoria-requerimentos/:id} : get the "id" categoriaRequerimento.
     *
     * @param id the id of the categoriaRequerimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaRequerimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-requerimentos/{id}")
    public ResponseEntity<CategoriaRequerimentoDTO> getCategoriaRequerimento(@PathVariable Long id) {
        log.debug("REST request to get CategoriaRequerimento : {}", id);
        Optional<CategoriaRequerimentoDTO> categoriaRequerimentoDTO = categoriaRequerimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaRequerimentoDTO);
    }

    /**
     * {@code DELETE  /categoria-requerimentos/:id} : delete the "id" categoriaRequerimento.
     *
     * @param id the id of the categoriaRequerimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-requerimentos/{id}")
    public ResponseEntity<Void> deleteCategoriaRequerimento(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaRequerimento : {}", id);
        categoriaRequerimentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/categoria-requerimentos?query=:query} : search for the categoriaRequerimento corresponding
     * to the query.
     *
     * @param query the query of the categoriaRequerimento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/categoria-requerimentos")
    public ResponseEntity<List<CategoriaRequerimentoDTO>> searchCategoriaRequerimentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CategoriaRequerimentos for query {}", query);
        Page<CategoriaRequerimentoDTO> page = categoriaRequerimentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
