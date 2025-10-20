/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BaseODataDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;

public abstract class BaseODataDao 
{
//Private
	private BaseDao dao;
	
//Public
	
	/* Constructors */
	
	public BaseODataDao(BaseDao dao)
	{
		this.dao = dao;
	}
	
	/* Methodes */
		
	public abstract BaseObjectRecord selectByPk(String schemaName, Integer objectId);
	
	public abstract ArrayList<BaseObjectRecord> selectAll(String schemaName);

	public abstract ArrayList<BaseObjectRecord> selectByRefEntityId(String schemaName, int refEntityId);

	public abstract Integer insert(String schemaName, BaseObjectRecord o);

	public abstract Integer update(String schemaName, BaseObjectRecord o);

	public abstract Integer insertOrUpdate(String schemaName, BaseObjectRecord o);

	/* Getters/Setters */
	
	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

}
