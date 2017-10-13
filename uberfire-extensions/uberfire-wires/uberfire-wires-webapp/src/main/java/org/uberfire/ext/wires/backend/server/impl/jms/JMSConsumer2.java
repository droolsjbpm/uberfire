//package org.uberfire.ext.wires.backend.server.impl;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Logger;
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.MessageDriven;
//import javax.ejb.TransactionManagement;
//import javax.ejb.TransactionManagementType;
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.TextMessage;
//
//@TransactionManagement(TransactionManagementType.BEAN)
//@MessageDriven(name = "JMSConsumer2", activationConfig = {
//        @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/APPFORMER.CLUSTER.MESSAGE"),
//        @ActivationConfigProperty(propertyName = "destinationJndiName", propertyValue = "topic/APPFORMER.CLUSTER.MESSAGE"),
//        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
//        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
//public class JMSConsumer2 implements MessageListener {
//
//    private final static Logger LOGGER = Logger.getLogger(JMSConsumer2.class.toString());
//
//    @Override
//    public void onMessage(Message msg) {
//        String msgText = "\n   "+new Date().toString()+" JMSConsumer2  ";
//        if (msg instanceof TextMessage) {
//            try {
//                msgText += ((TextMessage) msg).getText();
//            } catch (JMSException e) {
//                e.printStackTrace();
//            }
//        } else {
//            msgText += msg.toString();
//        }
//        // File location.
//        String filePath = "/tmp/file.txt";
//
//        try {
//            Files.write(Paths.get(filePath), msgText.getBytes(), StandardOpenOption.APPEND);
//        } catch (IOException e) {
//            System.out.println("Problem occurs when deleting the directory : " + filePath);
//            e.printStackTrace();
//        }
//    }
//}
