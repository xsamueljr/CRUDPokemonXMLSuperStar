Crear un programa java que presente un menú con las siguientes opciones:

1. Listar Pokemon
2. Crear pokemon
3. Modificar pokemon
4. Eliminar Pokemon
5. Crear XML de un tipo.

- Para cada Pokemon se quiere almacenar un id que será único, un nombre y un tipo.
- Hay que garantizar la persistencia de la lista de Pokemon.
- Para crear un nuevo Pokemon se comprobará previamente los repetidos (mismo id), y si se consigue dar de alta un nuevo Pokemon se añadirá al final de un fichero llamado logPokemon.txt una línea con: El día 12/12/2000 se dió de alta a Pikachu
- Para eliminar un Pokemon se pedirá el id del pokemon a eliminar, se comprobará que existe y se visualizará, se pedirá confirmación para el borrado. Si se confirma el borrado e añadirá al final de un fichero llamado logPokemon.txt una línea con: El día 13/12/2000 se dió de baja a Chadmander
- Para la opción 5 se solicitará al usuario un tipo de Pokemon y se creará un XML con los Pokemon de ese tipo, siendo el id un atributo.