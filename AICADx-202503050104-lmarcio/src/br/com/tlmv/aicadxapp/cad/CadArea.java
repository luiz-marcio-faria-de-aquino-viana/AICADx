/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadArea.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData3dVO;

public class CadArea extends CadEntity 
{
//Private
    private ArrayList<GeomPoint3d> lsPts;
    private GeomPoint3d ptCentroid;
    private int areaType;
    private String name;
    private double area;
    
//Public

    public CadArea(CadLayerDef oLayer) {
    	super(AppDefs.OBJTYPE_BIMAREA, oLayer);
    }
	
	/* Methodes */
	
	private void init(int tipoArea, String name, double altura, ArrayList<GeomPoint2d> lsPts2d) {
		ArrayList<GeomPoint3d> lsPts3d = GeomUtil.from2dTo3d(lsPts2d, altura);
		this.init(tipoArea, name, lsPts3d);
	}

	public void init(int areaType, String name, ArrayList<GeomPoint3d> lsPts3d) {
		this.areaType = areaType;
		this.name = name;
		this.lsPts = new ArrayList<GeomPoint3d>(lsPts3d);
		this.ptCentroid = GeomUtil.centroidOf3d(lsPts3d);
		//
		this.area = GeomUtil.calculateArea(this.ptCentroid, this.lsPts);
    }
	
	private void init(CadArea other) {
	    this.init(other.areaType, other.name, other.lsPts);
	}
	
	/* CREATE */
	
	public static CadArea create(CadLayerDef oLayer, int tipoArea, String name, double altura, ArrayList<GeomPoint2d> lsPts2d) {
    	CadArea o = new CadArea(oLayer);
    	o.init(tipoArea, name, altura, lsPts2d);
    	return o;
    }
	
	public static CadArea create(CadLayerDef oLayer, int tipoArea, String name, ArrayList<GeomPoint3d> lsPts3d) {
    	CadArea o = new CadArea(oLayer);
    	o.init(tipoArea, name, lsPts3d);
    	return o;
    }
	
	public static CadArea create(CadArea other) {
    	CadArea o = new CadArea(other.getLayer());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	public CadArea duplicate()
	{
		CadArea other = CadArea.create(this);
		return other;
	}
	
	public CadArea copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadArea other = CadArea.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	public CadArea moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts)
		{
	    	MoveData3dVO o = GeomUtil.moveToPt3d(ptIMcs, ptFMcs, oPt3d);
	    	
	    	GeomPoint3d oPtDest3d = o.getPtDest();
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
    	return this;
	}
	
	public CadArea scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		ArrayList<GeomPoint3d> newLsPts = new ArrayList<GeomPoint3d>();
		
		double xCentroid = 0.0;
		double yCentroid = 0.0;
		double zCentroid = 0.0;

		double n = this.lsPts.size(); 
		
		for(GeomPoint3d oPt3d : this.lsPts)
		{
	    	ScaleData3dVO o = GeomUtil.scaleToPt3d(ptIMcs, ptFMcs, this.ptCentroid, oPt3d);
	    	
	    	GeomPoint3d oPtDest3d = o.getPtDest();
	    	newLsPts.add(oPtDest3d);
	    	
			xCentroid = xCentroid + oPtDest3d.getX();
			yCentroid = yCentroid + oPtDest3d.getY();
			zCentroid = zCentroid + oPtDest3d.getZ();
		}
		
		xCentroid = xCentroid / n;
		yCentroid = yCentroid / n;
		zCentroid = zCentroid / n;
		
