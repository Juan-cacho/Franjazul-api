package com.franjazul.api.controller;

import com.franjazul.api.dto.*;
import com.franjazul.api.services.ReportesService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportesController {

    @Autowired
    private ReportesService reportesService;


    @GetMapping("/citas-por-periodo")
    public ResponseEntity<Map<String, Object>> obtenerReporteCitasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String idTecnico) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<ReporteCitaPorPeriodoDTO> reporte = reportesService.obtenerReporteCitasPorPeriodo(
                    fechaInicio, fechaFin, estado, idTecnico);

            response.put("success", true);
            response.put("data", reporte);
            response.put("total", reporte.size());
            response.put("message", "Reporte generado exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al generar reporte: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/productividad-tecnicos")
    public ResponseEntity<Map<String, Object>> obtenerReporteProductividadTecnicos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) String idTecnico) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<ReporteProductividadTecnicoDTO> reporte = reportesService.obtenerReporteProductividadTecnicos(
                    fechaInicio, fechaFin, idTecnico);

            response.put("success", true);
            response.put("data", reporte);
            response.put("total", reporte.size());
            response.put("message", "Reporte generado exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al generar reporte: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/servicios-solicitados")
    public ResponseEntity<Map<String, Object>> obtenerReporteServiciosSolicitados(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) String tipoServicio) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<ReporteServiciosSolicitadosDTO> reporte = reportesService.obtenerReporteServiciosSolicitados(
                    fechaInicio, fechaFin, tipoServicio);

            response.put("success", true);
            response.put("data", reporte);
            response.put("total", reporte.size());
            response.put("message", "Reporte generado exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al generar reporte: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== ENDPOINTS PARA DESCARGA EXCEL ====================

    @GetMapping("/citas-por-periodo/excel")
    public ResponseEntity<InputStreamResource> descargarCitasExcel(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String idTecnico) {

        try {
            List<ReporteCitaPorPeriodoDTO> datos = reportesService.obtenerReporteCitasPorPeriodo(
                    fechaInicio, fechaFin, estado, idTecnico);

            ByteArrayInputStream excel = generarExcelCitas(datos);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=reporte_citas_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(excel));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/productividad-tecnicos/excel")
    public ResponseEntity<InputStreamResource> descargarProductividadExcel(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) String idTecnico) {

        try {
            List<ReporteProductividadTecnicoDTO> datos = reportesService.obtenerReporteProductividadTecnicos(
                    fechaInicio, fechaFin, idTecnico);

            ByteArrayInputStream excel = generarExcelProductividad(datos);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=reporte_productividad_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(excel));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/servicios-solicitados/excel")
    public ResponseEntity<InputStreamResource> descargarServiciosExcel(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) String tipoServicio) {

        try {
            List<ReporteServiciosSolicitadosDTO> datos = reportesService.obtenerReporteServiciosSolicitados(
                    fechaInicio, fechaFin, tipoServicio);

            ByteArrayInputStream excel = generarExcelServicios(datos);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=reporte_servicios_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(excel));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // ==================== MÉTODOS AUXILIARES PARA GENERAR EXCEL ====================


    private ByteArrayInputStream generarExcelCitas(List<ReporteCitaPorPeriodoDTO> datos) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Citas");

            // Estilo para encabezados
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            String[] columnas = {"ID", "Fecha Inicio", "Fecha Fin", "Cliente", "Email", "Técnico",
                    "Lugar", "Dirección", "Servicios", "Estado", "Observaciones"};

            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            // Agregar datos
            int rowNum = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (ReporteCitaPorPeriodoDTO dato : datos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dato.getIdCita());
                row.createCell(1).setCellValue(dato.getFechaInicio() != null ? dato.getFechaInicio().format(formatter) : "");
                row.createCell(2).setCellValue(dato.getFechaFin() != null ? dato.getFechaFin().format(formatter) : "");
                row.createCell(3).setCellValue(dato.getCliente());
                row.createCell(4).setCellValue(dato.getEmailCliente());
                row.createCell(5).setCellValue(dato.getTecnico());
                row.createCell(6).setCellValue(dato.getLugar());
                row.createCell(7).setCellValue(dato.getDireccion());
                row.createCell(8).setCellValue(dato.getServicios());
                row.createCell(9).setCellValue(dato.getEstado());
                row.createCell(10).setCellValue(dato.getObservaciones());
            }

            // Ajustar ancho de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private ByteArrayInputStream generarExcelProductividad(List<ReporteProductividadTecnicoDTO> datos) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Productividad");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(0);
            String[] columnas = {"ID", "Técnico", "Email", "Teléfono", "Total Citas", "Completadas",
                    "Pendientes", "Canceladas", "Reagendadas", "% Efectividad", "% Cancelación"};

            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (ReporteProductividadTecnicoDTO dato : datos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dato.getIdTecnico());
                row.createCell(1).setCellValue(dato.getNombreCompleto());
                row.createCell(2).setCellValue(dato.getEmail());
                row.createCell(3).setCellValue(dato.getTelefono());
                row.createCell(4).setCellValue(dato.getTotalCitasAsignadas());
                row.createCell(5).setCellValue(dato.getCitasCompletadas());
                row.createCell(6).setCellValue(dato.getCitasPendientes());
                row.createCell(7).setCellValue(dato.getCitasCanceladas());
                row.createCell(8).setCellValue(dato.getCitasReagendadas());
                row.createCell(9).setCellValue(dato.getPorcentajeEfectividad() + "%");
                row.createCell(10).setCellValue(dato.getPorcentajeCancelacion() + "%");
            }

            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private ByteArrayInputStream generarExcelServicios(List<ReporteServiciosSolicitadosDTO> datos) throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Servicios");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(0);
            String[] columnas = {"ID", "Servicio", "Descripción", "Tipo", "Molécula", "Veces Solicitado",
                    "Cantidad Total", "Completadas", "Pendientes", "% Completado"};

            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (ReporteServiciosSolicitadosDTO dato : datos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dato.getIdServicio());
                row.createCell(1).setCellValue(dato.getNombreServicio());
                row.createCell(2).setCellValue(dato.getDescripcion());
                row.createCell(3).setCellValue(dato.getTipoServicio());
                row.createCell(4).setCellValue(dato.getMolecula());
                row.createCell(5).setCellValue(dato.getVecesSolicitado());
                row.createCell(6).setCellValue(dato.getCantidadTotal());
                row.createCell(7).setCellValue(dato.getCitasCompletadas());
                row.createCell(8).setCellValue(dato.getCitasPendientes());
                row.createCell(9).setCellValue(dato.getPorcentajeCompletado() + "%");
            }

            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

}
