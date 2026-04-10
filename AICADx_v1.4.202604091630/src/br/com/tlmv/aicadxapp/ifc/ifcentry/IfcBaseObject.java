/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * IfcDataEntry.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/03/2026
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

package br.com.tlmv.aicadxapp.ifc.ifcentry;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.ifc.IIfcFile;
import br.com.tlmv.aicadxapp.ifc.IfcDefs;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class IfcBaseObject 
{
//Private
	private IIfcFile ifcFile = null;
	private long oid = AppDefs.NULL_LNG;
	private String rawDataEntry = AppDefs.NULL_STR;
	private int ifcSectionType = AppDefs.NULL_INT;
	private String ifcKey = AppDefs.NULL_INTSTR; 
	private String ifcRowId = AppDefs.NULL_INTSTR;
	private String ifcRowData = AppDefs.NULL_STR;
	private String ifcClass = AppDefs.NULL_STR;
	//
	private ArrayList<IfcDataValue> lsDataValue = null;

//Public

	public IfcBaseObject()
	{
		this.ifcFile = null;
		//
		this.oid = AppDefs.NULL_LNG;
		this.rawDataEntry = AppDefs.NULL_STR;
		this.ifcSectionType = AppDefs.NULL_INT;
		this.ifcKey = AppDefs.NULL_INTSTR; 
		this.ifcRowId = AppDefs.NULL_INTSTR;
		this.ifcRowData = AppDefs.NULL_STR;
		this.ifcClass = AppDefs.NULL_STR;
		//
		this.lsDataValue = new ArrayList<IfcDataValue>();
	}

	public IfcBaseObject(
		IIfcFile ifcFile,
		long oid, 
		int ifcSectionType, 
		String rawDataEntry )
	{
		this.init(ifcFile, oid, ifcSectionType, rawDataEntry);
	}
	
	public IfcBaseObject(IfcBaseObject other)
	{
		this.init(other);
	}

	/* Methodes */
	
	public void init(
		IIfcFile ifcFile,
		long oid, 
		int ifcSectionType, 
		String rawDataEntry )
	{
		this.ifcFile = ifcFile;
		this.oid = oid;
		this.rawDataEntry = rawDataEntry;
		this.ifcSectionType = this.parseIfcSectionType(ifcSectionType, this.rawDataEntry);
		
		if( this.ifcSectionType == IfcDefs.IFCSECTIONTYPE_DATA_VAL ) {
			this.ifcRowId = this.parseIfcRowId(rawDataEntry);
			this.ifcRowData = this.parseIfcRowData(rawDataEntry);
			this.ifcClass = this.parseIfcClass(this.getIfcRowData());
		}
		
		this.ifcKey = IfcBaseObject.generateIfcKey(
			this.getOid(), 
			this.ifcSectionType,
			this.ifcRowId); 
	}
		
	public void init(IfcBaseObject other)
	{
		this.ifcFile = other.getIfcFile();
		this.oid = other.getOid();
		this.rawDataEntry = other.getRawDataEntry();
		this.ifcSectionType = other.getIfcSectionType();
		this.ifcKey = other.getIfcKey();
		this.ifcRowId = this.getIfcRowId();
		this.ifcRowData = this.getIfcRowData();
		this.ifcClass = this.getIfcClass();
	}
	
	/* TO/FROM IFC */
	
	public void fromIfcData(IIfcFile f) 
	{
		/* nothing todo! */
	}
	
	public void toIfcData(IIfcFile f) 
	{
		/* nothing todo! */
	}
	
	/* DEBUG */
	
	public String toStr()
	{
		String str = String.format(
			"Oid:%s;" +
			"IfcSectionType:%s;" +
			"IfcKey:%s;" +
			"IfcRowId:%s;" +
			"IfcClass:%s;" +
			"IfcRowData:%s;" +
			"RawDataEntry:%s; ",
			this.oid,
			this.ifcSectionType,
			this.ifcKey, 
			this.ifcRowId,
			this.ifcClass,
			this.ifcRowData,
			this.rawDataEntry );
		return str;
	}

	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* PARSER */
	
	private int parseIfcSectionType(int sectionType, String rawDataEntry)
	{
		int ifcSectionType = sectionType;
		
		if( rawDataEntry.equalsIgnoreCase( IfcDefs.IFCSECTION_FILEDEF_STR )  ) {
			ifcSectionType = IfcDefs.IFCSECTIONTYPE_FILEDATA_VAL;
		}
		if( rawDataEntry.equalsIgnoreCase( IfcDefs.IFCSECTION_HEADER_STR )  ) {
			ifcSectionType = IfcDefs.IFCSECTIONTYPE_HEADER_VAL;
		}
		if( rawDataEntry.equalsIgnoreCase( IfcDefs.IFCSECTION_DATA_STR )  ) {
			ifcSectionType = IfcDefs.IFCSECTIONTYPE_DATA_VAL;
		}
		if( rawDataEntry.equalsIgnoreCase( IfcDefs.IFCSECTION_ENDSEC_STR )  ) {
			ifcSectionType = IfcDefs.IFCSECTIONTYPE_FILEDATA_VAL;
		}
		if( rawDataEntry.equalsIgnoreCase( IfcDefs.IFCSECTION_ENDFILEDEF_STR )  ) {
			ifcSectionType = IfcDefs.IFCSECTIONTYPE_FILEDATA_VAL;
		}
		else {
			/* nothing todo! */
		}
		return ifcSectionType;
	}
	
	public String parseIfcRowId(String rawDataEntry)
	{
		String ifcRowId = StringUtil.getHead(rawDataEntry, '=');
		return ifcRowId;
	}

	public String parseIfcRowData(String rawDataEntry)
	{
		String ifcRowData = StringUtil.getTail(rawDataEntry, '=');
		int sz = ifcRowData.length();
		if(sz > 0) {
			ifcRowData = ifcRowData.substring(1);
			ifcRowData = StringUtil.trimAll( ifcRowData );
		}
		return ifcRowData;
	}

	public String parseIfcClass(String ifcRowData)
	{
		String ifcClass = StringUtil.getHead(ifcRowData, '(');

		String strParam = StringUtil.getHead( StringUtil.getTail(ifcRowData, '('), ')');
		int sz = strParam.length();
		if(sz > 0) {
			strParam = strParam.substring(1);
		}		
		this.lsDataValue = this.parseIfcClassParam(ifcClass, strParam); 

		return ifcClass;
	}

	public ArrayList<IfcDataValue> parseIfcClassParam(String ifcClass, String strParam)
	{
		ArrayList<IfcDataValue> lsResult = new ArrayList<IfcDataValue>();

		int szStrParam = strParam.length();
		if(szStrParam > 0) {

			String[] arr = StringUtil.split(strParam, ',');
			int sz = arr.length;
			for(int i = 0; i < sz; i++) {
				String strVal = arr[i];
				
				int szStrVal = strVal.length();
				if(szStrVal > 0) {
					int lastPos = szStrVal - 1;

					char c0 = strVal.charAt(lastPos);
					if(c0 == ')') {
						strVal = strVal.substring(0, lastPos - 1);
					}
					
					szStrVal = strVal.length();
					if(szStrVal > 0) {
						char c1 = strVal.charAt(0);
						if(c1 == '(') {
							strVal = strVal.substring(1);
						}
					}

					IfcDataValue o = new IfcDataValue(
						i, 
						IfcDefs.tagIfcUnknow,
						strVal );
					lsResult.add(o);
				}
			}
		}
		return lsResult;
	}
	
	/* UTILITIES */
	
	public static String generateIfcKey(long oid, int ifcSectionType, String ifcRowId)
	{
		String strKey = AppDefs.NULL_INTSTR; 

		if( ifcSectionType == IfcDefs.IFCSECTIONTYPE_DATA_VAL ) {
			strKey = String.format(
				"%s_%s",
				ifcSectionType,
				ifcRowId );
		}
		else {
			strKey = String.format(
				"%s_%s",
				ifcSectionType,
				oid );
		}
		return strKey;
	}
	
	/* Getters/Setters */
		
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getRawDataEntry() {
		return rawDataEntry;
	}

	public void setRawDataEntry(String rawDataEntry) {
		this.rawDataEntry = rawDataEntry;
	}

	public String getIfcClass() {
		return ifcClass;
	}

	public void setIfcClass(String ifcClass) {
		this.ifcClass = ifcClass;
	}

	public ArrayList<IfcDataValue> getLsDataValue() {
		return lsDataValue;
	}

	public void setLsDataValue(ArrayList<IfcDataValue> lsDataValue) {
		this.lsDataValue = lsDataValue;
	}

	public int getIfcSectionType() {
		return ifcSectionType;
	}

	public void setIfcSectionType(int ifcSectionType) {
		this.ifcSectionType = ifcSectionType;
	}

	public String getIfcRowId() {
		return ifcRowId;
	}

	public void setIfcRowId(String ifcRowId) {
		this.ifcRowId = ifcRowId;
	}

	public String getIfcRowData() {
		return ifcRowData;
	}

	public void setIfcRowData(String ifcRowData) {
		this.ifcRowData = ifcRowData;
	}

	public String getIfcKey() {
		return ifcKey;
	}

	public void setIfcKey(String ifcKey) {
		this.ifcKey = ifcKey;
	}

	public IIfcFile getIfcFile() {
		return ifcFile;
	}

	public void setIfcFile(IIfcFile ifcFile) {
		this.ifcFile = ifcFile;
	}
		
}
