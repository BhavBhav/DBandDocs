package parallel;
import static org.junit.Assert.assertTrue;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SqliteConnection {
public static SqliteConnection single_instance=null;
private static Connection conn;	
public static void connect() {

		 
String url = "jdbc:sqlite:src/test/resources/TestData.db";
	
		
	try {
		
		
	
		  conn = DriverManager.getConnection(url);
		 System.out.println(" SQL Connection established");

		 
	}
	  catch(Exception e) {
	assertTrue(e.getLocalizedMessage(),false);
		
	  }
	

}

public String queryForGetDataFromControlsByObjectkeyAndScreen(String ObjectKey, String Screen){
	String sql="select * from Controls1 where ObjectKey='" + ObjectKey + "' and Screen='"+Screen+"'";
	System.out.println(sql);
	return sql;
}

public String queryForGetDataFromControlsByObjectKey(String ObjectKey){
	String query = "select * from Controls1 where ObjectKey= '" + ObjectKey + "'";
	return query;
}

public String queryForGetDataFromControlsByObjectKeyNameScreen(String ObjectKey, String Name, String Screen){
	String query = "select * from Controls1 where ObjectKey='" + ObjectKey + "'and Name='" + Name + "'and Screen='"
			+ Screen + "'";
	return query;
}

public String queryForGetDataFromControlsByObjectKeyName(String ObjectKey, String Name){
	String query = "select * from Controls1 where ObjectKey='"+ObjectKey+ "' and Name='" + Name + "'";
	return query;
}

public String queryForGetDataFromControlsByNameAndScreen(String Name, String Screen){
	String query = "select * from Controls1 where Name= '" + Name + "' and Screen='" + Screen + "'";
	
	return query;
}

public String queryForGetDataFromControlsByName(String Name){
	String query = "select * from Controls1 where Name='"+Name+"'";
	return query;
}

public String queryToGetLOVdataByObjectKey(String ObjectKey){
	String query = "Select * from Lovs where ObjectKey='" + ObjectKey + "'";
	return query;
}


public static SqliteConnection getInstance() throws SQLException{
	
	if (single_instance==null) {
		single_instance=new SqliteConnection();
		connect();
		
		
		
	}
	return single_instance;
}
	
public ResultSet GetDataSet(String key,String table) {
		
		String SQL="Select * from "+table + " where " +"Key ="+ "'"+key+"'"; 
		//System.out.println(SQL);
		
		ResultSet rs=null;
		try {
			connect();
		
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(SQL);
	//System.out.println(rs);
			  while (rs.next()) {
	                System.out.println(rs.getString(2)
	                                   );
	            }
			
		}
		catch(SQLException e){
			
	
	}
		return rs;
		

		
		
	}

public static void ConnectionClose() {
	
	try {
		if(conn!=null) {
			conn.close();
		}
	}
	catch(Exception e) {
		assertTrue(e.getLocalizedMessage(),false);
	}
}
	public static void main(String [] args) {
		SqliteConnection sql2=new SqliteConnection();
		connect();
	
		sql2.GetDataSet("A1","LE_GLEIFEntityStatus");
	}
	

	public ResultSet GetDataSet(String sql) {
		ResultSet rs=null;
		
		try {
//		SqliteConnection sql1=SqliteConnection.getInstance();
		Statement stmt=conn.createStatement();
		rs=stmt.executeQuery(sql);}
		catch(Exception e) {
			assertTrue(e.getLocalizedMessage(),false);
			
		
		}
		
		
		return rs;
		
		
	}
	
	
	public ResultSet buildQuery(String TableName,String ObjectKey) throws Exception {
		
		SqliteConnection sql=SqliteConnection.getInstance();
		ResultSet rst=sql.GetDataSet("select test,group_concat(colname,'') as concatval from (SELECT 'test' as test,'SELECT ''' ||name|| ''' as FieldName1,' ||name  || ' as fieldValue ' ||' from  \""+TableName +"\" where key=\"" + ObjectKey + "\" UNION ALL ' as colname from (select p.name as name from pragma_table_info('" + TableName +"')p where name !='Key')) group by test");
				
 				String query=rst.getString(2);
				query=query.substring(0,query.length() - "UNION ALL ".length());
				System.out.println(query);
				String anotherQuery="select b.datakey as FieldName,a.FieldValue,b.identificationType,b.locator,b.type,b.label from ("+ query + ") a left outer join Fielddata b "
           						+ " on b.objectkey like '" + TableName + ".'||a.FieldName1";
						System.out.println(anotherQuery);
		    rst=sql.GetDataSet(anotherQuery);
		return rst;
						
		
		
	}
	
	public ResultSet GetTaskGrid(String Key) throws SQLException{
		ResultSet rst=null;
		String sql="Select * from Controls1 where ObjectKey="+ "'"+ Key +"'and Name ='Task'" ;
		Statement stmt=conn.createStatement();
		rst=stmt.executeQuery(sql);
		
		return rst;
	}
	
	public ResultSet getControlsData( String Controls1,String dataKey) throws SQLException {
		ResultSet rst=null;
		String sql="Select * from "+Controls1 +" where ObjectKey="+ "'"+ dataKey +"'";
//		System.out.println(sql);
		Statement stmt=conn.createStatement();
		rst=stmt.executeQuery(sql);
		return rst;
	}
	
	public ResultSet getUsernameandPassword( String Users,String Key) throws SQLException {
		ResultSet rst=null;
		String sql="Select * from "+Users +" where Key="+ "'"+ Key +"'";
		System.out.println(sql);
		Statement stmt=conn.createStatement();
		rst=stmt.executeQuery(sql);
		return rst;
	}
	
	public ResultSet getControlsDataByName(String str) throws SQLException{
		ResultSet rst=null;
		String sql= queryForGetDataFromControlsByName(str);
		System.out.println(sql);
		Statement stmt=conn.createStatement();
		rst=stmt.executeQuery(sql);
		return rst;
	}
	
	public ResultSet getControlsDataAlongWithScreenName( String Controls1,String ObjectKey, String screenName) throws SQLException {
		ResultSet rst=null;
		String sql="Select * from "+Controls1 +" where ObjectKey="+ "'"+ ObjectKey +"' and Screen='"+screenName+"'";;
//		System.out.println(sql);
		Statement stmt=conn.createStatement();
		rst=stmt.executeQuery(sql);
		return rst;
	}
	
	public String queryForGetDataFromControlsByObjectKeyNameType(String ObjectKey, String Name, String Type){
		String query = "select * from Controls1 where ObjectKey='"+ObjectKey+ "' and Name='" + Name + "' and Type='"+Type+"'";
		return query;
	}		

}