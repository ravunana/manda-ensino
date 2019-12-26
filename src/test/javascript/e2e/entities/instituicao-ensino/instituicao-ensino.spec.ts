import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  InstituicaoEnsinoComponentsPage,
  InstituicaoEnsinoDeleteDialog,
  InstituicaoEnsinoUpdatePage
} from './instituicao-ensino.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('InstituicaoEnsino e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let instituicaoEnsinoComponentsPage: InstituicaoEnsinoComponentsPage;
  let instituicaoEnsinoUpdatePage: InstituicaoEnsinoUpdatePage;
  let instituicaoEnsinoDeleteDialog: InstituicaoEnsinoDeleteDialog;
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

  it('should load InstituicaoEnsinos', async () => {
    await navBarPage.goToEntity('instituicao-ensino');
    instituicaoEnsinoComponentsPage = new InstituicaoEnsinoComponentsPage();
    await browser.wait(ec.visibilityOf(instituicaoEnsinoComponentsPage.title), 5000);
    expect(await instituicaoEnsinoComponentsPage.getTitle()).to.eq('ensinoApp.instituicaoEnsino.home.title');
  });

  it('should load create InstituicaoEnsino page', async () => {
    await instituicaoEnsinoComponentsPage.clickOnCreateButton();
    instituicaoEnsinoUpdatePage = new InstituicaoEnsinoUpdatePage();
    expect(await instituicaoEnsinoUpdatePage.getPageTitle()).to.eq('ensinoApp.instituicaoEnsino.home.createOrEditLabel');
    await instituicaoEnsinoUpdatePage.cancel();
  });

  it('should create and save InstituicaoEnsinos', async () => {
    const nbButtonsBeforeCreate = await instituicaoEnsinoComponentsPage.countDeleteButtons();

    await instituicaoEnsinoComponentsPage.clickOnCreateButton();
    await promise.all([
      instituicaoEnsinoUpdatePage.setNomeInput('nome'),
      instituicaoEnsinoUpdatePage.setLogotipoInput(absolutePath),
      instituicaoEnsinoUpdatePage.setFundacaoInput('2000-12-31'),
      instituicaoEnsinoUpdatePage.setNumeroInput('numero'),
      instituicaoEnsinoUpdatePage.setTipoVinculoInput('tipoVinculo'),
      instituicaoEnsinoUpdatePage.setUnidadePagadoraInput('unidadePagadora'),
      instituicaoEnsinoUpdatePage.setUnidadeOrganicaInput('unidadeOrganica'),
      instituicaoEnsinoUpdatePage.setTipoInstalacaoInput('tipoInstalacao'),
      instituicaoEnsinoUpdatePage.setDimensaoInput('dimensao'),
      instituicaoEnsinoUpdatePage.setCarimboInput(absolutePath),
      instituicaoEnsinoUpdatePage.utilizadorSelectLastOption(),
      instituicaoEnsinoUpdatePage.hierarquiaSelectLastOption()
    ]);
    expect(await instituicaoEnsinoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await instituicaoEnsinoUpdatePage.getLogotipoInput()).to.endsWith(
      fileNameToUpload,
      'Expected Logotipo value to be end with ' + fileNameToUpload
    );
    expect(await instituicaoEnsinoUpdatePage.getFundacaoInput()).to.eq('2000-12-31', 'Expected fundacao value to be equals to 2000-12-31');
    expect(await instituicaoEnsinoUpdatePage.getNumeroInput()).to.eq('numero', 'Expected Numero value to be equals to numero');
    expect(await instituicaoEnsinoUpdatePage.getTipoVinculoInput()).to.eq(
      'tipoVinculo',
      'Expected TipoVinculo value to be equals to tipoVinculo'
    );
    expect(await instituicaoEnsinoUpdatePage.getUnidadePagadoraInput()).to.eq(
      'unidadePagadora',
      'Expected UnidadePagadora value to be equals to unidadePagadora'
    );
    expect(await instituicaoEnsinoUpdatePage.getUnidadeOrganicaInput()).to.eq(
      'unidadeOrganica',
      'Expected UnidadeOrganica value to be equals to unidadeOrganica'
    );
    expect(await instituicaoEnsinoUpdatePage.getTipoInstalacaoInput()).to.eq(
      'tipoInstalacao',
      'Expected TipoInstalacao value to be equals to tipoInstalacao'
    );
    expect(await instituicaoEnsinoUpdatePage.getDimensaoInput()).to.eq('dimensao', 'Expected Dimensao value to be equals to dimensao');
    expect(await instituicaoEnsinoUpdatePage.getCarimboInput()).to.endsWith(
      fileNameToUpload,
      'Expected Carimbo value to be end with ' + fileNameToUpload
    );
    const selectedSede = instituicaoEnsinoUpdatePage.getSedeInput();
    if (await selectedSede.isSelected()) {
      await instituicaoEnsinoUpdatePage.getSedeInput().click();
      expect(await instituicaoEnsinoUpdatePage.getSedeInput().isSelected(), 'Expected sede not to be selected').to.be.false;
    } else {
      await instituicaoEnsinoUpdatePage.getSedeInput().click();
      expect(await instituicaoEnsinoUpdatePage.getSedeInput().isSelected(), 'Expected sede to be selected').to.be.true;
    }
    await instituicaoEnsinoUpdatePage.save();
    expect(await instituicaoEnsinoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await instituicaoEnsinoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last InstituicaoEnsino', async () => {
    const nbButtonsBeforeDelete = await instituicaoEnsinoComponentsPage.countDeleteButtons();
    await instituicaoEnsinoComponentsPage.clickOnLastDeleteButton();

    instituicaoEnsinoDeleteDialog = new InstituicaoEnsinoDeleteDialog();
    expect(await instituicaoEnsinoDeleteDialog.getDialogTitle()).to.eq('ensinoApp.instituicaoEnsino.delete.question');
    await instituicaoEnsinoDeleteDialog.clickOnConfirmButton();

    expect(await instituicaoEnsinoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
