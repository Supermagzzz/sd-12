package reactive.logic;

import com.mongodb.rx.client.Success;
import reactive.database.Database;
import reactive.model.Product;
import reactive.model.User;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class Logic {

    private final Database database;

    public Logic(Database database) {
        this.database = database;
    }

    public Observable<Success> addProduct(Product product) {
        return database.addProduct(product);
    }

    public Observable<Success> addUser(User user) {
        return database.addUser(user);
    }

    public Observable<Product> getProducts(int id) {
        return database.getUser(id)
                .flatMap(user -> database.getProducts().map(product -> product.convertPrice(user.getCurrency())));
    }
}
