package com.ecom.shopping_cart.controllers;

import com.ecom.shopping_cart.entities.ItemCategory;
import com.ecom.shopping_cart.entities.Product;
import com.ecom.shopping_cart.helper.ExcelHelper;
import com.ecom.shopping_cart.helper.ResponseMessage;
import com.ecom.shopping_cart.repository.CategoryRepository;
import com.ecom.shopping_cart.services.ProductService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ExcelHelper excelHelper;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductService productService;

//	@GetMapping("/list")
//	public ResponseEntity<?> getProductList(){
//		return ResponseEntity.ok (productService.getProList());
//	}

	@PostMapping("/")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Optional<ItemCategory> item=categoryRepository.findByCategory(product.getCategory().getCategory());
		 if (item.isPresent()) {
	            product.setCategory(item.get());
	        } else {
	            ItemCategory newCategory = new ItemCategory();
	            newCategory.setCategory(product.getCategory().getCategory());
	            product.setCategory(newCategory);
	        }
		productService.saveProduct(product);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	@GetMapping("/")
	public List<Product> find() {
		return productService.findAll();
	}
	@GetMapping("/{category}")
	public ResponseEntity<List<Product>> searchByCat(@PathVariable String category){
		List<Product> list=productService.findCategory(category);
		if(list.size()!=0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
		else
			return ResponseEntity.notFound().build();
	}
	@GetMapping("/{category}/{price}")
	public ResponseEntity<List<Product>> searchByCatPrice(@PathVariable String category,@PathVariable int price){
		List<Product> list=productService.findByPriceAndCat ( category,price );
		if(list.size()!=0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
		else
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/upload")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";

	    if (ExcelHelper.hasExcelFormat(file)) {
	      try {
	    	  productService.save(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      } catch (Exception e) {
	    	  e.printStackTrace();
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	      }
	    }

	    message = "Please upload an excel file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	  }

	 @GetMapping("/download")
	  public ResponseEntity<Resource> getFile() {
	    String filename = "products.xlsx";
	    InputStreamResource file = new InputStreamResource(productService.load());

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
	@GetMapping("/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date ());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<Product> products = productService.findAll ();

		ExcelHelper exporter = new ExcelHelper (products);
		exporter.export( response );

	}

	@GetMapping("/global/{name}")
	public ResponseEntity<List<Product>> globalSearch(@PathVariable String name){
		List<Product> list=productService.globalSer ( name );
		return ResponseEntity.status(HttpStatus.OK).body( list );
	}

}
