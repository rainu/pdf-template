package de.rainu.example.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

public class PDFGenerator {
	private TemplateResolver templateResolver;
	private TemplateEngine templateEngine;

	public PDFGenerator(
			final String templatePrefix,
			final String templateSuffix){

		this(templatePrefix, templateSuffix, "HTML5", "UTF-8");
	}

	public PDFGenerator(
			final String templatePrefix,
			final String templateSuffix,
			final String templateMode,
			final String templateEncoding){

		this(new ClassLoaderTemplateResolver());

		this.templateResolver.setPrefix(templatePrefix);
		this.templateResolver.setSuffix(templateSuffix);
		this.templateResolver.setTemplateMode(templateMode);
		this.templateResolver.setCharacterEncoding(templateEncoding);
	}

	public PDFGenerator(TemplateResolver templateResolver){
		this.templateResolver = templateResolver;
	}

	public PDFGenerator(TemplateEngine templateEngine){
		this.templateEngine = templateEngine;
	}

	private TemplateEngine getTemplateEngine() {
		if(templateEngine == null){
			templateEngine = new TemplateEngine();
			templateEngine.setTemplateResolver(templateResolver);
		}

		return templateEngine;
	}

	/**
	 * Process the template and generate a PDF of this rendered template.
	 *
	 * @param ouputPDF Target pdf file.
	 * @param template Source template.
	 * @param model The data for the template.
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void generate(File ouputPDF, String template, Map<String, ?> model) throws FileNotFoundException, DocumentException {
		final Context ctx = new Context();
	    ctx.setVariables(model);

	    final TemplateEngine templateEngine = getTemplateEngine();
	    String htmlContent = templateEngine.process(template, ctx);

	    ITextRenderer renderer = new ITextRenderer();

	    renderer.setDocumentFromString(htmlContent);
	    renderer.layout();
	    renderer.createPDF(new FileOutputStream(ouputPDF));
	}
}
