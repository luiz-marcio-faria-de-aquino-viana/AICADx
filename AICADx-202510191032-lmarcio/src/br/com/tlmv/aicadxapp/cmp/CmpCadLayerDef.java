/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmpCadLayerDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmp;

import java.util.Comparator;

import br.com.tlmv.aicadxapp.cad.CadLayerDef;

public class CmpCadLayerDef implements Comparator<CadLayerDef>
{
//Private
	private boolean bAsc = true;

	/* Methodes */

	public int compareAsc(CadLayerDef o1, CadLayerDef o2) 
	{
		String layer1 = o1.getName();
		String layer2 = o2.getName();
		
		int rscode = layer1.compareToIgnoreCase(layer2);
		return rscode;
	}

	public int compareDesc(CadLayerDef o1, CadLayerDef o2) 
	{
		String layer1 = o1.getName();
		String layer2 = o2.getName();
		
		int rscode = - layer1.compareToIgnoreCase(layer2);
		return rscode;
	}
	
//Public

	public CmpCadLayerDef(boolean bAsc)
	{
		this.bAsc = bAsc;
	}
	
	/* Methodes */
	
	@Override
	public int compare(CadLayerDef o1, CadLayerDef o2) 
	{
		int rscode = 0;
		
		if( this.bAsc )
			rscode = this.compareAsc(o1, o2);
		else
			rscode = this.compareDesc(o1, o2);
		return rscode;
	}

}
