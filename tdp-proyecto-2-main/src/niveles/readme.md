# Formato de niveles
##### Nombres de archivo.
Los niveles se leen a partir de archivos de texto plano. Las nombres son:  
- nivel1.txt
- nivel2.txt
- nivel3.txt
- nivel4.txt
- nivel5.txt  

Todos los archivos se ubican en la carpeta `/src/niveles`
<br></br>
##### Formato de archivo
El archivo de nivel debe constar exactamente de 3 líneas.  

###### Primera línea: Paredes.
Esta línea contiene las ubicaciones de las paredes.  
Estas están dadas por coordenadas de la forma `fila,columna`, y separadas por `;`.
Nótese que estas coordenadas no toman en cuenta las paredes externas, y empiezan desde el 0.
Es decir, que tanto la fila como la columna deben estar en el rango `[0,19]`.  

###### Segunda y tercera línea: Consumibles
Esta linea contiene información sobre los consumibles en el nivel. La primera línea refiere a los alimentos, mientras que la segunda refiere a los powerups.  
Estos deben tener el formato `tipo, fila, columna`. Al igual que en la fila anterior, las ternas se separan por `;`.  
Nótese que fila y columna tienen las mismas restricciones que en las paredes. El tipo debe estar ente `1 y 5` para alimentos, y entre `1 y 3` para powerups.   
Si no se desea incluir alimentos o powerups, puede introducirse simplemente un `;` en la línea correspondiente.  

###### Tabla de Alimentos
- 1 Frutilla
- 2 Banana
- 3 Palta
- 4 Pera
- 5 Cereza

###### Tabla de Powerups
- 1 Hamburgesa
- 2 Lata de Pintura
- 3 Cisne

###### Consideraciones importantes:
Para que un nivel sea válido, además de lo mencionado anteriormente, deben cumplirse las siguientes restricciones:
- No debe haber consumibles encima de paredes.
- No debe haber consumibles encima de consumibles.
- Debe haber al menos un consumible.
- No debe haber caminos sin salida. Se considera un camino sin salida a aquel pasillo que posee 1 solo bloque de ancho y termina en una pared, o un pasillo de 2 bloques de ancho que termina en paredes, con paredes internas de por medio.

###### Ejemplo de archivo:
El siguiente es un ejemplo de archivo válido:
>4,4;5,5  
1,2,2;1,3,5  
3,6,8  


