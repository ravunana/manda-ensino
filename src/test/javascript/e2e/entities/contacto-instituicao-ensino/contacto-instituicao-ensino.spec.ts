import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContactoInstituicaoEnsinoComponentsPage,
  /* ContactoInstituicaoEnsinoDeleteDialog,
   */ ContactoInstituicaoEnsinoUpdatePage
} from './contacto-instituicao-ensino.page-object';

const expect = chai.expect;

describe('ContactoInstituicaoEnsino e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contactoInstituicaoEnsinoComponentsPage: ContactoInstituicaoEnsinoComponentsPage;
  let contactoInstituicaoEnsinoUpdatePage: ContactoInstituicaoEnsinoUpdatePage;
  /* let contactoInstituicaoEnsinoDeleteDialog: ContactoInstituicaoEnsinoDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContactoInstituicaoEnsinos', async () => {
    await navBarPage.goToEntity('contacto-instituicao-ensino');
    contactoInstituicaoEnsinoComponentsPage = new ContactoInstituicaoEnsinoComponentsPage();
    await browser.wait(ec.visibilityOf(contactoInstituicaoEnsinoComponentsPage.title), 5000);
    expect(await contactoInstituicaoEnsinoComponentsPage.getTitle()).to.eq('ensinoApp.contactoInstituicaoEnsino.home.title');
  });

  it('should load create ContactoInstituicaoEnsino page', async () => {
    await contactoInstituicaoEnsinoComponentsPage.clickOnCreateButton();
    contactoInstituicaoEnsinoUpdatePage = new ContactoInstituicaoEnsinoUpdatePage();
    expect(await contactoInstituicaoEnsinoUpdatePage.getPageTitle()).to.eq('ensinoApp.contactoInstituicaoEnsino.home.createOrEditLabel');
    await contactoInstituicaoEnsinoUpdatePage.cancel();
  });

  /*  it('should create and save ContactoInstituicaoEnsinos', async () => {
        const nbButtonsBeforeCreate = await contactoInstituicaoEnsinoComponentsPage.countDeleteButtons();

        await contactoInstituicaoEnsinoComponentsPage.clickOnCreateButton();
        await promise.all([
            contactoInstituicaoEnsinoUpdatePage.setTipoContactoInput('tipoContacto'),
            contactoInstituicaoEnsinoUpdatePage.setDescricaoInput('descricao'),
            contactoInstituicaoEnsinoUpdatePage.setContactoInput('contacto'),
            contactoInstituicaoEnsinoUpdatePage.instituicaoEnsinoSelectLastOption(),
        ]);
        expect(await contactoInstituicaoEnsinoUpdatePage.getTipoContactoInput()).to.eq('tipoContacto', 'Expected TipoContacto value to be equals to tipoContacto');
        expect(await contactoInstituicaoEnsinoUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
        expect(await contactoInstituicaoEnsinoUpdatePage.getContactoInput()).to.eq('contacto', 'Expected Contacto value to be equals to contacto');
        await contactoInstituicaoEnsinoUpdatePage.save();
        expect(await contactoInstituicaoEnsinoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await contactoInstituicaoEnsinoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last ContactoInstituicaoEnsino', async () => {
        const nbButtonsBeforeDelete = await contactoInstituicaoEnsinoComponentsPage.countDeleteButtons();
        await contactoInstituicaoEnsinoComponentsPage.clickOnLastDeleteButton();

        contactoInstituicaoEnsinoDeleteDialog = new ContactoInstituicaoEnsinoDeleteDialog();
        expect(await contactoInstituicaoEnsinoDeleteDialog.getDialogTitle())
            .to.eq('ensinoApp.contactoInstituicaoEnsino.delete.question');
        await contactoInstituicaoEnsinoDeleteDialog.clickOnConfirmButton();

        expect(await contactoInstituicaoEnsinoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
