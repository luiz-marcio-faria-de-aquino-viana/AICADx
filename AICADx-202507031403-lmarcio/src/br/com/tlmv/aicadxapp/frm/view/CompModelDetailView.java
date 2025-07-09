/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CompModelDetailView.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 10/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.view;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadViewPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.frm.MainFrame;

public class CompModelDetailView extends CompModelPlanView
{
//Public

	public CompModelDetailView(String name, int viewType, MainFrame frm, CadDocumentDef doc) {
		super(name, viewType, frm, doc);
	}
	
	@Override
	public void initCadView(double wScr, double hScr)
	{
		//MCS
		double wMcs = AppDefs.MCSDETAIL_XSIZE_MILI * AppDefs.MCSDETAIL_SCALEFACTOR;
		double hMcs = AppDefs.MCSDETAIL_YSIZE_MILI * AppDefs.MCSDETAIL_SCALEFACTOR;
		
		double xCenterMcs = wMcs / 2.0;
		double yCenterMcs = hMcs / 2.0;
		
		GeomPoint2d ptCenterMcs = new GeomPoint2d(xCenterMcs, yCenterMcs); 
		GeomPoint2d ptXDirMcs = new GeomPoint2d(xCenterMcs + 1.0, yCenterMcs);

		GeomVector2d xDirMcs = new GeomVector2d(ptCenterMcs, ptXDirMcs);

		this.v = new CadViewPlan2d(
			ptCenterMcs,
			xDirMcs,
			wMcs,
			hMcs,
			wScr,
			hScr);
	}
	
	public void resetCadView(double wScr, double hScr)
	{
		//MCS
		GeomPlan2d planMcs = this.v.getPlanMcs2d();
		
		double wMcs = planMcs.getWidth();
		double hMcs = planMcs.getHeight();
		
		GeomPoint2d ptCenterMcs = planMcs.getPtCenter();
		GeomVector2d xDirMcs = planMcs.getXDir();

		this.v.reset(
			ptCenterMcs,
			xDirMcs,
			wMcs,
			hMcs,
			wScr,
			hScr);
	}

	/* Getters/Setters */
	
	public String getName() {
		return name;
	}

	public int getViewType() {
		return viewType;
	}
		
}
