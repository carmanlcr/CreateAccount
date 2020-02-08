package com.selenium.Controller;


import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;


/**
 * Connecion SFTP para la descarga de imagenes en el servidor
 * 
 * @author Luis Morales
 *
 */
public class SftpController {

	private final String URL_SFTP = "192.168.2.6";
	private final String USERNAME = "root";
	private final String PASSWORD = "R315T4R*";
	protected final static String PATH_IMAGE_DOWNLOAD_FTP = "C:\\imagesSftp\\";
	private JSch jsch;
	
	
	protected ChannelSftp connectionSftp() {
		jsch = new JSch();
		
	    try {
			jsch.setKnownHosts("/var/www/generate");
			Session jschSession = jsch.getSession(USERNAME, URL_SFTP);
			java.util.Properties config = new java.util.Properties(); 
		    config.put("StrictHostKeyChecking", "no");
		    jschSession.setConfig(config);
		    jschSession.setPassword(PASSWORD);
		    jschSession.connect();
			return (ChannelSftp) jschSession.openChannel("sftp");
			
		}catch (JSchException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void downloadFileSftp(String name_image) {
		ChannelSftp channelSftp = connectionSftp();
		try {
			channelSftp.connect();
			
			String remoteFile = "/var/www/generate/public/images/profiles/"+name_image;
		    try {
				channelSftp.get(remoteFile, PATH_IMAGE_DOWNLOAD_FTP);
			} catch (SftpException e) {
				e.printStackTrace();
			}
		  
		    channelSftp.exit();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}
}
