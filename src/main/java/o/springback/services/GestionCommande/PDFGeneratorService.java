package o.springback.services.GestionCommande;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import o.springback.entities.GestionCommande.Order;
import o.springback.entities.GestionCommande.OrderProduct;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
public class PDFGeneratorService {

    public byte[] generateOrderInvoice(Order order) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        // Style
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 10);

        // En-tête
        Paragraph title = new Paragraph("FACTURE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Informations de la commande
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingBefore(20f);
        infoTable.setSpacingAfter(20f);

        // Client et date
        infoTable.addCell(createCell("N° Commande", String.valueOf(order.getId()), headerFont));
        infoTable.addCell(createCell("Date", order.getDateCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont));
        infoTable.addCell(createCell("Statut", order.getStatus(), normalFont));
        infoTable.addCell(createCell("Total", String.format("%.3f TND", order.getTotalOrderPrice()), headerFont));

        document.add(infoTable);

        // Détails des produits
        Paragraph productsTitle = new Paragraph("Détails des produits", headerFont);
        productsTitle.setSpacingBefore(20f);
        document.add(productsTitle);

        PdfPTable productsTable = new PdfPTable(4);
        productsTable.setWidthPercentage(100);
        productsTable.setSpacingBefore(10f);

        // En-têtes du tableau des produits
        Stream.of("Produit", "Prix unitaire", "Quantité", "Total").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell(new Phrase(columnTitle, headerFont));
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            productsTable.addCell(header);
        });

        // Lignes des produits
        for (OrderProduct op : order.getOrderProducts()) {
            productsTable.addCell(createCell(op.getProduct().getNom(), normalFont));
            productsTable.addCell(createCell(String.format("%.3f TND", op.getProduct().getPrix()), normalFont));
            productsTable.addCell(createCell(String.valueOf(op.getQuantity()), normalFont));
            productsTable.addCell(createCell(String.format("%.3f TND", op.getTotalPrice()), normalFont));
        }

        document.add(productsTable);

        // Total
        Paragraph total = new Paragraph(
                String.format("\nTotal général: %.3f TND ", order.getTotalOrderPrice()),
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
        total.setAlignment(Element.ALIGN_RIGHT);
        total.setSpacingBefore(20f);
        document.add(total);

        // Livraison si existe
        if (order.getLivraison() != null) {
            Paragraph livraison = new Paragraph(
                    String.format("\nLivraison: %s", order.getLivraison().getAdresse()),
                    normalFont);
            document.add(livraison);
        }

        document.close();
        return baos.toByteArray();
    }

    private PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }

    private PdfPCell createCell(String label, String value, Font font) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        PdfPCell labelCell = new PdfPCell(new Phrase(label + ":", font));
        labelCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(labelCell);
        table.addCell(valueCell);

        PdfPCell container = new PdfPCell(table);
        container.setBorder(Rectangle.NO_BORDER);
        container.setPadding(2);
        return container;
    }
}