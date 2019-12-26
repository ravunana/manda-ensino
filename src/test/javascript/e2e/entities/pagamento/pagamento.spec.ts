import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PagamentoComponentsPage,
  /* PagamentoDeleteDialog,
   */ PagamentoUpdatePage
} from './pagamento.page-object';

const expect = chai.expect;

describe('Pagamento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pagamentoComponentsPage: PagamentoComponentsPage;
  let pagamentoUpdatePage: PagamentoUpdatePage;
  /* let pagamentoDeleteDialog: PagamentoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Pagamentos', async () => {
    await navBarPage.goToEntity('pagamento');
    pagamentoComponentsPage = new PagamentoComponentsPage();
    await browser.wait(ec.visibilityOf(pagamentoComponentsPage.title), 5000);
    expect(await pagamentoComponentsPage.getTitle()).to.eq('ensinoApp.pagamento.home.title');
  });

  it('should load create Pagamento page', async () => {
    await pagamentoComponentsPage.clickOnCreateButton();
    pagamentoUpdatePage = new PagamentoUpdatePage();
    expect(await pagamentoUpdatePage.getPageTitle()).to.eq('ensinoApp.pagamento.home.createOrEditLabel');
    await pagamentoUpdatePage.cancel();
  });

  /*  it('should create and save Pagamentos', async () => {
        const nbButtonsBeforeCreate = await pagamentoComponentsPage.countDeleteButtons();

        await pagamentoComponentsPage.clickOnCreateButton();
        await promise.all([
            pagamentoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            pagamentoUpdatePage.setNumeroInput('numero'),
            pagamentoUpdatePage.utilizadorSelectLastOption(),
            pagamentoUpdatePage.alunoSelectLastOption(),
            pagamentoUpdatePage.formaLiquidacaoSelectLastOption(),
        ]);
        expect(await pagamentoUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await pagamentoUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        await pagamentoUpdatePage.save();
        expect(await pagamentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await pagamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Pagamento', async () => {
        const nbButtonsBeforeDelete = await pagamentoComponentsPage.countDeleteButtons();
        await pagamentoComponentsPage.clickOnLastDeleteButton();

        pagamentoDeleteDialog = new PagamentoDeleteDialog();
        expect(await pagamentoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.pagamento.delete.question');
        await pagamentoDeleteDialog.clickOnConfirmButton();

        expect(await pagamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
