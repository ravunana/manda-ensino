package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.FichaMedicaService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.FichaMedicaDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.FichaMedica}.
 */
@RestController
@RequestMapping("/api")
public class FichaMedicaResource {

    private final Logger log = LoggerFactory.getLogger(FichaMedicaResource.class);

    private static final String ENTITY_NAME = "fichaMedica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FichaMedicaService fichaMedicaService;

    public FichaMedicaResource(FichaMedicaService fichaMedicaService) {
        this.fichaMedicaService = fichaMedicaService;
    }

    /**
     * {@code POST  /ficha-medicas} : Create a new fichaMedica.
     *
     * @param fichaMedicaDTO the fichaMedicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fichaMedicaDTO, or with status {@code 400 (Bad Request)} if the fichaMedica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ficha-medicas")
    public ResponseEntity<FichaMedicaDTO> createFichaMedica(@Valid @RequestBody FichaMedicaDTO fichaMedicaDTO) throws URISyntaxException {
        log.debug("REST request to save FichaMedica : {}", fichaMedicaDTO);
        if (fichaMedicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new fichaMedica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichaMedicaDTO result = fichaMedicaService.save(fichaMedicaDTO);
        return ResponseEntity.created(new URI("/api/ficha-medicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ficha-medicas} : Updates an existing fichaMedica.
     *
     * @param fichaMedicaDTO the fichaMedicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fichaMedicaDTO,
     * or with status {@code 400 (Bad Request)} if the fichaMedicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fichaMedicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ficha-medicas")
    public ResponseEntity<FichaMedicaDTO> updateFichaMedica(@Valid @RequestBody FichaMedicaDTO fichaMedicaDTO) throws URISyntaxException {
        log.debug("REST request to update FichaMedica : {}", fichaMedicaDTO);
        if (fichaMedicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichaMedicaDTO result = fichaMedicaService.save(fichaMedicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fichaMedicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ficha-medicas} : get all the fichaMedicas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fichaMedicas in body.
     */
    @GetMapping("/ficha-medicas")
    public ResponseEntity<List<FichaMedicaDTO>> getAllFichaMedicas(Pageable pageable) {
        log.debug("REST request to get a page of FichaMedicas");
        Page<FichaMedicaDTO> page = fichaMedicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ficha-medicas/:id} : get the "id" fichaMedica.
     *
     * @param id the id of the fichaMedicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fichaMedicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ficha-medicas/{id}")
    public ResponseEntity<FichaMedicaDTO> getFichaMedica(@PathVariable Long id) {
        log.debug("REST request to get FichaMedica : {}", id);
        Optional<FichaMedicaDTO> fichaMedicaDTO = fichaMedicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichaMedicaDTO);
    }

    /**
     * {@code DELETE  /ficha-medicas/:id} : delete the "id" fichaMedica.
     *
     * @param id the id of the fichaMedicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ficha-medicas/{id}")
    public ResponseEntity<Void> deleteFichaMedica(@PathVariable Long id) {
        log.debug("REST request to delete FichaMedica : {}", id);
        fichaMedicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ficha-medicas?query=:query} : search for the fichaMedica corresponding
     * to the query.
     *
     * @param query the query of the fichaMedica search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ficha-medicas")
    public ResponseEntity<List<FichaMedicaDTO>> searchFichaMedicas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FichaMedicas for query {}", query);
        Page<FichaMedicaDTO> page = fichaMedicaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
