package me.elordenador.practica6.http;

import com.sun.net.httpserver.HttpServer;
import me.elordenador.practica6.Dispositivo;
import me.elordenador.practica6.Ordenador;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HResponder {
    private static HttpServer server;
    public static void main(String[] args) throws IOException {
        Dispositivo.init();
        Ordenador.init();
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        createContext();

        server.setExecutor(null);
        server.start();
    }

    private static void createContext() {
        server.createContext("/", new RootHandler());
        server.createContext("/dispositivos", new DispositivosHandler());
        server.createContext("/api/v1/getDispositivos", new DispositivosHandler());
        server.createContext("/api/v1/getOrdenadores", new DispositivosHandler());
    }
}
