Vehicle Parking Management System (VPMS)
A modern, desktop-based Vehicle Parking Management System to automate and centralize parking operations for managed facilities. Built with Java Swing, MySQL, and the MVC architectural pattern, VPMS aims to simplify parking slot allocation, improve record-keeping, and maximize operational efficiency.

🌟 Features
Admin & Staff Authentication
Secure login and role-based access control for admins and staff.

User & Staff Management
Create, edit, and manage users and staff profiles from an intuitive dashboard.

Parking Slot Management
Dashboard showing real-time slot status, with full/available slot alerts.

Vehicle Entry & Exit Logging
Record and track vehicle movements with entry/exit timestamps.

Parking Fee Calculation
Automatic computation of parking fees and penalties based on duration.

Payment Management
Track payments (cash or online). Support for integrating payment APIs.

Reports & Activity Logs
Generate and export summary and detailed reports of all major operations.

Password Reset via OTP
Secure, email-based password recovery options for users and staff.

Comprehensive Dashboards
Analytics on earnings, parking status, and staff activity.

Planned & Future Features

Reservation Functionality:
Users & staff will soon be able to reserve parking slots in advance, with time-bound space blocking and notifications.

Mobile/Web Extensions:
Future versions may offer mobile/web interfaces.

IoT/Smart Sensors:
Optional: Integration with hardware for automatic slot sensing.

🚗 System Architecture
Three-Tier MVC Design

Presentation (View): Java Swing user interfaces.

Application (Controller): Handles all business logic, validation, and flow.

Data Access (Model): JDBC connection to a MySQL database, ensuring all CRUD and reporting operations.

🚀 Getting Started
1. Prerequisites
Java JDK 8 or higher

Apache NetBeans IDE or IntelliJ IDEA

MySQL Server (local or remote)

MySQL Connector/J JDBC driver

2. Setup Instructions
Clone the repository:

bash
git clone https://github.com/beingbivek/Java_VPMS.git
Database setup:

Create a new MySQL database (e.g., vpms_db).

Run the provided SQL schema or run the Java table initializer.

Update DB credentials in your configuration file/class (e.g., DBConnection.java).

Add JDBC Driver:

Attach the mysql-connector-java.jar library to your project.

Add All Libraries:

Right-click on project → Properties → Libraries 

Add all jar files from src\JARFiles

Build and Run:

Clean and build the project 

Run the main class: src\vpms\VehicleParkingManagementSystem.java 

Login:
Login using default credentials (username: admin@vpms.com and pass: 
adminvpms123) 

3. Demo and Screenshots
See /docs/screenshots/ for key UI screens.

Watch the demo video here ([VPMS Demo Video](https://www.youtube.com/watch?v=ia1EOnA97kM&feature=youtu.be)).

🗂️ Project Structure
text
vpms/
  ├─ view/         // Swing GUI components
  ├─ controller/   // Application logic controllers
  ├─ model/        // Domain/data objects
  ├─ database/     // MySQL connection and schema
  └─ utils/        // Helpers and shared utilities
  
🤝 Contributing

Message me at ([Linked In](https://www.linkedin.com/in/beingbivek/)).

📅 Roadmap
 Admin/staff login & dashboard

 Parking slot management

 Vehicle entry/exit & fee calculation

 Reports and activity logs

 Password reset and error handling

 Reservation system (coming soon!)

 Mobile/web app integration (planned)

 Advanced analytics (future)

 IoT sensors for auto-slot detection (future)

📖 References
See the main project report for full academic and technical references.

📜 License
This project is licensed for educational, non-commercial use for the Coventry University STW4006CEM module.

👨💻 Authors
Bivek Thapa

Chandani Rai

Prabhash Sigdel

Rupesh Yadav

📣 Contact

For queries, contact the main author via Linked In or email address in the profile.
