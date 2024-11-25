package pe.edu.utp.marketapi.Domain.Rol;

public record DataListRol(
        Long id,
        String nombreRol
) {
    public DataListRol(Rol rol){
        this(
                rol.getId(),
                rol.getNombreRol()
        );
    }
}
