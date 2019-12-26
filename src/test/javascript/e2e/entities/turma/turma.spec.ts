import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TurmaComponentsPage,
  /* TurmaDeleteDialog,
   */ TurmaUpdatePage
} from './turma.page-object';

const expect = chai.expect;

describe('Turma e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let turmaComponentsPage: TurmaComponentsPage;
  let turmaUpdatePage: TurmaUpdatePage;
  /* let turmaDeleteDialog: TurmaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Turmas', async () => {
    await navBarPage.goToEntity('turma');
    turmaComponentsPage = new TurmaComponentsPage();
    await browser.wait(ec.visibilityOf(turmaComponentsPage.title), 5000);
    expect(await turmaComponentsPage.getTitle()).to.eq('ensinoApp.turma.home.title');
  });

  it('should load create Turma page', async () => {
    await turmaComponentsPage.clickOnCreateButton();
    turmaUpdatePage = new TurmaUpdatePage();
    expect(await turmaUpdatePage.getPageTitle()).to.eq('ensinoApp.turma.home.createOrEditLabel');
    await turmaUpdatePage.cancel();
  });

  /*  it('should create and save Turmas', async () => {
        const nbButtonsBeforeCreate = await turmaComponentsPage.countDeleteButtons();

        await turmaComponentsPage.clickOnCreateButton();
        await promise.all([
            turmaUpdatePage.setDescricaoInput('descricao'),
            turmaUpdatePage.setAnoLectivoInput('2000-12-31'),
            turmaUpdatePage.setRegimeInput('regime'),
            turmaUpdatePage.setTurnoInput('turno'),
            turmaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            turmaUpdatePage.utilizadorSelectLastOption(),
            turmaUpdatePage.salaSelectLastOption(),
            turmaUpdatePage.classeSelectLastOption(),
            turmaUpdatePage.cursoSelectLastOption(),
        ]);
        expect(await turmaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await turmaUpdatePage.getAnoLectivoInput()).to.eq('2000-12-31', 'Expected anoLectivo value to be equals to 2000-12-31');
        expect(await turmaUpdatePage.getRegimeInput()).to.eq('regime', 'Expected Regime value to be equals to regime');
        expect(await turmaUpdatePage.getTurnoInput()).to.eq('turno', 'Expected Turno value to be equals to turno');
        expect(await turmaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        const selectedAtivo = turmaUpdatePage.getAtivoInput();
        if (await selectedAtivo.isSelected()) {
            await turmaUpdatePage.getAtivoInput().click();
            expect(await turmaUpdatePage.getAtivoInput().isSelected(), 'Expected ativo not to be selected').to.be.false;
        } else {
            await turmaUpdatePage.getAtivoInput().click();
            expect(await turmaUpdatePage.getAtivoInput().isSelected(), 'Expected ativo to be selected').to.be.true;
        }
        await turmaUpdatePage.save();
        expect(await turmaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await turmaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Turma', async () => {
        const nbButtonsBeforeDelete = await turmaComponentsPage.countDeleteButtons();
        await turmaComponentsPage.clickOnLastDeleteButton();

        turmaDeleteDialog = new TurmaDeleteDialog();
        expect(await turmaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.turma.delete.question');
        await turmaDeleteDialog.clickOnConfirmButton();

        expect(await turmaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
