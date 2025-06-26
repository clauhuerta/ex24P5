package es.ufv.dis.final2024.CH;

public class RequestDTO {
    private String ship;

    public RequestDTO() {}

    public RequestDTO(String ship) {
        this.ship = ship;
    }

    public String getShip() { return ship; }
    public void setShip(String ship) { this.ship = ship; }
}
