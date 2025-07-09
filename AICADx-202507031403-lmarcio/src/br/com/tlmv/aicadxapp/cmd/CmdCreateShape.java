/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdCreateShape.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdCreateShape extends CmdBase
{
//Private

	/* PromptOption
	*/
	private PromptOptionVO optArquitetura = new PromptOptionVO(AppDefs.DISCIPLINE_ARQ_VAL, AppDefs.DISCIPLINE_ARQ_STR, "ARQ", false);
	private PromptOptionVO optFormas = new PromptOptionVO(AppDefs.DISCIPLINE_FOR_VAL, AppDefs.DISCIPLINE_FOR_STR, "FOR", false);
	private PromptOptionVO optFuracao = new PromptOptionVO(AppDefs.DISCIPLINE_FU_VAL, AppDefs.DISCIPLINE_FU_STR, "FU", false);
	private PromptOptionVO optEntradaEnergia = new PromptOptionVO(AppDefs.DISCIPLINE_EE_VAL, AppDefs.DISCIPLINE_EE_STR, "EE", false);
	private PromptOptionVO optEletrica = new PromptOptionVO(AppDefs.DISCIPLINE_EL_VAL, AppDefs.DISCIPLINE_EL_STR, "EL", false);
	private PromptOptionVO optEsgoto = new PromptOptionVO(AppDefs.DISCIPLINE_ES_VAL, AppDefs.DISCIPLINE_ES_STR, "ES", false);
	private PromptOptionVO optAguasPluviais = new PromptOptionVO(AppDefs.DISCIPLINE_AP_VAL, AppDefs.DISCIPLINE_AP_STR, "AP", false);
	private PromptOptionVO optRedesPublicasDrenagem = new PromptOptionVO(AppDefs.DISCIPLINE_RPD_VAL, AppDefs.DISCIPLINE_RPD_STR, "RPD", false);
	private PromptOptionVO optHidraulica = new PromptOptionVO(AppDefs.DISCIPLINE_H_VAL, AppDefs.DISCIPLINE_H_STR, "H", false);
	private PromptOptionVO optIncendio = new PromptOptionVO(AppDefs.DISCIPLINE_INC_VAL, AppDefs.DISCIPLINE_INC_STR, "INC", false);
	private PromptOptionVO optGas = new PromptOptionVO(AppDefs.DISCIPLINE_G_VAL, AppDefs.DISCIPLINE_G_STR, "G", false);
	private PromptOptionVO optInstalacoesEspeciais = new PromptOptionVO(AppDefs.DISCIPLINE_IE_VAL, AppDefs.DISCIPLINE_IE_STR, "IE", false);
	private PromptOptionVO optTelefonia = new PromptOptionVO(AppDefs.DISCIPLINE_TE_VAL, AppDefs.DISCIPLINE_TE_STR, "TE", false);
	private PromptOptionVO optArCondicionado = new PromptOptionVO(AppDefs.DISCIPLINE_AR_VAL, AppDefs.DISCIPLINE_AR_STR, "AR", false);
	
	/* Methodes */
	
	public int createGeomShape2d(String shapeName, String shapeFile, GeomPoint2d ptIns2d, boolean bAnnotation)
	{
		CadBlockDef blkDef = this.getDoc().getCurrBlockDef();
		
		ArrayList<ShapeOper2d> lsShapeOper2d = new ArrayList<ShapeOper2d>();
		
		int sz = blkDef.getEntityTableSz();
		for(int i = 0; i < sz; i++) {
			CadEntity oEnt = blkDef.getEntityAt(i);
			ArrayList<ShapeOper2d> ls2d = oEnt.toGeomShape2d_planView(true, ptIns2d);
			if(ls2d != null)
				lsShapeOper2d.addAll( ls2d );
		}
		
		GeomShape2d shape2d = new GeomShape2d(bAnnotation);
		shape2d.setLsShpOper2d(lsShapeOper2d);
		
		shape2d.toShape(shapeName, shapeFile);

		return AppDefs.RSOK;
	}
	
//Public

	public CmdCreateShape() {
		super(AppDefs.ACTION_BLOCKS_CREATESHAPE);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Create Shape...");
		
		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(optArquitetura);
		lsPromptOptions.add(optFormas);
		lsPromptOptions.add(optFuracao);
		lsPromptOptions.add(optEntradaEnergia);
		lsPromptOptions.add(optEletrica);
		lsPromptOptions.add(optEsgoto);
		lsPromptOptions.add(optAguasPluviais);
		lsPromptOptions.add(optRedesPublicasDrenagem);
		lsPromptOptions.add(optHidraulica);
		lsPromptOptions.add(optIncendio);
		lsPromptOptions.add(optGas);
		lsPromptOptions.add(optInstalacoesEspeciais);
		lsPromptOptions.add(optTelefonia);
		lsPromptOptions.add(optArCondicionado);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(lsPromptOptions, "Discipline: ");
		if(oKeyword == null) return null;
		
		String shapeName = PromptUtil.getText("Shape name: ");
		if(shapeName == null) return null;
		
		GeomPoint2d ptIns2d = PromptUtil.getFirstPoint2d(null, "Origin: ");
		if(ptIns2d == null) return null;

		GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);
		
		result = new InputParamVO();
		result.initPoint(ptIns3d, oKeyword, shapeName);
		
		return result;
	}

	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		String shapeName = oParam.getShapeName();		
		
		PromptOptionVO oDiscipline = oParam.getDiscipline();
		String shapeFile = shapeName;
		if(oDiscipline != null) {
			shapeFile = oDiscipline.getKeyOption() + shapeFile;
		}
		
		//GEOMPOINT3D
		//
		GeomPoint3d ptIns3d = oParam.getPt0(); 
		GeomPoint2d ptIns2d = new GeomPoint2d(ptIns3d);
		
		int rscode = this.createGeomShape2d(shapeName, shapeFile, ptIns2d, false);
		if(rscode == AppDefs.RSOK) {
			String warnmsg = String.format("Arquivo de simbologia criado com sucesso (=%s).", shapeName);
			PromptUtil.prompt(warnmsg);
		}
		else {
			String warnmsg = String.format("ERR: Falha na criacao do arquivo de simpologia (=%s).", shapeName);
			PromptUtil.prompt(warnmsg);			
		}
	}

}
