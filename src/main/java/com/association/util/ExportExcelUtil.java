package com.association.util;

import com.association.model.Cotisation;
import com.association.model.Membre;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExportExcelUtil {

    // ===== EXPORT MEMBRES =====
    public static void exportMembres(List<Membre> membres,
                                      HttpServletResponse response)
            throws IOException {

        response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
            "attachment; filename=liste_membres_sunuassos.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Membres");

        // Styles
        CellStyle styleHeader = creerStyleHeader(workbook);
        CellStyle styleTitre  = creerStyleTitre(workbook);
        CellStyle stylePair   = creerStylePair(workbook);
        CellStyle styleImpair = creerStyleImpair(workbook);

        // Titre
        Row rowTitre = sheet.createRow(0);
        rowTitre.setHeight((short) 800);
        Cell cellTitre = rowTitre.createCell(0);
        cellTitre.setCellValue("SunuAssos — Liste des Membres");
        cellTitre.setCellStyle(styleTitre);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        // Ligne vide
        sheet.createRow(1);

        // Headers
        String[] colonnes = {"#", "Nom", "Prénom",
                             "Email", "Téléphone", "Statut"};
        Row rowHeader = sheet.createRow(2);
        rowHeader.setHeight((short) 500);
        for (int i = 0; i < colonnes.length; i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(colonnes[i]);
            cell.setCellStyle(styleHeader);
        }

        // Données
        int rowNum = 3;
        int num = 1;
        for (Membre m : membres) {
            Row row = sheet.createRow(rowNum);
            CellStyle style = (rowNum % 2 == 0) ? stylePair : styleImpair;

            creerCellule(row, 0, String.valueOf(num++), style);
            creerCellule(row, 1, m.getNom(), style);
            creerCellule(row, 2, m.getPrenom(), style);
            creerCellule(row, 3, m.getEmail(), style);
            creerCellule(row, 4,
                m.getTelephone() != null ? m.getTelephone() : "—", style);
            creerCellule(row, 5, m.getStatut().name(), style);

            rowNum++;
        }

        // Total
        Row rowTotal = sheet.createRow(rowNum + 1);
        Cell cellTotal = rowTotal.createCell(0);
        cellTotal.setCellValue("Total : " + membres.size() + " membres");
        CellStyle styleTotal = creerStyleTotal(workbook);
        cellTotal.setCellStyle(styleTotal);
        sheet.addMergedRegion(
            new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 5));

        // Largeurs colonnes
        sheet.setColumnWidth(0, 1500);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 8000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 3000);

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // ===== EXPORT COTISATIONS =====
    public static void exportCotisations(List<Cotisation> cotisations,
                                          String nomMois, int annee,
                                          double totalEncaisse,
                                          HttpServletResponse response)
            throws IOException {

        response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
            "attachment; filename=cotisations_" + nomMois +
            "_" + annee + ".xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet =
            workbook.createSheet("Cotisations " + nomMois + " " + annee);

        CellStyle styleHeader = creerStyleHeader(workbook);
        CellStyle styleTitre  = creerStyleTitre(workbook);
        CellStyle stylePair   = creerStylePair(workbook);
        CellStyle styleImpair = creerStyleImpair(workbook);
        CellStyle styleTotal  = creerStyleTotal(workbook);

        // Titre
        Row rowTitre = sheet.createRow(0);
        rowTitre.setHeight((short) 800);
        Cell cellTitre = rowTitre.createCell(0);
        cellTitre.setCellValue(
            "SunuAssos — Cotisations " + nomMois + " " + annee);
        cellTitre.setCellStyle(styleTitre);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        // Résumé
        Row rowResume = sheet.createRow(1);
        Cell cellResume = rowResume.createCell(0);
        cellResume.setCellValue(
            "Total encaissé : " + totalEncaisse + " FCFA");
        CellStyle styleResume = creerStyleResume(workbook);
        cellResume.setCellStyle(styleResume);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));

        // Headers
        String[] colonnes = {"Membre", "Période",
                             "Montant (FCFA)", "Mode paiement", "Date"};
        Row rowHeader = sheet.createRow(2);
        rowHeader.setHeight((short) 500);
        for (int i = 0; i < colonnes.length; i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(colonnes[i]);
            cell.setCellStyle(styleHeader);
        }

        // Données
        int rowNum = 3;
        for (Cotisation c : cotisations) {
            Row row = sheet.createRow(rowNum);
            CellStyle style = (rowNum % 2 == 0) ? stylePair : styleImpair;

            creerCellule(row, 0, c.getMembre().getNomComplet(), style);
            creerCellule(row, 1, c.getPeriode(), style);
            creerCellule(row, 2, String.valueOf(c.getMontant()), style);
            creerCellule(row, 3, c.getModePaiement().name(), style);
            creerCellule(row, 4,
                c.getDatePaiement() != null
                    ? c.getDatePaiement().toString() : "—", style);
            rowNum++;
        }

        // Total
        Row rowTotal = sheet.createRow(rowNum + 1);
        Cell cellTotal = rowTotal.createCell(0);
        cellTotal.setCellValue("Total encaissé : " +
            totalEncaisse + " FCFA");
        cellTotal.setCellStyle(styleTotal);
        sheet.addMergedRegion(
            new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 4));

        // Largeurs
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // ===== STYLES =====
    private static CellStyle creerStyleTitre(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setColor(new XSSFColor(
            new byte[]{(byte)255, (byte)255, (byte)255}, null));
        style.setFont(font);
        style.setFillForegroundColor(
            new XSSFColor(new byte[]{13, 59, 102}, null));  // #0D3B66
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private static CellStyle creerStyleHeader(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(new XSSFColor(
            new byte[]{(byte)255, (byte)255, (byte)255}, null));
        style.setFont(font);
        style.setFillForegroundColor(
            new XSSFColor(new byte[]{13, 59, 102}, null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static CellStyle creerStylePair(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(
            new XSSFColor(new byte[]{(byte)237, (byte)242, (byte)244}, null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static CellStyle creerStyleImpair(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(
            new XSSFColor(new byte[]{(byte)255, (byte)255, (byte)255}, null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static CellStyle creerStyleTotal(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        font.setColor(new XSSFColor(
            new byte[]{(byte)255, (byte)255, (byte)255}, null));
        style.setFont(font);
        style.setFillForegroundColor(
            new XSSFColor(new byte[]{27, (byte)138, 90}, null)); // #1B8A5A
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private static CellStyle creerStyleResume(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setColor(new XSSFColor(
            new byte[]{27, (byte)138, 90}, null));
        style.setFont(font);
        style.setFillForegroundColor(
            new XSSFColor(new byte[]{(byte)212, (byte)237, (byte)218}, null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private static void creerCellule(Row row, int col,
                                      String valeur, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(valeur);
        cell.setCellStyle(style);
    }
}