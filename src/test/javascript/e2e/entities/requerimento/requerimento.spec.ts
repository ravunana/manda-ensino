import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RequerimentoComponentsPage, RequerimentoDeleteDialog, RequerimentoUpdatePage } from './requerimento.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Requerimento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let requerimentoComponentsPage: RequerimentoComponentsPage;
  let requerimentoUpdatePage: RequerimentoUpdatePage;
  let requerimentoDeleteDialog: RequerimentoDeleteDialog;
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

  it('should load Requerimentos', async () => {
    await navBarPage.goToEntity('requerimento');
    requerimentoComponentsPage = new RequerimentoComponentsPage();
    await browser.wait(ec.visibilityOf(requerimentoComponentsPage.title), 5000);
    expect(await requerimentoComponentsPage.getTitle()).to.eq('ensinoApp.requerimento.home.title');
  });

  it('should load create Requerimento page', async () => {
    await requerimentoComponentsPage.clickOnCreateButton();
    requerimentoUpdatePage = new RequerimentoUpdatePage();
    expect(await requerimentoUpdatePage.getPageTitle()).to.eq('ensinoApp.requerimento.home.createOrEditLabel');
    await requerimentoUpdatePage.cancel();
  });

  it('should create and save Requerimentos', async () => {
    const nbButtonsBeforeCreate = await requerimentoComponentsPage.countDeleteButtons();

    await requerimentoComponentsPage.clickOnCreateButton();
    await promise.all([
      requerimentoUpdatePage.setRequerimentoInput(absolutePath),
      requerimentoUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      requerimentoUpdatePage.setStatusRequerimentoInput('statusRequerimento'),
      requerimentoUpdatePage.utilizadorSelectLastOption(),
      requerimentoUpdatePage.categoriaSelectLastOption(),
      requerimentoUpdatePage.alunoSelectLastOption()
    ]);
    expect(await requerimentoUpdatePage.getRequerimentoInput()).to.endsWith(
      fileNameToUpload,
      'Expected Requerimento value to be end with ' + fileNameToUpload
    );
    expect(await requerimentoUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
    expect(await requerimentoUpdatePage.getStatusRequerimentoInput()).to.eq(
      'statusRequerimento',
      'Expected StatusRequerimento value to be equals to statusRequerimento'
    );
    await requerimentoUpdatePage.save();
    expect(await requerimentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await requerimentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Requerimento', async () => {
    const nbButtonsBeforeDelete = await requerimentoComponentsPage.countDeleteButtons();
    await requerimentoComponentsPage.clickOnLastDeleteButton();

    requerimentoDeleteDialog = new RequerimentoDeleteDialog();
    expect(await requerimentoDeleteDialog.getDialogTitle()).to.eq('ensinoApp.requerimento.delete.question');
    await requerimentoDeleteDialog.clickOnConfirmButton();

    expect(await requerimentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
