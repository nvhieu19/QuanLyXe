# 🚀 Hướng dẫn Nhanh - FU Car Renting System UI

## 📖 Bắt đầu nhanh trong 5 phút

### Bước 1: Copy CSS file
```bash
Tệp: src/main/resources/css/style.css
Đã cập nhật với tất cả classes mới
```

### Bước 2: Copy/Update FXML files
```bash
CarManagement.fxml          ✅ Updated
RentalManagement.fxml       ✅ Updated
Report.fxml                 ✅ Updated
CustomerManagement.fxml     ⚠️ Cần thay thế
  → Sử dụng: CustomerManagement_New.fxml
```

### Bước 3: Load CSS trong Main class
```java
import javafx.scene.Scene;

Scene scene = new Scene(root, 1400, 700);
scene.getStylesheets().add(
    getClass().getResource("/css/style.css").toExternalForm()
);
primaryStage.setScene(scene);
```

### Bước 4: Build & Run
```bash
Maven: mvn clean compile
Gradle: gradle build
Hoặc: IDE rebuild
```

---

## 🎯 Sử dụng CSS Classes

### Tạo Header
```xml
<HBox alignment="CENTER_LEFT" spacing="15.0" 
      style="-fx-background-color: #2c3e50; -fx-padding: 20;">
    <Label styleClass="page-header-title" text="🚘 Quản lý Xe"/>
    <Region HBox.hgrow="ALWAYS"/>
    <Button styleClass="btn-secondary-light" text="🔄 Làm mới"/>
</HBox>
```

### Tạo Search Bar
```xml
<HBox spacing="10.0" alignment="CENTER_LEFT" style="-fx-padding: 15 20 15 20;">
    <TextField styleClass="search-field" HBox.hgrow="ALWAYS" 
               promptText="Tìm kiếm..."/>
    <ComboBox styleClass="filter-combo" prefWidth="150.0"/>
    <Button styleClass="btn-primary" text="🔍 Tìm"/>
</HBox>
```

### Tạo Form Section
```xml
<VBox styleClass="form-card" spacing="15.0">
    <Label styleClass="section-header" text="Thông tin Cá nhân"/>
    <Separator style="-fx-border-color: #e0e0e0;"/>
    
    <GridPane hgap="25.0" vgap="15.0">
        <Label text="Tên:" style="-fx-font-weight: bold;"/>
        <TextField styleClass="form-input" 
                   promptText="VD: Nguyễn Văn A"/>
    </GridPane>
</VBox>
```

### Tạo Buttons
```xml
<HBox spacing="10.0">
    <Button styleClass="btn-success" text="➕ THÊM"/>
    <Button styleClass="btn-warning" text="✏️ CẬP NHẬT"/>
    <Button styleClass="btn-danger" text="🗑️ XÓA"/>
    <Button styleClass="btn-secondary-light" text="↺ RESET"/>
</HBox>
```

### Tạo Table
```xml
<TableView styleClass="data-table" VBox.vgrow="ALWAYS">
    <columns>
        <TableColumn text="ID" prefWidth="40.0"/>
        <TableColumn text="Tên" prefWidth="150.0"/>
        <!-- More columns -->
    </columns>
    <columnResizePolicy>
        <TableView fx:constant="CONSTRAINED_RESIZE_POLICY"/>
    </columnResizePolicy>
</TableView>
```

---

## 🎨 Màu sắc Quick Reference

```
Xanh chính (Primary):    #3b82f6
Xanh lá (Success):       #28a745  ← Dùng cho THÊM
Vàng (Warning):          #ffc107  ← Dùng cho CẬP NHẬT
Đỏ (Danger):             #dc3545  ← Dùng cho XÓA
Xanh tối (Header):       #2c3e50
Xám nhạt (Background):   #f4f6f9
Trắng (Surface):         #ffffff
Xám cực nhạt (Border):   #e2e8f0
```

---

## 📐 Font Sizes Quick Reference

