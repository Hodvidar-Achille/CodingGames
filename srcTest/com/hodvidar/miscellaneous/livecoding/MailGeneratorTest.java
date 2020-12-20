package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * You are given a list of names of new employees in a company.
 * You need to generate a company email address for each of them.
 * Exercise
 * Write a function: given a string names containing a list of names separated by the characters ", "
 * and a string company specifying the name of the company, returns a string containing the list of email addresses.
 * For example, given company= "Example"and names as follows:
 *
 * John Doe,
 * Peter Parker,
 * Mary Jane Watson-Parker,
 * James Doe,
 * John Elvis Doe,
 * Jane Doe,
 * Penny Parker
 *
 * the function should return:
 *
 * John Doe <j_doe@example.com>, Peter Parker <p_parker@example.com>,
 * Mary Jane Watson-Parker <m_j_watsonpa@example.com>,
 * James Doe <j_doe2@example.com>,
 * John Elvis Doe <j_e_doe@example.com>,
 * Jane Doe <j_doe3@example.com>,
 * Penny Parker <p_parker2@example.com>
 *
 */
public class MailGeneratorTest {

	@Test
	void return_mail_for_one_name() {
		MailGenerator s = new MailGenerator();
		assertThat(s.generateMails("James Bond", "MI6"))
				.isEqualTo("James Bond <j_bond@mi6.com>");
	}

	@Test
	void return_mail_for_two_names() {
		MailGenerator s = new MailGenerator();
		assertThat(s.generateMails("James Bond, MoneyPenny Dam", "MI6"))
				.isEqualTo("James Bond <j_bond@mi6.com>, "
						+ "MoneyPenny Dam <m_dam@mi6.com>");
	}

	@Test
	void return_mail_for_one_name_with_middle_name() {
		MailGenerator s = new MailGenerator();
		assertThat(s.generateMails("James Moriaty Bond", "MI6"))
				.isEqualTo("James Moriaty Bond <j_m_bond@mi6.com>");
	}

	@Test
	void return_mail_for_one_name_with_surname_more_than_eight_characters_long() {
		MailGenerator s = new MailGenerator();
		assertThat(s.generateMails("Monique Delacroix", "MI6"))
				.isEqualTo("Monique Delacroix <m_delacroi@mi6.com>");
	}

	@Test
	void return_mail_for_one_name_with_surname_with_dash() {
		MailGenerator s = new MailGenerator();
		assertThat(s.generateMails("Monique Bond-Delacroix", "MI6"))
				.isEqualTo("Monique Bond-Delacroix <m_bonddela@mi6.com>");
	}

	@Test
	void return_mail_for_duplicated_name() {
		MailGenerator s = new MailGenerator();
		assertThat(s.generateMails("Monique Bond-Delacroix, "
				+ "Monique Bond-Delacroix", "MI6"))
				.isEqualTo("Monique Bond-Delacroix <m_bonddela@mi6.com>, "
						+ "Monique Bond-Delacroix <m_bonddela2@mi6.com>");
	}

	@Test
	void return_mail_for_duplicated_name_three_times() {
		MailGenerator s = new MailGenerator();
		assertThat(s.generateMails("Monique Bond-Delacroix, "
				+ "Monique Bond-Delacroix, "
				+ "Monique Bond-Delacroix", "MI6"))
				.isEqualTo("Monique Bond-Delacroix <m_bonddela@mi6.com>, "
						+ "Monique Bond-Delacroix <m_bonddela2@mi6.com>, "
						+ "Monique Bond-Delacroix <m_bonddela3@mi6.com>");
	}

	@Test
	void check_for_full_example() {
		MailGenerator s = new MailGenerator();
		assertThat(s.generateMails("John Doe, "
				+ "Peter Parker, "
				+ "Mary Jane Watson-Parker, "
				+ "James Doe, "
				+ "John Elvis Doe, "
				+ "Jane Doe, "
				+ "Penny Parker", "example"))
				.isEqualTo("John Doe <j_doe@example.com>, "
						+ "Peter Parker <p_parker@example.com>, "
						+ "Mary Jane Watson-Parker <m_j_watsonpa@example.com>, "
						+ "James Doe <j_doe2@example.com>, "
						+ "John Elvis Doe <j_e_doe@example.com>, "
						+ "Jane Doe <j_doe3@example.com>, "
						+ "Penny Parker <p_parker2@example.com>");
	}

}
