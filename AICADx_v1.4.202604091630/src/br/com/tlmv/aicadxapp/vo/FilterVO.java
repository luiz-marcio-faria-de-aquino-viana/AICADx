/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * FilterVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/01/2026
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

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.cad.CadEntity;

public class FilterVO extends ItemDataVO
{
//Private
	private String name;
	private String descricao;
	private ColorVO oColor;
	private BorderStrokeVO oStroke;
	private ArrayList<CadEntity> lsEntities = null;
	private Hashtable map = null;
	
//Public
	
	public FilterVO(
		String name,
		String descricao,
		ColorVO oColor,
		BorderStrokeVO oStroke)
	{
		super(name, descricao);
		
		this.name = name;
		this.descricao = descricao;
		this.oColor = oColor;
		this.oStroke = oStroke;
		
		this.lsEntities = new ArrayList<CadEntity>();
		this.map = new Hashtable();
	}

	/* Methodes */

	public synchronized int getSzLsEntities()
	{
		int sz = this.lsEntities.size();
		return sz;
	}

	public synchronized void addEntity(CadEntity ent)
	{
		Integer objectId = ent.getObjectId();
		
		this.lsEntities.add(ent);
		this.map.put(objectId, ent);
	}

	public synchronized CadEntity getEntityAt(int pos)
	{
		int sz = this.lsEntities.size();

		if( (pos >= 0) && (pos < sz) ) {
			CadEntity oEnt = this.lsEntities.get(pos);
			return oEnt;
		}
		return null;
	}

	public synchronized CadEntity getEntity(Integer objectId)
	{
		if( this.map.containsKey(objectId) ) {
			CadEntity oEnt = (CadEntity)this.map.get(objectId);
			return oEnt;
		}
		return null;
	}
	
	/* DEBUG */
	
	public String toString()
	{
		return this.descricao;
	}

	/* Getters/Setters */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ColorVO getColor() {
		return oColor;
	}

	public void setColor(ColorVO oColor) {
		this.oColor = oColor;
	}

	public BorderStrokeVO getStroke() {
		return oStroke;
	}

	public void setStroke(BorderStrokeVO oStroke) {
		this.oStroke = oStroke;
	}
			
}
