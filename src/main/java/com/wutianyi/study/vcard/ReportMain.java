package com.wutianyi.study.vcard;

import java.io.IOException;
import java.util.List;

import info.ineighborhood.cardme.io.CompatibilityMode;
import info.ineighborhood.cardme.vcard.VCardImpl;

public class ReportMain {
	public static void main(String[] args) throws IOException {
		TestParser testParser = new TestParser();
		testParser.setCompatibilityMode(CompatibilityMode.RFC2426);
		List<VCardImpl> vcards = testParser.importVCards();
		ReportDO report = ReportServices.getReport(1);

		StringBuilder builder = new StringBuilder();
		int i = 0;
		int l = report.getHeaders().length - 1;
		for (Header header : report.getHeaders()) {
			Value[] values = header.getValues();
			if (null == values || values.length == 0) {
				builder.append('"');
				builder.append(header.getdKey());
				builder.append('"');
				if (header.isMulti() && header.isAutoIncrement()) {
					for (int j = 1; j < header.getCount(); j++) {
						builder.append(',');
						builder.append('"');
						builder.append(header.getdKey());
						builder.append(' ');
						builder.append(j);
						builder.append('"');
					}
				}

			} else {
				int index = 0;
				int len = values.length - 1;

				for (Value value : values) {
					if (!value.escape) {
						builder.append('"');
						builder.append(value.getDisplayName());
						builder.append('"');
						if (index != len) {
							builder.append(',');
						}
					}
					++index;
				}
				if (header.isMulti() && header.isAutoIncrement()) {
					for (int j = 1; j < header.getCount(); j++) {
						builder.append(',');
						index = 0;
						for (Value value : values) {
							if (!value.escape) {
								builder.append('"');
								builder.append(value.getDisplayName());
								builder.append(' ');
								builder.append(j);
								builder.append('"');
								if (index != len) {
									builder.append(',');
								}
							}
							++index;
						}
					}
				}
			}

			if (i != l) {
				builder.append(',');
			}
			i++;
		}

		System.out.println(builder.toString());
	}
}
