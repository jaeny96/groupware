//package com.group.board.control;
//
//import java.io.File;
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.oreilly.servlet.MultipartRequest;
//import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
//import com.oreilly.servlet.multipart.FileRenamePolicy;
//
///**
// * Servlet implementation class BoardFileUploadServlet
// */
//public class BoardFileUploadServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		MultipartRequest mr;
////		String saveDirectory = "C:\\Users\\JISU\\Desktop\\JAVAW_OJS\\myweb\\upload";
//		String saveDirectory = getServletContext().getRealPath("/");
//		int maxPostSize = 1024*100;
//		String encoding = "utf-8";
//		FileRenamePolicy policy = new DefaultFileRenamePolicy();
//		mr = new MultipartRequest(request, saveDirectory, maxPostSize,encoding, policy);
//		
//		File file = mr.getFile("fileUploadBoard");
//		System.out.println("a "+file.getName()); //파일 이름 확인
//		System.out.println("b "+file.length()); //파일 크기 확인
//		//해결해야할것 : 업로드 된 파일들이 다운로드 형태로 보여줘야함 , 일련번호 1,2 말고 파일이름 변경(로그인한 아이디라던지)
////		InputStream is = request.getInputStream();
////		Scanner sc =new Scanner(is);
////		String line=null;
////		while(sc.hasNextLine()) {
////			line = sc.nextLine();
////			System.out.println(line);
////		}
////		sc.close();
//
//	}
//
//}
