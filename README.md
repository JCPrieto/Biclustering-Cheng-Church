# Biclustering Cheng & Church (GUI)

Aplicacion de escritorio en Java (Swing) para ejecutar el algoritmo de Cheng & Church sobre datasets biologicos en
formato ARFF y visualizar resultados.

## Caracteristicas

- Interfaz grafica con tres pestañas: Beginning, Biclustering y Results.
- Configuracion de parametros del algoritmo (MSR threshold, numero de biclusters, alfa, rango de aleatorios y valor de
  missing).
- Carga de datasets ARFF usando Weka.
- Generacion de resultados en `Executions/` y visualizacion de graficas desde archivos `.dat`.

## Requisitos

- Java 21
- Maven 3.x

Dependencias principales (se descargan con Maven):

- Weka 3.8.6
- JFreeChart 1.5.4

## Compilar

```bash
mvn package
```

El jar se genera en `target/`.

## Ejecutar

No hay entrada de consola. Ejecuta desde tu IDE la clase principal:

```
bicl_CC.Bicluster
```

## Uso rapido

1. En la pestaña **Biclustering**, ajusta los parametros y pulsa **Accept current configuration**.
2. Pulsa **Load Dataset** y selecciona un `.arff`.
3. Pulsa **Run** para ejecutar el algoritmo.
4. Los resultados se guardan en `Executions/<dataset_fecha-hora>/`.
5. En **Results**, selecciona un `.dat` y pulsa **Show Graph** para ver la grafica.

## Notas sobre salidas

- Se crea una carpeta por ejecucion con un resumen `.txt` y archivos `.dat` para cada bicluster.
- El sistema genera tambien scripts `.txt` para graficas PostScript.

## Estructura del proyecto

- `src/main/java/bicl_CC/`: codigo Java de la GUI y la logica del algoritmo.
- `Executions/`: carpeta donde se guardan resultados.
- `pom.xml`: configuracion Maven y dependencias.

## Licencia

No especificada.
