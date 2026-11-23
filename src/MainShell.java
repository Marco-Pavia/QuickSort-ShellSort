
public class MainShell {
    public static void main(String[] args) {
        // Mismo arreglo de ejemplo que se uso en QuickSort para poder comparar
        int[] arreglo = {6, 1, 5, 8, 3, 2, 7, 4};

        System.out.println("Arreglo original:");
        ShellSort.imprimirArreglo(arreglo);

        // Llamamos al metodo de ordenamiento Shellsort
        ShellSort.ordenar(arreglo);

        System.out.println("Arreglo ordenado con Shellsort:");
        ShellSort.imprimirArreglo(arreglo);
    }
}
