import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CategoriaValiacaoComponentsPage,
  /* CategoriaValiacaoDeleteDialog,
   */ CategoriaValiacaoUpdatePage
} from './categoria-valiacao.page-object';

const expect = chai.expect;

describe('CategoriaValiacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let categoriaValiacaoComponentsPage: CategoriaValiacaoComponentsPage;
  let categoriaValiacaoUpdatePage: CategoriaValiacaoUpdatePage;
  /* let categoriaValiacaoDeleteDialog: CategoriaValiacaoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CategoriaValiacaos', async () => {
    await navBarPage.goToEntity('categoria-valiacao');
    categoriaValiacaoComponentsPage = new CategoriaValiacaoComponentsPage();
    await browser.wait(ec.visibilityOf(categoriaValiacaoComponentsPage.title), 5000);
    expect(await categoriaValiacaoComponentsPage.getTitle()).to.eq('ensinoApp.categoriaValiacao.home.title');
  });

  it('should load create CategoriaValiacao page', async () => {
    await categoriaValiacaoComponentsPage.clickOnCreateButton();
    categoriaValiacaoUpdatePage = new CategoriaValiacaoUpdatePage();
    expect(await categoriaValiacaoUpdatePage.getPageTitle()).to.eq('ensinoApp.categoriaValiacao.home.createOrEditLabel');
    await categoriaValiacaoUpdatePage.cancel();
  });

  /*  it('should create and save CategoriaValiacaos', async () => {
        const nbButtonsBeforeCreate = await categoriaValiacaoComponentsPage.countDeleteButtons();

        await categoriaValiacaoComponentsPage.clickOnCreateButton();
        await promise.all([
            categoriaValiacaoUpdatePage.setNomeInput('nome'),
            categoriaValiacaoUpdatePage.setSiglaInternaInput('siglaInterna'),
            categoriaValiacaoUpdatePage.setSiglaPautaInput('siglaPauta'),
            categoriaValiacaoUpdatePage.areaFormacaoSelectLastOption(),
        ]);
        expect(await categoriaValiacaoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
        expect(await categoriaValiacaoUpdatePage.getSiglaInternaInput()).to.eq('siglaInterna', 'Expected SiglaInterna value to be equals to siglaInterna');
        expect(await categoriaValiacaoUpdatePage.getSiglaPautaInput()).to.eq('siglaPauta', 'Expected SiglaPauta value to be equals to siglaPauta');
        await categoriaValiacaoUpdatePage.save();
        expect(await categoriaValiacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await categoriaValiacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last CategoriaValiacao', async () => {
        const nbButtonsBeforeDelete = await categoriaValiacaoComponentsPage.countDeleteButtons();
        await categoriaValiacaoComponentsPage.clickOnLastDeleteButton();

        categoriaValiacaoDeleteDialog = new CategoriaValiacaoDeleteDialog();
        expect(await categoriaValiacaoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.categoriaValiacao.delete.question');
        await categoriaValiacaoDeleteDialog.clickOnConfirmButton();

        expect(await categoriaValiacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
