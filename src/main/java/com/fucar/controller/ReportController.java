package com.fucar.controller;

import com.fucar.entity.CarRental;
import com.fucar.service.CarRentalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class ReportController {
    
    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;
    @FXML private RadioButton rbSortByReturnDate;
    @FXML private RadioButton rbSortByPrice;
    @FXML private Button btnGenerate;
    @FXML private Button btnExport;
    
    @FXML private TableView<CarRental> tblReport;
    @FXML private TableColumn<CarRental, String> colCustomerName;
    @FXML private TableColumn<CarRental, String> colCarName;
    @FXML private TableColumn<CarRental, LocalDate> colPickupDate;
    @FXML private TableColumn<CarRental, LocalDate> colReturnDate;
    @FXML private TableColumn<CarRental, Double> colRentPrice;
    @FXML private TableColumn<CarRental, String> colStatus;
    
    @FXML private Label lblTotal;
    @FXML private Label lblTotalRevenue;
    
    private CarRentalService rentalService;
    private ObservableList<CarRental> reportData;
    private ToggleGroup sortGroup;
    
    public ReportController() {
        this.rentalService = new CarRentalService();
    }
    
    @FXML
    public void initialize() {
        setupTable();
        setupSortGroup();
        btnExport.setDisable(true);
    }
    
    private void setupTable() {
        colCustomerName.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCustomer().getCustomerName()));
        colCarName.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCar().getCarName()));
        colPickupDate.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colRentPrice.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    private void setupSortGroup() {
        sortGroup = new ToggleGroup();
        rbSortByReturnDate.setToggleGroup(sortGroup);
        rbSortByPrice.setToggleGroup(sortGroup);
        rbSortByReturnDate.setSelected(true);
    }
    
    @FXML
    private void handleGenerate() {
        LocalDate startDate = dpStartDate.getValue();
        LocalDate endDate = dpEndDate.getValue();
        
        if (startDate == null || endDate == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select both start and end dates");
            return;
        }
        
        if (startDate.isAfter(endDate)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Start date must be before end date");
            return;
        }
        
        try {
            List<CarRental> rentals = rentalService.findByDateRange(startDate, endDate);
            
            // Sort based on selected option
            if (rbSortByReturnDate.isSelected()) {
                rentals.sort(Comparator.comparing(CarRental::getReturnDate).reversed());
            } else {
                rentals.sort(Comparator.comparing(CarRental::getRentPrice).reversed());
            }
            
            reportData = FXCollections.observableArrayList(rentals);
            tblReport.setItems(reportData);
            
            // Calculate statistics
            lblTotal.setText("Total Rentals: " + rentals.size());
            double totalRevenue = rentals.stream()
                .mapToDouble(CarRental::getRentPrice)
                .sum();
            lblTotalRevenue.setText(String.format("Total Revenue: %.2f VND", totalRevenue));
            
            btnExport.setDisable(reportData.isEmpty());
            
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    
    @FXML
    private void handleExport() {
        if (reportData == null || reportData.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Data", "No data to export");
            return;
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Report");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        fileChooser.setInitialFileName("rental_report.csv");
        
        File file = fileChooser.showSaveDialog(btnExport.getScene().getWindow());
        
        if (file != null) {
            try {
                exportToFile(file);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Report exported successfully");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to export: " + e.getMessage());
            }
        }
    }
    
    private void exportToFile(File file) throws Exception {
        try (FileWriter writer = new FileWriter(file)) {
            // Write header
            writer.write("Customer Name,Car Name,Pickup Date,Return Date,Rent Price,Status\n");
            
            // Write data
            for (CarRental rental : reportData) {
                writer.write(String.format("%s,%s,%s,%s,%.2f,%s\n",
                    rental.getCustomer().getCustomerName(),
                    rental.getCar().getCarName(),
                    rental.getPickupDate(),
                    rental.getReturnDate(),
                    rental.getRentPrice(),
                    rental.getStatus()
                ));
            }
            
            // Write summary
            writer.write("\n");
            writer.write("Total Rentals," + reportData.size() + "\n");
            double totalRevenue = reportData.stream()
                .mapToDouble(CarRental::getRentPrice)
                .sum();
            writer.write(String.format("Total Revenue,%.2f VND\n", totalRevenue));
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}