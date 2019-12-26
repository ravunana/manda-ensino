import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DocumentoMatriculaComponentsPage,
  DocumentoMatriculaDeleteDialog,
  DocumentoMatriculaUpdatePage
} from './documento-matricula.page-object';

const expect = chai.expect;

describe('DocumentoMatricula e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let documentoMatriculaComponentsPage: DocumentoMatriculaComponentsPage;
  let documentoMatriculaUpdatePage: DocumentoMatriculaUpdatePage;
  let documentoMatriculaDeleteDialog: DocumentoMatriculaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DocumentoMatriculas', async () => {
    await navBarPage.goToEntity('documento-matricula');
    documentoMatriculaComponentsPage = new DocumentoMatriculaComponentsPage();
    await browser.wait(ec.visibilityOf(documentoMatriculaComponentsPage.title), 5000);
    expect(await documentoMatriculaComponentsPage.getTitle()).to.eq('ensinoApp.documentoMatricula.home.title');
  });

  it('should load create DocumentoMatricula page', async () => {
    await documentoMatriculaComponentsPage.clickOnCreateButton();
    documentoMatriculaUpdatePage = new DocumentoMatriculaUpdatePage();
    expect(await documentoMatriculaUpdatePage.getPageTitle()).to.eq('ensinoApp.documentoMatricula.home.createOrEditLabel');
    await documentoMatriculaUpdatePage.cancel();
  });

  it('should create and save DocumentoMatriculas', async () => {
    const nbButtonsBeforeCreate = await documentoMatriculaComponentsPage.countDeleteButtons();

    await documentoMatriculaComponentsPage.clickOnCreateButton();
    await promise.all([
      documentoMatriculaUpdatePage.setDescricaoInput('descricao'),
      documentoMatriculaUpdatePage.setAnoLectivoInput('5'),
      documentoMatriculaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      documentoMatriculaUpdatePage.matriculaSelectLastOption()
    ]);
    const selectedFotografia = documentoMatriculaUpdatePage.getFotografiaInput();
    if (await selectedFotografia.isSelected()) {
      await documentoMatriculaUpdatePage.getFotografiaInput().click();
      expect(await documentoMatriculaUpdatePage.getFotografiaInput().isSelected(), 'Expected fotografia not to be selected').to.be.false;
    } else {
      await documentoMatriculaUpdatePage.getFotografiaInput().click();
      expect(await documentoMatriculaUpdatePage.getFotografiaInput().isSelected(), 'Expected fotografia to be selected').to.be.true;
    }
    const selectedCertificado = documentoMatriculaUpdatePage.getCertificadoInput();
    if (await selectedCertificado.isSelected()) {
      await documentoMatriculaUpdatePage.getCertificadoInput().click();
      expect(await documentoMatriculaUpdatePage.getCertificadoInput().isSelected(), 'Expected certificado not to be selected').to.be.false;
    } else {
      await documentoMatriculaUpdatePage.getCertificadoInput().click();
      expect(await documentoMatriculaUpdatePage.getCertificadoInput().isSelected(), 'Expected certificado to be selected').to.be.true;
    }
    const selectedBilhete = documentoMatriculaUpdatePage.getBilheteInput();
    if (await selectedBilhete.isSelected()) {
      await documentoMatriculaUpdatePage.getBilheteInput().click();
      expect(await documentoMatriculaUpdatePage.getBilheteInput().isSelected(), 'Expected bilhete not to be selected').to.be.false;
    } else {
      await documentoMatriculaUpdatePage.getBilheteInput().click();
      expect(await documentoMatriculaUpdatePage.getBilheteInput().isSelected(), 'Expected bilhete to be selected').to.be.true;
    }
    const selectedResenciamentoMilitar = documentoMatriculaUpdatePage.getResenciamentoMilitarInput();
    if (await selectedResenciamentoMilitar.isSelected()) {
      await documentoMatriculaUpdatePage.getResenciamentoMilitarInput().click();
      expect(
        await documentoMatriculaUpdatePage.getResenciamentoMilitarInput().isSelected(),
        'Expected resenciamentoMilitar not to be selected'
      ).to.be.false;
    } else {
      await documentoMatriculaUpdatePage.getResenciamentoMilitarInput().click();
      expect(await documentoMatriculaUpdatePage.getResenciamentoMilitarInput().isSelected(), 'Expected resenciamentoMilitar to be selected')
        .to.be.true;
    }
    const selectedCartaoVacina = documentoMatriculaUpdatePage.getCartaoVacinaInput();
    if (await selectedCartaoVacina.isSelected()) {
      await documentoMatriculaUpdatePage.getCartaoVacinaInput().click();
      expect(await documentoMatriculaUpdatePage.getCartaoVacinaInput().isSelected(), 'Expected cartaoVacina not to be selected').to.be
        .false;
    } else {
      await documentoMatriculaUpdatePage.getCartaoVacinaInput().click();
      expect(await documentoMatriculaUpdatePage.getCartaoVacinaInput().isSelected(), 'Expected cartaoVacina to be selected').to.be.true;
    }
    const selectedAtestadoMedico = documentoMatriculaUpdatePage.getAtestadoMedicoInput();
    if (await selectedAtestadoMedico.isSelected()) {
      await documentoMatriculaUpdatePage.getAtestadoMedicoInput().click();
      expect(await documentoMatriculaUpdatePage.getAtestadoMedicoInput().isSelected(), 'Expected atestadoMedico not to be selected').to.be
        .false;
    } else {
      await documentoMatriculaUpdatePage.getAtestadoMedicoInput().click();
      expect(await documentoMatriculaUpdatePage.getAtestadoMedicoInput().isSelected(), 'Expected atestadoMedico to be selected').to.be.true;
    }
    const selectedFichaTrnasferencia = documentoMatriculaUpdatePage.getFichaTrnasferenciaInput();
    if (await selectedFichaTrnasferencia.isSelected()) {
      await documentoMatriculaUpdatePage.getFichaTrnasferenciaInput().click();
      expect(await documentoMatriculaUpdatePage.getFichaTrnasferenciaInput().isSelected(), 'Expected fichaTrnasferencia not to be selected')
        .to.be.false;
    } else {
      await documentoMatriculaUpdatePage.getFichaTrnasferenciaInput().click();
      expect(await documentoMatriculaUpdatePage.getFichaTrnasferenciaInput().isSelected(), 'Expected fichaTrnasferencia to be selected').to
        .be.true;
    }
    const selectedHistoricoEscolar = documentoMatriculaUpdatePage.getHistoricoEscolarInput();
    if (await selectedHistoricoEscolar.isSelected()) {
      await documentoMatriculaUpdatePage.getHistoricoEscolarInput().click();
      expect(await documentoMatriculaUpdatePage.getHistoricoEscolarInput().isSelected(), 'Expected historicoEscolar not to be selected').to
        .be.false;
    } else {
      await documentoMatriculaUpdatePage.getHistoricoEscolarInput().click();
      expect(await documentoMatriculaUpdatePage.getHistoricoEscolarInput().isSelected(), 'Expected historicoEscolar to be selected').to.be
        .true;
    }
    const selectedCedula = documentoMatriculaUpdatePage.getCedulaInput();
    if (await selectedCedula.isSelected()) {
      await documentoMatriculaUpdatePage.getCedulaInput().click();
      expect(await documentoMatriculaUpdatePage.getCedulaInput().isSelected(), 'Expected cedula not to be selected').to.be.false;
    } else {
      await documentoMatriculaUpdatePage.getCedulaInput().click();
      expect(await documentoMatriculaUpdatePage.getCedulaInput().isSelected(), 'Expected cedula to be selected').to.be.true;
    }
    expect(await documentoMatriculaUpdatePage.getDescricaoInput()).to.eq('descricao', 'Expected Descricao value to be equals to descricao');
    expect(await documentoMatriculaUpdatePage.getAnoLectivoInput()).to.eq('5', 'Expected anoLectivo value to be equals to 5');
    expect(await documentoMatriculaUpdatePage.getDataInput()).to.contain(
      '2001-01-01T02:30',
      'Expected data value to be equals to 2000-12-31'
    );
    await documentoMatriculaUpdatePage.save();
    expect(await documentoMatriculaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await documentoMatriculaComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DocumentoMatricula', async () => {
    const nbButtonsBeforeDelete = await documentoMatriculaComponentsPage.countDeleteButtons();
    await documentoMatriculaComponentsPage.clickOnLastDeleteButton();

    documentoMatriculaDeleteDialog = new DocumentoMatriculaDeleteDialog();
    expect(await documentoMatriculaDeleteDialog.getDialogTitle()).to.eq('ensinoApp.documentoMatricula.delete.question');
    await documentoMatriculaDeleteDialog.clickOnConfirmButton();

    expect(await documentoMatriculaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
