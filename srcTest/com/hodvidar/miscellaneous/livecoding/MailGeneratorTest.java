package com.hodvidar.miscellaneous.livecoding;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


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

}
