/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * MemoriaCalculoExport.java
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

package br.com.tlmv.aicadxmod.drenagem.export;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.export.IExportData;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.TagDataVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoItemDrenagemOData;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoDrenagem;

public class MemoriaCalculoExport implements IExportData
{
//Private
	private Hashtable<String, ItemDataVO> lsTipoItemData = null;
	
	private ArrayList<TagDataVO> lsTagData = null;

	private CadMemoriaCalculoDrenagem oMemoriaCalculo = null;

	//
	// --- MEMORIA_CALCULO
	//
	private String[] TAG_MEMORIA_CALCULO = {	
		AppDefs.DEF_TAG_MEMCALC_PROJETO,
		AppDefs.DEF_TAG_MEMCALC_DATA_EMISSAO,
		AppDefs.DEF_TAG_MEMCALC_PLUVIOGRAFO,
		AppDefs.DEF_TAG_MEMCALC_COEF_MANNING,
		AppDefs.DEF_TAG_MEMCALC_TR
	};

	//
	// --- MEMORIA_CALCULO_ITEM
	//
	private String[] TAG_MEMORIA_CALCULO_ITEM = {	
		AppDefs.DEF_TAG_MEMCALCITEM_ROWID,
		AppDefs.DEF_TAG_MEMCALCITEM_PV,
		AppDefs.DEF_TAG_MEMCALCITEM_LOCAL,
		AppDefs.DEF_TAG_MEMCALCITEM_ESTACA,
		AppDefs.DEF_TAG_MEMCALCITEM_COTA_TERRENO,		
		AppDefs.DEF_TAG_MEMCALCITEM_COTA_FUNDO,		
		AppDefs.DEF_TAG_MEMCALCITEM_NIVEL_AGUA,		
		AppDefs.DEF_TAG_MEMCALCITEM_AREA_EXTERNA,		
		AppDefs.DEF_TAG_MEMCALCITEM_AREA_LOCAL,		
		AppDefs.DEF_TAG_MEMCALCITEM_AREA_TOTAL,		
		AppDefs.DEF_TAG_MEMCALCITEM_AREA_TOTAL_IMP,		
		AppDefs.DEF_TAG_MEMCALCITEM_COEF,		
		AppDefs.DEF_TAG_MEMCALCITEM_COEF_DISTR,		
		AppDefs.DEF_TAG_MEMCALCITEM_TEMPO_CONC,		
		AppDefs.DEF_TAG_MEMCALCITEM_INDICE_PLUVIOMETRICO,		
		AppDefs.DEF_TAG_MEMCALCITEM_COEF_DEFLUV,		
		AppDefs.DEF_TAG_MEMCALCITEM_DEFL_LOCAL,		
		AppDefs.DEF_TAG_MEMCALCITEM_DEFL_ESCOAR,		
		AppDefs.DEF_TAG_MEMCALCITEM_DECLIVIDADE_GREIDE,		
		AppDefs.DEF_TAG_MEMCALCITEM_DECLIVIDADE,		
		AppDefs.DEF_TAG_MEMCALCITEM_DIMENSOES,		
		AppDefs.DEF_TAG_MEMCALCITEM_ALTURA_AGUA,		
		AppDefs.DEF_TAG_MEMCALCITEM_YD,		
		AppDefs.DEF_TAG_MEMCALCITEM_PROF_MONT_JUS,		
		AppDefs.DEF_TAG_MEMCALCITEM_VELOCIDADE,		
		AppDefs.DEF_TAG_MEMCALCITEM_COMPRIMENTO,		
		AppDefs.DEF_TAG_MEMCALCITEM_TEMPO,		
		AppDefs.DEF_TAG_MEMCALCITEM_TEMPO_TOTAL,		
		AppDefs.DEF_TAG_MEMCALCITEM_OBSERVACAO		
	};
	
