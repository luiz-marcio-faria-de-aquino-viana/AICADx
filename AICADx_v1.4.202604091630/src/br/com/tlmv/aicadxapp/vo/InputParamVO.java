/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * InputParamVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/02/2025
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
	//Entities
	private ArrayList<CadEntity> lsEntities;	
	//Points
	private GeomPoint3d pt0;
	private GeomPoint3d pt1;
	private GeomPoint3d pt2;
	private GeomPoint3d pt3;
	private GeomPoint3d pt4;
	//Points (Min/Max)
	private GeomPoint3d ptMin;
	private GeomPoint3d ptMax;
	//Center Point
	private GeomPoint3d ptCenter;
	//Direction Point
	private GeomPoint3d ptDir;
	//Point Array
	private ArrayList<GeomPoint3d> lsPts3d;
	//Direction Vector
	private GeomVector3d vDir;
	//IntegerValues
	private int numElem;
	private int numRows;
	private int numCols;
	//DoubleValues
	private double rowDist;
	private double colDist;
	private double length;
	private double width;
	private double height;
	private double dist;
	private double radius;
	private double axisA;
	private double axisB;
	private double topRadius;
	private double baseRadius;
	private double torusRadius;
	private double startAngle;
	private double endAngle;
	private double textHeight;
	private double scale;
	private double alturaBase;
	private double altura;
	private double comprimento;
	private double diametro;
	private double caimento;
	//Keyword
	private PromptOptionVO keyword;
	//Texts
	private String reference;
	private String text;
	private String dirName;
	private String fileName;
	private String shapeName;
	//PromptXXX
	private PromptOptionVO discipline;
	//ProjectRepo
	private ProjectRepoVO projectRepo;	
	//Flags
	private boolean closed;
	//OtherValues
	private String strVal;
	private int intVal;
	private double dblVal;
	//QuadroCargas
	private String nomeQuadro;
	private String descricaoQuadro;
	private double tensaoQuadro;
	private String sistemaFase;
	//Margem
	private String disciplinaDesenho;
	private String numeroDesenho;
	private String descricaoDesenho;
	
