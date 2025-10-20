/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadView3dRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.dao.record;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomObserver3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomRect3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomUCS;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;

public class CadView3dRecord extends BaseObjectRecord
{
//Public Static

	public static final String sqlDrop = 
		"DROP TABLE #SCHEMA_NAME#.cad_view3d ";

	public static final String sqlCreate = 
		"CREATE TABLE #SCHEMA_NAME#.cad_view3d ( " +
		    "oid numeric(10,0) NOT NULL, " +
		    "object_id numeric(10,0) NOT NULL, " +
		    "obj_type numeric(10,0) NOT NULL, " +
		    "obj_type_str character varying(256) NOT NULL, " +
		    "plan_mcs0_x numeric(20,6) NOT NULL, " +
		    "plan_mcs0_y numeric(20,6) NOT NULL, " +
		    "plan_mcs0_z numeric(20,6) NOT NULL, " +
		    "plan_proj0_x numeric(20,6) NOT NULL, " +
		    "plan_proj0_y numeric(20,6) NOT NULL, " +
		    "plan_proj0_z numeric(20,6) NOT NULL, " +
		    "plan_scr0_x numeric(20,6) NOT NULL, " +
		    "plan_scr0_y numeric(20,6) NOT NULL, " +
		    "limits_mcs0_x numeric(20,6) NOT NULL, " +
		    "limits_mcs0_y numeric(20,6) NOT NULL, " +
		    "limits_mcs0_z numeric(20,6) NOT NULL, " +
		    "limits_proj0_x numeric(20,6) NOT NULL, " +
		    "limits_proj0_y numeric(20,6) NOT NULL, " +
		    "limits_proj0_z numeric(20,6) NOT NULL, " +
		    "limits_scr0_x numeric(20,6) NOT NULL, " +
		    "limits_scr0_y numeric(20,6) NOT NULL, " +
		    "obs_mcs0_x numeric(20,6) NOT NULL, " +
		    "obs_mcs0_y numeric(20,6) NOT NULL, " +
		    "obs_mcs0_z numeric(20,6) NOT NULL, " +
		    "scale_proj0 numeric(20,6) NOT NULL, " +
		    "scale_scr0 numeric(20,6) NOT NULL, " +
		    "plan_mcs_x numeric(20,6) NOT NULL, " +
		    "plan_mcs_y numeric(20,6) NOT NULL, " +
		    "plan_mcs_z numeric(20,6) NOT NULL, " +
		    "limits_mcs_x numeric(20,6) NOT NULL, " +
		    "limits_mcs_y numeric(20,6) NOT NULL, " +
		    "limits_mcs_z numeric(20,6) NOT NULL, " +
		    "obs_mcs_x numeric(20,6) NOT NULL, " +
		    "obs_mcs_y numeric(20,6) NOT NULL, " +
		    "obs_mcs_z numeric(20,6) NOT NULL, " +
		    "plan_proj_x numeric(20,6) NOT NULL, " +
		    "plan_proj_y numeric(20,6) NOT NULL, " +
		    "plan_proj_z numeric(20,6) NOT NULL, " +
		    "limits_proj_x numeric(20,6) NOT NULL, " +
		    "limits_proj_y numeric(20,6) NOT NULL, " +
		    "limits_proj_z numeric(20,6) NOT NULL, " +
		    "scale_proj0 numeric(20,6) NOT NULL, " +
		    "plan_scr_x numeric(20,6) NOT NULL, " +
		    "plan_scr_y numeric(20,6) NOT NULL, " +
		    "limits_scr_x numeric(20,6) NOT NULL, " +
		    "limits_scr_y numeric(20,6) NOT NULL, " +
		    "scale_scr numeric(20,6) NOT NULL, " +
		    "is_entity_object character(1) NOT NULL, " +
		    "is_deleted character(1) NOT NULL) ";
			
	public static final String sqlSelectByPk = 
		"SELECT " +
			"oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "plan_mcs0_x, " +
		    "plan_mcs0_y, " +
		    "plan_mcs0_z, " +
		    "plan_proj0_x, " +
		    "plan_proj0_y, " +
		    "plan_proj0_z, " +
		    "plan_scr0_x, " +
		    "plan_scr0_y, " +
		    "limits_mcs0_x, " +
		    "limits_mcs0_y, " +
		    "limits_mcs0_z, " +
		    "limits_proj0_x, " +
		    "limits_proj0_y, " +
		    "limits_proj0_z, " +
		    "limits_scr0_x, " +
		    "limits_scr0_y, " +
		    "obs_mcs0_x, " +
		    "obs_mcs0_y, " +
		    "obs_mcs0_z, " +
		    "scale_proj0, " +
		    "scale_scr0, " +
		    "plan_mcs_x, " +
		    "plan_mcs_y, " +
		    "plan_mcs_z, " +
		    "limits_mcs_x, " +
		    "limits_mcs_y, " +
		    "limits_mcs_z, " +
		    "obs_mcs_x, " +
		    "obs_mcs_y, " +
		    "obs_mcs_z, " +
		    "plan_proj_x, " +
		    "plan_proj_y, " +
		    "plan_proj_z, " +
		    "limits_proj_x, " +
		    "limits_proj_y, " +
		    "limits_proj_z, " +
		    "scale_proj0, " +
		    "plan_scr_x, " +
		    "plan_scr_y, " +
		    "limits_scr_x, " +
		    "limits_scr_y, " +
		    "scale_scr, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_view3d " +
		"WHERE object_id = ? ";
	
	public static final String sqlSelectAll = 
		"SELECT " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "plan_mcs0_x, " +
		    "plan_mcs0_y, " +
		    "plan_mcs0_z, " +
		    "plan_proj0_x, " +
		    "plan_proj0_y, " +
		    "plan_proj0_z, " +
		    "plan_scr0_x, " +
		    "plan_scr0_y, " +
		    "limits_mcs0_x, " +
		    "limits_mcs0_y, " +
		    "limits_mcs0_z, " +
		    "limits_proj0_x, " +
		    "limits_proj0_y, " +
		    "limits_proj0_z, " +
		    "limits_scr0_x, " +
		    "limits_scr0_y, " +
		    "obs_mcs0_x, " +
		    "obs_mcs0_y, " +
		    "obs_mcs0_z, " +
		    "scale_proj0, " +
		    "scale_scr0, " +
		    "plan_mcs_x, " +
		    "plan_mcs_y, " +
		    "plan_mcs_z, " +
		    "limits_mcs_x, " +
		    "limits_mcs_y, " +
		    "limits_mcs_z, " +
		    "obs_mcs_x, " +
		    "obs_mcs_y, " +
		    "obs_mcs_z, " +
		    "plan_proj_x, " +
		    "plan_proj_y, " +
		    "plan_proj_z, " +
		    "limits_proj_x, " +
		    "limits_proj_y, " +
		    "limits_proj_z, " +
		    "scale_proj0, " +
		    "plan_scr_x, " +
		    "plan_scr_y, " +
		    "limits_scr_x, " +
		    "limits_scr_y, " +
		    "scale_scr, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"FROM #SCHEMA_NAME#.cad_view3d " +
		"ORDER BY object_id ";

	public static final String sqlInsert = 
		"INSERT INTO #SCHEMA_NAME#.cad_view3d( " +
		    "oid, " +
		    "object_id, " +
		    "obj_type, " +
		    "obj_type_str, " +
		    "plan_mcs0_x, " +
		    "plan_mcs0_y, " +
		    "plan_mcs0_z, " +
		    "plan_proj0_x, " +
		    "plan_proj0_y, " +
		    "plan_proj0_z, " +
		    "plan_scr0_x, " +
		    "plan_scr0_y, " +
		    "limits_mcs0_x, " +
		    "limits_mcs0_y, " +
		    "limits_mcs0_z, " +
		    "limits_proj0_x, " +
		    "limits_proj0_y, " +
		    "limits_proj0_z, " +
		    "limits_scr0_x, " +
		    "limits_scr0_y, " +
		    "obs_mcs0_x, " +
		    "obs_mcs0_y, " +
		    "obs_mcs0_z, " +
		    "scale_proj0, " +
		    "scale_scr0, " +
		    "plan_mcs_x, " +
		    "plan_mcs_y, " +
		    "plan_mcs_z, " +
		    "limits_mcs_x, " +
		    "limits_mcs_y, " +
		    "limits_mcs_z, " +
		    "obs_mcs_x, " +
		    "obs_mcs_y, " +
		    "obs_mcs_z, " +
		    "plan_proj_x, " +
		    "plan_proj_y, " +
		    "plan_proj_z, " +
		    "limits_proj_x, " +
		    "limits_proj_y, " +
		    "limits_proj_z, " +
		    "scale_proj0, " +
		    "plan_scr_x, " +
		    "plan_scr_y, " +
		    "limits_scr_x, " +
		    "limits_scr_y, " +
		    "scale_scr, " +
		    "is_entity_object, " +
		    "is_deleted " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

	public static final String sqlUpdate = 
		"UPDATE #SCHEMA_NAME#.cad_view3d set " +
		    "plan_mcs0_x = ?, " +
		    "plan_mcs0_y = ?, " +
		    "plan_mcs0_z = ?, " +
		    "plan_proj0_x = ?, " +
		    "plan_proj0_y = ?, " +
		    "plan_proj0_z = ?, " +
		    "plan_scr0_x = ?, " +
		    "plan_scr0_y = ?, " +
		    "limits_mcs0_x = ?, " +
		    "limits_mcs0_y = ?, " +
		    "limits_mcs0_z = ?, " +
		    "limits_proj0_x = ?, " +
		    "limits_proj0_y = ?, " +
		    "limits_proj0_z = ?, " +
		    "limits_scr0_x = ?, " +
		    "limits_scr0_y = ?, " +
		    "obs_mcs0_x = ?, " +
		    "obs_mcs0_y = ?, " +
		    "obs_mcs0_z = ?, " +
		    "scale_proj0 = ?, " +
		    "scale_scr0 = ?, " +
		    "plan_mcs_x = ?, " +
		    "plan_mcs_y = ?, " +
		    "plan_mcs_z = ?, " +
		    "limits_mcs_x = ?, " +
		    "limits_mcs_y = ?, " +
		    "limits_mcs_z = ?, " +
		    "obs_mcs_x = ?, " +
		    "obs_mcs_y = ?, " +
		    "obs_mcs_z = ?, " +
		    "plan_proj_x = ?, " +
		    "plan_proj_y = ?, " +
		    "plan_proj_z = ?, " +
		    "limits_proj_x = ?, " +
		    "limits_proj_y = ?, " +
		    "limits_proj_z = ?, " +
		    "scale_proj0 = ?, " +
		    "plan_scr_x = ?, " +
		    "plan_scr_y = ?, " +
		    "limits_scr_x = ?, " +
		    "limits_scr_y = ?, " +
		    "scale_scr = ?, " +
		    "is_deleted = ? " +
		"WHERE object_id = ? ";

	public static final String sqlNextSeq = 
		"SELECT nextval('#SCHEMA_NAME#.cad_view3d') ";

	public static final String sqlCurrSeq = 
		"SELECT currval('#SCHEMA_NAME#.cad_view3d') ";
			
//Private
	//*** InitialView
	
	//*** PLAN_MCS0
	
	//PTORIGIN_MCS
	private double planMcs0_ucs_ptOriginMcs_x;
	private double planMcs0_ucs_ptOriginMcs_y;
	private double planMcs0_ucs_ptOriginMcs_z;
	//
	//XDIR_MCS
	private double planMcs0_ucs_ptXDirMcs_x;
	private double planMcs0_ucs_ptXDirMcs_y;
	private double planMcs0_ucs_ptXDirMcs_z;
	//
	//PTPLAN_MCS
	private double planMcs0_ucs_ptPlanMcs_x;
	private double planMcs0_ucs_ptPlanMcs_y;
	private double planMcs0_ucs_ptPlanMcs_z;
	//
	//XDIR_MCS
	private double planMcs0_ucs_xDirMcs_xI;
	private double planMcs0_ucs_xDirMcs_yI;
	private double planMcs0_ucs_xDirMcs_zI;
	private double planMcs0_ucs_xDirMcs_xF;
	private double planMcs0_ucs_xDirMcs_yF;
	private double planMcs0_ucs_xDirMcs_zF;
	//
	//YDIR_MCS
	private double planMcs0_ucs_yDirMcs_xI;
	private double planMcs0_ucs_yDirMcs_yI;
	private double planMcs0_ucs_yDirMcs_zI;
	private double planMcs0_ucs_yDirMcs_xF;
	private double planMcs0_ucs_yDirMcs_yF;
	private double planMcs0_ucs_yDirMcs_zF;
	//
	//ZDIR_MCS
	private double planMcs0_ucs_zDirMcs_xI;
	private double planMcs0_ucs_zDirMcs_yI;
	private double planMcs0_ucs_zDirMcs_zI;
	private double planMcs0_ucs_zDirMcs_xF;
	private double planMcs0_ucs_zDirMcs_yF;
	private double planMcs0_ucs_zDirMcs_zF;
	//
	//VPLAN_MCS
	private double planMcs0_ucs_vPlanMcs_xI;
	private double planMcs0_ucs_vPlanMcs_yI;
	private double planMcs0_ucs_vPlanMcs_zI;
	private double planMcs0_ucs_vPlanMcs_xF;
	private double planMcs0_ucs_vPlanMcs_yF;
	private double planMcs0_ucs_vPlanMcs_zF;
	//
	//AXIS-X
	private double planMcs0_ucs_axisX_xI;
	private double planMcs0_ucs_axisX_yI;
	private double planMcs0_ucs_axisX_zI;
	private double planMcs0_ucs_axisX_xF;
	private double planMcs0_ucs_axisX_yF;
	private double planMcs0_ucs_axisX_zF;
	//
	//AXIS-Y
	private double planMcs0_ucs_axisY_xI;
	private double planMcs0_ucs_axisY_yI;
	private double planMcs0_ucs_axisY_zI;
	private double planMcs0_ucs_axisY_xF;
	private double planMcs0_ucs_axisY_yF;
	private double planMcs0_ucs_axisY_zF;
	//
	//AXIS-Z
	private double planMcs0_ucs_axisZ_xI;
	private double planMcs0_ucs_axisZ_yI;
	private double planMcs0_ucs_axisZ_zI;
	private double planMcs0_ucs_axisZ_xF;
	private double planMcs0_ucs_axisZ_yF;
	private double planMcs0_ucs_axisZ_zF;

	//PLAN_ORIGIN
	//
	//PTCENTER
	private double planMcs0_ptCenter_x;
	private double planMcs0_ptCenter_y;
	private double planMcs0_ptCenter_z;
	//
	//XDIR
	private double planMcs0_xDir_xI;
	private double planMcs0_xDir_yI;
	private double planMcs0_xDir_zI;
	private double planMcs0_xDir_xF;
	private double planMcs0_xDir_yF;
	private double planMcs0_xDir_zF;

	//AXIS_DIRECTIONS
	//
	//AXIS-X
	private double planMcs0_axisX_xI;
	private double planMcs0_axisX_yI;
	private double planMcs0_axisX_zI;
	private double planMcs0_axisX_xF;
	private double planMcs0_axisX_yF;
	private double planMcs0_axisX_zF;
	//
	//AXIS-Y
	private double planMcs0_axisY_xI;
	private double planMcs0_axisY_yI;
	private double planMcs0_axisY_zI;
	private double planMcs0_axisY_xF;
	private double planMcs0_axisY_yF;
	private double planMcs0_axisY_zF;
	//
	//AXIS-Z
	private double planMcs0_axisZ_xI;
	private double planMcs0_axisZ_yI;
	private double planMcs0_axisZ_zI;
	private double planMcs0_axisZ_xF;
	private double planMcs0_axisZ_yF;
	private double planMcs0_axisZ_zF;

	//PLAN_SIZE
	//
	private double planMcs0_width;
	private double planMcs0_height;

	//PLAN_RATIO
	//
	private double planMcs0_ratio;

	//PLAN_LIMITS
	//
	private double planMcs0_ptLowerLeftCorner_x;
	private double planMcs0_ptLowerLeftCorner_y;
	//
	private double planMcs0_ptLowerRightCorner_x;
	private double planMcs0_ptLowerRightCorner_y;
	//
	private double planMcs0_ptUpperRightCorner_x;
	private double planMcs0_ptUpperRightCorner_y;
	//
	private double planMcs0_ptUpperLeftCorner_x;
	private double planMcs0_ptUpperLeftCorner_y;
	
	//InitialLimits
	//
	private double limitsMcs0_ptMin_x;
	private double limitsMcs0_ptMin_y;
	//
	private double limitsMcs0_ptMax_x;
	private double limitsMcs0_ptMax_y;
	
	//*** PLAN_PROJ0
	
	//PTORIGIN_MCS
	private double planProj0_ucs_ptOriginMcs_x;
	private double planProj0_ucs_ptOriginMcs_y;
	private double planProj0_ucs_ptOriginMcs_z;
	//
	//XDIR_MCS
	private double planProj0_ucs_ptXDirMcs_x;
	private double planProj0_ucs_ptXDirMcs_y;
	private double planProj0_ucs_ptXDirMcs_z;
	//
	//PTPLAN_MCS
	private double planProj0_ucs_ptPlanMcs_x;
	private double planProj0_ucs_ptPlanMcs_y;
	private double planProj0_ucs_ptPlanMcs_z;
	//
	//XDIR_MCS
	private double planProj0_ucs_xDirMcs_xI;
	private double planProj0_ucs_xDirMcs_yI;
	private double planProj0_ucs_xDirMcs_zI;
	private double planProj0_ucs_xDirMcs_xF;
	private double planProj0_ucs_xDirMcs_yF;
	private double planProj0_ucs_xDirMcs_zF;
	//
	//YDIR_MCS
	private double planProj0_ucs_yDirMcs_xI;
	private double planProj0_ucs_yDirMcs_yI;
	private double planProj0_ucs_yDirMcs_zI;
	private double planProj0_ucs_yDirMcs_xF;
	private double planProj0_ucs_yDirMcs_yF;
	private double planProj0_ucs_yDirMcs_zF;
	//
	//ZDIR_MCS
	private double planProj0_ucs_zDirMcs_xI;
	private double planProj0_ucs_zDirMcs_yI;
	private double planProj0_ucs_zDirMcs_zI;
	private double planProj0_ucs_zDirMcs_xF;
	private double planProj0_ucs_zDirMcs_yF;
	private double planProj0_ucs_zDirMcs_zF;
	//
	//VPLAN_MCS
	private double planProj0_ucs_vPlanMcs_xI;
	private double planProj0_ucs_vPlanMcs_yI;
	private double planProj0_ucs_vPlanMcs_zI;
	private double planProj0_ucs_vPlanMcs_xF;
	private double planProj0_ucs_vPlanMcs_yF;
	private double planProj0_ucs_vPlanMcs_zF;
	//
	//AXIS-X
	private double planProj0_ucs_axisX_xI;
	private double planProj0_ucs_axisX_yI;
	private double planProj0_ucs_axisX_zI;
	private double planProj0_ucs_axisX_xF;
	private double planProj0_ucs_axisX_yF;
	private double planProj0_ucs_axisX_zF;
	//
	//AXIS-Y
	private double planProj0_ucs_axisY_xI;
	private double planProj0_ucs_axisY_yI;
	private double planProj0_ucs_axisY_zI;
	private double planProj0_ucs_axisY_xF;
	private double planProj0_ucs_axisY_yF;
	private double planProj0_ucs_axisY_zF;
	//
	//AXIS-Z
	private double planProj0_ucs_axisZ_xI;
	private double planProj0_ucs_axisZ_yI;
	private double planProj0_ucs_axisZ_zI;
	private double planProj0_ucs_axisZ_xF;
	private double planProj0_ucs_axisZ_yF;
	private double planProj0_ucs_axisZ_zF;

	//PLAN_ORIGIN
	//
	//PTCENTER
	private double planProj0_ptCenter_x;
	private double planProj0_ptCenter_y;
	private double planProj0_ptCenter_z;
	//
	//XDIR
	private double planProj0_xDir_xI;
	private double planProj0_xDir_yI;
	private double planProj0_xDir_zI;
	private double planProj0_xDir_xF;
	private double planProj0_xDir_yF;
	private double planProj0_xDir_zF;

	//AXIS_DIRECTIONS
	//
	//AXIS-X
	private double planProj0_axisX_xI;
	private double planProj0_axisX_yI;
	private double planProj0_axisX_zI;
	private double planProj0_axisX_xF;
	private double planProj0_axisX_yF;
	private double planProj0_axisX_zF;
	//
	//AXIS-Y
	private double planProj0_axisY_xI;
	private double planProj0_axisY_yI;
	private double planProj0_axisY_zI;
	private double planProj0_axisY_xF;
	private double planProj0_axisY_yF;
	private double planProj0_axisY_zF;
	//
	//AXIS-Z
	private double planProj0_axisZ_xI;
	private double planProj0_axisZ_yI;
	private double planProj0_axisZ_zI;
	private double planProj0_axisZ_xF;
	private double planProj0_axisZ_yF;
	private double planProj0_axisZ_zF;

	//PLAN_SIZE
	//
	private double planProj0_width;
	private double planProj0_height;

	//PLAN_RATIO
	//
	private double planProj0_ratio;

	//PLAN_LIMITS
	//
	private double planProj0_ptLowerLeftCorner_x;
	private double planProj0_ptLowerLeftCorner_y;
	//
	private double planProj0_ptLowerRightCorner_x;
	private double planProj0_ptLowerRightCorner_y;
	//
	private double planProj0_ptUpperRightCorner_x;
	private double planProj0_ptUpperRightCorner_y;
	//
	private double planProj0_ptUpperLeftCorner_x;
	private double planProj0_ptUpperLeftCorner_y;
	
	//InitialLimits
	//
	private double limitsProj0_ptMin_x;
	private double limitsProj0_ptMin_y;
	//
	private double limitsProj0_ptMax_x;
	private double limitsProj0_ptMax_y;
	
	//*** PLAN_SCR0
		
	//PTORIGIN_MCS
	private double planScr0_ucs_ptOriginMcs_x;
	private double planScr0_ucs_ptOriginMcs_y;
	private double planScr0_ucs_ptOriginMcs_z;
	//
	//XDIR_MCS
	private double planScr0_ucs_ptXDirMcs_x;
	private double planScr0_ucs_ptXDirMcs_y;
	private double planScr0_ucs_ptXDirMcs_z;
	//
	//PTPLAN_MCS
	private double planScr0_ucs_ptPlanScr_x;
	private double planScr0_ucs_ptPlanScr_y;
	private double planScr0_ucs_ptPlanScr_z;
	//
	//XDIR_MCS
	private double planScr0_ucs_xDirMcs_xI;
	private double planScr0_ucs_xDirMcs_yI;
	private double planScr0_ucs_xDirMcs_zI;
	private double planScr0_ucs_xDirMcs_xF;
	private double planScr0_ucs_xDirMcs_yF;
	private double planScr0_ucs_xDirMcs_zF;
	//
	//YDIR_MCS
	private double planScr0_ucs_yDirMcs_xI;
	private double planScr0_ucs_yDirMcs_yI;
	private double planScr0_ucs_yDirMcs_zI;
	private double planScr0_ucs_yDirMcs_xF;
	private double planScr0_ucs_yDirMcs_yF;
	private double planScr0_ucs_yDirMcs_zF;
	//
	//ZDIR_MCS
	private double planScr0_ucs_zDirMcs_xI;
	private double planScr0_ucs_zDirMcs_yI;
	private double planScr0_ucs_zDirMcs_zI;
	private double planScr0_ucs_zDirMcs_xF;
	private double planScr0_ucs_zDirMcs_yF;
	private double planScr0_ucs_zDirMcs_zF;
	//
	//VPLAN_MCS
	private double planScr0_ucs_vPlanScr_xI;
	private double planScr0_ucs_vPlanScr_yI;
	private double planScr0_ucs_vPlanScr_zI;
	private double planScr0_ucs_vPlanScr_xF;
	private double planScr0_ucs_vPlanScr_yF;
	private double planScr0_ucs_vPlanScr_zF;
	//
	//AXIS-X
	private double planScr0_ucs_axisX_xI;
	private double planScr0_ucs_axisX_yI;
	private double planScr0_ucs_axisX_zI;
	private double planScr0_ucs_axisX_xF;
	private double planScr0_ucs_axisX_yF;
	private double planScr0_ucs_axisX_zF;
	//
	//AXIS-Y
	private double planScr0_ucs_axisY_xI;
	private double planScr0_ucs_axisY_yI;
	private double planScr0_ucs_axisY_zI;
	private double planScr0_ucs_axisY_xF;
	private double planScr0_ucs_axisY_yF;
	private double planScr0_ucs_axisY_zF;
	//
	//AXIS-Z
	private double planScr0_ucs_axisZ_xI;
	private double planScr0_ucs_axisZ_yI;
	private double planScr0_ucs_axisZ_zI;
	private double planScr0_ucs_axisZ_xF;
	private double planScr0_ucs_axisZ_yF;
	private double planScr0_ucs_axisZ_zF;

	//PLAN_ORIGIN
	//
	//PTCENTER
	private double planScr0_ptCenter_x;
	private double planScr0_ptCenter_y;
	private double planScr0_ptCenter_z;
	//
	//XDIR
	private double planScr0_xDir_xI;
	private double planScr0_xDir_yI;
	private double planScr0_xDir_zI;
	private double planScr0_xDir_xF;
	private double planScr0_xDir_yF;
	private double planScr0_xDir_zF;

	//AXIS_DIRECTIONS
	//
	//AXIS-X
	private double planScr0_axisX_xI;
	private double planScr0_axisX_yI;
	private double planScr0_axisX_zI;
	private double planScr0_axisX_xF;
	private double planScr0_axisX_yF;
	private double planScr0_axisX_zF;
	//
	//AXIS-Y
	private double planScr0_axisY_xI;
	private double planScr0_axisY_yI;
	private double planScr0_axisY_zI;
	private double planScr0_axisY_xF;
	private double planScr0_axisY_yF;
	private double planScr0_axisY_zF;
	//
	//AXIS-Z
	private double planScr0_axisZ_xI;
	private double planScr0_axisZ_yI;
	private double planScr0_axisZ_zI;
	private double planScr0_axisZ_xF;
	private double planScr0_axisZ_yF;
	private double planScr0_axisZ_zF;

	//PLAN_SIZE
	//
	private double planScr0_width;
	private double planScr0_height;

	//PLAN_RATIO
	//
	private double planScr0_ratio;

	//PLAN_LIMITS
	//
	private double planScr0_ptLowerLeftCorner_x;
	private double planScr0_ptLowerLeftCorner_y;
	//
	private double planScr0_ptLowerRightCorner_x;
	private double planScr0_ptLowerRightCorner_y;
	//
	private double planScr0_ptUpperRightCorner_x;
	private double planScr0_ptUpperRightCorner_y;
	//
	private double planScr0_ptUpperLeftCorner_x;
	private double planScr0_ptUpperLeftCorner_y;
	
	//*** OBS_MCS0
	
	//PTOBSERVER
	//
	private double obsMcs0_ptObserver_x;
	private double obsMcs0_ptObserver_y;
	private double obsMcs0_ptObserver_z;
	
	//PTOBSERVER
	//
	private double obsMcs0_uDir_xI;
	private double obsMcs0_uDir_yI;
	private double obsMcs0_uDir_zI;
	private double obsMcs0_uDir_xF;
	private double obsMcs0_uDir_yF;
	private double obsMcs0_uDir_zF;
	//
	private double obsMcs0_nDir_xI;
	private double obsMcs0_nDir_yI;
	private double obsMcs0_nDir_zI;
	private double obsMcs0_nDir_xF;
	private double obsMcs0_nDir_yF;
	private double obsMcs0_nDir_zF;
	
	//*** InitialScale
	
	private double scaleProj0;
	private double scaleScr0;

	//*** CurrentView

	//*** PLAN_MCS
	
	//PTORIGIN_MCS
	private double planMcs_ucs_ptOriginMcs_x;
	private double planMcs_ucs_ptOriginMcs_y;
	private double planMcs_ucs_ptOriginMcs_z;
	//
	//XDIR_MCS
	private double planMcs_ucs_ptXDirMcs_x;
	private double planMcs_ucs_ptXDirMcs_y;
	private double planMcs_ucs_ptXDirMcs_z;
	//
	//PTPLAN_MCS
	private double planMcs_ucs_ptPlanMcs_x;
	private double planMcs_ucs_ptPlanMcs_y;
	private double planMcs_ucs_ptPlanMcs_z;
	//
	//XDIR_MCS
	private double planMcs_ucs_xDirMcs_xI;
	private double planMcs_ucs_xDirMcs_yI;
	private double planMcs_ucs_xDirMcs_zI;
	private double planMcs_ucs_xDirMcs_xF;
	private double planMcs_ucs_xDirMcs_yF;
	private double planMcs_ucs_xDirMcs_zF;
	//
	//YDIR_MCS
	private double planMcs_ucs_yDirMcs_xI;
	private double planMcs_ucs_yDirMcs_yI;
	private double planMcs_ucs_yDirMcs_zI;
	private double planMcs_ucs_yDirMcs_xF;
	private double planMcs_ucs_yDirMcs_yF;
	private double planMcs_ucs_yDirMcs_zF;
	//
	//ZDIR_MCS
	private double planMcs_ucs_zDirMcs_xI;
	private double planMcs_ucs_zDirMcs_yI;
	private double planMcs_ucs_zDirMcs_zI;
	private double planMcs_ucs_zDirMcs_xF;
	private double planMcs_ucs_zDirMcs_yF;
	private double planMcs_ucs_zDirMcs_zF;
	//
	//VPLAN_MCS
	private double planMcs_ucs_vPlanMcs_xI;
	private double planMcs_ucs_vPlanMcs_yI;
	private double planMcs_ucs_vPlanMcs_zI;
	private double planMcs_ucs_vPlanMcs_xF;
	private double planMcs_ucs_vPlanMcs_yF;
	private double planMcs_ucs_vPlanMcs_zF;
	//
	//AXIS-X
	private double planMcs_ucs_axisX_xI;
	private double planMcs_ucs_axisX_yI;
	private double planMcs_ucs_axisX_zI;
	private double planMcs_ucs_axisX_xF;
	private double planMcs_ucs_axisX_yF;
	private double planMcs_ucs_axisX_zF;
	//
	//AXIS-Y
	private double planMcs_ucs_axisY_xI;
	private double planMcs_ucs_axisY_yI;
	private double planMcs_ucs_axisY_zI;
	private double planMcs_ucs_axisY_xF;
	private double planMcs_ucs_axisY_yF;
	private double planMcs_ucs_axisY_zF;
	//
	//AXIS-Z
	private double planMcs_ucs_axisZ_xI;
	private double planMcs_ucs_axisZ_yI;
	private double planMcs_ucs_axisZ_zI;
	private double planMcs_ucs_axisZ_xF;
	private double planMcs_ucs_axisZ_yF;
	private double planMcs_ucs_axisZ_zF;

	//PLAN_ORIGIN
	//
	//PTCENTER
	private double planMcs_ptCenter_x;
	private double planMcs_ptCenter_y;
	private double planMcs_ptCenter_z;
	//
	//XDIR
	private double planMcs_xDir_xI;
	private double planMcs_xDir_yI;
	private double planMcs_xDir_zI;
	private double planMcs_xDir_xF;
	private double planMcs_xDir_yF;
	private double planMcs_xDir_zF;

	//AXIS_DIRECTIONS
	//
	//AXIS-X
	private double planMcs_axisX_xI;
	private double planMcs_axisX_yI;
	private double planMcs_axisX_zI;
	private double planMcs_axisX_xF;
	private double planMcs_axisX_yF;
	private double planMcs_axisX_zF;
	//
	//AXIS-Y
	private double planMcs_axisY_xI;
	private double planMcs_axisY_yI;
	private double planMcs_axisY_zI;
	private double planMcs_axisY_xF;
	private double planMcs_axisY_yF;
	private double planMcs_axisY_zF;
	//
	//AXIS-Z
	private double planMcs_axisZ_xI;
	private double planMcs_axisZ_yI;
	private double planMcs_axisZ_zI;
	private double planMcs_axisZ_xF;
	private double planMcs_axisZ_yF;
	private double planMcs_axisZ_zF;

	//PLAN_SIZE
	//
	private double planMcs_width;
	private double planMcs_height;

	//PLAN_RATIO
	//
	private double planMcs_ratio;

	//PLAN_LIMITS
	//
	private double planMcs_ptLowerLeftCorner_x;
	private double planMcs_ptLowerLeftCorner_y;
	//
	private double planMcs_ptLowerRightCorner_x;
	private double planMcs_ptLowerRightCorner_y;
	//
	private double planMcs_ptUpperRightCorner_x;
	private double planMcs_ptUpperRightCorner_y;
	//
	private double planMcs_ptUpperLeftCorner_x;
	private double planMcs_ptUpperLeftCorner_y;
	
	//*** CurrentLimits
	//
	private double limitsMcs_ptMin_x;
	private double limitsMcs_ptMin_y;
	//
	private double limitsMcs_ptMax_x;
	private double limitsMcs_ptMax_y;
	
	//*** PLAN_PROJ
	
	//PTORIGIN_MCS
	private double planProj_ucs_ptOriginMcs_x;
	private double planProj_ucs_ptOriginMcs_y;
	private double planProj_ucs_ptOriginMcs_z;
	//
	//XDIR_MCS
	private double planProj_ucs_ptXDirMcs_x;
	private double planProj_ucs_ptXDirMcs_y;
	private double planProj_ucs_ptXDirMcs_z;
	//
	//PTPLAN_MCS
	private double planProj_ucs_ptPlanMcs_x;
	private double planProj_ucs_ptPlanMcs_y;
	private double planProj_ucs_ptPlanMcs_z;
	//
	//XDIR_MCS
	private double planProj_ucs_xDirMcs_xI;
	private double planProj_ucs_xDirMcs_yI;
	private double planProj_ucs_xDirMcs_zI;
	private double planProj_ucs_xDirMcs_xF;
	private double planProj_ucs_xDirMcs_yF;
	private double planProj_ucs_xDirMcs_zF;
	//
	//YDIR_MCS
	private double planProj_ucs_yDirMcs_xI;
	private double planProj_ucs_yDirMcs_yI;
	private double planProj_ucs_yDirMcs_zI;
	private double planProj_ucs_yDirMcs_xF;
	private double planProj_ucs_yDirMcs_yF;
	private double planProj_ucs_yDirMcs_zF;
	//
	//ZDIR_MCS
	private double planProj_ucs_zDirMcs_xI;
	private double planProj_ucs_zDirMcs_yI;
	private double planProj_ucs_zDirMcs_zI;
	private double planProj_ucs_zDirMcs_xF;
	private double planProj_ucs_zDirMcs_yF;
	private double planProj_ucs_zDirMcs_zF;
	//
	//VPLAN_MCS
	private double planProj_ucs_vPlanMcs_xI;
	private double planProj_ucs_vPlanMcs_yI;
	private double planProj_ucs_vPlanMcs_zI;
	private double planProj_ucs_vPlanMcs_xF;
	private double planProj_ucs_vPlanMcs_yF;
	private double planProj_ucs_vPlanMcs_zF;
	//
	//AXIS-X
	private double planProj_ucs_axisX_xI;
	private double planProj_ucs_axisX_yI;
	private double planProj_ucs_axisX_zI;
	private double planProj_ucs_axisX_xF;
	private double planProj_ucs_axisX_yF;
	private double planProj_ucs_axisX_zF;
	//
	//AXIS-Y
	private double planProj_ucs_axisY_xI;
	private double planProj_ucs_axisY_yI;
	private double planProj_ucs_axisY_zI;
	private double planProj_ucs_axisY_xF;
	private double planProj_ucs_axisY_yF;
	private double planProj_ucs_axisY_zF;
	//
	//AXIS-Z
	private double planProj_ucs_axisZ_xI;
	private double planProj_ucs_axisZ_yI;
	private double planProj_ucs_axisZ_zI;
	private double planProj_ucs_axisZ_xF;
	private double planProj_ucs_axisZ_yF;
	private double planProj_ucs_axisZ_zF;

	//PLAN_ORIGIN
	//
	//PTCENTER
	private double planProj_ptCenter_x;
	private double planProj_ptCenter_y;
	private double planProj_ptCenter_z;
	//
	//XDIR
	private double planProj_xDir_xI;
	private double planProj_xDir_yI;
	private double planProj_xDir_zI;
	private double planProj_xDir_xF;
	private double planProj_xDir_yF;
	private double planProj_xDir_zF;

	//AXIS_DIRECTIONS
	//
	//AXIS-X
	private double planProj_axisX_xI;
	private double planProj_axisX_yI;
	private double planProj_axisX_zI;
	private double planProj_axisX_xF;
	private double planProj_axisX_yF;
	private double planProj_axisX_zF;
	//
	//AXIS-Y
	private double planProj_axisY_xI;
	private double planProj_axisY_yI;
	private double planProj_axisY_zI;
	private double planProj_axisY_xF;
	private double planProj_axisY_yF;
	private double planProj_axisY_zF;
	//
	//AXIS-Z
	private double planProj_axisZ_xI;
	private double planProj_axisZ_yI;
	private double planProj_axisZ_zI;
	private double planProj_axisZ_xF;
	private double planProj_axisZ_yF;
	private double planProj_axisZ_zF;

	//PLAN_SIZE
	//
	private double planProj_width;
	private double planProj_height;

	//PLAN_RATIO
	//
	private double planProj_ratio;

	//PLAN_LIMITS
	//
	private double planProj_ptLowerLeftCorner_x;
	private double planProj_ptLowerLeftCorner_y;
	//
	private double planProj_ptLowerRightCorner_x;
	private double planProj_ptLowerRightCorner_y;
	//
	private double planProj_ptUpperRightCorner_x;
	private double planProj_ptUpperRightCorner_y;
	//
	private double planProj_ptUpperLeftCorner_x;
	private double planProj_ptUpperLeftCorner_y;
	
	//*** CurrentLimits
	//
	private double limitsProj_ptMin_x;
	private double limitsProj_ptMin_y;
	//
	private double limitsProj_ptMax_x;
	private double limitsProj_ptMax_y;
	
	//*** PLAN_SCR
		
