	package com.selenium.Controller;

import java.io.IOException;

/**
 * 
 * Se movio al proyecto Configurations
 * 
 * @deprecated
 * @author Morales
 *
 */
public class VpnController {
	private RobotController robot;
	
	public VpnController(RobotController robot) {
		this.robot = robot;
	}
	
	void iniciarVpn(String vpn, boolean bandera) throws InterruptedException {
		//Abrir la aplicación OpenVPN
		try {
			openVPN();
			Thread.sleep(2000);
			//Maximizar ventana
			robot.maximizar();
			
			Thread.sleep(3000);
			//Seleccionar el buscador de la vpn
			if(bandera) {
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
			}else {
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
				robot.pressTab();
				Thread.sleep(256);
			}
			//copiar la vpn en el portapapeles
			robot.copy(vpn);
			Thread.sleep(2000);
			//Pegar el nombre de la VPN
			robot.paste();
			Thread.sleep(2000);
			//Presionar enter para que busque la vpn
			robot.enter();
			//Presionar enter para que busque la vpn
			Thread.sleep(35000);
			//Borrar la vpn escrita
			robot.selectAllAndDelete();
			//Cerrar la pestaña
			robot.close();
		} catch (IOException e) {
			System.out.println("Error al conseguir archivo");
		}
		
	}
	
	private static void openVPN() throws IOException {
		
		String path = "C:\\Program Files\\NordVPN\\NordVPN.exe";
		Runtime objrun = Runtime.getRuntime();
		try {
			objrun.exec(path);
		} catch(IOException e) {				
			System.err.println("No consigue el archivo a ejecutar");
		}
		objrun.freeMemory();
	}
	
	protected void desconectVpn() throws IOException, InterruptedException {
		robot = new RobotController();
		//Abrir la aplicacion OpenVPN
		openVPN();
		Thread.sleep(2015);
		//Maximizar ventana
		robot.maximizar();
		//Seleccionar la opcion de desconectar
		
		robot.pulsarShiftTabulador();
		Thread.sleep(290);
		robot.pulsarShiftTabulador();
		Thread.sleep(290);
		robot.pulsarShiftTabulador();
		Thread.sleep(290);
		robot.pulsarShiftTabulador();
		Thread.sleep(290);
		robot.pulsarShiftTabulador();
		Thread.sleep(290);
		robot.pulsarShiftTabulador();
		Thread.sleep(290);
		robot.pulsarShiftTabulador();
		Thread.sleep(290);
		robot.pulsarShiftTabulador();
		Thread.sleep(290);
		robot.pulsarShiftTabulador();
		Thread.sleep(290);
		robot.pulsarShiftTabulador();
		Thread.sleep(500);
		robot.enter();
		Thread.sleep(500);
		//Cerrar el vpn
		robot.close();
		
		Thread.sleep(1054);
	}
	
	
	
}
