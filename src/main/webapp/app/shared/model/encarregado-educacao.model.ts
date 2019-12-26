import { IAluno } from 'app/shared/model/aluno.model';

export interface IEncarregadoEducacao {
  id?: number;
  profissao?: string;
  cargo?: string;
  faixaSalarial?: number;
  nomeEmpresa?: string;
  contactoEmpresa?: string;
  pessoaNome?: string;
  pessoaId?: number;
  alunos?: IAluno[];
}

export class EncarregadoEducacao implements IEncarregadoEducacao {
  constructor(
    public id?: number,
    public profissao?: string,
    public cargo?: string,
    public faixaSalarial?: number,
    public nomeEmpresa?: string,
    public contactoEmpresa?: string,
    public pessoaNome?: string,
    public pessoaId?: number,
    public alunos?: IAluno[]
  ) {}
}
