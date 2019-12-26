import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SerieDocumentoComponentsPage, SerieDocumentoDeleteDialog, SerieDocumentoUpdatePage } from './serie-documento.page-object';

const expect = chai.expect;

describe('SerieDocumento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serieDocumentoComponentsPage: SerieDocumentoComponentsPage;
  let serieDocumentoUpdatePage: SerieDocumentoUpdatePage;
  let serieDocumentoDeleteDialog: SerieDocumentoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SerieDocumentos', async () => {
    await navBarPage.goToEntity('serie-documento');
    serieDocumentoComponentsPage = new SerieDocumentoComponentsPage();
    await browser.wait(ec.visibilityOf(serieDocumentoComponentsPage.title), 5000);
    expect(await serieDocumentoComponentsPage.getTitle()).to.eq('ensinoApp.serieDocumento.home.title');
  });

  it('should load create SerieDocumento page', async () => {
    await serieDocumentoComponentsPage.clickOnCreateButton();
    serieDocumentoUpdatePage = new SerieDocumentoUpdatePage();
    expect(await serieDocumentoUpdatePage.getPageTitle()).to.eq('ensinoApp.serieDocumento.home.createOrEditLabel');
    await serieDocumentoUpdatePage.cancel();
  });

  it('should create and save SerieDocumentos', async () => {
    const nbButtonsBeforeCreate = await serieDocumentoComponentsPage.countDeleteButtons();

    await serieDocumentoComponentsPage.clickOnCreateButton();
    await promise.all([
      serieDocumentoUpdatePage.setSerieInput('serie'),
      serieDocumentoUpdatePage.setSequenciaInput('5'),
      serieDocumentoUpdatePage.setEntidadeInput('entidade'),
      serieDocumentoUpdatePage.instituicaoEnsinoSelectLastOption()
    ]);
    expect(await serieDocumentoUpdatePage.getSerieInput()).to.eq('serie', 'Expected Serie value to be equals to serie');
    expect(await serieDocumentoUpdatePage.getSequenciaInput()).to.eq('5', 'Expected sequencia value to be equals to 5');
    expect(await serieDocumentoUpdatePage.getEntidadeInput()).to.eq('entidade', 'Expected Entidade value to be equals to entidade');
    await serieDocumentoUpdatePage.save();
    expect(await serieDocumentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await serieDocumentoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SerieDocumento', async () => {
    const nbButtonsBeforeDelete = await serieDocumentoComponentsPage.countDeleteButtons();
    await serieDocumentoComponentsPage.clickOnLastDeleteButton();

    serieDocumentoDeleteDialog = new SerieDocumentoDeleteDialog();
    expect(await serieDocumentoDeleteDialog.getDialogTitle()).to.eq('ensinoApp.serieDocumento.delete.question');
    await serieDocumentoDeleteDialog.clickOnConfirmButton();

    expect(await serieDocumentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