```
Tiêu đề trang:     28px, Bold      → .page-header-title
Tiêu đề section:   16px, Bold      → .section-header
Text thường:       13-14px
Label/Hint:        12px
```

---

## 🔢 Spacing Quick Reference

```
Page padding:      20px
Card padding:      15-20px
Grid hgap:         25px
Grid vgap:         15px
Button spacing:    10-12px
VBox spacing:      15-20px
HBox spacing:      10-15px
```

---

## 🎯 CSS Classes Most Used

| Task | Class |
|------|-------|
| Tìm kiếm | `.search-field` |
| Lọc | `.filter-combo` |
| Nút THÊM | `.btn-success` |
| Nút CẬP NHẬT | `.btn-warning` |
| Nút XÓA | `.btn-danger` |
| Form input | `.form-input` |
| Form textarea | `.form-textarea` |
| Table | `.data-table` |
| Card | `.form-card` |
| Tiêu đề | `.section-header` |

---

## 🔗 File Reference

```
FXML Layout:
  CarManagement.fxml
  → TabPane + Header + Search

CSS Styling:
  style.css (1000+ lines)
  → 50+ CSS classes

Documentation:
  README_UI_UPDATE.md
  → Hướng dẫn chi tiết

Classes Reference:
  CSS_CLASSES_REFERENCE.md
  → Tất cả classes + examples

Changelog:
  CHANGELOG_UI.md
  → Ghi chép thay đổi

Summary:
  SUMMARY.md
  → Tóm tắt hoàn chỉnh
```

---

## 🚨 Lỗi thường gặp

### ❌ CSS không load
**Giải pháp:**
```java
// Kiểm tra đường dẫn
getClass().getResource("/css/style.css")

// Hoặc absolute path
getClass().getResource("../css/style.css")
```

### ❌ Style không áp dụng
**Kiểm tra:**
1. Đúng tên class chưa? → `styleClass="btn-primary"`
2. Đúng file CSS chưa? → style.css loaded?
3. Rebuild project?

### ❌ Lỗi XML trong FXML
**Kiểm tra:**
1. Closing tags đúng không?
2. Element nesting đúng không?
3. Attributes syntax đúng không?

---

## ✅ Checklist trước Deploy

- [ ] CSS file copied
- [ ] FXML files updated
- [ ] CSS loaded in Main class
- [ ] Project rebuilt
- [ ] No XML errors
- [ ] No CSS errors
- [ ] UI renders correctly
- [ ] All buttons work
- [ ] Search works
- [ ] Tables display correctly
- [ ] Forms look good
- [ ] Colors are correct
- [ ] Fonts are correct
- [ ] Spacing is consistent

---

## 📞 Cần giúp?

1. **CSS class name?** → Xem `CSS_CLASSES_REFERENCE.md`
2. **FXML layout?** → Copy từ `CarManagement.fxml`
3. **Màu sắc?** → Xem `README_UI_UPDATE.md` → Color palette
4. **Spacing?** → Xem `README_UI_UPDATE.md` → Spacing standards
5. **Cách sử dụng?** → Xem ví dụ trong `CSS_CLASSES_REFERENCE.md`

---

## 🎓 Học thêm

1. **CSS Variables** (Root variables)
   ```css
   .root {
       -fx-primary: #3b82f6;
       -fx-success: #28a745;
   }
   ```

2. **Pseudo-states**
   ```css
   .button:hover { -fx-opacity: 0.9; }
   .button:pressed { -fx-opacity: 0.7; }
   .text-field:focused { -fx-border-color: #3b82f6; }
   ```

3. **Effects**
   ```css
   -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 1);
   ```

---

## 🎉 Sẵn sàng!

Bây giờ bạn đã có:
- ✅ CSS hoàn chỉnh
- ✅ FXML layouts
- ✅ Documentation đầy đủ
- ✅ Examples để copy
- ✅ Best practices

**Bắt đầu code!** 🚀

---

*Quick Reference v1.0 - 26/11/2025*
