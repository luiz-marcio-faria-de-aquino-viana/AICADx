/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Shape.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom.shape;

import java.io.FileReader;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape3d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.utils.XmlUtil;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class Shape 
{
//Private
	private String name = null;					//   1 -  80
	private String fileName = null;				//  82 - 161
	private double defaultZ = 0.0;				// 163 - 172
    private ArrayList<CadParamEletricoOData> lsParamEletrico = null;

    private GeomShape2d planView2d = null;
    private GeomShape3d modelView3d = null;

//Public
    
    public Shape(String name, String fileName, double defaultZ)
    {
    	this.init(name, fileName, defaultZ);
    }
    
    public Shape(Shape shape)
    {
    	this.init(shape);
    }
    
    /* Methodes */
    
    public void init(String name, String fileName, double defaultZ)
    {
    	this.name = name;
    	this.fileName = fileName;
    	this.defaultZ = defaultZ;    	
    	this.lsParamEletrico = new ArrayList<CadParamEletricoOData>();
    	
    	this.loadFile(this.fileName);
    }
    
    public void init(Shape shape)
    {
    	this.name = shape.getName();
    	this.fileName = shape.getFileName();
    	this.defaultZ = shape.getDefaultZ();
    	this.lsParamEletrico = new ArrayList<CadParamEletricoOData>();
    	this.addAllParamEletrico(shape);

    	this.planView2d = new GeomShape2d(shape.getPlanView2d());
    	this.modelView3d = new GeomShape3d(shape.getModelView3d());
    }
    
	public void loadFile(String fileName)
	{
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			InputSource is = new InputSource(new FileReader(fileName));
		    Document doc = db.parse(is);
		    
		    //LOAD: Shape Data
		    
		    NodeList lsShape = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE);
		    int szLsShape = lsShape.getLength();
		    if(szLsShape > 0) {
		    	Node nShape = lsShape.item(0);  

	    		String name = XmlUtil.getAttrAsStringByName(nShape, AppDefs.SHPFILE_TAGPARM_SHAPE_NAME);
	    		if( (name != null) && (name.compareToIgnoreCase(this.name) == 0) ) {
	    			String warnmsg = String.format("Loading shape file: %s...", name);
	    			PromptUtil.prompt(warnmsg);
	    		}
	    		else {
	    			String warnmsg = String.format("Loading shape file: %s... (Wrong shape name)", name);
	    			PromptUtil.prompt(warnmsg);
	    		}
		    }		    
		    
		    //LOAD: ParamEletrico Data
		    
		    NodeList lsParamEletrico = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE_PARAMELETRICO);
		    int szLsParamEletrico = lsParamEletrico.getLength();
		    if(szLsParamEletrico > 0) {
		    	for(int i = 0; i < szLsParamEletrico; i++) {
			    	Node nParamEletrico = lsParamEletrico.item(i);  
			    	
			    	CadParamEletricoOData oParamEletrico = new CadParamEletricoOData();
			    	oParamEletrico.loadFrom(nParamEletrico);
			    	
			    	this.lsParamEletrico.add(oParamEletrico);
		    	}
		    }
		    
		    //LOAD: GeomShape2d Data
		    
		    NodeList lsGeomShape2d = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE_GEOMSHAPE2D);
		    int szLsGeomShape2d = lsGeomShape2d.getLength();
		    if(szLsGeomShape2d > 0) {
		    	Node nGeomShape2d = lsGeomShape2d.item(0);  
		    	
		    	this.planView2d = new GeomShape2d();
		    	this.planView2d.loadFrom(nGeomShape2d);
		    }
		    
		    //LOAD: GeomShape3d Data
		    
		    NodeList lsGeomShape3d = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE_GEOMSHAPE3D);
		    int szLsGeomShape3d = lsGeomShape3d.getLength();
		    if(szLsGeomShape3d > 0) {
		    	Node nGeomShape3d = lsGeomShape3d.item(0);  
		    	
		    	this.modelView3d = new GeomShape3d();
		    	this.modelView3d.loadFrom(nGeomShape3d);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
    
	/* CREATE_FROM STRING_DATA */
	
	//12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890
	//         1         2         3         4         5         6         7         8         9         0         1         2         3         4         5         6         7         8         9         0
	//                                                                                                   1         1         1         1         1         1         1         1         1         1         2
	//
	//1-80                                                                             82-161                                                                           163-172
	//SHAPE                                                                            SHAPE_FILE                                                                       Z          (DON'T ERASE THIS LINE!)
	//-------------------------------------------------------------------------------- -------------------------------------------------------------------------------- ---------- (DON'T ERASE THIS LINE!)
	//EL-Ponto_Luz_Teto                                                                EL/EL-Ponto_Luz_Teto.ais                                                         2.7
	//		  :
	//
	public static Shape createFrom(String str)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();
		
		if(str.length() < 164) 
			return null;
		
		String strName = StringUtil.trimAll(str.substring(0, 79));
		String strFileName = StringUtil.trimAll(str.substring(81, 160));
		String strDefaultZ = StringUtil.trimAll(str.substring(162));
		
		String strFullFileName = ctx.getShapesDir() + strFileName;
		double defaultZ = StringUtil.safeDbl(nf6, strDefaultZ);
		
		Shape oNewShape = new Shape(strName, strFullFileName, defaultZ);
		return oNewShape;
	}
	
	/* LIST */
	
    public synchronized int getSzLsParamEletrico() {
    	return this.lsParamEletrico.size();
    }
	
    public synchronized CadParamEletricoOData getParamEletricoAt(int pos) {
    	int sz = this.lsParamEletrico.size();
    	if(pos < sz) {
    		CadParamEletricoOData o = this.lsParamEletrico.get(pos);
    		return o;
    	}
    	return null;
    }
	
    public synchronized void addAllParamEletrico(Shape shape) {
    	int sz = shape.getSzLsParamEletrico();
    	for(int i = 0; i < sz; i++) {
    		CadParamEletricoOData oParam = shape.getParamEletricoAt(i);
    		this.addParamEletrico( new CadParamEletricoOData(oParam) );
    	}
    }
    
    public synchronized void addParamEletrico(CadParamEletricoOData o) {
    	this.lsParamEletrico.add(o);
    }
	
    /* Getters/Setters */

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GeomShape2d getPlanView2d() {
		return planView2d;
	}

	public void setPlanView2d(GeomShape2d planView2d) {
		this.planView2d = planView2d;
	}

	public GeomShape3d getModelView3d() {
		return modelView3d;
	}

	public void setModelView3d(GeomShape3d modelView3d) {
		this.modelView3d = modelView3d;
	}

	public ArrayList<CadParamEletricoOData> getLsParamEletrico() {
		return lsParamEletrico;
	}

	public void setLsParamEletrico(ArrayList<CadParamEletricoOData> lsParamEletrico) {
		this.lsParamEletrico = lsParamEletrico;
	}

	public double getDefaultZ() {
		return defaultZ;
	}

	public void setDefaultZ(double defaultZ) {
		this.defaultZ = defaultZ;
	}
    
}
