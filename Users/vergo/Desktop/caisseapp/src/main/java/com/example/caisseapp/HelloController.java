package com.example.caisseapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class HelloController implements Initializable {
    ObservableList<String> choiseList = FXCollections.observableArrayList(
            "Makloub", "Melaoui", "Chapati", "Pizza", "Tabouna"
    );

    ObservableList<Plat> observableList = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextArea invoice;
    @FXML
    private Label welcomeText;
    @FXML
    private TableColumn<Plat, Integer> price;
    @FXML
    private TableColumn<Plat, Integer> quantity;
    @FXML
    private TableColumn<Plat, String> platName;
    @FXML
    private TableView<Plat> table;
    @FXML
    private TextField prix;
    @FXML
    private TextField quantite;


    @FXML
    protected void calcul() {


        observableList.add(new Plat(choiceBox.getValue(), parseInt(prix.getText()), parseInt(quantite.getText())));

        invoice.setText("***Welcome Chez Ezzdine *** \n " + "\n" + new Date() + "\n");
        int total = 0;
        for (int i = 0; i < table.getItems().size(); i++) {
            System.out.println(platName.getCellData(i) + " " + price.getCellData(i) + "  X" + quantity.getCellData(i));
            total += price.getCellData(i) * quantity.getCellData(i);
            invoice.setText(invoice.getText() + price.getCellData(i) + " X " + quantity.getCellData(i) + " " + platName.getCellData(i) + "\n");
        }

        System.out.println("le total est de " + total);
        invoice.setText(invoice.getText() + "\n --------" + "\n prix : " + total + " Dt" + "\n \n \n ***Thank you***");
        prix.setText("");
        quantite.setText("");
        saveToExcel();

    }


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void saySomethingElse() {
        welcomeText.setText("we are here to by something ");
    }

    @FXML
    public void print() {
        Stage stage = new Stage(StageStyle.DECORATED);
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        Printer printer = printerJob.getPrinter();
        Paper paper = printerJob.getJobSettings().getPageLayout().getPaper();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        double scaleX
                = pageLayout.getPrintableWidth() / invoice.getWidth();
        double scaleY
                = pageLayout.getPrintableHeight() / invoice.getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        invoice.getTransforms().add(scale);

        boolean printed = printerJob.printPage(pageLayout, invoice);
        boolean dialog = printerJob.showPrintDialog(stage);


        if (printed) {
            printerJob.endJob();

        }
        invoice.getTransforms().remove(scale);
    }

    public  void saveToExcel(){
         String excelFilePath = "C:\\Users\\vergo\\WebstormProjects\\recette.xlsx";
         try{
             FileInputStream fileInputStream = new FileInputStream(excelFilePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream);
             Sheet sheet = workbook.getSheetAt(0);
             int laRowcount = sheet.getLastRowNum();
             for (int i =0;i<table.getItems().size();i++){
                 Row dataRow =sheet.createRow(++laRowcount);
                 dataRow.createCell(0).setCellValue(platName.getCellData(i));
                 dataRow.createCell(1).setCellValue(price.getCellData(i));
                 dataRow.createCell(2).setCellValue(quantity.getCellData(i));
             }
             fileInputStream.close();
             FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
             workbook.write(fileOutputStream);
             fileOutputStream.close();


         } catch (Exception e){
             e.printStackTrace();

         }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        platName.setCellValueFactory(new PropertyValueFactory<>("PlatName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));


        choiceBox.setItems(choiseList);
        table.setItems(observableList);

    }
    @FXML
    public  void nouvelleComamnde(){
        observableList.clear();
        table.getItems().clear();
        invoice.setText("");
        System.out.println(table.getItems().size());


    }
}