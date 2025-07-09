/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomDimension2d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 06/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class GeomDimension2d
{
//Private
	private GeomPoint2d ptMin;
	private GeomPoint2d ptMax;
	
	private GeomPoint2d ptCentroid;
	
	private GeomPoint2d ptMiddleHoriz;
	private GeomPoint2d ptMiddleVert;
	
	private double w;
	private double h;
	
	private double area;
	
//Public
	
	public GeomDimension2d(
		GeomPoint2d ptI,
		GeomPoint2d ptF)
	{
		this.init(ptI, ptF);
	}

	public GeomDimension2d(
		GeomPoint2d ptCentroid,
		double w,
		double h)
	{
		this.init(ptCentroid, w, h);
	}
	
	/* Methodes */

	public void init(
		GeomPoint2d ptI,
		GeomPoint2d ptF)
	{
		GeomPoint2d[] arrPts = GeomUtil.maxMinPointOf(ptI, ptF);
		this.ptMin = arrPts[0];
		this.ptMax = arrPts[1];

		this.ptCentroid = GeomUtil.midPointOf(this.ptMin, this.ptMax);
		
		this.ptMiddleHoriz = new GeomPoint2d(this.ptCentroid.getX(), this.ptMin.getY());
		this.ptMiddleVert = new GeomPoint2d(this.ptMin.getX(), this.ptCentroid.getY());

		this.w = Math.abs(this.ptMax.getX() - this.ptMin.getX());
		this.h = Math.abs(this.ptMax.getY() - this.ptMin.getY());
		
		this.area = this.w * this.h;
	}
	
	public void init(
		GeomPoint2d ptCentroid,
		double w,
		double h)
	{
		this.ptCentroid = new GeomPoint2d(ptCentroid);
		double xCentroid = ptCentroid.getX();
		double yCentroid = ptCentroid.getY();

		this.w = w;
		this.h = h;
		
		double xMin = xCentroid - w / 2.0;
		double yMin = yCentroid - h / 2.0;
		this.ptMin = new GeomPoint2d(xMin, yMin);
		
		double xMax = xCentroid + w / 2.0;
		double yMax = yCentroid + h / 2.0;
		this.ptMax = new GeomPoint2d(xMax, yMax);

		this.ptMiddleHoriz = new GeomPoint2d(this.ptCentroid.getX(), this.ptMin.getY());
		this.ptMiddleVert = new GeomPoint2d(this.ptMin.getX(), this.ptCentroid.getY());

		this.area = this.w * this.h;
	}
		
	/* DEBUG */
	
	public String toStr()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"GeomDimension2d - PtMin:%s;PtMax:%s;PtCentroid:%s;PtMiddleHoriz:%s;PtMiddleVert:%s;W:%s;H:%s;Area:%s",
			this.ptMin.toStr(),
			this.ptMax.toStr(),
			this.ptCentroid.toStr(),
			this.ptMiddleHoriz.toStr(),
			this.ptMiddleVert.toStr(),
			nf3.format(this.w),
			nf3.format(this.h),
			nf3.format(this.area) );
		return str;
	}
	
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		System.out.println(str);
	}
	
	/* Getters/Setters */

	public GeomPoint2d getPtMin() {
		return ptMin;
	}

	public void setPtMin(GeomPoint2d ptMin) {
		this.ptMin = ptMin;
	}

	public GeomPoint2d getPtMax() {
		return ptMax;
	}

	public void setPtMax(GeomPoint2d ptMax) {
		this.ptMax = ptMax;
	}

	public GeomPoint2d getPtCentroid() {
		return ptCentroid;
	}

	public void setPtCentroid(GeomPoint2d ptCentroid) {
		this.ptCentroid = ptCentroid;
	}

	public GeomPoint2d getPtMiddleHoriz() {
		return ptMiddleHoriz;
	}

	public void setPtMiddleHoriz(GeomPoint2d ptMiddleHoriz) {
		this.ptMiddleHoriz = ptMiddleHoriz;
	}

	public GeomPoint2d getPtMiddleVert() {
		return ptMiddleVert;
	}

	public void setPtMiddleVert(GeomPoint2d ptMiddleVert) {
		this.ptMiddleVert = ptMiddleVert;
	}

	public String getWidthStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String strW = String.format(
			"Width: %s m", 
			nf3.format(w) );
		return strW;
	}

	public String getWidthStr(String str) {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String strW = String.format(
			"%s: %s m", 
			str,
			nf3.format(w) );
		return strW;
	}

	public double getWidth() {
		return w;
	}

	public void setWidth(double w) {
		this.w = w;
	}

	public String getHeightStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String strH = String.format(
			"Height: %s m", 
			nf3.format(h) );
		return strH;
	}

	public String getHeightStr(String str) {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String strH = String.format(
			"%s: %s m",
			str,
			nf3.format(h) );
		return strH;
	}

	public double getHeight() {
		return h;
	}

	public void setHeight(double h) {
		this.h = h;
	}

	public String getAreaStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String strArea = String.format(
			"Area: %s m2", 
			nf3.format(area) );
		return strArea;
	}

	public String getHalfWidthStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double hW = w / 2.0;
		
		String strHW = String.format(
			"Width: %s m", 
			nf3.format(hW) );
		return strHW;
	}

	public String getHalfWidthStr(String str) {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double hW = w / 2.0;
		
		String strHW = String.format(
			"%s: %s m", 
			str,
			nf3.format(hW) );
		return strHW;
	}

	public String getHalfHeightStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double hH = h / 2.0;
		
		String strHH = String.format(
			"Width: %s m", 
			nf3.format(hH) );
		return strHH;
	}

	public String getHalfHeightStr(String str) {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double hH = h / 2.0;
		
		String strHH = String.format(
			"%s: %s m",
			str,
			nf3.format(hH) );
		return strHH;
	}
	
	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}	
	
}
