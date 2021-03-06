package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.MeioLiquidacao;
import com.ravunana.ensino.repository.MeioLiquidacaoRepository;
import com.ravunana.ensino.repository.search.MeioLiquidacaoSearchRepository;
import com.ravunana.ensino.service.dto.MeioLiquidacaoDTO;
import com.ravunana.ensino.service.mapper.MeioLiquidacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MeioLiquidacao}.
 */
@Service
@Transactional
public class MeioLiquidacaoService {

    private final Logger log = LoggerFactory.getLogger(MeioLiquidacaoService.class);

    private final MeioLiquidacaoRepository meioLiquidacaoRepository;

    private final MeioLiquidacaoMapper meioLiquidacaoMapper;

    private final MeioLiquidacaoSearchRepository meioLiquidacaoSearchRepository;

    public MeioLiquidacaoService(MeioLiquidacaoRepository meioLiquidacaoRepository, MeioLiquidacaoMapper meioLiquidacaoMapper, MeioLiquidacaoSearchRepository meioLiquidacaoSearchRepository) {
        this.meioLiquidacaoRepository = meioLiquidacaoRepository;
        this.meioLiquidacaoMapper = meioLiquidacaoMapper;
        this.meioLiquidacaoSearchRepository = meioLiquidacaoSearchRepository;
    }

    /**
     * Save a meioLiquidacao.
     *
     * @param meioLiquidacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public MeioLiquidacaoDTO save(MeioLiquidacaoDTO meioLiquidacaoDTO) {
        log.debug("Request to save MeioLiquidacao : {}", meioLiquidacaoDTO);
        MeioLiquidacao meioLiquidacao = meioLiquidacaoMapper.toEntity(meioLiquidacaoDTO);
        meioLiquidacao = meioLiquidacaoRepository.save(meioLiquidacao);
        MeioLiquidacaoDTO result = meioLiquidacaoMapper.toDto(meioLiquidacao);
        meioLiquidacaoSearchRepository.save(meioLiquidacao);
        return result;
    }

    /**
     * Get all the meioLiquidacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MeioLiquidacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeioLiquidacaos");
        return meioLiquidacaoRepository.findAll(pageable)
            .map(meioLiquidacaoMapper::toDto);
    }


    /**
     * Get one meioLiquidacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeioLiquidacaoDTO> findOne(Long id) {
        log.debug("Request to get MeioLiquidacao : {}", id);
        return meioLiquidacaoRepository.findById(id)
            .map(meioLiquidacaoMapper::toDto);
    }

    /**
     * Delete the meioLiquidacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeioLiquidacao : {}", id);
        meioLiquidacaoRepository.deleteById(id);
        meioLiquidacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the meioLiquidacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MeioLiquidacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MeioLiquidacaos for query {}", query);
        return meioLiquidacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(meioLiquidacaoMapper::toDto);
    }
}
