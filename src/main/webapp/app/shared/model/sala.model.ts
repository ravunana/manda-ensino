import { ITurma } from 'app/shared/model/turma.model';

export interface ISala {
  id?: number;
  numero?: number;
  descricao?: any;
  lotacao?: number;
  turmas?: ITurma[];
}

export class Sala implements ISala {
  constructor(public id?: number, public numero?: number, public descricao?: any, public lotacao?: number, public turmas?: ITurma[]) {}
}
