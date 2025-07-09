/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BasePointRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.utils.DbUtil;

public class BasePointRecord 
{
//Private
    private long oid;
    private int cadRefEntityId;
    private double ptX;
    private double ptY;
    private double ptZ;
    
//Public
	
	public BasePointRecord()
	{
		this.init(
			AppDefs.NULL_LNG, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL);
	}
	
	public BasePointRecord(
		int cadRefEntityId,
		GeomPoint3d oPt)
	{
		double ptX = oPt.getX();
		double ptY = oPt.getY();
		double ptZ = oPt.getZ();
		
		this.init(
			-1, 
			cadRefEntityId, 
			ptX,
			ptY,
			ptZ);
	}
	
	/* Methodes */

	public void init(
		long oid,
	    int cadRefEntityId,
	    double ptX,
	    double ptY,
	    double ptZ)
	{
	    this.cadRefEntityId = cadRefEntityId;
	    this.ptX = ptX;
	    this.ptY = ptY;
	    this.ptZ = ptZ;
	}
	
	public void init(ResultSet rs)
	{
		DbUtil o = new DbUtil(rs);

		this.setOid( o.getNextLng() );
		this.setCadRefEntityId( o.getNextInt() );
		this.setPtX( o.getNextDbl() );
		this.setPtY( o.getNextDbl() );
		this.setPtZ( o.getNextDbl() );
	}
	
	/* TO_GEOMxxx */

	public GeomPoint3d toGeomPoint3d() {
		GeomPoint3d oPt = new GeomPoint3d(
			this.ptX, 
			this.ptY, 
			this.ptZ );
	    return oPt;
	}
	
	/* Getters/Setters */

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public int getCadRefEntityId() {
		return cadRefEntityId;
	}

	public void setCadRefEntityId(int cadRefEntityId) {
		this.cadRefEntityId = cadRefEntityId;
	}

	public double getPtX() {
		return ptX;
	}

	public void setPtX(double ptX) {
		this.ptX = ptX;
	}

	public double getPtY() {
		return ptY;
	}

	public void setPtY(double ptY) {
		this.ptY = ptY;
	}

	public double getPtZ() {
		return ptZ;
	}

	public void setPtZ(double ptZ) {
		this.ptZ = ptZ;
	}

}
