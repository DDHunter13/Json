import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;


public class Main {

    public static void main(String[] args) {

        try {
            HttpServer serv = HttpServer.create();
            serv.bind(new InetSocketAddress(8000), 0);
            serv.createContext("/", new Validator());
            serv.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}