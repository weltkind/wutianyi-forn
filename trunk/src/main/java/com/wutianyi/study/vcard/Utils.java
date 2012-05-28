package com.wutianyi.study.vcard;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import info.ineighborhood.cardme.vcard.VCardImpl;
import info.ineighborhood.cardme.vcard.VCardType;
import info.ineighborhood.cardme.vcard.features.ExtendedFeature;
import info.ineighborhood.cardme.vcard.features.NameFeature;
import info.ineighborhood.cardme.vcard.features.TelephoneFeature;
import info.ineighborhood.cardme.vcard.types.parameters.TelephoneParameterType;

public class Utils {

	public static void buildValues(Header header, VCardImpl vcard,
			StringBuilder builder) {
		VCardType vCardType = null;

		if (header.getvKey().startsWith("X-")) {
			vCardType = VCardType.XTENDED;
		} else {
			try {
				vCardType = VCardType.valueOf(header.getvKey());
			} catch (Exception e) {
				return;
			}

		}
		int count = 0;
		int i = 0;
		if (header.isAutoIncrement() && header.isMulti()) {
			count = header.getCount();
		}
		switch (vCardType) {
		case N: {
			NameFeature nameFeature = vcard.getName();
			String value = getNameValue(nameFeature);
			buildValue(header, value, builder);
			break;
		}
		case XTENDED: {
			
			if (vcard.hasExtendedTypes()) {
				Iterator<ExtendedFeature> features = vcard.getExtendedTypes();

				while (features.hasNext() && i < count) {
					ExtendedFeature feature = features.next();
					if (StringUtils.equals(feature.getExtensionName(), header
							.getvKey())) {
						buildValue(header, feature.getExtensionData(), builder);
						i++;
						if (i != count) {
							builder.append(',');
						}
						
					}
				}
			}
			appendEmpty(count - i - 1, builder);
			break;
		}
		case TEL: {
			if(vcard.hasTelephoneNumbers()) {
				Iterator<TelephoneFeature> tels = vcard.getTelephoneNumbers();
				while(tels.hasNext() && i < count) {
					TelephoneFeature tel = tels.next();
					String[] pS = header.getParameters();
					boolean r = true;
					for(String s : pS) {
						try {
							r &= tel.containsTelephoneParameterType(TelephoneParameterType.valueOf(s));
						}
						catch(Exception e) {
							r = false;
						}
						if(!r) {
							break;
						}
					}
					if(r) {
						buildValue(header, tel.getTelephone(), builder);
						i++;
						if (i != count) {
							builder.append(',');
						}
					}
				}
			}
			appendEmpty(count - i - 1, builder);
			break;
		}
		}
	}

	private static void appendEmpty(int count, StringBuilder builder) {
		for (int i = 0; i < count; i++) {
			builder.append(',');
		}
	}

	private static void buildValue(Header header, String value,
			StringBuilder builder) {
		if (null == value) {
			value = "";
		}
		Value[] values = header.getValues();
		if (null == values) {
			builder.append('"');
			builder.append(value);
			builder.append('"');
		} else {
			int len = values.length;
			String[] vs = value.split(";", len);

			int i = 0;
			for (Value v : values) {
				i++;
				if (v.escape) {
					continue;
				}
				builder.append('"');
				if (v.getIndex() < len) {
					builder.append(vs[v.getIndex()]);
				}
				builder.append('"');

				if (i != len) {
					builder.append(',');
				}
			}
		}
	}

	private static String getNameValue(NameFeature nameFeature) {
		StringBuilder builder = new StringBuilder();
		builder.append(nameFeature.getFamilyName());
		builder.append(';');
		builder.append(nameFeature.getGivenName());
		builder.append(';');
		for (Iterator<String> itr = nameFeature.getAdditionalNames(); itr
				.hasNext();) {
			builder.append(itr.next());
			builder.append(' ');
		}
		builder.append(';');
		for (Iterator<String> itr = nameFeature.getHonorificPrefixes(); itr
				.hasNext();) {
			builder.append(itr.next());
			builder.append(' ');
		}
		builder.append(';');
		for (Iterator<String> itr = nameFeature.getHonorificSuffixes(); itr
				.hasNext();) {
			builder.append(itr.next());
			builder.append(' ');
		}
		return builder.toString();
	}

}
