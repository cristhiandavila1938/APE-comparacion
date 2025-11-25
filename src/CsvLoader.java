import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CsvLoader {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static Object loadDataset(String filename) throws Exception {

        switch (filename) {
            case "citas_100_ordenadas.csv":
                return loadCitasOrdenadas("data/" + filename);
            case "citas_100_casi_ordenadas.csv":
                return loadCitas("data/" + filename);
            case "pacientes_500.csv":
                return loadPacientes("data/" + filename);
            case "inventario_500_inverso.csv":
                return loadInventario("data/" + filename);

            default:
                throw new IllegalArgumentException(
                        "Dataset no manejado por esta parte del proyecto: " + filename);
        }
    }
    // 1. citas_100_ordenadas
    private static String[] loadCitasOrdenadas(String path) throws Exception {
        List<String> lista = new ArrayList<>();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

        String line = br.readLine(); // header

        while ((line = br.readLine()) != null) {
            lista.add(line);
        }

        br.close();
        return lista.toArray(new String[0]);
    }




    // 2.citas_100_casi_ordenadas.csv
    //    -> convertir fechaHora a número (minutos)
    private static int[] loadCitas(String path) throws Exception {

        List<Integer> lista = new ArrayList<>();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

        String line = br.readLine(); // header

        while ((line = br.readLine()) != null) {
            String[] p = line.split(";");
            String fechaStr = p[2];

            // convertir fecha a un número comparable
            LocalDateTime dt = LocalDateTime.parse(fechaStr, FMT);
            int value = (int) (dt.toLocalDate().toEpochDay() * 1440 +
                    dt.getHour() * 60 +
                    dt.getMinute());
            lista.add(value);
        }
        br.close();
        return lista.stream().mapToInt(i -> i).toArray();
    }

    // 3.pacientes_500.csv
    //    -> devolver solo los apellidos en un String[]
    private static String[] loadPacientes(String path) throws Exception {

        List<String> lista = new ArrayList<>();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

        String line = br.readLine(); // header

        while ((line = br.readLine()) != null) {
            String[] p = line.split(";");
            String apellido = p[1];
            lista.add(apellido);
        }
        br.close();
        return lista.toArray(new String[0]);
    }

    // 4. inventario_500_inverso.csv
    private static int[] loadInventario(String path) throws Exception {

        List<Integer> lista = new ArrayList<>();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

        String line = br.readLine(); // header

        while ((line = br.readLine()) != null) {
            String[] p = line.split(";");

            // columna 2 = stock
            int stock = Integer.parseInt(p[2].trim());

            lista.add(stock);
        }
        br.close();

        return lista.stream().mapToInt(i -> i).toArray();
    }


}
