import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DepositoComponentsPage,
  /* DepositoDeleteDialog,
   */ DepositoUpdatePage
} from './deposito.page-object';

const expect = chai.expect;

describe('Deposito e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let depositoComponentsPage: DepositoComponentsPage;
  let depositoUpdatePage: DepositoUpdatePage;
  /* let depositoDeleteDialog: DepositoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Depositos', async () => {
    await navBarPage.goToEntity('deposito');
    depositoComponentsPage = new DepositoComponentsPage();
    await browser.wait(ec.visibilityOf(depositoComponentsPage.title), 5000);
    expect(await depositoComponentsPage.getTitle()).to.eq('ensinoApp.deposito.home.title');
  });

  it('should load create Deposito page', async () => {
    await depositoComponentsPage.clickOnCreateButton();
    depositoUpdatePage = new DepositoUpdatePage();
    expect(await depositoUpdatePage.getPageTitle()).to.eq('ensinoApp.deposito.home.createOrEditLabel');
    await depositoUpdatePage.cancel();
  });

  /*  it('should create and save Depositos', async () => {
        const nbButtonsBeforeCreate = await depositoComponentsPage.countDeleteButtons();

        await depositoComponentsPage.clickOnCreateButton();
        await promise.all([
            depositoUpdatePage.setNumeroTalaoInput('numeroTalao'),
            depositoUpdatePage.setDataDepositoInput('2000-12-31'),
            depositoUpdatePage.setValorInput('5'),
            depositoUpdatePage.setSaldoInput('5'),
            depositoUpdatePage.utilizadorSelectLastOption(),
            depositoUpdatePage.meioLiquidacaoSelectLastOption(),
            depositoUpdatePage.alunoSelectLastOption(),
            depositoUpdatePage.contaSelectLastOption(),
        ]);
        expect(await depositoUpdatePage.getNumeroTalaoInput()).to.eq('numeroTalao', 'Expected NumeroTalao value to be equals to numeroTalao');
        expect(await depositoUpdatePage.getDataDepositoInput()).to.eq('2000-12-31', 'Expected dataDeposito value to be equals to 2000-12-31');
        expect(await depositoUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await depositoUpdatePage.getSaldoInput()).to.eq('5', 'Expected saldo value to be equals to 5');
        await depositoUpdatePage.save();
        expect(await depositoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await depositoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Deposito', async () => {
        const nbButtonsBeforeDelete = await depositoComponentsPage.countDeleteButtons();
        await depositoComponentsPage.clickOnLastDeleteButton();

        depositoDeleteDialog = new DepositoDeleteDialog();
        expect(await depositoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.deposito.delete.question');
        await depositoDeleteDialog.clickOnConfirmButton();

        expect(await depositoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
