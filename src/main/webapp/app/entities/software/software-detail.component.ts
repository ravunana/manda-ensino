import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISoftware } from 'app/shared/model/software.model';

@Component({
  selector: 'rv-software-detail',
  templateUrl: './software-detail.component.html'
})
export class SoftwareDetailComponent implements OnInit {
  software: ISoftware | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ software }) => {
      this.software = software;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
