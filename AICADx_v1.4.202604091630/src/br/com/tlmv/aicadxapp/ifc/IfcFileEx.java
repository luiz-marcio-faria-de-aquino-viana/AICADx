/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * IfcFileEx.java
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

package br.com.tlmv.aicadxapp.ifc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcBaseObject;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcCartesianPoint;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcDataValue;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcFace;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcFaceOuterBound;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcPolyLoop;
import br.com.tlmv.aicadxapp.ifc.ifcentry.IfcPolyline;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.utils.FileUtil;

public class IfcFileEx implements IIfcFile
{
//Private Static
	private static long maxNumLoops = 100000L;
	private static int maxNumRows = 20;
	
	//ARR_TAGS_PROCLEVEL000
	//
	public static String[] ARR_TAGS_PROCLEVEL000 = {
		IfcDefs.tagIfcPolyLoop,
		IfcDefs.tagIfcPolyline,
		IfcDefs.tagIfcCartesianPoint
	};
	
	//ARR_TAGS_PROCLEVEL001
	//
	public static String[] ARR_TAGS_PROCLEVEL001 = {
		IfcDefs.tagIfcPolyLoop,
		IfcDefs.tagIfcPolyline,
		IfcDefs.tagIfcCartesianPoint
	};
	
//Private
	private R r = null;
	
	private String fileName = null;
	
	//DATA_ENTRY (LEVEL_001)
	//
	//private ArrayList<IfcBaseObject> lsIfcSelectedDataEntry_level001 = null;
	
	//DATA_ENTRY (LEVEL_000)
	//
	private Hashtable mapIfcDataEntry = null;
	
	/* Methodes */
	
	//LEVEL_000
	//
	private void showDataEntry_level000(long maxSz)
	{
		int nrows = 0;
		
		System.out.println( this.getR().getString( R.TXT_SHOW_ALL_DATAENTRY ) );
		
		Collection col = this.mapIfcDataEntry.values();
		for(Object o : col) {
			IfcBaseObject oDataEntry = (IfcBaseObject)o;
			System.out.println( oDataEntry.toStr() );

			nrows += 1;
			if(nrows >= maxSz) return;
		}
	}
	
	private void showDataEntryByIfcSectionType_level000(int ifcSectionType, long maxSz)
	{
		int nrows = 0;

		String warnmsg = String.format(this.getR().getString( R.TXT_SHOW_ALL_DATAENTRY_BY_SECTION_TYPE ), ifcSectionType);
		System.out.println( warnmsg );

		Collection col = this.mapIfcDataEntry.values();
		for(Object o : col) {
			IfcBaseObject oDataEntry = (IfcBaseObject)o;

			int sectionType = oDataEntry.getIfcSectionType();
			if(sectionType == ifcSectionType) {
				System.out.println( oDataEntry.toStr() );

				nrows += 1;
				if(nrows >= maxSz) return;
			}
		}
	}
	
	private long loadIfcFile_level000(String fileName)
	{
		long nrows = 0;

		System.out.print( this.getR().getString( R.TXT_PROCESSING ) );

		File f = new File(fileName);
		if( f.exists() ) {
			ArrayList<String> lsDataBuf = FileUtil.readDataAsList(fileName);
			
			int currSectionType = IfcDefs.IFCSECTIONTYPE_NONE_VAL;	
			for(String strBuf : lsDataBuf) {
				nrows += 1;
				
				if((nrows % IfcFileEx.maxNumLoops) == 0) {
					String warnmsg = String.format(this.getR().getString( R.TXT_PROCESSING_STEP ), nrows);
					System.out.print(warnmsg);
				}
				
				IfcBaseObject oDataEntry = new IfcBaseObject(
					this, nrows, currSectionType, strBuf);

				String ifcClass = this.checkDataEntry_level001(IfcFileEx.ARR_TAGS_PROCLEVEL000, oDataEntry);
				if(ifcClass != null) {
					this.mapIfcDataEntry.put(oDataEntry.getIfcKey(), oDataEntry);
				}

				int newIfcSectionType = oDataEntry.getIfcSectionType();
				if(newIfcSectionType != IfcDefs.IFCSECTIONTYPE_NONE_VAL) {
					currSectionType = newIfcSectionType; 
				}
			}
		}

		String warnmsg = String.format(this.getR().getString( R.TXT_NUMBER_OF_PROCESSED_ELEMENTS ), nrows);
		System.out.println( warnmsg );
		
		return nrows;
	}

	//LEVEL_001
	//
	private String checkDataEntry_level001(String[] arr, IfcBaseObject o)
	{
		String ifcClass = o.getIfcClass();

		String warnmsg = String.format("LEVEL_001: CHECK[ IfcClass:%s; ]", ifcClass);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL32, warnmsg, this.getClass());

