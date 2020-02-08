package com.selenium.Controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;


import com.selenium.Model.Db_Admin_Users_Create;
import com.selenium.Model.Db_admin_users_create_detail;
import com.selenium.Model.User;

public class FacebookController {
	
	private static DriverController drive;
	private Db_Admin_Users_Create users_create;
	private static String urlPageFacebook;
	private RobotController robot;
	private Db_admin_users_create_detail create_detail;
	private final String PATH_IMAGE = "C:\\imagesSftp\\";
	private String biografia;
	public FacebookController(DriverController drive, Db_Admin_Users_Create users_create, String urlPage, RobotController robot, Db_admin_users_create_detail create_detail, String biografia) {
		FacebookController.drive = drive;
		this.users_create = users_create;
		FacebookController.urlPageFacebook = urlPage;
		this.robot = robot;
		this.create_detail = create_detail;
		this.biografia = biografia;
	}
	
	public void init() throws InterruptedException {
		System.out.println("ENTRAR A FACEBOOK");
		drive.goPage(urlPageFacebook);
		Thread.sleep(1245);
		while(drive.searchElement(2, "firstname") == 0);
		

		Db_Admin_Users_Create users = this.users_create;
		String[] full_name = users.getFull_name().split(" ");
		String name = full_name[0];
		String last_name = full_name[1];
 		String phone = "+57"+users.getPhones();
 		String[] date_of_birth = users.getDate_of_birth().split("-");
		String year = date_of_birth[0];
		String month = String.valueOf(Integer.parseInt(date_of_birth[1]));
		String day = date_of_birth[2];
 		String password = users.getPassword();
		
 		System.out.println("Escribir Nombre");
		if(drive.searchElement(2, "firstname") != 0) {
			drive.inputWrite(2, "firstname", name, 112);
		}else if(drive.searchElement(1,"/html/body/div[1]/div[3]/div[1]/div/div/div/div[2]/div[3]/div/div/div[1]/form/div[1]/div[1]/div[1]/div[1]/div/div[1]/input") != 0) {
			drive.inputWrite(1, "/html/body/div[1]/div[3]/div[1]/div/div/div/div[2]/div[3]/div/div/div[1]/form/div[1]/div[1]/div[1]/div[1]/div/div[1]/input", name, 112);
		}
		
		Thread.sleep(882);
		robot.pressTab();
		Thread.sleep(402);
		System.out.println("Escribir apellido");
		robot.inputWrite(last_name);
		
		Thread.sleep(982);
		robot.pressTab();
		System.out.println("Escribir phone");
		robot.inputWrite(phone);
		
		Thread.sleep(1582);
		robot.pressTab();
		System.out.println("Escribir password");
		robot.inputWrite(password);
		
		Thread.sleep(456);
		robot.pressTab();
		System.out.println("Setear el día de nacimiento");
		Thread.sleep(472);
		for(int i = 0;i<31-Integer.parseInt(day);i++) {
			robot.pressDown();
			Thread.sleep(72);
		}
		
		Thread.sleep(456);
		robot.pressTab();
		System.out.println("Setear el mes de nacimiento");
		Thread.sleep(472);
		for(int i = 0;i<Integer.parseInt(month);i++) {
			robot.pressDown();
			Thread.sleep(72);
		}
		
		Thread.sleep(456);
		robot.pressTab();
		System.out.println("Setear el año de nacimiento");
		Thread.sleep(472);
		for(int i = 0;i<1995-Integer.parseInt(year);i++) {
			robot.pressDown();
			Thread.sleep(72);
		}
		
		robot.pressTab();
		Thread.sleep(456);
		
		if(users.isGender()) {
			robot.pressTab();
			Thread.sleep(456);
			robot.pressSpace();
		}else {
			robot.pressTab();
			Thread.sleep(456);
			robot.pressTab();
			Thread.sleep(456);
			robot.pressSpace();
		}
		Thread.sleep(1245);
		robot.enter();
		
		Thread.sleep(10543);
		robot.pressEsc();
		robot.pressEsc();
		
		validatorSecurity();

		Thread.sleep(2543);
		drive.goPage("https://mbasic.facebook.com/");
		Thread.sleep(2543);
		if(drive.searchElement(1, "//*[text()[contains(.,'Completa los siguientes pasos para iniciar sesión')]]") != 0) {
			if(drive.searchElement(3, "checkpointSubmitButton") != 0) {
				drive.clickButton(3, "checkpointSubmitButton", "Continuar id");
			}else if(drive.searchElement(1, "//*[text()[contains(.,'Continuar')]]") != 0) {
				drive.clickButton(1, "//*[text()[contains(.,'Continuar')]]", "Continuar xPath");
			}else if(drive.searchElement(2, "submit[Secure Account]") != 0) {
				drive.clickButton(2, "submit[Secure Account]", "Continuar name");
			}else if(drive.searchElement(1, "//input[@type='submit']") != 0) {
				drive.clickButton(1, "//input[@type='submit']", "Continuar submit");
			}
			Thread.sleep(2543);
			User user = new User();
			user.setUsername(phone);
			user.setEmail(users.getEmail());
			user.setFull_name(users.getFull_name());
			user.setDate_of_birth(users.getDate_of_birth());
			user.setPassword(users.getPassword());
			user.setPhone(users.getPhones());
			try {
				user.insertFacebook(users.getVpn());
				System.out.println("Se registro con exito");
				users.setCreate_fb(true);
				users.update();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			drive.goPage("https://mbasic.facebook.com/");
			String verification_code = "";
			while(drive.searchElement(3, "captcha_persist_data") != 0 || drive.searchElement(2, "captcha_persist_data") != 0) {
				verification_code = JOptionPane.showInputDialog("INGRESE CODIGO DE VERIFICACIÓN Y PULSE ACEPTAR");
				if (verification_code == null || verification_code.isEmpty()) {

				} else {
					if (drive.searchElement(2, "captcha_response") != 0) {
						drive.inputWrite(2, "captcha_response", verification_code, 115);
					} else if (drive.searchElement(3, "captcha_response") != 0) {
						drive.inputWrite(3, "captcha_response", verification_code, 115);
					}

					if (drive.searchElement(2, "submit[Continue]") != 0) {
						drive.clickButton(2, "submit[Continue]", "Continue name");
					} else if (drive.searchElement(3, "checkpointSubmitButton-actual-button") != 0) {
						drive.clickButton(3,"checkpointSubmitButton-actual-button","Continue id");
					}
					 Thread.sleep(6000);
			
				}
			}
			
			if(drive.searchElement(2, "photo-input") != 0) {
				drive.inputWriteFile(2, "photo-input", PATH_IMAGE+create_detail.getfPerfil());
				Thread.sleep(1452);
				
			}
			
			if(drive.searchElement(3, "checkpointSubmitButton-actual-button") != 0) {
				drive.clickButton(3, "checkpointSubmitButton-actual-button", "Continuar id");
			}else if(drive.searchElement(2, "submit[Continue]") != 0) {
				drive.clickButton(2, "submit[Continue]", "Continuar name");
			}
			
			Thread.sleep(2540);
			
			if(drive.searchElement(1, "//*[text()[contains(.,'No puedes usar Facebook en este momento')]]") != 0) {
				drive.clickButton(2, "submit[OK]", "Aceptar");
			}
			
		}else {
			if(drive.searchElement(1, "//*[text()[contains(.,'Siguiente')]]") != 0) {
				drive.clickButton(1, "//*[text()[contains(.,'Siguiente')]]", "Siguiente xPath");
			}
			
			Thread.sleep(1000);
			
			if(drive.searchElement(1, "//*[text()[contains(.,'Siguiente')]]") != 0) {
				drive.clickButton(1, "//*[text()[contains(.,'Siguiente')]]", "Siguiente xPath");
			}

			Thread.sleep(1000);
			
			if(drive.searchElement(1, "//*[text()[contains(.,'Siguiente')]]") != 0) {
				drive.clickButton(1, "//*[text()[contains(.,'Siguiente')]]", "Siguiente xPath");
			}
			
			User user = new User();
			user.setUsername(phone);
			user.setEmail(users.getEmail());
			user.setFull_name(users.getFull_name());
			user.setDate_of_birth(users.getDate_of_birth());
			user.setPassword(users.getPassword());
			user.setPhone(users.getPhones());
			try {
				user.insertFacebook(users.getVpn());
				System.out.println("Se registro con exito");
				users.setCreate_fb(true);
				users.update();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			Thread.sleep(3000);
			if(drive.searchElement(1, "//*[text()[contains(.,'Editar perfil')]]") != 0) {
				drive.clickButton(1, "//*[text()[contains(.,'Editar perfil')]]", "Editar Perfil xPath");
			}else if(drive.searchElement(1, "/html/body/div/div/div[1]/div/div/a[2]") != 0) {
				drive.clickButton(1, "/html/body/div/div/div[1]/div/div/a[2]","Editar Perfil xPath");
			}
			
			if(drive.searchElement(1, "/html/body/div/div/div[2]/div/div[1]/div[1]/div[2]/div/div[1]/div/div/a") != 0) {
				drive.clickButton(1, "/html/body/div/div/div[2]/div/div[1]/div[1]/div[2]/div/div[1]/div/div/a", "Foto de Portada xPath");
				
				Thread.sleep(1000);
				System.out.println("Subir foto de perfil");
				drive.inputWriteFile(2, "pic", "C:\\imagesSftp\\"+create_detail.getfPerfil());
				if(drive.searchElement(1, "//input[@type='submit']") != 0) {
					drive.clickButton(1, "//input[@type='submit']", "Guardar");
				}else if(drive.searchElement(1, "//*[text()[contains(.,'Guardar')]]") != 0) {
					drive.clickButton(1,"//*[text()[contains(.,'Guardar')]]", "Guardar xPath");
				}
			}
			
			Thread.sleep(3000);
			
			if(drive.searchElement(3, "profile_cover_photo") != 0) {
				drive.clickButton(3, "profile_cover_photo", "Subir foto de Portada id");
			}else if(drive.searchElement(1, "/html/body/div/div/div[2]/div/div[1]/div[1]/div[1]/div/div/div/div/a") != 0) {
				drive.clickButton(1, "/html/body/div/div/div[2]/div/div[1]/div[1]/div[1]/div/div/div/div/a", "Subir foto xPath");
			}
			
			Thread.sleep(1000);
			
			if(drive.searchElement(1, "/html/body/div/div/div[2]/div/table/tbody/tr/td/div/div[4]/form/div[2]/div/div[1]/a") != 0) {
				drive.clickButton(1, "/html/body/div/div/div[2]/div/table/tbody/tr/td/div/div[4]/form/div[2]/div/div[1]/a", "Añadir foto XPath");
			}else if(drive.searchElement(1, "//*[text()[contains(.,'Añadir foto')]]") != 0) {
				drive.clickButton(1, "//*[text()[contains(.,'Añadir foto')]]", "Añadir foto xPath");
			}
			
			Thread.sleep(1000);
			System.out.println("Subir foto de portada");
			if(drive.searchElement(2, "file1") != 0) {
				drive.inputWriteFile(2, "file1", PATH_IMAGE+create_detail.getfPortada());
			}else if(drive.searchElement(1, "/html/body/div/div/div[2]/div/table/tbody/tr/td/div/form/div[1]/input") != 0) {
				drive.inputWriteFile(1,"/html/body/div/div/div[2]/div/table/tbody/tr/td/div/form/div[1]/input", PATH_IMAGE+create_detail.getfPortada());
			}
			
			Thread.sleep(1000);
			
			if(drive.searchElement(1, "/html/body/div/div/div[2]/div/table/tbody/tr/td/div/form/div[2]/input") != 0) {
				drive.clickButton(1, "/html/body/div/div/div[2]/div/table/tbody/tr/td/div/form/div[2]/input", "Subir xPath");
			}else if(drive.searchElement(1, "//input[@type='submit']") != 0) {
				drive.clickButton(1, "//input[@type='submit']", "Subir xPath");
			}
			
			if(drive.searchElement(1, "/html/body/div/div/div[2]/div/div[1]/div[1]/div[2]/a") != 0) {
				drive.clickButton(1,"/html/body/div/div/div[2]/div/div[1]/div[1]/div[2]/a", "Describir quien eres");
				
				Thread.sleep(1450);
				
				drive.inputWrite(2, "bio", biografia, 114);
				
				if(drive.searchElement(1, "/html/body/div/div/div[2]/div/table/tbody/tr/td/form/table/tbody/tr/td[2]/input") != 0) {
					drive.clickButton(1, "/html/body/div/div/div[2]/div/table/tbody/tr/td/form/table/tbody/tr/td[2]/input", "Guardar");
				}else if(drive.searchElement(1, "//*[text()[contains(.,'Guardar')]]") != 0) {
					drive.clickButton(1, "//*[text()[contains(.,'Guardar')]]", "Guardar xPath");
				}
			}
		}
		
	}
	
	private void validatorSecurity() throws InterruptedException {
		Thread.sleep(1236);
		String verification_code = "";
		System.out.println("Introducir codigo de verificación");
		while(drive.searchElement(3, "code_in_cliff") != 0) {
			verification_code = JOptionPane.showInputDialog("INGRESE CATPCHA Y PULSE ACEPTAR");
			if (verification_code == null || verification_code.isEmpty()) {

			} else {
				if (drive.searchElement(3, "code_in_cliff") != 0) {
					drive.inputWrite(3, "code_in_cliff", verification_code, 115);
				}else if(drive.searchElement(2, "code") != 0) {
					drive.inputWrite(2, "code", verification_code, 114);
				}
				Thread.sleep(1457);
				if(drive.searchElement(1, "/html/body/div[1]/div[4]/div[1]/div/div/div[1]/div[2]/form/div[2]/div/button") != 0) {
					drive.clickButton(1, "/html/body/div[1]/div[4]/div[1]/div/div/div[1]/div[2]/form/div[2]/div/button", "Continuar xPath");
				}else if (drive.searchElement(2, "confirm") != 0) {
					drive.clickButton(2, "confirm", "Continuar name");
				}else if(drive.searchElement(3, "u_0_k") != 0) {
					drive.clickButton(3, "u_0_k","Continuar id");
				}
				 Thread.sleep(15000);
				 
				 if(drive.searchElement(1, "/html/body/div[4]/div[2]/div/div/div/div[3]/div/a") != 0) {
					 drive.clickButton(1, "/html/body/div[4]/div[2]/div/div/div/div[3]/div/a", "Aceptar xPath");
				 }

			}
		}
	}
}
