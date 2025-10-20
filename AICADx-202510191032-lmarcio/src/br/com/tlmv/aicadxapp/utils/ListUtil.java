/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ListUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

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

	//ListUtil: Array Type
	
	public static ItemDataVO findItemDataById(String itemDataId, ItemDataVO[] arr)
	{
		int sz = arr.length;
		for(int i = 0; i < sz; i++)
		{
			ItemDataVO oItem = arr[i];
			if( itemDataId.equalsIgnoreCase(oItem.getItemDataId()) )
				return oItem;
		}
		return null;
	}

	public static ItemDataVO findItemDataByDescricao(String descricao, ItemDataVO[] arr)
	{
		int sz = arr.length;
		for(int i = 0; i < sz; i++)
		{
			ItemDataVO oItem = arr[i];
			if( descricao.equalsIgnoreCase(oItem.getItemDataId()) )
				return oItem;
		}
		return null;
	}

	//ListUtil: GeomPoint3d
	
	public static String toStrArray(ArrayList<GeomPoint3d> lsPts)
	{
		StringBuilder sb = new StringBuilder();
		for(GeomPoint3d oPt3d : lsPts) {
			String str = oPt3d.toStr();
			
			if(sb.length() > 0)
				sb.append(";");
			sb.append(str);
		}
		return sb.toString();
	}
	
	//ListUtil: Object
	
	public static Object[] toArray(ArrayList<Object> lsObj)
	{
		int szArr = lsObj.size();
		Object[] arr = new Object[szArr]; 
		for(int k = 0; k < szArr; k++) {
			Object obj = (Object)lsObj.get(k);
			arr[k] = obj;
		}
		return arr;
	}
		
}
