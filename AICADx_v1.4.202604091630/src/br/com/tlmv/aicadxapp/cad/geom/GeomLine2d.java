/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GeomLine2dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 25/12/2025
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

package br.com.tlmv.aicadxapp.cad.geom;

import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class GeomLine2d 
{
//Private
	private double xI;
	private double yI;

	private double xF;
	private double yF;
	
	private double const_A;
	private double const_B;

	private boolean bVertical;
	private double const_Xp;		// vertical lines
	
//Public
	
	public GeomLine2d(double xI, double yI, double xF, double yF)
	{
		this.init(xI, yI, xF, yF);
	}
	
	public GeomLine2d(GeomPoint2d ptI, GeomPoint2d ptF)
	{
		this.init(ptI.getX(), ptI.getY(), ptF.getX(), ptF.getY());
	}
	
	public GeomLine2d(GeomLine2d other)
	{
		this.init(other.getXI(), other.getYI(), other.getXF(), other.getYF());
	}
	
	/* Methodes */
	
	public void init(double xI, double yI, double xF, double yF)
	{
		this.xI = xI;
		this.yI = yI;
		
		this.xF = xF;
		this.yF = yF;
		
		double dX = (xF - xI);
		double dY = (yF - yI);

		this.const_A = 0.0;

		if( Math.abs( dX ) < AppDefs.MATHPREC_MIN) {
			this.bVertical = true;			
			this.const_Xp = xI;
			
			this.const_A = 0.0;
			this.const_B = 0.0;			
		}
		else {
			this.bVertical = false;
			this.const_Xp = 0.0;
			
			if( Math.abs( dX ) >= AppDefs.MATHPREC_MIN)
				this.const_A = dY / dX;
			this.const_B = (this.const_A * xI) - yI;			
		}
	}
	
	/* COORDINATES */
	
	public Double getYp(double xp)
	{
		Double yp = null;
		
		if( !this.isVertical() ) {
			yp = (this.const_A * xp) - this.const_B;
		}
		return yp;
	}	
	
	public Double getXp(double yp)
	{
		Double xp = null;
		
		if( !this.isVertical() ) {
			xp = (this.const_A * yp) - this.const_B;
		}
		else {
			xp = this.const_Xp;
		}
		return xp;
	}	

	/* OPERATIONS */
	
	public GeomPoint2d intersectionOf(GeomLine2d other)
	{
		if( this.isVertical() && other.isVertical() ) return null;

		double xp = 0.0;
		double yp = 0.0;

		if( this.isVertical() ) {
			xp = this.getConstXp();
			yp = other.getYp(xp);
		}
		else if( other.isVertical() ) {
			xp = other.getConstXp();
			yp = this.getYp(xp);
		}
		else {
			double dA = this.getConstA() - other.getConstA();
			double dB = this.getConstB() - other.getConstB();
			
			if( Math.abs( dA ) >= AppDefs.MATHPREC_MIN ) {
				xp = dB / dA;
			}
			else {
				xp = this.getXI();
			}
			yp = this.getYp( xp );
		}

		GeomPoint2d ptResult = new GeomPoint2d(xp, yp);				
		return ptResult;
	}
	
	/* PROPERTY_LIST */
	
	public ArrayList<ItemDataVO> toPropertyList(String strLabel)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();

		lsProperty.add( new ItemDataVO(strLabel + " - XI", nf3.format(this.getXI())) );
		lsProperty.add( new ItemDataVO(strLabel + " - YI", nf3.format(this.getYI())) );
		lsProperty.add( new ItemDataVO(strLabel + " - XF", nf3.format(this.getXF())) );
		lsProperty.add( new ItemDataVO(strLabel + " - YF", nf3.format(this.getYF())) );
		lsProperty.add( new ItemDataVO(strLabel + " - A", nf3.format(this.getConstA())) );
		lsProperty.add( new ItemDataVO(strLabel + " - B", nf3.format(this.getConstB())) );
		lsProperty.add( new ItemDataVO(strLabel + " - Xp", nf3.format(this.getConstXp())) );
		
		return lsProperty;
	}
	
	/* DEBUG */
	
	public String toStr()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"[ %s, %s ]-[ %s, %s ]; ConstA:%s; ConstB:%s; ConstXp:%s; ", 
			nf3.format(this.xI),
			nf3.format(this.yI),
			nf3.format(this.xF),
			nf3.format(this.yF),
			nf3.format(this.const_A),
			nf3.format(this.const_B),
			nf3.format(this.const_Xp) );
		return str;
	}
	
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		System.out.println(str);
	}
		
	/* Getters/Setters */

	public double getXI() {
		return this.xI;
	}

	public void setXI(double xI) {
		this.xI = xI;
	}

	public double getYI() {
		return this.yI;
	}

	public void setYI(double yI) {
		this.yI = yI;
	}

	public double getXF() {
		return this.xF;
	}

	public void setXF(double xF) {
		this.xF = xF;
	}

	public double getYF() {
		return this.yF;
	}

	public void setYF(double yF) {
		this.yF = yF;
	}

	public double getConstA() {
		return this.const_A;
	}

	public void setConstA(double const_A) {
		this.const_A = const_A;
	}

	public double getConstB() {
		return const_B;
	}

	public void setConstB(double const_B) {
		this.const_B = const_B;
	}

	public boolean isVertical() {
		return this.bVertical;
	}

	public void setVertical(boolean bVertical) {
		this.bVertical = bVertical;
	}

	public double getConstXp() {
		return this.const_Xp;
	}

	public void setConstXp(double constXp) {
		this.const_Xp = constXp;
	}
	
}
