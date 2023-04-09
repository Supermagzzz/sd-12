package reactive;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import reactive.database.MongoDB;
import reactive.logic.Logic;
import reactive.model.Product;
import reactive.model.User;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class Server {

    private static final Logic logic = new Logic(new MongoDB());

    public static void main(final String[] args) {
        HttpServer.newServer(9090).start((req, resp) -> {
            Map<String, List<String>> queryParameters = req.getQueryParameters();
            String command = req.getDecodedPath().substring(1);

            Observable<String> response;
            switch (command) {
                case "add_product":
                    Product product = new Product(queryParameters);
                    response = logic.addProduct(product).map(Enum::toString);
                    break;
                case "add_user":
                    User user = new User(queryParameters);
                    response = logic.addUser(user).map(Enum::toString);
                    break;
                case "get_products":
                    int id = Integer.parseInt(queryParameters.get("user_id").get(0));
                    response = logic.getProducts(id).map(x -> x.toString() + "\n");
                    break;
                default:
                    response = Observable.just("404");
                    resp.setStatus(HttpResponseStatus.NOT_FOUND);
            }
            return resp.writeString(response);
        }).awaitShutdown();
    }
}
