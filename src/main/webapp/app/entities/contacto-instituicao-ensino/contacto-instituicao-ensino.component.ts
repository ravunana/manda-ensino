import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactoInstituicaoEnsino } from 'app/shared/model/contacto-instituicao-ensino.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContactoInstituicaoEnsinoService } from './contacto-instituicao-ensino.service';
import { ContactoInstituicaoEnsinoDeleteDialogComponent } from './contacto-instituicao-ensino-delete-dialog.component';

@Component({
  selector: 'rv-contacto-instituicao-ensino',
  templateUrl: './contacto-instituicao-ensino.component.html'
})
export class ContactoInstituicaoEnsinoComponent implements OnInit, OnDestroy {
  contactoInstituicaoEnsinos?: IContactoInstituicaoEnsino[];
  eventSubscriber?: Subscription;
  currentSearch: string;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected contactoInstituicaoEnsinoService: ContactoInstituicaoEnsinoService,
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
      this.contactoInstituicaoEnsinoService
        .search({
          page: pageToLoad - 1,
          query: this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IContactoInstituicaoEnsino[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
          () => this.onError()
        );
      return;
    }
    this.contactoInstituicaoEnsinoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IContactoInstituicaoEnsino[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInContactoInstituicaoEnsinos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContactoInstituicaoEnsino): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContactoInstituicaoEnsinos(): void {
    this.eventSubscriber = this.eventManager.subscribe('contactoInstituicaoEnsinoListModification', () => this.loadPage());
  }

  delete(contactoInstituicaoEnsino: IContactoInstituicaoEnsino): void {
    const modalRef = this.modalService.open(ContactoInstituicaoEnsinoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactoInstituicaoEnsino = contactoInstituicaoEnsino;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IContactoInstituicaoEnsino[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.ngbPaginationPage = this.page;
    this.router.navigate(['/contacto-instituicao-ensino'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.contactoInstituicaoEnsinos = data ? data : [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
