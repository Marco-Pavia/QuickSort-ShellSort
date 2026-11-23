# Ordenamiento Interno en Java: QuickSort y ShellSort

Implementación en Java de dos algoritmos de ordenamiento interno: **QuickSort** (divide y vencerás, con partición de Lomuto) y **ShellSort** (inserción con saltos decrecientes). El método `QuickSort.ordenar` ordena un arreglo de enteros particionándolo alrededor de un pivote de forma recursiva; el método `ShellSort.ordenar` lo ordena comparando y desplazando elementos separados por un `intervalo` que se reduce a la mitad en cada pasada, hasta llegar a 1. Ambos programas se prueban con el mismo arreglo para poder comparar sus resultados.

**Autor:** Marco Antonio Pavia Flores.
**Materia:** AED-1026 Estructura de Datos — Unidad 5: Métodos de Ordenamiento
**Docente:** Víctor Hugo Vásquez Herrera
**Institución:** Instituto Tecnológico Superior de Xalapa

## Lenguaje de programación

**Java** (JDK 8 o superior; se recomienda JDK 17 o superior). No requiere librerías externas: solo clases nativas del lenguaje.

## Estructura del proyecto

```text
QuickSort-ShellSort/
├── src/
│   ├── QuickSort.java
│   ├── MainQuick.java
│   ├── ShellSort.java
│   └── MainShell.java                                 
└── README.md
```

| Archivo | Descripción |
|---|---|
| `QuickSort.java` | Contiene el método recursivo `ordenar`, el método privado `particionar` (esquema de Lomuto) y el método auxiliar `imprimirArreglo`. |
| `MainQuick.java` | Programa de prueba: ordena el arreglo `{6, 1, 5, 8, 3, 2, 7, 4}` con QuickSort e imprime el resultado. |
| `ShellSort.java` | Contiene el método `ordenar` con el ciclo `while` de desplazamiento completado y el método auxiliar `imprimirArreglo`. |
| `MainShell.java` | Programa de prueba: ordena el mismo arreglo con ShellSort e imprime el resultado. |

Todas las clases pertenecen al paquete `Metodos_de_ordenamiento`, por lo que la carpeta del paquete debe conservar ese nombre y se ejecutan con el nombre completo (por ejemplo, `Metodos_de_ordenamiento.MainQuick`). La carpeta `out/` y las carpetas del IDE (`.idea/`) son locales y no es necesario subirlas al repositorio.

## Fundamento teórico

### QuickSort

Elige un **pivote** (en esta implementación, el último elemento del subarreglo), reorganiza los elementos para que los menores o iguales queden a su izquierda y los mayores a su derecha, y repite el proceso de forma recursiva en cada lado.

| Paso | Acción |
|---|---|
| 1 | **Particionar:** `particionar` coloca el pivote en su posición definitiva y devuelve su índice. |
| 2 | **Sublista izquierda:** `ordenar(arr, bajo, pivote - 1)`. |
| 3 | **Sublista derecha:** `ordenar(arr, pivote + 1, alto)`. |

La condición `bajo < alto` es el caso base de la recursión.

### ShellSort

Es una optimización del ordenamiento por inserción: en lugar de mover cada elemento una posición a la vez, compara elementos separados por un `intervalo`. Empieza en `n / 2` y se divide entre 2 en cada vuelta hasta llegar a 0. La última pasada (`intervalo = 1`) es una inserción normal sobre un arreglo ya casi ordenado.

### Comparativa

| Criterio | QuickSort | ShellSort |
|---|---|---|
| Complejidad: mejor caso | O(N log N) | O(N log N) |
| Complejidad: caso promedio | O(N log N) | Depende de los intervalos (con `n/2` ≈ O(N^1.5)) |
| Complejidad: peor caso | O(N²) (pivote siempre el menor o el mayor) | O(N²) con la secuencia de Shell |
| Memoria adicional | O(log N) de pila (O(N) en el peor caso) | O(1) |
| Estabilidad | No es estable | No es estable |
| Enfoque | Recursivo | Iterativo |

### Código completado en el TODO de ShellSort

El código base de `ShellSort` traía el ciclo `while` vacío, con un comentario `TODO`. Se completó así:

```java
// TODO: completar la condición y el desplazamiento
while (j >= intervalo && arr[j - intervalo] > temp) {
    arr[j] = arr[j - intervalo];   // Desplaza el elemento mayor 'intervalo' posiciones
    j -= intervalo;                // Retrocede un salto para seguir comparando
}
```

### Conclusión de QuickSort

El código base de `QuickSort` se verificó y concluyó con sus tres pasos: particionamiento, llamada recursiva a la izquierda del pivote y llamada recursiva a la derecha.

```java
public static void ordenar(int[] arr, int bajo, int alto) {
    if (bajo < alto) {
        int pivote = particionar(arr, bajo, alto);
        ordenar(arr, bajo, pivote - 1);
        ordenar(arr, pivote + 1, alto);
    }
}
```

### Detalles importantes de la lógica

