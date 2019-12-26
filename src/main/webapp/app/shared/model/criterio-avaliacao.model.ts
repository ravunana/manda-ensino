export interface ICriterioAvaliacao {
  id?: number;
  aprovaCom?: number;
  reporvaCom?: number;
  recursoCom?: number;
  fazExame?: boolean;
  fazRecurso?: boolean;
  fazExameEspecial?: boolean;
  numeroFaltaReprova?: number;
  menorNota?: number;
  maiorNota?: number;
  notaMinimaAprovacao?: number;
  planoCurricularDescricao?: string;
  planoCurricularId?: number;
}

export class CriterioAvaliacao implements ICriterioAvaliacao {
  constructor(
    public id?: number,
    public aprovaCom?: number,
    public reporvaCom?: number,
    public recursoCom?: number,
    public fazExame?: boolean,
    public fazRecurso?: boolean,
    public fazExameEspecial?: boolean,
    public numeroFaltaReprova?: number,
    public menorNota?: number,
    public maiorNota?: number,
    public notaMinimaAprovacao?: number,
    public planoCurricularDescricao?: string,
    public planoCurricularId?: number
  ) {
    this.fazExame = this.fazExame || false;
    this.fazRecurso = this.fazRecurso || false;
    this.fazExameEspecial = this.fazExameEspecial || false;
  }
}
