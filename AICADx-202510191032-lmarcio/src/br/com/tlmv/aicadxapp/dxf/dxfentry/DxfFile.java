/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DxfFile.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dxf.dxfentry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.TagDataVO;

public class DxfFile 
{
//Private
	private ArrayList<DxfEntry> lsDxfEntry = null;
	private String fileName = null;
	
	private ArrayList<DxfSection> lsDxfSection = null;

	private String templateFileName = null;
	
	private ArrayList<String> templateFileBuff = null;
	
	private ArrayList<String> resultFileBuff = null;
	
//Public
	
	public DxfFile(String fileName) 
	{
		this.fileName = fileName;
		//
		this.lsDxfEntry = new ArrayList<DxfEntry>();
		this.lsDxfSection = new ArrayList<DxfSection>();
		//
		this.templateFileBuff = new ArrayList<String>();
		this.resultFileBuff = new ArrayList<String>();
	}

	public DxfFile(String fileName, String templateFileName) 
	{
		this(fileName);
		//
		this.templateFileName = templateFileName;		
	}
	
	/* Methodes */
	
	// DXFOUT
	//
	public ArrayList<String> loadTemplateDxfFile()
	{
		this.templateFileBuff = FileUtil.readDataAsList(this.templateFileName);
		return this.templateFileBuff;
	}

	public String replaceAllTags(String strIn, ArrayList<TagDataVO> lsTags)
	{
		String strOut = new String(strIn);

		for(TagDataVO oTagData : lsTags) {
			strOut = StringUtil.replace(strOut, oTagData.getTagName(), oTagData.getTagValue());
		}
		return strOut;
	}
	
	public ArrayList<String> processAllTags(ArrayList<TagDataVO> lsTags)
	{
		this.resultFileBuff = new ArrayList<String>();
		for(String strBuff : this.templateFileBuff) {
			String strOut = this.replaceAllTags(strBuff, lsTags);
			this.resultFileBuff.add(strOut);
		}
		return this.resultFileBuff;
	}

	public boolean writeDxfFile()
	{
		boolean bResult = FileUtil.writeDataAsList(this.fileName, this.resultFileBuff);
		return bResult;
	}
	
	// DXFIN
	//
	public long readDxfFile()
	{
		BufferedReader fin = null;		

		long lineNum = 0;
		int dxfCode = -1;

		File f = new File(this.fileName);
		if( f.exists() ) {
			try {
				fin = new BufferedReader(new FileReader(this.fileName));
				String sbuf = null;
				while((sbuf = fin.readLine()) != null) {
					String strDxfCode = sbuf.trim(); 
							
					dxfCode = StringUtil.safeInt(strDxfCode);					
					if((sbuf = fin.readLine()) != null) {
						lineNum += 1;
						
						DxfEntry dxfEntry = new DxfEntry(lineNum, dxfCode, sbuf); 
						this.lsDxfEntry.add(dxfEntry);
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					if(fin != null) fin.close();				
				}
				catch(Exception e1) { }
			}
		}
		return lineNum;
	}

	public long processAllSection()
	{
		this.lsDxfSection = new ArrayList<DxfSection>();

		DxfSection currDxfSection = null;
		long sz = this.szDxfEntry();

		int state = 0;
		
		int pos = 0;
		while(pos < sz) {
			if(state == 0) {
				DxfEntry oEntry1 = this.getDxfEntryAt(pos);

				if(oEntry1.getDxfCode() == AppDefs.DXFCODE_ENTITYTYPE) {
					String strEntityType = oEntry1.getDxfVal();
					if( AppDefs.DXFETYPE_SECTION.equals(strEntityType) ) {
						state = 1;
					}
				}
				pos += 1;
			}
			else if(state == 1) {
				DxfEntry oEntry2 = this.getDxfEntryAt(pos);

				if(oEntry2.getDxfCode() == AppDefs.DXFCODE_NAME) {
					currDxfSection = new DxfSection(
						oEntry2.getDxfLineNum(),
						oEntry2.getDxfCode(),
						oEntry2.getDxfVal());
					
					state = 2;
				}
				pos += 1;
			}
			else if(state == 2) {
				DxfEntry oEntry3 = this.getDxfEntryAt(pos);

				if(oEntry3.getDxfCode() == AppDefs.DXFCODE_ENTITYTYPE) {
					String strEntityType = oEntry3.getDxfVal();
					if( AppDefs.DXFETYPE_ENDSEC.equals(strEntityType) ) {
						this.lsDxfSection.add(currDxfSection);
						state = 0;
					}
					else {
						currDxfSection.addDxfEntry(oEntry3);
					}
				}
				else {
					currDxfSection.addDxfEntry(oEntry3);
				}
				pos += 1;
			}
		}
		
		long szCurrDxfSection = currDxfSection.szDxfEntry();
		return szCurrDxfSection;
	}
	
	/* DEBUG */
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		long sz = this.szDxfSection();
		for(int i = 0; i < sz; i++) {
			DxfSection oDxfSection = this.getDxfSectionAt(i);
			
			String str = String.format(
				"SECTION - LineNum:%s;DxfCode:%s;DxfSectionName:%s;Size:%s", 
				oDxfSection.getDxfLineNum(),
				oDxfSection.getDxfCode(),
				oDxfSection.getDxfSectionName(),
				oDxfSection.szDxfEntry() );
			System.out.println(str);
		}
	}
	
	/* Getters/Setters */

	public ArrayList<DxfEntry> getLsDxfEntry() {
		return this.lsDxfEntry;
	}

	public void setLsDxfEntry(ArrayList<DxfEntry> lsDxfEntry) {
		this.lsDxfEntry = lsDxfEntry;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/* LIST_DXF_ENTRY */
	
	public synchronized long szDxfEntry()
	{
		long sz = this.lsDxfEntry.size();
		return sz;
	}

	public synchronized void addDxfEntry(DxfEntry dxfEntry)
	{
		this.lsDxfEntry.add(dxfEntry);
	}

	public synchronized DxfEntry getDxfEntryAt(int pos)
	{
		DxfEntry oResult = null;
		
		long sz = this.lsDxfEntry.size();
		if(pos < sz) {
			oResult = this.lsDxfEntry.get(pos);
		}
		return oResult;
	}
	
	/* LIST_DXF_SECTION */
	
	public synchronized long szDxfSection()
	{
		long sz = this.lsDxfSection.size();
		return sz;
	}

	public synchronized void addDxfSection(DxfSection dxfSection)
	{
		this.lsDxfSection.add(dxfSection);
	}

	public synchronized DxfSection getDxfSectionAt(int pos)
	{
		DxfSection oResult = null;
		
		long sz = this.lsDxfSection.size();
		if(pos < sz) {
			oResult = this.lsDxfSection.get(pos);
		}
		return oResult;
	}

	public synchronized DxfSection getDxfSectionByName(String strDxfSectionName)
	{
		long sz = this.lsDxfSection.size();
		for(int i = 0; i < sz; i++) {
			DxfSection o = this.lsDxfSection.get(i);
			
			if(strDxfSectionName.compareToIgnoreCase(o.getDxfSectionName()) == 0) {
				return o;
			}
		}
		return null;
	}

	public ArrayList<DxfSection> getLsDxfSection() {
		return this.lsDxfSection;
	}

	public void setLsDxfSection(ArrayList<DxfSection> lsDxfSection) {
		this.lsDxfSection = lsDxfSection;
	}

	public String getTemplateFileName() {
		return this.templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

}
