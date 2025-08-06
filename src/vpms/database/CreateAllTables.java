package vpms.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class CreateAllTables {
    MySqlConnection mySql = new MySqlConnection();

    public CreateAllTables() {
        createUserTable();
        createVehicleTypeAndPriceTable();
        createSlotTable();
        createSlotInstanceTable();
        createVehicleTable();
        createActivityLogTable();
        createParkingTable();
        createReservationTable();
        createPaymentTable();
    }

    public void createUserTable() {
        Connection conn = mySql.openConnection();
        String sql = """
            CREATE TABLE IF NOT EXISTS vpmsUsers (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                type VARCHAR(20) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                phone VARCHAR(10) NOT NULL,
                image BLOB,
                status VARCHAR(20) DEFAULT 'Active'
            )
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public void createVehicleTypeAndPriceTable() {
        Connection conn = mySql.openConnection();
        String sql = """
            CREATE TABLE IF NOT EXISTS vehicle_type_and_price (
                id INT AUTO_INCREMENT PRIMARY KEY,
                vehicle_type VARCHAR(50),
                reservation_price VARCHAR(10),
                regular_price VARCHAR(10),
                demand_price VARCHAR(10),
                extra_charge VARCHAR(10),
                status VARCHAR(20)
            )
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public void createSlotTable() {
        Connection conn = mySql.openConnection();
        String sql = """
            CREATE TABLE IF NOT EXISTS slots (
                slot_id INT AUTO_INCREMENT PRIMARY KEY,
                vehicletandp_id INT NOT NULL,
                number_of_slot INT NOT NULL,
                level_number INT NOT NULL,
                FOREIGN KEY (vehicletandp_id) REFERENCES vehicle_type_and_price(id)
            )
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public void createSlotInstanceTable() {
        Connection conn = mySql.openConnection();
        String sql = """
            CREATE TABLE IF NOT EXISTS slot_instances (
                instance_id INT AUTO_INCREMENT PRIMARY KEY,
                slot_id INT NOT NULL,
                slot_index INT NOT NULL,
                code VARCHAR(15) UNIQUE,
                status VARCHAR(10) DEFAULT 'free',
                FOREIGN KEY (slot_id) REFERENCES slots(slot_id) ON DELETE CASCADE
            )
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public void createVehicleTable() {
        Connection conn = mySql.openConnection();
        String sql = "CREATE TABLE IF NOT EXISTS vehicles ("
            + "vehicle_id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "vehicletandp_id INT NOT NULL, "
            + "vehicle_number VARCHAR(100) NOT NULL, "
            + "owner_name VARCHAR(100) NOT NULL, "
            + "owner_contact VARCHAR(50) NOT NULL, "
            + "created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, "
            + "updated_at DATETIME,"
            + "FOREIGN KEY (vehicletandp_id) REFERENCES vehicle_type_and_price(id))";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public void createActivityLogTable() {
        Connection conn = mySql.openConnection();
        String sql = """
            CREATE TABLE IF NOT EXISTS activity_log (
                log_id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT,
                action VARCHAR(255),
                timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES vpmsUsers(id)
            )
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public void createParkingTable() {
        Connection conn = mySql.openConnection();
        String sql = """
            CREATE TABLE IF NOT EXISTS parkings (
                parking_id INT AUTO_INCREMENT PRIMARY KEY,
                vehicle_id INT,
                instance_id INT,
                entryDateTime DATETIME NOT NULL,
                entryNote VARCHAR(200) NOT NULL,
                exitDateTime DATETIME,
                parkingStatus VARCHAR(100),
                parkingType VARCHAR(100),
                exitNote VARCHAR(200),
                penaltyApplied BOOLEAN,
                FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),
                FOREIGN KEY (instance_id) REFERENCES slot_instances(instance_id)
            )
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public void createReservationTable() {
        Connection conn = mySql.openConnection();
        String sql = """
            CREATE TABLE IF NOT EXISTS reservations (
                id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT,
                vehicle_id INT,
                instance_id INT,
                reservation_time VARCHAR(50),
                status VARCHAR(20),
                duration VARCHAR(20),
                payment_status VARCHAR(20),
                FOREIGN KEY (user_id) REFERENCES vpmsUsers(id),
                FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),
                FOREIGN KEY (instance_id) REFERENCES slot_instances(instance_id)
            )
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public void createPaymentTable() {
        Connection conn = mySql.openConnection();
        String sql = """
            CREATE TABLE IF NOT EXISTS payments (
                payment_id INT AUTO_INCREMENT PRIMARY KEY,
                parking_id INT,
                vehicle_id INT,
                user_id INT,
                regular_price VARCHAR(10),
                demand_price VARCHAR(10),
                reservation_price VARCHAR(10),
                extra_charge VARCHAR(10),
                payment_status VARCHAR(10),
                payment_time DATETIME,
                FOREIGN KEY (parking_id) REFERENCES parkings(parking_id),
                FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),
                FOREIGN KEY (user_id) REFERENCES vpmsUsers(id)
            )
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
    }
}
