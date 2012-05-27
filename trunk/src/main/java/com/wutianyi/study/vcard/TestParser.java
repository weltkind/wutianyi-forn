package com.wutianyi.study.vcard;

import info.ineighborhood.cardme.engine.VCardEngine;
import info.ineighborhood.cardme.io.CompatibilityMode;
import info.ineighborhood.cardme.io.VCardWriter;
import info.ineighborhood.cardme.util.StringUtil;
import info.ineighborhood.cardme.vcard.EncodingType;
import info.ineighborhood.cardme.vcard.VCard;
import info.ineighborhood.cardme.vcard.VCardImpl;
import info.ineighborhood.cardme.vcard.errors.VCardError;
import info.ineighborhood.cardme.vcard.types.PhotoType;
import info.ineighborhood.cardme.vcard.types.media.ImageMediaType;
import info.ineighborhood.cardme.vcard.types.parameters.PhotoParameterType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class TestParser {

	private File[] vcardFiles = null;
	private VCardEngine vcardEngine = null;

	public TestParser() {
		vcardEngine = new VCardEngine();
	}

	public void setCompatibilityMode(CompatibilityMode compatMode) {
		vcardEngine.setCompatibilityMode(compatMode);
	}
	
	private File[] getFiles() {
		File f = new File("file/vcard");
		
		File[] fs = f.listFiles();
		List<File> files = new ArrayList<File>();
		for(File file : fs) {
			if(file.getName().endsWith(".vcf")) {
				files.add(file);
			}
		}
		
		return files.toArray(new File[]{});
	}

	public List importVCards() throws IOException {
		List vcards = new ArrayList();
		vcardFiles = getFiles();
		for(int i = 0; i < vcardFiles.length; i ++) {
			VCard vcard = vcardEngine.parse(vcardFiles[i]);
			vcards.add(vcard);
		}
		return vcards;
	}
	
	public static void main(String[] args) throws IOException {
		TestParser testParser = new TestParser();
		testParser.setCompatibilityMode(CompatibilityMode.RFC2426);
		List vcards = testParser.importVCards();
		VCardWriter writer = new VCardWriter();
		for(int i = 0; i < vcards.size(); i ++) {
			VCardImpl vcard = (VCardImpl) vcards.get(i);
			PhotoType photo = new PhotoType();
			
			photo.setPhoto(FileUtils.readFileToByteArray(new File("file/vcard/admin-bar-sprite.png")));
			photo.setEncodingType(EncodingType.BASE64);
			photo.setImageMediaType(ImageMediaType.PNG);
			vcard.addPhoto(photo);
			
			writer.setVCard(vcard);
			String vstring = writer.buildVCardString();
			if(writer.hasErrors()) {
				List<VCardError> errors = vcard.getErrors();
				for(int j = 0; j < errors.size(); j ++) {
					System.out.println(StringUtil.formatException(errors.get(j).getError()));
				}
				
			}
			System.out.println(vstring);
		}
		System.out.println("\n-- END --");
	}
}
