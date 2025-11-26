# KỊCH BẢN TRIỂN KHAI CHI TIẾT CHO TỪNG THÀNH VIÊN

Đây là kịch bản gợi ý các bước thực hiện cho từng thành viên. Mỗi người nên bắt đầu bằng việc tạo các lớp Entity, sau đó đến Repository, Service, và cuối cùng là View (.fxml) và Controller.

---

## A. Hướng dẫn chung

1.  **Clone project:** Mọi người clone project từ repository chung về máy.
2.  **Mở bằng Eclipse:** Import project vào Eclipse (hoặc IDE tương đương) dưới dạng một Maven project.
3.  **Cập nhật Maven:** Chuột phải vào project -> Maven -> Update Project để đảm bảo tất cả dependencies từ file `pom.xml` được tải về.
4.  **Làm việc trên branch cá nhân:** Mỗi thành viên tạo một branch riêng để phát triển chức năng của mình (ví dụ: `feature/login`, `feature/car-management`).
5.  **Commit thường xuyên:** Commit code lên branch cá nhân với message rõ ràng.
6.  **Pull request:** Khi hoàn thành một phần chức năng, tạo Pull Request (hoặc Merge Request) để Trưởng nhóm (Định) review và merge vào branch chính (ví dụ: `develop` hoặc `main`).

---

## B. Kịch bản chi tiết

### 1. Định (Trưởng nhóm) - Setup & Lõi hệ thống

**Mục tiêu:** Cấu hình dự án, tạo sườn ứng dụng và xây dựng giao diện trung tâm cho Admin.

*   **Bước 1: Cấu hình `pom.xml`**
    *   Mở file `pom.xml`.
    *   Rà soát và đảm bảo các thư viện cần thiết đã có: JavaFX, Hibernate, driver kết nối CSDL (ví dụ: SQL Server, MySQL).
    *   Chạy Maven Update để tải thư viện.

*   **Bước 2: Cấu hình Hibernate**
    *   Mở file `src/main/java/com/fucar/config/HibernateConfig.java`.
    *   Kiểm tra code, đảm bảo `SessionFactory` được tạo thành công.
    *   Mở `hibernate.cfg.xml` và điền đúng thông tin kết nối tới CSDL của dự án (URL, user, password).

*   **Bước 3: Tạo sườn ứng dụng**
    *   Mở `src/main/java/com/fucar/App.java`.
    *   Đây là file khởi chạy chính. Viết code để load màn hình Login (`Login.fxml`) làm màn hình đầu tiên khi ứng dụng bắt đầu.
    *   Tham khảo `SceneUtils.java` để xây dựng hàm chuyển đổi giữa các Scene (màn hình).

*   **Bước 4: Xây dựng Admin Dashboard**
    *   Mở file `AdminDashboard.fxml` bằng Scene Builder (hoặc code tay).
    *   Thiết kế giao diện chính cho admin, bao gồm các nút hoặc menu để điều hướng tới các chức năng khác (Quản lý Xe, Quản lý Khách hàng, etc.).
    *   Mở `src/main/java/com/fucar/controller/AdminDashboardController.java`.
    *   Viết các hàm `setOnAction` cho các nút bấm. Khi một nút được bấm, gọi `SceneUtils` để chuyển sang màn hình tương ứng.

### 2. Hải - Module Đăng nhập & Tài khoản

**Mục tiêu:** Hoàn thiện luồng đăng nhập, cho phép người dùng xác thực và truy cập vào hệ thống.

*   **Bước 1: Xem lại Entity**
    *   Mở `src/main/java/com/fucar/entity/Account.java`.
    *   Kiểm tra các thuộc tính (ví dụ: `username`, `password`, `role`) và các annotation của Hibernate (`@Entity`, `@Id`, `@Column`) đã chính xác chưa.

*   **Bước 2: Xây dựng Repository**
    *   Mở `src/main/java/com/fucar/repository/AccountRepository.java`.
    *   Viết hàm `findByUsername(String username)` để tìm một tài khoản trong CSDL dựa vào tên đăng nhập. Sử dụng HQL hoặc Criteria API của Hibernate.

