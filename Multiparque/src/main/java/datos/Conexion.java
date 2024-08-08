/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author jesus
 */
public class Conexion {
    private static final String JDBC_URL= "jdbc:mysql://localhost:3306/juegos?useSSL=false&useTimezone=true&severTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String user= "root";
    private static final String password= "root";
    private static BasicDataSource dataSourse;
    
    //pool de conecciones de 50, dependencia apache.commons.dbcp2
    public static DataSource getDataSource(){
        if(dataSourse == null){
        dataSourse=new BasicDataSource();
        dataSourse.setUrl(JDBC_URL);
        dataSourse.setUsername(user);
        dataSourse.setPassword(password);
        dataSourse.setInitialSize(50);
        }
        return dataSourse;
    }
    //obtener una coneccion del pool de conecciones
    public static Connection getConnection () throws SQLException{
        return getDataSource().getConnection();
    }
    
    //metodos para cerrar canales
    public static void close(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    public static void close(PreparedStatement stmt){
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    public static void close(Connection con){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
