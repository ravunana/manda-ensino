import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CategoriaAlunoComponentsPage, CategoriaAlunoDeleteDialog, CategoriaAlunoUpdatePage } from './categoria-aluno.page-object';

const expect = chai.expect;

describe('CategoriaAluno e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let categoriaAlunoComponentsPage: CategoriaAlunoComponentsPage;
  let categoriaAlunoUpdatePage: CategoriaAlunoUpdatePage;
  let categoriaAlunoDeleteDialog: CategoriaAlunoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CategoriaAlunos', async () => {
    await navBarPage.goToEntity('categoria-aluno');
    categoriaAlunoComponentsPage = new CategoriaAlunoComponentsPage();
    await browser.wait(ec.visibilityOf(categoriaAlunoComponentsPage.title), 5000);
    expect(await categoriaAlunoComponentsPage.getTitle()).to.eq('ensinoApp.categoriaAluno.home.title');
  });

  it('should load create CategoriaAluno page', async () => {
    await categoriaAlunoComponentsPage.clickOnCreateButton();
    categoriaAlunoUpdatePage = new CategoriaAlunoUpdatePage();
    expect(await categoriaAlunoUpdatePage.getPageTitle()).to.eq('ensinoApp.categoriaAluno.home.createOrEditLabel');
    await categoriaAlunoUpdatePage.cancel();
  });

  it('should create and save CategoriaAlunos', async () => {
    const nbButtonsBeforeCreate = await categoriaAlunoComponentsPage.countDeleteButtons();

    await categoriaAlunoComponentsPage.clickOnCreateButton();
    await promise.all([
      categoriaAlunoUpdatePage.setNomeInput('nome'),
      categoriaAlunoUpdatePage.setDescontoInput('5'),
      categoriaAlunoUpdatePage.setDescricaoInput('descricao'),
      categoriaAlunoUpdatePage.setDiaPagamentoInput('5')
    ]);
    expect(await categoriaAlunoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await categoriaAlunoUpdatePage.getDescontoInput()).to.eq('5', 'Expected desconto value to be equals to 5');
    const selectedPagaPropina = categoriaAlunoUpdatePage.getPagaPropinaInput();
    if (await selectedPagaPropina.isSelected()) {
      await categoriaAlunoUpdatePage.getPagaPropinaInput().click();
      expect(await categoriaAlunoUpdatePage.getPagaPropinaInput().isSelected(), 'Expected pagaPropina not to be selected').to.be.false;
    } else {
      await categoriaAlunoUpdatePage.getPagaPropinaInput().click();
      expect(await categoriaAlunoUpdatePage.getPagaPropinaInput().isSelected(), 'Expected pagaPropina to be selected').to.be.true;
    }
    const selectedPagaMulta = categoriaAlunoUpdatePage.getPagaMultaInput();
    if (await selectedPagaMulta.isSelected()) {
      await categoriaAlunoUpdatePage.getPagaMultaInput().click();
      expect(await categoriaAlunoUpdatePage.getPagaMultaInput().isSelected(), 'Expected pagaMulta not to be selected').to.be.false;
    } else {
      await categoriaAlunoUpdatePage.getPagaMultaInput().click();
      expect(await categoriaAlunoUpdatePage.getPagaMultaInput().isSelected(), 'Expected pagaMulta to be selected').to.be.true;
    }
    expect(await categoriaAlunoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await categoriaAlunoUpdatePage.getDiaPagamentoInput()).to.eq('5', 'Expected diaPagamento value to be equals to 5');
    const selectedMesAtual = categoriaAlunoUpdatePage.getMesAtualInput();
    if (await selectedMesAtual.isSelected()) {
      await categoriaAlunoUpdatePage.getMesAtualInput().click();
      expect(await categoriaAlunoUpdatePage.getMesAtualInput().isSelected(), 'Expected mesAtual not to be selected').to.be.false;
    } else {
      await categoriaAlunoUpdatePage.getMesAtualInput().click();
      expect(await categoriaAlunoUpdatePage.getMesAtualInput().isSelected(), 'Expected mesAtual to be selected').to.be.true;
    }
    const selectedAtivo = categoriaAlunoUpdatePage.getAtivoInput();
    if (await selectedAtivo.isSelected()) {
      await categoriaAlunoUpdatePage.getAtivoInput().click();
      expect(await categoriaAlunoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
    } else {
      await categoriaAlunoUpdatePage.getAtivoInput().click();
      expect(await categoriaAlunoUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
    }
    await categoriaAlunoUpdatePage.save();
    expect(await categoriaAlunoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await categoriaAlunoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CategoriaAluno', async () => {
    const nbButtonsBeforeDelete = await categoriaAlunoComponentsPage.countDeleteButtons();
    await categoriaAlunoComponentsPage.clickOnLastDeleteButton();

    categoriaAlunoDeleteDialog = new CategoriaAlunoDeleteDialog();
    expect(await categoriaAlunoDeleteDialog.getDialogTitle()).to.eq('ensinoApp.categoriaAluno.delete.question');
    await categoriaAlunoDeleteDialog.clickOnConfirmButton();

    expect(await categoriaAlunoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