	//PTORIGIN_MCS
	private double planScr_ucs_ptOriginMcs_x;
	private double planScr_ucs_ptOriginMcs_y;
	private double planScr_ucs_ptOriginMcs_z;
	//
	//XDIR_MCS
	private double planScr_ucs_ptXDirMcs_x;
	private double planScr_ucs_ptXDirMcs_y;
	private double planScr_ucs_ptXDirMcs_z;
	//
	//PTPLAN_MCS
	private double planScr_ucs_ptPlanScr_x;
	private double planScr_ucs_ptPlanScr_y;
	private double planScr_ucs_ptPlanScr_z;
	//
	//XDIR_MCS
	private double planScr_ucs_xDirMcs_xI;
	private double planScr_ucs_xDirMcs_yI;
	private double planScr_ucs_xDirMcs_zI;
	private double planScr_ucs_xDirMcs_xF;
	private double planScr_ucs_xDirMcs_yF;
	private double planScr_ucs_xDirMcs_zF;
	//
	//YDIR_MCS
	private double planScr_ucs_yDirMcs_xI;
	private double planScr_ucs_yDirMcs_yI;
	private double planScr_ucs_yDirMcs_zI;
	private double planScr_ucs_yDirMcs_xF;
	private double planScr_ucs_yDirMcs_yF;
	private double planScr_ucs_yDirMcs_zF;
	//
	//ZDIR_MCS
	private double planScr_ucs_zDirMcs_xI;
	private double planScr_ucs_zDirMcs_yI;
	private double planScr_ucs_zDirMcs_zI;
	private double planScr_ucs_zDirMcs_xF;
	private double planScr_ucs_zDirMcs_yF;
	private double planScr_ucs_zDirMcs_zF;
	//
	//VPLAN_MCS
	private double planScr_ucs_vPlanScr_xI;
	private double planScr_ucs_vPlanScr_yI;
	private double planScr_ucs_vPlanScr_zI;
	private double planScr_ucs_vPlanScr_xF;
	private double planScr_ucs_vPlanScr_yF;
	private double planScr_ucs_vPlanScr_zF;
	//
	//AXIS-X
	private double planScr_ucs_axisX_xI;
	private double planScr_ucs_axisX_yI;
	private double planScr_ucs_axisX_zI;
	private double planScr_ucs_axisX_xF;
	private double planScr_ucs_axisX_yF;
	private double planScr_ucs_axisX_zF;
	//
	//AXIS-Y
	private double planScr_ucs_axisY_xI;
	private double planScr_ucs_axisY_yI;
	private double planScr_ucs_axisY_zI;
	private double planScr_ucs_axisY_xF;
	private double planScr_ucs_axisY_yF;
	private double planScr_ucs_axisY_zF;
	//
	//AXIS-Z
	private double planScr_ucs_axisZ_xI;
	private double planScr_ucs_axisZ_yI;
	private double planScr_ucs_axisZ_zI;
	private double planScr_ucs_axisZ_xF;
	private double planScr_ucs_axisZ_yF;
	private double planScr_ucs_axisZ_zF;

	//PLAN_ORIGIN
	//
	//PTCENTER
	private double planScr_ptCenter_x;
	private double planScr_ptCenter_y;
	private double planScr_ptCenter_z;
	//
	//XDIR
	private double planScr_xDir_xI;
	private double planScr_xDir_yI;
	private double planScr_xDir_zI;
	private double planScr_xDir_xF;
	private double planScr_xDir_yF;
	private double planScr_xDir_zF;

	//AXIS_DIRECTIONS
	//
	//AXIS-X
	private double planScr_axisX_xI;
	private double planScr_axisX_yI;
	private double planScr_axisX_zI;
	private double planScr_axisX_xF;
	private double planScr_axisX_yF;
	private double planScr_axisX_zF;
	//
	//AXIS-Y
	private double planScr_axisY_xI;
	private double planScr_axisY_yI;
	private double planScr_axisY_zI;
	private double planScr_axisY_xF;
	private double planScr_axisY_yF;
	private double planScr_axisY_zF;
	//
	//AXIS-Z
	private double planScr_axisZ_xI;
	private double planScr_axisZ_yI;
	private double planScr_axisZ_zI;
	private double planScr_axisZ_xF;
	private double planScr_axisZ_yF;
	private double planScr_axisZ_zF;

	//PLAN_SIZE
	//
	private double planScr_width;
	private double planScr_height;

	//PLAN_RATIO
	//
	private double planScr_ratio;

	//PLAN_LIMITS
	//
	private double planScr_ptLowerLeftCorner_x;
	private double planScr_ptLowerLeftCorner_y;
	//
	private double planScr_ptLowerRightCorner_x;
	private double planScr_ptLowerRightCorner_y;
	//
	private double planScr_ptUpperRightCorner_x;
	private double planScr_ptUpperRightCorner_y;
	//
	private double planScr_ptUpperLeftCorner_x;
	private double planScr_ptUpperLeftCorner_y;
	
	//*** OBS_MCS
	
	//PTOBSERVER
	//
	private double obsMcs_ptObserver_x;
	private double obsMcs_ptObserver_y;
	private double obsMcs_ptObserver_z;
	
	//PTOBSERVER
	//
	private double obsMcs_uDir_xI;
	private double obsMcs_uDir_yI;
	private double obsMcs_uDir_zI;
	private double obsMcs_uDir_xF;
	private double obsMcs_uDir_yF;
	private double obsMcs_uDir_zF;
	//
	private double obsMcs_nDir_xI;
	private double obsMcs_nDir_yI;
	private double obsMcs_nDir_zI;
	private double obsMcs_nDir_xF;
	private double obsMcs_nDir_yF;
	private double obsMcs_nDir_zF;
	
	//*** CurrentScale
	
	private double scaleProj;
	private double scaleScr;
	
//Public
    
    public CadView3dRecord()
    {
    	this.init1_MCS0(
    		AppDefs.NULL_LNG, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR, 
    		AppDefs.DEF_VALUES_NAO,
    		
    		//*** InitialView
    		
    		//*** PLAN_MCS0
    		
    		//PTORIGIN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//PTPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//YDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//ZDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//VPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_ORIGIN
    		//
    		//PTCENTER
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//AXIS_DIRECTIONS
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_SIZE
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_RATIO
    		//
    		AppDefs.NULL_DBL, 

    		//PLAN_LIMITS
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		
    		//InitialLimits
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL);

        this.init1_PROJ0(
    		//*** PLAN_PROJ0
    		
    		//PTORIGIN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//PTPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//YDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//ZDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//VPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_ORIGIN
    		//
    		//PTCENTER
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//AXIS_DIRECTIONS
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_SIZE
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_RATIO
    		//
    		AppDefs.NULL_DBL, 

    		//PLAN_LIMITS
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		
    		//InitialLimits
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		
    		//*** InitialScale
    		
    		AppDefs.NULL_DBL );

        this.init1_SCR0(
    		//*** PLAN_SCR0
    			
    		//PTORIGIN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//PTPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//YDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//ZDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//VPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_ORIGIN
    		//
    		//PTCENTER
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//AXIS_DIRECTIONS
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_SIZE
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_RATIO
    		//
    		AppDefs.NULL_DBL, 

    		//PLAN_LIMITS
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		
    		//*** InitialScale
    		
    		AppDefs.NULL_DBL);        

        this.init1_OBS0(
    		//*** OBS_MCS0
    		
    		//PTOBSERVER
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		
    		//PTOBSERVER
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL); 
        
        this.init2_MCS(
    		//*** CurrentView

    		//*** PLAN_MCS
    		
    		//PTORIGIN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//PTPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//YDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//ZDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//VPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_ORIGIN
    		//
    		//PTCENTER
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//AXIS_DIRECTIONS
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_SIZE
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_RATIO
    		//
    		AppDefs.NULL_DBL, 

    		//PLAN_LIMITS
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		
    		//*** CurrentLimits
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL);

        this.init2_PROJ(
    		//*** PLAN_PROJ
    		
    		//PTORIGIN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//PTPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//YDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//ZDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//VPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_ORIGIN
    		//
    		//PTCENTER
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//AXIS_DIRECTIONS
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_SIZE
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_RATIO
    		//
    		AppDefs.NULL_DBL, 

    		//PLAN_LIMITS
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		
    		//*** CurrentLimits
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL); 

        this.init2_SCR(
    		//*** PLAN_SCR
    			
    		//PTORIGIN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//PTPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//YDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//ZDIR_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//VPLAN_MCS
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_ORIGIN
    		//
    		//PTCENTER
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//XDIR
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//AXIS_DIRECTIONS
    		//
    		//AXIS-X
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Y
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		//AXIS-Z
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_SIZE
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 

    		//PLAN_RATIO
    		//
    		AppDefs.NULL_DBL, 

    		//PLAN_LIMITS
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL);

        this.init2_OBS(
    		//*** OBS_MCS
    		
    		//PTOBSERVER
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		
    		//PTOBSERVER
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL);
        
    }
    
    /* Methodes */
    
    public void init1_MCS0(
		long oid,
		int objectId,
		int objType,
		String objTypeStr,
		String isDeleted,
		
		//*** InitialView
		
		//*** PLAN_MCS0
		
		//PTORIGIN_MCS
		double planMcs0_ucs_ptOriginMcs_x,
		double planMcs0_ucs_ptOriginMcs_y,
		double planMcs0_ucs_ptOriginMcs_z,
		//
		//XDIR_MCS
		double planMcs0_ucs_ptXDirMcs_x,
		double planMcs0_ucs_ptXDirMcs_y,
		double planMcs0_ucs_ptXDirMcs_z,
		//
		//PTPLAN_MCS
		double planMcs0_ucs_ptPlanMcs_x,
		double planMcs0_ucs_ptPlanMcs_y,
		double planMcs0_ucs_ptPlanMcs_z,
		//
		//XDIR_MCS
		double planMcs0_ucs_xDirMcs_xI,
		double planMcs0_ucs_xDirMcs_yI,
		double planMcs0_ucs_xDirMcs_zI,
		double planMcs0_ucs_xDirMcs_xF,
		double planMcs0_ucs_xDirMcs_yF,
		double planMcs0_ucs_xDirMcs_zF,
		//
		//YDIR_MCS
		double planMcs0_ucs_yDirMcs_xI,
		double planMcs0_ucs_yDirMcs_yI,
		double planMcs0_ucs_yDirMcs_zI,
		double planMcs0_ucs_yDirMcs_xF,
		double planMcs0_ucs_yDirMcs_yF,
		double planMcs0_ucs_yDirMcs_zF,
		//
		//ZDIR_MCS
		double planMcs0_ucs_zDirMcs_xI,
		double planMcs0_ucs_zDirMcs_yI,
		double planMcs0_ucs_zDirMcs_zI,
		double planMcs0_ucs_zDirMcs_xF,
		double planMcs0_ucs_zDirMcs_yF,
		double planMcs0_ucs_zDirMcs_zF,
		//
		//VPLAN_MCS
		double planMcs0_ucs_vPlanMcs_xI,
		double planMcs0_ucs_vPlanMcs_yI,
		double planMcs0_ucs_vPlanMcs_zI,
		double planMcs0_ucs_vPlanMcs_xF,
		double planMcs0_ucs_vPlanMcs_yF,
		double planMcs0_ucs_vPlanMcs_zF,
		//
		//AXIS-X
		double planMcs0_ucs_axisX_xI,
		double planMcs0_ucs_axisX_yI,
		double planMcs0_ucs_axisX_zI,
		double planMcs0_ucs_axisX_xF,
		double planMcs0_ucs_axisX_yF,
		double planMcs0_ucs_axisX_zF,
		//
		//AXIS-Y
		double planMcs0_ucs_axisY_xI,
		double planMcs0_ucs_axisY_yI,
		double planMcs0_ucs_axisY_zI,
		double planMcs0_ucs_axisY_xF,
		double planMcs0_ucs_axisY_yF,
		double planMcs0_ucs_axisY_zF,
		//
		//AXIS-Z
		double planMcs0_ucs_axisZ_xI,
		double planMcs0_ucs_axisZ_yI,
		double planMcs0_ucs_axisZ_zI,
		double planMcs0_ucs_axisZ_xF,
		double planMcs0_ucs_axisZ_yF,
		double planMcs0_ucs_axisZ_zF,

		//PLAN_ORIGIN
		//
		//PTCENTER
		double planMcs0_ptCenter_x,
		double planMcs0_ptCenter_y,
		double planMcs0_ptCenter_z,
		//
		//XDIR
		double planMcs0_xDir_xI,
		double planMcs0_xDir_yI,
		double planMcs0_xDir_zI,
		double planMcs0_xDir_xF,
		double planMcs0_xDir_yF,
		double planMcs0_xDir_zF,

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		double planMcs0_axisX_xI,
		double planMcs0_axisX_yI,
		double planMcs0_axisX_zI,
		double planMcs0_axisX_xF,
		double planMcs0_axisX_yF,
		double planMcs0_axisX_zF,
		//
		//AXIS-Y
		double planMcs0_axisY_xI,
		double planMcs0_axisY_yI,
		double planMcs0_axisY_zI,
		double planMcs0_axisY_xF,
		double planMcs0_axisY_yF,
		double planMcs0_axisY_zF,
		//
		//AXIS-Z
		double planMcs0_axisZ_xI,
		double planMcs0_axisZ_yI,
		double planMcs0_axisZ_zI,
		double planMcs0_axisZ_xF,
		double planMcs0_axisZ_yF,
		double planMcs0_axisZ_zF,

		//PLAN_SIZE
		//
		double planMcs0_width,
		double planMcs0_height,

		//PLAN_RATIO
		//
		double planMcs0_ratio,

		//PLAN_LIMITS
		//
		double planMcs0_ptLowerLeftCorner_x,
		double planMcs0_ptLowerLeftCorner_y,
		//
		double planMcs0_ptLowerRightCorner_x,
		double planMcs0_ptLowerRightCorner_y,
		//
		double planMcs0_ptUpperRightCorner_x,
		double planMcs0_ptUpperRightCorner_y,
		//
		double planMcs0_ptUpperLeftCorner_x,
		double planMcs0_ptUpperLeftCorner_y,
		
		//InitialLimits
		//
		double limitsMcs0_ptMin_x,
		double limitsMcs0_ptMin_y,
		//
		double limitsMcs0_ptMax_x,
		double limitsMcs0_ptMax_y )
    {
    	super.init(oid, objectId, objType, objTypeStr, AppDefs.DEF_VALUES_NAO, isDeleted);
		
		//*** InitialView
		
		//*** PLAN_MCS0
		
		//PTORIGIN_MCS
		this.planMcs0_ucs_ptOriginMcs_x = planMcs0_ucs_ptOriginMcs_x;
		this.planMcs0_ucs_ptOriginMcs_y = planMcs0_ucs_ptOriginMcs_y;
		this.planMcs0_ucs_ptOriginMcs_z = planMcs0_ucs_ptOriginMcs_z;
		//
		//XDIR_MCS
		this.planMcs0_ucs_ptXDirMcs_x = planMcs0_ucs_ptXDirMcs_x;
		this.planMcs0_ucs_ptXDirMcs_y = planMcs0_ucs_ptXDirMcs_y;
		this.planMcs0_ucs_ptXDirMcs_z = planMcs0_ucs_ptXDirMcs_z;
		//
		//PTPLAN_MCS
		this.planMcs0_ucs_ptPlanMcs_x = planMcs0_ucs_ptPlanMcs_x;
		this.planMcs0_ucs_ptPlanMcs_y = planMcs0_ucs_ptPlanMcs_y;
		this.planMcs0_ucs_ptPlanMcs_z = planMcs0_ucs_ptPlanMcs_z;
		//
		//XDIR_MCS
		this.planMcs0_ucs_xDirMcs_xI = planMcs0_ucs_xDirMcs_xI;
		this.planMcs0_ucs_xDirMcs_yI = planMcs0_ucs_xDirMcs_yI;
		this.planMcs0_ucs_xDirMcs_zI = planMcs0_ucs_xDirMcs_zI;
		this.planMcs0_ucs_xDirMcs_xF = planMcs0_ucs_xDirMcs_xF;
		this.planMcs0_ucs_xDirMcs_yF = planMcs0_ucs_xDirMcs_yF;
		this.planMcs0_ucs_xDirMcs_zF = planMcs0_ucs_xDirMcs_zF;
		//
		//YDIR_MCS
		this.planMcs0_ucs_yDirMcs_xI = planMcs0_ucs_yDirMcs_xI;
		this.planMcs0_ucs_yDirMcs_yI = planMcs0_ucs_yDirMcs_yI;
		this.planMcs0_ucs_yDirMcs_zI = planMcs0_ucs_yDirMcs_zI;
		this.planMcs0_ucs_yDirMcs_xF = planMcs0_ucs_yDirMcs_xF;
		this.planMcs0_ucs_yDirMcs_yF = planMcs0_ucs_yDirMcs_yF;
		this.planMcs0_ucs_yDirMcs_zF = planMcs0_ucs_yDirMcs_zF;
		//
		//ZDIR_MCS
		this.planMcs0_ucs_zDirMcs_xI = planMcs0_ucs_zDirMcs_xI;
		this.planMcs0_ucs_zDirMcs_yI = planMcs0_ucs_zDirMcs_yI;
		this.planMcs0_ucs_zDirMcs_zI = planMcs0_ucs_zDirMcs_zI;
		this.planMcs0_ucs_zDirMcs_xF = planMcs0_ucs_zDirMcs_xF;
		this.planMcs0_ucs_zDirMcs_yF = planMcs0_ucs_zDirMcs_yF;
		this.planMcs0_ucs_zDirMcs_zF = planMcs0_ucs_zDirMcs_zF;
		//
		//VPLAN_MCS
		this.planMcs0_ucs_vPlanMcs_xI = planMcs0_ucs_vPlanMcs_xI;
		this.planMcs0_ucs_vPlanMcs_yI = planMcs0_ucs_vPlanMcs_yI;
		this.planMcs0_ucs_vPlanMcs_zI = planMcs0_ucs_vPlanMcs_zI;
		this.planMcs0_ucs_vPlanMcs_xF = planMcs0_ucs_vPlanMcs_xF;
		this.planMcs0_ucs_vPlanMcs_yF = planMcs0_ucs_vPlanMcs_yF;
		this.planMcs0_ucs_vPlanMcs_zF = planMcs0_ucs_vPlanMcs_zF;
		//
		//AXIS-X
		this.planMcs0_ucs_axisX_xI = planMcs0_ucs_axisX_xI;
		this.planMcs0_ucs_axisX_yI = planMcs0_ucs_axisX_yI;
		this.planMcs0_ucs_axisX_zI = planMcs0_ucs_axisX_zI;
		this.planMcs0_ucs_axisX_xF = planMcs0_ucs_axisX_xF;
		this.planMcs0_ucs_axisX_yF = planMcs0_ucs_axisX_yF;
		this.planMcs0_ucs_axisX_zF = planMcs0_ucs_axisX_zF;
		//
		//AXIS-Y
		this.planMcs0_ucs_axisY_xI = planMcs0_ucs_axisY_xI;
		this.planMcs0_ucs_axisY_yI = planMcs0_ucs_axisY_yI;
		this.planMcs0_ucs_axisY_zI = planMcs0_ucs_axisY_zI;
		this.planMcs0_ucs_axisY_xF = planMcs0_ucs_axisY_xF;
		this.planMcs0_ucs_axisY_yF = planMcs0_ucs_axisY_yF;
		this.planMcs0_ucs_axisY_zF = planMcs0_ucs_axisY_zF;
		//
		//AXIS-Z
		this.planMcs0_ucs_axisZ_xI = planMcs0_ucs_axisZ_xI;
		this.planMcs0_ucs_axisZ_yI = planMcs0_ucs_axisZ_yI;
		this.planMcs0_ucs_axisZ_zI = planMcs0_ucs_axisZ_zI;
		this.planMcs0_ucs_axisZ_xF = planMcs0_ucs_axisZ_xF;
		this.planMcs0_ucs_axisZ_yF = planMcs0_ucs_axisZ_yF;
		this.planMcs0_ucs_axisZ_zF = planMcs0_ucs_axisZ_zF;

		//PLAN_ORIGIN
		//
		//PTCENTER
		this.planMcs0_ptCenter_x = planMcs0_ptCenter_x;
		this.planMcs0_ptCenter_y = planMcs0_ptCenter_y;
		this.planMcs0_ptCenter_z = planMcs0_ptCenter_z;
		//
		//XDIR
		this.planMcs0_xDir_xI = planMcs0_xDir_xI;
		this.planMcs0_xDir_yI = planMcs0_xDir_yI;
		this.planMcs0_xDir_zI = planMcs0_xDir_zI;
		this.planMcs0_xDir_xF = planMcs0_xDir_xF;
		this.planMcs0_xDir_yF = planMcs0_xDir_yF;
		this.planMcs0_xDir_zF = planMcs0_xDir_zF;

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		this.planMcs0_axisX_xI = planMcs0_axisX_xI;
		this.planMcs0_axisX_yI = planMcs0_axisX_yI;
		this.planMcs0_axisX_zI = planMcs0_axisX_zI;
		this.planMcs0_axisX_xF = planMcs0_axisX_xF;
		this.planMcs0_axisX_yF = planMcs0_axisX_yF;
		this.planMcs0_axisX_zF = planMcs0_axisX_zF;
		//
		//AXIS-Y
		this.planMcs0_axisY_xI = planMcs0_axisY_xI;
		this.planMcs0_axisY_yI = planMcs0_axisY_yI;
		this.planMcs0_axisY_zI = planMcs0_axisY_zI;
		this.planMcs0_axisY_xF = planMcs0_axisY_xF;
		this.planMcs0_axisY_yF = planMcs0_axisY_yF;
		this.planMcs0_axisY_zF = planMcs0_axisY_zF;
		//
		//AXIS-Z
		this.planMcs0_axisZ_xI = planMcs0_axisZ_xI;
		this.planMcs0_axisZ_yI = planMcs0_axisZ_yI;
		this.planMcs0_axisZ_zI = planMcs0_axisZ_zI;
		this.planMcs0_axisZ_xF = planMcs0_axisZ_xF;
		this.planMcs0_axisZ_yF = planMcs0_axisZ_yF;
		this.planMcs0_axisZ_zF = planMcs0_axisZ_zF;

		//PLAN_SIZE
		//
		this.planMcs0_width = planMcs0_width;
		this.planMcs0_height = planMcs0_height;

		//PLAN_RATIO
		//
		this.planMcs0_ratio = planMcs0_ratio;

		//PLAN_LIMITS
		//
		this.planMcs0_ptLowerLeftCorner_x = planMcs0_ptLowerLeftCorner_x;
		this.planMcs0_ptLowerLeftCorner_y = planMcs0_ptLowerLeftCorner_y;
		//
		this.planMcs0_ptLowerRightCorner_x = planMcs0_ptLowerRightCorner_x;
		this.planMcs0_ptLowerRightCorner_y = planMcs0_ptLowerRightCorner_y;
		//
		this.planMcs0_ptUpperRightCorner_x = planMcs0_ptUpperRightCorner_x;
		this.planMcs0_ptUpperRightCorner_y = planMcs0_ptUpperRightCorner_y;
		//
		this.planMcs0_ptUpperLeftCorner_x = planMcs0_ptUpperLeftCorner_x;
		this.planMcs0_ptUpperLeftCorner_y = planMcs0_ptUpperLeftCorner_y;
		
		//InitialLimits
		//
		this.limitsMcs0_ptMin_x = limitsMcs0_ptMin_x;
		this.limitsMcs0_ptMin_y = limitsMcs0_ptMin_y;
		//
		this.limitsMcs0_ptMax_x = limitsMcs0_ptMax_x;
		this.limitsMcs0_ptMax_y = limitsMcs0_ptMax_y;
    }

    public void init1_PROJ0(
		//*** PLAN_PROJ0
		
		//PTORIGIN_MCS
		double planProj0_ucs_ptOriginMcs_x,
		double planProj0_ucs_ptOriginMcs_y,
		double planProj0_ucs_ptOriginMcs_z,
		//
		//XDIR_MCS
		double planProj0_ucs_ptXDirMcs_x,
		double planProj0_ucs_ptXDirMcs_y,
		double planProj0_ucs_ptXDirMcs_z,
		//
		//PTPLAN_MCS
		double planProj0_ucs_ptPlanMcs_x,
		double planProj0_ucs_ptPlanMcs_y,
		double planProj0_ucs_ptPlanMcs_z,
		//
		//XDIR_MCS
		double planProj0_ucs_xDirMcs_xI,
		double planProj0_ucs_xDirMcs_yI,
		double planProj0_ucs_xDirMcs_zI,
		double planProj0_ucs_xDirMcs_xF,
		double planProj0_ucs_xDirMcs_yF,
		double planProj0_ucs_xDirMcs_zF,
		//
		//YDIR_MCS
		double planProj0_ucs_yDirMcs_xI,
		double planProj0_ucs_yDirMcs_yI,
		double planProj0_ucs_yDirMcs_zI,
		double planProj0_ucs_yDirMcs_xF,
		double planProj0_ucs_yDirMcs_yF,
		double planProj0_ucs_yDirMcs_zF,
		//
		//ZDIR_MCS
		double planProj0_ucs_zDirMcs_xI,
		double planProj0_ucs_zDirMcs_yI,
		double planProj0_ucs_zDirMcs_zI,
		double planProj0_ucs_zDirMcs_xF,
		double planProj0_ucs_zDirMcs_yF,
		double planProj0_ucs_zDirMcs_zF,
		//
		//VPLAN_MCS
		double planProj0_ucs_vPlanMcs_xI,
		double planProj0_ucs_vPlanMcs_yI,
		double planProj0_ucs_vPlanMcs_zI,
		double planProj0_ucs_vPlanMcs_xF,
		double planProj0_ucs_vPlanMcs_yF,
		double planProj0_ucs_vPlanMcs_zF,
		//
		//AXIS-X
		double planProj0_ucs_axisX_xI,
		double planProj0_ucs_axisX_yI,
		double planProj0_ucs_axisX_zI,
		double planProj0_ucs_axisX_xF,
		double planProj0_ucs_axisX_yF,
		double planProj0_ucs_axisX_zF,
		//
		//AXIS-Y
		double planProj0_ucs_axisY_xI,
		double planProj0_ucs_axisY_yI,
		double planProj0_ucs_axisY_zI,
		double planProj0_ucs_axisY_xF,
		double planProj0_ucs_axisY_yF,
		double planProj0_ucs_axisY_zF,
		//
		//AXIS-Z
		double planProj0_ucs_axisZ_xI,
		double planProj0_ucs_axisZ_yI,
		double planProj0_ucs_axisZ_zI,
		double planProj0_ucs_axisZ_xF,
		double planProj0_ucs_axisZ_yF,
		double planProj0_ucs_axisZ_zF,

		//PLAN_ORIGIN
		//
		//PTCENTER
		double planProj0_ptCenter_x,
		double planProj0_ptCenter_y,
		double planProj0_ptCenter_z,
		//
		//XDIR
		double planProj0_xDir_xI,
		double planProj0_xDir_yI,
		double planProj0_xDir_zI,
		double planProj0_xDir_xF,
		double planProj0_xDir_yF,
		double planProj0_xDir_zF,

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		double planProj0_axisX_xI,
		double planProj0_axisX_yI,
		double planProj0_axisX_zI,
		double planProj0_axisX_xF,
		double planProj0_axisX_yF,
		double planProj0_axisX_zF,
		//
		//AXIS-Y
		double planProj0_axisY_xI,
		double planProj0_axisY_yI,
		double planProj0_axisY_zI,
		double planProj0_axisY_xF,
		double planProj0_axisY_yF,
		double planProj0_axisY_zF,
		//
		//AXIS-Z
		double planProj0_axisZ_xI,
		double planProj0_axisZ_yI,
		double planProj0_axisZ_zI,
		double planProj0_axisZ_xF,
		double planProj0_axisZ_yF,
		double planProj0_axisZ_zF,

		//PLAN_SIZE
		//
		double planProj0_width,
		double planProj0_height,

		//PLAN_RATIO
		//
		double planProj0_ratio,

		//PLAN_LIMITS
		//
		double planProj0_ptLowerLeftCorner_x,
		double planProj0_ptLowerLeftCorner_y,
		//
		double planProj0_ptLowerRightCorner_x,
		double planProj0_ptLowerRightCorner_y,
		//
		double planProj0_ptUpperRightCorner_x,
		double planProj0_ptUpperRightCorner_y,
		//
		double planProj0_ptUpperLeftCorner_x,
		double planProj0_ptUpperLeftCorner_y,
		
		//InitialLimits
		//
		double limitsProj0_ptMin_x,
		double limitsProj0_ptMin_y,
		//
		double limitsProj0_ptMax_x,
		double limitsProj0_ptMax_y, 
		
		//*** InitialScale
		
		double scaleProj0 )
    {
		//*** PLAN_PROJ0
		
		//PTORIGIN_MCS
		this.planProj0_ucs_ptOriginMcs_x = planProj0_ucs_ptOriginMcs_x;
		this.planProj0_ucs_ptOriginMcs_y = planProj0_ucs_ptOriginMcs_y;
		this.planProj0_ucs_ptOriginMcs_z = planProj0_ucs_ptOriginMcs_z;
		//
		//XDIR_MCS
		this.planProj0_ucs_ptXDirMcs_x = planProj0_ucs_ptXDirMcs_x;
		this.planProj0_ucs_ptXDirMcs_y = planProj0_ucs_ptXDirMcs_y;
		this.planProj0_ucs_ptXDirMcs_z = planProj0_ucs_ptXDirMcs_z;
		//
		//PTPLAN_MCS
		this.planProj0_ucs_ptPlanMcs_x = planProj0_ucs_ptPlanMcs_x;
		this.planProj0_ucs_ptPlanMcs_y = planProj0_ucs_ptPlanMcs_y;
		this.planProj0_ucs_ptPlanMcs_z = planProj0_ucs_ptPlanMcs_z;
		//
		//XDIR_MCS
		this.planProj0_ucs_xDirMcs_xI = planProj0_ucs_xDirMcs_xI;
		this.planProj0_ucs_xDirMcs_yI = planProj0_ucs_xDirMcs_yI;
		this.planProj0_ucs_xDirMcs_zI = planProj0_ucs_xDirMcs_zI;
		this.planProj0_ucs_xDirMcs_xF = planProj0_ucs_xDirMcs_xF;
		this.planProj0_ucs_xDirMcs_yF = planProj0_ucs_xDirMcs_yF;
		this.planProj0_ucs_xDirMcs_zF = planProj0_ucs_xDirMcs_zF;
		//
		//YDIR_MCS
		this.planProj0_ucs_yDirMcs_xI = planProj0_ucs_yDirMcs_xI;
		this.planProj0_ucs_yDirMcs_yI = planProj0_ucs_yDirMcs_yI;
		this.planProj0_ucs_yDirMcs_zI = planProj0_ucs_yDirMcs_zI;
		this.planProj0_ucs_yDirMcs_xF = planProj0_ucs_yDirMcs_xF;
		this.planProj0_ucs_yDirMcs_yF = planProj0_ucs_yDirMcs_yF;
		this.planProj0_ucs_yDirMcs_zF = planProj0_ucs_yDirMcs_zF;
		//
		//ZDIR_MCS
		this.planProj0_ucs_zDirMcs_xI = planProj0_ucs_zDirMcs_xI;
		this.planProj0_ucs_zDirMcs_yI = planProj0_ucs_zDirMcs_yI;
		this.planProj0_ucs_zDirMcs_zI = planProj0_ucs_zDirMcs_zI;
		this.planProj0_ucs_zDirMcs_xF = planProj0_ucs_zDirMcs_xF;
		this.planProj0_ucs_zDirMcs_yF = planProj0_ucs_zDirMcs_yF;
		this.planProj0_ucs_zDirMcs_zF = planProj0_ucs_zDirMcs_zF;
		//
		//VPLAN_MCS
		this.planProj0_ucs_vPlanMcs_xI = planProj0_ucs_vPlanMcs_xI;
		this.planProj0_ucs_vPlanMcs_yI = planProj0_ucs_vPlanMcs_yI;
		this.planProj0_ucs_vPlanMcs_zI = planProj0_ucs_vPlanMcs_zI;
		this.planProj0_ucs_vPlanMcs_xF = planProj0_ucs_vPlanMcs_xF;
		this.planProj0_ucs_vPlanMcs_yF = planProj0_ucs_vPlanMcs_yF;
		this.planProj0_ucs_vPlanMcs_zF = planProj0_ucs_vPlanMcs_zF;
		//
		//AXIS-X
		this.planProj0_ucs_axisX_xI = planProj0_ucs_axisX_xI;
		this.planProj0_ucs_axisX_yI = planProj0_ucs_axisX_yI;
		this.planProj0_ucs_axisX_zI = planProj0_ucs_axisX_zI;
		this.planProj0_ucs_axisX_xF = planProj0_ucs_axisX_xF;
		this.planProj0_ucs_axisX_yF = planProj0_ucs_axisX_yF;
		this.planProj0_ucs_axisX_zF = planProj0_ucs_axisX_zF;
		//
		//AXIS-Y
		this.planProj0_ucs_axisY_xI = planProj0_ucs_axisY_xI;
		this.planProj0_ucs_axisY_yI = planProj0_ucs_axisY_yI;
		this.planProj0_ucs_axisY_zI = planProj0_ucs_axisY_zI;
		this.planProj0_ucs_axisY_xF = planProj0_ucs_axisY_xF;
		this.planProj0_ucs_axisY_yF = planProj0_ucs_axisY_yF;
		this.planProj0_ucs_axisY_zF = planProj0_ucs_axisY_zF;
		//
		//AXIS-Z
		this.planProj0_ucs_axisZ_xI = planProj0_ucs_axisZ_xI;
		this.planProj0_ucs_axisZ_yI = planProj0_ucs_axisZ_yI;
		this.planProj0_ucs_axisZ_zI = planProj0_ucs_axisZ_zI;
		this.planProj0_ucs_axisZ_xF = planProj0_ucs_axisZ_xF;
		this.planProj0_ucs_axisZ_yF = planProj0_ucs_axisZ_yF;
		this.planProj0_ucs_axisZ_zF = planProj0_ucs_axisZ_zF;

		//PLAN_ORIGIN
		//
		//PTCENTER
		this.planProj0_ptCenter_x = planProj0_ptCenter_x;
		this.planProj0_ptCenter_y = planProj0_ptCenter_y;
		this.planProj0_ptCenter_z = planProj0_ptCenter_z;
		//
		//XDIR
		this.planProj0_xDir_xI = planProj0_xDir_xI;
		this.planProj0_xDir_yI = planProj0_xDir_yI;
		this.planProj0_xDir_zI = planProj0_xDir_zI;
		this.planProj0_xDir_xF = planProj0_xDir_xF;
		this.planProj0_xDir_yF = planProj0_xDir_yF;
		this.planProj0_xDir_zF = planProj0_xDir_zF;

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		this.planProj0_axisX_xI = planProj0_axisX_xI;
		this.planProj0_axisX_yI = planProj0_axisX_yI;
		this.planProj0_axisX_zI = planProj0_axisX_zI;
		this.planProj0_axisX_xF = planProj0_axisX_xF;
		this.planProj0_axisX_yF = planProj0_axisX_yF;
		this.planProj0_axisX_zF = planProj0_axisX_zF;
		//
		//AXIS-Y
		this.planProj0_axisY_xI = planProj0_axisY_xI;
		this.planProj0_axisY_yI = planProj0_axisY_yI;
		this.planProj0_axisY_zI = planProj0_axisY_zI;
		this.planProj0_axisY_xF = planProj0_axisY_xF;
		this.planProj0_axisY_yF = planProj0_axisY_yF;
		this.planProj0_axisY_zF = planProj0_axisY_zF;
		//
		//AXIS-Z
		this.planProj0_axisZ_xI = planProj0_axisZ_xI;
		this.planProj0_axisZ_yI = planProj0_axisZ_yI;
		this.planProj0_axisZ_zI = planProj0_axisZ_zI;
		this.planProj0_axisZ_xF = planProj0_axisZ_xF;
		this.planProj0_axisZ_yF = planProj0_axisZ_yF;
		this.planProj0_axisZ_zF = planProj0_axisZ_zF;

		//PLAN_SIZE
		//
		this.planProj0_width = planProj0_width;
		this.planProj0_height = planProj0_height;

		//PLAN_RATIO
		//
		this.planProj0_ratio = planProj0_ratio;

		//PLAN_LIMITS
		//
		this.planProj0_ptLowerLeftCorner_x = planProj0_ptLowerLeftCorner_x;
		this.planProj0_ptLowerLeftCorner_y = planProj0_ptLowerLeftCorner_y;
		//
		this.planProj0_ptLowerRightCorner_x = planProj0_ptLowerRightCorner_x;
		this.planProj0_ptLowerRightCorner_y = planProj0_ptLowerRightCorner_y;
		//
		this.planProj0_ptUpperRightCorner_x = planProj0_ptUpperRightCorner_x;
		this.planProj0_ptUpperRightCorner_y = planProj0_ptUpperRightCorner_y;
		//
		this.planProj0_ptUpperLeftCorner_x = planProj0_ptUpperLeftCorner_x;
		this.planProj0_ptUpperLeftCorner_y = planProj0_ptUpperLeftCorner_y;
		
		//InitialLimits
		//
		this.limitsProj0_ptMin_x = limitsProj0_ptMin_x;
		this.limitsProj0_ptMin_y = limitsProj0_ptMin_y;
		//
		this.limitsProj0_ptMax_x = limitsProj0_ptMax_x;
		this.limitsProj0_ptMax_y = limitsProj0_ptMax_y; 
		
		//*** InitialScale
		
		this.scaleProj0 = scaleProj0;
    }

