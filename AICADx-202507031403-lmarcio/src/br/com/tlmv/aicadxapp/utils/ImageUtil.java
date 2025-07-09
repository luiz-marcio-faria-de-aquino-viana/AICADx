/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ImageUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageUtil 
{
//Public
	
	public static Image readImageFromFile(String fileName)
		throws Exception		
	{
		BufferedImage image = null;
		File imageFile = new File(fileName);
        image = ImageIO.read(imageFile);
		return image;
	}
	
	public static void saveImageToFile(String fileName, Image image)
		throws Exception		
	{
		BufferedImage bImage = (BufferedImage)image;
		
		File imageFile = new File(fileName);
        ImageIO.write(bImage, "jpg", imageFile);
	}

}
