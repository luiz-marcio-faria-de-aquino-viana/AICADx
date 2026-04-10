/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadParedeExport.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/04/2025
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

package br.com.tlmv.aicadxapp.export;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.TagDataVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;

public class CadParedeExport implements IExportData
{
//Private
	private Hashtable<Integer, CadParede> lsItemData = new Hashtable<Integer, CadParede>();

	private Hashtable<String, ItemDataVO> lsTipoItemData = new Hashtable<String, ItemDataVO>();
	
	private ArrayList<TagDataVO> lsTagData = null;

	//TAG - EXPORTDATA
	//
	// --- CADPAREDE
	//
	private String[] TAG_LIST = {	
		AppDefs.DEF_TAG_CADPAREDE_OBJECT_ID,
		AppDefs.DEF_TAG_CADPAREDE_OBJTYPE,
		AppDefs.DEF_TAG_CADPAREDE_LAYERNAME,
		AppDefs.DEF_TAG_CADPAREDE_TIPO,
		AppDefs.DEF_TAG_CADPAREDE_ALTURA,
		AppDefs.DEF_TAG_CADPAREDE_LARGURA,
		AppDefs.DEF_TAG_CADPAREDE_XPTI,
		AppDefs.DEF_TAG_CADPAREDE_YPTI,
		AppDefs.DEF_TAG_CADPAREDE_ZPTI,
		AppDefs.DEF_TAG_CADPAREDE_XPTF,
		AppDefs.DEF_TAG_CADPAREDE_YPTF,
		AppDefs.DEF_TAG_CADPAREDE_ZPTF
	};
	
	private void buildFieldTypeList()
	{
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);

		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		//ArrayList: NomeCampo - TipoCampo		
		// --- CADPAREDE
		this.lsTipoItemData.put(AppDefs.DEF_TAG_CADPAREDE_OBJECT_ID, new ItemDataVO(AppDefs.DEF_TAG_CADPAREDE_OBJECT_ID, AppDefs.DEF_TIPOCAMPO_LONG));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_CADPAREDE_OBJTYPE, new ItemDataVO(AppDefs.DEF_TAG_CADPAREDE_OBJTYPE, AppDefs.DEF_TIPOCAMPO_INT));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_CADPAREDE_LAYERNAME, new ItemDataVO(AppDefs.DEF_TAG_CADPAREDE_LAYERNAME, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_CADPAREDE_TIPO, new ItemDataVO(AppDefs.DEF_TAG_CADPAREDE_TIPO, AppDefs.DEF_TIPOCAMPO_INT));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_CADPAREDE_ALTURA, new ItemDataVO(AppDefs.DEF_TAG_CADPAREDE_ALTURA, AppDefs.DEF_TIPOCAMPO_DOUBLE));		
		this.lsTipoItemData.put(AppDefs.DEF_TAG_CADPAREDE_LARGURA, new ItemDataVO(AppDefs.DEF_TAG_CADPAREDE_LARGURA, AppDefs.DEF_TIPOCAMPO_DOUBLE));		
		this.lsTipoItemData.put(AppDefs.DEF_TAG_CADPAREDE_XPTI, new ItemDataVO(AppDefs.DEF_TAG_CADPAREDE_XPTI, AppDefs.DEF_TIPOCAMPO_DOUBLE));			
	}
	
	private ItemDataVO findItemDataByFieldName(String fieldName, Hashtable<String, ItemDataVO> lsTipoItemData)
	{
		if( lsTipoItemData.contains(fieldName) )
		{
			ItemDataVO o = lsTipoItemData.get(fieldName);
			return o;
		}
		return null;
	}
	
	private ItemDataVO getItemDataValueByFieldName(String fieldName, ArrayList<String> lsFieldName, ArrayList<String> lsValue)
	{
		ItemDataVO result = null;
		
		try
		{
			NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
			
			DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
			
			ItemDataVO o = findItemDataByFieldName(fieldName, lsTipoItemData);
			
			for(int i = 0; i < lsFieldName.size(); i++)
			{
				String fieldNameData = lsFieldName.get(i);
				String valueData = lsValue.get(i);
				
				if( fieldName.equalsIgnoreCase(fieldNameData) )
				{
					result = new ItemDataVO(o);
					
					//TIPOCAMPO
					if( AppDefs.DEF_TIPOCAMPO_INT.equalsIgnoreCase(o.getDescricao()) )
						result.setIntVal(StringUtil.safeInt(valueData));
					else if( AppDefs.DEF_TIPOCAMPO_LONG.equalsIgnoreCase(o.getDescricao()) )
						result.setLngVal(StringUtil.safeLng(valueData));			
					else if( AppDefs.DEF_TIPOCAMPO_DOUBLE.equalsIgnoreCase(o.getDescricao()) )
						result.setDblVal(StringUtil.safeDbl(nf6, valueData));						
					else if( AppDefs.DEF_TIPOCAMPO_STRING.equalsIgnoreCase(o.getDescricao()) )
						result.setStrVal(valueData);
					else if( AppDefs.DEF_TIPOCAMPO_DATE.equalsIgnoreCase(o.getDescricao()) )
						result.setDateVal(df.parse(valueData));			
					
				}
				
			}
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
		}
		
		return result;
	}
	
