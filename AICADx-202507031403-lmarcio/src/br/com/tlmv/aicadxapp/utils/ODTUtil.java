/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ODTUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHyperlink;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import br.com.tlmv.aicadxapp.export.IExportData;

public class ODTUtil 
{
//Private
	private String outputFile = null;
	private String templateFile = null;

	private IExportData exportData = null;
	
	private void copyStyle(String newText, XWPFRun newRun, XWPFRun currRun)
	{
		try
		{
			CTR currCTR = currRun.getCTR();
			CTRPr newRPr = null;
			if( currCTR.isSetRPr() )
			{
				CTRPr currRPr = currCTR.getRPr();
				if( currRPr.isSetRStyle() )
				{
					CTString currRPrStyle = currRPr.getRStyle();
					String currStyle = currRPrStyle.getVal();				    
					if( StringUtil.isEmpty(currStyle) ) 
					{
						CTR newCTR = newRun.getCTR();
						if( newCTR.isSetRPr() )
							newRPr = newCTR.getRPr();
						else
							newRPr = newCTR.addNewRPr();

						CTString newStyle = null;
						if( newRPr.isSetRStyle() )
							newStyle = newRPr.getRStyle();
						else
							newStyle = newRPr.addNewRStyle();

						newStyle.setVal(currStyle);
				    }
				}
			}
			
			newRun.setText(newText);
			newRun.setFontSize(currRun.getFontSize());
			newRun.setCharacterSpacing(currRun.getCharacterSpacing());
			newRun.setKerning(currRun.getKerning());
			newRun.setTextScale(currRun.getTextScale());

			if( currRun.isBold() ) 
				newRun.setBold(true);
			if( currRun.isCapitalized() ) 
				newRun.setCapitalized(true);
			if( currRun.isDoubleStrikeThrough() ) 
				newRun.setDoubleStrikethrough(true);
			if( currRun.isEmbossed() ) 
				newRun.setEmbossed(true);
			if( currRun.isEmbossed() ) 
				newRun.setEmbossed(true);
			if( currRun.isImprinted() ) 
				newRun.setImprinted(true);
			if( currRun.isItalic() ) 
				newRun.setItalic(true);
			if( currRun.isShadowed() ) 
				newRun.setShadow(true);
			if( currRun.isVanish() ) 
				newRun.setVanish(true);
			if( currRun.isSmallCaps() ) 
				newRun.setSmallCaps(true);
			if( currRun.isStrikeThrough() ) 
				newRun.setStrikeThrough(true);
			
			if(currRun.getColor() != null) 
				newRun.setColor(currRun.getColor());
			if(currRun.getFontFamily() != null) 
				newRun.setFontFamily(currRun.getFontFamily());			
			if(currRun.getUnderline() != null) 
				newRun.setUnderline(currRun.getUnderline());
			if(currRun.getUnderlineColor() != null) 
				newRun.setUnderlineColor(currRun.getUnderlineColor());
			if(currRun.getTextHightlightColor() != null) 
				newRun.setTextHighlightColor(currRun.getTextHightlightColor().toString());
			if(currRun.getUnderlineThemeColor() != null) 
				newRun.setUnderlineThemeColor(currRun.getUnderlineThemeColor().toString());
			if(currRun.getVerticalAlignment() != null) 
				newRun.setVerticalAlignment(currRun.getVerticalAlignment().toString());	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void setNewText(String newText, XWPFRun newRun, XWPFRun currRun)
	{
		try
		{
			newRun.setFontSize(currRun.getFontSize());

			if( currRun.isBold() ) 
				newRun.setBold(true);
			if( currRun.isItalic() ) 
				newRun.setItalic(true);
			
			if(currRun.getColor() != null) 
				newRun.setColor(currRun.getColor());
			if(currRun.getFontFamily() != null) 
				newRun.setFontFamily(currRun.getFontFamily());			
			if(currRun.getUnderline() != null) 
				newRun.setUnderline(currRun.getUnderline());
			if(currRun.getUnderlineColor() != null) 
				newRun.setUnderlineColor(currRun.getUnderlineColor());
			if(currRun.getTextHightlightColor() != null) 
				newRun.setTextHighlightColor(currRun.getTextHightlightColor().toString());
			if(currRun.getUnderlineThemeColor() != null) 
				newRun.setUnderlineThemeColor(currRun.getUnderlineThemeColor().toString());
			if(currRun.getVerticalAlignment() != null) 
				newRun.setVerticalAlignment(currRun.getVerticalAlignment().toString());	

			newRun.setText(newText);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

//Public
	
	public ODTUtil(String outputFile, String templateFile, IExportData exportData)
	{
		this.outputFile = outputFile;
		this.templateFile = templateFile;
		this.exportData = exportData;
		//
		this.exportData.buildTagDataList();
	}
	
	public void processTable(XWPFDocument doc, List<XWPFTable> lsTables2)
	{
		for(XWPFTable currTable2 : lsTables2)
		{
			List<XWPFTableRow> lsRows2 = currTable2.getRows();
			for(XWPFTableRow currRow2 : lsRows2)
			{	
				List<XWPFTableCell> lsCells2 = currRow2.getTableCells();
				for(XWPFTableCell currCell2 : lsCells2)
				{	
					List<XWPFParagraph> lsParagraphs2 = currCell2.getParagraphs();
					for(XWPFParagraph currParagraph2 : lsParagraphs2)
					{
						List<XWPFRun> lsRuns2 = currParagraph2.getRuns();
						int pos2 = 0;
						while(pos2 < lsRuns2.size())
						{	
							XWPFRun currRun2 = lsRuns2.get(pos2);

							if(currRun2 instanceof XWPFHyperlinkRun) 
							{
								XWPFHyperlinkRun currLinkRun2 = (XWPFHyperlinkRun)currRun2;
								XWPFHyperlink currLink2 = currLinkRun2.getHyperlink(doc);

								String currUrl2 = null;
								if(currLink2 != null)
								{
									currUrl2 = EncodeUtil.decode(currLink2.getURL());
									
									String newUrl2 = this.exportData.replaceTags(currUrl2, false);
									if( !currUrl2.equals(newUrl2) )
									{
										PackageRelationship r = doc.getPackagePart().addExternalRelationship(
											newUrl2, 
											XWPFRelation.HYPERLINK.getRelation());
										
										CTHyperlink cth = (CTHyperlink)currParagraph2.getCTP().addNewHyperlink();
										cth.setId(r.getId());
										cth.addNewR();
										
										currLinkRun2.setHyperlinkId(r.getId());
									}
								}
							}

							String currText2 = null;
							int i2 = 0;
							do {
								currText2 = null;
								try {
									currText2 = currRun2.getText(i2);

									String newText2 = this.exportData.replaceTags(currText2, false);
									if( !currText2.equals(newText2) )
										currRun2.setText(newText2, i2);
									i2++;
								}
								catch(Exception e) { 
									
								}									
							} while(currText2 != null);

							pos2++;
						}
					}
					
					List<XWPFTable> newLsTables2 = currCell2.getTables();
					processTable(doc, newLsTables2);
				}
			}
		}		
	}
	
	public boolean executeXls() {		
		boolean result = false;

		Hashtable<String,String> map = new Hashtable<String,String>(); 
		
		FileInputStream fin = null;
		FileOutputStream fout = null;

		try {		
			fin = new FileInputStream(this.templateFile);		
			XSSFWorkbook workbook = new XSSFWorkbook(fin);		

			int sz = workbook.getNumberOfSheets();
			for(int i = 0; i < sz; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);				

				int firstRow = sheet.getFirstRowNum();
				int lastRow = sheet.getLastRowNum();

				int n = -1;
				for(int j = firstRow; j <= lastRow; j++) {
					if(j < 0) continue;
					
					XSSFRow oRow = sheet.getRow(j);
					if(oRow != null) {
						int firstCell = oRow.getFirstCellNum();
						int lastCell = oRow.getLastCellNum();
						for(int k = firstCell; k <= lastCell; k++) {
							if(k < 0) continue;
							
							XSSFCell oCell = oRow.getCell(k);
							if(oCell != null) {
								CellType c = oCell.getCellType();
								if(c == CellType.STRING) {
									String currText1 = oCell.getStringCellValue();
									int szCurrText1 = currText1.length();
									
									String newText1 = currText1;
									if(szCurrText1 > 0) {
										int posF = szCurrText1 - 1;
										int pos0 = szCurrText1 - 2;
										
										char chI = currText1.charAt(0);
										char chF = currText1.charAt(posF);
										
										if( (chI == '[') && (chF == ']') ) {
											char ch0 = '0';
											if(pos0 >= 0) {
												ch0 = currText1.charAt(pos0);											
											}

											if(ch0 == '#') {
												if( !map.containsKey(currText1) ) {
													map.put(currText1, currText1);
													n = j;
												}

												int pos = j - n;
			
												String currText2 = StringUtil.replace(currText1, "#", Integer.toString(pos));
			
												newText1 = this.exportData.replaceTags(currText2, false);
												if( newText1.equals(currText2) ) {
													newText1 = "";													
												}
											}
											else {
												newText1 = this.exportData.replaceTags(currText1, false);												
											}
										}
									}
									oCell.setCellValue(newText1);
								}
							}
						}
					}
				}
			}

		    fout = new FileOutputStream(new File(this.outputFile));
			workbook.write(fout);
			fout.flush();
			
			workbook.close();
			
			result = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(fout != null) fout.close();
				if(fin != null) fin.close();							
			}
			catch(Exception e1) { }
		}
	
		return result;
	}
	
	public boolean executeDoc() {
		boolean result = false;
		
		FileInputStream fin = null;
		FileOutputStream fout = null;

		try {		
			fin = new FileInputStream(this.templateFile);
		
			XWPFDocument doc = new XWPFDocument(fin);
					
			List<XWPFParagraph> lsParagraphs1 = doc.getParagraphs();
			for(XWPFParagraph currParagraph1 : lsParagraphs1) {
				List<XWPFRun> lsRuns1 = currParagraph1.getRuns();
				int pos1 = 0;
				while(pos1 < lsRuns1.size()) {	
					XWPFRun currRun1 = lsRuns1.get(pos1);
					
					if(currRun1 instanceof XWPFHyperlinkRun) {
						XWPFHyperlinkRun currLinkRun1 = (XWPFHyperlinkRun)currRun1;
						XWPFHyperlink currLink1 = currLinkRun1.getHyperlink(doc);

						String currUrl1 = null;
						int j1 = 0;
						if(currLink1 != null) {
							currUrl1 = EncodeUtil.decode(currLink1.getURL());
							
							String newUrl1 = this.exportData.replaceTags(currUrl1, false);
							if( !currUrl1.equals(newUrl1) ) {
								PackageRelationship r = doc.getPackagePart().addExternalRelationship(
									newUrl1, 
									XWPFRelation.HYPERLINK.getRelation());

								CTHyperlink cth = (CTHyperlink)currParagraph1.getCTP().addNewHyperlink();
								cth.setId(r.getId());
								cth.addNewR();
								
								currLinkRun1.setHyperlinkId(r.getId());
							}
						}
					}

					String currText1 = null;
					int i1 = 0;
					do {
						currText1 = null;
						try {
							currText1 = currRun1.getText(i1);
							
							String newText1 = this.exportData.replaceTags(currText1, false);
							if( !currText1.equals(newText1) )
								currRun1.setText(newText1, i1);
							i1++;
						}
						catch(Exception e) { }
					} while(currText1 != null);

					pos1++;
				}
			}

			List<XWPFTable> lsTables2 = doc.getTables();
			processTable(doc, lsTables2);
			
			fout = new FileOutputStream(this.outputFile);
			doc.write(fout);
			
			//System.out.println(sb.toString());
			
			result = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(fout != null) fout.close();
				if(fin != null) fin.close();							
			}
			catch(Exception e1) { }
		}
	
		return result;
	}
	
}
