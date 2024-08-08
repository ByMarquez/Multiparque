/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import dominio.Tarjeta;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesus
 */
public class TarjetaDaoJDBC {

    private static final String SQL_SELECT = "SELECT * FROM tarjetas";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM tarjetas WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM tarjetas WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO tarjetas "
            + "(nombre,apellido,email,telefono,saldo) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE tarjetas SET "
            + " nombre=?, apellido=?, email=?, telefono=? , saldo=? WHERE id=?";

    public List<Tarjeta> listar() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Tarjeta tarjeta = null;
        List<Tarjeta> tarjetas = new ArrayList<>();

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                long telefono = rs.getLong("telefono");
                double saldo = rs.getDouble("saldo");

                tarjeta = new Tarjeta(id, nombre, apellido, email, telefono, saldo);
                tarjetas.add(tarjeta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return tarjetas;
    }

    public Tarjeta encontrar(Tarjeta tarjeta) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, tarjeta.getId());
            rs = stmt.executeQuery();
            rs.next();
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String email = rs.getString("email");
            long telefono = rs.getLong("telefono");
            double saldo = rs.getDouble("saldo");

            tarjeta.setNombre(nombre);
            tarjeta.setApellido(apellido);
            tarjeta.setEmail(email);
            tarjeta.setTelefono(telefono);
            tarjeta.setSaldo(saldo);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return tarjeta;
    }

    public int insertar(Tarjeta tarjeta) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, tarjeta.getNombre());
            stmt.setString(2, tarjeta.getApellido());
            stmt.setString(3, tarjeta.getEmail());
            stmt.setLong(4, tarjeta.getTelefono());
            stmt.setDouble(5, tarjeta.getSaldo());

            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }
    
    public int actualizar (Tarjeta tarjeta) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, tarjeta.getNombre());
            stmt.setString(2, tarjeta.getApellido());
            stmt.setString(3, tarjeta.getEmail());
            stmt.setLong(4, tarjeta.getTelefono());
            stmt.setDouble(5, tarjeta.getSaldo());
            stmt.setInt(6, tarjeta.getId());

            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }
    
    public int eliminar (Tarjeta tarjeta) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, tarjeta.getId());
            
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

}
