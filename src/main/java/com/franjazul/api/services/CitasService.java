package com.franjazul.api.services;

import com.franjazul.api.model.Citas;
import com.franjazul.api.model.Usuarios;
import com.franjazul.api.model.EstadoCita;
import com.franjazul.api.model.FranjasHorarias;
import com.franjazul.api.model.Lugares;
import com.franjazul.api.repository.CitasRepository;
import com.franjazul.api.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitasService {

    @Autowired
    private CitasRepository citasRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private EstadoCitaService estadoCitaService;

    @Autowired
    private FranjasHorariasService franjasHorariasService;

    @Autowired
    private LugaresService lugaresService;

    // Obtener una cita por ID
    public Optional<Citas> obtenerPorId(Integer id) {
        return citasRepository.findById(id);
    }

    // Obtener todas las citas
    public List<Citas> obtenerTodos() {
        return citasRepository.findAll();
    }

    // Crear una nueva cita
    public Citas crear(Citas cita) {

        cita.setIdCita(null);
        // Validar que el ID no exista
        if (citasRepository.existsById(cita.getIdCita())) {
            throw new RuntimeException("Ya existe una cita con el ID: " + cita.getIdCita());
        }

        // Validar que el usuario técnico exista (obligatorio)
        if (cita.getUsuarioTecnico() == null || cita.getUsuarioTecnico().getIdUsuario() == null) {
            throw new RuntimeException("El usuario técnico es obligatorio");
        }

        Optional<Usuarios> usuarioTecnicoOpt = usuariosRepository.findById(cita.getUsuarioTecnico().getIdUsuario());
        if (!usuarioTecnicoOpt.isPresent()) {
            throw new RuntimeException("El usuario técnico con ID " + cita.getUsuarioTecnico().getIdUsuario() + " no existe");
        }
        cita.setUsuarioTecnico(usuarioTecnicoOpt.get());

        // Validar que el usuario que creó la cita exista (obligatorio)
        if (cita.getUsuarioCreo() == null || cita.getUsuarioCreo().getIdUsuario() == null) {
            throw new RuntimeException("El usuario creador es obligatorio");
        }

        Optional<Usuarios> usuarioCreoOpt = usuariosRepository.findById(cita.getUsuarioCreo().getIdUsuario());
        if (!usuarioCreoOpt.isPresent()) {
            throw new RuntimeException("El usuario creador con ID " + cita.getUsuarioCreo().getIdUsuario() + " no existe");
        }
        cita.setUsuarioCreo(usuarioCreoOpt.get());

        // Validar que la franja horaria exista (obligatorio)
        if (cita.getFranjaHoraria() == null || cita.getFranjaHoraria().getIdFranja() == null) {
            throw new RuntimeException("La franja horaria es obligatoria");
        }

        Optional<FranjasHorarias> franjaHorariaOpt = franjasHorariasService.obtenerPorId(cita.getFranjaHoraria().getIdFranja());
        if (!franjaHorariaOpt.isPresent()) {
            throw new RuntimeException("La franja horaria con ID " + cita.getFranjaHoraria().getIdFranja() + " no existe");
        }
        cita.setFranjaHoraria(franjaHorariaOpt.get());

        // Validar que el lugar exista (obligatorio)
        if (cita.getLugar() == null || cita.getLugar().getIdLugar() == null) {
            throw new RuntimeException("El lugar es obligatorio");
        }

        Optional<Lugares> lugarOpt = lugaresService.obtenerPorId(cita.getLugar().getIdLugar());
        if (!lugarOpt.isPresent()) {
            throw new RuntimeException("El lugar con ID " + cita.getLugar().getIdLugar() + " no existe");
        }
        cita.setLugar(lugarOpt.get());

        // Validar que el estado de cita exista (obligatorio)
        if (cita.getEstadoCita() == null || cita.getEstadoCita().getNombreEc() == null) {
            throw new RuntimeException("El estado de cita es obligatorio");
        }

        Optional<EstadoCita> estadoCitaOpt = estadoCitaService.obtenerPorId(cita.getEstadoCita().getNombreEc());
        if (!estadoCitaOpt.isPresent()) {
            throw new RuntimeException("El estado de cita con nombre " + cita.getEstadoCita().getNombreEc() + " no existe");
        }
        cita.setEstadoCita(estadoCitaOpt.get());

        return citasRepository.save(cita);
    }

    // Actualizar una cita existente
    public Citas actualizar(Integer id, Citas citaActualizada) {
        Optional<Citas> citaOpt = citasRepository.findById(id);

        if (!citaOpt.isPresent()) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }

        Citas citaExistente = citaOpt.get();

        // Actualiza solo si se envió
        if (citaActualizada.getObservacionesCita() != null) {
            citaExistente.setObservacionesCita(citaActualizada.getObservacionesCita());
        }

        // Validar usuario técnico antes de actualizar
        if (citaActualizada.getUsuarioTecnico() != null && citaActualizada.getUsuarioTecnico().getIdUsuario() != null) {
            Optional<Usuarios> usuarioTecnicoOpt = usuariosRepository.findById(citaActualizada.getUsuarioTecnico().getIdUsuario());
            if (!usuarioTecnicoOpt.isPresent()) {
                throw new RuntimeException("El usuario técnico con ID " + citaActualizada.getUsuarioTecnico().getIdUsuario() + " no existe");
            }
            citaExistente.setUsuarioTecnico(usuarioTecnicoOpt.get());
        }

        // Validar usuario creador antes de actualizar
        if (citaActualizada.getUsuarioCreo() != null && citaActualizada.getUsuarioCreo().getIdUsuario() != null) {
            Optional<Usuarios> usuarioCreoOpt = usuariosRepository.findById(citaActualizada.getUsuarioCreo().getIdUsuario());
            if (!usuarioCreoOpt.isPresent()) {
                throw new RuntimeException("El usuario creador con ID " + citaActualizada.getUsuarioCreo().getIdUsuario() + " no existe");
            }
            citaExistente.setUsuarioCreo(usuarioCreoOpt.get());
        }

        // Validar franja horaria antes de actualizar
        if (citaActualizada.getFranjaHoraria() != null && citaActualizada.getFranjaHoraria().getIdFranja() != null) {
            Optional<FranjasHorarias> franjaHorariaOpt = franjasHorariasService.obtenerPorId(citaActualizada.getFranjaHoraria().getIdFranja());
            if (!franjaHorariaOpt.isPresent()) {
                throw new RuntimeException("La franja horaria con ID " + citaActualizada.getFranjaHoraria().getIdFranja() + " no existe");
            }
            citaExistente.setFranjaHoraria(franjaHorariaOpt.get());
        }

        // Validar lugar antes de actualizar
        if (citaActualizada.getLugar() != null && citaActualizada.getLugar().getIdLugar() != null) {
            Optional<Lugares> lugarOpt = lugaresService.obtenerPorId(citaActualizada.getLugar().getIdLugar());
            if (!lugarOpt.isPresent()) {
                throw new RuntimeException("El lugar con ID " + citaActualizada.getLugar().getIdLugar() + " no existe");
            }
            citaExistente.setLugar(lugarOpt.get());
        }

        // Validar estado de cita antes de actualizar
        if (citaActualizada.getEstadoCita() != null && citaActualizada.getEstadoCita().getNombreEc() != null) {
            Optional<EstadoCita> estadoCitaOpt = estadoCitaService.obtenerPorId(citaActualizada.getEstadoCita().getNombreEc());
            if (!estadoCitaOpt.isPresent()) {
                throw new RuntimeException("El estado de cita con nombre " + citaActualizada.getEstadoCita().getNombreEc() + " no existe");
            }
            citaExistente.setEstadoCita(estadoCitaOpt.get());
        }

        return citasRepository.save(citaExistente);
    }

    // Borrar físicamente
    public boolean borrar(Integer id) {
        if (!citasRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }

        citasRepository.deleteById(id);
        return true;
    }

    // Verificar si existe una cita
    public boolean existe(Integer id) {
        return citasRepository.existsById(id);
    }

    // Obtener citas por usuario técnico
    public List<Citas> obtenerPorUsuarioTecnico(String idUsuario) {
        Optional<Usuarios> usuarioOpt = usuariosRepository.findById(idUsuario);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return citasRepository.findByUsuarioTecnico(usuarioOpt.get());
    }

    // Obtener citas creadas por un usuario
    public List<Citas> obtenerPorUsuarioCreador(String idUsuario) {
        Optional<Usuarios> usuarioOpt = usuariosRepository.findById(idUsuario);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }
        return citasRepository.findByUsuarioCreo(usuarioOpt.get());
    }

    // Obtener citas por estado
    public List<Citas> obtenerPorEstado(String nombreEc) {
        Optional<EstadoCita> estadoCitaOpt = estadoCitaService.obtenerPorId(nombreEc);
        if (!estadoCitaOpt.isPresent()) {
            throw new RuntimeException("Estado de cita no encontrado con nombre: " + nombreEc);
        }
        return citasRepository.findByEstadoCita(estadoCitaOpt.get());
    }

    // Obtener citas por lugar
    public List<Citas> obtenerPorLugar(Integer idLugar) {
        Optional<Lugares> lugarOpt = lugaresService.obtenerPorId(idLugar);
        if (!lugarOpt.isPresent()) {
            throw new RuntimeException("Lugar no encontrado con ID: " + idLugar);
        }
        return citasRepository.findByLugar(lugarOpt.get());
    }

    // Obtener citas por franja horaria
    public List<Citas> obtenerPorFranjaHoraria(Integer idFranja) {
        Optional<FranjasHorarias> franjaOpt = franjasHorariasService.obtenerPorId(idFranja);
        if (!franjaOpt.isPresent()) {
            throw new RuntimeException("Franja horaria no encontrada con ID: " + idFranja);
        }
        return citasRepository.findByFranjaHoraria(franjaOpt.get());
    }
}