1. **Orden de la condición en ShellSort:** `j >= intervalo` debe evaluarse **antes** que `arr[j - intervalo] > temp`. Así, el operador `&&` evita acceder a un índice negativo. Si se invierte el orden, el programa lanza `ArrayIndexOutOfBoundsException`.
2. **Retroceso por saltos:** dentro del ciclo se hace `j -= intervalo` (no `j--`), de lo contrario se comparan elementos que no pertenecen a la misma sublista de saltos y el arreglo no queda ordenado.
3. **Inserción final:** al terminar el `while`, la línea `arr[j] = temp;` coloca el valor guardado en el hueco correcto.
4. **Rango en QuickSort:** la primera llamada debe recibir `bajo = 0` y `alto = arr.length - 1`; usar `arr.length` como `alto` provoca una excepción por salirse del arreglo.

## Consideraciones sobre los datos de entrada

- Ambos métodos ordenan arreglos de tipo `int[]` de forma **ascendente** y modifican el arreglo original (ordenamiento *in-place*).
- Un arreglo vacío o de un solo elemento no genera error: queda igual.
- Los valores repetidos se admiten; sin embargo, ninguno de los dos algoritmos es estable, por lo que el orden relativo de elementos iguales no está garantizado.
- Para cambiar los datos de prueba, edita el arreglo declarado en `MainQuick.java` o `MainShell.java` y vuelve a compilar.
- `QuickSort` es recursivo: con arreglos muy grandes y ya ordenados, el peor caso O(N²) puede agotar la pila (`StackOverflowError`).

## Datos de prueba

| Prueba | Arreglo de entrada | Método |
|---|---|---|
| 1 (práctica) | `{6, 1, 5, 8, 3, 2, 7, 4}` | QuickSort |
| 2 (práctica) | `{6, 1, 5, 8, 3, 2, 7, 4}` | ShellSort |

## Cómo clonar, compilar y ejecutar

### Desde la terminal

1. Clona el repositorio y entra a la carpeta:
   ```bash
   git clone https://github.com/Marco-Pavia/QuickSort-ShellSort.git
   cd QuickSort-ShellSort
   ```
2. Compila desde la carpeta raíz (la que contiene `QuickSort-ShellSort/`):
   ```bash
   javac -d out Metodos_de_ordenamiento/*.java
   ```
3. Ejecuta QuickSort:
   ```bash
   java -cp out Metodos_de_ordenamiento.MainQuick
   ```
4. Ejecuta ShellSort:
   ```bash
   java -cp out Metodos_de_ordenamiento.MainShell
   ```

### Desde IntelliJ IDEA

1. Abre la carpeta del proyecto con *File → Open*.
2. Confirma que la carpeta raíz esté marcada como *Sources Root* (clic derecho → *Mark Directory as → Sources Root*) y que el SDK esté configurado en *File → Project Structure → Project SDK*.
3. Abre `MainQuick.java` o `MainShell.java` y presiona el botón verde ▶ junto al método `main`.
4. La salida aparece en la ventana *Run* de la parte inferior.

## Ejemplo de ejecución

**Prueba 1** (`java -cp out Metodos_de_ordenamiento.MainQuick`):

```text
Arreglo original:
[6, 1, 5, 8, 3, 2, 7, 4]
Arreglo ordenado con Quicksort:
[1, 2, 3, 4, 5, 6, 7, 8]
```

**Prueba 2** (`java -cp out Metodos_de_ordenamiento.MainShell`):

```text
Arreglo original:
[6, 1, 5, 8, 3, 2, 7, 4]
Arreglo ordenado con Shellsort:
[1, 2, 3, 4, 5, 6, 7, 8]
```

### Traza de QuickSort (pivote = último elemento)

| Paso | Pivote | Posición final | Estado del arreglo |
|---|---|---|---|
| 1 | 4 | 3 | `[1, 3, 2, 4, 6, 5, 7, 8]` |
| 2 | 2 | 1 | `[1, 2, 3, 4, 6, 5, 7, 8]` |
| 3 | 8 | 7 | `[1, 2, 3, 4, 6, 5, 7, 8]` |
| 4 | 7 | 6 | `[1, 2, 3, 4, 6, 5, 7, 8]` |
| 5 | 5 | 4 | `[1, 2, 3, 4, 5, 6, 7, 8]` |

### Traza de ShellSort (n = 8)

| Pasada | Intervalo | Estado del arreglo |
|---|---|---|
| Inicial | — | `[6, 1, 5, 8, 3, 2, 7, 4]` |
| 1 | 4 | `[3, 1, 5, 4, 6, 2, 7, 8]` |
| 2 | 2 | `[3, 1, 5, 2, 6, 4, 7, 8]` |
| 3 | 1 | `[1, 2, 3, 4, 5, 6, 7, 8]` |

**Si se ejecuta fuera de la carpeta raíz** (sin el paquete en el classpath):

```text
Error: Could not find or load main class Metodos_de_ordenamiento.MainQuick
```

El texto exacto de este mensaje depende de la versión de Java.

## Resultados esperados

La columna «Sin verificar `j >= intervalo`» muestra qué ocurre en ShellSort si se omite la primera parte de la condición del `while`; se incluye para mostrar el efecto de ese detalle.

| Prueba | Resultado esperado | Código final | Sin verificar `j >= intervalo` |
|---|---|---|---|
| 1 (QuickSort) | `1 2 3 4 5 6 7 8` | `1 2 3 4 5 6 7 8` | No aplica |
| 2 (ShellSort) | `1 2 3 4 5 6 7 8` | `1 2 3 4 5 6 7 8` | `ArrayIndexOutOfBoundsException` |
