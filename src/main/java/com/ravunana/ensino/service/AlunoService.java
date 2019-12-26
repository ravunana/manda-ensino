package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Aluno;
import com.ravunana.ensino.repository.AlunoRepository;
import com.ravunana.ensino.repository.search.AlunoSearchRepository;
import com.ravunana.ensino.service.dto.AlunoDTO;
import com.ravunana.ensino.service.mapper.AlunoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Aluno}.
 */
@Service
@Transactional
public class AlunoService {

    private final Logger log = LoggerFactory.getLogger(AlunoService.class);

    private final AlunoRepository alunoRepository;

    private final AlunoMapper alunoMapper;

    private final AlunoSearchRepository alunoSearchRepository;

    public AlunoService(AlunoRepository alunoRepository, AlunoMapper alunoMapper, AlunoSearchRepository alunoSearchRepository) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
        this.alunoSearchRepository = alunoSearchRepository;
    }

    /**
     * Save a aluno.
     *
     * @param alunoDTO the entity to save.
     * @return the persisted entity.
     */
    public AlunoDTO save(AlunoDTO alunoDTO) {
        log.debug("Request to save Aluno : {}", alunoDTO);
        Aluno aluno = alunoMapper.toEntity(alunoDTO);
        aluno = alunoRepository.save(aluno);
        AlunoDTO result = alunoMapper.toDto(aluno);
        alunoSearchRepository.save(aluno);
        return result;
    }

    /**
     * Get all the alunos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlunoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alunos");
        return alunoRepository.findAll(pageable)
            .map(alunoMapper::toDto);
    }


    /**
     * Get one aluno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AlunoDTO> findOne(Long id) {
        log.debug("Request to get Aluno : {}", id);
        return alunoRepository.findById(id)
            .map(alunoMapper::toDto);
    }

    /**
     * Delete the aluno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Aluno : {}", id);
        alunoRepository.deleteById(id);
        alunoSearchRepository.deleteById(id);
    }

    /**
     * Search for the aluno corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlunoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Alunos for query {}", query);
        return alunoSearchRepository.search(queryStringQuery(query), pageable)
            .map(alunoMapper::toDto);
    }
}
