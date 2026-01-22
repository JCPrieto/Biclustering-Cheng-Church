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

## Paquetes de distribucion

El proyecto usa `jpackage` via Maven para crear un paquete instalable o una app portable.

### App image (portable)

```bash
mvn package
```

Salida en `target/dist/`:

- `target/dist/BiclusteringCC/` (app image)
- Ejecutable: `target/dist/BiclusteringCC/bin/BiclusteringCC`

### Instalador Linux (.deb)

```bash
mvn package -Djpackage.type=DEB -Plinux-installer
```

Salida en `target/dist/` con un archivo `.deb`.

### Instaladores Windows y macOS

Los tipos soportados dependen del SO donde ejecutes el build:

Windows:

```bash
mvn package -Djpackage.type=MSI
```

o

```bash
mvn package -Djpackage.type=EXE
```

macOS:

```bash
mvn package -Djpackage.type=DMG
```

o

```bash
mvn package -Djpackage.type=PKG
```

Salida en `target/dist/` con el instalador correspondiente.

Notas de distribucion:

- macOS: para evitar advertencias de seguridad, los paquetes deben estar firmados y notarizados (Apple Developer ID).
- Windows: SmartScreen puede advertir si el instalador no esta firmado; se recomienda firmar con un certificado de
  codigo.

Ejemplos (ajusta nombres y rutas):

macOS (firmar y notarizar):

```bash
codesign --deep --force --options runtime --sign "Developer ID Application: YOUR_NAME (TEAM_ID)" target/dist/BiclusteringCC.app
xcrun notarytool submit target/dist/BiclusteringCC.dmg --apple-id "APPLE_ID" --team-id "TEAM_ID" --password "APP_SPECIFIC_PASSWORD" --wait
xcrun stapler staple target/dist/BiclusteringCC.dmg
```

Windows (firmar):

```bash
signtool sign /fd SHA256 /a /tr http://timestamp.digicert.com /td SHA256 target\\dist\\BiclusteringCC.msi
```

## Instalacion

### Linux (.deb)

```bash
sudo dpkg -i target/dist/*.deb
```

Si hay dependencias pendientes:

```bash
sudo apt-get -f install
```

## Ejecutar (app instalada)

- Desde el menu de aplicaciones: **BiclusteringCC**.
- Desde terminal (si se creo el lanzador): `BiclusteringCC`.

En Windows, el acceso es desde el menu Inicio. En macOS, desde Applications.

## Ejecutar (app image)

```bash
target/dist/BiclusteringCC/bin/BiclusteringCC
```

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

Este software se distribuye bajo licencia GNU GPL v3. Ver `LICENSE`.
