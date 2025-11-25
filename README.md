# Práctica de Ordenación Básica en Java haciendo comparaciones: Burbuja, Selección e Inserción

## Cómo descargar el repositorio
Link del informe de la practica:
https://drive.google.com/file/d/14PBLb8aFSyb8vvbm6dASnb3l2Pizg6cQ/view?usp=sharing

## Datos de Identificación
**Estudiantes:** Bryan Gustavo Troya Velez, Cristhian Alexander Dávila Sari  
**Asignatura:** Estructura de Datos  
**Ciclo:** 3 A  
**Unidad:** 2  
**Docente:** Andrés Roberto Navas Castellanos  
**Fecha:** Jueves 20 y Viernes 21 de noviembre  
**Horario:** 07h30 – 10h30 / 07h30 – 09h30  
**Lugar:** Aula  

## Objetivo
Ejecutar y analizar comparativamente los algoritmos de Burbuja, Selección e Inserción sobre casos de prueba, para determinar cuándo conviene cada uno según tamaño, grado de orden y duplicados.

## Materiales y Herramientas
- Guía de pruebas con datasets  
- JDK OpenJDK 25  
- IntelliJ IDEA Community  
- Git/GitHub  
- EVA/Moodle para entrega  
- Herramientas de documentación (Markdown, Google Docs/LibreOffice/Word)  

## Procedimiento
1. **Instrumentación:**  
   - Contadores de `comparisons` y `swaps`  
   - Medición de tiempo con `System.nanoTime()`  
   - Ejecutar 10 repeticiones y tomar la mediana de las 7 últimas  
   - Cargar CSV fuera de la medición  

2. **Casos de prueba:**  
   - Clave de orden: `fechaHora`, `apellido`, `stock`  
   - Convertir a array de la clave  
   - Ejecutar Insertion, Selection y Bubble (con corte temprano)  
   - Registrar n, comparaciones, swaps y mediana de tiempo  

3. **Análisis:**  
   - Tablas comparativas y gráficos (tiempo vs n)  
   - Matriz de recomendación según tipo de dataset y tamaño  

## Resultados
- Inserción es más rápida en datasets casi ordenados y mantiene estabilidad en duplicados.  
- Selección realiza comparaciones constantes ~ n(n−1)/2 sin importar el orden inicial.  
- Burbuja mejora con corte temprano en datasets casi ordenados, pero penaliza en inverso.  

## Cómo descargar el repositorio

Para clonar este repositorio en tu máquina local, sigue estos pasos:

1. Abre la terminal o Git Bash.  
2. Ejecuta el comando:

```bash
git clone https://github.com/cristhiandavila1938/APE-comparacion.git



 

 


