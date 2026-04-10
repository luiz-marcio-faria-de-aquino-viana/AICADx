/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * MultAreaContribuicaoDrenagemVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 15/11/2025
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

package br.com.tlmv.aicadxmod.drenagem.vo;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class MultAreaContribuicaoDrenagemVO 
{
//Private
	private PromptOptionVO oKeyword;
	private String strAreaPrefix;
	private int numeroCI;
	private CadEntity oCI;
	private int blkObjectId;
	private CadEntity oInsBlk;
	private String blockName;			
	private CadBlockDef oBlkDef;
	private ArrayList<CadEntity> lsLines;
	
//Public
	
	public MultAreaContribuicaoDrenagemVO(
		PromptOptionVO oKeyword,
		String strAreaPrefix,
		int numeroCI,
		CadEntity oCI,
		int blkObjectId,
		CadEntity oInsBlk,
		String blockName,	
		CadBlockDef blkDef,
		ArrayList<CadEntity> lsLines)
	{		
		this.oKeyword = oKeyword;
		this.strAreaPrefix = strAreaPrefix;
		this.numeroCI = numeroCI;
		this.oCI = oCI;
		this.blkObjectId = blkObjectId;
		this.oInsBlk = oInsBlk;
		this.blockName = blockName;	
		this.oBlkDef = blkDef;
		this.lsLines = lsLines;
	}
	
	public MultAreaContribuicaoDrenagemVO(MultAreaContribuicaoDrenagemVO o)
	{
		this.oKeyword = o.getKeyword();
		this.strAreaPrefix = o.getAreaPrefix();
		this.numeroCI = o.getNumeroCI();
		this.oCI = o.getCI();
		this.blkObjectId = o.getBlkObjectId();
		this.oInsBlk = o.getInsBlk();
		this.blockName = o.getBlockName();	
		this.oBlkDef = o.getBlkDef();
		this.lsLines = o.getLsLines();
	}

	/* Methodes */
	
	public String toString()
	{
		String str = String.format(
			"Tipo:%s;Nome:%s;",
			this.oKeyword.getTextOption(),
			this.strAreaPrefix );
		return str;
	}
	
	/* Getters/Setters */
		
	public PromptOptionVO getKeyword() {
		return oKeyword;
	}

	public void setKeyword(PromptOptionVO oKeyword) {
		this.oKeyword = oKeyword;
	}

	public int getNumeroCI() {
		return numeroCI;
	}

	public void setNumeroCI(int numeroCI) {
		this.numeroCI = numeroCI;
	}

	public CadEntity getCI() {
		return oCI;
	}

	public void setCI(CadEntity oCI) {
		this.oCI = oCI;
	}

	public int getBlkObjectId() {
		return blkObjectId;
	}

	public void setBlkObjectId(int blkObjectId) {
		this.blkObjectId = blkObjectId;
	}

	public CadEntity getInsBlk() {
		return oInsBlk;
	}

	public void setInsBlk(CadEntity oInsBlk) {
		this.oInsBlk = oInsBlk;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public ArrayList<CadEntity> getLsLines() {
		return lsLines;
	}

	public void setLsLines(ArrayList<CadEntity> lsLines) {
		this.lsLines = lsLines;
	}

	public String getAreaPrefix() {
		return strAreaPrefix;
	}

	public void setAreaPrefix(String strAreaPrefix) {
		this.strAreaPrefix = strAreaPrefix;
	}

	public CadBlockDef getBlkDef() {
		return oBlkDef;
	}

	public void setBlkDef(CadBlockDef oBlkDef) {
		this.oBlkDef = oBlkDef;
	}
	
}
