package co.edu.upb.inventarioquimicos;

import co.edu.upb.inventarioquimicos.modelos.entidades.Usuario;
import co.edu.upb.inventarioquimicos.modelos.enums.Rol;
import co.edu.upb.inventarioquimicos.servicios.interfaces.UsuarioServicio;
import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@SpringBootApplication
public class Launcher {

    private static final Logger LOGGER = Logger.getLogger(Launcher.class.getName());

    public static void main(String[] args) {
        Application.launch(InventarioQuimicosApplication.class, args);
    }

    @Bean
    public CommandLineRunner crearUsuarioInicial(UsuarioServicio usuarioServicio) {
        return args -> {

            if (usuarioServicio.buscarPorUsername("admin").isEmpty()) {

                Usuario user = new Usuario();
                user.setNombre("Administrador");
                user.setUsername("admin");
                user.setPassword("1234");
                user.setRol(Rol.ADMINISTRADOR);

                usuarioServicio.guardar(user);

                LOGGER.info("Usuario admin creado");
            }

            if (usuarioServicio.buscarPorUsername("user").isEmpty()) {

                Usuario bodeguero = new Usuario();
                bodeguero.setNombre("user");
                bodeguero.setUsername("user");
                bodeguero.setPassword("1234");
                bodeguero.setRol(Rol.BODEGUERO);

                usuarioServicio.guardar(bodeguero);

                LOGGER.info("Usuario bodeguero creado");
            }
        };
    }
}