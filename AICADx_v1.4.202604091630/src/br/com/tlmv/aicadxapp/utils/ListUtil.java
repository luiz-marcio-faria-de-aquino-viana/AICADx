/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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

package br.com.tlmv.aicadxapp.utils;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoItemDrenagemOData;

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
			if( descricao.equalsIgnoreCase(oItem.getDescricao()) )
				return oItem;
		}
		return null;
	}
	
	public static int findPosItemDataById(String itemDataId, ItemDataVO[] arr)
	{
		int sz = arr.length;
		for(int i = 0; i < sz; i++)
		{
			ItemDataVO oItem = arr[i];
			if( itemDataId.equalsIgnoreCase(oItem.getItemDataId()) )
				return i;
		}
		return -1;
	}

	public static int findPosItemDataByDescricao(String descricao, ItemDataVO[] arr)
	{
		int sz = arr.length;
		for(int i = 0; i < sz; i++)
		{
			ItemDataVO oItem = arr[i];
			if( descricao.equalsIgnoreCase(oItem.getDescricao()) )
				return i;
		}
		return -1;
	}

	//ListUtil: List Type
	
	public static ItemDataVO findItemDataById(String itemDataId, ArrayList ls)
	{
		int sz = ls.size();
		for(int i = 0; i < sz; i++)
		{
			ItemDataVO oItem = (ItemDataVO)ls.get(i);
			if( itemDataId.equalsIgnoreCase(oItem.getItemDataId()) )
				return oItem;
		}
		return null;
	}

	public static ItemDataVO findItemDataByDescricao(String descricao, ArrayList ls)
	{
		int sz = ls.size();
		for(int i = 0; i < sz; i++)
		{
			ItemDataVO oItem = (ItemDataVO)ls.get(i);
			if( descricao.equalsIgnoreCase(oItem.getDescricao()) )
				return oItem;
		}
		return null;
	}

	public static int findPosItemDataById(String itemDataId, ArrayList ls)
	{
		int sz = ls.size();
		for(int i = 0; i < sz; i++)
		{
			ItemDataVO oItem = (ItemDataVO)ls.get(i);
			if( itemDataId.equalsIgnoreCase(oItem.getItemDataId()) )
				return i;
		}
		return -1;
	}

	public static int findPosItemDataByDescricao(String descricao, ArrayList ls)
	{
		int sz = ls.size();
		for(int i = 0; i < sz; i++)
		{
			ItemDataVO oItem = (ItemDataVO)ls.get(i);
			if( descricao.equalsIgnoreCase(oItem.getDescricao()) )
				return i;
		}
		return -1;
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
	
	//ListUtil: CadMemoriaCalculoItemDrenagemOData
	
	public static CadMemoriaCalculoItemDrenagemOData findItemDataById(int objectId, ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItemData)	
	{
		int sz = lsItemData.size();
		for(int i = 0; i < sz; i++) {
			CadMemoriaCalculoItemDrenagemOData oItemData = lsItemData.get(i);
			if(objectId == oItemData.getObjectId()) {
				return oItemData;
			}
		}
		return null;
	}
	
	public static CadMemoriaCalculoItemDrenagemOData findItemDataBy(int objectId, CadMemoriaCalculoItemDrenagemOData[] lsItemData)	
	{
		int sz = lsItemData.length;
		for(int i = 0; i < sz; i++) {
			CadMemoriaCalculoItemDrenagemOData oItemData = lsItemData[i];
			if(objectId == oItemData.getObjectId()) {
				return oItemData;
			}
		}
		return null;
	}
		
}
