import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocalizacaoInstituicaoEnsino } from 'app/shared/model/localizacao-instituicao-ensino.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LocalizacaoInstituicaoEnsinoService } from './localizacao-instituicao-ensino.service';
import { LocalizacaoInstituicaoEnsinoDeleteDialogComponent } from './localizacao-instituicao-ensino-delete-dialog.component';

@Component({
  selector: 'rv-localizacao-instituicao-ensino',
  templateUrl: './localizacao-instituicao-ensino.component.html'
})
export class LocalizacaoInstituicaoEnsinoComponent implements OnInit, OnDestroy {
  localizacaoInstituicaoEnsinos?: ILocalizacaoInstituicaoEnsino[];
  eventSubscriber?: Subscription;
  currentSearch: string;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected localizacaoInstituicaoEnsinoService: LocalizacaoInstituicaoEnsinoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadPage(page?: number): void {
    const pageToLoad: number = page ? page : this.page;
    if (this.currentSearch) {
      this.localizacaoInstituicaoEnsinoService
        .search({
          page: pageToLoad - 1,
          query: this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<ILocalizacaoInstituicaoEnsino[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
          () => this.onError()
        );
      return;
    }
    this.localizacaoInstituicaoEnsinoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ILocalizacaoInstituicaoEnsino[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadPage(1);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInLocalizacaoInstituicaoEnsinos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILocalizacaoInstituicaoEnsino): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLocalizacaoInstituicaoEnsinos(): void {
    this.eventSubscriber = this.eventManager.subscribe('localizacaoInstituicaoEnsinoListModification', () => this.loadPage());
  }

  delete(localizacaoInstituicaoEnsino: ILocalizacaoInstituicaoEnsino): void {
    const modalRef = this.modalService.open(LocalizacaoInstituicaoEnsinoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.localizacaoInstituicaoEnsino = localizacaoInstituicaoEnsino;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ILocalizacaoInstituicaoEnsino[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.ngbPaginationPage = this.page;
    this.router.navigate(['/localizacao-instituicao-ensino'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.localizacaoInstituicaoEnsinos = data ? data : [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
