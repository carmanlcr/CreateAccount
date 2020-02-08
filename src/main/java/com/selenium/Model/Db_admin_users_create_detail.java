package com.selenium.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.selenium.Connection.ConnectionDB;
import com.selenium.Interface.Model;

public class Db_admin_users_create_detail implements Model {
	
	private final String TABLE_NAME = "db_admin_users_create_details";
	private int users_create_details_id; 
	private String fPerfil; 
	private String fPortada; 
	private String fAdicional; 
	private boolean active; 
	private String created_at; 
	private String updated_at; 
	private int users_create_id;
//	private Date date;
//	private SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ConnectionDB conn = new ConnectionDB(); 
	
	
	@Override
	public void insert() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() throws SQLException {
		// TODO Auto-generated method stub

	}
	
	public Db_admin_users_create_detail getDetail() {
		Db_admin_users_create_detail create_detail = null;
		String query = "SELECT * FROM "+TABLE_NAME+" WHERE users_create_id = ?;";
		try(Connection conexion = conn.conectar();
				PreparedStatement pr = conexion.prepareStatement(query);){
			pr.setInt(1, getUsers_create_id());
			ResultSet rs = pr.executeQuery();
			if(rs.next()) {
				create_detail = new Db_admin_users_create_detail();
				create_detail.setUsers_create_details_id(rs.getInt("users_create_details_id"));
				create_detail.setfPerfil(rs.getString("fPerfil"));
				create_detail.setfPortada(rs.getString("fPortada"));
				create_detail.setfAdicional(rs.getString("fAdicional"));
				create_detail.setUsers_create_id(rs.getInt("users_create_id"));
			}
		}catch(SQLException e) {
			System.err.println(e);
		}
		return create_detail;
	}

	public int getUsers_create_details_id() {
		return users_create_details_id;
	}

	public void setUsers_create_details_id(int users_create_details_id) {
		this.users_create_details_id = users_create_details_id;
	}

	public String getfPerfil() {
		return fPerfil;
	}

	public void setfPerfil(String fPerfil) {
		this.fPerfil = fPerfil;
	}

	public String getfPortada() {
		return fPortada;
	}

	public void setfPortada(String fPortada) {
		this.fPortada = fPortada;
	}

	public String getfAdicional() {
		return fAdicional;
	}

	public void setfAdicional(String fAdicional) {
		this.fAdicional = fAdicional;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public int getUsers_create_id() {
		return users_create_id;
	}

	public void setUsers_create_id(int users_create_id) {
		this.users_create_id = users_create_id;
	}

}
