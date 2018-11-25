package com.example.tr.appsql;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class ConectionDB {


    private String userName = "sa";
    private String password = "12345";
    private String ip="192.168.2.71";
    private String ip2="192.168.2.35";
    private String url = "jdbc:jtds:sqlserver://"+ip+":1433/UNIVERSIDADPOLITECNICA_UPP;";
    private String url2 ="jdbc:jtds:sqlserver://"+ip2+":1433/UNIVERSIDADPOLITECNICA_UPP;";


    private String EstadoConvert="",MunicipioConvert="",LocalidadConvert="";
    public boolean conexion=false;
    public boolean admin=false;
    public boolean confirmationNick=false,confirmationAlumno=false;
    public boolean confirmationInsert=false;
    public String EstadosSpinner;
    public String MunicipiosSpinner;
    public String LocalidadesSpinner;
    public String idEstado;
    public String idMunicipios;
    public String idLocalidad;
    Statement st;
    ResultSet rs;

    /**
     *ESTA FUNCIÓN SIRVE PARA TOMAR EL VALOR DE 'EstadosSpinner' Y REGRESARLO  A LA VARIABLE
     * */
    public String getEstadosSpinner() {
        return EstadosSpinner;
    }

    /**
     *ESTA FUNCIÓN SIRVE PARA TOMAR EL VALOR DE 'MunicipiosSpinner' Y REGRESARLO  A LA VARIABLE
     * */
    public String getMunicipiosSpinner() {
        return MunicipiosSpinner;
    }

    /**
     *ESTA FUNCIÓN SIRVE PARA TOMAR EL VALOR DE 'LocalidadesSpinner' Y REGRESARLO  A LA VARIABLE
     * */
    public String getLocalidadesSpinner() {
        return LocalidadesSpinner;
    }

    /**
     *ESTA FUNCIÓN SIRVE PARA ASIGNAR EL VALOR A 'EstadosSpinner' CUANDO LA FUNCION SEA INSTANCIADA Y UTILIZADA
     * */
    public void setEstadosSpinner(String valor){
        this.EstadosSpinner=valor;
    }

    /**
     *ESTA FUNCIÓN SIRVE PARA ASIGNAR EL VALOR A 'MunicipiosSpinner' CUANDO LA FUNCION SEA INSTANCIADA Y UTILIZADA
     * */
    public void setMunicipiosSpinner(String valor){
        this.MunicipiosSpinner=valor;
    }

    /**
     *ESTA FUNCIÓN SIRVE PARA ASIGNAR EL VALOR A 'LocalidadesSpinner' CUANDO LA FUNCION SEA INSTANCIADA Y UTILIZADA
     * */
    public void setLocalidadesSpinner(String valor){
        this.LocalidadesSpinner=valor;
    }

    /**
     *ESTA FUNCIÓN SIRVE PARA ASIGNAR EL VALOR A 'confirmationInsert' CUANDO LA FUNCION SEA INSTANCIADA Y UTILIZADA
     * DESCRIPCION: ESTA FUNCION ES USADA PARA CONFIRMAR SI LOS DATOS SE HAN INSERTADO CORRECTAMENTE ASIGNANDO UN VALOR
     * (DEPENDERA DEL VALOR QUE SEA ASIGNADO DURANTE SU INSTANCIACIÓN) BOOLEANO
     * */
    public void  setConfirmationInsert(boolean val){

        this.confirmationInsert=val;
    }

    /**
     *ESTA FUNCIÓN SIRVE PARA ASIGNAR EL VALOR A 'confirmationNick' CUANDO LA FUNCION SEA INSTANCIADA Y UTILIZADA
     * DESCRIPCION: ESTA FUNCION ES USADA PARA CONFIRMAR SI EL ADMINISTADOR EXISTE
     * */
    public void setConfirmationNick(boolean res){

        this.confirmationNick=res;
    }

    /**
     *ESTA FUNCIÓN SIRVE PARA ASIGNAR EL VALOR A 'confirmationAlumno' CUANDO LA FUNCION SEA INSTANCIADA Y UTILIZADA
     * DESCRIPCION: ESTA FUNCION ES USADA PARA CONFIRMAR SI EL ALUMNO EXISTE
     * */
    public void setConfirmationAlumno(boolean val){

        this.confirmationAlumno=val;
    }

    /**
     *ESTA FUNCION ES USADA PARA CONFIRMAR QUE UN ALUMNO FUE ENCONTRADO
     */
    public void setAdmin(boolean result){

        this.admin=result;
    }

    /**
     *ESTA FUNCION SIRVE PARA TOMAR EL VALOR DE LA VARIABLE 'admin'
     */
    public boolean getAdmin(){
        return this.admin;
    }

    /**
     * VARIABLE QUE SIRVE PARA CONFIRMAR SI HAY CONEXION DE LA APLICACION A LOS SERVIDORES
     */
    public void setConexion(boolean res){

        this.conexion=res;
    }

    /**
     * FUNCION QUE SIRVE PARA TOMAR EL VALOR DE LA VARIABLE 'conexion'
     * */
    public boolean getConexion(){
        return this.conexion;
    }

    /**
     * ESTA FUNCION REALIZA LA CONEXION A SERVIDOR 1 (MAQUINA FISICA 'DONDE SE ENCUENTRA SQL SERVERORIGINAL')
     * */
    public Connection conectionServer1() {
        Connection connection = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, userName, password);
            setConexion(true);
        } catch (Exception e) {
            Log.e("servidor1",e.toString());
            setConexion(false);
        }
        return connection;
    }

    /**
     * ESTA FUNCION REALIZA LA CONEXION A SERVIDOR 2 (MAQUINA VIRTUAL 'DONDE SE ENCUENTRA SQL SERVERVIRTUAL')
     * */
    public Connection conectionServer2() {
        Connection connection = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url2, userName, password);
            setConexion(true);
        } catch (Exception e) {
            Log.e("servidor2",e.toString());
            setConexion(false);
        }
        return connection;
    }

    /**
     * ESTA FUNCIÓN HACE UNA PETICIÓN A LOS SERVIDORES PARA VERIFICAR QUE EL USUARIO EXISTE DE ACUERDO A LOS PARAMETROS
     * INGRESADOS
     * */
    public void queryAdminServers(String nombre,String pass){
            conectionServer1();
            if (this.conexion){
                try {
                    Statement st = conectionServer1().createStatement();
                    ResultSet rs = st.executeQuery("select nick from admin where nick='" + nombre + "' and password ='" + pass + "';");
                    while (rs.next()) {
                        setAdmin(true);
                    }
                    conectionServer1().close();
                }
                catch (Exception e){
                }
            }
            else {
                conectionServer2();
                if (this.conexion) {
                    try {
                        Statement st = conectionServer2().createStatement();
                        ResultSet rs = st.executeQuery("select nick from admin where nick='" + nombre + "' and password ='" + pass + "';");
                        while (rs.next()) {
                            setAdmin(true);
                        }
                        conectionServer2().close();
                    }
                    catch (Exception e) {
                    }
                }
            }
    }

    /**
     * ESTA FUNCIÓN HACE UNA PETICIÓN A LOS SERVIDORES PARA VERIFICAR QUE NO EXISTE UN ADMINISTRADOR EN LA BD
     * CON LA MISMA MATRICULA PARA EVITAR ERRORES DE CLAVES DENTRO DEL SERVIDOR
     * */
    public void checkInRecordsAdmin(String nick){
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select nick from admin where nick='"+nick+"'");
                while (rs.next()) {
                    setConfirmationNick(true);
                }
                conectionServer1().close();
            }
            catch (Exception e){
            }
        }
        else{
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select nick from admin where nick='"+nick+"'");
                    while (rs.next()) {
                        setConfirmationNick(true);
                    }
                    conectionServer2().close();
                }
                catch (Exception e){
                }
            }

        }

    }

    /**
     * ESTA FUNCIÓN HACE UNA PETICIÓN A LOS SERVIDORES PARA VERIFICAR QUE NO EXISTE UN ALUMNO EN LA BD
     * CON LA MISMA MATRICULA PARA EVITAR ERRORES DE CLAVES DENTRO DEL SERVIDOR
     * */
    public void checkInRecordsAlumnos(String Matricula){
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select Matricula from Alumno where Matricula='"+Matricula+"'");
                while (rs.next()) {
                    setConfirmationAlumno(true);
                }
                conectionServer1().close();
            }
            catch (Exception e){
            }
        }else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select Matricula from Alumno where Matricula='"+Matricula+"'");
                    while (rs.next()) {
                        setConfirmationAlumno(true);
                    }
                    conectionServer2().close();
                }
                catch (Exception e){
                }
            }
        }
    }

    /**
     * ESTA FUNCION SIRVE PARA INSERTAR UN ADMINISTRADOR CON TODOS LOS PARAMETRO EQUERIDOS
     */
    public void insertAdmin(String nick,String nombre,String paterno, String materno, String password){
        conectionServer1();
        if (this.conexion){
            try {
                PreparedStatement query=conectionServer1().prepareStatement("insert into admin (nick,nombre,Ap_paterno,Ap_materno,password) values ('"+nick+"','"+nombre+"','"+paterno+"','"+materno+"','"+password+"');");
                query.execute();
                setConfirmationInsert(true);
                conectionServer1().close();
            }
            catch (SQLException e){

            }
        }
        conectionServer2();
        if (this.conexion){
            try {
                PreparedStatement query=conectionServer2().prepareStatement("insert into admin (nick,nombre,Ap_paterno,Ap_materno,password) values ('"+nick+"','"+nombre+"','"+paterno+"','"+materno+"','"+password+"');");
                query.execute();
                setConfirmationInsert(true);
                conectionServer2().close();
            }
            catch (SQLException e){

            }
        }
    }

    /**
     * ESTA FUNCION SIRVE PARA INSERTAR UN ALUMNO CON TODOS LOS PARAMETRO EQUERIDOS
     */
    public void insertarAlumno(String matricula,String nombre,String paterno,String materno,String estado,String municipio,String localidad){
        conectionServer1();
        if (this.conexion){
            try {
                PreparedStatement query=conectionServer1().prepareStatement("insert Alumno (Matricula,nombre,Ap_paterno,Ap_materno,Cve_estado,Cve_municipio,Cve_localidad) values ('"+matricula+"','"+nombre+"','"+paterno+"','"+materno+"','"+estado+"','"+municipio+"','"+localidad+"')");
                query.execute();
                setConfirmationInsert(true);
                conectionServer1().close();
            }
            catch (SQLException e){

            }
        }else {
            conectionServer2();
            if (this.conexion){
                try {
                    PreparedStatement query=conectionServer2().prepareStatement("insert Alumno (Matricula,nombre,Ap_paterno,Ap_materno,Cve_estado,Cve_municipio,Cve_localidad) values ('"+matricula+"','"+nombre+"','"+paterno+"','"+materno+"','"+estado+"','"+municipio+"','"+localidad+"')");
                    query.execute();
                    setConfirmationInsert(true);
                    conectionServer2().close();
                }
                catch (SQLException e){

                }
            }
        }

    }

    /**
     * ESTA FUNCION RETORNA EL ID DEL ESTADO SELECCIONADO Y LO INSERTA EN LA BASE DE DATOS DE LOS SERVIDORES
     * */
    public String setClaveEstadoQuery(){
        /***
         * Este metodo es usado para tomar la clave primaria de los estados e insertarla dentro de la variable
         * idEstado
         */
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select Cve_Estado from Estados where nombre='"+getEstadosSpinner()+"'");
                while (rs.next()) {
                    idEstado=rs.getString("Cve_Estado");
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select Cve_Estado from Estados where nombre='"+getEstadosSpinner()+"'");
                    while (rs.next()) {
                        idEstado=rs.getString("Cve_Estado");
                    }
                    conectionServer2().close();
                }
                catch (Exception e){

                }
            }
        }

        return idEstado;
    }

    /**
     * ESTA FUNCION RETORNA EL ID DEL MUNICIPIO SELECCIONADO Y LO INSERTA EN LA BASE DE DATOS DE LOS SERVIDORES
     * */
    public String setClaveMunicipioQuery(){
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select Cve_municipios from Municipios where Cve_estado='"+setClaveEstadoQuery()+"' and nombre='"+getMunicipiosSpinner()+"'");
                while (rs.next()) {
                    idMunicipios=rs.getString("Cve_municipios");
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select Cve_municipios from Municipios where Cve_estado='"+setClaveEstadoQuery()+"' and nombre='"+getMunicipiosSpinner()+"'");
                    while (rs.next()) {
                        idMunicipios=rs.getString("Cve_municipios");
                    }
                    conectionServer2().close();
                }
                catch (Exception e){

                }
            }
        }
        return idMunicipios;
    }

    /**
     * ESTA FUNCION RETORNA EL ID DE LOCALIDAD SELECCIONADO Y LO INSERTA EN LA BASE DE DATOS DE LOS SERVIDORES
     * */
    public String setClaveLocalidadesQuery(){
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select Cve_Localidad from Localidades where nombre='"+getLocalidadesSpinner()+"' and Cve_municipio='"+setClaveMunicipioQuery()+"' and Cve_estado='"+setClaveEstadoQuery()+"'");
                while (rs.next()) {
                    idLocalidad=rs.getString("Cve_Localidad");
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select Cve_Localidad from Localidades where nombre='"+getLocalidadesSpinner()+"' and Cve_municipio='"+setClaveMunicipioQuery()+"' and Cve_estado='"+setClaveEstadoQuery()+"'");
                    while (rs.next()) {
                        idLocalidad=rs.getString("Cve_Localidad");
                    }
                    conectionServer2().close();
                }
                catch (Exception e){

                }
            }
        }
        return idLocalidad;
    }

    /**
     * TOMA TODOS LOS ESTADOS DE LA BASE DE DATOS DE CUALQUIERA DE LOS 2 SERVIDORES
     * */
    public ArrayList<String> getDataEstados(){
        ArrayList<String> dataEstados= new ArrayList<>();
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select Cve_estado,nombre from Estados");
                while (rs.next()) {
                    dataEstados.add(rs.getString("nombre"));
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }
        else {
            conectionServer2();
            if (this.conexion) {
                try {
                    st = conectionServer2().createStatement();
                    rs = st.executeQuery("select Cve_estado,nombre from Estados");
                    while (rs.next()) {
                        dataEstados.add(rs.getString("nombre"));
                    }
                    conectionServer2().close();
                }
                catch (Exception e) {

                }
            }
        }
        return dataEstados;
    }

    /**
     * TOMA TODOS LOS MUNICIPIOS DE LA BASE DE DATOS DE CUALQUIERA DE LOS 2 SERVIDORES
     * */
    public ArrayList<String> getDataMunicipios(){
        ArrayList<String> dataMunicipios= new ArrayList<>();
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select nombre from Municipios where Cve_estado='"+setClaveEstadoQuery()+"' ORDER BY nombre ASC");
                while (rs.next()) {
                    dataMunicipios.add(rs.getString("nombre"));
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }
        else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select nombre from Municipios where Cve_estado='"+setClaveEstadoQuery()+"' ORDER BY nombre ASC");
                    while (rs.next()) {
                        dataMunicipios.add(rs.getString("nombre"));
                    }
                    conectionServer2().close();
                }
                catch (Exception e){

                }
            }
        }
        return dataMunicipios;
    }

    /**
     * TOMA TODOS LAS LOCALIDADES DE LA BASE DE DATOS DE CUALQUIERA DE LOS 2 SERVIDORES
     * */
    public ArrayList<String> getDataLocalidades(){
        ArrayList<String> dataLocalidades= new ArrayList<>();
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select nombre from Localidades where Cve_municipio='"+setClaveMunicipioQuery()+"' and Cve_estado='"+setClaveEstadoQuery()+"' ORDER BY nombre ASC");
                while (rs.next()) {
                    dataLocalidades.add(rs.getString("nombre"));
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }
        else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select nombre from Localidades where Cve_municipio='"+setClaveMunicipioQuery()+"' and Cve_estado='"+setClaveEstadoQuery()+"' ORDER BY nombre ASC");
                    while (rs.next()) {
                        dataLocalidades.add(rs.getString("nombre"));
                    }
                    conectionServer2().close();
                }
                catch (Exception e){

                }
            }
        }
        return dataLocalidades;
    }

    /**
     * OBTIENE EL NOMBRE DEL ESTADO SELECCIONADO PARA INGRESAR EN EL REPORTE
     * */
    public String obtenerNombreEstado(String id){
        String nombre="";
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select nombre from Estados where Cve_Estado='"+id+"'");
                while (rs.next()) {
                    nombre=rs.getString("nombre");
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }
        else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select nombre from Estados where Cve_Estado='"+id+"'");
                    while (rs.next()) {
                        nombre=rs.getString("nombre");
                    }
                    conectionServer2().close();
                }
                catch (Exception e){

                }
            }
        }
        return nombre;
    }


    /**
     * OBTIENE EL NOMBRE DEL MUNICIPIO SELECCIONADO PARA INGRESAR EN EL REPORTE
     * */
    public String obtenerNombreMunicipio(String idEdo,String idMunicipio){
        String nombre="";
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select nombre from Municipios where Cve_municipios='"+idMunicipio+"' and Cve_estado='"+idEdo+"'");
                while (rs.next()) {
                    nombre=rs.getString("nombre");
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }
        else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select nombre from Municipios where Cve_municipios='"+idMunicipio+"' and Cve_estado='"+idEdo+"'");
                    while (rs.next()) {
                        nombre=rs.getString("nombre");
                    }
                    conectionServer2().close();
                }
                catch (Exception e){

                }
            }
        }
        return nombre;
    }

    /**
     * OBTIENE EL NOMBRE DEL LOCALIDAD SELECCIONADO PARA INGRESAR EN EL REPORTE
     * */
    public String obtenerNombreLocalidad(String idEdo,String idMunicipio,String idLocalidad){
        String nombre="";
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select nombre from Localidades where Cve_municipio='"+idMunicipio+"' and Cve_estado='"+idEdo+"' and Cve_Localidad='"+idLocalidad+"'");
                while (rs.next()) {
                    nombre=rs.getString("nombre");
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }
        else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select nombre from Localidades where Cve_municipio='"+idMunicipio+"' and Cve_estado='"+idEdo+"' and Cve_Localidad='"+idLocalidad+"'");
                    while (rs.next()) {
                        nombre=rs.getString("nombre");
                    }
                    conectionServer2().close();
                }
                catch (Exception e){

                }
            }
        }
        return nombre;
    }


    /**
     * OBTIENE UN ARREGLO DE LISTAS QUE SERAN UTILIZADOS PARA GENERAR UN REPORTE
     * */
    public ArrayList<String[]> reportDataGeneral(){
        ArrayList<String[]> dataReportOne= new ArrayList<>();
        conectionServer1();
        if (this.conexion){
            try {
                Statement st = conectionServer1().createStatement();
                ResultSet rs = st.executeQuery("select Matricula,nombre,Ap_paterno,Ap_materno,Cve_estado,Cve_municipio,Cve_localidad from Alumno ORDER BY Cve_estado ASC");
                while (rs.next()) {
                    EstadoConvert=rs.getString("Cve_estado");
                    MunicipioConvert=rs.getString("Cve_municipio");
                    LocalidadConvert=rs.getString("Cve_localidad");
                    dataReportOne.add(new String[]{rs.getString("Matricula"),rs.getString("nombre"),rs.getString("Ap_paterno"),rs.getString("Ap_materno"),obtenerNombreEstado(EstadoConvert),obtenerNombreMunicipio(EstadoConvert,MunicipioConvert),obtenerNombreLocalidad(EstadoConvert,MunicipioConvert,LocalidadConvert)});
                }
                conectionServer1().close();
            }
            catch (Exception e){

            }
        }
        else {
            conectionServer2();
            if (this.conexion){
                try {
                    Statement st = conectionServer2().createStatement();
                    ResultSet rs = st.executeQuery("select Matricula,nombre,Ap_paterno,Ap_materno,Cve_estado,Cve_municipio,Cve_localidad from Alumno ORDER BY Cve_estado ASC");
                    while (rs.next()) {
                        EstadoConvert=rs.getString("Cve_estado");
                        MunicipioConvert=rs.getString("Cve_municipio");
                        LocalidadConvert=rs.getString("Cve_localidad");
                        dataReportOne.add(new String[]{rs.getString("Matricula"),rs.getString("nombre"),rs.getString("Ap_paterno"),rs.getString("Ap_materno"),obtenerNombreEstado(EstadoConvert),obtenerNombreMunicipio(EstadoConvert,MunicipioConvert),obtenerNombreLocalidad(EstadoConvert,MunicipioConvert,LocalidadConvert)});
                    }
                    conectionServer2().close();
                }
                catch (Exception e){

                }
            }
        }
        return dataReportOne;
    }
}