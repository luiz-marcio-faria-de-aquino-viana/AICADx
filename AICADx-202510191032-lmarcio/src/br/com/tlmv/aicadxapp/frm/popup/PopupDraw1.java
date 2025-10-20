/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * PopupDraw.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.popup;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class PopupDraw1 implements PopupBase 
{
//Private
	private JMenu mnu = null;

//Public
	
	public int createMenu(JMenuBar mnubar, ActionListener listener)
	{
		int result = AppDefs.RSERR;
		
		try {		
			//MENU: DRAW
			//
			this.mnu = FormControlUtil.newMenu(AppDefs.MNU_DRAW1);

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_OFFSET,
				AppDefs.ACTION_DRAW1_OFFSET,
				listener) );
			
			mnu.add(new JSeparator());

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_LINE,
				AppDefs.ACTION_DRAW1_LINE,
				listener) );
			
			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_PLINE,
			//	AppDefs.MNU_DRAW1_PLINE,
			//	listener) );
			
			mnu.add(new JSeparator());
			
			JMenu submnuArc = FormControlUtil.newMenu(AppDefs.MNU_DRAW1_ARC);

			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARC3PT,
			//	AppDefs.MNU_DRAW1_ARC3PT,
			//	listener) );
			
			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARCSCR,
			//	AppDefs.MNU_DRAW1_ARCSCR,
			//	listener) );
			
			submnuArc.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_ARCSCA,
				AppDefs.ACTION_DRAW1_ARCSCA,
				listener) );
			
			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARCSCL,
			//	AppDefs.MNU_DRAW1_ARCSCL,
			//	listener) );
			
			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARCSEA,
			//	AppDefs.MNU_DRAW1_ARCSEA,
			//	listener) );
			
			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARCSED,
			//	AppDefs.MNU_DRAW1_ARCSED,
			//	listener) );
			
			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARCSER,
			//	AppDefs.MNU_DRAW1_ARCSER,
			//	listener) );
			
			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARCCSE,
			//	AppDefs.MNU_DRAW1_ARCCSE,
			//	listener) );
			
			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARCCSA,
			//	AppDefs.MNU_DRAW1_ARCCSA,
			//	listener) );
			
			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARCCSL,
			//	AppDefs.MNU_DRAW1_ARCCSL,
			//	listener) );
			
			//submnuArc.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ARCCONT,
			//	AppDefs.MNU_DRAW1_ARCCONT,
			//	listener) );
			
			mnu.add(submnuArc);
			
			mnu.add(new JSeparator());
			
			JMenu submnuCircle = FormControlUtil.newMenu(AppDefs.MNU_DRAW1_CIRCLE);
			
			submnuCircle.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_CIRCLECR,
				AppDefs.ACTION_DRAW1_CIRCLECR,
				listener) );
			
			//submnuCircle.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_CIRCLECD,
			//	AppDefs.MNU_DRAW1_CIRCLECD,
			//	listener) );
			
			//submnuCircle.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_CIRCLE2PT,
			//	AppDefs.MNU_DRAW1_CIRCLE2PT,
			//	listener) );
			
			//submnuCircle.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_CIRCLE3PT,
			//	AppDefs.MNU_DRAW1_CIRCLE3PT,
			//	listener) );
			
			//submnuCircle.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_CIRCLETTR,
			//	AppDefs.MNU_DRAW1_CIRCLETTR,
			//	listener) );
			
			//submnuCircle.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_CIRCLETTT,
			//	AppDefs.MNU_DRAW1_CIRCLETTT,
			//	listener) );
			
			mnu.add(submnuCircle);
			
			//submnuCircle.add(new JSeparator());

			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_SPLINE,
			//	AppDefs.MNU_DRAW1_SPLINE,
			//	listener) );
			
			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_RAY,
			//	AppDefs.MNU_DRAW1_RAY,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_INFILINE,
			//	AppDefs.MNU_DRAW1_INFILINE,
			//	listener) );
			
			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_FREEHAND,
			//	AppDefs.MNU_DRAW1_FREEHAND,
			//	listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_RECTANGLE,
				AppDefs.ACTION_DRAW1_RECTANGLE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_POLYGON,
				AppDefs.ACTION_DRAW1_POLYGON,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_POLYLINE,
				AppDefs.ACTION_DRAW1_POLYLINE,
				listener) );

			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_DONUTD,
			//	AppDefs.MNU_DRAW1_DONUTD,
			//	listener) );
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_DONUTR,
			//	AppDefs.MNU_DRAW1_DONUTR,
			//	listener) );
			
			//mnu.add(new JSeparator());
			
			//JMenu submnuEllipse = FormControlUtil.newSubmenu(AppDefs.MNU_DRAW1_ELLIPSE);
			
			//submnuEllipse.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ELLIPSEC,
			//	AppDefs.MNU_DRAW1_ELLIPSEC,
			//	listener) );
			
			//submnuEllipse.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ELLIPSEAE,
			//	AppDefs.MNU_DRAW1_ELLIPSEAE,
			//	listener) );
			
			//submnuEllipse.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_ELLIPSEA,
			//	AppDefs.MNU_DRAW1_ELLIPSEA,
			//	listener) );
			
			//mnu.add(submnuEllipse);
			
			//mnu.add(new JSeparator());
			
			//mnu.add(FormControlUtil.newMenuItem(
			//	AppDefs.MNU_DRAW1_POLYGON,
			//	AppDefs.MNU_DRAW1_POLYGON,
			//	listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_POINT,
				AppDefs.ACTION_DRAW1_POINT,
				listener) );
	
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_TEXT,
				AppDefs.ACTION_DRAW1_TEXT,
				listener) );

			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_AREA,
				AppDefs.ACTION_DRAW1_AREA,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_AREATABLE,
				AppDefs.ACTION_DRAW1_AREATABLE,
				listener) );
			
			mnubar.add(mnu);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int createPopupMenu(JPopupMenu mnuPopup, ActionListener listener) 
	{
		return AppDefs.RSOK;		
	}
	
	public boolean isVisible()
	{
		boolean bVisible = this.mnu.isVisible();
		return bVisible;
	}
	
	public void setVisible(boolean bVisible)
	{
		this.mnu.setVisible(bVisible);
	}

}
