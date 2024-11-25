package pe.edu.utp.marketapi.Domain.MetodoPago;

public record DataListMetodoPago(
        Long idMetodoPago,
        String nombreMetodo
) {
    public DataListMetodoPago(MetodoPago metodoPago){
        this(
                metodoPago.getIdMetodoPago(),
                metodoPago.getNombreMetodo()
        );
    }
}