//Public
	
	//Points
	public InputParamVO()
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_STRVAL;
		//Entity
		this.ent1 = null;
		this.ent2 = null;
		//ListOfEntities
		this.lsEntities = null;
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
		//Direction Point
		this.ptDir = new GeomPoint3d(0.0, 0.0, 0.0);
		//Point Array
		this.lsPts3d = new ArrayList<GeomPoint3d>();
		//Direction Vector
		this.vDir = new GeomVector3d(0.0, 0.0, 0.0); 
		//Values
		this.length = 0.0;
		this.width = 0.0;
		this.height = 0.0;
		this.dist = 0.0;
		this.radius = 0.0;
		this.axisA = 0.0;
		this.axisB = 0.0;
		this.topRadius = 0.0;
		this.baseRadius = 0.0;
		this.torusRadius = 0.0;
		this.startAngle = 0.0;
		this.endAngle = 0.0;
		this.textHeight = 0.0;
		this.scale = 0.0;
		this.alturaBase = 0.0;
		this.altura = 0.0;
		this.comprimento = 0.0;
		//Keyword
		this.keyword = null;
		//Texts
		this.text = "";
		this.dirName = "";
		this.fileName = "";
		this.shapeName = "";
		//PromptXXX
		this.discipline = null;
		//ProjectRepo
		this.projectRepo = null;
		//Flags
		this.closed = false;
		//OtherValues
		this.strVal = "";
		this.intVal = -1;
		this.dblVal = 0.0;
	}
	
	/* Methodes */

	// POINT
	//
	public void initPoint(GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINT;
		this.pt0 = pt0;
	}

	public void initPoint(GeomPoint3d pt0, double height)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINT;
		this.pt0 = pt0;
		this.height = height;
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

	public void initPointLayerRotationScaleAndFileName(String strReference, GeomPoint3d ptBase, GeomPoint3d ptDir, double scale, String fileName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTROTATIONSCALEANDFILENAME;
		this.reference = strReference;
		this.pt0 = ptBase;
		this.pt1 = ptDir;
		this.scale = scale;
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

	// MULT_POINT
	//
	public void initMultPoint(ArrayList<GeomPoint3d> lsPts3d)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_MULTPOINT;
		this.lsPts3d = lsPts3d;
		this.closed = false;
	}
	
	// LINE
	//
	public void initLine(GeomPoint3d pt0, GeomPoint3d pt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_LINE;
		this.pt0 = pt0;
		this.pt1 = pt1;
	}
	
	// PIPE
	//
	public void initPipe(GeomPoint3d pt0, GeomPoint3d pt1, double diametro)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_PIPE_2PTDIAMETER;
		this.pt0 = pt0;
		this.pt1 = pt1;
		this.diametro = diametro;
	}

	public void initPipe(GeomPoint3d pt0, GeomPoint3d pt1, double diametro, double caimento)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_PIPE_2PTDIAMETERSLOPE;
		this.pt0 = pt0;
		this.pt1 = pt1;
		this.diametro = diametro;
		this.caimento = caimento;
	}

	public void initPipe(GeomPoint3d pt0, GeomPoint3d pt1, double diametro, double caimento, double altura)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_PIPE_2PTDIAMETERSLOPEALTURA;
		this.pt0 = pt0;
		this.pt1 = pt1;
		this.diametro = diametro;
		this.caimento = caimento;
		this.altura = altura;
	}

	public void initPipe(GeomPoint3d ptI, GeomPoint3d pt1, GeomPoint3d ptF, double diametro, double caimento, double altura)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_PIPE_3PTDIAMETERSLOPEALTURA;
		this.pt0 = ptI;
		this.pt1 = pt1;
		this.pt2 = ptF;
		this.diametro = diametro;
		this.caimento = caimento;
		this.altura = altura;
	}

	public void initPipe(GeomPoint3d ptI, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d ptF, double diametro, double caimento, double altura)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_PIPE_4PTDIAMETERSLOPEALTURA;
		this.pt0 = ptI;
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.pt3 = ptF;
		this.diametro = diametro;
		this.caimento = caimento;
		this.altura = altura;
	}

	public void initPipe(GeomPoint3d ptI, GeomPoint3d pt1, GeomPoint3d pt2, GeomPoint3d pt3, GeomPoint3d ptF, double diametro, double caimento, double altura)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_PIPE_5PTDIAMETERSLOPEALTURA;
		this.pt0 = ptI;
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.pt3 = pt3;
		this.pt4 = ptF;
		this.diametro = diametro;
		this.caimento = caimento;
		this.altura = altura;
	}
	
	// PIPELINE
	//
	public void initPipeLine(ArrayList<GeomPoint3d> lsPts3d, double diametro, double caimento, double altura)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_PIPELINE_LSPTSDIAMETERSLOPEALTURA;
		this.lsPts3d = lsPts3d;
		this.diametro = diametro;
		this.caimento = caimento;
		this.altura = altura;
	}
	
	// RECTANGLE
	//
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

	public void initRectangle(GeomPoint3d ptIns, GeomPoint3d ptDir, double length, double width, double height)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_RECTANGLEANDLENGTHWIDTHHEIGHT;	
		this.pt0 = ptIns;
		this.pt1 = ptDir;
		this.length = length;
		this.width = width;
		this.height = height;		
	}
	
	// POLYLINE
	//
	public void initPolyline(ArrayList<GeomPoint3d> lsPts3d, boolean closed)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POLYLINE;
		this.lsPts3d = lsPts3d;
		this.closed = closed;
	}

	// POLYGON
	//
	public void initPolygon(ArrayList<GeomPoint3d> lsPts3d)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POLYGON;
		this.lsPts3d = lsPts3d;
		this.closed = true;
	}

	public void initPolygon(PromptOptionVO keyword, GeomPoint3d ptCenter3d, GeomPoint3d ptDir3d, double radius, int numVertices)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POLYGON_KEYWORD_2PTS_RADIUS_NUMVERT;
		this.keyword = keyword;
		this.pt0 = ptCenter3d;		
		this.pt1 = ptDir3d;
		this.radius = radius;		
		this.intVal = numVertices;		
	}
	
	// ARC
	//
	public void initArc(GeomPoint3d ptCenter, double radius, double startAngle, double endAngle)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ARC;
		this.ptCenter = ptCenter;
		this.radius = radius;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
	}
	
	// CIRCLE
	//
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
		this.type = AppDefs.DEF_INPUTPARAMTYPE_CIRCLEBASERADIUSTOPRADIUSANDHEIGHT;
		this.ptCenter = ptCenter;
		this.baseRadius = baseRadius;
		this.topRadius = topRadius;
		this.height = height;
	}

	// ELLIPSE
	//
	public void initEllipse(GeomPoint3d ptCenter, double axisA, double axisB)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ELLIPSEAXISAAXISB;
		this.ptCenter = ptCenter;
		this.axisA = axisA;
		this.axisB = axisB;
	}

	public void initEllipse(GeomPoint3d ptCenter, double axisA, double axisB, GeomPoint3d ptDir)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ELLIPSEAXISAAXISBDIR;
		this.ptCenter = ptCenter;
		this.axisA = axisA;
		this.axisB = axisB;
		this.ptDir = ptDir;
	}
	
	public void initQuadroCargas(GeomPoint3d pt0, String nomeQuadro, String descricaoQuadro, double tensaoQuadro, String sistemaFase)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_INSPOINT_NOMEQUADRO_DESCRICAO_TENSAO_SISTEMAFASE;
		this.pt0 = pt0;
		this.nomeQuadro = nomeQuadro;
		this.descricaoQuadro = descricaoQuadro;
		this.tensaoQuadro = tensaoQuadro;
		this.sistemaFase = sistemaFase;
	}
	
	// AREA
	//	
	public void initArea(String text, ArrayList<GeomPoint3d> lsPts3d)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_AREA;
		this.text = text;
		this.lsPts3d = lsPts3d;
		this.closed = true;
	}
	
	// TORUS
	//
	public void initTorus(GeomPoint3d ptCenter, double radius, double torusRadius)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_TORUS;
		this.ptCenter = ptCenter;
		this.radius = radius;
		this.torusRadius = torusRadius;
	}

	// TEXT
	//
	public void initText(GeomPoint3d pt0, double textHeight, String text)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_TEXT;
		this.pt0 = pt0;
		this.textHeight = textHeight;
		this.text = text;
	}
	
	// VDIR
	//
	public void initVDir(GeomPoint3d ptI, GeomPoint3d ptF)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_VDIR;
		this.vDir = new GeomVector3d(ptI, ptF);
	}

	// DIR_NAME
	//
	public void initDirName(String dirName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_DIRNAME;
		this.dirName = dirName;		
	}
	
	// FILE_NAME
	//
	public void initFileName(String fileName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_FILENAME;
		this.fileName = fileName;		
	}
	
	public void initFileNameAndLayer(String strReference, String fileName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_POINTROTATIONSCALEANDFILENAME;
		this.reference = strReference;
		this.fileName = fileName;
	}

	// COLUNA
	//
	public void initColuna(GeomPoint3d ptCenter, double radius, double alturaBase, double comprimento)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_COLUNARADIUSALTURABASECOMPRIMENTO;
		this.ptCenter = ptCenter;
		this.radius = radius;
		this.alturaBase = alturaBase;
		this.comprimento = comprimento;
	}

	public void initColuna(GeomPoint3d ptCenter, int identificadorColuna, double radius, double alturaBase, double comprimento)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_COLUNARADIUSALTURABASECOMPRIMENTO;
		this.ptCenter = ptCenter;
		this.intVal = identificadorColuna;
		this.radius = radius;
		this.alturaBase = alturaBase;
		this.comprimento = comprimento;
	}
	
	// PROJECT_REPO
	//
	public void initProjectRepo(ProjectRepoVO projectRepo)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_PROJECTREPO;
		this.projectRepo = projectRepo;
	}
	
	// KEYWORD
	//
	public void initKey(PromptOptionVO keyword)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEY;
		this.keyword = keyword;
	}
	
	public void initKeyPointAndRotation(PromptOptionVO keyword, GeomPoint3d ptBase, GeomPoint3d ptDir)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYPOINTANDROTATION;
		this.keyword = keyword;
		this.pt0 = ptBase;
		this.pt1 = ptDir;
	}
	
	public void initKeyPointRotationAndParamMargem(PromptOptionVO keyword, GeomPoint3d ptBase, GeomPoint3d ptDir, String disciplinaDesenho, String numeroDesenho, String descricaoDesenho)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYPOINTANDROTATION;
		this.keyword = keyword;
		this.pt0 = ptBase;
		this.pt1 = ptDir;
		this.disciplinaDesenho = disciplinaDesenho;
		this.numeroDesenho = numeroDesenho;
		this.descricaoDesenho = descricaoDesenho;
	}

	public void initKeyArea(PromptOptionVO keyword, String text, ArrayList<GeomPoint3d> lsPts3d)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYAREA;
		this.keyword = keyword;
		this.text = text;
		this.lsPts3d = lsPts3d;
		this.closed = true;
	}
	
	public void initKeyPointLayerRotationScaleAndFileName(PromptOptionVO keyword, String strReference, GeomPoint3d ptBase, GeomPoint3d ptDir, double scale, String fileName)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYPOINTROTATIONSCALEANDFILENAME;
		this.keyword = keyword;
		this.reference = strReference;
		this.pt0 = ptBase;
		this.pt1 = ptDir;
		this.scale = scale;
		this.fileName = fileName;
	}

	public void initKeyLsPts(PromptOptionVO keyword, ArrayList<GeomPoint3d> lsPts3d)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYLSPTS;
		this.keyword = keyword;
		this.lsPts3d = lsPts3d;
		this.closed = true;
	}
	
	public void initKeyAreaTable(PromptOptionVO keyword, GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYAREATABLE;
		this.keyword = keyword;
		this.pt0 = pt0;
		this.closed = true;
	}

	public void initKeyRectangle(PromptOptionVO keyword, GeomPoint3d ptMin, GeomPoint3d ptMax)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYRECTANGLE;
		this.keyword = keyword;
		this.ptMin = ptMin;
		this.ptMax = ptMax;
	}

	public void initKeyAll(PromptOptionVO keyword)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYALL;
		this.keyword = keyword;
	}
	
	// ENTITY_AND_KEYWORD
	//
	public void initEntityKeyAreaWithEntity(PromptOptionVO keyword, String text, ArrayList<GeomPoint3d> lsPts3d, CadEntity ent1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYAREA;
		this.keyword = keyword;
		this.text = text;
		this.lsPts3d = lsPts3d;
		this.ent1 = ent1;
		this.closed = true;
	}
	
	public void initEntityKeyEntity(PromptOptionVO keyword, String text, CadEntity oEnt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_KEYAREA_BLKREF;
		this.keyword = keyword;
		this.text = text;
		this.ent1 = oEnt1;
	}

	public void initEntityKeyEntityAndBlkRef(PromptOptionVO keyword, String text, CadEntity oEnt1, CadEntity oEnt2)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_KEYAREA_ENTITY_BLKREF;
		this.keyword = keyword;
		this.text = text;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
	}
	
	public void initEntityKeyEntityWithPoint(PromptOptionVO keyword, CadEntity oEnt1, GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_KEYENTITYWITHPOINT;
		this.keyword = keyword;
		this.ent1 = oEnt1;
		this.pt0 = pt0;
	}
			
	public void initEntityKeyEntityWithPoint(PromptOptionVO keyword, String text, CadEntity oEnt1, GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_KEYAREA_BLKREF_1PTS;
		this.keyword = keyword;
		this.text = text;
		this.ent1 = oEnt1;
		this.pt0 = pt0;
	}
	
	public void initEntityKeyEntityAndArea(PromptOptionVO keyword, String text, CadEntity oEnt1, ArrayList<CadEntity> lsEntities)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_KEYAREA_CONTOUR;
		this.keyword = keyword;
		this.text = text;
		this.ent1 = oEnt1;
		this.lsEntities = new ArrayList<CadEntity>(lsEntities);
		this.closed = true;
	}

	public void initEntityKeyArea(PromptOptionVO keyword, String text, ArrayList<GeomPoint3d> lsPts3d, CadEntity oEnt1, GeomPoint3d pt0)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_KEYAREA_CONTOUR_1PTS;
		this.keyword = keyword;
		this.text = text;
		this.lsPts3d = lsPts3d;
		this.ent1 = oEnt1;
		this.pt0 = pt0;
		this.closed = true;
	}
	
	public void initEntityKeyEntityAndArea(PromptOptionVO keyword, String text, CadEntity oEnt1, CadEntity oEnt2, ArrayList<CadEntity> lsEntities)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_KEYAREA_CONTOUR_BLKREF_1PTS;
		this.keyword = keyword;
		this.text = text;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
		this.lsEntities = new ArrayList<CadEntity>(lsEntities);
		this.closed = true;
	}
		
	// ENTITY
	//
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
	
	public void initEntity(CadEntity oEnt1, GeomPoint3d pt0, Integer intVal)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_2PTS;
		this.ent1 = oEnt1;
		this.pt0 = pt0;
		this.intVal = intVal;
	}
	
	public void initEntity(CadEntity oEnt1, GeomPoint3d pt0, GeomPoint3d pt1, Double dblVal)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_2PTS_DIST;
		this.ent1 = oEnt1;
		this.pt0 = pt0;
		this.pt1 = pt1;
		this.dblVal = dblVal;
	}

	public void initEntity(CadEntity oEnt1, CadEntity oEnt2)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_2ENTITY;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
	}

	public void initEntity(CadEntity oEnt1, CadEntity oEnt2, ArrayList<GeomPoint3d> lsPts3d)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_2ENTITY_LSPTS;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
		//
		this.lsPts3d = lsPts3d;
	}

	public void initEntity(CadEntity oEnt1, CadEntity oEnt2, Integer intVal)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_2ENTITY_INTVAL;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
		this.intVal = intVal;
	}

	public void initEntity(CadEntity oEnt1, CadEntity oEnt2, Double dblVal)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_2ENTITY_DBLVAL;
		this.ent1 = oEnt1;
		this.ent2 = oEnt2;
		this.dblVal = dblVal;
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
	
	public void initEntity(CadEntity oEnt1, GeomPoint3d pt0, int intVal)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_1PTS_INTVAL;
		this.ent1 = oEnt1;
		this.pt0 = pt0;
		this.intVal = intVal;
	}
	
	public void initEntity(ArrayList<CadEntity> lsEntities, GeomPoint3d pt0, GeomPoint3d pt1, int numRows, int numCols, double rowDist, double colDist)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_2PTS_NROWS_NCOLS_ROWDIST_COLDIST;
		this.lsEntities = lsEntities;
		this.pt0 = pt0;
		this.pt1 = pt1;
		this.numRows = numRows;
		this.numCols = numCols;
		this.rowDist = rowDist;
		this.colDist = colDist;
	}
	
	public void initEntity(ArrayList<CadEntity> lsEntities, GeomPoint3d pt0, GeomPoint3d pt1, GeomPoint3d pt2, double startAngle, double endAngle, int numElem)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_3PTS_STARTANGLE_ENDANGLE_NELEM;
		this.lsEntities = lsEntities;
		this.pt0 = pt0;
		this.pt1 = pt1;
		this.pt2 = pt2;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
		this.numElem = numElem;
	}
	
	public void initEntity(CadEntity oEnt1, double dblVal)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_DBLVAL;
		this.ent1 = oEnt1;
		this.dblVal = dblVal;
	}
	
	public void initEntity(CadEntity oEnt1, ArrayList<CadEntity> lsEntities)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_LISTOFENTITIES_ENTREF;
		this.ent1 = oEnt1;
		this.lsEntities = new ArrayList<CadEntity>(lsEntities);
	}
	
	public void initEntity(ArrayList<CadEntity> lsEntities)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_LISTOFENTITIES;
		this.lsEntities = new ArrayList<CadEntity>(lsEntities);
	}
	
	public void initEntity(ArrayList<CadEntity> lsEntities, GeomPoint3d pt0, GeomPoint3d pt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_LISTOFENTITIES_2PTS;
		this.lsEntities = new ArrayList<CadEntity>(lsEntities);
		this.pt0 = pt0;
		this.pt1 = pt1;
	}
	
	public void initEntity(double dist, ArrayList<CadEntity> lsEntities, GeomPoint3d pt0, GeomPoint3d pt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_LISTOFENTITIES_2PTS_DIST;
		this.dist = dist;
		this.lsEntities = new ArrayList<CadEntity>(lsEntities);
		this.pt0 = pt0;
		this.pt1 = pt1;
	}
	
	public void initEntity(PromptOptionVO keyword, ArrayList<CadEntity> lsEntities, GeomPoint3d pt0, GeomPoint3d pt1)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_LISTOFENTITIES_2PTS_AND_KEY;
		this.keyword = keyword;
		this.lsEntities = new ArrayList<CadEntity>(lsEntities);
		this.pt0 = pt0;
		this.pt1 = pt1;
	}
	
	public void initEntity(GeomPoint3d pt0, GeomPoint3d pt1, int numRows, int numCols)
	{
		this.type = AppDefs.DEF_INPUTPARAMTYPE_ENTITY_2PTS_NROWS_NCOLS;
		this.pt0 = pt0;
		this.pt1 = pt1;
		this.numRows = numRows;
		this.numCols = numCols;
	}
	
	/* Getters/Setters */

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
			
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

	public GeomPoint3d getPtDir() {
		return ptDir;
	}

	public void setPtDir(GeomPoint3d ptDir) {
		this.ptDir = ptDir;
	}

	public ArrayList<GeomPoint3d> getLsPts3d() {
		return lsPts3d;
	}

	public double getRadius() {
		return radius;
	}
	
	public double getAxisA() {
		return axisA;
	}

	public void setAxisA(double axisA) {
		this.axisA = axisA;
	}

	public double getAxisB() {
		return axisB;
	}

	public void setAxisB(double axisB) {
		this.axisB = axisB;
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

	public double getLength() {
		return length;
	}

	public double getWidth() {
		return width;
	}

	public ArrayList<CadEntity> getLsEntities() {
		return lsEntities;
	}

	public void setLsEntities(ArrayList<CadEntity> lsEntities) {
		this.lsEntities = lsEntities;
	}

	public ProjectRepoVO getProjectRepo() {
		return projectRepo;
	}

	public void setProjectRepo(ProjectRepoVO projectRepo) {
		this.projectRepo = projectRepo;
	}

	public GeomVector3d getvDir() {
		return vDir;
	}

	public double getAltura() {
		return altura;
	}

	public double getAlturaBase() {
		return alturaBase;
	}

	public void setAlturaBase(double alturaBase) {
		this.alturaBase = alturaBase;
	}

	public double getComprimento() {
		return comprimento;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getDiametro() {
		return diametro;
	}

	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}

	public double getCaimento() {
		return caimento;
	}

	public void setCaimento(double caimento) {
		this.caimento = caimento;
	}

	public String getNomeQuadro() {
		return nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public String getDescricaoQuadro() {
		return descricaoQuadro;
	}

	public void setDescricaoQuadro(String descricaoQuadro) {
		this.descricaoQuadro = descricaoQuadro;
	}

	public double getTensaoQuadro() {
		return tensaoQuadro;
	}

	public void setTensaoQuadro(double tensaoQuadro) {
		this.tensaoQuadro = tensaoQuadro;
	}

	public String getSistemaFase() {
		return sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

	public String getDisciplinaDesenho() {
		return disciplinaDesenho;
	}

	public void setDisciplinaDesenho(String disciplinaDesenho) {
		this.disciplinaDesenho = disciplinaDesenho;
	}

	public String getNumeroDesenho() {
		return numeroDesenho;
	}

	public void setNumeroDesenho(String numeroDesenho) {
		this.numeroDesenho = numeroDesenho;
	}

	public String getDescricaoDesenho() {
		return descricaoDesenho;
	}

	public void setDescricaoDesenho(String descricaoDesenho) {
		this.descricaoDesenho = descricaoDesenho;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public double getRowDist() {
		return rowDist;
	}

	public void setRowDist(double rowDist) {
		this.rowDist = rowDist;
	}

	public double getColDist() {
		return colDist;
	}

	public void setColDist(double colDist) {
		this.colDist = colDist;
	}

	public int getNumElem() {
		return numElem;
	}

	public void setNumElem(int numElem) {
		this.numElem = numElem;
	}

	public GeomPoint3d getPt4() {
		return pt4;
	}

	public void setPt4(GeomPoint3d pt4) {
		this.pt4 = pt4;
	}

}
