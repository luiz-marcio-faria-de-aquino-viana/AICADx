/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdUniline.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/11/2025
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

package br.com.tlmv.aicadxapp.cmd;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLine;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.vo.InputParamVO;

public class CmdUniline extends CmdBase
{
//Public

	public CmdUniline() {
		super(AppDefs.ACTION_EDIT2_UNILINE, false, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt( this.getR().getString( R.CMD_TIT_EDIT_UNILINE ) );
		
		CadEntity oEnt1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_LINE, this.getR().getString( R.CMD_PRT_SELECT_FIRST_OBJECT ) );
		if(oEnt1 == null) return null;
		
		CadEntity oEnt2 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_LINE, this.getR().getString( R.CMD_PRT_SELECT_SECOND_OBJECT ) );
		if(oEnt2 == null) return null;
		
		result = new InputParamVO();
		result.initEntity(oEnt1, oEnt2);
		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		while(oParam != null) {
			CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();
			
			//LINE 1
			CadLine oLine1 = (CadLine)oParam.getEnt1();
	
			GeomPoint3d oLine1PtI3d = new GeomPoint3d( oLine1.getPtI() );
			GeomPoint3d oLine1PtF3d = new GeomPoint3d( oLine1.getPtF() );
	
			GeomPoint2d oLine1PtI2d = new GeomPoint2d( oLine1PtI3d );
			GeomPoint2d oLine1PtF2d = new GeomPoint2d( oLine1PtF3d );
	
			//LINE 2
			CadLine oLine2 = (CadLine)oParam.getEnt2();
	
			GeomPoint3d oLine2PtI3d = new GeomPoint3d( oLine2.getPtI() );
			GeomPoint3d oLine2PtF3d = new GeomPoint3d( oLine2.getPtF() );
	
			GeomPoint2d oLine2PtI2d = new GeomPoint2d( oLine2PtI3d );
			GeomPoint2d oLine2PtF2d = new GeomPoint2d( oLine2PtF3d );
	
			//INTERSECTION_OF: L1_PTI-TO-L1_PTF with L2_PTI-TO-L2_PTF 
			//
			GeomPoint2d ptIntersec2d = GeomUtil.dirIntersectionOf( oLine1PtI2d, oLine1PtF2d, oLine2PtI2d, oLine2PtF2d, false );
			if(ptIntersec2d == null) {
				//INTERSECTION_OF: L1_PTI-TO-L1_PTF with L2_PTF-TO-L2_PTI 
				//
				ptIntersec2d = GeomUtil.dirIntersectionOf( oLine1PtI2d, oLine1PtF2d, oLine2PtF2d, oLine2PtI2d, false );
				if(ptIntersec2d == null) {
					//INTERSECTION_OF: L1_PTF-TO-L1_PTI with L2_PTI-TO-L2_PTF 
					//
					ptIntersec2d = GeomUtil.dirIntersectionOf( oLine1PtF2d, oLine1PtI2d, oLine2PtI2d, oLine2PtF2d, false );
					if(ptIntersec2d == null) {
						//INTERSECTION_OF: L1_PTF-TO-L1_PTI with L2_PTF-TO-L2_PTI 
						//
						ptIntersec2d = GeomUtil.dirIntersectionOf( oLine1PtF2d, oLine1PtI2d, oLine2PtF2d, oLine2PtI2d, false );
					}			
				}			
			}

			//CHANGE_BLOCK
			//
			currBlockDef.beginTrans();
			
			if(ptIntersec2d != null) {
				//LINE 1
				double d1I = oLine1PtI2d.distTo(ptIntersec2d);
				double d1F = oLine1PtF2d.distTo(ptIntersec2d);
	
				//LINE 2
				double d2I = oLine2PtI2d.distTo(ptIntersec2d);
				double d2F = oLine2PtF2d.distTo(ptIntersec2d);

				//UPDATE - LINE 1
				//
				CadLine oldLine1 = oLine1.duplicate();
				
				GeomPoint3d ptIntersec3d = null;
				if(d1F < d1I) {
					ptIntersec3d = new GeomPoint3d( ptIntersec2d.getX(), ptIntersec2d.getY(), oLine1PtF3d.getZ() );					
					oLine1.setPtF(ptIntersec3d);
				}
				else {
					ptIntersec3d = new GeomPoint3d( ptIntersec2d.getX(), ptIntersec2d.getY(), oLine1PtI3d.getZ() );						
					oLine1.setPtI(ptIntersec3d);
				}

				CadLine newLine1 = oLine1.duplicate();

				currBlockDef.saveTrans(oldLine1, newLine1);

				//UPDATE - LINE 2
				//
				CadLine oldLine2 = oLine2.duplicate();
				
				if(d2F < d2I) {
					oLine2.setPtF(ptIntersec3d);				
				}
				else {
					oLine2.setPtI(ptIntersec3d);				
				}

				CadLine newLine2 = oLine2.duplicate();
				
				currBlockDef.saveTrans(oldLine2, newLine2);
			}
			
			currBlockDef.endTrans();

			this.refreshAll();

			oParam = this.promptInputParam(this.getFrm(), oParam);
		}
		
	}

}
