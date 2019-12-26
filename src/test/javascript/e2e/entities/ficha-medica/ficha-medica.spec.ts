import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FichaMedicaComponentsPage,
  /* FichaMedicaDeleteDialog,
   */ FichaMedicaUpdatePage
} from './ficha-medica.page-object';

const expect = chai.expect;

describe('FichaMedica e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fichaMedicaComponentsPage: FichaMedicaComponentsPage;
  let fichaMedicaUpdatePage: FichaMedicaUpdatePage;
  /* let fichaMedicaDeleteDialog: FichaMedicaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FichaMedicas', async () => {
    await navBarPage.goToEntity('ficha-medica');
    fichaMedicaComponentsPage = new FichaMedicaComponentsPage();
    await browser.wait(ec.visibilityOf(fichaMedicaComponentsPage.title), 5000);
    expect(await fichaMedicaComponentsPage.getTitle()).to.eq('ensinoApp.fichaMedica.home.title');
  });

  it('should load create FichaMedica page', async () => {
    await fichaMedicaComponentsPage.clickOnCreateButton();
    fichaMedicaUpdatePage = new FichaMedicaUpdatePage();
    expect(await fichaMedicaUpdatePage.getPageTitle()).to.eq('ensinoApp.fichaMedica.home.createOrEditLabel');
    await fichaMedicaUpdatePage.cancel();
  });

  /*  it('should create and save FichaMedicas', async () => {
        const nbButtonsBeforeCreate = await fichaMedicaComponentsPage.countDeleteButtons();

        await fichaMedicaComponentsPage.clickOnCreateButton();
        await promise.all([
            fichaMedicaUpdatePage.setGrupoSanguinioInput('grupoSanguinio'),
            fichaMedicaUpdatePage.setAlturaInput('5'),
            fichaMedicaUpdatePage.setPesoInput('5'),
            fichaMedicaUpdatePage.setObservacaoInput('observacao'),
            fichaMedicaUpdatePage.setNomeMedicoInput('nomeMedico'),
            fichaMedicaUpdatePage.setContactoMedicoInput('contactoMedico'),
            fichaMedicaUpdatePage.setComplicacoesSaudeInput('complicacoesSaude'),
            fichaMedicaUpdatePage.alunoSelectLastOption(),
            fichaMedicaUpdatePage.utilizadorSelectLastOption(),
        ]);
        const selectedFazEducacaoFisica = fichaMedicaUpdatePage.getFazEducacaoFisicaInput();
        if (await selectedFazEducacaoFisica.isSelected()) {
            await fichaMedicaUpdatePage.getFazEducacaoFisicaInput().click();
            expect(await fichaMedicaUpdatePage.getFazEducacaoFisicaInput().isSelected(), 'Expected fazEducacaoFisica not to be selected').to.be.false;
        } else {
            await fichaMedicaUpdatePage.getFazEducacaoFisicaInput().click();
            expect(await fichaMedicaUpdatePage.getFazEducacaoFisicaInput().isSelected(), 'Expected fazEducacaoFisica to be selected').to.be.true;
        }
        expect(await fichaMedicaUpdatePage.getGrupoSanguinioInput()).to.eq('grupoSanguinio', 'Expected GrupoSanguinio value to be equals to grupoSanguinio');
        expect(await fichaMedicaUpdatePage.getAlturaInput()).to.eq('5', 'Expected altura value to be equals to 5');
        expect(await fichaMedicaUpdatePage.getPesoInput()).to.eq('5', 'Expected peso value to be equals to 5');
        const selectedAutorizaMedicamento = fichaMedicaUpdatePage.getAutorizaMedicamentoInput();
        if (await selectedAutorizaMedicamento.isSelected()) {
            await fichaMedicaUpdatePage.getAutorizaMedicamentoInput().click();
            expect(await fichaMedicaUpdatePage.getAutorizaMedicamentoInput().isSelected(), 'Expected autorizaMedicamento not to be selected').to.be.false;
        } else {
            await fichaMedicaUpdatePage.getAutorizaMedicamentoInput().click();
            expect(await fichaMedicaUpdatePage.getAutorizaMedicamentoInput().isSelected(), 'Expected autorizaMedicamento to be selected').to.be.true;
        }
        expect(await fichaMedicaUpdatePage.getObservacaoInput()).to.eq('observacao', 'Expected Observacao value to be equals to observacao');
        expect(await fichaMedicaUpdatePage.getNomeMedicoInput()).to.eq('nomeMedico', 'Expected NomeMedico value to be equals to nomeMedico');
        expect(await fichaMedicaUpdatePage.getContactoMedicoInput()).to.eq('contactoMedico', 'Expected ContactoMedico value to be equals to contactoMedico');
        const selectedDesmaioConstante = fichaMedicaUpdatePage.getDesmaioConstanteInput();
        if (await selectedDesmaioConstante.isSelected()) {
            await fichaMedicaUpdatePage.getDesmaioConstanteInput().click();
            expect(await fichaMedicaUpdatePage.getDesmaioConstanteInput().isSelected(), 'Expected desmaioConstante not to be selected').to.be.false;
        } else {
            await fichaMedicaUpdatePage.getDesmaioConstanteInput().click();
            expect(await fichaMedicaUpdatePage.getDesmaioConstanteInput().isSelected(), 'Expected desmaioConstante to be selected').to.be.true;
        }
        expect(await fichaMedicaUpdatePage.getComplicacoesSaudeInput()).to.eq('complicacoesSaude', 'Expected ComplicacoesSaude value to be equals to complicacoesSaude');
        await fichaMedicaUpdatePage.save();
        expect(await fichaMedicaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await fichaMedicaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last FichaMedica', async () => {
        const nbButtonsBeforeDelete = await fichaMedicaComponentsPage.countDeleteButtons();
        await fichaMedicaComponentsPage.clickOnLastDeleteButton();

        fichaMedicaDeleteDialog = new FichaMedicaDeleteDialog();
        expect(await fichaMedicaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.fichaMedica.delete.question');
        await fichaMedicaDeleteDialog.clickOnConfirmButton();

        expect(await fichaMedicaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