    public void init1_SCR0(
		//*** PLAN_SCR0
			
		//PTORIGIN_MCS
		double planScr0_ucs_ptOriginMcs_x,
		double planScr0_ucs_ptOriginMcs_y,
		double planScr0_ucs_ptOriginMcs_z,
		//
		//XDIR_MCS
		double planScr0_ucs_ptXDirMcs_x,
		double planScr0_ucs_ptXDirMcs_y,
		double planScr0_ucs_ptXDirMcs_z,
		//
		//PTPLAN_MCS
		double planScr0_ucs_ptPlanScr_x,
		double planScr0_ucs_ptPlanScr_y,
		double planScr0_ucs_ptPlanScr_z,
		//
		//XDIR_MCS
		double planScr0_ucs_xDirMcs_xI,
		double planScr0_ucs_xDirMcs_yI,
		double planScr0_ucs_xDirMcs_zI,
		double planScr0_ucs_xDirMcs_xF,
		double planScr0_ucs_xDirMcs_yF,
		double planScr0_ucs_xDirMcs_zF,
		//
		//YDIR_MCS
		double planScr0_ucs_yDirMcs_xI,
		double planScr0_ucs_yDirMcs_yI,
		double planScr0_ucs_yDirMcs_zI,
		double planScr0_ucs_yDirMcs_xF,
		double planScr0_ucs_yDirMcs_yF,
		double planScr0_ucs_yDirMcs_zF,
		//
		//ZDIR_MCS
		double planScr0_ucs_zDirMcs_xI,
		double planScr0_ucs_zDirMcs_yI,
		double planScr0_ucs_zDirMcs_zI,
		double planScr0_ucs_zDirMcs_xF,
		double planScr0_ucs_zDirMcs_yF,
		double planScr0_ucs_zDirMcs_zF,
		//
		//VPLAN_MCS
		double planScr0_ucs_vPlanScr_xI,
		double planScr0_ucs_vPlanScr_yI,
		double planScr0_ucs_vPlanScr_zI,
		double planScr0_ucs_vPlanScr_xF,
		double planScr0_ucs_vPlanScr_yF,
		double planScr0_ucs_vPlanScr_zF,
		//
		//AXIS-X
		double planScr0_ucs_axisX_xI,
		double planScr0_ucs_axisX_yI,
		double planScr0_ucs_axisX_zI,
		double planScr0_ucs_axisX_xF,
		double planScr0_ucs_axisX_yF,
		double planScr0_ucs_axisX_zF,
		//
		//AXIS-Y
		double planScr0_ucs_axisY_xI,
		double planScr0_ucs_axisY_yI,
		double planScr0_ucs_axisY_zI,
		double planScr0_ucs_axisY_xF,
		double planScr0_ucs_axisY_yF,
		double planScr0_ucs_axisY_zF,
		//
		//AXIS-Z
		double planScr0_ucs_axisZ_xI,
		double planScr0_ucs_axisZ_yI,
		double planScr0_ucs_axisZ_zI,
		double planScr0_ucs_axisZ_xF,
		double planScr0_ucs_axisZ_yF,
		double planScr0_ucs_axisZ_zF,

		//PLAN_ORIGIN
		//
		//PTCENTER
		double planScr0_ptCenter_x,
		double planScr0_ptCenter_y,
		double planScr0_ptCenter_z,
		//
		//XDIR
		double planScr0_xDir_xI,
		double planScr0_xDir_yI,
		double planScr0_xDir_zI,
		double planScr0_xDir_xF,
		double planScr0_xDir_yF,
		double planScr0_xDir_zF,

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		double planScr0_axisX_xI,
		double planScr0_axisX_yI,
		double planScr0_axisX_zI,
		double planScr0_axisX_xF,
		double planScr0_axisX_yF,
		double planScr0_axisX_zF,
		//
		//AXIS-Y
		double planScr0_axisY_xI,
		double planScr0_axisY_yI,
		double planScr0_axisY_zI,
		double planScr0_axisY_xF,
		double planScr0_axisY_yF,
		double planScr0_axisY_zF,
		//
		//AXIS-Z
		double planScr0_axisZ_xI,
		double planScr0_axisZ_yI,
		double planScr0_axisZ_zI,
		double planScr0_axisZ_xF,
		double planScr0_axisZ_yF,
		double planScr0_axisZ_zF,

		//PLAN_SIZE
		//
		double planScr0_width,
		double planScr0_height,

		//PLAN_RATIO
		//
		double planScr0_ratio,

		//PLAN_LIMITS
		//
		double planScr0_ptLowerLeftCorner_x,
		double planScr0_ptLowerLeftCorner_y,
		//
		double planScr0_ptLowerRightCorner_x,
		double planScr0_ptLowerRightCorner_y,
		//
		double planScr0_ptUpperRightCorner_x,
		double planScr0_ptUpperRightCorner_y,
		//
		double planScr0_ptUpperLeftCorner_x,
		double planScr0_ptUpperLeftCorner_y,
		
		//*** InitialScale
		
		double scaleScr0 )
    {
		//*** PLAN_SCR0
		
		//PTORIGIN_MCS
		this.planScr0_ucs_ptOriginMcs_x = planScr0_ucs_ptOriginMcs_x;
		this.planScr0_ucs_ptOriginMcs_y = planScr0_ucs_ptOriginMcs_y;
		this.planScr0_ucs_ptOriginMcs_z = planScr0_ucs_ptOriginMcs_z;
		//
		//XDIR_MCS
		this.planScr0_ucs_ptXDirMcs_x = planScr0_ucs_ptXDirMcs_x;
		this.planScr0_ucs_ptXDirMcs_y = planScr0_ucs_ptXDirMcs_y;
		this.planScr0_ucs_ptXDirMcs_z = planScr0_ucs_ptXDirMcs_z;
		//
		//PTPLAN_MCS
		this.planScr0_ucs_ptPlanScr_x = planScr0_ucs_ptPlanScr_x;
		this.planScr0_ucs_ptPlanScr_y = planScr0_ucs_ptPlanScr_y;
		this.planScr0_ucs_ptPlanScr_z = planScr0_ucs_ptPlanScr_z;
		//
		//XDIR_MCS
		this.planScr0_ucs_xDirMcs_xI = planScr0_ucs_xDirMcs_xI;
		this.planScr0_ucs_xDirMcs_yI = planScr0_ucs_xDirMcs_yI;
		this.planScr0_ucs_xDirMcs_zI = planScr0_ucs_xDirMcs_zI;
		this.planScr0_ucs_xDirMcs_xF = planScr0_ucs_xDirMcs_xF;
		this.planScr0_ucs_xDirMcs_yF = planScr0_ucs_xDirMcs_yF;
		this.planScr0_ucs_xDirMcs_zF = planScr0_ucs_xDirMcs_zF;
		//
		//YDIR_MCS
		this.planScr0_ucs_yDirMcs_xI = planScr0_ucs_yDirMcs_xI;
		this.planScr0_ucs_yDirMcs_yI = planScr0_ucs_yDirMcs_yI;
		this.planScr0_ucs_yDirMcs_zI = planScr0_ucs_yDirMcs_zI;
		this.planScr0_ucs_yDirMcs_xF = planScr0_ucs_yDirMcs_xF;
		this.planScr0_ucs_yDirMcs_yF = planScr0_ucs_yDirMcs_yF;
		this.planScr0_ucs_yDirMcs_zF = planScr0_ucs_yDirMcs_zF;
		//
		//ZDIR_MCS
		this.planScr0_ucs_zDirMcs_xI = planScr0_ucs_zDirMcs_xI;
		this.planScr0_ucs_zDirMcs_yI = planScr0_ucs_zDirMcs_yI;
		this.planScr0_ucs_zDirMcs_zI = planScr0_ucs_zDirMcs_zI;
		this.planScr0_ucs_zDirMcs_xF = planScr0_ucs_zDirMcs_xF;
		this.planScr0_ucs_zDirMcs_yF = planScr0_ucs_zDirMcs_yF;
		this.planScr0_ucs_zDirMcs_zF = planScr0_ucs_zDirMcs_zF;
		//
		//VPLAN_MCS
		this.planScr0_ucs_vPlanScr_xI = planScr0_ucs_vPlanScr_xI;
		this.planScr0_ucs_vPlanScr_yI = planScr0_ucs_vPlanScr_yI;
		this.planScr0_ucs_vPlanScr_zI = planScr0_ucs_vPlanScr_zI;
		this.planScr0_ucs_vPlanScr_xF = planScr0_ucs_vPlanScr_xF;
		this.planScr0_ucs_vPlanScr_yF = planScr0_ucs_vPlanScr_yF;
		this.planScr0_ucs_vPlanScr_zF = planScr0_ucs_vPlanScr_zF;
		//
		//AXIS-X
		this.planScr0_ucs_axisX_xI = planScr0_ucs_axisX_xI;
		this.planScr0_ucs_axisX_yI = planScr0_ucs_axisX_yI;
		this.planScr0_ucs_axisX_zI = planScr0_ucs_axisX_zI;
		this.planScr0_ucs_axisX_xF = planScr0_ucs_axisX_xF;
		this.planScr0_ucs_axisX_yF = planScr0_ucs_axisX_yF;
		this.planScr0_ucs_axisX_zF = planScr0_ucs_axisX_zF;
		//
		//AXIS-Y
		this.planScr0_ucs_axisY_xI = planScr0_ucs_axisY_xI;
		this.planScr0_ucs_axisY_yI = planScr0_ucs_axisY_yI;
		this.planScr0_ucs_axisY_zI = planScr0_ucs_axisY_zI;
		this.planScr0_ucs_axisY_xF = planScr0_ucs_axisY_xF;
		this.planScr0_ucs_axisY_yF = planScr0_ucs_axisY_yF;
		this.planScr0_ucs_axisY_zF = planScr0_ucs_axisY_zF;
		//
		//AXIS-Z
		this.planScr0_ucs_axisZ_xI = planScr0_ucs_axisZ_xI;
		this.planScr0_ucs_axisZ_yI = planScr0_ucs_axisZ_yI;
		this.planScr0_ucs_axisZ_zI = planScr0_ucs_axisZ_zI;
		this.planScr0_ucs_axisZ_xF = planScr0_ucs_axisZ_xF;
		this.planScr0_ucs_axisZ_yF = planScr0_ucs_axisZ_yF;
		this.planScr0_ucs_axisZ_zF = planScr0_ucs_axisZ_zF;

		//PLAN_ORIGIN
		//
		//PTCENTER
		this.planScr0_ptCenter_x = planScr0_ptCenter_x;
		this.planScr0_ptCenter_y = planScr0_ptCenter_y;
		this.planScr0_ptCenter_z = planScr0_ptCenter_z;
		//
		//XDIR
		this.planScr0_xDir_xI = planScr0_xDir_xI;
		this.planScr0_xDir_yI = planScr0_xDir_yI;
		this.planScr0_xDir_zI = planScr0_xDir_zI;
		this.planScr0_xDir_xF = planScr0_xDir_xF;
		this.planScr0_xDir_yF = planScr0_xDir_yF;
		this.planScr0_xDir_zF = planScr0_xDir_zF;

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		this.planScr0_axisX_xI = planScr0_axisX_xI;
		this.planScr0_axisX_yI = planScr0_axisX_yI;
		this.planScr0_axisX_zI = planScr0_axisX_zI;
		this.planScr0_axisX_xF = planScr0_axisX_xF;
		this.planScr0_axisX_yF = planScr0_axisX_yF;
		this.planScr0_axisX_zF = planScr0_axisX_zF;
		//
		//AXIS-Y
		this.planScr0_axisY_xI = planScr0_axisY_xI;
		this.planScr0_axisY_yI = planScr0_axisY_yI;
		this.planScr0_axisY_zI = planScr0_axisY_zI;
		this.planScr0_axisY_xF = planScr0_axisY_xF;
		this.planScr0_axisY_yF = planScr0_axisY_yF;
		this.planScr0_axisY_zF = planScr0_axisY_zF;
		//
		//AXIS-Z
		this.planScr0_axisZ_xI = planScr0_axisZ_xI;
		this.planScr0_axisZ_yI = planScr0_axisZ_yI;
		this.planScr0_axisZ_zI = planScr0_axisZ_zI;
		this.planScr0_axisZ_xF = planScr0_axisZ_xF;
		this.planScr0_axisZ_yF = planScr0_axisZ_yF;
		this.planScr0_axisZ_zF = planScr0_axisZ_zF;

		//PLAN_SIZE
		//
		this.planScr0_width = planScr0_width;
		this.planScr0_height = planScr0_height;

		//PLAN_RATIO
		//
		this.planScr0_ratio = planScr0_ratio;

		//PLAN_LIMITS
		//
		this.planScr0_ptLowerLeftCorner_x = planScr0_ptLowerLeftCorner_x;
		this.planScr0_ptLowerLeftCorner_y = planScr0_ptLowerLeftCorner_y;
		//
		this.planScr0_ptLowerRightCorner_x = planScr0_ptLowerRightCorner_x;
		this.planScr0_ptLowerRightCorner_y = planScr0_ptLowerRightCorner_y;
		//
		this.planScr0_ptUpperRightCorner_x = planScr0_ptUpperRightCorner_x;
		this.planScr0_ptUpperRightCorner_y = planScr0_ptUpperRightCorner_y;
		//
		this.planScr0_ptUpperLeftCorner_x = planScr0_ptUpperLeftCorner_x;
		this.planScr0_ptUpperLeftCorner_y = planScr0_ptUpperLeftCorner_y;
		
		//*** InitialScale
		
		this.scaleScr0 = scaleScr0;
    }

    public void init1_OBS0(
		//*** OBS_MCS0
		
		//PTOBSERVER
		//
		double obsMcs0_ptObserver_x,
		double obsMcs0_ptObserver_y,
		double obsMcs0_ptObserver_z,
		
		//PTOBSERVER
		//
		double obsMcs0_uDir_xI,
		double obsMcs0_uDir_yI,
		double obsMcs0_uDir_zI,
		double obsMcs0_uDir_xF,
		double obsMcs0_uDir_yF,
		double obsMcs0_uDir_zF,
		//
		double obsMcs0_nDir_xI,
		double obsMcs0_nDir_yI,
		double obsMcs0_nDir_zI,
		double obsMcs0_nDir_xF,
		double obsMcs0_nDir_yF,
		double obsMcs0_nDir_zF) 
    {
		//*** OBS_MCS0
		
		//PTOBSERVER
		//
		this.obsMcs0_ptObserver_x = obsMcs0_ptObserver_x;
		this.obsMcs0_ptObserver_y = obsMcs0_ptObserver_y;
		this.obsMcs0_ptObserver_z = obsMcs0_ptObserver_z;
		
		//PTOBSERVER
		//
		this.obsMcs0_uDir_xI = obsMcs0_uDir_xI;
		this.obsMcs0_uDir_yI = obsMcs0_uDir_yI;
		this.obsMcs0_uDir_zI = obsMcs0_uDir_zI;
		this.obsMcs0_uDir_xF = obsMcs0_uDir_xF;
		this.obsMcs0_uDir_yF = obsMcs0_uDir_yF;
		this.obsMcs0_uDir_zF = obsMcs0_uDir_zF;
		//
		this.obsMcs0_nDir_xI = obsMcs0_nDir_xI;
		this.obsMcs0_nDir_yI = obsMcs0_nDir_yI;
		this.obsMcs0_nDir_zI = obsMcs0_nDir_zI;
		this.obsMcs0_nDir_xF = obsMcs0_nDir_xF;
		this.obsMcs0_nDir_yF = obsMcs0_nDir_yF;
		this.obsMcs0_nDir_zF = obsMcs0_nDir_zF;
    }

    public void init2_MCS(
		//*** CurrentView

		//*** PLAN_MCS
		
		//PTORIGIN_MCS
		double planMcs_ucs_ptOriginMcs_x,
		double planMcs_ucs_ptOriginMcs_y,
		double planMcs_ucs_ptOriginMcs_z,
		//
		//XDIR_MCS
		double planMcs_ucs_ptXDirMcs_x,
		double planMcs_ucs_ptXDirMcs_y,
		double planMcs_ucs_ptXDirMcs_z,
		//
		//PTPLAN_MCS
		double planMcs_ucs_ptPlanMcs_x,
		double planMcs_ucs_ptPlanMcs_y,
		double planMcs_ucs_ptPlanMcs_z,
		//
		//XDIR_MCS
		double planMcs_ucs_xDirMcs_xI,
		double planMcs_ucs_xDirMcs_yI,
		double planMcs_ucs_xDirMcs_zI,
		double planMcs_ucs_xDirMcs_xF,
		double planMcs_ucs_xDirMcs_yF,
		double planMcs_ucs_xDirMcs_zF,
		//
		//YDIR_MCS
		double planMcs_ucs_yDirMcs_xI,
		double planMcs_ucs_yDirMcs_yI,
		double planMcs_ucs_yDirMcs_zI,
		double planMcs_ucs_yDirMcs_xF,
		double planMcs_ucs_yDirMcs_yF,
		double planMcs_ucs_yDirMcs_zF,
		//
		//ZDIR_MCS
		double planMcs_ucs_zDirMcs_xI,
		double planMcs_ucs_zDirMcs_yI,
		double planMcs_ucs_zDirMcs_zI,
		double planMcs_ucs_zDirMcs_xF,
		double planMcs_ucs_zDirMcs_yF,
		double planMcs_ucs_zDirMcs_zF,
		//
		//VPLAN_MCS
		double planMcs_ucs_vPlanMcs_xI,
		double planMcs_ucs_vPlanMcs_yI,
		double planMcs_ucs_vPlanMcs_zI,
		double planMcs_ucs_vPlanMcs_xF,
		double planMcs_ucs_vPlanMcs_yF,
		double planMcs_ucs_vPlanMcs_zF,
		//
		//AXIS-X
		double planMcs_ucs_axisX_xI,
		double planMcs_ucs_axisX_yI,
		double planMcs_ucs_axisX_zI,
		double planMcs_ucs_axisX_xF,
		double planMcs_ucs_axisX_yF,
		double planMcs_ucs_axisX_zF,
		//
		//AXIS-Y
		double planMcs_ucs_axisY_xI,
		double planMcs_ucs_axisY_yI,
		double planMcs_ucs_axisY_zI,
		double planMcs_ucs_axisY_xF,
		double planMcs_ucs_axisY_yF,
		double planMcs_ucs_axisY_zF,
		//
		//AXIS-Z
		double planMcs_ucs_axisZ_xI,
		double planMcs_ucs_axisZ_yI,
		double planMcs_ucs_axisZ_zI,
		double planMcs_ucs_axisZ_xF,
		double planMcs_ucs_axisZ_yF,
		double planMcs_ucs_axisZ_zF,

		//PLAN_ORIGIN
		//
		//PTCENTER
		double planMcs_ptCenter_x,
		double planMcs_ptCenter_y,
		double planMcs_ptCenter_z,
		//
		//XDIR
		double planMcs_xDir_xI,
		double planMcs_xDir_yI,
		double planMcs_xDir_zI,
		double planMcs_xDir_xF,
		double planMcs_xDir_yF,
		double planMcs_xDir_zF,

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		double planMcs_axisX_xI,
		double planMcs_axisX_yI,
		double planMcs_axisX_zI,
		double planMcs_axisX_xF,
		double planMcs_axisX_yF,
		double planMcs_axisX_zF,
		//
		//AXIS-Y
		double planMcs_axisY_xI,
		double planMcs_axisY_yI,
		double planMcs_axisY_zI,
		double planMcs_axisY_xF,
		double planMcs_axisY_yF,
		double planMcs_axisY_zF,
		//
		//AXIS-Z
		double planMcs_axisZ_xI,
		double planMcs_axisZ_yI,
		double planMcs_axisZ_zI,
		double planMcs_axisZ_xF,
		double planMcs_axisZ_yF,
		double planMcs_axisZ_zF,

		//PLAN_SIZE
		//
		double planMcs_width,
		double planMcs_height,

		//PLAN_RATIO
		//
		double planMcs_ratio,

		//PLAN_LIMITS
		//
		double planMcs_ptLowerLeftCorner_x,
		double planMcs_ptLowerLeftCorner_y,
		//
		double planMcs_ptLowerRightCorner_x,
		double planMcs_ptLowerRightCorner_y,
		//
		double planMcs_ptUpperRightCorner_x,
		double planMcs_ptUpperRightCorner_y,
		//
		double planMcs_ptUpperLeftCorner_x,
		double planMcs_ptUpperLeftCorner_y,
		
		//*** CurrentLimits
		//
		double limitsMcs_ptMin_x,
		double limitsMcs_ptMin_y,
		//
		double limitsMcs_ptMax_x,
		double limitsMcs_ptMax_y)
    {
		//*** CurrentView

		//*** PLAN_MCS
		
		//PTORIGIN_MCS
		this.planMcs_ucs_ptOriginMcs_x = planMcs_ucs_ptOriginMcs_x;
		this.planMcs_ucs_ptOriginMcs_y = planMcs_ucs_ptOriginMcs_y;
		this.planMcs_ucs_ptOriginMcs_z = planMcs_ucs_ptOriginMcs_z;
		//
		//XDIR_MCS
		this.planMcs_ucs_ptXDirMcs_x = planMcs_ucs_ptXDirMcs_x;
		this.planMcs_ucs_ptXDirMcs_y = planMcs_ucs_ptXDirMcs_y;
		this.planMcs_ucs_ptXDirMcs_z = planMcs_ucs_ptXDirMcs_z;
		//
		//PTPLAN_MCS
		this.planMcs_ucs_ptPlanMcs_x = planMcs_ucs_ptPlanMcs_x;
		this.planMcs_ucs_ptPlanMcs_y = planMcs_ucs_ptPlanMcs_y;
		this.planMcs_ucs_ptPlanMcs_z = planMcs_ucs_ptPlanMcs_z;
		//
		//XDIR_MCS
		this.planMcs_ucs_xDirMcs_xI = planMcs_ucs_xDirMcs_xI;
		this.planMcs_ucs_xDirMcs_yI = planMcs_ucs_xDirMcs_yI;
		this.planMcs_ucs_xDirMcs_zI = planMcs_ucs_xDirMcs_zI;
		this.planMcs_ucs_xDirMcs_xF = planMcs_ucs_xDirMcs_xF;
		this.planMcs_ucs_xDirMcs_yF = planMcs_ucs_xDirMcs_yF;
		this.planMcs_ucs_xDirMcs_zF = planMcs_ucs_xDirMcs_zF;
		//
		//YDIR_MCS
		this.planMcs_ucs_yDirMcs_xI = planMcs_ucs_yDirMcs_xI;
		this.planMcs_ucs_yDirMcs_yI = planMcs_ucs_yDirMcs_yI;
		this.planMcs_ucs_yDirMcs_zI = planMcs_ucs_yDirMcs_zI;
		this.planMcs_ucs_yDirMcs_xF = planMcs_ucs_yDirMcs_xF;
		this.planMcs_ucs_yDirMcs_yF = planMcs_ucs_yDirMcs_yF;
		this.planMcs_ucs_yDirMcs_zF = planMcs_ucs_yDirMcs_zF;
		//
		//ZDIR_MCS
		this.planMcs_ucs_zDirMcs_xI = planMcs_ucs_zDirMcs_xI;
		this.planMcs_ucs_zDirMcs_yI = planMcs_ucs_zDirMcs_yI;
		this.planMcs_ucs_zDirMcs_zI = planMcs_ucs_zDirMcs_zI;
		this.planMcs_ucs_zDirMcs_xF = planMcs_ucs_zDirMcs_xF;
		this.planMcs_ucs_zDirMcs_yF = planMcs_ucs_zDirMcs_yF;
		this.planMcs_ucs_zDirMcs_zF = planMcs_ucs_zDirMcs_zF;
		//
		//VPLAN_MCS
		this.planMcs_ucs_vPlanMcs_xI = planMcs_ucs_vPlanMcs_xI;
		this.planMcs_ucs_vPlanMcs_yI = planMcs_ucs_vPlanMcs_yI;
		this.planMcs_ucs_vPlanMcs_zI = planMcs_ucs_vPlanMcs_zI;
		this.planMcs_ucs_vPlanMcs_xF = planMcs_ucs_vPlanMcs_xF;
		this.planMcs_ucs_vPlanMcs_yF = planMcs_ucs_vPlanMcs_yF;
		this.planMcs_ucs_vPlanMcs_zF = planMcs_ucs_vPlanMcs_zF;
		//
		//AXIS-X
		this.planMcs_ucs_axisX_xI = planMcs_ucs_axisX_xI;
		this.planMcs_ucs_axisX_yI = planMcs_ucs_axisX_yI;
		this.planMcs_ucs_axisX_zI = planMcs_ucs_axisX_zI;
		this.planMcs_ucs_axisX_xF = planMcs_ucs_axisX_xF;
		this.planMcs_ucs_axisX_yF = planMcs_ucs_axisX_yF;
		this.planMcs_ucs_axisX_zF = planMcs_ucs_axisX_zF;
		//
		//AXIS-Y
		this.planMcs_ucs_axisY_xI = planMcs_ucs_axisY_xI;
		this.planMcs_ucs_axisY_yI = planMcs_ucs_axisY_yI;
		this.planMcs_ucs_axisY_zI = planMcs_ucs_axisY_zI;
		this.planMcs_ucs_axisY_xF = planMcs_ucs_axisY_xF;
		this.planMcs_ucs_axisY_yF = planMcs_ucs_axisY_yF;
		this.planMcs_ucs_axisY_zF = planMcs_ucs_axisY_zF;
		//
		//AXIS-Z
		this.planMcs_ucs_axisZ_xI = planMcs_ucs_axisZ_xI;
		this.planMcs_ucs_axisZ_yI = planMcs_ucs_axisZ_yI;
		this.planMcs_ucs_axisZ_zI = planMcs_ucs_axisZ_zI;
		this.planMcs_ucs_axisZ_xF = planMcs_ucs_axisZ_xF;
		this.planMcs_ucs_axisZ_yF = planMcs_ucs_axisZ_yF;
		this.planMcs_ucs_axisZ_zF = planMcs_ucs_axisZ_zF;

		//PLAN_ORIGIN
		//
		//PTCENTER
		this.planMcs_ptCenter_x = planMcs_ptCenter_x;
		this.planMcs_ptCenter_y = planMcs_ptCenter_y;
		this.planMcs_ptCenter_z = planMcs_ptCenter_z;
		//
		//XDIR
		this.planMcs_xDir_xI = planMcs_xDir_xI;
		this.planMcs_xDir_yI = planMcs_xDir_yI;
		this.planMcs_xDir_zI = planMcs_xDir_zI;
		this.planMcs_xDir_xF = planMcs_xDir_xF;
		this.planMcs_xDir_yF = planMcs_xDir_yF;
		this.planMcs_xDir_zF = planMcs_xDir_zF;

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		this.planMcs_axisX_xI = planMcs_axisX_xI;
		this.planMcs_axisX_yI = planMcs_axisX_yI;
		this.planMcs_axisX_zI = planMcs_axisX_zI;
		this.planMcs_axisX_xF = planMcs_axisX_xF;
		this.planMcs_axisX_yF = planMcs_axisX_yF;
		this.planMcs_axisX_zF = planMcs_axisX_zF;
		//
		//AXIS-Y
		this.planMcs_axisY_xI = planMcs_axisY_xI;
		this.planMcs_axisY_yI = planMcs_axisY_yI;
		this.planMcs_axisY_zI = planMcs_axisY_zI;
		this.planMcs_axisY_xF = planMcs_axisY_xF;
		this.planMcs_axisY_yF = planMcs_axisY_yF;
		this.planMcs_axisY_zF = planMcs_axisY_zF;
		//
		//AXIS-Z
		this.planMcs_axisZ_xI = planMcs_axisZ_xI;
		this.planMcs_axisZ_yI = planMcs_axisZ_yI;
		this.planMcs_axisZ_zI = planMcs_axisZ_zI;
		this.planMcs_axisZ_xF = planMcs_axisZ_xF;
		this.planMcs_axisZ_yF = planMcs_axisZ_yF;
		this.planMcs_axisZ_zF = planMcs_axisZ_zF;

		//PLAN_SIZE
		//
		this.planMcs_width = planMcs_width;
		this.planMcs_height = planMcs_height;

		//PLAN_RATIO
		//
		this.planMcs_ratio = planMcs_ratio;

		//PLAN_LIMITS
		//
		this.planMcs_ptLowerLeftCorner_x = planMcs_ptLowerLeftCorner_x;
		this.planMcs_ptLowerLeftCorner_y = planMcs_ptLowerLeftCorner_y;
		//
		this.planMcs_ptLowerRightCorner_x = planMcs_ptLowerRightCorner_x;
		this.planMcs_ptLowerRightCorner_y = planMcs_ptLowerRightCorner_y;
		//
		this.planMcs_ptUpperRightCorner_x = planMcs_ptUpperRightCorner_x;
		this.planMcs_ptUpperRightCorner_y = planMcs_ptUpperRightCorner_y;
		//
		this.planMcs_ptUpperLeftCorner_x = planMcs_ptUpperLeftCorner_x;
		this.planMcs_ptUpperLeftCorner_y = planMcs_ptUpperLeftCorner_y;
		
		//*** CurrentLimits
		//
		this.limitsMcs_ptMin_x = limitsMcs_ptMin_x;
		this.limitsMcs_ptMin_y = limitsMcs_ptMin_y;
		//
		this.limitsMcs_ptMax_x = limitsMcs_ptMax_x;
		this.limitsMcs_ptMax_y = limitsMcs_ptMax_y;
    }

    public void init2_PROJ(
		//*** PLAN_PROJ
		
		//PTORIGIN_MCS
		double planProj_ucs_ptOriginMcs_x,
		double planProj_ucs_ptOriginMcs_y,
		double planProj_ucs_ptOriginMcs_z,
		//
		//XDIR_MCS
		double planProj_ucs_ptXDirMcs_x,
		double planProj_ucs_ptXDirMcs_y,
		double planProj_ucs_ptXDirMcs_z,
		//
		//PTPLAN_MCS
		double planProj_ucs_ptPlanMcs_x,
		double planProj_ucs_ptPlanMcs_y,
		double planProj_ucs_ptPlanMcs_z,
		//
		//XDIR_MCS
		double planProj_ucs_xDirMcs_xI,
		double planProj_ucs_xDirMcs_yI,
		double planProj_ucs_xDirMcs_zI,
		double planProj_ucs_xDirMcs_xF,
		double planProj_ucs_xDirMcs_yF,
		double planProj_ucs_xDirMcs_zF,
		//
		//YDIR_MCS
		double planProj_ucs_yDirMcs_xI,
		double planProj_ucs_yDirMcs_yI,
		double planProj_ucs_yDirMcs_zI,
		double planProj_ucs_yDirMcs_xF,
		double planProj_ucs_yDirMcs_yF,
		double planProj_ucs_yDirMcs_zF,
		//
		//ZDIR_MCS
		double planProj_ucs_zDirMcs_xI,
		double planProj_ucs_zDirMcs_yI,
		double planProj_ucs_zDirMcs_zI,
		double planProj_ucs_zDirMcs_xF,
		double planProj_ucs_zDirMcs_yF,
		double planProj_ucs_zDirMcs_zF,
		//
		//VPLAN_MCS
		double planProj_ucs_vPlanMcs_xI,
		double planProj_ucs_vPlanMcs_yI,
		double planProj_ucs_vPlanMcs_zI,
		double planProj_ucs_vPlanMcs_xF,
		double planProj_ucs_vPlanMcs_yF,
		double planProj_ucs_vPlanMcs_zF,
		//
		//AXIS-X
		double planProj_ucs_axisX_xI,
		double planProj_ucs_axisX_yI,
		double planProj_ucs_axisX_zI,
		double planProj_ucs_axisX_xF,
		double planProj_ucs_axisX_yF,
		double planProj_ucs_axisX_zF,
		//
		//AXIS-Y
		double planProj_ucs_axisY_xI,
		double planProj_ucs_axisY_yI,
		double planProj_ucs_axisY_zI,
		double planProj_ucs_axisY_xF,
		double planProj_ucs_axisY_yF,
		double planProj_ucs_axisY_zF,
		//
		//AXIS-Z
		double planProj_ucs_axisZ_xI,
		double planProj_ucs_axisZ_yI,
		double planProj_ucs_axisZ_zI,
		double planProj_ucs_axisZ_xF,
		double planProj_ucs_axisZ_yF,
		double planProj_ucs_axisZ_zF,

		//PLAN_ORIGIN
		//
		//PTCENTER
		double planProj_ptCenter_x,
		double planProj_ptCenter_y,
		double planProj_ptCenter_z,
		//
		//XDIR
		double planProj_xDir_xI,
		double planProj_xDir_yI,
		double planProj_xDir_zI,
		double planProj_xDir_xF,
		double planProj_xDir_yF,
		double planProj_xDir_zF,

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		double planProj_axisX_xI,
		double planProj_axisX_yI,
		double planProj_axisX_zI,
		double planProj_axisX_xF,
		double planProj_axisX_yF,
		double planProj_axisX_zF,
		//
		//AXIS-Y
		double planProj_axisY_xI,
		double planProj_axisY_yI,
		double planProj_axisY_zI,
		double planProj_axisY_xF,
		double planProj_axisY_yF,
		double planProj_axisY_zF,
		//
		//AXIS-Z
		double planProj_axisZ_xI,
		double planProj_axisZ_yI,
		double planProj_axisZ_zI,
		double planProj_axisZ_xF,
		double planProj_axisZ_yF,
		double planProj_axisZ_zF,

		//PLAN_SIZE
		//
		double planProj_width,
		double planProj_height,

		//PLAN_RATIO
		//
		double planProj_ratio,

		//PLAN_LIMITS
		//
		double planProj_ptLowerLeftCorner_x,
		double planProj_ptLowerLeftCorner_y,
		//
		double planProj_ptLowerRightCorner_x,
		double planProj_ptLowerRightCorner_y,
		//
		double planProj_ptUpperRightCorner_x,
		double planProj_ptUpperRightCorner_y,
		//
		double planProj_ptUpperLeftCorner_x,
		double planProj_ptUpperLeftCorner_y,
		
		//*** CurrentLimits
		//
		double limitsProj_ptMin_x,
		double limitsProj_ptMin_y,
		//
		double limitsProj_ptMax_x,
		double limitsProj_ptMax_y)
    {
		//*** PLAN_PROJ
		
		//PTORIGIN_MCS
		this.planProj_ucs_ptOriginMcs_x = planProj_ucs_ptOriginMcs_x;
		this.planProj_ucs_ptOriginMcs_y = planProj_ucs_ptOriginMcs_y;
		this.planProj_ucs_ptOriginMcs_z = planProj_ucs_ptOriginMcs_z;
		//
		//XDIR_MCS
		this.planProj_ucs_ptXDirMcs_x = planProj_ucs_ptXDirMcs_x;
		this.planProj_ucs_ptXDirMcs_y = planProj_ucs_ptXDirMcs_y;
		this.planProj_ucs_ptXDirMcs_z = planProj_ucs_ptXDirMcs_z;
		//
		//PTPLAN_MCS
		this.planProj_ucs_ptPlanMcs_x = planProj_ucs_ptPlanMcs_x;
		this.planProj_ucs_ptPlanMcs_y = planProj_ucs_ptPlanMcs_y;
		this.planProj_ucs_ptPlanMcs_z = planProj_ucs_ptPlanMcs_z;
		//
		//XDIR_MCS
		this.planProj_ucs_xDirMcs_xI = planProj_ucs_xDirMcs_xI;
		this.planProj_ucs_xDirMcs_yI = planProj_ucs_xDirMcs_yI;
		this.planProj_ucs_xDirMcs_zI = planProj_ucs_xDirMcs_zI;
		this.planProj_ucs_xDirMcs_xF = planProj_ucs_xDirMcs_xF;
		this.planProj_ucs_xDirMcs_yF = planProj_ucs_xDirMcs_yF;
		this.planProj_ucs_xDirMcs_zF = planProj_ucs_xDirMcs_zF;
		//
		//YDIR_MCS
		this.planProj_ucs_yDirMcs_xI = planProj_ucs_yDirMcs_xI;
		this.planProj_ucs_yDirMcs_yI = planProj_ucs_yDirMcs_yI;
		this.planProj_ucs_yDirMcs_zI = planProj_ucs_yDirMcs_zI;
		this.planProj_ucs_yDirMcs_xF = planProj_ucs_yDirMcs_xF;
		this.planProj_ucs_yDirMcs_yF = planProj_ucs_yDirMcs_yF;
		this.planProj_ucs_yDirMcs_zF = planProj_ucs_yDirMcs_zF;
		//
		//ZDIR_MCS
		this.planProj_ucs_zDirMcs_xI = planProj_ucs_zDirMcs_xI;
		this.planProj_ucs_zDirMcs_yI = planProj_ucs_zDirMcs_yI;
		this.planProj_ucs_zDirMcs_zI = planProj_ucs_zDirMcs_zI;
		this.planProj_ucs_zDirMcs_xF = planProj_ucs_zDirMcs_xF;
		this.planProj_ucs_zDirMcs_yF = planProj_ucs_zDirMcs_yF;
		this.planProj_ucs_zDirMcs_zF = planProj_ucs_zDirMcs_zF;
		//
		//VPLAN_MCS
		this.planProj_ucs_vPlanMcs_xI = planProj_ucs_vPlanMcs_xI;
		this.planProj_ucs_vPlanMcs_yI = planProj_ucs_vPlanMcs_yI;
		this.planProj_ucs_vPlanMcs_zI = planProj_ucs_vPlanMcs_zI;
		this.planProj_ucs_vPlanMcs_xF = planProj_ucs_vPlanMcs_xF;
		this.planProj_ucs_vPlanMcs_yF = planProj_ucs_vPlanMcs_yF;
		this.planProj_ucs_vPlanMcs_zF = planProj_ucs_vPlanMcs_zF;
		//
		//AXIS-X
		this.planProj_ucs_axisX_xI = planProj_ucs_axisX_xI;
		this.planProj_ucs_axisX_yI = planProj_ucs_axisX_yI;
		this.planProj_ucs_axisX_zI = planProj_ucs_axisX_zI;
		this.planProj_ucs_axisX_xF = planProj_ucs_axisX_xF;
		this.planProj_ucs_axisX_yF = planProj_ucs_axisX_yF;
		this.planProj_ucs_axisX_zF = planProj_ucs_axisX_zF;
		//
		//AXIS-Y
		this.planProj_ucs_axisY_xI = planProj_ucs_axisY_xI;
		this.planProj_ucs_axisY_yI = planProj_ucs_axisY_yI;
		this.planProj_ucs_axisY_zI = planProj_ucs_axisY_zI;
		this.planProj_ucs_axisY_xF = planProj_ucs_axisY_xF;
		this.planProj_ucs_axisY_yF = planProj_ucs_axisY_yF;
		this.planProj_ucs_axisY_zF = planProj_ucs_axisY_zF;
		//
		//AXIS-Z
		this.planProj_ucs_axisZ_xI = planProj_ucs_axisZ_xI;
		this.planProj_ucs_axisZ_yI = planProj_ucs_axisZ_yI;
		this.planProj_ucs_axisZ_zI = planProj_ucs_axisZ_zI;
		this.planProj_ucs_axisZ_xF = planProj_ucs_axisZ_xF;
		this.planProj_ucs_axisZ_yF = planProj_ucs_axisZ_yF;
		this.planProj_ucs_axisZ_zF = planProj_ucs_axisZ_zF;

		//PLAN_ORIGIN
		//
		//PTCENTER
		this.planProj_ptCenter_x = planProj_ptCenter_x;
		this.planProj_ptCenter_y = planProj_ptCenter_y;
		this.planProj_ptCenter_z = planProj_ptCenter_z;
		//
		//XDIR
		this.planProj_xDir_xI = planProj_xDir_xI;
		this.planProj_xDir_yI = planProj_xDir_yI;
		this.planProj_xDir_zI = planProj_xDir_zI;
		this.planProj_xDir_xF = planProj_xDir_xF;
		this.planProj_xDir_yF = planProj_xDir_yF;
		this.planProj_xDir_zF = planProj_xDir_zF;

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		this.planProj_axisX_xI = planProj_axisX_xI;
		this.planProj_axisX_yI = planProj_axisX_yI;
		this.planProj_axisX_zI = planProj_axisX_zI;
		this.planProj_axisX_xF = planProj_axisX_xF;
		this.planProj_axisX_yF = planProj_axisX_yF;
		this.planProj_axisX_zF = planProj_axisX_zF;
		//
		//AXIS-Y
		this.planProj_axisY_xI = planProj_axisY_xI;
		this.planProj_axisY_yI = planProj_axisY_yI;
		this.planProj_axisY_zI = planProj_axisY_zI;
		this.planProj_axisY_xF = planProj_axisY_xF;
		this.planProj_axisY_yF = planProj_axisY_yF;
		this.planProj_axisY_zF = planProj_axisY_zF;
		//
		//AXIS-Z
		this.planProj_axisZ_xI = planProj_axisZ_xI;
		this.planProj_axisZ_yI = planProj_axisZ_yI;
		this.planProj_axisZ_zI = planProj_axisZ_zI;
		this.planProj_axisZ_xF = planProj_axisZ_xF;
		this.planProj_axisZ_yF = planProj_axisZ_yF;
		this.planProj_axisZ_zF = planProj_axisZ_zF;

		//PLAN_SIZE
		//
		this.planProj_width = planProj_width;
		this.planProj_height = planProj_height;

		//PLAN_RATIO
		//
		this.planProj_ratio = planProj_ratio;

		//PLAN_LIMITS
		//
		this.planProj_ptLowerLeftCorner_x = planProj_ptLowerLeftCorner_x;
		this.planProj_ptLowerLeftCorner_y = planProj_ptLowerLeftCorner_y;
		//
		this.planProj_ptLowerRightCorner_x = planProj_ptLowerRightCorner_x;
		this.planProj_ptLowerRightCorner_y = planProj_ptLowerRightCorner_y;
		//
		this.planProj_ptUpperRightCorner_x = planProj_ptUpperRightCorner_x;
		this.planProj_ptUpperRightCorner_y = planProj_ptUpperRightCorner_y;
		//
		this.planProj_ptUpperLeftCorner_x = planProj_ptUpperLeftCorner_x;
		this.planProj_ptUpperLeftCorner_y = planProj_ptUpperLeftCorner_y;
		
		//*** CurrentLimits
		//
		this.limitsProj_ptMin_x = limitsProj_ptMin_x;
		this.limitsProj_ptMin_y = limitsProj_ptMin_y;
		//
		this.limitsProj_ptMax_x = limitsProj_ptMax_x;
		this.limitsProj_ptMax_y = limitsProj_ptMax_y;
    }

