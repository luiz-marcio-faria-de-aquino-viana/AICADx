/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmdPontoDrenagem.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/04/2025
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

package br.com.tlmv.aicadxmod.drenagem.cmd;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.cmd.CmdBase;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPontoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class CmdPontoDrenagem extends CmdBase
{
//Private
	private String shapeName = null;
	//
    private double largura = DrenagemCalc.DEF_RALO_SIMPLES_LARGURA;
    private double altura = DrenagemCalc.DEF_RALO_SIMPLES_ALTURA;
    private double profundidade	= DrenagemCalc.DEF_RALO_SIMPLES_PROFUNDIDADE;
    
//Public
	
	public CmdPontoDrenagem(
		String actionCommand, 
		String shapeName,
	    double largura,
	    double altura,
	    double profundidade) 
	{
		super(actionCommand, true, true);

		this.shapeName = shapeName;
	    this.largura = largura;
	    this.altura = altura;
	    this.profundidade = profundidade;
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("DRENAGEM: Adicionando novos elementos de drenagem...");

		GeomPoint2d ptIns2d = PromptUtil.getStartPoint2d(this, null, null, "Ponto de insercao: ");
		if(ptIns2d == null) return null;
		
		GeomPoint3d ptIns3d = new GeomPoint3d(ptIns2d);

		GeomPoint2d ptDir2d = PromptUtil.getSecondPoint2d(this, ptIns2d, "Rotation: ");
		if(ptDir2d == null) return null;
		
		GeomPoint3d ptDir3d = new GeomPoint3d(ptDir2d);		

		CadEntity oEnt1 = PromptUtil.selectObject(this, AppDefs.OBJTYPE_MODDRCAIXAINSPECAO, "Selecione a caixa inspecao(PV): ");
		if(oEnt1 == null) return null;
		
		result = new InputParamVO();
		result.initEntity(oEnt1, ptIns3d, ptDir3d);
		return result;
	}
	
	@Override
	public void doCommand() {
		CadBlockDef currBlockDef = this.getDoc().getCurrBlockDef();

		//-- LAYER
		LayerTable oTbl = this.getDoc().getLayerTable();
		
		CadLayerDef oLayer = oTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PONTOS);
		
		//-- SHAPE
		ShapeTable shapeTable = this.getDoc().getShapeTable();
		
		Shape oShape = shapeTable.getShape(this.shapeName);
		if(oShape == null) return;
		
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam != null) {
			//GEOMPOINT3D
			//
			CadCaixaInspecaoDrenagem oEnt1 = (CadCaixaInspecaoDrenagem)oParam.getEnt1();
	
			GeomPoint3d ptIns3d_orig = oParam.getPt0(); 

			GeomPoint3d ptDir3d_orig = oParam.getPt1(); 
			
			//TO_LEVEL
			//
			double zLevel = 0.0;
			
			CadLevel oLevel = GeomUtil.getCurrLevel();    	
			if(oLevel != null) {
				zLevel = oLevel.getZLevel();
			}
			
			GeomPoint3d ptIns3d = GeomUtil.toLevelFromPt3d(ptIns3d_orig, oLevel); 
			
			GeomPoint3d ptDir3d = GeomUtil.toLevelFromPt3d(ptDir3d_orig, oLevel); 
			
			//CADSHAPE
			//
			GeomPoint2d ptIns2d = new GeomPoint2d(ptIns3d);
			GeomPoint2d ptDir2d = new GeomPoint2d(ptDir3d);
			
			GeomVector2d vDir = new GeomVector2d(ptIns2d, ptDir2d);
			double rotateRad = vDir.angleToAxisX();

			double rotate = GeomUtil.convertRadToDegrees(rotateRad);
				
			CadPontoDrenagem o = CadPontoDrenagem.create(
				currBlockDef,
				oLayer, 
				oLevel,
				oEnt1, 
				ptIns3d, 
				rotate,
				oShape,
			    this.largura,
			    this.altura,
			    this.profundidade);
			currBlockDef.addEntity(o);
		}
	}

	/* Getters/Setters */
	
	public String getShapeName() {
		return shapeName;
	}

	public void setShapeName(String shapeName) {
		this.shapeName = shapeName;
	}

	public double getLargura() {
		return largura;
	}

	public double getAltura() {
		return altura;
	}

	public double getProfundidade() {
		return profundidade;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

}
