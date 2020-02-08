package com.selenium.Controller;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class RobotController {
	
	private static Robot robot;
	private static int mask = InputEvent.BUTTON1_DOWN_MASK;
	private String vpn = "";
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	/**
	 * Instancia del robot sin parametros
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 */
	public RobotController() {
		try {
			robot = new Robot();
		}catch (Exception e) {
			System.err.println("Error al iniciar el robot");
		}
	}
	
	/**
	 * Instancia del robot recibiendo como parametro el nombre de la vpn
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 * @param vpn Nombre de la vpn
	 */
	public RobotController(String vpn) {
		this.vpn = vpn;
		try {
			robot = new Robot();
		}catch (Exception e) {
			System.err.println("Error al iniciar el robot");
		}
	}
	
	/**
	 * Colocar mouse en una posición de la pantalla en especifica
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 * @param x posición en equis(x) del mouse
	 * @param y posición en y del mouse
	 */
	public void dimensions(int x, int y) {
		robot.mouseMove(x, y);
	}
	
	/**
	 * Presionar el boton izquierdo del mouse
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 */
	public void clickPressed() {
		robot.mousePress(mask);
		robot.mouseRelease(mask);
	}

	public void pressF12() {
		robot.keyPress(KeyEvent.VK_F12);
		robot.keyRelease(KeyEvent.VK_F12);
	}
	
	public void pressF5() {
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
	}
	
	public void changeDeveloper(boolean banderaInicioApp) throws InterruptedException {
		//Pulsar F12 para abrir el developer de google
		pressF12();
//		if(banderaInicioApp) toggleDeviceToolbar();
		Thread.sleep(1850);
		//Presionar la opcion de cambiar el responsive de la pagina
		dimensions(523, 123);
		clickPressed();
		Thread.sleep(1850);
		//Presionar Galaxy S5
		dimensions(518, 188);
		clickPressed();
		Thread.sleep(2250);
		//Cambiar a modo telefono y acutalizar
		pressF5();
		Thread.sleep(2250);
		pressF12();
	}
	
	/**
	 * Hacer una copia en el portapapeles de la vpn
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 */
	public void copy() {
		 StringSelection stringSelection = new StringSelection(this.vpn);
		 clipboard.setContents(stringSelection, null);
	}
	
	/**
	 * Se copia en el portapapeles la direccion de la imagen a publicar
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 * @param imagePath path de la imagen a publicar
	 */
	public void copy(String imagePath) {
		 StringSelection stringSelection = new StringSelection(imagePath);
		 try {
			 clipboard.setContents(stringSelection, null);
		 }catch(IllegalStateException e) {
			 System.err.println("No puede copiarse en el portapapeles");
		 }
		 
		
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_C);
		 
	}
	
	/**
	 * Hace un ctrl+v o pega en donde este posicionado el mouse y pueda escribir
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 */
	public void paste() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
	}
	
	/**
	 * Hacer (enter) desde codigo
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 */
	public void enter() {
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/**
	 * Cerrar una ventana con el comando Alt+F4
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 */
	public void close() {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_F4);
		robot.keyRelease(KeyEvent.VK_ALT);
		robot.keyRelease(KeyEvent.VK_F4);
	}
	
	public void copyCtrlC() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_C);
	}
	/**
	 * Maximizar una ventana de windows con el comando Windows+Up o Windows+Flecha Arriba
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 */
	public void maximizar() {
		robot.keyPress(KeyEvent.VK_WINDOWS);
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_WINDOWS);
		robot.keyRelease(KeyEvent.VK_UP);
	}
	
	/**
	 * Hacer scroll de manera random, valores negativo indica movimiento hacía arriba,
	 * valores positivos indica valores hacía abajo
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 * @param value
	 */
	public void mouseScroll(int value){
		robot.mouseWheel(value);
	}
	
	/**
	 * Maximizar una pantalla a la izquierda con el comando Windows+Bloq Mayus+Left o Windows+Bloq Mayus+Flecha Izquierda
	 * 
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 */
	public void maxiIzquierda() {
		robot.keyPress(KeyEvent.VK_WINDOWS);
		robot.keyPress(KeyEvent.VK_CAPS_LOCK);
		robot.keyPress(KeyEvent.VK_LEFT);
		robot.keyRelease(KeyEvent.VK_WINDOWS);
		robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
		robot.keyRelease(KeyEvent.VK_LEFT);
	}
	
	/**
	 *  Pulsar tecla tabulador
	 *  
	 *  @author Luis Morales
	 *  
	 */
	public void pressTab() {
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
	}
	
	/**
	 *  Pulsar tecla Shift + Tabulador
	 *  
	 *  @author Luis Morales
	 *  
	 */
	public void pulsarShiftTabulador() {
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_TAB);
	}
	
	public void pressShift() {
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_SHIFT);
	}
	
	public void pressDown() {
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyRelease(KeyEvent.VK_DOWN);
	}
	
	
	public void pressUp() {
		robot.keyPress(KeyEvent.VK_UP);
		robot.keyRelease(KeyEvent.VK_UP);
	}
	
	public void pressSpace() {
		robot.keyPress(KeyEvent.VK_SPACE);
		robot.keyRelease(KeyEvent.VK_SPACE);
	}
	
	public void toggleDeviceToolbar() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_M);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_M);
	}
	/**
	 * Seleccionar todo Ctrl + A y Eliminar Delete
	 */
	public void selectAllAndDelete() {
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyPress(KeyEvent.VK_DELETE);
		robot.keyRelease(KeyEvent.VK_DELETE);
	}
	
	/**
	 * Darle click al boton de Escape
	 * 
	 */
	public void pressEsc() {
		robot.keyPress(KeyEvent.VK_ESCAPE);
		robot.keyRelease(KeyEvent.VK_ESCAPE);
	}
	
	/**
	 * 
	 * 
	 * @param key
	 */
	public void pressKey(char key) {
		switch(key){
			
        case '☺': altNumpad("1"); break;
        case '☻': altNumpad("2"); break;
        case '♥': altNumpad("3"); break;
        case '♦': altNumpad("4"); break;
        case '♣': altNumpad("5"); break;
        case '♠': altNumpad("6"); break;
        case '♂': altNumpad("11"); break;
        case '♀': altNumpad("12"); break;
        case '♫': altNumpad("14"); break;
        case '☼': altNumpad("15"); break;
        case '►': altNumpad("16"); break;
        case '◄': altNumpad("17"); break;
        case '↕': altNumpad("18"); break;
        case '‼': altNumpad("19"); break;
        case '¶': altNumpad("20"); break;
        case '§': altNumpad("21"); break;
        case '▬': altNumpad("22"); break;
        case '↨': altNumpad("23"); break;
        case '↑': altNumpad("24"); break;
        case '↓': altNumpad("25"); break;
        case '→': altNumpad("26"); break;
        case '∟': altNumpad("28"); break;
        case '↔': altNumpad("29"); break;
        case '▲': altNumpad("30"); break;
        case '▼': altNumpad("31"); break;
        case '!': altNumpad("33"); break;
        case '"': altNumpad("34"); break;
        case '#': altNumpad("35"); break;
        case '$': altNumpad("36"); break;
        case '%': altNumpad("37"); break;
        case '&': altNumpad("38"); break;
        case '\'': altNumpad("39"); break;
        case '(': altNumpad("40"); break;
        case ')': altNumpad("41"); break;
        case '*': altNumpad("42"); break;
        case '+': altNumpad("43"); break;
        case ',': altNumpad("44"); break;
        case '-': altNumpad("45"); break;
        case '.': altNumpad("46"); break;
        case '/': altNumpad("47"); break;
        case '0': altNumpad("48"); break;
        case '1': altNumpad("49"); break;
        case '2': altNumpad("50"); break;
        case '3': altNumpad("51"); break;
        case '4': altNumpad("52"); break;
        case '5': altNumpad("53"); break;
        case '6': altNumpad("54"); break;
        case '7': altNumpad("55"); break;
        case '8': altNumpad("56"); break;
        case '9': altNumpad("57"); break;
        case ':': altNumpad("58"); break;
        case ';': altNumpad("59"); break;
        case '<': altNumpad("60"); break;
        case '=': altNumpad("61"); break;
        case '>': altNumpad("62"); break;
        case '?': altNumpad("63"); break;
        case '@': altNumpad("64"); break;
        case 'A': altNumpad("65"); break;
        case 'B': altNumpad("66"); break;
        case 'C': altNumpad("67"); break;
        case 'D': altNumpad("68"); break;
        case 'E': altNumpad("69"); break;
        case 'F': altNumpad("70"); break;
        case 'G': altNumpad("71"); break;
        case 'H': altNumpad("72"); break;
        case 'I': altNumpad("73"); break;
        case 'J': altNumpad("74"); break;
        case 'K': altNumpad("75"); break;
        case 'L': altNumpad("76"); break;
        case 'M': altNumpad("77"); break;
        case 'N': altNumpad("78"); break;
        case 'O': altNumpad("79"); break;
        case 'P': altNumpad("80"); break;
        case 'Q': altNumpad("81"); break;
        case 'R': altNumpad("82"); break;
        case 'S': altNumpad("83"); break;
        case 'T': altNumpad("84"); break;
        case 'U': altNumpad("85"); break;
        case 'V': altNumpad("86"); break;
        case 'W': altNumpad("87"); break;
        case 'X': altNumpad("88"); break;
        case 'Y': altNumpad("89"); break;
        case 'Z': altNumpad("90"); break;
        case '[': altNumpad("91"); break;
        case '\\': altNumpad("92"); break;
        case ']': altNumpad("93"); break;
        case '^': altNumpad("94"); break;
        case '_': altNumpad("95"); break;
        case '`': altNumpad("96"); break;
        case 'a': altNumpad("97"); break;
        case 'b': altNumpad("98"); break;
        case 'c': altNumpad("99"); break;
        case 'd': altNumpad("100"); break;
        case 'e': altNumpad("101"); break;
        case 'f': altNumpad("102"); break;
        case 'g': altNumpad("103"); break;
        case 'h': altNumpad("104"); break;
        case 'i': altNumpad("105"); break;
        case 'j': altNumpad("106"); break;
        case 'k': altNumpad("107"); break;
        case 'l': altNumpad("108"); break;
        case 'm': altNumpad("109"); break;
        case 'n': altNumpad("110"); break;
        case 'o': altNumpad("111"); break;
        case 'p': altNumpad("112"); break;
        case 'q': altNumpad("113"); break;
        case 'r': altNumpad("114"); break;
        case 's': altNumpad("115"); break;
        case 't': altNumpad("116"); break;
        case 'u': altNumpad("117"); break;
        case 'v': altNumpad("118"); break;
        case 'w': altNumpad("119"); break;
        case 'x': altNumpad("120"); break;
        case 'y': altNumpad("121"); break;
        case 'z': altNumpad("122"); break;
        case '{': altNumpad("123"); break;
        case '|': altNumpad("124"); break;
        case '}': altNumpad("125"); break;
        case '~': altNumpad("126"); break;
        case '⌂': altNumpad("127"); break;
        case 'Ç': altNumpad("128"); break;
        case 'ü': altNumpad("129"); break;
        case 'é': altNumpad("130"); break;
        case 'â': altNumpad("131"); break;
        case 'ä': altNumpad("132"); break;
        case 'à': altNumpad("133"); break;
        case 'å': altNumpad("134"); break;
        case 'ç': altNumpad("135"); break;
        case 'ê': altNumpad("136"); break;
        case 'ë': altNumpad("137"); break;
        case 'è': altNumpad("138"); break;
        case 'ï': altNumpad("139"); break;
        case 'î': altNumpad("140"); break;
        case 'ì': altNumpad("141"); break;
        case 'Ä': altNumpad("142"); break;
        case 'Å': altNumpad("143"); break;
        case 'É': altNumpad("144"); break;
        case 'æ': altNumpad("145"); break;
        case 'Æ': altNumpad("146"); break;
        case 'ô': altNumpad("147"); break;
        case 'ö': altNumpad("148"); break;
        case 'ò': altNumpad("149"); break;
        case 'û': altNumpad("150"); break;
        case 'ù': altNumpad("151"); break;
        case 'ÿ': altNumpad("152"); break;
        case 'Ö': altNumpad("153"); break;
        case 'Ü': altNumpad("154"); break;
        case '¢': altNumpad("155"); break;
        case '£': altNumpad("156"); break;
        case '¥': altNumpad("157"); break;
        case '₧': altNumpad("158"); break;
        case 'ƒ': altNumpad("159"); break;
        case 'á': altNumpad("160"); break;
        case 'í': altNumpad("161"); break;
        case 'ó': altNumpad("162"); break;
        case 'ú': altNumpad("163"); break;
        case 'ñ': altNumpad("164"); break;
        case 'Ñ': altNumpad("165"); break;
        case 'ª': altNumpad("166"); break;
        case 'º': altNumpad("167"); break;
        case '¿': altNumpad("168"); break;
        case '¬': altNumpad("170"); break;
        case '½': altNumpad("171"); break;
        case '¼': altNumpad("172"); break;
        case '¡': altNumpad("173"); break;
        case '«': altNumpad("174"); break;
        case '»': altNumpad("175"); break;
        case '░': altNumpad("176"); break;
        case '▒': altNumpad("177"); break;
        case '▓': altNumpad("178"); break;
        case '│': altNumpad("179"); break;
        case '┤': altNumpad("180"); break;
        case '╡': altNumpad("181"); break;
        case '╢': altNumpad("182"); break;
        case '╖': altNumpad("183"); break;
        case '╕': altNumpad("184"); break;
        case '╣': altNumpad("185"); break;
        case '║': altNumpad("186"); break;
        case '╗': altNumpad("187"); break;
        case '╜': altNumpad("189"); break;
        case '╛': altNumpad("190"); break;
        case '└': altNumpad("192"); break;
        case '┴': altNumpad("193"); break;
        case '┬': altNumpad("194"); break;
        case '├': altNumpad("195"); break;
        case '─': altNumpad("196"); break;
        case '┼': altNumpad("197"); break;
        case '╞': altNumpad("198"); break;
        case '╟': altNumpad("199"); break;
        case '╚': altNumpad("200"); break;
        case '╔': altNumpad("201"); break;
        case '╩': altNumpad("202"); break;
        case '╦': altNumpad("203"); break;
        case '╠': altNumpad("204"); break;
        case '╬': altNumpad("206"); break;
        case '╧': altNumpad("207"); break;
        case '╨': altNumpad("208"); break;
        case '╤': altNumpad("209"); break;
        case '╥': altNumpad("210"); break;
        case '╙': altNumpad("211"); break;
        case '╘': altNumpad("212"); break;
        case '╒': altNumpad("213"); break;
        case '╓': altNumpad("214"); break;
        case '╫': altNumpad("215"); break;
        case '╪': altNumpad("216"); break;
        case '┘': altNumpad("217"); break;
        case '┌': altNumpad("218"); break;
        case '█': altNumpad("219"); break;
        case '▄': altNumpad("220"); break;
        case '▌': altNumpad("221"); break;
        case '▀': altNumpad("223"); break;
        case 'α': altNumpad("224"); break;
        case 'ß': altNumpad("225"); break;
        case 'Γ': altNumpad("226"); break;
        case 'π': altNumpad("227"); break;
        case 'Σ': altNumpad("228"); break;
        case 'σ': altNumpad("229"); break;
        case 'µ': altNumpad("230"); break;
        case 'τ': altNumpad("231"); break;
        case 'Φ': altNumpad("232"); break;
        case 'Θ': altNumpad("233"); break;
        case 'Ω': altNumpad("234"); break;
        case 'δ': altNumpad("235"); break;
        case '∞': altNumpad("236"); break;
        case 'φ': altNumpad("237"); break;
        case 'ε': altNumpad("238"); break;
        case '∩': altNumpad("239"); break;
        case '≡': altNumpad("240"); break;
        case '±': altNumpad("241"); break;
        case '≥': altNumpad("242"); break;
        case '≤': altNumpad("243"); break;
        case '⌠': altNumpad("244"); break;
        case '⌡': altNumpad("245"); break;
        case '÷': altNumpad("246"); break;
        case '≈': altNumpad("247"); break;
        case '°': altNumpad("248"); break;
        case '∙': altNumpad("249"); break;
        case '·': altNumpad("250"); break;
        case '√': altNumpad("251"); break;
        case '²': altNumpad("253"); break;
        case '■': altNumpad("254"); break;
        case ' ': robot.keyPress(KeyEvent.VK_SPACE); robot.keyRelease(KeyEvent.VK_SPACE); break;
        default: return;
		}

	}
	

	private void altNumpad(String numpadCodes){
	    if (numpadCodes == null || !numpadCodes.matches("^\\d+$")){
	        return;
	    }               

	    robot.keyPress(KeyEvent.VK_ALT);

	    for (char charater : numpadCodes.toCharArray()){

	        int NUMPAD_KEY = getNumpad(charater);

	        if (NUMPAD_KEY != -1){
	            robot.keyPress(NUMPAD_KEY);
	            robot.keyRelease(NUMPAD_KEY);
	        }
	    }

	    robot.keyRelease(KeyEvent.VK_ALT);        
	}

	private int getNumpad(char numberChar){
	    switch (numberChar){
	        case '0' : return KeyEvent.VK_NUMPAD0;
	        case '1' : return KeyEvent.VK_NUMPAD1;
	        case '2' : return KeyEvent.VK_NUMPAD2;
	        case '3' : return KeyEvent.VK_NUMPAD3;
	        case '4' : return KeyEvent.VK_NUMPAD4;
	        case '5' : return KeyEvent.VK_NUMPAD5;
	        case '6' : return KeyEvent.VK_NUMPAD6;
	        case '7' : return KeyEvent.VK_NUMPAD7;
	        case '8' : return KeyEvent.VK_NUMPAD8;
	        case '9' : return KeyEvent.VK_NUMPAD9;
	        default: return -1;
	    }

	}

	public void openChromeIncognit() throws IOException {
		String path = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe -incognito";
		Runtime objrun = Runtime.getRuntime();
		try {
			objrun.exec(path);
		} catch(IOException e) {		
			path = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe -incognito";
			objrun.exec(path);
		}
		objrun.freeMemory();
	}
	
	protected void inputWrite(String inputData) throws InterruptedException {
		RobotController rob = new RobotController();
		for(int i=0;i<inputData.length();i++) {
			Thread.sleep(164);
			String caracterActual = inputData.substring(i,i+1);
			rob.pressKey(caracterActual.charAt(0));
		}
	}
	
	protected void inputWriteUsers(String inputData,String user) throws InterruptedException {
		RobotController rob = new RobotController();
		for(int i=0;i<inputData.length();i++) {
			Thread.sleep(164);
			String caracterActual = inputData.substring(i,i+1);
			if(caracterActual.equals("&")) {
				for(int j=0; j<user.length();j++) {
					Thread.sleep(164);
					String caracter = user.substring(j,j+1);
					rob.pressKey(caracter.charAt(0));
				}
			}
			rob.pressKey(caracterActual.charAt(0));
		}
	}
	
}

