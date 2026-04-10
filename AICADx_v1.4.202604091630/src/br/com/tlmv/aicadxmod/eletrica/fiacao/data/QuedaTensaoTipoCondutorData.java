/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * QuedaTensaoTipoCondutorData.java
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

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

public class QuedaTensaoTipoCondutorData 
{
//Private
	private Integer quedaTensaoTipoCondutorId;
	private String nome;
	private Double temperatura;
	private Double resistencia;
	private Double reatanciaIndutiva;
	private Double fatorPotenciaIndutivo;
	private Double fatorReativo;

//Public
	
	public QuedaTensaoTipoCondutorData(
		Integer quedaTensaoTipoCondutorId,
		String nome,
		Double temperatura,
		Double resistencia,
		Double reatanciaIndutiva,
		Double fatorPotenciaIndutivo,
		Double fatorReativo)
	{
		this.quedaTensaoTipoCondutorId = quedaTensaoTipoCondutorId;
		this.nome = nome;
		this.temperatura = temperatura;
		this.resistencia = resistencia;
		this.reatanciaIndutiva = reatanciaIndutiva;
		this.fatorPotenciaIndutivo = fatorPotenciaIndutivo;
		this.fatorReativo = fatorReativo;
	}

	/* Getters/Setters */
	
	public Integer getQuedaTensaoTipoCondutorId() {
		return quedaTensaoTipoCondutorId;
	}

	public void setQuedaTensaoTipoCondutorId(Integer quedaTensaoTipoCondutorId) {
		this.quedaTensaoTipoCondutorId = quedaTensaoTipoCondutorId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	public Double getResistencia() {
		return resistencia;
	}

	public void setResistencia(Double resistencia) {
		this.resistencia = resistencia;
	}

	public Double getReatanciaIndutiva() {
		return reatanciaIndutiva;
	}

	public void setReatanciaIndutiva(Double reatanciaIndutiva) {
		this.reatanciaIndutiva = reatanciaIndutiva;
	}

	public Double getFatorPotenciaIndutivo() {
		return fatorPotenciaIndutivo;
	}

	public void setFatorPotenciaIndutivo(Double fatorPotenciaIndutivo) {
		this.fatorPotenciaIndutivo = fatorPotenciaIndutivo;
	}

	public Double getFatorReativo() {
		return fatorReativo;
	}

	public void setFatorReativo(Double fatorReativo) {
		this.fatorReativo = fatorReativo;
	}	
	
}
