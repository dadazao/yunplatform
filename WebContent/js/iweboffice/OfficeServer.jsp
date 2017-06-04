<%@page contentType="text/html; charset=utf-8"%>
<%@page import="java.io.*"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="DBstep.iMsgServer2000.*"%>
<%@page import="com.cloudstong.UUIDGeneratorUtil"%>

<%!public class iWebOffice {
		private byte[] mFileBody;
		private String mFileName;
		private String mFileType;
		private String mFileID;
		private String mRecordID;
		private String mOption;
		private String mFilePath;
		private String mUserName;
		private DBstep.iMsgServer2000 MsgObj;
		
		public void ExecuteRun(HttpServletRequest request,
				HttpServletResponse response) {
			MsgObj = new DBstep.iMsgServer2000(); //创建信息包对象
			mOption = "";
			mRecordID = "";
			mFileBody = null;
			mFileName = "";
			mFileType = "";
			mFileID = "";
			mFilePath = request.getSession().getServletContext().getRealPath(""); //取得服务器路径
			try {
				if (request.getMethod().equalsIgnoreCase("POST")) {
					MsgObj.Load(request); //8.1.0.2版后台类新增解析接口，可支持UTF-8编码自适应功能

					if (MsgObj.GetMsgByName("DBSTEP")
							.equalsIgnoreCase("DBSTEP")) { //判断是否是合法的信息包，或者数据包信息是否完整
						mOption = MsgObj.GetMsgByName("OPTION"); //取得操作信息
						mUserName = MsgObj.GetMsgByName("USERNAME"); //取得系统用户
						if (mOption.equalsIgnoreCase("LOADFILE")) { //下面的代码为打开服务器数据库里的文件
							mRecordID = MsgObj.GetMsgByName("RECORDID"); //取得文档编号
							mFileName = MsgObj.GetMsgByName("FILENAME"); //取得文档名称
							mFileType = MsgObj.GetMsgByName("FILETYPE"); //取得文档类型
							MsgObj.MsgTextClear(); //清除文本信息
							if (MsgObj.MsgFileLoad(mFilePath+"\\"+mFileName)){//从文件夹调入文档
								MsgObj.SetMsgByName("STATUS", "打开成功!"); //设置状态信息
								MsgObj.MsgError(""); //清除错误信息
							} else {
								MsgObj.MsgError("打开失败!"); //设置错误信息
							}
						}else if (mOption.equalsIgnoreCase("SAVEFILE")) { //下面的代码为保存文件在服务器的数据库里
							mRecordID = MsgObj.GetMsgByName("RECORDID"); //取得文档编号
							mFileName = MsgObj.GetMsgByName("FILENAME"); //取得文档名称
							mFileType = MsgObj.GetMsgByName("FILETYPE"); //取得文档类型
							mUserName = mUserName; //取得保存用户名称
							mFileBody = MsgObj.MsgFileBody(); //取得文档内容
							MsgObj.MsgTextClear(); //清除文本信息
							if (MsgObj.MsgFileSave(mFilePath+"\\"+mFileName)){//保存文档内容到文件夹中
								MsgObj.SetMsgByName("STATUS", "保存成功!"); //设置状态信息
								MsgObj.MsgError(""); //清除错误信息
							} else {
								MsgObj.MsgError("保存失败!"); //设置错误信息
							}
							MsgObj.MsgFileClear(); //清除文档内容
						}
					} else {
						MsgObj.MsgError("客户端发送数据包错误!");
						MsgObj.MsgTextClear();
						MsgObj.MsgFileClear();
					}
				} else {
					MsgObj.MsgError("请使用Post方法");
					MsgObj.MsgTextClear();
					MsgObj.MsgFileClear();
				}
				MsgObj.Send(response); //8.1.0.2新版后台类新增的功能接口，返回信息包数据
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}%>
<%
	iWebOffice officeServer = new iWebOffice();
	officeServer.ExecuteRun(request, response);
	out.clear(); //用于解决JSP页面中“已经调用getOutputStream()”问题
	out = pageContext.pushBody(); //用于解决JSP页面中“已经调用getOutputStream()”问题
%>