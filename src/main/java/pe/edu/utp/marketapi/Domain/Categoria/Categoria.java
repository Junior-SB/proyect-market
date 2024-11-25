package pe.edu.utp.marketapi.Domain.Categoria;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nombre_categoria")
    private String nombreCategoria;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    public Categoria(DataRegisterCategoria data){
        this.nombreCategoria = data.nombre();
        this.descripcion = data.descripcion();
    }

    public void updateCategoria(DataUpdateCategoria data){
        if (data.nombre() != null) this.nombreCategoria = data.nombre();
        if (data.descripcion() != null) this.descripcion = data.descripcion();
    }
}
