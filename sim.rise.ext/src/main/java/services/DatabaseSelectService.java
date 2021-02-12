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
import java.sql.ResultSetMetaData;
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

	private ArrayList<HashMap<String,String>> parseResults(ResultSet results) throws SQLException{
		
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
        ResultSetMetaData rsmd = results.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (results.next()){	
        	HashMap<String, String> row = new HashMap<String, String>();
        	for(int i = 1; i <= columnCount; i++) {
        		String column = rsmd.getColumnLabel(i);
        		row.put(column,results.getString(column));
            }
        	resultsArray.add(row);	
        }  
        return resultsArray;
	}
	
	// return projects of a member
	public ArrayList <HashMap<String,String>> getMembersProjects(int memberID) throws IOException{
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "select * from \"FourthYearProject\".\"Project\" p inner join "
	            		+ "	(select pm.projectid from \"FourthYearProject\".\"Members\" m inner join "
	            		+ "		\"FourthYearProject\".\"Project_Members\" pm "
	            		+ "		on pm.memberid = m.memberid "
	            		+ "		where m.memberid = ? "
	            		+ "	) mp "
	            		+ "	on p.projectid = mp.projectid";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, memberID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		return resultsArray;
	}
	
	//return a member by ID
	public ArrayList <HashMap<String,String>> getMember(int memberID){
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT memberid, fname, lname"
					+ " FROM \"FourthYearProject\".\"Members\" m "
					+ " where m.memberid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, memberID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		return resultsArray;
	}
	
	//return a project - This will be the format of all the services
	public ArrayList<HashMap<String,String>> getProject(int projectID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT projectid, projectname, projectdescription, creationdate"
				+ " FROM \"FourthYearProject\".\"Project\" p "
				+ " where p.projectid = ?;";	
		
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, projectID);
            resultsArray = parseResults(pstmt.executeQuery());
        } catch (SQLException e) {
            System.out.println("SQLException in getProject()");
            e.printStackTrace();
        } finally {
            DbUtil.close(connection);
        }
        
	    return resultsArray;
	}

	//return the members of a project
	public ArrayList <HashMap<String,String>> getProjectMembers(int projectId){
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT m.memberid, m.fname, m.lname "
					+ " FROM \"FourthYearProject\".\"Project_Members\" pm "
					+ " inner join \"FourthYearProject\".\"Members\" m "
					+ " on pm.memberid = m.memberid "
					+ " inner join \"FourthYearProject\".\"Project\" p "
					+ " on pm.projectid = p.projectid "
					+ " where p.projectid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, projectId);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}

	//return a model
	public ArrayList <HashMap<String,String>> getModel(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT m.modelid modelid, modelname, description, creationdate, mt.modeltypename modeltype, sourcelanguage "
					+ "FROM \"FourthYearProject\".\"Model\" m "
					+ "inner join \"FourthYearProject\".\"ModelType\" mt " 
					+ "on m.modeltype = mt.modeltypeid "
					+ "where modelid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, modelID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	//return a model's files
	public ArrayList <HashMap<String,String>> getModelFiles(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, concat_ws(' ',m2.fname, m2.lname) as author, mf.modelid "
					+ " FROM \"FourthYearProject\".\"Model Files\" mf "
					+ " inner join \"FourthYearProject\".\"Model\" m "
					+ " on m.modelid = mf.modelid "
					+ " inner join \"FourthYearProject\".\"Members\" m2 "
					+ " on m2.memberid = mf.author "
					+ " where mf.modelid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, modelID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}

	//return a model's source files
	public ArrayList <HashMap<String,String>> getModelSourceFiles(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, concat_ws(' ',m2.fname, m2.lname) as author, mf.modelid "
				+ " FROM \"FourthYearProject\".\"Model Files\" mf "
				+ " inner join \"FourthYearProject\".\"Model\" m "
				+ " on m.modelid = mf.modelid "
				+ " inner join \"FourthYearProject\".\"Source Files\" sf "
				+ " on mf.fileid = sf.fileid "
				+ " inner join \"FourthYearProject\".\"Members\" m2 "
				+ " on m2.memberid = mf.author "
				+ " where mf.modelid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, modelID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}

	//return a model's result files
	public ArrayList <HashMap<String,String>> getModelResultFiles(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, concat_ws(' ',m2.fname, m2.lname) as author, mf.modelid "
				+ " FROM \"FourthYearProject\".\"Model Files\" mf "
				+ " inner join \"FourthYearProject\".\"Model\" m "
				+ " on m.modelid = mf.modelid "
				+ " inner join \"FourthYearProject\".\"Original Results\" or2 "
				+ " on mf.fileid = or2.fileid"
				+ " inner join \"FourthYearProject\".\"Members\" m2 "
				+ "on m2.memberid = mf.author "
				+ " where mf.modelid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, modelID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	
	public ArrayList <HashMap<String,String>> getSimulation(int simulationId) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, cr.\"visualizationNumber\" "
				+ " FROM \"FourthYearProject\".\"Model Files\" mf "
				+ " inner join \"FourthYearProject\".\"Converted Results\" cr "
				+ " on cr.fileid = mf.fileid "
				+ " where cr.\"visualizationNumber\" = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, simulationId);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}

	//return a model's converted files
	public ArrayList <HashMap<String,String>> getModelConvertedFiles(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT mf.fileid, mf.\"name\", mf.\"type\", mf.\"location\", mf.created, mf.description, concat_ws(' ',m2.fname, m2.lname) as author, mf.modelid, cr.\"visualizationNumber\" "
				+ " FROM \"FourthYearProject\".\"Model Files\" mf "
				+ " inner join \"FourthYearProject\".\"Model\" m "
				+ " on m.modelid = mf.modelid "
				+ " inner join \"FourthYearProject\".\"Converted Results\" "
				+ " cr on mf.fileid = cr.fileid"
				+ " inner join \"FourthYearProject\".\"Members\" m2 "
				+ "on m2.memberid = mf.author "
				+ " where mf.modelid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, modelID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}

	//return a model file
	public ArrayList <HashMap<String,String>> getFile(int fileID){
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT fileid, \"name\", \"type\", \"location\", created, description, author, modelid "
				+ " FROM \"FourthYearProject\".\"Model Files\" mf "
				+ " WHERE mf.fileid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, fileID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	//return the files written by a member
	public ArrayList <HashMap<String,String>> getMembersFiles(int memberID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT m.memberid, m.fname, m.lname "
				+ " from \"FourthYearProject\".\"Model Files\" mf "
				+ " inner join \"FourthYearProject\".\"Members\" m "
				+ " on mf.author = m.memberid "
				+ " where m.memberid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, memberID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	//return the authors of a file
	public ArrayList <HashMap<String,String>> getFilesAuthors(int fileID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT m.memberid, m.fname, m.lname from \"FourthYearProject\".\"Model Files\" mf "
		 			+ " inner join \"FourthYearProject\".\"Members\" m "
					+ " on mf.author = m.memberid"
					+ " where mf.fileid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, fileID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	
	
	//return all models that a project has
	public ArrayList <HashMap<String,String>> getProjectsModel(int projectID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "select m.modelid, m.modelname, m.description, m.creationdate, m.modeltype, m.sourcelanguage"
					+ " from \"FourthYearProject\".\"Project_Model\" pm "
					+ " inner join \"FourthYearProject\".\"Model\" m  "
					+ " on pm.model_id = m.modelid "
					+ " inner join \"FourthYearProject\".\"Project\" p "
					+ " on pm.project_id = p.projectid "
					+ " where p.projectid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, projectID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	//return all project a model is apart of
	public ArrayList <HashMap<String,String>> getModelsProjects(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT p.projectid, p.projectname, p.projectdescription, p.creationdate"
					+ " from \"FourthYearProject\".\"Project_Model\" pm "
					+ " inner join \"FourthYearProject\".\"Model\" m  "
					+ " on pm.model_id = m.modelid "
					+ " inner join \"FourthYearProject\".\"Project\" p "
					+ " on pm.project_id = p.projectid "
					+ " where m.modelid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, modelID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	//return a projects documentation file
	public ArrayList <HashMap<String,String>> getProjectsDocumentation(int projectid) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT pd.\"documentID\", pd.\"name\", pd.\"type\", pd.\"location\", pd.created, pd.description, pd.author, pd.projectid "
					+ " FROM \"FourthYearProject\".\"Project Documents\" pd "
					+ " inner join \"FourthYearProject\".\"Project\" p "
					+ " on pd.projectid = p.projectid "
					+ " where p.projectid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, projectid);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}

	// return a document file
	public ArrayList <HashMap<String,String>> getDocumentFile(int documentID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT pd.\"documentID\", pd.\"name\", pd.\"type\", pd.\"location\", pd.created, pd.description, pd.author, pd.projectid "
					+ " FROM \"FourthYearProject\".\"Project Documents\" pd "
					+ " where pd.\"documentID\" = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, documentID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	//return the members that wrote a file in a model
	public ArrayList <HashMap<String,String>> getMembersFromModel(int modelID) {
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT DISTINCT me.memberid, me.fname, me.lname"
					+ " from \"FourthYearProject\".\"Model Files\" mf "
					+ " inner join \"FourthYearProject\".\"Model\" mo "
					+ " on mo.modelid = mf.modelid "
					+ " inner join \"FourthYearProject\".\"Members\" me "
					+ " on mf.author = me.memberid "
					+ " where mf.modelid = ?;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, modelID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}

	//return all children of a model
	public ArrayList <HashMap<String,String>> getModelChildren(int modelID){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "with recursive cte_model as ( "
				+ "	select m2.parentid, m2.childid, 1 lev  from \"FourthYearProject\".\"Model Children\" m2 "
				+ "	where m2.parentid = ? " 
				+ "	union all "
				+ "	select mc.parentid, mc.childid, cte_model.lev+1 lev from cte_model "
				+ "	inner join \"FourthYearProject\".\"Model Children\" mc "
				+ "	on mc.parentid = cte_model.childid "
				+ " ) "
				+ " "
				+ " SELECT cte_model.parentid, pm.modelname parentName, cm.modelid, cm.modelname, cm.description, cm.creationdate, mt.modeltypename , cm.sourcelanguage, cte_model.lev as level "
				+ " FROM cte_model "
				+ " inner join \"FourthYearProject\".\"Model\" pm "
				+ " on pm.modelid = cte_model.parentid  "
				+ " inner join \"FourthYearProject\".\"Model\" cm "
				+ " on cm.modelid = cte_model.childid  "
				+ " inner join \"FourthYearProject\".\"ModelType\" mt "
				+ " on cm.modeltype = mt.modeltypeid  "
				+ " ORDER BY lev, modelname asc ";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, modelID);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllConvertedResults(){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT fileid, originalid, convertedid, simulationid FROM \"FourthYearProject\".\"Converted Results\" order by convertedid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllMembers(){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT memberid, fname, lname FROM \"FourthYearProject\".\"Members\" order by fname, lname;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllModels(){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT modelid, modelname, description, creationdate, modeltype, sourcelanguage FROM \"FourthYearProject\".\"Model\" order by modelname;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllModelFiles(){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT fileid, \"name\", \"type\", \"location\", created, description, author, modelid FROM \"FourthYearProject\".\"Model Files\" order by fileid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllOriginalResults(){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT fileid, sourceid, originalid FROM \"FourthYearProject\".\"Original Results\" order by fileid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllProjects(){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT projectid, projectname, projectdescription, creationdate FROM \"FourthYearProject\".\"Project\" order by projectid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllProjectDocuments(){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT \"documentID\", \"name\", \"type\", \"location\", created, description, author, projectid FROM \"FourthYearProject\".\"Project Documents\" order by \"documentID\";";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllSourceFiles(){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT fileid, sourceid FROM \"FourthYearProject\".\"Source Files\" order by sourceid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllMembersNotInProject(int projectId){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "select m.memberid, m.fname, m.lname "
				+ " from \"FourthYearProject\".\"Members\" m "
				+ " where m.memberid not in( "
				+ "	select m.memberid "
				+ "	from \"FourthYearProject\".\"Members\" m "
				+ "	inner join \"FourthYearProject\".\"Project_Members\" pm "
				+ "	on m.memberid = pm.memberid "
				+ "	where pm.projectid = ? "
				+ ")";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, projectId);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
	}
	
	public ArrayList <HashMap<String,String>> getAllModelsNotInProject(int projectId){		
		Connection connection = ConnectionFactory.getConnection();
		ArrayList<HashMap<String,String>> resultsArray = new ArrayList<HashMap<String,String>>();
		String sql = "select m.modelid, m.modelname, m.description, m.creationdate, m.modeltype, m.sourcelanguage "
				+ " from \"FourthYearProject\".\"Model\" m  "
				+ " where m.modelid not in( "
				+ "	select m.modelid  "
				+ "	from \"FourthYearProject\".\"Model\" m "
				+ "	inner join \"FourthYearProject\".\"Project_Model\" pm "
				+ "	on  m.modelid = pm.model_id  "
				+ "	where pm.project_id = ? "
				+ ")";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, projectId);
			resultsArray = parseResults(pstmt.executeQuery());
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
		
		return resultsArray;
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
	
}