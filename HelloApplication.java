package com.example.zov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class HelloApplication extends Application {

    public static String filePlace = "";
    public static String bpmnFilePlace = "";
    public static boolean flag = false;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 480, 604.8);
        Image image = new Image("D:/Kursach/ZoV/src/main/resources/icons/icon5.png");

        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.setTitle("Create bna");
        stage.show();
    }

    public static void getBPMNLink(Stage stage)
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("BPMN Files", "*.bpmn")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        bpmnFilePlace = String.valueOf(selectedFile);

        bpmnFilePlace = bpmnFilePlace.replace('\\', '/');


        System.out.println(bpmnFilePlace); // ссылка на файл
        Test.Check(bpmnFilePlace);

    }

    public static void getLink(Stage stage)
    {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        filePlace = String.valueOf(selectedFile);

        filePlace = filePlace.replace('\\', '/');


        System.out.println(filePlace); // ссылка на файл

        if (filePlace == "null")
        {
            System.out.println("Файл не выбран");
            flag = true;
        }

        else
        {
            flag = false;
            try {
                File inputFile = new File("script.py");
                File tempFile = new File("temp.py");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String currentLine;


                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.contains("with open('') as file:")) {

                        currentLine = currentLine.replace("with open('') as file:", "with " +
                                "open('" + filePlace + "') as file:");

                    }
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();


                if (!inputFile.delete()) {
                    System.out.println("Не удалось удалить исходный файл");
                    return;
                }
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Не удалось переименовать временный файл в исходный");
                }
                System.out.println("Файл успешно изменен");
            } catch (IOException exception) {
                System.out.println("Ошибка");
            }
        }

    }
    public static void runPythonCode() {

        try {
            File inputFile = new File("script.py");
            File tempFile = new File("temp.py");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;


            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains("with open('smartcontracts/.cto', 'w') as f:")) {

                    currentLine = currentLine.replace( "with open('smartcontracts/.cto', 'w') as f:", "with open('smartcontracts/" + HelloController.names +".cto', 'w') as f:");

                }
                if (currentLine.contains("with open('smartcontracts/.acl', 'w') as f:")) {

                    currentLine = currentLine.replace( "with open('smartcontracts/.acl', 'w') as f:", "with open('smartcontracts/" + HelloController.names +".acl', 'w') as f:");

                }
                if (currentLine.contains("cto_file = 'smartcontracts/.cto'")) {

                    currentLine = currentLine.replace( "cto_file = 'smartcontracts/.cto'", "cto_file = 'smartcontracts/" + HelloController.names +".cto'");

                }
                if (currentLine.contains("acl_file = 'smartcontracts/.acl'")) {

                    currentLine = currentLine.replace( "acl_file = 'smartcontracts/.acl'", "acl_file = 'smartcontracts/" + HelloController.names +".acl'");

                }
                if (currentLine.contains("bna_file = 'smartcontracts/.bna'")) {

                    currentLine = currentLine.replace( "bna_file = 'smartcontracts/.bna'", "bna_file = 'smartcontracts/" + HelloController.names +".bna'");

                }

                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();


            if (!inputFile.delete()) {
                System.out.println("Не удалось удалить исходный файл");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Не удалось переименовать временный файл в исходный");
            }
            System.out.println("Файл успешно изменен");
        } catch (IOException exception) {
            System.out.println("Ошибка");
        }

        try {

            Process process = Runtime.getRuntime().exec("python script.py");
            int exitCode = process.waitFor();
            System.out.println("Python скрипт завершил выполнение с кодом: " + exitCode);
            if (exitCode != 0) flag = true;
            else flag = false;
        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
        }

        try {
            File inputFile = new File("script.py");
            File tempFile = new File("temp.py");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            // Здесь вы можете указать условие, при котором нужно изменить строку
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains("with open('" + filePlace + "') as file:")) {
                    // Здесь вы можете изменить строку на ваше усмотрение
                    currentLine = currentLine.replace( "with open('" + filePlace + "') as file:", "with open('') as file:");

                }

                if (currentLine.contains("with open('smartcontracts/" + HelloController.names +".cto', 'w') as f:")) {
                    // Здесь вы можете изменить строку на ваше усмотрение
                    currentLine = currentLine.replace("with open('smartcontracts/" + HelloController.names +".cto', 'w') as f:", "with open('smartcontracts/.cto', 'w') as f:");

                }
                if (currentLine.contains("with open('smartcontracts/" + HelloController.names +".acl', 'w') as f:")) {
                    // Здесь вы можете изменить строку на ваше усмотрение
                    currentLine = currentLine.replace("with open('smartcontracts/" + HelloController.names +".acl', 'w') as f:",  "with open('smartcontracts/.acl', 'w') as f:");

                }

                if (currentLine.contains("cto_file = 'smartcontracts/" + HelloController.names +".cto'")) {
                    // Здесь вы можете изменить строку на ваше усмотрение
                    currentLine = currentLine.replace("cto_file = 'smartcontracts/" + HelloController.names +".cto'", "cto_file = 'smartcontracts/.cto'" );

                }
                if (currentLine.contains("acl_file = 'smartcontracts/" + HelloController.names +".acl'")) {
                    // Здесь вы можете изменить строку на ваше усмотрение
                    currentLine = currentLine.replace("acl_file = 'smartcontracts/" + HelloController.names +".acl'", "acl_file = 'smartcontracts/.acl'");

                }
                if (currentLine.contains("bna_file = 'smartcontracts/" + HelloController.names +".bna'")) {
                    // Здесь вы можете изменить строку на ваше усмотрение
                    currentLine = currentLine.replace( "bna_file = 'smartcontracts/" + HelloController.names +".bna'", "bna_file = 'smartcontracts/.bna'");

                }

                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();

            // Заменяем исходный файл измененным
            if (!inputFile.delete()) {
                System.out.println("Не удалось удалить исходный файл");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Не удалось переименовать временный файл в исходный");
            }
            System.out.println("Файл успешно изменен");
        } catch (IOException exception) {
            System.out.println("Ошибка");
        }

    }

    public static void main(String[] args) {
        launch();
    }
}
