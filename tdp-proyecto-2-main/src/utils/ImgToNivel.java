package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Lee una imágen pasada por parámetro y crea un archivo con formato de nivel, insertando paredes en
 * todos los puntos donde no hayan pixeles transparentes.
 * 
 * @param inputPath ruta de la imágen de entrada
 * @param outPath ruta del archivo de salida
 */

public class ImgToNivel {
	public static void convert(String inputPath, String outPath) {
		try {   		
			// leer la imágen
			BufferedImage bi = ImageIO.read(new File(inputPath));
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < bi.getWidth(); i++)
				for (int j = 0; j < bi.getHeight(); j++)
					if (bi.getRGB(j, i) != 0) sb.append(j + "," + i + ";");
			
			// escribir el archivo
			FileWriter writer = new FileWriter(outPath);
			writer.write(sb.toString() + "\n;\n;");
			writer.close();
		} catch (IOException e) {
			System.out.println("error! revise los parámetros");
		}
	}
	
	public static void main(String[] args) {
		if (args.length == 2) {
			convert(args[0], args[1]);
		} else {
			System.out.println("error! uso: java -jar ImgToNivel.jar <in-file> <out-file>");
		}
	}
}
