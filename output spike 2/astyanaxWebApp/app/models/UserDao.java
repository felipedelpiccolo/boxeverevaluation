package models;

import java.util.ArrayList;
import java.util.List;

import jobs.SetUpAstyanax;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.netflix.astyanax.query.AllRowsQuery;
import com.netflix.astyanax.serializers.StringSerializer;

public class UserDao {

	private static final ColumnFamily<String, String> CF_USER_INFO =
			  new ColumnFamily<String, String>(
			    "users",              // Column Family Name
			    StringSerializer.get(),   // Key Serializer
			    StringSerializer.get());  // Column Serializer
	
	private static UserDao userDao; 	
	
	private UserDao() {
		
	}
	
	public static UserDao getInstance() {
		if(userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}
	
	public void store(UserDto user) {
		MutationBatch m = SetUpAstyanax.keyspace.prepareMutationBatch();
		
		m.withRow(CF_USER_INFO, user.getFirstname())			
			.putColumn("lastname", user.getLastname());
		
		try {
			OperationResult<Void> result = m.execute();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	public List<UserDto> getAllUser() throws ConnectionException {
		
		OperationResult<Rows<String, String>> result =
				SetUpAstyanax.keyspace.prepareQuery(CF_USER_INFO)
				    .getAllRows().execute();
			
		List<UserDto> usersList = new ArrayList<UserDto>();
		
		for (Row<String, String> row : result.getResult()) {
			ColumnList<String> columns = row.getColumns();
			
			String lastname = columns.getColumnByName("lastname").getStringValue();
			String firstname = row.getKey();
			
			usersList.add(new UserDto(firstname, lastname));
		}
		
		return usersList;
	}
	
}
