/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ICadObject.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;

public interface ICadObject
{
//Public
	
	/* Methodes */
	
	public void init(int objType, CadBlockDef blkDef);
	
	public void reset();
	
	/* DEBUG */
	
	public String toStr();
	
	public void debug(int debugLevel);
	
	/* LOAD/SAVE */
	
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc);

	public void save(AppDatabase db, String schemaName, CadDocumentDef doc);
	
	/* READ/WRITE DXF R12 */
	
	public void fromDxfR12(DxfCadEntity o);
	
	public ArrayList<DxfCadEntity> toDxfR12();
	
	public ArrayList<DxfCadEntity> toDxfR12_view2d();
	
	public ArrayList<DxfCadEntity> toDxfR12_view3d();
	
	/* Getters/Setters */
	
	public int getObjectId();
	
	public void setObjectId(int objectId);
		
	public CadBlockDef getBlkDef();

	public void setBlkDef(CadBlockDef blkDef);
	
	public int getObjType();
	
	public void setObjType(int objType);
	
	public String getObjTypeStr();

	public void setObjTypeStr(String objTypeStr);

	public boolean isEntityObject();
	
	public void setEntityObject(boolean bEntityObject);

	public boolean isDeleted();

	public void setDeleted(boolean bDeleted);
	
}
