# CRUD Pokemon

Programa Java interactivo por consola que expone un CRUD de Pokemon, con persistencia en un archivo binario, y capacidad de exportar en XML los datos de los Pokemon de un tipo en particular

Enunciado original en [enunciado.md](./enunciado.md)

## Detalles técnicos

### División de responsabilidades
Una tiene el programa, otra la persistencia y otra la capacidad de exportar a XML. No digo que aplico 100% el principio de responsabilidad única porque no estoy seguro

### Patrón colección
En vez de ir moviendo una lista por el programa y desperdigando el código en el que se modifica o consulta, está todo en una clase, junto al código para leer y escribir del archivo binario

Asimismo, el exportar a XML lo hace otra clase

---

Hecho con ❤️ por [Samuel Jiménez Robles](https://github.com/xsamueljr)

<img src="https://no-ai-icon.com/wp-content/uploads/2023/01/no-ai-icon-08.svg" height="200px" alt="NO AI ICON" />