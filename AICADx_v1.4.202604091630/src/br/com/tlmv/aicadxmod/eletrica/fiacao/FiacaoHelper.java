/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * FiacaoHelper.java
 * Autor: Luiz Marcio Viana, 13/02/2018
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao;

import java.awt.Graphics;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.utils.XmlUtil;
import br.com.tlmv.aicadxmod.eletrica.cad.CadImportaFiacaoEletrodutoEletricaOData;
import br.com.tlmv.aicadxmod.eletrica.cad.CadImportaFiacaoEletricaOData;
import br.com.tlmv.aicadxmod.eletrica.vo.FioVO;

public class FiacaoHelper 
{
//Private Static
    private static double FIA_MULT_T   = 4.00;
    private static double FIA_MULT_N   = 3.50;
    private static double FIA_MULT_RC  = 3.50;
    private static double FIA_MULT_F   = 2.50;

    private static double FIA_MULT_TXT = 5.50;

    private static FioVO[] TFIO = {
        new FioVO("R", AppDefs.FIA_R10, AppDefs.FAM_FIA_R),
        new FioVO("R", AppDefs.FIA_R9, AppDefs.FAM_FIA_R),
        new FioVO("R", AppDefs.FIA_R8, AppDefs.FAM_FIA_R),
        new FioVO("R", AppDefs.FIA_R7, AppDefs.FAM_FIA_R),
        new FioVO("R", AppDefs.FIA_R6, AppDefs.FAM_FIA_R),
        new FioVO("R", AppDefs.FIA_R5, AppDefs.FAM_FIA_R),
        new FioVO("R", AppDefs.FIA_R4, AppDefs.FAM_FIA_R),
        new FioVO("R", AppDefs.FIA_R3, AppDefs.FAM_FIA_R),
        new FioVO("R", AppDefs.FIA_R2, AppDefs.FAM_FIA_R),
        new FioVO("R", AppDefs.FIA_R1, AppDefs.FAM_FIA_R),
        new FioVO("RC", AppDefs.FIA_RC, AppDefs.FAM_FIA_RC),
        new FioVO("F", AppDefs.FIA_F3, AppDefs.FAM_FIA_F),
        new FioVO("F", AppDefs.FIA_F2, AppDefs.FAM_FIA_F),
        new FioVO("F", AppDefs.FIA_F1, AppDefs.FAM_FIA_F),
        new FioVO("N", AppDefs.FIA_N, AppDefs.FAM_FIA_N),
        new FioVO("T", AppDefs.FIA_TR, AppDefs.FAM_FIA_TR)
    };

//Private
    private CadDocumentDef doc = null;
    private String homeDir;
	private int debugMode;
	
	/* Methodes */
    
    private int sizeInsFios(int fios)
    {
        int sz = 0;
        while (fios > 0)
        {
            if ((fios & 0x00000001) != 0)
                sz = sz + 1;
            fios = fios >> 0x01;
        }
        return sz;
    }

    private double lenFios(ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia, double d)
    {
        double result = 0.0;

        for(CadImportaFiacaoEletrodutoEletricaOData fia : lsFia)
        {
            for(FioVO it : FiacaoHelper.TFIO)
            {
                if ((fia.getFia() & it.getFio()) != 0)
                {
                    if (it.getNomeFio() == "T")
                        result += FIA_MULT_T * d;
                    else if (it.getNomeFio() == "N")
                        result += FIA_MULT_N * d;
                    else if (it.getNomeFio() == "RC")
                        result += FIA_MULT_RC * d;
                    else
                        result += FIA_MULT_F * d;
                }
            }
        }
        return result;
    }

    private GeomPoint3d insFios(int fios, GeomPoint3d pti, GeomPoint3d vt, double d, ICadViewBase v)
    {
        for(FioVO it : FiacaoHelper.TFIO)
        {
            if ((fios & it.getFio()) != 0)
            {
                if (it.getNomeFio() == "T")
                    pti = null;
                else if (it.getNomeFio() == "N")
                    pti = null;
                else if (it.getNomeFio() == "RC")
                    pti = null;
                else
                    pti = null;
            }
        }
        return pti;
    }

//Public
    
	public FiacaoHelper(CadDocumentDef doc, String homeDir, int debugMode)
	{
		this.doc = doc;
		
		this.homeDir = homeDir;
		this.debugMode = debugMode;
	}
	
	/* Methodes */
	
	public native int processaFiacao(String homeDir, String srcFile, String targetFile, String debugFile, int debugMode);

