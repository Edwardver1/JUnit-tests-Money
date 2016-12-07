package b_Money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

// the last 2 values are coins
public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);

		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {

		assertEquals("Ammounts should be equal", (Integer) 10000, SEK100.getAmount());
		assertEquals("Ammounts should be equal", (Integer) 1000, EUR10.getAmount());
		assertEquals("Ammounts should be equal", (Integer) 20000, SEK200.getAmount());
		assertEquals("Ammounts should be equal", (Integer) 2000, EUR20.getAmount());
		assertEquals("Ammounts should be equal", (Integer) 0, SEK0.getAmount());
		assertEquals("Ammounts should be equal", (Integer) 0, EUR0.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK100.getCurrency(), SEK);
		assertEquals(EUR10.getCurrency(), EUR);
		// EUR != DKK
		assertNotEquals(EUR10.getCurrency(), DKK);

	}

	@Test
	public void testToString() {
		assertEquals(EUR10.toString(), "1000 EUR");
		assertEquals(SEK200.toString(), "20000 SEK");
	}

	@Test
	public void testGlobalValue() {
		assertEquals(EUR10.universalValue(), (Integer) 1500);
		assertEquals(EUR20.universalValue(), (Integer) 3000);
	}

	@Test
	public void testEqualsMoney() {
		assertEquals(SEK100, new Money(10000, SEK));
	}

	@Test
	public void testAdd() {
		// plus
		assertEquals(EUR0.add(new Money(100, EUR)), new Money(100, EUR));
	}

	@Test
	public void testSub() {
		// sub
		assertEquals(SEK100.sub(new Money(10000, SEK)), new Money(0, SEK));
	}

	@Test
	public void testIsZero() {
		// Logic - it's true value
		assertTrue(SEK0.isZero());
	}

	@Test
	public void testNegate() {
		// * (-1)
		assertEquals(SEKn100.negate(), SEK100);
	}

	@Test
	public void testCompareTo() {
		// SEK100 != EUR10
		assertNotEquals(SEK100, EUR10);
		assertEquals(SEK100, SEK100);
	}
}