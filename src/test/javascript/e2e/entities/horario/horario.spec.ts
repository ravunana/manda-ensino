import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  HorarioComponentsPage,
  /* HorarioDeleteDialog,
   */ HorarioUpdatePage
} from './horario.page-object';

const expect = chai.expect;

describe('Horario e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let horarioComponentsPage: HorarioComponentsPage;
  let horarioUpdatePage: HorarioUpdatePage;
  /* let horarioDeleteDialog: HorarioDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Horarios', async () => {
    await navBarPage.goToEntity('horario');
    horarioComponentsPage = new HorarioComponentsPage();
    await browser.wait(ec.visibilityOf(horarioComponentsPage.title), 5000);
    expect(await horarioComponentsPage.getTitle()).to.eq('ensinoApp.horario.home.title');
  });

  it('should load create Horario page', async () => {
    await horarioComponentsPage.clickOnCreateButton();
    horarioUpdatePage = new HorarioUpdatePage();
    expect(await horarioUpdatePage.getPageTitle()).to.eq('ensinoApp.horario.home.createOrEditLabel');
    await horarioUpdatePage.cancel();
  });

  /*  it('should create and save Horarios', async () => {
        const nbButtonsBeforeCreate = await horarioComponentsPage.countDeleteButtons();

        await horarioComponentsPage.clickOnCreateButton();
        await promise.all([
            horarioUpdatePage.setInicioAulaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            horarioUpdatePage.setTerminoAluaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            horarioUpdatePage.setIntervaloInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            horarioUpdatePage.setDiaSemanaInput('diaSemana'),
            horarioUpdatePage.setRegimeCurricularInput('regimeCurricular'),
            horarioUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            horarioUpdatePage.setAnoLectivoInput('2000-12-31'),
            horarioUpdatePage.setCategoriaInput('categoria'),
            horarioUpdatePage.utilizadorSelectLastOption(),
            horarioUpdatePage.turmaSelectLastOption(),
            horarioUpdatePage.professorSelectLastOption(),
            horarioUpdatePage.disciplinaSelectLastOption(),
        ]);
        expect(await horarioUpdatePage.getInicioAulaInput()).to.contain('2001-01-01T02:30', 'Expected inicioAula value to be equals to 2000-12-31');
        expect(await horarioUpdatePage.getTerminoAluaInput()).to.contain('2001-01-01T02:30', 'Expected terminoAlua value to be equals to 2000-12-31');
        expect(await horarioUpdatePage.getIntervaloInput()).to.contain('2001-01-01T02:30', 'Expected intervalo value to be equals to 2000-12-31');
        expect(await horarioUpdatePage.getDiaSemanaInput()).to.eq('diaSemana', 'Expected DiaSemana value to be equals to diaSemana');
        expect(await horarioUpdatePage.getRegimeCurricularInput()).to.eq('regimeCurricular', 'Expected RegimeCurricular value to be equals to regimeCurricular');
        expect(await horarioUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await horarioUpdatePage.getAnoLectivoInput()).to.eq('2000-12-31', 'Expected anoLectivo value to be equals to 2000-12-31');
        expect(await horarioUpdatePage.getCategoriaInput()).to.eq('categoria', 'Expected Categoria value to be equals to categoria');
        await horarioUpdatePage.save();
        expect(await horarioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await horarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Horario', async () => {
        const nbButtonsBeforeDelete = await horarioComponentsPage.countDeleteButtons();
        await horarioComponentsPage.clickOnLastDeleteButton();

        horarioDeleteDialog = new HorarioDeleteDialog();
        expect(await horarioDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.horario.delete.question');
        await horarioDeleteDialog.clickOnConfirmButton();

        expect(await horarioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
