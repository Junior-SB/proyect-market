package pe.edu.utp.marketapi.Domain.Categoria;

public record DataListCategoria(
        Long idCategoria,
        String nombre,
        String descripcion
) {
    public DataListCategoria(Categoria categoria){
        this(
            categoria.getIdCategoria(),
            categoria.getNombreCategoria(),
            categoria.getDescripcion()
        );
    }
}
