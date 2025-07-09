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
	private double radius;
	private double startAngle;
	private double endAngle;
	private double textHeight;
	//Keyword
	private PromptOptionVO keyword;
	//Texts
	private String text;
	//Flags
	private boolean closed;
	
//Public
	
	//Points
	public InputParamVO()
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_NONE;
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
		this.radius = 0.0;
		this.startAngle = 0.0;
		this.endAngle = 0.0;
		this.textHeight = 0.0;
		//Keyword
		this.keyword = null;
		//Texts
		this.text = "";
		//Flags
		this.closed = false;		
	}
	
	/* Methodes */

	public void initPoint(GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINT;
		this.pt0 = pt0;
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
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY;
		this.ent1 = oEnt1;
	}
	
	public void initEntity(CadEntity oEnt1, CadEntity oEnt2, GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_1PTS;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
		this.pt0 = pt0;		
	}
	
	public void initEntity(CadEntity oEnt1, CadEntity oEnt2, GeomPoint3d pt0, GeomPoint3d pt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_2PTS;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
		this.pt0 = pt0;
		this.pt1 = pt1;
	}
	
	/* Getters/Setters */
			
	public GeomPoint3d getPt0() {
		return pt0;
	}
	public void setPt0(GeomPoint3d pt0) {
		this.pt0 = pt0;
	}
	public GeomPoint3d getPt1() {
		return pt1;
	}
	public void setPt1(GeomPoint3d pt1) {
		this.pt1 = pt1;
	}
	public GeomPoint3d getPt2() {
		return pt2;
	}
	public void setPt2(GeomPoint3d pt2) {
		this.pt2 = pt2;
	}
	public GeomPoint3d getPt3() {
		return pt3;
	}
	public void setPt3(GeomPoint3d pt3) {
		this.pt3 = pt3;
	}
	public GeomPoint3d getPtMin() {
		return ptMin;
	}
	public void setPtMin(GeomPoint3d ptMin) {
		this.ptMin = ptMin;
	}
	public GeomPoint3d getPtMax() {
		return ptMax;
	}
	public void setPtMax(GeomPoint3d ptMax) {
		this.ptMax = ptMax;
	}
	public GeomPoint3d getPtCenter() {
		return ptCenter;
	}
	public void setPtCenter(GeomPoint3d ptCenter) {
		this.ptCenter = ptCenter;
	}
	public ArrayList<GeomPoint3d> getLsPt() {
		return lsPt;
	}
	public void setLsPt(ArrayList<GeomPoint3d> lsPt) {
		this.lsPt = lsPt;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public double getStartAngle() {
		return startAngle;
	}
	public void setStartAngle(double startAngle) {
		this.startAngle = startAngle;
	}
	public double getEndAngle() {
		return endAngle;
	}
	public void setEndAngle(double endAngle) {
		this.endAngle = endAngle;
	}
	public double getTextHeight() {
		return textHeight;
	}
	public void setTextHeight(double textHeight) {
		this.textHeight = textHeight;
	}
	public boolean getClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public GeomVector3d getVDir() {
		return vDir;
	}
	public void setVDir(GeomVector3d vDir) {
		this.vDir = vDir;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public CadEntity getEnt1() {
		return ent1;
	}

	public void setEnt1(CadEntity ent1) {
		this.ent1 = ent1;
	}

	public CadEntity getEnt2() {
		return ent2;
	}

	public void setEnt2(CadEntity ent2) {
		this.ent2 = ent2;
	}

	public PromptOptionVO getKeyword() {
		return keyword;
	}

	public void setKeyword(PromptOptionVO keyword) {
		this.keyword = keyword;
	}
		
}
