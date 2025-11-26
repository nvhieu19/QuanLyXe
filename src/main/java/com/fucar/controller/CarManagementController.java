package com.fucar.controller;

import com.fucar.entity.Car;
import com.fucar.entity.CarProducer;
import com.fucar.service.CarService;
import com.fucar.service.CarProducerService;
import com.fucar.util.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.List;

public class CarManagementController {
    
    @FXML private TableView<Car> tblCar;
    @FXML private TableColumn<Car, Integer> colCarId;
    @FXML private TableColumn<Car, String> colCarName;
    @FXML private TableColumn<Car, Integer> colYear;
    @FXML private TableColumn<Car, String> colProducer;
    @FXML private TableColumn<Car, Double> colPrice;
    @FXML private TableColumn<Car, String> colStatus;
    @FXML private TableColumn<Car, Integer> colCapacity;
    @FXML private TableColumn<Car, String> colColor;
    @FXML private TableColumn<Car, LocalDate> colImportDate;
    
    @FXML private TextField txtCarName;
    @FXML private TextField txtYear;
    @FXML private TextField txtColor;
    @FXML private TextField txtCapacity;
    @FXML private TextArea txtDescription;
    @FXML private DatePicker dpImportDate;
    @FXML private TextField txtProducerName;
    @FXML private TextField txtProducerAddress;
    @FXML private TextField txtProducerCountry;
    @FXML private TextField txtRentPrice;
    @FXML private ComboBox<Car.Status> cmbStatus;
    
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;
    
    private CarService carService;
    private CarProducerService producerService;
    private ObservableList<Car> carList;
    private Car selectedCar;
    
    public CarManagementController() {
        this.carService = new CarService();
        this.producerService = new CarProducerService();
    }
    
