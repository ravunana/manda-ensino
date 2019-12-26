import { Moment } from 'moment';
import { INota } from 'app/shared/model/nota.model';
import { IMatricula } from 'app/shared/model/matricula.model';
import { IOcorrencia } from 'app/shared/model/ocorrencia.model';
import { IDocumentoMatricula } from 'app/shared/model/documento-matricula.model';

export interface IMatricula {
  id?: number;
  data?: Moment;
  numero?: number;
  observacao?: any;
  anoLectivo?: number;
  peridoLectivo?: string;
  notas?: INota[];
  matriculas?: IMatricula[];
  ocorrencias?: IOcorrencia[];
  documentoMatriculas?: IDocumentoMatricula[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  alunoNumeroProcesso?: string;
  alunoId?: number;
  confirmacaoNumero?: string;
  confirmacaoId?: number;
  categoriaNome?: string;
  categoriaId?: number;
  turmaDescricao?: string;
  turmaId?: number;
}

export class Matricula implements IMatricula {
  constructor(
    public id?: number,
    public data?: Moment,
    public numero?: number,
    public observacao?: any,
    public anoLectivo?: number,
    public peridoLectivo?: string,
    public notas?: INota[],
    public matriculas?: IMatricula[],
    public ocorrencias?: IOcorrencia[],
    public documentoMatriculas?: IDocumentoMatricula[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public alunoNumeroProcesso?: string,
    public alunoId?: number,
    public confirmacaoNumero?: string,
    public confirmacaoId?: number,
    public categoriaNome?: string,
    public categoriaId?: number,
    public turmaDescricao?: string,
    public turmaId?: number
  ) {}
}
