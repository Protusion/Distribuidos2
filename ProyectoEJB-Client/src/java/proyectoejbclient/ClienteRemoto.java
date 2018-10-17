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
 * @author Alberto
 */
public class ClienteRemoto {

    //@Resource(mappedName = "jms/Noticias")
    public Topic t;
    //@Resource(mappedName = "jms/FactoriaConexiones")
    public TopicConnectionFactory tcf;

    public TopicConnection tconnection;
    public Context contexto = null;
    public TopicSession tsession;
    public TextMessage mm;

    public boolean inicializaCliente() throws JMSException {
        try {
            if (contexto == null) {
                contexto = new InitialContext();
                tcf = (TopicConnectionFactory) contexto.lookup("jms/FactoriaConexiones");
                t = (Topic) contexto.lookup("jms/Noticias");
                tconnection = tcf.createTopicConnection();
                tsession = tconnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            }
        } catch (NamingException e) {
            System.out.println("Exception:" + e);
            contexto = null;
            return false;
        }
        return true;
    }

}
