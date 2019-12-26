import { Moment } from 'moment';

export interface IHorario {
  id?: number;
  inicioAula?: Moment;
  terminoAlua?: Moment;
  intervalo?: Moment;
  diaSemana?: string;
  regimeCurricular?: string;
  data?: Moment;
  anoLectivo?: Moment;
  categoria?: string;
  utilizadorLogin?: string;
  utilizadorId?: number;
  turmaDescricao?: string;
  turmaId?: number;
  professorNumeroAgente?: string;
  professorId?: number;
  disciplinaNome?: string;
  disciplinaId?: number;
}

export class Horario implements IHorario {
  constructor(
    public id?: number,
    public inicioAula?: Moment,
    public terminoAlua?: Moment,
    public intervalo?: Moment,
    public diaSemana?: string,
    public regimeCurricular?: string,
    public data?: Moment,
    public anoLectivo?: Moment,
    public categoria?: string,
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public turmaDescricao?: string,
    public turmaId?: number,
    public professorNumeroAgente?: string,
    public professorId?: number,
    public disciplinaNome?: string,
    public disciplinaId?: number
  ) {}
}
