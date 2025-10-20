/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdDxfOut.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.awt.FileDialog;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppConfig;
import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.TagDataVO;

public class CmdDxfOut extends CmdBase
{
//Private
	
	//EXTMIN
	private double extMinX = 0.0;
	private double extMinY = 0.0;
	private double extMinZ = 0.0;
	//EXTMAX
	private double extMaxX = AppDefs.DEF_DEFAULT_PROJECT_AREA_WIDTH;
	private double extMaxY = AppDefs.DEF_DEFAULT_PROJECT_AREA_HEIGHT;
	private double extMaxZ = 0.0;
	//LIMMIN
	private double limMinX = 0.0;
	private double limMinY = 0.0;
	//LIMMAX
	private double limMaxX = AppDefs.DEF_DEFAULT_PROJECT_AREA_WIDTH;
	private double limMaxY = AppDefs.DEF_DEFAULT_PROJECT_AREA_HEIGHT;
	//LTSCALE
	private double ltscale = AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR * 2.0;
	//TEXTSIZE
	private double textSize = AppDefs.FONTSZ_NORMAL;
	//DIMSCALE
	private double dimScale = AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
	//PDSIZE
	private double pdSize = AppDefs.POINT_SIZE;
	//USERIx
	private double useri1 = AppDefs.DEF_DEFAULT_PROJECT_UNIT;
	private double useri2 = 0;
	private double useri3 = 0;
	private double useri4 = 0;
	private double useri5 = 0;
	//USERRx
	private double userr1 = AppDefs.DEF_DEFAULT_PROJECT_SCALE;
	private double userr2 = 0.0;
	private double userr3 = 0.0;
	private double userr4 = 0.0;
	private double userr5 = 0.0;
	//TDxxx
	private double tdCreate = 0.0;
	private double tdUpdate = 0.0;
	private double tdInDwg = 0.0;
	private double tdUsrTime = 0.0;

	//SECTION_VARS
	//
	private ArrayList<TagDataVO> lsTags = null;
	
	/* Methodes */

	private ArrayList<String> buildLayerTable() {
		ArrayList<String> lsResult = new ArrayList<String>();

		CadDocumentDef doc = this.getDoc();

		LayerTable layTbl = doc.getLayerTable();

		ArrayList<CadLayerDef> lsLayer = layTbl.getAllLayerDef();
		for(CadLayerDef oLayer : lsLayer) {
			String strRef = oLayer.getReference();
			if(strRef.compareToIgnoreCase(AppDefs.LAYER_0) != 0) {
				ArrayList<DxfCadEntity> lsDxfLayer = oLayer.toDxfR12();
				if(lsDxfLayer != null) {
					for(DxfCadEntity oDxfEntity : lsDxfLayer) {
						lsResult.addAll( oDxfEntity.toDxf() );
					}
				}				
			}
		}		
		return lsResult;
	}

	private ArrayList<String> buildSectionEntities() {
		ArrayList<String> lsResult = new ArrayList<String>();

		CadDocumentDef doc = this.getDoc();

		CadBlockDef blkDef = doc.getCurrBlockDef();

		ArrayList<CadEntity> lsEntity = blkDef.findAllEntity();
		for(CadEntity oEnt : lsEntity) {
			ArrayList<DxfCadEntity> lsDxfEntity = oEnt.toDxfR12();
			if(lsDxfEntity != null) {
				for(DxfCadEntity oDxfEntity : lsDxfEntity) {
					lsResult.addAll( oDxfEntity.toDxf() );
				}
			}
		}		
		return lsResult;
	}
	
	private ArrayList<TagDataVO> buildTagList() {
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);

		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		Date dataAtual = new Date();
		long dataAtualMili = dataAtual.getTime() / 1000;
		
		CadDocumentDef doc = this.getDoc();

		CadBlockDef blkDef = doc.getCurrBlockDef();

    	CadProjectDef projDef = doc.getCurrProjectDef();

    	double scale = projDef.getEscala();
    	double unidade = projDef.getUnidade();

    	double sclFact = projDef.getScaleFactor();
    	double ltScale = sclFact * 2.0;
    	
