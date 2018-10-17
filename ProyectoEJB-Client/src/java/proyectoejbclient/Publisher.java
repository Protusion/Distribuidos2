/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoejbclient;

import java.util.Scanner;
import javax.jms.*;
/**
 *
 * @author Alberto
 */
public class Publisher {
    
        
    public static void main(String[] args){
        ClienteRemoto cliente;
        boolean ok;
        TopicPublisher tpublisher;
        TextMessage message;
        Scanner sc = new Scanner(System.in);
        try{
            cliente = new ClienteRemoto();
            ok = cliente.inicializaCliente();
            tpublisher = cliente.tsession.createPublisher(cliente.t);
            
            if(ok){
                String cadena = sc.nextLine();
                message = cliente.tsession.createTextMessage();
                message.setText(cadena);
                cliente.tconnection.start();
                tpublisher.publish(message);
                cliente.tconnection.close();
            }
        }catch(JMSException e){
            System.out.println(e);
        }
    }
    
}
