/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmpCadLevel.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/11/2025
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

package br.com.tlmv.aicadxapp.cmp;

import java.util.Comparator;

import br.com.tlmv.aicadxapp.cad.CadLevel;

public class CmpCadLevel implements Comparator<CadLevel>
{
//Private
	private boolean bAsc = true;

	/* Methodes */

	public int compareAsc(CadLevel o1, CadLevel o2) 
	{
		String layer1 = o1.getLevelLocalText();
		String layer2 = o2.getLevelLocalText();
		
		int rscode = layer1.compareToIgnoreCase(layer2);
		return rscode;
	}

	public int compareDesc(CadLevel o1, CadLevel o2) 
	{
		String layer1 = o1.getLevelLocalText();
		String layer2 = o2.getLevelLocalText();
		
		int rscode = - layer1.compareToIgnoreCase(layer2);
		return rscode;
	}
	
//Public

	public CmpCadLevel()
	{
		this.bAsc = true;
	}

	public CmpCadLevel(boolean bAsc)
	{
		this.bAsc = bAsc;
	}
	
	/* Methodes */
	
	@Override
	public int compare(CadLevel o1, CadLevel o2) 
	{
		int rscode = 0;
		
		if( this.bAsc )
			rscode = this.compareAsc(o1, o2);
		else
			rscode = this.compareDesc(o1, o2);
		return rscode;
	}

}
