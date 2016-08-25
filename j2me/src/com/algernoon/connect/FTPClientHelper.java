package com.algernoon.connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPClientHelper {
    
    private transient Logger log = LoggerFactory.getLogger(FTPClientHelper.class);
    private String host;
    private String port;
    private String username;
    private String password;
    private FTPClient client;

    public FTPClientHelper() {}

    public FTPClientHelper(String host, String port, String username, String password){
	this.host = host;
	this.port = port;
	this.username = username;
	this.password = password;
    }

    /**
     * @author algernoon
     * @Description 连接并登录到FTP
     */
    public void login(){
	client = new FTPClient();
	try {
	    client.connect(host, Integer.parseInt(port));
	    int replyCode = client.getReplyCode();
	    if(!FTPReply.isPositiveCompletion(replyCode)){
		client.disconnect();
		throw new SocketException("connection refused.");
	    }
	    if(StringUtils.isNotBlank(username)){
		if(!client.login(username, password)){
		    client.disconnect();
		    throw new RuntimeException(String.format("incorrect username[%s] or password[%s]", username, password));
		}
	    }else{
		throw new RuntimeException("username is required.");
	    }
	} catch (NumberFormatException e) {
	    log.error(e.getMessage(), e);
	    throw new RuntimeException(e);
	} catch (SocketException e) {
	    log.error("Cannot connect to specified ftp server : {}:{} \n Exception message is: {}", new Object[]{host, port, e.getMessage()});
	    throw new RuntimeException(e);
	} catch (IOException e) {
	    log.error(e.getMessage(), e);
	    throw new RuntimeException(e);
	}
	System.err.println("登录FTP成功。。。。。。。。。。。。");
    }
    
    /**
     * @author algernoon
     * @Description 判断文件是否存在
     * @param remoteFile 文件
     * @return
     */
    public boolean isRemoteFileExists(String remoteFile){
	try { 
	    FTPFile[] files = client.listFiles(remoteFile);
	    return !ArrayUtils.isEmpty(files) && files.length > 0;
	} catch (IOException e) {
	    log.error(e.getMessage(), e);
	    return false;
	}
    }	
    
    /**
     * @author algernoon
     * @Description 判断目录是否存在
     * @param remoteDirPath
     * @return
     */
    public boolean isRemoteDirExists(String remoteDirPath){
	try{
	    return client.changeWorkingDirectory(remoteDirPath);   	
	}catch(IOException e){
	    log.error(e.getMessage(), e);
	    return false;
	}
    }
    
    /**
     * @author algernoon
     * @Description 文件上传
     * @param localFile 本地文件
     * @param remoteFilePath 远程文件全路径
     * @param remoteFileName
     * @return
     */
    public boolean upload(File localFile, String remoteFilePath, String remoteFileName){
	try {
	    if(remoteFilePath == null){
		log.error("remoteFilePath file is required.");
		throw new IOException("remoteFilePath file is required.");
	    }
	    //set ftp file transfer mode
	    client.setFileType(FTP.BINARY_FILE_TYPE);
	    //create directory
	    client.makeDirectory(new String(remoteFilePath.getBytes(), client.getControlEncoding()));
	    //change directory
	    client.changeWorkingDirectory(remoteFilePath);   
	    //construct input stream
	    InputStream is = new FileInputStream(localFile);
	    //put file to server
	    boolean stored = client.storeFile(remoteFileName, is);
	    //close stream
	    IOUtils.closeQuietly(is);
	    log.info("upload {}", stored ? "successful" : "failed");
	    return stored;
	} catch (IOException e) {
	    log.error(e.getMessage(), e);
	    throw new RuntimeException(e);
	} 
    }
    
    /**
     * @author algernoon
     * @Description 下载文件
     * @param remoteFilePath 远程全路径
     * @param localFile 本地文件全路径
     * @return
     */
    public boolean download(String remoteFilePath, File localFile){
	try {
	    client.enterLocalPassiveMode();
	    client.setFileType(FTP.BINARY_FILE_TYPE);
	    if(!localFile.getParentFile().exists()){
		boolean dirCreated = localFile.getParentFile().mkdirs();
		log.info("directory created {}", dirCreated ? "successful" : "failed");
	    }
	    OutputStream os = new FileOutputStream(localFile);
	    boolean received = client.retrieveFile(new String(remoteFilePath.getBytes(), client.getControlEncoding()), os);
	    log.info("download {}", received ? "successful" : "failed");
	    return received;
	} catch (IOException e) {
	    log.error(e.getMessage(), e);
	    throw new RuntimeException(e);
	}
    }
    
    /**
     * @author algernoon
     * @Description 登出FTP
     */
    public void logout(){
	try {
	    client.disconnect();
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
    
    public static void main(String[] args) {
	FTPClientHelper ftp = new FTPClientHelper("192.168.2.223", "21", "uftp", "uftp");
	ftp.login();
	
	boolean r = ftp.isRemoteFileExists("appStore/appplat/app_dir/24251471404534026/3.0/fc5143b8f932782c48284c486b1e7b79_1468554499746.apk");
	System.out.println("r >>> " + r);
	
	ftp.logout();
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
