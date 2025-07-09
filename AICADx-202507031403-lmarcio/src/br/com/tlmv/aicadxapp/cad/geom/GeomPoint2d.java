/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Point2dVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.DataRecordUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ListUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class GeomPoint2d
{
//Private
	private double x;
	private double y;
	
//Public
	
	public GeomPoint2d(double x, double y)
	{
		this.init(x, y);
	}
	
	public GeomPoint2d(GeomPoint2d pt)
	{
		this.init(pt.getX(), pt.getY());
	}
	
	public GeomPoint2d(GeomPoint3d pt)
	{
		this.init(pt.getX(), pt.getY());
	}
	
	public GeomPoint2d(GeomPoint2d ptRef, GeomVector2d xDir, double length)
	{
		GeomPoint2d ptNew = ptRef.otherMoveTo(xDir, length);		
		this.init(ptNew.getX(), ptNew.getY());
	}

	/* Methodes */
	
	public void init(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double distTo(GeomPoint2d pt)
	{
		double d = this.distTo(pt.getX(), pt.getY());
		return d;
	}
	
	public double distTo(double xPt, double yPt)
	{
		double dX = xPt - x;
		double dY = yPt - y;
		
		double d = Math.sqrt(dX * dX + dY * dY);
		return d;
	}
	
	/* ANGLE */
	
	public double angleTo(GeomPoint2d pt2d)
	{
		GeomVector2d v = new GeomVector2d(this, pt2d);
		
		GeomVector2d axisX = GeomUtil.axisX2d();
		double d = axisX.dotProd(v);

		double angRad = Math.acos(d);
		return angRad;		
	}
	
	public double angleTo(GeomPoint3d pt3d)
	{
		GeomPoint3d pt3dI = new GeomPoint3d(this.getX(), this.getY(), 0.0);
		GeomVector3d v = new GeomVector3d(pt3dI, pt3d);
		
		GeomVector3d axisX = GeomUtil.axisX3d();
		double d = axisX.dotProd(v);

		double angRad = Math.acos(d);
		return angRad;		
	}
	
	/* SELF */
	
	public GeomPoint2d selfOffsetTo(double deltaX, double deltaY)
	{
		this.x = this.x + deltaX;
		this.y = this.y + deltaY;

		return this;
	}
	
	public GeomPoint2d selfMoveTo(GeomVector2d xDir, double length)
	{
		GeomVector2d uDir = xDir.otherUnit();
		
		GeomVector2d v = uDir.otherMult(length);

		this.x = this.x + v.getXOrig();
		this.y = this.y + v.getYOrig();
		
		return this;
	}
	
	public GeomPoint2d selfAdd(GeomVector2d v2d)
	{
		double xFinal = this.x + v2d.getXOrig();
		double yFinal = this.y + v2d.getYOrig();

		this.x = xFinal;
		this.y = yFinal;
		
		return this;
	}
	
	public GeomPoint2d selfSub(GeomVector2d v2d)
	{
		double xFinal = this.x - v2d.getXOrig();
		double yFinal = this.y - v2d.getYOrig();

		this.x = xFinal;
		this.y = yFinal;
		
		return this;
	}
	
	/* OTHER */
	
	public GeomPoint2d otherOffsetTo(double deltaX, double deltaY)
	{
		GeomPoint2d ptOrig = new GeomPoint2d(this);

		double xNew = ptOrig.getX() + deltaX;
		double yNew = ptOrig.getY() + deltaY;

		GeomPoint2d ptNew = new GeomPoint2d(xNew, yNew);
		return ptNew;
	}
	
	public GeomPoint2d otherRotateTo(GeomPoint2d ptBase, double angle)
	{
		GeomPoint2d ptOrig = new GeomPoint2d(this);

		GeomVector2d vDir = new GeomVector2d(ptBase, ptOrig);		
		GeomVector2d v = vDir.otherRotateToDegrees(angle);

		double xNew = ptBase.getX() + v.getXOrig();
		double yNew = ptBase.getY() + v.getYOrig();
		
		GeomPoint2d ptNew = new GeomPoint2d(xNew, yNew);
		return ptNew;
	}
	
	public GeomPoint2d otherMoveTo(GeomVector2d xDir, double length)
	{
		GeomPoint2d ptOrig = new GeomPoint2d(this);
		
		GeomVector2d uDir = xDir.otherUnit();		
		GeomVector2d v = uDir.otherMult(length);

		double xNew = ptOrig.getX() + v.getXOrig();
		double yNew = ptOrig.getY() + v.getYOrig();
		
		GeomPoint2d ptNew = new GeomPoint2d(xNew, yNew);
		return ptNew;
	}
	
	public GeomPoint2d otherAdd(GeomVector2d v2d)
	{
		GeomPoint2d ptOrig = new GeomPoint2d(this);

		GeomPoint2d ptNew = ptOrig.selfAdd(v2d);
		return ptNew;
	}
	
	public GeomPoint2d otherSub(GeomVector2d v2d)
	{
		GeomPoint2d ptOrig = new GeomPoint2d(this);

		GeomPoint2d ptNew = ptOrig.selfAdd(v2d);
		return ptNew;
	}
	
	/* UTILITIES */
	
	public static GeomPoint2d lowerLeftCornerFrom(GeomPoint2d pt2dI, GeomPoint2d pt2dF)
	{
		double xMin = Math.min(pt2dI.getX(), pt2dF.getX());
		double yMin = Math.min(pt2dI.getY(), pt2dF.getY());

		GeomPoint2d ptLLC = new GeomPoint2d(xMin, yMin);
		return ptLLC;
	}
	
	public static GeomPoint2d lowerRightCornerFrom(GeomPoint2d pt2dI, GeomPoint2d pt2dF)
	{
		double xMax = Math.max(pt2dI.getX(), pt2dF.getX());
		double yMin = Math.min(pt2dI.getY(), pt2dF.getY());

		GeomPoint2d ptLLC = new GeomPoint2d(xMax, yMin);
		return ptLLC;
	}
	
	public static GeomPoint2d upperRightCornerFrom(GeomPoint2d pt2dI, GeomPoint2d pt2dF)
	{
		double xMax = Math.max(pt2dI.getX(), pt2dF.getX());
		double yMax = Math.max(pt2dI.getY(), pt2dF.getY());

		GeomPoint2d ptURC = new GeomPoint2d(xMax, yMax);
		return ptURC;
	}
	
	public static GeomPoint2d upperLeftCornerFrom(GeomPoint2d pt2dI, GeomPoint2d pt2dF)
	{
		double xMin = Math.min(pt2dI.getX(), pt2dF.getX());
		double yMax = Math.max(pt2dI.getY(), pt2dF.getY());

		GeomPoint2d ptURC = new GeomPoint2d(xMin, yMax);
		return ptURC;
	}
	
	/* AXIAL_ROTATE */
	
	//PLAN-XY
	//
	public GeomPoint3d otherRotatePlanXYRad(GeomPoint3d ptRef, double angleXYRad)
	{
		GeomPoint3d pt3d = new GeomPoint3d(this);
		
		GeomPoint3d ptResult3d = pt3d.otherRotatePlanXYRad(ptRef, angleXYRad);
		return ptResult3d;
	}
	
	public GeomPoint3d otherRotatePlanXYDegrees(GeomPoint3d ptRef, double angleXYDegrees)
	{
		GeomPoint3d pt3d = new GeomPoint3d(this);
		
		GeomPoint3d ptResult3d = pt3d.otherRotatePlanXYDegrees(ptRef, angleXYDegrees);
		return ptResult3d;
	}
	
	//PLAN-ZX
	//
	public GeomPoint3d otherRotatePlanZXRad(GeomPoint3d ptRef, double angleZXRad)
	{
		GeomPoint3d pt3d = new GeomPoint3d(this);
		
		GeomPoint3d ptResult3d = pt3d.otherRotatePlanZXRad(ptRef, angleZXRad);
		return ptResult3d;
	}
	
	public GeomPoint3d otherRotatePlanZXDegrees(GeomPoint3d ptRef, double angleZXDegrees)
	{
		GeomPoint3d pt3d = new GeomPoint3d(this);
		
		GeomPoint3d ptResult3d = pt3d.otherRotatePlanZXDegrees(ptRef, angleZXDegrees);
		return ptResult3d;
	}
	
	//PLAN-YZ
	//
	public GeomPoint3d otherRotatePlanYZRad(GeomPoint3d ptRef, double angleYZRad)
	{
		GeomPoint3d pt3d = new GeomPoint3d(this);
		
		GeomPoint3d ptResult3d = pt3d.otherRotatePlanYZRad(ptRef, angleYZRad);
		return ptResult3d;
	}
	
	public GeomPoint3d otherRotatePlanYZDegrees(GeomPoint3d ptRef, double angleYZDegrees)
	{
		GeomPoint3d pt3d = new GeomPoint3d(this);
		
		GeomPoint3d ptResult3d = pt3d.otherRotatePlanYZDegrees(ptRef, angleYZDegrees);
		return ptResult3d;
	}

	/* PROPERTY_LIST */
	
	public ArrayList<ItemDataVO> toPropertyList(String strLabel)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();

		lsProperty.add( new ItemDataVO(strLabel + " - X", nf3.format(this.getX())) );
		lsProperty.add( new ItemDataVO(strLabel + " - Y", nf3.format(this.getY())) );
		
		return lsProperty;
	}
	
	/* DEBUG */
	
	public String toStr()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"[ %s, %s ]", 
			nf3.format(this.x),
			nf3.format(this.y) );
		return str;
	}
	
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		System.out.println(str);
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
	
}
