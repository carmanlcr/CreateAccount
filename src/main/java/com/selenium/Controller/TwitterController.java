package com.selenium.Controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;


import com.selenium.Model.Db_Admin_Users_Create;
import com.selenium.Model.Db_admin_users_create_detail;
import com.selenium.Model.User;

public class TwitterController {
	
	private static DriverController driver;
	private Db_Admin_Users_Create users_create;
	private static String urlPageTwitter;
	private static Db_admin_users_create_detail create_detail;
	private String biografia;
	private RobotController robot;
	public TwitterController(DriverController driver, Db_Admin_Users_Create users_create, String urlPageTwitter, RobotController robot, Db_admin_users_create_detail create_detail, String biografia) {
		TwitterController.driver = driver;
		this.users_create = users_create;
		TwitterController.urlPageTwitter = urlPageTwitter;
		TwitterController.create_detail = create_detail;
		this.robot = robot;
		this.biografia = biografia;
	}
	
	
	public Db_Admin_Users_Create init() throws InterruptedException {
		System.out.println("INGRESAR EN TWITTER");
		driver.goPage(urlPageTwitter);
		Thread.sleep(1234);
		
		Db_Admin_Users_Create users = this.users_create;
		
		while(driver.searchElement(2, "name") == 0);
		Thread.sleep(334);
		System.out.println("Escribir el nombre");
		robot.inputWrite(users.getFull_name());
		Thread.sleep(434);
		
		robot.pressTab();
		System.out.println("Escribir el telefono");
		robot.inputWrite("+57"+users.getPhones());
		Thread.sleep(434);
		robot.pressTab();
		Thread.sleep(434);
		robot.pressTab();
		Thread.sleep(2034);
		
		
		String[] date_of_birth = users.getDate_of_birth().split("-");
		int year = Integer.parseInt(date_of_birth[0]);
		int month = Integer.parseInt(date_of_birth[1]);
		int day = Integer.parseInt(date_of_birth[2]);
		
		
		System.out.println("Setear el mes de nacimiento");
		setDropDown(month);
		robot.pressTab();
		Thread.sleep(734);
		
		System.out.println("Setear el dia de nacimiento");
		setDropDown(day);	
		robot.pressTab();
		Thread.sleep(734);
		
		System.out.println("Setear el año de nacimiento");
		for(int i = 0; i<2021-year; i++) {
			robot.pressDown();
			Thread.sleep(45);
		}
		
		robot.pressTab();
		Thread.sleep(734);
		System.out.println("Presionar siguiente");
		robot.enter();
		
		Thread.sleep(4562);
		robot.pressTab();
		Thread.sleep(734);
		System.out.println("Presionar siguiente");
		robot.enter();
		
		Thread.sleep(4562);
		System.out.println("Pulsar Registrarse");
		if(driver.searchElement(1, "//*[text()[contains(.,'Registrarse')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Registrarse')]]", "Siguiente xPath");
		}
		
		Thread.sleep(1234);
		System.out.println("Pulsar aceptar");
		if(driver.searchElement(1, "//*[text()[contains(.,'Aceptar')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Aceptar')]]", "Aceptar xPath");
		}
		
		Thread.sleep(1237);
		String verification_code = ""; 
		while(driver.searchElement(2, "verfication_code") != 0) {
			verification_code = JOptionPane.showInputDialog("INGRESE CODIGO DE VERIFICACIÓN Y PULSE ACEPTAR");
			if (verification_code == null || verification_code.isEmpty()) {

			} else {
				if (driver.searchElement(2, "verfication_code") != 0) {
					driver.inputWrite(2, "verfication_code", verification_code, 115);
					Thread.sleep(2034);
					robot.pressTab();
					Thread.sleep(124);
					robot.pressTab();
					Thread.sleep(124);
					robot.pressTab();
					Thread.sleep(124);
					robot.enter();
					Thread.sleep(6034);
				}
			}
		}
		
		System.out.println("Se verifico el codigo");
		Thread.sleep(2034);
		System.out.println("Ingresar contraseña");
		if(driver.searchElement(2, "password") != 0) {
			driver.inputWrite(2, "password", users.getPassword(), 145);
		}
		Thread.sleep(3034);
		robot.pressTab();
		Thread.sleep(865);
		robot.pressTab();
		Thread.sleep(865);
		robot.enter();
		
		Thread.sleep(3034);
		
		System.out.println("Agregar foto");
		if(driver.searchElement(1, "//input[@type='file']") != 0) {
			driver.inputWriteFile(1, "//input[@type='file']", "C:\\imagesSftp\\"+create_detail.getfPerfil());
		}
		
		Thread.sleep(1034);
		System.out.println("Pulsar siguiente");
		if(driver.searchElement(1, "//*[text()[contains(.,'Siguiente')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Siguiente')]]", "Siguiente xPath");
		}
		
		Thread.sleep(2034);
		if(driver.searchElement(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[3]/label/div[2]/div/textarea") != 0) {
			driver.inputWrite(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[3]/label/div[2]/div/textarea", biografia, 112);
		}else if(driver.searchElement(2, "text") != 0) {
			driver.inputWrite(2, "text", biografia, 114);
		}
		
		Thread.sleep(1034);
		System.out.println("Pulsar siguiente");
		if(driver.searchElement(1, "//*[text()[contains(.,'Siguiente')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Siguiente')]]", "Siguiente xPath");
		}
		
		Thread.sleep(2034);
		
		selectedInteresting();
		
		
		System.out.println("Pulsar siguiente");
		if(driver.searchElement(1, "//*[text()[contains(.,'Siguiente')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Siguiente')]]", "Siguiente xPath");
		}else if(driver.searchElement(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div") != 0) {
			driver.clickButton(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div", "Siguiente");
		}
		
		
		Thread.sleep(1034);
		if(driver.searchElement(1, "//*[text()[contains(.,'Aply')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Aply')]]", "Descartar por ahora xPath");
		}
		
		
		if(driver.searchElement(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div/div[3]/div[1]") != 0) {
			driver.clickButton(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div/div/div[3]/div[1]", "Activar notificaciones");
		}
		
		
		User us = new User();
		us.setUsername(users.getUsername());
		us.setEmail(users.getEmail());
		us.setFull_name(users.getFull_name());
		us.setPassword(users.getPassword());
		us.setPhone(users.getPhones());
		us.setDate_of_birth(users.getDate_of_birth());
		
		try {
			us.insertTwitter(users.getVpn());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		users.setCreate_tw(true);
		try {
			users.update();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	private void setDropDown(int num) throws InterruptedException {
		for(int i = 0; i<num; i++) {
			robot.pressDown();
			Thread.sleep(85);
		}
	}
	
	private void selectedInteresting() throws InterruptedException {
		int quantitySports = driver.getQuantityElements(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[4]/div[1]/div[2]") -1;
														  
		for(int i = 1; i<=quantitySports; i++) {
			driver.clickButton(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[4]/div[1]/div[2]/div["+i+"]", "Sport "+i+" ");
			Thread.sleep(457);
		}
		//html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[4]/div[2]/div[2]
		//html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[4]/div[2]/div[2]/div[2]
		
		int quantityNews = driver.getQuantityElements(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[4]/div[2]/div[2]") -1;
		
		for(int j = 1; j<=quantityNews; j++) {
			driver.clickButton(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[4]/div[2]/div[2]/div["+j+"]", "News "+j);
			Thread.sleep(745);
		}
		
		Thread.sleep(1567);
		if(driver.searchElement(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div") != 0) {
			driver.clickButton(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div", "Siguiente xPath");
		}else if(driver.searchElement(1, "//*[text()[contains(.,'Next')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Next')]]", "Next xPath 2");
		}else if(driver.searchElement(1, "//*[text()[contains(.,'Siguiente')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Siguiente')]]", "Siguiente xPath 2");
		}
		
		Thread.sleep(3567);
		
		//html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/div/label[1]
		//html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/div/label[2]
		
		int quantityFollowSugeriders = driver.getQuantityElements(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/div") - 2;
		
		for(int k = 1; k<=quantityFollowSugeriders; k++) {
			driver.clickButton(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[2]/div/div/div[2]/div/div/div/div/div/label["+k+"]/div/div/div[2]/div[1]/div[2]/div", "Follow");
			Thread.sleep(658);
		}
									
		if(driver.searchElement(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div") != 0) {
			driver.clickButton(1, "/html/body/div/div/div/div[1]/div/div/div/div/div[2]/div[2]/div/div/div[2]/div[1]/div/div/div/div[3]/div", "Siguiente xPath");
		}else if(driver.searchElement(1, "//*[text()[contains(.,'Next')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Next')]]", "Next xPath 2");
		}else if(driver.searchElement(1, "//*[text()[contains(.,'Siguiente')]]") != 0) {
			driver.clickButton(1, "//*[text()[contains(.,'Siguiente')]]", "Siguiente xPath 2");
		}
		
		
	}
}
