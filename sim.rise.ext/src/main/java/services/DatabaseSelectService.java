package services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import util.ConnectionFactory;
import util.DbUtil;

public class DatabaseSelectService {

	public String testConnection() throws IOException {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            
	            String sql = "SELECT * from \"FourthYearProject\".\"Members\"";	
				java.sql.Statement stmt = connection.createStatement();
	            
				results = stmt.executeQuery(sql);
	            while (results.next()){	
	            	returnThis += results.getString("Fname") + " " + results.getString("Lname") + "\n";
	            }
	        } catch (SQLException e) {
	            System.out.println("SQLException in get() method");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return returnThis;
	}
	
	//return a member by lastname and firstname
	public String getAuthorAccount(String lname, String fname) throws IOException{
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            
	            String sql = "SELECT * FROM \"FourthYearProject\".\"Members\" where  lname = ? and fname= ?";
	
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, lname);
	            pstmt.setString(2, fname);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	returnThis += results.getString("memberid") + " " +  results.getString("fname") + " " + results.getString("lname") + "\n";
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in get() method");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
	        return returnThis;
	}
	
	// return projects of a member
	public String getMembersProjects(int memberID) throws IOException{
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            
	            String sql = "select * from \"FourthYearProject\".\"Project\" p inner join "
	            		+ "	(select pm.projectid from \"FourthYearProject\".\"Members\" m inner join "
	            		+ "		\"FourthYearProject\".\"Project_Members\" pm "
	            		+ "		on pm.memberid = m.memberid "
	            		+ "		where m.memberid = ? "
	            		+ "	) mp "
	            		+ "	on p.projectid = mp.projectid";
	
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, memberID);
	      
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	returnThis += "Project ID: " + results.getString("projectid")
	            				+ " Project Name: " +  results.getString("projectname")
	            				+ " Project Description: " + results.getString("projectdescription") 
	            				+ " Creation Date: " + results.getString("creationdate") + " \n";
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in get() method");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
	        return returnThis;
	}
	
	//return a member by ID
	public String getMember(int memberID){
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            
		String sql = "SELECT memberid, fname, lname"
					+ "FROM \"FourthYearProject\".\"Members\" m "
					+ "where m.memberid = ?;";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, memberID);
        
        
        results = pstmt.executeQuery();
        while (results.next()){	
        	return results.getString("memberid");
        	
        }    
    } catch (SQLException e) {
        System.out.println("SQLException in addMemberAccount()");
        e.printStackTrace();
    } finally {
        DbUtil.close(connection);
    }
		return "return member by ID";
	}
	
	//return a project - This will be the format of all the services
	public ArrayList getProject(int projectID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap> resultsArray = new ArrayList<HashMap>();
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT projectid, projectname, projectdescription, creationdate"
					+ " FROM \"FourthYearProject\".\"Project\" p "
					+ " where p.projectid = ?;";	
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, projectID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	HashMap<String, String> row = new HashMap<String, String>();
	            	row.put("projectid",results.getString("projectid"));
	            	row.put("projectname",results.getString("projectname"));
	            	row.put("projectdescription",results.getString("projectdescription"));
	            	row.put("creationdate",results.getString("creationdate"));
	            	resultsArray.add(row);	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return resultsArray;
	}
