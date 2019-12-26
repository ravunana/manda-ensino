import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CriterioAvaliacaoComponentsPage,
  CriterioAvaliacaoDeleteDialog,
  CriterioAvaliacaoUpdatePage
} from './criterio-avaliacao.page-object';

const expect = chai.expect;

describe('CriterioAvaliacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let criterioAvaliacaoComponentsPage: CriterioAvaliacaoComponentsPage;
  let criterioAvaliacaoUpdatePage: CriterioAvaliacaoUpdatePage;
  let criterioAvaliacaoDeleteDialog: CriterioAvaliacaoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CriterioAvaliacaos', async () => {
    await navBarPage.goToEntity('criterio-avaliacao');
    criterioAvaliacaoComponentsPage = new CriterioAvaliacaoComponentsPage();
    await browser.wait(ec.visibilityOf(criterioAvaliacaoComponentsPage.title), 5000);
    expect(await criterioAvaliacaoComponentsPage.getTitle()).to.eq('ensinoApp.criterioAvaliacao.home.title');
  });

  it('should load create CriterioAvaliacao page', async () => {
    await criterioAvaliacaoComponentsPage.clickOnCreateButton();
    criterioAvaliacaoUpdatePage = new CriterioAvaliacaoUpdatePage();
    expect(await criterioAvaliacaoUpdatePage.getPageTitle()).to.eq('ensinoApp.criterioAvaliacao.home.createOrEditLabel');
    await criterioAvaliacaoUpdatePage.cancel();
  });

  it('should create and save CriterioAvaliacaos', async () => {
    const nbButtonsBeforeCreate = await criterioAvaliacaoComponentsPage.countDeleteButtons();

    await criterioAvaliacaoComponentsPage.clickOnCreateButton();
    await promise.all([
      criterioAvaliacaoUpdatePage.setAprovaComInput('5'),
      criterioAvaliacaoUpdatePage.setReporvaComInput('5'),
      criterioAvaliacaoUpdatePage.setRecursoComInput('5'),
      criterioAvaliacaoUpdatePage.setNumeroFaltaReprovaInput('5'),
      criterioAvaliacaoUpdatePage.setMenorNotaInput('5'),
      criterioAvaliacaoUpdatePage.setMaiorNotaInput('5'),
      criterioAvaliacaoUpdatePage.setNotaMinimaAprovacaoInput('5'),
      criterioAvaliacaoUpdatePage.planoCurricularSelectLastOption()
    ]);
    expect(await criterioAvaliacaoUpdatePage.getAprovaComInput()).to.eq('5', 'Expected aprovaCom value to be equals to 5');
    expect(await criterioAvaliacaoUpdatePage.getReporvaComInput()).to.eq('5', 'Expected reporvaCom value to be equals to 5');
    expect(await criterioAvaliacaoUpdatePage.getRecursoComInput()).to.eq('5', 'Expected recursoCom value to be equals to 5');
    const selectedFazExame = criterioAvaliacaoUpdatePage.getFazExameInput();
    if (await selectedFazExame.isSelected()) {
      await criterioAvaliacaoUpdatePage.getFazExameInput().click();
      expect(await criterioAvaliacaoUpdatePage.getFazExameInput().isSelected(), 'Expected fazExame not to be selected').to.be.false;
    } else {
      await criterioAvaliacaoUpdatePage.getFazExameInput().click();
      expect(await criterioAvaliacaoUpdatePage.getFazExameInput().isSelected(), 'Expected fazExame to be selected').to.be.true;
    }
    const selectedFazRecurso = criterioAvaliacaoUpdatePage.getFazRecursoInput();
    if (await selectedFazRecurso.isSelected()) {
      await criterioAvaliacaoUpdatePage.getFazRecursoInput().click();
      expect(await criterioAvaliacaoUpdatePage.getFazRecursoInput().isSelected(), 'Expected fazRecurso not to be selected').to.be.false;
    } else {
      await criterioAvaliacaoUpdatePage.getFazRecursoInput().click();
      expect(await criterioAvaliacaoUpdatePage.getFazRecursoInput().isSelected(), 'Expected fazRecurso to be selected').to.be.true;
    }
    const selectedFazExameEspecial = criterioAvaliacaoUpdatePage.getFazExameEspecialInput();
    if (await selectedFazExameEspecial.isSelected()) {
      await criterioAvaliacaoUpdatePage.getFazExameEspecialInput().click();
      expect(await criterioAvaliacaoUpdatePage.getFazExameEspecialInput().isSelected(), 'Expected fazExameEspecial not to be selected').to
        .be.false;
    } else {
      await criterioAvaliacaoUpdatePage.getFazExameEspecialInput().click();
      expect(await criterioAvaliacaoUpdatePage.getFazExameEspecialInput().isSelected(), 'Expected fazExameEspecial to be selected').to.be
        .true;
    }
    expect(await criterioAvaliacaoUpdatePage.getNumeroFaltaReprovaInput()).to.eq(
      '5',
      'Expected numeroFaltaReprova value to be equals to 5'
    );
    expect(await criterioAvaliacaoUpdatePage.getMenorNotaInput()).to.eq('5', 'Expected menorNota value to be equals to 5');
    expect(await criterioAvaliacaoUpdatePage.getMaiorNotaInput()).to.eq('5', 'Expected maiorNota value to be equals to 5');
    expect(await criterioAvaliacaoUpdatePage.getNotaMinimaAprovacaoInput()).to.eq(
      '5',
      'Expected notaMinimaAprovacao value to be equals to 5'
    );
    await criterioAvaliacaoUpdatePage.save();
    expect(await criterioAvaliacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await criterioAvaliacaoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CriterioAvaliacao', async () => {
    const nbButtonsBeforeDelete = await criterioAvaliacaoComponentsPage.countDeleteButtons();
    await criterioAvaliacaoComponentsPage.clickOnLastDeleteButton();

    criterioAvaliacaoDeleteDialog = new CriterioAvaliacaoDeleteDialog();
    expect(await criterioAvaliacaoDeleteDialog.getDialogTitle()).to.eq('ensinoApp.criterioAvaliacao.delete.question');
    await criterioAvaliacaoDeleteDialog.clickOnConfirmButton();

    expect(await criterioAvaliacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
