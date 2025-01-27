import me.elordenador.practica6.Disco;
import me.elordenador.practica6.Ordenador;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdenadorTest {

    private String[] marcas = {"HP", "Lenovo", "Acer", "Asus", "Honor", "Dell", "MSI", "Samsung", "Huawei"};
    private String[] modelos = {"modelo1", "modelo2", "modelo3", "modelo4", "modelo5", "modelo6", "modelo7", "modelo8", "modelo9", "modelo10"};
    private boolean[] estados = {true, false};
    private int[] rams = {1, 2, 4, 8, 16, 32, 64, 128};
    private String[] procesadores = {"Intel Core i3-12300", "Intel Core i3-12300K", "Intel Core i5-12500", "Intel Core i5-12500K", "Intel Core i7-12700", "Intel Core i7-12700K", "Intel Core i9-12900", "Intel Core i9-12900K"};
    private int[] tamDiscos = {16, 32, 64, 128, 256, 512, 1024, 2048, 4096};
    private Disco[] tipoDiscos = {Disco.HDD, Disco.SSD_SATA, Disco.SSD_NVME};

    @BeforeAll
    public static void setup() {
        Ordenador.init();
    }

    @Order(1)
    @Test
    public void addData() {
        for (String marca : marcas) {
            for (String modelo : modelos) {
                for (boolean estado : estados) {
                    for (int ram : rams) {
                        for (String procesador : procesadores) {
                            for (int tamDisco : tamDiscos) {
                                for (Disco disco : tipoDiscos) {
                                    Ordenador ordenador = new Ordenador(marca, modelo, estado, ram, procesador, tamDisco, disco);
                                    ordenador.save();
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}
