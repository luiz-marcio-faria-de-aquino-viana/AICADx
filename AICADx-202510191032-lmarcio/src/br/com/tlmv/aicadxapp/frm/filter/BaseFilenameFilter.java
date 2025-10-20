/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
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
