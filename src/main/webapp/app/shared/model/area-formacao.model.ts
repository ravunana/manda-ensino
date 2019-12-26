import { ICurso } from 'app/shared/model/curso.model';
import { ICategoriaValiacao } from 'app/shared/model/categoria-valiacao.model';

export interface IAreaFormacao {
  id?: number;
  nome?: string;
  competencias?: any;
  cursos?: ICurso[];
  categoriaValiacaos?: ICategoriaValiacao[];
}

export class AreaFormacao implements IAreaFormacao {
  constructor(
    public id?: number,
    public nome?: string,
    public competencias?: any,
    public cursos?: ICurso[],
    public categoriaValiacaos?: ICategoriaValiacao[]
  ) {}
}