		for(String tagIfcClass : arr) {
			if( tagIfcClass.equalsIgnoreCase(ifcClass) ) {
				warnmsg = String.format("LEVEL_001: FOUND[ IfcClass:%s; ]", tagIfcClass);
				AppError.showCmdWarn(AppDefs.DEBUG_LEVEL33, warnmsg, this.getClass());
				
				if( IfcDefs.tagIfcFace.equalsIgnoreCase(ifcClass) ) {
					o.debug(AppDefs.DEBUG_LEVEL35);
				}
				else if( IfcDefs.tagIfcFaceOuterBound.equalsIgnoreCase(ifcClass) ) {
					o.debug(AppDefs.DEBUG_LEVEL35);
				}
				else if( IfcDefs.tagIfcPolyLoop.equalsIgnoreCase(ifcClass) ) {
					o.debug(AppDefs.DEBUG_LEVEL35);
				}
				else if( IfcDefs.tagIfcCartesianPoint.equalsIgnoreCase(ifcClass) ) {
					o.debug(AppDefs.DEBUG_LEVEL35);
				}
				return tagIfcClass;
			}
		}
		return null;
	}
	
	public void showSelectedDataEntry_level001(int maxSz, ArrayList<IfcBaseObject> lsIfcSelectedDataEntry)
	{
		int nrows = 0;
		
		System.out.println( this.getR().getString( R.TXT_SHOW_SELECTED_DATAENTRY ) );
		
		for(IfcBaseObject oDataEntry : lsIfcSelectedDataEntry) {
			nrows += 1;
			if(nrows >= maxSz) return;

			System.out.println( oDataEntry.toStr() );
			
			ArrayList<IfcDataValue> lsDataValue = oDataEntry.getLsDataValue();
			for(IfcDataValue oDataValue : lsDataValue) {
				oDataValue.debug(AppDefs.DEBUG_LEVEL38);
			}
		}
	}
	
	private ArrayList<IfcBaseObject> processIfcFile_level001()
	{
		ArrayList<IfcBaseObject> lsIfcSelectedDataEntry = new ArrayList<IfcBaseObject>(); 
		long nrows = 0;

		System.out.print( this.getR().getString( R.TXT_PROCESSING ) );

		Collection col = this.mapIfcDataEntry.values();
		for(Object obj : col) {
			IfcBaseObject o = (IfcBaseObject)obj;

			String ifcClass = this.checkDataEntry_level001(IfcFileEx.ARR_TAGS_PROCLEVEL001, o);
			if(ifcClass != null) {
				o.setIfcClass(ifcClass);
				
				if( IfcDefs.tagIfcFace.equalsIgnoreCase(ifcClass) ) {
					IfcFace oRef = new IfcFace( this, o.getOid(), o.getIfcSectionType(), o.getRawDataEntry() );
					if(oRef != null) {
						oRef.debug(AppDefs.DEBUG_LEVEL38);
						
						lsIfcSelectedDataEntry.add(oRef);
					}
				}
				else if( IfcDefs.tagIfcFaceOuterBound.equalsIgnoreCase(ifcClass) ) {
					IfcFaceOuterBound oRef = new IfcFaceOuterBound( this, o.getOid(), o.getIfcSectionType(), o.getRawDataEntry() );
					if(oRef != null) {
						oRef.debug(AppDefs.DEBUG_LEVEL38);
						
						lsIfcSelectedDataEntry.add(oRef);			
					}
				}
				else if( IfcDefs.tagIfcPolyLoop.equalsIgnoreCase(ifcClass) ) {
					IfcPolyLoop oRef = new IfcPolyLoop( this, o.getOid(), o.getIfcSectionType(), o.getRawDataEntry() );
					if(oRef != null) {
						oRef.debug(AppDefs.DEBUG_LEVEL38);
						
						lsIfcSelectedDataEntry.add(oRef);			
					}
				}
				else if( IfcDefs.tagIfcPolyline.equalsIgnoreCase(ifcClass) ) {
					IfcPolyline oRef = new IfcPolyline( this, o.getOid(), o.getIfcSectionType(), o.getRawDataEntry() );
					if(oRef != null) {
						oRef.debug(AppDefs.DEBUG_LEVEL38);
						
						lsIfcSelectedDataEntry.add(oRef);			
					}
				}
				else if( IfcDefs.tagIfcCartesianPoint.equalsIgnoreCase(ifcClass) ) {
					IfcCartesianPoint oRef = new IfcCartesianPoint( this, o.getOid(), o.getIfcSectionType(), o.getRawDataEntry() );
					if(oRef != null) {
						oRef.debug(AppDefs.DEBUG_LEVEL38);
						
						lsIfcSelectedDataEntry.add(oRef);			
					}
				}
				nrows += 1;
				
				if((nrows % IfcFileEx.maxNumLoops) == 0) {
					String warnmsg = String.format(this.getR().getString( R.TXT_PROCESSING_STEP ), nrows);
					System.out.print(warnmsg);
				}
			}
		}

		String warnmsg = String.format(this.getR().getString( R.TXT_NUMBER_OF_PROCESSED_ELEMENTS ), nrows);
		System.out.println( warnmsg );
		
		return lsIfcSelectedDataEntry;
	}

