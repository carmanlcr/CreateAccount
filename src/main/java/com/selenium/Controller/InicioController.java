package com.selenium.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;


import com.selenium.Model.Db_Admin_Users_Create;
import com.selenium.Model.Db_admin_users_create_detail;
import com.selenium.Model.User;

public class InicioController {
	
	private static final String URL_PAGE_GOOGLE = "https://accounts.google.com/signup/v2";
	private static final String URL_PAGE_FACEBOOK = "https://www.facebook.com/";
	private static final String URL_PAGE_INSTAGRAM = "https://www.instagram.com/accounts/emailsignup/";
	private static final String URL_PAGE_TWITTER = "https://twitter.com/i/flow/signup";
	private static final String PATH_IMAGES = "C:\\ImagenesSikuli\\";
	private static List<Db_Admin_Users_Create> list = new ArrayList<Db_Admin_Users_Create>();
	private static RobotController robot;
	private static VpnController vpn;
	private static boolean banderaVpn = false;
	private static DriverController drive;
	protected static String[] biografia = {"¡No soy bipolar! Me hacen enfadar cuando estoy feliz =D",
			"La única manera de hacer un buen trabajo, es amar lo que haces. Si no lo has encontrado, sigue buscando. No te conformes",
			"La vida es una aventura atrevida o no es nada",
			"Un viaje de mil millas comienza con un solo paso",
			"Recuerda que eres tan bueno como lo mejor que hayas hecho en tu vida",
			"No me acuerdo de olvidarte",
			"Te echo de menos más de lo que puedo soportar",
			"No quiero necesitarte porque no puedo tenerte",
			"Tú haces que quiera ser mejor persona ",
			"Al final lo que importa no son los años de vida, sino la vida de los años",
			"Es propio de las censuras acreditar las opiniones que atacan",
			"Lo que haces por ti se desvanece cuando mueres. Lo que haces por el resto, conforma tu legado",
			"Antes de convencer al intelecto, es imprescindible tocar y predisponer al corazón",
			"La vida no es un problema para ser resuelto, es un misterio para ser vivido",
			"Lo poco que he aprendido carece de valor comparado con lo que ignoro y no desespero en aprender",
			"Antes de hablar piensa, pero antes de pensar, lee",
			"Los grandes pensamientos aparecieron al andar",
			"La vida no trata de recoger los frutos que cosechas cada día, sino de las semillas que siembras",
			"Me gustan mis errores. No quiero renunciar a la deliciosa libertad de equivocarme",
			"El mayor misterio del mundo es que resulta comprensible",
			"Quien tiene paciencia obtendrá lo que desea",
			"Siempre es temprano para rendirse",
			"La edad es algo que no importa, a menos que seas un queso",
			"Un día sin reír es un día perdido",
			"Quien es feliz hará también felices a los demás",
			"Para viajar lejos, no hay mejor nave que un libro",
			"El que busque la verdad corre el riesgo de encontrarla",
			"Sé tú e intenta ser feliz, pero sobre todo sé tú",
			"La vida es como el jazz, mejor si es improvisada",
			"No pongas en peligro tu vida, eres todo lo que tienes",
			"Las estrellas están ahí, solo debes mirarlas",
			"El amor no necesita ser entendido, necesita ser demostrado",
			"Donde quiera que vayas, sin importar el clima, lleva siempre tu propio sol. La actitud lo es todo",
			"Las grandes mentes discuten ideas. Las mentes medianas discuten los eventos. Las mentes pequeñas discuten con gente",
			"Las familias son la brújula que nos guían. Son la inspiración para llegar a grandes alturas, y nuestro consuelo cuando ocasionalmente fallamos",
			"Primero de ignoran. Luego se ríen de ti. Después de atacan. A continuación, se gana",
			"No te olvides que tal vez eres el faro en la tempestad de alguien",
			"Mi mujer y yo fuimos felices durante 20 años. Luego, nos conocimos",
			"Ríe y el mundo reirá contigo. Ronca y dormirás solo",
			"La pereza no es más que el hábito de descansar antes de que te canses"};
	
