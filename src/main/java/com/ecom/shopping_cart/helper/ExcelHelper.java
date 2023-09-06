package com.ecom.shopping_cart.helper;

import com.ecom.shopping_cart.entities.ItemCategory;
import com.ecom.shopping_cart.entities.Product;
import com.ecom.shopping_cart.repository.CategoryRepository;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ExcelHelper {

    @Autowired
    static
    CategoryRepository categoryRepository;
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static List<String> HEADERs = Arrays.asList ( "pId", "pName", "Price", "Details", "thumbnail", "qyt", "category" );
    static String SHEET = "products";
    public List<Product> products;

    public ExcelHelper(List<Product> products) {
        this.products = products;
    }
    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals ( file.getContentType () );
    }
    public static List<Product> excelToProducts(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook ( is );
            Sheet sheet = workbook.getSheet ( "Sheet1" );
            Iterator<Row> rows = sheet.iterator ();

            List<Product> products = new ArrayList<> ();
            int rowNumber = 0;
            while (rows.hasNext ()) {
                Row currentRow = rows.next ();
				// skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator ();
                Product product = new Product ();
                ItemCategory itemCategory = new ItemCategory ();
                int cellIdx = 0;
                while (cellsInRow.hasNext ()) {
                    Cell currentCell = cellsInRow.next ();
                    switch (cellIdx) {
                        case 0:
                            product.setpId ( (int) currentCell.getNumericCellValue () );
                            break;

                        case 1:
                            product.setpName ( currentCell.getStringCellValue () );
                            break;

                        case 2:
                            product.setPrice ( currentCell.getNumericCellValue () );
                            break;

                        case 3:
                            product.setDetails ( currentCell.getStringCellValue () );
                            break;
                        case 4:
                            product.setThumbnail ( currentCell.getStringCellValue () );
                            break;

                        case 5:
                            product.setQty ( (int) currentCell.getNumericCellValue () );
                            break;
                        case 6:
                            itemCategory.setCategory ( currentCell.getStringCellValue () );
                            itemCategory.setProduct ( product );
                            product.setCategory ( itemCategory );
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                products.add ( product );
            }
			workbook.close ();
            return products;
        } catch (IOException e) {
            throw new RuntimeException ( "fail to parse Excel file: " + e.getMessage () );
        }
    }
    public static ByteArrayInputStream productsToExcel(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook ();) {
            Sheet sheet = workbook.createSheet ( "product" );
			// Header cell
            Row headerRow = sheet.createRow ( 0 );
            for (int col = 0; col < HEADERs.size (); col++) {
                Cell cell = headerRow.createCell ( col );
                cell.setCellValue ( HEADERs.get ( col ) );
            }
            AtomicInteger rowIdx = new AtomicInteger ( 1 );
            products.forEach ( product -> {
                Row row = sheet.createRow ( rowIdx.getAndIncrement () );
                row.createCell ( 0 ).setCellValue ( product.getpId () );
                row.createCell ( 1 ).setCellValue ( product.getpName () );
                row.createCell ( 2 ).setCellValue ( product.getPrice () );
                row.createCell ( 3 ).setCellValue ( product.getDetails () );
                row.createCell ( 4 ).setCellValue ( product.getThumbnail () );
                row.createCell ( 5 ).setCellValue ( product.getQty () );
                row.createCell ( 6 ).setCellValue ( product.getCategory ().getCategory () );
            } );
			ByteArrayOutputStream out = new ByteArrayOutputStream ();
            workbook.write ( out );
            return new ByteArrayInputStream ( out.toByteArray () );
        } catch (IOException e) {
            e.printStackTrace ();
            throw new RuntimeException ( "fail to import data to Excel file: " + e.getMessage () );
        }
    }
    private void writeTableHeader(PdfPTable table) {
        Font font = FontFactory.getFont ( FontFactory.HELVETICA );
        font.setColor ( Color.WHITE );
        HEADERs.forEach ( headerText -> {
            PdfPCell cell = new PdfPCell ();
            cell.setBackgroundColor ( Color.BLUE );
            cell.setPadding ( 5 );
            cell.setPhrase ( new Phrase ( headerText, font ) );
            table.addCell ( cell );
        } );
    }
    private void writeTableData(PdfPTable table) {
        products.forEach ( user -> {
            table.addCell ( String.valueOf ( user.getpId () ) );
            table.addCell ( user.getpName () );
            table.addCell ( user.getCategory ().getCategory () );
            table.addCell ( user.getDetails () );
            table.addCell ( String.valueOf ( user.getQty () ) );
            table.addCell ( String.valueOf ( user.getPrice () ) );
        } );
    }
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document ( PageSize.A4 );
        PdfWriter.getInstance ( document, response.getOutputStream () );
        document.open ();
        Font font = FontFactory.getFont ( FontFactory.HELVETICA_BOLD );
        font.setSize ( 18 );
        font.setColor ( Color.BLUE );
        Paragraph p = new Paragraph ( "List of Products", font );
        p.setAlignment ( Paragraph.ALIGN_CENTER );
        document.add ( p );
        PdfPTable table = new PdfPTable ( 6 );
        table.setWidthPercentage ( 100f );
        table.setWidths ( new float[]{1.5f, 3.5f, 3.0f, 3.0f, 1.5f, 1.5f} );
        table.setSpacingBefore ( 10 );
        writeTableHeader ( table );
        writeTableData ( table );
        document.add ( table );
        document.close ();
    }
}
