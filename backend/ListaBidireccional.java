/*
 LEON GAMEZ FERNANDO
 CAMACHO GUERRA JACOBO
 GONZALEZ ALVARADO ALEXA MICHELLE
*/

public class ListaBidireccional<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int tamaño;

    public ListaBidireccional() {
        this.primero = null;
        this.ultimo = null;
        this.tamaño = 0;
    }

    public boolean estaVacia() { return primero == null; }
    public int getTamaño() { return tamaño; }

    // --- INSERCIONES ---
    public void insertarInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            primero = ultimo = nuevo;
        } else {
            nuevo.setSiguiente(primero);
            primero.setAnterior(nuevo);
            primero = nuevo;
        }
        tamaño++;
    }

    public void insertarFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            primero = ultimo = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            ultimo = nuevo;
        }
        tamaño++;
    }

    public void insertarPosicion(T dato, int posicion) {
        if (posicion < 0 || posicion > tamaño) {
            throw new IndexOutOfBoundsException("Posición fuera de rango.");
        }
        if (posicion == 0) insertarInicio(dato);
        else if (posicion == tamaño) insertarFinal(dato);
        else {
            Nodo<T> actual = primero;
            for (int i = 0; i < posicion; i++) actual = actual.getSiguiente();
            
            Nodo<T> nuevo = new Nodo<>(dato);
            Nodo<T> anterior = actual.getAnterior();
            
            anterior.setSiguiente(nuevo);
            nuevo.setAnterior(anterior);
            nuevo.setSiguiente(actual);
            actual.setAnterior(nuevo);
            tamaño++;
        }
    }

    // --- REMOCIONES ---
    public T removerInicio() {
        if (estaVacia()) throw new IllegalStateException("La lista está vacía.");
        T datoRemovido = primero.getDato();
        if (primero == ultimo) {
            primero = ultimo = null;
        } else {
            primero = primero.getSiguiente();
            primero.setAnterior(null);
        }
        tamaño--;
        return datoRemovido;
    }

    public T removerFinal() {
        if (estaVacia()) throw new IllegalStateException("La lista está vacía.");
        T datoRemovido = ultimo.getDato();
        if (primero == ultimo) {
            primero = ultimo = null;
        } else {
            ultimo = ultimo.getAnterior();
            ultimo.setSiguiente(null);
        }
        tamaño--;
        return datoRemovido;
    }

    public T removerPosicion(int posicion) {
        if (estaVacia()) throw new IllegalStateException("La lista está vacía.");
        if (posicion < 0 || posicion >= tamaño) throw new IndexOutOfBoundsException("Posición fuera de rango.");
        
        if (posicion == 0) return removerInicio();
        if (posicion == tamaño - 1) return removerFinal();

        Nodo<T> actual = primero;
        for (int i = 0; i < posicion; i++) actual = actual.getSiguiente();
        
        Nodo<T> anterior = actual.getAnterior();
        Nodo<T> siguiente = actual.getSiguiente();
        
        anterior.setSiguiente(siguiente);
        siguiente.setAnterior(anterior);
        tamaño--;
        return actual.getDato();
    }

    // --- MUESTRAS PUNTUALES ---
    public T mostrarInicio() {
        if (estaVacia()) throw new IllegalStateException("La lista está vacía.");
        return primero.getDato();
    }

    public T mostrarFinal() {
        if (estaVacia()) throw new IllegalStateException("La lista está vacía.");
        return ultimo.getDato();
    }

    public T mostrarPosicion(int posicion) {
        if (estaVacia()) throw new IllegalStateException("La lista está vacía.");
        if (posicion < 0 || posicion >= tamaño) throw new IndexOutOfBoundsException("Posición fuera de rango.");
        
        Nodo<T> actual;
        if (posicion <= tamaño / 2) {
            actual = primero;
            for (int i = 0; i < posicion; i++) actual = actual.getSiguiente();
        } else {
            actual = ultimo;
            for (int i = tamaño - 1; i > posicion; i--) actual = actual.getAnterior();
        }
        return actual.getDato();
    }

    // --- BÚSQUEDAS ---
    // Busca por un atributo clave (en este caso, el título de la canción)
    public String buscarPorValor(String valorBuscado) {
        if (estaVacia()) throw new IllegalStateException("La lista está vacía.");
        Nodo<T> actual = primero;
        int pos = 0;
        while (actual != null) {
            // Se asume que T es Cancion para este ejemplo específico de búsqueda por valor
            if (actual.getDato() instanceof Cancion c && c.titulo().equalsIgnoreCase(valorBuscado)) {
                return "Encontrado en posición " + pos + ": " + c.toString();
            }
            actual = actual.getSiguiente();
            pos++;
        }
        return "No se encontró el valor '" + valorBuscado + "' en la lista.";
    }

    public T buscarPorPosicion(int posicion) {
        return mostrarPosicion(posicion); // La lógica es idéntica a mostrar por posición
    }

    // --- RECORRIDOS Y AUXILIARES ---
    public String recorrerAdelante() {
        if (estaVacia()) return "Lista vacía.";
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = primero;
        while (actual != null) {
            sb.append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
        }
        return sb.toString();
    }

    public String recorrerAtras() {
        if (estaVacia()) return "Lista vacía.";
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = ultimo;
        while (actual != null) {
            sb.append(actual.getDato().toString()).append("\n");
            actual = actual.getAnterior();
        }
        return sb.toString();
    }

    public void vaciar() {
        primero = null;
        ultimo = null;
        tamaño = 0;
    }
}