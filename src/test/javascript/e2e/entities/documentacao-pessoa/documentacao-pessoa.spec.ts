import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DocumentacaoPessoaComponentsPage,
  /* DocumentacaoPessoaDeleteDialog,
   */ DocumentacaoPessoaUpdatePage
} from './documentacao-pessoa.page-object';

const expect = chai.expect;

describe('DocumentacaoPessoa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let documentacaoPessoaComponentsPage: DocumentacaoPessoaComponentsPage;
  let documentacaoPessoaUpdatePage: DocumentacaoPessoaUpdatePage;
  /* let documentacaoPessoaDeleteDialog: DocumentacaoPessoaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DocumentacaoPessoas', async () => {
    await navBarPage.goToEntity('documentacao-pessoa');
    documentacaoPessoaComponentsPage = new DocumentacaoPessoaComponentsPage();
    await browser.wait(ec.visibilityOf(documentacaoPessoaComponentsPage.title), 5000);
    expect(await documentacaoPessoaComponentsPage.getTitle()).to.eq('ensinoApp.documentacaoPessoa.home.title');
  });

  it('should load create DocumentacaoPessoa page', async () => {
    await documentacaoPessoaComponentsPage.clickOnCreateButton();
    documentacaoPessoaUpdatePage = new DocumentacaoPessoaUpdatePage();
    expect(await documentacaoPessoaUpdatePage.getPageTitle()).to.eq('ensinoApp.documentacaoPessoa.home.createOrEditLabel');
    await documentacaoPessoaUpdatePage.cancel();
  });

  /*  it('should create and save DocumentacaoPessoas', async () => {
        const nbButtonsBeforeCreate = await documentacaoPessoaComponentsPage.countDeleteButtons();

        await documentacaoPessoaComponentsPage.clickOnCreateButton();
        await promise.all([
            documentacaoPessoaUpdatePage.setTipoInput('tipo'),
            documentacaoPessoaUpdatePage.setNumeroInput('numero'),
            documentacaoPessoaUpdatePage.setEmissaoInput('2000-12-31'),
            documentacaoPessoaUpdatePage.setValidadeInput('2000-12-31'),
            documentacaoPessoaUpdatePage.setNaturalidadeInput('naturalidade'),
            documentacaoPessoaUpdatePage.setNacionalidadeInput('nacionalidade'),
            documentacaoPessoaUpdatePage.setLocalEmissaoInput('localEmissao'),
            documentacaoPessoaUpdatePage.setNifInput('nif'),
            documentacaoPessoaUpdatePage.pessoaSelectLastOption(),
        ]);
        expect(await documentacaoPessoaUpdatePage.getTipoInput()).to.eq('tipo', 'Expected Tipo value to be equals to tipo');
        expect(await documentacaoPessoaUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
        expect(await documentacaoPessoaUpdatePage.getEmissaoInput()).to.eq('2000-12-31', 'Expected emissao value to be equals to 2000-12-31');
        expect(await documentacaoPessoaUpdatePage.getValidadeInput()).to.eq('2000-12-31', 'Expected validade value to be equals to 2000-12-31');
        expect(await documentacaoPessoaUpdatePage.getNaturalidadeInput()).to.eq('naturalidade', 'Expected Naturalidade value to be equals to naturalidade');
        expect(await documentacaoPessoaUpdatePage.getNacionalidadeInput()).to.eq('nacionalidade', 'Expected Nacionalidade value to be equals to nacionalidade');
        expect(await documentacaoPessoaUpdatePage.getLocalEmissaoInput()).to.eq('localEmissao', 'Expected LocalEmissao value to be equals to localEmissao');
        expect(await documentacaoPessoaUpdatePage.getNifInput()).to.eq('nif', 'Expected Nif value to be equals to nif');
        await documentacaoPessoaUpdatePage.save();
        expect(await documentacaoPessoaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await documentacaoPessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last DocumentacaoPessoa', async () => {
        const nbButtonsBeforeDelete = await documentacaoPessoaComponentsPage.countDeleteButtons();
        await documentacaoPessoaComponentsPage.clickOnLastDeleteButton();

        documentacaoPessoaDeleteDialog = new DocumentacaoPessoaDeleteDialog();
        expect(await documentacaoPessoaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.documentacaoPessoa.delete.question');
        await documentacaoPessoaDeleteDialog.clickOnConfirmButton();

        expect(await documentacaoPessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
