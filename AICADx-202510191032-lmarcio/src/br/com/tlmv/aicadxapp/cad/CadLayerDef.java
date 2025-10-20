/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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

package br.com.tlmv.aicadxapp.cad;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
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
	private ColorVO oColor;
	private BorderStrokeVO oLtype;
	private double lineWeight;
	private boolean bLayerOn;
	
//Public

	public CadLayerDef() {
		super(AppDefs.OBJTYPE_LAYER_DEF, null);
	}

	/* Methodes */

	public void init() 
	{
		this.name = "";
		this.reference = "";
		this.oColor = new ColorVO();
		this.oLtype = new BorderStrokeVO();
		this.bLayerOn = true;
	}
	
	public void init(
		String name,
		String reference,
		int colorIndex,
		int ltypeIndex) 
	{
		String colorIndexName = StyleUtil.getColorName(colorIndex);
		int colorIndexVal = StyleUtil.getColorIndex(colorIndex);

		this.name = name;
		this.reference = reference;
		this.oColor = new ColorVO(colorIndexName, colorIndexVal); 
		this.oLtype = StyleUtil.getLtype(ltypeIndex);
		this.bLayerOn = true;
	}
	
	public void init(
		String name,
		String reference,
		int r, int g, int b,
		int ltypeIndex) 
	{
		String colorIndexName = StyleUtil.getColorName(r, g, b);

		this.name = name;
		this.reference = reference;
		this.oColor = new ColorVO(colorIndexName, r, g, b); 
		this.oLtype = StyleUtil.getLtype(ltypeIndex);
		this.bLayerOn = true;
	}
	
	public void init(CadLayerDef oLayer) 
	{
		this.name = oLayer.name;
		this.reference = oLayer.reference;
		this.oColor = oLayer.oColor;
		this.oLtype = oLayer.oLtype;
		this.bLayerOn = oLayer.bLayerOn;
	}

	@Override
	public void reset() {
		/* nothing todo! */
	}
	
	/* CREATE */

	public static CadLayerDef create(
		String name,
		String reference,
		int colorIndex,
		int ltypeIndex) 
	{
		CadLayerDef o = new CadLayerDef(); 
		o.init(name, reference, colorIndex, ltypeIndex);
		return o;
	}
		
	public static CadLayerDef create(
		String name,
		String reference,
		int r, int g, int b,
		int ltypeIndex) 
	{
		CadLayerDef o = new CadLayerDef(); 
		o.init(name, reference, r, g, b, ltypeIndex);
		return o;
	}
	
	public static CadLayerDef create(CadLayerDef oLayer) 
	{
		CadLayerDef o = new CadLayerDef(); 
		o.init(oLayer);
		return o;
	}
	
	/* TO/FROM STRING_DATA */
	
	//012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789
	//          1         2         3         4         5         6         7         8         9         0         
	//                                                                                                    1         
	//
	//0-35                                36-71                               72-87           88-103          104           118
	//LAYER                               REFERENCE                           LTYPE           COLOR           LWEIGHT         (DON'T ERASE THIS LINE!)
	//----------------------------------- ----------------------------------- --------------- --------------- --------------- (DON'T ERASE THIS LINE!)
	//ARQ-ALVENARIA                       A_ALVE                              CONTINUOUS      7               0.0
	//ARQ-ALVENARIA-ACABAMENTO            A_ALVE_ACAB                         CONTINUOUS      6               0.0
	//ARQ-PORTA                           A_PORTA                             CONTINUOUS      9               0.0
	// :
	public int fromStrData(String str)
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);
		
		if(str.length() < 104) 
			return AppDefs.RSERR;
		
		String strName = StringUtil.trimAll(str.substring(0, 35));
		String strReference = StringUtil.trimAll(str.substring(36, 71));
		//String strLtype = StringUtil.trimAll(str.substring(72, 87));
		String strLtype = "CONTINUOUS";
		String strColor = StringUtil.trimAll(str.substring(88, 103));
		String strLweight = StringUtil.trimAll(str.substring(104));

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
		//double lweight = StringUtil.safeDbl(nf3, strLweight);	

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
		arr[n++] = bLayerOnOff;

		return arr;
	}
	
	/* DEBUG */
	
	@Override
	public String toStr() {
		String strActive = "*";
		String strName = this.name;
		String strReference = this.reference;
		String strColor = oColor.getName();
		String strLtype = oLtype.getName();
		String strLayerOnOff = ( this.bLayerOn ) ? "On" : "Off";
		
		String str = strActive;
		str += strName;
		str += strReference;
		str += strColor;
		str += strLtype;
		str += strLayerOnOff;
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* LOAD/SAVE */
	
	@Override
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		//TODO:
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		//TODO:
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
	
}
