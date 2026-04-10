/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadLayerDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/04/2025
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

package br.com.tlmv.aicadxapp.cad;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfEntry;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.utils.StyleUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;

public class CadLayerDef extends CadObject 
{
//Public
	private String name;
	private String reference;
	private String ltypeName;
	private int ltypeIndex;
	private String colorName;
	private int colorIndex;
	private double lineWeight;
	private double minDist;
	private int categoriaId;
	private String descricaoCategoria;
	private boolean bLayerOn;
	
	//Internal_Ltype
	private BorderStrokeVO oLtype;

	//Internal_Color
	private ColorVO oColor;
	
//Public

	public CadLayerDef(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_LAYER_DEF, doc, null);
	}

	/* Methodes */

	public void init() 
	{
		this.oLtype				= new BorderStrokeVO();
		this.oColor 			= new ColorVO();
		//
		this.name 				= "";
		this.reference 			= "";
		this.ltypeName			= this.oLtype.getName();
		this.colorName			= this.oColor.getName();
		this.lineWeight 		= AppDefs.LAYDEF_LINEWEIGHT_DEFAULT;
		this.minDist 			= AppDefs.LAYDEF_MINDIST_DEFAULT;
		this.categoriaId 		= AppDefs.LAYDEF_CATEGORIAID_DEFAULT;
		this.descricaoCategoria = AppDefs.LAYDEF_DESCRICAO_CATEGORIA_DEFAULT;
		this.bLayerOn 			= true;
	}
	
	public void init(
		String name,
		String reference,
		int ltypeIndex,
		int colorIndex,
		double lineWeight,
		double minDist,
		int categoriaId,
		String descricaoCategoria,
		boolean bLayerOn ) 
	{
		String colorIndexName = StyleUtil.getColorName(colorIndex);
		int colorIndexVal = StyleUtil.getColorIndex(colorIndex);

		this.oLtype 			= StyleUtil.getLtype(ltypeIndex);
		this.oColor 			= new ColorVO(colorIndexName, colorIndexVal); 
		
		this.name 				= name;
		this.reference 			= reference;
		this.ltypeName			= this.oLtype.getName();
		this.colorName			= this.oColor.getName();
		this.lineWeight 		= lineWeight;
		this.minDist 			= minDist;
		this.categoriaId 		= categoriaId;
		this.descricaoCategoria = descricaoCategoria;
		this.bLayerOn 			= true;
	}
	
	public void init(
		String name,
		String reference,
		int r, int g, int b,
		int ltypeIndex,
		double lineWeight,
		double minDist,
		int categoriaId,
		String descricaoCategoria) 
	{
		String colorIndexName = StyleUtil.getColorName(r, g, b);

		this.oLtype 			= StyleUtil.getLtype(ltypeIndex);
		this.oColor 			= new ColorVO(colorIndexName, r, g, b); 
		
		this.name 				= name;
		this.reference 			= reference;
		this.ltypeName			= this.oLtype.getName();
		this.colorName			= this.oColor.getName();
		this.lineWeight 		= AppDefs.LAYDEF_LINEWEIGHT_DEFAULT;
		this.minDist 			= AppDefs.LAYDEF_MINDIST_DEFAULT;
		this.categoriaId 		= AppDefs.LAYDEF_CATEGORIAID_DEFAULT;
		this.descricaoCategoria = AppDefs.LAYDEF_DESCRICAO_CATEGORIA_DEFAULT;
		this.bLayerOn 			= true;
	}
	
	@Override
	public void init(ICadObject other) {
		CadLayerDef oLayer = (CadLayerDef)other;

		this.oLtype 			= oLayer.oLtype;
		this.oColor 			= oLayer.oColor;

		this.name 				= oLayer.name;
		this.reference 			= oLayer.reference;
		this.ltypeName			= this.oLtype.getName();
		this.colorName			= this.oColor.getName();
		this.lineWeight 		= oLayer.lineWeight;
		this.minDist	 		= oLayer.minDist;
		this.categoriaId		= oLayer.categoriaId;
		this.descricaoCategoria = oLayer.descricaoCategoria;
		this.bLayerOn 			= oLayer.bLayerOn;
	}

	@Override
	public void reset() {
		/* nothing todo! */
	}
	
	/* CREATE */

	public static CadLayerDef create(
		CadDocumentDef doc,
		String name,
		String reference,
		int ltypeIndex,
		int colorIndex,
		double lineWeight,
		double minDist,
		int categoriaId,
		String descricaoCategoria )
	{
		CadLayerDef o = new CadLayerDef(doc); 
		o.init(
			name,
			reference,
			ltypeIndex,
			colorIndex,
			lineWeight,
			minDist,
			categoriaId,
			descricaoCategoria,
			true );
		return o;
	}
			
	public static CadLayerDef create(
		CadDocumentDef doc,
		String name,
		String reference,
		int ltypeIndex,
		int colorRgbR, 
		int colorRgbG, 
		int colorRgbB,
		double lineWeight,
		double minDist,
		int categoriaId,
		String descricaoCategoria) 
	{
		CadLayerDef o = new CadLayerDef(doc); 
		o.init(
			name, 
			reference, 
			ltypeIndex,
			colorRgbR, 
			colorRgbG, 
			colorRgbB, 
			lineWeight,
			minDist,
			categoriaId,
			descricaoCategoria);
		return o;
	}
	
	public static CadLayerDef create(CadDocumentDef doc, CadLayerDef oLayer) 
	{
		CadLayerDef o = new CadLayerDef(doc); 
		o.init(oLayer);
		return o;
	}
	
	/* TO/FROM STRING_DATA */
	
	//012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
	//          1         2         3         4         5         6         7         8         9         0         
	//                                                                                                    1         
	//
	//0-35                                36-71                               72-87           88-103          104-119         120-129 128131
	//LAYER                               REFERENCE                           LTYPE           COLOR           LWEIGHT         MINDIST ID CATEGORIA                     (DON'T ERASE THIS LINE!)
	//----------------------------------- ----------------------------------- --------------- --------------- --------------- ------- -- ----------------------------- (DON'T ERASE THIS LINE!)
	//ARQ-ALVENARIA                       A_ALVE                              CONTINUOUS      7               0.0             10.0    01 ALV
	//ARQ-ALVENARIA-ACABAMENTO            A_ALVE_ACAB                         CONTINUOUS      6               0.0             -1      02 ALV-ACAB
	//ARQ-PORTA                           A_PORTA                             CONTINUOUS      9               0.0             -1      99 NB
	// :
	public int fromStrData(String str)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		if(str.length() < 104) 
			return AppDefs.RSERR;
		
		String strName 					= StringUtil.trimAll(str.substring(0, 35));
		String strReference 			= StringUtil.trimAll(str.substring(36, 71));
		String strLtype 				= StringUtil.trimAll(str.substring(72, 87));
		String strColor 				= StringUtil.trimAll(str.substring(88, 103));
		String strLweight 				= StringUtil.trimAll(str.substring(104, 119));
		String strMinDist 				= StringUtil.trimAll(str.substring(120, 127));
		String strCategoriaId 			= StringUtil.trimAll(str.substring(128, 130));
		String strDescricaoCategoria 	= StringUtil.trimAll(str.substring(131));
		
		//NAME
		this.name = strName;
		
		//REFERENCE
		this.reference = strReference;
		
		//LTYPE
		this.oLtype = StyleUtil.getLtypeByName(strLtype);
		
		//COLOR
		String[] arr = StringUtil.split(strColor, ',');
		if(arr.length < 4) {
			int val = StringUtil.safeInt(arr[0]);
			
			String colorName = StyleUtil.getColorName(val);
			int colorIndex = StyleUtil.getColorIndex(val);
			this.oColor = new ColorVO(colorName, colorIndex);
		}
		else {
			int r = StringUtil.safeInt(arr[0]);
			int g = StringUtil.safeInt(arr[1]);
			int b = StringUtil.safeInt(arr[2]);
			
			String colorName = StyleUtil.getColorName(r, g, b);
			this.oColor = new ColorVO(colorName, r, g, b);
		}

		//LWEIGHT
		this.lineWeight = StringUtil.safeDbl(nf3, strLweight);	

		//MINDIST
		this.minDist = StringUtil.safeDbl(nf3, strMinDist);	

		//CATEGORIA_ID
		this.categoriaId = StringUtil.safeInt(strCategoriaId);	

		//DESCRICAO_CATEGORIA
		this.descricaoCategoria = strDescricaoCategoria;
		
		this.bLayerOn = true;
		
		return AppDefs.RSOK;
	}
	
	public Object[] toArrayData(String strCurrLayer)
	{
		Object[] arr = null;
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);

		Date dataHoraAtual = new Date();
		
		Date dataAtual = new Date(dataHoraAtual.getYear(), dataHoraAtual.getMonth(), dataHoraAtual.getDate());

		ColorVO oColor = this.oColor;
		BorderStrokeVO oLtype = this.oLtype;
		
		int szArr = r.LS_TBLLAYEREXPLORER.length;
		arr = new Object[szArr];

		String strLayerName = this.name;		

		Boolean bActive = false;

		if(strLayerName.compareToIgnoreCase(strCurrLayer) == 0) {
			bActive = true;
		}

		//Color valColor = oColor.getColor();
		//String strLtype = oLtype.getName();
		Boolean bLayerOnOff = this.bLayerOn;

		//Ativo,Nome,Cor,Ltype,On/Off
		//
		int n = 0;
		arr[n++] = bActive;
		arr[n++] = strLayerName;
		arr[n++] = oColor;
		arr[n++] = oLtype;
		//arr[n++] = this.lineWeight;
		//arr[n++] = this.minDist;
		//arr[n++] = this.categoriaId;
		//arr[n++] = this.descricaoCategoria;
		
		arr[n++] = bLayerOnOff;

		return arr;
	}
	
	/* DEBUG */
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingEnUs(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingEnUs(3);
		
		String strActive = "*";
		String strName = this.name;
		String strReference = this.reference;
		String strColor = oColor.getName();
		String strLtype = oLtype.getName();
		String strLineWeight = nf3.format( this.lineWeight );
		String strMinDist = nf0.format( this.minDist );
		String strCategoriaId = nf0.format( this.categoriaId );
		String strDescricaoCategoria = this.descricaoCategoria;
		String strLayerOnOff = ( this.bLayerOn ) ? "On" : "Off";
		
		String str = String.format(
			"Active:%s;" +
			"Name:%s;" +
			"Reference:%s;" +
			"Color:%s;" +
			"Ltype:%s;" +
			"LineWeight:%s;" +
			"MinDist:%s;" +
			"CategoriaId:%s;" +
			"DescricaoCategoria:%s;" +
			"LayerOnOff:%s;",
			strActive,
			strName,
			strReference,
			strColor,
			strLtype,
			strLineWeight,
			strMinDist,
			strCategoriaId,
			strDescricaoCategoria,
			strLayerOnOff );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* LOAD/SAVE */
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return false;
	}
	
	/* READ/WRITE DXF R12 */
	
	@Override
	public void fromDxfR12(DxfCadEntity o)
	{
		//TODO:
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12()
	{
		ArrayList<DxfCadEntity> lsDxfLayerTable = new ArrayList<DxfCadEntity>(); 
		
		ArrayList<DxfCadEntity> lsDxfLayerDef = toDxfR12_view2d();
		lsDxfLayerTable.addAll( lsDxfLayerDef );
		
		return lsDxfLayerTable;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingEnUs(6);

		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingEnUs(0);
		
		//NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingEnUs(3);

		//DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		//AppCadMain cad = AppCadMain.getCad();
		
		//CadDocumentDef doc = cad.getCurrDocumentDef();
	
		DxfCadEntity oDxfCadEntity = new DxfCadEntity(
			this.getObjectId(), 
			AppDefs.NULL_LNG, 
			AppDefs.DXFCODE_ENTITYTYPE, 
			AppDefs.DXFETYPE_LAYER); 
		
		//LAYER
		//
		DxfEntry oDxfEntry = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_NAME, this.getName());
		oDxfCadEntity.add(oDxfEntry);

		//FLAGS
		//
		String dxfLayerFlagVal = "     0";
		DxfEntry oDxfLayerFlag = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LAYER_FLAG, dxfLayerFlagVal);
		oDxfCadEntity.add(oDxfLayerFlag);
		
		//COLOR
		//
		String dxfColorVal = nf0.format( this.oColor.getColorIndex() );
		DxfEntry oDxfColor = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_COLOR, dxfColorVal);
		oDxfCadEntity.add(oDxfColor);
		
		//LTYPE
		//
		String dxfLinetypeVal = this.oLtype.getName();
		DxfEntry oDxfLineType = new DxfEntry(AppDefs.NULL_LNG, AppDefs.DXFCODE_LTYPE, dxfLinetypeVal);
		oDxfCadEntity.add(oDxfLineType);

		ArrayList<DxfCadEntity> lsCadLayerDef = new ArrayList<DxfCadEntity>();
		lsCadLayerDef.add( oDxfCadEntity );
		
		return lsCadLayerDef;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		return null;
	}
	
	/* Getters/Setters */
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLayerOn() {
		return this.bLayerOn;
	}

	public void setLayerOn(boolean bLayerOn) {
		this.bLayerOn = bLayerOn;
	}

	public ColorVO getColor() {
		return oColor;
	}

	public void setColor(ColorVO oColor) {
		this.oColor = oColor;
	}

	public BorderStrokeVO getLtype() {
		return oLtype;
	}

	public void setLtype(BorderStrokeVO oLtype) {
		this.oLtype = oLtype;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public double getLineWeight() {
		return lineWeight;
	}

	public void setLineWeight(double lineWeight) {
		this.lineWeight = lineWeight;
	}

	public double getMinDist() {
		return minDist;
	}

	public void setMinDist(double minDist) {
		this.minDist = minDist;
	}

	public int getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}
	
}
