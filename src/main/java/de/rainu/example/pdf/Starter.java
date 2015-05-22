package de.rainu.example.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.lowagie.text.DocumentException;

public class Starter {

	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		PDFGenerator gen = new PDFGenerator("templates/", ".html");

		Map<String, Object> model = new HashMap<>();
		model.put("message", "Hallo Welt!");

		gen.generate(new File("/tmp/test.pdf"), "pdf_template", model);
	}

}
