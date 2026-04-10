/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadAcabamentoPisoDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 19/02/2025
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

package br.com.tlmv.aicadxmod.arquitetura.cad;

import java.awt.Color;
import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class CadAcabamentoPisoDef extends CadAcabamentoDef 
{
//Public
    private String nome;
    private String descricao;
    private int tipo;
    private double largura; 
    private Color color; 
	
//Public

	public CadAcabamentoPisoDef(
		CadDocumentDef doc,	
		String nome,
		String descricao,
	    int tipo,
	    double largura,
	    Color color,
	    CadLayerDef oLayer) 
	{
		super(AppDefs.OBJTYPE_BIMACABAMENTOPISO_DEF, doc, oLayer);
		
		this.init(
			nome,
			descricao,
		    tipo,
		    largura,
		    color); 
	}

	public CadAcabamentoPisoDef(CadAcabamentoPisoDef o)
	{
		super(AppDefs.OBJTYPE_BIMACABAMENTOPISO_DEF, o.getDocument(), o.getLayer());
		
		this.init(
			nome,
			descricao,
		    tipo,
		    largura,
		    color); 
	}

	/* Methodes */
	
	private void init(
		String nome,
		String descricao,
	    int tipo,
	    double largura,
	    Color color) 
	{
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
		this.largura = largura;
		this.color = color;
	}
	
	private void init(CadAcabamentoPisoDef o)
	{
		this.nome = o.nome;
		this.descricao = o.descricao;
		this.tipo = o.tipo;
		this.largura = o.largura;
		this.color = o.color;
	}

	/* CREATE */
	
	public static CadAcabamentoPisoDef create(
		CadDocumentDef doc,
		String nome,
		String descricao,
	    int tipo,
	    double largura,
	    Color color,
	    CadLayerDef oLayer) 
	{
		CadAcabamentoPisoDef o = new CadAcabamentoPisoDef(
			doc,
			nome,
			descricao,
		    tipo,
		    largura,
		    color,
		    oLayer); 
		return o;
	}
	
	public static CadAcabamentoPisoDef create(CadAcabamentoPisoDef o) 
	{
		CadAcabamentoPisoDef oNew = new CadAcabamentoPisoDef(o);
		return oNew;
	}
	
	/* DEBUG */
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
		
		float[] arrRGB = this.color.getRGBColorComponents(null);
		
		String str = String.format(
			"Nome:%s;" +
			"descricao:%s;" +
			"tipo:%s;" +
			"largura:%s;",
			"color:rgb(%s,%s,%s);",
			this.nome,
			this.descricao,
			this.tipo,
			this.largura,
			nf0.format(arrRGB[0]),
			nf0.format(arrRGB[1]),
			nf0.format(arrRGB[2]) );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

	/* Getters/Setters */
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getLargura() {
		return largura;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
