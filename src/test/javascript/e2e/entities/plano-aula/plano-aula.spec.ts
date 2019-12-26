import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PlanoAulaComponentsPage,
  /* PlanoAulaDeleteDialog,
   */ PlanoAulaUpdatePage
} from './plano-aula.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('PlanoAula e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let planoAulaComponentsPage: PlanoAulaComponentsPage;
  let planoAulaUpdatePage: PlanoAulaUpdatePage;
  /* let planoAulaDeleteDialog: PlanoAulaDeleteDialog; */
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

  it('should load PlanoAulas', async () => {
    await navBarPage.goToEntity('plano-aula');
    planoAulaComponentsPage = new PlanoAulaComponentsPage();
    await browser.wait(ec.visibilityOf(planoAulaComponentsPage.title), 5000);
    expect(await planoAulaComponentsPage.getTitle()).to.eq('ensinoApp.planoAula.home.title');
  });

  it('should load create PlanoAula page', async () => {
    await planoAulaComponentsPage.clickOnCreateButton();
    planoAulaUpdatePage = new PlanoAulaUpdatePage();
    expect(await planoAulaUpdatePage.getPageTitle()).to.eq('ensinoApp.planoAula.home.createOrEditLabel');
    await planoAulaUpdatePage.cancel();
  });

  /*  it('should create and save PlanoAulas', async () => {
        const nbButtonsBeforeCreate = await planoAulaComponentsPage.countDeleteButtons();

        await planoAulaComponentsPage.clickOnCreateButton();
        await promise.all([
            planoAulaUpdatePage.setObjectivoGeralInput('objectivoGeral'),
            planoAulaUpdatePage.setObjectivoEspecificoInput('objectivoEspecifico'),
            planoAulaUpdatePage.setConteudoInput('conteudo'),
            planoAulaUpdatePage.setEstrategiaInput('estrategia'),
            planoAulaUpdatePage.setActividadesInput('actividades'),
            planoAulaUpdatePage.setTempoInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            planoAulaUpdatePage.setRecursosEnsinoInput('recursosEnsino'),
            planoAulaUpdatePage.setAvaliacaoInput('avaliacao'),
            planoAulaUpdatePage.setObservacaoInput('observacao'),
            planoAulaUpdatePage.setBibliografiaInput('bibliografia'),
            planoAulaUpdatePage.setPerfilEntradaInput('perfilEntrada'),
            planoAulaUpdatePage.setPerfilSaidaInput('perfilSaida'),
            planoAulaUpdatePage.setAnexo1Input(absolutePath),
            planoAulaUpdatePage.setAnexo2Input(absolutePath),
            planoAulaUpdatePage.setAnexo3Input(absolutePath),
            planoAulaUpdatePage.utilizadorSelectLastOption(),
            // planoAulaUpdatePage.turmaSelectLastOption(),
            planoAulaUpdatePage.disciplinaSelectLastOption(),
            planoAulaUpdatePage.dossificacaoSelectLastOption(),
        ]);
        expect(await planoAulaUpdatePage.getObjectivoGeralInput()).to.eq('objectivoGeral', 'Expected ObjectivoGeral value to be equals to objectivoGeral');
        expect(await planoAulaUpdatePage.getObjectivoEspecificoInput()).to.eq('objectivoEspecifico', 'Expected ObjectivoEspecifico value to be equals to objectivoEspecifico');
        expect(await planoAulaUpdatePage.getConteudoInput()).to.eq('conteudo', 'Expected Conteudo value to be equals to conteudo');
        expect(await planoAulaUpdatePage.getEstrategiaInput()).to.eq('estrategia', 'Expected Estrategia value to be equals to estrategia');
        expect(await planoAulaUpdatePage.getActividadesInput()).to.eq('actividades', 'Expected Actividades value to be equals to actividades');
        expect(await planoAulaUpdatePage.getTempoInput()).to.contain('2001-01-01T02:30', 'Expected tempo value to be equals to 2000-12-31');
        expect(await planoAulaUpdatePage.getRecursosEnsinoInput()).to.eq('recursosEnsino', 'Expected RecursosEnsino value to be equals to recursosEnsino');
        expect(await planoAulaUpdatePage.getAvaliacaoInput()).to.eq('avaliacao', 'Expected Avaliacao value to be equals to avaliacao');
        expect(await planoAulaUpdatePage.getObservacaoInput()).to.eq('observacao', 'Expected Observacao value to be equals to observacao');
        expect(await planoAulaUpdatePage.getBibliografiaInput()).to.eq('bibliografia', 'Expected Bibliografia value to be equals to bibliografia');
        expect(await planoAulaUpdatePage.getPerfilEntradaInput()).to.eq('perfilEntrada', 'Expected PerfilEntrada value to be equals to perfilEntrada');
        expect(await planoAulaUpdatePage.getPerfilSaidaInput()).to.eq('perfilSaida', 'Expected PerfilSaida value to be equals to perfilSaida');
        expect(await planoAulaUpdatePage.getAnexo1Input()).to.endsWith(fileNameToUpload, 'Expected Anexo1 value to be end with ' + fileNameToUpload);
        expect(await planoAulaUpdatePage.getAnexo2Input()).to.endsWith(fileNameToUpload, 'Expected Anexo2 value to be end with ' + fileNameToUpload);
        expect(await planoAulaUpdatePage.getAnexo3Input()).to.endsWith(fileNameToUpload, 'Expected Anexo3 value to be end with ' + fileNameToUpload);
        await planoAulaUpdatePage.save();
        expect(await planoAulaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await planoAulaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last PlanoAula', async () => {
        const nbButtonsBeforeDelete = await planoAulaComponentsPage.countDeleteButtons();
        await planoAulaComponentsPage.clickOnLastDeleteButton();

        planoAulaDeleteDialog = new PlanoAulaDeleteDialog();
        expect(await planoAulaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.planoAula.delete.question');
        await planoAulaDeleteDialog.clickOnConfirmButton();

        expect(await planoAulaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
