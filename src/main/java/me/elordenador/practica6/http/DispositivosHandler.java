package me.elordenador.practica6.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class DispositivosHandler implements HttpHandler {
    private String response;
    public void handle(HttpExchange ex) throws IOException {
        String path = ex.getHttpContext().getPath();
        ex.sendResponseHeaders(200, path.length());
        OutputStream out = ex.getResponseBody();
        out.write(path.getBytes());
        out.close();
    }

    public void generateHTMLRoot() {

    }
}
