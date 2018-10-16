import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class Server{

	private static final String MESSAGE_OK = "SUCCESS";
	private static final String MESSAGE_ERROR = "FAILURE";
	private static final String REQUEST_AUTHENTICATION = "authenticate";
	private static final String REQUEST_STUDENT_PROFILE = "studentprofile";
	private static final String REQUEST_UPDATE_PROFILE = "updateprofile";
	private static final String REQUEST_STUDENT_LIST = "studentlist";
	private static final String REQUEST_ADMIN_LIST = "adminlist";
	private static final String REQUEST_DELETE = "deleteprofile";
	private static final String REQUEST_ADD_STUDENT = "insertStudent";
	private static final String REQUEST_ADD_ADMIN = "insertadmin";
	private static final String REQUEST_SIGNUP = "signup";

	public static void main(String[] arg){

		try{
			ServerSocket myServerSocket = new ServerSocket(5555);
			while(true) {
				Socket incoming = myServerSocket.accept();
				ObjectOutputStream myOutputStream =
					new ObjectOutputStream(incoming.getOutputStream());
				ObjectInputStream myInputStream =
					new ObjectInputStream(incoming.getInputStream());
	
				try {
				
						Object myObject = myInputStream.readObject();
						System.out.println("Message received : " + ((DataObject) myObject).getMessage());
						Object response = null;
	
						switch(((DataObject) myObject).getMessage()){
							case REQUEST_AUTHENTICATION:
								LoginRequestModel requestModel = (LoginRequestModel) myObject;
								response = authenticate(requestModel.getUserName(), requestModel.getPassword());
								break;

							case REQUEST_STUDENT_PROFILE:
								StudentProfileRequestModel request = (StudentProfileRequestModel) myObject;
								response = getStudentProfile(request.getUserId());
								break;

							case REQUEST_UPDATE_PROFILE:
								Student updateProfileRequest = (Student) myObject;
								response = updateProfile(updateProfileRequest);
								break;

							case REQUEST_STUDENT_LIST:
								response = getStudentList();
								break;

							case REQUEST_DELETE:
								Student deleteRequest = (Student) myObject;
								response = deleteProfile(deleteRequest.getId());
								break;

							case REQUEST_ADD_STUDENT:
								Student addStudentRequest = (Student) myObject;
								response = addStudent(addStudentRequest);
								break;

							case REQUEST_ADMIN_LIST:
								response = getAdminList();
								break;

							case REQUEST_ADD_ADMIN:
								Student addAdminRequest = (Student) myObject;
								response = addAdmin(addAdminRequest);
								break;

							case REQUEST_SIGNUP:
								Student signupRequest = (Student) myObject;
								response = addStudent(signupRequest);
								break;
						}
	
						myOutputStream.writeObject(response);
				} finally {
					myOutputStream.close();
					myInputStream.close();
				} 
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private static DataObject addAdmin(Student student){
		DataObject response = new DataObject();
		try{
			Connection connection = getConnection();
			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();
			String query1 = "insert into tbl_user values(" + student.getId() +", \'"+student.getFirstName() +"\', \'"+student.getLastName() +"\', \'"+student.getEmail() +"\', \'"+student.getPhone() +"\', \'"+student.getDepartment() +"\', \'2018-05-16\')";
			String query2 = "insert into tbl_authentication values("+student.getId()+", \'"+student.getFirstName() +"\', \'"+student.getLastName() +"\', 0)";

			System.out.println(query1);
			System.out.println(query2);
			int rows1 = statement1.executeUpdate(query1);
			int rows2 = statement2.executeUpdate(query2);
			if(rows1 > 0 && rows2 > 0){
				response.setMessage(MESSAGE_OK);
			} else {
				response.setMessage(MESSAGE_ERROR);
			}
			connection.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		return response;
	}

	private static DataObject addStudent(Student student){
		DataObject response = new DataObject();
		try{
			Connection connection = getConnection();
			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();
			String query1 = "insert into tbl_user values(" + student.getId() +", \'"+student.getFirstName() +"\', \'"+student.getLastName() +"\', \'"+student.getEmail() +"\', \'"+student.getPhone() +"\', \'"+student.getDepartment() +"\', \'2018-05-16\')";
			String query2 = "insert into tbl_authentication values("+student.getId()+", \'"+student.getFirstName() +"\', \'"+student.getLastName() +"\', 1)";

			System.out.println(query1);
			System.out.println(query2);
			int rows1 = statement1.executeUpdate(query1);
			int rows2 = statement2.executeUpdate(query2);
			if(rows1 > 0 && rows2 > 0){
				response.setMessage(MESSAGE_OK);
			} else {
				response.setMessage(MESSAGE_ERROR);
			}
			connection.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		return response;
	}

	private static StudentListResponse getStudentList(){
		StudentListResponse response = new StudentListResponse();
		try{
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM tbl_user u, tbl_authentication a WHERE u.id = a.userid and a.type = 1";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				response.setMessage(MESSAGE_OK);
				List<Student> studentsList = new ArrayList();

				do{
					Student student = new Student();
					student.setId(resultSet.getInt("id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					student.setEmail(resultSet.getString("email"));
					student.setPhone(resultSet.getString("contact_number"));
					student.setDepartment(resultSet.getString("department"));
					student.setDob(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getTimestamp("dob")));
					studentsList.add(student);
				} while(resultSet.next());
				response.setStudentsList(studentsList);
			} else {
				response.setMessage(MESSAGE_ERROR);
			}
			connection.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return response;
	}

	private static StudentListResponse getAdminList(){
		StudentListResponse response = new StudentListResponse();
		try{
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM tbl_user u, tbl_authentication a WHERE u.id = a.userid and a.type = 0";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				response.setMessage(MESSAGE_OK);
				List<Student> studentsList = new ArrayList();

				do{
					Student student = new Student();
					student.setId(resultSet.getInt("id"));
					student.setFirstName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					student.setEmail(resultSet.getString("email"));
					student.setPhone(resultSet.getString("contact_number"));
					student.setDepartment(resultSet.getString("department"));
					student.setDob(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getTimestamp("dob")));
					studentsList.add(student);
				} while(resultSet.next());
				response.setStudentsList(studentsList);
			} else {
				response.setMessage(MESSAGE_ERROR);
			}
			connection.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return response;
	}

	private static Student getStudentProfile(int id){
		Student response = new Student();
		try{
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String query = "select * from tbl_user where id = " + id;
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				response.setMessage(MESSAGE_OK);
				response.setId(resultSet.getInt("id"));
				response.setFirstName(resultSet.getString("first_name"));
				response.setLastName(resultSet.getString("last_name"));
				response.setEmail(resultSet.getString("email"));
				response.setPhone(resultSet.getString("contact_number"));
				response.setDepartment(resultSet.getString("department"));
				response.setDob(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getTimestamp("dob")));
			} else {
				response.setMessage(MESSAGE_ERROR);
			}
			connection.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return response;
	}

	private static DataObject deleteProfile(int id){
		DataObject response = new DataObject();
		try{
			Connection connection = getConnection();
			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();
			String query1 = "delete from tbl_user where id = " + id;
			String query2 = "delete from tbl_authentication where userid = " + id;

			System.out.println(query1);
			System.out.println(query2);
			int rows1 = statement1.executeUpdate(query1);
			int rows2 = statement2.executeUpdate(query2);
			if(rows1 > 0 && rows2 > 0){
				response.setMessage(MESSAGE_OK);
			} else {
				response.setMessage(MESSAGE_ERROR);
			}
			connection.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return response;
	}


	private static Student updateProfile(Student request){
		Student response = new Student();
		try{
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String query = "update tbl_user set first_name = \'" + request.getFirstName() + "\', last_name = \'" + request.getLastName() + "\', email = \'" + request.getEmail() + "\',contact_number = \'" + request.getPhone()+ "\', department = \'" + request.getDepartment()+ "\' where id = " + request.getId();
			System.out.println(query);
			int rowsEffected = statement.executeUpdate(query);
			if(rowsEffected > 0) {
				response.setMessage(MESSAGE_OK);
			} else {
				response.setMessage(MESSAGE_ERROR);
			}
			connection.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return response;
	}

	private static LoginResponseModel authenticate(String userName, String password){
		LoginResponseModel response = new LoginResponseModel();
		try{
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String query = "select * from tbl_authentication where username = '" + userName + "' and password = '" + password + "'";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.next()){
				response.setMessage(MESSAGE_OK);
				response.setType(resultSet.getInt("type"));
				response.setUserId(resultSet.getInt("userid"));
			} else {
				response.setMessage(MESSAGE_ERROR);
			}
			connection.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return response;
	}

	private static Connection getConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://sql1.njit.edu/sg943", "sg943", "bamboo57");	
		} catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		} catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}
