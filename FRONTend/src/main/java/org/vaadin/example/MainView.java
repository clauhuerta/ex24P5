package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private final String backendUrl = "http://localhost:8083/starships";

    public MainView() {
        setSizeFull();

        Grid<Starship> grid = new Grid<>(Starship.class, false);

        // Definir columnas con nombres amigables
        grid.addColumn(Starship::getName).setHeader("Nombre");
        grid.addColumn(Starship::getModel).setHeader("Modelo");
        grid.addColumn(Starship::getCost_in_credits).setHeader("Coste en créditos");
        grid.addColumn(Starship::getCrew).setHeader("Tripulación");
        grid.addColumn(Starship::getCargo_capacity).setHeader("Capacidad de carga");
        grid.addColumn(Starship::getConsumables).setHeader("Consumibles");
        grid.addColumn(Starship::getHyperdrive_rating).setHeader("Hyperdrive");
        grid.addColumn(Starship::getStarship_class).setHeader("Clase de nave");
        grid.addColumn(starship -> starship.getPilots() != null ? starship.getPilots().size() : 0)
                .setHeader("Nº de pilotos");
        grid.addColumn(starship -> starship.getFilms() != null ? starship.getFilms().size() : 0)
                .setHeader("Nº de películas");

        // Columna de botón "Generar"
        grid.addComponentColumn(starship -> {
            Button btn = new Button("Generar");
            btn.addClickListener(event -> {
                generarPDF(starship.getName());
            });
            return btn;
        }).setHeader("Generar PDF");

        // Llenar el grid al iniciar
        cargarDatos(grid);

        add(grid);
    }

    private void cargarDatos(Grid<Starship> grid) {
        try {
            URL url = new URL(backendUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            if (con.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8);
                Type listType = new TypeToken<List<Starship>>(){}.getType();
                List<Starship> starships = new Gson().fromJson(reader, listType);
                grid.setItems(starships);
                reader.close();
            } else {
                Notification.show("Error al cargar datos del backend", 5000, Notification.Position.MIDDLE);
            }

            con.disconnect();
        } catch (IOException e) {
            Notification.show("Error de conexión con backend", 5000, Notification.Position.MIDDLE);
        }
    }

    private void generarPDF(String nombreNave) {
        try {
            URL url = new URL(backendUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInputString = "{\"ship\":\"" + nombreNave.replace("\"", "\\\"") + "\"}";
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            if (code == 200) {
                Notification.show("PDF generado correctamente", 3000, Notification.Position.MIDDLE);
            } else if (code == 404) {
                Notification.show("Nave no encontrada", 5000, Notification.Position.MIDDLE);
            } else {
                Notification.show("Error en la petición al backend", 5000, Notification.Position.MIDDLE);
            }

            con.disconnect();
        } catch (IOException e) {
            Notification.show("Error de conexión al generar PDF", 5000, Notification.Position.MIDDLE);
        }
    }
}
