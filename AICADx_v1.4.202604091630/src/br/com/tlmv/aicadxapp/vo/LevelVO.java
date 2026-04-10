/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * LevelVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/11/2025
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

package br.com.tlmv.aicadxapp.vo;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class LevelVO extends ItemDataVO
{
//Private
	private int objectId;
	private String levelLocalName;
	private String levelLocalText;
	private GeomPoint3d ptI;
    private GeomPoint3d ptF;
    private double zLevel;
    
//Public

    public LevelVO(
    	int objectId,
		String levelLocalName,
		String levelLocalText,
		double xI, 
		double yI, 
		double xF, 
		double yF, 
		double zLevel) 
	{
    	super(levelLocalName, levelLocalText, zLevel);
    	
		this.init(
			objectId,
			levelLocalName,
			levelLocalText,
			xI, 
			yI, 
			xF, 
			yF, 
			zLevel);
	}
	
    public LevelVO(
    	int objectId,
		String levelLocalName,
		String levelLocalText,
		GeomPoint2d ptI, 
		GeomPoint2d ptF, 
		double zLevel) 
	{
    	super(levelLocalName, levelLocalText, zLevel);
    	
		this.init(
			objectId,
			levelLocalName,
			levelLocalText,
			ptI.getX(), 
			ptI.getY(), 
			ptF.getX(), 
			ptF.getY(), 
			zLevel);
	}
	
    public LevelVO(
    	int objectId,
		String levelLocalName,
		String levelLocalText,
		GeomPoint3d ptI, 
		GeomPoint3d ptF, 
		double zLevel) 
	{
    	super(levelLocalName, levelLocalText, zLevel);
    	
		this.init(
			objectId,
			levelLocalName,
			levelLocalText,
			ptI.getX(), 
			ptI.getY(), 
			ptF.getX(), 
			ptF.getY(), 
			zLevel);
	}
    
    public LevelVO(
		String levelLocalName,
		String levelLocalText,
		double xI, 
		double yI, 
		double xF, 
		double yF, 
		double zLevel) 
	{
    	super(levelLocalName, levelLocalText, zLevel);
    	
		this.init(
			AppDefs.NULL_INT,
			levelLocalName,
			levelLocalText,
			xI, 
			yI, 
			xF, 
			yF, 
			zLevel);
	}
	
    public LevelVO(
		String levelLocalName,
		String levelLocalText,
		GeomPoint2d ptI, 
		GeomPoint2d ptF, 
		double zLevel) 
	{
    	super(levelLocalName, levelLocalText, zLevel);
    	
		this.init(
			AppDefs.NULL_INT,
			levelLocalName,
			levelLocalText,
			ptI.getX(), 
			ptI.getY(), 
			ptF.getX(), 
			ptF.getY(), 
			zLevel);
	}
	
    public LevelVO(
		String levelLocalName,
		String levelLocalText,
		GeomPoint3d ptI, 
		GeomPoint3d ptF, 
		double zLevel) 
	{
    	super(levelLocalName, levelLocalText, zLevel);
    	
		this.init(
			AppDefs.NULL_INT,
			levelLocalName,
			levelLocalText,
			ptI.getX(), 
			ptI.getY(), 
			ptF.getX(), 
			ptF.getY(), 
			zLevel);
	}
	
    public LevelVO(LevelVO other) 
	{
    	super(other.levelLocalName, other.levelLocalText, other.zLevel);
    	
		GeomPoint3d ptI = other.ptI;
		GeomPoint3d ptF = other.ptF;
		
		this.init(
			other.objectId,
			other.levelLocalName,
			other.levelLocalText,
			ptI.getX(), 
			ptI.getY(), 
			ptF.getX(), 
			ptF.getY(), 
			other.zLevel);
	}
	
    public LevelVO(CadLevel other) 
	{
    	super(other.getLevelLocalName(), other.getLevelLocalText(), other.getZLevel());
    	
		GeomPoint3d ptI = other.getPtI();
		GeomPoint3d ptF = other.getPtF();
		
		this.init(
			other.getObjectId(),
			other.getLevelLocalName(), 
			other.getLevelLocalText(), 
			ptI.getX(), 
			ptI.getY(), 
			ptF.getX(), 
			ptF.getY(), 
			other.getZLevel() );
	}

	/* Methodes */
	
	public void init(
		int objectId,
		String levelLocalName,
		String levelLocalText,
		double xI, 
		double yI, 
		double xF, 
		double yF, 
		double zLevel) 
	{
		this.objectId = objectId;
		this.levelLocalName = levelLocalName;
		this.levelLocalText = levelLocalText;
    	this.ptI = new GeomPoint3d(xI, yI, zLevel);
    	this.ptF = new GeomPoint3d(xF, yF, zLevel);
    	this.zLevel = zLevel;
    }

	@Override
	public String toString()
	{
		String str = this.getLabel();
		return str;
	}
	
	/* DEBUG */

	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"(XI: %s; YI: %s; ZI: %s)-(XF: %s; YF: %s; ZF: %s); ObjectId: %s; Name: %s; Text: %s; Z-Level: %s; ", 
			nf6.format(this.ptI.getX()), 
			nf6.format(this.ptI.getY()), 
			nf6.format(this.ptI.getZ()),
			nf6.format(this.ptF.getX()), 
			nf6.format(this.ptF.getY()), 
			nf6.format(this.ptF.getZ()),
			this.objectId,
			this.levelLocalName,
			this.levelLocalText,
			nf6.format(this.zLevel) );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

	/* Getters/Setters */
	
    public GeomPoint3d getPtI() {
        return this.ptI;
    }

    public GeomPoint3d getPtF() {
        return this.ptF;
    }

	public double getZLevel() {
		return zLevel;
	}

	public void setZLevel(double zLevel) {
		this.zLevel = zLevel;
	}

	public void setPtI(GeomPoint3d ptI) {
		this.ptI = ptI;
	}

	public void setPtF(GeomPoint3d ptF) {
		this.ptF = ptF;
	}
	
	public String getSign()
	{
		String strSign = (this.zLevel < 0) ? "-" : "+";
		return strSign;
	}		
	
	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getLevelLocalName() {
		return levelLocalName;
	}

	public void setLevelLocalName(String levelLocalName) {
		this.levelLocalName = levelLocalName;
	}

	public String getLevelLocalText() {
		return levelLocalText;
	}

	public void setLevelLocalText(String levelLocalText) {
		this.levelLocalText = levelLocalText;
	}

	public String getLabel()
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);
		
		String strLabel =
			String.format(
				"%s (%s %s m)",
				this.levelLocalText,
				this.getSign(),
				nf3.format( Math.abs( this.zLevel ) ) );
		return strLabel;
	}		

}
