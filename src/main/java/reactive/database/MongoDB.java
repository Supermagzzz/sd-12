package reactive.database;

import com.mongodb.rx.client.*;
import org.springframework.stereotype.Component;
import reactive.model.Product;
import reactive.model.User;
import rx.Observable;
import static com.mongodb.client.model.Filters.eq;

@Component
public class MongoDB implements Database {
    private final MongoClient client = MongoClients.create("mongodb://localhost:27017");
    private final MongoDatabase database;

    public MongoDB() {
        database = client.getDatabase("app");
    }

    public Observable<Success> addProduct(Product product) {
        return database.getCollection("product").insertOne(product.toDocument());
    }

    public Observable<Success> addUser(User user) {
        return database.getCollection("user").insertOne(user.toDocument());
    }

    public Observable<User> getUser(int id) {
        return database.getCollection("user").find(eq("id", id)).toObservable().map(User::new);
    }

    public Observable<Product> getProducts() {
        return database.getCollection("product").find().toObservable().map(Product::new);
    }
}
