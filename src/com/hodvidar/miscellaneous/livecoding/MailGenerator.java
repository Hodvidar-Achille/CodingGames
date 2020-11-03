package com.hodvidar.miscellaneous.livecoding;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MailGenerator {

	public String generateMails(String names, String company) {
		return Arrays.stream(names.split(", "))
				.map(name -> getMailForOnePerson(name, company))
				.collect(Collectors.joining(", "));
	}

	private static String getMailForOnePerson(String fullName,
			String company) {
		String[] splitFullName = fullName.toLowerCase().split(" ");
		String initialAndSurname =
				IntStream.range(0, splitFullName.length)
						.mapToObj(i
								->
								i < (splitFullName.length -1) ?
										String.valueOf(splitFullName[i].charAt(0))
										: splitFullName[i]
								)
						.collect(Collectors.joining("_"));
		String email =
				"<"
				+ initialAndSurname
				+ "@"
				+ company.toLowerCase()
				+".com"
				+ ">";
		return fullName + " " + email;
	}

}
