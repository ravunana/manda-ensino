import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  OcorrenciaComponentsPage,
  /* OcorrenciaDeleteDialog,
   */ OcorrenciaUpdatePage
} from './ocorrencia.page-object';

const expect = chai.expect;

describe('Ocorrencia e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ocorrenciaComponentsPage: OcorrenciaComponentsPage;
  let ocorrenciaUpdatePage: OcorrenciaUpdatePage;
  /* let ocorrenciaDeleteDialog: OcorrenciaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Ocorrencias', async () => {
    await navBarPage.goToEntity('ocorrencia');
    ocorrenciaComponentsPage = new OcorrenciaComponentsPage();
    await browser.wait(ec.visibilityOf(ocorrenciaComponentsPage.title), 5000);
    expect(await ocorrenciaComponentsPage.getTitle()).to.eq('ensinoApp.ocorrencia.home.title');
  });

  it('should load create Ocorrencia page', async () => {
    await ocorrenciaComponentsPage.clickOnCreateButton();
    ocorrenciaUpdatePage = new OcorrenciaUpdatePage();
    expect(await ocorrenciaUpdatePage.getPageTitle()).to.eq('ensinoApp.ocorrencia.home.createOrEditLabel');
    await ocorrenciaUpdatePage.cancel();
  });

  /*  it('should create and save Ocorrencias', async () => {
        const nbButtonsBeforeCreate = await ocorrenciaComponentsPage.countDeleteButtons();

        await ocorrenciaComponentsPage.clickOnCreateButton();
        await promise.all([
            ocorrenciaUpdatePage.setTipoInput('tipo'),
            ocorrenciaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            ocorrenciaUpdatePage.setNumeroInput('numero'),
            ocorrenciaUpdatePage.utilizadorSelectLastOption(),
            ocorrenciaUpdatePage.matriculaSelectLastOption(),
        ]);
        expect(await ocorrenciaUpdatePage.getTipoInput()).to.eq('tipo', 'Expected Tipo value to be equals to tipo');
        expect(await ocorrenciaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await ocorrenciaUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        const selectedReportarEncarregado = ocorrenciaUpdatePage.getReportarEncarregadoInput();
        if (await selectedReportarEncarregado.isSelected()) {
            await ocorrenciaUpdatePage.getReportarEncarregadoInput().click();
            expect(await ocorrenciaUpdatePage.getReportarEncarregadoInput().isSelected(), 'Expected reportarEncarregado not to be selected').to.be.false;
        } else {
            await ocorrenciaUpdatePage.getReportarEncarregadoInput().click();
            expect(await ocorrenciaUpdatePage.getReportarEncarregadoInput().isSelected(), 'Expected reportarEncarregado to be selected').to.be.true;
        }
        await ocorrenciaUpdatePage.save();
        expect(await ocorrenciaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await ocorrenciaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Ocorrencia', async () => {
        const nbButtonsBeforeDelete = await ocorrenciaComponentsPage.countDeleteButtons();
        await ocorrenciaComponentsPage.clickOnLastDeleteButton();

        ocorrenciaDeleteDialog = new OcorrenciaDeleteDialog();
        expect(await ocorrenciaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.ocorrencia.delete.question');
        await ocorrenciaDeleteDialog.clickOnConfirmButton();

        expect(await ocorrenciaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
