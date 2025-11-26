package com.fucar.controller;

import com.fucar.entity.Car;
import com.fucar.entity.CarRental;
import com.fucar.entity.CarRentalId;
import com.fucar.entity.Customer;
import com.fucar.service.CarRentalService;
import com.fucar.service.CarService;
import com.fucar.service.CustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalManagementController {
    
    @FXML private TableView<CarRental> tblRental;
    @FXML private TableColumn<CarRental, String> colCustomerName;
    @FXML private TableColumn<CarRental, String> colCarName;
    @FXML private TableColumn<CarRental, LocalDate> colPickupDate;
    @FXML private TableColumn<CarRental, LocalDate> colReturnDate;
    @FXML private TableColumn<CarRental, Double> colRentPrice;
    @FXML private TableColumn<CarRental, String> colStatus;
    
    @FXML private ComboBox<Customer> cmbCustomer;
    @FXML private ComboBox<Car> cmbCar;
    @FXML private DatePicker dpPickupDate;
    @FXML private DatePicker dpReturnDate;
    @FXML private TextField txtRentPrice;
    @FXML private ComboBox<CarRental.Status> cmbStatus;
    
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnReturn;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;
    
    private CarRentalService rentalService;
    private CustomerService customerService;
    private CarService carService;
    private ObservableList<CarRental> rentalList;
    private CarRental selectedRental;
    
    public RentalManagementController() {
        this.rentalService = new CarRentalService();
        this.customerService = new CustomerService();
        this.carService = new CarService();
    }
    
    @FXML
    public void initialize() {
        setupTable();
        loadCustomers();
        loadCars();
        loadStatus();
        loadRentals();
        
        btnUpdate.setDisable(true);
        btnReturn.setDisable(true);
        btnDelete.setDisable(true);
        
        // --- CẤU HÌNH ĐỊNH DẠNG NGÀY THÁNG (dd/MM/yyyy) ---
        String pattern = "dd/MM/yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        // 1. Định dạng cho các DatePicker (Ô nhập ngày)
        StringConverter<LocalDate> dateConverter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    try {
                        return LocalDate.parse(string, dateFormatter);
                    } catch (Exception e) {
                        return null;
                    }
                }
                return null;
            }
        };

        // Áp dụng cho Ngày nhận
        dpPickupDate.setConverter(dateConverter);
        dpPickupDate.setPromptText(pattern.toLowerCase());

        // Áp dụng cho Ngày trả
        dpReturnDate.setConverter(dateConverter);
        dpReturnDate.setPromptText(pattern.toLowerCase());

        // 2. Định dạng cho các TableColumn (Cột ngày trong bảng)
        javafx.util.Callback<TableColumn<CarRental, LocalDate>, TableCell<CarRental, LocalDate>> cellFactory = column -> new TableCell<CarRental, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(dateFormatter.format(date));
                }
            }
        };

        colPickupDate.setCellFactory(cellFactory);
        colReturnDate.setCellFactory(cellFactory);
        // ---------------------------------------------------
        
        tblRental.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectRental(newVal);
            }
        });
        
        cmbCar.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtRentPrice.setText(String.valueOf(newVal.getRentPrice()));
            }
        });
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
    
    private void loadCustomers() {
        ObservableList<Customer> customers = 
            FXCollections.observableArrayList(customerService.findAll());
        cmbCustomer.setItems(customers);
        cmbCustomer.setConverter(new javafx.util.StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer == null ? "" : customer.getCustomerName();
            }
            @Override
            public Customer fromString(String string) {
                return null;
            }
        });
    }
    
    private void loadCars() {
        ObservableList<Car> cars = FXCollections.observableArrayList(carService.findAll());
        cmbCar.setItems(cars);
        cmbCar.setConverter(new javafx.util.StringConverter<Car>() {
            @Override
            public String toString(Car car) {
                return car == null ? "" : car.getCarName() + " - " + car.getStatus();
            }
            @Override
            public Car fromString(String string) {
                return null;
            }
        });
    }
    
    private void loadStatus() {
        cmbStatus.setItems(FXCollections.observableArrayList(CarRental.Status.values()));
    }
    
    private void loadRentals() {
        rentalList = FXCollections.observableArrayList(rentalService.findAll());
        tblRental.setItems(rentalList);
    }
    
    private void selectRental(CarRental rental) {
        selectedRental = rental;
        cmbCustomer.setValue(rental.getCustomer());
        cmbCar.setValue(rental.getCar());
        dpPickupDate.setValue(rental.getPickupDate());
        dpReturnDate.setValue(rental.getReturnDate());
        txtRentPrice.setText(String.valueOf(rental.getRentPrice()));
        cmbStatus.setValue(rental.getStatus());
        
        btnUpdate.setDisable(false);
        btnReturn.setDisable(false);
        btnDelete.setDisable(false);
    }
    
    @FXML
    private void handleAdd() {
        if (!validateInput()) return;
        
        try {
            CarRental rental = new CarRental();
            rental.setCustomer(cmbCustomer.getValue());
            rental.setCar(cmbCar.getValue());
            rental.setPickupDate(dpPickupDate.getValue());
            rental.setReturnDate(dpReturnDate.getValue());
            rental.setRentPrice(Double.parseDouble(txtRentPrice.getText().trim()));
            rental.setStatus(cmbStatus.getValue());
            rental.setId(new CarRentalId(
                cmbCustomer.getValue().getCustomerId(),
                cmbCar.getValue().getCarId()
            ));
            
            rentalService.save(rental);
            loadRentals();
            loadCars(); // Refresh car list to update status
            handleClear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Rental created successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    
    @FXML
    private void handleUpdate() {
        if (selectedRental == null || !validateInput()) return;
        
        try {
            selectedRental.setPickupDate(dpPickupDate.getValue());
            selectedRental.setReturnDate(dpReturnDate.getValue());
            selectedRental.setRentPrice(Double.parseDouble(txtRentPrice.getText().trim()));
            selectedRental.setStatus(cmbStatus.getValue());
            
            rentalService.update(selectedRental);
            loadRentals();
            handleClear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Rental updated successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    
    @FXML
    private void handleReturn() {
        if (selectedRental == null) return;
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Return");
        confirm.setHeaderText("Return Car");
        confirm.setContentText("Mark this rental as returned?");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                rentalService.returnCar(selectedRental.getId());
                loadRentals();
                loadCars(); // Refresh car list to update status
                handleClear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Car returned successfully");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleDelete() {
        if (selectedRental == null) return;
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Rental");
        confirm.setContentText("Are you sure you want to delete this rental?");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                rentalService.delete(selectedRental.getId());
                loadRentals();
                handleClear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Rental deleted successfully");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleClear() {
        selectedRental = null;
        cmbCustomer.setValue(null);
        cmbCar.setValue(null);
        dpPickupDate.setValue(null);
        dpReturnDate.setValue(null);
        txtRentPrice.clear();
        cmbStatus.setValue(null);
        
        btnUpdate.setDisable(true);
        btnReturn.setDisable(true);
        btnDelete.setDisable(true);
        tblRental.getSelectionModel().clearSelection();
    }
    
    private boolean validateInput() {
        if (cmbCustomer.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Customer is required");
            return false;
        }
        
        if (cmbCar.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Car is required");
            return false;
        }
        
        if (dpPickupDate.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Pickup date is required");
            return false;
        }
        
        if (dpReturnDate.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Return date is required");
            return false;
        }
        
        if (dpPickupDate.getValue().isAfter(dpReturnDate.getValue()) ||
            dpPickupDate.getValue().isEqual(dpReturnDate.getValue())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Pickup date must be before return date");
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