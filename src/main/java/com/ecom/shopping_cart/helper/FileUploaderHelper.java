package com.ecom.shopping_cart.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FileUploaderHelper {

	public final String upload_dir="D:\\KAVITA\\SpringBoot\\shopping_Cart\\src\\main\\resources\\static\\image";
//	public final String upload_dir=new ClassPathResource("/static/image").getFile().getAbsolutePath();

	FileUploaderHelper() throws IOException{
		
	}

public boolean uploadFile(MultipartFile multiPart) {
	boolean f=false;
	try {
		InputStream is=multiPart.getInputStream();
		byte data[]=new byte[is.available()];
		is.read(data);
		FileOutputStream fod=new FileOutputStream(upload_dir+File.separator + multiPart.getOriginalFilename());
		fod.write(data);
		fod.flush();
		fod.close();
//		Files.copy(multiPart.getInputStream(), Paths.get(upload_dir+File.separator+multiPart.getOriginalFilename(),StandardCopyOption.REPLACE_EXISTING ));
		f=true;
	
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	return f;
}
}
