package com.wutianyi.study.vcard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.wutianyi.study.vcard.Wrapper.VcardWrapper;

import info.ineighborhood.cardme.io.CompatibilityMode;
import info.ineighborhood.cardme.vcard.VCardImpl;

public class ReportMain {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		TestParser testParser = new TestParser();
		testParser.setCompatibilityMode(CompatibilityMode.RFC2426);
		List<VCardImpl> vcards = testParser.importVCards();
		
		List<VcardWrapper> vs = new ArrayList<VcardWrapper>();
		for(VCardImpl v : vcards)
		{
			vs.add(new VcardWrapper(v));
		}
		
		ReportDO r = ReportServices.getReport(1);
		ReportDO report = Utils.createReportDO(vs, r);
		StringBuilder builder = new StringBuilder();
		builder.append(report.getReportHeader());
		builder.append('\n');

		int j = 0;
		int count = vcards.size();
		for (VCardImpl vcard : vcards) {
			int i = 0;
			int len = report.getHeaders().length;
			for (Header header : report.getHeaders()) {
				if (i != 0 && i != len && header.isShow()) {
					builder.append(',');
				}
				if (header.isShow()) {
					Utils.buildValues(header, new VcardWrapper(vcard), builder);
				}
				i++;
			}
			if (j != count - 1) {
				builder.append('\n');
			}
			j++;
		}

		System.out.println(builder.toString());
	}
}
