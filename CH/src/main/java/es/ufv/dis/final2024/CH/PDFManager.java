package es.ufv.dis.final2024.CH;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFManager {

    public static void crearPDF(String path, Starship s) throws DocumentException, IOException {
        Document doc = new Document(PageSize.A4, 50, 50, 100, 72);
        PdfWriter.getInstance(doc, new FileOutputStream(path));
        doc.open();

        doc.add(new Paragraph("Nombre: " + s.getName()));
        doc.add(new Paragraph("Modelo: " + s.getModel()));
        doc.add(new Paragraph("Clase de nave: " + s.getStarship_class()));
        doc.add(new Paragraph("Tripulación: " + s.getCrew()));
        doc.add(new Paragraph("Número de películas: " + (s.getFilms() != null ? s.getFilms().size() : 0)));

        doc.close();
    }
}
