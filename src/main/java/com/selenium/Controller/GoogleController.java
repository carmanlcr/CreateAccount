package com.selenium.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.openqa.selenium.ElementNotInteractableException;
import org.sikuli.script.Screen;

import com.selenium.Model.Db_Admin_Users_Create;
import com.selenium.Model.User;

public class GoogleController {

	private static DriverController driver;
	private static Db_Admin_Users_Create users_Create;
	private static String urlPageGoogle;
	private static String first_name;
	private static String last_name;
	private static String username;
	private static String password;
	private static String phone;
	private static RobotController robot;
	
	public GoogleController(DriverController driver, Db_Admin_Users_Create users_Create, String urlPageGoogle,String username,
			RobotController robot,String path_images) {
		GoogleController.driver = driver;
		GoogleController.users_Create = users_Create;
		GoogleController.urlPageGoogle = urlPageGoogle;
		GoogleController.username = username;
		GoogleController.robot = robot;
	}

	public Db_Admin_Users_Create init() throws InterruptedException, IOException {
		driver.goPage(urlPageGoogle);

		Db_Admin_Users_Create userC = GoogleController.users_Create;
		String[] full_name = userC.getFull_name().split(" ");
		first_name = full_name[0];
		last_name = full_name[1];
		password = userC.getPassword();
		phone = userC.getPhones();
		String userna = GoogleController.username;
		while(driver.searchElement(2, "firstName") == 0);
		// Escribir data del usuario
		writeData();

		Thread.sleep(589);
		System.out.println("Pulsar siguiente");
		robot.pressTab();
		Thread.sleep(123);
		robot.pressTab();
		robot.enter();
		
		while(driver.searchElement(3, "phoneNumberId") == 0);
		Thread.sleep(1800);
		robot.inputWrite("+57"+phone);
		Thread.sleep(854);
		robot.enter();
		
		Thread.sleep(3500);
		if(driver.searchElement(1, "//*[text()[contains(.,'Este número de teléfono se ha utilizado demasiadas veces.')]]") != 0) {
			
			userC.setEmail(userna+ "@gmail.com");
			userC.setUsername(userna);
			try {
				userC.update();
			} catch (SQLException e1) {
				System.out.println("No se pudo actualizar el gmail en la bd" + e1);
				e1.printStackTrace();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
			User user = new User();
			user.setEmail(null);
			user.setPassword(null);
			user.setPhone(phone);
			try {
				user.insertReviews(userC.getVpn());
				System.out.println("Se registro en google y en reviews");
			} catch (SQLException e) {
				System.out.println("Error al registrar en reviews");
				e.printStackTrace();
			}
			return userC;
		}else {
			while(driver.searchElement(2, "code") == 0);
			
			while(driver.searchElement(2, "code") != 0) {
				String verification_code = JOptionPane.showInputDialog(null, "Ingrese el codigo de verificacion y pulse aceptar");
				
				if(verification_code == null || verification_code.isEmpty()) {
					
				}else {
					driver.inputWrite(2, "code", verification_code, 114);
					Thread.sleep(645);
					robot.enter();
					
					Thread.sleep(7500);
				}
			}
			
			
			robot.pressTab();
			Thread.sleep(452);
			robot.pressTab();
			Thread.sleep(452);
			
			String[] date_of_birth = userC.getDate_of_birth().split("-");
			String year = date_of_birth[0];
			String month = String.valueOf(Integer.parseInt(date_of_birth[1]));
			String day = date_of_birth[2];

			System.out.println("Escribir el día de nacimiento");
			robot.inputWrite(day);
			Thread.sleep(486);
			robot.pressTab();
			Thread.sleep(486);
			
			System.out.println("Setear el mes");
			for(int i = 0; i<Integer.parseInt(month); i++) {
				robot.pressDown();
				Thread.sleep(86);
			}
			robot.pressTab();
			Thread.sleep(478);
			System.out.println("Escribir el año");
			robot.inputWrite(year);
			Thread.sleep(478);
			robot.pressTab();
			Thread.sleep(478);
			
			System.out.println("Setear el sexo");
			int gender = users_Create.isGender() == true ? 1 : 2;
			for(int j = 0; j< gender; j++) {
				robot.pressDown();
				Thread.sleep(86);
			}
			
			robot.pressTab();
			Thread.sleep(478);
			robot.pressTab();
			Thread.sleep(478);
			robot.enter();
			
			while(driver.searchElement(3, "phoneUsageNext") == 0);
			//Dia1077189Gri
			Thread.sleep(1000);
			System.out.println("Pulsar siguiente");
			driver.clickButton(3, "phoneUsageNext", "Si, acepto id");
			
			Thread.sleep(2000);
			
			for(int i = 0;i < 6 ;i++) {
				try {
					driver.clickButton(1, "/html/body/div[1]/div/div[2]/div[1]/div[2]/form/div[2]/div/div/div/div[1]/div/div", "Bajar");
				}catch(ElementNotInteractableException e) {
					
				}
				
				Thread.sleep(621);
				
			}
			
			Thread.sleep(1000);
			
			if(driver.searchElement(3, "termsofserviceNext") != 0) {
				driver.clickButton(3, "termsofserviceNext", "Acepto");
			}else if (driver.searchElement(1, "/html/body/div[1]/div/div[2]/div[1]/div[2]/form/div[2]/div/div/div/div[2]/div/div[1]/div") != 0) {
				driver.clickButton(1, "/html/body/div[1]/div/div[2]/div[1]/div[2]/form/div[2]/div/div/div/div[2]/div/div[1]/div", "Acepto xpath");
			}
			
			Thread.sleep(1000);
			
			robot.pressTab();
			Thread.sleep(450);
			robot.enter();
			
			Thread.sleep(3000);
			
			userC.setEmail(userna+ "@gmail.com");
			userC.setUsername(userna);
			try {
				userC.update();
			} catch (SQLException e1) {
				System.out.println("No se pudo actualizar el gmail en la bd" + e1);
				e1.printStackTrace();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
			User user = new User();
			user.setEmail(userna+"@gmail.com");
			user.setPassword(password);
			user.setPhone(phone);
			try {
				user.insertReviews(userC.getVpn());
				System.out.println("Se registro en google y en reviews");
			} catch (SQLException e) {
				System.out.println("Error al registrar en reviews");
				e.printStackTrace();
			}
			return userC;
		}
		
	}

	/**
	 * 
	 * @throws InterruptedException
	 * @throws FindFailed 
	 */
	private static void writeData() throws InterruptedException {
		System.out.println("Escribir el primer nombre");
		robot.inputWrite(first_name);
		Thread.sleep(489);

		System.out.println("Escribir el apellido");
		robot.pressTab();
		Thread.sleep(489);
		robot.inputWrite(last_name);
		Thread.sleep(489);

		System.out.println("Escribir el usuario");
		robot.pressTab();
		Thread.sleep(489);
		robot.inputWrite(username);
		
		robot.pressTab();
		Thread.sleep(489);
		robot.pressTab();
		Thread.sleep(489);
		Thread.sleep(489);
		System.out.println("Escribir la contraseña");
		robot.inputWrite(password);
		
		Thread.sleep(489);
		System.out.println("Escribir la confirmacion contraseña");
		robot.pressTab();
		Thread.sleep(489);
		robot.inputWrite(password);
		

	}
}
