package lk.ijse.dep11.jasper.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;

public class MainViewController {
    public Button btnShowJasperReport;

    public void btnShowJasperReportOnAction(ActionEvent actionEvent) throws JRException {
        // JasperSoft Studio : This is where we design our report
        // Jasper Report Library : This is what helps to integrate the jasper report with our app
        JasperDesign jasperDesign = JRXmlLoader
                .load(getClass().getResourceAsStream("/report/hello-jasper.jrxml"));

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager
               // fillReport(reportRef, parametersRef, datasourceRef)
                .fillReport(jasperReport, new HashMap<>(), new JREmptyDataSource(8));

        // JasperViewer.viewReport(jasperPrint, false);
        // JasperPrintManager.printReport(jasperPrint, true);
        JasperExportManager.exportReportToPdfFile(jasperPrint,
                "/home/ranjith-suranga/Desktop/abc.pdf");
    }
}
