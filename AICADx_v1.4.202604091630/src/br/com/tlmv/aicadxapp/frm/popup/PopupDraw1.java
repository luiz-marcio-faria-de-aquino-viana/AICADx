/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.frm.popup;

import java.awt.event.ActionListener;

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

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_LINEPERP,
				AppDefs.ACTION_DRAW1_LINEPERP,
				listener) );

			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_LINEINTERSEC,
				AppDefs.ACTION_DRAW1_LINEINTERSEC,
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
			
			mnu.add(new JSeparator());
			
			JMenu submnuEllipse = FormControlUtil.newMenu(AppDefs.MNU_DRAW1_ELLIPSE);
			
			mnu.add(submnuEllipse);

			submnuEllipse.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_DRAW1_ELLIPSE,
					AppDefs.ACTION_DRAW1_ELLIPSE,
					listener) );

			submnuEllipse.add(FormControlUtil.newMenuItem(
					AppDefs.MNU_DRAW1_ELLIPSE_ROTATE,
					AppDefs.ACTION_DRAW1_ELLIPSE_ROTATE,
					listener) );
			
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
				AppDefs.MNU_DRAW1_REGULAR_POLYGON,
				AppDefs.ACTION_DRAW1_REGULAR_POLYGON,
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
				AppDefs.MNU_DRAW1_AREA_BYSELECTION,
				AppDefs.ACTION_DRAW1_AREA_BYSELECTION,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_AREA_BYINSIDEPOINT,
				AppDefs.ACTION_DRAW1_AREA_BYINSIDEPOINT,
				listener) );

			mnu.add(new JSeparator());
						
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_AREATABLE,
				AppDefs.ACTION_DRAW1_AREATABLE,
				listener) );

			mnu.add(new JSeparator());
						
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_PIPE,
				AppDefs.ACTION_DRAW1_PIPE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW1_PIPELINE,
				AppDefs.ACTION_DRAW1_PIPELINE,
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
