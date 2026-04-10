/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdInsereTopoPointTopografia.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/11/2025
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

package br.com.tlmv.aicadxmod.topografia.cad.cmd;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.comp.CompCommandPrompt;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.topografia.cad.CadTopoPointTopografia;

public class CmdInsereTopoPointTopografia extends CmdBase
{
//Public

	public CmdInsereTopoPointTopografia() {
		super(AppDefs.ACTION_TOPO1_INSERE_TOPOPOINT, true, true);
	}

	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("TOPOGRAFIA: Adicionando ponto de topografia...");

		GeomPoint3d ptIns3d = PromptUtil.getStartPoint3d(this, null, null, "Insert point: ");
		if(ptIns3d == null) return null;
	
		Double alturaAntena = PromptUtil.getDouble(null, "Altura da antena: ");
		if(alturaAntena == null) return null;
		
		result = new InputParamVO();
		result.initPoint(ptIns3d, alturaAntena);
		return result;
	}
	
	@Override
	public void doCommand() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingEnUs(0);
		
		DateFormat df0 = new SimpleDateFormat(AppDefs.DEF_DATETIME_TYPE3_MASC);
		
		Date dataHoraAtual = new Date();
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//GEOMPOINT3D
			//
			GeomPoint3d ptIns3d_orig = oParam.getPt0();

			//TO_LEVEL
			//
			GeomPoint3d ptIns3d = GeomUtil.toLevelFromPt3d(ptIns3d_orig); 
			
			double dAlturaAntena = oParam.getHeight();
			String strDataAtualizacao = df0.format(dataHoraAtual);
				
			//CADTOPOPOINTTOPOGRAFIA
			//
			LayerTable oTbl = this.getDoc().getLayerTable();
		
			CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_TT_PADRAO_N);
	
			CadTopoPointTopografia oTopoPoint = CadTopoPointTopografia.create(
				currBlockDef, 
				oLayer, 
				null,
			    AppDefs.NULL_INT,
			    oLayer.getCategoriaId(),
			    oLayer.getDescricaoCategoria(),
			    AppDefs.NULL_STR,
			    dAlturaAntena,
			    strDataAtualizacao,
				ptIns3d);

			int objectId = oTopoPoint.getObjectId();
			String strNome = nf0.format(objectId);

			oTopoPoint.setNome( strNome );
			oTopoPoint.setPontoId(objectId);
			
			currBlockDef.addEntity(oTopoPoint);
	
	        MainPanel panel = MainPanel.getMainPanel();
			
			CompCommandPrompt commandPrompt = panel.getCommandPrompt();
			commandPrompt.setCommandPromptFocus(false);
		}
	}

}
