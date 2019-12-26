import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AlunoComponentsPage,
  /* AlunoDeleteDialog,
   */ AlunoUpdatePage
} from './aluno.page-object';

const expect = chai.expect;

describe('Aluno e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let alunoComponentsPage: AlunoComponentsPage;
  let alunoUpdatePage: AlunoUpdatePage;
  /* let alunoDeleteDialog: AlunoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Alunos', async () => {
    await navBarPage.goToEntity('aluno');
    alunoComponentsPage = new AlunoComponentsPage();
    await browser.wait(ec.visibilityOf(alunoComponentsPage.title), 5000);
    expect(await alunoComponentsPage.getTitle()).to.eq('ensinoApp.aluno.home.title');
  });

  it('should load create Aluno page', async () => {
    await alunoComponentsPage.clickOnCreateButton();
    alunoUpdatePage = new AlunoUpdatePage();
    expect(await alunoUpdatePage.getPageTitle()).to.eq('ensinoApp.aluno.home.createOrEditLabel');
    await alunoUpdatePage.cancel();
  });

  /*  it('should create and save Alunos', async () => {
        const nbButtonsBeforeCreate = await alunoComponentsPage.countDeleteButtons();

        await alunoComponentsPage.clickOnCreateButton();
        await promise.all([
            alunoUpdatePage.setNumeroProcessoInput('numeroProcesso'),
            alunoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            alunoUpdatePage.setTurmaAnteriorInput('turmaAnterior'),
            alunoUpdatePage.setAnoConclusaoInput('5'),
            alunoUpdatePage.setCursoFrequentadoInput('cursoFrequentado'),
            alunoUpdatePage.setNomeEscolaAnterirorInput('nomeEscolaAnteriror'),
            alunoUpdatePage.setEnderecoEscolaAnteriorInput('enderecoEscolaAnterior'),
            alunoUpdatePage.setClasseConcluidaInput('5'),
            alunoUpdatePage.setNumeroProcessoAnteriorInput('numeroProcessoAnterior'),
            alunoUpdatePage.setSituacaoAnteriorInput('situacaoAnterior'),
            alunoUpdatePage.pessoaSelectLastOption(),
            alunoUpdatePage.utilizadorSelectLastOption(),
            alunoUpdatePage.encarregadoEducacaoSelectLastOption(),
        ]);
        expect(await alunoUpdatePage.getNumeroProcessoInput()).to.eq('numeroProcesso', 'Expected NumeroProcesso value to be equals to numeroProcesso');
        const selectedTransferido = alunoUpdatePage.getTransferidoInput();
        if (await selectedTransferido.isSelected()) {
            await alunoUpdatePage.getTransferidoInput().click();
            expect(await alunoUpdatePage.getTransferidoInput().isSelected(), 'Expected transferido not to be selected').to.be.false;
        } else {
            await alunoUpdatePage.getTransferidoInput().click();
            expect(await alunoUpdatePage.getTransferidoInput().isSelected(), 'Expected transferido to be selected').to.be.true;
        }
        expect(await alunoUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await alunoUpdatePage.getTurmaAnteriorInput()).to.eq('turmaAnterior', 'Expected TurmaAnterior value to be equals to turmaAnterior');
        expect(await alunoUpdatePage.getAnoConclusaoInput()).to.eq('5', 'Expected anoConclusao value to be equals to 5');
        expect(await alunoUpdatePage.getCursoFrequentadoInput()).to.eq('cursoFrequentado', 'Expected CursoFrequentado value to be equals to cursoFrequentado');
        expect(await alunoUpdatePage.getNomeEscolaAnterirorInput()).to.eq('nomeEscolaAnteriror', 'Expected NomeEscolaAnteriror value to be equals to nomeEscolaAnteriror');
        expect(await alunoUpdatePage.getEnderecoEscolaAnteriorInput()).to.eq('enderecoEscolaAnterior', 'Expected EnderecoEscolaAnterior value to be equals to enderecoEscolaAnterior');
        expect(await alunoUpdatePage.getClasseConcluidaInput()).to.eq('5', 'Expected classeConcluida value to be equals to 5');
        expect(await alunoUpdatePage.getNumeroProcessoAnteriorInput()).to.eq('numeroProcessoAnterior', 'Expected NumeroProcessoAnterior value to be equals to numeroProcessoAnterior');
        expect(await alunoUpdatePage.getSituacaoAnteriorInput()).to.eq('situacaoAnterior', 'Expected SituacaoAnterior value to be equals to situacaoAnterior');
        await alunoUpdatePage.save();
        expect(await alunoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await alunoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Aluno', async () => {
        const nbButtonsBeforeDelete = await alunoComponentsPage.countDeleteButtons();
        await alunoComponentsPage.clickOnLastDeleteButton();

        alunoDeleteDialog = new AlunoDeleteDialog();
        expect(await alunoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.aluno.delete.question');
        await alunoDeleteDialog.clickOnConfirmButton();

        expect(await alunoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
