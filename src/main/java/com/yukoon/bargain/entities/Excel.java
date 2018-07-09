package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Excel {
    private String headTextName; //列头（标题）名
    private String propertyName; //对应字段名
    private Integer cols; //合并单元格数
    private XSSFCellStyle cellStyle;

    public Excel(String headTextName, String propertyName){
        this.headTextName = headTextName;
        this.propertyName = propertyName;
    }
    public Excel(String headTextName, String propertyName, Integer cols) {
        super();
        this.headTextName = headTextName;
        this.propertyName = propertyName;
        this.cols = cols;
    }
}
