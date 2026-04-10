/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdControleBacklistTransMar.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/09/2025
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

package br.com.tlmv.aicadxmod.transmar.cmd;

import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.esgoto.cad.CadCaixaInspecaoEsgoto;
import br.com.tlmv.aicadxmod.transmar.cad.CadContentorTransMar;
import br.com.tlmv.aicadxmod.transmar.cad.CadControleBacklistTransMar;

public class CmdControleBacklistTransMar extends CmdBase
{
//Public
	
	public CmdControleBacklistTransMar() {
		super(AppDefs.ACTION_TMAR1_INSERE_CONTROLE_BACKLIST, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("TRANSPORTE MARITIMO: Adding new Controle Backlist...");

		GeomPoint2d ptIns2d = PromptUtil.getStartPoint2d(this, null, null, "Insert point: ");
		if(ptIns2d == null) return null;
		
		GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);

		result = new InputParamVO();
		result.initPoint(ptIns3d);
		
        MainPanel panel = MainPanel.getMainPanel();

		ICompView v = panel.getCurrView();
		v.repaintAll();
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//PARAMS
			//
			GeomPoint3d ptIns3d_orig = oParam.getPt0(); 

			//TO_LEVEL
			//
			CadLevel oLevel = GeomUtil.getCurrLevel();
			
			GeomPoint3d ptIns3d = GeomUtil.toLevelFromPt3d(ptIns3d_orig, oLevel); 

			//CADCONTENTOR
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
	
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_TMAR_PONTOS);
			
			//FILENAME
			//
			ProjectRepoVO projectRepo = this.getDoc().getProjectRepo();
			
			String name = projectRepo.getName();	
			//String fileName = projectRepo.getFileName();	
					
	    	MainPanel panel = MainPanel.getMainPanel();
	    	
	    	MainFrame frm = MainFrame.getMainFrame();
			frm.updateTitle(name);

			CadControleBacklistTransMar o = CadControleBacklistTransMar.create(
				currBlockDef,
				oLayer, 
				oLevel,
				ptIns3d,
				name,
				"",
			    1001,
				new Date());
			currBlockDef.addEntity(o);
			
			//oParam = this.promptInputParam(this.getFrm(), oParam);
		}
	}

}
