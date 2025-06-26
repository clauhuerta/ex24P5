package es.ufv.dis.final2024.CH;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.DocumentException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class StarshipService {

    private final Gson gson = new Gson();
    private final String PETICIONES_PATH = "peticiones/peticiones.json";

    // Lee el JSON desde src/main/resources/data.json
    public List<Starship> getAllStarships() throws Exception {
        try (
                InputStream is = getClass().getClassLoader().getResourceAsStream("data.json");
                InputStreamReader reader = new InputStreamReader(is)
        ) {
            Type listType = new TypeToken<List<Starship>>(){}.getType();
            return gson.fromJson(reader, listType);
        }
    }

    public Optional<Starship> getStarshipByName(String name) throws Exception {
        List<Starship> ships = getAllStarships();
        return ships.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Genera PDF en carpeta /naves y actualiza el histórico en /peticiones
    public void generarPdf(Starship starship) throws DocumentException, IOException {
        File navesFolder = new File("naves");
        if (!navesFolder.exists()) navesFolder.mkdirs();
        String pdfPath = "naves/" + starship.getName().replace(" ", "_") + ".pdf";
        PDFManager.crearPDF(pdfPath, starship);

        actualizarHistoricoPeticiones(starship.getName());
    }

    private void actualizarHistoricoPeticiones(String shipName) throws IOException {
        File peticionesFolder = new File("peticiones");
        if (!peticionesFolder.exists()) peticionesFolder.mkdirs();

        List<PeticionHistorico> historico = new ArrayList<>();
        File peticionesFile = new File(PETICIONES_PATH);

        if (peticionesFile.exists()) {
            try (Reader reader = new FileReader(PETICIONES_PATH)) {
                Type listType = new TypeToken<List<PeticionHistorico>>(){}.getType();
                historico = gson.fromJson(reader, listType);
                if (historico == null) historico = new ArrayList<>();
            }
        }

        boolean found = false;
        for (PeticionHistorico h : historico) {
            if (h.getShipName().equalsIgnoreCase(shipName)) {
                h.setCount(h.getCount() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            historico.add(new PeticionHistorico(shipName, 1));
        }

        try (Writer writer = new FileWriter(PETICIONES_PATH, false)) {
            gson.toJson(historico, writer);
        }
    }
}
