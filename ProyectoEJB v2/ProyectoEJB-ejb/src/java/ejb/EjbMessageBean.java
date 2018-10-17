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
    private EjBSessionBeanLocal ejBSessionBean;

    public EjbMessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        String cadena = null;
        try {
            cadena = message.getBody(String.class);
            cadena = cadena + "\n";
        } catch (JMSException ex) {
            System.out.println(ex);
        }
        try {
            File file = new File("mensajes.txt");
            BufferedWriter writer;
            if (!file.exists()) {
                 writer = new BufferedWriter(new FileWriter("mensajes.txt"));
                writer.write(cadena);
            }else{
                writer = new BufferedWriter(new FileWriter("mensajes.txt",true));
                writer.append(cadena);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ejBSessionBean.addToList(cadena);

    }
}
