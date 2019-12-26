import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LocalizacaoInstituicaoEnsinoComponentsPage,
  /* LocalizacaoInstituicaoEnsinoDeleteDialog,
   */ LocalizacaoInstituicaoEnsinoUpdatePage
} from './localizacao-instituicao-ensino.page-object';

const expect = chai.expect;

describe('LocalizacaoInstituicaoEnsino e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let localizacaoInstituicaoEnsinoComponentsPage: LocalizacaoInstituicaoEnsinoComponentsPage;
  let localizacaoInstituicaoEnsinoUpdatePage: LocalizacaoInstituicaoEnsinoUpdatePage;
  /* let localizacaoInstituicaoEnsinoDeleteDialog: LocalizacaoInstituicaoEnsinoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LocalizacaoInstituicaoEnsinos', async () => {
    await navBarPage.goToEntity('localizacao-instituicao-ensino');
    localizacaoInstituicaoEnsinoComponentsPage = new LocalizacaoInstituicaoEnsinoComponentsPage();
    await browser.wait(ec.visibilityOf(localizacaoInstituicaoEnsinoComponentsPage.title), 5000);
    expect(await localizacaoInstituicaoEnsinoComponentsPage.getTitle()).to.eq('ensinoApp.localizacaoInstituicaoEnsino.home.title');
  });

  it('should load create LocalizacaoInstituicaoEnsino page', async () => {
    await localizacaoInstituicaoEnsinoComponentsPage.clickOnCreateButton();
    localizacaoInstituicaoEnsinoUpdatePage = new LocalizacaoInstituicaoEnsinoUpdatePage();
    expect(await localizacaoInstituicaoEnsinoUpdatePage.getPageTitle()).to.eq(
      'ensinoApp.localizacaoInstituicaoEnsino.home.createOrEditLabel'
    );
    await localizacaoInstituicaoEnsinoUpdatePage.cancel();
  });

  /*  it('should create and save LocalizacaoInstituicaoEnsinos', async () => {
        const nbButtonsBeforeCreate = await localizacaoInstituicaoEnsinoComponentsPage.countDeleteButtons();

        await localizacaoInstituicaoEnsinoComponentsPage.clickOnCreateButton();
        await promise.all([
            localizacaoInstituicaoEnsinoUpdatePage.setProvinciaInput('provincia'),
            localizacaoInstituicaoEnsinoUpdatePage.setMunicipioInput('municipio'),
            localizacaoInstituicaoEnsinoUpdatePage.setBairroInput('bairro'),
            localizacaoInstituicaoEnsinoUpdatePage.setRuaInput('rua'),
            localizacaoInstituicaoEnsinoUpdatePage.setQuarteiraoInput('quarteirao'),
            localizacaoInstituicaoEnsinoUpdatePage.setNumeroPortaInput('numeroPorta'),
            localizacaoInstituicaoEnsinoUpdatePage.setCaixaPostalInput('caixaPostal'),
            localizacaoInstituicaoEnsinoUpdatePage.instituicaoEnsinoSelectLastOption(),
        ]);
        expect(await localizacaoInstituicaoEnsinoUpdatePage.getProvinciaInput()).to.eq('provincia', 'Expected Provincia value to be equals to provincia');
        expect(await localizacaoInstituicaoEnsinoUpdatePage.getMunicipioInput()).to.eq('municipio', 'Expected Municipio value to be equals to municipio');
        expect(await localizacaoInstituicaoEnsinoUpdatePage.getBairroInput()).to.eq('bairro', 'Expected Bairro value to be equals to bairro');
        expect(await localizacaoInstituicaoEnsinoUpdatePage.getRuaInput()).to.eq('rua', 'Expected Rua value to be equals to rua');
        expect(await localizacaoInstituicaoEnsinoUpdatePage.getQuarteiraoInput()).to.eq('quarteirao', 'Expected Quarteirao value to be equals to quarteirao');
        expect(await localizacaoInstituicaoEnsinoUpdatePage.getNumeroPortaInput()).to.eq('numeroPorta', 'Expected NumeroPorta value to be equals to numeroPorta');
        expect(await localizacaoInstituicaoEnsinoUpdatePage.getCaixaPostalInput()).to.eq('caixaPostal', 'Expected CaixaPostal value to be equals to caixaPostal');
        await localizacaoInstituicaoEnsinoUpdatePage.save();
        expect(await localizacaoInstituicaoEnsinoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await localizacaoInstituicaoEnsinoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last LocalizacaoInstituicaoEnsino', async () => {
        const nbButtonsBeforeDelete = await localizacaoInstituicaoEnsinoComponentsPage.countDeleteButtons();
        await localizacaoInstituicaoEnsinoComponentsPage.clickOnLastDeleteButton();

        localizacaoInstituicaoEnsinoDeleteDialog = new LocalizacaoInstituicaoEnsinoDeleteDialog();
        expect(await localizacaoInstituicaoEnsinoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.localizacaoInstituicaoEnsino.delete.question');
        await localizacaoInstituicaoEnsinoDeleteDialog.clickOnConfirmButton();

        expect(await localizacaoInstituicaoEnsinoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
