/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * InputParamVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;

public class InputParamVO 
{
//Private	
	private int type;
	//Entity
	private CadEntity ent1;
	private CadEntity ent2;
	//Points
	private GeomPoint3d pt0;
	private GeomPoint3d pt1;
	private GeomPoint3d pt2;
	private GeomPoint3d pt3;
	//Points (Min/Max)
	private GeomPoint3d ptMin;
	private GeomPoint3d ptMax;
	//Center Point
	private GeomPoint3d ptCenter;
	//Point Array
	private ArrayList<GeomPoint3d> lsPt;
	//Direction Vector
	private GeomVector3d vDir;
	//Values
	private double height;
	private double dist;
	private double radius;
	private double topRadius;
	private double baseRadius;
	private double torusRadius;
	private double startAngle;
	private double endAngle;
	private double textHeight;
	private double scale;
	//Keyword
	private PromptOptionVO keyword;
	//Texts
	private String text;
	private String dirName;
	private String fileName;
	private String shapeName;
	//PromptXXX
	private PromptOptionVO discipline;
	//Flags
	private boolean closed;
	//OtherValues
	private String strVal;
	private int intVal;
	private double dblVal;
	
//Public
	
	//Points
	public InputParamVO()
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_STRVAL;
		//Entity
		this.ent1 = null;
		this.ent2 = null;
		//Points
		this.pt0 = new GeomPoint3d(0.0, 0.0, 0.0);
		this.pt1 = new GeomPoint3d(0.0, 0.0, 0.0);
		this.pt2 = new GeomPoint3d(0.0, 0.0, 0.0);
		this.pt3 = new GeomPoint3d(0.0, 0.0, 0.0);
		//Points (Min/Max)
		this.ptMin = new GeomPoint3d(0.0, 0.0, 0.0);
		this.ptMax = new GeomPoint3d(0.0, 0.0, 0.0);
		//Center Point
		this.ptCenter = new GeomPoint3d(0.0, 0.0, 0.0);
		//Point Array
		this.lsPt = new ArrayList<GeomPoint3d>();
		//Direction Vector
		this.vDir = new GeomVector3d(0.0, 0.0, 0.0); 
		//Values
		this.height = 0.0;
		this.dist = 0.0;
		this.radius = 0.0;
		this.topRadius = 0.0;
		this.baseRadius = 0.0;
		this.torusRadius = 0.0;
		this.startAngle = 0.0;
		this.endAngle = 0.0;
		this.textHeight = 0.0;
		//Keyword
		this.keyword = null;
		//Texts
		this.text = "";
		this.dirName = "";
		this.fileName = "";
		this.shapeName = "";
		//PromptXXX */
		this.discipline = null;
		//Flags
		this.closed = false;
		//OtherValues
		this.strVal = "";
		this.intVal = -1;
		this.dblVal = 0.0;
	}
	
	/* Methodes */

	public void initPoint(GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINT;
		this.pt0 = pt0;
	}
	
	public void initPointAndRotation(GeomPoint3d ptBase, GeomPoint3d ptDir)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTANDROTATION;
		this.pt0 = ptBase;
		this.pt1 = ptDir;
	}

	public void initPoint(GeomPoint3d pt0, String text)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTANDTEXT;
		this.pt0 = pt0;
		this.text = text;
	}

	public void initPoint(GeomPoint3d pt0, PromptOptionVO oDiscipline, String shapeName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTDISCIPLINEANDSHAPENAME;
		this.pt0 = pt0;
		this.discipline = oDiscipline;
		this.shapeName = shapeName;
	}

	public void initPointAndRotation(GeomPoint3d ptBase, GeomPoint3d ptDir, String text)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTTEXTANDROTATION;
		this.pt0 = ptBase;
		this.pt1 = ptDir;
		this.text = text;
	}
	
	public void initPointRotationAndScale(GeomPoint3d ptBase, GeomPoint3d ptDir, double scale)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTROTATIONANDSCALE;
		this.pt0 = ptBase;
		this.pt1 = ptDir;
		this.scale = scale;
	}
	
	public void initDirName(String dirName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_FILENAME;
		this.dirName = dirName;		
	}
	
	public void initFileName(String fileName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_FILENAME;
		this.fileName = fileName;		
	}

	public void initPointAndFileName(GeomPoint3d ptBase, String fileName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTANDFILENAME;
		this.pt0 = ptBase;
		this.fileName = fileName;
	}
	
	public void initPointRotationAndFileName(GeomPoint3d ptBase, GeomPoint3d ptDir, String fileName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTROTATIONANDFILENAME;
		this.pt0 = ptBase;
		this.pt1 = ptDir;
		this.fileName = fileName;
	}
	
	public void initPointRotationScaleAndFileName(GeomPoint3d ptBase, GeomPoint3d ptDir, double scale, String fileName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTROTATIONSCALEANDFILENAME;
		this.pt0 = ptBase;
		this.pt1 = ptDir;
		this.scale = scale;
		this.fileName = fileName;
	}

	public void initLine(GeomPoint3d pt0, GeomPoint3d pt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_LINE;
		this.pt0 = pt0;
		this.pt1 = pt1;
	}

	public void initRectangle(GeomPoint3d ptMin, GeomPoint3d ptMax)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_RECTANGLE;
		this.ptMin = ptMin;
		this.ptMax = ptMax;
	}

	public void initRectangle(GeomPoint3d ptMin, GeomPoint3d ptMax, double height)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_RECTANGLEANDHEIGHT;
		this.ptMin = ptMin;
		this.ptMax = ptMax;
		this.height = height;
	}

	public void initPolyline(ArrayList<GeomPoint3d> lsPt, boolean closed)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POLYLINE;
		this.lsPt = lsPt;
		this.closed = closed;
	}

	public void initPolygon(ArrayList<GeomPoint3d> lsPt)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POLYGON;
		this.lsPt = lsPt;
		this.closed = true;
	}

	public void initKeyArea(PromptOptionVO keyword, String text, ArrayList<GeomPoint3d> lsPt)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYAREA;
		this.keyword = keyword;
		this.text = text;
		this.lsPt = lsPt;
		this.closed = true;
	}

	public void initEntityKeyArea(PromptOptionVO keyword, String text, ArrayList<GeomPoint3d> lsPt, CadEntity oEnt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_1PTS_KEYAREA;
		this.keyword = keyword;
		this.text = text;
		this.lsPt = lsPt;
		this.ent1 = oEnt1;
		this.closed = true;
	}

	public void initKeyAreaTable(PromptOptionVO keyword, GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYAREATABLE;
		this.keyword = keyword;
		this.pt0 = pt0;
		this.closed = true;
	}

	public void initArea(String text, ArrayList<GeomPoint3d> lsPt)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_AREA;
		this.text = text;
		this.lsPt = lsPt;
		this.closed = true;
	}

	public void initCircle(GeomPoint3d ptCenter, double radius)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_CIRCLE;
		this.ptCenter = ptCenter;
		this.radius = radius;
	}

	public void initCircle(GeomPoint3d ptCenter, double radius, double height)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_CIRCLEANDHEIGHT;
		this.ptCenter = ptCenter;
		this.radius = radius;
		this.height = height;
	}

	public void initCircle(GeomPoint3d ptCenter, double baseRadius, double topRadius, double height)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_CIRCLEANDHEIGHT;
		this.ptCenter = ptCenter;
		this.baseRadius = baseRadius;
		this.topRadius = topRadius;
		this.height = height;
	}

	public void initTorus(GeomPoint3d ptCenter, double radius, double torusRadius)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_TORUS;
		this.ptCenter = ptCenter;
		this.radius = radius;
		this.torusRadius = torusRadius;
	}

	public void initArc(GeomPoint3d ptCenter, double radius, double startAngle, double endAngle)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ARC;
		this.ptCenter = ptCenter;
		this.radius = radius;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
	}

	public void initText(GeomPoint3d pt0, double textHeight, String text)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_TEXT;
		this.pt0 = pt0;
		this.textHeight = textHeight;
		this.text = text;
	}

	public void initVDir(GeomPoint3d ptI, GeomPoint3d ptF)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_VDIR;
		this.vDir = new GeomVector3d(ptI, ptF);
	}

	public void initEntity(CadEntity oEnt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY;
		this.ent1 = oEnt1;
	}
	
	public void initEntity(CadEntity oEnt1, GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_1PTS;
		this.ent1 = oEnt1;
		this.pt0 = pt0;		
	}
	
	public void initEntity(CadEntity oEnt1, GeomPoint3d pt0, GeomPoint3d pt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_2PTS;
		this.ent1 = oEnt1;
		this.pt0 = pt0;
		this.pt1 = pt1;
	}

	public void initEntity(CadEntity oEnt1, CadEntity oEnt2)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_2ENTITY;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
	}
	
	public void initEntity(CadEntity oEnt1, CadEntity oEnt2, GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_2ENTITY_1PTS;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
		this.pt0 = pt0;		
	}
	
	public void initEntity(CadEntity oEnt1, CadEntity oEnt2, GeomPoint3d pt0, GeomPoint3d pt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_2ENTITY_2PTS;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
		this.pt0 = pt0;
		this.pt1 = pt1;
	}
	
	public void initEntity(double dist, CadEntity oEnt1, GeomPoint3d pt0, GeomPoint3d pt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_2ENTITY_2PTS_DIST;
		this.dist = dist;
		this.ent1 = oEnt1;
		this.pt0 = pt0;
		this.pt1 = pt1;
	}
	
	public void initEntity(CadEntity oEnt1, String strVal)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_STRVAL;
		this.ent1 = oEnt1;
		this.strVal = strVal;
	}
	
	public void initEntity(CadEntity oEnt1, int intVal)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_INTVAL;
		this.ent1 = oEnt1;
		this.intVal = intVal;
	}
	
	public void initEntity(CadEntity oEnt1, double dblVal)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_DBLVAL;
		this.ent1 = oEnt1;
		this.dblVal = dblVal;
	}
	
	/* Getters/Setters */
			
	public GeomPoint3d getPt0() {
		return pt0;
	}

	public GeomPoint3d getPt1() {
		return pt1;
	}

	public GeomPoint3d getPt2() {
		return pt2;
	}

	public GeomPoint3d getPt3() {
		return pt3;
	}

	public GeomPoint3d getPtMin() {
		return ptMin;
	}

	public GeomPoint3d getPtMax() {
		return ptMax;
	}

	public GeomPoint3d getPtCenter() {
		return ptCenter;
	}

	public ArrayList<GeomPoint3d> getLsPt() {
		return lsPt;
	}

	public double getRadius() {
		return radius;
	}

	public double getStartAngle() {
		return startAngle;
	}

	public double getEndAngle() {
		return endAngle;
	}

	public double getTextHeight() {
		return textHeight;
	}

	public boolean getClosed() {
		return closed;
	}

	public GeomVector3d getVDir() {
		return vDir;
	}

	public String getText() {
		return text;
	}

	public CadEntity getEnt1() {
		return ent1;
	}

	public CadEntity getEnt2() {
		return ent2;
	}

	public PromptOptionVO getKeyword() {
		return keyword;
	}

	public double getHeight() {
		return height;
	}

	public double getDist() {
		return dist;
	}

	public int getType() {
		return type;
	}

	public double getScale() {
		return scale;
	}

	public String getFileName() {
		return fileName;
	}

	public String getShapeName() {
		return shapeName;
	}

	public PromptOptionVO getDiscipline() {
		return discipline;
	}

	public String getStrVal() {
		return strVal;
	}

	public int getIntVal() {
		return intVal;
	}

	public double getDblVal() {
		return dblVal;
	}

	public double getTopRadius() {
		return topRadius;
	}

	public double getBaseRadius() {
		return baseRadius;
	}

	public double getTorusRadius() {
		return torusRadius;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

}
