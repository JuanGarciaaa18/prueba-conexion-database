import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRes = null;
        try {
            myConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project",
                    "root",
                    ""
            );
            System.out.println("Genial, nos conectamos");

            Scanner scanner = new Scanner(System.in);


            int opcion;

            String message = "\n\n!Bienvenido a tu CRUD¡\n\n";

            message += "1. ¿Quieres Insertar un Empleado?\n";
            message += "2. ¿Quieres Consultar un Empleado?\n";
            message += "3. ¿Quieres Actualizar un Empleado?\n";
            message += "4. ¿Quieres Eliminar un Empleado?\n";
            System.out.println(message);

            System.out.println("Elige tu opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Cargo: ");
                    String cargo = scanner.nextLine();
                    System.out.print("Salario: ");
                    double salario = scanner.nextDouble();
                    insertarEmpleado(myConn, nombre, cargo,salario);
                    break;
                case 2:
                    consultarEmpleados(myConn);
                    break;
                case 3:
                    System.out.print("ID del empleado a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Nuevo cargo: ");
                    String nuevoCargo = scanner.nextLine();
                    System.out.print("Nuevo salario: ");
                    double nuevoSalario = scanner.nextDouble();
                    actualizarEmpleado(myConn, idActualizar, nuevoNombre, nuevoCargo, nuevoSalario);
                    break;
                case 4:
                    System.out.print("ID del empleado a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    eliminarEmpleado(myConn, idEliminar);
                    break;

            }

            } catch(Exception e){
                e.printStackTrace();
                System.out.println("Algo salio mal :(");


            }
    }
    private static void insertarEmpleado(Connection conexion, String nombre, String cargo, double salario)
            throws SQLException {
        String sql = "INSERT INTO empleados (nombre, cargo, salario) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente!");
        }
    }

    // Método para consultar empleados
    private static void consultarEmpleados(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM empleados";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Cargo: %s, Salario: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getDouble("salario"));
            }
        }
    }

    // Método para actualizar un empleado
    private static void actualizarEmpleado(Connection conexion, int id, String nombre, String cargo, double salario)
            throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, cargo = ?, salario = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, cargo);
            pstmt.setDouble(3, salario);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente!");
        }
    }

    // Método para eliminar un empleado
    private static void eliminarEmpleado(Connection conexion, int id) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente!");
        }
    }
}