package com.portfoliojosele.alquileres_tfm;

import com.portfoliojosele.alquileres_tfm.models.dao.InquilinoRepository;
import com.portfoliojosele.alquileres_tfm.models.services.InquilinoServicesImpl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import com.portfoliojosele.alquileres_tfm.models.entity.Inquilino;

/**
 * Pruebas unitarias para la lógica de negocio de Inquilinos.
 * Usamos MockitoExtension para no levantar todo el contexto de Spring
 */
@ExtendWith (MockitoExtension.class)
class InquilinoServiceImplTest {

    // Simulo la base de datos para que esto vuele sin tocar MySQL
    @Mock
    private InquilinoRepository inquilinoRepository;

    // Meto el repo falso dentro de la implementación real del servicio
    @InjectMocks
    private InquilinoServicesImpl inquilinoService;

    // Metodo para prueba unitaria de buscar inquilino por id
    @Test
    void buscarInquilinoPorId_DeberiaDevolverInquilino_CuandoExiste() {
        // Me armo un inquilino falso para la prueba
        Long idPrueba = 1L;
        Inquilino inquilinoFalso = new Inquilino();
        inquilinoFalso.setId(idPrueba);
        inquilinoFalso.setNombre("Juan");
        inquilinoFalso.setApellidos("Pérez");

        // Le digo al repo falso lo que tiene que devolver cuando le pregunten por este ID
        when(inquilinoRepository.findById(idPrueba)).thenReturn(Optional.of(inquilinoFalso));

        // Llamo al método de verdad de mi servicio
        Inquilino resultado = inquilinoService.findOne(idPrueba);

        // Compruebo que no sea nulo y que el nombre sea el que toque
        assertNotNull(resultado, "El inquilino no debería ser nulo");
        assertEquals("Juan", resultado.getNombre(), "El nombre debería coincidir");
        
        // Me aseguro de que el servicio tiró del repositorio exactamente una vez
        verify(inquilinoRepository, times(1)).findById(idPrueba);
    }

    // Metodo para prueba unitaria de buscar inquilino cuando no existe
    @Test
    void buscarInquilinoPorId_DeberiaDevolverNulo_CuandoNoExiste() {
        // Pruebo a buscar un ID que sé que no existe en el sistema
        Long idInexistente = 99L;

        // Simulo que el repositorio devuelve un Optional vacío (no hay nada)
        when(inquilinoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Llamo al servicio
        Inquilino resultado = inquilinoService.findOne(idInexistente);

        // Compruebo que me devuelve null limpiamente y no peta
        assertNull(resultado, "El resultado debería ser nulo si el inquilino no existe");
        
        // Verifico que intentó buscarlo una vez
        verify(inquilinoRepository, times(1)).findById(idInexistente);
    }

    // Metodo para prueba unitaria de guardar inquilino
    @Test
    void guardarInquilino_DeberiaLlamarAlRepositorio() {
        // Me creo un inquilino nuevo para probar el guardado
        Inquilino nuevoInquilino = new Inquilino();
        nuevoInquilino.setNombre("Ana");
        nuevoInquilino.setApellidos("García");
        nuevoInquilino.setEmail("ana@correo.com");

        // Ejecuto el método de guardar del servicio
        inquilinoService.save(nuevoInquilino);

        // Como es void, compruebo que le pasó el objeto al repo para guardarlo una vez
        verify(inquilinoRepository, times(1)).save(nuevoInquilino);
    }

    // Metodo para prueba unitaria de lista completa inquilino
    @Test
    void listarInquilinos_DeberiaDevolverListaCompleta() {
        // Me creo dos inquilinos de mentira para la prueba
        Inquilino inquilino1 = new Inquilino();
        inquilino1.setId(1L);
        inquilino1.setNombre("Ana");

        Inquilino inquilino2 = new Inquilino();
        inquilino2.setId(2L);
        inquilino2.setNombre("Carlos");

        List<Inquilino> listaFalsa = List.of(inquilino1, inquilino2);

        // Le digo al repositorio falso que devuelva esta lista cuando la pidan
        when(inquilinoRepository.findAll()).thenReturn(listaFalsa);

        // Llamo al servicio real
        List<Inquilino> resultado = inquilinoService.findAll();

        // Compruebo que no sea nula, que tenga 2 elementos y que el primero sea Ana
        assertNotNull(resultado, "La lista no debería ser nula");
        assertEquals(2, resultado.size(), "Tendría que haber 2 inquilinos");
        assertEquals("Ana", resultado.get(0).getNombre());

        // Me aseguro de que el repositorio se llamó una vez
        verify(inquilinoRepository, times(1)).findAll();
    }

    // Metodo para prueba unitaria de eliminar inquilino
    @Test
    void eliminarInquilino_DeberiaLlamarAlRepositorio() {
        Long idPrueba = 1L;

        // Como delete es void y no devuelve nada, solo ejecutamos
        inquilinoService.delete(idPrueba);

        // Y aquí comprobamos que el servicio le pasó la orden de borrado al repositorio
        verify(inquilinoRepository, times(1)).deleteById(idPrueba);
    }

}


