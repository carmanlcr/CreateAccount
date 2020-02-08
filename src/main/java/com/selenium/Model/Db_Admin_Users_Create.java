package com.selenium.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.selenium.Connection.ConnectionDB;
import com.selenium.Interface.Model;


public class Db_Admin_Users_Create implements Model {
	
	private final String TABLE_NAME="db_admin_users_create";
	private int users_create_id;
	private String full_name;
	private int phones_id;
	private String phones;
	private boolean gender;
	private String date_of_birth;
	private String password;
	private String username;
	private String vpn;
	private String email;
	private boolean active;
	private boolean create_fb;
	private boolean create_ig;
	private boolean create_tw;
	private String created_at;
	private String updated_at;
	private Date date;
	private SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private SimpleDateFormat dateBD = new SimpleDateFormat("yyyy-MM-dd");
	private ConnectionDB conn = new ConnectionDB(); 
	

	public void insert() throws SQLException {

	}

	public void update() throws SQLException {
		date = new Date();
		setUpdated_at(timestamp.format(date));
		String update = "UPDATE "+TABLE_NAME+ " SET full_name = ?, phones_id = ?,"
				+ "email = ?, gender = ?, date_of_birth = ?, password = ?, vpn = ?, active = ?,"
				+ "create_fb = ?, create_ig = ?, create_tw = ?, created_at = ?, updated_at = ?, username = ? WHERE users_create_id = ?;";
		try(Connection conexion = conn.conectar();
				PreparedStatement pre = conexion.prepareStatement(update)){
			pre.setString(1, getFull_name());
			pre.setInt(2, getPhones_id());
			pre.setString(3, getEmail());
			pre.setBoolean(4, isGender());
			pre.setString(5, getDate_of_birth());
			pre.setString(6, getPassword());
			pre.setString(7, getVpn());
			pre.setBoolean(8, isActive());
			pre.setBoolean(9, isCreate_fb());
			pre.setBoolean(10, isCreate_ig());
			pre.setBoolean(11, isCreate_tw());
			pre.setString(12, getCreated_at());
			pre.setString(13, getUpdated_at());
			pre.setString(14, getUsername());
			pre.setInt(15, getUsers_create_id());
			
			pre.executeUpdate();
		}
	}
	
	public List<Db_Admin_Users_Create> getUserForCreate(){
		List<Db_Admin_Users_Create> listU = new ArrayList<Db_Admin_Users_Create>();
		Db_Admin_Users_Create usersC = null;
		
		String query = "SELECT * FROM "+TABLE_NAME+" uc "
				+"INNER JOIN db_admin_phones ph ON ph.phones_id = uc.phones_id "
				+ "INNER JOIN db_admin_users_create_details ucd ON ucd.users_create_id = uc.users_create_id "
				+ "WHERE uc.active = ? AND (create_fb = 0 OR create_ig = 0 OR create_tw = 0);";
		try(Connection conexion = conn.conectar();
				PreparedStatement pr = conexion.prepareStatement(query);){
			pr.setInt(1, 1);
			ResultSet rs = pr.executeQuery();
			while(rs.next()) {
				usersC = new Db_Admin_Users_Create();
				usersC.setUsers_create_id(rs.getInt("uc.users_create_id"));
				usersC.setFull_name(rs.getString("uc.full_name"));
				usersC.setPhones_id(rs.getInt("uc.phones_id"));
				usersC.setPhones(rs.getString("ph.phone"));
				usersC.setEmail(rs.getString("uc.email"));
				usersC.setUsername(rs.getString("uc.username"));
				usersC.setGender(rs.getBoolean("uc.gender"));
				usersC.setDate_of_birth(rs.getString("uc.date_of_birth"));
				usersC.setPassword(rs.getString("uc.password"));
				usersC.setVpn(rs.getString("uc.vpn"));
				usersC.setActive(rs.getBoolean("uc.active"));
				usersC.setCreate_fb(rs.getBoolean("uc.create_fb"));
				usersC.setCreate_ig(rs.getBoolean("uc.create_ig"));
				usersC.setCreate_tw(rs.getBoolean("uc.create_tw"));
				usersC.setCreated_at(rs.getString("uc.created_at"));
				usersC.setUpdated_at(rs.getString("uc.updated_at"));
				listU.add(usersC);
			}
		}catch (SQLException e) {
			System.err.println(e);
		}
		
		return listU;
	}
	
	public int getUsers_create_id() {
		return users_create_id;
	}

	public void setUsers_create_id(int users_create_id) {
		this.users_create_id = users_create_id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPhones_id() {
		return phones_id;
	}

	public void setPhones_id(int phones_id) {
		this.phones_id = phones_id;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVpn() {
		return vpn;
	}

	public void setVpn(String vpn) {
		this.vpn = vpn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isCreate_fb() {
		return create_fb;
	}

	public void setCreate_fb(boolean create_fb) {
		this.create_fb = create_fb;
	}

	public boolean isCreate_ig() {
		return create_ig;
	}

	public void setCreate_ig(boolean create_ig) {
		this.create_ig = create_ig;
	}

	public boolean isCreate_tw() {
		return create_tw;
	}

	public void setCreate_tw(boolean create_tw) {
		this.create_tw = create_tw;
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
