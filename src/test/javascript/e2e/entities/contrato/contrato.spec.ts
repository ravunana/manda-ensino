import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContratoComponentsPage,
  /* ContratoDeleteDialog,
   */ ContratoUpdatePage
} from './contrato.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Contrato e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contratoComponentsPage: ContratoComponentsPage;
  let contratoUpdatePage: ContratoUpdatePage;
  /* let contratoDeleteDialog: ContratoDeleteDialog; */
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Contratoes', async () => {
    await navBarPage.goToEntity('contrato');
    contratoComponentsPage = new ContratoComponentsPage();
    await browser.wait(ec.visibilityOf(contratoComponentsPage.title), 5000);
    expect(await contratoComponentsPage.getTitle()).to.eq('ensinoApp.contrato.home.title');
  });

  it('should load create Contrato page', async () => {
    await contratoComponentsPage.clickOnCreateButton();
    contratoUpdatePage = new ContratoUpdatePage();
    expect(await contratoUpdatePage.getPageTitle()).to.eq('ensinoApp.contrato.home.createOrEditLabel');
    await contratoUpdatePage.cancel();
  });

  /*  it('should create and save Contratoes', async () => {
        const nbButtonsBeforeCreate = await contratoComponentsPage.countDeleteButtons();

        await contratoComponentsPage.clickOnCreateButton();
        await promise.all([
            contratoUpdatePage.setDeInput('2000-12-31'),
            contratoUpdatePage.setAteInput('2000-12-31'),
            contratoUpdatePage.setContratoInput('contrato'),
            contratoUpdatePage.setObservacaoInput('observacao'),
            contratoUpdatePage.setTermosInput(absolutePath),
            contratoUpdatePage.alunoSelectLastOption(),
        ]);
        expect(await contratoUpdatePage.getDeInput()).to.eq('2000-12-31', 'Expected de value to be equals to 2000-12-31');
        expect(await contratoUpdatePage.getAteInput()).to.eq('2000-12-31', 'Expected ate value to be equals to 2000-12-31');
        expect(await contratoUpdatePage.getContratoInput()).to.eq('contrato', 'Expected Contrato value to be equals to contrato');
        const selectedAceitaTermos = contratoUpdatePage.getAceitaTermosInput();
        if (await selectedAceitaTermos.isSelected()) {
            await contratoUpdatePage.getAceitaTermosInput().click();
            expect(await contratoUpdatePage.getAceitaTermosInput().isSelected(), 'Expected aceitaTermos not to be selected').to.be.false;
        } else {
            await contratoUpdatePage.getAceitaTermosInput().click();
            expect(await contratoUpdatePage.getAceitaTermosInput().isSelected(), 'Expected aceitaTermos to be selected').to.be.true;
        }
        expect(await contratoUpdatePage.getObservacaoInput()).to.eq('observacao', 'Expected Observacao value to be equals to observacao');
        expect(await contratoUpdatePage.getTermosInput()).to.endsWith(fileNameToUpload, 'Expected Termos value to be end with ' + fileNameToUpload);
        const selectedEmVigor = contratoUpdatePage.getEmVigorInput();
        if (await selectedEmVigor.isSelected()) {
            await contratoUpdatePage.getEmVigorInput().click();
            expect(await contratoUpdatePage.getEmVigorInput().isSelected(), 'Expected emVigor not to be selected').to.be.false;
        } else {
            await contratoUpdatePage.getEmVigorInput().click();
            expect(await contratoUpdatePage.getEmVigorInput().isSelected(), 'Expected emVigor to be selected').to.be.true;
        }
        await contratoUpdatePage.save();
        expect(await contratoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await contratoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Contrato', async () => {
        const nbButtonsBeforeDelete = await contratoComponentsPage.countDeleteButtons();
        await contratoComponentsPage.clickOnLastDeleteButton();

        contratoDeleteDialog = new ContratoDeleteDialog();
        expect(await contratoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.contrato.delete.question');
        await contratoDeleteDialog.clickOnConfirmButton();

        expect(await contratoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
