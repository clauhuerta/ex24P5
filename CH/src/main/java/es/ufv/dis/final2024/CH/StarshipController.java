package es.ufv.dis.final2024.CH;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class StarshipController {

    private final StarshipService starshipService = new StarshipService();

    @GetMapping("/starships")
    public ResponseEntity<List<Starship>> getAllStarships() {
        try {
            List<Starship> ships = starshipService.getAllStarships();
            return ResponseEntity.ok(ships);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/starships/{name}")
    public ResponseEntity<Starship> getStarshipByName(@PathVariable String name) {
        try {
            Optional<Starship> ship = starshipService.getStarshipByName(name);
            return ship.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/starships")
    public ResponseEntity<String> generarPDF(@RequestBody RequestDTO request) {
        try {
            Optional<Starship> match = starshipService.getStarshipByName(request.getShip());
            if (!match.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ship not found");
            }
            starshipService.generarPdf(match.get());
            return ResponseEntity.ok("PDF generado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generando PDF: " + e.getMessage());
        }
    }
}
