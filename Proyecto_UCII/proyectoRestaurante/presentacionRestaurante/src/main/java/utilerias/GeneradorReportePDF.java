package utilerias;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import dtos.ReporteComandaDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class GeneradorReportePDF {

    public void crearPdfComandas(List<ReporteComandaDTO> lista, String destino, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        PdfWriter writer = new PdfWriter(destino);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new EventosPdf());

        Document documento = new Document(pdf);
        documento.setMargins(85, 36, 60, 36);

        PdfFont fontNormal = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        documento.add(new Paragraph("REPORTE DE COMANDAS")
                .setFont(fontBold).setFontSize(18).setTextAlignment(TextAlignment.CENTER));

        double totalAcumulado = 0;
        for (ReporteComandaDTO r : lista) {
            totalAcumulado += r.getTotal();
        }

        String rangoFechas = (fechaInicio != null && fechaFin != null)
                ? "Periodo: " + fechaInicio.format(formatter) + " al " + fechaFin.format(formatter)
                : "Periodo: Histórico General";

        documento.add(new Paragraph(rangoFechas)
                .setFont(fontNormal).setFontSize(11).setTextAlignment(TextAlignment.CENTER));

        documento.add(new Paragraph("Venta Total Acumulada: $" + String.format("%.2f", totalAcumulado))
                .setFont(fontBold).setFontSize(12).setTextAlignment(TextAlignment.RIGHT).setMarginBottom(10));

        Table tabla = new Table(new float[]{15, 15, 10, 15, 15, 30}).useAllAvailableWidth();

        String[] headers = {"Folio", "Fecha", "Mesa", "Estado", "Total", "Cliente"};
        for (String h : headers) {
            tabla.addHeaderCell(new Cell().add(new Paragraph(h).setFont(fontBold).setFontSize(11))
                    .setTextAlignment(TextAlignment.CENTER));
        }

        for (ReporteComandaDTO reporte : lista) {
            tabla.addCell(new Cell().add(new Paragraph(reporte.getFolio() != null ? reporte.getFolio() : "-").setFont(fontNormal).setFontSize(10)));

            String f = (reporte.getFecha() != null) ? reporte.getFecha().format(formatter) : "-";
            tabla.addCell(new Cell().add(new Paragraph(f).setFont(fontNormal).setFontSize(10)));

            tabla.addCell(new Cell().add(new Paragraph(String.valueOf(reporte.getNumeroMesa())).setFont(fontNormal).setFontSize(10)).setTextAlignment(TextAlignment.CENTER));
            tabla.addCell(new Cell().add(new Paragraph(String.valueOf(reporte.getEstado())).setFont(fontNormal).setFontSize(10)));
            tabla.addCell(new Cell().add(new Paragraph("$" + String.format("%.2f", reporte.getTotal())).setFont(fontNormal).setFontSize(10)).setTextAlignment(TextAlignment.RIGHT));

            String cliente = (reporte.getNombreCliente() != null && !reporte.getNombreCliente().isEmpty()) ? reporte.getNombreCliente() : "Cliente general";
            tabla.addCell(new Cell().add(new Paragraph(cliente).setFont(fontNormal).setFontSize(10)));
        }

        tabla.addCell(new Cell(1, 4).add(new Paragraph("TOTAL GENERAL:").setFont(fontBold))
                .setTextAlignment(TextAlignment.RIGHT));
        tabla.addCell(new Cell().add(new Paragraph("$" + String.format("%.2f", totalAcumulado)).setFont(fontBold))
                .setTextAlignment(TextAlignment.RIGHT));
        tabla.addCell(new Cell()); //celda vacía para completar la fila

        documento.add(tabla);
        documento.flush();
        documento.close();
    }
}
