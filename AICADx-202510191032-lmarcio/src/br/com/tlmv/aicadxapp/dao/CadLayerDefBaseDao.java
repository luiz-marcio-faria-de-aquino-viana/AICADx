/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadLayerDefBaseDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.dao.record.CadLayerDefRecord;

public abstract class CadLayerDefBaseDao {
//Private
	private BaseDao dao;
	
//Public
	
	/* Constructors */
	
	public CadLayerDefBaseDao(BaseDao dao)
	{
		this.dao = dao;
	}
	
	/* Methodes */
		
	public abstract CadLayerDefRecord selectByPk(Integer objectId);
	
	public abstract ArrayList<CadLayerDefRecord> selectAll();

	public abstract Integer insert(
			int objectId,
			int objType,
			String layerName,
			String reference,
			String colorVal,
			String ltypeName,
			double lineWeight,
			String isDeleted);

	public abstract Integer update(
			int objectId,
			String layerName,
			String reference,
			String colorVal,
			String ltypeName,
			double lineWeight,
			String isDeleted);

	public abstract Integer insertOrUpdate(
			int objectId,
			int objType,
			String layerName,
			String reference,
			String colorVal,
			String ltypeName,
			double lineWeight,
			String isDeleted);

	/* Getters/Setters */
	
	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

}
