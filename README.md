# 🚗 Vehicle Parking Management System (VPMS)

A modern, desktop-based Vehicle Parking Management System designed to automate and centralize parking operations for managed facilities. Built with **Java Swing**, **MySQL**, and the **MVC architectural pattern**, VPMS streamlines slot allocation, enhances record-keeping, and boosts operational efficiency.

---

## 🌟 Features

- **Admin & Staff Authentication**  
  Secure login with role-based access control.

- **User & Staff Management**  
  Create, edit, and manage users and staff via a central dashboard.

- **Parking Slot Management**  
  Real-time display of slot status with alerts for full/available spaces.

- **Vehicle Entry & Exit Logging**  
  Log timestamps for every vehicle movement.

- **Parking Fee Calculation**  
  Auto-calculate parking charges and penalties based on duration.

- **Payment Management**  
  Record payments (cash/online) with optional API integration.

- **Reports & Activity Logs**  
  Export summary or detailed reports of all major operations.

- **Password Reset via OTP**  
  Email-based password recovery system.

- **Comprehensive Dashboards**  
  Analytics for earnings, parking status, and staff activities.

---

## 🔮 Planned & Future Features

- **Reservation Functionality**  
  Time-bound slot reservation with alerts and notifications.

- **Mobile/Web Extensions**  
  Mobile or web interface for remote access.

- **IoT/Smart Sensors**  
  Optional hardware integration for automatic slot detection.

---

## 🏛️ System Architecture – MVC Pattern

- **Presentation (View):** Java Swing UI
- **Application (Controller):** Business logic, validation & flow
- **Data Access (Model):** JDBC + MySQL for CRUD and reporting

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java JDK 8 or higher  
- Apache NetBeans IDE or IntelliJ IDEA  
- MySQL Server (local or remote)  
- MySQL Connector/J JDBC driver  

### 🛠️ Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/beingbivek/Java_VPMS.git
   ```

2. **Database Setup**
   - Create a MySQL database (e.g., `vpms_db`)
   - Run the provided SQL schema or the Java table initializer
   - Update DB credentials in `DBConnection.java`

3. **Add JDBC Driver**
   - Attach `mysql-connector-java.jar` to your project

4. **Add Required Libraries**
   - Right-click project → `Properties` → `Libraries`
   - Add all JARs from `src/JARFiles`

5. **Build and Run**
   - Clean and build the project
   - Run the main class:  
     `src/vpms/VehicleParkingManagementSystem.java`

6. **Default Login Credentials**
   - **Username:** `admin@vpms.com`  
   - **Password:** `adminvpms123`

---

## 📸 Demo and Screenshots

- 📂 See screenshots in: `/docs/screenshots/`  
- 🎥 Watch the demo video: [VPMS Demo Video](https://www.youtube.com/watch?v=ia1EOnA97kM&feature=youtu.be)

---

## 🗂️ Project Structure

```plaintext
vpms/
├── view/        → Java Swing GUI components
├── controller/  → Application logic
├── model/       → Domain/data objects
├── database/    → MySQL connection and schema
└── utils/       → Shared helpers and utilities
```

---

## 🤝 Contributing

Want to contribute?  
Message the author via [LinkedIn](https://www.linkedin.com/in/beingbivek/).

---

## 🗓️ Roadmap

- ✅ Admin/staff login & dashboard  
- ✅ Parking slot management  
- ✅ Vehicle entry/exit & fee calculation  
- ✅ Reports and logs  
- ✅ Password reset system  
- 🔜 Reservation system  
- 🧪 Mobile/web app integration  
- 📊 Advanced analytics  
- 🧠 IoT auto-slot detection  

---

## 📖 References

Please refer to the main project report for full academic and technical references.

---

## 📜 License

Licensed for **educational and non-commercial** use under Coventry University's STW4006CEM module.

---

## 👨‍💻 Authors

- **Bivek Thapa**  
- Chandani Rai  
- Prabhash Sigdel  
- Rupesh Yadav  

---

## 📣 Contact

For any queries, contact the lead author via [LinkedIn](https://www.linkedin.com/in/beingbivek/) or email listed in the profile.
