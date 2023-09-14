package com.ecom.shopping_cart.controllers;

import com.ecom.shopping_cart.helper.FileUploaderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ImageUploader {
		@Autowired
		private FileUploaderHelper fileUploadHelper;
		
		@PostMapping("/upload-file")
		public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
//			System.out.println(file.getOriginalFilename());
//			System.out.println(file.getSize());
//			System.out.println(file.getName());
			try {
				if(file.isEmpty()) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file is empty,Request for non empty file");
					
				}
				if(!file.getContentType().equals("image/jpeg")) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only jpg format allowed");	
				}
				boolean f=fileUploadHelper.uploadFile(file);
				if(f) {
					return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString());
//					return ResponseEntity.ok("File uploaded successfully");
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong ....!");
		}

		@PostMapping("/upload-data")
		public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file){
//			System.out.println(file.getOriginalFilename());
//			System.out.println(file.getSize());
//			System.out.println(file.getName());
			try {
				if(file.isEmpty()) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file is empty,Request for non empty file");
					
				}
				if(!file.getContentType().equals(".xls")) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only e format allowed");	
				}
				boolean f=fileUploadHelper.uploadFile(file);
				if(f) {
					return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString());
//					return ResponseEntity.ok("File uploaded successfully");
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong ....!");
		}
}
