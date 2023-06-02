package fr.xkgd.marieteam;

import fr.xkgd.marieteam.customexception.BadPdfFileFormatException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.Assert.expectThrows;

@SuppressWarnings("SpellCheckingInspection")
@ExtendWith(MockitoExtension.class)
public class PdfTest {

    private Pdf pdf1;

    private String testPdf;

    private final String pdfPath = "src/test/resources/fr/xkgd/marieteam/pdf/";
    private final String imgPath = "src/test/resources/fr/xkgd/marieteam/image/";

    /**
     * Création d'un PDF avant chaque test
     */
    @BeforeEach
    public void setUp() {
        testPdf = "test.pdf";
        pdf1 = new Pdf(pdfPath + testPdf);
    }

    /**
     * Test de création d'un PDF avec un nom de fichier incorrect
     */
    @Test
    public void testPdfWithIncorrectName() {
        // Given
        String pdfTest = "test";
        String pdfTestJpg = "test.xml ";

        // Then
        assertAll(
                () -> expectThrows(BadPdfFileFormatException.class, () -> {
                    // When
                    pdf1 = new Pdf(pdfTest);
                }),
                () -> expectThrows(BadPdfFileFormatException.class, () -> {
                    // When
                    pdf1 = new Pdf(pdfTestJpg);
                })
        );
    }

    /**
     * Test d'ajout de texte dans un PDF
     */
    @Test
    public void testAddTextInPdf() {
        // Given
        String text = "Test";
        String blank = "   ";

        // When
        pdf1.addTextInPdf(text);
        pdf1.addTextInPdf(blank);
        pdf1.closePdf();

        // Then
        try {
            PDDocument document = PDDocument.load(new File(pdfPath + testPdf));
            PDFTextStripper stripper = new PDFTextStripper();
            String textInPdf = stripper.getText(document);
            document.close();
            assertTrue(textInPdf.contains(text));
            assertTrue(textInPdf.contains(blank));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test d'ajout d'une image dans un PDF
     */
    @Test
    public void testAddImageInPdf() {
        // Given
        String imagePath = imgPath + "bateau1.jpg";

        // When
        pdf1.addImageInPdf(imagePath);
        pdf1.closePdf();

        // Then
        try {
            PDDocument document = PDDocument.load(new File(pdfPath + testPdf));
            PDPageTree pages = document.getPages();
            PDPage page = pages.get(0);
            List<PDImageXObject> images = getImagesFromPageResources(page);

            assertEquals(1, images.size());
            PDImageXObject image = images.get(0);
            assertTrue(image.getHeight() > 0);
            assertTrue(image.getWidth() > 0);
            document.close();
        } catch (IOException e) {
            fail("Failed to open or close PDF document.");
        }
    }

    /**
     * Test d'ajout d'une image qui n'existe pas dans un PDF
     */
    @Test
    public void testAddImageInPdfWithBadPath() {
        // Given
        String imagePath = imgPath + "bateau3.jpg";

        // When
        pdf1.addImageInPdf(imagePath);
        pdf1.closePdf();

        // Then
        try {
            PDDocument document = PDDocument.load(new File(pdfPath + testPdf));
            PDPageTree pages = document.getPages();
            PDPage page = pages.get(0);
            List<PDImageXObject> images = getImagesFromPageResources(page);

            assertEquals(0, images.size());
            document.close();
        } catch (IOException e) {
            fail("Failed to open or close PDF document.");
        }
    }

    private List<PDImageXObject> getImagesFromPageResources(PDPage page) {
        List<PDImageXObject> images = new ArrayList<>();
        page.getResources().getXObjectNames().forEach(xObjectName -> {
            PDXObject xObject;
            try {
                xObject = page.getResources().getXObject(xObjectName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (xObject instanceof PDImageXObject) {
                images.add((PDImageXObject) xObject);
            }
        });
        return images;
    }
}