//Public
	
	public CadParedeExport()
	{
		Date dataHoraAtual = new Date();

		Date dataAtual = new Date(dataHoraAtual.getYear(), dataHoraAtual.getMonth(), dataHoraAtual.getDate());
	}
		
	// Replace Tags
	
	public String replaceTags(String paragraphIn, boolean bUseAlternate)
	{
		for(TagDataVO o : lsTagData)
		{
			if( bUseAlternate && (o.getAlternateTag() != null) )			
			{
				TagDataVO oTagData = o.getAlternateTag();

				paragraphIn = paragraphIn.replace(o.getTagName(), oTagData.getTagValue());
			}
			else
			{
				paragraphIn = paragraphIn.replace(o.getTagName(), o.getTagValue());				
			}
		}
		return paragraphIn;
	}	
		
	// Fill Tag List With Data
	
	public void buildTagDataList()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		DateFormat df1 = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		lsTagData = new ArrayList<TagDataVO>();
		int n = 1;

		int sz = this.lsItemData.size();
		for(int i = 0; i < sz; i++) {
			CadParede oEnt = this.lsItemData.get(i);

			GeomPoint3d ptI = oEnt.getPtI();
			GeomPoint3d ptF = oEnt.getPtF();			
			
			String strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_OBJECT_ID, i);			
			TagDataVO o = new TagDataVO(n++, strTag, oEnt.getObjectId());
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_OBJTYPE, i);			
			o = new TagDataVO(n++, strTag, oEnt.getObjType());
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_LAYERNAME, i);			
			CadLayerDef oLay = oEnt.getLayer();
			o = new TagDataVO(n++, strTag, oLay.getName());
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_TIPO, i);			
			o = new TagDataVO(n++, strTag, oEnt.getTipo());
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_ALTURA, i);
			o = new TagDataVO(n++, strTag, nf3.format(oEnt.getAltura()));
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_LARGURA, i);
			o = new TagDataVO(n++, strTag, nf3.format(oEnt.getLargura()));
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_XPTI, i);
			o = new TagDataVO(n++, strTag, nf3.format(ptI.getX()));
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_YPTI, i);
			o = new TagDataVO(n++, strTag, nf3.format(ptI.getY()));
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_ZPTI, i);
			o = new TagDataVO(n++, strTag, nf3.format(ptI.getZ()));
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_XPTF, i);
			o = new TagDataVO(n++, strTag, nf3.format(ptF.getX()));
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_YPTF, i);
			o = new TagDataVO(n++, strTag, nf3.format(ptF.getY()));
			lsTagData.add(o);
			
			strTag = String.format(AppDefs.DEF_TAG_CADPAREDE_ZPTF, i);
			o = new TagDataVO(n++, strTag, nf3.format(ptF.getZ()));
			lsTagData.add(o);
		}
	}
		
	/* Getters/Setters */

	public Hashtable<Integer, CadParede> getLsItemData() {
		return lsItemData;
	}

	public void setLsItemData(Hashtable<Integer, CadParede> lsItemData) {
		this.lsItemData = lsItemData;
	}

	public Hashtable<String, ItemDataVO> getLsTipoItemData() {
		return lsTipoItemData;
	}

	public void setLsTipoItemData(Hashtable<String, ItemDataVO> lsTipoItemData) {
		this.lsTipoItemData = lsTipoItemData;
	}

	public ArrayList<TagDataVO> getLsTagData() {
		return lsTagData;
	}

	public void setLsTagData(ArrayList<TagDataVO> lsTagData) {
		this.lsTagData = lsTagData;
	}

}
