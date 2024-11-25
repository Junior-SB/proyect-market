package pe.edu.utp.marketapi.Domain.Proveedor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProveedorServiceTest {

    @InjectMocks
    private ProveedorService proveedorService;

    @Mock
    private ProveedorRepository proveedorRepository;

    private DataRegisterProveedor dataRegisterProveedor;
    private DataUpdateProveedor dataUpdateProveedor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dataRegisterProveedor = new DataRegisterProveedor("Proveedor Test", "123456789", "Direccion Test", "123456789", "3333333");
        dataUpdateProveedor = new DataUpdateProveedor(1L, "Proveedor Actualizado", "987654321", "Direccion Actualizada", "987654321", "4444444");
    }

    @Test
    void save_Success() {
        when(proveedorRepository.existsByNombreProveedorOrRuc(anyString(), anyString())).thenReturn(false);
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(new Proveedor(dataRegisterProveedor));

        boolean result = proveedorService.save(dataRegisterProveedor);

        assertTrue(result);
        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    void save_ProveedorAlreadyExists() {
        when(proveedorRepository.existsByNombreProveedorOrRuc(anyString(), anyString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> proveedorService.save(dataRegisterProveedor));
        assertEquals("Proveedor ya existe", exception.getMessage());
    }

    @Test
    void update_Success() {
        Proveedor proveedor = new Proveedor(dataRegisterProveedor);
        proveedor.setIdProveedor(dataUpdateProveedor.id());

        when(proveedorRepository.findById(dataUpdateProveedor.id())).thenReturn(Optional.of(proveedor));
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);

        boolean result = proveedorService.update(dataUpdateProveedor);

        assertTrue(result);
        verify(proveedorRepository, times(1)).save(proveedor);
    }

    @Test
    void update_ProveedorNotFound() {
        when(proveedorRepository.findById(dataUpdateProveedor.id())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> proveedorService.update(dataUpdateProveedor));
        assertEquals("Proveedor no encontrado", exception.getMessage());
    }

    @Test
    void delete_Success() {
        when(proveedorRepository.existsById(1L)).thenReturn(true);

        boolean result = proveedorService.delete(1L);

        assertTrue(result);
        verify(proveedorRepository, times(1)).deleteById(1L);
    }

    @Test
    void findById_Success() {
        Proveedor proveedor = new Proveedor(dataRegisterProveedor);
        proveedor.setIdProveedor(1L);

        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));

        DataListProveedor result = proveedorService.findById(1L);

        assertNotNull(result);
        assertEquals(proveedor.getNombreProveedor(), result.nombreProveedor());
    }

    @Test
    void findById_ProveedorNotFound() {
        when(proveedorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> proveedorService.findById(1L));
        assertEquals("Proveedor no encontrado", exception.getMessage());
    }

    @Test
    void existById() {
        when(proveedorRepository.existsById(1L)).thenReturn(true);

        boolean result = proveedorService.existById(1L);

        assertTrue(result);
        verify(proveedorRepository, times(1)).existsById(1L);
    }
}
