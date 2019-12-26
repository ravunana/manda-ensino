import { element, by, ElementFinder } from 'protractor';

export class DocumentoMatriculaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('rv-documento-matricula div table .btn-danger'));
  title = element.all(by.css('rv-documento-matricula div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class DocumentoMatriculaUpdatePage {
  pageTitle = element(by.id('rv-documento-matricula-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fotografiaInput = element(by.id('field_fotografia'));
  certificadoInput = element(by.id('field_certificado'));
  bilheteInput = element(by.id('field_bilhete'));
  resenciamentoMilitarInput = element(by.id('field_resenciamentoMilitar'));
  cartaoVacinaInput = element(by.id('field_cartaoVacina'));
  atestadoMedicoInput = element(by.id('field_atestadoMedico'));
  fichaTrnasferenciaInput = element(by.id('field_fichaTrnasferencia'));
  historicoEscolarInput = element(by.id('field_historicoEscolar'));
  cedulaInput = element(by.id('field_cedula'));
  descricaoInput = element(by.id('field_descricao'));
  anoLectivoInput = element(by.id('field_anoLectivo'));
  dataInput = element(by.id('field_data'));
  matriculaSelect = element(by.id('field_matricula'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  getFotografiaInput(): ElementFinder {
    return this.fotografiaInput;
  }
  getCertificadoInput(): ElementFinder {
    return this.certificadoInput;
  }
  getBilheteInput(): ElementFinder {
    return this.bilheteInput;
  }
  getResenciamentoMilitarInput(): ElementFinder {
    return this.resenciamentoMilitarInput;
  }
  getCartaoVacinaInput(): ElementFinder {
    return this.cartaoVacinaInput;
  }
  getAtestadoMedicoInput(): ElementFinder {
    return this.atestadoMedicoInput;
  }
  getFichaTrnasferenciaInput(): ElementFinder {
    return this.fichaTrnasferenciaInput;
  }
  getHistoricoEscolarInput(): ElementFinder {
    return this.historicoEscolarInput;
  }
  getCedulaInput(): ElementFinder {
    return this.cedulaInput;
  }
  async setDescricaoInput(descricao: string): Promise<void> {
    await this.descricaoInput.sendKeys(descricao);
  }

  async getDescricaoInput(): Promise<string> {
    return await this.descricaoInput.getAttribute('value');
  }

  async setAnoLectivoInput(anoLectivo: string): Promise<void> {
    await this.anoLectivoInput.sendKeys(anoLectivo);
  }

  async getAnoLectivoInput(): Promise<string> {
    return await this.anoLectivoInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async matriculaSelectLastOption(): Promise<void> {
    await this.matriculaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async matriculaSelectOption(option: string): Promise<void> {
    await this.matriculaSelect.sendKeys(option);
  }

  getMatriculaSelect(): ElementFinder {
    return this.matriculaSelect;
  }

  async getMatriculaSelectedOption(): Promise<string> {
    return await this.matriculaSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DocumentoMatriculaDeleteDialog {
  private dialogTitle = element(by.id('rv-delete-documentoMatricula-heading'));
  private confirmButton = element(by.id('rv-confirm-delete-documentoMatricula'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
