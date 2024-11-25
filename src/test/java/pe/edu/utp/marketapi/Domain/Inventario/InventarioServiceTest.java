package pe.edu.utp.marketapi.Domain.Inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.utp.marketapi.Domain.Producto.ProductoService;
import pe.edu.utp.marketapi.Domain.Proveedor.ProveedorService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class InventarioServiceTest {

    @InjectMocks
    private InventarioService inventarioService;

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoService productoService;

    @Mock
    private ProveedorService proveedorService;

    private DataRegisterInventario dataRegisterInventario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dataRegisterInventario = new DataRegisterInventario(1L, 1L, 100, 50.0); // Ajusta los valores según tu constructor
    }

    @Test
    public void testSaveInventario_Success() {
        when(productoService.existById(1L)).thenReturn(true);
        when(proveedorService.existById(1L)).thenReturn(true);

        when(inventarioRepository.save(any(Inventario.class))).thenReturn(new Inventario());

        boolean result = inventarioService.save(dataRegisterInventario);

        assertTrue(result);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    public void testSaveInventario_ProductNotFound() {
        when(productoService.existById(1L)).thenThrow(new RuntimeException("Producto no encontrado"));

        assertThrows(RuntimeException.class, () -> inventarioService.save(dataRegisterInventario));
    }

    @Test
    public void testUpdateInventario_Success() {
        DataUpdateInventario dataUpdate = new DataUpdateInventario(1L, 1L, 1L, 100, 50.0);
        Inventario existingInventario = new Inventario();
        existingInventario.setIdInventario(1L);

        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(existingInventario));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(existingInventario);

        boolean result = inventarioService.update(dataUpdate);

        assertTrue(result);
        verify(inventarioRepository, times(1)).save(existingInventario);
    }

    @Test
    public void testUpdateInventario_NotFound() {
        DataUpdateInventario dataUpdate = new DataUpdateInventario(2L, 1L, 1L, 100, 50.0);

        when(inventarioRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> inventarioService.update(dataUpdate));
    }
}
