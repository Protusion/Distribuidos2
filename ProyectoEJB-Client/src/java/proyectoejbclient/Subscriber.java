/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoejbclient;

import javax.jms.*;

/**
 *
 * @author Alberto
 */
public class Subscriber {

    public static void main(String[] args) {
        TopicSubscriber tsubscriber;
        ClienteRemoto cliente;
        boolean ok;
        MessageListener listener = new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException ex) {
                    System.out.println(ex);
                }
            }
        };

        try {
            cliente = new ClienteRemoto();
            ok = cliente.inicializaCliente();
            tsubscriber = cliente.tsession.createSubscriber(cliente.t);
            while (ok) {
                //tsubscriber.setMessageListener(listener);
                cliente.tconnection.start();
                tsubscriber.setMessageListener(listener);

            }
        } catch (JMSException e) {
            System.out.println(e);
        }
    }
}
