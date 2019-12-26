import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILookupItem } from 'app/shared/model/lookup-item.model';

@Component({
  selector: 'rv-lookup-item-detail',
  templateUrl: './lookup-item-detail.component.html'
})
export class LookupItemDetailComponent implements OnInit {
  lookupItem: ILookupItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lookupItem }) => {
      this.lookupItem = lookupItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
