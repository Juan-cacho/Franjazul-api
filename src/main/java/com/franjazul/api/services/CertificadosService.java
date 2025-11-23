package com.franjazul.api.services;

import com.franjazul.api.dto.SolicitudCertificadoDTO;
import com.franjazul.api.model.Usuarios;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.franjazul.api.model.Certificados;
import com.franjazul.api.model.Citas;
import com.franjazul.api.repository.CertificadosRepository;
import com.franjazul.api.repository.UsuariosRepository;
import com.franjazul.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CertificadosService {

    @Autowired
    private CertificadosRepository certificadosRepository;

    @Autowired
    private CitasService citasService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Obtener un certificado por código
    public Optional<Certificados> obtenerPorId(String codigoCer) {
        return certificadosRepository.findById(codigoCer);
    }

    // Obtener todos los certificados
    public List<Certificados> obtenerTodos() {
        return certificadosRepository.findAll();
    }

    // Crear un nuevo certificado
    public Certificados crear(Certificados certificado) {
        // Validar que el código no exista
        if (certificadosRepository.existsById(certificado.getCodigoCer())) {
            throw new RuntimeException("Ya existe un certificado con el código: " + certificado.getCodigoCer());
        }

        // Validar que la cita exista (obligatorio)
        if (certificado.getCita() == null || certificado.getCita().getIdCita() == null) {
            throw new RuntimeException("La cita es obligatoria");
        }

        Optional<Citas> citaOpt = citasService.obtenerPorId(certificado.getCita().getIdCita());
        if (!citaOpt.isPresent()) {
            throw new RuntimeException("La cita con ID " + certificado.getCita().getIdCita() + " no existe");
        }
        certificado.setCita(citaOpt.get());

        // Validar que la fecha de emisión sea anterior a la fecha de vencimiento
        if (certificado.getFechaEmision() != null && certificado.getFechaVence() != null) {
            if (certificado.getFechaEmision().isAfter(certificado.getFechaVence())) {
                throw new RuntimeException("La fecha de emisión debe ser anterior a la fecha de vencimiento");
            }
        }

        return certificadosRepository.save(certificado);
    }

    // Actualizar un certificado existente
    public Certificados actualizar(String codigoCer, Certificados certificadoActualizado) {
        Optional<Certificados> certificadoOpt = certificadosRepository.findById(codigoCer);

        if (!certificadoOpt.isPresent()) {
            throw new RuntimeException("Certificado no encontrado con código: " + codigoCer);
        }

        Certificados certificadoExistente = certificadoOpt.get();

        // Actualiza solo si se envió
        if (certificadoActualizado.getFechaEmision() != null) {
            certificadoExistente.setFechaEmision(certificadoActualizado.getFechaEmision());
        }

        if (certificadoActualizado.getFechaVence() != null) {
            certificadoExistente.setFechaVence(certificadoActualizado.getFechaVence());
        }

        // Validar cita antes de actualizar
        if (certificadoActualizado.getCita() != null && certificadoActualizado.getCita().getIdCita() != null) {
            Optional<Citas> citaOpt = citasService.obtenerPorId(certificadoActualizado.getCita().getIdCita());
            if (!citaOpt.isPresent()) {
                throw new RuntimeException("La cita con ID " + certificadoActualizado.getCita().getIdCita() + " no existe");
            }
            certificadoExistente.setCita(citaOpt.get());
        }

        // Validar fechas
        if (certificadoExistente.getFechaEmision().isAfter(certificadoExistente.getFechaVence())) {
            throw new RuntimeException("La fecha de emisión debe ser anterior a la fecha de vencimiento");
        }

        return certificadosRepository.save(certificadoExistente);
    }

    // Borrar físicamente
    public boolean borrar(String codigoCer) {
        if (!certificadosRepository.existsById(codigoCer)) {
            throw new RuntimeException("Certificado no encontrado con código: " + codigoCer);
        }

        certificadosRepository.deleteById(codigoCer);
        return true;
    }

    // Verificar si existe un certificado
    public boolean existe(String codigoCer) {
        return certificadosRepository.existsById(codigoCer);
    }

    // Obtener certificados por cita
    public List<Certificados> obtenerPorCita(Integer citaId) {
        Optional<Citas> citaOpt = citasService.obtenerPorId(citaId);
        if (!citaOpt.isPresent()) {
            throw new RuntimeException("Cita no encontrada con ID: " + citaId);
        }
        return certificadosRepository.findByCita(citaOpt.get());
    }

    // Obtener certificados vencidos
    public List<Certificados> obtenerVencidos() {
        LocalDateTime ahora = LocalDateTime.now();
        return certificadosRepository.findByFechaVenceBefore(ahora);
    }

    // Obtener certificados vigentes
    public List<Certificados> obtenerVigentes() {
        LocalDateTime ahora = LocalDateTime.now();
        return certificadosRepository.findByFechaVenceAfter(ahora);
    }

    // Obtener certificados por rango de fechas
    public List<Certificados> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        return certificadosRepository.findByFechaEmisionBetween(fechaInicio, fechaFin);
    }







    //>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //>>>>>>>>>>>>>>Logica Solicitar Certificado<<<<<<<<<<<<<<<
    //>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<







    public byte[] generarCertificadoPDF(SolicitudCertificadoDTO solicitud, String token) throws Exception {
        // 1. Obtener ID del usuario desde el token
        String userId = jwtUtil.extractIdUsuario(token);

        // 2. Validaciones según tipo de documento
        validarSolicitud(solicitud, userId);

        // 3. Obtener el último certificado del usuario
        Optional<Certificados> certificadoOpt = certificadosRepository.findFirstByCita_UsuarioCreo_IdUsuarioOrderByFechaEmisionDesc(userId);

        if (!certificadoOpt.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se encontró ningún certificado asociado a este usuario"
            );
        }

        Certificados certificado = certificadoOpt.get();

        // 4. Obtener información del usuario
        Usuarios usuario = usuariosRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        // 5. Generar el PDF
        return crearPDF(certificado, solicitud, usuario);
    }



    //Se validan los campos que el usuario ingreso en el front con su id del token


    private void validarSolicitud(SolicitudCertificadoDTO solicitud, String userId) {
        if (solicitud.getTipoDocumento().equals("CC")) {
            // Validar que la CC coincida con el ID del token
            if (!solicitud.getCedula().equals(userId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La cédula ingresada no coincide con el usuario autenticado"
                );
            }
        } else if (solicitud.getTipoDocumento().equals("NIT")) {
            // Validar que la CC del solicitante coincida con el ID del token
            if (!solicitud.getCedulaSolicitante().equals(userId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La cédula del solicitante no coincide con el usuario autenticado"
                );
            }

            // Validar campos requeridos para NIT
            if (solicitud.getNit() == null || solicitud.getNit().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El NIT es requerido");
            }
            if (solicitud.getNombreEmpresa() == null || solicitud.getNombreEmpresa().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la empresa es requerido");
            }
            if (solicitud.getRut() == null || solicitud.getRut().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El RUT es requerido");
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo de documento inválido. Debe ser CC o NIT"
            );
        }
    }




    //
    //Creacion de del PDF
    //




    private byte[] crearPDF(Certificados certificado, SolicitudCertificadoDTO solicitud, Usuarios usuario) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Definir fuentes
        PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont fontBold   = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        // Colores corporativos
        DeviceRgb colorAzul = new DeviceRgb(37, 99, 235);
        DeviceRgb colorVerde = new DeviceRgb(13, 148, 136);

        // Obtener datos de la cita
        Citas cita = certificado.getCita();
        String nombreCompleto = usuario.getNombreUs() + " " + usuario.getApellidoUs() +
                (usuario.getApellido2Us() != null ? " " + usuario.getApellido2Us() : "");

        // Formatear fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaEmision = certificado.getFechaEmision().format(formatter);
        String fechaVence = certificado.getFechaVence().format(formatter);

        // ==================== ENCABEZADO ====================

        Paragraph titulo = new Paragraph("CERTIFICADO")
                .setFont(fontBold)
                .setFontSize(20)
                .setFontColor(colorAzul)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setMarginBottom(10);
        document.add(titulo);

        Paragraph codigo = new Paragraph("Co. " + certificado.getCodigoCer())
                .setFont(fontBold)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(15);
        document.add(codigo);

        // ==================== TABLA DE INFORMACIÓN ====================

        Table tablaInfo = new Table(UnitValue.createPercentArray(new float[]{1, 3}))
                .useAllAvailableWidth()
                .setMarginBottom(15);

        String nombreCertificado = solicitud.getTipoDocumento().equals("CC")
                ? nombreCompleto
                : solicitud.getNombreEmpresa();

        agregarFilaTabla(tablaInfo, "NOMBRE", nombreCertificado, fontBold, fontNormal);

        String documento = solicitud.getTipoDocumento().equals("CC")
                ? solicitud.getCedula()
                : solicitud.getNit();

        agregarFilaTabla(tablaInfo, "CC / NIT", documento, fontBold, fontNormal);
        agregarFilaTabla(tablaInfo, "DIRECCIÓN", cita.getLugar().getDireccionLugar(), fontBold, fontNormal);
        agregarFilaTabla(tablaInfo, "TELÉFONO", String.valueOf(usuario.getTelefonoUs()), fontBold, fontNormal);

        document.add(tablaInfo);

        // ==================== EMPRESA ====================

        Paragraph empresa = new Paragraph("FRANJAZUL Y ECOMIP")
                .setFont(fontBold)
                .setFontSize(14)
                .setFontColor(colorVerde)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setMarginTop(10)
                .setMarginBottom(5);
        document.add(empresa);

        if (solicitud.getTipoDocumento().equals("NIT")) {
            Paragraph rut = new Paragraph("Registro Único Tributario Nº " + solicitud.getRut())
                    .setFont(fontBold)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);
            document.add(rut);
        }

        // ==================== CERTIFICACIÓN ====================

        Paragraph certifica = new Paragraph("Certifica:")
                .setFont(fontBold)
                .setFontSize(12)
                .setMarginTop(15)
                .setMarginBottom(10);
        document.add(certifica);

        String tipoLugar = solicitud.getTipoDocumento().equals("CC") ? "residencia" : "Sede comercial";

        String textoCertificacion = String.format(
                "Que se desarrolló desinsectación y desratización en la %s %s propiedad de la entidad identificada con %s %s, " +
                        "ubicada en la dirección %s, teléfono %s, el día %s.",
                tipoLugar,
                cita.getLugar().getNombreLugar(),
                solicitud.getTipoDocumento(),
                documento,
                cita.getLugar().getDireccionLugar(),
                usuario.getTelefonoUs(),
                fechaEmision
        );

        Paragraph certificacion = new Paragraph(textoCertificacion)
                .setFont(fontNormal)
                .setFontSize(11)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setMarginBottom(10);
        document.add(certificacion);

        if (cita.getObservacionesCita() != null && !cita.getObservacionesCita().isEmpty()) {
            Paragraph observaciones = new Paragraph(cita.getObservacionesCita())
                    .setFont(fontNormal)
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setItalic()
                    .setMarginBottom(10);
            document.add(observaciones);
        }

        Paragraph vigencia = new Paragraph(
                String.format("Se expide el %s y tiene validez hasta %s", fechaEmision, fechaVence)
        )
                .setFont(fontNormal)
                .setFontSize(11)
                .setMarginTop(10)
                .setMarginBottom(30);
        document.add(vigencia);

        // ==================== FIRMAS ====================

        Table tablaFirmas = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .useAllAvailableWidth()
                .setMarginTop(30);

        Cell firma1 = new Cell()
                .add(new Paragraph("María Isabel Cárdenas Restrepo").setFont(fontBold).setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("Gerente").setFont(fontNormal).setFontSize(9).setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("Tel: 4894219 / 316 570 17 21").setFont(fontNormal).setFontSize(8).setTextAlignment(TextAlignment.CENTER))
                .setBorder(Border.NO_BORDER);

        Cell firma2 = new Cell()
                .add(new Paragraph("Carlos Andrés Cárdenas Restrepo").setFont(fontBold).setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("Asesor técnico").setFont(fontNormal).setFontSize(9).setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("Cel: 301 437 8917").setFont(fontNormal).setFontSize(8).setTextAlignment(TextAlignment.CENTER))
                .setBorder(Border.NO_BORDER);

        tablaFirmas.addCell(firma1);
        tablaFirmas.addCell(firma2);
        document.add(tablaFirmas);

        document.close();

        return baos.toByteArray();
    }

    private void agregarFilaTabla(Table tabla, String etiqueta, String valor, PdfFont fontBold, PdfFont fontNormal) {
        Cell celdaEtiqueta = new Cell()
                .add(new Paragraph(etiqueta).setFont(fontBold))
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(new DeviceRgb(240, 240, 240))
                .setPadding(8);

        Cell celdaValor = new Cell()
                .add(new Paragraph(valor).setFont(fontNormal))
                .setBorder(Border.NO_BORDER)
                .setPadding(8);

        tabla.addCell(celdaEtiqueta);
        tabla.addCell(celdaValor);
    }


}