*   **Bước 3: Xây dựng Service**
    *   Mở `src/main/java/com/fucar/service/AccountService.java`.
    *   Viết hàm `checkLogin(String username, String password)`. Hàm này sẽ:
        1.  Gọi `accountRepository.findByUsername(username)`.
        2.  Nếu tìm thấy, so sánh password người dùng nhập vào với password trong CSDL (lưu ý mã hóa password nếu có).
        3.  Trả về thông tin `Account` nếu đăng nhập thành công, hoặc `null` nếu thất bại.

*   **Bước 4: Thiết kế giao diện Login**
    *   Mở `src/main/resources/fxml/Login.fxml` bằng Scene Builder.
    *   Thiết kế giao diện gồm: 2 ô `TextField` cho username/password, 1 `Button` để đăng nhập, và 1 `Label` để hiển thị thông báo lỗi.
    *   Đặt `fx:id` cho các component này (ví dụ: `tfUsername`, `tfPassword`, `btnLogin`, `lblError`).

*   **Bước 5: Lập trình Controller**
    *   Mở `src/main/java/com/fucar/controller/LoginController.java`.
    *   Khai báo các biến tương ứng với `fx:id` đã đặt.
    *   Viết hàm xử lý sự kiện cho `btnLogin`. Trong hàm này:
        1.  Lấy text từ `tfUsername` và `tfPassword`.
        2.  Gọi `accountService.checkLogin(...)`.
        3.  Nếu thành công, dựa vào role của account để chuyển đến màn hình Admin Dashboard hoặc Customer Dashboard.
        4.  Nếu thất bại, hiển thị thông báo lỗi trên `lblError`.

### 3. Hiếu - Module Quản lý Khách hàng & Dashboard

**Mục tiêu:** Xây dựng giao diện và logic cho việc thêm, sửa, xóa, tìm kiếm khách hàng.

*   **Bước 1: Entity & Repository**
    *   Xem lại `src/main/java/com/fucar/entity/Customer.java`.
    *   Triển khai các hàm CRUD cơ bản trong `src/main/java/com/fucar/repository/CustomerRepository.java` (findAll, findById, save, update, delete).

*   **Bước 2: Service**
    *   Mở `src/main/java/com/fucar/service/CustomerService.java`.
    *   Tạo các hàm tương ứng gọi xuống Repository (ví dụ: `findAll()`, `save(Customer customer)`, `delete(int customerId)`).

*   **Bước 3: Thiết kế giao diện `CustomerManagement.fxml`**
    *   Dùng Scene Builder mở file.
    *   Thiết kế giao diện gồm:
        *   Một `TableView` để hiển thị danh sách khách hàng. Đặt `fx:id` và định nghĩa các `TableColumn` cho từng thuộc tính (ID, Tên, SĐT, ...).
        *   Các `TextField` để nhập thông tin cho khách hàng mới hoặc chỉnh sửa.
        *   Các `Button` "Thêm", "Sửa", "Xóa", "Làm mới".

*   **Bước 4: Lập trình `CustomerManagementController.java`**
    *   Viết hàm `initialize()` để load danh sách khách hàng từ `customerService.findAll()` và hiển thị lên `TableView`.
    *   Viết hàm xử lý sự kiện cho các nút "Thêm", "Sửa", "Xóa". Các hàm này sẽ lấy thông tin từ `TextField`, tạo đối tượng `Customer`, gọi service tương ứng và cuối cùng là refresh lại `TableView`.
    *   Lập trình cho sự kiện click chuột vào một hàng trên `TableView` để đổ dữ liệu lên các `TextField`, sẵn sàng cho việc sửa/xóa.

*   **Bước 5: Dashboard khách hàng**
    *   Thiết kế giao diện `CustomerDashboard.fxml` để hiển thị thông tin của khách hàng đang đăng nhập, hoặc các chức năng dành riêng cho họ.
    *   Controller `CustomerDashboardController.java` sẽ nhận dữ liệu (ví dụ: đối tượng `Account`) từ màn hình Login để biết ai đang đăng nhập.

