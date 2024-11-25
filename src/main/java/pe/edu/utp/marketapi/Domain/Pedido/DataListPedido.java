package pe.edu.utp.marketapi.Domain.Pedido;

import pe.edu.utp.marketapi.Domain.Usuario.DataListUsuario;

import java.sql.Timestamp;

public record DataListPedido(
        Long idPedido,
        DataListUsuario usuario,
        Timestamp fechaPedido,
        double total
) {
    public DataListPedido(Pedido pedido){
        this(
                (long) pedido.getIdPedido(),
                new DataListUsuario(pedido.getUsuario()),
                pedido.getFechaPedido(),
                pedido.getTotal()
        );
    }
}
