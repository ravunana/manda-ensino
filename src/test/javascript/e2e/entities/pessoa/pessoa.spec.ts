import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PessoaComponentsPage, PessoaDeleteDialog, PessoaUpdatePage } from './pessoa.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Pessoa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pessoaComponentsPage: PessoaComponentsPage;
  let pessoaUpdatePage: PessoaUpdatePage;
  let pessoaDeleteDialog: PessoaDeleteDialog;
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

  it('should load Pessoas', async () => {
    await navBarPage.goToEntity('pessoa');
    pessoaComponentsPage = new PessoaComponentsPage();
    await browser.wait(ec.visibilityOf(pessoaComponentsPage.title), 5000);
    expect(await pessoaComponentsPage.getTitle()).to.eq('ensinoApp.pessoa.home.title');
  });

  it('should load create Pessoa page', async () => {
    await pessoaComponentsPage.clickOnCreateButton();
    pessoaUpdatePage = new PessoaUpdatePage();
    expect(await pessoaUpdatePage.getPageTitle()).to.eq('ensinoApp.pessoa.home.createOrEditLabel');
    await pessoaUpdatePage.cancel();
  });

  it('should create and save Pessoas', async () => {
    const nbButtonsBeforeCreate = await pessoaComponentsPage.countDeleteButtons();

    await pessoaComponentsPage.clickOnCreateButton();
    await promise.all([
      pessoaUpdatePage.setTipoPessoaInput('tipoPessoa'),
      pessoaUpdatePage.setNomeInput('nome'),
      pessoaUpdatePage.setImagemInput(absolutePath),
      pessoaUpdatePage.setPaiInput('pai'),
      pessoaUpdatePage.setMaeInput('mae'),
      pessoaUpdatePage.setNascimentoInput('2000-12-31'),
      pessoaUpdatePage.utilizadorSelectLastOption()
    ]);
    expect(await pessoaUpdatePage.getTipoPessoaInput()).to.eq('tipoPessoa', 'Expected TipoPessoa value to be equals to tipoPessoa');
    expect(await pessoaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await pessoaUpdatePage.getImagemInput()).to.endsWith(
      fileNameToUpload,
      'Expected Imagem value to be end with ' + fileNameToUpload
    );
    expect(await pessoaUpdatePage.getPaiInput()).to.eq('pai', 'Expected Pai value to be equals to pai');
    expect(await pessoaUpdatePage.getMaeInput()).to.eq('mae', 'Expected Mae value to be equals to mae');
    expect(await pessoaUpdatePage.getNascimentoInput()).to.eq('2000-12-31', 'Expected nascimento value to be equals to 2000-12-31');
    await pessoaUpdatePage.save();
    expect(await pessoaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await pessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Pessoa', async () => {
    const nbButtonsBeforeDelete = await pessoaComponentsPage.countDeleteButtons();
    await pessoaComponentsPage.clickOnLastDeleteButton();

    pessoaDeleteDialog = new PessoaDeleteDialog();
    expect(await pessoaDeleteDialog.getDialogTitle()).to.eq('ensinoApp.pessoa.delete.question');
    await pessoaDeleteDialog.clickOnConfirmButton();

    expect(await pessoaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
