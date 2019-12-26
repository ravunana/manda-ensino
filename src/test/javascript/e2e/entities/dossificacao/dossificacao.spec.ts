import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DossificacaoComponentsPage,
  /* DossificacaoDeleteDialog,
   */ DossificacaoUpdatePage
} from './dossificacao.page-object';

const expect = chai.expect;

describe('Dossificacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dossificacaoComponentsPage: DossificacaoComponentsPage;
  let dossificacaoUpdatePage: DossificacaoUpdatePage;
  /* let dossificacaoDeleteDialog: DossificacaoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Dossificacaos', async () => {
    await navBarPage.goToEntity('dossificacao');
    dossificacaoComponentsPage = new DossificacaoComponentsPage();
    await browser.wait(ec.visibilityOf(dossificacaoComponentsPage.title), 5000);
    expect(await dossificacaoComponentsPage.getTitle()).to.eq('ensinoApp.dossificacao.home.title');
  });

  it('should load create Dossificacao page', async () => {
    await dossificacaoComponentsPage.clickOnCreateButton();
    dossificacaoUpdatePage = new DossificacaoUpdatePage();
    expect(await dossificacaoUpdatePage.getPageTitle()).to.eq('ensinoApp.dossificacao.home.createOrEditLabel');
    await dossificacaoUpdatePage.cancel();
  });

  /*  it('should create and save Dossificacaos', async () => {
        const nbButtonsBeforeCreate = await dossificacaoComponentsPage.countDeleteButtons();

        await dossificacaoComponentsPage.clickOnCreateButton();
        await promise.all([
            dossificacaoUpdatePage.setPeriodoLectivoInput('periodoLectivo'),
            dossificacaoUpdatePage.setAnoLectivoInput('2000-12-31'),
            dossificacaoUpdatePage.setObjectivoGeralInput('objectivoGeral'),
            dossificacaoUpdatePage.setObjectivoEspecificoInput('objectivoEspecifico'),
            dossificacaoUpdatePage.setSemanaLectivaInput('5'),
            dossificacaoUpdatePage.setDeInput('2000-12-31'),
            dossificacaoUpdatePage.setAteInput('2000-12-31'),
            dossificacaoUpdatePage.setConteudoInput('conteudo'),
            dossificacaoUpdatePage.setProcedimentoEnsinoInput('procedimentoEnsino'),
            dossificacaoUpdatePage.setRecursosDidaticoInput('recursosDidatico'),
            dossificacaoUpdatePage.setTempoAulaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            dossificacaoUpdatePage.setFormaAvaliacaoInput('formaAvaliacao'),
            // dossificacaoUpdatePage.cursoSelectLastOption(),
            // dossificacaoUpdatePage.classeSelectLastOption(),
            dossificacaoUpdatePage.disciplinaSelectLastOption(),
        ]);
        expect(await dossificacaoUpdatePage.getPeriodoLectivoInput()).to.eq('periodoLectivo', 'Expected PeriodoLectivo value to be equals to periodoLectivo');
        expect(await dossificacaoUpdatePage.getAnoLectivoInput()).to.eq('2000-12-31', 'Expected anoLectivo value to be equals to 2000-12-31');
        expect(await dossificacaoUpdatePage.getObjectivoGeralInput()).to.eq('objectivoGeral', 'Expected ObjectivoGeral value to be equals to objectivoGeral');
        expect(await dossificacaoUpdatePage.getObjectivoEspecificoInput()).to.eq('objectivoEspecifico', 'Expected ObjectivoEspecifico value to be equals to objectivoEspecifico');
        expect(await dossificacaoUpdatePage.getSemanaLectivaInput()).to.eq('5', 'Expected semanaLectiva value to be equals to 5');
        expect(await dossificacaoUpdatePage.getDeInput()).to.eq('2000-12-31', 'Expected de value to be equals to 2000-12-31');
        expect(await dossificacaoUpdatePage.getAteInput()).to.eq('2000-12-31', 'Expected ate value to be equals to 2000-12-31');
        expect(await dossificacaoUpdatePage.getConteudoInput()).to.eq('conteudo', 'Expected Conteudo value to be equals to conteudo');
        expect(await dossificacaoUpdatePage.getProcedimentoEnsinoInput()).to.eq('procedimentoEnsino', 'Expected ProcedimentoEnsino value to be equals to procedimentoEnsino');
        expect(await dossificacaoUpdatePage.getRecursosDidaticoInput()).to.eq('recursosDidatico', 'Expected RecursosDidatico value to be equals to recursosDidatico');
        expect(await dossificacaoUpdatePage.getTempoAulaInput()).to.contain('2001-01-01T02:30', 'Expected tempoAula value to be equals to 2000-12-31');
        expect(await dossificacaoUpdatePage.getFormaAvaliacaoInput()).to.eq('formaAvaliacao', 'Expected FormaAvaliacao value to be equals to formaAvaliacao');
        await dossificacaoUpdatePage.save();
        expect(await dossificacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await dossificacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Dossificacao', async () => {
        const nbButtonsBeforeDelete = await dossificacaoComponentsPage.countDeleteButtons();
        await dossificacaoComponentsPage.clickOnLastDeleteButton();

        dossificacaoDeleteDialog = new DossificacaoDeleteDialog();
        expect(await dossificacaoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.dossificacao.delete.question');
        await dossificacaoDeleteDialog.clickOnConfirmButton();

        expect(await dossificacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
