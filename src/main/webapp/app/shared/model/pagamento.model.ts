import { Moment } from 'moment';

export interface IPagamento {
  id?: number;
  data?: Moment;
  numero?: string;
  utilizadorLogin?: string;
  utilizadorId?: number;
  alunoNumeroProcesso?: string;
  alunoId?: number;
  formaLiquidacaoNome?: string;
  formaLiquidacaoId?: number;
}

export class Pagamento implements IPagamento {
  constructor(
    public id?: number,
    public data?: Moment,
    public numero?: string,
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public alunoNumeroProcesso?: string,
    public alunoId?: number,
    public formaLiquidacaoNome?: string,
    public formaLiquidacaoId?: number
  ) {}
}
