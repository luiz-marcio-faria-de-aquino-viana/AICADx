/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * IfcDefs.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/03/2026
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

package br.com.tlmv.aicadxapp.ifc;

public class IfcDefs 
{
//Public Static
	
	/* IFC_SECTION_TYPE
	 */
	public static final int IFCSECTIONTYPE_NONE_VAL				= -1;	
	public static final int IFCSECTIONTYPE_FILEDATA_VAL			= 1001;	
	public static final int IFCSECTIONTYPE_HEADER_VAL			= 1002;	
	public static final int IFCSECTIONTYPE_DATA_VAL				= 1003;	
	
	/* IFC_TYPE
	 */
	public static final int IFCTYPE_INT							= 2001;	
	public static final int IFCTYPE_LNG							= 2002;	
	public static final int IFCTYPE_DBL							= 2003;
	public static final int IFCTYPE_STR							= 2004;
	public static final int IFCTYPE_PTR							= 2005;	
	
	/* IFC_SECTION
	 */
	public static final String IFCSECTION_FILEDEF_STR			= "ISO-10303-21;";
	public static final String IFCSECTION_HEADER_STR			= "HEADER;";	
	public static final String IFCSECTION_DATA_STR				= "DATA;";	
	public static final String IFCSECTION_ENDSEC_STR			= "ENDSEC;";	
	public static final String IFCSECTION_ENDFILEDEF_STR		= "END-ISO-10303-21;";

	/* IFC_COORD
	 */
	public static final int IFCCOORD_X							= 0;	
	public static final int IFCCOORD_Y							= 1;	
	public static final int IFCCOORD_Z							= 2;	
	
	//TAGS
	public static String tagIfcUnknow 							= "IfcUnknow";
	public static String tagIfcOrganization						= "IfcOrganization";
	public static String tagIfcApplication						= "IfcApplication";
	public static String tagIfcProject 							= "IfcProject";
	public static String tagIfcSite 							= "IfcSite";
	public static String tagIfcBuilding							= "IfcBuilding";
	public static String tagIfcBuildingStorey					= "IfcBuildingStorey";
	public static String tagIfcSpace							= "IfcSpace";
	public static String tagIfcWall 							= "IfcWall";
	public static String tagIfcWallStandardCase					= "IfcWallStandardCase";
	public static String tagIfcLocalPlacement					= "IfcLocalPlacement";
	public static String tagIfcAxis2Placement3D					= "IfcAxis2Placement3D";
	public static String tagIfcDoor								= "IfcDoor";
	public static String tagIfcWindow							= "IfcWindow";
	public static String tagIfcFace 							= "IfcFace";
	public static String tagIfcFaceBound						= "IfcFaceBound";
	public static String tagIfcFaceOuterBound					= "IfcFaceOuterBound";
	public static String tagIfcPolyLoop							= "IfcPolyLoop";
	public static String tagIfcPolyline							= "IfcPolyline";
	public static String tagIfcCartesianPoint					= "IfcCartesianPoint";
	
	//IFCPROJECT
	//
	public static int ifcProjectLongName 						= 0;
	public static int ifcProjectPhase 							= 1;	
	public static int ifcProjectRepresentationContexts 			= 2;
	public static int ifcProjectUnitsInContext 					= 3;
	
}
