package com.franjazul.api.services;

import com.franjazul.api.dto.LoginRequest;
import com.franjazul.api.dto.LoginResponse;
import com.franjazul.api.model.Usuarios;
import com.franjazul.api.model.Perfiles;
import com.franjazul.api.model.Cargos;
import com.franjazul.api.repository.UsuariosRepository;
import com.franjazul.api.repository.PerfilesRepository;
import com.franjazul.api.repository.CargosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Autowired
    private PerfilesRepository perfilRepository;

    @Autowired
    private CargosRepository cargoRepository;

    // Obtener un usuario por ID
    public Optional<Usuarios> obtenerPorId(String id) {
        return usuarioRepository.findById(id);
    }

    // Obtener todos los usuarios
    public List<Usuarios> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // Crear un nuevo usuario
    public Usuarios crear(Usuarios usuario) {
        // Validar que el email no exista
        if (usuarioRepository.existsByEmailUs(usuario.getEmailUs())) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        // Validar formato de email
        if (!usuario.getEmailUs().contains("@")) {
            throw new RuntimeException("El email debe contener @");
        }

        // Validar longitud del teléfono
        String telefonoStr = String.valueOf(usuario.getTelefonoUs());
        if (telefonoStr.length() != 10) {
            throw new RuntimeException("El teléfono debe tener exactamente 10 dígitos");
        }

        // Validar que el perfil exista
        if (usuario.getPerfilDeUsuario() == null || usuario.getPerfilDeUsuario().getIdPer() == null) {
            throw new RuntimeException("Debe especificar un perfil válido");
        }

        Integer idPerfil = usuario.getPerfilDeUsuario().getIdPer();
        Optional<Perfiles> perfilExiste = perfilRepository.findById(idPerfil);
        if (!perfilExiste.isPresent()) {
            throw new RuntimeException("El perfil especificado no existe");
        }
        usuario.setPerfilDeUsuario(perfilExiste.get());

        // Validar que el cargo exista
        if (usuario.getCargoDeUsuario() == null || usuario.getCargoDeUsuario().getNombreCargo() == null) {
            throw new RuntimeException("Debe especificar un cargo válido");
        }

        Optional<Cargos> cargoExiste = cargoRepository.findById(usuario.getCargoDeUsuario().getNombreCargo());
        if (!cargoExiste.isPresent()) {
            throw new RuntimeException("El cargo especificado no existe");
        }

        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario existente
    public Usuarios actualizar(String id, Usuarios usuarioActualizado) {
        Optional<Usuarios> usuarioOptional = usuarioRepository.findById(id);

        if (!usuarioOptional.isPresent()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        Usuarios usuarioExistente = usuarioOptional.get();

        // Actualizar campos si se enviaron
        if (usuarioActualizado.getNombreUs() != null) {
            usuarioExistente.setNombreUs(usuarioActualizado.getNombreUs());
        }
        if (usuarioActualizado.getApellidoUs() != null) {
            usuarioExistente.setApellidoUs(usuarioActualizado.getApellidoUs());
        }
        if (usuarioActualizado.getApellido2Us() != null) {
            usuarioExistente.setApellido2Us(usuarioActualizado.getApellido2Us());
        }
        if (usuarioActualizado.getEmailUs() != null) {
            // Validar formato de email
            if (!usuarioActualizado.getEmailUs().contains("@")) {
                throw new RuntimeException("El email debe contener @");
            }
            usuarioExistente.setEmailUs(usuarioActualizado.getEmailUs());
        }
        if (usuarioActualizado.getPasswordUs() != null) {
            usuarioExistente.setPasswordUs(usuarioActualizado.getPasswordUs());
        }
        if (usuarioActualizado.getTelefonoUs() != null) {
            // Validar longitud del teléfono
            String telefonoStr = String.valueOf(usuarioActualizado.getTelefonoUs());
            if (telefonoStr.length() != 10) {
                throw new RuntimeException("El teléfono debe tener exactamente 10 dígitos");
            }
            usuarioExistente.setTelefonoUs(usuarioActualizado.getTelefonoUs());
        }

        // Actualizar perfil si se proporciona
        if (usuarioActualizado.getPerfilDeUsuario() != null && usuarioActualizado.getPerfilDeUsuario().getIdPer() != null) {
            Integer idPerfil = usuarioActualizado.getPerfilDeUsuario().getIdPer();
            Optional<Perfiles> perfilExiste = perfilRepository.findById(idPerfil);
            if (!perfilExiste.isPresent()) {
                throw new RuntimeException("El perfil especificado no existe");
            }
            usuarioExistente.setPerfilDeUsuario(perfilExiste.get());
        }

        // Actualizar cargo si se proporciona
        if (usuarioActualizado.getCargoDeUsuario() != null) {
            Optional<Cargos> cargoExiste = cargoRepository.findById(usuarioActualizado.getCargoDeUsuario().getNombreCargo());
            if (!cargoExiste.isPresent()) {
                throw new RuntimeException("El cargo especificado no existe");
            }
            usuarioExistente.setCargoDeUsuario(usuarioActualizado.getCargoDeUsuario());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    // Borrar físicamente
    public boolean borrar(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        usuarioRepository.deleteById(id);
        return true;
    }

    // Verificar si existe un usuario
    public boolean existe(String id) {
        return usuarioRepository.existsById(id);
    }

    // Login - Validar credenciales
    public LoginResponse login(LoginRequest loginRequest) {
        // Validar que se envíen email y password
        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Debe proporcionar un email");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Debe proporcionar una contraseña");
        }

        // Buscar usuario por email y password
        Optional<Usuarios> usuarioOptional = usuarioRepository.findByEmailAndPassword(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (!usuarioOptional.isPresent()) {
            throw new RuntimeException("Credenciales inválidas");
        }

        Usuarios usuario = usuarioOptional.get();

        // Construir nombre completo
        String nombreCompleto = usuario.getNombreUs() + " " + usuario.getApellidoUs();
        if (usuario.getApellido2Us() != null && !usuario.getApellido2Us().trim().isEmpty()) {
            nombreCompleto = nombreCompleto + " " + usuario.getApellido2Us();
        }

        // Crear respuesta del login
        LoginResponse response = new LoginResponse();
        response.setIdUsuario(usuario.getIdUsuario());
        response.setNombreCompleto(nombreCompleto);
        response.setEmail(usuario.getEmailUs());
        response.setCargo(usuario.getCargoDeUsuario().getNombreCargo());
        response.setIdPerfil(usuario.getPerfilDeUsuario().getIdPer());
        response.setNombrePerfil(usuario.getPerfilDeUsuario().getNombrePer());

        return response;
    }

    // Obtener usuarios por cargo
    public List<Usuarios> obtenerPorCargo(String nombreCargo) {
        return usuarioRepository.findByCargoDeUsuario_NombreCargo(nombreCargo);
    }
}