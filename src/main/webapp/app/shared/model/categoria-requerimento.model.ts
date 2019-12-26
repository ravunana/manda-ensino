import { IRequerimento } from 'app/shared/model/requerimento.model';

export interface ICategoriaRequerimento {
  id?: number;
  nome?: string;
  tempoResposta?: number;
  pagase?: boolean;
  descricao?: any;
  requerimentos?: IRequerimento[];
}

export class CategoriaRequerimento implements ICategoriaRequerimento {
  constructor(
    public id?: number,
    public nome?: string,
    public tempoResposta?: number,
    public pagase?: boolean,
    public descricao?: any,
    public requerimentos?: IRequerimento[]
  ) {
    this.pagase = this.pagase || false;
  }
}
