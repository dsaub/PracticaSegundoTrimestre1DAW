import me.elordenador.practica6.Dispositivo;
import me.elordenador.practica6.ElementNotFoundException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DispositivoTest {
    private class ModeloT {
        public int id;
        public String marca, modelo;

        public ModeloT(int id, String marca, String modelo) {
            this.id = id;
            this.marca = marca;
            this.modelo = modelo;
        }
    }

    private ArrayList<ModeloT> modeloTArrayList = new ArrayList<ModeloT>();


    private String[] marcas = {"LG", "Samsung", "HP", "Lenovo", "Dell", "Asus", "Acer"};
    private String[] modelos = {"Modelo1","Modelo2","Modelo3","Modelo4","Modelo5","Modelo6"};

    /*
    @BeforeAll
    public static void begin() {
        Dispositivo.init();
    }
    */

    @Order(1)
    @Test
    public void testInitialize() {
        Dispositivo.cleanup();
        Dispositivo.init();
    }
    @Order(2)
    @Test
    public void testGenerateData() {
        for (String marca : marcas) {
            for (String modelo : modelos) {
                Dispositivo dispositivo = new Dispositivo(marca, modelo, true);
                dispositivo.save();

                modeloTArrayList.add(new ModeloT(dispositivo.getId(), dispositivo.getMarca(), dispositivo.getModelo()));
            }
        }

    }

    @Order(3)
    @Test
    public void testReadData() {
        int position = 0;
        boolean salida = false;
        while (!salida) {
            Dispositivo dispositivo = new Dispositivo(position);
            try {
                dispositivo.load();

            } catch (ElementNotFoundException e) {
                if (!e.logicaldelete) {
                    salida = true;
                }
            }
            position++;


        }
    }
    @Order(4)
    @Test
    public void testCheckData() throws ElementNotFoundException {
        int elementos = modeloTArrayList.size();
        for (int i = 0; i < elementos; i++) {
            Dispositivo modeloGenerado = new Dispositivo(i);
            ModeloT modelo = modeloTArrayList.get(i);

            // Conseguir datos
            modeloGenerado.load();

            assertEquals(modelo.marca, modeloGenerado.getMarca(), "The Device brand (" + modeloGenerado.getMarca() + ") isn't the same as the one we generated earlier (" + modelo.marca + ").");
            assertEquals(modelo.modelo, modeloGenerado.getModelo(), "The Device model (" + modeloGenerado.getModelo() + ") isn't the same as the one we generated earlier ("+modelo.modelo+").");

        }
    }

    @Order(5)
    @Test
    public void testDeleteData() {
        int elementos = modeloTArrayList.size();
        for (int i = 0; i < elementos; i++) {
            int id = i + 1;
            Dispositivo dispositivo = new Dispositivo(id);
            dispositivo.delete();
        }
    }

    @Order(6)
    @Test
    public void testCheckDataDoesNoLongerExists() {
        int elementos = modeloTArrayList.size();
        for (int i = 0; i < elementos; i++) {
            int id = i + 1;
            Dispositivo modeloGenerado = new Dispositivo(id);
            ModeloT modelo = modeloTArrayList.get(i);
            boolean failed = false;

            // Conseguir datos
            try {
                modeloGenerado.load();
            } catch (ElementNotFoundException e) {
                failed = true;
                assertEquals(true, e.logicaldelete, "SO data doesn't exists phisically.");
            }

                assertEquals(true, failed, "No errors found, but we expect those errors. Test Failed");

        }
    }

    @AfterAll
    public static void finish() {
        File file = new File("dispositivos.dat");
        file.delete();
    }
}
