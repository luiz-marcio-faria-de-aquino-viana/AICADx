/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * IfcFace.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/03/2026
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

import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.ifc.IIfcFile;
import br.com.tlmv.aicadxapp.ifc.IfcDefs;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class IfcWall extends IfcBaseObject 
{
//Private
	private IfcObjectPlacement ifcObjectPlacement = null;
	private ArrayList<IfcFaceOuterBound> lsIfcFaceOuterBound = new ArrayList<IfcFaceOuterBound>();
	
//Public

	public IfcWall() { super(); }

	public IfcWall(
		IIfcFile ifcFile,
		long oid, 
		int ifcSectionType, 
		String rawDataEntry )
	{
		super(ifcFile, oid, ifcSectionType, rawDataEntry);
		
		this.fromIfcData(this.getIfcFile());
	}

	public IfcWall(IfcWall other)
	{
		super(other);
		
		this.fromIfcData(this.getIfcFile());
	}
	
	/* Methodes */
	
	public void init()
	{
		this.lsIfcFaceOuterBound = new ArrayList<IfcFaceOuterBound>();
	}

	public void init(ArrayList<IfcFaceOuterBound> lsIfcFaceOuterBound)
	{
		this.lsIfcFaceOuterBound = new ArrayList<IfcFaceOuterBound>(lsIfcFaceOuterBound);
	}

	public void init(IfcWall other)
	{
		this.lsIfcFaceOuterBound = new ArrayList<IfcFaceOuterBound>( other.getLsIfcFaceOuterBound() );
	}
	
	/* LIST */

	public synchronized int getSzLsIfcFaceOuterBound() 
	{
		int sz = this.lsIfcFaceOuterBound.size();
		return sz;
	}

	public synchronized void addIfcFaceOuterBound(IfcFaceOuterBound o) 
	{
		this.lsIfcFaceOuterBound.add(o);
	}

	public synchronized IfcFaceOuterBound getIfcFaceOuterBoundAt(int pos) 
	{
		IfcFaceOuterBound oResult = null;
		
		int sz = this.lsIfcFaceOuterBound.size();
		if( (pos >= 0) && (pos < sz) ) {
			oResult = this.lsIfcFaceOuterBound.get(pos);
		}
		return oResult;
	}
	
	/* TO/FROM IFC */
	
	@Override
	public void fromIfcData(IIfcFile f) 
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		ArrayList<IfcDataValue> lsParam = this.getLsDataValue();
		int szLsParam = lsParam.size();
		for(int i = 0; i < szLsParam; i++) {
			IfcDataValue oParam = lsParam.get(i);

			String ifcRowId = oParam.getStrVal();
			String ifcKey = IfcBaseObject.generateIfcKey( 
				AppDefs.NULL_LNG, IfcDefs.IFCSECTIONTYPE_DATA_VAL, ifcRowId); 

			IfcBaseObject o = f.getDataEntryByKey(ifcKey);
			if(o != null) {
				String ifcSubclass = o.getIfcClass();
				if( IfcDefs.tagIfcFaceOuterBound.equalsIgnoreCase(ifcSubclass) ) {
					IfcFaceOuterBound oRef = new IfcFaceOuterBound(
						this.getIfcFile(), o.getOid(), o.getIfcSectionType(), o.getRawDataEntry() );
					this.addIfcFaceOuterBound(oRef);
				}
			}
		}
	}
	
	/* DEBUG */
	
	@Override
	public String toStr()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("IfcFace");
		
		for(IfcFaceOuterBound o : this.lsIfcFaceOuterBound) {
			String str = String.format(
				"(%s)", 
				o.toStr() );
			sb.append(str);
		}
		return sb.toString();
	}

	@Override
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

	/* Getters/Setters */

	public ArrayList<IfcFaceOuterBound> getLsIfcFaceOuterBound() {
		return this.lsIfcFaceOuterBound;
	}

	public void setLsIfcFaceOuterBound(ArrayList<IfcFaceOuterBound> lsIfcFaceOuterBound) {
		this.lsIfcFaceOuterBound = lsIfcFaceOuterBound;
	}
	
}
