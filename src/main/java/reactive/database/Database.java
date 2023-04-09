package reactive.database;

import com.mongodb.rx.client.Success;
import reactive.model.Product;
import reactive.model.User;
import rx.Observable;

public interface Database {
    Observable<Success> addProduct(Product product);
    Observable<Success> addUser(User user);
    Observable<User> getUser(int id);
    Observable<Product> getProducts();
}
