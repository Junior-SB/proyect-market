package pe.edu.utp.marketapi.Domain.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import pe.edu.utp.marketapi.Domain.Rol.Rol;
import pe.edu.utp.marketapi.Infra.Security.DataLogin;
import pe.edu.utp.marketapi.Infra.Security.DataResponseLogin;
import pe.edu.utp.marketapi.Infra.Security.TokenService;

import javax.management.relation.Role;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    private DataRegisterUsuario dataRegisterUsuario;
    private DataUpdateUsuario dataUpdateUsuario;
    private DataLogin dataLogin;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Rol rol = Rol.builder().id(2L).build();
        dataRegisterUsuario = new DataRegisterUsuario("Juan", "Doe", "doe@gmail.com", "password",2L, "999999999","111111111", "TEST");
        dataUpdateUsuario = new DataUpdateUsuario(1L, "Juan", "alejandro", "doe@gmail.com",  "password", 2L, "111111111", "222222222", "TEST");
        dataLogin = new DataLogin("juan@example.com", "password");
        usuario = new Usuario(dataRegisterUsuario);
        usuario.setIdUsuario(1L);
    }

    @Test
    void save_Success() {
        when(usuarioRepository.existsByDniOrEmail(anyString(), anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        boolean result = usuarioService.save(dataRegisterUsuario);

        assertTrue(result);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void save_UserAlreadyExists() {
        when(usuarioRepository.existsByDniOrEmail(anyString(), anyString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.save(dataRegisterUsuario));
        assertEquals("Correo o Dni ya registrado", exception.getMessage());
    }

    @Test
    void update_Success() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

         DataResponseLogin result = usuarioService.update(dataUpdateUsuario);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void update_UserNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.update(dataUpdateUsuario));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void delete_Success() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean result = usuarioService.delete(1L);

        assertTrue(result);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void authenticate_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);
        when(tokenService.generateToken(any(Usuario.class))).thenReturn("token");
        when(usuarioRepository.findByEmail(dataLogin.email())).thenReturn(Optional.of(usuario));

        DataResponseLogin response = usuarioService.authenticate(dataLogin);

        assertNotNull(response);
        assertEquals("token", response.token());
    }

    @Test
    void findById_Success() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        DataListUsuario result = usuarioService.findById(1L);

        assertNotNull(result);
        assertEquals(usuario.getEmail(), result.email());
    }

    @Test
    void findById_UserNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.findById(1L));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void findByEmail_Success() {
        when(usuarioRepository.findByEmail("juan@example.com")).thenReturn(Optional.of(usuario));

        DataListUsuario result = usuarioService.findByEmail("juan@example.com");

        assertNotNull(result);
        assertEquals(usuario.getEmail(), result.email());
    }

    @Test
    void getAll_Success() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<DataListUsuario> result = usuarioService.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(usuario.getEmail(), result.get(0).email());
    }

    @Test
    void existById_UserExists() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        usuarioService.existById(1L);

        verify(usuarioRepository, times(1)).existsById(1L);
    }
}
