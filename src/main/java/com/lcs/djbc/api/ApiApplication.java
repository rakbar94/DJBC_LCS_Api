package com.lcs.djbc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcs.djbc.api.models.Data;
import com.lcs.djbc.api.models.Models;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private String name = "LCS_DJBC_";

    @Bean
    CommandLineRunner run() {
        return args -> {
            String filename = name + sdf.format(new Date());
            try {
                File file1 = new File("D:\\Project\\api\\" + filename);
                file1.renameTo(new File("D:\\Project\\api\\New folder\\Sukses\\baru\\" + filename + ".xlsx"));
                File file = new File("D:\\Project\\api\\New folder\\Sukses\\baru\\" + filename + ".xlsx");
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    XSSFWorkbook workbook = new XSSFWorkbook(fis);
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    for (int i = 0; i < sheet.getPhysicalNumberOfRows() - 1; i++) {
                        XSSFRow row = sheet.getRow(i);
                        if (Objects.nonNull(row)) {
                            String kode = UUID.randomUUID().toString();
                            Cell cellNOMOR_NPWP = row.getCell(0);
                            Cell cellNAMA_PERUSAHAAN = row.getCell(1);
                            Cell cellNOMOR_INVOICE = row.getCell(2);
                            Cell cellTGL_INVOICE = row.getCell(3);
                            Cell cellJENIS_VALUTA_INVOICE = row.getCell(4);
                            Cell cellNILAI_INVOICE = row.getCell(5);
                            Cell cellLCS_NEGARA_MITRA = row.getCell(6);
                            //=================================================
                            String Cell = cellNOMOR_NPWP.getStringCellValue();
                            String Cell1 = cellNAMA_PERUSAHAAN.getStringCellValue();
                            String Cell2 = cellNOMOR_INVOICE.getStringCellValue();
                            String Cell3 = cellTGL_INVOICE.getStringCellValue();
                            String Cell4 = cellJENIS_VALUTA_INVOICE.getStringCellValue();
                            String Cell5 = cellNILAI_INVOICE.getStringCellValue();
                            String Cell6 = cellLCS_NEGARA_MITRA.getStringCellValue();
                            //===================================================
                            Data data = new Data();
                            data.setIdPerusahaan(Cell);
                            data.setNamaPerusahaan(Cell1);
                            data.setNomorInvoice(Cell2);
                            data.setTanggalInvoice(Cell3);
                            data.setKodeValuta(Cell4);
                            data.setNilaiTransaksi(Cell5);
                            data.setNipRekam("SYSTEM");
                            data.setLCS_NEGARA_MITRA(Cell6);
                            Models model = new Models();
                            model.setIdPerusahaan(Cell);
                            model.setStatusResponse("0");
                            model.setDescription("SUKSES");
                            model.setData(data);
                            //=============================
                            ObjectMapper mapper = new ObjectMapper();
                            String result = mapper.writeValueAsString(model);
                            //=============================
                            FileOutputStream fout = new FileOutputStream("D:\\Project\\api\\New folder\\Sukses\\" + kode + ".txt");
                            String df = result;
                            byte er[] = df.getBytes();//converting string into byte array
                            fout.write(er);
                            fout.close();
                            //=============================
                            //file1.delete();
                            //file.delete();
                            //=============================
                            System.out.println("BERHASIL");
                        }
                    }
                } else {
                    System.out.println("file tidak ditemukan");
                }


            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
