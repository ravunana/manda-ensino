import { LookupType } from 'app/shared/model/enumerations/lookup-type.model';

export interface ILookupItem {
  id?: number;
  valor?: string;
  nome?: string;
  type?: LookupType;
  lookupNome?: string;
  lookupId?: number;
}

export class LookupItem implements ILookupItem {
  constructor(
    public id?: number,
    public valor?: string,
    public nome?: string,
    public type?: LookupType,
    public lookupNome?: string,
    public lookupId?: number
  ) {}
}
