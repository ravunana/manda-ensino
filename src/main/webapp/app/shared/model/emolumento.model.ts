import { IDetalhePagamento } from 'app/shared/model/detalhe-pagamento.model';

export interface IEmolumento {
  id?: number;
  nome?: string;
  valor?: number;
  valorMulta?: number;
  tempoMulta?: number;
  quantidade?: number;
  detalhePagamentos?: IDetalhePagamento[];
  cursoNome?: string;
  cursoId?: number;
  classeDescricao?: string;
  classeId?: number;
}

export class Emolumento implements IEmolumento {
  constructor(
    public id?: number,
    public nome?: string,
    public valor?: number,
    public valorMulta?: number,
    public tempoMulta?: number,
    public quantidade?: number,
    public detalhePagamentos?: IDetalhePagamento[],
    public cursoNome?: string,
    public cursoId?: number,
    public classeDescricao?: string,
    public classeId?: number
  ) {}
}
