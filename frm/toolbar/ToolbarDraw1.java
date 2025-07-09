/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ToolbarZoom.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 31/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.frm.toolbar;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class ToolbarDraw1 implements ToolbarBase 
{
//Public
	
	@Override
	public int createToolbarMenu(JPanel mnuToolbar, ActionListener listener) 
	{
		int result = AppDefs.RSERR;
		
		try {
			//MENU: TOOLBARDRAW1
			//
			JButton btnOffset = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_OFFSET, AppDefs.ACTION_DRAW1_OFFSET, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_OFFSET);
			mnuToolbar.add(btnOffset);

			JButton btnLine = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_LINE, AppDefs.ACTION_DRAW1_LINE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_LINE);
			mnuToolbar.add(btnLine);

			JButton btnArc = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_ARC, AppDefs.ACTION_DRAW1_ARCSCA, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_ARC);
			mnuToolbar.add(btnArc);

			JButton btnCircle = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_CIRCLE, AppDefs.ACTION_DRAW1_CIRCLECR, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_CIRCLE);
			mnuToolbar.add(btnCircle);

			JButton btnRectangle = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_RECTANGLE, AppDefs.ACTION_DRAW1_RECTANGLE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_RECTANGLE);
			mnuToolbar.add(btnRectangle);

			JButton btnPolygon = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_POLYGON, AppDefs.ACTION_DRAW1_POLYGON, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_POLYGON);
			mnuToolbar.add(btnPolygon);

			JButton btnPoint = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_POINT, AppDefs.ACTION_DRAW1_POINT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_POINT);
			mnuToolbar.add(btnPoint);

			JButton btnText = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_TEXT, AppDefs.ACTION_DRAW1_TEXT, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_TEXT);
			mnuToolbar.add(btnText);

			JButton btnAreaTable = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_AREATABLE, AppDefs.ACTION_DRAW1_AREATABLE, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_AREATABLE);
			mnuToolbar.add(btnAreaTable);

			JButton btnBox3D = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_BOX3D, AppDefs.ACTION_DRAW1_BOX3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_BOX3D);
			mnuToolbar.add(btnBox3D);

			JButton btnCilinder3D = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_CILINDER3D, AppDefs.ACTION_DRAW1_CILINDER3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_CILINDER3D);
			mnuToolbar.add(btnCilinder3D);

			JButton btnCone3D = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_CONE3D, AppDefs.ACTION_DRAW1_CONE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_CONE3D);
			mnuToolbar.add(btnCone3D);

			JButton btnTroncoCone3D = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_TRONCOCONE3D, AppDefs.ACTION_DRAW1_TRONCOCONE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_TRONCOCONE3D);
			mnuToolbar.add(btnTroncoCone3D);

			JButton btnTorus3D = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_TORUS3D, AppDefs.ACTION_DRAW1_TORUS3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_TORUS3D);
			mnuToolbar.add(btnTorus3D);

			JButton btnSphere3D = FormControlUtil.newImageButton(MainPanel.getParentFrame(), AppDefs.ICOMNU_DRAW1_SPHERE3D, AppDefs.ACTION_DRAW1_SPHERE3D, 16, 15, true, listener, AppDefs.TOOLTIP_ICOMNU_DRAW1_SPHERE3D);
			mnuToolbar.add(btnSphere3D);

			result = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
