package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Pessoa;
import com.ravunana.ensino.repository.PessoaRepository;
import com.ravunana.ensino.repository.UserRepository;
import com.ravunana.ensino.repository.search.PessoaSearchRepository;
import com.ravunana.ensino.service.dto.ContactoPessoaDTO;
import com.ravunana.ensino.service.dto.MoradaPessoaDTO;
import com.ravunana.ensino.service.dto.PessoaDTO;
import com.ravunana.ensino.service.mapper.PessoaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Pessoa}.
 */
@Service
@Transactional
public class PessoaService {

    private final Logger log = LoggerFactory.getLogger(PessoaService.class);

    private final PessoaRepository pessoaRepository;

    private final PessoaMapper pessoaMapper;

    private final PessoaSearchRepository pessoaSearchRepository;

    @Autowired
    private ContactoPessoaService contactoPessoaService;
    @Autowired
    private MoradaPessoaService moradaPessoaService;
    @Autowired
    private UserService userService;

    public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper, PessoaSearchRepository pessoaSearchRepository) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
        this.pessoaSearchRepository = pessoaSearchRepository;
    }

    /**
     * Save a pessoa.
     *
     * @param pessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public PessoaDTO save(PessoaDTO pessoaDTO) {
        log.debug("Request to save Pessoa : {}", pessoaDTO);
        Pessoa pessoa = pessoaMapper.toEntity(pessoaDTO);
        // Pessoa Salva
        pessoa = pessoaRepository.save(pessoa);

            // Percorrer contacto
            for ( ContactoPessoaDTO contacto : contactoPessoaService.listContactos() ) {
                contacto.setPessoaId( pessoa.getId() );
                contactoPessoaService.save( contacto );
            }

            // Percorrer moradas
            for ( MoradaPessoaDTO morada : moradaPessoaService.listMoradas() ) {
                morada.setPessoaId( pessoa.getId() );
                moradaPessoaService.save( morada );
            }

            contactoPessoaService.limparContactos();
            moradaPessoaService.limparMoradas();

        PessoaDTO result = pessoaMapper.toDto(pessoa);
        pessoaSearchRepository.save(pessoa);
        return result;
    }

    /**
     * Get all the pessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PessoaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pessoas");
        return pessoaRepository.findAll(pageable)
            .map(pessoaMapper::toDto);
    }


    /**
     * Get one pessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findOne(Long id) {
        log.debug("Request to get Pessoa : {}", id);
        return pessoaRepository.findById(id)
            .map(pessoaMapper::toDto);
    }

    /**
     * Delete the pessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pessoa : {}", id);
        pessoaRepository.deleteById(id);
        pessoaSearchRepository.deleteById(id);
    }

    /**
     * Search for the pessoa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PessoaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pessoas for query {}", query);
        return pessoaSearchRepository.search(queryStringQuery(query), pageable)
            .map(pessoaMapper::toDto);
    }
}
