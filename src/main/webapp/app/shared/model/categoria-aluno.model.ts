import { IMatricula } from 'app/shared/model/matricula.model';

export interface ICategoriaAluno {
  id?: number;
  nome?: string;
  desconto?: number;
  pagaPropina?: boolean;
  pagaMulta?: boolean;
  descricao?: any;
  diaPagamento?: number;
  mesAtual?: boolean;
  ativo?: boolean;
  matriculas?: IMatricula[];
}

export class CategoriaAluno implements ICategoriaAluno {
  constructor(
    public id?: number,
    public nome?: string,
    public desconto?: number,
    public pagaPropina?: boolean,
    public pagaMulta?: boolean,
    public descricao?: any,
    public diaPagamento?: number,
    public mesAtual?: boolean,
    public ativo?: boolean,
    public matriculas?: IMatricula[]
  ) {
    this.pagaPropina = this.pagaPropina || false;
    this.pagaMulta = this.pagaMulta || false;
    this.mesAtual = this.mesAtual || false;
    this.ativo = this.ativo || false;
  }
}
