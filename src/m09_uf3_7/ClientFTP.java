/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m09_uf3_7;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author Daniel
 */
public class ClientFTP {
   private String server;
    private int port;
    private String user;
    private String pass;
    private FTPClient ftp;
    private boolean login;

    public ClientFTP(String server, int port, String user, String pass) throws IOException {
        this.server = server;
        this.port = port;
        this.user = user;
        this.pass = pass;

        ftp = new FTPClient();
        
        conectar();

    }

    private void conectar() throws IOException {
        //realitzar conexió amb connect(), ip i port
       // --CODE--
        ftp.connect(server, port);
       
        //realitzar login amb user i password.
        //--CODE--
        login = ftp.login(user, pass);
        //Comprobació de connexió amb valor de retorn de login
        if (login) {
            System.out.println("Connexió realitzada correctament!");
        } else {
            System.out.println("No s'ha pogut conectar... revisa la configuració!");
        }
        
        //Comprobació de valor de connexió amb els metode getReplyCode() i isPositiveCompletion(reply)
        //--CODE--
       int x = ftp.getReplyCode();
       
       if(FTPReply.isPositiveCompletion(x)){
           System.out.println("se a conectao");
       }else{
           System.out.println("no se a conectao");
       }
    }
    
    //cambiar a directori rebut per parametre amb changeWorkingDirectory()
    public void setDirectorio(String directorio) throws IOException {
       // --CODE--
       ftp.changeWorkingDirectory(directorio);
    }
    
    //obtenir el llistat de fitxers i directoris amb listFiles() i printWorkingDirectory()
    public List<String> listar() throws IOException {
        List<String> lista = new ArrayList<>(); // creamos una array List
        if (this.login) { // si es true
           // --CODE--
           String lista2 = ftp.printWorkingDirectory();
           FTPFile[] listars = ftp.listFiles(lista2);
           for (FTPFile file : listars) {
            String details = file.getName();
            System.out.println(details);
           }
        } else {
            System.out.println("No logeat...");
        }
        return lista;
    }
    
    //activar enviamente en mode binari amb setFileType()
    public void activarEnvio() throws IOException {
        //--CODE--
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
    }
    
    //pujar fitxer al servidor. BufferedInputStream, FileInputStream, enterLocalPassiveMode() i storeFile()
    public void enviarFichero(String ruta) throws FileNotFoundException, IOException {
       // --CODE--
       BufferedInputStream bis = new BufferedInputStream(new FileInputStream(ruta));
       ftp.enterLocalPassiveMode();
       ftp.storeFile(ruta, bis);
    }
    
    public void bajarFichero (String ruta) throws FileNotFoundException, IOException{

        FileOutputStream fichero = new FileOutputStream(ruta);
       try (BufferedOutputStream buffOut = new BufferedOutputStream(fichero)) {
           boolean m = ftp.retrieveFile("\\src", buffOut);
           System.out.println(m);
           if(m)
               System.out.println("Descarga correcta");
           else
               System.out.println("Error Descarga");
       }
   
    }
    
    //tancar la sessió
    public void cerrarSesion() throws IOException {
        //--CODE--
        ftp.logout();
    }
    
    //desconectar del servidor
    public void desconectarServidor() throws IOException {
        //--CODE--
        ftp.disconnect();
    }
}