    public void init2_SCR(
		//*** PLAN_SCR
			
		//PTORIGIN_MCS
		double planScr_ucs_ptOriginMcs_x,
		double planScr_ucs_ptOriginMcs_y,
		double planScr_ucs_ptOriginMcs_z,
		//
		//XDIR_MCS
		double planScr_ucs_ptXDirMcs_x,
		double planScr_ucs_ptXDirMcs_y,
		double planScr_ucs_ptXDirMcs_z,
		//
		//PTPLAN_MCS
		double planScr_ucs_ptPlanScr_x,
		double planScr_ucs_ptPlanScr_y,
		double planScr_ucs_ptPlanScr_z,
		//
		//XDIR_MCS
		double planScr_ucs_xDirMcs_xI,
		double planScr_ucs_xDirMcs_yI,
		double planScr_ucs_xDirMcs_zI,
		double planScr_ucs_xDirMcs_xF,
		double planScr_ucs_xDirMcs_yF,
		double planScr_ucs_xDirMcs_zF,
		//
		//YDIR_MCS
		double planScr_ucs_yDirMcs_xI,
		double planScr_ucs_yDirMcs_yI,
		double planScr_ucs_yDirMcs_zI,
		double planScr_ucs_yDirMcs_xF,
		double planScr_ucs_yDirMcs_yF,
		double planScr_ucs_yDirMcs_zF,
		//
		//ZDIR_MCS
		double planScr_ucs_zDirMcs_xI,
		double planScr_ucs_zDirMcs_yI,
		double planScr_ucs_zDirMcs_zI,
		double planScr_ucs_zDirMcs_xF,
		double planScr_ucs_zDirMcs_yF,
		double planScr_ucs_zDirMcs_zF,
		//
		//VPLAN_MCS
		double planScr_ucs_vPlanScr_xI,
		double planScr_ucs_vPlanScr_yI,
		double planScr_ucs_vPlanScr_zI,
		double planScr_ucs_vPlanScr_xF,
		double planScr_ucs_vPlanScr_yF,
		double planScr_ucs_vPlanScr_zF,
		//
		//AXIS-X
		double planScr_ucs_axisX_xI,
		double planScr_ucs_axisX_yI,
		double planScr_ucs_axisX_zI,
		double planScr_ucs_axisX_xF,
		double planScr_ucs_axisX_yF,
		double planScr_ucs_axisX_zF,
		//
		//AXIS-Y
		double planScr_ucs_axisY_xI,
		double planScr_ucs_axisY_yI,
		double planScr_ucs_axisY_zI,
		double planScr_ucs_axisY_xF,
		double planScr_ucs_axisY_yF,
		double planScr_ucs_axisY_zF,
		//
		//AXIS-Z
		double planScr_ucs_axisZ_xI,
		double planScr_ucs_axisZ_yI,
		double planScr_ucs_axisZ_zI,
		double planScr_ucs_axisZ_xF,
		double planScr_ucs_axisZ_yF,
		double planScr_ucs_axisZ_zF,

		//PLAN_ORIGIN
		//
		//PTCENTER
		double planScr_ptCenter_x,
		double planScr_ptCenter_y,
		double planScr_ptCenter_z,
		//
		//XDIR
		double planScr_xDir_xI,
		double planScr_xDir_yI,
		double planScr_xDir_zI,
		double planScr_xDir_xF,
		double planScr_xDir_yF,
		double planScr_xDir_zF,

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		double planScr_axisX_xI,
		double planScr_axisX_yI,
		double planScr_axisX_zI,
		double planScr_axisX_xF,
		double planScr_axisX_yF,
		double planScr_axisX_zF,
		//
		//AXIS-Y
		double planScr_axisY_xI,
		double planScr_axisY_yI,
		double planScr_axisY_zI,
		double planScr_axisY_xF,
		double planScr_axisY_yF,
		double planScr_axisY_zF,
		//
		//AXIS-Z
		double planScr_axisZ_xI,
		double planScr_axisZ_yI,
		double planScr_axisZ_zI,
		double planScr_axisZ_xF,
		double planScr_axisZ_yF,
		double planScr_axisZ_zF,

		//PLAN_SIZE
		//
		double planScr_width,
		double planScr_height,

		//PLAN_RATIO
		//
		double planScr_ratio,

		//PLAN_LIMITS
		//
		double planScr_ptLowerLeftCorner_x,
		double planScr_ptLowerLeftCorner_y,
		//
		double planScr_ptLowerRightCorner_x,
		double planScr_ptLowerRightCorner_y,
		//
		double planScr_ptUpperRightCorner_x,
		double planScr_ptUpperRightCorner_y,
		//
		double planScr_ptUpperLeftCorner_x,
		double planScr_ptUpperLeftCorner_y )
    {
		//*** PLAN_SCR
		
		//PTORIGIN_MCS
		this.planScr_ucs_ptOriginMcs_x = planScr_ucs_ptOriginMcs_x;
		this.planScr_ucs_ptOriginMcs_y = planScr_ucs_ptOriginMcs_y;
		this.planScr_ucs_ptOriginMcs_z = planScr_ucs_ptOriginMcs_z;
		//
		//XDIR_MCS
		this.planScr_ucs_ptXDirMcs_x = planScr_ucs_ptXDirMcs_x;
		this.planScr_ucs_ptXDirMcs_y = planScr_ucs_ptXDirMcs_y;
		this.planScr_ucs_ptXDirMcs_z = planScr_ucs_ptXDirMcs_z;
		//
		//PTPLAN_MCS
		this.planScr_ucs_ptPlanScr_x = planScr_ucs_ptPlanScr_x;
		this.planScr_ucs_ptPlanScr_y = planScr_ucs_ptPlanScr_y;
		this.planScr_ucs_ptPlanScr_z = planScr_ucs_ptPlanScr_z;
		//
		//XDIR_MCS
		this.planScr_ucs_xDirMcs_xI = planScr_ucs_xDirMcs_xI;
		this.planScr_ucs_xDirMcs_yI = planScr_ucs_xDirMcs_yI;
		this.planScr_ucs_xDirMcs_zI = planScr_ucs_xDirMcs_zI;
		this.planScr_ucs_xDirMcs_xF = planScr_ucs_xDirMcs_xF;
		this.planScr_ucs_xDirMcs_yF = planScr_ucs_xDirMcs_yF;
		this.planScr_ucs_xDirMcs_zF = planScr_ucs_xDirMcs_zF;
		//
		//YDIR_MCS
		this.planScr_ucs_yDirMcs_xI = planScr_ucs_yDirMcs_xI;
		this.planScr_ucs_yDirMcs_yI = planScr_ucs_yDirMcs_yI;
		this.planScr_ucs_yDirMcs_zI = planScr_ucs_yDirMcs_zI;
		this.planScr_ucs_yDirMcs_xF = planScr_ucs_yDirMcs_xF;
		this.planScr_ucs_yDirMcs_yF = planScr_ucs_yDirMcs_yF;
		this.planScr_ucs_yDirMcs_zF = planScr_ucs_yDirMcs_zF;
		//
		//ZDIR_MCS
		this.planScr_ucs_zDirMcs_xI = planScr_ucs_zDirMcs_xI;
		this.planScr_ucs_zDirMcs_yI = planScr_ucs_zDirMcs_yI;
		this.planScr_ucs_zDirMcs_zI = planScr_ucs_zDirMcs_zI;
		this.planScr_ucs_zDirMcs_xF = planScr_ucs_zDirMcs_xF;
		this.planScr_ucs_zDirMcs_yF = planScr_ucs_zDirMcs_yF;
		this.planScr_ucs_zDirMcs_zF = planScr_ucs_zDirMcs_zF;
		//
		//VPLAN_MCS
		this.planScr_ucs_vPlanScr_xI = planScr_ucs_vPlanScr_xI;
		this.planScr_ucs_vPlanScr_yI = planScr_ucs_vPlanScr_yI;
		this.planScr_ucs_vPlanScr_zI = planScr_ucs_vPlanScr_zI;
		this.planScr_ucs_vPlanScr_xF = planScr_ucs_vPlanScr_xF;
		this.planScr_ucs_vPlanScr_yF = planScr_ucs_vPlanScr_yF;
		this.planScr_ucs_vPlanScr_zF = planScr_ucs_vPlanScr_zF;
		//
		//AXIS-X
		this.planScr_ucs_axisX_xI = planScr_ucs_axisX_xI;
		this.planScr_ucs_axisX_yI = planScr_ucs_axisX_yI;
		this.planScr_ucs_axisX_zI = planScr_ucs_axisX_zI;
		this.planScr_ucs_axisX_xF = planScr_ucs_axisX_xF;
		this.planScr_ucs_axisX_yF = planScr_ucs_axisX_yF;
		this.planScr_ucs_axisX_zF = planScr_ucs_axisX_zF;
		//
		//AXIS-Y
		this.planScr_ucs_axisY_xI = planScr_ucs_axisY_xI;
		this.planScr_ucs_axisY_yI = planScr_ucs_axisY_yI;
		this.planScr_ucs_axisY_zI = planScr_ucs_axisY_zI;
		this.planScr_ucs_axisY_xF = planScr_ucs_axisY_xF;
		this.planScr_ucs_axisY_yF = planScr_ucs_axisY_yF;
		this.planScr_ucs_axisY_zF = planScr_ucs_axisY_zF;
		//
		//AXIS-Z
		this.planScr_ucs_axisZ_xI = planScr_ucs_axisZ_xI;
		this.planScr_ucs_axisZ_yI = planScr_ucs_axisZ_yI;
		this.planScr_ucs_axisZ_zI = planScr_ucs_axisZ_zI;
		this.planScr_ucs_axisZ_xF = planScr_ucs_axisZ_xF;
		this.planScr_ucs_axisZ_yF = planScr_ucs_axisZ_yF;
		this.planScr_ucs_axisZ_zF = planScr_ucs_axisZ_zF;

		//PLAN_ORIGIN
		//
		//PTCENTER
		this.planScr_ptCenter_x = planScr_ptCenter_x;
		this.planScr_ptCenter_y = planScr_ptCenter_y;
		this.planScr_ptCenter_z = planScr_ptCenter_z;
		//
		//XDIR
		this.planScr_xDir_xI = planScr_xDir_xI;
		this.planScr_xDir_yI = planScr_xDir_yI;
		this.planScr_xDir_zI = planScr_xDir_zI;
		this.planScr_xDir_xF = planScr_xDir_xF;
		this.planScr_xDir_yF = planScr_xDir_yF;
		this.planScr_xDir_zF = planScr_xDir_zF;

		//AXIS_DIRECTIONS
		//
		//AXIS-X
		this.planScr_axisX_xI = planScr_axisX_xI;
		this.planScr_axisX_yI = planScr_axisX_yI;
		this.planScr_axisX_zI = planScr_axisX_zI;
		this.planScr_axisX_xF = planScr_axisX_xF;
		this.planScr_axisX_yF = planScr_axisX_yF;
		this.planScr_axisX_zF = planScr_axisX_zF;
		//
		//AXIS-Y
		this.planScr_axisY_xI = planScr_axisY_xI;
		this.planScr_axisY_yI = planScr_axisY_yI;
		this.planScr_axisY_zI = planScr_axisY_zI;
		this.planScr_axisY_xF = planScr_axisY_xF;
		this.planScr_axisY_yF = planScr_axisY_yF;
		this.planScr_axisY_zF = planScr_axisY_zF;
		//
		//AXIS-Z
		this.planScr_axisZ_xI = planScr_axisZ_xI;
		this.planScr_axisZ_yI = planScr_axisZ_yI;
		this.planScr_axisZ_zI = planScr_axisZ_zI;
		this.planScr_axisZ_xF = planScr_axisZ_xF;
		this.planScr_axisZ_yF = planScr_axisZ_yF;
		this.planScr_axisZ_zF = planScr_axisZ_zF;

		//PLAN_SIZE
		//
		this.planScr_width = planScr_width;
		this.planScr_height = planScr_height;

		//PLAN_RATIO
		//
		this.planScr_ratio = planScr_ratio;

		//PLAN_LIMITS
		//
		this.planScr_ptLowerLeftCorner_x = planScr_ptLowerLeftCorner_x;
		this.planScr_ptLowerLeftCorner_y = planScr_ptLowerLeftCorner_y;
		//
		this.planScr_ptLowerRightCorner_x = planScr_ptLowerRightCorner_x;
		this.planScr_ptLowerRightCorner_y = planScr_ptLowerRightCorner_y;
		//
		this.planScr_ptUpperRightCorner_x = planScr_ptUpperRightCorner_x;
		this.planScr_ptUpperRightCorner_y = planScr_ptUpperRightCorner_y;
		//
		this.planScr_ptUpperLeftCorner_x = planScr_ptUpperLeftCorner_x;
		this.planScr_ptUpperLeftCorner_y = planScr_ptUpperLeftCorner_y;
    }

	public void init2_OBS(
		//*** OBS_MCS
		
		//PTOBSERVER
		//
		double obsMcs_ptObserver_x,
		double obsMcs_ptObserver_y,
		double obsMcs_ptObserver_z,
		
		//PTOBSERVER
		//
		double obsMcs_uDir_xI,
		double obsMcs_uDir_yI,
		double obsMcs_uDir_zI,
		double obsMcs_uDir_xF,
		double obsMcs_uDir_yF,
		double obsMcs_uDir_zF,
		//
		double obsMcs_nDir_xI,
		double obsMcs_nDir_yI,
		double obsMcs_nDir_zI,
		double obsMcs_nDir_xF,
		double obsMcs_nDir_yF,
		double obsMcs_nDir_zF)
    {
		//*** OBS_MCS
		
		//PTOBSERVER
		//
		this.obsMcs_ptObserver_x = obsMcs_ptObserver_x;
		this.obsMcs_ptObserver_y = obsMcs_ptObserver_y;
		this.obsMcs_ptObserver_z = obsMcs_ptObserver_z;
		
		//PTOBSERVER
		//
		this.obsMcs_uDir_xI = obsMcs_uDir_xI;
		this.obsMcs_uDir_yI = obsMcs_uDir_yI;
		this.obsMcs_uDir_zI = obsMcs_uDir_zI;
		this.obsMcs_uDir_xF = obsMcs_uDir_xF;
		this.obsMcs_uDir_yF = obsMcs_uDir_yF;
		this.obsMcs_uDir_zF = obsMcs_uDir_zF;
		//
		this.obsMcs_nDir_xI = obsMcs_nDir_xI;
		this.obsMcs_nDir_yI = obsMcs_nDir_yI;
		this.obsMcs_nDir_zI = obsMcs_nDir_zI;
		this.obsMcs_nDir_xF = obsMcs_nDir_xF;
		this.obsMcs_nDir_yF = obsMcs_nDir_yF;
		this.obsMcs_nDir_zF = obsMcs_nDir_zF;
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
	    return null;
	}

    /* Getters/Setters */

	public double getPlanMcs0_ucs_ptOriginMcs_x() {
		return planMcs0_ucs_ptOriginMcs_x;
	}

	public void setPlanMcs0_ucs_ptOriginMcs_x(double planMcs0_ucs_ptOriginMcs_x) {
		this.planMcs0_ucs_ptOriginMcs_x = planMcs0_ucs_ptOriginMcs_x;
	}

	public double getPlanMcs0_ucs_ptOriginMcs_y() {
		return planMcs0_ucs_ptOriginMcs_y;
	}

	public void setPlanMcs0_ucs_ptOriginMcs_y(double planMcs0_ucs_ptOriginMcs_y) {
		this.planMcs0_ucs_ptOriginMcs_y = planMcs0_ucs_ptOriginMcs_y;
	}

	public double getPlanMcs0_ucs_ptOriginMcs_z() {
		return planMcs0_ucs_ptOriginMcs_z;
	}

	public void setPlanMcs0_ucs_ptOriginMcs_z(double planMcs0_ucs_ptOriginMcs_z) {
		this.planMcs0_ucs_ptOriginMcs_z = planMcs0_ucs_ptOriginMcs_z;
	}

	public double getPlanMcs0_ucs_ptXDirMcs_x() {
		return planMcs0_ucs_ptXDirMcs_x;
	}

	public void setPlanMcs0_ucs_ptXDirMcs_x(double planMcs0_ucs_ptXDirMcs_x) {
		this.planMcs0_ucs_ptXDirMcs_x = planMcs0_ucs_ptXDirMcs_x;
	}

	public double getPlanMcs0_ucs_ptXDirMcs_y() {
		return planMcs0_ucs_ptXDirMcs_y;
	}

	public void setPlanMcs0_ucs_ptXDirMcs_y(double planMcs0_ucs_ptXDirMcs_y) {
		this.planMcs0_ucs_ptXDirMcs_y = planMcs0_ucs_ptXDirMcs_y;
	}

	public double getPlanMcs0_ucs_ptXDirMcs_z() {
		return planMcs0_ucs_ptXDirMcs_z;
	}

	public void setPlanMcs0_ucs_ptXDirMcs_z(double planMcs0_ucs_ptXDirMcs_z) {
		this.planMcs0_ucs_ptXDirMcs_z = planMcs0_ucs_ptXDirMcs_z;
	}

	public double getPlanMcs0_ucs_ptPlanMcs_x() {
		return planMcs0_ucs_ptPlanMcs_x;
	}

	public void setPlanMcs0_ucs_ptPlanMcs_x(double planMcs0_ucs_ptPlanMcs_x) {
		this.planMcs0_ucs_ptPlanMcs_x = planMcs0_ucs_ptPlanMcs_x;
	}

	public double getPlanMcs0_ucs_ptPlanMcs_y() {
		return planMcs0_ucs_ptPlanMcs_y;
	}

	public void setPlanMcs0_ucs_ptPlanMcs_y(double planMcs0_ucs_ptPlanMcs_y) {
		this.planMcs0_ucs_ptPlanMcs_y = planMcs0_ucs_ptPlanMcs_y;
	}

	public double getPlanMcs0_ucs_ptPlanMcs_z() {
		return planMcs0_ucs_ptPlanMcs_z;
	}

	public void setPlanMcs0_ucs_ptPlanMcs_z(double planMcs0_ucs_ptPlanMcs_z) {
		this.planMcs0_ucs_ptPlanMcs_z = planMcs0_ucs_ptPlanMcs_z;
	}

	public double getPlanMcs0_ucs_xDirMcs_xI() {
		return planMcs0_ucs_xDirMcs_xI;
	}

	public void setPlanMcs0_ucs_xDirMcs_xI(double planMcs0_ucs_xDirMcs_xI) {
		this.planMcs0_ucs_xDirMcs_xI = planMcs0_ucs_xDirMcs_xI;
	}

	public double getPlanMcs0_ucs_xDirMcs_yI() {
		return planMcs0_ucs_xDirMcs_yI;
	}

	public void setPlanMcs0_ucs_xDirMcs_yI(double planMcs0_ucs_xDirMcs_yI) {
		this.planMcs0_ucs_xDirMcs_yI = planMcs0_ucs_xDirMcs_yI;
	}

	public double getPlanMcs0_ucs_xDirMcs_zI() {
		return planMcs0_ucs_xDirMcs_zI;
	}

	public void setPlanMcs0_ucs_xDirMcs_zI(double planMcs0_ucs_xDirMcs_zI) {
		this.planMcs0_ucs_xDirMcs_zI = planMcs0_ucs_xDirMcs_zI;
	}

	public double getPlanMcs0_ucs_xDirMcs_xF() {
		return planMcs0_ucs_xDirMcs_xF;
	}

	public void setPlanMcs0_ucs_xDirMcs_xF(double planMcs0_ucs_xDirMcs_xF) {
		this.planMcs0_ucs_xDirMcs_xF = planMcs0_ucs_xDirMcs_xF;
	}

	public double getPlanMcs0_ucs_xDirMcs_yF() {
		return planMcs0_ucs_xDirMcs_yF;
	}

	public void setPlanMcs0_ucs_xDirMcs_yF(double planMcs0_ucs_xDirMcs_yF) {
		this.planMcs0_ucs_xDirMcs_yF = planMcs0_ucs_xDirMcs_yF;
	}

	public double getPlanMcs0_ucs_xDirMcs_zF() {
		return planMcs0_ucs_xDirMcs_zF;
	}

	public void setPlanMcs0_ucs_xDirMcs_zF(double planMcs0_ucs_xDirMcs_zF) {
		this.planMcs0_ucs_xDirMcs_zF = planMcs0_ucs_xDirMcs_zF;
	}

	public double getPlanMcs0_ucs_yDirMcs_xI() {
		return planMcs0_ucs_yDirMcs_xI;
	}

	public void setPlanMcs0_ucs_yDirMcs_xI(double planMcs0_ucs_yDirMcs_xI) {
		this.planMcs0_ucs_yDirMcs_xI = planMcs0_ucs_yDirMcs_xI;
	}

	public double getPlanMcs0_ucs_yDirMcs_yI() {
		return planMcs0_ucs_yDirMcs_yI;
	}

	public void setPlanMcs0_ucs_yDirMcs_yI(double planMcs0_ucs_yDirMcs_yI) {
		this.planMcs0_ucs_yDirMcs_yI = planMcs0_ucs_yDirMcs_yI;
	}

	public double getPlanMcs0_ucs_yDirMcs_zI() {
		return planMcs0_ucs_yDirMcs_zI;
	}

	public void setPlanMcs0_ucs_yDirMcs_zI(double planMcs0_ucs_yDirMcs_zI) {
		this.planMcs0_ucs_yDirMcs_zI = planMcs0_ucs_yDirMcs_zI;
	}

	public double getPlanMcs0_ucs_yDirMcs_xF() {
		return planMcs0_ucs_yDirMcs_xF;
	}

	public void setPlanMcs0_ucs_yDirMcs_xF(double planMcs0_ucs_yDirMcs_xF) {
		this.planMcs0_ucs_yDirMcs_xF = planMcs0_ucs_yDirMcs_xF;
	}

	public double getPlanMcs0_ucs_yDirMcs_yF() {
		return planMcs0_ucs_yDirMcs_yF;
	}

	public void setPlanMcs0_ucs_yDirMcs_yF(double planMcs0_ucs_yDirMcs_yF) {
		this.planMcs0_ucs_yDirMcs_yF = planMcs0_ucs_yDirMcs_yF;
	}

	public double getPlanMcs0_ucs_yDirMcs_zF() {
		return planMcs0_ucs_yDirMcs_zF;
	}

	public void setPlanMcs0_ucs_yDirMcs_zF(double planMcs0_ucs_yDirMcs_zF) {
		this.planMcs0_ucs_yDirMcs_zF = planMcs0_ucs_yDirMcs_zF;
	}

	public double getPlanMcs0_ucs_zDirMcs_xI() {
		return planMcs0_ucs_zDirMcs_xI;
	}

	public void setPlanMcs0_ucs_zDirMcs_xI(double planMcs0_ucs_zDirMcs_xI) {
		this.planMcs0_ucs_zDirMcs_xI = planMcs0_ucs_zDirMcs_xI;
	}

	public double getPlanMcs0_ucs_zDirMcs_yI() {
		return planMcs0_ucs_zDirMcs_yI;
	}

	public void setPlanMcs0_ucs_zDirMcs_yI(double planMcs0_ucs_zDirMcs_yI) {
		this.planMcs0_ucs_zDirMcs_yI = planMcs0_ucs_zDirMcs_yI;
	}

	public double getPlanMcs0_ucs_zDirMcs_zI() {
		return planMcs0_ucs_zDirMcs_zI;
	}

	public void setPlanMcs0_ucs_zDirMcs_zI(double planMcs0_ucs_zDirMcs_zI) {
		this.planMcs0_ucs_zDirMcs_zI = planMcs0_ucs_zDirMcs_zI;
	}

	public double getPlanMcs0_ucs_zDirMcs_xF() {
		return planMcs0_ucs_zDirMcs_xF;
	}

	public void setPlanMcs0_ucs_zDirMcs_xF(double planMcs0_ucs_zDirMcs_xF) {
		this.planMcs0_ucs_zDirMcs_xF = planMcs0_ucs_zDirMcs_xF;
	}

	public double getPlanMcs0_ucs_zDirMcs_yF() {
		return planMcs0_ucs_zDirMcs_yF;
	}

	public void setPlanMcs0_ucs_zDirMcs_yF(double planMcs0_ucs_zDirMcs_yF) {
		this.planMcs0_ucs_zDirMcs_yF = planMcs0_ucs_zDirMcs_yF;
	}

	public double getPlanMcs0_ucs_zDirMcs_zF() {
		return planMcs0_ucs_zDirMcs_zF;
	}

	public void setPlanMcs0_ucs_zDirMcs_zF(double planMcs0_ucs_zDirMcs_zF) {
		this.planMcs0_ucs_zDirMcs_zF = planMcs0_ucs_zDirMcs_zF;
	}

	public double getPlanMcs0_ucs_vPlanMcs_xI() {
		return planMcs0_ucs_vPlanMcs_xI;
	}

	public void setPlanMcs0_ucs_vPlanMcs_xI(double planMcs0_ucs_vPlanMcs_xI) {
		this.planMcs0_ucs_vPlanMcs_xI = planMcs0_ucs_vPlanMcs_xI;
	}

	public double getPlanMcs0_ucs_vPlanMcs_yI() {
		return planMcs0_ucs_vPlanMcs_yI;
	}

	public void setPlanMcs0_ucs_vPlanMcs_yI(double planMcs0_ucs_vPlanMcs_yI) {
		this.planMcs0_ucs_vPlanMcs_yI = planMcs0_ucs_vPlanMcs_yI;
	}

	public double getPlanMcs0_ucs_vPlanMcs_zI() {
		return planMcs0_ucs_vPlanMcs_zI;
	}

	public void setPlanMcs0_ucs_vPlanMcs_zI(double planMcs0_ucs_vPlanMcs_zI) {
		this.planMcs0_ucs_vPlanMcs_zI = planMcs0_ucs_vPlanMcs_zI;
	}

	public double getPlanMcs0_ucs_vPlanMcs_xF() {
		return planMcs0_ucs_vPlanMcs_xF;
	}

	public void setPlanMcs0_ucs_vPlanMcs_xF(double planMcs0_ucs_vPlanMcs_xF) {
		this.planMcs0_ucs_vPlanMcs_xF = planMcs0_ucs_vPlanMcs_xF;
	}

	public double getPlanMcs0_ucs_vPlanMcs_yF() {
		return planMcs0_ucs_vPlanMcs_yF;
	}

	public void setPlanMcs0_ucs_vPlanMcs_yF(double planMcs0_ucs_vPlanMcs_yF) {
		this.planMcs0_ucs_vPlanMcs_yF = planMcs0_ucs_vPlanMcs_yF;
	}

	public double getPlanMcs0_ucs_vPlanMcs_zF() {
		return planMcs0_ucs_vPlanMcs_zF;
	}

	public void setPlanMcs0_ucs_vPlanMcs_zF(double planMcs0_ucs_vPlanMcs_zF) {
		this.planMcs0_ucs_vPlanMcs_zF = planMcs0_ucs_vPlanMcs_zF;
	}

	public double getPlanMcs0_ucs_axisX_xI() {
		return planMcs0_ucs_axisX_xI;
	}

	public void setPlanMcs0_ucs_axisX_xI(double planMcs0_ucs_axisX_xI) {
		this.planMcs0_ucs_axisX_xI = planMcs0_ucs_axisX_xI;
	}

	public double getPlanMcs0_ucs_axisX_yI() {
		return planMcs0_ucs_axisX_yI;
	}

	public void setPlanMcs0_ucs_axisX_yI(double planMcs0_ucs_axisX_yI) {
		this.planMcs0_ucs_axisX_yI = planMcs0_ucs_axisX_yI;
	}

	public double getPlanMcs0_ucs_axisX_zI() {
		return planMcs0_ucs_axisX_zI;
	}

	public void setPlanMcs0_ucs_axisX_zI(double planMcs0_ucs_axisX_zI) {
		this.planMcs0_ucs_axisX_zI = planMcs0_ucs_axisX_zI;
	}

	public double getPlanMcs0_ucs_axisX_xF() {
		return planMcs0_ucs_axisX_xF;
	}

	public void setPlanMcs0_ucs_axisX_xF(double planMcs0_ucs_axisX_xF) {
		this.planMcs0_ucs_axisX_xF = planMcs0_ucs_axisX_xF;
	}

	public double getPlanMcs0_ucs_axisX_yF() {
		return planMcs0_ucs_axisX_yF;
	}

	public void setPlanMcs0_ucs_axisX_yF(double planMcs0_ucs_axisX_yF) {
		this.planMcs0_ucs_axisX_yF = planMcs0_ucs_axisX_yF;
	}

	public double getPlanMcs0_ucs_axisX_zF() {
		return planMcs0_ucs_axisX_zF;
	}

	public void setPlanMcs0_ucs_axisX_zF(double planMcs0_ucs_axisX_zF) {
		this.planMcs0_ucs_axisX_zF = planMcs0_ucs_axisX_zF;
	}

	public double getPlanMcs0_ucs_axisY_xI() {
		return planMcs0_ucs_axisY_xI;
	}

	public void setPlanMcs0_ucs_axisY_xI(double planMcs0_ucs_axisY_xI) {
		this.planMcs0_ucs_axisY_xI = planMcs0_ucs_axisY_xI;
	}

	public double getPlanMcs0_ucs_axisY_yI() {
		return planMcs0_ucs_axisY_yI;
	}

	public void setPlanMcs0_ucs_axisY_yI(double planMcs0_ucs_axisY_yI) {
		this.planMcs0_ucs_axisY_yI = planMcs0_ucs_axisY_yI;
	}

	public double getPlanMcs0_ucs_axisY_zI() {
		return planMcs0_ucs_axisY_zI;
	}

	public void setPlanMcs0_ucs_axisY_zI(double planMcs0_ucs_axisY_zI) {
		this.planMcs0_ucs_axisY_zI = planMcs0_ucs_axisY_zI;
	}

	public double getPlanMcs0_ucs_axisY_xF() {
		return planMcs0_ucs_axisY_xF;
	}

	public void setPlanMcs0_ucs_axisY_xF(double planMcs0_ucs_axisY_xF) {
		this.planMcs0_ucs_axisY_xF = planMcs0_ucs_axisY_xF;
	}

	public double getPlanMcs0_ucs_axisY_yF() {
		return planMcs0_ucs_axisY_yF;
	}

	public void setPlanMcs0_ucs_axisY_yF(double planMcs0_ucs_axisY_yF) {
		this.planMcs0_ucs_axisY_yF = planMcs0_ucs_axisY_yF;
	}

	public double getPlanMcs0_ucs_axisY_zF() {
		return planMcs0_ucs_axisY_zF;
	}

	public void setPlanMcs0_ucs_axisY_zF(double planMcs0_ucs_axisY_zF) {
		this.planMcs0_ucs_axisY_zF = planMcs0_ucs_axisY_zF;
	}

	public double getPlanMcs0_ucs_axisZ_xI() {
		return planMcs0_ucs_axisZ_xI;
	}

	public void setPlanMcs0_ucs_axisZ_xI(double planMcs0_ucs_axisZ_xI) {
		this.planMcs0_ucs_axisZ_xI = planMcs0_ucs_axisZ_xI;
	}

	public double getPlanMcs0_ucs_axisZ_yI() {
		return planMcs0_ucs_axisZ_yI;
	}

	public void setPlanMcs0_ucs_axisZ_yI(double planMcs0_ucs_axisZ_yI) {
		this.planMcs0_ucs_axisZ_yI = planMcs0_ucs_axisZ_yI;
	}

	public double getPlanMcs0_ucs_axisZ_zI() {
		return planMcs0_ucs_axisZ_zI;
	}

	public void setPlanMcs0_ucs_axisZ_zI(double planMcs0_ucs_axisZ_zI) {
		this.planMcs0_ucs_axisZ_zI = planMcs0_ucs_axisZ_zI;
	}

	public double getPlanMcs0_ucs_axisZ_xF() {
		return planMcs0_ucs_axisZ_xF;
	}

	public void setPlanMcs0_ucs_axisZ_xF(double planMcs0_ucs_axisZ_xF) {
		this.planMcs0_ucs_axisZ_xF = planMcs0_ucs_axisZ_xF;
	}

	public double getPlanMcs0_ucs_axisZ_yF() {
		return planMcs0_ucs_axisZ_yF;
	}

	public void setPlanMcs0_ucs_axisZ_yF(double planMcs0_ucs_axisZ_yF) {
		this.planMcs0_ucs_axisZ_yF = planMcs0_ucs_axisZ_yF;
	}

	public double getPlanMcs0_ucs_axisZ_zF() {
		return planMcs0_ucs_axisZ_zF;
	}

	public void setPlanMcs0_ucs_axisZ_zF(double planMcs0_ucs_axisZ_zF) {
		this.planMcs0_ucs_axisZ_zF = planMcs0_ucs_axisZ_zF;
	}

	public double getPlanMcs0_ptCenter_x() {
		return planMcs0_ptCenter_x;
	}

	public void setPlanMcs0_ptCenter_x(double planMcs0_ptCenter_x) {
		this.planMcs0_ptCenter_x = planMcs0_ptCenter_x;
	}

	public double getPlanMcs0_ptCenter_y() {
		return planMcs0_ptCenter_y;
	}

	public void setPlanMcs0_ptCenter_y(double planMcs0_ptCenter_y) {
		this.planMcs0_ptCenter_y = planMcs0_ptCenter_y;
	}

	public double getPlanMcs0_ptCenter_z() {
		return planMcs0_ptCenter_z;
	}

	public void setPlanMcs0_ptCenter_z(double planMcs0_ptCenter_z) {
		this.planMcs0_ptCenter_z = planMcs0_ptCenter_z;
	}

	public double getPlanMcs0_xDir_xI() {
		return planMcs0_xDir_xI;
	}

	public void setPlanMcs0_xDir_xI(double planMcs0_xDir_xI) {
		this.planMcs0_xDir_xI = planMcs0_xDir_xI;
	}

	public double getPlanMcs0_xDir_yI() {
		return planMcs0_xDir_yI;
	}

	public void setPlanMcs0_xDir_yI(double planMcs0_xDir_yI) {
		this.planMcs0_xDir_yI = planMcs0_xDir_yI;
	}

	public double getPlanMcs0_xDir_zI() {
		return planMcs0_xDir_zI;
	}

	public void setPlanMcs0_xDir_zI(double planMcs0_xDir_zI) {
		this.planMcs0_xDir_zI = planMcs0_xDir_zI;
	}

	public double getPlanMcs0_xDir_xF() {
		return planMcs0_xDir_xF;
	}

	public void setPlanMcs0_xDir_xF(double planMcs0_xDir_xF) {
		this.planMcs0_xDir_xF = planMcs0_xDir_xF;
	}

	public double getPlanMcs0_xDir_yF() {
		return planMcs0_xDir_yF;
	}

	public void setPlanMcs0_xDir_yF(double planMcs0_xDir_yF) {
		this.planMcs0_xDir_yF = planMcs0_xDir_yF;
	}

	public double getPlanMcs0_xDir_zF() {
		return planMcs0_xDir_zF;
	}

	public void setPlanMcs0_xDir_zF(double planMcs0_xDir_zF) {
		this.planMcs0_xDir_zF = planMcs0_xDir_zF;
	}

	public double getPlanMcs0_axisX_xI() {
		return planMcs0_axisX_xI;
	}

	public void setPlanMcs0_axisX_xI(double planMcs0_axisX_xI) {
		this.planMcs0_axisX_xI = planMcs0_axisX_xI;
	}

	public double getPlanMcs0_axisX_yI() {
		return planMcs0_axisX_yI;
	}

	public void setPlanMcs0_axisX_yI(double planMcs0_axisX_yI) {
		this.planMcs0_axisX_yI = planMcs0_axisX_yI;
	}

	public double getPlanMcs0_axisX_zI() {
		return planMcs0_axisX_zI;
	}

	public void setPlanMcs0_axisX_zI(double planMcs0_axisX_zI) {
		this.planMcs0_axisX_zI = planMcs0_axisX_zI;
	}