	private void buildFieldTypeList()
	{
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);

		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		//ArrayList: NomeCampo - TipoCampo		
		// --- MEMORIA_CALCULO
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALC_PROJETO, new ItemDataVO(AppDefs.DEF_TAG_MEMCALC_PROJETO, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALC_DATA_EMISSAO, new ItemDataVO(AppDefs.DEF_TAG_MEMCALC_DATA_EMISSAO, AppDefs.DEF_TIPOCAMPO_DATE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALC_PLUVIOGRAFO, new ItemDataVO(AppDefs.DEF_TAG_MEMCALC_PLUVIOGRAFO, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALC_COEF_MANNING, new ItemDataVO(AppDefs.DEF_TAG_MEMCALC_COEF_MANNING, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALC_TR, new ItemDataVO(AppDefs.DEF_TAG_MEMCALC_TR, AppDefs.DEF_TIPOCAMPO_DOUBLE));

		//ArrayList: NomeCampo - TipoCampo		
		// --- MEMORIA_CALCULO_ITEM
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_ROWID, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_ROWID, AppDefs.DEF_TIPOCAMPO_INT));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_PV, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_PV, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_LOCAL, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_LOCAL, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_ESTACA, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_ESTACA, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_COTA_TERRENO, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_COTA_TERRENO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_COTA_FUNDO, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_COTA_FUNDO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_NIVEL_AGUA, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_NIVEL_AGUA, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_AREA_EXTERNA, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_AREA_EXTERNA, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_AREA_LOCAL, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_AREA_LOCAL, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_AREA_TOTAL, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_AREA_TOTAL, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_AREA_TOTAL_IMP, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_AREA_TOTAL_IMP, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_COEF, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_COEF, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_COEF_DISTR, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_COEF_DISTR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_TEMPO_CONC, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_TEMPO_CONC, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_INDICE_PLUVIOMETRICO, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_INDICE_PLUVIOMETRICO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_COEF_DEFLUV, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_COEF_DEFLUV, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_DEFL_LOCAL, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_DEFL_LOCAL, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_DEFL_ESCOAR, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_DEFL_ESCOAR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_DECLIVIDADE_GREIDE, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_DECLIVIDADE_GREIDE, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_DECLIVIDADE, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_DECLIVIDADE, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_DIMENSOES, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_DIMENSOES, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_ALTURA_AGUA, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_ALTURA_AGUA, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_YD, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_YD, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_PROF_MONT_JUS, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_PROF_MONT_JUS, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_VELOCIDADE, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_VELOCIDADE, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_COMPRIMENTO, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_COMPRIMENTO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_TEMPO, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_TEMPO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_TEMPO_TOTAL, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_TEMPO_TOTAL, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_MEMCALCITEM_OBSERVACAO, new ItemDataVO(AppDefs.DEF_TAG_MEMCALCITEM_OBSERVACAO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
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
	
	public MemoriaCalculoExport(CadMemoriaCalculoDrenagem oMemoriaCalculo)
	{
		this.lsTipoItemData = new Hashtable<String, ItemDataVO>();
		
		this.lsTagData = new ArrayList<TagDataVO>();

		this.oMemoriaCalculo = oMemoriaCalculo;
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

		this.lsTagData = new ArrayList<TagDataVO>();
		int n = 1;

		TagDataVO o = new TagDataVO(n++, AppDefs.DEF_TAG_MEMCALC_PROJETO, this.oMemoriaCalculo.getNomeProjeto());
		this.lsTagData.add(o);

		o = new TagDataVO(n++, AppDefs.DEF_TAG_MEMCALC_PLUVIOGRAFO, this.oMemoriaCalculo.getPluviografo());
		this.lsTagData.add(o);

		String strDataEmissao = df1.format(oMemoriaCalculo.getDataEmissao());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_MEMCALC_DATA_EMISSAO, strDataEmissao);
		this.lsTagData.add(o);

		o = new TagDataVO(n++, AppDefs.DEF_TAG_MEMCALC_COEF_MANNING, this.oMemoriaCalculo.getCoefManning());
		this.lsTagData.add(o);

		o = new TagDataVO(n++, AppDefs.DEF_TAG_MEMCALC_TR, this.oMemoriaCalculo.getPeriodoRecorrencia());
		this.lsTagData.add(o);
		
		ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItemData = this.oMemoriaCalculo.getLsItem();
		int sz = lsItemData.size();
		for(int i = 0; i < sz; i++) {
			CadMemoriaCalculoItemDrenagemOData oItemData = lsItemData.get(i);

			String strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_ROWID, i);			
			o = new TagDataVO(n++, strTag, oItemData.getRowId());
			this.lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_PV, i);			
			o = new TagDataVO(n++, strTag, oItemData.getPv());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_LOCAL, i);			
			o = new TagDataVO(n++, strTag, oItemData.getLocal());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_ESTACA, i);			
			o = new TagDataVO(n++, strTag, oItemData.getEstaca());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_COTA_TERRENO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getCotaTerreno());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_COTA_FUNDO, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getFundo()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_NIVEL_AGUA, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getNivelAgua()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_AREA_EXTERNA, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getAreaExterna()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_AREA_LOCAL, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getAreaLocal()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_AREA_TOTAL, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getAreaTotal()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_AREA_TOTAL_IMP, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getAreaTotalImp()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_COEF, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getCoef()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_COEF_DISTR, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getCoefDistr()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_TEMPO_CONC, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getTempoConc()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_INDICE_PLUVIOMETRICO, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getIndicePluviometrico()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_COEF_DEFLUV, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getCoefDefluv()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_DEFL_LOCAL, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getDeflLocal()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_DEFL_ESCOAR, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getDeflEscoar()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_DECLIVIDADE_GREIDE, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getDeclividadeGreide()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_DECLIVIDADE, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getDeclividade()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_DIMENSOES, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getDimensoesMeter()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_ALTURA_AGUA, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getAlturaAgua()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_YD, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getYd()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_PROF_MONT_JUS, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getProfMontJus()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_VELOCIDADE, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getVelocidade()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_COMPRIMENTO, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getComprimento()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_TEMPO, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getTempoPercurso()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_TEMPO_TOTAL, i);			
			o = new TagDataVO(n++, strTag, nf3.format(oItemData.getTempoTotal()));
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_MEMCALCITEM_OBSERVACAO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getObservacao());
			lsTagData.add(o);
		}
	}
		
	/* Getters/Setters */

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

	public CadMemoriaCalculoDrenagem getoMemoriaCalculo() {
		return oMemoriaCalculo;
	}

	public void setoMemoriaCalculo(CadMemoriaCalculoDrenagem oMemoriaCalculo) {
		this.oMemoriaCalculo = oMemoriaCalculo;
	}

}
