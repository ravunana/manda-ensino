import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DisciplinaComponentsPage, DisciplinaDeleteDialog, DisciplinaUpdatePage } from './disciplina.page-object';

const expect = chai.expect;

describe('Disciplina e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let disciplinaComponentsPage: DisciplinaComponentsPage;
  let disciplinaUpdatePage: DisciplinaUpdatePage;
  let disciplinaDeleteDialog: DisciplinaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Disciplinas', async () => {
    await navBarPage.goToEntity('disciplina');
    disciplinaComponentsPage = new DisciplinaComponentsPage();
    await browser.wait(ec.visibilityOf(disciplinaComponentsPage.title), 5000);
    expect(await disciplinaComponentsPage.getTitle()).to.eq('ensinoApp.disciplina.home.title');
  });

  it('should load create Disciplina page', async () => {
    await disciplinaComponentsPage.clickOnCreateButton();
    disciplinaUpdatePage = new DisciplinaUpdatePage();
    expect(await disciplinaUpdatePage.getPageTitle()).to.eq('ensinoApp.disciplina.home.createOrEditLabel');
    await disciplinaUpdatePage.cancel();
  });

  it('should create and save Disciplinas', async () => {
    const nbButtonsBeforeCreate = await disciplinaComponentsPage.countDeleteButtons();

    await disciplinaComponentsPage.clickOnCreateButton();
    await promise.all([disciplinaUpdatePage.setNomeInput('nome'), disciplinaUpdatePage.setSiglaInput('sigla')]);
    expect(await disciplinaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await disciplinaUpdatePage.getSiglaInput()).to.eq('sigla', 'Expected Sigla value to be equals to sigla');
    await disciplinaUpdatePage.save();
    expect(await disciplinaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await disciplinaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Disciplina', async () => {
    const nbButtonsBeforeDelete = await disciplinaComponentsPage.countDeleteButtons();
    await disciplinaComponentsPage.clickOnLastDeleteButton();

    disciplinaDeleteDialog = new DisciplinaDeleteDialog();
    expect(await disciplinaDeleteDialog.getDialogTitle()).to.eq('ensinoApp.disciplina.delete.question');
    await disciplinaDeleteDialog.clickOnConfirmButton();

    expect(await disciplinaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