	public double getPlanMcs0_axisX_xF() {
		return planMcs0_axisX_xF;
	}

	public void setPlanMcs0_axisX_xF(double planMcs0_axisX_xF) {
		this.planMcs0_axisX_xF = planMcs0_axisX_xF;
	}

	public double getPlanMcs0_axisX_yF() {
		return planMcs0_axisX_yF;
	}

	public void setPlanMcs0_axisX_yF(double planMcs0_axisX_yF) {
		this.planMcs0_axisX_yF = planMcs0_axisX_yF;
	}

	public double getPlanMcs0_axisX_zF() {
		return planMcs0_axisX_zF;
	}

	public void setPlanMcs0_axisX_zF(double planMcs0_axisX_zF) {
		this.planMcs0_axisX_zF = planMcs0_axisX_zF;
	}

	public double getPlanMcs0_axisY_xI() {
		return planMcs0_axisY_xI;
	}

	public void setPlanMcs0_axisY_xI(double planMcs0_axisY_xI) {
		this.planMcs0_axisY_xI = planMcs0_axisY_xI;
	}

	public double getPlanMcs0_axisY_yI() {
		return planMcs0_axisY_yI;
	}

	public void setPlanMcs0_axisY_yI(double planMcs0_axisY_yI) {
		this.planMcs0_axisY_yI = planMcs0_axisY_yI;
	}

	public double getPlanMcs0_axisY_zI() {
		return planMcs0_axisY_zI;
	}

	public void setPlanMcs0_axisY_zI(double planMcs0_axisY_zI) {
		this.planMcs0_axisY_zI = planMcs0_axisY_zI;
	}

	public double getPlanMcs0_axisY_xF() {
		return planMcs0_axisY_xF;
	}

	public void setPlanMcs0_axisY_xF(double planMcs0_axisY_xF) {
		this.planMcs0_axisY_xF = planMcs0_axisY_xF;
	}

	public double getPlanMcs0_axisY_yF() {
		return planMcs0_axisY_yF;
	}

	public void setPlanMcs0_axisY_yF(double planMcs0_axisY_yF) {
		this.planMcs0_axisY_yF = planMcs0_axisY_yF;
	}

	public double getPlanMcs0_axisY_zF() {
		return planMcs0_axisY_zF;
	}

	public void setPlanMcs0_axisY_zF(double planMcs0_axisY_zF) {
		this.planMcs0_axisY_zF = planMcs0_axisY_zF;
	}

	public double getPlanMcs0_axisZ_xI() {
		return planMcs0_axisZ_xI;
	}

	public void setPlanMcs0_axisZ_xI(double planMcs0_axisZ_xI) {
		this.planMcs0_axisZ_xI = planMcs0_axisZ_xI;
	}

	public double getPlanMcs0_axisZ_yI() {
		return planMcs0_axisZ_yI;
	}

	public void setPlanMcs0_axisZ_yI(double planMcs0_axisZ_yI) {
		this.planMcs0_axisZ_yI = planMcs0_axisZ_yI;
	}

	public double getPlanMcs0_axisZ_zI() {
		return planMcs0_axisZ_zI;
	}

	public void setPlanMcs0_axisZ_zI(double planMcs0_axisZ_zI) {
		this.planMcs0_axisZ_zI = planMcs0_axisZ_zI;
	}

	public double getPlanMcs0_axisZ_xF() {
		return planMcs0_axisZ_xF;
	}

	public void setPlanMcs0_axisZ_xF(double planMcs0_axisZ_xF) {
		this.planMcs0_axisZ_xF = planMcs0_axisZ_xF;
	}

	public double getPlanMcs0_axisZ_yF() {
		return planMcs0_axisZ_yF;
	}

	public void setPlanMcs0_axisZ_yF(double planMcs0_axisZ_yF) {
		this.planMcs0_axisZ_yF = planMcs0_axisZ_yF;
	}

	public double getPlanMcs0_axisZ_zF() {
		return planMcs0_axisZ_zF;
	}

	public void setPlanMcs0_axisZ_zF(double planMcs0_axisZ_zF) {
		this.planMcs0_axisZ_zF = planMcs0_axisZ_zF;
	}

	public double getPlanMcs0_width() {
		return planMcs0_width;
	}

	public void setPlanMcs0_width(double planMcs0_width) {
		this.planMcs0_width = planMcs0_width;
	}

	public double getPlanMcs0_height() {
		return planMcs0_height;
	}

	public void setPlanMcs0_height(double planMcs0_height) {
		this.planMcs0_height = planMcs0_height;
	}

	public double getPlanMcs0_ratio() {
		return planMcs0_ratio;
	}

	public void setPlanMcs0_ratio(double planMcs0_ratio) {
		this.planMcs0_ratio = planMcs0_ratio;
	}

	public double getPlanMcs0_ptLowerLeftCorner_x() {
		return planMcs0_ptLowerLeftCorner_x;
	}

	public void setPlanMcs0_ptLowerLeftCorner_x(double planMcs0_ptLowerLeftCorner_x) {
		this.planMcs0_ptLowerLeftCorner_x = planMcs0_ptLowerLeftCorner_x;
	}

	public double getPlanMcs0_ptLowerLeftCorner_y() {
		return planMcs0_ptLowerLeftCorner_y;
	}

	public void setPlanMcs0_ptLowerLeftCorner_y(double planMcs0_ptLowerLeftCorner_y) {
		this.planMcs0_ptLowerLeftCorner_y = planMcs0_ptLowerLeftCorner_y;
	}

	public double getPlanMcs0_ptLowerRightCorner_x() {
		return planMcs0_ptLowerRightCorner_x;
	}

	public void setPlanMcs0_ptLowerRightCorner_x(double planMcs0_ptLowerRightCorner_x) {
		this.planMcs0_ptLowerRightCorner_x = planMcs0_ptLowerRightCorner_x;
	}

	public double getPlanMcs0_ptLowerRightCorner_y() {
		return planMcs0_ptLowerRightCorner_y;
	}

	public void setPlanMcs0_ptLowerRightCorner_y(double planMcs0_ptLowerRightCorner_y) {
		this.planMcs0_ptLowerRightCorner_y = planMcs0_ptLowerRightCorner_y;
	}

	public double getPlanMcs0_ptUpperRightCorner_x() {
		return planMcs0_ptUpperRightCorner_x;
	}

	public void setPlanMcs0_ptUpperRightCorner_x(double planMcs0_ptUpperRightCorner_x) {
		this.planMcs0_ptUpperRightCorner_x = planMcs0_ptUpperRightCorner_x;
	}

	public double getPlanMcs0_ptUpperRightCorner_y() {
		return planMcs0_ptUpperRightCorner_y;
	}

	public void setPlanMcs0_ptUpperRightCorner_y(double planMcs0_ptUpperRightCorner_y) {
		this.planMcs0_ptUpperRightCorner_y = planMcs0_ptUpperRightCorner_y;
	}

	public double getPlanMcs0_ptUpperLeftCorner_x() {
		return planMcs0_ptUpperLeftCorner_x;
	}

	public void setPlanMcs0_ptUpperLeftCorner_x(double planMcs0_ptUpperLeftCorner_x) {
		this.planMcs0_ptUpperLeftCorner_x = planMcs0_ptUpperLeftCorner_x;
	}

	public double getPlanMcs0_ptUpperLeftCorner_y() {
		return planMcs0_ptUpperLeftCorner_y;
	}

	public void setPlanMcs0_ptUpperLeftCorner_y(double planMcs0_ptUpperLeftCorner_y) {
		this.planMcs0_ptUpperLeftCorner_y = planMcs0_ptUpperLeftCorner_y;
	}

	public double getLimitsMcs0_ptMin_x() {
		return limitsMcs0_ptMin_x;
	}

	public void setLimitsMcs0_ptMin_x(double limitsMcs0_ptMin_x) {
		this.limitsMcs0_ptMin_x = limitsMcs0_ptMin_x;
	}

	public double getLimitsMcs0_ptMin_y() {
		return limitsMcs0_ptMin_y;
	}

	public void setLimitsMcs0_ptMin_y(double limitsMcs0_ptMin_y) {
		this.limitsMcs0_ptMin_y = limitsMcs0_ptMin_y;
	}

	public double getLimitsMcs0_ptMax_x() {
		return limitsMcs0_ptMax_x;
	}

	public void setLimitsMcs0_ptMax_x(double limitsMcs0_ptMax_x) {
		this.limitsMcs0_ptMax_x = limitsMcs0_ptMax_x;
	}

	public double getLimitsMcs0_ptMax_y() {
		return limitsMcs0_ptMax_y;
	}

	public void setLimitsMcs0_ptMax_y(double limitsMcs0_ptMax_y) {
		this.limitsMcs0_ptMax_y = limitsMcs0_ptMax_y;
	}

	public double getPlanProj0_ucs_ptOriginMcs_x() {
		return planProj0_ucs_ptOriginMcs_x;
	}

	public void setPlanProj0_ucs_ptOriginMcs_x(double planProj0_ucs_ptOriginMcs_x) {
		this.planProj0_ucs_ptOriginMcs_x = planProj0_ucs_ptOriginMcs_x;
	}

	public double getPlanProj0_ucs_ptOriginMcs_y() {
		return planProj0_ucs_ptOriginMcs_y;
	}

	public void setPlanProj0_ucs_ptOriginMcs_y(double planProj0_ucs_ptOriginMcs_y) {
		this.planProj0_ucs_ptOriginMcs_y = planProj0_ucs_ptOriginMcs_y;
	}

	public double getPlanProj0_ucs_ptOriginMcs_z() {
		return planProj0_ucs_ptOriginMcs_z;
	}

	public void setPlanProj0_ucs_ptOriginMcs_z(double planProj0_ucs_ptOriginMcs_z) {
		this.planProj0_ucs_ptOriginMcs_z = planProj0_ucs_ptOriginMcs_z;
	}

	public double getPlanProj0_ucs_ptXDirMcs_x() {
		return planProj0_ucs_ptXDirMcs_x;
	}

	public void setPlanProj0_ucs_ptXDirMcs_x(double planProj0_ucs_ptXDirMcs_x) {
		this.planProj0_ucs_ptXDirMcs_x = planProj0_ucs_ptXDirMcs_x;
	}

	public double getPlanProj0_ucs_ptXDirMcs_y() {
		return planProj0_ucs_ptXDirMcs_y;
	}

	public void setPlanProj0_ucs_ptXDirMcs_y(double planProj0_ucs_ptXDirMcs_y) {
		this.planProj0_ucs_ptXDirMcs_y = planProj0_ucs_ptXDirMcs_y;
	}

	public double getPlanProj0_ucs_ptXDirMcs_z() {
		return planProj0_ucs_ptXDirMcs_z;
	}

	public void setPlanProj0_ucs_ptXDirMcs_z(double planProj0_ucs_ptXDirMcs_z) {
		this.planProj0_ucs_ptXDirMcs_z = planProj0_ucs_ptXDirMcs_z;
	}

	public double getPlanProj0_ucs_ptPlanMcs_x() {
		return planProj0_ucs_ptPlanMcs_x;
	}

	public void setPlanProj0_ucs_ptPlanMcs_x(double planProj0_ucs_ptPlanMcs_x) {
		this.planProj0_ucs_ptPlanMcs_x = planProj0_ucs_ptPlanMcs_x;
	}

	public double getPlanProj0_ucs_ptPlanMcs_y() {
		return planProj0_ucs_ptPlanMcs_y;
	}

	public void setPlanProj0_ucs_ptPlanMcs_y(double planProj0_ucs_ptPlanMcs_y) {
		this.planProj0_ucs_ptPlanMcs_y = planProj0_ucs_ptPlanMcs_y;
	}

	public double getPlanProj0_ucs_ptPlanMcs_z() {
		return planProj0_ucs_ptPlanMcs_z;
	}

	public void setPlanProj0_ucs_ptPlanMcs_z(double planProj0_ucs_ptPlanMcs_z) {
		this.planProj0_ucs_ptPlanMcs_z = planProj0_ucs_ptPlanMcs_z;
	}

	public double getPlanProj0_ucs_xDirMcs_xI() {
		return planProj0_ucs_xDirMcs_xI;
	}

	public void setPlanProj0_ucs_xDirMcs_xI(double planProj0_ucs_xDirMcs_xI) {
		this.planProj0_ucs_xDirMcs_xI = planProj0_ucs_xDirMcs_xI;
	}

	public double getPlanProj0_ucs_xDirMcs_yI() {
		return planProj0_ucs_xDirMcs_yI;
	}

	public void setPlanProj0_ucs_xDirMcs_yI(double planProj0_ucs_xDirMcs_yI) {
		this.planProj0_ucs_xDirMcs_yI = planProj0_ucs_xDirMcs_yI;
	}

	public double getPlanProj0_ucs_xDirMcs_zI() {
		return planProj0_ucs_xDirMcs_zI;
	}

	public void setPlanProj0_ucs_xDirMcs_zI(double planProj0_ucs_xDirMcs_zI) {
		this.planProj0_ucs_xDirMcs_zI = planProj0_ucs_xDirMcs_zI;
	}

	public double getPlanProj0_ucs_xDirMcs_xF() {
		return planProj0_ucs_xDirMcs_xF;
	}

	public void setPlanProj0_ucs_xDirMcs_xF(double planProj0_ucs_xDirMcs_xF) {
		this.planProj0_ucs_xDirMcs_xF = planProj0_ucs_xDirMcs_xF;
	}

	public double getPlanProj0_ucs_xDirMcs_yF() {
		return planProj0_ucs_xDirMcs_yF;
	}

	public void setPlanProj0_ucs_xDirMcs_yF(double planProj0_ucs_xDirMcs_yF) {
		this.planProj0_ucs_xDirMcs_yF = planProj0_ucs_xDirMcs_yF;
	}

	public double getPlanProj0_ucs_xDirMcs_zF() {
		return planProj0_ucs_xDirMcs_zF;
	}

	public void setPlanProj0_ucs_xDirMcs_zF(double planProj0_ucs_xDirMcs_zF) {
		this.planProj0_ucs_xDirMcs_zF = planProj0_ucs_xDirMcs_zF;
	}

	public double getPlanProj0_ucs_yDirMcs_xI() {
		return planProj0_ucs_yDirMcs_xI;
	}

	public void setPlanProj0_ucs_yDirMcs_xI(double planProj0_ucs_yDirMcs_xI) {
		this.planProj0_ucs_yDirMcs_xI = planProj0_ucs_yDirMcs_xI;
	}

	public double getPlanProj0_ucs_yDirMcs_yI() {
		return planProj0_ucs_yDirMcs_yI;
	}

	public void setPlanProj0_ucs_yDirMcs_yI(double planProj0_ucs_yDirMcs_yI) {
		this.planProj0_ucs_yDirMcs_yI = planProj0_ucs_yDirMcs_yI;
	}

	public double getPlanProj0_ucs_yDirMcs_zI() {
		return planProj0_ucs_yDirMcs_zI;
	}

	public void setPlanProj0_ucs_yDirMcs_zI(double planProj0_ucs_yDirMcs_zI) {
		this.planProj0_ucs_yDirMcs_zI = planProj0_ucs_yDirMcs_zI;
	}

	public double getPlanProj0_ucs_yDirMcs_xF() {
		return planProj0_ucs_yDirMcs_xF;
	}

	public void setPlanProj0_ucs_yDirMcs_xF(double planProj0_ucs_yDirMcs_xF) {
		this.planProj0_ucs_yDirMcs_xF = planProj0_ucs_yDirMcs_xF;
	}

	public double getPlanProj0_ucs_yDirMcs_yF() {
		return planProj0_ucs_yDirMcs_yF;
	}

	public void setPlanProj0_ucs_yDirMcs_yF(double planProj0_ucs_yDirMcs_yF) {
		this.planProj0_ucs_yDirMcs_yF = planProj0_ucs_yDirMcs_yF;
	}

	public double getPlanProj0_ucs_yDirMcs_zF() {
		return planProj0_ucs_yDirMcs_zF;
	}

	public void setPlanProj0_ucs_yDirMcs_zF(double planProj0_ucs_yDirMcs_zF) {
		this.planProj0_ucs_yDirMcs_zF = planProj0_ucs_yDirMcs_zF;
	}

	public double getPlanProj0_ucs_zDirMcs_xI() {
		return planProj0_ucs_zDirMcs_xI;
	}

	public void setPlanProj0_ucs_zDirMcs_xI(double planProj0_ucs_zDirMcs_xI) {
		this.planProj0_ucs_zDirMcs_xI = planProj0_ucs_zDirMcs_xI;
	}

	public double getPlanProj0_ucs_zDirMcs_yI() {
		return planProj0_ucs_zDirMcs_yI;
	}

	public void setPlanProj0_ucs_zDirMcs_yI(double planProj0_ucs_zDirMcs_yI) {
		this.planProj0_ucs_zDirMcs_yI = planProj0_ucs_zDirMcs_yI;
	}

	public double getPlanProj0_ucs_zDirMcs_zI() {
		return planProj0_ucs_zDirMcs_zI;
	}

	public void setPlanProj0_ucs_zDirMcs_zI(double planProj0_ucs_zDirMcs_zI) {
		this.planProj0_ucs_zDirMcs_zI = planProj0_ucs_zDirMcs_zI;
	}

	public double getPlanProj0_ucs_zDirMcs_xF() {
		return planProj0_ucs_zDirMcs_xF;
	}

	public void setPlanProj0_ucs_zDirMcs_xF(double planProj0_ucs_zDirMcs_xF) {
		this.planProj0_ucs_zDirMcs_xF = planProj0_ucs_zDirMcs_xF;
	}

	public double getPlanProj0_ucs_zDirMcs_yF() {
		return planProj0_ucs_zDirMcs_yF;
	}

	public void setPlanProj0_ucs_zDirMcs_yF(double planProj0_ucs_zDirMcs_yF) {
		this.planProj0_ucs_zDirMcs_yF = planProj0_ucs_zDirMcs_yF;
	}

	public double getPlanProj0_ucs_zDirMcs_zF() {
		return planProj0_ucs_zDirMcs_zF;
	}

	public void setPlanProj0_ucs_zDirMcs_zF(double planProj0_ucs_zDirMcs_zF) {
		this.planProj0_ucs_zDirMcs_zF = planProj0_ucs_zDirMcs_zF;
	}

	public double getPlanProj0_ucs_vPlanMcs_xI() {
		return planProj0_ucs_vPlanMcs_xI;
	}

	public void setPlanProj0_ucs_vPlanMcs_xI(double planProj0_ucs_vPlanMcs_xI) {
		this.planProj0_ucs_vPlanMcs_xI = planProj0_ucs_vPlanMcs_xI;
	}

	public double getPlanProj0_ucs_vPlanMcs_yI() {
		return planProj0_ucs_vPlanMcs_yI;
	}

	public void setPlanProj0_ucs_vPlanMcs_yI(double planProj0_ucs_vPlanMcs_yI) {
		this.planProj0_ucs_vPlanMcs_yI = planProj0_ucs_vPlanMcs_yI;
	}

	public double getPlanProj0_ucs_vPlanMcs_zI() {
		return planProj0_ucs_vPlanMcs_zI;
	}

	public void setPlanProj0_ucs_vPlanMcs_zI(double planProj0_ucs_vPlanMcs_zI) {
		this.planProj0_ucs_vPlanMcs_zI = planProj0_ucs_vPlanMcs_zI;
	}

	public double getPlanProj0_ucs_vPlanMcs_xF() {
		return planProj0_ucs_vPlanMcs_xF;
	}

	public void setPlanProj0_ucs_vPlanMcs_xF(double planProj0_ucs_vPlanMcs_xF) {
		this.planProj0_ucs_vPlanMcs_xF = planProj0_ucs_vPlanMcs_xF;
	}

	public double getPlanProj0_ucs_vPlanMcs_yF() {
		return planProj0_ucs_vPlanMcs_yF;
	}

	public void setPlanProj0_ucs_vPlanMcs_yF(double planProj0_ucs_vPlanMcs_yF) {
		this.planProj0_ucs_vPlanMcs_yF = planProj0_ucs_vPlanMcs_yF;
	}

	public double getPlanProj0_ucs_vPlanMcs_zF() {
		return planProj0_ucs_vPlanMcs_zF;
	}

	public void setPlanProj0_ucs_vPlanMcs_zF(double planProj0_ucs_vPlanMcs_zF) {
		this.planProj0_ucs_vPlanMcs_zF = planProj0_ucs_vPlanMcs_zF;
	}

	public double getPlanProj0_ucs_axisX_xI() {
		return planProj0_ucs_axisX_xI;
	}

	public void setPlanProj0_ucs_axisX_xI(double planProj0_ucs_axisX_xI) {
		this.planProj0_ucs_axisX_xI = planProj0_ucs_axisX_xI;
	}

	public double getPlanProj0_ucs_axisX_yI() {
		return planProj0_ucs_axisX_yI;
	}

	public void setPlanProj0_ucs_axisX_yI(double planProj0_ucs_axisX_yI) {
		this.planProj0_ucs_axisX_yI = planProj0_ucs_axisX_yI;
	}

	public double getPlanProj0_ucs_axisX_zI() {
		return planProj0_ucs_axisX_zI;
	}

	public void setPlanProj0_ucs_axisX_zI(double planProj0_ucs_axisX_zI) {
		this.planProj0_ucs_axisX_zI = planProj0_ucs_axisX_zI;
	}

	public double getPlanProj0_ucs_axisX_xF() {
		return planProj0_ucs_axisX_xF;
	}

	public void setPlanProj0_ucs_axisX_xF(double planProj0_ucs_axisX_xF) {
		this.planProj0_ucs_axisX_xF = planProj0_ucs_axisX_xF;
	}

	public double getPlanProj0_ucs_axisX_yF() {
		return planProj0_ucs_axisX_yF;
	}

	public void setPlanProj0_ucs_axisX_yF(double planProj0_ucs_axisX_yF) {
		this.planProj0_ucs_axisX_yF = planProj0_ucs_axisX_yF;
	}

	public double getPlanProj0_ucs_axisX_zF() {
		return planProj0_ucs_axisX_zF;
	}

	public void setPlanProj0_ucs_axisX_zF(double planProj0_ucs_axisX_zF) {
		this.planProj0_ucs_axisX_zF = planProj0_ucs_axisX_zF;
	}

	public double getPlanProj0_ucs_axisY_xI() {
		return planProj0_ucs_axisY_xI;
	}

	public void setPlanProj0_ucs_axisY_xI(double planProj0_ucs_axisY_xI) {
		this.planProj0_ucs_axisY_xI = planProj0_ucs_axisY_xI;
	}

	public double getPlanProj0_ucs_axisY_yI() {
		return planProj0_ucs_axisY_yI;
	}

	public void setPlanProj0_ucs_axisY_yI(double planProj0_ucs_axisY_yI) {
		this.planProj0_ucs_axisY_yI = planProj0_ucs_axisY_yI;
	}

	public double getPlanProj0_ucs_axisY_zI() {
		return planProj0_ucs_axisY_zI;
	}

	public void setPlanProj0_ucs_axisY_zI(double planProj0_ucs_axisY_zI) {
		this.planProj0_ucs_axisY_zI = planProj0_ucs_axisY_zI;
	}

	public double getPlanProj0_ucs_axisY_xF() {
		return planProj0_ucs_axisY_xF;
	}

	public void setPlanProj0_ucs_axisY_xF(double planProj0_ucs_axisY_xF) {
		this.planProj0_ucs_axisY_xF = planProj0_ucs_axisY_xF;
	}

	public double getPlanProj0_ucs_axisY_yF() {
		return planProj0_ucs_axisY_yF;
	}

	public void setPlanProj0_ucs_axisY_yF(double planProj0_ucs_axisY_yF) {
		this.planProj0_ucs_axisY_yF = planProj0_ucs_axisY_yF;
	}

	public double getPlanProj0_ucs_axisY_zF() {
		return planProj0_ucs_axisY_zF;
	}

	public void setPlanProj0_ucs_axisY_zF(double planProj0_ucs_axisY_zF) {
		this.planProj0_ucs_axisY_zF = planProj0_ucs_axisY_zF;
	}

	public double getPlanProj0_ucs_axisZ_xI() {
		return planProj0_ucs_axisZ_xI;
	}

	public void setPlanProj0_ucs_axisZ_xI(double planProj0_ucs_axisZ_xI) {
		this.planProj0_ucs_axisZ_xI = planProj0_ucs_axisZ_xI;
	}

	public double getPlanProj0_ucs_axisZ_yI() {
		return planProj0_ucs_axisZ_yI;
	}

	public void setPlanProj0_ucs_axisZ_yI(double planProj0_ucs_axisZ_yI) {
		this.planProj0_ucs_axisZ_yI = planProj0_ucs_axisZ_yI;
	}

	public double getPlanProj0_ucs_axisZ_zI() {
		return planProj0_ucs_axisZ_zI;
	}

	public void setPlanProj0_ucs_axisZ_zI(double planProj0_ucs_axisZ_zI) {
		this.planProj0_ucs_axisZ_zI = planProj0_ucs_axisZ_zI;
	}

	public double getPlanProj0_ucs_axisZ_xF() {
		return planProj0_ucs_axisZ_xF;
	}

	public void setPlanProj0_ucs_axisZ_xF(double planProj0_ucs_axisZ_xF) {
		this.planProj0_ucs_axisZ_xF = planProj0_ucs_axisZ_xF;
	}

	public double getPlanProj0_ucs_axisZ_yF() {
		return planProj0_ucs_axisZ_yF;
	}

	public void setPlanProj0_ucs_axisZ_yF(double planProj0_ucs_axisZ_yF) {
		this.planProj0_ucs_axisZ_yF = planProj0_ucs_axisZ_yF;
	}

	public double getPlanProj0_ucs_axisZ_zF() {
		return planProj0_ucs_axisZ_zF;
	}

	public void setPlanProj0_ucs_axisZ_zF(double planProj0_ucs_axisZ_zF) {
		this.planProj0_ucs_axisZ_zF = planProj0_ucs_axisZ_zF;
	}

	public double getPlanProj0_ptCenter_x() {
		return planProj0_ptCenter_x;
	}

	public void setPlanProj0_ptCenter_x(double planProj0_ptCenter_x) {
		this.planProj0_ptCenter_x = planProj0_ptCenter_x;
	}

	public double getPlanProj0_ptCenter_y() {
		return planProj0_ptCenter_y;
	}

	public void setPlanProj0_ptCenter_y(double planProj0_ptCenter_y) {
		this.planProj0_ptCenter_y = planProj0_ptCenter_y;
	}

	public double getPlanProj0_ptCenter_z() {
		return planProj0_ptCenter_z;
	}

	public void setPlanProj0_ptCenter_z(double planProj0_ptCenter_z) {
		this.planProj0_ptCenter_z = planProj0_ptCenter_z;
	}

	public double getPlanProj0_xDir_xI() {
		return planProj0_xDir_xI;
	}

	public void setPlanProj0_xDir_xI(double planProj0_xDir_xI) {
		this.planProj0_xDir_xI = planProj0_xDir_xI;
	}

	public double getPlanProj0_xDir_yI() {
		return planProj0_xDir_yI;
	}

	public void setPlanProj0_xDir_yI(double planProj0_xDir_yI) {
		this.planProj0_xDir_yI = planProj0_xDir_yI;
	}

	public double getPlanProj0_xDir_zI() {
		return planProj0_xDir_zI;
	}

	public void setPlanProj0_xDir_zI(double planProj0_xDir_zI) {
		this.planProj0_xDir_zI = planProj0_xDir_zI;
	}

	public double getPlanProj0_xDir_xF() {
		return planProj0_xDir_xF;
	}

	public void setPlanProj0_xDir_xF(double planProj0_xDir_xF) {
		this.planProj0_xDir_xF = planProj0_xDir_xF;
	}

	public double getPlanProj0_xDir_yF() {
		return planProj0_xDir_yF;
	}

	public void setPlanProj0_xDir_yF(double planProj0_xDir_yF) {
		this.planProj0_xDir_yF = planProj0_xDir_yF;
	}

	public double getPlanProj0_xDir_zF() {
		return planProj0_xDir_zF;
	}

	public void setPlanProj0_xDir_zF(double planProj0_xDir_zF) {
		this.planProj0_xDir_zF = planProj0_xDir_zF;
	}

	public double getPlanProj0_axisX_xI() {
		return planProj0_axisX_xI;
	}

	public void setPlanProj0_axisX_xI(double planProj0_axisX_xI) {
		this.planProj0_axisX_xI = planProj0_axisX_xI;
	}

	public double getPlanProj0_axisX_yI() {
		return planProj0_axisX_yI;
	}

	public void setPlanProj0_axisX_yI(double planProj0_axisX_yI) {
		this.planProj0_axisX_yI = planProj0_axisX_yI;
	}

	public double getPlanProj0_axisX_zI() {
		return planProj0_axisX_zI;
	}

	public void setPlanProj0_axisX_zI(double planProj0_axisX_zI) {
		this.planProj0_axisX_zI = planProj0_axisX_zI;
	}

	public double getPlanProj0_axisX_xF() {
		return planProj0_axisX_xF;
	}

	public void setPlanProj0_axisX_xF(double planProj0_axisX_xF) {
		this.planProj0_axisX_xF = planProj0_axisX_xF;
	}

	public double getPlanProj0_axisX_yF() {
		return planProj0_axisX_yF;
	}

	public void setPlanProj0_axisX_yF(double planProj0_axisX_yF) {
		this.planProj0_axisX_yF = planProj0_axisX_yF;
	}

	public double getPlanProj0_axisX_zF() {
		return planProj0_axisX_zF;
	}

	public void setPlanProj0_axisX_zF(double planProj0_axisX_zF) {
		this.planProj0_axisX_zF = planProj0_axisX_zF;
	}

	public double getPlanProj0_axisY_xI() {
		return planProj0_axisY_xI;
	}

	public void setPlanProj0_axisY_xI(double planProj0_axisY_xI) {
		this.planProj0_axisY_xI = planProj0_axisY_xI;
	}

	public double getPlanProj0_axisY_yI() {
		return planProj0_axisY_yI;
	}

	public void setPlanProj0_axisY_yI(double planProj0_axisY_yI) {
		this.planProj0_axisY_yI = planProj0_axisY_yI;
	}

	public double getPlanProj0_axisY_zI() {
		return planProj0_axisY_zI;
	}

	public void setPlanProj0_axisY_zI(double planProj0_axisY_zI) {
		this.planProj0_axisY_zI = planProj0_axisY_zI;
	}

	public double getPlanProj0_axisY_xF() {
		return planProj0_axisY_xF;
	}

	public void setPlanProj0_axisY_xF(double planProj0_axisY_xF) {
		this.planProj0_axisY_xF = planProj0_axisY_xF;
	}

	public double getPlanProj0_axisY_yF() {
		return planProj0_axisY_yF;
	}

	public void setPlanProj0_axisY_yF(double planProj0_axisY_yF) {
		this.planProj0_axisY_yF = planProj0_axisY_yF;
	}

	public double getPlanProj0_axisY_zF() {
		return planProj0_axisY_zF;
	}

	public void setPlanProj0_axisY_zF(double planProj0_axisY_zF) {
		this.planProj0_axisY_zF = planProj0_axisY_zF;
	}

	public double getPlanProj0_axisZ_xI() {
		return planProj0_axisZ_xI;
	}

	public void setPlanProj0_axisZ_xI(double planProj0_axisZ_xI) {
		this.planProj0_axisZ_xI = planProj0_axisZ_xI;
	}

	public double getPlanProj0_axisZ_yI() {
		return planProj0_axisZ_yI;
	}

	public void setPlanProj0_axisZ_yI(double planProj0_axisZ_yI) {
		this.planProj0_axisZ_yI = planProj0_axisZ_yI;
	}

	public double getPlanProj0_axisZ_zI() {
		return planProj0_axisZ_zI;
	}

	public void setPlanProj0_axisZ_zI(double planProj0_axisZ_zI) {
		this.planProj0_axisZ_zI = planProj0_axisZ_zI;
	}

	public double getPlanProj0_axisZ_xF() {
		return planProj0_axisZ_xF;
	}

	public void setPlanProj0_axisZ_xF(double planProj0_axisZ_xF) {
		this.planProj0_axisZ_xF = planProj0_axisZ_xF;
	}

	public double getPlanProj0_axisZ_yF() {
		return planProj0_axisZ_yF;
	}

	public void setPlanProj0_axisZ_yF(double planProj0_axisZ_yF) {
		this.planProj0_axisZ_yF = planProj0_axisZ_yF;
	}

	public double getPlanProj0_axisZ_zF() {
		return planProj0_axisZ_zF;
	}

	public void setPlanProj0_axisZ_zF(double planProj0_axisZ_zF) {
		this.planProj0_axisZ_zF = planProj0_axisZ_zF;
	}

	public double getPlanProj0_width() {
		return planProj0_width;
	}

	public void setPlanProj0_width(double planProj0_width) {
		this.planProj0_width = planProj0_width;
	}

	public double getPlanProj0_height() {
		return planProj0_height;
	}

	public void setPlanProj0_height(double planProj0_height) {
		this.planProj0_height = planProj0_height;
	}

	public double getPlanProj0_ratio() {
		return planProj0_ratio;
	}

	public void setPlanProj0_ratio(double planProj0_ratio) {
		this.planProj0_ratio = planProj0_ratio;
	}

	public double getPlanProj0_ptLowerLeftCorner_x() {
		return planProj0_ptLowerLeftCorner_x;
	}

	public void setPlanProj0_ptLowerLeftCorner_x(double planProj0_ptLowerLeftCorner_x) {
		this.planProj0_ptLowerLeftCorner_x = planProj0_ptLowerLeftCorner_x;
	}

	public double getPlanProj0_ptLowerLeftCorner_y() {
		return planProj0_ptLowerLeftCorner_y;
	}

	public void setPlanProj0_ptLowerLeftCorner_y(double planProj0_ptLowerLeftCorner_y) {
		this.planProj0_ptLowerLeftCorner_y = planProj0_ptLowerLeftCorner_y;
	}

	public double getPlanProj0_ptLowerRightCorner_x() {
		return planProj0_ptLowerRightCorner_x;
	}

	public void setPlanProj0_ptLowerRightCorner_x(double planProj0_ptLowerRightCorner_x) {
		this.planProj0_ptLowerRightCorner_x = planProj0_ptLowerRightCorner_x;
	}

	public double getPlanProj0_ptLowerRightCorner_y() {
		return planProj0_ptLowerRightCorner_y;
	}

	public void setPlanProj0_ptLowerRightCorner_y(double planProj0_ptLowerRightCorner_y) {
		this.planProj0_ptLowerRightCorner_y = planProj0_ptLowerRightCorner_y;
	}

	public double getPlanProj0_ptUpperRightCorner_x() {
		return planProj0_ptUpperRightCorner_x;
	}

	public void setPlanProj0_ptUpperRightCorner_x(double planProj0_ptUpperRightCorner_x) {
		this.planProj0_ptUpperRightCorner_x = planProj0_ptUpperRightCorner_x;
	}

	public double getPlanProj0_ptUpperRightCorner_y() {
		return planProj0_ptUpperRightCorner_y;
	}

	public void setPlanProj0_ptUpperRightCorner_y(double planProj0_ptUpperRightCorner_y) {
		this.planProj0_ptUpperRightCorner_y = planProj0_ptUpperRightCorner_y;
	}

	public double getPlanProj0_ptUpperLeftCorner_x() {
		return planProj0_ptUpperLeftCorner_x;
	}

	public void setPlanProj0_ptUpperLeftCorner_x(double planProj0_ptUpperLeftCorner_x) {
		this.planProj0_ptUpperLeftCorner_x = planProj0_ptUpperLeftCorner_x;
	}

	public double getPlanProj0_ptUpperLeftCorner_y() {
		return planProj0_ptUpperLeftCorner_y;
	}

	public void setPlanProj0_ptUpperLeftCorner_y(double planProj0_ptUpperLeftCorner_y) {
		this.planProj0_ptUpperLeftCorner_y = planProj0_ptUpperLeftCorner_y;
	}

	public double getLimitsProj0_ptMin_x() {
		return limitsProj0_ptMin_x;
	}

	public void setLimitsProj0_ptMin_x(double limitsProj0_ptMin_x) {
		this.limitsProj0_ptMin_x = limitsProj0_ptMin_x;
	}

	public double getLimitsProj0_ptMin_y() {
		return limitsProj0_ptMin_y;
	}

	public void setLimitsProj0_ptMin_y(double limitsProj0_ptMin_y) {
		this.limitsProj0_ptMin_y = limitsProj0_ptMin_y;
	}

	public double getLimitsProj0_ptMax_x() {
		return limitsProj0_ptMax_x;
	}

	public void setLimitsProj0_ptMax_x(double limitsProj0_ptMax_x) {
		this.limitsProj0_ptMax_x = limitsProj0_ptMax_x;
	}

	public double getLimitsProj0_ptMax_y() {
		return limitsProj0_ptMax_y;
	}

	public void setLimitsProj0_ptMax_y(double limitsProj0_ptMax_y) {
		this.limitsProj0_ptMax_y = limitsProj0_ptMax_y;
	}

	public double getPlanScr0_ucs_ptOriginMcs_x() {
		return planScr0_ucs_ptOriginMcs_x;
	}

	public void setPlanScr0_ucs_ptOriginMcs_x(double planScr0_ucs_ptOriginMcs_x) {
		this.planScr0_ucs_ptOriginMcs_x = planScr0_ucs_ptOriginMcs_x;
	}

	public double getPlanScr0_ucs_ptOriginMcs_y() {
		return planScr0_ucs_ptOriginMcs_y;
	}

	public void setPlanScr0_ucs_ptOriginMcs_y(double planScr0_ucs_ptOriginMcs_y) {
		this.planScr0_ucs_ptOriginMcs_y = planScr0_ucs_ptOriginMcs_y;
	}

	public double getPlanScr0_ucs_ptOriginMcs_z() {
		return planScr0_ucs_ptOriginMcs_z;
	}

	public void setPlanScr0_ucs_ptOriginMcs_z(double planScr0_ucs_ptOriginMcs_z) {
		this.planScr0_ucs_ptOriginMcs_z = planScr0_ucs_ptOriginMcs_z;
	}

	public double getPlanScr0_ucs_ptXDirMcs_x() {
		return planScr0_ucs_ptXDirMcs_x;
	}

	public void setPlanScr0_ucs_ptXDirMcs_x(double planScr0_ucs_ptXDirMcs_x) {
		this.planScr0_ucs_ptXDirMcs_x = planScr0_ucs_ptXDirMcs_x;
	}

