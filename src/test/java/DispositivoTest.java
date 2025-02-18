import me.elordenador.practica6.Dispositivo;
import me.elordenador.practica6.ElementNotFoundException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
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
    public void testGenerateData() throws ElementNotFoundException {
        ArrayList<ModeloT> modeloTArrayList = new ArrayList<ModeloT>();
        Dispositivo.cleanup();
        Dispositivo.init();
        for (String marca : marcas) {
            for (String modelo : modelos) {
                Dispositivo dispositivo = new Dispositivo(marca, modelo, true);
                dispositivo.save();

                modeloTArrayList.add(new ModeloT(dispositivo.getId(), dispositivo.getMarca(), dispositivo.getModelo()));
            }
        }

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
    public void testDeleteData() throws IOException {
        Dispositivo.cleanup();
        Dispositivo.init();
        Dispositivo disp = new Dispositivo("Model1", "Model2", true);
        disp.save();
        disp.delete();
        try {
            disp.load();
        } catch (ElementNotFoundException e) {
            assertEquals(true, e.logicaldelete);
        }
    }
}
