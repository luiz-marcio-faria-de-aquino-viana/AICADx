/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * Shape.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom.shape;

import java.io.FileReader;
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
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.utils.XmlUtil;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class Shape 
{
//Private
	private String fileName = null;
	private String name = null;
    private GeomShape2d planView2d = null;
    private GeomShape3d modelView3d = null;
    
    private ArrayList<CadParamEletricoOData> lsParamEletrico = null;

//Public
    
    public Shape(String fileName)
    {
    	this.init(fileName);
    }
    
    public Shape(Shape shape)
    {
    	this.init(shape);
    }
    
    /* Methodes */
    
    public void init(String fileName)
    {
    	this.fileName = fileName;
    	this.lsParamEletrico = new ArrayList<CadParamEletricoOData>();
    	
    	this.loadFile(this.fileName);
    }
    
    public void init(Shape shape)
    {
    	this.fileName = shape.getFileName();
    	this.lsParamEletrico = new ArrayList<CadParamEletricoOData>();

    	this.name = shape.getName();
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

				this.name = XmlUtil.getAttrAsStringByName(nShape, AppDefs.SHPFILE_TAGPARM_SHAPE_NAME);
		    }		    
		    
		    //LOAD: ParamEletrico Data
		    
		    NodeList lsParamEletrico = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE_PARAMELETRICO);
		    int szLsParamEletrico = lsParamEletrico.getLength();
		    if(szLsParamEletrico > 0) {
		    	Node nParamEletrico = lsParamEletrico.item(0);  
		    	
		    	CadParamEletricoOData oParamEletrico = new CadParamEletricoOData();
		    	oParamEletrico.loadFrom(nParamEletrico);
		    	
		    	this.lsParamEletrico.add(oParamEletrico);
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
	
	//012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
	//          1         2         3         4         5         6         7         8         9         0         
	//                                                                                                    1         
	//
	//0-35                                36-71                               72-87           88-103          104           118
	//SHAPE                               SHAPE_FILE                                                                          (DON'T ERASE THIS LINE!)
	//----------------------------------- ----------------------------------------------------------------------------------- (DON'T ERASE THIS LINE!)
	//EL-Caixa_Passagem_Piso              EL/EL-Caixa_Passagem_Piso.ais
	// :
	public static Shape createFrom(String str)
	{
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();
		
		if(str.length() < 36) 
			return null;
		
		//String strName = StringUtil.trimAll(str.substring(0, 35));
		String strFileName = StringUtil.trimAll(str.substring(36));
		String strFullFileName = ctx.getShapesDir() + strFileName;
		
		Shape oNewShape = new Shape(strFullFileName);
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
    
}
