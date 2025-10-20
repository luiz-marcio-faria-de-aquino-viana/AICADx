/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomDimension3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
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

public class GeomDimension3d
{
//Private
	private GeomPoint3d ptMin;
	private GeomPoint3d ptMax;
	//
	private GeomPoint3d ptCentroid;
	//
	private GeomPoint3d ptMiddleHoriz;
	private GeomPoint3d ptMiddleVert;
	private GeomPoint3d ptMiddleElev;
	
	private double w;
	private double h;
	//
	private double zH;

	private double areaBase;
	private double volume;
	
//Public
	
	public GeomDimension3d(
		GeomPoint3d ptI,
		GeomPoint3d ptF)
	{
		this.init(ptI, ptF);
	}

	public GeomDimension3d(
		GeomPoint3d ptCentroid,
		double w,
		double h,
		double zH)
	{
		this.init(ptCentroid, w, h, zH);
	}
	
	/* Methodes */

	public void init(
		GeomPoint3d ptI,
		GeomPoint3d ptF)
	{
		GeomPoint3d[] arrPts = GeomUtil.maxMinPointOf(ptI, ptF);
		this.ptMin = arrPts[0];
		this.ptMax = arrPts[1];

		this.ptCentroid = GeomUtil.midPointOf(this.ptMin, this.ptMax);
		
		this.ptMiddleHoriz = new GeomPoint3d(this.ptCentroid.getX(), this.ptMin.getY(), this.ptMin.getZ());
		this.ptMiddleVert = new GeomPoint3d(this.ptMin.getX(), this.ptCentroid.getY(), this.ptMin.getZ());
		this.ptMiddleElev = new GeomPoint3d(this.ptMin.getX(), this.ptMin.getY(), this.ptCentroid.getZ());

		this.w = Math.abs(this.ptMax.getX() - this.ptMin.getX());
		this.h = Math.abs(this.ptMax.getY() - this.ptMin.getY());
		this.zH = Math.abs(this.ptMax.getZ() - this.ptMin.getZ());
		
		this.areaBase = this.w * this.h;
		this.volume = this.areaBase * this.zH;
	}
	
	public void init(
		GeomPoint3d ptCentroid,
		double w,
		double h,
		double zH)
	{
		this.ptCentroid = new GeomPoint3d(ptCentroid);
		double xCentroid = ptCentroid.getX();
		double yCentroid = ptCentroid.getY();
		double zCentroid = ptCentroid.getZ();

		this.w = w;
		this.h = h;
		this.zH = zH;
		
		double xMin = xCentroid - (w / 2.0);
		double yMin = yCentroid - (h / 2.0);
		double zMin = zCentroid - (zH / 2.0);
		this.ptMin = new GeomPoint3d(xMin, yMin, zMin);
		
		double xMax = xCentroid + (w / 2.0);
		double yMax = yCentroid + (h / 2.0);
		double zMax = zCentroid + (zH / 2.0);
		this.ptMax = new GeomPoint3d(xMax, yMax, zMax);

		this.ptMiddleHoriz = new GeomPoint3d(this.ptCentroid.getX(), this.ptMin.getY(), this.ptMin.getZ());
		this.ptMiddleVert = new GeomPoint3d(this.ptMin.getX(), this.ptCentroid.getY(), this.ptMin.getY());
		this.ptMiddleElev = new GeomPoint3d(this.ptMin.getX(), this.ptMin.getY(), this.ptCentroid.getZ());

		this.areaBase = this.w * this.h;
		this.volume = this.areaBase * this.zH;
	}
		
	/* DEBUG */
	
	public String toStr()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"GeomDimension2d - PtMin:%s;PtMax:%s;PtCentroid:%s;PtMiddleHoriz:%s;PtMiddleVert:%s;PtMiddleElev:%s;W:%s;H:%s;ZH:%s;AreaBase:%s;Volume:%s",
			this.ptMin.toStr(),
			this.ptMax.toStr(),
			this.ptCentroid.toStr(),
			this.ptMiddleHoriz.toStr(),
			this.ptMiddleVert.toStr(),
			this.ptMiddleElev.toStr(),
			nf3.format(this.w),
			nf3.format(this.h),
			nf3.format(this.zH),
			nf3.format(this.areaBase),
			nf3.format(this.volume) );
		return str;
	}
	
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		System.out.println(str);
	}
	
	/* Getters/Setters */

	public GeomPoint3d getPtMin() {
		return ptMin;
	}

	public GeomPoint3d getPtMax() {
		return ptMax;
	}

	public GeomPoint3d getPtCentroid() {
		return ptCentroid;
	}

	public GeomPoint3d getPtMiddleHoriz() {
		return ptMiddleHoriz;
	}

	public GeomPoint3d getPtMiddleVert() {
		return ptMiddleVert;
	}

	public GeomPoint3d getPtMiddleElev() {
		return ptMiddleElev;
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

	public double getElev() {
		return zH;
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

	public String getElevStr(String str) {
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

	public String getAreaBaseStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String strAreaBase = String.format(
			"Area: %s m2", 
			nf3.format(areaBase) );
		return strAreaBase;
	}

	public String getVolumeStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String strVolume = String.format(
			"Volume: %s m2", 
			nf3.format(volume) );
		return strVolume;
	}

	public String getHalfWidthStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double hW = w / 2.0;
		
		String strHW = String.format(
			"Width: %s m", 
			nf3.format(hW) );
		return strHW;
	}

	public String getHalfHeightStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double hH = h / 2.0;
		
		String strHH = String.format(
			"Height: %s m", 
			nf3.format(hH) );
		return strHH;
	}

	public String getHalfElevStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double hZH = zH / 2.0;
		
		String strHH = String.format(
			"Height: %s m", 
			nf3.format(hZH) );
		return strHH;
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

	public String getHalfHeightStr(String str) {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double hH = h / 2.0;
		
		String strHH = String.format(
			"%s: %s m",
			str,
			nf3.format(hH) );
		return strHH;
	}

	public String getHalfElevStr(String str) {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		double hZH = zH / 2.0;
		
		String strHH = String.format(
			"%s: %s m",
			str,
			nf3.format(hZH) );
		return strHH;
	}
	
	public double getAreaBase() {
		return areaBase;
	}

	public double getVolume() {
		return volume;
	}
	
}
