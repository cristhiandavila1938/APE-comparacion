public final class BubbleSort {

    private BubbleSort() {} // evitar instanciar la clas

    public static void sort(int[] a, boolean trace) {

        int n = a.length;
        int contador = 0; //Contador para saber cuantas iteraciones se han realizado
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) { // este primer for recorre todo el arreglo
            boolean huboSwap = false; // es para que en caso de que el arreglo ya este ordenado simplemente haya como un corte y se detenga antes
            contador++; // contar una iteración completa del algoritmo
            for (int j = 0; j < n - i - 1; j++) { // este otro tambien lo recorre pero hasta el anterior al ultimo elemento del arreglo
                if (a[j] > a[j + 1]) { // en este apartado se realiza la comparacion de que si el elemento actual es mayor que el siguiente, estan en un orden incorrecto por lo que se debe swapear para acercarlos en orden ascendente
                    int temporal = a[j]; //
                    a[j] = a[j + 1]; // se mueve el elemento menor hacia adelante osea la posicion j
                    a[j + 1] = temporal; // este coloca el elemento mayor en la posicion j + 1
                    swaps++;
                    huboSwap = true; // este para marcar que en la pasada hubo algun intercambio haciendo uso de la actovacion de la optimizacion de corte temprano
                }
            }

            // mostrar traza por iteración
            if (trace) {
                System.out.print("Iteracion " + (i + 1) + ": ");
                printArray(a);
            }

            // optimización: si no hubo swaps, el arreglo ya está ordenado
            if (!huboSwap) break;
        }
        //imprime numero de swaps que se realizo
        System.out.println("Numero de swaps: " + swaps);

        // imprimir contador SOLO al final
        System.out.println("Contador de iteraciones: " + contador);
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

        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            boolean huboSwap = false;

            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;

                    swaps++;
                    huboSwap = true;
                }
            }

            if (!huboSwap) break;
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
            boolean huboSwap = false;

            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (a[j].compareTo(a[j + 1]) > 0) {

                    String temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;

                    swaps++;
                    huboSwap = true;
                }
            }

            if (!huboSwap) break;  // optimización
        }

        long end = System.nanoTime();
        return new SortResult(comparisons, swaps, end - start);
    }
}