/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ICmdBase.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public interface ICmdBase 
{
//Public
	
	public String getCommandName();
	
	public void initCommand();
	
	public int executeCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc);	
	
	public void finishCommand();

	/* ABSTRACT */
	
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam);

	public void doCommand();
	
	public void doExecuteCommand(AppMain app, MainFrame frm, AppCadMain cad, CadDocumentDef doc, String[] args);
	
}
