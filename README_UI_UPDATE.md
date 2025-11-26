# 🎨 FU Car Renting System - Cập nhật Giao diện (UI Update)

## 📋 Tổng quan

Giao diện hệ thống FU Car Renting đã được **tái thiết kế hoàn toàn** để có:
- ✅ Phong cách hiện đại và chuyên nghiệp
- ✅ Tính nhất quán cao giữa các trang
- ✅ Trải nghiệm người dùng tốt hơn
- ✅ Màu sắc và styling hợp lý

---

## 🎯 Các trang được cập nhật

### 1. **AdminDashboard** (Trang chính quản trị viên)
- **Header**: Màu #2c3e50 (xanh tối), cỡ chữ 28px, bold
- **Sidebar**: Menu điều hướng bên trái với các nút
- **Content**: Nền #f4f6f9, cards với shadow mềm
- **Cấu trúc**: BorderPane với left sidebar + center content

### 2. **CarManagement** (Quản lý Xe)
**📊 Tab "Danh sách Xe"**
- Thanh thống kê (tổng số xe)
- TableView với 9 cột (ID, Tên, Năm, Nhà SX, Màu, Ghế, Giá, Trạng thái, Ngày nhập)
- Nút action: THÊM (xanh), CẬP NHẬT (vàng), XÓA (đỏ)

**📝 Tab "Thông tin Chi tiết"**
- Section 1: Thông tin Xe (8 trường input)
- Section 2: Thông tin Nhà SX (3 trường input)
- Section 3: Mô tả chi tiết (TextArea)
- Nút action: THÊM MỚI, CẬP NHẬT, XÓA, RESET

**Header chung:**
- Tiêu đề: "🚘 Quản lý Xe"
- Nút "Làm mới" góc phải
- Thanh tìm kiếm + lọc trạng thái

### 3. **CustomerManagement** (Quản lý Người dùng)
**📋 Tab "Danh sách Người dùng"**
- Thanh thống kê (tổng số người dùng)
- TableView với 10 cột
- Nút action: THÊM (xanh), CẬP NHẬT (vàng), XÓA (đỏ)

**📝 Tab "Thông tin Chi tiết"**
- Section 1: Thông tin Cá nhân (7 trường)
- Section 2: Thông tin Tài khoản (Tên đăng nhập, Mật khẩu, Phân quyền)
- Nút action: THÊM MỚI, CẬP NHẬT, XÓA, RESET

**Header chung:**
- Tiêu đề: "👥 Quản lý Người dùng"
- Thanh tìm kiếm + lọc chức vụ

### 4. **RentalManagement** (Quản lý Giao dịch)
**Header:**
- Tiêu đề: "📋 Quản lý Giao dịch Thuê xe"
- Thanh tìm kiếm + lọc trạng thái
- Nút "Làm mới"

### 5. **Report** (Báo cáo)
**Header:**
- Tiêu đề: "📊 Báo cáo Giao dịch"
- Bộ lọc: Từ ngày, Đến ngày, Sắp xếp
- Nút "TẠO BÁO CÁO" (xanh, 150px)
- Nút "XUẤT FILE" (xanh lá)

---

## 🎨 Hệ thống màu sắc (Color System)

| Tên | Hex Code | Sử dụng |
|-----|----------|--------|
| **Primary** | #3b82f6 | Nút bình thường, Highlight |
| **Primary Dark** | #2563eb | Hover state của Primary |
| **Success** | #28a745 | Nút THÊM, EXPORT |
| **Warning** | #ffc107 | Nút CẬP NHẬT |
| **Danger** | #dc3545 | Nút XÓA |
| **Info** | #06b6d4 | Thông tin |
| **Background** | #f4f6f9 | Nền chính |
| **Surface** | #ffffff | Nền card, input |
| **Border** | #e2e8f0 | Border của component |
| **Header** | #2c3e50 | Header chính |

---

## 📐 Typography (Kiểu chữ)

