import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PlanoActividadeComponentsPage, PlanoActividadeDeleteDialog, PlanoActividadeUpdatePage } from './plano-actividade.page-object';

const expect = chai.expect;

describe('PlanoActividade e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let planoActividadeComponentsPage: PlanoActividadeComponentsPage;
  let planoActividadeUpdatePage: PlanoActividadeUpdatePage;
  let planoActividadeDeleteDialog: PlanoActividadeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PlanoActividades', async () => {
    await navBarPage.goToEntity('plano-actividade');
    planoActividadeComponentsPage = new PlanoActividadeComponentsPage();
    await browser.wait(ec.visibilityOf(planoActividadeComponentsPage.title), 5000);
    expect(await planoActividadeComponentsPage.getTitle()).to.eq('ensinoApp.planoActividade.home.title');
  });

  it('should load create PlanoActividade page', async () => {
    await planoActividadeComponentsPage.clickOnCreateButton();
    planoActividadeUpdatePage = new PlanoActividadeUpdatePage();
    expect(await planoActividadeUpdatePage.getPageTitle()).to.eq('ensinoApp.planoActividade.home.createOrEditLabel');
    await planoActividadeUpdatePage.cancel();
  });

  it('should create and save PlanoActividades', async () => {
    const nbButtonsBeforeCreate = await planoActividadeComponentsPage.countDeleteButtons();

    await planoActividadeComponentsPage.clickOnCreateButton();
    await promise.all([
      planoActividadeUpdatePage.setNumeroActividadeInput('5'),
      planoActividadeUpdatePage.setAtividadeInput('atividade'),
      planoActividadeUpdatePage.setObjectivosInput('objectivos'),
      planoActividadeUpdatePage.setDeInput('2000-12-31'),
      planoActividadeUpdatePage.setAteInput('2000-12-31'),
      planoActividadeUpdatePage.setResponsavelInput('responsavel'),
      planoActividadeUpdatePage.setLocalInput('local'),
      planoActividadeUpdatePage.setObservacaoInput('observacao'),
      planoActividadeUpdatePage.setParticipantesInput('participantes'),
      planoActividadeUpdatePage.setCoResponsavelInput('coResponsavel'),
      planoActividadeUpdatePage.setStatusActividadeInput('statusActividade'),
      planoActividadeUpdatePage.setAnoLectivoInput('2000-12-31'),
      planoActividadeUpdatePage.setPeriodoLectivoInput('periodoLectivo'),
      planoActividadeUpdatePage.utilizadorSelectLastOption()
    ]);
    expect(await planoActividadeUpdatePage.getNumeroActividadeInput()).to.eq('5', 'Expected numeroActividade value to be equals to 5');
    expect(await planoActividadeUpdatePage.getAtividadeInput()).to.eq('atividade', 'Expected Atividade value to be equals to atividade');
    expect(await planoActividadeUpdatePage.getObjectivosInput()).to.eq(
      'objectivos',
      'Expected Objectivos value to be equals to objectivos'
    );
    expect(await planoActividadeUpdatePage.getDeInput()).to.eq('2000-12-31', 'Expected de value to be equals to 2000-12-31');
    expect(await planoActividadeUpdatePage.getAteInput()).to.eq('2000-12-31', 'Expected ate value to be equals to 2000-12-31');
    expect(await planoActividadeUpdatePage.getResponsavelInput()).to.eq(
      'responsavel',
      'Expected Responsavel value to be equals to responsavel'
    );
    expect(await planoActividadeUpdatePage.getLocalInput()).to.eq('local', 'Expected Local value to be equals to local');
    expect(await planoActividadeUpdatePage.getObservacaoInput()).to.eq(
      'observacao',
      'Expected Observacao value to be equals to observacao'
    );
    expect(await planoActividadeUpdatePage.getParticipantesInput()).to.eq(
      'participantes',
      'Expected Participantes value to be equals to participantes'
    );
    expect(await planoActividadeUpdatePage.getCoResponsavelInput()).to.eq(
      'coResponsavel',
      'Expected CoResponsavel value to be equals to coResponsavel'
    );
    expect(await planoActividadeUpdatePage.getStatusActividadeInput()).to.eq(
      'statusActividade',
      'Expected StatusActividade value to be equals to statusActividade'
    );
    expect(await planoActividadeUpdatePage.getAnoLectivoInput()).to.eq(
      '2000-12-31',
      'Expected anoLectivo value to be equals to 2000-12-31'
    );
    expect(await planoActividadeUpdatePage.getPeriodoLectivoInput()).to.eq(
      'periodoLectivo',
      'Expected PeriodoLectivo value to be equals to periodoLectivo'
    );
    await planoActividadeUpdatePage.save();
    expect(await planoActividadeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await planoActividadeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PlanoActividade', async () => {
    const nbButtonsBeforeDelete = await planoActividadeComponentsPage.countDeleteButtons();
    await planoActividadeComponentsPage.clickOnLastDeleteButton();

    planoActividadeDeleteDialog = new PlanoActividadeDeleteDialog();
    expect(await planoActividadeDeleteDialog.getDialogTitle()).to.eq('ensinoApp.planoActividade.delete.question');
    await planoActividadeDeleteDialog.clickOnConfirmButton();

    expect(await planoActividadeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
