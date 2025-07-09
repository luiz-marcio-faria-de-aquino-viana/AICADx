/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Point3dVO.java
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
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class GeomPoint3d
{
//Private
	private double x;
	private double y;
	private double z;
	
//Public
	
	public GeomPoint3d(double x, double y, double z)
	{
		this.init(x, y, z);
	}
	
	public GeomPoint3d(GeomPoint2d pt)
	{
		this.init(pt.getX(), pt.getY(), 0.0);
	}
	
	public GeomPoint3d(GeomPoint2d pt, double zH)
	{
		this.init(pt.getX(), pt.getY(), zH);
	}
	
	public GeomPoint3d(GeomPoint3d pt)
	{
		this.init(pt.getX(), pt.getY(), pt.getZ());
	}
	
	public GeomPoint3d(GeomPoint3d ptRef, GeomVector3d xDir, double length)
	{
		GeomPoint3d ptNew = ptRef.otherMoveTo(xDir, length);		
		this.init(ptNew.getX(), ptNew.getY(), ptNew.getZ());
	}

	/* Methodes */
	
	public void init(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double distTo(GeomPoint3d pt)
	{
		double d = this.distTo(pt.getX(), pt.getY(), pt.getZ());
		return d;
	}
	
	public double distTo(double xPt, double yPt, double zPt)
	{
		double dX = xPt - x;
		double dY = yPt - y;
		double dZ = zPt - z;
		
		double d = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
		return d;
	}
	
	/* ANGLE */
	
	public double angleTo(GeomPoint3d pt3d)
	{
		GeomVector3d v = new GeomVector3d(this, pt3d);
		
		GeomVector3d axisX = GeomUtil.axisX3d();
		double d = axisX.dotProd(v);

		double angRad = Math.acos(d);
		return angRad;		
	}
	
	public double angleTo(GeomPoint2d pt2d)
	{
		GeomPoint2d pt2dI = new GeomPoint2d(this.getX(), this.getY());
		GeomVector2d v = new GeomVector2d(pt2dI, pt2d);
		
		GeomVector2d axisX = GeomUtil.axisX2d();
		double d = axisX.dotProd(v);

		double angRad = Math.acos(d);
		return angRad;		
	}
	
	/* SELF */
	
	public GeomPoint3d selfMoveTo(GeomVector3d xDir, double length)
	{
		GeomVector3d uDir = xDir.otherUnit();		
		GeomVector3d v = uDir.otherMult(length);

		this.x = this.x + v.getXOrig();
		this.y = this.y + v.getYOrig();
		this.z = this.z + v.getZOrig();
		
		return this;
	}
	
	public GeomPoint3d selfAdd(GeomVector3d v3d)
	{
		double xFinal = this.x + v3d.getXOrig();
		double yFinal = this.y + v3d.getYOrig();
		double zFinal = this.z + v3d.getZOrig();

		this.x = xFinal;
		this.y = yFinal;
		this.z = zFinal;
		
		return this;
	}
	
	public GeomPoint3d selfSub(GeomVector3d v3d)
	{
		double xFinal = this.x - v3d.getXOrig();
		double yFinal = this.y - v3d.getYOrig();
		double zFinal = this.z - v3d.getZOrig();

		this.x = xFinal;
		this.y = yFinal;
		this.z = zFinal;
		
		return this;
	}
	
	/* OTHER */
	
	public GeomPoint3d otherOffsetTo(double deltaX, double deltaY, double deltaZ)
	{
		GeomPoint3d ptOrig = new GeomPoint3d(this);

		double xNew = ptOrig.getX() + deltaX;
		double yNew = ptOrig.getY() + deltaY;
		double zNew = ptOrig.getZ() + deltaZ;

		GeomPoint3d ptNew = new GeomPoint3d(xNew, yNew, zNew);
		return ptNew;
	}

	public GeomPoint3d otherMoveTo(GeomVector3d xDir, double length)
	{
		GeomPoint3d ptOrig = new GeomPoint3d(this);
		
		GeomVector3d uDir = xDir.otherUnit();		
		GeomVector3d v = uDir.otherMult(length);

		double xNew = ptOrig.getX() + v.getXOrig();
		double yNew = ptOrig.getY() + v.getYOrig();
		double zNew = ptOrig.getZ() + v.getZOrig();
		
		GeomPoint3d ptNew = new GeomPoint3d(xNew, yNew, zNew);
		return ptNew;
	}
	
	public GeomPoint3d otherAdd(GeomVector3d v3d)
	{
		GeomPoint3d pt3d = new GeomPoint3d(this);
		pt3d.selfAdd(v3d);
		return pt3d;
	}
	
	public GeomPoint3d otherSub(GeomVector3d v3d)
	{
		GeomPoint3d pt3d = new GeomPoint3d(this);
		pt3d.selfSub(v3d);
		return pt3d;
	}

	/* UTILITIES */
	
	//FROM_GEOMPOINT2D
	//
	public static GeomPoint3d lowerLeftCornerFrom(GeomPoint2d pt2dI, GeomPoint2d pt2dF)
	{
		double xMin = Math.min(pt2dI.getX(), pt2dF.getX());
		double yMin = Math.min(pt2dI.getY(), pt2dF.getY());

		GeomPoint3d ptLLC = new GeomPoint3d(xMin, yMin, 0.0);
		return ptLLC;
	}
	
	public static GeomPoint3d lowerRightCornerFrom(GeomPoint2d pt2dI, GeomPoint2d pt2dF)
	{
		double xMax = Math.max(pt2dI.getX(), pt2dF.getX());
		double yMin = Math.min(pt2dI.getY(), pt2dF.getY());

		GeomPoint3d ptLLC = new GeomPoint3d(xMax, yMin, 0.0);
		return ptLLC;
	}
	
	public static GeomPoint3d upperRightCornerFrom(GeomPoint2d pt2dI, GeomPoint2d pt2dF)
	{
		double xMax = Math.max(pt2dI.getX(), pt2dF.getX());
		double yMax = Math.max(pt2dI.getY(), pt2dF.getY());

		GeomPoint3d ptURC = new GeomPoint3d(xMax, yMax, 0.0);
		return ptURC;
	}
	
	public static GeomPoint3d upperLeftCornerFrom(GeomPoint2d pt2dI, GeomPoint2d pt2dF)
	{
		double xMin = Math.min(pt2dI.getX(), pt2dF.getX());
		double yMax = Math.max(pt2dI.getY(), pt2dF.getY());

		GeomPoint3d ptURC = new GeomPoint3d(xMin, yMax, 0.0);
		return ptURC;
	}
	
	/* AXIAL_ROTATE */

	//PLAN-XY
	//
	public GeomPoint3d otherRotatePlanXYRad(GeomPoint3d ptRef, double stepAngleZRad)
	{
		GeomVector3d v3d = new GeomVector3d(ptRef, this);
		double d = v3d.mod();
		
		GeomVector3d axisZ = GeomUtil.axisZ3d();

		double startAngleZRad = axisZ.angleTo(v3d);
		double endAngleZRad = startAngleZRad + stepAngleZRad;

		double newZOrig = ptRef.getZ() + (d * Math.cos(endAngleZRad));
		double dXY = d * Math.sin(endAngleZRad);

		GeomVector2d vXY2d = new GeomVector2d(v3d);
		GeomVector2d uXY2d = vXY2d.otherUnit();

		GeomVector2d axisX = GeomUtil.axisX2d();

		double angleXRad = axisX.angleTo(uXY2d);
		double newXOrig = ptRef.getX() + (dXY * Math.cos(angleXRad));
		double newYOrig = ptRef.getY() + (dXY * Math.sin(angleXRad));

		//RESULT
		//
		GeomPoint3d ptResult3d = new GeomPoint3d(newXOrig, newYOrig, newZOrig); 
		return ptResult3d;
	}

	public GeomPoint3d otherRotatePlanXYDegrees(GeomPoint3d ptRef, double stepAngleZDegrees)
	{
		double stepAngleZRad = GeomUtil.convertDegreesToRad(stepAngleZDegrees);

		GeomPoint3d other = this.otherRotatePlanXYRad(ptRef, stepAngleZRad);
		return other;
	}

	//PLAN-ZX
	//
	public GeomPoint3d otherRotatePlanZXRad(GeomPoint3d ptRef, double stepAngleYRad)
	{
		GeomVector3d v3d = new GeomVector3d(ptRef, this);
		double d = v3d.mod();
		
		GeomVector3d axisY3d = GeomUtil.axisY3d();

		double startAngleYRad = axisY3d.angleTo(v3d);
		double endAngleYRad = startAngleYRad + stepAngleYRad;

		double newYOrig = ptRef.getY() + (d * Math.cos(endAngleYRad));
		double dZX = d * Math.sin(endAngleYRad);

		GeomVector2d vZX2d = new GeomVector2d(v3d.getZOrig(), v3d.getXOrig());
		GeomVector2d uZX2d = vZX2d.otherUnit();

		GeomVector2d axisX2d = GeomUtil.axisX2d();

		double angleXRad = axisX2d.angleTo(uZX2d);
		double newZOrig = ptRef.getZ() + (dZX * Math.cos(angleXRad));
		double newXOrig = ptRef.getX() + (dZX * Math.sin(angleXRad));

		//RESULT
		//
		GeomPoint3d ptResult3d = new GeomPoint3d(newXOrig, newYOrig, newZOrig); 
		return ptResult3d;
	}

	public GeomPoint3d otherRotatePlanZXDegrees(GeomPoint3d ptRef, double stepAngleYDegrees)
	{
		double stepAngleYRad = GeomUtil.convertDegreesToRad(stepAngleYDegrees);

		GeomPoint3d other = this.otherRotatePlanZXRad(ptRef, stepAngleYRad);
		return other;
	}

	//PLAN-YZ
	//
	public GeomPoint3d otherRotatePlanYZRad(GeomPoint3d ptRef, double stepAngleXDegrees)
	{
		GeomVector3d v3d = new GeomVector3d(ptRef, this);
		double d = v3d.mod();
		
		GeomVector3d axisX3d = GeomUtil.axisX3d();

		double startAngleXRad = axisX3d.angleTo(v3d);
		double endAngleXRad = startAngleXRad + stepAngleXDegrees;

		double newXOrig = ptRef.getX() + (d * Math.cos(endAngleXRad));
		double dYZ = d * Math.sin(endAngleXRad);

		GeomVector2d vYZ2d = new GeomVector2d(v3d.getYOrig(), v3d.getZOrig());
		GeomVector2d uYZ2d = vYZ2d.otherUnit();

		GeomVector2d axisX2d = GeomUtil.axisX2d();

		double angleXRad = axisX2d.angleTo(uYZ2d);
		double newYOrig = ptRef.getY() + (dYZ * Math.cos(angleXRad));
		double newZOrig = ptRef.getZ() + (dYZ * Math.cos(angleXRad));

		//RESULT
		//
		GeomPoint3d ptResult3d = new GeomPoint3d(newXOrig, newYOrig, newZOrig); 
		return ptResult3d;
	}

	public GeomPoint3d otherRotatePlanYZDegrees(GeomPoint3d ptRef, double stepAngleXDegrees)
	{
		double stepAngleYRad = GeomUtil.convertDegreesToRad(stepAngleXDegrees);

		GeomPoint3d other = this.otherRotatePlanYZRad(ptRef, stepAngleYRad);
		return other;
	}

	/* PROPERTY_LIST */
	
	public ArrayList<ItemDataVO> toPropertyList(String strLabel)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		ArrayList<ItemDataVO> lsProperty = new ArrayList<ItemDataVO>();

		lsProperty.add( new ItemDataVO(strLabel + " - X", nf3.format(this.getX())) );
		lsProperty.add( new ItemDataVO(strLabel + " - Y", nf3.format(this.getY())) );
		lsProperty.add( new ItemDataVO(strLabel + " - Z", nf3.format(this.getZ())) );
		
		return lsProperty;
	}
	
	/* DEBUG */
	
	public String toStr()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		String str = String.format(
			"[ %s, %s, %s ]", 
			nf3.format(this.x),
			nf3.format(this.y),
			nf3.format(this.z) );
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

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
}
