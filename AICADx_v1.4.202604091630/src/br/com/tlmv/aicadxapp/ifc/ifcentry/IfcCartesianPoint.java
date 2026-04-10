/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * IfcCartesianPoint.java
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
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class IfcCartesianPoint extends IfcBaseObject
{
//Private
	private double x;
	private double y;
	private double z;
	
//Public
	
	public IfcCartesianPoint() { super(); }
	
	public IfcCartesianPoint(
		IIfcFile ifcFile,
		long oid, 
		int ifcSectionType, 
		String rawDataEntry )
	{
		super(ifcFile, oid, ifcSectionType, rawDataEntry);
		
		this.fromIfcData(this.getIfcFile());
	}

	public IfcCartesianPoint(IfcBaseObject other)
	{
		super(other);
		
		this.fromIfcData(this.getIfcFile());
	}
	
	/* Methodes */
	
	public void init(
		double x,
		double y,
		double z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void init(IfcCartesianPoint other)
	{
		this.x = other.getX();
		this.y = other.getY();
		this.z = other.getZ();
	}

	/* TO/FROM IFC */
	
	@Override
	public void fromIfcData(IIfcFile f) 
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		ArrayList<IfcDataValue> lsParam = this.getLsDataValue();
		int szLsParam = lsParam.size();

		this.x = 0.0;
		if(szLsParam >= 1) {
			IfcDataValue o = lsParam.get(0);
			this.x = StringUtil.safeDbl(nf6, o.getStrVal());
		}
		
		this.y = 0.0;
		if(szLsParam >= 2) {
			IfcDataValue o = lsParam.get(1);
			this.y = StringUtil.safeDbl(nf6, o.getStrVal());
		}
		
		this.z = 0.0;
		if(szLsParam >= 3) {
			IfcDataValue o = lsParam.get(2);
			this.z = StringUtil.safeDbl(nf6, o.getStrVal());			
		}
	}
	
	/* DEBUG */
	
	@Override
	public String toStr()
	{
		String str = String.format(
			"IfcCartesianPoint - X:%s;Y:%s;Z:%s", 
			this.x,
			this.y,
			this.z );
		return str;
	}

	@Override
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* Getters/Setters */

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
}