	public static void main(String args[]) {
		try {
			init();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static void init() throws InterruptedException, IOException {
		Db_Admin_Users_Create user_C = new Db_Admin_Users_Create();
		list = user_C.getUserForCreate();
		
		if(list.size() < 1) {
			System.out.println("No hay perfiles para crear");
			JOptionPane.showMessageDialog(null, "NO HAY PERFILES PARA CREAR","INFORMATION",JOptionPane.INFORMATION_MESSAGE);
		}else {
			for(Db_Admin_Users_Create users_create : list) {
				int aceptar = JOptionPane.showConfirmDialog(null, "El numero a usar es "+users_create.getPhones());
				
				//Si se pulsa el boton de aceptar
				if(aceptar == 0) {
					String ip = validateIP();
					robot = new RobotController();
					vpn = new VpnController(robot);
					try {
						vpn.iniciarVpn(users_create.getVpn(), banderaVpn);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					String ipActual = validateIP();
					if(ip.equals(ipActual)) {
						System.out.println("No se pudo conectar a la vpn");
					}else {
						drive = new DriverController();
						drive.optionsChrome();
						
						User user = new User();
						user.setPhone(users_create.getPhones());
						user = user.getUserReview();
						String[] full_name = users_create.getFull_name().split(" ");
						String username = users_create.getUsername() == null ? generateUsername(full_name[0], full_name[1]) : users_create.getUsername();
						//Validar si ya se registro en google
						
						if(user == null) {
							System.out.println("Crear cuenta de Google");
							GoogleController google = new GoogleController(drive, users_create,URL_PAGE_GOOGLE,username,robot,PATH_IMAGES);
							users_create = google.init();
						}
						Db_admin_users_create_detail create_detail = new Db_admin_users_create_detail();
						create_detail.setUsers_create_id(users_create.getUsers_create_id());
						create_detail = create_detail.getDetail();
						
						if(create_detail != null) {
							downloadImagesSftp(create_detail);
						}
						String bio = biografia[getNumberRandomForSecond(0, biografia.length)];
						if(!users_create.isCreate_fb()) {
							FacebookController facebook = new FacebookController(drive, users_create,URL_PAGE_FACEBOOK,robot,create_detail, bio);
							facebook.init();
						}
						
						if(!users_create.isCreate_tw()) {
							TwitterController twitter = new TwitterController(drive, users_create,URL_PAGE_TWITTER,robot,create_detail,bio);
							users_create = twitter.init();
						}
						
						if(!users_create.isCreate_ig()) {
							InstagramController instagram = new InstagramController(drive,users_create,URL_PAGE_INSTAGRAM,robot,username,create_detail,bio);
							instagram.init();
						}
						
						
						
						drive.quit();

						
						
					}//Fin del else si se conecto a la vpn
					vpn.desconectVpn();
				}//Si se pulso el boton de aceptar 
			}//Fin del for de los usuarios por crear
			
		}//Fin del if si hay usuarios por crear
		System.out.println("Finalizo el programa");
	}
	
	/**
	 * generador de username automatico con las 3 primeras letras del nombre,
	 * seguidillas de numeros del 1 al 8 
	 * y las 3 primeras letras del apellido
	 * 
	 * @return String 
	 */
	private static String generateUsername(String first_name, String last_name) {
		
		
		return first_name.substring(0, 3)+((int)(Math.random() * 456789) + 999999)+last_name.substring(0,3);
	}
	
	private static void downloadImagesSftp(Db_admin_users_create_detail create_detail) {
		SftpController sftp = new SftpController();
		sftp.downloadFileSftp(create_detail.getfAdicional());
		sftp.downloadFileSftp(create_detail.getfPerfil());
		sftp.downloadFileSftp(create_detail.getfPortada());
	}
	
	/**
	 * Validar ip publica de la maquina
	 * 
	 * @return String 
	 */
	private static String validateIP() {

		try {

			URL whatismyip = new URL("http://checkip.amazonaws.com");

			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

			return in.readLine();

		} catch (MalformedURLException ex) {

			System.err.println(ex);
			return "190.146.186.130";
		} catch (IOException ex) {

			System.err.println(ex);
			return "190.146.186.130";
		}

	}
	
	
	protected static int getNumberRandomForSecond(int init, int fin) {
		return ThreadLocalRandom.current().nextInt(init, fin + 1);
	}
	
}
