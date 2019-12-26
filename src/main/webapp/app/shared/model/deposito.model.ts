import { Moment } from 'moment';
import { IDetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';

export interface IDeposito {
  id?: number;
  numeroTalao?: string;
  dataDeposito?: Moment;
  valor?: number;
  saldo?: number;
  detalhePagamentos?: IDetalhePagamento[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  meioLiquidacaoNome?: string;
  meioLiquidacaoId?: number;
  alunoNumeroProcesso?: string;
  alunoId?: number;
  contaDescricao?: string;
  contaId?: number;
}

export class Deposito implements IDeposito {
  constructor(
    public id?: number,
    public numeroTalao?: string,
    public dataDeposito?: Moment,
    public valor?: number,
    public saldo?: number,
    public detalhePagamentos?: IDetalhePagamento[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public meioLiquidacaoNome?: string,
    public meioLiquidacaoId?: number,
    public alunoNumeroProcesso?: string,
    public alunoId?: number,
    public contaDescricao?: string,
    public contaId?: number
  ) {}
}
