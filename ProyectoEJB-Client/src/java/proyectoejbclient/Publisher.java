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
 * @author usuario
 */
public class Publisher {
    
        
    public static void main(String[] args){
        ClienteRemoto cliente; // Creamos la conexion mediante ClienteRemoto
        boolean ok; // Comprobación retorno
        TopicPublisher tpublisher; // Creamos al publisher
        TextMessage message; // Creamos el mensaje
        Scanner sc = new Scanner(System.in); // Creamos una variable de tipo scanner para poder
        // leer por pantalla los mensajes que publica el cliente Remoto
        try{
            cliente = new ClienteRemoto(); // Crea objeto de la clase ClienteRemoto
            ok = cliente.inicializaCliente(); // Inicializa parametros
            tpublisher = cliente.tsession.createPublisher(cliente.t); // Preparamos al publisher
            
            while(ok){ // Bucle infinito para que pueda publicar tantas veces como quiera
                String cadena = sc.nextLine(); // Mensaje que lee por pantalla
                message = cliente.tsession.createTextMessage(); // Inicializamos la clase TextMessage
                message.setText(cadena); // Le introducimos el mensaje obtenido por pantalla 
                tpublisher.publish(message); // Envia el mensaje
            }
        }catch(JMSException e){
            System.out.println(e); // Tratamiento de excepciones
        }
    }
    
}
