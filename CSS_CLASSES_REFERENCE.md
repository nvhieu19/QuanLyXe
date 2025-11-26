# 🎨 CSS Classes Reference - FU Car Renting System

## 📚 Danh sách CSS Classes

---

## 🎯 Layout Classes

### `.content-pane`
- **Sử dụng**: Main content wrapper
- **Màu nền**: #f4f6f9 (light gray)
- **Ứng dụng**: BorderPane, VBox chính
```xml
<BorderPane styleClass="content-pane">
```

### `.page-header`
- **Sử dụng**: Header của trang
- **Background**: White (#ffffff)
- **Border**: Bottom 1px #e2e8f0
- **Padding**: 15px
```xml
<HBox styleClass="page-header">
```

### `.page-header-title`
- **Sử dụng**: Tiêu đề trang
- **Màu**: White (#ffffff)
- **Font-size**: 28px, Bold
- **Ứng dụng**: Label trong header
```xml
<Label styleClass="page-header-title" text="Quản lý Xe">
```

### `.form-section`
- **Sử dụng**: Container cho form inputs
- **Background**: White (#ffffff)
- **Border-radius**: 10px
- **Padding**: 20px
- **Shadow**: dropshadow
```xml
<VBox styleClass="form-section">
    <TextField styleClass="form-input"/>
</VBox>
```

### `.form-scroll`
- **Sử dụng**: ScrollPane cho form
- **Background**: Transparent
```xml
<ScrollPane styleClass="form-scroll">
```

---

## 🔘 Button Classes

### `.btn-primary`
- **Màu**: #3b82f6 (Xanh)
- **Màu chữ**: White
- **Sử dụng**: Nút hành động chính (Tìm)
```xml
<Button styleClass="btn-primary" text="🔍 Tìm"/>
```

### `.btn-success`
- **Màu**: #28a745 (Xanh lá)
- **Màu chữ**: White
- **Sử dụng**: Nút THÊM
```xml
<Button styleClass="btn-success" text="➕ THÊM XE"/>
```

### `.btn-warning`
- **Màu**: #ffc107 (Vàng)
- **Màu chữ**: White
- **Sử dụng**: Nút CẬP NHẬT
```xml
<Button styleClass="btn-warning" text="✏️ CẬP NHẬT"/>
```

### `.btn-danger`
- **Màu**: #dc3545 (Đỏ)
- **Màu chữ**: White
- **Sử dụng**: Nút XÓA
```xml
<Button styleClass="btn-danger" text="🗑️ XÓA"/>
```

### `.btn-info`
- **Màu**: #06b6d4 (Xanh ngọc)
- **Màu chữ**: White
- **Sử dụng**: Nút thông tin
```xml
<Button styleClass="btn-info" text="ℹ️ Thông tin"/>
```

### `.btn-secondary-light`
- **Màu**: #f1f5f9 (Xám rất nhạt)
- **Border**: 1px #e2e8f0
- **Màu chữ**: #475569
- **Sử dụng**: Nút phụ (Làm mới, Reset)
```xml
<Button styleClass="btn-secondary-light" text="🔄 Làm mới"/>
```

### `.logout-button`
- **Màu**: #ef4444 (Đỏ)
- **Sử dụng**: Nút đăng xuất
```xml
<Button styleClass="logout-button" text="ĐĂNG XUẤT"/>
```

### `.generate-button`
- **Màu**: #3b82f6 (Primary)
- **Sử dụng**: Nút tạo báo cáo
```xml
<Button styleClass="generate-button" text="📊 TẠO BÁO CÁO"/>
```

### `.export-button`
- **Màu**: #10b981 (Success)
- **Sử dụng**: Nút xuất file
```xml
<Button styleClass="export-button" text="📥 XUẤT FILE"/>
```

### `.refresh-button`
- **Màu**: White with border
- **Border**: 2px #e2e8f0
- **Sử dụng**: Nút làm mới (alternative style)
```xml
<Button styleClass="refresh-button" text="🔄 Làm mới"/>
```

---

## 📝 Input Classes

### `.text-field`
- **Sử dụng**: TextField input
- **Background**: White (#ffffff)
- **Border**: 2px #e2e8f0
- **Border-radius**: 6px
- **Padding**: 8px 12px
- **Focus color**: #3b82f6
```xml
<TextField styleClass="text-field" promptText="Nhập..."/>
```

### `.text-area`
- **Sử dụng**: TextArea input
- **Style**: Giống TextField
- **Font-size**: 13px
```xml
<TextArea styleClass="text-area" wrapText="true"/>
```

### `.date-picker`
- **Sử dụng**: DatePicker input
- **Style**: Giống TextField
```xml
<DatePicker styleClass="date-picker"/>
```

### `.combo-box`
- **Sử dụng**: ComboBox input
- **Style**: Giống TextField
```xml
<ComboBox styleClass="combo-box">
```

### `.search-field`
- **Sử dụng**: TextField tìm kiếm
- **Border**: 1px #e2e8f0
- **Padding**: 8px 12px
- **Font-size**: 12px
- **Focus border**: 2px #3b82f6
```xml
<TextField styleClass="search-field" 
           promptText="Tìm kiếm..."/>
```

### `.filter-combo`
- **Sử dụng**: ComboBox lọc
- **Border**: 1px #e2e8f0
- **Padding**: 8px 12px
```xml
<ComboBox styleClass="filter-combo" 
          promptText="Lọc..."/>
```

### `.form-input`
- **Sử dụng**: TextField trong form
- **Border**: 1px #cccccc
- **Border-radius**: 4px
- **Padding**: 8px 12px
```xml
<TextField styleClass="form-input" 
           promptText="VD: Nguyễn Văn A"/>
```

### `.form-combo`
- **Sử dụng**: ComboBox trong form
- **Style**: Giống form-input
```xml
<ComboBox styleClass="form-combo"/>
```

### `.form-textarea`
- **Sử dụng**: TextArea trong form
- **Style**: Giống form-input
```xml
<TextArea styleClass="form-textarea" wrapText="true"/>
```

---

## 📊 Table Classes

### `.data-table`
- **Sử dụng**: TableView chính
- **Background**: White (#ffffff)
- **Border**: 2px #e2e8f0
- **Border-radius**: 8px
- **Header background**: #f8fafc
- **Header text**: Bold, #0f172a
- **Alternating rows**: #fafbfc
- **Hover**: rgba(59, 130, 246, 0.08)
- **Selected**: rgba(59, 130, 246, 0.15)
```xml
<TableView styleClass="data-table">
```

---

## 🏷️ Label Classes

### `.page-title`
- **Sử dụng**: Tiêu đề trang
- **Màu**: Tuỳ context (white hay dark)
- **Font-size**: Tuỳ context
- **Font-weight**: Bold
```xml
<Label styleClass="page-title" text="Quản lý Xe">
```

### `.section-header`
- **Sử dụng**: Tiêu đề section
- **Màu**: #0f172a (Dark)
- **Font-weight**: Bold
```xml
<Label styleClass="section-header" text="Thông tin Xe">
```

### `.subsection-title`
- **Sử dụng**: Tiêu đề sub-section
- **Màu**: #3b82f6 (Primary blue)
```xml
<Label styleClass="subsection-title" 
       text="👤 Thông tin Cá nhân">
```

### `.welcome-text`
- **Sử dụng**: Text chào mừng
- **Màu**: White
```xml
<Label styleClass="welcome-text" text="Welcome, Admin">
```

### `.customer-subtitle`
- **Sử dụng**: Subtitle dưới welcome
- **Màu**: rgba(255, 255, 255, 0.9)
```xml
<Label styleClass="customer-subtitle" 
       text="Hệ thống Quản lý Thuê xe">
```

### `.demo-title`
- **Sử dụng**: Demo title
- **Màu**: #0f172a
```xml
<Label styleClass="demo-title" text="Tài khoản demo:">
```

### `.demo-text`
- **Sử dụng**: Demo text
- **Màu**: #475569 (Light)
```xml
<Label styleClass="demo-text" text="admin01 / admin123">
```

### `.error-label`
- **Sử dụng**: Hiển thị error
- **Màu text**: #ef4444
- **Background**: rgba(239, 68, 68, 0.1)
- **Padding**: 10px
- **Border-radius**: 6px
```xml
<Label styleClass="error-label" text="Lỗi!"/>
```

### `.info-label`
- **Sử dụng**: Info label
- **Màu**: #475569
- **Font-weight**: Bold
- **Font-size**: 13px
```xml
<Label styleClass="info-label" text="Tổng số xe:">
```

### `.info-value`
- **Sử dụng**: Giá trị info
- **Màu**: #3b82f6 (Primary)
- **Font-weight**: Bold
- **Font-size**: 14px
```xml
<Label styleClass="info-value" text="42">
```

---

## 📑 Tab Classes

### `.data-tab-pane`
- **Sử dụng**: TabPane chính
- **Padding**: 0
```xml
<TabPane styleClass="data-tab-pane">
```

### Tab items
- **Background**: White (#ffffff)
- **Border**: Bottom 2px #e2e8f0
- **Font-size**: 13px
- **Font-weight**: Bold
- **Padding**: 12px 25px
- **Selected border**: #3b82f6
- **Selected text**: #3b82f6
- **Hover background**: #f1f5f9
```xml
<Tab text="📊 Danh sách Xe">
```

### `.customer-tabs`
- **Sử dụng**: Customer dashboard tabs
```xml
<TabPane styleClass="customer-tabs">
```

---

## 🎨 Other Component Classes

### `.login-background`
- **Sử dụng**: Login page background
- **Background**: gradient (135deg, #667eea → #764ba2)
```xml
<StackPane styleClass="login-background">
```

### `.login-container`
- **Sử dụng**: Login form container
- **Background**: White (#ffffff)
- **Border-radius**: 16px
- **Shadow**: Strong dropshadow
```xml
<VBox styleClass="login-container">
```

### `.login-title`
- **Sử dụng**: Login title
- **Màu**: #3b82f6 (Primary)
```xml
<Label styleClass="login-title" text="🚗 FU Car Renting">
```

### `.login-subtitle`
- **Sử dụng**: Login subtitle
- **Màu**: #475569
```xml
<Label styleClass="login-subtitle" 
       text="Hệ thống Quản lý Thuê xe">
```

### `.modern-input`
- **Sử dụng**: Login inputs
- **Background**: #f8fafc
- **Border**: 2px #e2e8f0
- **Border-radius**: 8px
- **Padding**: 12px 16px
```xml
<TextField styleClass="modern-input" 
           promptText="Tên đăng nhập">
```

### `.login-button`
- **Sử dụng**: Login button
- **Background**: #3b82f6
- **Màu chữ**: White
- **Border-radius**: 8px
- **Shadow**: dropshadow
```xml
<Button styleClass="login-button" text="ĐĂNG NHẬP">
```

### `.input-label`
- **Sử dụng**: Label cho inputs
- **Màu**: #0f172a
```xml
<Label styleClass="input-label" text="Tên đăng nhập">
```

### `.form-card`
- **Sử dụng**: Card chứa form
- **Background**: White (#ffffff)
- **Border-radius**: 8px
- **Padding**: 20px
- **Border**: 1px #e0e0e0
- **Shadow**: dropshadow(gaussian, rgba(0,0,0,0.05), 8, 0, 0, 1)
```xml
<VBox styleClass="form-card">
```

---

## 🔄 Combo Classes Thường Dùng

### Thanh tìm kiếm + Lọc
```xml
<HBox spacing="10.0" alignment="CENTER_LEFT">
    <TextField styleClass="search-field" HBox.hgrow="ALWAYS"/>
    <ComboBox styleClass="filter-combo" prefWidth="150.0"/>
    <Button styleClass="btn-primary" text="🔍 Tìm"/>
</HBox>
```

### Thanh thống kê
```xml
<HBox spacing="15.0" alignment="CENTER_LEFT" 
      style="-fx-background-color: #ffffff; -fx-padding: 15;">
    <Label styleClass="info-label" text="Tổng số:"/>
    <Label styleClass="info-value" text="42"/>
</HBox>
```

### Form Section
```xml
<VBox styleClass="form-card" spacing="15.0">
    <Label styleClass="subsection-title" text="👤 Thông tin"/>
    <GridPane hgap="25.0" vgap="15.0">
        <Label styleClass="form-label" text="Tên:"/>
        <TextField styleClass="form-input"/>
    </GridPane>
</VBox>
```

### Action Buttons
```xml
<HBox spacing="10.0">
    <Button styleClass="btn-success" text="➕ THÊM"/>
    <Button styleClass="btn-warning" text="✏️ CẬP NHẬT"/>
    <Button styleClass="btn-danger" text="🗑️ XÓA"/>
    <Button styleClass="btn-secondary-light" text="↺ RESET"/>
</HBox>
```

---

## 📌 Best Practices

1. **Sử dụng styleClass thay vì inline style** khi có thể
2. **Combine multiple classes** nếu cần
3. **Sử dụng theme variables** từ .root
4. **Focus state**: Tự động áp dụng khi input được focus
5. **Hover state**: Tự động áp dụng khi chuột hover

---

## 🎯 Ví dụ hoàn chỉnh

### Header của trang
```xml
<HBox alignment="CENTER_LEFT" spacing="15.0" 
      style="-fx-background-color: #2c3e50; -fx-padding: 20;">
    <Label styleClass="page-header-title" text="🚘 Quản lý Xe"/>
    <Region HBox.hgrow="ALWAYS"/>
    <Button styleClass="btn-secondary-light" text="🔄 Làm mới"/>
</HBox>
```

### Search Bar
```xml
<HBox spacing="10.0" alignment="CENTER_LEFT" 
      style="-fx-padding: 15 20 15 20;">
    <TextField styleClass="search-field" HBox.hgrow="ALWAYS" 
               promptText="Tìm kiếm theo tên..."/>
    <ComboBox styleClass="filter-combo" prefWidth="150.0" 
              promptText="Lọc"/>
    <Button styleClass="btn-primary" text="🔍 Tìm"/>
</HBox>
```

### Form Input Group
```xml
<VBox styleClass="form-card" spacing="15.0">
    <Label styleClass="section-header" text="Thông tin Cá nhân"/>
    <Separator style="-fx-border-color: #e0e0e0;"/>
    
    <GridPane hgap="25.0" vgap="15.0">
        <Label text="Tên:" GridPane.columnIndex="0" 
               style="-fx-font-weight: bold;"/>
        <TextField styleClass="form-input" GridPane.columnIndex="1" 
                   promptText="VD: Nguyễn Văn A"/>
    </GridPane>
</VBox>
```

---

**Happy Coding! 🚀**

*Last updated: 26/11/2025*
