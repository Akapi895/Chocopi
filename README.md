# Ứng dụng quản lý thư viện CHOCOPI LIBRARY


## Giới thiệu
* Đây là bài tập lớn môn Lập trình hướng đối tượng lớp INT2204 15 của nhóm CHOCOPI.
* Ứng dụng này là một phần mềm hỗ trợ việc quản lý sách, tạp chí, truyện,... trong thư viện.
* CHOCOPI LIBRARY cho phép người dùng tìm sách, mượn sách.

## Tính năng chính
Ứng dụng có các tính năng chính như sau:
* Tìm sách.
* Mượn sách.
* Trợ lý ảo trả lời câu hỏi của người dùng.
* Thêm sách.
* Quản lý người mượn, số lượng sách.
* Thống kê số lượng sách được mượn và trả.

## Công nghệ sử dụng
* Ngôn ngữ lập trình: Java, CSS.
* Framework giao diện: JavaFX.
* Cơ sở dữ liệu: MySQL.
* Công cụ build: Maven.

## Cấu trúc thư mục dự án
``` 
src
│       └───main
│           ├───java
│           │   │   module-info.java
│           │   │
│           │   └───com
│           │       └───chocopi
│           │           │
│           │           ├───controller
│           │           │   │
│           │           │   ├───admin
│           │           │   │
│           │           │   └───user
│           │           │
│           │           ├───dao
│           │           │
│           │           ├───model
│           │           │
│           │           ├───service
│           │           │
│           │           └───util
│           │
│           └───resources
│               └───com
│                   └───chocopi
│                       ├───css
│                       │
│                       ├───fxml
│                       │   │
│                       │   ├───admin
│                       │   │
│                       │   └───user
│                       │
│                       └───images
│                           │
│                           ├───avatar
│                           ├───book 
│                           │
│                           └───identify
│
└───Database
``` 
## Thông tin thành viên nhóm
* Phạm Anh Tuấn: Nhóm trưởng.
* Nguyễn Diệu Mai Vy: Thành viên.
* Lê Nhữ Quang: Thành viên.
* Phan Trần Quang Trí: Thành viên.
