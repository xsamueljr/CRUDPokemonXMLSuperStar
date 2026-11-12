import data.models.Pokemon;
import data.persistence.PokemonStorage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PokemonStorage storage = PokemonStorage.load();

        int opcion;
        do {
            System.out.println("1. Listar Pokémon");
            System.out.println("2. Crear Pokémon");
            System.out.println("3. Modificar Pokémon");
            System.out.println("4. Eliminar Pokémon");
            System.out.println("5. Crear XML de un tipo");
            System.out.println("0. Salir");

            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    listar(storage.getPokemons());
                }
                case 2 -> {
                    crear(storage);
                }
                case 3 -> {
                    modificar(storage);
                }
                case 4 -> {
                    eliminar(storage);
                }
                case 5 -> {
                    exportarXML(storage);
                }
                case 0 -> {
                    System.out.println("Hasta lueguis");
                }
                default -> {
                    System.out.println("Opción no válida");
                }
            }
        } while (opcion != 0);
    }

    private static void exportarXML(PokemonStorage storage) {
        System.out.print("Escribe el tipo que quieres exportar");
        String tipo = scanner.nextLine();

        storage.filterByType(tipo).exportToXML();
        System.out.println("Exportado con éxito");
    }

    private static void eliminar(PokemonStorage storage) {
        System.out.print("Introduce ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Pokemon pokemon = storage.getById(id);
        if (pokemon == null) {
            System.out.println("No hay un Pokémon con ese ID");
            return;
        }

        System.out.println(pokemon);
        System.out.print("¿Seguro que quieres eliminarlo? (s/n): ");
        boolean confirmacion = scanner.nextLine().trim().toLowerCase().charAt(0) == 's';
        if (confirmacion) {
            storage.remove(pokemon);
            System.out.println("Borrado con éxito");
            log("se borró a " + pokemon.getNombre());
        } else {
            System.out.println("Cancelado");
        }
    }

    private static void modificar(PokemonStorage storage) {
        System.out.print("Introduce ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Pokemon pokemon = storage.getById(id);
        if (pokemon == null) {
            System.out.println("No hay un Pokémon con ese ID");
            return;
        }

        Pokemon nuevo = pedirDatosPokemon(id);
        storage.replace(pokemon, nuevo);
        log("se modificó a " + pokemon.getNombre());
    }

    private static void crear(PokemonStorage storage) {
        int id = pedirId(storage);
        Pokemon pokemon = pedirDatosPokemon(id);
        storage.add(pokemon);
        System.out.println("Añadido con éxito");
        log("se dio de alta a " + pokemon.getNombre());
    }

    private static Pokemon pedirDatosPokemon(int id) {
        System.out.print("Introduce nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Introduce tipo: ");
        String tipo = scanner.nextLine();
        return new Pokemon(id, nombre, tipo);
    }

    private static int pedirId(PokemonStorage storage) {
        while (true) {
            System.out.println("Introduce ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Pokemon existente = storage.getById(id);
            if (existente == null) return id;
            System.out.println("Ya hay un Pokémon con ese ID");
        }
    }

    private static void listar(List<Pokemon> pokemons) {
        if (pokemons.isEmpty()) {
            System.out.println("No hay ningún Pokémon");
        } else {
            for (Pokemon pokemon : pokemons) {
                System.out.println(pokemon);
            }
        }
    }

    /**
     * Escribe el mensaje en un archivo de log
     *
     * El mensaje empieza con "El día [diaActual]
     * @param message
     */
    private static void log(String message) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        String formatted = formatter.format(now);

        File logFile = new File("pokemons.log");
        try (
                FileWriter fw = new FileWriter(logFile, true);
                BufferedWriter bw = new BufferedWriter(fw)
                ) {
            bw.write("El día " + formatted + " " + message);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
