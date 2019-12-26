import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  NotaComponentsPage,
  /* NotaDeleteDialog,
   */ NotaUpdatePage
} from './nota.page-object';

const expect = chai.expect;

describe('Nota e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notaComponentsPage: NotaComponentsPage;
  let notaUpdatePage: NotaUpdatePage;
  /* let notaDeleteDialog: NotaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Notas', async () => {
    await navBarPage.goToEntity('nota');
    notaComponentsPage = new NotaComponentsPage();
    await browser.wait(ec.visibilityOf(notaComponentsPage.title), 5000);
    expect(await notaComponentsPage.getTitle()).to.eq('ensinoApp.nota.home.title');
  });

  it('should load create Nota page', async () => {
    await notaComponentsPage.clickOnCreateButton();
    notaUpdatePage = new NotaUpdatePage();
    expect(await notaUpdatePage.getPageTitle()).to.eq('ensinoApp.nota.home.createOrEditLabel');
    await notaUpdatePage.cancel();
  });

  /*  it('should create and save Notas', async () => {
        const nbButtonsBeforeCreate = await notaComponentsPage.countDeleteButtons();

        await notaComponentsPage.clickOnCreateButton();
        await promise.all([
            notaUpdatePage.setValorInput('5'),
            notaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            notaUpdatePage.setAnoLectivoInput('2000-12-31'),
            notaUpdatePage.setPeriodoLectivoInput('periodoLectivo'),
            notaUpdatePage.utilizadorSelectLastOption(),
            notaUpdatePage.disciplinaSelectLastOption(),
            notaUpdatePage.turmaSelectLastOption(),
            notaUpdatePage.categoriaAvaliacaoSelectLastOption(),
            notaUpdatePage.matriculaSelectLastOption(),
        ]);
        expect(await notaUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await notaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await notaUpdatePage.getAnoLectivoInput()).to.eq('2000-12-31', 'Expected anoLectivo value to be equals to 2000-12-31');
        expect(await notaUpdatePage.getPeriodoLectivoInput()).to.eq('periodoLectivo', 'Expected PeriodoLectivo value to be equals to periodoLectivo');
        await notaUpdatePage.save();
        expect(await notaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await notaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Nota', async () => {
        const nbButtonsBeforeDelete = await notaComponentsPage.countDeleteButtons();
        await notaComponentsPage.clickOnLastDeleteButton();

        notaDeleteDialog = new NotaDeleteDialog();
        expect(await notaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.nota.delete.question');
        await notaDeleteDialog.clickOnConfirmButton();

        expect(await notaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
