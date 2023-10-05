package lk.ijse.dep11.jasper.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.dep11.jasper.dto.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class MainViewController {
    public Spinner<Integer> txtRecords;
    public Button btnViewReport;
    public Button btnExportReport;
    public AnchorPane root;

    private final ArrayList<Item> itemList = new ArrayList<>();

    public void initialize(){
        txtRecords.setValueFactory(new SpinnerValueFactory
                .IntegerSpinnerValueFactory(1, 10, 1, 1));

        itemList.add(new Item("I-1234", "Logitech Mouse", 5, new BigDecimal("850")));
        itemList.add(new Item("I-2234", "Logitech Keyboard", 2, new BigDecimal("950")));
        itemList.add(new Item("I-3234", "Casio Watch", 7, new BigDecimal("7500")));
        itemList.add(new Item("I-4234", "HD Web Camera", 3, new BigDecimal("2500")));
    }

    public void btnViewReportOnAction(ActionEvent actionEvent) throws JRException {
        JasperViewer.viewReport(getJasperPrint(), false);
    }

    private JasperPrint getJasperPrint() throws JRException {
        JasperDesign jasperDesign = JRXmlLoader
                .load(getClass().getResourceAsStream("/report/hello-jasper-2.jrxml"));

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("invoiceId", "Invoice #5");
        parameters.put("name", "Kasun Sampath");
        parameters.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd")));

        return JasperFillManager
                .fillReport(jasperReport, parameters , new JRBeanCollectionDataSource(itemList));
    }

    public void btnExportReportOnAction(ActionEvent actionEvent) throws JRException {
        FileChooser.ExtensionFilter htmlFilter = new FileChooser.ExtensionFilter("HTML File (*.html)", "*.html");
        FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF File (*.pdf)", "*.pdf");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export");
        fileChooser.getExtensionFilters().addAll(htmlFilter, pdfFilter);
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (file == null) return;

        String filePath = file.getAbsolutePath();
        if (fileChooser.getSelectedExtensionFilter() == htmlFilter){
            if (!filePath.endsWith(".html")) filePath += ".html";
            JasperExportManager.exportReportToHtmlFile(getJasperPrint(), filePath);
        }else {
            if (!filePath.endsWith(".pdf")) filePath += ".pdf";
            JasperExportManager.exportReportToPdfFile(getJasperPrint(), filePath);
        }
        new Alert(Alert.AlertType.INFORMATION, "Exported").show();
    }
}
