package com.hodvidar.miscellaneous.livecoding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MailGenerator {

	private static final String START_MAIL_DELIMITER = "<";
	private static final String END_MAIL_DELIMITER = ">";
	private static final String MIDDLE_MAIL_DELIMITER = "@";

	public String generateMails(String names, String company) {
		HashMap<String, Integer> namesOccurrence = new HashMap<>();
		List<String> emails = new ArrayList<>();
		for(String fullName : names.split(", ")) {
			String fullNameAndEmail = getMailForOnePerson(fullName, company);
			fullNameAndEmail = handlesDuplication(namesOccurrence, fullNameAndEmail);
			emails.add(fullNameAndEmail);
		}
		return emails.stream().collect(Collectors.joining(", "));
	}

	private static String handlesDuplication(HashMap<String, Integer> namesOccurrence, String fullNameAndEmail) {
		String email = fullNameAndEmail.substring(
				fullNameAndEmail.indexOf(START_MAIL_DELIMITER)+1,
				fullNameAndEmail.indexOf(END_MAIL_DELIMITER));
		if(!namesOccurrence.containsKey(email)) {
			namesOccurrence.put(email, 1);
			return fullNameAndEmail;
		}
		String fullName = fullNameAndEmail.substring(
				0,
				fullNameAndEmail.indexOf(START_MAIL_DELIMITER));
		namesOccurrence.put(email, namesOccurrence.get(email) + 1);
		email = email.replace(MIDDLE_MAIL_DELIMITER,
				namesOccurrence.get(email)+MIDDLE_MAIL_DELIMITER);
		return fullName + START_MAIL_DELIMITER + email + END_MAIL_DELIMITER;
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
				START_MAIL_DELIMITER
				+ initialAndSurname
				+ MIDDLE_MAIL_DELIMITER
				+ company.toLowerCase()
				+".com"
				+ END_MAIL_DELIMITER;
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
