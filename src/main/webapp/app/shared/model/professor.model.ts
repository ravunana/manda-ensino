import { IHorario } from 'app/shared/model/horario.model';

export interface IProfessor {
  id?: number;
  numeroAgente?: string;
  ativo?: boolean;
  pessoaNome?: string;
  pessoaId?: number;
  horarios?: IHorario[];
  utilizadorLogin?: string;
  utilizadorId?: number;
}

export class Professor implements IProfessor {
  constructor(
    public id?: number,
    public numeroAgente?: string,
    public ativo?: boolean,
    public pessoaNome?: string,
    public pessoaId?: number,
    public horarios?: IHorario[],
    public utilizadorLogin?: string,
    public utilizadorId?: number
  ) {
    this.ativo = this.ativo || false;
  }
}
