/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * BaseFilenameFilter.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 09/07/2025
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

package br.com.tlmv.aicadxapp.frm.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class BaseFilenameFilter implements FilenameFilter 
{
//Private
	private ArrayList<String> lsFileExt = null;
	
//Public
	
	public BaseFilenameFilter()
	{
		this.init(null);
	}
	
	public BaseFilenameFilter(String fileExt)
	{
		this.init(fileExt);
	}

	/* Methodes */
	
	public void init(String fileExt)
	{
		this.lsFileExt = new ArrayList<String>();
		this.addFileExt(fileExt);
	}
	
	public void addFileExt(String fileExt)
	{
		if(fileExt != null) {
			this.lsFileExt.add(fileExt);
		}
	}
	
	/* INTERFACE */
	
	@Override
	public boolean accept(File dir, String name) 
	{
		for(String fileExt : this.lsFileExt) {
			if( name.endsWith(fileExt) )
				return true;
		}		
		return false;
	}

}
