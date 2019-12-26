import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  MatrizCurricularComponentsPage,
  /* MatrizCurricularDeleteDialog,
   */ MatrizCurricularUpdatePage
} from './matriz-curricular.page-object';

const expect = chai.expect;

describe('MatrizCurricular e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let matrizCurricularComponentsPage: MatrizCurricularComponentsPage;
  let matrizCurricularUpdatePage: MatrizCurricularUpdatePage;
  /* let matrizCurricularDeleteDialog: MatrizCurricularDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MatrizCurriculars', async () => {
    await navBarPage.goToEntity('matriz-curricular');
    matrizCurricularComponentsPage = new MatrizCurricularComponentsPage();
    await browser.wait(ec.visibilityOf(matrizCurricularComponentsPage.title), 5000);
    expect(await matrizCurricularComponentsPage.getTitle()).to.eq('ensinoApp.matrizCurricular.home.title');
  });

  it('should load create MatrizCurricular page', async () => {
    await matrizCurricularComponentsPage.clickOnCreateButton();
    matrizCurricularUpdatePage = new MatrizCurricularUpdatePage();
    expect(await matrizCurricularUpdatePage.getPageTitle()).to.eq('ensinoApp.matrizCurricular.home.createOrEditLabel');
    await matrizCurricularUpdatePage.cancel();
  });

  /*  it('should create and save MatrizCurriculars', async () => {
        const nbButtonsBeforeCreate = await matrizCurricularComponentsPage.countDeleteButtons();

        await matrizCurricularComponentsPage.clickOnCreateButton();
        await promise.all([
            matrizCurricularUpdatePage.cursoSelectLastOption(),
        ]);
        await matrizCurricularUpdatePage.save();
        expect(await matrizCurricularUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await matrizCurricularComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last MatrizCurricular', async () => {
        const nbButtonsBeforeDelete = await matrizCurricularComponentsPage.countDeleteButtons();
        await matrizCurricularComponentsPage.clickOnLastDeleteButton();

        matrizCurricularDeleteDialog = new MatrizCurricularDeleteDialog();
        expect(await matrizCurricularDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.matrizCurricular.delete.question');
        await matrizCurricularDeleteDialog.clickOnConfirmButton();

        expect(await matrizCurricularComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
