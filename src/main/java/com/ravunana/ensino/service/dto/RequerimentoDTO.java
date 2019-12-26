package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Requerimento} entity.
 */
public class RequerimentoDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] requerimento;

    private String requerimentoContentType;
    private ZonedDateTime data;

    private String statusRequerimento;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Long categoriaId;

    private String categoriaNome;

    private Long alunoId;

    private String alunoNumeroProcesso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getRequerimento() {
        return requerimento;
    }

    public void setRequerimento(byte[] requerimento) {
        this.requerimento = requerimento;
    }

    public String getRequerimentoContentType() {
        return requerimentoContentType;
    }

    public void setRequerimentoContentType(String requerimentoContentType) {
        this.requerimentoContentType = requerimentoContentType;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getStatusRequerimento() {
        return statusRequerimento;
    }

    public void setStatusRequerimento(String statusRequerimento) {
        this.statusRequerimento = statusRequerimento;
    }

    public Long getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Long userId) {
        this.utilizadorId = userId;
    }

    public String getUtilizadorLogin() {
        return utilizadorLogin;
    }

    public void setUtilizadorLogin(String userLogin) {
        this.utilizadorLogin = userLogin;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaRequerimentoId) {
        this.categoriaId = categoriaRequerimentoId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaRequerimentoNome) {
        this.categoriaNome = categoriaRequerimentoNome;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoNumeroProcesso() {
        return alunoNumeroProcesso;
    }

    public void setAlunoNumeroProcesso(String alunoNumeroProcesso) {
        this.alunoNumeroProcesso = alunoNumeroProcesso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequerimentoDTO requerimentoDTO = (RequerimentoDTO) o;
        if (requerimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requerimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequerimentoDTO{" +
            "id=" + getId() +
            ", requerimento='" + getRequerimento() + "'" +
            ", data='" + getData() + "'" +
            ", statusRequerimento='" + getStatusRequerimento() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", categoriaId=" + getCategoriaId() +
            ", categoriaNome='" + getCategoriaNome() + "'" +
            ", alunoId=" + getAlunoId() +
            ", alunoNumeroProcesso='" + getAlunoNumeroProcesso() + "'" +
            "}";
    }
}
