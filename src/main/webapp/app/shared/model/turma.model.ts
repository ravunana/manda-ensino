import { Moment } from 'moment';
import { IHorario } from 'app/shared/model/horario.model';
import { INota } from 'app/shared/model/nota.model';
import { IAula } from 'app/shared/model/aula.model';
import { IMatricula } from 'app/shared/model/matricula.model';
import { IPlanoAula } from 'app/shared/model/plano-aula.model';

export interface ITurma {
  id?: number;
  descricao?: string;
  anoLectivo?: Moment;
  regime?: string;
  turno?: string;
  data?: Moment;
  ativo?: boolean;
  horarios?: IHorario[];
  notas?: INota[];
  aulas?: IAula[];
  matriculas?: IMatricula[];
  utilizadorLogin?: string;
  utilizadorId?: number;
  salaNumero?: string;
  salaId?: number;
  classeDescricao?: string;
  classeId?: number;
  cursoNome?: string;
  cursoId?: number;
  planoAulas?: IPlanoAula[];
}

export class Turma implements ITurma {
  constructor(
    public id?: number,
    public descricao?: string,
    public anoLectivo?: Moment,
    public regime?: string,
    public turno?: string,
    public data?: Moment,
    public ativo?: boolean,
    public horarios?: IHorario[],
    public notas?: INota[],
    public aulas?: IAula[],
    public matriculas?: IMatricula[],
    public utilizadorLogin?: string,
    public utilizadorId?: number,
    public salaNumero?: string,
    public salaId?: number,
    public classeDescricao?: string,
    public classeId?: number,
    public cursoNome?: string,
    public cursoId?: number,
    public planoAulas?: IPlanoAula[]
  ) {
    this.ativo = this.ativo || false;
  }
}
