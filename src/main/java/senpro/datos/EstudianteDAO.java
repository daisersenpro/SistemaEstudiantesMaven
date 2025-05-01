package senpro.datos;

import senpro.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static senpro.conexion.Conexion.getConnection;

public class EstudianteDAO {

    public List<Estudiante> listarEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM estudiante ORDER BY id_estudiante";

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("mail"));
                estudiantes.add(estudiante);
            }

        } catch (Exception e) {
            System.out.println("Error al listar estudiantes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return estudiantes;
    }

    public static void main(String[] args) {
        var estudianteDAO = new EstudianteDAO();
        System.out.println("Lista de estudiantes:");
        List<Estudiante> estudiantes = estudianteDAO.listarEstudiantes();
        estudiantes.forEach(System.out::println);
    }
}
