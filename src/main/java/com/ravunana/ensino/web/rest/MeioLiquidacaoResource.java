package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.MeioLiquidacaoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.MeioLiquidacaoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.MeioLiquidacao}.
 */
@RestController
@RequestMapping("/api")
public class MeioLiquidacaoResource {

    private final Logger log = LoggerFactory.getLogger(MeioLiquidacaoResource.class);

    private static final String ENTITY_NAME = "meioLiquidacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeioLiquidacaoService meioLiquidacaoService;

    public MeioLiquidacaoResource(MeioLiquidacaoService meioLiquidacaoService) {
        this.meioLiquidacaoService = meioLiquidacaoService;
    }

    /**
     * {@code POST  /meio-liquidacaos} : Create a new meioLiquidacao.
     *
     * @param meioLiquidacaoDTO the meioLiquidacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meioLiquidacaoDTO, or with status {@code 400 (Bad Request)} if the meioLiquidacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meio-liquidacaos")
    public ResponseEntity<MeioLiquidacaoDTO> createMeioLiquidacao(@Valid @RequestBody MeioLiquidacaoDTO meioLiquidacaoDTO) throws URISyntaxException {
        log.debug("REST request to save MeioLiquidacao : {}", meioLiquidacaoDTO);
        if (meioLiquidacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new meioLiquidacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeioLiquidacaoDTO result = meioLiquidacaoService.save(meioLiquidacaoDTO);
        return ResponseEntity.created(new URI("/api/meio-liquidacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meio-liquidacaos} : Updates an existing meioLiquidacao.
     *
     * @param meioLiquidacaoDTO the meioLiquidacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meioLiquidacaoDTO,
     * or with status {@code 400 (Bad Request)} if the meioLiquidacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meioLiquidacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meio-liquidacaos")
    public ResponseEntity<MeioLiquidacaoDTO> updateMeioLiquidacao(@Valid @RequestBody MeioLiquidacaoDTO meioLiquidacaoDTO) throws URISyntaxException {
        log.debug("REST request to update MeioLiquidacao : {}", meioLiquidacaoDTO);
        if (meioLiquidacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeioLiquidacaoDTO result = meioLiquidacaoService.save(meioLiquidacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meioLiquidacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meio-liquidacaos} : get all the meioLiquidacaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meioLiquidacaos in body.
     */
    @GetMapping("/meio-liquidacaos")
    public ResponseEntity<List<MeioLiquidacaoDTO>> getAllMeioLiquidacaos(Pageable pageable) {
        log.debug("REST request to get a page of MeioLiquidacaos");
        Page<MeioLiquidacaoDTO> page = meioLiquidacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /meio-liquidacaos/:id} : get the "id" meioLiquidacao.
     *
     * @param id the id of the meioLiquidacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meioLiquidacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meio-liquidacaos/{id}")
    public ResponseEntity<MeioLiquidacaoDTO> getMeioLiquidacao(@PathVariable Long id) {
        log.debug("REST request to get MeioLiquidacao : {}", id);
        Optional<MeioLiquidacaoDTO> meioLiquidacaoDTO = meioLiquidacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meioLiquidacaoDTO);
    }

    /**
     * {@code DELETE  /meio-liquidacaos/:id} : delete the "id" meioLiquidacao.
     *
     * @param id the id of the meioLiquidacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meio-liquidacaos/{id}")
    public ResponseEntity<Void> deleteMeioLiquidacao(@PathVariable Long id) {
        log.debug("REST request to delete MeioLiquidacao : {}", id);
        meioLiquidacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/meio-liquidacaos?query=:query} : search for the meioLiquidacao corresponding
     * to the query.
     *
     * @param query the query of the meioLiquidacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/meio-liquidacaos")
    public ResponseEntity<List<MeioLiquidacaoDTO>> searchMeioLiquidacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MeioLiquidacaos for query {}", query);
        Page<MeioLiquidacaoDTO> page = meioLiquidacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
