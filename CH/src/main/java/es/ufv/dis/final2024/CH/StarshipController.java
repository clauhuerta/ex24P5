package es.ufv.dis.final2024.CH;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin // Por si el front está en otro puerto
public class StarshipController {

    private final String DATA_PATH = "data.json";
    private final String PETICIONES_PATH = "peticiones/peticiones.json";
    private final String NAVES_DIR = "naves/";

    private final Gson gson = new Gson();

    // GET devuelve todas las naves
    @GetMapping("/starships")
    public ResponseEntity<List<Starship>> getAllStarships() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(DATA_PATH));
            Type listType = new TypeToken<List<Starship>>(){}.getType();
            List<Starship> ships = gson.fromJson(reader, listType);
            reader.close();
            return ResponseEntity.ok(ships);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST genera PDF y actualiza peticiones
    @PostMapping("/starships")
    public ResponseEntity<String> generarPDF(@RequestBody RequestDTO request) {
        try {
            // 1. Buscar la nave
            Reader reader = Files.newBufferedReader(Paths.get(DATA_PATH));
            Type listType = new TypeToken<List<Starship>>(){}.getType();
            List<Starship> ships = gson.fromJson(reader, listType);
            reader.close();

            Optional<Starship> match = ships.stream()
                    .filter(s -> s.getName().equalsIgnoreCase(request.getShip()))
                    .findFirst();

            if (!match.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ship not found");
            }

            Starship starship = match.get();

            // 2. Generar PDF en carpeta /naves
            File navesFolder = new File(NAVES_DIR);
            if (!navesFolder.exists()) navesFolder.mkdirs();

            String pdfPath = NAVES_DIR + starship.getName().replace(" ", "_") + ".pdf";
            PDFManager.crearPDF(pdfPath, starship);

            // 3. Actualizar fichero de peticiones
            File petFolder = new File("peticiones");
            if (!petFolder.exists()) petFolder.mkdirs();

            List<PeticionHistorico> historico = new ArrayList<>();
            File peticionesFile = new File(PETICIONES_PATH);
            if (peticionesFile.exists()) {
                Reader petReader = Files.newBufferedReader(Paths.get(PETICIONES_PATH));
                Type histType = new TypeToken<List<PeticionHistorico>>(){}.getType();
                historico = gson.fromJson(petReader, histType);
                if (historico == null) historico = new ArrayList<>();
                petReader.close();
            }

            boolean found = false;
            for (PeticionHistorico ph : historico) {
                if (ph.getShipName().equalsIgnoreCase(starship.getName())) {
                    ph.setCount(ph.getCount() + 1);
                    found = true;
                    break;
                }
            }
            if (!found) historico.add(new PeticionHistorico(starship.getName(), 1));

            Writer writer = new FileWriter(PETICIONES_PATH, false);
            gson.toJson(historico, writer);
            writer.close();

            return ResponseEntity.ok("PDF generado y petición registrada");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
