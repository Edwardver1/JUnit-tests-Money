package b_Money;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		// code of the currency( expected value) = objCurrency.getName();
		assertEquals("Names should be equal", "SEK", SEK.getName());
		assertEquals("Names should be equal", "DKK", DKK.getName());
		assertEquals("Names should be equal", "EUR", EUR.getName());
	}

	@Test
	public void testGetRate() {
		// check value
		assertEquals("Rates should be equal", 0.15, SEK.getRate(), 0);
		assertEquals("Rates should be equal", 0.20, DKK.getRate(), 0);
		assertEquals("Rates should be equal", 1.5, EUR.getRate(), 0);
	}

	@Test
	public void testSetRate() {

		// set rate for sek,dkk,eur - 0.09
		SEK.setRate(0.09);
		DKK.setRate(0.09);
		EUR.setRate(0.09);

		// Check value for every of 3 currencies 
		assertEquals("Rates should be equal", 0.09, SEK.getRate(), 0);
		assertEquals("Rates should be equal", 0.09, DKK.getRate(), 0);
		assertEquals("Rates should be equal", 0.09, EUR.getRate(), 0);
	}

	@Test
	public void testGlobalValue() {
		// test global value
		assertEquals("Global values should be equal", (long) 15, (long) SEK.universalValue(100));
		assertEquals("Global values should be equal", (long) 20, (long) DKK.universalValue(100));
		assertEquals("Global values should be equal", (long) 150, (long) EUR.universalValue(100));
	}

	@Test
	public void testValueInThisCurrency() {
		// test value currency
		assertEquals("Values should be equal", (long) 1000, (long) SEK.valueInThisCurrency(100, EUR));
		assertEquals("Values should be equal", (long) 750, (long) DKK.valueInThisCurrency(100, EUR));
		assertEquals("Values should be equal", (long) 13, (long) EUR.valueInThisCurrency(100, DKK));
	}

}
