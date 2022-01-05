package com.pdfcreator;

import com.itextpdf.html2pdf.HtmlConverter;
import com.lowagie.text.DocumentException;
import org.jsoup.Jsoup;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.URISyntaxException;

@Path("/hello")
public class ExampleResource {

    private static final String ORIG = "receipt/index.html";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws DocumentException, IOException, URISyntaxException {

//        String content = parseThymeleafTemplate();

        String content = getHTMLString();

        generatePdfFromHtml(content);


        return "Hello RESTEasy";
    }

    private String getHTMLString() throws IOException, URISyntaxException {
        FileResourcesUtils app = new FileResourcesUtils();
        return Jsoup.parse(app.getFileFromResource(ORIG),
                "UTF-8").toString();
    }

//    private String parseThymeleafTemplate() {
//        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//
//        TemplateEngine templateEngine = new TemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver);
//
//        Context context = new Context();
//
//        return templateEngine.process("thymeleaf_template", context);
//    }

    private byte[] getBytesPDF(String content) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(content, outputStream);

        byte[] bytes = outputStream.toByteArray();
        return java.util.Base64.getEncoder().encode(bytes);
    }

    public void generatePdfFromHtml(String html) throws DocumentException, IOException {
        String outputFolder = System.getProperty("user.home") + File.separator + "thymeleaf.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }
}