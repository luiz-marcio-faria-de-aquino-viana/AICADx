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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FormControlUtil;

public class PopupDraw2 implements PopupBase 
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
			this.mnu = FormControlUtil.newMenu(AppDefs.MNU_DRAW2);
	
			JMenu submnuOffset = FormControlUtil.newSubmenu(AppDefs.MNU_DRAW2_OFFSET);
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET2_5CM,
				AppDefs.MNU_DRAW2_OFFSET2_5CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET5CM,
				AppDefs.MNU_DRAW2_OFFSET5CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET10CM,
				AppDefs.MNU_DRAW2_OFFSET10CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET15CM,
				AppDefs.MNU_DRAW2_OFFSET15CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET20CM,
				AppDefs.MNU_DRAW2_OFFSET20CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET25CM,
				AppDefs.MNU_DRAW2_OFFSET25CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET30CM,
				AppDefs.MNU_DRAW2_OFFSET30CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET35CM,
				AppDefs.MNU_DRAW2_OFFSET35CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET40CM,
				AppDefs.MNU_DRAW2_OFFSET40CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET45CM,
				AppDefs.MNU_DRAW2_OFFSET45CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET50CM,
				AppDefs.MNU_DRAW2_OFFSET50CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET60CM,
				AppDefs.MNU_DRAW2_OFFSET60CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET70CM,
				AppDefs.MNU_DRAW2_OFFSET70CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET80CM,
				AppDefs.MNU_DRAW2_OFFSET80CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET90CM,
				AppDefs.MNU_DRAW2_OFFSET90CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET100CM,
				AppDefs.MNU_DRAW2_OFFSET100CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET125CM,
				AppDefs.MNU_DRAW2_OFFSET125CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET200CM,
				AppDefs.MNU_DRAW2_OFFSET200CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET250CM,
				AppDefs.MNU_DRAW2_OFFSET250CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET500CM,
				AppDefs.MNU_DRAW2_OFFSET500CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET750CM,
				AppDefs.MNU_DRAW2_OFFSET750CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSET1000CM,
				AppDefs.MNU_DRAW2_OFFSET1000CM,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETTHROWGHT,
				AppDefs.MNU_DRAW2_OFFSETTHROWGHT,
				listener) );
			
			submnuOffset.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETDIST,
				AppDefs.MNU_DRAW2_OFFSETDIST,
				listener) );
			
			mnu.add(submnuOffset);
			
			mnu.add(new JSeparator());
			
			JMenu submnuOffsetChg = FormControlUtil.newSubmenu(AppDefs.MNU_DRAW2_OFFSETCHG);
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG2_5CM,
				AppDefs.MNU_DRAW2_OFFSETCHG2_5CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG5CM,
				AppDefs.MNU_DRAW2_OFFSETCHG5CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG10CM,
				AppDefs.MNU_DRAW2_OFFSETCHG10CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG15CM,
				AppDefs.MNU_DRAW2_OFFSETCHG15CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG20CM,
				AppDefs.MNU_DRAW2_OFFSETCHG20CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG25CM,
				AppDefs.MNU_DRAW2_OFFSETCHG25CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG30CM,
				AppDefs.MNU_DRAW2_OFFSETCHG30CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG35CM,
				AppDefs.MNU_DRAW2_OFFSETCHG35CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG40CM,
				AppDefs.MNU_DRAW2_OFFSETCHG40CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG45CM,
				AppDefs.MNU_DRAW2_OFFSETCHG45CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG50CM,
				AppDefs.MNU_DRAW2_OFFSETCHG50CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG60CM,
				AppDefs.MNU_DRAW2_OFFSETCHG60CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG70CM,
				AppDefs.MNU_DRAW2_OFFSETCHG70CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG80CM,
				AppDefs.MNU_DRAW2_OFFSETCHG80CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG90CM,
				AppDefs.MNU_DRAW2_OFFSETCHG90CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG100CM,
				AppDefs.MNU_DRAW2_OFFSETCHG100CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG125CM,
				AppDefs.MNU_DRAW2_OFFSETCHG125CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG200CM,
				AppDefs.MNU_DRAW2_OFFSETCHG200CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG250CM,
				AppDefs.MNU_DRAW2_OFFSETCHG250CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG500CM,
				AppDefs.MNU_DRAW2_OFFSETCHG500CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG750CM,
				AppDefs.MNU_DRAW2_OFFSETCHG750CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHG1000CM,
				AppDefs.MNU_DRAW2_OFFSETCHG1000CM,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHGTHROWGHT,
				AppDefs.MNU_DRAW2_OFFSETCHGTHROWGHT,
				listener) );
			
			submnuOffsetChg.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_OFFSETCHGDIST,
				AppDefs.MNU_DRAW2_OFFSETCHGDIST,
				listener) );
			
			mnu.add(submnuOffsetChg);
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_BHATCH,
				AppDefs.MNU_DRAW2_BHATCH,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_PLANE,
				AppDefs.MNU_DRAW2_PLANE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_TRACE,
				AppDefs.MNU_DRAW2_TRACE,
				listener) );
			
			mnu.add(new JSeparator());
			
			JMenu submnuText = FormControlUtil.newSubmenu(AppDefs.MNU_DRAW2_TEXT);
			
			submnuText.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_TEXTSMALL,
				AppDefs.MNU_DRAW2_TEXTSMALL,
				listener) );
			
			submnuText.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_TEXTNORMAL,
				AppDefs.MNU_DRAW2_TEXTNORMAL,
				listener) );
			
			submnuText.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_TEXTMEDIUM,
				AppDefs.MNU_DRAW2_TEXTMEDIUM,
				listener) );
			
			submnuText.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_TEXTBIG,
				AppDefs.MNU_DRAW2_TEXTBIG,
				listener) );
			
			submnuText.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_DTEXT,
				AppDefs.MNU_DRAW2_DTEXT,
				listener) );

			submnuText.add(new JSeparator());
			
			submnuText.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_STYLES,
				AppDefs.MNU_DRAW2_STYLES,
				listener) );
			
			submnuText.add(new JSeparator());
			
			submnuText.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_MULTILINETEXT,
				AppDefs.MNU_DRAW2_MULTILINETEXT,
				listener) );
			
			submnuText.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_SINGLELINETEXT,
				AppDefs.MNU_DRAW2_SINGLELINETEXT,
				listener) );
			
			mnu.add(submnuText);
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_BOUNDARYPLINE,
				AppDefs.MNU_DRAW2_BOUNDARYPLINE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_BOUNDARYENTITIES,
				AppDefs.MNU_DRAW2_BOUNDARYENTITIES,
				listener) );
			
			mnu.add(new JSeparator());
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_DIVIDE,
				AppDefs.MNU_DRAW2_DIVIDE,
				listener) );
			
			mnu.add(FormControlUtil.newMenuItem(
				AppDefs.MNU_DRAW2_MEASURE,
				AppDefs.MNU_DRAW2_MEASURE,
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
