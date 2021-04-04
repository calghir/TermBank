package termbank.jbdc;

/*
    Implementation for DB credentials
 */
public interface DAOImpl {

    // Database URL credentials
    static final String DATABASE_URL = "jdbc:mysql://localhost:3307/databanklog";
    static final String DATABASE_USERNAME = "root";
    static final String DATABASE_PASSWORD = "vJE!m^8v'SzC;%&g";
    static final String INSERT_QUERY = "INSERT INTO databanktable (category, term, definition)" +
            " VALUES (?, ?, ?)";

}
