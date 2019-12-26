import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CategoriaRequerimentoComponentsPage,
  CategoriaRequerimentoDeleteDialog,
  CategoriaRequerimentoUpdatePage
} from './categoria-requerimento.page-object';

const expect = chai.expect;

describe('CategoriaRequerimento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let categoriaRequerimentoComponentsPage: CategoriaRequerimentoComponentsPage;
  let categoriaRequerimentoUpdatePage: CategoriaRequerimentoUpdatePage;
  let categoriaRequerimentoDeleteDialog: CategoriaRequerimentoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CategoriaRequerimentos', async () => {
    await navBarPage.goToEntity('categoria-requerimento');
    categoriaRequerimentoComponentsPage = new CategoriaRequerimentoComponentsPage();
    await browser.wait(ec.visibilityOf(categoriaRequerimentoComponentsPage.title), 5000);
    expect(await categoriaRequerimentoComponentsPage.getTitle()).to.eq('ensinoApp.categoriaRequerimento.home.title');
  });

  it('should load create CategoriaRequerimento page', async () => {
    await categoriaRequerimentoComponentsPage.clickOnCreateButton();
    categoriaRequerimentoUpdatePage = new CategoriaRequerimentoUpdatePage();
    expect(await categoriaRequerimentoUpdatePage.getPageTitle()).to.eq('ensinoApp.categoriaRequerimento.home.createOrEditLabel');
    await categoriaRequerimentoUpdatePage.cancel();
  });

  it('should create and save CategoriaRequerimentos', async () => {
    const nbButtonsBeforeCreate = await categoriaRequerimentoComponentsPage.countDeleteButtons();

    await categoriaRequerimentoComponentsPage.clickOnCreateButton();
    await promise.all([
      categoriaRequerimentoUpdatePage.setNomeInput('nome'),
      categoriaRequerimentoUpdatePage.setTempoRespostaInput('5'),
      categoriaRequerimentoUpdatePage.setDescricaoInput('descricao')
    ]);
    expect(await categoriaRequerimentoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await categoriaRequerimentoUpdatePage.getTempoRespostaInput()).to.eq('5', 'Expected tempoResposta value to be equals to 5');
    const selectedPagase = categoriaRequerimentoUpdatePage.getPagaseInput();
    if (await selectedPagase.isSelected()) {
      await categoriaRequerimentoUpdatePage.getPagaseInput().click();
      expect(await categoriaRequerimentoUpdatePage.getPagaseInput().isSelected(), 'Expected pagase not to be selected').to.be.false;
    } else {
      await categoriaRequerimentoUpdatePage.getPagaseInput().click();
      expect(await categoriaRequerimentoUpdatePage.getPagaseInput().isSelected(), 'Expected pagase to be selected').to.be.true;
    }
    expect(await categoriaRequerimentoUpdatePage.getDescricaoInput()).to.eq(
      'descricao',
      'Expected Descricao value to be equals to descricao'
    );
    await categoriaRequerimentoUpdatePage.save();
    expect(await categoriaRequerimentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await categoriaRequerimentoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CategoriaRequerimento', async () => {
    const nbButtonsBeforeDelete = await categoriaRequerimentoComponentsPage.countDeleteButtons();
    await categoriaRequerimentoComponentsPage.clickOnLastDeleteButton();

    categoriaRequerimentoDeleteDialog = new CategoriaRequerimentoDeleteDialog();
    expect(await categoriaRequerimentoDeleteDialog.getDialogTitle()).to.eq('ensinoApp.categoriaRequerimento.delete.question');
    await categoriaRequerimentoDeleteDialog.clickOnConfirmButton();

    expect(await categoriaRequerimentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
