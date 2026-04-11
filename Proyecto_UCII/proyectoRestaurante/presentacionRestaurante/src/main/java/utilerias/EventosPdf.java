package utilerias;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.properties.TextAlignment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Image;

public class EventosPdf implements IEventHandler {

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfPage page = docEvent.getPage();
        int pageNumber = docEvent.getDocument().getPageNumber(page);

        PdfCanvas pdfCanvas = new PdfCanvas(page);
        Canvas canvas = new Canvas(pdfCanvas, page.getPageSize());

        try {
            try {
                String rutaLogo = "src\\main\\resources\\imagenes\\icono_restaurante.png";
                Image logo = new Image(ImageDataFactory.create(rutaLogo));
                logo.setWidth(60); // Ajusta el tamaño
                logo.setFixedPosition(36, 770);
                canvas.add(logo);
            } catch (Exception e) {
                System.err.println("No se pudo cargar el logo: " + e.getMessage());
            }

            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            canvas.setFont(font);
            canvas.setFontSize(9);

            String fechaStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            canvas.showTextAligned("Generado el: " + fechaStr, 550, 790, TextAlignment.RIGHT);

            canvas.showTextAligned("Página " + pageNumber, 297, 30, TextAlignment.CENTER);

        } catch (Exception e) {
            System.err.println("Error en eventos PDF: " + e.getMessage());
        } finally {
            canvas.close();
        }
    }
}
