/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
/**
 *
 * @author XIZUTH
 */
public class BaseDeDatos {//constructor*******************************
    
    
       // Controlador JDBC y Ruta de la Base de Datos
   final String DRIVER = "org.sqlite.JDBC";             
   final String DATABASE_URL = "jdbc:sqlite:EmpresaDB.db";
   Connection connection = null; // manages connection
   Statement statement = null; // query statement
   ResultSet resultSet = null; // manages results

   public BaseDeDatos(){//constructor
             // Conexión con la Base de Datos "Escuela.db" y se realiza una Consulta
      try 
      {
         // Se carga la clase del controlador
         Class.forName( DRIVER );

         // Se establece la conexión con la base de datos
         connection = DriverManager.getConnection( DATABASE_URL);

         // Se crea un objeto statement para consultar la base de datos
         statement = connection.createStatement();
       }  // end try
      catch ( SQLException sqlException )                                
      { } // end catch                                                     
      catch ( ClassNotFoundException classNotFound )                     
      { } // end catch           
   }
   
   public boolean existeUsuarioPassword(String usuario,String password) {try {
            // datos obtenidos de la BD tabla -> usuario

        resultSet = statement.executeQuery("SELECT * FROM Usuarios WHERE usuario = '" + 
                                        usuario+"' AND password = '"+password + "'");
        
        if (resultSet.next())
            return true;
                
                
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
       
       return false; 
}
   
   public Vector getEmpleados(){//metodo para utilizar la tabla
       
       Vector resultado = new Vector();//vector es un tipo de arrayList 
       
       try {
            // datos obtenidos de la BD tabla -> Empleado
        resultSet = statement.executeQuery("SELECT * FROM Empleados");
        
        while (resultSet.next() ){
            Vector vTemp = new Vector();
            vTemp.add(resultSet.getInt(1)); //ID
            vTemp.add(resultSet.getString(2)); //NOMBRE
            vTemp.add(resultSet.getString(3)); //apellido paterno
            vTemp.add(resultSet.getString(4)); //apellido materno
            vTemp.add(resultSet.getString(5)); //departamento
            vTemp.add(resultSet.getDouble(6)); //salario
            vTemp.add(resultSet.getString(8)); //e-mail 
            vTemp.add(resultSet.getString(7)); //foto
            resultado.add(vTemp);    
            
        }//fin del if
        
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
       
       return resultado;
   }
   
   public boolean insertarEmpleado(Vector registro){//agrega un empleado
       
       try {
            // datos obtenidos de la BD tabla -> usuario
           boolean devolver;
        devolver = statement.execute("INSERT INTO Empleados ("
                //+ "id,"           elimine el id y abajo el registro.get(0); por que ya se escribira por default
                + " nombre, apellidoPaterno,apellidoMaterno,"
                + " departamento,salario, foto,correo)"+ "VALUES ("
                + //registro.get(0) +             //ID
                //",'"
                "'"+registro.get(1) +"',"      //NOMBRE
                + "'"+ registro.get(2) +"',"    //APA PAT
                + "'"+ registro.get(3) +"',"    //APA MAT
                + "'"+ registro.get(4) +"',"    //DEPART
                + ""+ registro.get(5) +","      //SALARIO
                + "'"+registro.get(6) +"',"     //FOTO
                + "'" +registro.get(7)+         //CORREO
                  "')");
        
        return true; //ya que siempre devuelve false por lo tanto si hay algun error se atrapa en la excepcion
                                
        } catch (SQLException ex) {
          ex.printStackTrace();
          
          return false;
        }
       
   }// fin del metodo insertar Empleado
   public boolean actualizarEmpleado(Vector registro){//agrega un empleado
       
       try {
            // datos obtenidos de la BD tabla -> usuario
           int devolver;
        devolver = statement.executeUpdate( //retorna un valor entero
                "UPDATE Empleados SET  id = " + registro.get(0)      //ID
                + ", nombre = '"            +registro.get(1)+"'"     //NOMBRE
                + ", apellidoPaterno = '"   +registro.get(2)+"'"     //APA PAT
                + ", apellidoMaterno = '"   +registro.get(3) +"'"    //APA MAT
                + ", departamento = '"      +registro.get(4) +"'"    //DEPART
                + ", salario = "            +registro.get(5)         //SALARIO
                + ", foto = '"              + registro.get(6)+"'"    //FOTO
                + ", correo = '"            + registro.get(7)+"'"    //CORREO
                +"  WHERE id ='" + registro.get(0) + "'"
                );
        if(devolver > 0)
             return true; //ya que siempre devuelve false por lo tanto si hay algun error se atrapa en la excepcion
                                
        } catch (SQLException ex) {
          ex.printStackTrace();
          
          //JOptionPane.showMessageDialog(null, ex);
          return false;
        }
       return false;
       
   }// fin del metodo actualizar Empleado
   public void cerrarBD (){
        try {
            if(resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        }catch (SQLException ex) {
         
            JOptionPane.showMessageDialog(null, "Fallo al cerrar");
            
        }
   }//fin metodo cerrar!!!!!!!
   
    public boolean eliminarEmpleado(Vector <Integer> ids){//agrega un empleado

       try {
           String sql = "DELETE FROM Empleados WHERE ";
            //System.out.println(sql);
           
            for(Integer id : ids){
               sql += "id = " + id + " OR ";// la coma es por si se eliminan varios registros
               //System.out.println(sql);
           }

          sql = sql.substring(0, sql.length()-4); //con esta instruccion elimina la coma que tiene la instruccion anterior
          //System.out.println(sql);

          // datos obtenidos de la BD tabla -> usuario
          int devolver = statement.executeUpdate(sql );

        if(devolver > 0)
             return true; //ya que siempre devuelve false por lo tanto si hay algun error se atrapa en la excepcion

        } catch (SQLException ex) {
          ex.printStackTrace();

          //JOptionPane.showMessageDialog(null, ex);
          return false;
        }
       return false;

   }// fin del metodo actualizar Empleado

   public Vector buscarEmpleados(String buscar){//metodo para utilizar la tabla
       
       Vector resultado = new Vector();//vector es un tipo de arrayList 
       
       try {
            // datos obtenidos de la BD tabla -> Empleado
        resultSet = statement.executeQuery("SELECT * FROM Empleados WHERE nombre LIKE '%" + buscar +"%' OR "
                + "apellidoPaterno LIKE '%" +buscar+"%' OR apellidoMaterno LIKE '%"+ buscar+"%' OR "
                +"departamento LIKE '%"+ buscar +"%'");
        
        while (resultSet.next() ){
            Vector vTemp = new Vector();
            vTemp.add(resultSet.getInt(1)); //ID
            vTemp.add(resultSet.getString(2)); //NOMBRE
            vTemp.add(resultSet.getString(3)); //apellido paterno
            vTemp.add(resultSet.getString(4)); //apellido materno
            vTemp.add(resultSet.getString(5)); //departamento
            vTemp.add(resultSet.getDouble(6)); //salario
            vTemp.add(resultSet.getString(8)); //e-mail 
            vTemp.add(resultSet.getString(7)); //foto
            resultado.add(vTemp);    
            
        }//fin del if
        
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
       
       return resultado;
   }
    
}// fin de la clase