### 4. Long - Module Quản lý Xe

**Mục tiêu:** Xây dựng chức năng quản lý thông tin xe và nhà sản xuất.

*   **Bước 1: Entity & Repository**
    *   Xem lại `Car.java` và `CarProducer.java`. Chú ý mối quan hệ giữa chúng (`@ManyToOne`).
    *   Triển khai các hàm CRUD trong `CarRepository.java` và `CarProducerRepository.java`.

*   **Bước 2: Service**
    *   Xây dựng `CarService.java` và `CarProducerService.java` với các logic nghiệp vụ cần thiết. Ví dụ, `CarService` có thể cần hàm `findByProducer(int producerId)`. `CarProducerService` cần hàm `findAll()`.

*   **Bước 3: Thiết kế giao diện `CarManagement.fxml`**
    *   Tương tự màn hình quản lý khách hàng, thiết kế một `TableView` để hiển thị danh sách xe.
    *   Thêm các `TextField` cho thông tin xe.
    *   Đặc biệt, dùng một `ComboBox` để hiển thị danh sách các nhà sản xuất (`CarProducer`). Dữ liệu cho ComboBox này sẽ được load từ `carProducerService.findAll()`.

*   **Bước 4: Lập trình `CarManagementController.java`**
    *   Trong hàm `initialize()`, load dữ liệu cho cả `TableView` (danh sách xe) và `ComboBox` (danh sách nhà sản xuất).
    *   Viết logic cho các nút "Thêm", "Sửa", "Xóa". Khi thêm/sửa xe, cần lấy đối tượng `CarProducer` được chọn từ `ComboBox`.
    *   Thêm chức năng tìm kiếm xe (theo tên, theo hãng, etc.).

### 5. Quang - Module Thuê xe, Báo cáo & Đánh giá

**Mục tiêu:** Xây dựng các chức năng phức tạp liên quan đến nghiệp vụ thuê xe và thống kê.

*   **Bước 1: Entity & Repository**
    *   Xem lại các entity `CarRental.java`, `CarRentalId.java`, `Review.java`. Chú ý các mối quan hệ phức tạp.
    *   Xây dựng `CarRentalRepository.java` và `ReviewRepository.java` với các hàm cần thiết (ví dụ: tìm các đơn thuê chưa trả, tìm đơn thuê theo khách hàng/xe, ...).

*   **Bước 2: Service**
    *   Xây dựng `CarRentalService.java` và `ReviewService.java`.
    *   Logic ở đây sẽ phức tạp hơn, ví dụ: hàm `createRental` trong `CarRentalService` phải kiểm tra xem xe có sẵn sàng để cho thuê hay không trước khi tạo đơn.

*   **Bước 3: Giao diện và Controller `RentalManagement.fxml`**
    *   Thiết kế giao diện cho phép nhân viên tạo đơn thuê xe mới.
    *   Cần các `ComboBox` hoặc `TextField` có chức năng tìm kiếm để chọn Khách hàng và Xe.
    *   Hiển thị danh sách các đơn đang thuê và các đơn đã trả.
    *   Logic controller `RentalManagementController.java` sẽ gọi các service để thực hiện nghiệp vụ.

*   **Bước 4: Giao diện và Controller `Report.fxml`**
    *   Thiết kế giao diện cho phép chọn loại báo cáo (ví dụ: doanh thu theo tháng, số lượt thuê của mỗi xe).
    *   Controller `ReportController.java` sẽ:
        1.  Gọi các repository hoặc service để truy vấn dữ liệu thống kê.
        2.  Hiển thị kết quả lên `TableView` hoặc một `BarChart`, `PieChart` của JavaFX.

*   **Bước 5: Module Đánh giá**
    *   Chức năng này có thể được tích hợp vào Dashboard của khách hàng hoặc sau khi khách hàng trả xe.
    *   Tạo giao diện cho phép khách hàng để lại bình luận và xếp hạng (sao).
    *   Logic sẽ gọi `ReviewService` để lưu đánh giá vào CSDL.
