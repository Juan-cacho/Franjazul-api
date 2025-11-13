package com.franjazul.api.services;

import com.franjazul.api.model.Servicios;
import com.franjazul.api.model.TipoServicio;
import com.franjazul.api.model.Moleculas;
import com.franjazul.api.repository.ServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiciosService {

    @Autowired
    private ServiciosRepository serviciosRepository;

    @Autowired
    private TipoServicioService tipoServicioService;

    @Autowired
    private MoleculasService moleculasService;

    // Obtener un servicio por ID
    public Optional<Servicios> obtenerPorId(Integer id) {
        return serviciosRepository.findById(id);
    }

    // Obtener todos los servicios
    public List<Servicios> obtenerTodos() {
        return serviciosRepository.findAll();
    }

    // Crear un nuevo servicio
    public Servicios crear(Servicios servicio) {

        servicio.setIdServicio(null);
        // Validar que el nombre no exista
        Optional<Servicios> servicioExistente = serviciosRepository.findByNombreSer(servicio.getNombreSer());
        if (servicioExistente.isPresent()) {
            throw new RuntimeException("Ya existe un servicio con ese nombre");
        }

        // Validar que el tipo de servicio exista y esté asignado (obligatorio)
        if (servicio.getTipoServicio() == null || servicio.getTipoServicio().getNombreTps() == null) {
            throw new RuntimeException("El tipo de servicio es obligatorio");
        }

        Optional<TipoServicio> tipoServicioOpt = tipoServicioService.obtenerPorId(servicio.getTipoServicio().getNombreTps());
        if (!tipoServicioOpt.isPresent()) {
            throw new RuntimeException("El tipo de servicio con nombre " + servicio.getTipoServicio().getNombreTps() + " no existe");
        }

        servicio.setTipoServicio(tipoServicioOpt.get());

        // Validar que la molécula exista (si se especificó, es opcional)
        if (servicio.getMolecula() != null && servicio.getMolecula().getNombreMol() != null) {
            Optional<Moleculas> moleculaOpt = moleculasService.obtenerPorId(servicio.getMolecula().getNombreMol());
            if (!moleculaOpt.isPresent()) {
                throw new RuntimeException("La molécula con nombre " + servicio.getMolecula().getNombreMol() + " no existe");
            }
            servicio.setMolecula(moleculaOpt.get());
        } else {
            servicio.setMolecula(null);
        }

        return serviciosRepository.save(servicio);
    }

    // Actualizar un servicio existente
    public Servicios actualizar(Integer id, Servicios servicioActualizado) {
        Optional<Servicios> servicioOpt = serviciosRepository.findById(id);

        if (!servicioOpt.isPresent()) {
            throw new RuntimeException("Servicio no encontrado con ID: " + id);
        }

        Servicios servicioExistente = servicioOpt.get();

        // Actualiza solo si se envió
        if (servicioActualizado.getNombreSer() != null) {
            servicioExistente.setNombreSer(servicioActualizado.getNombreSer());
        }

        if (servicioActualizado.getDescripcionSer() != null) {
            servicioExistente.setDescripcionSer(servicioActualizado.getDescripcionSer());
        }

        // Validar tipo de servicio antes de actualizar
        if (servicioActualizado.getTipoServicio() != null && servicioActualizado.getTipoServicio().getNombreTps() != null) {
            Optional<TipoServicio> tipoServicioOpt = tipoServicioService.obtenerPorId(servicioActualizado.getTipoServicio().getNombreTps());
            if (!tipoServicioOpt.isPresent()) {
                throw new RuntimeException("El tipo de servicio con nombre " + servicioActualizado.getTipoServicio().getNombreTps() + " no existe");
            }
            servicioExistente.setTipoServicio(tipoServicioOpt.get());
        }

        // Validar molécula antes de actualizar (puede ser null)
        if (servicioActualizado.getMolecula() != null) {
            if (servicioActualizado.getMolecula().getNombreMol() != null) {
                Optional<Moleculas> moleculaOpt = moleculasService.obtenerPorId(servicioActualizado.getMolecula().getNombreMol());
                if (!moleculaOpt.isPresent()) {
                    throw new RuntimeException("La molécula con nombre " + servicioActualizado.getMolecula().getNombreMol() + " no existe");
                }
                servicioExistente.setMolecula(moleculaOpt.get());
            }
        }

        return serviciosRepository.save(servicioExistente);
    }

    // Borrar físicamente
    public boolean borrar(Integer id) {
        if (!serviciosRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado con ID: " + id);
        }

        serviciosRepository.deleteById(id);
        return true;
    }

    // Verificar si existe un servicio
    public boolean existe(Integer id) {
        return serviciosRepository.existsById(id);
    }

    // Obtener servicios por tipo
    public List<Servicios> obtenerPorTipo(String nombreTps) {
        Optional<TipoServicio> tipoServicioOpt = tipoServicioService.obtenerPorId(nombreTps);
        if (!tipoServicioOpt.isPresent()) {
            throw new RuntimeException("Tipo de servicio no encontrado con nombre: " + nombreTps);
        }
        return serviciosRepository.findByTipoServicio(tipoServicioOpt.get());
    }

    // Obtener servicios por molécula
    public List<Servicios> obtenerPorMolecula(String nombreMol) {
        Optional<Moleculas> moleculaOpt = moleculasService.obtenerPorId(nombreMol);
        if (!moleculaOpt.isPresent()) {
            throw new RuntimeException("Molécula no encontrada con nombre: " + nombreMol);
        }
        return serviciosRepository.findByMolecula(moleculaOpt.get());
    }
}
