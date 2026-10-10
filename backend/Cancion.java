/*
 LEON GAMEZ FERNANDO
 CAMACHO GUERRA JACOBO
 GONZALEZ ALVARADO ALEXA MICHELLE
*/

public record Cancion(int id, String titulo, String artista, int duracionSegundos) {
    
    // Constructor compacto para validar datos al momento de la instanciación
    public Cancion {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor a 0.");
        if (titulo == null || titulo.trim().isEmpty()) throw new IllegalArgumentException("El título no puede estar vacío.");
        if (artista == null || artista.trim().isEmpty()) throw new IllegalArgumentException("El artista no puede estar vacío.");
        if (duracionSegundos <= 0) throw new IllegalArgumentException("La duración debe ser mayor a 0.");
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (%d:%02d)", 
                id, titulo, artista, duracionSegundos / 60, duracionSegundos % 60);
    }
}