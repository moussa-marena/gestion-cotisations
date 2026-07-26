package com.association.util;

import com.association.model.Cotisation;
import com.association.model.Membre;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExportPDFUtil {

    // Couleurs SunuAssos
    private static final BaseColor BLEU_PRIMARY  =
        new BaseColor(13, 59, 102);   // #0D3B66
    private static final BaseColor VERT_SECONDARY =
        new BaseColor(27, 138, 90);   // #1B8A5A
    private static final BaseColor GRIS_LIGHT    =
        new BaseColor(237, 242, 244); // #EDF2F4
    private static final BaseColor BLANC         =
        BaseColor.WHITE;

    // Fonts
    private static final Font FONT_TITRE =
        new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BLANC);
    private static final Font FONT_SOUS_TITRE =
        new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BLEU_PRIMARY);
    private static final Font FONT_HEADER_TABLE =
        new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BLANC);
    private static final Font FONT_CELL =
        new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL,
            new BaseColor(43, 45, 66)); // #2B2D42
    private static final Font FONT_TOTAL =
        new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, VERT_SECONDARY);

    // ===== EXPORT LISTE DES MEMBRES =====
    public static void exportMembres(List<Membre> membres,
                                     HttpServletResponse response)
            throws IOException, DocumentException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
            "attachment; filename=liste_membres_sunuassos.pdf");

        Document doc = new Document(PageSize.A4.rotate(), 20, 20, 40, 30);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        // En-tête
        ajouterEnTete(doc, "Liste des Membres");

        // Infos
        doc.add(new Paragraph("Total membres : " + membres.size(),
            FONT_SOUS_TITRE));
        doc.add(Chunk.NEWLINE);

        // Tableau
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{1f, 2.5f, 3f, 2f, 1.5f, 1.5f});

        // Headers
        ajouterHeaderCellule(table, "#");
        ajouterHeaderCellule(table, "Nom complet");
        ajouterHeaderCellule(table, "Email");
        ajouterHeaderCellule(table, "Téléphone");
        ajouterHeaderCellule(table, "Rôle");
        ajouterHeaderCellule(table, "Statut");

        // Données
        int i = 1;
        for (Membre m : membres) {
            boolean pair = (i % 2 == 0);
            ajouterCellule(table, String.valueOf(i++), pair);
            ajouterCellule(table, m.getNomComplet(), pair);
            ajouterCellule(table, m.getEmail(), pair);
            ajouterCellule(table,
                m.getTelephone() != null ? m.getTelephone() : "—", pair);
            ajouterCellule(table, m.getRole().name(), pair);
            ajouterCellule(table, m.getStatut().name(), pair);
        }

        doc.add(table);
        ajouterPiedDePage(doc);
        doc.close();
    }

    // ===== EXPORT LISTE DES COTISATIONS =====
    public static void exportCotisations(List<Cotisation> cotisations,
                                          String nomMois, int annee,
                                          double totalEncaisse,
                                          HttpServletResponse response)
            throws IOException, DocumentException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
            "attachment; filename=cotisations_" + nomMois + "_" +
            annee + ".pdf");

        Document doc = new Document(PageSize.A4, 20, 20, 40, 30);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();

        ajouterEnTete(doc, "Cotisations — " + nomMois + " " + annee);

        // Résumé
        doc.add(new Paragraph(
            "Nombre de paiements : " + cotisations.size(),
            FONT_SOUS_TITRE));
        doc.add(new Paragraph(
            "Total encaissé : " + totalEncaisse + " FCFA",
            FONT_TOTAL));
        doc.add(Chunk.NEWLINE);

        // Tableau
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setWidths(new float[]{2.5f, 1.5f, 2f, 2f, 1.5f});

        ajouterHeaderCellule(table, "Membre");
        ajouterHeaderCellule(table, "Période");
        ajouterHeaderCellule(table, "Montant (FCFA)");
        ajouterHeaderCellule(table, "Mode paiement");
        ajouterHeaderCellule(table, "Date paiement");

        int i = 1;
        for (Cotisation c : cotisations) {
            boolean pair = (i++ % 2 == 0);
            ajouterCellule(table,
                c.getMembre().getNomComplet(), pair);
            ajouterCellule(table, c.getPeriode(), pair);
            ajouterCellule(table,
                String.valueOf(c.getMontant()), pair);
            ajouterCellule(table,
                c.getModePaiement().name(), pair);
            ajouterCellule(table,
                c.getDatePaiement() != null
                    ? c.getDatePaiement().toString() : "—", pair);
        }

        doc.add(table);
        ajouterPiedDePage(doc);
        doc.close();
    }

    // ===== MÉTHODES UTILITAIRES =====

    private static void ajouterEnTete(Document doc, String titre)
            throws DocumentException {

        // Bande de titre bleue
        PdfPTable header = new PdfPTable(1);
        header.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell(new Phrase(
            "SunuAssos — " + titre, FONT_TITRE));
        cell.setBackgroundColor(BLEU_PRIMARY);
        cell.setPadding(14);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(cell);
        doc.add(header);
        doc.add(Chunk.NEWLINE);
    }

    private static void ajouterHeaderCellule(PdfPTable table, String texte) {
        PdfPCell cell = new PdfPCell(new Phrase(texte, FONT_HEADER_TABLE));
        cell.setBackgroundColor(BLEU_PRIMARY);
        cell.setPadding(8);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderColor(BLANC);
        table.addCell(cell);
    }

    private static void ajouterCellule(PdfPTable table,
                                        String texte, boolean pair) {
        PdfPCell cell = new PdfPCell(new Phrase(texte, FONT_CELL));
        cell.setBackgroundColor(pair ? GRIS_LIGHT : BLANC);
        cell.setPadding(6);
        cell.setBorderColor(new BaseColor(200, 200, 200));
        table.addCell(cell);
    }

    private static void ajouterPiedDePage(Document doc)
            throws DocumentException {
        doc.add(Chunk.NEWLINE);
        Paragraph pied = new Paragraph(
            "Document généré par SunuAssos — Notre association, notre force",
            new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC,
                BaseColor.GRAY));
        pied.setAlignment(Element.ALIGN_CENTER);
        doc.add(pied);
    }
}