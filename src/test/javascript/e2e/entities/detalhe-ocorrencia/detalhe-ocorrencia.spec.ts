import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DetalheOcorrenciaComponentsPage,
  /* DetalheOcorrenciaDeleteDialog,
   */ DetalheOcorrenciaUpdatePage
} from './detalhe-ocorrencia.page-object';

const expect = chai.expect;

describe('DetalheOcorrencia e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let detalheOcorrenciaComponentsPage: DetalheOcorrenciaComponentsPage;
  let detalheOcorrenciaUpdatePage: DetalheOcorrenciaUpdatePage;
  /* let detalheOcorrenciaDeleteDialog: DetalheOcorrenciaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DetalheOcorrencias', async () => {
    await navBarPage.goToEntity('detalhe-ocorrencia');
    detalheOcorrenciaComponentsPage = new DetalheOcorrenciaComponentsPage();
    await browser.wait(ec.visibilityOf(detalheOcorrenciaComponentsPage.title), 5000);
    expect(await detalheOcorrenciaComponentsPage.getTitle()).to.eq('ensinoApp.detalheOcorrencia.home.title');
  });

  it('should load create DetalheOcorrencia page', async () => {
    await detalheOcorrenciaComponentsPage.clickOnCreateButton();
    detalheOcorrenciaUpdatePage = new DetalheOcorrenciaUpdatePage();
    expect(await detalheOcorrenciaUpdatePage.getPageTitle()).to.eq('ensinoApp.detalheOcorrencia.home.createOrEditLabel');
    await detalheOcorrenciaUpdatePage.cancel();
  });

  /*  it('should create and save DetalheOcorrencias', async () => {
        const nbButtonsBeforeCreate = await detalheOcorrenciaComponentsPage.countDeleteButtons();

        await detalheOcorrenciaComponentsPage.clickOnCreateButton();
        await promise.all([
            detalheOcorrenciaUpdatePage.setDeInput('2000-12-31'),
            detalheOcorrenciaUpdatePage.setAteInput('2000-12-31'),
            detalheOcorrenciaUpdatePage.setMotivoInput('motivo'),
            detalheOcorrenciaUpdatePage.ocorrenciaSelectLastOption(),
        ]);
        expect(await detalheOcorrenciaUpdatePage.getDeInput()).to.eq('2000-12-31', 'Expected de value to be equals to 2000-12-31');
        expect(await detalheOcorrenciaUpdatePage.getAteInput()).to.eq('2000-12-31', 'Expected ate value to be equals to 2000-12-31');
        expect(await detalheOcorrenciaUpdatePage.getMotivoInput()).to.eq('motivo', 'Expected Motivo value to be equals to motivo');
        await detalheOcorrenciaUpdatePage.save();
        expect(await detalheOcorrenciaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await detalheOcorrenciaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last DetalheOcorrencia', async () => {
        const nbButtonsBeforeDelete = await detalheOcorrenciaComponentsPage.countDeleteButtons();
        await detalheOcorrenciaComponentsPage.clickOnLastDeleteButton();

        detalheOcorrenciaDeleteDialog = new DetalheOcorrenciaDeleteDialog();
        expect(await detalheOcorrenciaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.detalheOcorrencia.delete.question');
        await detalheOcorrenciaDeleteDialog.clickOnConfirmButton();

        expect(await detalheOcorrenciaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
