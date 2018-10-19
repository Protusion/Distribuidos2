/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author usuario
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/Noticias")
    ,
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Noticias")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/Noticias")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class EjbMessageBean implements MessageListener {

    @EJB
    private EjBSessionBeanLocal ejBSessionBean; // Acceso a EjBSessionBean

    public EjbMessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        String cadena = null; // Instanciamos un string para guardar el mensaje
        try {
            cadena = message.getBody(String.class); // Obtenemos el texto del mensaje y lo metemos en el String cadena
            cadena = (ejBSessionBean.getNumber() +1) + " " +cadena + "\n"; // Damos formato al mensaje
        } catch (JMSException ex) {
            System.out.println(ex); // Tratamiento de excepciones
        }
        try {
            File file = new File("mensajes.txt");  // Cargamos el fichero
            BufferedWriter writer; // Creamos el buffer de escritura
            if (!file.exists()) { // Comprobamos que el fichero existe, si no existe entonces:
                writer = new BufferedWriter(new FileWriter("mensajes.txt")); // Creamos el fichero mensajes.txt
                writer.write(cadena); // Escribimos el mensaje
            }else{ // Si existe: 
                writer = new BufferedWriter(new FileWriter("mensajes.txt",true)); // Abrimos el fichero en modo append
                writer.append(cadena); // Añadimos el nuevo mensaje
            }
            writer.close(); // Cerramos el fichero
        } catch (IOException e) {
            e.printStackTrace(); // Tratamiento de excepciones
        }

        ejBSessionBean.addToList(cadena); // Añadimos el mensaje a la lista de mensajes que implementa EjBSessionBean

    }
}
