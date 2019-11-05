package com.ainur;

import com.ainur.Model.*;
import com.ainur.Model.responses.StatusResponse;
import com.ainur.util.HttpStatus;
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
    static String token;

    public static void main(String[] args) {
        gson = new Gson();


        try {

            Socket clientSocket = new Socket("localhost", 8000);

            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            scanner = new Scanner(System.in);
            System.out.println("Команды: signup = 0, signin = 1, disconnect = 4, publish = 2, subscribe = 3, createChannel = 5");
            System.out.println("Введите команду:");
            int command = scanner.nextInt();


            while (command >= 0) {

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

                System.out.println("Команды: signup = 0, signin = 1, disconnect = 4, publish = 2, subscribe = 3, createChannel = 5");
                System.out.println("Введите команду:");
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
            StatusResponse response = gson.fromJson(reader.readLine(), StatusResponse.class);
            if (response.getStatusCode() == HttpStatus.FORBIDDEN)
                System.out.println("ПОльзователь существует");
            else if (response.getStatusCode() == HttpStatus.OK)
                System.out.println("Регистрация прошла успешно");
            if (response.getToken() != null)
                token = response.getToken();
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
            StatusResponse response = gson.fromJson(reader.readLine(), StatusResponse.class);
            if (response.getToken() != null)
                token = response.getToken();
            System.out.println(response.getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void disconnect() {


        Message message = new Message();
        DisconnectMessage disconnectMessage = new DisconnectMessage();
        disconnectMessage.setToken(token);

        message.setCommand(MessageType.DISCONNECT);
        message.setData(gson.toJson(disconnectMessage, DisconnectMessage.class));

        try {
            writer.write(gson.toJson(message, Message.class) + "\n");
            writer.flush();
            StatusResponse response = gson.fromJson(reader.readLine(), StatusResponse.class);
            System.out.println(response.getStatusCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void publish() {
        Message message = new Message();
        PublishMessage publishMessage = new PublishMessage();
        publishMessage.setToken(token);
        System.out.println("Название канала:");
        publishMessage.setChannelName(scanner.next());
        System.out.println("Текст сообщения:");
        publishMessage.setChannelName(scanner.next());

        publishMessage.setDate();
        message.setCommand(MessageType.PUBLISH);
        message.setData(gson.toJson(publishMessage, PublishMessage.class));


        try {
            writer.write(gson.toJson(message, Message.class) + "\n");
            writer.flush();
            StatusResponse response = gson.fromJson(reader.readLine(), StatusResponse.class);
            System.out.println(response.getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void subscribe() {
        Message message = new Message();
        SubscribeMessage subscribeMessage = new SubscribeMessage();
        subscribeMessage.setToken(token);
        System.out.println("Название канала:");
        subscribeMessage.setChannelName(scanner.next());

        message.setCommand(MessageType.SUBSCRIBE);
        message.setData(gson.toJson(subscribeMessage, SubscribeMessage.class));

        try {
            writer.write(gson.toJson(message, Message.class) + "\n");
            writer.flush();
            StatusResponse response = gson.fromJson(reader.readLine(), StatusResponse.class);
            System.out.println(response.getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void createChannel() {
        Message message = new Message();

        CreateChannelMessage channelMessage = new CreateChannelMessage();
        channelMessage.setToken(token);
        System.out.println("Название канала:");
        channelMessage.setChannelName(scanner.next());

        message.setCommand(MessageType.CREATE_CHANNEL);
        message.setData(gson.toJson(channelMessage, CreateChannelMessage.class));

        try {
            writer.write(gson.toJson(message, Message.class) + "\n");
            writer.flush();
            StatusResponse response = gson.fromJson(reader.readLine(), StatusResponse.class);
            System.out.println(response.getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