    	String str = "ScaleFactor: " + nf6.format(sclFact);
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL05, str, this.getClass());

		GeomDimension2d oGeomDim = blkDef.getEnvelop2d(AppDefs.OBJTYPE_ALL);
		
		GeomPoint2d ptMin2d = oGeomDim.getPtMin();
		GeomPoint2d ptMax2d = oGeomDim.getPtMax();
		
		//EXTMIN
		this.extMinX = ptMin2d.getX();
		this.extMinY = ptMin2d.getY();
		this.extMinZ = 0.0;
		//EXTMAX
		this.extMaxX = ptMax2d.getX();
		this.extMaxY = ptMax2d.getY();
		this.extMaxZ = 0.0;
		//LIMMIN
		this.limMinX = ptMin2d.getX();
		this.limMinY = ptMin2d.getY();
		//LIMMAX
		this.limMaxX = ptMin2d.getX();
		this.limMaxY = ptMin2d.getY();
		//LTSCALE
		this.ltscale = ltScale;
		//TEXTSIZE
		this.textSize = AppDefs.FONTSZ_NORMAL;
		//DIMSCALE
		this.dimScale = sclFact;
		//PDSIZE
		this.pdSize = AppDefs.POINT_SIZE;
		//USERIxxx
		this.useri1 = unidade;
		this.useri2 = 0;
		this.useri3 = 0;
		this.useri4 = 0;
		this.useri5 = 0;
		//USERRxxx
		this.userr1 = scale;
		this.userr2 = 0.0;
		this.userr3 = 0.0;
		this.userr4 = 0.0;
		this.userr5 = 0.0;
		//TDxxx
		this.tdCreate = dataAtualMili;
		this.tdUpdate = dataAtualMili;
		this.tdInDwg = 1.0;
		this.tdUsrTime = 1.0;
		
		//SECTION_VARS
		//
		this.lsTags = new ArrayList<TagDataVO>();
		int pos = 0;

		//EXTMIN
		str = nf6.format(this.extMinX);
		TagDataVO oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_EXTMIN_X, str);
		this.lsTags.add(oTag);
		
		str = nf6.format(this.extMinY);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_EXTMIN_Y, str);
		this.lsTags.add(oTag);
		
		str = nf6.format(this.extMinZ);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_EXTMIN_Z, str);
		this.lsTags.add(oTag);

		//EXTMAX
		str = nf6.format(this.extMaxX);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_EXTMAX_X, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.extMaxY);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_EXTMAX_Y, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.extMaxZ);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_EXTMAX_Z, str);
		this.lsTags.add(oTag);

		//LIMMIN
		str = nf6.format(this.limMinX);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_LIMMIN_X, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.limMinY);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_LIMMIN_Y, str);
		this.lsTags.add(oTag);
		
		//LIMMAX
		str = nf6.format(this.limMaxX);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_LIMMAX_X, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.limMaxY);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_LIMMAX_Y, str);
		this.lsTags.add(oTag);
		
		//LTSCALE
		str = nf6.format(this.ltscale);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_LTSCALE, str);
		this.lsTags.add(oTag);
		
		//TEXTSIZE
		str = nf6.format(this.textSize);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_TEXTSIZE, str);
		this.lsTags.add(oTag);
		
		//DIMSCALE
		str = nf6.format(this.dimScale);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_DIMSCALE, str);
		this.lsTags.add(oTag);
		
		//PDSIZE
		str = nf6.format(this.pdSize);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_PDSIZE, str);
		this.lsTags.add(oTag);
		
		//USERIxxx
		str = nf6.format(this.useri1);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERI1, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.useri2);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERI2, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.useri3);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERI3, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.useri4);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERI4, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.useri5);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERI5, str);
		this.lsTags.add(oTag);

		//USERRxxx
		str = nf6.format(this.userr1);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERR1, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.userr2);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERR2, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.userr3);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERR3, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.userr4);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERR4, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.userr5);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_USERR5, str);
		this.lsTags.add(oTag);

		//TDxxx
		str = nf6.format(this.tdCreate);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_TDCREATE, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.tdUpdate);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_TDUPDATE, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.tdInDwg);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_TDINDWG, str);
		this.lsTags.add(oTag);

		str = nf6.format(this.tdUsrTime);
		oTag = new TagDataVO(pos++, AppDefs.TAGS_DXFR12_TDUSRTIME, str);
		this.lsTags.add(oTag);
		
		return this.lsTags;
	}
		
