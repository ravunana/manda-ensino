package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.PagamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pagamento} and its DTO {@link PagamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AlunoMapper.class, FormaLiquidacaoMapper.class})
public interface PagamentoMapper extends EntityMapper<PagamentoDTO, Pagamento> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "aluno.numeroProcesso", target = "alunoNumeroProcesso")
    @Mapping(source = "formaLiquidacao.id", target = "formaLiquidacaoId")
    @Mapping(source = "formaLiquidacao.nome", target = "formaLiquidacaoNome")
    PagamentoDTO toDto(Pagamento pagamento);

    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "alunoId", target = "aluno")
    @Mapping(source = "formaLiquidacaoId", target = "formaLiquidacao")
    Pagamento toEntity(PagamentoDTO pagamentoDTO);

    default Pagamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pagamento pagamento = new Pagamento();
        pagamento.setId(id);
        return pagamento;
    }
}
