/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * EntSelVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class EntSelVO 
{
//Private
	private CadEntity ent1 = null;
	private GeomPoint2d ptIns2d = null;	
	
//Public
	
	public EntSelVO(
		CadEntity ent1,
		GeomPoint2d ptIns2d)
	{
		this.ent1 = ent1;
		this.ptIns2d = ptIns2d;
	}

	/* Methodes */
	
	public String toString()
	{
		int objectId = AppDefs.NULL_INT;
		if(this.ent1 != null) {
			objectId = this.ent1.getObjectId();
		}

		GeomPoint2d ptIns = new GeomPoint2d(0.0, 0.0);
		if(this.ptIns2d != null) {
			ptIns = new GeomPoint2d(ptIns2d);			
		}
		
		String str = String.format(
			"Id Objeto: %s; Ponto Insercao: %s; ", 
		 	Integer.toString( objectId ),
		 	ptIns.toStr() );		
		return str;
	}
	
	/* Getters/Setters */

	public CadEntity getEnt1() {
		return ent1;
	}

	public void setEnt1(CadEntity ent1) {
		this.ent1 = ent1;
	}

	public GeomPoint2d getPtIns2d() {
		return ptIns2d;
	}

	public void setPtIns2d(GeomPoint2d ptIns2d) {
		this.ptIns2d = ptIns2d;
	}	
	
}
