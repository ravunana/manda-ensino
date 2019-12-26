import { Moment } from 'moment';

export interface IPlanoActividade {
  id?: number;
  numeroActividade?: number;
  atividade?: string;
  objectivos?: any;
  de?: Moment;
  ate?: Moment;
  responsavel?: string;
  local?: string;
  observacao?: any;
  participantes?: string;
  coResponsavel?: string;
  statusActividade?: string;
  anoLectivo?: Moment;
  periodoLectivo?: string;
  utilizadorLogin?: string;
  utilizadorId?: number;
}

export class PlanoActividade implements IPlanoActividade {
  constructor(
    public id?: number,
    public numeroActividade?: number,
    public atividade?: string,
    public objectivos?: any,
    public de?: Moment,
    public ate?: Moment,
    public responsavel?: string,
    public local?: string,
    public observacao?: any,
    public participantes?: string,
    public coResponsavel?: string,
    public statusActividade?: string,
    public anoLectivo?: Moment,
    public periodoLectivo?: string,
    public utilizadorLogin?: string,
    public utilizadorId?: number
  ) {}
}
