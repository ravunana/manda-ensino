import { Moment } from 'moment';

export interface IDetalhePagamento {
  id?: number;
  descricao?: string;
  mensalidade?: boolean;
  quantidade?: number;
  valor?: number;
  desconto?: number;
  multa?: number;
  juro?: number;
  data?: Moment;
  vencimento?: Moment;
  quitado?: boolean;
  utilizadorLogin?: string;
  utilizadorId?: number;
  emolumentoNome?: string;
  emolumentoId?: number;
  depositoNumeroTalao?: string;
  depositoId?: number;
}

export class DetalhePagamento implements IDetalhePagamento {
  constructor(
    public id?: number,
    public descricao?: string,
    public mensalidade?: boolean,
    public quantidade?: number,
    public valor?: number,
    public desconto?: number,
    public multa?: number,
    public juro?: number,
    public data?: Moment,
    public vencimento?: Moment,
    public quitado?: boolean,
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public emolumentoNome?: string,
    public emolumentoId?: number,
    public depositoNumeroTalao?: string,
    public depositoId?: number
  ) {
    this.mensalidade = this.mensalidade || false;
    this.quitado = this.quitado || false;
  }
}
