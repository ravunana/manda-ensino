import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AssinaturaDigitalComponentsPage,
  /* AssinaturaDigitalDeleteDialog,
   */ AssinaturaDigitalUpdatePage
} from './assinatura-digital.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('AssinaturaDigital e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let assinaturaDigitalComponentsPage: AssinaturaDigitalComponentsPage;
  let assinaturaDigitalUpdatePage: AssinaturaDigitalUpdatePage;
  /* let assinaturaDigitalDeleteDialog: AssinaturaDigitalDeleteDialog; */
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

  it('should load AssinaturaDigitals', async () => {
    await navBarPage.goToEntity('assinatura-digital');
    assinaturaDigitalComponentsPage = new AssinaturaDigitalComponentsPage();
    await browser.wait(ec.visibilityOf(assinaturaDigitalComponentsPage.title), 5000);
    expect(await assinaturaDigitalComponentsPage.getTitle()).to.eq('ensinoApp.assinaturaDigital.home.title');
  });

  it('should load create AssinaturaDigital page', async () => {
    await assinaturaDigitalComponentsPage.clickOnCreateButton();
    assinaturaDigitalUpdatePage = new AssinaturaDigitalUpdatePage();
    expect(await assinaturaDigitalUpdatePage.getPageTitle()).to.eq('ensinoApp.assinaturaDigital.home.createOrEditLabel');
    await assinaturaDigitalUpdatePage.cancel();
  });

  /*  it('should create and save AssinaturaDigitals', async () => {
        const nbButtonsBeforeCreate = await assinaturaDigitalComponentsPage.countDeleteButtons();

        await assinaturaDigitalComponentsPage.clickOnCreateButton();
        await promise.all([
            assinaturaDigitalUpdatePage.setTipoInput('tipo'),
            assinaturaDigitalUpdatePage.setAssinaturaInput(absolutePath),
            assinaturaDigitalUpdatePage.setHashcodeInput('hashcode'),
            assinaturaDigitalUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            assinaturaDigitalUpdatePage.instituicaoSelectLastOption(),
        ]);
        expect(await assinaturaDigitalUpdatePage.getTipoInput()).to.eq('tipo', 'Expected Tipo value to be equals to tipo');
        expect(await assinaturaDigitalUpdatePage.getAssinaturaInput()).to.endsWith(fileNameToUpload, 'Expected Assinatura value to be end with ' + fileNameToUpload);
        expect(await assinaturaDigitalUpdatePage.getHashcodeInput()).to.eq('hashcode', 'Expected Hashcode value to be equals to hashcode');
        expect(await assinaturaDigitalUpdatePage.getDataInput()).to.contain('2001-01-01T02:30', 'Expected data value to be equals to 2000-12-31');
        await assinaturaDigitalUpdatePage.save();
        expect(await assinaturaDigitalUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await assinaturaDigitalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last AssinaturaDigital', async () => {
        const nbButtonsBeforeDelete = await assinaturaDigitalComponentsPage.countDeleteButtons();
        await assinaturaDigitalComponentsPage.clickOnLastDeleteButton();

        assinaturaDigitalDeleteDialog = new AssinaturaDigitalDeleteDialog();
        expect(await assinaturaDigitalDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.assinaturaDigital.delete.question');
        await assinaturaDigitalDeleteDialog.clickOnConfirmButton();

        expect(await assinaturaDigitalComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
