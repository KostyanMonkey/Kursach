package com.example.zov;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class HelloController{
    public static String names;
    @FXML
    private Button ChooseFIle;

    @FXML
    private Button ChooseFIleBPMN;

    @FXML
    private Text TextInfoBPMN;

    @FXML
    private Button ConvertFiles;

    @FXML
    private ImageView Good;

    @FXML
    private Text TextInfo;

    @FXML
    private TextField NameOfFiles;

    @FXML
    private ImageView Bad;

    @FXML
    void initialize()
    {
        Stage stage = new Stage();
        ChooseFIleBPMN.setOnAction(event -> {
            HelloApplication.getBPMNLink(stage);
            if (Test.bpmnValid)
            {
                TextInfoBPMN.setText("Бизнес-процесс непротиворечивый");
                Bad.setVisible(false);
            }
            else
            {
                TextInfoBPMN.setText("Бизнес-процесс противоречивый");
                Bad.setVisible(true);
            }
        });


        ChooseFIle.setOnAction(event -> {

            Good.setVisible(false);
            if (Test.bpmnValid)
            {
                HelloApplication.getLink(stage);
                if (HelloApplication.filePlace == "null")
                {
                    TextInfo.setText("Файл не выбран!");
                    Bad.setVisible(true);
                }
                else {
                    TextInfo.setText(HelloApplication.filePlace);
                    Bad.setVisible(false);
                }
            }
            else Bad.setVisible(false);


        });
        ConvertFiles.setOnAction(event -> {
            names = NameOfFiles.getText();
            if (names == "") names = "noname";
            if (HelloApplication.filePlace != "null" && HelloApplication.filePlace != "" && Test.bpmnValid) {
                HelloApplication.runPythonCode();
                Good.setVisible(true);
            }
            if (HelloApplication.flag)
            {
                Bad.setVisible(true);
                Good.setVisible(false);
            }
            TextInfo.setText("Выберите CSV файл");
        });
    }
}
