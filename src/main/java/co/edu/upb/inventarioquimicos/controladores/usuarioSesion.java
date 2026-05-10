package co.edu.upb.inventarioquimicos.controladores;

import co.edu.upb.inventarioquimicos.modelos.entidades.Usuario;

public class usuarioSesion {

    private static String  rol;
    private static Usuario usuarioActual;

    public static void setRol(String rolUsuario) {
        rol = rolUsuario;
    }

    public static String getRol() {
        return rol;
    }

    public static void setUsuario(Usuario u) {
        usuarioActual = u;
    }

    public static Usuario getUsuario() {
        return usuarioActual;
    }
}
