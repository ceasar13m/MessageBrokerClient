package com.ainur;

import com.ainur.Model.DisconnectMessage;
import com.ainur.Model.Message;
import com.ainur.Model.SignInMessage;
import com.ainur.Model.SignUpMessage;
import com.ainur.util.MessageType;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    static Scanner scanner;
    static Gson gson;
    static BufferedWriter writer;
    static BufferedReader reader;

    public static void main(String[] args) {
        gson = new Gson();
        String token;


        try {

            Socket clientSocket = new Socket("localhost", 8080);

            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            scanner = new Scanner(System.in);

            int command = scanner.nextInt();
            while (command >= 0) {
                System.out.println("Команды: signup = 0, signin = 1, disconnect = 4, publish = 2, subscribe = 3, createChannel = 5");
                System.out.println("Введите команду:");


                switch (command) {
                    case MessageType.SIGN_UP: {
                        signUp();
                        break;
                    }
                    case MessageType.SIGN_IN: {
                        signIn();
                        break;
                    }
                    case MessageType.PUBLISH: {
                        publish();
                        break;
                    }
                    case MessageType.SUBSCRIBE: {
                        subscribe();
                        break;
                    }
                    case MessageType.DISCONNECT: {
                        disconnect();
                        break;
                    }
                    case MessageType.CREATE_CHANNEL: {
                        createChannel();
                        break;
                    }
                    default: {
                        break;
                    }
                }

                command = scanner.nextInt();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void signUp() {
        System.out.println("username:");
        String username = scanner.next();
        System.out.println("password:");
        String password = scanner.next();

        Message message = new Message();
        SignUpMessage signUpMessage = new SignUpMessage();
        signUpMessage.setUsername(username);
        signUpMessage.setPassword(password);

        message.setCommand(MessageType.SIGN_UP);
        message.setData(gson.toJson(signUpMessage, SignUpMessage.class));

        try {
            writer.write(gson.toJson(message, Message.class) + "\n");
            writer.flush();

            System.out.println(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static void signIn() {

        System.out.println("username:");
        String username = scanner.next();
        System.out.println("password:");
        String password = scanner.next();

        Message message = new Message();
        SignInMessage signInMessage = new SignInMessage();
        signInMessage.setUsername(username);
        signInMessage.setPassword(password);

        message.setCommand(MessageType.SIGN_IN);
        message.setData(gson.toJson(signInMessage, SignInMessage.class));

        try {
            writer.write(gson.toJson(message, Message.class) + "\n");
            writer.flush();

            System.out.println(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void disconnect() {

    }

    static void publish() {

    }

    static void subscribe() {

    }

    static void createChannel() {

    }

}
