import com.google.gson.*;
import com.sun.net.httpserver.*;
import java.io.*;

public class Validator implements  HttpHandler {

    private int id = 0;

    public void handle(HttpExchange exchange) throws IOException {

        id++;

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
            JsonObject jObj = new JsonObject();
            String description = ex.getMessage().split(": ")[1];
            String message = description.split(" at ")[0];
            String occur = description.split(" at ")[1];
            int code = ex.hashCode();
            jObj.addProperty("id", id);
            jObj.addProperty("message", message);
            jObj.addProperty("occur", occur);
            jObj.addProperty("code", code);
            str3 = gson.toJson(jObj);
        }

        System.out.println(str3);
        exchange.sendResponseHeaders(200, str3.length());
        OutputStream out = exchange.getResponseBody();
        out.write(str3.getBytes());
        out.close();
    }

}
