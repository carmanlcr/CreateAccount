package com.selenium.Model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.selenium.Connection.ConnectionFB;
import com.selenium.Connection.ConnectionIG;
import com.selenium.Connection.ConnectionReviews;
import com.selenium.Connection.ConnectionTW;
import com.selenium.Interface.Model;

public class User implements Model {
	
	private final String TABLE_NAME = "users";
	private int users_id;
	private String full_name;
	private String username;
	private String email;
	private String password;
	private String phone;
	private String date_of_birth;
	private String created_at;
	private String updated_at;
	private Date date;
	private SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private ConnectionDB conn = new ConnectionDB(); 
	private ConnectionReviews connR = new ConnectionReviews();
	private ConnectionFB connF = new ConnectionFB();
	private ConnectionTW connT = new ConnectionTW();
	private ConnectionIG connI = new ConnectionIG();
	
	@Override
	public void insert() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() throws SQLException {
		// TODO Auto-generated method stub

	}
	
	public User getUserReview() {
		User user = null;
		String query = "SELECT * FROM "+TABLE_NAME+" WHERE phone = ?;";
		try(Connection conexion = connR.conectar();
				PreparedStatement pre = conexion.prepareStatement(query);){
			pre.setString(1, getPhone());
			ResultSet rs = pre.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setUsers_id(rs.getInt("users_id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
			}
		}catch (SQLException e) {
			System.err.println(e);
		}
		return user;
	}

	public void insertReviews(String vpn_name) throws SQLException {
		date = new Date();
		setCreated_at(timestamp.format(date));
		setUpdated_at(timestamp.format(date));
		String insert = "INSERT INTO "+TABLE_NAME+"(email,password,phone,vpn_id,created_at,updated_at) "
				+ " VALUE (?,?,?,?,?,?);";
		try (Connection conexion = connR.conectar();
				PreparedStatement  query = conexion.prepareStatement(insert);){
			
			query.setString(1, getEmail());
			query.setString(2, getPassword());
			query.setString(3,getPhone());
			String queryVpn = "SELECT * FROM vpn WHERE UPPER(name) = ?";
			PreparedStatement pe = conexion.prepareStatement(queryVpn);
			pe.setString(1, vpn_name.toUpperCase());
			ResultSet rse = pe.executeQuery();
			int vpn_id = 0;
			if(rse.next()) {
				vpn_id = rse.getInt("vpn_id");
			}
			
			if(vpn_id == 0) {
				String insertVpn = "INSERT INTO vpn(name,created_at, updated_at) VALUES(?,?,?);";
				pe = conexion.prepareStatement(insertVpn);
				pe.setString(1, vpn_name);
				pe.setString(2, getCreated_at());
				pe.setString(3, getUpdated_at());
				
				pe.executeUpdate();
				
				queryVpn = "SELECT vpn_id FROM vpn ORDER BY vpn_id DESC LIMIT 1;";
				pe = conexion.prepareStatement(queryVpn);
				rse = pe.executeQuery();
				if(rse.next()) {
					vpn_id = rse.getInt("vpn_id");
				}
			}
			query.setInt(4, vpn_id);
			query.setString(5,getCreated_at());
			query.setString(6, getUpdated_at());
			query.executeUpdate();

		}catch(SQLException e) {
			System.err.println(e);
		}
	}
	
	public void insertFacebook(String vpn_name) throws SQLException {
		date = new Date();
		setCreated_at(timestamp.format(date));
		setUpdated_at(timestamp.format(date));
		
		String insert = "INSERT INTO "+TABLE_NAME+"(username,email,full_name,phone,password,creator,date_of_birth,created_at,updated_at,sim_card_number,vpn_id)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?);";
		try(Connection conexion = connF.conectar();
				PreparedStatement pr = conexion.prepareStatement(insert)){
			pr.setString(1, getUsername());
			pr.setString(2,getEmail());
			pr.setString(3, getFull_name());
			pr.setBigDecimal(4, new BigDecimal(getPhone()));
			pr.setString(5, getPassword());
			pr.setString(6, "APP");
			pr.setString(7, getDate_of_birth());
			pr.setString(8, getCreated_at());
			pr.setString(9, getUpdated_at());
			pr.setInt(10, 0);
			String queryVpn = "SELECT * FROM vpn WHERE UPPER(name) = ?";
			PreparedStatement pe = conexion.prepareStatement(queryVpn);
			pe.setString(1, vpn_name.toUpperCase());
			ResultSet rse = pe.executeQuery();
			int vpn_id = 0;
			if(rse.next()) {
				vpn_id = rse.getInt("vpn_id");
			}
			
			if(vpn_id == 0) {
				String insertVpn = "INSERT INTO vpn(name,created_at, updated_at) VALUES(?,?,?);";
				pe = conexion.prepareStatement(insertVpn);
				pe.setString(1, vpn_name);
				pe.setString(2, getCreated_at());
				pe.setString(3, getUpdated_at());
				
				pe.executeUpdate();
				
				queryVpn = "SELECT vpn_id FROM vpn ORDER BY vpn_id DESC LIMIT 1;";
				pe = conexion.prepareStatement(queryVpn);
				rse = pe.executeQuery();
				if(rse.next()) {
					vpn_id = rse.getInt("vpn_id");
				}
			}
			pr.setInt(11, vpn_id);
			
			pr.executeUpdate();
			
			String queryLast = "SELECT * FROM "+TABLE_NAME+" ORDER BY users_id DESC LIMIT 1;";
			pe = conexion.prepareStatement(queryLast);
			
			ResultSet rs = pe.executeQuery();
			int users_id = 0;
			if(rs.next()) {
				users_id = rs.getInt("users_id");
			}
			
			String queryUserCa = "INSERT INTO users_categories(users_id,categories_id,created_at,updated_at)" + 
								 " VALUE (?,?,?,?);";
			pe = conexion.prepareStatement(queryUserCa);
			pe.setInt(1, users_id);
			pe.setInt(2, 3);
			pe.setString(3, getCreated_at());
			pe.setString(4, getUpdated_at());
			pe.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertTwitter(String vpn_name) throws SQLException{
		date = new Date();
		setCreated_at(timestamp.format(date));
		setUpdated_at(timestamp.format(date));
		
		String insert = "INSERT INTO "+TABLE_NAME+"(username,email,full_name,phone,"
				+ "password,creator,date_of_birth,created_at,updated_at,sim_card_number,vpn_id)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?);";
		try(Connection conexion = connT.conectar();
				PreparedStatement pr = conexion.prepareStatement(insert);){
			pr.setString(1, getUsername());
			pr.setString(2,getEmail());
			pr.setString(3, getFull_name());
			pr.setBigDecimal(4, new BigDecimal(getPhone()));
			pr.setString(5, getPassword());
			pr.setString(6, "APP");
			pr.setString(7, getDate_of_birth());
			pr.setString(8, getCreated_at());
			pr.setString(9, getUpdated_at());
			pr.setInt(10, 0);
			String queryVpn = "SELECT * FROM vpn WHERE UPPER(name) = ?";
			PreparedStatement pe = conexion.prepareStatement(queryVpn);
			pe.setString(1, vpn_name.toUpperCase());
			ResultSet rse = pe.executeQuery();
			int vpn_id = 0;
			if(rse.next()) {
				vpn_id = rse.getInt("vpn_id");
			}
			
			if(vpn_id == 0) {
				String insertVpn = "INSERT INTO vpn(name,created_at, updated_at) VALUES(?,?,?);";
				pe = conexion.prepareStatement(insertVpn);
				pe.setString(1, vpn_name);
				pe.setString(2, getCreated_at());
				pe.setString(3, getUpdated_at());
				
				pe.executeUpdate();
				
				queryVpn = "SELECT vpn_id FROM vpn ORDER BY vpn_id DESC LIMIT 1;";
				pe = conexion.prepareStatement(queryVpn);
				rse = pe.executeQuery();
				if(rse.next()) {
					vpn_id = rse.getInt("vpn_id");
				}
			}
			pr.setInt(11, vpn_id);
			
			pr.executeUpdate();
			
			String queryLast = "SELECT * FROM "+TABLE_NAME+" ORDER BY users_id DESC LIMIT 1;";
			pe = conexion.prepareStatement(queryLast);
			
			ResultSet rs = pe.executeQuery();
			int users_id = 0;
			if(rs.next()) {
				users_id = rs.getInt("users_id");
			}
			
			String queryUserCa = "INSERT INTO users_categories(users_id,categories_id,created_at,updated_at)" + 
								 " VALUE (?,?,?,?);";
			pe = conexion.prepareStatement(queryUserCa);
			pe.setInt(1, users_id);
			pe.setInt(2, 3);
			pe.setString(3, getCreated_at());
			pe.setString(4, getUpdated_at());
			pe.executeUpdate();
		}catch (SQLException e) {
			System.err.println(e);	
		}
	}
	
	public void insertInstagram(String vpn_name) throws SQLException {
		date = new Date();
		setCreated_at(timestamp.format(date));
		setUpdated_at(timestamp.format(date));
		
		String insert = "INSERT INTO "+TABLE_NAME+"(username,email,full_name,phone,password,creator,"
				+ "date_of_birth,created_at,updated_at,sim_card_number,vpn_id)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?);";
		try(Connection conexion = connI.conectar();
				PreparedStatement pr = conexion.prepareStatement(insert)){
			pr.setString(1, getUsername());
			pr.setString(2,getEmail());
			pr.setString(3, getFull_name());
			pr.setBigDecimal(4, new BigDecimal(getPhone()));
			pr.setString(5, getPassword());
			pr.setString(6, "APP");
			pr.setString(7, getDate_of_birth());
			pr.setString(8, getCreated_at());
			pr.setString(9, getUpdated_at());
			pr.setInt(10, 0);
			String queryVpn = "SELECT * FROM vpn WHERE UPPER(name) = ?";
			PreparedStatement pe = conexion.prepareStatement(queryVpn);
			pe.setString(1, vpn_name.toUpperCase());
			ResultSet rse = pe.executeQuery();
			int vpn_id = 0;
			if(rse.next()) {
				vpn_id = rse.getInt("vpn_id");
			}
			
			if(vpn_id == 0) {
				String insertVpn = "INSERT INTO vpn(name,created_at, updated_at) VALUES(?,?,?);";
				pe = conexion.prepareStatement(insertVpn);
				pe.setString(1, vpn_name);
				pe.setString(2, getCreated_at());
				pe.setString(3, getUpdated_at());
				
				pe.executeUpdate();
				
				queryVpn = "SELECT vpn_id FROM vpn ORDER BY vpn_id DESC LIMIT 1;";
				pe = conexion.prepareStatement(queryVpn);
				rse = pe.executeQuery();
				if(rse.next()) {
					vpn_id = rse.getInt("vpn_id");
				}
			}
			pr.setInt(11, vpn_id);
			
			pr.executeUpdate();
			
			String queryLast = "SELECT * FROM "+TABLE_NAME+" ORDER BY users_id DESC LIMIT 1;";
			pe = conexion.prepareStatement(queryLast);
			
			ResultSet rs = pe.executeQuery();
			int users_id = 0;
			if(rs.next()) {
				users_id = rs.getInt("users_id");
			}
			
			String queryUserCa = "INSERT INTO users_categories(users_id,categories_id,created_at,updated_at)" + 
								 " VALUE (?,?,?,?);";
			pe = conexion.prepareStatement(queryUserCa);
			pe.setInt(1, users_id);
			pe.setInt(2, 3);
			pe.setString(3, getCreated_at());
			pe.setString(4, getUpdated_at());
			pe.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
}
