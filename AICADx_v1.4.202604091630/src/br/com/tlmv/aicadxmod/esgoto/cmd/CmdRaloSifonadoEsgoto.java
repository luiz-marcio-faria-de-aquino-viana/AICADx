/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdCaixaInspecaoEsgoto.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
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

package br.com.tlmv.aicadxmod.esgoto.cmd;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadCircle;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.cmd.ICmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.CompModelPlanView;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.esgoto.cad.CadCaixaInspecaoEsgoto;
import br.com.tlmv.aicadxmod.esgoto.cad.CadRaloSifonadoEsgoto;

public class CmdRaloSifonadoEsgoto extends CmdBase
{
//Public
	
	public CmdRaloSifonadoEsgoto() {
		super(AppDefs.ACTION_ES4_INSERE_RS, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_ES1_ADICIONA_RALO_SIFONADO ) );

		GeomPoint2d ptIns2d = PromptUtil.getFirstPoint2d(this, null, this.getR().getString( R.CMD_PRT_INSERT_POINT ) );
		if(ptIns2d == null) return null;
		
		GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);

		GeomPoint2d ptDir2d = PromptUtil.getSecondPoint2d(this, ptIns2d, this.getR().getString( R.CMD_PRT_ROTATION ) );
		if(ptDir2d == null) return null;
		
		GeomPoint3d ptDir3d = new GeomPoint3d(ptDir2d);

		result = new InputParamVO();
		result.initPointAndRotation(ptIns3d, ptDir3d);
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptIns3d_orig = oParam.getPt0(); 
			GeomPoint3d ptDir3d_orig = oParam.getPt1(); 

			//LEVEL
			//
			double zLevel = 0.0;
			
			CadLevel oLevel = GeomUtil.getCurrLevel();    	
			if(oLevel != null) {
				zLevel = oLevel.getZLevel();
			}
			
			GeomPoint3d ptIns3d = GeomUtil.toLevelFromPt3d(ptIns3d_orig, oLevel); 
			GeomPoint3d ptDir3d = GeomUtil.toLevelFromPt3d(ptDir3d_orig, oLevel); 
						
			//CADCAIXAINSPECAO
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_ESG_PONTOS);
			
			CadRaloSifonadoEsgoto o = CadRaloSifonadoEsgoto.create(currBlockDef, oLayer, oLevel, ptIns3d, ptDir3d);
			currBlockDef.addEntity(o);
			
			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
