/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoejbclient;

import javax.jms.*;

/**
 *
 * @author usuario
 */
public class Subscriber {

    public static void main(String[] args) {
        TopicSubscriber tsubscriber; // Creamos al subscriber
        ClienteRemoto cliente; // Creamos la conexion mediante ClienteRemoto
        boolean ok; // Comprobación de retorno
        MessageListener listener = new MessageListener() {  // Creamos una isntancia de MessageListener
            @Override
            public void onMessage(Message message) { // Hacemos @Override al metodo on Message.
                TextMessage textMessage = (TextMessage) message; // Creamos una instancia de TextMessage 
                try {
                    System.out.println("Noticia: "+textMessage.getText()); // Imprimimos el mensaje
                } catch (JMSException ex) {
                    System.out.println(ex); // Tratamiento de excepciones
                }
            }
        };

        try {
            cliente = new ClienteRemoto(); // Creamos objeto de la clase ClienteRemoto
            ok = cliente.inicializaCliente(); // Inicializamos parametros
            tsubscriber = cliente.tsession.createSubscriber(cliente.t);  // Preparamos al subscriber
            while (ok) { // Bucle infinito para que reciba todas las noticas de manera asincrona
                tsubscriber.setMessageListener(listener); // Creamos la intancia de messageListener que nos mantendra a la escucha
            }
        } catch (JMSException e) {
            System.out.println(e); // Tratamiento de excepciones
        }
    }
}