//Public
	
	public IfcFileEx(String fileName) 
	{
		AppMain app = AppMain.getApp();

		this.r = app.getResource();
		this.fileName = fileName;
		this.mapIfcDataEntry = new Hashtable();
	}
	
	public IfcFileEx(R r, String fileName) 
	{
		this.r = r;
		this.fileName = fileName;
		this.mapIfcDataEntry = new Hashtable();
	}

	/* Methodes */
	
	@Override
	public void execute() {
		this.mapIfcDataEntry = new Hashtable();
		
		this.loadIfcFile_level000(this.fileName);

		this.showDataEntryByIfcSectionType_level000(IfcDefs.IFCSECTIONTYPE_DATA_VAL, IfcFileEx.maxNumRows);		
	}
	
	@Override
	public ArrayList<IfcBaseObject> selectByIfcClass(String ifcClass, long startPos, long endPos) 
	{
		ArrayList<IfcBaseObject> lsIfcSelectedDataEntry = this.processIfcFile_level001();
		
		if(lsIfcSelectedDataEntry == null) return null;
		int sz = lsIfcSelectedDataEntry.size();
		
		if(startPos > endPos) return null;
		if( (startPos < 0) || (startPos >= sz) ) return null;
		if( (endPos < 0) || (endPos >= sz) ) return null;
		
		long nrows = 0;

		ArrayList<IfcBaseObject> lsResult = new ArrayList<IfcBaseObject>();
		
		System.out.print( this.getR().getString( R.TXT_PROCESSING ) );

		for(long i = startPos; i < endPos; i++) {
			IfcBaseObject o = lsIfcSelectedDataEntry.get((int)i);
			
			String strIfcClass = o.getIfcClass();
			if( ifcClass.equalsIgnoreCase( strIfcClass ) ) {
				lsResult.add(o);
				
				nrows += 1;
				if((nrows % IfcFileEx.maxNumLoops) == 0) {
					String warnmsg = String.format(this.getR().getString( R.TXT_PROCESSING_STEP ), nrows);
					System.out.println( warnmsg );
				}
			}
		}

		String warnmsg = String.format(this.getR().getString( R.TXT_NUMBER_OF_PROCESSED_ELEMENTS ), nrows);
		System.out.println( warnmsg );
		
		return lsResult;
	}
	
	@Override
	public ArrayList<IfcBaseObject> selectAllByIfcClass(String ifcClass)
	{
		ArrayList<IfcBaseObject> lsIfcSelectedDataEntry = this.processIfcFile_level001();
		
		if(lsIfcSelectedDataEntry == null) return null;
		int sz = lsIfcSelectedDataEntry.size();
		
		long nrows = 0;

		ArrayList<IfcBaseObject> lsResult = new ArrayList<IfcBaseObject>();
		
		System.out.print( this.getR().getString( R.TXT_PROCESSING ) );

		for(IfcBaseObject o : lsIfcSelectedDataEntry) {
			String strIfcClass = o.getIfcClass();
			if( ifcClass.equalsIgnoreCase( strIfcClass ) ) {
				lsResult.add(o);
				
				nrows += 1;
				if((nrows % IfcFileEx.maxNumLoops) == 0) {
					String warnmsg = String.format(this.getR().getString( R.TXT_PROCESSING_STEP ), nrows);
					System.out.println( warnmsg );
				}
			}
		}

		String warnmsg = String.format(this.getR().getString( R.TXT_NUMBER_OF_PROCESSED_ELEMENTS ), nrows);
		System.out.println( warnmsg );
		
		return lsResult;
	}
	
	/* DEBUG */
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		Collection col = this.mapIfcDataEntry.values();
		for(Object obj : col) {
			IfcBaseObject o = (IfcBaseObject)obj;
			o.debug(debugLevel);				
		}
	}
	
	/* UTILITIES */

	@Override
	public synchronized IfcBaseObject getDataEntryByKey(String ifcKey)
	{
		IfcBaseObject oResult = null;
		
		if( this.mapIfcDataEntry.containsKey(ifcKey) ) {
			oResult = (IfcBaseObject)this.mapIfcDataEntry.get(ifcKey);
		}
		return oResult;
	}

	@Override
	public synchronized IfcBaseObject insertUpdateDataEntryByKey(IfcBaseObject newObj)
	{
		String ifcKey = newObj.getIfcKey();
		
		if( !this.mapIfcDataEntry.containsKey(ifcKey) ) {
			this.mapIfcDataEntry.put(ifcKey, newObj);
		}
		else {
			IfcBaseObject oldObj = (IfcBaseObject)this.mapIfcDataEntry.get(ifcKey);
			if(oldObj != null)
				this.mapIfcDataEntry.remove(oldObj);
			this.mapIfcDataEntry.put(ifcKey, newObj);			
		}
		return newObj;
	}
	
	/* Getters/Setters */
	
	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public R getR() {
		return r;
	}

	public void setR(R r) {
		this.r = r;
	}
	
}
