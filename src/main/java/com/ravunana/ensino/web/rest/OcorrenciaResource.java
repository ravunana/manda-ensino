package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.OcorrenciaService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.OcorrenciaDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.Ocorrencia}.
 */
@RestController
@RequestMapping("/api")
public class OcorrenciaResource {

    private final Logger log = LoggerFactory.getLogger(OcorrenciaResource.class);

    private static final String ENTITY_NAME = "ocorrencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaResource(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    /**
     * {@code POST  /ocorrencias} : Create a new ocorrencia.
     *
     * @param ocorrenciaDTO the ocorrenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ocorrenciaDTO, or with status {@code 400 (Bad Request)} if the ocorrencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ocorrencias")
    public ResponseEntity<OcorrenciaDTO> createOcorrencia(@Valid @RequestBody OcorrenciaDTO ocorrenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Ocorrencia : {}", ocorrenciaDTO);
        if (ocorrenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new ocorrencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OcorrenciaDTO result = ocorrenciaService.save(ocorrenciaDTO);
        return ResponseEntity.created(new URI("/api/ocorrencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ocorrencias} : Updates an existing ocorrencia.
     *
     * @param ocorrenciaDTO the ocorrenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ocorrenciaDTO,
     * or with status {@code 400 (Bad Request)} if the ocorrenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ocorrenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ocorrencias")
    public ResponseEntity<OcorrenciaDTO> updateOcorrencia(@Valid @RequestBody OcorrenciaDTO ocorrenciaDTO) throws URISyntaxException {
        log.debug("REST request to update Ocorrencia : {}", ocorrenciaDTO);
        if (ocorrenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OcorrenciaDTO result = ocorrenciaService.save(ocorrenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ocorrenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ocorrencias} : get all the ocorrencias.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ocorrencias in body.
     */
    @GetMapping("/ocorrencias")
    public ResponseEntity<List<OcorrenciaDTO>> getAllOcorrencias(Pageable pageable) {
        log.debug("REST request to get a page of Ocorrencias");
        Page<OcorrenciaDTO> page = ocorrenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ocorrencias/:id} : get the "id" ocorrencia.
     *
     * @param id the id of the ocorrenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ocorrenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ocorrencias/{id}")
    public ResponseEntity<OcorrenciaDTO> getOcorrencia(@PathVariable Long id) {
        log.debug("REST request to get Ocorrencia : {}", id);
        Optional<OcorrenciaDTO> ocorrenciaDTO = ocorrenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ocorrenciaDTO);
    }

    /**
     * {@code DELETE  /ocorrencias/:id} : delete the "id" ocorrencia.
     *
     * @param id the id of the ocorrenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ocorrencias/{id}")
    public ResponseEntity<Void> deleteOcorrencia(@PathVariable Long id) {
        log.debug("REST request to delete Ocorrencia : {}", id);
        ocorrenciaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/ocorrencias?query=:query} : search for the ocorrencia corresponding
     * to the query.
     *
     * @param query the query of the ocorrencia search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/ocorrencias")
    public ResponseEntity<List<OcorrenciaDTO>> searchOcorrencias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Ocorrencias for query {}", query);
        Page<OcorrenciaDTO> page = ocorrenciaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
