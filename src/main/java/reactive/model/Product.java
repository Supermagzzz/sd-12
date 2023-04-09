package reactive.model;

import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Product {
    private final String name;
    private final double price;
    private final Currency currency;

    public Product(String name, double price, Currency currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public Product(Map<String, List<String>> queryParameters) {
        this(
                queryParameters.get("name").get(0),
                Double.parseDouble(queryParameters.get("price").get(0)),
                Currency.valueOf(queryParameters.get("currency").get(0))
        );
    }

    public Product(Document document) {
        this(
                document.getString("name"),
                Double.parseDouble(document.getString("price")),
                Currency.valueOf(document.getString("currency"))
        );
    }

    public Document toDocument() {
        return new Document()
                .append("name", name)
                .append("price", price)
                .append("currency", currency);
    }

    public Product convertPrice(Currency currency) {
        return new Product(name, this.currency.getValue() / currency.getValue() * price, currency);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name) && currency == product.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, currency);
    }
}
