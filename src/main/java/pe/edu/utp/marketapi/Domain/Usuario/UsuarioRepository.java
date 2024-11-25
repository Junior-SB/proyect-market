package pe.edu.utp.marketapi.Domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.email = :email")
    Optional<Usuario> findByEmailWithRol(@Param("email") String email);

    Optional<Usuario> findByEmail(String email);
    boolean existsByDniOrEmail(String dni,String email);
}
