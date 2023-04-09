package reactive.model;

import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class User {
    public final int id;
    private Currency currency;

    public User(int id, Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    public User(Map<String, List<String>> queryParameters) {
        this(
                Integer.parseInt(queryParameters.get("id").get(0)),
                Currency.valueOf(queryParameters.get("currency").get(0))
        );
    }

    public User(Document document) {
        this(
                document.getInteger("id"),
                Currency.valueOf(document.getString("currency"))
        );
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("currency", currency);
    }

    public int getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && currency == user.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currency);
    }
}
