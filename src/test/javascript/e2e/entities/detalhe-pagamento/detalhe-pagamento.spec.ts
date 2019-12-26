import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DetalhePagamentoComponentsPage,
  /* DetalhePagamentoDeleteDialog,
   */ DetalhePagamentoUpdatePage
} from './detalhe-pagamento.page-object';

const expect = chai.expect;

describe('DetalhePagamento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let detalhePagamentoComponentsPage: DetalhePagamentoComponentsPage;
  let detalhePagamentoUpdatePage: DetalhePagamentoUpdatePage;
  /* let detalhePagamentoDeleteDialog: DetalhePagamentoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DetalhePagamentos', async () => {
    await navBarPage.goToEntity('detalhe-pagamento');
    detalhePagamentoComponentsPage = new DetalhePagamentoComponentsPage();
    await browser.wait(ec.visibilityOf(detalhePagamentoComponentsPage.title), 5000);
    expect(await detalhePagamentoComponentsPage.getTitle()).to.eq('ensinoApp.detalhePagamento.home.title');
  });

  it('should load create DetalhePagamento page', async () => {
    await detalhePagamentoComponentsPage.clickOnCreateButton();
    detalhePagamentoUpdatePage = new DetalhePagamentoUpdatePage();
    expect(await detalhePagamentoUpdatePage.getPageTitle()).to.eq('ensinoApp.detalhePagamento.home.createOrEditLabel');
    await detalhePagamentoUpdatePage.cancel();
  });

  /*  it('should create and save DetalhePagamentos', async () => {
        const nbButtonsBeforeCreate = await detalhePagamentoComponentsPage.countDeleteButtons();

        await detalhePagamentoComponentsPage.clickOnCreateButton();
        await promise.all([
            detalhePagamentoUpdatePage.setDescricaoInput('descricao'),
            detalhePagamentoUpdatePage.setQuantidadeInput('5'),
            detalhePagamentoUpdatePage.setValorInput('5'),
            detalhePagamentoUpdatePage.setDescontoInput('5'),
            detalhePagamentoUpdatePage.setMultaInput('5'),
            detalhePagamentoUpdatePage.setJuroInput('5'),
            detalhePagamentoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            detalhePagamentoUpdatePage.setVencimentoInput('2000-12-31'),
            detalhePagamentoUpdatePage.utilizadorSelectLastOption(),
            detalhePagamentoUpdatePage.emolumentoSelectLastOption(),
            detalhePagamentoUpdatePage.depositoSelectLastOption(),
        ]);
        expect(await detalhePagamentoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        const selectedMensalidade = detalhePagamentoUpdatePage.getMensalidadeInput();
        if (await selectedMensalidade.isSelected()) {
            await detalhePagamentoUpdatePage.getMensalidadeInput().click();
            expect(await detalhePagamentoUpdatePage.getMensalidadeInput().isSelected(), 'Expected mensalidade not to be selected').to.be.false;
        } else {
            await detalhePagamentoUpdatePage.getMensalidadeInput().click();
            expect(await detalhePagamentoUpdatePage.getMensalidadeInput().isSelected(), 'Expected mensalidade to be selected').to.be.true;
        }
        expect(await detalhePagamentoUpdatePage.getQuantidadeInput()).to.eq('5', 'Expected quantidade value to be equals to 5');
        expect(await detalhePagamentoUpdatePage.getValorInput()).to.eq('5', 'Expected valor value to be equals to 5');
        expect(await detalhePagamentoUpdatePage.getDescontoInput()).to.eq('5', 'Expected desconto value to be equals to 5');
        expect(await detalhePagamentoUpdatePage.getMultaInput()).to.eq('5', 'Expected multa value to be equals to 5');
        expect(await detalhePagamentoUpdatePage.getJuroInput()).to.eq('5', 'Expected juro value to be equals to 5');
        expect(await detalhePagamentoUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        expect(await detalhePagamentoUpdatePage.getVencimentoInput()).to.eq('2000-12-31', 'Expected vencimento value to be equals to 2000-12-31');
        const selectedQuitado = detalhePagamentoUpdatePage.getQuitadoInput();
        if (await selectedQuitado.isSelected()) {
            await detalhePagamentoUpdatePage.getQuitadoInput().click();
            expect(await detalhePagamentoUpdatePage.getQuitadoInput().isSelected(), 'Expected quitado not to be selected').to.be.false;
        } else {
            await detalhePagamentoUpdatePage.getQuitadoInput().click();
            expect(await detalhePagamentoUpdatePage.getQuitadoInput().isSelected(), 'Expected quitado to be selected').to.be.true;
        }
        await detalhePagamentoUpdatePage.save();
        expect(await detalhePagamentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await detalhePagamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last DetalhePagamento', async () => {
        const nbButtonsBeforeDelete = await detalhePagamentoComponentsPage.countDeleteButtons();
        await detalhePagamentoComponentsPage.clickOnLastDeleteButton();

        detalhePagamentoDeleteDialog = new DetalhePagamentoDeleteDialog();
        expect(await detalhePagamentoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.detalhePagamento.delete.question');
        await detalhePagamentoDeleteDialog.clickOnConfirmButton();

        expect(await detalhePagamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