//Public
	
	public CmdDxfOut() {
		super(AppDefs.ACTION_FILE_DXFOUT, false, true);
	}
	
	/* Methodes */
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("DXF Out...");

		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();
		
		String cdir = ctx.getHomeDir();		
		
		FileDialog dlg = new FileDialog(this.getFrm());
		dlg.setTitle("Save DXF File");
		dlg.setMode(FileDialog.SAVE);
		dlg.setDirectory(cdir);
		dlg.setModal(true);
		dlg.show();
		
		String dirName = dlg.getDirectory();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, dirName, this.getClass());

		String fileName = dlg.getFile();
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fileName, this.getClass());

		String fullFileName = dirName + fileName;
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL06, fullFileName, this.getClass());
		
		result = new InputParamVO();
		result.initFileName(fullFileName);
		
		return result;
	}
		
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;
		
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();
		
		String templateDxfFileName = ctx.getTemplateDxfFile(); 		

		String fullFileName = oParam.getFileName();
		
		ArrayList<String> lsSrcStr = FileUtil.readDataAsList(templateDxfFileName);
		ArrayList<String> lsDstStr = new ArrayList<String>();
		for(String srcStr : lsSrcStr) {
			if( AppDefs.TAGS_DXFR12_SECTION_ENTITIES.equals(srcStr) ) {
				ArrayList<String> lsSectionEntities = buildSectionEntities();
				lsDstStr.addAll( lsSectionEntities );
			}
			else if( AppDefs.TAGS_DXFR12_LAYER_TABLE.equals(srcStr) ) {
				ArrayList<String> lsLayerTable = buildLayerTable();
				lsDstStr.addAll( lsLayerTable );
			}
			else {
				lsDstStr.add( srcStr );
			}
		}
		FileUtil.writeDataAsList(fullFileName, lsDstStr);
	}
	
	/* Getters/Setters */
	
	public double getExtMinX() {
		return extMinX;
	}

	public void setExtMinX(double extMinX) {
		this.extMinX = extMinX;
	}

	public double getExtMinY() {
		return extMinY;
	}

	public void setExtMinY(double extMinY) {
		this.extMinY = extMinY;
	}

	public double getExtMinZ() {
		return extMinZ;
	}

	public void setExtMinZ(double extMinZ) {
		this.extMinZ = extMinZ;
	}

	public double getExtMaxX() {
		return extMaxX;
	}

	public void setExtMaxX(double extMaxX) {
		this.extMaxX = extMaxX;
	}

	public double getExtMaxY() {
		return extMaxY;
	}

	public void setExtMaxY(double extMaxY) {
		this.extMaxY = extMaxY;
	}

	public double getExtMaxZ() {
		return extMaxZ;
	}

	public void setExtMaxZ(double extMaxZ) {
		this.extMaxZ = extMaxZ;
	}

	public double getLimMinX() {
		return limMinX;
	}

	public void setLimMinX(double limMinX) {
		this.limMinX = limMinX;
	}

	public double getLimMinY() {
		return limMinY;
	}

	public void setLimMinY(double limMinY) {
		this.limMinY = limMinY;
	}

	public double getLimMaxX() {
		return limMaxX;
	}

	public void setLimMaxX(double limMaxX) {
		this.limMaxX = limMaxX;
	}

	public double getLimMaxY() {
		return limMaxY;
	}

	public void setLimMaxY(double limMaxY) {
		this.limMaxY = limMaxY;
	}

	public double getLtscale() {
		return ltscale;
	}

	public void setLtscale(double ltscale) {
		this.ltscale = ltscale;
	}

	public double getTextSize() {
		return textSize;
	}

	public void setTextSize(double textSize) {
		this.textSize = textSize;
	}

	public double getDimScale() {
		return dimScale;
	}

	public void setDimScale(double dimScale) {
		this.dimScale = dimScale;
	}

	public double getPdSize() {
		return pdSize;
	}

	public void setPdSize(double pdSize) {
		this.pdSize = pdSize;
	}

	public double getUseri1() {
		return useri1;
	}

	public void setUseri1(double useri1) {
		this.useri1 = useri1;
	}

	public double getUseri2() {
		return useri2;
	}

	public void setUseri2(double useri2) {
		this.useri2 = useri2;
	}

	public double getUseri3() {
		return useri3;
	}

	public void setUseri3(double useri3) {
		this.useri3 = useri3;
	}

	public double getUseri4() {
		return useri4;
	}

	public void setUseri4(double useri4) {
		this.useri4 = useri4;
	}

	public double getUseri5() {
		return useri5;
	}

	public void setUseri5(double useri5) {
		this.useri5 = useri5;
	}

	public double getUserr1() {
		return userr1;
	}

	public void setUserr1(double userr1) {
		this.userr1 = userr1;
	}

	public double getUserr2() {
		return userr2;
	}

	public void setUserr2(double userr2) {
		this.userr2 = userr2;
	}

	public double getUserr3() {
		return userr3;
	}

	public void setUserr3(double userr3) {
		this.userr3 = userr3;
	}

	public double getUserr4() {
		return userr4;
	}

	public void setUserr4(double userr4) {
		this.userr4 = userr4;
	}

	public double getUserr5() {
		return userr5;
	}

	public void setUserr5(double userr5) {
		this.userr5 = userr5;
	}

	public double getTdCreate() {
		return tdCreate;
	}

	public void setTdCreate(double tdCreate) {
		this.tdCreate = tdCreate;
	}

	public double getTdUpdate() {
		return tdUpdate;
	}

	public void setTdUpdate(double tdUpdate) {
		this.tdUpdate = tdUpdate;
	}

	public double getTdInDwg() {
		return tdInDwg;
	}

	public void setTdInDwg(double tdInDwg) {
		this.tdInDwg = tdInDwg;
	}

	public double getTdUsrTime() {
		return tdUsrTime;
	}

	public void setTdUsrTime(double tdUsrTime) {
		this.tdUsrTime = tdUsrTime;
	}

	public ArrayList<TagDataVO> getLsTags() {
		return lsTags;
	}

	public void setLsTags(ArrayList<TagDataVO> lsTags) {
		this.lsTags = lsTags;
	}

}
