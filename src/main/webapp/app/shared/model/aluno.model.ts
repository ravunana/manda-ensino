import { Moment } from 'moment';
import { IMatricula } from 'app/shared/model/matricula.model';
import { IContrato } from 'app/shared/model/contrato.model';
import { ISituacaoAcademica } from 'app/shared/model/situacao-academica.model';
import { IRequerimento } from 'app/shared/model/requerimento.model';
import { IPagamento } from 'app/shared/model/pagamento.model';
import { IDeposito } from 'app/shared/model/deposito.model';

export interface IAluno {
  id?: number;
  numeroProcesso?: string;
  transferido?: boolean;
  data?: Moment;
  turmaAnterior?: string;
  anoConclusao?: number;
  cursoFrequentado?: string;
  nomeEscolaAnteriror?: string;
  enderecoEscolaAnterior?: string;
  classeConcluida?: number;
  numeroProcessoAnterior?: string;
  situacaoAnterior?: string;
  pessoaNome?: string;
  pessoaId?: number;
  matriculas?: IMatricula[];
  contratoes?: IContrato[];
  situacaoAcademicas?: ISituacaoAcademica[];
  requerimentos?: IRequerimento[];
  pagamentos?: IPagamento[];
  depositos?: IDeposito[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  encarregadoEducacaoId?: number;
}

export class Aluno implements IAluno {
  constructor(
    public id?: number,
    public numeroProcesso?: string,
    public transferido?: boolean,
    public data?: Moment,
    public turmaAnterior?: string,
    public anoConclusao?: number,
    public cursoFrequentado?: string,
    public nomeEscolaAnteriror?: string,
    public enderecoEscolaAnterior?: string,
    public classeConcluida?: number,
    public numeroProcessoAnterior?: string,
    public situacaoAnterior?: string,
    public pessoaNome?: string,
    public pessoaId?: number,
    public matriculas?: IMatricula[],
    public contratoes?: IContrato[],
    public situacaoAcademicas?: ISituacaoAcademica[],
    public requerimentos?: IRequerimento[],
    public pagamentos?: IPagamento[],
    public depositos?: IDeposito[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public encarregadoEducacaoId?: number
  ) {
    this.transferido = this.transferido || false;
  }
}
