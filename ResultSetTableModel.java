// A TableModel that supplies ResultSet data to a JTable.
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ResultSetTableModel extends AbstractTableModel
{
	
	//variable that contains query results
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;
	//will be used to establish connection to database

	private final Connection connect;
	//variable used to store Statement for querying database
	private final Statement state;
	//will be used to keep track of database connection
	private boolean connectedToDatabase = false;
	
	// constructor initializes resultSet and obtains its metadata object;
	// determines number of rows
	public ResultSetTableModel(String url, String username, String password, String query) throws SQLException
		{
				// connect to database
				connect = DriverManager.getConnection(url, username, password);

				//create Statement to query database
				//INSENSITIVE The cursor can scroll forward and backward, and the 
				//result set is not sensitive to changes made by others to the database
				//that occur after the result set was created.
				//The cursor can scroll forward and backward, and the result set is 
				//sensitive to changes made by others to the database that occur after
				//the result set was created.
				state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				//update database connection status
				connectedToDatabase = true;

				//set query and execute it
				setQuery(query);
		}
		//get class that represents column type
		public Class ColumnName(int column)throws IllegalStateException
		{
			// ensure database connection is available
			if (!connectedToDatabase)
				throw new IllegalStateException("Not Connected to Database");
			
			// determine Java class of column
			try
			{
				//Returns the fully-qualified name of the Java class whose instances
				//are manufactured if the method ResultSet.getObject is called to 
				//retrieve a value from the column.
				String nameOfClass = metaData.getColumnClassName(column + 1);

				// return Class object that represents className
				return Class.forName(nameOfClass);
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}



