import { IUnidadeCurricular } from 'app/shared/model/unidade-curricular.model';

export interface IUnidadeCurricular {
  id?: number;
  descricao?: any;
  unidade?: string;
  numero?: number;
  unidadeCurriculars?: IUnidadeCurricular[];
  disciplinaNome?: string;
  disciplinaId?: number;
  classeDescricao?: string;
  classeId?: number;
  herarquiaUnidade?: string;
  herarquiaId?: number;
}

export class UnidadeCurricular implements IUnidadeCurricular {
  constructor(
    public id?: number,
    public descricao?: any,
    public unidade?: string,
    public numero?: number,
    public unidadeCurriculars?: IUnidadeCurricular[],
    public disciplinaNome?: string,
    public disciplinaId?: number,
    public classeDescricao?: string,
    public classeId?: number,
    public herarquiaUnidade?: string,
    public herarquiaId?: number
  ) {}
}
