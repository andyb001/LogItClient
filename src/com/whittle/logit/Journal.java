package com.whittle.logit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class Journal {

	public static void main2(String[] args) throws IOException {
		
		String pdfFilename = "C:\\Users\\ldev077\\workspace-july-2018\\ScratchPad\\res\\v128i03j.pdf";
		PDDocument document = PDDocument.load(new File(pdfFilename));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
		ImageIOUtil.writeImage(bim, pdfFilename + "-" + 1 + ".jpg", 300);
		
		PDFTextStripper reader = new PDFTextStripper();
		reader.setStartPage(1);
		reader.setEndPage(1);
		String pageText = reader.getText(document);
		System.out.println(pageText);
		
		document.close();
	}

	public static void main(String[] args) throws IOException {
		
		FileFilter FF = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getAbsoluteFile().getAbsolutePath().endsWith("pdf");
			}
		};
		
		File dir = new File("C:\\Users\\ldev077\\workspace-july-2018\\ScratchPad\\res");
		File[] files = dir.listFiles(FF);
		String pageText;
		PDDocument document;
		PDFTextStripper reader;
		PDDocumentInformation info;
		String title;
		String entry;
		String[] arr;
		
		for (File f : files) {

			document = PDDocument.load(f);
			reader = new PDFTextStripper();
			reader.setStartPage(1);
			reader.setEndPage(1);
			pageText = reader.getText(document);
			info = document.getDocumentInformation();
			title = info.getTitle();
			
			if (title != null && title.trim().length() > 0 && title.contains("Journal of the British Astronomical Association")) {
				System.out.println("TITLE: " + title);
			} else {
				arr = pageText.split("\n");
				if (arr[0].contains("Journal of the British Astronomical Association")) {
					System.out.println("title: " + arr[0].trim() + ". " + arr[1].trim() + " " + arr[3].trim());
				} else {
					entry = arr[0].trim() + " " + arr[1].trim() + ". " + arr[3].trim();
					entry = entry.replaceAll("Volume", "Vol.").replaceAll("Number", "No.").replaceAll("  ", " ").replaceAll("   ", " ");
					System.out.println("title: " + entry);
				}
			}
			
			
			document.close();
			System.out.println("===================================================================================");
		}
		
		
	}
	
	
}
