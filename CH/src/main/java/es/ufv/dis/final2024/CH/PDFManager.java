package es.ufv.dis.final2024.CH;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.FileOutputStream;

public class PDFManager {

    public static void crearPDF(String path, Starship s) throws Exception {
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
