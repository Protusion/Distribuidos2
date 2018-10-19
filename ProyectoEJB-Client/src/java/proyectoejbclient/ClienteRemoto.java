/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoejbclient;

import javax.annotation.Resource;
import javax.jms.*;
import javax.naming.*;


/**
 *
 * @author usuario
 */
public class ClienteRemoto {

    public Topic t; // Creamos el topic
    public TopicConnectionFactory tcf; // Creamos la factoria de conexiones
    public TopicConnection tconnection; // Creamos la conexion
    public Context contexto = null; // Creamos el contexto JNDI
    public TopicSession tsession; // Creamos la sesión

    public boolean inicializaCliente() throws JMSException { // Creamos un método para inicializar el cliente
        try {
            if (contexto == null) { 
                // Aun no se ha realizado la inicializacion. Una vez realizada
                // no tiene sentido volver a realizarla.
                contexto = new InitialContext(); // Obtiene el contexto JNDI
                // obtiene factoria de conexiones 
                tcf = (TopicConnectionFactory) contexto.lookup("jms/FactoriaConexiones");
                // obtiene conexion con el topico Noticias
                t = (Topic) contexto.lookup("jms/Noticias");
                // crea la conexion 
                tconnection = tcf.createTopicConnection();
                // crea la sesión
                tsession = tconnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
                tconnection.start(); // Activamos la conexion para empezar
            }
        } catch (NamingException e) {
            System.out.println("Exception:" + e); // Tratamiento de excepciones
            contexto = null;
            return false;
        }
        return true;
    }

}
