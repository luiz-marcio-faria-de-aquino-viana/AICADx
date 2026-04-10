/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CmpCadEntity3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBox3d;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadJanela;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPDupla;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPiso;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadPorta;

public class CmpCadEntity implements Comparator<CadEntity>
{
//Private
	private GeomPoint3d ptBase3d = null;
	private boolean bAsc = true;

	/* Methodes */

	public double distToObserver(CadEntity oEnt) 
	{
		double dist = Double.MAX_VALUE;
		
		//CAD3Dxxx
		//
		if(oEnt.getObjType() == AppDefs.OBJTYPE_BOX3D) 
		{
			CadBox3d o = (CadBox3d)oEnt;
			
			GeomPoint3d ptCentroid = o.centroid();
			dist = ptCentroid.distTo(ptBase3d);
		}

		//BIMxxx
		//
		if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPISO) 
		{
			CadPiso o = (CadPiso)oEnt;
			
			GeomPoint3d ptCentroid = o.centroid();
			dist = ptCentroid.distTo(ptBase3d);
		}
		
		if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPAREDE) 
		{
			CadParede o = (CadParede)oEnt;
			
			GeomPoint3d ptCentroid = o.centroid();
			dist = ptCentroid.distTo(ptBase3d);
		}

		if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMJANELA) 
		{
			CadJanela o = (CadJanela)oEnt;
			
			GeomPoint3d ptCentroid = o.centroid();
			dist = ptCentroid.distTo(ptBase3d);
		}

		if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPORTA) 
		{
			CadPorta o = (CadPorta)oEnt;
			
			GeomPoint3d ptCentroid = o.centroid();
			dist = ptCentroid.distTo(ptBase3d);
		}
		
		if(oEnt.getObjType() == AppDefs.OBJTYPE_BIMPDUPLA) 
		{
			CadPDupla o = (CadPDupla)oEnt;
			
			GeomPoint3d ptCentroid = o.centroid();
			dist = ptCentroid.distTo(ptBase3d);
		}
		return dist;
	}
	
	public int compareAsc(CadEntity o1, CadEntity o2) 
	{
		double d1 = this.distToObserver(o1); 
		double d2 = this.distToObserver(o2); 
		
		int rscode = 0;
		if(d2 < d1)
			return 1;
		else if(d2 > d1)
			return -1;
		return rscode;
	}

	public int compareDesc(CadEntity o1, CadEntity o2) 
	{
		int rscode = - this.compareAsc(o1, o2);
		return rscode;
	}
	
//Public

	public CmpCadEntity(GeomPoint3d ptBase3d, boolean bAsc)
	{
		this.ptBase3d = new GeomPoint3d(ptBase3d);
		this.bAsc = bAsc;
	}
	
	/* Methodes */
	
	@Override
	public int compare(CadEntity o1, CadEntity o2) 
	{
		int rscode = 0;
		
		if( this.bAsc )
			rscode = this.compareAsc(o1, o2);
		else
			rscode = this.compareDesc(o1, o2);
		return rscode;
	}

}
