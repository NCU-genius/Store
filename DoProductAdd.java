package com.lmonkey.servlet.product;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.lmonkey.entity.LMONKEY_PRODUCT;
import com.lmonkey.service.LMONKEY_PRODUCTDao;

@WebServlet("/manage/admin_doproductadd")
public class DoProductAdd extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SmartUpload su= new SmartUpload();
		su.initialize(this.getServletConfig(), request, response);
		try {
			su.upload();
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
		Files fs= su.getFiles();
		File f = fs.getFile(0);
		String fname = f.getFileName();
		
		try {
			su.save("images/product");
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
		
		Request req1 = su.getRequest();
		
		String pname = req1.getParameter("productName");
		String id = req1.getParameter("parentId");
		String price = req1.getParameter("productPrice");
		String desc = req1.getParameter("productDesc");
		String stock = req1.getParameter("productStock");
		
		LMONKEY_PRODUCT p = new LMONKEY_PRODUCT(
						0,
						pname,
						desc,
						Integer.parseInt(price),
						Integer.parseInt(stock),
						Integer.parseInt(id.split("-")[0]),
						Integer.parseInt(id.split("-")[1]),
						fname
				);
				
		int count = LMONKEY_PRODUCTDao.insert(p);
		
				if(count >0 ) {
					
					response.sendRedirect("admin_productselect");
				} else {
					PrintWriter out = response.getWriter();
					out.write("<script>");
					out.write("alert('²úÆ·Ìí¼ÓÊ§°Ü')");
					out.write("location.href='manage/admin_toproductadd'");
					out.write("</script>");
				}
		
		
	}

}
