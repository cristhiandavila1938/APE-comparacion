import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BenchMark {
    public static void main(String[] args) throws Exception {
        // Archivo csv de salida
        PrintWriter out = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("data/resultados_benchmark.csv"),
                        StandardCharsets.UTF_8
                )
        );

        // Encabezado de csv
        out.println("dataset;algoritmo;n;comparisons;swaps;mediana_tiempo(ns)");

        String[] datasets = {
                "citas_100_casi_ordenadas.csv",
                "pacientes_500.csv"
        };

        System.out.println("\n--------RESULTADOS DEL BENCHMARK---------");

        for (String dataset : datasets) {
            System.out.println("\nDataset: " + dataset);
            System.out.println(
                    String.format("%-15s %-12s %-12s %-12s %-20s",
                            "Algoritmo", "N", "Comparaciones", "Swaps", "Tiempo(ns)")
            );
            System.out.println("-----------------------------------------------------------------------");

            Object loaded = CsvLoader.loadDataset(dataset);

            for (String alg : new String[]{"insertion", "selection", "bubble"}) {

                // almacenar métricas
                List<Long> tiempos = new ArrayList<>();
                List<Long> comps = new ArrayList<>();
                List<Long> swaps = new ArrayList<>();

                for (int i = 1; i <= 10; i++) {

                    SortResult result;

                    if (loaded instanceof int[]) {
                        // citas → int[]
                        int[] base = (int[]) loaded;
                        int[] copia = Arrays.copyOf(base, base.length);
                        result = ejecutarInt(alg, copia);
                    } else {
                        // pacientes → String[]
                        String[] base = (String[]) loaded;
                        String[] copia = Arrays.copyOf(base, base.length);
                        result = ejecutarString(alg, copia);
                    }

                    tiempos.add(result.timeNs);
                    comps.add(result.comparisons);
                    swaps.add(result.swaps);
                }

                // Descartar las primeras tres
                List<Long> tiemposValidos = tiempos.subList(3, 10);
                List<Long> compsValidos = comps.subList(3, 10);
                List<Long> swapsValidos = swaps.subList(3, 10);

                // mediana
                long medianaTiempo = mediana(tiemposValidos);
                long medianaComp = mediana(compsValidos);
                long medianaSwaps = mediana(swapsValidos);

                int n = (loaded instanceof int[]) ?
                        ((int[]) loaded).length :
                        ((String[]) loaded).length;

                System.out.println(String.format(
                        "%-15s %-12d %-12d %-12d %-20d",
                        formatearAlgoritmo(alg),
                        n,
                        medianaComp,
                        medianaSwaps,
                        medianaTiempo
                ));
                // Guardar en archivo CSV
                out.println(
                        dataset + ";" +
                                alg + ";" +
                                n + ";" +
                                medianaComp + ";" +
                                medianaSwaps + ";" +
                                medianaTiempo
                );
            }
        }
        System.out.println("\n");
        out.close();
        System.out.println("Resultados guardados en: data/resultados_benchmark.csv");
        System.out.println("Benchmark finalizado.");

    }

    private static String formatearAlgoritmo(String s) {
        switch(s) {
            case "insertion": return "InsertionSort";
            case "selection": return "SelectionSort";
            case "bubble":    return "BubbleSort";
        }
        return s;
    }

    // --------- Ejecutar algoritmo para int[] ----------
    private static SortResult ejecutarInt(String alg, int[] arr) {
        switch (alg) {
            case "insertion": return InsertionSort.sortBenchmark(arr);
            case "selection": return SelectionSort.sortBenchmark(arr);
            case "bubble":    return BubbleSort.sortBenchmark(arr);
        }
        throw new IllegalArgumentException("Algoritmo desconocido: " + alg);
    }

    // --------- Ejecutar algoritmo para String[] ----------
    private static SortResult ejecutarString(String alg, String[] arr) {
        switch (alg) {
            case "insertion": return InsertionSort.sortBenchmarkStrings(arr);
            case "selection": return SelectionSort.sortBenchmarkStrings(arr);
            case "bubble":    return BubbleSort.sortBenchmarkStrings(arr);
        }
        throw new IllegalArgumentException("Algoritmo desconocido: " + alg);
    }

    // --------- Calcular mediana ----------
    private static long mediana(List<Long> valores) {
        long[] arr = valores.stream().mapToLong(v -> v).toArray();
        Arrays.sort(arr);
        return arr[arr.length / 2];
    }
}
