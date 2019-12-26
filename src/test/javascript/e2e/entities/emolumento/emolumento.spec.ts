import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmolumentoComponentsPage, EmolumentoDeleteDialog, EmolumentoUpdatePage } from './emolumento.page-object';

const expect = chai.expect;

describe('Emolumento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emolumentoComponentsPage: EmolumentoComponentsPage;
  let emolumentoUpdatePage: EmolumentoUpdatePage;
  let emolumentoDeleteDialog: EmolumentoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Emolumentos', async () => {
    await navBarPage.goToEntity('emolumento');
    emolumentoComponentsPage = new EmolumentoComponentsPage();
    await browser.wait(ec.visibilityOf(emolumentoComponentsPage.title), 5000);
    expect(await emolumentoComponentsPage.getTitle()).to.eq('ensinoApp.emolumento.home.title');
  });

  it('should load create Emolumento page', async () => {
    await emolumentoComponentsPage.clickOnCreateButton();
    emolumentoUpdatePage = new EmolumentoUpdatePage();
    expect(await emolumentoUpdatePage.getPageTitle()).to.eq('ensinoApp.emolumento.home.createOrEditLabel');
    await emolumentoUpdatePage.cancel();
  });

  it('should create and save Emolumentos', async () => {
    const nbButtonsBeforeCreate = await emolumentoComponentsPage.countDeleteButtons();

    await emolumentoComponentsPage.clickOnCreateButton();
    await promise.all([
      emolumentoUpdatePage.setNomeInput('nome'),
      emolumentoUpdatePage.setValorInput('5'),
      emolumentoUpdatePage.setValorMultaInput('5'),
      emolumentoUpdatePage.setTempoMultaInput('5'),
      emolumentoUpdatePage.setQuantidadeInput('5'),
      emolumentoUpdatePage.cursoSelectLastOption(),
      emolumentoUpdatePage.classeSelectLastOption()
    ]);
    expect(await emolumentoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await emolumentoUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
    expect(await emolumentoUpdatePage.getValorMultaInput()).to.eq('5', 'Expected valorMulta value to be equals to 5');
    expect(await emolumentoUpdatePage.getTempoMultaInput()).to.eq('5', 'Expected tempoMulta value to be equals to 5');
    expect(await emolumentoUpdatePage.getQuantidadeInput()).to.eq('5', 'Expected quantidade value to be equals to 5');
    await emolumentoUpdatePage.save();
    expect(await emolumentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emolumentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Emolumento', async () => {
    const nbButtonsBeforeDelete = await emolumentoComponentsPage.countDeleteButtons();
    await emolumentoComponentsPage.clickOnLastDeleteButton();

    emolumentoDeleteDialog = new EmolumentoDeleteDialog();
    expect(await emolumentoDeleteDialog.getDialogTitle()).to.eq('ensinoApp.emolumento.delete.question');
    await emolumentoDeleteDialog.clickOnConfirmButton();

    expect(await emolumentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
