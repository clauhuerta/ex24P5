package es.ufv.dis.final2024.CH;

public class PeticionHistorico {
    private String shipName;
    private int count;

    public PeticionHistorico() {}

    public PeticionHistorico(String shipName, int count) {
        this.shipName = shipName;
        this.count = count;
    }

    public String getShipName() { return shipName; }
    public void setShipName(String shipName) { this.shipName = shipName; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
