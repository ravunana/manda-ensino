import { Moment } from 'moment';

export interface ISituacaoAcademica {
  id?: number;
  anoLectivo?: number;
  data?: Moment;
  estado?: string;
  descricao?: any;
  alunoNumeroProcesso?: string;
  alunoId?: number;
  disciplinaNome?: string;
  disciplinaId?: number;
}

export class SituacaoAcademica implements ISituacaoAcademica {
  constructor(
    public id?: number,
    public anoLectivo?: number,
    public data?: Moment,
    public estado?: string,
    public descricao?: any,
    public alunoNumeroProcesso?: string,
    public alunoId?: number,
    public disciplinaNome?: string,
    public disciplinaId?: number
  ) {}
}
