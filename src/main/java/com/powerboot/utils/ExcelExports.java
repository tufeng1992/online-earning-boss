package com.powerboot.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

public class ExcelExports {

    private final static Logger log = LoggerFactory.getLogger(ExcelExports.class);
    private static String sheetName = "SHEET1";
    private static String COMMA = ",";
    private static String SPACE = " ";

    public static void webExportExcel(SimpleExcelModel excelModel, HttpServletResponse response) {
        try {
            write(excelModel, response);
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException( "数据导出异常");
        }
    }

    public static <E> void webExportCsv(String fileName, List<E> data, HttpServletResponse response)
        throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        setCsvResponseHeader(fileName, response);
        try (Writer wr = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv csv = new StatefulBeanToCsvBuilder<E>(wr).build();
            csv.write(data);
        }
    }

    private static void write(SimpleExcelModel excelModel, HttpServletResponse response) throws IOException {
        if (SimpleExcelModel.EXCEL_TYPE.equals(excelModel.getType())) {
            writeAsExcel(excelModel, response);
        } else if (SimpleExcelModel.CSV_TYPE.equals(excelModel.getType())) {
            writeAsCsv(excelModel, response);
        }
    }

    public static void writeWorkbook(Workbook workbook, String fileName, HttpServletResponse response)
        throws IOException {
        setResponseHeader(fileName, SimpleExcelModel.EXCEL_TYPE, response);
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.warn("");
            throw e;
        }
    }

    private static void writeAsExcel(SimpleExcelModel excelModel, HttpServletResponse response) throws IOException {
        setResponseHeader(excelModel.getFileName(), excelModel.getType(), response);
        Workbook workbook = createWorkbook(excelModel);
        workbook.write(response.getOutputStream());
    }

    private static void writeAsCsv(SimpleExcelModel model, HttpServletResponse response) throws IOException {
        setCsvResponseHeader(model.getFileName(), response);
        List<List<String>> rows = new ArrayList<>();
        rows.add(getHeaderRow(model));
        rows.addAll(getDataRows(model));
        String str = buildCsvData(rows);
        //设置bom
        response.getOutputStream().write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
        response.getOutputStream().write(str.getBytes("UTF-8"));
        response.getOutputStream().flush();
    }

    public static byte[] writeAsCsv(SimpleExcelModel model) throws IOException {
        List<List<String>> rows = new ArrayList<>();
        rows.add(getHeaderRow(model));
        rows.addAll(getDataRows(model));
        String str = buildCsvData(rows);
        return str.getBytes("UTF-8");
    }

    private static List<String> getHeaderRow(SimpleExcelModel model) {
        List<String> fields = model.getExportFields();
        Map<String, String> headerMap = model.getFieldHeaderMap();
        List<String> headerNames = new ArrayList<>();
        for (String field : fields) {
            if (MapUtils.isNotEmpty(headerMap)) {
                if (headerMap.containsKey(field)) {
                    headerNames.add(headerMap.get(field));
                }
            } else {
                headerNames.add(field);
            }
        }
        return headerNames;
    }

    private static List<List<String>> getDataRows(SimpleExcelModel model) {
        List rows = model.getRowList();
        List<String> fields = model.getExportFields();
        List<List<String>> result = new ArrayList<>();
        for (Object dataRow : rows) {
            List<String> columns = Lists.newArrayListWithExpectedSize(fields.size());
            for (String field : fields) {
                Object obj = reflectiveGetFieldValue(dataRow, field);
                columns.add(getCsvValue(getValue(obj, model.excelOption)));
            }
            result.add(columns);
        }
        return result;
    }

    private static String getCsvValue(String value) {
        if (value.contains(COMMA)) {
            value = value.replace(COMMA, COMMA + SPACE);
            value = "\"" + value + "\"";
        }
        return value;
    }

    private static String buildCsvData(List<List<String>> rows) {
        StringBuilder sb = new StringBuilder(100);
        for (List<String> row : rows) {
            sb.append(Joiner.on(",").join(row));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Workbook createEmptyWorkbookWithHeader(SimpleExcelModel excelModel) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        setHeader(sheet, excelModel.getExportFields(), excelModel.getFieldHeaderMap(), excelModel.excelOption);
        return workbook;
    }

    private static Workbook createWorkbook(SimpleExcelModel excelModel) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        int startRowNum = 0;
        if (CollectionUtils.isNotEmpty(excelModel.getExportFields())) {
            setHeader(sheet, excelModel.getExportFields(), excelModel.getFieldHeaderMap(), excelModel.excelOption);
            startRowNum++;
        }
        setRows(sheet, excelModel.getExportFields(), excelModel.getRowList(), startRowNum, excelModel.excelOption);
        return workbook;
    }

    private static void setResponseHeader(String fileName, String type, HttpServletResponse response) {
        if (StringUtils.isBlank(fileName)) {
            fileName = "export";
        }
        try {
            response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("export error:" + e.getMessage());
        }
        response.setContentType("application/msexcel");
    }

    private static void setCsvResponseHeader(String fileName, HttpServletResponse response)
        throws UnsupportedEncodingException {
        if (StringUtils.isBlank(fileName)) {
            fileName = "export";
        }
        response
            .setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF8") + ".csv");
        response.setContentType("text/csv;charset=utf-8");
    }

    private static void setHeader(Sheet sheet, List<String> exportFields, Map<String, String> fieldHeaderMap,
        ExcelOption excelOption) {
        if (fieldHeaderMap == null) {
            fieldHeaderMap = new HashMap<>();
        }
        Row headerRow = sheet.createRow(0);
        int index = 0;
        for (String field : exportFields) {
            String headerName = fieldHeaderMap.get(field);
            Cell cell = headerRow.createCell(index++);
            if (headerName != null) {
                setCellValue(cell, headerName, excelOption);
            } else {
                setCellValue(cell, field, excelOption);
            }
        }
    }

    private static void setRows(Sheet sheet, List<String> exportFields, List<Object> dataRows, int startRowNum,
        ExcelOption excelOption) {
        int rowIndex = startRowNum;
        for (Object dataRow : dataRows) {
            Row row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            for (String field : exportFields) {
                Object value = reflectiveGetFieldValue(dataRow, field);
                Cell cell = row.createCell(columnIndex++);
                setCellValue(cell, value, excelOption);
            }
        }
    }

    private static Object reflectiveGetFieldValue(Object obj, String fieldName) {
        Class clazz = obj.getClass();
        try {
            Method getterMethod = null;
            Field field = ReflectionUtils.findField(clazz, fieldName);
            if ("boolean".equals(field.getType().getName())) {
                getterMethod = ReflectionUtils.findMethod(clazz, booleanGetterMethodName(fieldName));
            } else {
                getterMethod = ReflectionUtils.findMethod(clazz, commonGetterMethodName(fieldName));
            }
            return getterMethod.invoke(obj);
        } catch (Exception e) {
            log.error("excel生成异常", e);
            throw new RuntimeException("excel data error");
        }
    }

    private static String booleanGetterMethodName(String fieldName) {
        return "is" + firstIndexToUpper(fieldName);
    }

    private static String commonGetterMethodName(String fieldName) {
        return "get" + firstIndexToUpper(fieldName);
    }

    private static String firstIndexToUpper(String str) {
        if (str == null || str.length() <= 0) {
            return "";
        }
        String first = str.charAt(0) + "";
        return str.replaceFirst(first, first.toUpperCase());
    }

    private static void setCellValue(Cell cell, Object obj, ExcelOption excelOption) {
        String value = getValue(obj, excelOption);
        // 过滤 null 值转换为 空格
        if (Objects.isNull(value) || "null".equals(value)) {
            cell.setCellValue(StringUtils.EMPTY);
        } else {
            cell.setCellValue(value);
        }
        cell.setCellType(CellType.STRING);
    }

    /**
     * 获取值(针对 null 值处理为 空格)
     *
     */
    private static String getValue(Object obj, ExcelOption excelOption) {
        if (Objects.isNull(obj)) {
            return StringUtils.EMPTY;
        }
        if (obj instanceof String) {
            String value = (String) obj;
            return value;
        } else if (obj instanceof Date) {
            return formatDate((Date) obj, excelOption);
        } else {
            return String.valueOf(obj);
        }
    }

    private static String formatDate(Date date, ExcelOption excelOption) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        if (Objects.nonNull(excelOption) && Objects.nonNull(excelOption.getDatePattern())) {
            pattern = excelOption.getDatePattern();
        }
        return DateUtils.format(date, pattern);
    }

    public static class SimpleExcelModel<T> {

        public static final String CSV_TYPE = "csv";
        public static final String EXCEL_TYPE = "excel";

        private String fileName;
        /**
         * type: csv/excel
         */
        private String type = "csv";
        private Map<String, String> fieldHeaderMap;
        private List<String> exportFields;
        private List<T> rowList;

        public void setExcelOption(ExcelOption excelOption) {
            this.excelOption = excelOption;
        }

        private ExcelOption excelOption;

        public Map<String, String> getFieldHeaderMap() {
            return fieldHeaderMap;
        }

        public void setFieldHeaderMap(Map<String, String> fieldHeaderMap) {
            this.fieldHeaderMap = fieldHeaderMap;
        }

        public List<String> getExportFields() {
            return exportFields;
        }

        public void setExportFields(List<String> exportFields) {
            this.exportFields = exportFields;
        }

        public List<T> addRow(T row) {
            if (rowList == null) {
                rowList = new ArrayList<>();
            }
            rowList.add(row);
            return rowList;
        }

        public List<T> getRowList() {
            return rowList;
        }

        public void setRowList(List<T> rowList) {
            this.rowList = rowList;
        }

        public Map<String, String> putHeaderName(String fieldName, String headerName) {
            if (fieldHeaderMap == null) {
                fieldHeaderMap = new HashMap<>();
            }
            fieldHeaderMap.put(fieldName, headerName);
            return fieldHeaderMap;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
