import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AreaFormacaoComponentsPage, AreaFormacaoDeleteDialog, AreaFormacaoUpdatePage } from './area-formacao.page-object';

const expect = chai.expect;

describe('AreaFormacao e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let areaFormacaoComponentsPage: AreaFormacaoComponentsPage;
  let areaFormacaoUpdatePage: AreaFormacaoUpdatePage;
  let areaFormacaoDeleteDialog: AreaFormacaoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AreaFormacaos', async () => {
    await navBarPage.goToEntity('area-formacao');
    areaFormacaoComponentsPage = new AreaFormacaoComponentsPage();
    await browser.wait(ec.visibilityOf(areaFormacaoComponentsPage.title), 5000);
    expect(await areaFormacaoComponentsPage.getTitle()).to.eq('ensinoApp.areaFormacao.home.title');
  });

  it('should load create AreaFormacao page', async () => {
    await areaFormacaoComponentsPage.clickOnCreateButton();
    areaFormacaoUpdatePage = new AreaFormacaoUpdatePage();
    expect(await areaFormacaoUpdatePage.getPageTitle()).to.eq('ensinoApp.areaFormacao.home.createOrEditLabel');
    await areaFormacaoUpdatePage.cancel();
  });

  it('should create and save AreaFormacaos', async () => {
    const nbButtonsBeforeCreate = await areaFormacaoComponentsPage.countDeleteButtons();

    await areaFormacaoComponentsPage.clickOnCreateButton();
    await promise.all([areaFormacaoUpdatePage.setNomeInput('nome'), areaFormacaoUpdatePage.setCompetenciasInput('competencias')]);
    expect(await areaFormacaoUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await areaFormacaoUpdatePage.getCompetenciasInput()).to.eq(
      'competencias',
      'Expected Competencias value to be equals to competencias'
    );
    await areaFormacaoUpdatePage.save();
    expect(await areaFormacaoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await areaFormacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AreaFormacao', async () => {
    const nbButtonsBeforeDelete = await areaFormacaoComponentsPage.countDeleteButtons();
    await areaFormacaoComponentsPage.clickOnLastDeleteButton();

    areaFormacaoDeleteDialog = new AreaFormacaoDeleteDialog();
    expect(await areaFormacaoDeleteDialog.getDialogTitle()).to.eq('ensinoApp.areaFormacao.delete.question');
    await areaFormacaoDeleteDialog.clickOnConfirmButton();

    expect(await areaFormacaoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
