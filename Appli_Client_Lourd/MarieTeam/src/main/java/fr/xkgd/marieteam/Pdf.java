package fr.xkgd.marieteam;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import fr.xkgd.marieteam.customexception.BadImageFileFormatException;
import fr.xkgd.marieteam.customexception.BadPdfFileFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static fr.xkgd.marieteam.Utils.LS;

/**
 * Classe permettant de créer un PDF
 */
@SuppressWarnings("SpellCheckingInspection")
public class Pdf {
    private Document document;

    /**
     * Constructeur permettant de créer un PDF
     * @param pathPdf Chemin du PDF
     */
    public Pdf(String pathPdf) {
        if(!isPdfExtension(pathPdf)) {
            throw new BadPdfFileFormatException("Le fichier n'est pas un PDF !");
        }
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pathPdf));
            document.open();
            addTitleInPdf();
        } catch (FileNotFoundException ex) {
            System.out.println("Erreur : Le fichier n'existe pas !");
        } catch (DocumentException | BadPdfFileFormatException ex) {
            System.out.println("Erreur : Impossible d'ouvrir le PDF !");
        }
    }

    /**
     * Méthode permettant d'ajouter un titre au PDF.
     */
    private void addTitleInPdf() {
        try {
            Font font = new Font();
            font.setSize(18);
            font.setStyle(Font.BOLD);
            Paragraph paragraph = new Paragraph("Bateaux voyageurs" + LS.repeat(2), font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
        } catch (DocumentException ex) {
            System.out.println("Erreur : Impossible d'ajouter le titre !");
        }
    }

    /**
     * Méthode permettant d'ajouter du texte au PDF
     */
    public void addTextInPdf(String text) {
        try {
            Font font = new Font();
            font.setSize(12);
            Paragraph paragraph = new Paragraph(text, font);
            document.add(paragraph);
        } catch (DocumentException ex) {
            System.out.println("Erreur : Impossible d'ajouter le texte !");
        }
    }

    /**
     * Méthode permettant d'ajouter une image au PDF
     */
    public void addImageInPdf(String pathImage) {
        if (!isJpgExtension(pathImage)) throw new BadImageFileFormatException("Le fichier n'est pas un JPG !");
        try {
            File file = new File(pathImage);
            if(!file.exists()) throw new NullPointerException("L'image n'existe pas !");
            Image image = Image.getInstance(pathImage);
            image.scaleToFit(250, 250);
            document.add(image);
        } catch (DocumentException | IOException ex) {
            System.out.println("Erreur : Impossible d'ajouter l'image !");
        } catch (NullPointerException | BadImageFileFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Méthode permettant de fermer le PDF
     */
    public void closePdf() {
        document.close();
    }

    /**
     * Méthode permettant d'ajouter une nouvelle page au PDF
     */
    public void newPageToPdf() {
        document.newPage();
    }

    private boolean isPdfExtension(String pdfFile) {
        return pdfFile.endsWith(".pdf");
    }

    private boolean isJpgExtension(String jpgFile) {
        return jpgFile.endsWith(".jpg");
    }
}
