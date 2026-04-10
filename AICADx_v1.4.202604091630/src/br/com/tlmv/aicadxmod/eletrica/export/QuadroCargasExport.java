/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * QuadroCargasExport.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 15/01/2026
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

package br.com.tlmv.aicadxmod.eletrica.export;

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
import br.com.tlmv.aicadxmod.eletrica.cad.CadCircuitoQuadroCargasEletricaOData;
import br.com.tlmv.aicadxmod.eletrica.cad.CadQuadroCargasEletrica;

public class QuadroCargasExport implements IExportData
{
//Private
	private Hashtable<String, ItemDataVO> lsTipoItemData = null;
	
	private ArrayList<TagDataVO> lsTagData = null;

	private CadQuadroCargasEletrica oQuadroCargas = null;

	//
	// --- QUADRO_CARGAS
	//
	private String[] TAG_QUADRO_CARGAS = {	
		AppDefs.DEF_TAG_QDRCARGAS_NOME_QUADRO,
		AppDefs.DEF_TAG_QDRCARGAS_DESCRICAO_QUADRO,
		AppDefs.DEF_TAG_QDRCARGAS_TENSAO_QUADRO,
		AppDefs.DEF_TAG_QDRCARGAS_BITOLA_MINIMA_CONDUTOR,
		AppDefs.DEF_TAG_QDRCARGAS_TEMPERATURA_AMBIENTE,
		AppDefs.DEF_TAG_QDRCARGAS_FATOR_REDUCAO,
		AppDefs.DEF_TAG_QDRCARGAS_SISTEMA_FASE,
		AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_ILUMINACAO,
		AppDefs.DEF_TAG_QDRCARGAS_CARGAS_ILUMINACAO,
		AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_TOMADA,
		AppDefs.DEF_TAG_QDRCARGAS_CARGAS_TOMADA,
		AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_MOTOR,
		AppDefs.DEF_TAG_QDRCARGAS_CARGAS_MOTOR,
		AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_OUTROS,
		AppDefs.DEF_TAG_QDRCARGAS_CARGAS_OUTROS,
		AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_PAINEIS,
		AppDefs.DEF_TAG_QDRCARGAS_CARGAS_PAINEIS,
		AppDefs.DEF_TAG_QDRCARGAS_POTENCIA_SEM_RESERVA,
		AppDefs.DEF_TAG_QDRCARGAS_POTENCIA,
		AppDefs.DEF_TAG_QDRCARGAS_ALIMENTADOR,
		AppDefs.DEF_TAG_QDRCARGAS_ALIMENTADOR_PROTECAO,
		AppDefs.DEF_TAG_QDRCARGAS_DISJUNTOR,
		AppDefs.DEF_TAG_QDRCARGAS_FASE
	};

	//
	// --- QUADRO_CARGAS_ITEM
	//
	private String[] TAG_QUADRO_CARGAS_ITEM = {	
		AppDefs.DEF_TAG_QDRCARGASITEM_ROWID,
		AppDefs.DEF_TAG_QDRCARGASITEM_NOME_QUADRO,
		AppDefs.DEF_TAG_QDRCARGASITEM_NUMERO_CIRCUITO,
		AppDefs.DEF_TAG_QDRCARGASITEM_DESCR_CIRCUITO,
		AppDefs.DEF_TAG_QDRCARGASITEM_SISTEMA_FASE,
		AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_ILUMINACAO,
		AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_ILUMINACAO,
		AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_TOMADA,
		AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_TOMADA,
		AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_MOTOR,
		AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_MOTOR,
		AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_RAIOX,
		AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_RAIOX,
		AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_AQUECIMENTO,
		AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_AQUECIMENTO,
		AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_OUTROS,
		AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_OUTROS,
		AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_PAINEIS,
		AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_PAINEIS,	
		AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_PAINEIS,
		AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_PAINEIS,	
		AppDefs.DEF_TAG_QDRCARGASITEM_ALIMENTADOR,
		AppDefs.DEF_TAG_QDRCARGASITEM_ALIMENTADOR_PROTECAO,
		AppDefs.DEF_TAG_QDRCARGASITEM_DISJUNTOR,
		AppDefs.DEF_TAG_QDRCARGASITEM_FASE
	};
	
