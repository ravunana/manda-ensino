package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.CategoriaValiacaoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.CategoriaValiacaoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.CategoriaValiacao}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaValiacaoResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaValiacaoResource.class);

    private static final String ENTITY_NAME = "categoriaValiacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaValiacaoService categoriaValiacaoService;

    public CategoriaValiacaoResource(CategoriaValiacaoService categoriaValiacaoService) {
        this.categoriaValiacaoService = categoriaValiacaoService;
    }

    /**
     * {@code POST  /categoria-valiacaos} : Create a new categoriaValiacao.
     *
     * @param categoriaValiacaoDTO the categoriaValiacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaValiacaoDTO, or with status {@code 400 (Bad Request)} if the categoriaValiacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-valiacaos")
    public ResponseEntity<CategoriaValiacaoDTO> createCategoriaValiacao(@Valid @RequestBody CategoriaValiacaoDTO categoriaValiacaoDTO) throws URISyntaxException {
        log.debug("REST request to save CategoriaValiacao : {}", categoriaValiacaoDTO);
        if (categoriaValiacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoriaValiacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaValiacaoDTO result = categoriaValiacaoService.save(categoriaValiacaoDTO);
        return ResponseEntity.created(new URI("/api/categoria-valiacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-valiacaos} : Updates an existing categoriaValiacao.
     *
     * @param categoriaValiacaoDTO the categoriaValiacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaValiacaoDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaValiacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaValiacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-valiacaos")
    public ResponseEntity<CategoriaValiacaoDTO> updateCategoriaValiacao(@Valid @RequestBody CategoriaValiacaoDTO categoriaValiacaoDTO) throws URISyntaxException {
        log.debug("REST request to update CategoriaValiacao : {}", categoriaValiacaoDTO);
        if (categoriaValiacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaValiacaoDTO result = categoriaValiacaoService.save(categoriaValiacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaValiacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categoria-valiacaos} : get all the categoriaValiacaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaValiacaos in body.
     */
    @GetMapping("/categoria-valiacaos")
    public ResponseEntity<List<CategoriaValiacaoDTO>> getAllCategoriaValiacaos(Pageable pageable) {
        log.debug("REST request to get a page of CategoriaValiacaos");
        Page<CategoriaValiacaoDTO> page = categoriaValiacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoria-valiacaos/:id} : get the "id" categoriaValiacao.
     *
     * @param id the id of the categoriaValiacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaValiacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-valiacaos/{id}")
    public ResponseEntity<CategoriaValiacaoDTO> getCategoriaValiacao(@PathVariable Long id) {
        log.debug("REST request to get CategoriaValiacao : {}", id);
        Optional<CategoriaValiacaoDTO> categoriaValiacaoDTO = categoriaValiacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaValiacaoDTO);
    }

    /**
     * {@code DELETE  /categoria-valiacaos/:id} : delete the "id" categoriaValiacao.
     *
     * @param id the id of the categoriaValiacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-valiacaos/{id}")
    public ResponseEntity<Void> deleteCategoriaValiacao(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaValiacao : {}", id);
        categoriaValiacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/categoria-valiacaos?query=:query} : search for the categoriaValiacao corresponding
     * to the query.
     *
     * @param query the query of the categoriaValiacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/categoria-valiacaos")
    public ResponseEntity<List<CategoriaValiacaoDTO>> searchCategoriaValiacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CategoriaValiacaos for query {}", query);
        Page<CategoriaValiacaoDTO> page = categoriaValiacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
