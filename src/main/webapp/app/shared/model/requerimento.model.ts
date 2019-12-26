import { Moment } from 'moment';

export interface IRequerimento {
  id?: number;
  requerimentoContentType?: string;
  requerimento?: any;
  data?: Moment;
  statusRequerimento?: string;
  utilizadorLogin?: string;
  utilizadorId?: number;
  categoriaNome?: string;
  categoriaId?: number;
  alunoNumeroProcesso?: string;
  alunoId?: number;
}

export class Requerimento implements IRequerimento {
  constructor(
    public id?: number,
    public requerimentoContentType?: string,
    public requerimento?: any,
    public data?: Moment,
    public statusRequerimento?: string,
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public categoriaNome?: string,
    public categoriaId?: number,
    public alunoNumeroProcesso?: string,
    public alunoId?: number
  ) {}
}
