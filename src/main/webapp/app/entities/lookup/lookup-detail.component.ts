import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILookup } from 'app/shared/model/lookup.model';

@Component({
  selector: 'rv-lookup-detail',
  templateUrl: './lookup-detail.component.html'
})
export class LookupDetailComponent implements OnInit {
  lookup: ILookup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lookup }) => {
      this.lookup = lookup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