		this.ptCentroid = new GeomPoint3d(xCentroid, yCentroid, zCentroid);
    	return this;
	}
	
	public CadArea offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadArea oPolygon = copyTo(ptIMcs, ptFMcs);
		return oPolygon;
	}
    
	/* DEBUG */

	@Override
	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		String str = super.toStr();		
		str += String.format(
			"TipoArea:%s;" + 
			"Name:%s;" + 
			"Centroid:%s;" + 
			"Area:%s;",
			Integer.toString(this.areaType),
			this.name,
			nf3.format(this.area),
		    ptCentroid.toStr());
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWING */
    
    public void redraw2d(ICadViewBase v, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g) 
    {
    	if(this.isDeleted()) return;

    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
        
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, false);

		Stroke b = selectLtype(bDragMode, bSelected, bHover);

		Stroke oldltype = GeomUtil.setLtype(g, b);
		
		Color c = super.selectColor(bDragMode, bSelected, bHover);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getPanel();
        String action = panel.getCurrAction();

        if(ptBase2dMcs != null) 
        {        
            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);
            
	        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
	        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
	        {
	        	CadArea oPolygon = this.duplicate();
	        	oPolygon.moveTo(ptBase3dMcs, pt3dMcs);
	        }	        
	        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
	        {
	        	CadArea oPolygon = this.duplicate();
	        	oPolygon.scaleTo(ptBase3dMcs, pt3dMcs);
	        }
        }
        
        ArrayList<GeomPoint2d> lsPts2d = GeomUtil.from3dTo2d(this.lsPts); 
        DrawUtil.drawPolygonMcs(v, lsPts2d, g);
        
        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisY = planMcs.getAxisY();
        
        GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);

        String strTipoArea = GeomUtil.getAreaTypeText(this.areaType);
        String strName = this.name;        
        String strArea = String.format("%s m2", nf3.format(this.area));

        double fMediumSzScr = AppDefs.FONTSZ_MEDIUM * AppDefs.UNIT_FACTOR_POL_TO_MM;        
        double fNormalSzScr = AppDefs.FONTSZ_NORMAL * AppDefs.UNIT_FACTOR_POL_TO_MM;        
        double fSmallSzScr = AppDefs.FONTSZ_SMALL * AppDefs.UNIT_FACTOR_POL_TO_MM;        

        double fMediumSzMcs = v.fromScrToMcs(fMediumSzScr);
        double fNormalSzMcs = v.fromScrToMcs(fNormalSzScr);
        double fSmallSzMcs = v.fromScrToMcs(fSmallSzScr);
    	
        double hTextLine = fMediumSzMcs;

        GeomPoint2d ptLabelArea = new GeomPoint2d(ptCentroid2d);
        GeomPoint2d ptLabelName = ptLabelArea.otherMoveTo(axisY, hTextLine);
        GeomPoint2d ptLabelTipoArea = ptLabelName.otherMoveTo(axisY, hTextLine);
        
        DrawUtil.drawTextMcs(v, strTipoArea, ptLabelTipoArea, fMediumSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        DrawUtil.drawTextMcs(v, strName, ptLabelName, fNormalSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        DrawUtil.drawTextMcs(v, strArea, ptLabelArea, fSmallSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        		
        if(bSelected || bHover) {
        	DrawUtil.drawPointMcs(v, ptCentroid2d, AppDefs.POINT_SIZE, g);
        }

        GeomUtil.setColor(g, oldcol);

        GeomUtil.setLtype(g, oldltype);
    }
    
	/* SELECT */
	
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) 
	{
		if(this.isDeleted()) return false;
		if(this.isSelected()) return true;

        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
	    double distBox = boxSz / 2.0;

        ArrayList<GeomPoint2d> lsPts2d = GeomUtil.from3dTo2d(this.lsPts); 
        GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);

        double distCenter = GeomUtil.maxDistOf2d(ptCentroid2d, lsPts2d);

        double maxDist = distCenter + distBox;
        
        double dist = ptCentroid2d.distTo(pt2dMcs);
		if(dist < maxDist) {
			if( bSelectEntity ) {
				this.setSelected(true);
			}
			return true;
		}
		return false;
	}

	/* OSNAP */

	public GeomPoint2d osnap2d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

    	//ENDPOINT
    	//
    	ArrayList<GeomPoint2d> lsPtEndpoint = GeomUtil.from3dTo2d(this.lsPts);    	
		
    	//CENTER
    	//
    	ArrayList<GeomPoint2d> lsPtCenter = new ArrayList<GeomPoint2d>();
    	
    	GeomPoint2d ptCentroid2d = new GeomPoint2d(this.ptCentroid);
    	lsPtCenter.add(ptCentroid2d);
    	
    	GeomPoint2d ptResult = null;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
    	if(ptResult != null) return ptResult;
    	
    	ptResult = GeomUtil.osnap2d(view2d, AppDefs.OSNAPMODE_CENTER, pt2dMcs, lsPtCenter, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	/* CENTROID */
	
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptCentroid);
		return ptResult;
	}
    
    /* Getters/Setters */

	public GeomPoint3d getPtCentroid() {
		return ptCentroid;
	}

	public void setPtCentroid(GeomPoint3d ptCentroid) {
		this.ptCentroid = ptCentroid;
	}

    public ArrayList<GeomPoint3d> getLsPts() {
        return this.lsPts;
    }

    public void setLsPts(ArrayList<GeomPoint3d> lsPts) {
		this.lsPts = lsPts;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getAreaType() {
		return areaType;
	}

	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
