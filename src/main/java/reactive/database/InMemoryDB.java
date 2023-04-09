package reactive.database;

import com.mongodb.rx.client.Success;
import reactive.model.Product;
import reactive.model.User;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDB implements Database {

    private final List<User> users = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();

    @Override
    public Observable<Success> addProduct(Product product) {
        products.add(product);
        return Observable.just(Success.SUCCESS);
    }

    @Override
    public Observable<Success> addUser(User user) {
        users.add(user);
        return Observable.just(Success.SUCCESS);
    }

    @Override
    public Observable<User> getUser(int id) {
        return Observable.just(users.stream().filter(u -> u.id == id).findFirst().get());
    }

    @Override
    public Observable<Product> getProducts() {
        return Observable.from(products);
    }
}
