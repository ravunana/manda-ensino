import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AulaComponentsPage,
  /* AulaDeleteDialog,
   */ AulaUpdatePage
} from './aula.page-object';

const expect = chai.expect;

describe('Aula e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let aulaComponentsPage: AulaComponentsPage;
  let aulaUpdatePage: AulaUpdatePage;
  /* let aulaDeleteDialog: AulaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Aulas', async () => {
    await navBarPage.goToEntity('aula');
    aulaComponentsPage = new AulaComponentsPage();
    await browser.wait(ec.visibilityOf(aulaComponentsPage.title), 5000);
    expect(await aulaComponentsPage.getTitle()).to.eq('ensinoApp.aula.home.title');
  });

  it('should load create Aula page', async () => {
    await aulaComponentsPage.clickOnCreateButton();
    aulaUpdatePage = new AulaUpdatePage();
    expect(await aulaUpdatePage.getPageTitle()).to.eq('ensinoApp.aula.home.createOrEditLabel');
    await aulaUpdatePage.cancel();
  });

  /*  it('should create and save Aulas', async () => {
        const nbButtonsBeforeCreate = await aulaComponentsPage.countDeleteButtons();

        await aulaComponentsPage.clickOnCreateButton();
        await promise.all([
            aulaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            aulaUpdatePage.setSumarioInput('sumario'),
            aulaUpdatePage.setLicaoInput('5'),
            aulaUpdatePage.utilizadorSelectLastOption(),
            // aulaUpdatePage.planoAulaSelectLastOption(),
            aulaUpdatePage.turmaSelectLastOption(),
        ]);
        expect(await aulaUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await aulaUpdatePage.getSumarioInput()).to.eq('sumario', 'Expected Sumario value to be equals to sumario');
        expect(await aulaUpdatePage.getLicaoInput()).to.eq('5', 'Expected licao value to be equals to 5');
        const selectedDada = aulaUpdatePage.getDadaInput();
        if (await selectedDada.isSelected()) {
            await aulaUpdatePage.getDadaInput().click();
            expect(await aulaUpdatePage.getDadaInput().isSelected(), 'Expected dada not to be selected').to.be.false;
        } else {
            await aulaUpdatePage.getDadaInput().click();
            expect(await aulaUpdatePage.getDadaInput().isSelected(), 'Expected dada to be selected').to.be.true;
        }
        await aulaUpdatePage.save();
        expect(await aulaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await aulaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Aula', async () => {
        const nbButtonsBeforeDelete = await aulaComponentsPage.countDeleteButtons();
        await aulaComponentsPage.clickOnLastDeleteButton();

        aulaDeleteDialog = new AulaDeleteDialog();
        expect(await aulaDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.aula.delete.question');
        await aulaDeleteDialog.clickOnConfirmButton();

        expect(await aulaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
