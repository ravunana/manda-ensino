import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  MatriculaComponentsPage,
  /* MatriculaDeleteDialog,
   */ MatriculaUpdatePage
} from './matricula.page-object';

const expect = chai.expect;

describe('Matricula e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let matriculaComponentsPage: MatriculaComponentsPage;
  let matriculaUpdatePage: MatriculaUpdatePage;
  /* let matriculaDeleteDialog: MatriculaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Matriculas', async () => {
    await navBarPage.goToEntity('matricula');
    matriculaComponentsPage = new MatriculaComponentsPage();
    await browser.wait(ec.visibilityOf(matriculaComponentsPage.title), 5000);
    expect(await matriculaComponentsPage.getTitle()).to.eq('ensinoApp.matricula.home.title');
  });

  it('should load create Matricula page', async () => {
    await matriculaComponentsPage.clickOnCreateButton();
    matriculaUpdatePage = new MatriculaUpdatePage();
    expect(await matriculaUpdatePage.getPageTitle()).to.eq('ensinoApp.matricula.home.createOrEditLabel');
    await matriculaUpdatePage.cancel();
  });

  /*  it('should create and save Matriculas', async () => {
        const nbButtonsBeforeCreate = await matriculaComponentsPage.countDeleteButtons();

        await matriculaComponentsPage.clickOnCreateButton();
        await promise.all([
            matriculaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            matriculaUpdatePage.setNumeroInput('5'),
            matriculaUpdatePage.setObservacaoInput('observacao'),
            matriculaUpdatePage.setAnoLectivoInput('5'),
            matriculaUpdatePage.setPeridoLectivoInput('peridoLectivo'),
            matriculaUpdatePage.utilizadorSelectLastOption(),
            matriculaUpdatePage.alunoSelectLastOption(),
            matriculaUpdatePage.confirmacaoSelectLastOption(),
            matriculaUpdatePage.categoriaSelectLastOption(),
            matriculaUpdatePage.turmaSelectLastOption(),
        ]);
        expect(await matriculaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await matriculaUpdatePage.getNumeroInput()).to.eq('5', 'Expected numero value to be equals to 5');
        expect(await matriculaUpdatePage.getObservacaoInput()).to.eq('observacao', 'Expected Observacao value to be equals to observacao');
        expect(await matriculaUpdatePage.getAnoLectivoInput()).to.eq('5', 'Expected anoLectivo value to be equals to 5');
        expect(await matriculaUpdatePage.getPeridoLectivoInput()).to.eq('peridoLectivo', 'Expected PeridoLectivo value to be equals to peridoLectivo');
        await matriculaUpdatePage.save();
        expect(await matriculaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await matriculaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Matricula', async () => {
        const nbButtonsBeforeDelete = await matriculaComponentsPage.countDeleteButtons();
        await matriculaComponentsPage.clickOnLastDeleteButton();

        matriculaDeleteDialog = new MatriculaDeleteDialog();
        expect(await matriculaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.matricula.delete.question');
        await matriculaDeleteDialog.clickOnConfirmButton();

        expect(await matriculaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
