package practise.items;

public class ItemsItems {
    String name, measurement, articul;
    int price;
    int taxes;

    int id;
    public ItemsItems(String name, String measurement, String articul, int price, int taxes, int id) {
        this.name = name;
        this.measurement = measurement;
        this.articul = articul;
        this.price = price;
        this.taxes = taxes;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getArticul() {
        return articul;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTaxes() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = taxes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
