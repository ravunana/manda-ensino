import { Moment } from 'moment';

export interface INota {
  id?: number;
  valor?: number;
  data?: Moment;
  anoLectivo?: Moment;
  periodoLectivo?: string;
  utilizadorLogin?: string;
  utilizadorId?: number;
  disciplinaNome?: string;
  disciplinaId?: number;
  turmaDescricao?: string;
  turmaId?: number;
  categoriaAvaliacaoNome?: string;
  categoriaAvaliacaoId?: number;
  matriculaNumero?: string;
  matriculaId?: number;
}

export class Nota implements INota {
  constructor(
    public id?: number,
    public valor?: number,
    public data?: Moment,
    public anoLectivo?: Moment,
    public periodoLectivo?: string,
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public disciplinaNome?: string,
    public disciplinaId?: number,
    public turmaDescricao?: string,
    public turmaId?: number,
    public categoriaAvaliacaoNome?: string,
    public categoriaAvaliacaoId?: number,
    public matriculaNumero?: string,
    public matriculaId?: number
  ) {}
}
