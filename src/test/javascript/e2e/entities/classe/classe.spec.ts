import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ClasseComponentsPage, ClasseDeleteDialog, ClasseUpdatePage } from './classe.page-object';

const expect = chai.expect;

describe('Classe e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let classeComponentsPage: ClasseComponentsPage;
  let classeUpdatePage: ClasseUpdatePage;
  let classeDeleteDialog: ClasseDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Classes', async () => {
    await navBarPage.goToEntity('classe');
    classeComponentsPage = new ClasseComponentsPage();
    await browser.wait(ec.visibilityOf(classeComponentsPage.title), 5000);
    expect(await classeComponentsPage.getTitle()).to.eq('ensinoApp.classe.home.title');
  });

  it('should load create Classe page', async () => {
    await classeComponentsPage.clickOnCreateButton();
    classeUpdatePage = new ClasseUpdatePage();
    expect(await classeUpdatePage.getPageTitle()).to.eq('ensinoApp.classe.home.createOrEditLabel');
    await classeUpdatePage.cancel();
  });

  it('should create and save Classes', async () => {
    const nbButtonsBeforeCreate = await classeComponentsPage.countDeleteButtons();

    await classeComponentsPage.clickOnCreateButton();
    await promise.all([classeUpdatePage.setDescricaoInput('5'), classeUpdatePage.setTipoEnsinoInput('tipoEnsino')]);
    expect(await classeUpdatePage.getDescricaoInput()).to.eq('5', 'Expected descricao value to be equals to 5');
    expect(await classeUpdatePage.getTipoEnsinoInput()).to.eq('tipoEnsino', 'Expected TipoEnsino value to be equals to tipoEnsino');
    await classeUpdatePage.save();
    expect(await classeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await classeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Classe', async () => {
    const nbButtonsBeforeDelete = await classeComponentsPage.countDeleteButtons();
    await classeComponentsPage.clickOnLastDeleteButton();

    classeDeleteDialog = new ClasseDeleteDialog();
    expect(await classeDeleteDialog.getDialogTitle()).to.eq('ensinoApp.classe.delete.question');
    await classeDeleteDialog.clickOnConfirmButton();

    expect(await classeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
