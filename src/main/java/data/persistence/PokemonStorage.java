package data.persistence;

import data.models.Pokemon;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokemonStorage {
    private static final File FILE = new File("pokemons.dat");

    private final List<Pokemon> pokemons;

    public PokemonStorage(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public PokemonStorage() {
        this(new ArrayList<>());
    }

    public static PokemonStorage load() {
        if (!FILE.exists()) return new PokemonStorage();

        try (
                FileInputStream fis = new FileInputStream(FILE);
                ObjectInputStream ois = new ObjectInputStream(fis)
                ) {
            List<Pokemon> pokemons = new ArrayList<>();
            while (fis.available() > 0) {
                pokemons.add((Pokemon) ois.readObject());
            }
            return new PokemonStorage(pokemons);
        } catch (FileNotFoundException e) {
            // Should not happen due to the previous check but whatever
            return new PokemonStorage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e + " (did you change the pokemon class? if so, delete .dat)");
        }
    }

    private void save() {
        try (
                FileOutputStream fos = new FileOutputStream(FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
                ) {
            for (Pokemon pokemon : pokemons) {
                oos.writeObject(pokemon);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pokemon> getPokemons() {
        return List.copyOf(pokemons);
    }

    public void add(Pokemon pokemon) {
        pokemons.add(pokemon);
        save();
    }

    public void remove(Pokemon pokemon) {
        pokemons.remove(pokemon);
        save();
    }

    public Pokemon getById(int id) {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getId() == id) return pokemon;
        }
        return null;
    }
}
