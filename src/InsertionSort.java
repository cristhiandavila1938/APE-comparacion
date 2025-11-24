public final class InsertionSort {
    private InsertionSort() {} // evitar instanciar la clase

    // Versión con opción de trazas
    public static void sort(int[] a, boolean trace) {
        int n = a.length;
        int contador = 0; // un contador siempre lo vamos incializar en 0 y lo ponemos para ver el numero de iteraciones que se hacen en caso de que el trace este en false

        for (int i = 1; i < n; i++) {
            contador++; // contar una iteración completa del algoritmo
            int actual = a[i];// elemento a insertar
            int j = i - 1;
            // Mover elementos mayores que key hacia la derecha
            while (j >= 0 && a[j] > actual) {
                a[j + 1] = a[j];
                j--;
            }

            // Insertar el elemento en su posición correcta
            a[j + 1] = actual;

            // Imprimir traza si está activada
            if (trace) {
                System.out.print("Iteracion " + i + ": ");
                printArray(a);
            }
        }
        System.out.println("numero de itaraciones: " + contador);
    }

    private static void printArray(int[] a) {
        System.out.print("[");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if (i < a.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    public static SortResult sortBenchmark(int[] a) {
        long comparisons = 0;
        long swaps = 0;
        long start = System.nanoTime();

        for (int i = 1; i < a.length; i++) {
            int actual = a[i];
            int j = i - 1;

            comparisons++;
            while (j >= 0 && a[j] > actual) {
                comparisons++;
                a[j + 1] = a[j];
                j--;
                swaps++;
            }

            a[j + 1] = actual;
        }

        long end = System.nanoTime();
        return new SortResult(comparisons, swaps, end - start);
    }

    public static SortResult sortBenchmarkStrings(String[] a) {
        long comparisons = 0;
        long swaps = 0;
        long start = System.nanoTime();

        for (int i = 1; i < a.length; i++) {
            String actual = a[i];
            int j = i - 1;

            comparisons++;
            while (j >= 0 && a[j].compareTo(actual) > 0) {
                comparisons++;
                a[j + 1] = a[j];
                j--;
                swaps++;
            }

            a[j + 1] = actual;
        }

        long end = System.nanoTime();
        return new SortResult(comparisons, swaps, end - start);
    }


}