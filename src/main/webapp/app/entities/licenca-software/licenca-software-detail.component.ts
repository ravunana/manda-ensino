import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';

@Component({
  selector: 'rv-licenca-software-detail',
  templateUrl: './licenca-software-detail.component.html'
})
export class LicencaSoftwareDetailComponent implements OnInit {
  licencaSoftware: ILicencaSoftware | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licencaSoftware }) => {
      this.licencaSoftware = licencaSoftware;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