/*
	//return the members of a project
	public String getProjectMembers(int modelID){
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT m.memberid, m.fname, m.lname "
					+ "FROM \"FourthYearProject\".\"Project_Members\" pm "
					+ "inner join \"FourthYearProject\".\"Members\" m "
					+ "on pm.memberid = m.memberid "
					+ "inner join \"FourthYearProject\".\"Project\" p "
					+ "on pm.projectid = p.projectid "
					+ "where p.projectid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, modelID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "members of a project";
	}

	//return a model
	public String getModel(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT modelid, modelname, description, creationdate, modeltype, sourcelanguage "
					+ "FROM \"FourthYearProject\".\"Model\" "
					+ "where modelid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, modelID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return a model";
	}
	
	//return a model's files
	public String getModelFiles(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, mf.author, mf.modelid "
					+ "FROM \"FourthYearProject\".\"Model Files\" mf "
					+ "inner join \"FourthYearProject\".\"Model\" m "
					+ "on m.modelid = mf.modelid "
					+ "where mf.modelid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, modelID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		
		return "return a model's files";
	}

	//return a model's source files
	public String getModelSourceFiles(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, mf.author, mf.modelid "
				+ "FROM \"FourthYearProject\".\"Model Files\" mf "
				+ "inner join \"FourthYearProject\".\"Model\" m "
				+ "on m.modelid = mf.modelid "
				+ "inner join \"FourthYearProject\".\"Source Files\" sf "
				+ "on mf.fileid = sf.fileid"
				+ "where mf.modelid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, modelID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return a model's source files";
	}

	//return a model's result files
	public String getModelResultFiles(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, mf.author, mf.modelid "
				+ "FROM \"FourthYearProject\".\"Model Files\" mf "
				+ "inner join \"FourthYearProject\".\"Model\" m "
				+ "on m.modelid = mf.modelid "
				+ "inner join \"FourthYearProject\".\"Original Results\" or2 "
				+ "on mf.fileid = or2.fileid"
				+ "where mf.modelid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, modelID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return a model's result files";
	}

	//return a model's converted files
	public String getModelConvertedFiles(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, mf.author, mf.modelid "
				+ "FROM \"FourthYearProject\".\"Model Files\" mf "
				+ "inner join \"FourthYearProject\".\"Model\" m "
				+ "on m.modelid = mf.modelid "
				+ "inner join \"FourthYearProject\".\"Converted Results\" "
				+ "cr on mf.fileid = cr.fileid"
				+ "where mf.modelid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, modelID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return a model's converted files";
	}

	//return a model file
	public String getFile(int fileID){
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT fileid, \"name\", \"type\", \"location\", created, description, author, modelid "
				+ "FROM \"FourthYearProject\".\"Model Files\" mf "
				+ "WHERE mf.fileid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, fileID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return a model file";
	}
	
	//return the files written by a member
	public String GetMembersFiles(int memberID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT m.memberid, m.fname, m.lname "
				+ " from \"FourthYearProject\".\"Model Files\" mf "
				+ " inner join \"FourthYearProject\".\"Members\" m "
				+ " on mf.author = m.memberid "
				+ " where m.memberid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, memberID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "Get the files written by a member";
	}
	
	//return the authors of a file
	public String GetFilesAuthors(int fileID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, mf.author, mf.modelid "
				+ " from \"FourthYearProject\".\"Model Files\" mf "
				+ " inner join \"FourthYearProject\".\"Members\" m "
				+ " on mf.author = m.memberid "
				+ " where mf.fileid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, fileID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return the authors of a file";
	}
	
	
	
	//return all models that a project has
	public String getProjectsModel(int projectID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "select m.modelid, m.modelname, m.description, m.creationdate, m.modeltype, m.sourcelanguage"
					+ " from \"FourthYearProject\".\"Project_Model\" pm "
					+ " inner join \"FourthYearProject\".\"Model\" m  "
					+ " on pm.model_id = m.modelid "
					+ " inner join \"FourthYearProject\".\"Project\" p "
					+ " on pm.project_id = p.projectid "
					+ " where p.projectid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, projectID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		
		
		return "return all models that a project has";
	}
	
	//return all project a model is apart of
	public String getModelsProjects(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT p.projectid, p.projectname, p.projectdescription, p.creationdate"
					+ " from \"FourthYearProject\".\"Project_Model\" pm "
					+ " inner join \"FourthYearProject\".\"Model\" m  "
					+ " on pm.model_id = m.modelid "
					+ " inner join \"FourthYearProject\".\"Project\" p "
					+ " on pm.project_id = p.projectid "
					+ " where m.modelid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, modelID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		
		return "return all project a model is apart of";
	}
	
	//return a projects documentation file
	public String getProjectsDocumentation(int projectid) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT pd.\"documentID\", pd.\"name\", pd.\"type\", pd.\"location\", pd.created, pd.description, pd.author, pd.projectid "
					+ "FROM \"FourthYearProject\".\"Project Documents\" pd "
					+ "inner join \"FourthYearProject\".\"Project\" p "
					+ "on pd.projectid = p.projectid "
					+ "where p.projectid = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, projectid);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return a project documentation file";
	}

	// return a document file
	public String getDocumentFile(int documentID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT pd.\"documentID\", pd.\"name\", pd.\"type\", pd.\"location\", pd.created, pd.description, pd.author, pd.projectid "
					+ "FROM \"FourthYearProject\".\"Project Documents\" pd "
					+ "where pd.\"documentID\" = ?;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, documentID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return a document file";
	}
	
	//return the members that wrote a file in a model
	public String getMembersFromModel(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "SELECT me.memberid, me.fname, me.lname"
				+ " from \"FourthYearProject\".\"Model Files\" mf "
				+ " inner join \"FourthYearProject\".\"Model\" mo "
				+ " on mo.modelid = mf.modelid "
				+ " inner join \"FourthYearProject\".\"Converted Results\" cr "
				+ " on mf.fileid = cr.fileid "
				+ " inner join \"FourthYearProject\".\"Members\" me "
				+ " on mf.author = me.memberid "
				+ " where mf.modelid = 60;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, modelID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return the members that wrote a file in a model";
	}

	//return all children of a model
	public String getModelChildren(int modelID){		
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            String sql = "with recursive cte_model as ("
		+ "	select m2.parentid, m2.childid, 1 lev  from \"FourthYearProject\".\"Model Children\" m2 "
		+ "	where m2.parentid = 65"
		+ "	union all"
		+ "	select mc.parentid, mc.childid, cte_model.lev+1 lev from cte_model"
		+ "	inner join \"FourthYearProject\".\"Model Children\" mc "
		+ "	on mc.parentid = cte_model.childid\r\n"
		+ ")"
		+ " SELECT cte_model.parentid, pm.modelname parentName, cte_model.childid, cm.modelname childName, cte_model.lev as level"
		+ " FROM cte_model"
		+ " inner join \"FourthYearProject\".\"Model\" pm"
		+ " on pm.modelid = cte_model.parentid"
		+ " inner join \"FourthYearProject\".\"Model\" cm"
		+ " on cm.modelid = cte_model.childid"
		+ " ORDER BY lev ASC;";
	            PreparedStatement pstmt = connection.prepareStatement(sql);
	            pstmt.setInt(1, modelID);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return "return all children of a model";
	}
	
	public int addMemberAccount(String lname, String fname) throws IOException{
		Connection connection = ConnectionFactory.getConnection();
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            
	            String sql = "INSERT INTO \"FourthYearProject\".\"Members\" (fname, lname) VALUES(?, ?) RETURNING \"Members\".memberid;";
	
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, fname);
	            pstmt.setString(2, lname);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	return results.getInt("memberid");
	            	
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in addMemberAccount()");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
	        return 0;
	}
	*/
}