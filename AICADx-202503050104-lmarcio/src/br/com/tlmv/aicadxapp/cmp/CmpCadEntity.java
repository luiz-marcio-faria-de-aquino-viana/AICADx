/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmpCadEntity3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmp;

import java.util.Comparator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBox3D;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadJanela;
import br.com.tlmv.aicadxapp.cad.CadPDupla;
import br.com.tlmv.aicadxapp.cad.CadParede;
import br.com.tlmv.aicadxapp.cad.CadPiso;
import br.com.tlmv.aicadxapp.cad.CadPorta;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;

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
			CadBox3D o = (CadBox3D)oEnt;
			
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
