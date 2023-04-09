import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactive.database.InMemoryDB;
import reactive.logic.Logic;
import reactive.model.Currency;
import reactive.model.Product;
import reactive.model.User;
import reactive.database.MongoDB;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestApp {

    private Logic logic;

    @Before
    public void eachTest() {
        logic = new Logic(new InMemoryDB());
    }

    public void checkProducts(List<Product> a, List<Product> b) {
        assertTrue(a.containsAll(b) && b.containsAll(a));
    }

    @org.junit.Test
    public void checkSameCurrency() {
        logic.addUser(new User(1, Currency.RUB));
        logic.addProduct(new Product("apple", 10, Currency.RUB));
        checkProducts(
                logic.getProducts(1).toList().toBlocking().single(),
                List.of(new Product("apple", 10, Currency.RUB))
        );
    }

    @org.junit.Test
    public void checkAnotherCurrency() {
        logic.addUser(new User(1, Currency.USD));
        logic.addProduct(new Product("apple", 10, Currency.RUB));
        checkProducts(
                logic.getProducts(1).toList().toBlocking().single(),
                List.of(new Product("apple", 1, Currency.USD))
        );
    }

    @org.junit.Test
    public void checkManyUsers() {
        logic.addUser(new User(1, Currency.USD));
        logic.addUser(new User(2, Currency.RUB));
        logic.addProduct(new Product("apple", 10, Currency.RUB));
        logic.addProduct(new Product("banana", 10, Currency.USD));
        checkProducts(
                logic.getProducts(1).toList().toBlocking().single(),
                List.of(new Product("apple", 1, Currency.USD), new Product("banana", 10, Currency.USD))
        );
        checkProducts(
                logic.getProducts(2).toList().toBlocking().single(),
                List.of(new Product("apple", 10, Currency.RUB), new Product("banana", 100, Currency.RUB))
        );
    }
}
