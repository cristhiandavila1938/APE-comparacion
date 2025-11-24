public final class SelectionSort {
    private SelectionSort() {} //Constructor privado para evita instanciar la clase

    //Version con opcion de trazas
    public static void sort(int[] a, boolean trace) {
        int n = a.length;
        int contador = 0;
        int swaps = 0;

        // i representa la posición donde irá el siguiente elemento más pequeño
        for (int i = 0; i < n -1; i++){
            contador++; //se implementa un contador para llevar la cuenta de iteracioones

            int min = i; //i va tener el elemento minimo

            //Encuentra el indice del elemento minimo en el subarreglo
            for (int j = i + 1; j < n; j++){
                if (a[j] < a[min]){
                    min = j;
                }
            }

            //Intercambia solamente si es que encuentra un elemento minimo distinto
            if (min != i){
                int temp = a[i];
                a[i] = a[min];
                a[min] = temp;
                swaps++;
            }

            //Mostrar trazas si está activado
            if (trace) {
                System.out.print("Iteracion " + (i + 1) + ": ");
                printArray(a);
            }
        }
        //Imprime el numero de swaps
        System.out.println("Numero de swaps: " + swaps);
        //Imprime o muestra el numero de iteraciones con el contador
        System.out.println("Numero de iteraciones: " + contador);
    }

    //Imprime el arreglo
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

        int n = a.length;

        for (int i = 0; i < n - 1; i++) {
            int min = i;

            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (a[j] < a[min]) {
                    min = j;
                }
            }

            if (min != i) {
                int temp = a[i];
                a[i] = a[min];
                a[min] = temp;
                swaps++;
            }
        }

        long end = System.nanoTime();
        return new SortResult(comparisons, swaps, end - start);
    }

    public static SortResult sortBenchmarkStrings(String[] a) {
        long comparisons = 0;
        long swaps = 0;
        long start = System.nanoTime();

        int n = a.length;

        for (int i = 0; i < n - 1; i++) {
            int min = i;

            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (a[j].compareTo(a[min]) < 0) {
                    min = j;
                }
            }

            if (min != i) {
                String temp = a[i];
                a[i] = a[min];
                a[min] = temp;
                swaps++;
            }
        }

        long end = System.nanoTime();
        return new SortResult(comparisons, swaps, end - start);
    }
}
