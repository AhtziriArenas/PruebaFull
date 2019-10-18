package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class BaseDatosConexion
{
  private String url = "";
  private String usuario = "";
  private String contasena = "";
  private Connection conexion = null;

  public BaseDatosConexion
  (
    String host,
    String db_name,
    String user,
    String password
  ) throws SQLException, ClassNotFoundException 
  {
      Class.forName("com.mysql.jdbc.Driver");
      this.usuario = user;
      this.contasena = password;
      this.url = String.format("jdbc:mysql://%s/%s", host, db_name);
      this.conexion = obtenerConexion();
  }

  private Connection obtenerConexion() throws SQLException 
  {
    return DriverManager.getConnection(url, usuario, contasena);
  }

  public void cerrarConexion() throws SQLException 
  {
    this.conexion.close();
  }

  //Insertar Cliente
  public boolean insertCliente(  Map<String,Object> values ) throws SQLException 
  {
    String nombre_tabla = "cliente";
    String columnas = "nombre, apellido, correo, telefono, direccion";
    String valores = "?, ?, ?, ?, ?";

    String sql_query = (
      "INSERT INTO " + 
      nombre_tabla + " " +
      "(" + columnas + ")" + " VALUES (" + valores + ")"
    );

    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setString( 1, values.get( "nombre" ).toString() );
    declaracion.setString( 2, values.get( "apellido" ).toString() );
    declaracion.setString( 3, values.get( "correo" ).toString() );      
    declaracion.setString( 4, values.get( "telefono" ).toString() );      
    declaracion.setString( 5, values.get( "direccion" ).toString() );   

    int filas_agregadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_agregadas > 0;
  }

  //Borrar Cliente
  public boolean deleteCliente( int id_fila ) throws SQLException 
  {
    String nombre_tabla = "cliente";
    String tabla_pk = "idcliente";

    String sql_query = (
      "DELETE " +
      "FROM " + nombre_tabla + " " +
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_fila );

    int filas_eliminadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_eliminadas > 0;
  }

  //Actualizar cliente
  public boolean updateCliente( int id_fila, Map<String,Object> values ) throws SQLException 
  {
    String nombre_tabla = "cliente";
    String tabla_pk = "idcliente";
    String columnas_valores = "nombre = ?, apellido = ?, correo = ?, telefono = ?, direccion = ?";

    String sql_query = (
      "UPDATE " + 
      nombre_tabla + " " +
      "SET " + columnas_valores + " " +
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );

    declaracion.setString( 1, values.get( "nombre" ).toString() );
    declaracion.setString( 2, values.get( "apellido" ).toString() );
    declaracion.setString( 3, values.get( "correo" ).toString() );      
    declaracion.setString( 4, values.get( "telefono" ).toString() );      
    declaracion.setString( 5, values.get( "direccion" ).toString() );    
    declaracion.setInt( 6, id_fila );

    int filas_actualizadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_actualizadas > 0;
  }

  //Seleccionar¨todos los clientes
  public DefaultTableModel selectListaClientes() throws SQLException
  {
    String nombre_tabla = "cliente";
    String string_select = "*";

    String sql_query = (
      "SELECT " + 
      string_select + " " +
      "FROM " + nombre_tabla
    );

    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    ResultSet resultado = declaracion.executeQuery();

    //Psar de ResultSet a DefaultTableModel
    DefaultTableModel modelo = new DefaultTableModel()
    {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    ConversorResultSetADefaultTableModel.rellena( resultado, modelo );
    //

    declaracion.close();
    resultado.close();
    return modelo;
  }

  //Seleccionar cliente
  public List<Object> selectCliente( int id_fila ) throws SQLException
  {
    String nombre_tabla = "cliente";
    String tabla_pk = "idcliente";
    String string_select = "*";

    String sql_query =(
      "SELECT " + 
      string_select + " " +
      "FROM " + nombre_tabla + " "+
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_fila );

    ResultSet resultado = declaracion.executeQuery();
    ResultSetMetaData md = resultado.getMetaData();
    int columnas = md.getColumnCount();

    List<Object> fila_lista = new ArrayList<Object>();
    while ( resultado.next() ) 
    {
      Map<String, Object> fila = new HashMap<String, Object>();
      for ( int i = 1 ; i <= columnas ; i++ ) 
      {
        fila.put( md.getColumnLabel(i).toLowerCase(), resultado.getObject(i) );
      }
      fila_lista.add( fila );
    }

    declaracion.close();
    resultado.close();
    return fila_lista;
  }
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //Permisos
//  public ArrayList<Map<String,Object>> selectGrupos() throws SQLException
//  {
//    String nombre_tabla = "grupo";
//    String string_select = "*";
//
//    String sql_query = (
//      "SELECT " + 
//      string_select + " " +
//      "FROM " + nombre_tabla
//    );
//
//    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
//    ResultSet resultado = declaracion.executeQuery();
//    
//    ArrayList<Map<String,Object>> grupos = new ArrayList<Map<String,Object>>();
//    while( resultado.next() ) 
//    {
//      int idgrupo = resultado.getInt( "idgrupo" );
//      String nombre = resultado.getString( "nombre" );
//      grupos.add(
//    			new HashMap<String, Object>(){
//    				{
//    					put( "id", idgrupo );
//    					put( "nombre", nombre );
//    				}
//    			}
//    	);	
//    }
//
//    declaracion.close();
//    resultado.close();
//    return grupos;
//  }
  
  public DefaultTableModel obtenerTablaGrupos() throws SQLException
  {
    String nombre_tabla = "grupo";
    String string_select = "*";

    String sql_query = (
      "SELECT " + 
      string_select + " " +
      "FROM " + nombre_tabla
    );

    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    ResultSet resultado = declaracion.executeQuery();
    
    //Psar de ResultSet a DefaultTableModel
    DefaultTableModel modelo = new DefaultTableModel()
    {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    ConversorResultSetADefaultTableModel.rellena( resultado, modelo );
    //

    declaracion.close();
    resultado.close();
    return modelo;
  }
  
  //Seleccionar Grupo
  public List<Object> selectGrupo( int id_fila ) throws SQLException
  {
    String nombre_tabla = "grupo";
    String tabla_pk = "idgrupo";
    String string_select = "*";

    String sql_query =(
      "SELECT " + 
      string_select + " " +
      "FROM " + nombre_tabla + " "+
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_fila );

    ResultSet resultado = declaracion.executeQuery();
    ResultSetMetaData md = resultado.getMetaData();
    int columnas = md.getColumnCount();

    List<Object> fila_lista = new ArrayList<Object>();
    while ( resultado.next() ) 
    {
      Map<String, Object> fila = new HashMap<String, Object>();
      for ( int i = 1 ; i <= columnas ; i++ ) 
      {
        fila.put( md.getColumnLabel(i).toLowerCase(), resultado.getObject(i) );
      }
      fila_lista.add( fila );
    }

    declaracion.close();
    resultado.close();
    return fila_lista;
  }
  
  //Insertar Grupo
  public boolean insertGrupo(  Map<String,Object> values ) throws SQLException 
  {
    String nombre_tabla = "grupo";
    String columnas = "tipo, nombre";
    String valores = "?, ?";

    String sql_query = (
      "INSERT INTO " + 
      nombre_tabla + " " +
      "(" + columnas + ")" + " VALUES (" + valores + ")"
    );

    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setString( 1, values.get( "tipo" ).toString() );
    declaracion.setString( 2, values.get( "nombre" ).toString() );

    int filas_agregadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_agregadas > 0;
  }

  //Borrar Grupo
  public boolean deleteGrupo( int id_fila ) throws SQLException 
  {
    String nombre_tabla = "grupo";
    String tabla_pk = "idgrupo";

    String sql_query = (
      "DELETE " +
      "FROM " + nombre_tabla + " " +
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_fila );

    int filas_eliminadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_eliminadas > 0;
  }

  //Actualizar Grupo
  public boolean updateGrupo( int id_fila, Map<String,Object> values ) throws SQLException 
  {
    String nombre_tabla = "grupo";
    String tabla_pk = "idgrupo";
    String columnas_valores = "tipo = ?, nombre = ?";

    String sql_query = (
      "UPDATE " + 
      nombre_tabla + " " +
      "SET " + columnas_valores + " " +
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );

    declaracion.setString( 1, values.get( "tipo" ).toString() );
    declaracion.setString( 2, values.get( "nombre" ).toString() );   
    declaracion.setInt( 3, id_fila );

    int filas_actualizadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_actualizadas > 0;
  }
  
  public DefaultTableModel selectUsuariosDelGrupo(int id_grupo) throws SQLException
  {
    String nombre_tabla = "usuario";
    String campo_grupo_id = "id_grupo";
    String string_select = "idusuario, user, correo, nombre, apellido";

    String sql_query = (
      "SELECT " + 
      string_select + " " +
      "FROM " + nombre_tabla + " " +
      "WHERE " + campo_grupo_id + " = ?"      
    );

    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_grupo );
    ResultSet resultado = declaracion.executeQuery();
    
    //Psar de ResultSet a DefaultTableModel
    DefaultTableModel modelo = new DefaultTableModel()
    {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    ConversorResultSetADefaultTableModel.rellena( resultado, modelo );
    //

    declaracion.close();
    resultado.close();
    return modelo;
  }
  
  //Seleccionar Uusarios que no pertenecen al grupo
  public ArrayList<Map<String,Object>> selectUsuariosNoGrupo(int id_fila) throws SQLException
  {
    String nombre_tabla = "usuario";
    String tabla_pk = "id_grupo";
    String string_select = "*";

    String sql_query =(
      "SELECT " + 
      string_select + " " +
      "FROM " + nombre_tabla + " "+
      "WHERE " + tabla_pk + " != ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_fila );

    ResultSet resultado = declaracion.executeQuery();
    ResultSetMetaData md = resultado.getMetaData();
    int columnas = md.getColumnCount();

    ArrayList<Map<String,Object>> usuarios = new ArrayList<Map<String,Object>>();
    while( resultado.next() ) 
    {
      int idusuario = resultado.getInt( "idusuario" );
      String nombre = resultado.getString( "nombre" );
      String apellido = resultado.getString( "apellido" );
      usuarios.add(
    			new HashMap<String, Object>(){
    				{
    					put( "id", idusuario );
    					put( "nombre", nombre + " " + apellido );
    				}
    			}
    	);	
    }

    declaracion.close();
    resultado.close();
    return usuarios;
  }
  
  
  //Actualizar grupo de un usuario
  public boolean updateGrupoDelUsuario( int id_usuario, int id_grupo ) throws SQLException 
  {
    String nombre_tabla = "usuario";
    String tabla_pk = "idusuario";
    String columnas_valores = "id_grupo = ?";

    String sql_query = (
      "UPDATE " + 
      nombre_tabla + " " +
      "SET " + columnas_valores + " " +
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
 
    declaracion.setInt( 1, id_grupo );
    declaracion.setInt( 2, id_usuario );

    int filas_actualizadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_actualizadas > 0;
  }
  
   public DefaultTableModel selectPermisosDelGrupo(int id_grupo) throws SQLException
  {
    String nombre_tabla = "permiso";
    String campo_grupo_id = "id_grupo";
    String string_select = "idpermiso, tabla, leer, escribir, eliminar";

    String sql_query = (
      "SELECT " + 
      string_select + " " +
      "FROM " + nombre_tabla + " " +
      "WHERE " + campo_grupo_id + " = ?"      
    );

    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_grupo );
    ResultSet resultado = declaracion.executeQuery();
    
    //Psar de ResultSet a DefaultTableModel
    DefaultTableModel modelo = new DefaultTableModel()
    {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    ConversorResultSetADefaultTableModel.rellena( resultado, modelo );
    //

    declaracion.close();
    resultado.close();
    return modelo;
  }
  
   
  //Seleccionar Grupo
  public List<Object> selectPermiso( int id_fila ) throws SQLException
  {
    String nombre_tabla = "permiso";
    String tabla_pk = "idpermiso";
    String string_select = "*";

    String sql_query =(
      "SELECT " + 
      string_select + " " +
      "FROM " + nombre_tabla + " "+
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_fila );

    ResultSet resultado = declaracion.executeQuery();
    ResultSetMetaData md = resultado.getMetaData();
    int columnas = md.getColumnCount();

    List<Object> fila_lista = new ArrayList<Object>();
    while ( resultado.next() ) 
    {
      Map<String, Object> fila = new HashMap<String, Object>();
      for ( int i = 1 ; i <= columnas ; i++ ) 
      {
        fila.put( md.getColumnLabel(i).toLowerCase(), resultado.getObject(i) );
      }
      fila_lista.add( fila );
    }

    declaracion.close();
    resultado.close();
    return fila_lista;
  }
  
  //Insertar Permiso
  public boolean insertPermiso(  Map<String,Object> values ) throws SQLException 
  {
    String nombre_tabla = "permiso";
    String columnas = "tabla, leer, escribir, eliminar, id_grupo";
    String valores = "?, ?, ?, ?, ?";

    String sql_query = (
      "INSERT INTO " + 
      nombre_tabla + " " +
      "(" + columnas + ")" + " VALUES (" + valores + ")"
    );

    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setString( 1, values.get( "tabla" ).toString() );
    declaracion.setInt(2, Integer.parseInt( values.get( "leer" ).toString() ) );
    declaracion.setInt(3, Integer.parseInt( values.get( "escribir" ).toString() ) );
    declaracion.setInt(4, Integer.parseInt( values.get( "eliminar" ).toString() ) );
    declaracion.setInt(5, Integer.parseInt( values.get( "id_grupo" ).toString() ) );

    int filas_agregadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_agregadas > 0;
  }

  //Borrar Permiso
  public boolean deletePermiso( int id_fila ) throws SQLException 
  {
    String nombre_tabla = "permiso";
    String tabla_pk = "idpermiso";

    String sql_query = (
      "DELETE " +
      "FROM " + nombre_tabla + " " +
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_fila );

    int filas_eliminadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_eliminadas > 0;
  }

  //Actualizar Permiso
  public boolean updatePermiso( int id_fila, Map<String,Object> values ) throws SQLException 
  {
    String nombre_tabla = "permiso";
    String tabla_pk = "idpermiso";
    String columnas_valores = "tabla = ?, leer = ?, escribir = ?, eliminar = ?, id_grupo = ?";

    String sql_query = (
      "UPDATE " + 
      nombre_tabla + " " +
      "SET " + columnas_valores + " " +
      "WHERE " + tabla_pk + " = ?" 
    );
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );

    declaracion.setString( 1, values.get( "tabla" ).toString() );
    declaracion.setInt(2, Integer.parseInt( values.get( "leer" ).toString() ) );
    declaracion.setInt(3, Integer.parseInt( values.get( "escribir" ).toString() ) );
    declaracion.setInt(4, Integer.parseInt( values.get( "eliminar" ).toString() ) );
    declaracion.setInt(5, Integer.parseInt( values.get( "id_grupo" ).toString() ) );  
    declaracion.setInt( 6, id_fila );

    int filas_actualizadas = declaracion.executeUpdate();
    declaracion.close();
    return filas_actualizadas > 0;
  }
  
  public boolean checkPermisoLeer( int id_user, String tabla ) throws SQLException 
  {
    return checkPermiso( id_user, "leer", tabla );
  }
  
  public boolean checkPermisoEscribir( int id_user, String tabla ) throws SQLException 
  {
    return checkPermiso( id_user, "escribir", tabla );
  }
  
  public boolean checkPermisoEliminar( int id_user, String tabla ) throws SQLException 
  {
    return checkPermiso( id_user, "eliminar", tabla );
  }
  
  public boolean checkPermiso( int id_user, String permiso, String tabla ) throws SQLException 
  {
    String sql_query = (    
      "SELECT p."+ permiso.toLowerCase() + " " +
      "FROM permiso as p " +
      "INNER JOIN grupo as g " +
      "ON g.idgrupo = p.id_grupo " +
      "INNER JOIN usuario as u " +
      "ON u.id_grupo = g.idgrupo " +
      "WHERE u.idusuario = ? and p.tabla = ? " 
    );
    
    PreparedStatement declaracion = conexion.prepareStatement( sql_query );
    declaracion.setInt( 1, id_user );
    declaracion.setString( 2, tabla.toLowerCase() );

    ResultSet resultado = declaracion.executeQuery();
    ResultSetMetaData md = resultado.getMetaData();
    int columnas = md.getColumnCount();

    List<Object> fila_lista = new ArrayList<Object>();
    boolean retorno = false;
    while ( resultado.next() ) 
    {
      for ( int i = 1 ; i <= columnas ; i++ ) 
      {
        retorno = retorno || Integer.parseInt( resultado.getObject(i).toString() ) == 1;
      }
    }

    declaracion.close();
    resultado.close();
    return retorno;
  }
   
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}