/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ToolbarZoom.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/01/2025
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

package br.com.tlmv.aicadxapp.frm.toolbar;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class ToolbarDraw1 implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			MainFrame frm = MainFrame.getMainFrame();
			
			//MENU: TOOLBARDRAW1
			//
			JButton btnOffset = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_OFFSET, AppDefs.ACTION_DRAW1_OFFSET, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_OFFSET);
			mnuToolbar.add(btnOffset);

			JButton btnLine = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_LINE, AppDefs.ACTION_DRAW1_LINE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_LINE);
			mnuToolbar.add(btnLine);

			JButton btnLinePerp = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_LINEPERP, AppDefs.ACTION_DRAW1_LINEPERP, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_LINEPERP);
			mnuToolbar.add(btnLinePerp);

			JButton btnLineIntersec = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_LINEINTERSEC, AppDefs.ACTION_DRAW1_LINEINTERSEC, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_LINEINTERSEC);
			mnuToolbar.add(btnLineIntersec);

			JButton btnArc = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_ARC, AppDefs.ACTION_DRAW1_ARCSCA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_ARC);
			mnuToolbar.add(btnArc);

			JButton btnCircle = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_CIRCLE, AppDefs.ACTION_DRAW1_CIRCLECR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_CIRCLE);
			mnuToolbar.add(btnCircle);

			JButton btnEllipse = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_ELLIPSE, AppDefs.ACTION_DRAW1_ELLIPSE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_ELLIPSE);
			mnuToolbar.add(btnEllipse);

			JButton btnEllipseRotate = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_ELLIPSE_ROTATE, AppDefs.ACTION_DRAW1_ELLIPSE_ROTATE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_ELLIPSE_ROTATE);
			mnuToolbar.add(btnEllipseRotate);

			JButton btnRectangle = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_RECTANGLE, AppDefs.ACTION_DRAW1_RECTANGLE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_RECTANGLE);
			mnuToolbar.add(btnRectangle);

			JButton btnRegPolygon = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_REGULAR_POLYGON, AppDefs.ACTION_DRAW1_REGULAR_POLYGON, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_REGULAR_POLYGON);
			mnuToolbar.add(btnRegPolygon);

			JButton btnPolygon = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_POLYGON, AppDefs.ACTION_DRAW1_POLYGON, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_POLYGON);
			mnuToolbar.add(btnPolygon);

			JButton btnPolyline = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_POLYLINE, AppDefs.ACTION_DRAW1_POLYLINE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_POLYLINE);
			mnuToolbar.add(btnPolyline);

			JButton btnPoint = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_POINT, AppDefs.ACTION_DRAW1_POINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_POINT);
			mnuToolbar.add(btnPoint);

			JButton btnText = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_TEXT, AppDefs.ACTION_DRAW1_TEXT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_TEXT);
			mnuToolbar.add(btnText);

			JButton btnArea = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_AREA, AppDefs.ACTION_DRAW1_AREA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_AREA);
			mnuToolbar.add(btnArea);

			JButton btnAreaBySelection = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_AREA_BYSELECTION, AppDefs.ACTION_DRAW1_AREA_BYSELECTION, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_AREA_BYSELECTION);
			mnuToolbar.add(btnAreaBySelection);

			JButton btnAreaByInsidePoint = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_AREA_BYINSIDEPOINT, AppDefs.ACTION_DRAW1_AREA_BYINSIDEPOINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_AREA_BYINSIDEPOINT);
			mnuToolbar.add(btnAreaByInsidePoint);

			JButton btnAreaTable = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_AREATABLE, AppDefs.ACTION_DRAW1_AREATABLE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_AREATABLE);
			mnuToolbar.add(btnAreaTable);

			JButton btnPipe = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_PIPE, AppDefs.ACTION_DRAW1_PIPE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_PIPE);
			mnuToolbar.add(btnPipe);

			JButton btnPipeLine = FormControlUtil.newImageButton(frm, AppDefs.ICOMNU_DRAW1_PIPELINE, AppDefs.ACTION_DRAW1_PIPELINE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_PIPELINE);
			mnuToolbar.add(btnPipeLine);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
