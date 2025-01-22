import me.elordenador.practica6.Dispositivo;
import me.elordenador.practica6.ElementNotFoundException;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DispositivoTest {
    private String[] marcas = {"LG", "Samsung", "HP", "Lenovo", "Dell", "Asus", "Acer"};
    private String[] modelos = {"Modelo1","Modelo2","Modelo3","Modelo4","Modelo5","Modelo6"};
    @BeforeAll
    public static void setupFile() {
        Dispositivo.init();
    }


    @Order(1)
    @Test
    public void generateData() {
        for (String marca : marcas) {
            for (String modelo : modelos) {
                System.out.println("Creando dispositivo: " + marca + " " + modelo);
                Dispositivo dispositivo = new Dispositivo(marca, modelo, true);
                System.out.println("  Guardando dispositivo...");
                dispositivo.save();
            }
        }
    }

    @Order(2)
    @Test
    public void readData() {
        int position = 0;
        boolean salida = false;
        while (!salida) {
            Dispositivo dispositivo = new Dispositivo(position);
            try {
                dispositivo.load();
                System.out.println(dispositivo.toString());

            } catch (ElementNotFoundException e) {
                if (!e.logicaldelete) {
                    salida = true;
                }
            }
            position++;


        }
    }
}
