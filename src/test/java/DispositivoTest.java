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
    @BeforeAll
    public static void setupFile() {
        System.out.println("> Initializing File... (dispositivos.dat)");
        Dispositivo.init();
    }


    @Order(1)
    @Test
    public void generateData() {
        System.out.println("> Starting to save all data...");
        for (String marca : marcas) {
            for (String modelo : modelos) {
                Dispositivo dispositivo = new Dispositivo(marca, modelo, true);
                dispositivo.save();

                modeloTArrayList.add(new ModeloT(dispositivo.getId(), dispositivo.getMarca(), dispositivo.getModelo()));
            }
        }
        System.out.println("> Data saved to device and all models written saved to a list for the third test.");
    }

    @Order(2)
    @Test
    public void readData() {
        System.out.println("> Reading data...");
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
        System.out.println("> Data readed successfully");
    }
    @Order(3)
    @Test
    public void checkData() throws ElementNotFoundException {
        System.out.println("> Veryfing Data...");
        int elementos = modeloTArrayList.size();
        for (int i = 0; i < elementos; i++) {
            int id = i + 1;
            Dispositivo modeloGenerado = new Dispositivo(id);
            ModeloT modelo = modeloTArrayList.get(i);

            // Conseguir datos
            modeloGenerado.load();

            assertEquals(modelo.marca, modeloGenerado.getMarca(), "The Device brand (" + modeloGenerado.getMarca() + ") isn't the same as the one we generated earlier (" + modelo.marca + ").");
            assertEquals(modelo.modelo, modeloGenerado.getModelo(), "The Device model (" + modeloGenerado.getModelo() + ") isn't the same as the one we generated earlier ("+modelo.modelo+").");

        }
        System.out.println("> Data Verified");
    }

    @AfterAll
    public static void finish() {
        System.out.println("SUCCESS");
        System.out.println("Cleaning up...");
        File file = new File("dispositivos.dat");
        file.delete();
    }
}
