package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Matricula;
import com.ravunana.ensino.repository.MatriculaRepository;
import com.ravunana.ensino.repository.search.MatriculaSearchRepository;
import com.ravunana.ensino.service.dto.MatriculaDTO;
import com.ravunana.ensino.service.mapper.MatriculaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Matricula}.
 */
@Service
@Transactional
public class MatriculaService {

    private final Logger log = LoggerFactory.getLogger(MatriculaService.class);

    private final MatriculaRepository matriculaRepository;

    private final MatriculaMapper matriculaMapper;

    private final MatriculaSearchRepository matriculaSearchRepository;

    public MatriculaService(MatriculaRepository matriculaRepository, MatriculaMapper matriculaMapper, MatriculaSearchRepository matriculaSearchRepository) {
        this.matriculaRepository = matriculaRepository;
        this.matriculaMapper = matriculaMapper;
        this.matriculaSearchRepository = matriculaSearchRepository;
    }

    /**
     * Save a matricula.
     *
     * @param matriculaDTO the entity to save.
     * @return the persisted entity.
     */
    public MatriculaDTO save(MatriculaDTO matriculaDTO) {
        log.debug("Request to save Matricula : {}", matriculaDTO);
        Matricula matricula = matriculaMapper.toEntity(matriculaDTO);
        matricula = matriculaRepository.save(matricula);
        MatriculaDTO result = matriculaMapper.toDto(matricula);
        matriculaSearchRepository.save(matricula);
        return result;
    }

    /**
     * Get all the matriculas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MatriculaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Matriculas");
        return matriculaRepository.findAll(pageable)
            .map(matriculaMapper::toDto);
    }


    /**
     * Get one matricula by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MatriculaDTO> findOne(Long id) {
        log.debug("Request to get Matricula : {}", id);
        return matriculaRepository.findById(id)
            .map(matriculaMapper::toDto);
    }

    /**
     * Delete the matricula by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Matricula : {}", id);
        matriculaRepository.deleteById(id);
        matriculaSearchRepository.deleteById(id);
    }

    /**
     * Search for the matricula corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MatriculaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Matriculas for query {}", query);
        return matriculaSearchRepository.search(queryStringQuery(query), pageable)
            .map(matriculaMapper::toDto);
    }
}
