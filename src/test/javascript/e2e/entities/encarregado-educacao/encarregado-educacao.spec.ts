import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EncarregadoEducacaoComponentsPage,
  /* EncarregadoEducacaoDeleteDialog,
   */ EncarregadoEducacaoUpdatePage
} from './encarregado-educacao.page-object';

const expect = chai.expect;

describe('EncarregadoEducacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let encarregadoEducacaoComponentsPage: EncarregadoEducacaoComponentsPage;
  let encarregadoEducacaoUpdatePage: EncarregadoEducacaoUpdatePage;
  /* let encarregadoEducacaoDeleteDialog: EncarregadoEducacaoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EncarregadoEducacaos', async () => {
    await navBarPage.goToEntity('encarregado-educacao');
    encarregadoEducacaoComponentsPage = new EncarregadoEducacaoComponentsPage();
    await browser.wait(ec.visibilityOf(encarregadoEducacaoComponentsPage.title), 5000);
    expect(await encarregadoEducacaoComponentsPage.getTitle()).to.eq('ensinoApp.encarregadoEducacao.home.title');
  });

  it('should load create EncarregadoEducacao page', async () => {
    await encarregadoEducacaoComponentsPage.clickOnCreateButton();
    encarregadoEducacaoUpdatePage = new EncarregadoEducacaoUpdatePage();
    expect(await encarregadoEducacaoUpdatePage.getPageTitle()).to.eq('ensinoApp.encarregadoEducacao.home.createOrEditLabel');
    await encarregadoEducacaoUpdatePage.cancel();
  });

  /*  it('should create and save EncarregadoEducacaos', async () => {
        const nbButtonsBeforeCreate = await encarregadoEducacaoComponentsPage.countDeleteButtons();

        await encarregadoEducacaoComponentsPage.clickOnCreateButton();
        await promise.all([
            encarregadoEducacaoUpdatePage.setProfissaoInput('profissao'),
            encarregadoEducacaoUpdatePage.setCargoInput('cargo'),
            encarregadoEducacaoUpdatePage.setFaixaSalarialInput('5'),
            encarregadoEducacaoUpdatePage.setNomeEmpresaInput('nomeEmpresa'),
            encarregadoEducacaoUpdatePage.setContactoEmpresaInput('contactoEmpresa'),
            encarregadoEducacaoUpdatePage.pessoaSelectLastOption(),
        ]);
        expect(await encarregadoEducacaoUpdatePage.getProfissaoInput()).to.eq('profissao', 'Expected Profissao value to be equals to profissao');
        expect(await encarregadoEducacaoUpdatePage.getCargoInput()).to.eq('cargo', 'Expected Cargo value to be equals to cargo');
        expect(await encarregadoEducacaoUpdatePage.getFaixaSalarialInput()).to.eq('5', 'Expected faixaSalarial value to be equals to 5');
        expect(await encarregadoEducacaoUpdatePage.getNomeEmpresaInput()).to.eq('nomeEmpresa', 'Expected NomeEmpresa value to be equals to nomeEmpresa');
        expect(await encarregadoEducacaoUpdatePage.getContactoEmpresaInput()).to.eq('contactoEmpresa', 'Expected ContactoEmpresa value to be equals to contactoEmpresa');
        await encarregadoEducacaoUpdatePage.save();
        expect(await encarregadoEducacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await encarregadoEducacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last EncarregadoEducacao', async () => {
        const nbButtonsBeforeDelete = await encarregadoEducacaoComponentsPage.countDeleteButtons();
        await encarregadoEducacaoComponentsPage.clickOnLastDeleteButton();

        encarregadoEducacaoDeleteDialog = new EncarregadoEducacaoDeleteDialog();
        expect(await encarregadoEducacaoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.encarregadoEducacao.delete.question');
        await encarregadoEducacaoDeleteDialog.clickOnConfirmButton();

        expect(await encarregadoEducacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