	public double getPlanScr0_ucs_ptXDirMcs_y() {
		return planScr0_ucs_ptXDirMcs_y;
	}

	public void setPlanScr0_ucs_ptXDirMcs_y(double planScr0_ucs_ptXDirMcs_y) {
		this.planScr0_ucs_ptXDirMcs_y = planScr0_ucs_ptXDirMcs_y;
	}

	public double getPlanScr0_ucs_ptXDirMcs_z() {
		return planScr0_ucs_ptXDirMcs_z;
	}

	public void setPlanScr0_ucs_ptXDirMcs_z(double planScr0_ucs_ptXDirMcs_z) {
		this.planScr0_ucs_ptXDirMcs_z = planScr0_ucs_ptXDirMcs_z;
	}

	public double getPlanScr0_ucs_ptPlanScr_x() {
		return planScr0_ucs_ptPlanScr_x;
	}

	public void setPlanScr0_ucs_ptPlanScr_x(double planScr0_ucs_ptPlanScr_x) {
		this.planScr0_ucs_ptPlanScr_x = planScr0_ucs_ptPlanScr_x;
	}

	public double getPlanScr0_ucs_ptPlanScr_y() {
		return planScr0_ucs_ptPlanScr_y;
	}

	public void setPlanScr0_ucs_ptPlanScr_y(double planScr0_ucs_ptPlanScr_y) {
		this.planScr0_ucs_ptPlanScr_y = planScr0_ucs_ptPlanScr_y;
	}

	public double getPlanScr0_ucs_ptPlanScr_z() {
		return planScr0_ucs_ptPlanScr_z;
	}

	public void setPlanScr0_ucs_ptPlanScr_z(double planScr0_ucs_ptPlanScr_z) {
		this.planScr0_ucs_ptPlanScr_z = planScr0_ucs_ptPlanScr_z;
	}

	public double getPlanScr0_ucs_xDirMcs_xI() {
		return planScr0_ucs_xDirMcs_xI;
	}

	public void setPlanScr0_ucs_xDirMcs_xI(double planScr0_ucs_xDirMcs_xI) {
		this.planScr0_ucs_xDirMcs_xI = planScr0_ucs_xDirMcs_xI;
	}

	public double getPlanScr0_ucs_xDirMcs_yI() {
		return planScr0_ucs_xDirMcs_yI;
	}

	public void setPlanScr0_ucs_xDirMcs_yI(double planScr0_ucs_xDirMcs_yI) {
		this.planScr0_ucs_xDirMcs_yI = planScr0_ucs_xDirMcs_yI;
	}

	public double getPlanScr0_ucs_xDirMcs_zI() {
		return planScr0_ucs_xDirMcs_zI;
	}

	public void setPlanScr0_ucs_xDirMcs_zI(double planScr0_ucs_xDirMcs_zI) {
		this.planScr0_ucs_xDirMcs_zI = planScr0_ucs_xDirMcs_zI;
	}

	public double getPlanScr0_ucs_xDirMcs_xF() {
		return planScr0_ucs_xDirMcs_xF;
	}

	public void setPlanScr0_ucs_xDirMcs_xF(double planScr0_ucs_xDirMcs_xF) {
		this.planScr0_ucs_xDirMcs_xF = planScr0_ucs_xDirMcs_xF;
	}

	public double getPlanScr0_ucs_xDirMcs_yF() {
		return planScr0_ucs_xDirMcs_yF;
	}

	public void setPlanScr0_ucs_xDirMcs_yF(double planScr0_ucs_xDirMcs_yF) {
		this.planScr0_ucs_xDirMcs_yF = planScr0_ucs_xDirMcs_yF;
	}

	public double getPlanScr0_ucs_xDirMcs_zF() {
		return planScr0_ucs_xDirMcs_zF;
	}

	public void setPlanScr0_ucs_xDirMcs_zF(double planScr0_ucs_xDirMcs_zF) {
		this.planScr0_ucs_xDirMcs_zF = planScr0_ucs_xDirMcs_zF;
	}

	public double getPlanScr0_ucs_yDirMcs_xI() {
		return planScr0_ucs_yDirMcs_xI;
	}

	public void setPlanScr0_ucs_yDirMcs_xI(double planScr0_ucs_yDirMcs_xI) {
		this.planScr0_ucs_yDirMcs_xI = planScr0_ucs_yDirMcs_xI;
	}

	public double getPlanScr0_ucs_yDirMcs_yI() {
		return planScr0_ucs_yDirMcs_yI;
	}

	public void setPlanScr0_ucs_yDirMcs_yI(double planScr0_ucs_yDirMcs_yI) {
		this.planScr0_ucs_yDirMcs_yI = planScr0_ucs_yDirMcs_yI;
	}

	public double getPlanScr0_ucs_yDirMcs_zI() {
		return planScr0_ucs_yDirMcs_zI;
	}

	public void setPlanScr0_ucs_yDirMcs_zI(double planScr0_ucs_yDirMcs_zI) {
		this.planScr0_ucs_yDirMcs_zI = planScr0_ucs_yDirMcs_zI;
	}

	public double getPlanScr0_ucs_yDirMcs_xF() {
		return planScr0_ucs_yDirMcs_xF;
	}

	public void setPlanScr0_ucs_yDirMcs_xF(double planScr0_ucs_yDirMcs_xF) {
		this.planScr0_ucs_yDirMcs_xF = planScr0_ucs_yDirMcs_xF;
	}

	public double getPlanScr0_ucs_yDirMcs_yF() {
		return planScr0_ucs_yDirMcs_yF;
	}

	public void setPlanScr0_ucs_yDirMcs_yF(double planScr0_ucs_yDirMcs_yF) {
		this.planScr0_ucs_yDirMcs_yF = planScr0_ucs_yDirMcs_yF;
	}

	public double getPlanScr0_ucs_yDirMcs_zF() {
		return planScr0_ucs_yDirMcs_zF;
	}

	public void setPlanScr0_ucs_yDirMcs_zF(double planScr0_ucs_yDirMcs_zF) {
		this.planScr0_ucs_yDirMcs_zF = planScr0_ucs_yDirMcs_zF;
	}

	public double getPlanScr0_ucs_zDirMcs_xI() {
		return planScr0_ucs_zDirMcs_xI;
	}

	public void setPlanScr0_ucs_zDirMcs_xI(double planScr0_ucs_zDirMcs_xI) {
		this.planScr0_ucs_zDirMcs_xI = planScr0_ucs_zDirMcs_xI;
	}

	public double getPlanScr0_ucs_zDirMcs_yI() {
		return planScr0_ucs_zDirMcs_yI;
	}

	public void setPlanScr0_ucs_zDirMcs_yI(double planScr0_ucs_zDirMcs_yI) {
		this.planScr0_ucs_zDirMcs_yI = planScr0_ucs_zDirMcs_yI;
	}

	public double getPlanScr0_ucs_zDirMcs_zI() {
		return planScr0_ucs_zDirMcs_zI;
	}

	public void setPlanScr0_ucs_zDirMcs_zI(double planScr0_ucs_zDirMcs_zI) {
		this.planScr0_ucs_zDirMcs_zI = planScr0_ucs_zDirMcs_zI;
	}

	public double getPlanScr0_ucs_zDirMcs_xF() {
		return planScr0_ucs_zDirMcs_xF;
	}

	public void setPlanScr0_ucs_zDirMcs_xF(double planScr0_ucs_zDirMcs_xF) {
		this.planScr0_ucs_zDirMcs_xF = planScr0_ucs_zDirMcs_xF;
	}

	public double getPlanScr0_ucs_zDirMcs_yF() {
		return planScr0_ucs_zDirMcs_yF;
	}

	public void setPlanScr0_ucs_zDirMcs_yF(double planScr0_ucs_zDirMcs_yF) {
		this.planScr0_ucs_zDirMcs_yF = planScr0_ucs_zDirMcs_yF;
	}

	public double getPlanScr0_ucs_zDirMcs_zF() {
		return planScr0_ucs_zDirMcs_zF;
	}

	public void setPlanScr0_ucs_zDirMcs_zF(double planScr0_ucs_zDirMcs_zF) {
		this.planScr0_ucs_zDirMcs_zF = planScr0_ucs_zDirMcs_zF;
	}

	public double getPlanScr0_ucs_vPlanScr_xI() {
		return planScr0_ucs_vPlanScr_xI;
	}

	public void setPlanScr0_ucs_vPlanScr_xI(double planScr0_ucs_vPlanScr_xI) {
		this.planScr0_ucs_vPlanScr_xI = planScr0_ucs_vPlanScr_xI;
	}

	public double getPlanScr0_ucs_vPlanScr_yI() {
		return planScr0_ucs_vPlanScr_yI;
	}

	public void setPlanScr0_ucs_vPlanScr_yI(double planScr0_ucs_vPlanScr_yI) {
		this.planScr0_ucs_vPlanScr_yI = planScr0_ucs_vPlanScr_yI;
	}

	public double getPlanScr0_ucs_vPlanScr_zI() {
		return planScr0_ucs_vPlanScr_zI;
	}

	public void setPlanScr0_ucs_vPlanScr_zI(double planScr0_ucs_vPlanScr_zI) {
		this.planScr0_ucs_vPlanScr_zI = planScr0_ucs_vPlanScr_zI;
	}

	public double getPlanScr0_ucs_vPlanScr_xF() {
		return planScr0_ucs_vPlanScr_xF;
	}

	public void setPlanScr0_ucs_vPlanScr_xF(double planScr0_ucs_vPlanScr_xF) {
		this.planScr0_ucs_vPlanScr_xF = planScr0_ucs_vPlanScr_xF;
	}

	public double getPlanScr0_ucs_vPlanScr_yF() {
		return planScr0_ucs_vPlanScr_yF;
	}

	public void setPlanScr0_ucs_vPlanScr_yF(double planScr0_ucs_vPlanScr_yF) {
		this.planScr0_ucs_vPlanScr_yF = planScr0_ucs_vPlanScr_yF;
	}

	public double getPlanScr0_ucs_vPlanScr_zF() {
		return planScr0_ucs_vPlanScr_zF;
	}

	public void setPlanScr0_ucs_vPlanScr_zF(double planScr0_ucs_vPlanScr_zF) {
		this.planScr0_ucs_vPlanScr_zF = planScr0_ucs_vPlanScr_zF;
	}

	public double getPlanScr0_ucs_axisX_xI() {
		return planScr0_ucs_axisX_xI;
	}

	public void setPlanScr0_ucs_axisX_xI(double planScr0_ucs_axisX_xI) {
		this.planScr0_ucs_axisX_xI = planScr0_ucs_axisX_xI;
	}

	public double getPlanScr0_ucs_axisX_yI() {
		return planScr0_ucs_axisX_yI;
	}

	public void setPlanScr0_ucs_axisX_yI(double planScr0_ucs_axisX_yI) {
		this.planScr0_ucs_axisX_yI = planScr0_ucs_axisX_yI;
	}

	public double getPlanScr0_ucs_axisX_zI() {
		return planScr0_ucs_axisX_zI;
	}

	public void setPlanScr0_ucs_axisX_zI(double planScr0_ucs_axisX_zI) {
		this.planScr0_ucs_axisX_zI = planScr0_ucs_axisX_zI;
	}

	public double getPlanScr0_ucs_axisX_xF() {
		return planScr0_ucs_axisX_xF;
	}

	public void setPlanScr0_ucs_axisX_xF(double planScr0_ucs_axisX_xF) {
		this.planScr0_ucs_axisX_xF = planScr0_ucs_axisX_xF;
	}

	public double getPlanScr0_ucs_axisX_yF() {
		return planScr0_ucs_axisX_yF;
	}

	public void setPlanScr0_ucs_axisX_yF(double planScr0_ucs_axisX_yF) {
		this.planScr0_ucs_axisX_yF = planScr0_ucs_axisX_yF;
	}

	public double getPlanScr0_ucs_axisX_zF() {
		return planScr0_ucs_axisX_zF;
	}

	public void setPlanScr0_ucs_axisX_zF(double planScr0_ucs_axisX_zF) {
		this.planScr0_ucs_axisX_zF = planScr0_ucs_axisX_zF;
	}

	public double getPlanScr0_ucs_axisY_xI() {
		return planScr0_ucs_axisY_xI;
	}

	public void setPlanScr0_ucs_axisY_xI(double planScr0_ucs_axisY_xI) {
		this.planScr0_ucs_axisY_xI = planScr0_ucs_axisY_xI;
	}

	public double getPlanScr0_ucs_axisY_yI() {
		return planScr0_ucs_axisY_yI;
	}

	public void setPlanScr0_ucs_axisY_yI(double planScr0_ucs_axisY_yI) {
		this.planScr0_ucs_axisY_yI = planScr0_ucs_axisY_yI;
	}

	public double getPlanScr0_ucs_axisY_zI() {
		return planScr0_ucs_axisY_zI;
	}

	public void setPlanScr0_ucs_axisY_zI(double planScr0_ucs_axisY_zI) {
		this.planScr0_ucs_axisY_zI = planScr0_ucs_axisY_zI;
	}

	public double getPlanScr0_ucs_axisY_xF() {
		return planScr0_ucs_axisY_xF;
	}

	public void setPlanScr0_ucs_axisY_xF(double planScr0_ucs_axisY_xF) {
		this.planScr0_ucs_axisY_xF = planScr0_ucs_axisY_xF;
	}

	public double getPlanScr0_ucs_axisY_yF() {
		return planScr0_ucs_axisY_yF;
	}

	public void setPlanScr0_ucs_axisY_yF(double planScr0_ucs_axisY_yF) {
		this.planScr0_ucs_axisY_yF = planScr0_ucs_axisY_yF;
	}

	public double getPlanScr0_ucs_axisY_zF() {
		return planScr0_ucs_axisY_zF;
	}

	public void setPlanScr0_ucs_axisY_zF(double planScr0_ucs_axisY_zF) {
		this.planScr0_ucs_axisY_zF = planScr0_ucs_axisY_zF;
	}

	public double getPlanScr0_ucs_axisZ_xI() {
		return planScr0_ucs_axisZ_xI;
	}

	public void setPlanScr0_ucs_axisZ_xI(double planScr0_ucs_axisZ_xI) {
		this.planScr0_ucs_axisZ_xI = planScr0_ucs_axisZ_xI;
	}

	public double getPlanScr0_ucs_axisZ_yI() {
		return planScr0_ucs_axisZ_yI;
	}

	public void setPlanScr0_ucs_axisZ_yI(double planScr0_ucs_axisZ_yI) {
		this.planScr0_ucs_axisZ_yI = planScr0_ucs_axisZ_yI;
	}

	public double getPlanScr0_ucs_axisZ_zI() {
		return planScr0_ucs_axisZ_zI;
	}

	public void setPlanScr0_ucs_axisZ_zI(double planScr0_ucs_axisZ_zI) {
		this.planScr0_ucs_axisZ_zI = planScr0_ucs_axisZ_zI;
	}

	public double getPlanScr0_ucs_axisZ_xF() {
		return planScr0_ucs_axisZ_xF;
	}

	public void setPlanScr0_ucs_axisZ_xF(double planScr0_ucs_axisZ_xF) {
		this.planScr0_ucs_axisZ_xF = planScr0_ucs_axisZ_xF;
	}

	public double getPlanScr0_ucs_axisZ_yF() {
		return planScr0_ucs_axisZ_yF;
	}

	public void setPlanScr0_ucs_axisZ_yF(double planScr0_ucs_axisZ_yF) {
		this.planScr0_ucs_axisZ_yF = planScr0_ucs_axisZ_yF;
	}

	public double getPlanScr0_ucs_axisZ_zF() {
		return planScr0_ucs_axisZ_zF;
	}

	public void setPlanScr0_ucs_axisZ_zF(double planScr0_ucs_axisZ_zF) {
		this.planScr0_ucs_axisZ_zF = planScr0_ucs_axisZ_zF;
	}

	public double getPlanScr0_ptCenter_x() {
		return planScr0_ptCenter_x;
	}

	public void setPlanScr0_ptCenter_x(double planScr0_ptCenter_x) {
		this.planScr0_ptCenter_x = planScr0_ptCenter_x;
	}

	public double getPlanScr0_ptCenter_y() {
		return planScr0_ptCenter_y;
	}

	public void setPlanScr0_ptCenter_y(double planScr0_ptCenter_y) {
		this.planScr0_ptCenter_y = planScr0_ptCenter_y;
	}

	public double getPlanScr0_ptCenter_z() {
		return planScr0_ptCenter_z;
	}

	public void setPlanScr0_ptCenter_z(double planScr0_ptCenter_z) {
		this.planScr0_ptCenter_z = planScr0_ptCenter_z;
	}

	public double getPlanScr0_xDir_xI() {
		return planScr0_xDir_xI;
	}

	public void setPlanScr0_xDir_xI(double planScr0_xDir_xI) {
		this.planScr0_xDir_xI = planScr0_xDir_xI;
	}

	public double getPlanScr0_xDir_yI() {
		return planScr0_xDir_yI;
	}

	public void setPlanScr0_xDir_yI(double planScr0_xDir_yI) {
		this.planScr0_xDir_yI = planScr0_xDir_yI;
	}

	public double getPlanScr0_xDir_zI() {
		return planScr0_xDir_zI;
	}

	public void setPlanScr0_xDir_zI(double planScr0_xDir_zI) {
		this.planScr0_xDir_zI = planScr0_xDir_zI;
	}

	public double getPlanScr0_xDir_xF() {
		return planScr0_xDir_xF;
	}

	public void setPlanScr0_xDir_xF(double planScr0_xDir_xF) {
		this.planScr0_xDir_xF = planScr0_xDir_xF;
	}

	public double getPlanScr0_xDir_yF() {
		return planScr0_xDir_yF;
	}

	public void setPlanScr0_xDir_yF(double planScr0_xDir_yF) {
		this.planScr0_xDir_yF = planScr0_xDir_yF;
	}

	public double getPlanScr0_xDir_zF() {
		return planScr0_xDir_zF;
	}

	public void setPlanScr0_xDir_zF(double planScr0_xDir_zF) {
		this.planScr0_xDir_zF = planScr0_xDir_zF;
	}

	public double getPlanScr0_axisX_xI() {
		return planScr0_axisX_xI;
	}

	public void setPlanScr0_axisX_xI(double planScr0_axisX_xI) {
		this.planScr0_axisX_xI = planScr0_axisX_xI;
	}

	public double getPlanScr0_axisX_yI() {
		return planScr0_axisX_yI;
	}

	public void setPlanScr0_axisX_yI(double planScr0_axisX_yI) {
		this.planScr0_axisX_yI = planScr0_axisX_yI;
	}

	public double getPlanScr0_axisX_zI() {
		return planScr0_axisX_zI;
	}

	public void setPlanScr0_axisX_zI(double planScr0_axisX_zI) {
		this.planScr0_axisX_zI = planScr0_axisX_zI;
	}

	public double getPlanScr0_axisX_xF() {
		return planScr0_axisX_xF;
	}

	public void setPlanScr0_axisX_xF(double planScr0_axisX_xF) {
		this.planScr0_axisX_xF = planScr0_axisX_xF;
	}

	public double getPlanScr0_axisX_yF() {
		return planScr0_axisX_yF;
	}

	public void setPlanScr0_axisX_yF(double planScr0_axisX_yF) {
		this.planScr0_axisX_yF = planScr0_axisX_yF;
	}

	public double getPlanScr0_axisX_zF() {
		return planScr0_axisX_zF;
	}

	public void setPlanScr0_axisX_zF(double planScr0_axisX_zF) {
		this.planScr0_axisX_zF = planScr0_axisX_zF;
	}

	public double getPlanScr0_axisY_xI() {
		return planScr0_axisY_xI;
	}

	public void setPlanScr0_axisY_xI(double planScr0_axisY_xI) {
		this.planScr0_axisY_xI = planScr0_axisY_xI;
	}

	public double getPlanScr0_axisY_yI() {
		return planScr0_axisY_yI;
	}

	public void setPlanScr0_axisY_yI(double planScr0_axisY_yI) {
		this.planScr0_axisY_yI = planScr0_axisY_yI;
	}

	public double getPlanScr0_axisY_zI() {
		return planScr0_axisY_zI;
	}

	public void setPlanScr0_axisY_zI(double planScr0_axisY_zI) {
		this.planScr0_axisY_zI = planScr0_axisY_zI;
	}

	public double getPlanScr0_axisY_xF() {
		return planScr0_axisY_xF;
	}

	public void setPlanScr0_axisY_xF(double planScr0_axisY_xF) {
		this.planScr0_axisY_xF = planScr0_axisY_xF;
	}

	public double getPlanScr0_axisY_yF() {
		return planScr0_axisY_yF;
	}

	public void setPlanScr0_axisY_yF(double planScr0_axisY_yF) {
		this.planScr0_axisY_yF = planScr0_axisY_yF;
	}

	public double getPlanScr0_axisY_zF() {
		return planScr0_axisY_zF;
	}

	public void setPlanScr0_axisY_zF(double planScr0_axisY_zF) {
		this.planScr0_axisY_zF = planScr0_axisY_zF;
	}

	public double getPlanScr0_axisZ_xI() {
		return planScr0_axisZ_xI;
	}

	public void setPlanScr0_axisZ_xI(double planScr0_axisZ_xI) {
		this.planScr0_axisZ_xI = planScr0_axisZ_xI;
	}

	public double getPlanScr0_axisZ_yI() {
		return planScr0_axisZ_yI;
	}

	public void setPlanScr0_axisZ_yI(double planScr0_axisZ_yI) {
		this.planScr0_axisZ_yI = planScr0_axisZ_yI;
	}

	public double getPlanScr0_axisZ_zI() {
		return planScr0_axisZ_zI;
	}

	public void setPlanScr0_axisZ_zI(double planScr0_axisZ_zI) {
		this.planScr0_axisZ_zI = planScr0_axisZ_zI;
	}

	public double getPlanScr0_axisZ_xF() {
		return planScr0_axisZ_xF;
	}

	public void setPlanScr0_axisZ_xF(double planScr0_axisZ_xF) {
		this.planScr0_axisZ_xF = planScr0_axisZ_xF;
	}

	public double getPlanScr0_axisZ_yF() {
		return planScr0_axisZ_yF;
	}

	public void setPlanScr0_axisZ_yF(double planScr0_axisZ_yF) {
		this.planScr0_axisZ_yF = planScr0_axisZ_yF;
	}

	public double getPlanScr0_axisZ_zF() {
		return planScr0_axisZ_zF;
	}

	public void setPlanScr0_axisZ_zF(double planScr0_axisZ_zF) {
		this.planScr0_axisZ_zF = planScr0_axisZ_zF;
	}

	public double getPlanScr0_width() {
		return planScr0_width;
	}

	public void setPlanScr0_width(double planScr0_width) {
		this.planScr0_width = planScr0_width;
	}

	public double getPlanScr0_height() {
		return planScr0_height;
	}

	public void setPlanScr0_height(double planScr0_height) {
		this.planScr0_height = planScr0_height;
	}

	public double getPlanScr0_ratio() {
		return planScr0_ratio;
	}

	public void setPlanScr0_ratio(double planScr0_ratio) {
		this.planScr0_ratio = planScr0_ratio;
	}

	public double getPlanScr0_ptLowerLeftCorner_x() {
		return planScr0_ptLowerLeftCorner_x;
	}

	public void setPlanScr0_ptLowerLeftCorner_x(double planScr0_ptLowerLeftCorner_x) {
		this.planScr0_ptLowerLeftCorner_x = planScr0_ptLowerLeftCorner_x;
	}

	public double getPlanScr0_ptLowerLeftCorner_y() {
		return planScr0_ptLowerLeftCorner_y;
	}

	public void setPlanScr0_ptLowerLeftCorner_y(double planScr0_ptLowerLeftCorner_y) {
		this.planScr0_ptLowerLeftCorner_y = planScr0_ptLowerLeftCorner_y;
	}

	public double getPlanScr0_ptLowerRightCorner_x() {
		return planScr0_ptLowerRightCorner_x;
	}

	public void setPlanScr0_ptLowerRightCorner_x(double planScr0_ptLowerRightCorner_x) {
		this.planScr0_ptLowerRightCorner_x = planScr0_ptLowerRightCorner_x;
	}

	public double getPlanScr0_ptLowerRightCorner_y() {
		return planScr0_ptLowerRightCorner_y;
	}

	public void setPlanScr0_ptLowerRightCorner_y(double planScr0_ptLowerRightCorner_y) {
		this.planScr0_ptLowerRightCorner_y = planScr0_ptLowerRightCorner_y;
	}

	public double getPlanScr0_ptUpperRightCorner_x() {
		return planScr0_ptUpperRightCorner_x;
	}

	public void setPlanScr0_ptUpperRightCorner_x(double planScr0_ptUpperRightCorner_x) {
		this.planScr0_ptUpperRightCorner_x = planScr0_ptUpperRightCorner_x;
	}

	public double getPlanScr0_ptUpperRightCorner_y() {
		return planScr0_ptUpperRightCorner_y;
	}

	public void setPlanScr0_ptUpperRightCorner_y(double planScr0_ptUpperRightCorner_y) {
		this.planScr0_ptUpperRightCorner_y = planScr0_ptUpperRightCorner_y;
	}

	public double getPlanScr0_ptUpperLeftCorner_x() {
		return planScr0_ptUpperLeftCorner_x;
	}

	public void setPlanScr0_ptUpperLeftCorner_x(double planScr0_ptUpperLeftCorner_x) {
		this.planScr0_ptUpperLeftCorner_x = planScr0_ptUpperLeftCorner_x;
	}

	public double getPlanScr0_ptUpperLeftCorner_y() {
		return planScr0_ptUpperLeftCorner_y;
	}

	public void setPlanScr0_ptUpperLeftCorner_y(double planScr0_ptUpperLeftCorner_y) {
		this.planScr0_ptUpperLeftCorner_y = planScr0_ptUpperLeftCorner_y;
	}

	public double getObsMcs0_ptObserver_x() {
		return obsMcs0_ptObserver_x;
	}

	public void setObsMcs0_ptObserver_x(double obsMcs0_ptObserver_x) {
		this.obsMcs0_ptObserver_x = obsMcs0_ptObserver_x;
	}

	public double getObsMcs0_ptObserver_y() {
		return obsMcs0_ptObserver_y;
	}

	public void setObsMcs0_ptObserver_y(double obsMcs0_ptObserver_y) {
		this.obsMcs0_ptObserver_y = obsMcs0_ptObserver_y;
	}

	public double getObsMcs0_ptObserver_z() {
		return obsMcs0_ptObserver_z;
	}

	public void setObsMcs0_ptObserver_z(double obsMcs0_ptObserver_z) {
		this.obsMcs0_ptObserver_z = obsMcs0_ptObserver_z;
	}

	public double getObsMcs0_uDir_xI() {
		return obsMcs0_uDir_xI;
	}

	public void setObsMcs0_uDir_xI(double obsMcs0_uDir_xI) {
		this.obsMcs0_uDir_xI = obsMcs0_uDir_xI;
	}

	public double getObsMcs0_uDir_yI() {
		return obsMcs0_uDir_yI;
	}

	public void setObsMcs0_uDir_yI(double obsMcs0_uDir_yI) {
		this.obsMcs0_uDir_yI = obsMcs0_uDir_yI;
	}

	public double getObsMcs0_uDir_zI() {
		return obsMcs0_uDir_zI;
	}

	public void setObsMcs0_uDir_zI(double obsMcs0_uDir_zI) {
		this.obsMcs0_uDir_zI = obsMcs0_uDir_zI;
	}

	public double getObsMcs0_uDir_xF() {
		return obsMcs0_uDir_xF;
	}

	public void setObsMcs0_uDir_xF(double obsMcs0_uDir_xF) {
		this.obsMcs0_uDir_xF = obsMcs0_uDir_xF;
	}

	public double getObsMcs0_uDir_yF() {
		return obsMcs0_uDir_yF;
	}

	public void setObsMcs0_uDir_yF(double obsMcs0_uDir_yF) {
		this.obsMcs0_uDir_yF = obsMcs0_uDir_yF;
	}

	public double getObsMcs0_uDir_zF() {
		return obsMcs0_uDir_zF;
	}

	public void setObsMcs0_uDir_zF(double obsMcs0_uDir_zF) {
		this.obsMcs0_uDir_zF = obsMcs0_uDir_zF;
	}

	public double getObsMcs0_nDir_xI() {
		return obsMcs0_nDir_xI;
	}

	public void setObsMcs0_nDir_xI(double obsMcs0_nDir_xI) {
		this.obsMcs0_nDir_xI = obsMcs0_nDir_xI;
	}

	public double getObsMcs0_nDir_yI() {
		return obsMcs0_nDir_yI;
	}

	public void setObsMcs0_nDir_yI(double obsMcs0_nDir_yI) {
		this.obsMcs0_nDir_yI = obsMcs0_nDir_yI;
	}

	public double getObsMcs0_nDir_zI() {
		return obsMcs0_nDir_zI;
	}

	public void setObsMcs0_nDir_zI(double obsMcs0_nDir_zI) {
		this.obsMcs0_nDir_zI = obsMcs0_nDir_zI;
	}

	public double getObsMcs0_nDir_xF() {
		return obsMcs0_nDir_xF;
	}

	public void setObsMcs0_nDir_xF(double obsMcs0_nDir_xF) {
		this.obsMcs0_nDir_xF = obsMcs0_nDir_xF;
	}

	public double getObsMcs0_nDir_yF() {
		return obsMcs0_nDir_yF;
	}

	public void setObsMcs0_nDir_yF(double obsMcs0_nDir_yF) {
		this.obsMcs0_nDir_yF = obsMcs0_nDir_yF;
	}

	public double getObsMcs0_nDir_zF() {
		return obsMcs0_nDir_zF;
	}

	public void setObsMcs0_nDir_zF(double obsMcs0_nDir_zF) {
		this.obsMcs0_nDir_zF = obsMcs0_nDir_zF;
	}

	public double getPlanMcs_ucs_ptOriginMcs_x() {
		return planMcs_ucs_ptOriginMcs_x;
	}

	public void setPlanMcs_ucs_ptOriginMcs_x(double planMcs_ucs_ptOriginMcs_x) {
		this.planMcs_ucs_ptOriginMcs_x = planMcs_ucs_ptOriginMcs_x;
	}

	public double getPlanMcs_ucs_ptOriginMcs_y() {
		return planMcs_ucs_ptOriginMcs_y;
	}

	public void setPlanMcs_ucs_ptOriginMcs_y(double planMcs_ucs_ptOriginMcs_y) {
		this.planMcs_ucs_ptOriginMcs_y = planMcs_ucs_ptOriginMcs_y;
	}

	public double getPlanMcs_ucs_ptOriginMcs_z() {
		return planMcs_ucs_ptOriginMcs_z;
	}

	public void setPlanMcs_ucs_ptOriginMcs_z(double planMcs_ucs_ptOriginMcs_z) {
		this.planMcs_ucs_ptOriginMcs_z = planMcs_ucs_ptOriginMcs_z;
	}

	public double getPlanMcs_ucs_ptXDirMcs_x() {
		return planMcs_ucs_ptXDirMcs_x;
	}

	public void setPlanMcs_ucs_ptXDirMcs_x(double planMcs_ucs_ptXDirMcs_x) {
		this.planMcs_ucs_ptXDirMcs_x = planMcs_ucs_ptXDirMcs_x;
	}

	public double getPlanMcs_ucs_ptXDirMcs_y() {
		return planMcs_ucs_ptXDirMcs_y;
	}

	public void setPlanMcs_ucs_ptXDirMcs_y(double planMcs_ucs_ptXDirMcs_y) {
		this.planMcs_ucs_ptXDirMcs_y = planMcs_ucs_ptXDirMcs_y;
	}

	public double getPlanMcs_ucs_ptXDirMcs_z() {
		return planMcs_ucs_ptXDirMcs_z;
	}

	public void setPlanMcs_ucs_ptXDirMcs_z(double planMcs_ucs_ptXDirMcs_z) {
		this.planMcs_ucs_ptXDirMcs_z = planMcs_ucs_ptXDirMcs_z;
	}

	public double getPlanMcs_ucs_ptPlanMcs_x() {
		return planMcs_ucs_ptPlanMcs_x;
	}

	public void setPlanMcs_ucs_ptPlanMcs_x(double planMcs_ucs_ptPlanMcs_x) {
		this.planMcs_ucs_ptPlanMcs_x = planMcs_ucs_ptPlanMcs_x;
	}

	public double getPlanMcs_ucs_ptPlanMcs_y() {
		return planMcs_ucs_ptPlanMcs_y;
	}

	public void setPlanMcs_ucs_ptPlanMcs_y(double planMcs_ucs_ptPlanMcs_y) {
		this.planMcs_ucs_ptPlanMcs_y = planMcs_ucs_ptPlanMcs_y;
	}

	public double getPlanMcs_ucs_ptPlanMcs_z() {
		return planMcs_ucs_ptPlanMcs_z;
	}

	public void setPlanMcs_ucs_ptPlanMcs_z(double planMcs_ucs_ptPlanMcs_z) {
		this.planMcs_ucs_ptPlanMcs_z = planMcs_ucs_ptPlanMcs_z;
	}

	public double getPlanMcs_ucs_xDirMcs_xI() {
		return planMcs_ucs_xDirMcs_xI;
	}

	public void setPlanMcs_ucs_xDirMcs_xI(double planMcs_ucs_xDirMcs_xI) {
		this.planMcs_ucs_xDirMcs_xI = planMcs_ucs_xDirMcs_xI;
	}

	public double getPlanMcs_ucs_xDirMcs_yI() {
		return planMcs_ucs_xDirMcs_yI;
	}

	public void setPlanMcs_ucs_xDirMcs_yI(double planMcs_ucs_xDirMcs_yI) {
		this.planMcs_ucs_xDirMcs_yI = planMcs_ucs_xDirMcs_yI;
	}

	public double getPlanMcs_ucs_xDirMcs_zI() {
		return planMcs_ucs_xDirMcs_zI;
	}

	public void setPlanMcs_ucs_xDirMcs_zI(double planMcs_ucs_xDirMcs_zI) {
		this.planMcs_ucs_xDirMcs_zI = planMcs_ucs_xDirMcs_zI;
	}

	public double getPlanMcs_ucs_xDirMcs_xF() {
		return planMcs_ucs_xDirMcs_xF;
	}

	public void setPlanMcs_ucs_xDirMcs_xF(double planMcs_ucs_xDirMcs_xF) {
		this.planMcs_ucs_xDirMcs_xF = planMcs_ucs_xDirMcs_xF;
	}

	public double getPlanMcs_ucs_xDirMcs_yF() {
		return planMcs_ucs_xDirMcs_yF;
	}

	public void setPlanMcs_ucs_xDirMcs_yF(double planMcs_ucs_xDirMcs_yF) {
		this.planMcs_ucs_xDirMcs_yF = planMcs_ucs_xDirMcs_yF;
	}

	public double getPlanMcs_ucs_xDirMcs_zF() {
		return planMcs_ucs_xDirMcs_zF;
	}

	public void setPlanMcs_ucs_xDirMcs_zF(double planMcs_ucs_xDirMcs_zF) {
		this.planMcs_ucs_xDirMcs_zF = planMcs_ucs_xDirMcs_zF;
	}

	public double getPlanMcs_ucs_yDirMcs_xI() {
		return planMcs_ucs_yDirMcs_xI;
	}

	public void setPlanMcs_ucs_yDirMcs_xI(double planMcs_ucs_yDirMcs_xI) {
		this.planMcs_ucs_yDirMcs_xI = planMcs_ucs_yDirMcs_xI;
	}

	public double getPlanMcs_ucs_yDirMcs_yI() {
		return planMcs_ucs_yDirMcs_yI;
	}

	public void setPlanMcs_ucs_yDirMcs_yI(double planMcs_ucs_yDirMcs_yI) {
		this.planMcs_ucs_yDirMcs_yI = planMcs_ucs_yDirMcs_yI;
	}

	public double getPlanMcs_ucs_yDirMcs_zI() {
		return planMcs_ucs_yDirMcs_zI;
	}

	public void setPlanMcs_ucs_yDirMcs_zI(double planMcs_ucs_yDirMcs_zI) {
		this.planMcs_ucs_yDirMcs_zI = planMcs_ucs_yDirMcs_zI;
	}

	public double getPlanMcs_ucs_yDirMcs_xF() {
		return planMcs_ucs_yDirMcs_xF;
	}

	public void setPlanMcs_ucs_yDirMcs_xF(double planMcs_ucs_yDirMcs_xF) {
		this.planMcs_ucs_yDirMcs_xF = planMcs_ucs_yDirMcs_xF;
	}

	public double getPlanMcs_ucs_yDirMcs_yF() {
		return planMcs_ucs_yDirMcs_yF;
	}

	public void setPlanMcs_ucs_yDirMcs_yF(double planMcs_ucs_yDirMcs_yF) {
		this.planMcs_ucs_yDirMcs_yF = planMcs_ucs_yDirMcs_yF;
	}

	public double getPlanMcs_ucs_yDirMcs_zF() {
		return planMcs_ucs_yDirMcs_zF;
	}

	public void setPlanMcs_ucs_yDirMcs_zF(double planMcs_ucs_yDirMcs_zF) {
		this.planMcs_ucs_yDirMcs_zF = planMcs_ucs_yDirMcs_zF;
	}

	public double getPlanMcs_ucs_zDirMcs_xI() {
		return planMcs_ucs_zDirMcs_xI;
	}

	public void setPlanMcs_ucs_zDirMcs_xI(double planMcs_ucs_zDirMcs_xI) {
		this.planMcs_ucs_zDirMcs_xI = planMcs_ucs_zDirMcs_xI;
	}

	public double getPlanMcs_ucs_zDirMcs_yI() {
		return planMcs_ucs_zDirMcs_yI;
	}

	public void setPlanMcs_ucs_zDirMcs_yI(double planMcs_ucs_zDirMcs_yI) {
		this.planMcs_ucs_zDirMcs_yI = planMcs_ucs_zDirMcs_yI;
	}

	public double getPlanMcs_ucs_zDirMcs_zI() {
		return planMcs_ucs_zDirMcs_zI;
	}

	public void setPlanMcs_ucs_zDirMcs_zI(double planMcs_ucs_zDirMcs_zI) {
		this.planMcs_ucs_zDirMcs_zI = planMcs_ucs_zDirMcs_zI;
	}

	public double getPlanMcs_ucs_zDirMcs_xF() {
		return planMcs_ucs_zDirMcs_xF;
	}

	public void setPlanMcs_ucs_zDirMcs_xF(double planMcs_ucs_zDirMcs_xF) {
		this.planMcs_ucs_zDirMcs_xF = planMcs_ucs_zDirMcs_xF;
	}