    @FXML
    public void initialize() {
        setupTable();
        loadStatus();
        loadCars();
        
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        
        tblCar.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectCar(newVal);
            }
        });
    }
    
    private void setupTable() {
        colCarId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        colCarName.setCellValueFactory(new PropertyValueFactory<>("carName"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("carModelYear"));
        colProducer.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProducer().getProducerName()));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colImportDate.setCellValueFactory(new PropertyValueFactory<>("importDate"));
    }
    
    private void loadStatus() {
        cmbStatus.setItems(FXCollections.observableArrayList(Car.Status.values()));
    }
    
    private void loadCars() {
        carList = FXCollections.observableArrayList(carService.findAll());
        tblCar.setItems(carList);
    }
    
    private void selectCar(Car car) {
        selectedCar = car;
        txtCarName.setText(car.getCarName());
        txtYear.setText(String.valueOf(car.getCarModelYear()));
        txtColor.setText(car.getColor());
        txtCapacity.setText(String.valueOf(car.getCapacity()));
        txtDescription.setText(car.getDescription());
        dpImportDate.setValue(car.getImportDate());
        
        // Producer info
        if (car.getProducer() != null) {
            txtProducerName.setText(car.getProducer().getProducerName());
            txtProducerAddress.setText(car.getProducer().getAddress());
            txtProducerCountry.setText(car.getProducer().getCountry());
        }
        
        txtRentPrice.setText(String.valueOf(car.getRentPrice()));
        cmbStatus.setValue(car.getStatus());
        
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }
    
    @FXML
    private void handleAdd() {
        if (!validateInput()) return;
        
        try {
            // Find or create producer
            CarProducer producer = getOrCreateProducer();
            
            Car car = new Car();
            car.setCarName(txtCarName.getText().trim());
            car.setCarModelYear(Integer.parseInt(txtYear.getText().trim()));
            car.setColor(txtColor.getText().trim());
            car.setCapacity(Integer.parseInt(txtCapacity.getText().trim()));
            car.setDescription(txtDescription.getText().trim());
            car.setImportDate(dpImportDate.getValue());
            car.setProducer(producer);
            car.setRentPrice(Double.parseDouble(txtRentPrice.getText().trim()));
            car.setStatus(cmbStatus.getValue());
            
            carService.save(car);
            loadCars();
            handleClear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Car added successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    
    @FXML
    private void handleUpdate() {
        if (selectedCar == null || !validateInput()) return;
        
        try {
            // Find or create producer
            CarProducer producer = getOrCreateProducer();
            
            selectedCar.setCarName(txtCarName.getText().trim());
            selectedCar.setCarModelYear(Integer.parseInt(txtYear.getText().trim()));
            selectedCar.setColor(txtColor.getText().trim());
            selectedCar.setCapacity(Integer.parseInt(txtCapacity.getText().trim()));
            selectedCar.setDescription(txtDescription.getText().trim());
            selectedCar.setImportDate(dpImportDate.getValue());
            selectedCar.setProducer(producer);
            selectedCar.setRentPrice(Double.parseDouble(txtRentPrice.getText().trim()));
            selectedCar.setStatus(cmbStatus.getValue());
            
            carService.update(selectedCar);
            loadCars();
            handleClear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Car updated successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    
    @FXML
    private void handleDelete() {
        if (selectedCar == null) return;
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Car");
        confirm.setContentText("Are you sure you want to delete this car?");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                carService.delete(selectedCar.getCarId());
                loadCars();
                handleClear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Car deleted successfully");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleClear() {
        selectedCar = null;
        txtCarName.clear();
        txtYear.clear();
        txtColor.clear();
        txtCapacity.clear();
        txtDescription.clear();
        dpImportDate.setValue(null);
        txtProducerName.clear();
        txtProducerAddress.clear();
        txtProducerCountry.clear();
        txtRentPrice.clear();
        cmbStatus.setValue(null);
        
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        tblCar.getSelectionModel().clearSelection();
    }
    
    private CarProducer getOrCreateProducer() {
        String producerName = txtProducerName.getText().trim();
        String address = txtProducerAddress.getText().trim();
        String country = txtProducerCountry.getText().trim();
        
        // Try to find existing producer by name
        List<CarProducer> allProducers = producerService.findAll();
        for (CarProducer p : allProducers) {
            if (p.getProducerName().equalsIgnoreCase(producerName)) {
                // Update producer info if different
                if (!p.getAddress().equals(address) || !p.getCountry().equals(country)) {
                    p.setAddress(address);
                    p.setCountry(country);
                    producerService.update(p);
                }
                return p;
            }
        }
        
        // Create new producer if not found
        CarProducer newProducer = new CarProducer(producerName, address, country);
        producerService.save(newProducer);
        return newProducer;
    }
    
    private boolean validateInput() {
        if (ValidationUtils.isNullOrEmpty(txtCarName.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Car name is required");
            return false;
        }
        
        try {
            int year = Integer.parseInt(txtYear.getText().trim());
            if (year < 1900 || year > LocalDate.now().getYear() + 1) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid model year");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Model year must be a number");
            return false;
        }
        
        try {
            int capacity = Integer.parseInt(txtCapacity.getText().trim());
            if (capacity <= 0) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Capacity must be positive");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Capacity must be a number");
            return false;
        }
        
        try {
            double price = Double.parseDouble(txtRentPrice.getText().trim());
            if (price <= 0) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Rent price must be positive");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Rent price must be a number");
            return false;
        }
        
        if (dpImportDate.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Import date is required");
            return false;
        }
        
        if (ValidationUtils.isNullOrEmpty(txtProducerName.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Producer name is required");
            return false;
        }
        
        if (ValidationUtils.isNullOrEmpty(txtProducerAddress.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Producer address is required");
            return false;
        }
        
        if (ValidationUtils.isNullOrEmpty(txtProducerCountry.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Producer country is required");
            return false;
        }
        
        if (cmbStatus.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Status is required");
            return false;
        }
        
        return true;
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}