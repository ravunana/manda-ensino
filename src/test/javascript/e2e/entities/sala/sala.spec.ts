import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SalaComponentsPage, SalaDeleteDialog, SalaUpdatePage } from './sala.page-object';

const expect = chai.expect;

describe('Sala e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let salaComponentsPage: SalaComponentsPage;
  let salaUpdatePage: SalaUpdatePage;
  let salaDeleteDialog: SalaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Salas', async () => {
    await navBarPage.goToEntity('sala');
    salaComponentsPage = new SalaComponentsPage();
    await browser.wait(ec.visibilityOf(salaComponentsPage.title), 5000);
    expect(await salaComponentsPage.getTitle()).to.eq('ensinoApp.sala.home.title');
  });

  it('should load create Sala page', async () => {
    await salaComponentsPage.clickOnCreateButton();
    salaUpdatePage = new SalaUpdatePage();
    expect(await salaUpdatePage.getPageTitle()).to.eq('ensinoApp.sala.home.createOrEditLabel');
    await salaUpdatePage.cancel();
  });

  it('should create and save Salas', async () => {
    const nbButtonsBeforeCreate = await salaComponentsPage.countDeleteButtons();

    await salaComponentsPage.clickOnCreateButton();
    await promise.all([
      salaUpdatePage.setNumeroInput('5'),
      salaUpdatePage.setDescricaoInput('descricao'),
      salaUpdatePage.setLotacaoInput('5')
    ]);
    expect(await salaUpdatePage.getNumeroInput()).to.eq('5', 'Expected numero value to be equals to 5');
    expect(await salaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await salaUpdatePage.getLotacaoInput()).to.eq('5', 'Expected lotacao value to be equals to 5');
    await salaUpdatePage.save();
    expect(await salaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await salaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Sala', async () => {
    const nbButtonsBeforeDelete = await salaComponentsPage.countDeleteButtons();
    await salaComponentsPage.clickOnLastDeleteButton();

    salaDeleteDialog = new SalaDeleteDialog();
    expect(await salaDeleteDialog.getDialogTitle()).to.eq('ensinoApp.sala.delete.question');
    await salaDeleteDialog.clickOnConfirmButton();

    expect(await salaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
