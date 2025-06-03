package com.yang.jxc.controller;

import com.yang.jxc.domain.entity.Purchase;
import com.yang.jxc.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@Api(tags = "PdfController", description = "所有的pdf导出功能")
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PurchaseService purchaseService;

    // TODO 该功能后续完善
    @ApiOperation("采购pdf导出")
    @GetMapping(value = "/pdfToPurchase")
    public void pdfCreate(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        List<Purchase> PurchaseList = purchaseService.list();

        String fileName = "userinf" + ".xls";
        int rowNum = 1;
        String[] headers = {"学号", "姓名", "身份类型", "登录密码"};
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }


        //在表中存放查询到的数据放入对应的列
        for (Purchase purchase : PurchaseList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(purchase.getId());
            row1.createCell(1).setCellValue(purchase.getNumber());
            row1.createCell(2).setCellValue(purchase.getPurchaseUser());
            row1.createCell(3).setCellValue(purchase.getShop());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
}
