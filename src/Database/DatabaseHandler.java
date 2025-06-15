package Database;

import Data.Admin;
import Data.Students;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static DatabaseHandler handler = null;
    private static Statement stmt = null;
    private static PreparedStatement pstatement = null;

    public static String dburl = DatabaseCredentials.ignoreDBURL;
    public static String userName = DatabaseCredentials.ignoreUserName;
    public static String password = DatabaseCredentials.ignorePassword;

    private DatabaseHandler() {}

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public static Connection getDBConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dburl, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = getDBConnection().createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        return result;
    }

    ///////////////////// Admin Login Validation /////////////////////
    public static boolean validateadminLogin(String adminusername, String adminPassword) {
        getInstance();
        String query = "SELECT * FROM Admin WHERE Username = '" + adminusername + "' AND Password = '" + adminPassword + "'";
        System.out.println(query);

        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    ///////////////////// Students Credentials Validation /////////////////////
    public static boolean isUsernameTaken(String username) {
        String sql = "SELECT 1 FROM User WHERE Username = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean usernameExists(String username) {
        return isUsernameTaken(username);
    }

    public static boolean isEmailTaken(String email) {
        String sql = "SELECT 1 FROM User WHERE EmailAddress = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validatePremiumLogin(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        String query = "SELECT u.UserID FROM User u " +
                       "JOIN Subscription s ON u.SubscriptionID = s.SubscriptionID " +
                       "WHERE u.Username = ? AND u.Password = ? AND s.PlanType = 'Subscribed'";

        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    ////////////////////// User Settings (Change Email, Change Password, Change Name, Delete Account) /////////////////////
    public static boolean changeEmail(String username, String newEmail) {
        String sql = "UPDATE User SET EmailAddress = ? WHERE Username = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newEmail);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changePassword(String username, String newPassword) {
        String sql = "UPDATE User SET Password = ? WHERE Username = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteAccount(String username) {
        String sql = "DELETE FROM User WHERE Username = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changeName(String username, String newFirstName, String newLastName) {
        String sql = "UPDATE User SET FirstName = ?, LastName = ? WHERE Username = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newFirstName);
            stmt.setString(2, newLastName);
            stmt.setString(3, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //////////////////////////////// Badges (Free Users | Adding) ///////////////////////////////
    public static boolean addBadge(String username, String badgeName) {
        String sql = "INSERT INTO Badges (Username, BadgeName) VALUES (?, ?)";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, badgeName);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /////////////////////////// Badges (Getting) ///////////////////////////////
    public static boolean addBadgeIfAllowed(int userID, String badgeName) {
        String getSubscriptionSql = "SELECT SubscriptionID FROM User WHERE UserID = ?";
        String countBadgesSql = "SELECT COUNT(*) FROM UserBadge WHERE UserID = ?";
        String insertBadgeSql = """
            INSERT INTO UserBadge (UserID, BadgeID)
            VALUES (?, (SELECT BadgeID FROM Badge WHERE BadgeName = ?))
        """;

        try (Connection conn = getDBConnection()) {
            // Get the user's subscription type
            try (PreparedStatement subStmt = conn.prepareStatement(getSubscriptionSql)) {
                subStmt.setInt(1, userID);
                ResultSet subRs = subStmt.executeQuery();

                if (!subRs.next()) return false;

                int subscriptionID = subRs.getInt("SubscriptionID");

                // Count the number of badges the user has
                try (PreparedStatement countStmt = conn.prepareStatement(countBadgesSql)) {
                    countStmt.setInt(1, userID);
                    ResultSet countRs = countStmt.executeQuery();

                    if (!countRs.next()) return false;

                    int badgeCount = countRs.getInt(1);

                    // Prevent badge insert if user is on free plan and has 2 or more badges
                    if (subscriptionID == 2 && badgeCount >= 2) return false;

                    // Insert the badge
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertBadgeSql)) {
                        insertStmt.setInt(1, userID);
                        insertStmt.setString(2, badgeName);
                        return insertStmt.executeUpdate() > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /////////////////////////////////Badges Admin (Adding, Deleting, Getting) ///////////////////////////////
    public static boolean awardBadgeToUser(int userID, int badgeID) {
        String sql = "INSERT INTO UserBadge (UserID, BadgeID) VALUES (?, ?)";
        try (PreparedStatement stmt = getDBConnection().prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, badgeID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet getAllBadges() {
        String query = "SELECT BadgeID, BadgeName FROM Badge";
        try {
            return handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getBadges() {
        ResultSet result = null;
        String query = """
            SELECT 
                ub.UserBadgeID,
                u.UserID,
                u.FirstName,
                u.LastName,
                u.EmailAddress,
                u.Username,
                s.StrandName,
                sub.PlanType,
                b.BadgeID,
                b.BadgeName,
                ub.AwardedAt AS DateAwarded
            FROM UserBadge ub
            JOIN User u ON ub.UserID = u.UserID
            JOIN Badge b ON ub.BadgeID = b.BadgeID
            LEFT JOIN Strand s ON u.StrandID = s.StrandID
            LEFT JOIN Subscription sub ON u.SubscriptionID = sub.SubscriptionID
        """;
        try {
            result = handler.execQuery(query); // Make sure handler is initialized
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean deleteBadgeFromUser(int userBadgeID) {
        String sql = "DELETE FROM UserBadge WHERE UserBadgeID = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userBadgeID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    ////////////////////////// Badges (Has Badge) ///////////////////////////////
    public static boolean hasBadge(int userID, String badgeName) {
        String sql = """
            SELECT 1 FROM UserBadge ub
            JOIN Badge b ON ub.BadgeID = b.BadgeID
            WHERE ub.UserID = ? AND b.BadgeName = ?
        """;
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setString(2, badgeName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    ///////////////////// CRUD Students (Students, ICT, STEM) /////////////////////
    public static ResultSet getStudents() { // Get all students
        ResultSet result = null;
        try {
            String query = "SELECT u.UserID, u.FirstName, u.LastName, u.EmailAddress, u.Username, u.Password, u.Created, s.StrandName, u.SubscriptionID, u.PaymentID " +
                           "FROM User u JOIN Strand s ON u.StrandID = s.StrandID;";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ResultSet getStudentsICT() { // Get all ICT students
        ResultSet result = null;
        try {
            String query = "SELECT u.UserID, u.FirstName, u.LastName, u.EmailAddress, u.Username, u.Password, u.Created, s.StrandName, u.SubscriptionID, u.PaymentID " +
                           "FROM User u JOIN Strand s ON u.StrandID = s.StrandID WHERE s.StrandName = 'ICT';";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ResultSet getStudentsSTEM() { // Get all STEM students
        ResultSet result = null;
        try {
            String query = "SELECT u.UserID, u.FirstName, u.LastName, u.EmailAddress, u.Username, u.Password, u.Created, s.StrandName, u.SubscriptionID, u.PaymentID " +
                           "FROM User u JOIN Strand s ON u.StrandID = s.StrandID WHERE s.StrandName = 'STEM';";
            result = handler.execQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean createStudent(Students student) {
        String sql = "INSERT INTO User (FirstName, LastName, EmailAddress, Username, Password, StrandID, SubscriptionID, PaymentID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getDBConnection();
             PreparedStatement pstatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstatement.setString(1, student.getFirstName());
            pstatement.setString(2, student.getLastName());
            pstatement.setString(3, student.getEmail());
            pstatement.setString(4, student.getUsername());
            pstatement.setString(5, student.getPassword());
            pstatement.setString(6, student.getStrand());
            pstatement.setInt(7, student.getSubscriptionID());

            if (student.getPaymentID() == -1) {
                pstatement.setNull(8, java.sql.Types.INTEGER);
            } else {
                pstatement.setInt(8, student.getPaymentID());
            }

            int res = pstatement.executeUpdate();

            if (res > 0) {
                ResultSet rs = pstatement.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);

                    // If SubscriptionID is 1 (Subscribed), add a transaction
                    if (student.getSubscriptionID() == 1) {
                        String insertTransactionSQL = "INSERT INTO Transaction (UserID, PaymentID, SubscriptionID, StrandID, TransactionDate) VALUES (?, ?, ?, ?, CURDATE())";
                        try (PreparedStatement transStmt = conn.prepareStatement(insertTransactionSQL)) {
                            transStmt.setInt(1, userId);
                            transStmt.setInt(2, student.getPaymentID());
                            transStmt.setInt(3, student.getSubscriptionID());
                            transStmt.setString(4, student.getStrand());
                            transStmt.executeUpdate();
                        }
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateStudent(Students student) {
        String sql = "UPDATE User SET FirstName = ?, LastName = ?, EmailAddress = ?, Username = ?, Password = ?, StrandID = ?, SubscriptionID = ?, PaymentID = ? WHERE UserID = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstatement = conn.prepareStatement(sql)) {
            pstatement.setString(1, student.getFirstName());
            pstatement.setString(2, student.getLastName());
            pstatement.setString(3, student.getEmail());
            pstatement.setString(4, student.getUsername());
            pstatement.setString(5, student.getPassword());
            pstatement.setString(6, student.getStrand());
            pstatement.setInt(7, student.getSubscriptionID());
            if (student.getPaymentID() == -1) {
                pstatement.setNull(8, java.sql.Types.INTEGER);
            } else {
                pstatement.setInt(8, student.getPaymentID());
            }
            pstatement.setInt(9, student.getUserID());

            int res = pstatement.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteStudent(Students student) {
        try (Connection conn = getDBConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (
                PreparedStatement delTrans = conn.prepareStatement("DELETE FROM Transaction WHERE UserID = ?");
                PreparedStatement delUser = conn.prepareStatement("DELETE FROM User WHERE UserID = ?")
            ) {
                // Delete transactions first
                delTrans.setInt(1, student.getUserID());
                delTrans.executeUpdate();

                // Now delete the user
                delUser.setInt(1, student.getUserID());
                int res = delUser.executeUpdate();

                conn.commit(); // Commit transaction
                return res > 0;
            } catch (Exception e) {
                conn.rollback(); // Rollback if any error occurs
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /////////////////// Get Payment, Subscription, Strands | SubscriptionIDByPlanType, StrandIDByName /////////////////////
    public static List<String> getPaymentMethods() {
        List<String> paymentMethods = new ArrayList<>();
        String sql = "SELECT PaymentMethod FROM Payment";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                paymentMethods.add(rs.getString("PaymentMethod"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentMethods;
    }

    public static List<String> getStrands() {
        List<String> strands = new ArrayList<>();
        String sql = "SELECT StrandName FROM Strand";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                strands.add(rs.getString("StrandName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strands;
    }

    public static List<String> getSubscriptionTypes() {
        List<String> plans = new ArrayList<>();
        String sql = "SELECT PlanType FROM Subscription";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                plans.add(rs.getString("PlanType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }

    // Get SubscriptionID by PlanType
    public static int getSubscriptionIDByPlanType(String planType) {
        int subscriptionID = -1;
        String sql = "SELECT SubscriptionID FROM Subscription WHERE PlanType = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, planType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                subscriptionID = rs.getInt("SubscriptionID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptionID;
    }

    // Get StrandID by StrandName
    public static String getStrandIDByName(String strandName) {
        String sql = "SELECT StrandID FROM Strand WHERE StrandName = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, strandName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("StrandID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getPaymentIDByMethod(String paymentMethod) {
        String sql = "SELECT PaymentID FROM Payment WHERE PaymentMethod = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentMethod);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("PaymentID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }

    public static String getPlanTypeBySubscriptionID(int subscriptionID) {
        String sql = "SELECT PlanType FROM Subscription WHERE SubscriptionID = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, subscriptionID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("PlanType");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPaymentMethodByID(int paymentID) {
        String sql = "SELECT PaymentMethod FROM Payment WHERE PaymentID = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("PaymentMethod");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /////////////////////////////////////////////////////////ADD ADMIN///////////////////////////////////////////////////////
    public static boolean createAdmin(Admin admin) {
        String sql = "INSERT INTO Admin (Username, Password) VALUES (?, ?)";
        try (Connection conn = getDBConnection();
             PreparedStatement pstatement = conn.prepareStatement(sql)) {
            pstatement.setString(1, admin.getAdminUserName());
            pstatement.setString(2, admin.getAdminPassword());
            int res = pstatement.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateAdmin(Admin admin) {
        String sql = "UPDATE Admin SET Username = ?, Password = ? WHERE AdminID = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstatement = conn.prepareStatement(sql)) {
            pstatement.setString(1, admin.getAdminUserName());
            pstatement.setString(2, admin.getAdminPassword());
            pstatement.setInt(3, admin.getAdminID());
            int res = pstatement.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isAdminUsernameTaken(String username) {
        String sql = "SELECT 1 FROM Admin WHERE Username = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteAdmin(Admin admin) {
        try {
            PreparedStatement pstatement = getDBConnection().prepareStatement("DELETE FROM Admin WHERE AdminID = ?");
            pstatement.setInt(1, admin.getAdminID());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet getBillings() {
        ResultSet result = null;
        try {
            String query = """
                SELECT 
                    u.UserID,
                    t.TransactionID,
                    t.TransactionDate,
                    s.StrandName,
                    sub.SubscriptionID,
                    u.FirstName,
                    u.LastName,
                    u.EmailAddress,
                    p.PaymentMethod,
                    CASE
                        WHEN sub.PlanType = 'Subscribed' THEN '199.00'
                        ELSE '0.00'
                    END AS PaymentDetails
                FROM User u
                LEFT JOIN Transaction t ON t.UserID = u.UserID
                LEFT JOIN Strand s ON u.StrandID = s.StrandID
                LEFT JOIN Subscription sub ON u.SubscriptionID = sub.SubscriptionID
                LEFT JOIN Payment p ON u.PaymentID = p.PaymentID
                WHERE sub.SubscriptionID IS NOT NULL
            """;
            result = handler.execQuery(query);
        } catch (Exception e) {
            System.err.println("Error retrieving billing information: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static boolean updateUserSubscriptionStatus(int userId, String status) {
        try (Connection conn = getDBConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            // Get SubscriptionID for "Free" or any other status
            int subscriptionId = -1;
            try (PreparedStatement getSubId = conn.prepareStatement("SELECT SubscriptionID FROM Subscription WHERE PlanType = ?")) {
                getSubId.setString(1, status);
                ResultSet rs = getSubId.executeQuery();
                if (rs.next()) {
                    subscriptionId = rs.getInt("SubscriptionID");
                } else {
                    System.err.println("No SubscriptionID found for status: " + status);
                    conn.rollback();
                    return false;
                }
            }

            // Update User with SubscriptionID and NULL the PaymentID
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE User SET SubscriptionID = ?, PaymentID = NULL WHERE UserID = ?")) {
                stmt.setInt(1, subscriptionId);
                stmt.setInt(2, userId);
                int rows = stmt.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // If status is "Free" or "Cancelled", delete transactions
            if (status.equalsIgnoreCase("Free") || status.equalsIgnoreCase("Cancelled")) {
                try (PreparedStatement delTrans = conn.prepareStatement("DELETE FROM Transaction WHERE UserID = ?")) {
                    delTrans.setInt(1, userId);
                    delTrans.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateUserSubscriptionStatus(int userId, String status, String paymentMethod) {
        try (Connection conn = getDBConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            // Get SubscriptionID from status
            int subscriptionId = -1;
            try (PreparedStatement getSubId = conn.prepareStatement(
                    "SELECT SubscriptionID FROM Subscription WHERE PlanType = ?")) {
                getSubId.setString(1, status);
                ResultSet rs = getSubId.executeQuery();
                if (rs.next()) {
                    subscriptionId = rs.getInt("SubscriptionID");
                } else {
                    System.err.println("No SubscriptionID found for status: " + status);
                    conn.rollback();
                    return false;
                }
            }

            // Handle downgrade to Free or Cancelled (no payment)
            if (status.equalsIgnoreCase("Free") || status.equalsIgnoreCase("Cancelled")) {
                try (PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE User SET SubscriptionID = ?, PaymentID = NULL WHERE UserID = ?")) {
                    stmt.setInt(1, subscriptionId);
                    stmt.setInt(2, userId);
                    int rows = stmt.executeUpdate();
                    if (rows == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement delTrans = conn.prepareStatement(
                        "DELETE FROM Transaction WHERE UserID = ?")) {
                    delTrans.setInt(1, userId);
                    delTrans.executeUpdate();
                }

                conn.commit();
                return true;
            }

            // For "Subscribed" status, fetch PaymentID
            int paymentId = -1;
            try (PreparedStatement getPayId = conn.prepareStatement(
                    "SELECT PaymentID FROM Payment WHERE PaymentMethod = ?")) {
                getPayId.setString(1, paymentMethod);
                ResultSet rs = getPayId.executeQuery();
                if (rs.next()) {
                    paymentId = rs.getInt("PaymentID");
                } else {
                    System.err.println("No PaymentID found for method: " + paymentMethod);
                    conn.rollback();
                    return false;
                }
            }

            // Update user with subscription and payment info
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE User SET SubscriptionID = ?, PaymentID = ? WHERE UserID = ?")) {
                stmt.setInt(1, subscriptionId);
                stmt.setInt(2, paymentId);
                stmt.setInt(3, userId);
                int rows = stmt.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // Add transaction for subscribed user
            if (status.equalsIgnoreCase("Subscribed")) {
                addTransactionForUser(userId, conn);
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void addTransactionForUser(int userId, Connection conn) {
        String sql = """
            INSERT INTO Transaction (UserID, PaymentID, SubscriptionID, StrandID, TransactionDate)
            SELECT 
                u.UserID,
                u.PaymentID,
                u.SubscriptionID,
                u.StrandID,
                CURDATE()
            FROM User u
            WHERE u.UserID = ?
              AND NOT EXISTS (
                  SELECT 1 FROM Transaction t WHERE t.UserID = u.UserID
              )
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteUserById(int userId) {
        try (Connection conn = getDBConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (
                PreparedStatement delTrans = conn.prepareStatement("DELETE FROM Transaction WHERE UserID = ?");
                PreparedStatement delUser = conn.prepareStatement("DELETE FROM User WHERE UserID = ?")
            ) {
                // Delete transactions first
                delTrans.setInt(1, userId);
                delTrans.executeUpdate();

                // Now delete the user
                delUser.setInt(1, userId);
                int res = delUser.executeUpdate();

                conn.commit(); // Commit transaction
                return res > 0;
            } catch (Exception e) {
                conn.rollback(); // Rollback if any error occurs
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteTransaction(int transactionId) {
        String sql = "DELETE FROM Transaction WHERE TransactionID = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet getBillingsByStrand(String strandName) {
        ResultSet result = null;
        try {
            String query = """
                SELECT 
                    u.UserID,
                    t.TransactionID,
                    t.TransactionDate,
                    s.StrandName,
                    sub.SubscriptionID,
                    u.FirstName,
                    u.LastName,
                    u.EmailAddress,
                    p.PaymentMethod,
                    CASE
                        WHEN sub.PlanType = 'Subscribed' THEN '199.00'
                        ELSE '0.00'
                    END AS PaymentDetails
                FROM User u
                LEFT JOIN Transaction t ON t.UserID = u.UserID
                LEFT JOIN Strand s ON u.StrandID = s.StrandID
                LEFT JOIN Subscription sub ON u.SubscriptionID = sub.SubscriptionID
                LEFT JOIN Payment p ON u.PaymentID = p.PaymentID
                WHERE sub.SubscriptionID IS NOT NULL AND s.StrandName = ?
            """;
            PreparedStatement stmt = getDBConnection().prepareStatement(query);
            stmt.setString(1, strandName);
            result = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ResultSet getStudentsByStrand(String strandName) {
        ResultSet result = null;
        try {
            String query = """
                SELECT u.UserID, u.FirstName, u.LastName, u.EmailAddress, u.Username, u.Password, u.Created, 
                       s.StrandName, u.SubscriptionID, u.PaymentID
                FROM User u
                JOIN Strand s ON u.StrandID = s.StrandID
                WHERE s.StrandName = ?
            """;
            PreparedStatement stmt = getDBConnection().prepareStatement(query);
            stmt.setString(1, strandName);
            result = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Validate student login by username and password
    public static boolean validatestudentLogin(String username, String password) {
        String sql = "SELECT 1 FROM User WHERE Username = ? AND Password = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get Students object by username
    public static Students getStudentByUsername(String username) {
        String sql = """
            SELECT u.UserID, u.FirstName, u.LastName, u.EmailAddress, u.Username, u.Password, u.Created,
                   s.StrandName, u.SubscriptionID, u.PaymentID
            FROM User u
            JOIN Strand s ON u.StrandID = s.StrandID
            WHERE u.Username = ?
        """;
        try (Connection conn = getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Students(
                    rs.getInt("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("EmailAddress"),
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("StrandName"),
                    rs.getInt("SubscriptionID"),
                    rs.getInt("PaymentID"),
                    rs.getString("Created")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
