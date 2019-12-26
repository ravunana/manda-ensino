package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.DetalhePagamentoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.DetalhePagamentoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.DetalhePagamento}.
 */
@RestController
@RequestMapping("/api")
public class DetalhePagamentoResource {

    private final Logger log = LoggerFactory.getLogger(DetalhePagamentoResource.class);

    private static final String ENTITY_NAME = "detalhePagamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetalhePagamentoService detalhePagamentoService;

    public DetalhePagamentoResource(DetalhePagamentoService detalhePagamentoService) {
        this.detalhePagamentoService = detalhePagamentoService;
    }

    /**
     * {@code POST  /detalhe-pagamentos} : Create a new detalhePagamento.
     *
     * @param detalhePagamentoDTO the detalhePagamentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalhePagamentoDTO, or with status {@code 400 (Bad Request)} if the detalhePagamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalhe-pagamentos")
    public ResponseEntity<DetalhePagamentoDTO> createDetalhePagamento(@Valid @RequestBody DetalhePagamentoDTO detalhePagamentoDTO) throws URISyntaxException {
        log.debug("REST request to save DetalhePagamento : {}", detalhePagamentoDTO);
        if (detalhePagamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalhePagamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalhePagamentoDTO result = detalhePagamentoService.save(detalhePagamentoDTO);
        return ResponseEntity.created(new URI("/api/detalhe-pagamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalhe-pagamentos} : Updates an existing detalhePagamento.
     *
     * @param detalhePagamentoDTO the detalhePagamentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalhePagamentoDTO,
     * or with status {@code 400 (Bad Request)} if the detalhePagamentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalhePagamentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalhe-pagamentos")
    public ResponseEntity<DetalhePagamentoDTO> updateDetalhePagamento(@Valid @RequestBody DetalhePagamentoDTO detalhePagamentoDTO) throws URISyntaxException {
        log.debug("REST request to update DetalhePagamento : {}", detalhePagamentoDTO);
        if (detalhePagamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetalhePagamentoDTO result = detalhePagamentoService.save(detalhePagamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalhePagamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /detalhe-pagamentos} : get all the detalhePagamentos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalhePagamentos in body.
     */
    @GetMapping("/detalhe-pagamentos")
    public ResponseEntity<List<DetalhePagamentoDTO>> getAllDetalhePagamentos(Pageable pageable) {
        log.debug("REST request to get a page of DetalhePagamentos");
        Page<DetalhePagamentoDTO> page = detalhePagamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detalhe-pagamentos/:id} : get the "id" detalhePagamento.
     *
     * @param id the id of the detalhePagamentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalhePagamentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalhe-pagamentos/{id}")
    public ResponseEntity<DetalhePagamentoDTO> getDetalhePagamento(@PathVariable Long id) {
        log.debug("REST request to get DetalhePagamento : {}", id);
        Optional<DetalhePagamentoDTO> detalhePagamentoDTO = detalhePagamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalhePagamentoDTO);
    }

    /**
     * {@code DELETE  /detalhe-pagamentos/:id} : delete the "id" detalhePagamento.
     *
     * @param id the id of the detalhePagamentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalhe-pagamentos/{id}")
    public ResponseEntity<Void> deleteDetalhePagamento(@PathVariable Long id) {
        log.debug("REST request to delete DetalhePagamento : {}", id);
        detalhePagamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/detalhe-pagamentos?query=:query} : search for the detalhePagamento corresponding
     * to the query.
     *
     * @param query the query of the detalhePagamento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/detalhe-pagamentos")
    public ResponseEntity<List<DetalhePagamentoDTO>> searchDetalhePagamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DetalhePagamentos for query {}", query);
        Page<DetalhePagamentoDTO> page = detalhePagamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