	static {
		try {
			String libPath = System.getProperty("java.library.path");
			System.out.println("-Djava.library.path " + libPath);
			
			System.loadLibrary(AppDefs.fiacaoLib);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public int execute(String srcFile, String targetFile, String debugFile)
	{
	    int rscode = AppDefs.RSERR;
		try {
			String tmpSrcFile = this.homeDir + srcFile;
		    String tmpTargetFile = this.homeDir + targetFile;
		    String tmpDebugFile = this.homeDir + debugFile;

		    rscode = processaFiacao(
				this.homeDir, 
				tmpSrcFile, 
				tmpTargetFile, 
				tmpDebugFile, 
				this.debugMode);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return rscode;
	}
	
	public ArrayList<CadImportaFiacaoEletricaOData> loadResults(String targetFile)
	{
		ArrayList<CadImportaFiacaoEletricaOData> lsImportaFiacao = null;
		
		try {
		    String tmpTargetFile = this.homeDir + targetFile;

			String xmlData = FileUtil.readData(tmpTargetFile, AppDefs.DEF_COMMENT_MARK);
			if( !"-1".equals(xmlData) ) {
				lsImportaFiacao = this.importaFiacao(xmlData);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return lsImportaFiacao;
	}
	
	//
	//	<ResultadoImportaFiacao>
	//	<ImportaFiacao>
	//		<Hnd>100000919</Hnd>
	//		<ImportaFiacaoEletroduto>
	//			<Qdr>QDL-TIPO01</Qdr>
	//			<Cir>1</Cir>
	//			<Lbl>A</Lbl>
	//			<Fia>64</Fia>
	//		</ImportaFiacaoEletroduto>
	//		<ImportaFiacaoEletroduto>
	//			<Qdr>QDL-TIPO01</Qdr>
	//			<Cir>1</Cir>
	//			<Lbl>1</Lbl>
	//			<Fia>4</Fia>
	//		</ImportaFiacaoEletroduto>
	//		<ImportaFiacaoEletroduto>
	//			<Qdr>QDL-TIPO01</Qdr>
	//			<Cir>2</Cir>
	//			<Lbl>2</Lbl>
	//			<Fia>6</Fia>
	//		</ImportaFiacaoEletroduto>
	//	</ImportaFiacao>
	//	</ResultadoImportaFiacao>
	//
	public ArrayList<CadImportaFiacaoEletricaOData> importaFiacao(String xmlData)
	{
		ArrayList<CadImportaFiacaoEletricaOData> lsResult = new ArrayList<CadImportaFiacaoEletricaOData>(); 
		
		try {
   			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
   			DocumentBuilder db = dbf.newDocumentBuilder();
    			
   			InputSource is = new InputSource(new StringReader(xmlData));
   		    Document doc = db.parse(is);
    		    
            Node nResultadoImportaFiacao = doc.getFirstChild();

            ArrayList<Node> lsImportaFiacao = XmlUtil.getListChildNodeByName(nResultadoImportaFiacao, "ImportaFiacao");
            for(Node nImportaFiacao : lsImportaFiacao) {
                String strHnd = XmlUtil.getChildNodeValueByName(nImportaFiacao, "Hnd", false, "-1");
                int iHnd = StringUtil.safeInt(strHnd);
                
                CadImportaFiacaoEletricaOData oImportaFiacao = CadImportaFiacaoEletricaOData.create(this.doc, AppDefs.NULL_INTSTR, strHnd);
                
            	ArrayList<Node> lsImportaFiacaoEletroduto = XmlUtil.getListChildNodeByName(nImportaFiacao, "ImportaFiacaoEletroduto");
            	int rowId = 0;
                for(Node nImportaFiacaoEletroduto : lsImportaFiacaoEletroduto) {
                	String strQdr = XmlUtil.getChildNodeValueByName(nImportaFiacaoEletroduto, "Qdr", false, "");
                	String strCir = XmlUtil.getChildNodeValueByName(nImportaFiacaoEletroduto, "Cir", false, "");
                	String strLbl = XmlUtil.getChildNodeValueByName(nImportaFiacaoEletroduto, "Lbl", false, "");
                	String strFia = XmlUtil.getChildNodeValueByName(nImportaFiacaoEletroduto, "Fia", false, "0");
                
                	int iFia = StringUtil.safeInt(strFia);
                	
                	CadImportaFiacaoEletrodutoEletricaOData o = CadImportaFiacaoEletrodutoEletricaOData.create(
                		this.doc,
            			//
                	    rowId,
                	    strHnd,
                		strQdr, 
                		strCir, 
                		strLbl, 
                		iFia );
                	oImportaFiacao.addFia(o);
                	
                	rowId += 1;
                }
                lsResult.add(oImportaFiacao);
            }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return lsResult;
	}
	
//Public Static

	public static void drawFioAtMcs(ICadViewBase v, FioVO o, GeomPoint2d ptInsMcs, GeomVector2d vDirMcs, double tickSizeMcs, Graphics g)
	{
		GeomPoint2d ptIns = new GeomPoint2d(ptInsMcs);

		GeomVector2d uDir = vDirMcs.otherUnit();
		GeomVector2d nDir = uDir.otherNorm();

		double h2TickSizeMcs  = tickSizeMcs /  2.0;
		double h4TickSizeMcs  = tickSizeMcs /  4.0;
		double h8TickSizeMcs  = tickSizeMcs /  8.0;
		double h16TickSizeMcs = tickSizeMcs / 16.0;
		
		if( "T".equals(o.getNomeFio()) ) {
			//Terra
			//
			GeomPoint2d pt1 = ptIns.otherMoveTo(nDir, - h2TickSizeMcs);
			GeomPoint2d pt2 = ptIns.otherMoveTo(nDir,   h2TickSizeMcs);
			DrawUtil.drawLineMcs(v, pt1, pt2, g);

			GeomPoint2d pt3 = pt2.otherMoveTo(uDir, - h4TickSizeMcs);
			GeomPoint2d pt4 = pt2.otherMoveTo(uDir,   h4TickSizeMcs);
			DrawUtil.drawLineMcs(v, pt3, pt4, g);
		}
		else if( "N".equals(o.getNomeFio()) ) {
			//Neutro
			//
			GeomPoint2d pt1 = ptIns.otherMoveTo(nDir, - h2TickSizeMcs);
			GeomPoint2d pt2 = ptIns.otherMoveTo(nDir,   h2TickSizeMcs);
			DrawUtil.drawLineMcs(v, pt1, pt2, g);

			GeomPoint2d pt3 = pt2.otherMoveTo(uDir, h4TickSizeMcs);
			DrawUtil.drawLineMcs(v, pt2, pt3, g);
		}
		else if( "F".equals(o.getNomeFio()) ) {
			//Fase
			//
			GeomPoint2d pt1 = ptIns.otherMoveTo(nDir, - h2TickSizeMcs);
			GeomPoint2d pt2 = ptIns.otherMoveTo(nDir,   h2TickSizeMcs);
			DrawUtil.drawLineMcs(v, pt1, pt2, g);
		}
		else if( "RC".equals(o.getNomeFio()) ) {
			//RetornoCampainha
			//
			GeomPoint2d pt1 = ptIns.otherMoveTo(nDir,   h2TickSizeMcs);
			DrawUtil.drawLineMcs(v, ptIns, pt1, g);

			GeomPoint2d pt2 = ptIns.otherMoveTo(uDir,   h16TickSizeMcs);
			GeomPoint2d pt3 = ptIns.otherMoveTo(nDir,   h8TickSizeMcs);
			GeomPoint2d pt4 = ptIns.otherMoveTo(uDir, - h8TickSizeMcs);
			GeomPoint2d pt5 = ptIns.otherMoveTo(nDir, - h8TickSizeMcs);

			DrawUtil.drawLineMcs(v, pt1, pt2, g);
			DrawUtil.drawLineMcs(v, pt2, pt3, g);
			DrawUtil.drawLineMcs(v, pt3, pt4, g);
			DrawUtil.drawLineMcs(v, pt4, pt5, g);			
			DrawUtil.drawLineMcs(v, pt5, pt1, g);			
		}
		else if( "R".equals(o.getNomeFio()) ) {
			//Retorno
			//
			GeomPoint2d pt2 = ptIns.otherMoveTo(nDir,   h2TickSizeMcs);
			DrawUtil.drawLineMcs(v, ptIns, pt2, g);
		}
	}
	
	public static GeomPoint2d drawFiosMcs(ICadViewBase v, String label, int fios, GeomPoint2d ptInsMcs, GeomVector2d vDirMcs, double textSizeMcs, double tickSizeMcs, double tickDistanceMcs, Graphics g)
	{
		GeomPoint2d ptIns = new GeomPoint2d(ptInsMcs);
		
		GeomVector2d uDir = vDirMcs.otherUnit();
		GeomVector2d nDir = uDir.otherNorm();
		
		double lineHeight = textSizeMcs * 1.5;

		GeomPoint2d ptLbl = ptIns.otherMoveTo(nDir, lineHeight);
		
		for(FioVO o : FiacaoHelper.TFIO) {
			if( (fios & o.getFio()) != 0 ) {
				DrawUtil.drawTextMcs(v, label, ptLbl, textSizeMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
				
				FiacaoHelper.drawFioAtMcs(v, o, ptIns, uDir, tickSizeMcs, g);
				ptIns = ptIns.otherMoveTo(uDir, tickDistanceMcs);
			}
		}
		return ptIns;
	}
	
	public static double sizeOfFiosMcs(int fios, double tickDistanceMcs)
	{
		GeomPoint2d ptIns0 = new GeomPoint2d(0.0, 0.0);
		GeomVector2d uDir = new GeomVector2d(1.0, 0.0); 
		
		GeomPoint2d ptIns = new GeomPoint2d(ptIns0);
		for(FioVO o : FiacaoHelper.TFIO) {
			if( (fios & o.getFio()) != 0 ) {
				ptIns = ptIns.otherMoveTo(uDir, tickDistanceMcs);
			}
		}

		double dist = ptIns0.distTo(ptIns) - tickDistanceMcs; 
		return dist;
	}
	
}
