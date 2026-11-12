package data.persistence;

import data.models.Pokemon;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void replace(Pokemon old, Pokemon new_) {
        pokemons.set(pokemons.indexOf(old), new_);
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

    public PokemonStorage filterByType(String type) {
        if (type == null || type.isBlank()) return this;

        String normalizedType = type.trim().toLowerCase();
        return new PokemonStorage(
                pokemons.stream()
                        .filter(pokemon -> normalizedType.equals(pokemon.getTipo().trim().toLowerCase()))
                        .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public void exportToXML() {
        File file = new File("pokemons.xml");

        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document document = builder.newDocument();

        Element root = document.createElement("pokemons");

        for (Pokemon pokemon : pokemons) {
            Element pokemonEl = document.createElement("pokemon");
            pokemonEl.setAttribute("id", String.valueOf(pokemon.getId()));
            Element nombreEl = document.createElement("nombre");
            nombreEl.setTextContent(pokemon.getNombre());
            pokemonEl.appendChild(nombreEl);
            Element tipoEl = document.createElement("tipo");
            tipoEl.setTextContent(pokemon.getTipo());
            pokemonEl.appendChild(tipoEl);
            root.appendChild(pokemonEl);
        }

        document.appendChild(root);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(document), new StreamResult(file));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