	private void buildFieldTypeList()
	{
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);

		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		//ArrayList: NomeCampo - TipoCampo		
		// --- QUADRO_CARGAS
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_NOME_QUADRO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_NOME_QUADRO, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_DESCRICAO_QUADRO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_DESCRICAO_QUADRO, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_TENSAO_QUADRO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_TENSAO_QUADRO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_BITOLA_MINIMA_CONDUTOR, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_BITOLA_MINIMA_CONDUTOR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_TEMPERATURA_AMBIENTE, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_TEMPERATURA_AMBIENTE, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_FATOR_REDUCAO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_FATOR_REDUCAO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_SISTEMA_FASE, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_SISTEMA_FASE, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_ILUMINACAO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_ILUMINACAO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_ILUMINACAO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_ILUMINACAO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_TOMADA, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_TOMADA, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_TOMADA, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_TOMADA, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_MOTOR, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_MOTOR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_MOTOR, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_MOTOR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_OUTROS, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_OUTROS, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_OUTROS, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_OUTROS, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_PAINEIS, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_PAINEIS, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_PAINEIS, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_CARGAS_PAINEIS, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_POTENCIA_SEM_RESERVA, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_POTENCIA_SEM_RESERVA, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_POTENCIA, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_POTENCIA, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_ALIMENTADOR, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_ALIMENTADOR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_ALIMENTADOR_PROTECAO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_ALIMENTADOR_PROTECAO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_DISJUNTOR, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_DISJUNTOR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGAS_FASE, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGAS_FASE, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		
		//ArrayList: NomeCampo - TipoCampo		
		// --- QUADRO_CARGAS_ITEM
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_ROWID, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_ROWID, AppDefs.DEF_TIPOCAMPO_INT));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_NOME_QUADRO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_NOME_QUADRO, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_NUMERO_CIRCUITO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_NUMERO_CIRCUITO, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_DESCR_CIRCUITO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_DESCR_CIRCUITO, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_SISTEMA_FASE, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_SISTEMA_FASE, AppDefs.DEF_TIPOCAMPO_STRING));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_ILUMINACAO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_ILUMINACAO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_ILUMINACAO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_ILUMINACAO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_TOMADA, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_TOMADA, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_TOMADA, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_TOMADA, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_MOTOR, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_MOTOR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_MOTOR, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_MOTOR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_RAIOX, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_RAIOX, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_RAIOX, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_RAIOX, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_AQUECIMENTO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_AQUECIMENTO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_AQUECIMENTO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_AQUECIMENTO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_OUTROS, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_OUTROS, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_OUTROS, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_OUTROS, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_PAINEIS, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_PAINEIS, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_PAINEIS, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_PAINEIS, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_CIRCUITO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_CIRCUITO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_CIRCUITO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_CIRCUITO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_ALIMENTADOR, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_ALIMENTADOR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_ALIMENTADOR_PROTECAO, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_ALIMENTADOR_PROTECAO, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_DISJUNTOR, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_DISJUNTOR, AppDefs.DEF_TIPOCAMPO_DOUBLE));
		this.lsTipoItemData.put(AppDefs.DEF_TAG_QDRCARGASITEM_FASE, new ItemDataVO(AppDefs.DEF_TAG_QDRCARGASITEM_FASE, AppDefs.DEF_TIPOCAMPO_DOUBLE));
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
	
	public QuadroCargasExport(CadQuadroCargasEletrica oQuadroCargas)
	{
		this.lsTipoItemData = new Hashtable<String, ItemDataVO>();
		
		this.lsTagData = new ArrayList<TagDataVO>();

		this.oQuadroCargas = oQuadroCargas;
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

		NumberFormat nf1 = FormatUtil.newNumberFormatWithoutGroupingPtBr(1);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		DateFormat df1 = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		this.lsTagData = new ArrayList<TagDataVO>();
		int n = 1;
		
		TagDataVO o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_NOME_QUADRO, this.oQuadroCargas.getNomeQuadro());
		this.lsTagData.add(o);

		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_DESCRICAO_QUADRO, this.oQuadroCargas.getDescricaoQuadro());
		this.lsTagData.add(o);

		String str = nf1.format(this.oQuadroCargas.getBitolaMinimaCondutor());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_BITOLA_MINIMA_CONDUTOR, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getTemperaturaAmbiente());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_TEMPERATURA_AMBIENTE, str);
		this.lsTagData.add(o);

		str = nf3.format(this.oQuadroCargas.getFatorReducao());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_FATOR_REDUCAO, str);
		this.lsTagData.add(o);

		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_SISTEMA_FASE, this.oQuadroCargas.getSistemaFase());
		this.lsTagData.add(o);
		
		str = nf0.format(this.oQuadroCargas.getQtdCargasIluminacaoQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_ILUMINACAO, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getCargasIluminacaoQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_CARGAS_ILUMINACAO, str);
		this.lsTagData.add(o);
		
		str = nf0.format(this.oQuadroCargas.getQtdCargasTomadaQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_TOMADA, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getCargasTomadaQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_CARGAS_TOMADA, str);
		this.lsTagData.add(o);
		
		str = nf0.format(this.oQuadroCargas.getQtdCargasMotorQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_MOTOR, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getCargasMotorQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_CARGAS_MOTOR, str);
		this.lsTagData.add(o);
		
		str = nf0.format(this.oQuadroCargas.getQtdCargasOutrosQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_OUTROS, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getCargasOutrosQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_CARGAS_OUTROS, str);
		this.lsTagData.add(o);

		str = nf0.format(this.oQuadroCargas.getQtdCargasPaineisQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_QTD_CARGAS_PAINEIS, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getCargasPaineisQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_CARGAS_PAINEIS, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getPotenciaSemReservaQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_POTENCIA_SEM_RESERVA, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getPotenciaQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_POTENCIA, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getAlimentadorQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_ALIMENTADOR, str);
		this.lsTagData.add(o);

		str = nf1.format(this.oQuadroCargas.getAlimentadorProtecaoQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_ALIMENTADOR_PROTECAO, str);
		this.lsTagData.add(o);

		str = nf0.format(this.oQuadroCargas.getDisjuntorQuadro());
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_DISJUNTOR, str);
		this.lsTagData.add(o);
		
		o = new TagDataVO(n++, AppDefs.DEF_TAG_QDRCARGAS_FASE, this.oQuadroCargas.getFaseQuadro());
		this.lsTagData.add(o);
		
		ArrayList<CadCircuitoQuadroCargasEletricaOData> lsItemData = this.oQuadroCargas.getLsItem();
		int sz = lsItemData.size();
		for(int i = 0; i < sz; i++) {
			CadCircuitoQuadroCargasEletricaOData oItemData = lsItemData.get(i);
			
			String strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_ROWID, i);			
			o = new TagDataVO(n++, strTag, oItemData.getRowId());
			this.lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_NOME_QUADRO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getNomeQuadro());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_NUMERO_CIRCUITO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getNumeroCircuito());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_DESCR_CIRCUITO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getDescricaoCircuito());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_SISTEMA_FASE, i);			
			o = new TagDataVO(n++, strTag, oItemData.getSistemaFase());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_ILUMINACAO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getQtdCargaIluminacao());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_ILUMINACAO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getCargaIluminacao());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_TOMADA, i);			
			o = new TagDataVO(n++, strTag, oItemData.getQtdCargaTomada());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_TOMADA, i);			
			o = new TagDataVO(n++, strTag, oItemData.getCargaTomada());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_MOTOR, i);			
			o = new TagDataVO(n++, strTag, oItemData.getQtdCargaMotor());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_MOTOR, i);			
			o = new TagDataVO(n++, strTag, oItemData.getCargaMotor());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_RAIOX, i);			
			o = new TagDataVO(n++, strTag, oItemData.getQtdCargaRaioX());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_RAIOX, i);			
			o = new TagDataVO(n++, strTag, oItemData.getCargaRaioX());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_AQUECIMENTO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getQtdCargaAquecimento());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_AQUECIMENTO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getCargaAquecimento());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_OUTROS, i);			
			o = new TagDataVO(n++, strTag, oItemData.getQtdCargaOutra());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_OUTROS, i);			
			o = new TagDataVO(n++, strTag, oItemData.getCargaOutra());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_PAINEIS, i);			
			o = new TagDataVO(n++, strTag, oItemData.getQtdCargaPainel());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_PAINEIS, i);			
			o = new TagDataVO(n++, strTag, oItemData.getCargaPainel());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_QTD_CARGAS_CIRCUITO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getQtdCargaCircuito());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_CARGAS_CIRCUITO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getCargaCircuito());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_ALIMENTADOR, i);			
			o = new TagDataVO(n++, strTag, oItemData.getAlimentadorCircuito());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_ALIMENTADOR_PROTECAO, i);			
			o = new TagDataVO(n++, strTag, oItemData.getAlimentadorProtecaoCircuito());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_DISJUNTOR, i);			
			o = new TagDataVO(n++, strTag, oItemData.getDisjuntorCircuito());
			lsTagData.add(o);

			strTag = String.format(AppDefs.DEF_TAG_QDRCARGASITEM_FASE, i);			
			o = new TagDataVO(n++, strTag, oItemData.getFaseCircuito());
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

	public CadQuadroCargasEletrica getQuadroCargas() {
		return oQuadroCargas;
	}

	public void setQuadroCargas(CadQuadroCargasEletrica oQuadroCargas) {
		this.oQuadroCargas = oQuadroCargas;
	}

}
