export interface IPlanoCurricular {
  id?: number;
  cargaHoraria?: number;
  descricao?: string;
  terminal?: boolean;
  regimeCurricular?: string;
  componente?: string;
  disciplinaNome?: string;
  disciplinaId?: number;
  classeDescricao?: string;
  classeId?: number;
}

export class PlanoCurricular implements IPlanoCurricular {
  constructor(
    public id?: number,
    public cargaHoraria?: number,
    public descricao?: string,
    public terminal?: boolean,
    public regimeCurricular?: string,
    public componente?: string,
    public disciplinaNome?: string,
    public disciplinaId?: number,
    public classeDescricao?: string,
    public classeId?: number
  ) {
    this.terminal = this.terminal || false;
  }
}
