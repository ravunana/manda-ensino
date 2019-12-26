package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Pagamento;
import com.ravunana.ensino.repository.PagamentoRepository;
import com.ravunana.ensino.repository.search.PagamentoSearchRepository;
import com.ravunana.ensino.service.dto.PagamentoDTO;
import com.ravunana.ensino.service.mapper.PagamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Pagamento}.
 */
@Service
@Transactional
public class PagamentoService {

    private final Logger log = LoggerFactory.getLogger(PagamentoService.class);

    private final PagamentoRepository pagamentoRepository;

    private final PagamentoMapper pagamentoMapper;

    private final PagamentoSearchRepository pagamentoSearchRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, PagamentoMapper pagamentoMapper, PagamentoSearchRepository pagamentoSearchRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoMapper = pagamentoMapper;
        this.pagamentoSearchRepository = pagamentoSearchRepository;
    }

    /**
     * Save a pagamento.
     *
     * @param pagamentoDTO the entity to save.
     * @return the persisted entity.
     */
    public PagamentoDTO save(PagamentoDTO pagamentoDTO) {
        log.debug("Request to save Pagamento : {}", pagamentoDTO);
        Pagamento pagamento = pagamentoMapper.toEntity(pagamentoDTO);
        pagamento = pagamentoRepository.save(pagamento);
        PagamentoDTO result = pagamentoMapper.toDto(pagamento);
        pagamentoSearchRepository.save(pagamento);
        return result;
    }

    /**
     * Get all the pagamentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PagamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pagamentos");
        return pagamentoRepository.findAll(pageable)
            .map(pagamentoMapper::toDto);
    }


    /**
     * Get one pagamento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PagamentoDTO> findOne(Long id) {
        log.debug("Request to get Pagamento : {}", id);
        return pagamentoRepository.findById(id)
            .map(pagamentoMapper::toDto);
    }

    /**
     * Delete the pagamento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pagamento : {}", id);
        pagamentoRepository.deleteById(id);
        pagamentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the pagamento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PagamentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pagamentos for query {}", query);
        return pagamentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(pagamentoMapper::toDto);
    }
}
