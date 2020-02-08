package com.selenium.Controller;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import com.selenium.Model.Db_Admin_Users_Create;
import com.selenium.Model.Db_admin_users_create_detail;
import com.selenium.Model.User;

public class InstagramController {

	private static DriverController drive;
	private Db_Admin_Users_Create users_create;
	private static String urlPageInstagram;
	private String username;
	private Db_admin_users_create_detail create_detail;
	private RobotController robot;
	private String biografia;
	public InstagramController(DriverController drive, Db_Admin_Users_Create users_create, String urlPageInstagram, RobotController robot, 
			String username, Db_admin_users_create_detail create_detail, String biografia) {
		InstagramController.drive = drive;
		this.users_create = users_create;
		InstagramController.urlPageInstagram = urlPageInstagram;
		this.robot = robot;
		this.username = username;
		this.create_detail = create_detail;
		this.biografia = biografia;
	}
	
	public void init() throws InterruptedException {
		System.out.println("INGRESAR EN INSTAGRAM");
		drive.goPage(urlPageInstagram);
		Thread.sleep(1245);
		
		Db_Admin_Users_Create user = this.users_create;
		
		while(drive.searchElement(2, "emailOrPhone") == 0);
		
		robot.pressTab();
		Thread.sleep(475);
		robot.pressTab();
		Thread.sleep(475);
		
		System.out.println("Escribir el numero de telefono");
		robot.inputWrite("+57"+user.getPhones());
		robot.pressTab();
		Thread.sleep(475);
		
		System.out.println("Escribir el nombre completo");
		robot.inputWrite(user.getFull_name());
		Thread.sleep(556);
		robot.pressTab();
		
		System.out.println("Escribir el usuario");
		robot.inputWrite(username);
		Thread.sleep(556);
		robot.pressTab();
		Thread.sleep(556);
		robot.pressTab();
		
		Thread.sleep(756);
		System.out.println("Escribir la contraseña");
		robot.inputWrite(user.getPassword());
		Thread.sleep(556);
		robot.pressTab();
		Thread.sleep(556);
		robot.pressTab();
		
		Thread.sleep(478);
		System.out.println("Pulsar registrar");
		robot.enter();
		
		Thread.sleep(5478);
		String verification_code = "";
		while(drive.searchElement(1, "//*[@aria-describedby='confirmationCodeDescription']") != 0) {
			verification_code = JOptionPane.showInputDialog("INGRESE CODIGO DE VERIFICACIÓN Y PULSE ACEPTAR");
			if (verification_code == null || verification_code.isEmpty()) {

			} else {
				if (drive.searchElement(1, "//*[@aria-describedby='confirmationCodeDescription']") != 0) {
					drive.inputWrite(1, "//*[@aria-describedby='confirmationCodeDescription']", verification_code, 115);
				}
				Thread.sleep(2034);
				System.out.println("Pulsar confirmar");
				if(drive.searchElement(1, "//*[text()[contains(.,'Confirmar')]]") != 0) {
					drive.clickButton(1, "//*[text()[contains(.,'Confirmar')]]", "Confirmar xPath");
				}
				
				Thread.sleep(7034);
			}
		}
		
		User us = new User();
		us.setUsername("+57"+user.getPhones());
		us.setEmail(user.getEmail());
		us.setFull_name(user.getFull_name());
		us.setPassword(user.getPassword());
		us.setPhone(user.getPhones());
		us.setDate_of_birth(user.getDate_of_birth());
		try {
			us.insertInstagram(user.getVpn());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		user.setCreate_ig(true);
		try {
			user.update();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		robot.pressTab();
		Thread.sleep(145);
		robot.enter();
		
		
		Thread.sleep(3214);

		followSugerences();
		
		if(drive.searchElement(1, "//*[text()[contains(.,'Empezar')]]") != 0) {
			drive.clickButton(1, "//*[text()[contains(.,'Empezar')]]", "Empezar xPath");
		}
		
		Thread.sleep(4756);
		
		if(drive.searchElement(1, "/html/body/div[1]/section/nav/div[2]/div/div/div[3]/div/div[3]/a") != 0) {
			drive.clickButton(1, "/html/body/div[1]/section/nav/div[2]/div/div/div[3]/div/div[3]/a", "Perfil xPath");
			Thread.sleep(2230);
			
			
			if(drive.searchElement(1, "//input[@type='file']") != 0) {
				drive.inputWriteFile(1, "//input[@type='file']", "C:\\imagesSftp\\"+create_detail.getfPerfil());
				
				Thread.sleep(1235);
				
				if(drive.searchElement(1, "//*[text()[contains(.,'Publicar')]]") != 0) {
					drive.clickButton(1, "//*[text()[contains(.,'Publicar')]]", "Publicar foto");
					Thread.sleep(1240);
					
					drive.goPage("https://www.instagram.com/"+username);
				}
			}
			
			drive.goPage("https://www.instagram.com/"+username);
			Thread.sleep(1235);
			
			drive.clickButton(1, "/html/body/div[1]/section/main/div/header/section/div[1]/a/button", "Editar Perfil");
						
			Thread.sleep(1230);
			
			
			
			for(int i = 1; i<=14; i++) {
				robot.pressTab();
				Thread.sleep(85);
			}
			
			robot.inputWrite(biografia);
			
			for(int i = 1; i<=7; i++) {
				robot.pressTab();
				Thread.sleep(15);
			}
			
			robot.enter();
			
			Thread.sleep(5000);
		}
	}
	
	private void followSugerences() throws InterruptedException {
		//html/body/div[1]/section/main/section/div/div/div/div/div/div[1]
		if(drive.searchElement(1, "/html/body/div[1]/section/main/section/div/div/div/div[3]/div/div[1]") != 0) {
			int quantitySugerencias = drive.getQuantityElements(1, "/html/body/div[1]/section/main/section/div/div/div/div[3]/div/div") -5 ;
			
			for(int i = 1;i<=quantitySugerencias;i++) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
				if(randomNum == 2) {
					drive.clickButton(1, "/html/body/div[1]/section/main/section/div/div/div/div/div/div["+i+"]/div[3]/button", "Follow "+i);
					Thread.sleep(147);
				}
			}
		}else if(drive.searchElement(1, "/html/body/div[1]/section/main/section/div/div/div/div/div/div[1]") != 0) {
			int quantitySugerencias = drive.getQuantityElements(1, "/html/body/div[1]/section/main/section/div/div/div/div/div/div") -5 ;
			for(int i = 1;i<=quantitySugerencias;i++) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
				if(randomNum == 2) {
										 //html/body/div[1]/section/main/section/div/div/div/div/div/div["+i+"]/div[3]/button
					drive.clickButton(1, "/html/body/div[1]/section/main/section/div/div/div/div/div/div["+i+"]/div[3]/button", "Follow "+i);
					Thread.sleep(147);
				}
			}
		}
		
	}
		
}
