/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import datos.TarjetaDaoJDBC;
import dominio.Tarjeta;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 *
 * @author jesus
 */
@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "editar":
                    this.editarTarjeta(request, response);
                    break;
                case "eliminar":
                    this.eliminarTarjeta(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }
    }

    private void accionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tarjeta> tarjetas = new TarjetaDaoJDBC().listar();
        HttpSession sesion = request.getSession();
        sesion.setAttribute("tarjetas", tarjetas);
        sesion.setAttribute("totalTarjetas", tarjetas.size());
        sesion.setAttribute("saldoTotal", this.calcularSaldoTotal(tarjetas));
        response.sendRedirect("tarjetas.jsp");
    }

    private double calcularSaldoTotal(List<Tarjeta> tarjetas) {
        double saldoTotal = 0;
        for (Tarjeta tarjeta : tarjetas) {
            saldoTotal += tarjeta.getSaldo();
        }
        return saldoTotal;
    }

    private void editarTarjeta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idTarjeta = Integer.parseInt(request.getParameter("id"));
        Tarjeta tarjeta = new TarjetaDaoJDBC().encontrar(new Tarjeta(idTarjeta));
        request.setAttribute("tarjeta", tarjeta);
        String jspEditar = "/WEB-INF/paginas/tarjeta/editarTarjeta.jsp";
        request.getRequestDispatcher(jspEditar).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "insertar":
                    this.insertarTarjeta(request, response);
                    break;
                case "modificar":
                    this.modificarTarjeta(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        } else {
            this.accionDefault(request, response);
        }
    }

    private void insertarTarjeta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        long telefono = Long.parseLong(request.getParameter("telefono"));
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //Creamos el objeto de tarjeta (modelo)
        Tarjeta tarjeta = new Tarjeta(nombre, apellido, email, telefono, saldo);

        //Insertamos el nuevo objeto en la base de datos
        int registrosModificados = new TarjetaDaoJDBC().insertar(tarjeta);
        System.out.println("registrosModificados = " + registrosModificados);

        //Redirigimos hacia accion por default
        this.accionDefault(request, response);
    }

    private void modificarTarjeta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        long telefono = Long.parseLong(request.getParameter("telefono"));
        double saldo = 0;
        String saldoString = request.getParameter("saldo");
        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //Creamos el objeto de tarjeta (modelo)
        Tarjeta tarjeta = new Tarjeta(id, nombre, apellido, email, telefono, saldo);

        //Modificar el  objeto en la base de datos
        int registrosModificados = new TarjetaDaoJDBC().actualizar(tarjeta);
        System.out.println("registrosModificados = " + registrosModificados);

        //Redirigimos hacia accion por default
        this.accionDefault(request, response);
    }
    
        private void eliminarTarjeta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //recuperamos los valores del formulario
        int id = Integer.parseInt(request.getParameter("id"));
     

        //Creamos el objeto de tarjeta (modelo)
        Tarjeta tarjeta = new Tarjeta(id);

        //Eliminamos el  objeto en la base de datos
        int registrosModificados = new TarjetaDaoJDBC().eliminar(tarjeta);
        System.out.println("registrosModificados = " + registrosModificados);

        //Redirigimos hacia accion por default
        this.accionDefault(request, response);
    }

}