```
Font Family: Segoe UI, Roboto, Arial, sans-serif
Font Size:
  - Heading 1: 28px, Bold (Tiêu đề trang)
  - Heading 2: 16px, Bold (Tiêu đề section)
  - Normal: 13-14px (Văn bản thường)
  - Small: 12px (Label, placeholder)
```

---

## 🔧 CSS Classes

### Layout Classes
- `.content-pane` - Pane content chính
- `.page-header` - Header của trang
- `.form-section` - Section form

### Input Classes
- `.search-field` - TextField tìm kiếm
- `.filter-combo` - ComboBox lọc
- `.form-input` - TextField trong form
- `.form-combo` - ComboBox trong form
- `.form-textarea` - TextArea

### Button Classes
- `.btn-primary` - Nút màu xanh (mặc định)
- `.btn-success` - Nút xanh lá (THÊM)
- `.btn-warning` - Nút vàng (CẬP NHẬT)
- `.btn-danger` - Nút đỏ (XÓA)
- `.btn-secondary-light` - Nút xám nhạt
- `.logout-button` - Nút đăng xuất

### Table Classes
- `.data-table` - TableView chính

### Tab Classes
- `.data-tab-pane` - TabPane chính
- `.tab` - Tab item

---

## 📏 Spacing & Padding Standards

```
Padding:
  - Page padding: 20px
  - Card padding: 15-20px
  - Grid gap: 25px (hgap), 15px (vgap)
  
Margin:
  - Between sections: 20px
  - Button spacing: 10-12px

Border Radius:
  - Cards: 8px
  - Buttons: 6px
  - Inputs: 4-6px
```

---

## ✨ Hiệu ứng (Effects)

- **Shadow**: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 1)
- **Focus**: Border color thay đổi + shadow xanh nhạt
- **Hover**: Opacity 0.9, shadow tăng

---

## 📱 Responsive Design

- **Minimum Width**: 1400px (màn hình desktop)
- **Minimum Height**: 700px
- **SplitPane Ratio**: 55-60% (left table) : 40-45% (right form)

---

## 🚀 Hướng dẫn sử dụng CSS

### Cách áp dụng CSS
```xml
<!-- File được tham chiếu trong Main class -->
scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());

<!-- Hoặc trong FXML -->
<BorderPane stylesheets="@../css/style.css">
    <!-- Content -->
</BorderPane>
```

### Cách sử dụng styleClass
```xml
<!-- Áp dụng class CSS -->
<Button styleClass="btn-primary" text="OK"/>

<!-- Inline style (nếu cần) -->
<Button style="-fx-background-color: #28a745;" text="OK"/>
```

---

## 🎯 Best Practices

1. **Sử dụng styleClass thay vì inline style** khi có thể
2. **Giữ nhất quán** với hệ thống màu sắc
3. **Spacing**: Luôn dùng Grid hoặc HBox/VBox với spacing
4. **Accessibility**: Chắc chắn text có đủ contrast với background
5. **Responsiveness**: Sử dụng Region với hgrow="ALWAYS" để fill space

---

## 📝 Quy tắc Naming

- **Component ID**: `txt` (TextField), `btn` (Button), `tbl` (Table), `lbl` (Label), `cbb`/`cmb` (ComboBox), `dp` (DatePicker)
- **CSS Class**: `kebab-case` (ví dụ: `btn-primary`, `form-input`)
- **FXML ID**: `camelCase` (ví dụ: `btnLogin`, `txtUsername`)

---

## 🔄 Cập nhật tương lai

- Thêm animations/transitions
- Hỗ trợ Dark Mode
- Thêm Icon Library (FontAwesome hoặc tương tự)
- Responsive Layout cho tablet/mobile (nếu cần)

---

## 📞 Liên hệ

Nếu có câu hỏi về UI, vui lòng tham khảo:
- File CSS: `src/main/resources/css/style.css`
- File FXML tương ứng trong `src/main/resources/fxml/`

**Happy Coding! 🚀**