	public double getPlanMcs_ucs_zDirMcs_yF() {
		return planMcs_ucs_zDirMcs_yF;
	}

	public void setPlanMcs_ucs_zDirMcs_yF(double planMcs_ucs_zDirMcs_yF) {
		this.planMcs_ucs_zDirMcs_yF = planMcs_ucs_zDirMcs_yF;
	}

	public double getPlanMcs_ucs_zDirMcs_zF() {
		return planMcs_ucs_zDirMcs_zF;
	}

	public void setPlanMcs_ucs_zDirMcs_zF(double planMcs_ucs_zDirMcs_zF) {
		this.planMcs_ucs_zDirMcs_zF = planMcs_ucs_zDirMcs_zF;
	}

	public double getPlanMcs_ucs_vPlanMcs_xI() {
		return planMcs_ucs_vPlanMcs_xI;
	}

	public void setPlanMcs_ucs_vPlanMcs_xI(double planMcs_ucs_vPlanMcs_xI) {
		this.planMcs_ucs_vPlanMcs_xI = planMcs_ucs_vPlanMcs_xI;
	}

	public double getPlanMcs_ucs_vPlanMcs_yI() {
		return planMcs_ucs_vPlanMcs_yI;
	}

	public void setPlanMcs_ucs_vPlanMcs_yI(double planMcs_ucs_vPlanMcs_yI) {
		this.planMcs_ucs_vPlanMcs_yI = planMcs_ucs_vPlanMcs_yI;
	}

	public double getPlanMcs_ucs_vPlanMcs_zI() {
		return planMcs_ucs_vPlanMcs_zI;
	}

	public void setPlanMcs_ucs_vPlanMcs_zI(double planMcs_ucs_vPlanMcs_zI) {
		this.planMcs_ucs_vPlanMcs_zI = planMcs_ucs_vPlanMcs_zI;
	}

	public double getPlanMcs_ucs_vPlanMcs_xF() {
		return planMcs_ucs_vPlanMcs_xF;
	}

	public void setPlanMcs_ucs_vPlanMcs_xF(double planMcs_ucs_vPlanMcs_xF) {
		this.planMcs_ucs_vPlanMcs_xF = planMcs_ucs_vPlanMcs_xF;
	}

	public double getPlanMcs_ucs_vPlanMcs_yF() {
		return planMcs_ucs_vPlanMcs_yF;
	}

	public void setPlanMcs_ucs_vPlanMcs_yF(double planMcs_ucs_vPlanMcs_yF) {
		this.planMcs_ucs_vPlanMcs_yF = planMcs_ucs_vPlanMcs_yF;
	}

	public double getPlanMcs_ucs_vPlanMcs_zF() {
		return planMcs_ucs_vPlanMcs_zF;
	}

	public void setPlanMcs_ucs_vPlanMcs_zF(double planMcs_ucs_vPlanMcs_zF) {
		this.planMcs_ucs_vPlanMcs_zF = planMcs_ucs_vPlanMcs_zF;
	}

	public double getPlanMcs_ucs_axisX_xI() {
		return planMcs_ucs_axisX_xI;
	}

	public void setPlanMcs_ucs_axisX_xI(double planMcs_ucs_axisX_xI) {
		this.planMcs_ucs_axisX_xI = planMcs_ucs_axisX_xI;
	}

	public double getPlanMcs_ucs_axisX_yI() {
		return planMcs_ucs_axisX_yI;
	}

	public void setPlanMcs_ucs_axisX_yI(double planMcs_ucs_axisX_yI) {
		this.planMcs_ucs_axisX_yI = planMcs_ucs_axisX_yI;
	}

	public double getPlanMcs_ucs_axisX_zI() {
		return planMcs_ucs_axisX_zI;
	}

	public void setPlanMcs_ucs_axisX_zI(double planMcs_ucs_axisX_zI) {
		this.planMcs_ucs_axisX_zI = planMcs_ucs_axisX_zI;
	}

	public double getPlanMcs_ucs_axisX_xF() {
		return planMcs_ucs_axisX_xF;
	}

	public void setPlanMcs_ucs_axisX_xF(double planMcs_ucs_axisX_xF) {
		this.planMcs_ucs_axisX_xF = planMcs_ucs_axisX_xF;
	}

	public double getPlanMcs_ucs_axisX_yF() {
		return planMcs_ucs_axisX_yF;
	}

	public void setPlanMcs_ucs_axisX_yF(double planMcs_ucs_axisX_yF) {
		this.planMcs_ucs_axisX_yF = planMcs_ucs_axisX_yF;
	}

	public double getPlanMcs_ucs_axisX_zF() {
		return planMcs_ucs_axisX_zF;
	}

	public void setPlanMcs_ucs_axisX_zF(double planMcs_ucs_axisX_zF) {
		this.planMcs_ucs_axisX_zF = planMcs_ucs_axisX_zF;
	}

	public double getPlanMcs_ucs_axisY_xI() {
		return planMcs_ucs_axisY_xI;
	}

	public void setPlanMcs_ucs_axisY_xI(double planMcs_ucs_axisY_xI) {
		this.planMcs_ucs_axisY_xI = planMcs_ucs_axisY_xI;
	}

	public double getPlanMcs_ucs_axisY_yI() {
		return planMcs_ucs_axisY_yI;
	}

	public void setPlanMcs_ucs_axisY_yI(double planMcs_ucs_axisY_yI) {
		this.planMcs_ucs_axisY_yI = planMcs_ucs_axisY_yI;
	}

	public double getPlanMcs_ucs_axisY_zI() {
		return planMcs_ucs_axisY_zI;
	}

	public void setPlanMcs_ucs_axisY_zI(double planMcs_ucs_axisY_zI) {
		this.planMcs_ucs_axisY_zI = planMcs_ucs_axisY_zI;
	}

	public double getPlanMcs_ucs_axisY_xF() {
		return planMcs_ucs_axisY_xF;
	}

	public void setPlanMcs_ucs_axisY_xF(double planMcs_ucs_axisY_xF) {
		this.planMcs_ucs_axisY_xF = planMcs_ucs_axisY_xF;
	}

	public double getPlanMcs_ucs_axisY_yF() {
		return planMcs_ucs_axisY_yF;
	}

	public void setPlanMcs_ucs_axisY_yF(double planMcs_ucs_axisY_yF) {
		this.planMcs_ucs_axisY_yF = planMcs_ucs_axisY_yF;
	}

	public double getPlanMcs_ucs_axisY_zF() {
		return planMcs_ucs_axisY_zF;
	}

	public void setPlanMcs_ucs_axisY_zF(double planMcs_ucs_axisY_zF) {
		this.planMcs_ucs_axisY_zF = planMcs_ucs_axisY_zF;
	}

	public double getPlanMcs_ucs_axisZ_xI() {
		return planMcs_ucs_axisZ_xI;
	}

	public void setPlanMcs_ucs_axisZ_xI(double planMcs_ucs_axisZ_xI) {
		this.planMcs_ucs_axisZ_xI = planMcs_ucs_axisZ_xI;
	}

	public double getPlanMcs_ucs_axisZ_yI() {
		return planMcs_ucs_axisZ_yI;
	}

	public void setPlanMcs_ucs_axisZ_yI(double planMcs_ucs_axisZ_yI) {
		this.planMcs_ucs_axisZ_yI = planMcs_ucs_axisZ_yI;
	}

	public double getPlanMcs_ucs_axisZ_zI() {
		return planMcs_ucs_axisZ_zI;
	}

	public void setPlanMcs_ucs_axisZ_zI(double planMcs_ucs_axisZ_zI) {
		this.planMcs_ucs_axisZ_zI = planMcs_ucs_axisZ_zI;
	}

	public double getPlanMcs_ucs_axisZ_xF() {
		return planMcs_ucs_axisZ_xF;
	}

	public void setPlanMcs_ucs_axisZ_xF(double planMcs_ucs_axisZ_xF) {
		this.planMcs_ucs_axisZ_xF = planMcs_ucs_axisZ_xF;
	}

	public double getPlanMcs_ucs_axisZ_yF() {
		return planMcs_ucs_axisZ_yF;
	}

	public void setPlanMcs_ucs_axisZ_yF(double planMcs_ucs_axisZ_yF) {
		this.planMcs_ucs_axisZ_yF = planMcs_ucs_axisZ_yF;
	}

	public double getPlanMcs_ucs_axisZ_zF() {
		return planMcs_ucs_axisZ_zF;
	}

	public void setPlanMcs_ucs_axisZ_zF(double planMcs_ucs_axisZ_zF) {
		this.planMcs_ucs_axisZ_zF = planMcs_ucs_axisZ_zF;
	}

	public double getPlanMcs_ptCenter_x() {
		return planMcs_ptCenter_x;
	}

	public void setPlanMcs_ptCenter_x(double planMcs_ptCenter_x) {
		this.planMcs_ptCenter_x = planMcs_ptCenter_x;
	}

	public double getPlanMcs_ptCenter_y() {
		return planMcs_ptCenter_y;
	}

	public void setPlanMcs_ptCenter_y(double planMcs_ptCenter_y) {
		this.planMcs_ptCenter_y = planMcs_ptCenter_y;
	}

	public double getPlanMcs_ptCenter_z() {
		return planMcs_ptCenter_z;
	}

	public void setPlanMcs_ptCenter_z(double planMcs_ptCenter_z) {
		this.planMcs_ptCenter_z = planMcs_ptCenter_z;
	}

	public double getPlanMcs_xDir_xI() {
		return planMcs_xDir_xI;
	}

	public void setPlanMcs_xDir_xI(double planMcs_xDir_xI) {
		this.planMcs_xDir_xI = planMcs_xDir_xI;
	}

	public double getPlanMcs_xDir_yI() {
		return planMcs_xDir_yI;
	}

	public void setPlanMcs_xDir_yI(double planMcs_xDir_yI) {
		this.planMcs_xDir_yI = planMcs_xDir_yI;
	}

	public double getPlanMcs_xDir_zI() {
		return planMcs_xDir_zI;
	}

	public void setPlanMcs_xDir_zI(double planMcs_xDir_zI) {
		this.planMcs_xDir_zI = planMcs_xDir_zI;
	}

	public double getPlanMcs_xDir_xF() {
		return planMcs_xDir_xF;
	}

	public void setPlanMcs_xDir_xF(double planMcs_xDir_xF) {
		this.planMcs_xDir_xF = planMcs_xDir_xF;
	}

	public double getPlanMcs_xDir_yF() {
		return planMcs_xDir_yF;
	}

	public void setPlanMcs_xDir_yF(double planMcs_xDir_yF) {
		this.planMcs_xDir_yF = planMcs_xDir_yF;
	}

	public double getPlanMcs_xDir_zF() {
		return planMcs_xDir_zF;
	}

	public void setPlanMcs_xDir_zF(double planMcs_xDir_zF) {
		this.planMcs_xDir_zF = planMcs_xDir_zF;
	}

	public double getPlanMcs_axisX_xI() {
		return planMcs_axisX_xI;
	}

	public void setPlanMcs_axisX_xI(double planMcs_axisX_xI) {
		this.planMcs_axisX_xI = planMcs_axisX_xI;
	}

	public double getPlanMcs_axisX_yI() {
		return planMcs_axisX_yI;
	}

	public void setPlanMcs_axisX_yI(double planMcs_axisX_yI) {
		this.planMcs_axisX_yI = planMcs_axisX_yI;
	}

	public double getPlanMcs_axisX_zI() {
		return planMcs_axisX_zI;
	}

	public void setPlanMcs_axisX_zI(double planMcs_axisX_zI) {
		this.planMcs_axisX_zI = planMcs_axisX_zI;
	}

	public double getPlanMcs_axisX_xF() {
		return planMcs_axisX_xF;
	}

	public void setPlanMcs_axisX_xF(double planMcs_axisX_xF) {
		this.planMcs_axisX_xF = planMcs_axisX_xF;
	}

	public double getPlanMcs_axisX_yF() {
		return planMcs_axisX_yF;
	}

	public void setPlanMcs_axisX_yF(double planMcs_axisX_yF) {
		this.planMcs_axisX_yF = planMcs_axisX_yF;
	}

	public double getPlanMcs_axisX_zF() {
		return planMcs_axisX_zF;
	}

	public void setPlanMcs_axisX_zF(double planMcs_axisX_zF) {
		this.planMcs_axisX_zF = planMcs_axisX_zF;
	}

	public double getPlanMcs_axisY_xI() {
		return planMcs_axisY_xI;
	}

	public void setPlanMcs_axisY_xI(double planMcs_axisY_xI) {
		this.planMcs_axisY_xI = planMcs_axisY_xI;
	}

	public double getPlanMcs_axisY_yI() {
		return planMcs_axisY_yI;
	}

	public void setPlanMcs_axisY_yI(double planMcs_axisY_yI) {
		this.planMcs_axisY_yI = planMcs_axisY_yI;
	}

	public double getPlanMcs_axisY_zI() {
		return planMcs_axisY_zI;
	}

	public void setPlanMcs_axisY_zI(double planMcs_axisY_zI) {
		this.planMcs_axisY_zI = planMcs_axisY_zI;
	}

	public double getPlanMcs_axisY_xF() {
		return planMcs_axisY_xF;
	}

	public void setPlanMcs_axisY_xF(double planMcs_axisY_xF) {
		this.planMcs_axisY_xF = planMcs_axisY_xF;
	}

	public double getPlanMcs_axisY_yF() {
		return planMcs_axisY_yF;
	}

	public void setPlanMcs_axisY_yF(double planMcs_axisY_yF) {
		this.planMcs_axisY_yF = planMcs_axisY_yF;
	}

	public double getPlanMcs_axisY_zF() {
		return planMcs_axisY_zF;
	}

	public void setPlanMcs_axisY_zF(double planMcs_axisY_zF) {
		this.planMcs_axisY_zF = planMcs_axisY_zF;
	}

	public double getPlanMcs_axisZ_xI() {
		return planMcs_axisZ_xI;
	}

	public void setPlanMcs_axisZ_xI(double planMcs_axisZ_xI) {
		this.planMcs_axisZ_xI = planMcs_axisZ_xI;
	}

	public double getPlanMcs_axisZ_yI() {
		return planMcs_axisZ_yI;
	}

	public void setPlanMcs_axisZ_yI(double planMcs_axisZ_yI) {
		this.planMcs_axisZ_yI = planMcs_axisZ_yI;
	}

	public double getPlanMcs_axisZ_zI() {
		return planMcs_axisZ_zI;
	}

	public void setPlanMcs_axisZ_zI(double planMcs_axisZ_zI) {
		this.planMcs_axisZ_zI = planMcs_axisZ_zI;
	}

	public double getPlanMcs_axisZ_xF() {
		return planMcs_axisZ_xF;
	}

	public void setPlanMcs_axisZ_xF(double planMcs_axisZ_xF) {
		this.planMcs_axisZ_xF = planMcs_axisZ_xF;
	}

	public double getPlanMcs_axisZ_yF() {
		return planMcs_axisZ_yF;
	}

	public void setPlanMcs_axisZ_yF(double planMcs_axisZ_yF) {
		this.planMcs_axisZ_yF = planMcs_axisZ_yF;
	}

	public double getPlanMcs_axisZ_zF() {
		return planMcs_axisZ_zF;
	}

	public void setPlanMcs_axisZ_zF(double planMcs_axisZ_zF) {
		this.planMcs_axisZ_zF = planMcs_axisZ_zF;
	}

	public double getPlanMcs_width() {
		return planMcs_width;
	}

	public void setPlanMcs_width(double planMcs_width) {
		this.planMcs_width = planMcs_width;
	}

	public double getPlanMcs_height() {
		return planMcs_height;
	}

	public void setPlanMcs_height(double planMcs_height) {
		this.planMcs_height = planMcs_height;
	}

	public double getPlanMcs_ratio() {
		return planMcs_ratio;
	}

	public void setPlanMcs_ratio(double planMcs_ratio) {
		this.planMcs_ratio = planMcs_ratio;
	}

	public double getPlanMcs_ptLowerLeftCorner_x() {
		return planMcs_ptLowerLeftCorner_x;
	}

	public void setPlanMcs_ptLowerLeftCorner_x(double planMcs_ptLowerLeftCorner_x) {
		this.planMcs_ptLowerLeftCorner_x = planMcs_ptLowerLeftCorner_x;
	}

	public double getPlanMcs_ptLowerLeftCorner_y() {
		return planMcs_ptLowerLeftCorner_y;
	}

	public void setPlanMcs_ptLowerLeftCorner_y(double planMcs_ptLowerLeftCorner_y) {
		this.planMcs_ptLowerLeftCorner_y = planMcs_ptLowerLeftCorner_y;
	}

	public double getPlanMcs_ptLowerRightCorner_x() {
		return planMcs_ptLowerRightCorner_x;
	}

	public void setPlanMcs_ptLowerRightCorner_x(double planMcs_ptLowerRightCorner_x) {
		this.planMcs_ptLowerRightCorner_x = planMcs_ptLowerRightCorner_x;
	}

	public double getPlanMcs_ptLowerRightCorner_y() {
		return planMcs_ptLowerRightCorner_y;
	}

	public void setPlanMcs_ptLowerRightCorner_y(double planMcs_ptLowerRightCorner_y) {
		this.planMcs_ptLowerRightCorner_y = planMcs_ptLowerRightCorner_y;
	}

	public double getPlanMcs_ptUpperRightCorner_x() {
		return planMcs_ptUpperRightCorner_x;
	}

	public void setPlanMcs_ptUpperRightCorner_x(double planMcs_ptUpperRightCorner_x) {
		this.planMcs_ptUpperRightCorner_x = planMcs_ptUpperRightCorner_x;
	}

	public double getPlanMcs_ptUpperRightCorner_y() {
		return planMcs_ptUpperRightCorner_y;
	}

	public void setPlanMcs_ptUpperRightCorner_y(double planMcs_ptUpperRightCorner_y) {
		this.planMcs_ptUpperRightCorner_y = planMcs_ptUpperRightCorner_y;
	}

	public double getPlanMcs_ptUpperLeftCorner_x() {
		return planMcs_ptUpperLeftCorner_x;
	}

	public void setPlanMcs_ptUpperLeftCorner_x(double planMcs_ptUpperLeftCorner_x) {
		this.planMcs_ptUpperLeftCorner_x = planMcs_ptUpperLeftCorner_x;
	}

	public double getPlanMcs_ptUpperLeftCorner_y() {
		return planMcs_ptUpperLeftCorner_y;
	}

	public void setPlanMcs_ptUpperLeftCorner_y(double planMcs_ptUpperLeftCorner_y) {
		this.planMcs_ptUpperLeftCorner_y = planMcs_ptUpperLeftCorner_y;
	}

	public double getLimitsMcs_ptMin_x() {
		return limitsMcs_ptMin_x;
	}

	public void setLimitsMcs_ptMin_x(double limitsMcs_ptMin_x) {
		this.limitsMcs_ptMin_x = limitsMcs_ptMin_x;
	}

	public double getLimitsMcs_ptMin_y() {
		return limitsMcs_ptMin_y;
	}

	public void setLimitsMcs_ptMin_y(double limitsMcs_ptMin_y) {
		this.limitsMcs_ptMin_y = limitsMcs_ptMin_y;
	}

	public double getLimitsMcs_ptMax_x() {
		return limitsMcs_ptMax_x;
	}

	public void setLimitsMcs_ptMax_x(double limitsMcs_ptMax_x) {
		this.limitsMcs_ptMax_x = limitsMcs_ptMax_x;
	}

	public double getLimitsMcs_ptMax_y() {
		return limitsMcs_ptMax_y;
	}

	public void setLimitsMcs_ptMax_y(double limitsMcs_ptMax_y) {
		this.limitsMcs_ptMax_y = limitsMcs_ptMax_y;
	}

	public double getPlanProj_ucs_ptOriginMcs_x() {
		return planProj_ucs_ptOriginMcs_x;
	}

	public void setPlanProj_ucs_ptOriginMcs_x(double planProj_ucs_ptOriginMcs_x) {
		this.planProj_ucs_ptOriginMcs_x = planProj_ucs_ptOriginMcs_x;
	}

	public double getPlanProj_ucs_ptOriginMcs_y() {
		return planProj_ucs_ptOriginMcs_y;
	}

	public void setPlanProj_ucs_ptOriginMcs_y(double planProj_ucs_ptOriginMcs_y) {
		this.planProj_ucs_ptOriginMcs_y = planProj_ucs_ptOriginMcs_y;
	}

	public double getPlanProj_ucs_ptOriginMcs_z() {
		return planProj_ucs_ptOriginMcs_z;
	}

	public void setPlanProj_ucs_ptOriginMcs_z(double planProj_ucs_ptOriginMcs_z) {
		this.planProj_ucs_ptOriginMcs_z = planProj_ucs_ptOriginMcs_z;
	}

	public double getPlanProj_ucs_ptXDirMcs_x() {
		return planProj_ucs_ptXDirMcs_x;
	}

	public void setPlanProj_ucs_ptXDirMcs_x(double planProj_ucs_ptXDirMcs_x) {
		this.planProj_ucs_ptXDirMcs_x = planProj_ucs_ptXDirMcs_x;
	}

	public double getPlanProj_ucs_ptXDirMcs_y() {
		return planProj_ucs_ptXDirMcs_y;
	}

	public void setPlanProj_ucs_ptXDirMcs_y(double planProj_ucs_ptXDirMcs_y) {
		this.planProj_ucs_ptXDirMcs_y = planProj_ucs_ptXDirMcs_y;
	}

	public double getPlanProj_ucs_ptXDirMcs_z() {
		return planProj_ucs_ptXDirMcs_z;
	}

	public void setPlanProj_ucs_ptXDirMcs_z(double planProj_ucs_ptXDirMcs_z) {
		this.planProj_ucs_ptXDirMcs_z = planProj_ucs_ptXDirMcs_z;
	}

	public double getPlanProj_ucs_ptPlanMcs_x() {
		return planProj_ucs_ptPlanMcs_x;
	}

	public void setPlanProj_ucs_ptPlanMcs_x(double planProj_ucs_ptPlanMcs_x) {
		this.planProj_ucs_ptPlanMcs_x = planProj_ucs_ptPlanMcs_x;
	}

	public double getPlanProj_ucs_ptPlanMcs_y() {
		return planProj_ucs_ptPlanMcs_y;
	}

	public void setPlanProj_ucs_ptPlanMcs_y(double planProj_ucs_ptPlanMcs_y) {
		this.planProj_ucs_ptPlanMcs_y = planProj_ucs_ptPlanMcs_y;
	}

	public double getPlanProj_ucs_ptPlanMcs_z() {
		return planProj_ucs_ptPlanMcs_z;
	}

	public void setPlanProj_ucs_ptPlanMcs_z(double planProj_ucs_ptPlanMcs_z) {
		this.planProj_ucs_ptPlanMcs_z = planProj_ucs_ptPlanMcs_z;
	}

	public double getPlanProj_ucs_xDirMcs_xI() {
		return planProj_ucs_xDirMcs_xI;
	}

	public void setPlanProj_ucs_xDirMcs_xI(double planProj_ucs_xDirMcs_xI) {
		this.planProj_ucs_xDirMcs_xI = planProj_ucs_xDirMcs_xI;
	}

	public double getPlanProj_ucs_xDirMcs_yI() {
		return planProj_ucs_xDirMcs_yI;
	}

	public void setPlanProj_ucs_xDirMcs_yI(double planProj_ucs_xDirMcs_yI) {
		this.planProj_ucs_xDirMcs_yI = planProj_ucs_xDirMcs_yI;
	}

	public double getPlanProj_ucs_xDirMcs_zI() {
		return planProj_ucs_xDirMcs_zI;
	}

	public void setPlanProj_ucs_xDirMcs_zI(double planProj_ucs_xDirMcs_zI) {
		this.planProj_ucs_xDirMcs_zI = planProj_ucs_xDirMcs_zI;
	}

	public double getPlanProj_ucs_xDirMcs_xF() {
		return planProj_ucs_xDirMcs_xF;
	}

	public void setPlanProj_ucs_xDirMcs_xF(double planProj_ucs_xDirMcs_xF) {
		this.planProj_ucs_xDirMcs_xF = planProj_ucs_xDirMcs_xF;
	}

	public double getPlanProj_ucs_xDirMcs_yF() {
		return planProj_ucs_xDirMcs_yF;
	}

	public void setPlanProj_ucs_xDirMcs_yF(double planProj_ucs_xDirMcs_yF) {
		this.planProj_ucs_xDirMcs_yF = planProj_ucs_xDirMcs_yF;
	}

	public double getPlanProj_ucs_xDirMcs_zF() {
		return planProj_ucs_xDirMcs_zF;
	}

	public void setPlanProj_ucs_xDirMcs_zF(double planProj_ucs_xDirMcs_zF) {
		this.planProj_ucs_xDirMcs_zF = planProj_ucs_xDirMcs_zF;
	}

	public double getPlanProj_ucs_yDirMcs_xI() {
		return planProj_ucs_yDirMcs_xI;
	}

	public void setPlanProj_ucs_yDirMcs_xI(double planProj_ucs_yDirMcs_xI) {
		this.planProj_ucs_yDirMcs_xI = planProj_ucs_yDirMcs_xI;
	}

	public double getPlanProj_ucs_yDirMcs_yI() {
		return planProj_ucs_yDirMcs_yI;
	}

	public void setPlanProj_ucs_yDirMcs_yI(double planProj_ucs_yDirMcs_yI) {
		this.planProj_ucs_yDirMcs_yI = planProj_ucs_yDirMcs_yI;
	}

	public double getPlanProj_ucs_yDirMcs_zI() {
		return planProj_ucs_yDirMcs_zI;
	}

	public void setPlanProj_ucs_yDirMcs_zI(double planProj_ucs_yDirMcs_zI) {
		this.planProj_ucs_yDirMcs_zI = planProj_ucs_yDirMcs_zI;
	}

	public double getPlanProj_ucs_yDirMcs_xF() {
		return planProj_ucs_yDirMcs_xF;
	}

	public void setPlanProj_ucs_yDirMcs_xF(double planProj_ucs_yDirMcs_xF) {
		this.planProj_ucs_yDirMcs_xF = planProj_ucs_yDirMcs_xF;
	}

	public double getPlanProj_ucs_yDirMcs_yF() {
		return planProj_ucs_yDirMcs_yF;
	}

	public void setPlanProj_ucs_yDirMcs_yF(double planProj_ucs_yDirMcs_yF) {
		this.planProj_ucs_yDirMcs_yF = planProj_ucs_yDirMcs_yF;
	}

	public double getPlanProj_ucs_yDirMcs_zF() {
		return planProj_ucs_yDirMcs_zF;
	}

	public void setPlanProj_ucs_yDirMcs_zF(double planProj_ucs_yDirMcs_zF) {
		this.planProj_ucs_yDirMcs_zF = planProj_ucs_yDirMcs_zF;
	}

	public double getPlanProj_ucs_zDirMcs_xI() {
		return planProj_ucs_zDirMcs_xI;
	}

	public void setPlanProj_ucs_zDirMcs_xI(double planProj_ucs_zDirMcs_xI) {
		this.planProj_ucs_zDirMcs_xI = planProj_ucs_zDirMcs_xI;
	}

	public double getPlanProj_ucs_zDirMcs_yI() {
		return planProj_ucs_zDirMcs_yI;
	}

	public void setPlanProj_ucs_zDirMcs_yI(double planProj_ucs_zDirMcs_yI) {
		this.planProj_ucs_zDirMcs_yI = planProj_ucs_zDirMcs_yI;
	}

	public double getPlanProj_ucs_zDirMcs_zI() {
		return planProj_ucs_zDirMcs_zI;
	}

	public void setPlanProj_ucs_zDirMcs_zI(double planProj_ucs_zDirMcs_zI) {
		this.planProj_ucs_zDirMcs_zI = planProj_ucs_zDirMcs_zI;
	}

	public double getPlanProj_ucs_zDirMcs_xF() {
		return planProj_ucs_zDirMcs_xF;
	}

	public void setPlanProj_ucs_zDirMcs_xF(double planProj_ucs_zDirMcs_xF) {
		this.planProj_ucs_zDirMcs_xF = planProj_ucs_zDirMcs_xF;
	}

	public double getPlanProj_ucs_zDirMcs_yF() {
		return planProj_ucs_zDirMcs_yF;
	}

	public void setPlanProj_ucs_zDirMcs_yF(double planProj_ucs_zDirMcs_yF) {
		this.planProj_ucs_zDirMcs_yF = planProj_ucs_zDirMcs_yF;
	}

	public double getPlanProj_ucs_zDirMcs_zF() {
		return planProj_ucs_zDirMcs_zF;
	}

	public void setPlanProj_ucs_zDirMcs_zF(double planProj_ucs_zDirMcs_zF) {
		this.planProj_ucs_zDirMcs_zF = planProj_ucs_zDirMcs_zF;
	}

	public double getPlanProj_ucs_vPlanMcs_xI() {
		return planProj_ucs_vPlanMcs_xI;
	}

	public void setPlanProj_ucs_vPlanMcs_xI(double planProj_ucs_vPlanMcs_xI) {
		this.planProj_ucs_vPlanMcs_xI = planProj_ucs_vPlanMcs_xI;
	}

	public double getPlanProj_ucs_vPlanMcs_yI() {
		return planProj_ucs_vPlanMcs_yI;
	}

	public void setPlanProj_ucs_vPlanMcs_yI(double planProj_ucs_vPlanMcs_yI) {
		this.planProj_ucs_vPlanMcs_yI = planProj_ucs_vPlanMcs_yI;
	}

	public double getPlanProj_ucs_vPlanMcs_zI() {
		return planProj_ucs_vPlanMcs_zI;
	}

	public void setPlanProj_ucs_vPlanMcs_zI(double planProj_ucs_vPlanMcs_zI) {
		this.planProj_ucs_vPlanMcs_zI = planProj_ucs_vPlanMcs_zI;
	}

	public double getPlanProj_ucs_vPlanMcs_xF() {
		return planProj_ucs_vPlanMcs_xF;
	}

	public void setPlanProj_ucs_vPlanMcs_xF(double planProj_ucs_vPlanMcs_xF) {
		this.planProj_ucs_vPlanMcs_xF = planProj_ucs_vPlanMcs_xF;
	}

	public double getPlanProj_ucs_vPlanMcs_yF() {
		return planProj_ucs_vPlanMcs_yF;
	}

	public void setPlanProj_ucs_vPlanMcs_yF(double planProj_ucs_vPlanMcs_yF) {
		this.planProj_ucs_vPlanMcs_yF = planProj_ucs_vPlanMcs_yF;
	}

	public double getPlanProj_ucs_vPlanMcs_zF() {
		return planProj_ucs_vPlanMcs_zF;
	}

	public void setPlanProj_ucs_vPlanMcs_zF(double planProj_ucs_vPlanMcs_zF) {
		this.planProj_ucs_vPlanMcs_zF = planProj_ucs_vPlanMcs_zF;
	}

	public double getPlanProj_ucs_axisX_xI() {
		return planProj_ucs_axisX_xI;
	}

	public void setPlanProj_ucs_axisX_xI(double planProj_ucs_axisX_xI) {
		this.planProj_ucs_axisX_xI = planProj_ucs_axisX_xI;
	}

	public double getPlanProj_ucs_axisX_yI() {
		return planProj_ucs_axisX_yI;
	}

	public void setPlanProj_ucs_axisX_yI(double planProj_ucs_axisX_yI) {
		this.planProj_ucs_axisX_yI = planProj_ucs_axisX_yI;
	}

	public double getPlanProj_ucs_axisX_zI() {
		return planProj_ucs_axisX_zI;
	}

	public void setPlanProj_ucs_axisX_zI(double planProj_ucs_axisX_zI) {
		this.planProj_ucs_axisX_zI = planProj_ucs_axisX_zI;
	}

	public double getPlanProj_ucs_axisX_xF() {
		return planProj_ucs_axisX_xF;
	}

	public void setPlanProj_ucs_axisX_xF(double planProj_ucs_axisX_xF) {
		this.planProj_ucs_axisX_xF = planProj_ucs_axisX_xF;
	}

	public double getPlanProj_ucs_axisX_yF() {
		return planProj_ucs_axisX_yF;
	}

	public void setPlanProj_ucs_axisX_yF(double planProj_ucs_axisX_yF) {
		this.planProj_ucs_axisX_yF = planProj_ucs_axisX_yF;
	}

	public double getPlanProj_ucs_axisX_zF() {
		return planProj_ucs_axisX_zF;
	}

	public void setPlanProj_ucs_axisX_zF(double planProj_ucs_axisX_zF) {
		this.planProj_ucs_axisX_zF = planProj_ucs_axisX_zF;
	}

	public double getPlanProj_ucs_axisY_xI() {
		return planProj_ucs_axisY_xI;
	}

	public void setPlanProj_ucs_axisY_xI(double planProj_ucs_axisY_xI) {
		this.planProj_ucs_axisY_xI = planProj_ucs_axisY_xI;
	}

	public double getPlanProj_ucs_axisY_yI() {
		return planProj_ucs_axisY_yI;
	}

	public void setPlanProj_ucs_axisY_yI(double planProj_ucs_axisY_yI) {
		this.planProj_ucs_axisY_yI = planProj_ucs_axisY_yI;
	}

	public double getPlanProj_ucs_axisY_zI() {
		return planProj_ucs_axisY_zI;
	}

	public void setPlanProj_ucs_axisY_zI(double planProj_ucs_axisY_zI) {
		this.planProj_ucs_axisY_zI = planProj_ucs_axisY_zI;
	}

	public double getPlanProj_ucs_axisY_xF() {
		return planProj_ucs_axisY_xF;
	}

	public void setPlanProj_ucs_axisY_xF(double planProj_ucs_axisY_xF) {
		this.planProj_ucs_axisY_xF = planProj_ucs_axisY_xF;
	}

	public double getPlanProj_ucs_axisY_yF() {
		return planProj_ucs_axisY_yF;
	}

	public void setPlanProj_ucs_axisY_yF(double planProj_ucs_axisY_yF) {
		this.planProj_ucs_axisY_yF = planProj_ucs_axisY_yF;
	}

	public double getPlanProj_ucs_axisY_zF() {
		return planProj_ucs_axisY_zF;
	}

	public void setPlanProj_ucs_axisY_zF(double planProj_ucs_axisY_zF) {
		this.planProj_ucs_axisY_zF = planProj_ucs_axisY_zF;
	}

	public double getPlanProj_ucs_axisZ_xI() {
		return planProj_ucs_axisZ_xI;
	}

	public void setPlanProj_ucs_axisZ_xI(double planProj_ucs_axisZ_xI) {
		this.planProj_ucs_axisZ_xI = planProj_ucs_axisZ_xI;
	}

	public double getPlanProj_ucs_axisZ_yI() {
		return planProj_ucs_axisZ_yI;
	}

	public void setPlanProj_ucs_axisZ_yI(double planProj_ucs_axisZ_yI) {
		this.planProj_ucs_axisZ_yI = planProj_ucs_axisZ_yI;
	}

	public double getPlanProj_ucs_axisZ_zI() {
		return planProj_ucs_axisZ_zI;
	}

	public void setPlanProj_ucs_axisZ_zI(double planProj_ucs_axisZ_zI) {
		this.planProj_ucs_axisZ_zI = planProj_ucs_axisZ_zI;
	}

	public double getPlanProj_ucs_axisZ_xF() {
		return planProj_ucs_axisZ_xF;
	}

	public void setPlanProj_ucs_axisZ_xF(double planProj_ucs_axisZ_xF) {
		this.planProj_ucs_axisZ_xF = planProj_ucs_axisZ_xF;
	}

	public double getPlanProj_ucs_axisZ_yF() {
		return planProj_ucs_axisZ_yF;
	}

	public void setPlanProj_ucs_axisZ_yF(double planProj_ucs_axisZ_yF) {
		this.planProj_ucs_axisZ_yF = planProj_ucs_axisZ_yF;
	}

	public double getPlanProj_ucs_axisZ_zF() {
		return planProj_ucs_axisZ_zF;
	}

	public void setPlanProj_ucs_axisZ_zF(double planProj_ucs_axisZ_zF) {
		this.planProj_ucs_axisZ_zF = planProj_ucs_axisZ_zF;
	}

	public double getPlanProj_ptCenter_x() {
		return planProj_ptCenter_x;
	}

	public void setPlanProj_ptCenter_x(double planProj_ptCenter_x) {
		this.planProj_ptCenter_x = planProj_ptCenter_x;
	}

	public double getPlanProj_ptCenter_y() {
		return planProj_ptCenter_y;
	}

	public void setPlanProj_ptCenter_y(double planProj_ptCenter_y) {
		this.planProj_ptCenter_y = planProj_ptCenter_y;
	}

	public double getPlanProj_ptCenter_z() {
		return planProj_ptCenter_z;
	}

	public void setPlanProj_ptCenter_z(double planProj_ptCenter_z) {
		this.planProj_ptCenter_z = planProj_ptCenter_z;
	}

	public double getPlanProj_xDir_xI() {
		return planProj_xDir_xI;
	}

	public void setPlanProj_xDir_xI(double planProj_xDir_xI) {
		this.planProj_xDir_xI = planProj_xDir_xI;
	}

	public double getPlanProj_xDir_yI() {
		return planProj_xDir_yI;
	}

	public void setPlanProj_xDir_yI(double planProj_xDir_yI) {
		this.planProj_xDir_yI = planProj_xDir_yI;
	}

	public double getPlanProj_xDir_zI() {
		return planProj_xDir_zI;
	}

	public void setPlanProj_xDir_zI(double planProj_xDir_zI) {
		this.planProj_xDir_zI = planProj_xDir_zI;
	}

	public double getPlanProj_xDir_xF() {
		return planProj_xDir_xF;
	}

	public void setPlanProj_xDir_xF(double planProj_xDir_xF) {
		this.planProj_xDir_xF = planProj_xDir_xF;
	}

	public double getPlanProj_xDir_yF() {
		return planProj_xDir_yF;
	}

	public void setPlanProj_xDir_yF(double planProj_xDir_yF) {
		this.planProj_xDir_yF = planProj_xDir_yF;
	}

	public double getPlanProj_xDir_zF() {
		return planProj_xDir_zF;
	}

	public void setPlanProj_xDir_zF(double planProj_xDir_zF) {
		this.planProj_xDir_zF = planProj_xDir_zF;
	}

	public double getPlanProj_axisX_xI() {
		return planProj_axisX_xI;
	}

	public void setPlanProj_axisX_xI(double planProj_axisX_xI) {
		this.planProj_axisX_xI = planProj_axisX_xI;
	}

