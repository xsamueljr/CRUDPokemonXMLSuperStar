import data.models.Pokemon;
import data.persistence.PokemonStorage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

                }
                case 4 -> {

                }
                case 5 -> {

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

    private static void crear(PokemonStorage storage) {
        int id = pedirId(storage);
    }

    private static int pedirId(PokemonStorage storage) {
        while (true) {
            System.out.println("Introduce ID: ");
            int id = scanner.nextInt();
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

    private static void log(String message) {
        File logFile = new File("pokemons.log");
        try (
                FileWriter fw = new FileWriter(logFile);
                BufferedWriter bw = new BufferedWriter(fw)
                ) {
            bw.write(message);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
