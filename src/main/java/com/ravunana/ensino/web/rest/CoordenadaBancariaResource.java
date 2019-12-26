package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.CoordenadaBancariaService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.CoordenadaBancariaDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.CoordenadaBancaria}.
 */
@RestController
@RequestMapping("/api")
public class CoordenadaBancariaResource {

    private final Logger log = LoggerFactory.getLogger(CoordenadaBancariaResource.class);

    private static final String ENTITY_NAME = "coordenadaBancaria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoordenadaBancariaService coordenadaBancariaService;

    public CoordenadaBancariaResource(CoordenadaBancariaService coordenadaBancariaService) {
        this.coordenadaBancariaService = coordenadaBancariaService;
    }

    /**
     * {@code POST  /coordenada-bancarias} : Create a new coordenadaBancaria.
     *
     * @param coordenadaBancariaDTO the coordenadaBancariaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coordenadaBancariaDTO, or with status {@code 400 (Bad Request)} if the coordenadaBancaria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coordenada-bancarias")
    public ResponseEntity<CoordenadaBancariaDTO> createCoordenadaBancaria(@Valid @RequestBody CoordenadaBancariaDTO coordenadaBancariaDTO) throws URISyntaxException {
        log.debug("REST request to save CoordenadaBancaria : {}", coordenadaBancariaDTO);
        if (coordenadaBancariaDTO.getId() != null) {
            throw new BadRequestAlertException("A new coordenadaBancaria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoordenadaBancariaDTO result = coordenadaBancariaService.save(coordenadaBancariaDTO);
        return ResponseEntity.created(new URI("/api/coordenada-bancarias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coordenada-bancarias} : Updates an existing coordenadaBancaria.
     *
     * @param coordenadaBancariaDTO the coordenadaBancariaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coordenadaBancariaDTO,
     * or with status {@code 400 (Bad Request)} if the coordenadaBancariaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coordenadaBancariaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coordenada-bancarias")
    public ResponseEntity<CoordenadaBancariaDTO> updateCoordenadaBancaria(@Valid @RequestBody CoordenadaBancariaDTO coordenadaBancariaDTO) throws URISyntaxException {
        log.debug("REST request to update CoordenadaBancaria : {}", coordenadaBancariaDTO);
        if (coordenadaBancariaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoordenadaBancariaDTO result = coordenadaBancariaService.save(coordenadaBancariaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coordenadaBancariaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coordenada-bancarias} : get all the coordenadaBancarias.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coordenadaBancarias in body.
     */
    @GetMapping("/coordenada-bancarias")
    public ResponseEntity<List<CoordenadaBancariaDTO>> getAllCoordenadaBancarias(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of CoordenadaBancarias");
        Page<CoordenadaBancariaDTO> page;
        if (eagerload) {
            page = coordenadaBancariaService.findAllWithEagerRelationships(pageable);
        } else {
            page = coordenadaBancariaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /coordenada-bancarias/:id} : get the "id" coordenadaBancaria.
     *
     * @param id the id of the coordenadaBancariaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coordenadaBancariaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coordenada-bancarias/{id}")
    public ResponseEntity<CoordenadaBancariaDTO> getCoordenadaBancaria(@PathVariable Long id) {
        log.debug("REST request to get CoordenadaBancaria : {}", id);
        Optional<CoordenadaBancariaDTO> coordenadaBancariaDTO = coordenadaBancariaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coordenadaBancariaDTO);
    }

    /**
     * {@code DELETE  /coordenada-bancarias/:id} : delete the "id" coordenadaBancaria.
     *
     * @param id the id of the coordenadaBancariaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coordenada-bancarias/{id}")
    public ResponseEntity<Void> deleteCoordenadaBancaria(@PathVariable Long id) {
        log.debug("REST request to delete CoordenadaBancaria : {}", id);
        coordenadaBancariaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/coordenada-bancarias?query=:query} : search for the coordenadaBancaria corresponding
     * to the query.
     *
     * @param query the query of the coordenadaBancaria search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/coordenada-bancarias")
    public ResponseEntity<List<CoordenadaBancariaDTO>> searchCoordenadaBancarias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CoordenadaBancarias for query {}", query);
        Page<CoordenadaBancariaDTO> page = coordenadaBancariaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
