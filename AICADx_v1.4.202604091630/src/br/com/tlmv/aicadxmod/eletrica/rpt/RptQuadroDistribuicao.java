/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * RptQuadroDistribuicao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/10/2025
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

package br.com.tlmv.aicadxmod.eletrica.rpt;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxmod.eletrica.vo.DimensionamentoCircuito;
import br.com.tlmv.aicadxmod.eletrica.vo.DimensionamentoQuadro;

public class RptQuadroDistribuicao 
{

    public void exportaLevantamentoQuadro(String outputFile, String nomeQuadro, DimensionamentoQuadro dm)
    {
        NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);

        NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(3);

        NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);

        HSSFWorkbook wb;
        Sheet sh;

        CellStyle _doubleCenterCellStyle = null;
        CellStyle _doubleRightCellStyle = null;
        CellStyle _intCenterCellStyle = null;
        CellStyle _intRightCellStyle = null;

        File f_output = new File(outputFile);
        if ( f_output.exists() )
            f_output.delete();

        if( !f_output.exists() ) {
            wb = HSSFWorkbook.create(InternalWorkbook.createWorkbook());

            Font _f0 = wb.createFont();
            _f0.setFontHeightInPoints((short)14);
            _f0.setFontName("Arial");
            _f0.setBold(true);

            Font _f1 = wb.createFont();
            _f1.setFontHeightInPoints((short)10);
            _f1.setFontName("Arial");
            _f1.setBold(true);

            Font _f2 = wb.createFont();
            _f2.setFontHeightInPoints((short)10);
            _f2.setFontName("Arial");
            _f2.setBold(true);

            if (_doubleCenterCellStyle == null) {
                _doubleCenterCellStyle = wb.createCellStyle();
                _doubleCenterCellStyle.setDataFormat( wb.createDataFormat().getFormat("0.0") );
                _doubleCenterCellStyle.setAlignment( HorizontalAlignment.CENTER );
            }

            if (_doubleRightCellStyle == null) {
                _doubleRightCellStyle = wb.createCellStyle();
                _doubleRightCellStyle.setDataFormat( wb.createDataFormat().getFormat("0.0") );
                _doubleRightCellStyle.setAlignment( HorizontalAlignment.RIGHT );
            }

            if (_intCenterCellStyle == null) {
                _intCenterCellStyle = wb.createCellStyle();
                _intCenterCellStyle.setDataFormat( wb.createDataFormat().getFormat("0") );
                _intCenterCellStyle.setAlignment( HorizontalAlignment.CENTER );
            }

            if (_intRightCellStyle == null) {
                _intRightCellStyle = wb.createCellStyle();
                _intRightCellStyle.setDataFormat( wb.createDataFormat().getFormat("0") );
                _intRightCellStyle.setAlignment( HorizontalAlignment.RIGHT );
            }

            sh = wb.createSheet(nomeQuadro);

            int n = 1;

            Row r = sh.createRow(n++);
            Cell c = r.createCell(1);
            c.setCellValue("Nome do Quadro:");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.RIGHT );
            c = r.createCell(2);
            c.setCellValue(nomeQuadro);
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f2);
            c.getCellStyle().setAlignment( HorizontalAlignment.RIGHT );

            r = sh.createRow(n++);
            c = r.createCell(1);
            c.setCellValue("Potencia Total (VA):");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.RIGHT );
            c = r.createCell(2);
            c.setCellValue(dm.getPotenciaQuadro());
            c.setCellStyle( _doubleRightCellStyle );
            c.getCellStyle().setFont(_f2);

            r = sh.createRow(n++);
            c = r.createCell(1);
            c.setCellValue("Condutor Fase (mm):");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.RIGHT );
            c = r.createCell(2);
            c.setCellValue(dm.getBitolaAlimentadorQuadro());
            c.setCellStyle(_doubleRightCellStyle);
            c.getCellStyle().setFont(_f2);

            r = sh.createRow(n++);
            c = r.createCell(1);
            c.setCellValue("Condutor de Protecao (mm):");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.RIGHT );
            c = r.createCell(2);
            c.setCellValue(dm.getBitolaProtecaoQuadro());
            c.setCellStyle(_doubleRightCellStyle);
            c.getCellStyle().setFont(_f2);

            r = sh.createRow(n++);
            c = r.createCell(1);
            c.setCellValue("Disjuntor (A):");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.RIGHT );
            c = r.createCell(2);
            c.setCellValue(dm.getDisjuntorProtecaoQuadro());
            c.setCellStyle(_intRightCellStyle);
            c.getCellStyle().setFont(_f2);

            ArrayList<DimensionamentoCircuito> lsdmc = dm.getLsDimensionamentoCircuito();
            n = 7;

            r = sh.createRow(n++);
            c = r.createCell(1);
            c.setCellValue("Circuito");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.CENTER );
            c = r.createCell(2);
            c.setCellValue("Tensao (V)");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.CENTER );
            c = r.createCell(3);
            c.setCellValue("Potencia (VA)");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.CENTER );
            c = r.createCell(4);
            c.setCellValue("Condutor Fase (mm)");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.CENTER );
            c = r.createCell(5);
            c.setCellValue("Disjuntor (A)");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.CENTER );
            c = r.createCell(6);
            c.setCellValue("Fase");
            c.setCellStyle( wb.createCellStyle() );
            c.getCellStyle().setFont(_f1);
            c.getCellStyle().setAlignment( HorizontalAlignment.CENTER );

            for(DimensionamentoCircuito dmc : lsdmc)
            {
                r = sh.createRow(n++);
                c = r.createCell(1);
                c.setCellValue(dmc.getCircuito());
                c.setCellStyle( _intCenterCellStyle );
                c.getCellStyle().setFont(_f2);
                c = r.createCell(2);
                c.setCellValue(dmc.getTensao());
                c.setCellStyle( _doubleCenterCellStyle );
                c.getCellStyle().setFont(_f2);
                c = r.createCell(3);
                c.setCellValue(dmc.getPotencia());
                c.setCellStyle( _doubleRightCellStyle );
                c.getCellStyle().setFont(_f2);
                c = r.createCell(4);
                c.setCellValue(dmc.getBitolaCondutor());
                c.setCellStyle( _doubleRightCellStyle );
                c.getCellStyle().setFont(_f2);
                c = r.createCell(5);
                c.setCellValue(dmc.getDisjuntorProtecao());
                c.setCellStyle( _intRightCellStyle );
                c.getCellStyle().setFont(_f2);
                c = r.createCell(6);
                c.setCellValue(dmc.getFase());
                c.setCellStyle( wb.createCellStyle() );
                c.getCellStyle().setFont(_f2);
                c.getCellStyle().setAlignment( HorizontalAlignment.CENTER );
            }

            sh.setColumnWidth(1, 5000);
            sh.setColumnWidth(2, 5000);
            sh.setColumnWidth(3, 5000);
            sh.setColumnWidth(4, 5000);
            sh.setColumnWidth(5, 5000);
            sh.setColumnWidth(6, 5000);

            FileOutputStream fs = null;
            try {
                fs = new FileOutputStream(outputFile);
                wb.write(fs);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
            	try {
            		if(fs != null) fs.close();
            	} catch(Exception e1) { }
            }
        }
    }
}
