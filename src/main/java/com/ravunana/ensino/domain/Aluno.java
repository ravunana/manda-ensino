package com.ravunana.ensino.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Aluno.
 */
@Entity
@Table(name = "sec_aluno")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "aluno")
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "numero_processo", nullable = false, unique = true)
    private String numeroProcesso;

    @NotNull
    @Column(name = "transferido", nullable = false)
    private Boolean transferido;

    @Column(name = "data")
    private ZonedDateTime data;

    @Column(name = "turma_anterior")
    private String turmaAnterior;

    @Column(name = "ano_conclusao")
    private Integer anoConclusao;

    @Column(name = "curso_frequentado")
    private String cursoFrequentado;

    @Column(name = "nome_escola_anteriror")
    private String nomeEscolaAnteriror;

    @Column(name = "endereco_escola_anterior")
    private String enderecoEscolaAnterior;

    @Column(name = "classe_concluida")
    private Integer classeConcluida;

    @Column(name = "numero_processo_anterior")
    private String numeroProcessoAnterior;

    @Column(name = "situacao_anterior")
    private String situacaoAnterior;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Matricula> matriculas = new HashSet<>();

    @OneToMany(mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contrato> contratoes = new HashSet<>();

    @OneToMany(mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SituacaoAcademica> situacaoAcademicas = new HashSet<>();

    @OneToMany(mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requerimento> requerimentos = new HashSet<>();

    @OneToMany(mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pagamento> pagamentos = new HashSet<>();

    @OneToMany(mappedBy = "aluno")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Deposito> depositos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("alunos")
    private User utilizador;

    @ManyToOne
    @JsonIgnoreProperties("alunos")
    private EncarregadoEducacao encarregadoEducacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public Aluno numeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
        return this;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Boolean isTransferido() {
        return transferido;
    }

    public Aluno transferido(Boolean transferido) {
        this.transferido = transferido;
        return this;
    }

    public void setTransferido(Boolean transferido) {
        this.transferido = transferido;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Aluno data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getTurmaAnterior() {
        return turmaAnterior;
    }

    public Aluno turmaAnterior(String turmaAnterior) {
        this.turmaAnterior = turmaAnterior;
        return this;
    }

    public void setTurmaAnterior(String turmaAnterior) {
        this.turmaAnterior = turmaAnterior;
    }

    public Integer getAnoConclusao() {
        return anoConclusao;
    }

    public Aluno anoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
        return this;
    }

    public void setAnoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
    }

    public String getCursoFrequentado() {
        return cursoFrequentado;
    }

    public Aluno cursoFrequentado(String cursoFrequentado) {
        this.cursoFrequentado = cursoFrequentado;
        return this;
    }

    public void setCursoFrequentado(String cursoFrequentado) {
        this.cursoFrequentado = cursoFrequentado;
    }

    public String getNomeEscolaAnteriror() {
        return nomeEscolaAnteriror;
    }

    public Aluno nomeEscolaAnteriror(String nomeEscolaAnteriror) {
        this.nomeEscolaAnteriror = nomeEscolaAnteriror;
        return this;
    }

    public void setNomeEscolaAnteriror(String nomeEscolaAnteriror) {
        this.nomeEscolaAnteriror = nomeEscolaAnteriror;
    }

    public String getEnderecoEscolaAnterior() {
        return enderecoEscolaAnterior;
    }

    public Aluno enderecoEscolaAnterior(String enderecoEscolaAnterior) {
        this.enderecoEscolaAnterior = enderecoEscolaAnterior;
        return this;
    }

    public void setEnderecoEscolaAnterior(String enderecoEscolaAnterior) {
        this.enderecoEscolaAnterior = enderecoEscolaAnterior;
    }

    public Integer getClasseConcluida() {
        return classeConcluida;
    }

    public Aluno classeConcluida(Integer classeConcluida) {
        this.classeConcluida = classeConcluida;
        return this;
    }

    public void setClasseConcluida(Integer classeConcluida) {
        this.classeConcluida = classeConcluida;
    }

    public String getNumeroProcessoAnterior() {
        return numeroProcessoAnterior;
    }

    public Aluno numeroProcessoAnterior(String numeroProcessoAnterior) {
        this.numeroProcessoAnterior = numeroProcessoAnterior;
        return this;
    }

    public void setNumeroProcessoAnterior(String numeroProcessoAnterior) {
        this.numeroProcessoAnterior = numeroProcessoAnterior;
    }

    public String getSituacaoAnterior() {
        return situacaoAnterior;
    }

    public Aluno situacaoAnterior(String situacaoAnterior) {
        this.situacaoAnterior = situacaoAnterior;
        return this;
    }

    public void setSituacaoAnterior(String situacaoAnterior) {
        this.situacaoAnterior = situacaoAnterior;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Aluno pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Set<Matricula> getMatriculas() {
        return matriculas;
    }

    public Aluno matriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
        return this;
    }

    public Aluno addMatricula(Matricula matricula) {
        this.matriculas.add(matricula);
        matricula.setAluno(this);
        return this;
    }

    public Aluno removeMatricula(Matricula matricula) {
        this.matriculas.remove(matricula);
        matricula.setAluno(null);
        return this;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Set<Contrato> getContratoes() {
        return contratoes;
    }

    public Aluno contratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
        return this;
    }

    public Aluno addContrato(Contrato contrato) {
        this.contratoes.add(contrato);
        contrato.setAluno(this);
        return this;
    }

    public Aluno removeContrato(Contrato contrato) {
        this.contratoes.remove(contrato);
        contrato.setAluno(null);
        return this;
    }

    public void setContratoes(Set<Contrato> contratoes) {
        this.contratoes = contratoes;
    }

    public Set<SituacaoAcademica> getSituacaoAcademicas() {
        return situacaoAcademicas;
    }

    public Aluno situacaoAcademicas(Set<SituacaoAcademica> situacaoAcademicas) {
        this.situacaoAcademicas = situacaoAcademicas;
        return this;
    }

    public Aluno addSituacaoAcademica(SituacaoAcademica situacaoAcademica) {
        this.situacaoAcademicas.add(situacaoAcademica);
        situacaoAcademica.setAluno(this);
        return this;
    }

    public Aluno removeSituacaoAcademica(SituacaoAcademica situacaoAcademica) {
        this.situacaoAcademicas.remove(situacaoAcademica);
        situacaoAcademica.setAluno(null);
        return this;
    }

    public void setSituacaoAcademicas(Set<SituacaoAcademica> situacaoAcademicas) {
        this.situacaoAcademicas = situacaoAcademicas;
    }

    public Set<Requerimento> getRequerimentos() {
        return requerimentos;
    }

    public Aluno requerimentos(Set<Requerimento> requerimentos) {
        this.requerimentos = requerimentos;
        return this;
    }

    public Aluno addRequerimento(Requerimento requerimento) {
        this.requerimentos.add(requerimento);
        requerimento.setAluno(this);
        return this;
    }

    public Aluno removeRequerimento(Requerimento requerimento) {
        this.requerimentos.remove(requerimento);
        requerimento.setAluno(null);
        return this;
    }

    public void setRequerimentos(Set<Requerimento> requerimentos) {
        this.requerimentos = requerimentos;
    }

    public Set<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public Aluno pagamentos(Set<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
        return this;
    }

    public Aluno addPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
        pagamento.setAluno(this);
        return this;
    }

    public Aluno removePagamento(Pagamento pagamento) {
        this.pagamentos.remove(pagamento);
        pagamento.setAluno(null);
        return this;
    }

    public void setPagamentos(Set<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Set<Deposito> getDepositos() {
        return depositos;
    }

    public Aluno depositos(Set<Deposito> depositos) {
        this.depositos = depositos;
        return this;
    }

    public Aluno addDeposito(Deposito deposito) {
        this.depositos.add(deposito);
        deposito.setAluno(this);
        return this;
    }

    public Aluno removeDeposito(Deposito deposito) {
        this.depositos.remove(deposito);
        deposito.setAluno(null);
        return this;
    }

    public void setDepositos(Set<Deposito> depositos) {
        this.depositos = depositos;
    }

    public User getUtilizador() {
        return utilizador;
    }

    public Aluno utilizador(User user) {
        this.utilizador = user;
        return this;
    }

    public void setUtilizador(User user) {
        this.utilizador = user;
    }

    public EncarregadoEducacao getEncarregadoEducacao() {
        return encarregadoEducacao;
    }

    public Aluno encarregadoEducacao(EncarregadoEducacao encarregadoEducacao) {
        this.encarregadoEducacao = encarregadoEducacao;
        return this;
    }

    public void setEncarregadoEducacao(EncarregadoEducacao encarregadoEducacao) {
        this.encarregadoEducacao = encarregadoEducacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aluno)) {
            return false;
        }
        return id != null && id.equals(((Aluno) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Aluno{" +
            "id=" + getId() +
            ", numeroProcesso='" + getNumeroProcesso() + "'" +
            ", transferido='" + isTransferido() + "'" +
            ", data='" + getData() + "'" +
            ", turmaAnterior='" + getTurmaAnterior() + "'" +
            ", anoConclusao=" + getAnoConclusao() +
            ", cursoFrequentado='" + getCursoFrequentado() + "'" +
            ", nomeEscolaAnteriror='" + getNomeEscolaAnteriror() + "'" +
            ", enderecoEscolaAnterior='" + getEnderecoEscolaAnterior() + "'" +
            ", classeConcluida=" + getClasseConcluida() +
            ", numeroProcessoAnterior='" + getNumeroProcessoAnterior() + "'" +
            ", situacaoAnterior='" + getSituacaoAnterior() + "'" +
            "}";
    }
}
