import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  SituacaoAcademicaComponentsPage,
  /* SituacaoAcademicaDeleteDialog,
   */ SituacaoAcademicaUpdatePage
} from './situacao-academica.page-object';

const expect = chai.expect;

describe('SituacaoAcademica e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let situacaoAcademicaComponentsPage: SituacaoAcademicaComponentsPage;
  let situacaoAcademicaUpdatePage: SituacaoAcademicaUpdatePage;
  /* let situacaoAcademicaDeleteDialog: SituacaoAcademicaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SituacaoAcademicas', async () => {
    await navBarPage.goToEntity('situacao-academica');
    situacaoAcademicaComponentsPage = new SituacaoAcademicaComponentsPage();
    await browser.wait(ec.visibilityOf(situacaoAcademicaComponentsPage.title), 5000);
    expect(await situacaoAcademicaComponentsPage.getTitle()).to.eq('ensinoApp.situacaoAcademica.home.title');
  });

  it('should load create SituacaoAcademica page', async () => {
    await situacaoAcademicaComponentsPage.clickOnCreateButton();
    situacaoAcademicaUpdatePage = new SituacaoAcademicaUpdatePage();
    expect(await situacaoAcademicaUpdatePage.getPageTitle()).to.eq('ensinoApp.situacaoAcademica.home.createOrEditLabel');
    await situacaoAcademicaUpdatePage.cancel();
  });

  /*  it('should create and save SituacaoAcademicas', async () => {
        const nbButtonsBeforeCreate = await situacaoAcademicaComponentsPage.countDeleteButtons();

        await situacaoAcademicaComponentsPage.clickOnCreateButton();
        await promise.all([
            situacaoAcademicaUpdatePage.setAnoLectivoInput('5'),
            situacaoAcademicaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            situacaoAcademicaUpdatePage.setEstadoInput('estado'),
            situacaoAcademicaUpdatePage.setDescricaoInput('descricao'),
            situacaoAcademicaUpdatePage.alunoSelectLastOption(),
            situacaoAcademicaUpdatePage.disciplinaSelectLastOption(),
        ]);
        expect(await situacaoAcademicaUpdatePage.getAnoLectivoInput()).to.eq('5', 'Expected anoLectivo value to be equals to 5');
        expect(await situacaoAcademicaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await situacaoAcademicaUpdatePage.getEstadoInput()).to.eq('estado', 'Expected Estado value to be equals to estado');
        expect(await situacaoAcademicaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        await situacaoAcademicaUpdatePage.save();
        expect(await situacaoAcademicaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await situacaoAcademicaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last SituacaoAcademica', async () => {
        const nbButtonsBeforeDelete = await situacaoAcademicaComponentsPage.countDeleteButtons();
        await situacaoAcademicaComponentsPage.clickOnLastDeleteButton();

        situacaoAcademicaDeleteDialog = new SituacaoAcademicaDeleteDialog();
        expect(await situacaoAcademicaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.situacaoAcademica.delete.question');
        await situacaoAcademicaDeleteDialog.clickOnConfirmButton();

        expect(await situacaoAcademicaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
