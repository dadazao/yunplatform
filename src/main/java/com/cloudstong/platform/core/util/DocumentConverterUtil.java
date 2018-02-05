package com.cloudstong.platform.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

//import org.artofsolving.jodconverter.OfficeDocumentConverter;
//import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
//import org.artofsolving.jodconverter.office.OfficeManager;

/**
 * @author liutao Created on 2014-1-10
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:
 * 
 */
public class DocumentConverterUtil {
	/**
	 * Description:将文档格式的文件转换为归档文件
	 * 
	 * Steps:
	 * 
	 * @param fileDirAndName
	 * 				需要转换的文件路径,不包含服务器路径.eg: aaa/bbb/sadf.doc
	 * @param pdfFileDirAndName
	 * 				服务器路径,不包含应用内部的文件层次.eg: D://tomcat/webapps/ROOT
	 * @throws IOException
	 */
	public static synchronized void convert(String pFileSource, String pFileDest) throws IOException  {
		try {
			new Socket("127.0.0.1", 8100);
		} catch (Exception e) {
			System.out.println("端口未开放");
		}
		docToPdfThread docThread = new docToPdfThread();
		docThread._fileSource = new File(pFileSource);
		docThread._fileDest = new File(pFileDest);
		docThread.start();
	}
	
	private static class docToPdfThread extends java.lang.Thread {
		public File _fileSource;
		public File _fileDest;

		@Override
		public void run() {
			try {
				docToPdf(_fileSource, _fileDest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public static synchronized void docToPdf(File pFileSource, File pFileDest) {
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		try {
			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			if(pFileSource.isFile())System.out.println(123);
			converter.convert(pFileSource, pFileDest);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
	}
*/
	public static synchronized void docToPdf(File pFileSource, File pFileDest) throws IOException {
		// 文件路径
//		String path = PathUtil.getWebInfPath() + "/WEB-INF/classes/global.properties";
//		SystemProperty sp = new SystemProperty(path);
//		DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
//		config.setOfficeHome(sp.getKeyValue("openOffice"));
//
//		OfficeManager officeManager = config.buildOfficeManager();
//		officeManager.start();
//
//		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
//		if (pFileSource.exists()) {// 找不到源文件, 则返回
//			converter.convert(pFileSource, pFileDest);
//		}
//		officeManager.stop();
	}
	
	public static synchronized void pdf2swf(String inDirAndFileName,String outDirAndFileName, boolean pbIsFull) throws IOException {
		// 文件路径
		String path = PathUtil.getWebInfPath() + "/WEB-INF/classes/global.properties";
		SystemProperty sp = new SystemProperty(path);
		String _sCommand = "\"" + sp.getKeyValue("swfTools") + "\" \"" + inDirAndFileName + "\" -o \"" + outDirAndFileName;
		if (pbIsFull) {
			_sCommand = _sCommand + ".swf\"";
		} else {
			_sCommand = _sCommand + "%.swf\" -f -T 9 -t -s storeallcharacters";
		}
		Process process = Runtime.getRuntime().exec(_sCommand);
		// 非要读取一遍cmd的输出，要不不会flush生成文件（多线程）
		new DotOutput(process.getInputStream()).start();
		new DotOutput(process.getErrorStream()).start();
		try {
			// 调用waitFor方法，是为了阻塞当前进程，直到cmd执行完
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// System.out.println(process.exitValue());
	}
	
	/**
	 * 多线程内部类 读取转换时cmd进程的标准输出流和错误输出流，这样做是因为如果不读取流，进程将死锁
	 * 
	 * @author iori
	 */
	private static class DotOutput extends Thread {
		public InputStream is;

		public DotOutput(InputStream is) {// 构造方法
			this.is = is;
		}

		@Override
		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					this.is));
			String str = "";
			try {
				while ((str = br.readLine()) != null)
					;// 这里并没有对流的内容进行处理，只是读了一遍
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String fileName = "E://2013111161957.doc";
		try {
			//DocumentConverterUtil.convert(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
