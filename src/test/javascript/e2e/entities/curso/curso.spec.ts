import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CursoComponentsPage,
  /* CursoDeleteDialog,
   */ CursoUpdatePage
} from './curso.page-object';

const expect = chai.expect;

describe('Curso e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cursoComponentsPage: CursoComponentsPage;
  let cursoUpdatePage: CursoUpdatePage;
  /* let cursoDeleteDialog: CursoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Cursos', async () => {
    await navBarPage.goToEntity('curso');
    cursoComponentsPage = new CursoComponentsPage();
    await browser.wait(ec.visibilityOf(cursoComponentsPage.title), 5000);
    expect(await cursoComponentsPage.getTitle()).to.eq('ensinoApp.curso.home.title');
  });

  it('should load create Curso page', async () => {
    await cursoComponentsPage.clickOnCreateButton();
    cursoUpdatePage = new CursoUpdatePage();
    expect(await cursoUpdatePage.getPageTitle()).to.eq('ensinoApp.curso.home.createOrEditLabel');
    await cursoUpdatePage.cancel();
  });

  /*  it('should create and save Cursos', async () => {
        const nbButtonsBeforeCreate = await cursoComponentsPage.countDeleteButtons();

        await cursoComponentsPage.clickOnCreateButton();
        await promise.all([
            cursoUpdatePage.setNomeInput('nome'),
            cursoUpdatePage.setSiglaInput('sigla'),
            cursoUpdatePage.setCompetenciasInput('competencias'),
            cursoUpdatePage.areaFormacaoSelectLastOption(),
        ]);
        expect(await cursoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await cursoUpdatePage.getSiglaInput()).to.eq('sigla', 'Expected Sigla value to be equals to sigla');
        expect(await cursoUpdatePage.getCompetenciasInput()).to.eq('competencias', 'Expected Competencias value to be equals to competencias');
        await cursoUpdatePage.save();
        expect(await cursoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await cursoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Curso', async () => {
        const nbButtonsBeforeDelete = await cursoComponentsPage.countDeleteButtons();
        await cursoComponentsPage.clickOnLastDeleteButton();

        cursoDeleteDialog = new CursoDeleteDialog();
        expect(await cursoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.curso.delete.question');
        await cursoDeleteDialog.clickOnConfirmButton();

        expect(await cursoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
