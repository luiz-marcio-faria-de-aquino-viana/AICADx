/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * FileDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import java.util.Date;

public class FileDataVO extends ItemDataVO
{
//Private
	private String fullFileName;
	private String fileName;
	private String extension;
	private Date dataModificacao;
	private boolean isFile;

//Public
	
	public FileDataVO(
		String fullFileName,
		String fileName,
		String extension,
		Date dataModificacao,
		boolean isFile)
	{
		super(fullFileName, fileName);
		
		this.fullFileName = fullFileName;
		this.fileName = fileName;
		this.extension = extension;
		this.dataModificacao = dataModificacao;		
		this.isFile = isFile;
	}

	/* Methodes */
	
	@Override
	public String toString() 
	{
		return this.getFileName();
	}
	
	/* Getters/Setters */

	public String getFullFileName() {
		return fullFileName;
	}

	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Date getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	
}
