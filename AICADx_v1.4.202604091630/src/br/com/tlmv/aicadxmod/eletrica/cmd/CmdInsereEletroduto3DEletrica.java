/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdInsereEletroduto3DEletrica.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/01/2026
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 *
 */

package br.com.tlmv.aicadxmod.eletrica.cmd;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletrodutoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletroduto3DEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;

public class CmdInsereEletroduto3DEletrica extends CmdBase
{
//Private
	
	private CadLayerDef getDefaultLayer()
	{
		LayerTable oTbl = this.getDoc().getLayerTable();

		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_DT_TETO);
		
		if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_PISO.equals(super.getCommandName()) ) {
			oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_DT_PISO);
		}
		else if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_PAREDE.equals( super.getCommandName() ) ) {
			oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_DT_PAREDE);
		}
		else if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_APARENTE.equals(super.getCommandName()) ) {
			oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ELE_DT_APARENTE);
		}		
		return oLayer;
	}
	
	private double getInitialZ(double zLevel, double zI, double zF) 
	{
		double hTeto = zLevel + AppDefs.DEF_ELETRODUTO_HTETO;
		double hPiso = zLevel + AppDefs.DEF_ELETRODUTO_HPISO;

		double zResult = zF;				
		if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_TETO.equals( super.getCommandName() ) ) {
			if(zResult < hTeto)
				zResult = hTeto;			// altura da laje superior
		}
		else if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_PAREDE.equals(super.getCommandName()) ) {
			zResult = zI;					// altura primeiro ponto
		}
		else if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_PISO.equals(super.getCommandName()) ) {
			if(zResult > hPiso)
				zResult = hPiso;			// altura do contrapiso
		}
		else if( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_APARENTE.equals(super.getCommandName()) ) {
			zResult = zI;					// altura primeiro ponto
		}				
		return zResult;
	}
	
	private ArrayList<GeomPoint3d> ajustaAlturaUltimoTrecho(ArrayList<GeomPoint3d> lsPts3d, double zF)
	{
		int lastPos = lsPts3d.size() - 1;
		if(lastPos >= 1) {
			GeomPoint3d pt0 = lsPts3d.get(lastPos);
			GeomPoint3d pt1 = lsPts3d.get(lastPos - 1);
			
			GeomPoint3d ptM = new GeomPoint3d(pt1.getX(), pt1.getY(), zF);
			lsPts3d.set(lastPos, ptM);
			
			pt0.setZ(zF);
			lsPts3d.add(pt0);
		}
		return lsPts3d;
	}
	
//Public
	
	public CmdInsereEletroduto3DEletrica(String actionCommand) {
		super(actionCommand, true, true);
	}
	
	/* Methodes */
		
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		ArrayList<GeomPoint2d> lsPts2d = new ArrayList<GeomPoint2d>();
		
		PromptUtil.prompt( this.getR().getString(R.CMD_TIT_ELE1_ADDELETRICALCONDUITS3PTS));

		//PT-I
		//
		CadEntity entI = PromptUtil.selectObject(
			this, 
			AppDefs.OBJTYPE_MODELINSEREPONTO, 
			this.getR().getString(R.CMD_PRT_SELECT_ELECTRICAL_OBJECT_FROM) );
		if(entI == null) return null;
		
		CadPontoEletrica oEntI = (CadPontoEletrica)entI;
		GeomPoint3d ptInsI = new GeomPoint3d(oEntI.getPtIns());
		double zI = ptInsI.getZ();		

		GeomPoint2d ptI2d = new GeomPoint2d(ptInsI);
		lsPts2d.add(ptI2d);		

		//PT-F
		//
		CadEntity entF = null;
		CadPontoEletrica oEntF = null;		
		double zF = 0.0;
		
		for( ; ; ) {
			GeomPoint2d ptF2d = PromptUtil.getSecondPoint2d(
				this, 
				ptI2d, 
				lsPts2d, 
				this.getR().getString(R.CMD_PRT_NEXT_POINT) );
			if(ptF2d == null) break;

			//PT-F
			//
			entF = PromptUtil.selectObjectAt(AppDefs.OBJTYPE_MODELINSEREPONTO, ptF2d);
			if(entF != null) {
				oEntF = (CadPontoEletrica)entF;
				break;
			}
			lsPts2d.add(ptF2d);
			ptI2d = ptF2d;
		}
		
		GeomPoint3d ptF3d = new GeomPoint3d(oEntF.getPtIns());
		zF = ptF3d.getZ();
		
		GeomPoint2d ptF2d = new GeomPoint2d( ptF3d );
		lsPts2d.add(ptF2d);
		
		// LEVEL
		//
		CadLevel oLevel = GeomUtil.getCurrLevel();
		double zLevel = oLevel.getZLevel();

		double z0 = this.getInitialZ(zLevel, zI, zF);
		
		ArrayList<GeomPoint3d> lsPts3d = GeomUtil.copyPt2dTo3dList(lsPts2d, z0);

		result = new InputParamVO();

		if( ( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_PAREDE.equals(super.getCommandName()) ) ||
		    ( AppDefs.ACTION_EL2_INSERE_ELETRODUTO_MULT_APARENTE.equals(super.getCommandName()) ) ) 
		{
			ArrayList<GeomPoint3d> lsNewPts3d = this.ajustaAlturaUltimoTrecho(lsPts3d, zF);
			result.initEntity(entI, entF, lsNewPts3d);			
		}
		else {
			if(zI <= zF) {
				result.initEntity(entI, entF, lsPts3d);
			}
			else {
				ArrayList<GeomPoint3d> lsNewPts3d = GeomUtil.invertFrom3dTo3d(lsPts3d);
				result.initEntity(entF, entI, lsNewPts3d);
			}
		}
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//PARAMS
			//
			CadPontoEletrica ent1 = (CadPontoEletrica)oParam.getEnt1(); 
			CadPontoEletrica ent2 = (CadPontoEletrica)oParam.getEnt2(); 

			ArrayList<GeomPoint3d> lsPts = oParam.getLsPts3d();
			
			// LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel();
	
			//LAYER
			//
			CadLayerDef oLayer = this.getDefaultLayer();
			
			CadEletroduto3DEletrica o = CadEletroduto3DEletrica.create(currBlockDef, oLayer, oLevel, ent1, ent2, lsPts);
			currBlockDef.addEntity(o);

		}

	}

}
