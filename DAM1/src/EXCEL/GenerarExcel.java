package EXCEL;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;

public class GenerarExcel {

    public void GenerarExcel(String[][] entrada, String ruta) throws IOException, WriteException {

        try {
            WorkbookSettings conf = new WorkbookSettings();
            conf.setEncoding("ISO-8859-1");
            WritableWorkbook woorkbook = Workbook.createWorkbook(new File(ruta), conf);
            //Hoja
            WritableSheet sheet = woorkbook.createSheet("Resultado", 0);
            //Estilo
            WritableFont h = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD);
            WritableCellFormat hFormat = new WritableCellFormat(h);
            WritableFont g = new WritableFont(WritableFont.TAHOMA, 16, WritableFont.BOLD);
            WritableCellFormat gFormat = new WritableCellFormat(g);
            //hFormat.setOrientation(Orientation.VERTICAL);
            hFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
            gFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM);
            gFormat.setBackground(jxl.format.Colour.GRAY_25);

            //Recorrer las celdas
            for (int i = 0; i < entrada.length; i++) {
                for (int j = 0; j < entrada[i].length; j++) {
                    try {
                        if(i==0){
                            
                            sheet.addCell(new jxl.write.Label(j + 1, i + 1, entrada[i][j], gFormat));
                        }else{
                        sheet.addCell(new jxl.write.Label(j + 1, i + 1, entrada[i][j], hFormat));
                        }
                    } catch (WriteException ex) {
                        Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            woorkbook.write();
            try {
                woorkbook.close();
            } catch (WriteException ex) {
                Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException e) {
            System.out.println("Error al crear archvio: +e");
        }
    }

}
