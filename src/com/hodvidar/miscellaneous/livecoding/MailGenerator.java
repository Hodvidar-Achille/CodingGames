package com.hodvidar.miscellaneous.livecoding;

import java.util.Arrays;
import java.util.function.IntFunction;
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
						.mapToObj(getStringIntFunction(splitFullName)
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

	private static IntFunction<String> getStringIntFunction(String[] splitFullName) {
		return i
				->
				isSurname(splitFullName.length, i) ?
						transformSurname(splitFullName[i]) :
						transformFirstName(splitFullName[i]);
	}

	private static boolean isSurname(int arraySize, int i) {
		return i == (arraySize - 1);
	}

	private static String transformFirstName(String s) {
		return String.valueOf(s.charAt(0));
	}

	/**
	 * Result surname must be max 8 characters long and not contain
	 * special characters.
	 * (Only special character Dash "-" checked here).
	 */
	private static String transformSurname(String surname) {
		return surname
				.replace("-", "")
				.substring(
						0,
						Math.min(surname.length(),
								8)
				);
	}

}