	public double getPlanProj_axisX_yI() {
		return planProj_axisX_yI;
	}

	public void setPlanProj_axisX_yI(double planProj_axisX_yI) {
		this.planProj_axisX_yI = planProj_axisX_yI;
	}

	public double getPlanProj_axisX_zI() {
		return planProj_axisX_zI;
	}

	public void setPlanProj_axisX_zI(double planProj_axisX_zI) {
		this.planProj_axisX_zI = planProj_axisX_zI;
	}

	public double getPlanProj_axisX_xF() {
		return planProj_axisX_xF;
	}

	public void setPlanProj_axisX_xF(double planProj_axisX_xF) {
		this.planProj_axisX_xF = planProj_axisX_xF;
	}

	public double getPlanProj_axisX_yF() {
		return planProj_axisX_yF;
	}

	public void setPlanProj_axisX_yF(double planProj_axisX_yF) {
		this.planProj_axisX_yF = planProj_axisX_yF;
	}

	public double getPlanProj_axisX_zF() {
		return planProj_axisX_zF;
	}

	public void setPlanProj_axisX_zF(double planProj_axisX_zF) {
		this.planProj_axisX_zF = planProj_axisX_zF;
	}

	public double getPlanProj_axisY_xI() {
		return planProj_axisY_xI;
	}

	public void setPlanProj_axisY_xI(double planProj_axisY_xI) {
		this.planProj_axisY_xI = planProj_axisY_xI;
	}

	public double getPlanProj_axisY_yI() {
		return planProj_axisY_yI;
	}

	public void setPlanProj_axisY_yI(double planProj_axisY_yI) {
		this.planProj_axisY_yI = planProj_axisY_yI;
	}

	public double getPlanProj_axisY_zI() {
		return planProj_axisY_zI;
	}

	public void setPlanProj_axisY_zI(double planProj_axisY_zI) {
		this.planProj_axisY_zI = planProj_axisY_zI;
	}

	public double getPlanProj_axisY_xF() {
		return planProj_axisY_xF;
	}

	public void setPlanProj_axisY_xF(double planProj_axisY_xF) {
		this.planProj_axisY_xF = planProj_axisY_xF;
	}

	public double getPlanProj_axisY_yF() {
		return planProj_axisY_yF;
	}

	public void setPlanProj_axisY_yF(double planProj_axisY_yF) {
		this.planProj_axisY_yF = planProj_axisY_yF;
	}

	public double getPlanProj_axisY_zF() {
		return planProj_axisY_zF;
	}

	public void setPlanProj_axisY_zF(double planProj_axisY_zF) {
		this.planProj_axisY_zF = planProj_axisY_zF;
	}

	public double getPlanProj_axisZ_xI() {
		return planProj_axisZ_xI;
	}

	public void setPlanProj_axisZ_xI(double planProj_axisZ_xI) {
		this.planProj_axisZ_xI = planProj_axisZ_xI;
	}

	public double getPlanProj_axisZ_yI() {
		return planProj_axisZ_yI;
	}

	public void setPlanProj_axisZ_yI(double planProj_axisZ_yI) {
		this.planProj_axisZ_yI = planProj_axisZ_yI;
	}

	public double getPlanProj_axisZ_zI() {
		return planProj_axisZ_zI;
	}

	public void setPlanProj_axisZ_zI(double planProj_axisZ_zI) {
		this.planProj_axisZ_zI = planProj_axisZ_zI;
	}

	public double getPlanProj_axisZ_xF() {
		return planProj_axisZ_xF;
	}

	public void setPlanProj_axisZ_xF(double planProj_axisZ_xF) {
		this.planProj_axisZ_xF = planProj_axisZ_xF;
	}

	public double getPlanProj_axisZ_yF() {
		return planProj_axisZ_yF;
	}

	public void setPlanProj_axisZ_yF(double planProj_axisZ_yF) {
		this.planProj_axisZ_yF = planProj_axisZ_yF;
	}

	public double getPlanProj_axisZ_zF() {
		return planProj_axisZ_zF;
	}

	public void setPlanProj_axisZ_zF(double planProj_axisZ_zF) {
		this.planProj_axisZ_zF = planProj_axisZ_zF;
	}

	public double getPlanProj_width() {
		return planProj_width;
	}

	public void setPlanProj_width(double planProj_width) {
		this.planProj_width = planProj_width;
	}

	public double getPlanProj_height() {
		return planProj_height;
	}

	public void setPlanProj_height(double planProj_height) {
		this.planProj_height = planProj_height;
	}

	public double getPlanProj_ratio() {
		return planProj_ratio;
	}

	public void setPlanProj_ratio(double planProj_ratio) {
		this.planProj_ratio = planProj_ratio;
	}

	public double getPlanProj_ptLowerLeftCorner_x() {
		return planProj_ptLowerLeftCorner_x;
	}

	public void setPlanProj_ptLowerLeftCorner_x(double planProj_ptLowerLeftCorner_x) {
		this.planProj_ptLowerLeftCorner_x = planProj_ptLowerLeftCorner_x;
	}

	public double getPlanProj_ptLowerLeftCorner_y() {
		return planProj_ptLowerLeftCorner_y;
	}

	public void setPlanProj_ptLowerLeftCorner_y(double planProj_ptLowerLeftCorner_y) {
		this.planProj_ptLowerLeftCorner_y = planProj_ptLowerLeftCorner_y;
	}

	public double getPlanProj_ptLowerRightCorner_x() {
		return planProj_ptLowerRightCorner_x;
	}

	public void setPlanProj_ptLowerRightCorner_x(double planProj_ptLowerRightCorner_x) {
		this.planProj_ptLowerRightCorner_x = planProj_ptLowerRightCorner_x;
	}

	public double getPlanProj_ptLowerRightCorner_y() {
		return planProj_ptLowerRightCorner_y;
	}

	public void setPlanProj_ptLowerRightCorner_y(double planProj_ptLowerRightCorner_y) {
		this.planProj_ptLowerRightCorner_y = planProj_ptLowerRightCorner_y;
	}

	public double getPlanProj_ptUpperRightCorner_x() {
		return planProj_ptUpperRightCorner_x;
	}

	public void setPlanProj_ptUpperRightCorner_x(double planProj_ptUpperRightCorner_x) {
		this.planProj_ptUpperRightCorner_x = planProj_ptUpperRightCorner_x;
	}

	public double getPlanProj_ptUpperRightCorner_y() {
		return planProj_ptUpperRightCorner_y;
	}

	public void setPlanProj_ptUpperRightCorner_y(double planProj_ptUpperRightCorner_y) {
		this.planProj_ptUpperRightCorner_y = planProj_ptUpperRightCorner_y;
	}

	public double getPlanProj_ptUpperLeftCorner_x() {
		return planProj_ptUpperLeftCorner_x;
	}

	public void setPlanProj_ptUpperLeftCorner_x(double planProj_ptUpperLeftCorner_x) {
		this.planProj_ptUpperLeftCorner_x = planProj_ptUpperLeftCorner_x;
	}

	public double getPlanProj_ptUpperLeftCorner_y() {
		return planProj_ptUpperLeftCorner_y;
	}

	public void setPlanProj_ptUpperLeftCorner_y(double planProj_ptUpperLeftCorner_y) {
		this.planProj_ptUpperLeftCorner_y = planProj_ptUpperLeftCorner_y;
	}

	public double getLimitsProj_ptMin_x() {
		return limitsProj_ptMin_x;
	}

	public void setLimitsProj_ptMin_x(double limitsProj_ptMin_x) {
		this.limitsProj_ptMin_x = limitsProj_ptMin_x;
	}

	public double getLimitsProj_ptMin_y() {
		return limitsProj_ptMin_y;
	}

	public void setLimitsProj_ptMin_y(double limitsProj_ptMin_y) {
		this.limitsProj_ptMin_y = limitsProj_ptMin_y;
	}

	public double getLimitsProj_ptMax_x() {
		return limitsProj_ptMax_x;
	}

	public void setLimitsProj_ptMax_x(double limitsProj_ptMax_x) {
		this.limitsProj_ptMax_x = limitsProj_ptMax_x;
	}

	public double getLimitsProj_ptMax_y() {
		return limitsProj_ptMax_y;
	}

	public void setLimitsProj_ptMax_y(double limitsProj_ptMax_y) {
		this.limitsProj_ptMax_y = limitsProj_ptMax_y;
	}

	public double getPlanScr_ucs_ptOriginMcs_x() {
		return planScr_ucs_ptOriginMcs_x;
	}

	public void setPlanScr_ucs_ptOriginMcs_x(double planScr_ucs_ptOriginMcs_x) {
		this.planScr_ucs_ptOriginMcs_x = planScr_ucs_ptOriginMcs_x;
	}

	public double getPlanScr_ucs_ptOriginMcs_y() {
		return planScr_ucs_ptOriginMcs_y;
	}

	public void setPlanScr_ucs_ptOriginMcs_y(double planScr_ucs_ptOriginMcs_y) {
		this.planScr_ucs_ptOriginMcs_y = planScr_ucs_ptOriginMcs_y;
	}

	public double getPlanScr_ucs_ptOriginMcs_z() {
		return planScr_ucs_ptOriginMcs_z;
	}

	public void setPlanScr_ucs_ptOriginMcs_z(double planScr_ucs_ptOriginMcs_z) {
		this.planScr_ucs_ptOriginMcs_z = planScr_ucs_ptOriginMcs_z;
	}

	public double getPlanScr_ucs_ptXDirMcs_x() {
		return planScr_ucs_ptXDirMcs_x;
	}

	public void setPlanScr_ucs_ptXDirMcs_x(double planScr_ucs_ptXDirMcs_x) {
		this.planScr_ucs_ptXDirMcs_x = planScr_ucs_ptXDirMcs_x;
	}

	public double getPlanScr_ucs_ptXDirMcs_y() {
		return planScr_ucs_ptXDirMcs_y;
	}

	public void setPlanScr_ucs_ptXDirMcs_y(double planScr_ucs_ptXDirMcs_y) {
		this.planScr_ucs_ptXDirMcs_y = planScr_ucs_ptXDirMcs_y;
	}

	public double getPlanScr_ucs_ptXDirMcs_z() {
		return planScr_ucs_ptXDirMcs_z;
	}

	public void setPlanScr_ucs_ptXDirMcs_z(double planScr_ucs_ptXDirMcs_z) {
		this.planScr_ucs_ptXDirMcs_z = planScr_ucs_ptXDirMcs_z;
	}

	public double getPlanScr_ucs_ptPlanScr_x() {
		return planScr_ucs_ptPlanScr_x;
	}

	public void setPlanScr_ucs_ptPlanScr_x(double planScr_ucs_ptPlanScr_x) {
		this.planScr_ucs_ptPlanScr_x = planScr_ucs_ptPlanScr_x;
	}

	public double getPlanScr_ucs_ptPlanScr_y() {
		return planScr_ucs_ptPlanScr_y;
	}

	public void setPlanScr_ucs_ptPlanScr_y(double planScr_ucs_ptPlanScr_y) {
		this.planScr_ucs_ptPlanScr_y = planScr_ucs_ptPlanScr_y;
	}

	public double getPlanScr_ucs_ptPlanScr_z() {
		return planScr_ucs_ptPlanScr_z;
	}

	public void setPlanScr_ucs_ptPlanScr_z(double planScr_ucs_ptPlanScr_z) {
		this.planScr_ucs_ptPlanScr_z = planScr_ucs_ptPlanScr_z;
	}

	public double getPlanScr_ucs_xDirMcs_xI() {
		return planScr_ucs_xDirMcs_xI;
	}

	public void setPlanScr_ucs_xDirMcs_xI(double planScr_ucs_xDirMcs_xI) {
		this.planScr_ucs_xDirMcs_xI = planScr_ucs_xDirMcs_xI;
	}

	public double getPlanScr_ucs_xDirMcs_yI() {
		return planScr_ucs_xDirMcs_yI;
	}

	public void setPlanScr_ucs_xDirMcs_yI(double planScr_ucs_xDirMcs_yI) {
		this.planScr_ucs_xDirMcs_yI = planScr_ucs_xDirMcs_yI;
	}

	public double getPlanScr_ucs_xDirMcs_zI() {
		return planScr_ucs_xDirMcs_zI;
	}

	public void setPlanScr_ucs_xDirMcs_zI(double planScr_ucs_xDirMcs_zI) {
		this.planScr_ucs_xDirMcs_zI = planScr_ucs_xDirMcs_zI;
	}

	public double getPlanScr_ucs_xDirMcs_xF() {
		return planScr_ucs_xDirMcs_xF;
	}

	public void setPlanScr_ucs_xDirMcs_xF(double planScr_ucs_xDirMcs_xF) {
		this.planScr_ucs_xDirMcs_xF = planScr_ucs_xDirMcs_xF;
	}

	public double getPlanScr_ucs_xDirMcs_yF() {
		return planScr_ucs_xDirMcs_yF;
	}

	public void setPlanScr_ucs_xDirMcs_yF(double planScr_ucs_xDirMcs_yF) {
		this.planScr_ucs_xDirMcs_yF = planScr_ucs_xDirMcs_yF;
	}

	public double getPlanScr_ucs_xDirMcs_zF() {
		return planScr_ucs_xDirMcs_zF;
	}

	public void setPlanScr_ucs_xDirMcs_zF(double planScr_ucs_xDirMcs_zF) {
		this.planScr_ucs_xDirMcs_zF = planScr_ucs_xDirMcs_zF;
	}

	public double getPlanScr_ucs_yDirMcs_xI() {
		return planScr_ucs_yDirMcs_xI;
	}

	public void setPlanScr_ucs_yDirMcs_xI(double planScr_ucs_yDirMcs_xI) {
		this.planScr_ucs_yDirMcs_xI = planScr_ucs_yDirMcs_xI;
	}

	public double getPlanScr_ucs_yDirMcs_yI() {
		return planScr_ucs_yDirMcs_yI;
	}

	public void setPlanScr_ucs_yDirMcs_yI(double planScr_ucs_yDirMcs_yI) {
		this.planScr_ucs_yDirMcs_yI = planScr_ucs_yDirMcs_yI;
	}

	public double getPlanScr_ucs_yDirMcs_zI() {
		return planScr_ucs_yDirMcs_zI;
	}

	public void setPlanScr_ucs_yDirMcs_zI(double planScr_ucs_yDirMcs_zI) {
		this.planScr_ucs_yDirMcs_zI = planScr_ucs_yDirMcs_zI;
	}

	public double getPlanScr_ucs_yDirMcs_xF() {
		return planScr_ucs_yDirMcs_xF;
	}

	public void setPlanScr_ucs_yDirMcs_xF(double planScr_ucs_yDirMcs_xF) {
		this.planScr_ucs_yDirMcs_xF = planScr_ucs_yDirMcs_xF;
	}

	public double getPlanScr_ucs_yDirMcs_yF() {
		return planScr_ucs_yDirMcs_yF;
	}

	public void setPlanScr_ucs_yDirMcs_yF(double planScr_ucs_yDirMcs_yF) {
		this.planScr_ucs_yDirMcs_yF = planScr_ucs_yDirMcs_yF;
	}

	public double getPlanScr_ucs_yDirMcs_zF() {
		return planScr_ucs_yDirMcs_zF;
	}

	public void setPlanScr_ucs_yDirMcs_zF(double planScr_ucs_yDirMcs_zF) {
		this.planScr_ucs_yDirMcs_zF = planScr_ucs_yDirMcs_zF;
	}

	public double getPlanScr_ucs_zDirMcs_xI() {
		return planScr_ucs_zDirMcs_xI;
	}

	public void setPlanScr_ucs_zDirMcs_xI(double planScr_ucs_zDirMcs_xI) {
		this.planScr_ucs_zDirMcs_xI = planScr_ucs_zDirMcs_xI;
	}

	public double getPlanScr_ucs_zDirMcs_yI() {
		return planScr_ucs_zDirMcs_yI;
	}

	public void setPlanScr_ucs_zDirMcs_yI(double planScr_ucs_zDirMcs_yI) {
		this.planScr_ucs_zDirMcs_yI = planScr_ucs_zDirMcs_yI;
	}

	public double getPlanScr_ucs_zDirMcs_zI() {
		return planScr_ucs_zDirMcs_zI;
	}

	public void setPlanScr_ucs_zDirMcs_zI(double planScr_ucs_zDirMcs_zI) {
		this.planScr_ucs_zDirMcs_zI = planScr_ucs_zDirMcs_zI;
	}

	public double getPlanScr_ucs_zDirMcs_xF() {
		return planScr_ucs_zDirMcs_xF;
	}

	public void setPlanScr_ucs_zDirMcs_xF(double planScr_ucs_zDirMcs_xF) {
		this.planScr_ucs_zDirMcs_xF = planScr_ucs_zDirMcs_xF;
	}

	public double getPlanScr_ucs_zDirMcs_yF() {
		return planScr_ucs_zDirMcs_yF;
	}

	public void setPlanScr_ucs_zDirMcs_yF(double planScr_ucs_zDirMcs_yF) {
		this.planScr_ucs_zDirMcs_yF = planScr_ucs_zDirMcs_yF;
	}

	public double getPlanScr_ucs_zDirMcs_zF() {
		return planScr_ucs_zDirMcs_zF;
	}

	public void setPlanScr_ucs_zDirMcs_zF(double planScr_ucs_zDirMcs_zF) {
		this.planScr_ucs_zDirMcs_zF = planScr_ucs_zDirMcs_zF;
	}

	public double getPlanScr_ucs_vPlanScr_xI() {
		return planScr_ucs_vPlanScr_xI;
	}

	public void setPlanScr_ucs_vPlanScr_xI(double planScr_ucs_vPlanScr_xI) {
		this.planScr_ucs_vPlanScr_xI = planScr_ucs_vPlanScr_xI;
	}

	public double getPlanScr_ucs_vPlanScr_yI() {
		return planScr_ucs_vPlanScr_yI;
	}

	public void setPlanScr_ucs_vPlanScr_yI(double planScr_ucs_vPlanScr_yI) {
		this.planScr_ucs_vPlanScr_yI = planScr_ucs_vPlanScr_yI;
	}

	public double getPlanScr_ucs_vPlanScr_zI() {
		return planScr_ucs_vPlanScr_zI;
	}

	public void setPlanScr_ucs_vPlanScr_zI(double planScr_ucs_vPlanScr_zI) {
		this.planScr_ucs_vPlanScr_zI = planScr_ucs_vPlanScr_zI;
	}

	public double getPlanScr_ucs_vPlanScr_xF() {
		return planScr_ucs_vPlanScr_xF;
	}

	public void setPlanScr_ucs_vPlanScr_xF(double planScr_ucs_vPlanScr_xF) {
		this.planScr_ucs_vPlanScr_xF = planScr_ucs_vPlanScr_xF;
	}

	public double getPlanScr_ucs_vPlanScr_yF() {
		return planScr_ucs_vPlanScr_yF;
	}

	public void setPlanScr_ucs_vPlanScr_yF(double planScr_ucs_vPlanScr_yF) {
		this.planScr_ucs_vPlanScr_yF = planScr_ucs_vPlanScr_yF;
	}

	public double getPlanScr_ucs_vPlanScr_zF() {
		return planScr_ucs_vPlanScr_zF;
	}

	public void setPlanScr_ucs_vPlanScr_zF(double planScr_ucs_vPlanScr_zF) {
		this.planScr_ucs_vPlanScr_zF = planScr_ucs_vPlanScr_zF;
	}

	public double getPlanScr_ucs_axisX_xI() {
		return planScr_ucs_axisX_xI;
	}

	public void setPlanScr_ucs_axisX_xI(double planScr_ucs_axisX_xI) {
		this.planScr_ucs_axisX_xI = planScr_ucs_axisX_xI;
	}

	public double getPlanScr_ucs_axisX_yI() {
		return planScr_ucs_axisX_yI;
	}

	public void setPlanScr_ucs_axisX_yI(double planScr_ucs_axisX_yI) {
		this.planScr_ucs_axisX_yI = planScr_ucs_axisX_yI;
	}

	public double getPlanScr_ucs_axisX_zI() {
		return planScr_ucs_axisX_zI;
	}

	public void setPlanScr_ucs_axisX_zI(double planScr_ucs_axisX_zI) {
		this.planScr_ucs_axisX_zI = planScr_ucs_axisX_zI;
	}

	public double getPlanScr_ucs_axisX_xF() {
		return planScr_ucs_axisX_xF;
	}

	public void setPlanScr_ucs_axisX_xF(double planScr_ucs_axisX_xF) {
		this.planScr_ucs_axisX_xF = planScr_ucs_axisX_xF;
	}

	public double getPlanScr_ucs_axisX_yF() {
		return planScr_ucs_axisX_yF;
	}

	public void setPlanScr_ucs_axisX_yF(double planScr_ucs_axisX_yF) {
		this.planScr_ucs_axisX_yF = planScr_ucs_axisX_yF;
	}

	public double getPlanScr_ucs_axisX_zF() {
		return planScr_ucs_axisX_zF;
	}

	public void setPlanScr_ucs_axisX_zF(double planScr_ucs_axisX_zF) {
		this.planScr_ucs_axisX_zF = planScr_ucs_axisX_zF;
	}

	public double getPlanScr_ucs_axisY_xI() {
		return planScr_ucs_axisY_xI;
	}

	public void setPlanScr_ucs_axisY_xI(double planScr_ucs_axisY_xI) {
		this.planScr_ucs_axisY_xI = planScr_ucs_axisY_xI;
	}

	public double getPlanScr_ucs_axisY_yI() {
		return planScr_ucs_axisY_yI;
	}

	public void setPlanScr_ucs_axisY_yI(double planScr_ucs_axisY_yI) {
		this.planScr_ucs_axisY_yI = planScr_ucs_axisY_yI;
	}

	public double getPlanScr_ucs_axisY_zI() {
		return planScr_ucs_axisY_zI;
	}

	public void setPlanScr_ucs_axisY_zI(double planScr_ucs_axisY_zI) {
		this.planScr_ucs_axisY_zI = planScr_ucs_axisY_zI;
	}

	public double getPlanScr_ucs_axisY_xF() {
		return planScr_ucs_axisY_xF;
	}

	public void setPlanScr_ucs_axisY_xF(double planScr_ucs_axisY_xF) {
		this.planScr_ucs_axisY_xF = planScr_ucs_axisY_xF;
	}

	public double getPlanScr_ucs_axisY_yF() {
		return planScr_ucs_axisY_yF;
	}

	public void setPlanScr_ucs_axisY_yF(double planScr_ucs_axisY_yF) {
		this.planScr_ucs_axisY_yF = planScr_ucs_axisY_yF;
	}

	public double getPlanScr_ucs_axisY_zF() {
		return planScr_ucs_axisY_zF;
	}

	public void setPlanScr_ucs_axisY_zF(double planScr_ucs_axisY_zF) {
		this.planScr_ucs_axisY_zF = planScr_ucs_axisY_zF;
	}

	public double getPlanScr_ucs_axisZ_xI() {
		return planScr_ucs_axisZ_xI;
	}

	public void setPlanScr_ucs_axisZ_xI(double planScr_ucs_axisZ_xI) {
		this.planScr_ucs_axisZ_xI = planScr_ucs_axisZ_xI;
	}

	public double getPlanScr_ucs_axisZ_yI() {
		return planScr_ucs_axisZ_yI;
	}

	public void setPlanScr_ucs_axisZ_yI(double planScr_ucs_axisZ_yI) {
		this.planScr_ucs_axisZ_yI = planScr_ucs_axisZ_yI;
	}

	public double getPlanScr_ucs_axisZ_zI() {
		return planScr_ucs_axisZ_zI;
	}

	public void setPlanScr_ucs_axisZ_zI(double planScr_ucs_axisZ_zI) {
		this.planScr_ucs_axisZ_zI = planScr_ucs_axisZ_zI;
	}

	public double getPlanScr_ucs_axisZ_xF() {
		return planScr_ucs_axisZ_xF;
	}

	public void setPlanScr_ucs_axisZ_xF(double planScr_ucs_axisZ_xF) {
		this.planScr_ucs_axisZ_xF = planScr_ucs_axisZ_xF;
	}

	public double getPlanScr_ucs_axisZ_yF() {
		return planScr_ucs_axisZ_yF;
	}

	public void setPlanScr_ucs_axisZ_yF(double planScr_ucs_axisZ_yF) {
		this.planScr_ucs_axisZ_yF = planScr_ucs_axisZ_yF;
	}

	public double getPlanScr_ucs_axisZ_zF() {
		return planScr_ucs_axisZ_zF;
	}

	public void setPlanScr_ucs_axisZ_zF(double planScr_ucs_axisZ_zF) {
		this.planScr_ucs_axisZ_zF = planScr_ucs_axisZ_zF;
	}

	public double getPlanScr_ptCenter_x() {
		return planScr_ptCenter_x;
	}

	public void setPlanScr_ptCenter_x(double planScr_ptCenter_x) {
		this.planScr_ptCenter_x = planScr_ptCenter_x;
	}

	public double getPlanScr_ptCenter_y() {
		return planScr_ptCenter_y;
	}

	public void setPlanScr_ptCenter_y(double planScr_ptCenter_y) {
		this.planScr_ptCenter_y = planScr_ptCenter_y;
	}

	public double getPlanScr_ptCenter_z() {
		return planScr_ptCenter_z;
	}

	public void setPlanScr_ptCenter_z(double planScr_ptCenter_z) {
		this.planScr_ptCenter_z = planScr_ptCenter_z;
	}

	public double getPlanScr_xDir_xI() {
		return planScr_xDir_xI;
	}

	public void setPlanScr_xDir_xI(double planScr_xDir_xI) {
		this.planScr_xDir_xI = planScr_xDir_xI;
	}

	public double getPlanScr_xDir_yI() {
		return planScr_xDir_yI;
	}

	public void setPlanScr_xDir_yI(double planScr_xDir_yI) {
		this.planScr_xDir_yI = planScr_xDir_yI;
	}

	public double getPlanScr_xDir_zI() {
		return planScr_xDir_zI;
	}

	public void setPlanScr_xDir_zI(double planScr_xDir_zI) {
		this.planScr_xDir_zI = planScr_xDir_zI;
	}

	public double getPlanScr_xDir_xF() {
		return planScr_xDir_xF;
	}

	public void setPlanScr_xDir_xF(double planScr_xDir_xF) {
		this.planScr_xDir_xF = planScr_xDir_xF;
	}

	public double getPlanScr_xDir_yF() {
		return planScr_xDir_yF;
	}

	public void setPlanScr_xDir_yF(double planScr_xDir_yF) {
		this.planScr_xDir_yF = planScr_xDir_yF;
	}

	public double getPlanScr_xDir_zF() {
		return planScr_xDir_zF;
	}

	public void setPlanScr_xDir_zF(double planScr_xDir_zF) {
		this.planScr_xDir_zF = planScr_xDir_zF;
	}

	public double getPlanScr_axisX_xI() {
		return planScr_axisX_xI;
	}

	public void setPlanScr_axisX_xI(double planScr_axisX_xI) {
		this.planScr_axisX_xI = planScr_axisX_xI;
	}

	public double getPlanScr_axisX_yI() {
		return planScr_axisX_yI;
	}

	public void setPlanScr_axisX_yI(double planScr_axisX_yI) {
		this.planScr_axisX_yI = planScr_axisX_yI;
	}

	public double getPlanScr_axisX_zI() {
		return planScr_axisX_zI;
	}

	public void setPlanScr_axisX_zI(double planScr_axisX_zI) {
		this.planScr_axisX_zI = planScr_axisX_zI;
	}

	public double getPlanScr_axisX_xF() {
		return planScr_axisX_xF;
	}

	public void setPlanScr_axisX_xF(double planScr_axisX_xF) {
		this.planScr_axisX_xF = planScr_axisX_xF;
	}

	public double getPlanScr_axisX_yF() {
		return planScr_axisX_yF;
	}

	public void setPlanScr_axisX_yF(double planScr_axisX_yF) {
		this.planScr_axisX_yF = planScr_axisX_yF;
	}

	public double getPlanScr_axisX_zF() {
		return planScr_axisX_zF;
	}

	public void setPlanScr_axisX_zF(double planScr_axisX_zF) {
		this.planScr_axisX_zF = planScr_axisX_zF;
	}

	public double getPlanScr_axisY_xI() {
		return planScr_axisY_xI;
	}

	public void setPlanScr_axisY_xI(double planScr_axisY_xI) {
		this.planScr_axisY_xI = planScr_axisY_xI;
	}

	public double getPlanScr_axisY_yI() {
		return planScr_axisY_yI;
	}

	public void setPlanScr_axisY_yI(double planScr_axisY_yI) {
		this.planScr_axisY_yI = planScr_axisY_yI;
	}

	public double getPlanScr_axisY_zI() {
		return planScr_axisY_zI;
	}

	public void setPlanScr_axisY_zI(double planScr_axisY_zI) {
		this.planScr_axisY_zI = planScr_axisY_zI;
	}

	public double getPlanScr_axisY_xF() {
		return planScr_axisY_xF;
	}

	public void setPlanScr_axisY_xF(double planScr_axisY_xF) {
		this.planScr_axisY_xF = planScr_axisY_xF;
	}

	public double getPlanScr_axisY_yF() {
		return planScr_axisY_yF;
	}

	public void setPlanScr_axisY_yF(double planScr_axisY_yF) {
		this.planScr_axisY_yF = planScr_axisY_yF;
	}

	public double getPlanScr_axisY_zF() {
		return planScr_axisY_zF;
	}

	public void setPlanScr_axisY_zF(double planScr_axisY_zF) {
		this.planScr_axisY_zF = planScr_axisY_zF;
	}

	public double getPlanScr_axisZ_xI() {
		return planScr_axisZ_xI;
	}

	public void setPlanScr_axisZ_xI(double planScr_axisZ_xI) {
		this.planScr_axisZ_xI = planScr_axisZ_xI;
	}

	public double getPlanScr_axisZ_yI() {
		return planScr_axisZ_yI;
	}

	public void setPlanScr_axisZ_yI(double planScr_axisZ_yI) {
		this.planScr_axisZ_yI = planScr_axisZ_yI;
	}

	public double getPlanScr_axisZ_zI() {
		return planScr_axisZ_zI;
	}

	public void setPlanScr_axisZ_zI(double planScr_axisZ_zI) {
		this.planScr_axisZ_zI = planScr_axisZ_zI;
	}

	public double getPlanScr_axisZ_xF() {
		return planScr_axisZ_xF;
	}

	public void setPlanScr_axisZ_xF(double planScr_axisZ_xF) {
		this.planScr_axisZ_xF = planScr_axisZ_xF;
	}

	public double getPlanScr_axisZ_yF() {
		return planScr_axisZ_yF;
	}

	public void setPlanScr_axisZ_yF(double planScr_axisZ_yF) {
		this.planScr_axisZ_yF = planScr_axisZ_yF;
	}

	public double getPlanScr_axisZ_zF() {
		return planScr_axisZ_zF;
	}

	public void setPlanScr_axisZ_zF(double planScr_axisZ_zF) {
		this.planScr_axisZ_zF = planScr_axisZ_zF;
	}

	public double getPlanScr_width() {
		return planScr_width;
	}

	public void setPlanScr_width(double planScr_width) {
		this.planScr_width = planScr_width;
	}

	public double getPlanScr_height() {
		return planScr_height;
	}

	public void setPlanScr_height(double planScr_height) {
		this.planScr_height = planScr_height;
	}

	public double getPlanScr_ratio() {
		return planScr_ratio;
	}

	public void setPlanScr_ratio(double planScr_ratio) {
		this.planScr_ratio = planScr_ratio;
	}

	public double getPlanScr_ptLowerLeftCorner_x() {
		return planScr_ptLowerLeftCorner_x;
	}

	public void setPlanScr_ptLowerLeftCorner_x(double planScr_ptLowerLeftCorner_x) {
		this.planScr_ptLowerLeftCorner_x = planScr_ptLowerLeftCorner_x;
	}

	public double getPlanScr_ptLowerLeftCorner_y() {
		return planScr_ptLowerLeftCorner_y;
	}

	public void setPlanScr_ptLowerLeftCorner_y(double planScr_ptLowerLeftCorner_y) {
		this.planScr_ptLowerLeftCorner_y = planScr_ptLowerLeftCorner_y;
	}

	public double getPlanScr_ptLowerRightCorner_x() {
		return planScr_ptLowerRightCorner_x;
	}

	public void setPlanScr_ptLowerRightCorner_x(double planScr_ptLowerRightCorner_x) {
		this.planScr_ptLowerRightCorner_x = planScr_ptLowerRightCorner_x;
	}

	public double getPlanScr_ptLowerRightCorner_y() {
		return planScr_ptLowerRightCorner_y;
	}

	public void setPlanScr_ptLowerRightCorner_y(double planScr_ptLowerRightCorner_y) {
		this.planScr_ptLowerRightCorner_y = planScr_ptLowerRightCorner_y;
	}

	public double getPlanScr_ptUpperRightCorner_x() {
		return planScr_ptUpperRightCorner_x;
	}

	public void setPlanScr_ptUpperRightCorner_x(double planScr_ptUpperRightCorner_x) {
		this.planScr_ptUpperRightCorner_x = planScr_ptUpperRightCorner_x;
	}

	public double getPlanScr_ptUpperRightCorner_y() {
		return planScr_ptUpperRightCorner_y;
	}

	public void setPlanScr_ptUpperRightCorner_y(double planScr_ptUpperRightCorner_y) {
		this.planScr_ptUpperRightCorner_y = planScr_ptUpperRightCorner_y;
	}

	public double getPlanScr_ptUpperLeftCorner_x() {
		return planScr_ptUpperLeftCorner_x;
	}

	public void setPlanScr_ptUpperLeftCorner_x(double planScr_ptUpperLeftCorner_x) {
		this.planScr_ptUpperLeftCorner_x = planScr_ptUpperLeftCorner_x;
	}

	public double getPlanScr_ptUpperLeftCorner_y() {
		return planScr_ptUpperLeftCorner_y;
	}

	public void setPlanScr_ptUpperLeftCorner_y(double planScr_ptUpperLeftCorner_y) {
		this.planScr_ptUpperLeftCorner_y = planScr_ptUpperLeftCorner_y;
	}

	public double getObsMcs_ptObserver_x() {
		return obsMcs_ptObserver_x;
	}

	public void setObsMcs_ptObserver_x(double obsMcs_ptObserver_x) {
		this.obsMcs_ptObserver_x = obsMcs_ptObserver_x;
	}

	public double getObsMcs_ptObserver_y() {
		return obsMcs_ptObserver_y;
	}

	public void setObsMcs_ptObserver_y(double obsMcs_ptObserver_y) {
		this.obsMcs_ptObserver_y = obsMcs_ptObserver_y;
	}

	public double getObsMcs_ptObserver_z() {
		return obsMcs_ptObserver_z;
	}

	public void setObsMcs_ptObserver_z(double obsMcs_ptObserver_z) {
		this.obsMcs_ptObserver_z = obsMcs_ptObserver_z;
	}

	public double getObsMcs_uDir_xI() {
		return obsMcs_uDir_xI;
	}

	public void setObsMcs_uDir_xI(double obsMcs_uDir_xI) {
		this.obsMcs_uDir_xI = obsMcs_uDir_xI;
	}

	public double getObsMcs_uDir_yI() {
		return obsMcs_uDir_yI;
	}

	public void setObsMcs_uDir_yI(double obsMcs_uDir_yI) {
		this.obsMcs_uDir_yI = obsMcs_uDir_yI;
	}

	public double getObsMcs_uDir_zI() {
		return obsMcs_uDir_zI;
	}

	public void setObsMcs_uDir_zI(double obsMcs_uDir_zI) {
		this.obsMcs_uDir_zI = obsMcs_uDir_zI;
	}

	public double getObsMcs_uDir_xF() {
		return obsMcs_uDir_xF;
	}

	public void setObsMcs_uDir_xF(double obsMcs_uDir_xF) {
		this.obsMcs_uDir_xF = obsMcs_uDir_xF;
	}

	public double getObsMcs_uDir_yF() {
		return obsMcs_uDir_yF;
	}

	public void setObsMcs_uDir_yF(double obsMcs_uDir_yF) {
		this.obsMcs_uDir_yF = obsMcs_uDir_yF;
	}

	public double getObsMcs_uDir_zF() {
		return obsMcs_uDir_zF;
	}

	public void setObsMcs_uDir_zF(double obsMcs_uDir_zF) {
		this.obsMcs_uDir_zF = obsMcs_uDir_zF;
	}

	public double getObsMcs_nDir_xI() {
		return obsMcs_nDir_xI;
	}

	public void setObsMcs_nDir_xI(double obsMcs_nDir_xI) {
		this.obsMcs_nDir_xI = obsMcs_nDir_xI;
	}

	public double getObsMcs_nDir_yI() {
		return obsMcs_nDir_yI;
	}

	public void setObsMcs_nDir_yI(double obsMcs_nDir_yI) {
		this.obsMcs_nDir_yI = obsMcs_nDir_yI;
	}

	public double getObsMcs_nDir_zI() {
		return obsMcs_nDir_zI;
	}

	public void setObsMcs_nDir_zI(double obsMcs_nDir_zI) {
		this.obsMcs_nDir_zI = obsMcs_nDir_zI;
	}

	public double getObsMcs_nDir_xF() {
		return obsMcs_nDir_xF;
	}

	public void setObsMcs_nDir_xF(double obsMcs_nDir_xF) {
		this.obsMcs_nDir_xF = obsMcs_nDir_xF;
	}

	public double getObsMcs_nDir_yF() {
		return obsMcs_nDir_yF;
	}

	public void setObsMcs_nDir_yF(double obsMcs_nDir_yF) {
		this.obsMcs_nDir_yF = obsMcs_nDir_yF;
	}

	public double getObsMcs_nDir_zF() {
		return obsMcs_nDir_zF;
	}

	public void setObsMcs_nDir_zF(double obsMcs_nDir_zF) {
		this.obsMcs_nDir_zF = obsMcs_nDir_zF;
	}

	public double getScaleProj0() {
		return scaleProj0;
	}

	public void setScaleProj0(double scaleProj0) {
		this.scaleProj0 = scaleProj0;
	}

	public double getScaleScr0() {
		return scaleScr0;
	}

	public void setScaleScr0(double scaleScr0) {
		this.scaleScr0 = scaleScr0;
	}

	public double getScaleProj() {
		return scaleProj;
	}

	public void setScaleProj(double scaleProj) {
		this.scaleProj = scaleProj;
	}

	public double getScaleScr() {
		return scaleScr;
	}

	public void setScaleScr(double scaleScr) {
		this.scaleScr = scaleScr;
	}
    
}
