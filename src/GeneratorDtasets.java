import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Arrays;

public class GeneratorDtasets {

    static Random rnd = new Random(42);
    static DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static void main(String[] args) throws Exception {
        generarCitas100Ordenadas();
        generarCitasCasiOrdenadas();
        generarPacientes500();
        generarInventario500Inverso();
        System.out.println("Archivos generados correctamente:");
        System.out.println(" - citas_100_ordenadas");
        System.out.println(" - citas_100_casi_ordenadas.csv");
        System.out.println(" - pacientes_500.csv");
        System.out.println(" - inventario_500_inverso.csv");
    }

    private static String generarFechaCitaConHorario(Random rnd) {
        // Día entre 1 y 31
        int dia = 1 + rnd.nextInt(31);
        // Hora entre 08 y 18
        int hora = 8 + rnd.nextInt(11);
        // Minuto entre 0 y 59
        int minuto = rnd.nextInt(60);
        return String.format("2025-03-%02dT%02d:%02d", dia, hora, minuto);
    }

    // 1. Generar citas_100_ordenadas
    private static void generarCitas100Ordenadas() throws Exception {

        String[] apellidos = {
                "Guerrero","Naranjo","Cedeño","Ramírez","Vera","Mora","Sánchez","Bravo","Cruz",
                "Mendoza","Suárez","Muñoz","Jiménez","Vega","Reyes","Paredes","Castillo","Alvarez",
                "Flores","Ortega","Rivas","Rojas","Cardenas","Carrillo","Andrade","Paz","Romero",
                "Figueroa","Torres","Silva"
        };

        Random rnd = new Random(42);

        // Rango de fechas
        LocalDateTime inicio = LocalDateTime.of(2025,3,1,8,0);

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("data/citas_100_ordenadas.csv"),
                        StandardCharsets.UTF_8))) {

            pw.println("id;apellido;fechaHora");

            LocalDateTime actual = inicio;

            for (int i = 1; i <= 100; i++) {

                String id = String.format("CITA-%03d", i);
                String apellido = apellidos[rnd.nextInt(apellidos.length)];

                // Sumamos entre 1 y 30 minutos para mantener orden
                int minutos = 1 + rnd.nextInt(30);
                actual = actual.plusMinutes(minutos);

                pw.printf("%s;%s;%s%n",
                        id,
                        apellido,
                        actual.format(FMT)
                );
            }
        }
    }



    // 2. Generar citas_100_casi_ordenadas.csv
    private static void generarCitasCasiOrdenadas() throws Exception {

        String[] apellidos = {
                "Guerrero","Naranjo","Cedeño","Ramírez","Vera","Mora","Sánchez","Bravo","Cruz",
                "Mendoza","Suárez","Muñoz","Jiménez","Vega","Reyes","Paredes","Castillo","Álvarez",
                "Flores","Ortega","Rivas","Rojas","Cárdenas","Carrillo","Andrade","Paz","Romero",
                "Figueroa","Torres","Silva"
        };

        LocalDateTime inicio = LocalDateTime.of(2025,3,1,8,0);
        LocalDateTime fin    = LocalDateTime.of(2025,3,31,18,0);

        long minutosTot = Duration.between(inicio, fin).toMinutes();

        // Generar citas base (100)
        List<String[]> citas = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            citas.add(new String[]{
                    String.format("CITA-%03d", i),
                    apellidos[rnd.nextInt(apellidos.length)],
                    generarFechaCitaConHorario(rnd)
            });

        }

        // Ordenar por fechaHora asc
        citas.sort(Comparator.comparing(c -> c[2]));

        // Hacer exactamente 5 swaps
        rnd = new Random(42);
        HashSet<String> usados = new HashSet<>();

        for (int s = 0; s < 5; s++) {
            while (true) {
                int a = rnd.nextInt(100);
                int b = rnd.nextInt(100);
                if (a != b) {
                    String p1 = a + "-" + b;
                    String p2 = b + "-" + a;
                    if (!usados.contains(p1) && !usados.contains(p2)) {
                        usados.add(p1);
                        Collections.swap(citas, a, b);
                        break;
                    }
                }
            }
        }

        // Escribir archivo
        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("data/citas_100_casi_ordenadas.csv"),
                        StandardCharsets.UTF_8))) {

            pw.println("id;apellido;fechaHora");
            for (String[] c : citas) {
                pw.println(String.join(";", c));
            }
        }
    }

    // 3.Generar pacientes_500.csv
    private static void generarPacientes500() throws Exception {

        Random rnd = new Random(42);

        List<String> apes = Arrays.asList(
                "Guerrero","Naranjo","Cedeno","Ramirez","Vera","Mora","Sanchez","Bravo","Cruz",
                "Mendoza","Suarez","Munoz","Jimenez","Vega","Reyes","Paredes","Castillo","Alvarez",
                "Flores","Ortega","Rivas","Rojas","Carrillo","Andrade","Paz","Romero",
                "Figueroa","Torres","Silva","Santos","Navarrete","Guevara","Ponce",
                "Vargas","Cordova","Valencia","Aguilar","Rosales","Lozano","Calle","Villalba",
                "Cando","Soria","Salazar","Herrera","Pazmino","Benavides","Espinosa","Galvez", "Corozo"
        );
        System.out.println("Total apellidos = " + apes.size());

        List<String> g1 = apes.subList(0, 15);   // 60%
        List<String> g2 = apes.subList(15, 30);  // 30%
        List<String> g3 = apes.subList(30, 50);  // 10%

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("data/pacientes_500.csv"),
                        StandardCharsets.UTF_8))) {

            pw.println("id;apellido;prioridad");

            for (int i = 1; i <= 500; i++) {
                double r = rnd.nextDouble();
                String apellido;

                if (r < 0.6) apellido = g1.get(rnd.nextInt(g1.size()));
                else if (r < 0.9) apellido = g2.get(rnd.nextInt(g2.size()));
                else apellido = g3.get(rnd.nextInt(g3.size()));

                int prioridad = rnd.nextInt(3) + 1;

                pw.printf("PAC-%04d;%s;%d%n", i, apellido, prioridad);
            }
        }
    }

    // 4. Generar inventario_500_inverso.csv
    private static void generarInventario500Inverso() throws Exception {

        String[] insumos = {
                "Guante Nitrilo Talla M",
                "Alcohol 70% 1L",
                "Gasas 10x10",
                "Venda Elástica 5cm",
                "Mascarilla KN95",
                "Jeringa 5ml",
                "Desinfectante 500ml",
                "Algodón 200g",
                "Suero Fisiológico 1L",
                "Termómetro Digital",
                "Guantes Latex Talla L",
                "Tijeras Médicas",
                "Cinta Microporo",
                "Bata Desechable",
                "Apositos Estériles",
                "Tubo de Ensayo",
                "Toallas Antisépticas",
                "Cubre Zapatos",
                "Gel Antibacterial 250ml",
                "Esparadrapo 5m"
        };

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream("data/inventario_500_inverso.csv"),
                        StandardCharsets.UTF_8))) {

            pw.println("id;insumo;stock");

            for (int i = 1; i <= 500; i++) {
                String id = String.format("ITEM-%04d", i);
                String insumo = insumos[rnd.nextInt(insumos.length)];

                int stock = 501 - i;  // 500, 499, 498, ..., 1

                pw.println(id + ";" + insumo + ";" + stock);
            }
        }
    }

}