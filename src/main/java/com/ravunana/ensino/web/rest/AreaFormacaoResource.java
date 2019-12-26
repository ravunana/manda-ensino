package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.AreaFormacaoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.AreaFormacaoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.AreaFormacao}.
 */
@RestController
@RequestMapping("/api")
public class AreaFormacaoResource {

    private final Logger log = LoggerFactory.getLogger(AreaFormacaoResource.class);

    private static final String ENTITY_NAME = "areaFormacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaFormacaoService areaFormacaoService;

    public AreaFormacaoResource(AreaFormacaoService areaFormacaoService) {
        this.areaFormacaoService = areaFormacaoService;
    }

    /**
     * {@code POST  /area-formacaos} : Create a new areaFormacao.
     *
     * @param areaFormacaoDTO the areaFormacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaFormacaoDTO, or with status {@code 400 (Bad Request)} if the areaFormacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/area-formacaos")
    public ResponseEntity<AreaFormacaoDTO> createAreaFormacao(@Valid @RequestBody AreaFormacaoDTO areaFormacaoDTO) throws URISyntaxException {
        log.debug("REST request to save AreaFormacao : {}", areaFormacaoDTO);
        if (areaFormacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new areaFormacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaFormacaoDTO result = areaFormacaoService.save(areaFormacaoDTO);
        return ResponseEntity.created(new URI("/api/area-formacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /area-formacaos} : Updates an existing areaFormacao.
     *
     * @param areaFormacaoDTO the areaFormacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaFormacaoDTO,
     * or with status {@code 400 (Bad Request)} if the areaFormacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaFormacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/area-formacaos")
    public ResponseEntity<AreaFormacaoDTO> updateAreaFormacao(@Valid @RequestBody AreaFormacaoDTO areaFormacaoDTO) throws URISyntaxException {
        log.debug("REST request to update AreaFormacao : {}", areaFormacaoDTO);
        if (areaFormacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AreaFormacaoDTO result = areaFormacaoService.save(areaFormacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaFormacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /area-formacaos} : get all the areaFormacaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaFormacaos in body.
     */
    @GetMapping("/area-formacaos")
    public ResponseEntity<List<AreaFormacaoDTO>> getAllAreaFormacaos(Pageable pageable) {
        log.debug("REST request to get a page of AreaFormacaos");
        Page<AreaFormacaoDTO> page = areaFormacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /area-formacaos/:id} : get the "id" areaFormacao.
     *
     * @param id the id of the areaFormacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaFormacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/area-formacaos/{id}")
    public ResponseEntity<AreaFormacaoDTO> getAreaFormacao(@PathVariable Long id) {
        log.debug("REST request to get AreaFormacao : {}", id);
        Optional<AreaFormacaoDTO> areaFormacaoDTO = areaFormacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaFormacaoDTO);
    }

    /**
     * {@code DELETE  /area-formacaos/:id} : delete the "id" areaFormacao.
     *
     * @param id the id of the areaFormacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/area-formacaos/{id}")
    public ResponseEntity<Void> deleteAreaFormacao(@PathVariable Long id) {
        log.debug("REST request to delete AreaFormacao : {}", id);
        areaFormacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/area-formacaos?query=:query} : search for the areaFormacao corresponding
     * to the query.
     *
     * @param query the query of the areaFormacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/area-formacaos")
    public ResponseEntity<List<AreaFormacaoDTO>> searchAreaFormacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AreaFormacaos for query {}", query);
        Page<AreaFormacaoDTO> page = areaFormacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
