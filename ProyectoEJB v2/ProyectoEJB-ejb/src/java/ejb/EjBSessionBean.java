/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.jms.*;

/**
 *
 * @author usuario
 */
@Singleton
public class EjBSessionBean implements EjBSessionBeanLocal {

    @Resource(mappedName = "jms/FactoriaConexiones") //direccion de la cola Factoria de Conexiones.
    public TopicConnectionFactory tcf; // factoria de conexiones de topics.
    @Resource(mappedName = "jms/Noticias") //direccion de la cola Noticia. 
    public Topic t; // topic de noticias.
    public TopicPublisher publisher; // publiser que publicará las noticias.
    public TopicConnection top; // conexion del topic noticia.
    public TopicSession session; // sesion del topic noticia.
    
    public int count_msg = 0; // contador de mensajes.
    public LinkedList<String> list_msg = new LinkedList<String>(); // lista para guardar los mensajes. 
    public TextMessage mm; // mensaje tipo TextMessage que enviará el publisher

    @PostConstruct
    void inicializacion() {
        try {
            String cadena; // Cadena que almacena cada linea de texto
            FileReader f = new FileReader("mensajes.txt");  // Cargamos el fichero
            BufferedReader b = new BufferedReader(f); // Enlazamos el fichero con el buffer
            while ((cadena = b.readLine()) != null) { // Leemos una línea y comprobamos que no esta vacía
                list_msg.add(cadena);  // Guardamos la línea en la lista de mensajes
                count_msg++; // Aumentamos el contador
            }
            b.close(); // Cerramos el fichero
        } catch (FileNotFoundException e) { // Control de excepciones
            System.out.println(e);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addMsg(String m) {
        try {
            top = tcf.createTopicConnection(); // Creamos la conexion
            session = top.createTopicSession(false, Session.AUTO_ACKNOWLEDGE); // Creamos la sesión
            publisher = session.createPublisher(t); // Creamos el publisher
            mm = session.createTextMessage(m); // Creamos el TextMessage a partir del String pasado por parámetro
            top.start(); // Iniciamos la conexion 
            publisher.publish(mm); // El publisher envia el mensaje
            top.close(); // Cerramos la conexión 
        } catch (JMSException ex) {
            System.out.println(ex); // Control de excepciones
        }

    }

    @Override
    public int getNumber() {
        return count_msg; // Devolvemos el contador de mensajes
    }

    @Override
    public LinkedList<String> getList() {
        return list_msg; // Devolvemos la lista de mensajes
    }

    @Override
    public void addToList(String m) {
        list_msg.add(m); //Introducimos un nuevo mensaje a la lista
        count_msg++; // Aumentamos el contador de mensajes
    }

}
