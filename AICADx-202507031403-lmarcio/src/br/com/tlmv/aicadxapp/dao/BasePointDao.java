/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * BasePointDao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;

public abstract class BasePointDao 
{
//Private
	private BaseDao dao;
	
//Public
	
	/* Constructors */
	
	public BasePointDao(BaseDao dao)
	{
		this.dao = dao;
	}
	
	/* Methodes */
		
	public abstract BasePointRecord selectByPk(String schemaName, Integer objectId);
	
	public abstract ArrayList<BasePointRecord> selectAll(String schemaName);

	public abstract ArrayList<BasePointRecord> selectByRefEntityId(String schemaName, int refEntityId);

	public abstract Integer insert(String schemaName, BasePointRecord o);

	public abstract Integer update(String schemaName, BasePointRecord o);

	public abstract Integer insertOrUpdate(String schemaName, BasePointRecord o);

	/* Getters/Setters */
	
	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

}
