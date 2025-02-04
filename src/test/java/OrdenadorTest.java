import me.elordenador.practica6.Disco;
import me.elordenador.practica6.ElementNotFoundException;
import me.elordenador.practica6.Ordenador;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdenadorTest {

    private String[] marcas = {"HP", "Lenovo", "Acer", "Asus", "Honor", "Dell", "MSI", "Samsung", "Huawei"};
    private String[] modelos = {"modelo1", "modelo2", "modelo3", "modelo4", "modelo5", "modelo6", "modelo7", "modelo8", "modelo9", "modelo10"};
    private boolean[] estados = {true, false};
    private int[] rams = {1, 2, 4, 8, 16, 32, 64, 128};
    private String[] procesadores = {"Intel Core i3-12300", "Intel Core i3-12300K", "Intel Core i5-12500", "Intel Core i5-12500K", "Intel Core i7-12700", "Intel Core i7-12700K", "Intel Core i9-12900", "Intel Core i9-12900K"};
    private int[] tamDiscos = {16, 32, 64, 128, 256, 512, 1024, 2048, 4096};
    private Disco[] tipoDiscos = {Disco.HDD, Disco.SSD_SATA, Disco.SSD_NVME};
    /*
    @BeforeAll
    public static void beforeAll() {
        Ordenador.init();
    }
    */

    private class SaveOrdenador {
        public String marca, modelo, procesador;
        public boolean estado;
        public int ram, tamDisco;
        public Disco tipoDisco;
    }



    @Test
    @DisplayName("Add and verify data")
    public void testAddData() throws ElementNotFoundException {
        Ordenador.cleanup();
        Ordenador.init();
        ArrayList<SaveOrdenador> ordenadoresGuardados = new ArrayList<>();
        for (String marca : marcas) {
            for (String modelo : modelos) {
                for (boolean estado : estados) {
                    for (int ram : rams) {
                        for (String procesador : procesadores) {
                            for (int tamDisco : tamDiscos) {
                                for (Disco disco : tipoDiscos) {
                                    Ordenador ordenador = new Ordenador(marca, modelo, estado, ram, procesador, tamDisco, disco);
                                    ordenador.save();
                                    SaveOrdenador ordenador1 = new SaveOrdenador();
                                    ordenador1.marca = ordenador.getMarca();
                                    ordenador1.modelo = ordenador.getModelo();
                                    ordenador1.estado = ordenador.getEstado();
                                    ordenador1.ram = ordenador.getRam();
                                    ordenador1.procesador = ordenador.getProcesador();
                                    ordenador1.tamDisco = ordenador.getTamDisco();
                                    ordenador1.tipoDisco = ordenador.getTipoDisco();

                                    ordenadoresGuardados.add(ordenador1);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < ordenadoresGuardados.size(); i++) {
            Ordenador ordenador = new Ordenador(i);
            SaveOrdenador guardado = ordenadoresGuardados.get(i);
            ordenador.load();
            assertEquals(guardado.marca, ordenador.getMarca());
            assertEquals(guardado.modelo, ordenador.getModelo());
            assertEquals(guardado.estado, ordenador.getEstado());
            assertEquals(guardado.ram, ordenador.getRam());
            assertEquals(guardado.procesador, ordenador.getProcesador());
            assertEquals(guardado.tamDisco, ordenador.getTamDisco());
            assertEquals(guardado.tipoDisco, ordenador.getTipoDisco());
        }

    }



}
