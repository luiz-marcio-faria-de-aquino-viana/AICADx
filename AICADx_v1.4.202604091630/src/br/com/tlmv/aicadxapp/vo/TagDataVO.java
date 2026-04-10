/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * TagDataVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 15/03/2025
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

package br.com.tlmv.aicadxapp.vo;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class TagDataVO 
{
//Private
	private int tagId;
	private String tagName;
	private String tagValue;
	private boolean hasHyperlink;
	private TagDataVO oAlternateTag;
	
//Public
	
	public TagDataVO()
	{
		this.tagId = -1;
		this.tagName = "";
		this.tagValue = "";
		this.hasHyperlink = false;
		this.oAlternateTag = null;
	}
	
	public TagDataVO(
		int tagId,
		String tagName,
		boolean bUseAllways)
	{
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = "";
		this.hasHyperlink = false;
		this.oAlternateTag = null;
	}
	
	public TagDataVO(
		int tagId,
		String tagName,
		String tagValue)
	{
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = tagValue;
		this.hasHyperlink = false;
		this.oAlternateTag = null;
	}
	
	public TagDataVO(
		int tagId,
		String tagName,
		double tagValue)
	{
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = nf2.format(tagValue);
		this.hasHyperlink = false;
		this.oAlternateTag = null;
	}
	
	public TagDataVO(
		int tagId,
		String tagName,
		double tagValue,
		NumberFormat nf)
	{
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = nf.format(tagValue);
		this.hasHyperlink = false;
		this.oAlternateTag = null;
	}
	
	public TagDataVO(
		int tagId,
		String tagName,
		int tagValue)
	{
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = Integer.toString(tagValue);
		this.hasHyperlink = false;
		this.oAlternateTag = null;
	}
	
	public TagDataVO(
		int tagId,
		String tagName,
		long tagValue)
	{
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = Long.toString(tagValue);
		this.hasHyperlink = false;
		this.oAlternateTag = null;
	}
	
	public TagDataVO(
		int tagId,
		String tagName,
		Date tagValue)
	{
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		this.tagId = tagId;
		this.tagName = tagName;
		this.tagValue = df.format(tagValue);
		this.hasHyperlink = false;
		this.oAlternateTag = null;
	}

	/* Gettes/Setters */
	
	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public void setTagValue(double tagValue) {
		NumberFormat nf2 = FormatUtil.newNumberFormatPtBr(2);
		
		this.tagValue = nf2.format(tagValue);
	}

	public void setTagValue(int tagValue) {
		this.tagValue = Integer.toString(tagValue);
	}

	public void setTagValue(Date tagValue) {
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);
		
		this.tagValue = df.format(tagValue);
	}

	public boolean isHasHyperlink() {
		return hasHyperlink;
	}

	public void setHasHyperlink(boolean hasHyperlink) {
		this.hasHyperlink = hasHyperlink;
	}

	public TagDataVO getAlternateTag() {
		return oAlternateTag;
	}

	public void setAlternateTag(TagDataVO oAlternateTag) {
		this.oAlternateTag = oAlternateTag;
	}

}
