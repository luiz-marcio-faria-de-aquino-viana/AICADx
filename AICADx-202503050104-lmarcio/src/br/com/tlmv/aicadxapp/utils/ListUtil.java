/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ListUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.util.ArrayList;

public class ListUtil 
{
//Public
	
	//ListUtil: String Type

	public static int findPosInList(ArrayList<String> lsStr, String inStr)
	{
		for(int i = 0; i < lsStr.size(); i++)
		{
			String str = lsStr.get(i);
			
			if(inStr.compareToIgnoreCase(str) == 0)
				return i;
		}
		return -1;
	}
	
	public static int findPosInList(String[] lsStr, String inStr)
	{
		for(int i = 0; i < lsStr.length; i++)
		{
			String str = lsStr[i];
			
			if(inStr.compareToIgnoreCase(str) == 0)
				return i;
		}
		return -1;
	}

}
