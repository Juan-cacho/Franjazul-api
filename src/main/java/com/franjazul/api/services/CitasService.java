package com.franjazul.api.services;

import com.franjazul.api.dto.ActualizarCitaRequest;
import com.franjazul.api.dto.MisCitaDetalleDTO;
import com.franjazul.api.dto.SolicitudCitaRequest;
import com.franjazul.api.model.*;
import com.franjazul.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitasService {

    @Autowired
    private CitasRepository citasRepository;

    @Autowired
    private CitaServicioRepository citaServicioRepository;

    @Autowired
    private ServiciosRepository serviciosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private TipoLugarRepository tipoLugarRepository;

    @Autowired
    private EstadoCitaService estadoCitaService;

    @Autowired
    private EstadoCitaRepository estadoCitaRepository;

    @Autowired
    private FranjasHorariasService franjasHorariasService;

    @Autowired
    private FranjasHorariasRepository franjasHorariasRepository;

    @Autowired
    private LugaresRepository lugaresRepository;

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




    public Citas crear(Citas cita) {
        // Validar y cargar Usuario Técnico
        if (cita.getUsuarioTecnico() == null || cita.getUsuarioTecnico().getIdUsuario() == null) {
            throw new RuntimeException("El técnico asignado es obligatorio");
        }
        Usuarios tecnico = usuariosRepository.findById(cita.getUsuarioTecnico().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("El técnico con ID " + cita.getUsuarioTecnico().getIdUsuario() + " no existe"));

        // Validar que el técnico tenga cargo TECNICO
        if (!"TECNICO".equalsIgnoreCase(tecnico.getCargoDeUsuario().getNombreCargo())) {
            throw new RuntimeException("El usuario seleccionado no es un técnico");
        }
        cita.setUsuarioTecnico(tecnico);

        // Validar y cargar Usuario Cliente
        if (cita.getUsuarioCreo() == null || cita.getUsuarioCreo().getIdUsuario() == null) {
            throw new RuntimeException("El cliente solicitante es obligatorio");
        }
        Usuarios cliente = usuariosRepository.findById(cita.getUsuarioCreo().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("El cliente con ID " + cita.getUsuarioCreo().getIdUsuario() + " no existe"));

        // Validar que el cliente tenga cargo CLIENTE
        if (!"CLIENTE".equalsIgnoreCase(cliente.getCargoDeUsuario().getNombreCargo())) {
            throw new RuntimeException("El usuario seleccionado no es un cliente");
        }
        cita.setUsuarioCreo(cliente);

        // Validar y cargar Franja Horaria
        if (cita.getFranjaHoraria() == null || cita.getFranjaHoraria().getIdFranja() == null) {
            throw new RuntimeException("La franja horaria es obligatoria");
        }
        FranjasHorarias franja = franjasHorariasRepository.findById(cita.getFranjaHoraria().getIdFranja())
                .orElseThrow(() -> new RuntimeException("La franja horaria con ID " + cita.getFranjaHoraria().getIdFranja() + " no existe"));
        cita.setFranjaHoraria(franja);

        // Validar y cargar Lugar
        if (cita.getLugar() == null || cita.getLugar().getIdLugar() == null) {
            throw new RuntimeException("El lugar es obligatorio");
        }
        Lugares lugar = lugaresRepository.findById(cita.getLugar().getIdLugar())
                .orElseThrow(() -> new RuntimeException("El lugar con ID " + cita.getLugar().getIdLugar() + " no existe"));
        cita.setLugar(lugar);

        // Validar y cargar Estado de Cita
        if (cita.getEstadoCita() == null || cita.getEstadoCita().getNombreEc() == null) {
            throw new RuntimeException("El estado de la cita es obligatorio");
        }
        EstadoCita estado = estadoCitaRepository.findById(cita.getEstadoCita().getNombreEc())
                .orElseThrow(() -> new RuntimeException("El estado de cita '" + cita.getEstadoCita().getNombreEc() + "' no existe"));
        cita.setEstadoCita(estado);

        // Validar observaciones
        if (cita.getObservacionesCita() == null) {
            throw new RuntimeException("Las observaciones son obligatorias");
        }

        // Guardar la cita
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






    //>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //>>>>>>>>>Logica para el CLIENTE Pedir una cita<<<<<<<<<<<<
    //>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<








    @Transactional
    public Citas solicitarCita(SolicitudCitaRequest request) {
        System.out.println("Empezo");
        // 1. Validar que el usuario cliente existe
        Usuarios cliente = usuariosRepository.findById(request.getIdUsuarioCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!"CLIENTE".equalsIgnoreCase(cliente.getCargoDeUsuario().getNombreCargo())) {
            throw new RuntimeException("El usuario debe ser un CLIENTE");
        }

        System.out.println("Llego al primer punto");
        // 2. Validar que los servicios existen
        if (request.getServiciosIds() == null || request.getServiciosIds().isEmpty()) {
            throw new RuntimeException("Debe seleccionar al menos un servicio");
        }

        System.out.println("Llego al punto 2");
        // 3. Obtener técnico por rotación
        Usuarios tecnico = obtenerSiguienteTecnico();

        System.out.println("Llego al punto 3");
        // 4. Buscar o crear franja horaria
        FranjasHorarias franja = obtenerOCrearFranja(
                request.getFechaInicio(),
                request.getFechaFin()
        );

        System.out.println("Llego al punto 4");
        // 5. Crear o buscar lugar
        Lugares lugar = crearLugar(
                request.getNombreLugar(),
                request.getDireccionLugar(),
                request.getIdTipoLugar(),
                request.getIdLugarPadre()
        );

        System.out.println("Llego al punto 5");
        // 6. Obtener estado PENDIENTE
        EstadoCita estadoPendiente = estadoCitaRepository.findById("PENDIENTE")
                .orElseThrow(() -> new RuntimeException("Estado PENDIENTE no encontrado"));

        System.out.println("Llego al punto 6");
        // 7. Crear la cita
        Citas nuevaCita = new Citas();
        nuevaCita.setObservacionesCita(" "); // Espacio en blanco por defecto
        nuevaCita.setUsuarioTecnico(tecnico);
        nuevaCita.setUsuarioCreo(cliente);
        nuevaCita.setFranjaHoraria(franja);
        nuevaCita.setLugar(lugar);
        nuevaCita.setEstadoCita(estadoPendiente);

        Citas citaGuardada = citasRepository.save(nuevaCita);

        System.out.println("Llego al punto 7");
        // 8. Crear relaciones CitaServicio
        for (Integer servicioId : request.getServiciosIds()) {
            Servicios servicio = serviciosRepository.findById(servicioId)
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado: " + servicioId));

            CitaServicio citaServicio = new CitaServicio();
            citaServicio.setCitaEnIntermedio(citaGuardada.getIdCita());
            citaServicio.setServicioEnIntermedio(servicioId);
            citaServicio.setCantidadSer(1); // Por defecto 1

            citaServicioRepository.save(citaServicio);
        }

        return citaGuardada;
    }


    private Usuarios obtenerSiguienteTecnico() {
        System.out.println("Entro a buscar los tecnicos");
        // Buscar todos los técnicos
        List<Usuarios> tecnicos = usuariosRepository.findByCargoDeUsuario_NombreCargo("TECNICO");

        if (tecnicos.isEmpty()) {
            throw new RuntimeException("No hay técnicos disponibles");
        }
        System.out.println("Va a buscar la ultima cita");
        // Obtener la última cita para saber qué técnico le toca
        Optional<Citas> ultimaCita = citasRepository.findFirstByOrderByIdCitaDesc();

        if (!ultimaCita.isPresent()) {
            // Si no hay citas, asignar al primer técnico
            return tecnicos.get(0);
        }
        System.out.println("Va a buscar el ultimo ID de Tecnico");
        // Buscar el índice del técnico de la última cita
        String idUltimoTecnico = ultimaCita.get().getUsuarioTecnico().getIdUsuario();
        int indexUltimoTecnico = -1;

        for (int i = 0; i < tecnicos.size(); i++) {
            if (tecnicos.get(i).getIdUsuario().equals(idUltimoTecnico)) {
                indexUltimoTecnico = i;
                break;
            }
        }
        System.out.println("Asigno al tecnico");
        // Asignar al siguiente técnico (rotación circular)
        int indexSiguienteTecnico = (indexUltimoTecnico + 1) % tecnicos.size();
        return tecnicos.get(indexSiguienteTecnico);

    }

    private FranjasHorarias obtenerOCrearFranja(
            java.time.LocalDateTime fechaInicio,
            java.time.LocalDateTime fechaFin
    ) {
        // Buscar si ya existe la franja
        Optional<FranjasHorarias> franjaExistente = franjasHorariasRepository
                .findByFechaInicioAndFechaFin(fechaInicio, fechaFin);

        if (franjaExistente.isPresent()) {
            return franjaExistente.get();
        }

        // Si no existe, crear nueva franja
        FranjasHorarias nuevaFranja = new FranjasHorarias();
        nuevaFranja.setFechaInicio(fechaInicio);
        nuevaFranja.setFechaFin(fechaFin);

        return franjasHorariasRepository.save(nuevaFranja);
    }

    private Lugares crearLugar(
            String nombreLugar,
            String direccionLugar,
            Integer idTipoLugar,
            Integer idLugarPadre
    ) {
        TipoLugar tipoLugar = tipoLugarRepository.findById(idTipoLugar)
                .orElseThrow(() -> new RuntimeException("Tipo de lugar no encontrado"));

        Lugares nuevoLugar = new Lugares();
        nuevoLugar.setNombreLugar(nombreLugar);
        nuevoLugar.setDireccionLugar(direccionLugar);
        nuevoLugar.setTipoLugar(tipoLugar);

        if (idLugarPadre != null) {
            Lugares lugarPadre = lugaresRepository.findById(idLugarPadre)
                    .orElseThrow(() -> new RuntimeException("Lugar padre no encontrado"));
            nuevoLugar.setLugarPadre(lugarPadre);
        }

        return lugaresRepository.save(nuevoLugar);
    }







    //>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //>>>>>>Logica para el TECNICO actualizar la cita<<<<<<<<<<<
    //>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<








    public Page<MisCitaDetalleDTO> obtenerCitasPorTecnico(String idTecnico, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Citas> citas = citasRepository.findByUsuarioTecnicoOrderByFecha(idTecnico, pageable);
        return citas.map(this::convertirAMisCitaDetalleDTO);
    }

    public Page<MisCitaDetalleDTO> buscarCitasPorTecnicoYCliente(String idTecnico, String busqueda, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Citas> citas = citasRepository.findByUsuarioTecnicoAndClienteContaining(idTecnico, busqueda, pageable);
        return citas.map(this::convertirAMisCitaDetalleDTO);
    }

    private MisCitaDetalleDTO convertirAMisCitaDetalleDTO(Citas cita) {
        String nombreCliente = cita.getUsuarioCreo().getNombreUs() + " " +
                cita.getUsuarioCreo().getApellidoUs();
        if (cita.getUsuarioCreo().getApellido2Us() != null &&
                !cita.getUsuarioCreo().getApellido2Us().trim().isEmpty()) {
            nombreCliente += " " + cita.getUsuarioCreo().getApellido2Us();
        }

        String nombreTecnico = cita.getUsuarioTecnico().getNombreUs() + " " +
                cita.getUsuarioTecnico().getApellidoUs();

        List<CitaServicio> citaServicios = citaServicioRepository.findByCitaEnIntermedio(cita.getIdCita());
        List<String> servicios = citaServicios.stream()
                .map(cs -> cs.getServicio().getNombreSer())
                .collect(Collectors.toList());

        return new MisCitaDetalleDTO(
                cita.getIdCita(),
                cita.getObservacionesCita(),
                nombreCliente,
                cita.getUsuarioCreo().getIdUsuario(),
                nombreTecnico,
                cita.getUsuarioTecnico().getIdUsuario(),
                cita.getFranjaHoraria().getFechaInicio(),
                cita.getFranjaHoraria().getFechaFin(),
                cita.getLugar().getNombreLugar(),
                cita.getLugar().getDireccionLugar(),
                cita.getEstadoCita().getNombreEc(),
                cita.getEstadoCita().getDescripcionEc(),
                servicios
        );
    }


    public MisCitaDetalleDTO obtenerDetalleCita(Integer idCita) {
        Citas cita = citasRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        return convertirAMisCitaDetalleDTO(cita);
    }

    @Transactional
    public MisCitaDetalleDTO actualizarCitaTecnico(ActualizarCitaRequest request) {
        Citas cita = citasRepository.findById(request.getIdCita())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        boolean cambioFranja = false;

        // Actualizar observaciones si se proporcionan
        if (request.getObservacionesCita() != null) {
            cita.setObservacionesCita(request.getObservacionesCita());
        }

        // Validar y actualizar franja horaria si cambió
        if (request.getFechaInicio() != null && request.getFechaFin() != null) {
            LocalDateTime franjaActualInicio = cita.getFranjaHoraria().getFechaInicio();
            LocalDateTime franjaActualFin = cita.getFranjaHoraria().getFechaFin();

            if (!franjaActualInicio.equals(request.getFechaInicio()) ||
                    !franjaActualFin.equals(request.getFechaFin())) {

                cambioFranja = true;

                // Validar que el técnico no tenga otra cita en esa franja
                long citasEnFranja = citasRepository.countByTecnicoAndFranjaExcluyendoCita(
                        cita.getUsuarioTecnico().getIdUsuario(),
                        request.getFechaInicio(),
                        request.getFechaFin(),
                        cita.getIdCita()
                );

                if (citasEnFranja > 0) {
                    throw new RuntimeException("Ya tienes otra cita programada en esa franja horaria");
                }

                // Buscar o crear nueva franja
                FranjasHorarias nuevaFranja = obtenerOCrearFranja(
                        request.getFechaInicio(),
                        request.getFechaFin()
                );
                cita.setFranjaHoraria(nuevaFranja);

                // Cambiar estado a REAGENDADA automáticamente
                EstadoCita estadoReagendada = estadoCitaRepository.findById("REAGENDADA")
                        .orElseThrow(() -> new RuntimeException("Estado REAGENDADA no encontrado"));
                cita.setEstadoCita(estadoReagendada);
            }
        }

        // Actualizar estado solo si no se cambió la franja
        if (!cambioFranja && request.getEstadoCita() != null) {
            if ("REAGENDADA".equalsIgnoreCase(request.getEstadoCita())) {
                throw new RuntimeException("No puedes cambiar manualmente el estado a REAGENDADA");
            }

            EstadoCita nuevoEstado = estadoCitaRepository.findById(request.getEstadoCita())
                    .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
            cita.setEstadoCita(nuevoEstado);
        }

        Citas citaActualizada = citasRepository.save(cita);
        return convertirAMisCitaDetalleDTO(citaActualizada);
    }

    public List<EstadoCita> obtenerEstadosDisponibles() {
        List<EstadoCita> todosEstados = estadoCitaRepository.findAll();
        // Filtrar REAGENDADA
        return todosEstados.stream()
                .filter(estado -> !"REAGENDADA".equalsIgnoreCase(estado.getNombreEc()))
                .collect(Collectors.toList());
    }

}
