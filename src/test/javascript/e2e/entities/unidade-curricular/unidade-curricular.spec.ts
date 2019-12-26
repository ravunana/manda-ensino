import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UnidadeCurricularComponentsPage,
  /* UnidadeCurricularDeleteDialog,
   */ UnidadeCurricularUpdatePage
} from './unidade-curricular.page-object';

const expect = chai.expect;

describe('UnidadeCurricular e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let unidadeCurricularComponentsPage: UnidadeCurricularComponentsPage;
  let unidadeCurricularUpdatePage: UnidadeCurricularUpdatePage;
  /* let unidadeCurricularDeleteDialog: UnidadeCurricularDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UnidadeCurriculars', async () => {
    await navBarPage.goToEntity('unidade-curricular');
    unidadeCurricularComponentsPage = new UnidadeCurricularComponentsPage();
    await browser.wait(ec.visibilityOf(unidadeCurricularComponentsPage.title), 5000);
    expect(await unidadeCurricularComponentsPage.getTitle()).to.eq('ensinoApp.unidadeCurricular.home.title');
  });

  it('should load create UnidadeCurricular page', async () => {
    await unidadeCurricularComponentsPage.clickOnCreateButton();
    unidadeCurricularUpdatePage = new UnidadeCurricularUpdatePage();
    expect(await unidadeCurricularUpdatePage.getPageTitle()).to.eq('ensinoApp.unidadeCurricular.home.createOrEditLabel');
    await unidadeCurricularUpdatePage.cancel();
  });

  /*  it('should create and save UnidadeCurriculars', async () => {
        const nbButtonsBeforeCreate = await unidadeCurricularComponentsPage.countDeleteButtons();

        await unidadeCurricularComponentsPage.clickOnCreateButton();
        await promise.all([
            unidadeCurricularUpdatePage.setDescricaoInput('descricao'),
            unidadeCurricularUpdatePage.setUnidadeInput('unidade'),
            unidadeCurricularUpdatePage.setNumeroInput('5'),
            unidadeCurricularUpdatePage.disciplinaSelectLastOption(),
            unidadeCurricularUpdatePage.classeSelectLastOption(),
            unidadeCurricularUpdatePage.herarquiaSelectLastOption(),
        ]);
        expect(await unidadeCurricularUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await unidadeCurricularUpdatePage.getUnidadeInput()).to.eq('unidade', 'Expected Unidade value to be equals to unidade');
        expect(await unidadeCurricularUpdatePage.getNumeroInput()).to.eq('5', 'Expected numero value to be equals to 5');
        await unidadeCurricularUpdatePage.save();
        expect(await unidadeCurricularUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await unidadeCurricularComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last UnidadeCurricular', async () => {
        const nbButtonsBeforeDelete = await unidadeCurricularComponentsPage.countDeleteButtons();
        await unidadeCurricularComponentsPage.clickOnLastDeleteButton();

        unidadeCurricularDeleteDialog = new UnidadeCurricularDeleteDialog();
        expect(await unidadeCurricularDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.unidadeCurricular.delete.question');
        await unidadeCurricularDeleteDialog.clickOnConfirmButton();

        expect(await unidadeCurricularComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
