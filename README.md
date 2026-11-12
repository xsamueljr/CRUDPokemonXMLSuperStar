# CRUD Pokémon

Programa Java interactivo por consola que expone un CRUD de Pokemon, con persistencia en un archivo binario, y capacidad de exportar en XML los datos de los Pokemon de un tipo en particular

Enunciado original en [enunciado.md](./enunciado.md)

## Detalles técnicos

Para exportar a XML se usa el parser DOM

### División de responsabilidades
Una clase tiene el programa y otra la persistencia y el exportar a XML. Supongo que no cuenta como una responsabilidad por clase pero mejor que todo en una

### Patrón colección
En vez de ir moviendo una lista por el programa y desperdigando el código en el que se modifica o consulta, está todo en una clase, junto al código para leer y escribir del archivo binario

Esto asegura que cualquier operación que modifique la lista en memoria también hará el cambio en el archivo binario

---

Hecho con ❤️ por [Samuel Jiménez Robles](https://github.com/xsamueljr)

<img src="https://no-ai-icon.com/wp-content/uploads/2023/01/no-ai-icon-08.svg" height="200px" alt="NO AI ICON" />