/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * FormControlUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.TableColumnModel;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class FormControlUtil 
{
	
	public static Cursor createBlankCursor()
	{
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "CURSOR_NONE");
		return cursor;
	}
	
	public static Cursor create3DCursor()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = MainFrame.class.getResource("/br/com/tlmv/aicadxapp/res/cursor/ico_cursor_pan_24x24.png");
		Image cursorImg = tk.getImage(url);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "CURSOR_NONE");
		return cursor;
	}
	
	public static void loadIcon(JFrame frm)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = MainFrame.class.getResource(AppDefs.APP_ICON);
		Image iconImg = tk.getImage(url);
		frm.setIconImage(iconImg);
	}

	public static Image createColoredIamge(Color c, int w, int h)
	{
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();		
		g.fillRect(0, 0, w, h);
		return img;
	}
	
	public static Image loadAnyIcon(JFrame frm, String res)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = MainFrame.class.getResource(res);
		Image iconImg = tk.getImage(url);
		return iconImg;
	}
	
	
	public static void setCbxByText(JComboBox cbx, BorderStrokeVO[] lsItemData, String text)
	{
		if( (lsItemData == null) || (lsItemData.length == 0) ) return;
		
		for(int i = 0; i < lsItemData.length; i++) {
			BorderStrokeVO o = lsItemData[i];
			
			String item = o.getName();
			if( text.equals(item) ) {
				cbx.setSelectedIndex(i);
				return;
			}
		}
		cbx.setSelectedIndex(0);
	}

	public static void setCbxByText(JComboBox cbx, String[] lsItemData, String text)
	{
		if( (lsItemData == null) || (lsItemData.length == 0) ) return;
		
		for(int i = 0; i < lsItemData.length; i++)
		{
			String item = lsItemData[i];
			
			if( text.equals(item) )
			{
				cbx.setSelectedIndex(i);
				return;
			}
		}
		cbx.setSelectedIndex(0);
	}

	public static void setCbx(JComboBox cbx, String[] lsItemData, String id)
	{
		if( (lsItemData == null) || (lsItemData.length == 0) ) return;
		
		for(int i = 0; i < lsItemData.length; i++)
		{
			String item = lsItemData[i];
			
			if( id.equals(item) )
			{
				cbx.setSelectedIndex(i);
				return;
			}
		}
		cbx.setSelectedIndex(0);
	}

	public static void setCbx(JComboBox cbx, ItemDataVO[] lsItemData, String id)
	{
		if( (lsItemData == null) || (lsItemData.length == 0) ) return;
		
		for(int i = 0; i < lsItemData.length; i++)
		{
			ItemDataVO item = lsItemData[i];
			
			if( id.equals(item.getItemDataId()) )
			{
				cbx.setSelectedIndex(i);
				return;
			}
		}
		cbx.setSelectedIndex(0);
	}

	public static void setCbx(JComboBox cbx, ArrayList<ItemDataVO> lsItemData, String id)
	{
		if( (lsItemData == null) || (lsItemData.size() == 0) ) return;
		
		for(int i = 0; i < lsItemData.size(); i++)
		{
			ItemDataVO item = lsItemData.get(i);
			
			if( id.equals(item.getItemDataId()) )
			{
				cbx.setSelectedIndex(i);
				return;
			}
		}
		cbx.setSelectedIndex(0);
	}
	
	public static void setCbxByValue(JComboBox cbx, ItemDataVO[] lsItemData, String value)
	{
		if( (lsItemData == null) || (lsItemData.length == 0) ) return;
		
		for(int i = 0; i < lsItemData.length; i++)
		{
			ItemDataVO item = lsItemData[i];
			
			if( value.equals(item.getDescricao()) )
			{
				cbx.setSelectedIndex(i);
				return;
			}
		}
		cbx.setSelectedIndex(0);
	}

	public static void setCbxByValue(JComboBox cbx, ArrayList<ItemDataVO> lsItemData, String value)
	{
		if( (lsItemData == null) || (lsItemData.size() == 0) ) return;
		
		for(int i = 0; i < lsItemData.size(); i++)
		{
			ItemDataVO item = lsItemData.get(i);
			
			if( value.equals(item.getDescricao()) )
			{
				cbx.setSelectedIndex(i);
				return;
			}
		}
		cbx.setSelectedIndex(0);
	}

	public static JPanel newTabPanel(String name) 
	{
	    JPanel panel = new JPanel(false);
	    panel.setLayout(null);
	    panel.setName(name);	    
	    return panel;
	}	
	
	public static JLabel newLabel(String label, int xp, int yp, int w, int h, boolean isVisible)
	{
		JLabel o = new JLabel(label);
		o.setLocation(xp, yp);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		return o;		
	}
	
	public static JLabel newLabel(String label, int w, int h, boolean isVisible)
	{
		JLabel o = new JLabel(label);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		return o;		
	}
	
	public static JLabel newLabelEx(JPanel parent, String label, boolean isVisible, String layoutPos)
	{
		JLabel o = new JLabel(label);
		o.setVisible(isVisible);
		parent.add(o, layoutPos);
		return o;		
	}
	
	public static JCheckBox newCheckBox(boolean isChecked, String label, int xp, int yp, int w, int h, boolean isVisible, boolean isEnabled)
	{
		JCheckBox o = new JCheckBox();
		o.setLocation(xp, yp);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		o.setEnabled(isEnabled);
		o.setSelected(isChecked);
		o.setText(label);
		return o;
	}
	
	public static JCheckBox newCheckBox(boolean isChecked, int w, int h, boolean isVisible, boolean isEnabled)
	{
		JCheckBox o = new JCheckBox();
		o.setSize(w, h); 
		o.setVisible(isVisible);
		o.setEnabled(isEnabled);
		o.setSelected(isChecked);
		return o;
	}
	
	public static JTextField newTextField(String textval, int xp, int yp, int w, int h, boolean isVisible, boolean isEditable)
	{
		JTextField o = new JTextField();
		o.setLocation(xp, yp);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		o.setEditable(isEditable);
		o.setText(textval);
		return o;
	}
	
	public static JTextField newTextFieldEx(JPanel parent, String textval, boolean isVisible, boolean isEditable, String layoutPos, ActionListener listener)
	{
		JTextField o = new JTextField();
		o.setVisible(isVisible);
		o.setEditable(isEditable);
		o.setText(textval);
		o.addActionListener(listener);
		parent.add(o, layoutPos);
		return o;
	}
	
	public static JTextArea newTextArea(String textval, int xp, int yp, int w, int h, boolean isVisible, boolean isEditable)
	{
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		
		JTextArea o = new JTextArea();
		o.setLocation(xp, yp);
		o.setSize(w, h);
		o.setVisible(isVisible);
		o.setEditable(isEditable);
		o.setText(textval);
		o.setBorder(border);
		return o;
	}
	
	public static JComboBox newComboBox(Object[] arr, int xp, int yp, int w, int h, boolean isVisible)
	{
		JComboBox o = new JComboBox(arr);
		o.setLocation(xp, yp);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		return o;
	}	

	public static JComboBox newComboBox(Object[] arr, int xp, int yp, int w, int h, boolean isVisible, String action)
	{
		JComboBox o = new JComboBox(arr);
		o.setLocation(xp, yp);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		o.setActionCommand(action);
		return o;
	}	

	public static JComboBox newComboBox(Object[] arr, int w, int h, boolean isVisible)
	{
		JComboBox o = new JComboBox(arr);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		return o;
	}	

	public static JComboBox newComboBox(Object[] arr, int w, int h, boolean isVisible, String action)
	{
		JComboBox o = new JComboBox(arr);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		o.setActionCommand(action);
		return o;
	}	

	public static JTable newTable(JComponent parent, String[] hdr, Object[][] rows, int xp, int yp, int w, int h, boolean isVisible)
	{
        JTable o = new JTable(rows, hdr);
        
		JScrollPane oPanel = new JScrollPane(o);
        oPanel.setBounds(xp, yp, w, h);
		parent.add(oPanel);
		
		return o;
	}	

	public static JList newList(JComponent parent, Object[] arr, int xp, int yp, int w, int h, boolean isVisible)
	{
		JList o = new JList(arr);
		o.setBounds(xp, yp, w, h);
	
		JScrollPane oPanel = new JScrollPane(o);
		oPanel.setLocation(xp, yp);
		oPanel.setSize(w, h); 
		oPanel.setVisible(isVisible);
		parent.add(oPanel);
		
		return o;
	}	
	
	public static JList newList(JComponent parent, ListModel model, int xp, int yp, int w, int h, boolean isVisible)
	{
		JList o = new JList(model);
		o.setBounds(xp, yp, w, h);
		o.setFixedCellHeight(AppDefs.CMDHIST_COMMANDPROMPT_CMDLIST_CELLHEIGHT);
	
		JScrollPane oPanel = new JScrollPane(o);
		oPanel.setLocation(xp, yp);
		oPanel.setSize(w, h); 
		oPanel.setVisible(isVisible);
		parent.add(oPanel);
		
		return o;
	}	
	
	public static JList newListEx(JComponent parent, ListModel model, boolean isVisible, String layoutPos, AdjustmentListener listener)
	{
		JList o = new JList(model);
		//o.setBounds(xp, yp, w, h);
	
		JScrollPane oPanel = new JScrollPane(o);
		oPanel.setVisible(isVisible);
		oPanel.getVerticalScrollBar().addAdjustmentListener(listener);
		parent.add(oPanel, layoutPos);
		
		return o;
	}	
	
	public static JButton newButton(String label, String action, int xp, int yp, int w, int h, boolean isVisible, ActionListener listener)
	{
		JButton o = new JButton(label);
		o.setActionCommand(action);
		o.setLocation(xp, yp);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		if(listener != null)
			o.addActionListener(listener);
		return o;
	}
	
	public static JButton newButton(String label, String action, int w, int h, boolean isVisible, ActionListener listener)
	{
		JButton o = new JButton(label);
		o.setActionCommand(action);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		if(listener != null)
			o.addActionListener(listener);
		return o;
	}
	
	public static JButton newButton(String label, int action, int xp, int yp, int w, int h, boolean isVisible, ActionListener listener)
	{
		JButton o = new JButton(label);
		o.setActionCommand(Integer.toString(action));
		o.setLocation(xp, yp);
		o.setSize(w, h); 
		o.setVisible(isVisible);
		if(listener != null)
			o.addActionListener(listener);
		return o;
	}
	
	public static JButton newButton(String label, int action, int w, int h, boolean isVisible, ActionListener listener)
	{
		JButton o = new JButton(label);
		o.setActionCommand(Integer.toString(action));
		o.setSize(w, h); 
		o.setVisible(isVisible);
		if(listener != null)
			o.addActionListener(listener);
		return o;
	}
	
	public static JButton newImageButton(JFrame frm, String res, String action, int w, int h, boolean isVisible, ActionListener listener)
	{
		Image image = loadAnyIcon(frm, res);

		ImageIcon imageIcon = new ImageIcon(image);
		
		JButton o = new JButton();
		o.setIcon(imageIcon);
		o.setActionCommand(action);
		o.setMargin(new Insets(0, 0, 0, 0));
		o.setSize(w, h); 
		o.setVisible(isVisible);
		if(listener != null)
			o.addActionListener(listener);
		return o;
	}
	
	public static JButton newImageButton(JFrame frm, String res, String action, int w, int h, boolean isVisible, ActionListener listener, String tooltip)
	{
		Image image = loadAnyIcon(frm, res);

		ImageIcon imageIcon = new ImageIcon(image);
		
		JButton o = new JButton();
		o.setIcon(imageIcon);
		o.setActionCommand(action);
		o.setMargin(new Insets(0, 0, 0, 0));
		o.setSize(w, h); 
		o.setVisible(isVisible);
		o.setToolTipText(tooltip);
		if(listener != null)
			o.addActionListener(listener);
		return o;
	}
	
	public static JMenuItem newMenuItem(String label, String action, ActionListener listener)
	{
		JMenuItem mnuItem = new JMenuItem(label);
		mnuItem.setActionCommand(action);
		mnuItem.addActionListener(listener);
		return mnuItem;
	}
	
	public static JMenu newSubmenu(String label)
	{
		JMenu mnu = new JMenu(label);
		return mnu;
	}
	
	public static JMenu newMenu(String label)
	{
		JMenu mnu = new JMenu(label);
		return mnu;
	}
	
}

