export interface IMatrizCurricular {
  id?: number;
  cursoNome?: string;
  cursoId?: number;
}

export class MatrizCurricular implements IMatrizCurricular {
  constructor(public id?: number, public cursoNome?: string, public cursoId?: number) {}
}
