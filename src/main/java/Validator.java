import com.google.gson.*;
import com.sun.net.httpserver.*;
import java.io.*;

public class Validator implements  HttpHandler{

    public void handle(HttpExchange exchange) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String str1 = reader.readLine();
        StringBuilder str_build =  new StringBuilder();

        while(str1 != null) {
            str_build.append(str1);
            str1 = reader.readLine();
        }

        String str2 = str_build.toString();

        GsonBuilder gson_build = new GsonBuilder();
        gson_build.setPrettyPrinting().serializeNulls();
        Gson gson = gson_build.create();
        String str3 = null;

        try {
            Object object = gson.fromJson(str2, Object.class);
            str3 = gson.toJson(object);
        } catch (JsonSyntaxException ex) {
            str3 = ex.getMessage();
        }

        System.out.println(str3);
        exchange.sendResponseHeaders(200, str3.length());
        OutputStream out = exchange.getResponseBody();
        out.write(str3.getBytes());
        out.close();
    }

}
