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

    @Resource(mappedName = "jms/FactoriaConexiones")
    public TopicConnectionFactory tcf;
    @Resource(mappedName = "jms/Noticias")
    public Topic t;
    public TopicPublisher publisher;
    public TopicConnection top;
    public TopicSession session;
    public int count_msg = 0;
    public LinkedList<String> list_msg = new LinkedList<String>();
    public TextMessage mm;

    @PostConstruct
    void inicializacion() {
        try {
            String cadena;
            FileReader f = new FileReader("mensajes.txt");
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) {
                list_msg.add(cadena);
                count_msg++;
            }
            b.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void addMsg(String m) {
        try {
            top = tcf.createTopicConnection();
            session = top.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            publisher = session.createPublisher(t);
            mm = session.createTextMessage(m);
            top.start();
            publisher.publish(mm);
            top.close();
        } catch (JMSException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public int getNumber() {
        return count_msg;
    }

    @Override
    public LinkedList<String> getList() {
        return list_msg;
    }

    @Override
    public void addToList(String m) {
        list_msg.add(m);
        count_msg++;
    }

}
