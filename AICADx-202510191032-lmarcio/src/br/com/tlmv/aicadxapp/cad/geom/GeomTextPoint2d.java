/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomTextPoint2d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/09/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

public class GeomTextPoint2d extends GeomPoint2d
{
//Private
	private String text;
	private double height;
	private double rotate;
	private int horizAlign;
	private int vertAlign;
	
//Public
	
	public GeomTextPoint2d(String text, GeomPoint2d ptIns, double height, double rotate, int horizAlign, int vertAlign) {
		super(ptIns);
		
		this.text = text;
		this.height = height;
		this.rotate = rotate;
		this.horizAlign = horizAlign;
		this.vertAlign = vertAlign;
	}
	
	public GeomTextPoint2d(String text, GeomPoint3d ptIns, double height, double rotate, int horizAlign, int vertAlign) {
		super(ptIns);
		
		this.text = text;
		this.height = height;
		this.rotate = rotate;
		this.horizAlign = horizAlign;
		this.vertAlign = vertAlign;
	}
	
	public GeomTextPoint2d(GeomTextPoint2d ptText) {
		super( new GeomPoint2d(ptText.getTagId(), ptText.getTagName(), ptText.getX(), ptText.getY()) );

		this.text = ptText.getText();
		this.height = ptText.getHeight();
		this.rotate = ptText.getRotate();
		this.horizAlign = ptText.getHorizAlign();
		this.vertAlign = ptText.getVertAlign();
	}
	
	public GeomTextPoint2d(GeomTextPoint3d ptText) {
		super( new GeomPoint2d(ptText.getTagId(), ptText.getTagName(), ptText.getX(), ptText.getY()) );

		this.text = ptText.getText();
		this.height = ptText.getHeight();
		this.rotate = ptText.getRotate();
		this.horizAlign = ptText.getHorizAlign();
		this.vertAlign = ptText.getVertAlign();
	}

	/* Methodes */
	
	public void init(String text, GeomPoint2d ptIns, double height, double rotate, int horizAlign, int vertAlign) {
		super.init(ptIns.getTagId(), ptIns.getTagName(), ptIns.getX(), ptIns.getY());
		
		this.text = text;
		this.height = height;
		this.rotate = rotate;
		this.horizAlign = horizAlign;
		this.vertAlign = vertAlign;
	}
	
	public void init(String text, GeomPoint3d ptIns, double height, double rotate, int horizAlign, int vertAlign) {
		super.init(ptIns.getTagId(), ptIns.getTagName(), ptIns.getX(), ptIns.getY());
		
		this.text = text;
		this.height = height;
		this.rotate = rotate;
		this.horizAlign = horizAlign;
		this.vertAlign = vertAlign;
	}
	
	/* Getters/Setters */
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getRotate() {
		return rotate;
	}

	public void setRotate(double rotate) {
		this.rotate = rotate;
	}

	public int getHorizAlign() {
		return horizAlign;
	}

	public int getVertAlign() {
		return vertAlign;
	}

	public void setHorizAlign(int horizAlign) {
		this.horizAlign = horizAlign;
	}

	public void setVertAlign(int vertAlign) {
		this.vertAlign = vertAlign;
	}
	
}
