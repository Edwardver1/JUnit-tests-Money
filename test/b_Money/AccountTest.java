package b_Money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);

		// *
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		// id, interval, next, amount, tobank, toaccount
		testAccount.addTimedPayment("1", 2, 3, new Money(1000000, SEK), SweBank, "test");
		// true existence of the account
		assertTrue("testAddRemoveTimedPayment addition", testAccount.timedPaymentExists("1"));
		// false existence of the account
		assertFalse("testAddRemoveTimedPayment addition", testAccount.timedPaymentExists("2"));

		// remover account (id)
		testAccount.removeTimedPayment("1");

		// false existence of the account
		assertFalse("testAddRemoveTimedPayment addition", testAccount.timedPaymentExists("1"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		Money money = new Money(1000, SEK);
		Money base = testAccount.getBalance();

		// id, interval, next, amount, tobank, toaccount
		testAccount.addTimedPayment("1", 2, 3, money, SweBank, "Alice");

		// NotEquals
		testAccount.tick();
		assertNotEquals(testAccount.getBalance().getAmount(), base.sub(money).getAmount());
		testAccount.tick();
		assertNotEquals(testAccount.getBalance().getAmount(), base.sub(money).getAmount());
		testAccount.tick();
		assertNotEquals(testAccount.getBalance().getAmount(), base.sub(money).getAmount());

		// Equals
		testAccount.tick();
		assertEquals(testAccount.getBalance().getAmount(), base.sub(money).getAmount());
		testAccount.tick();
		assertEquals(testAccount.getBalance().getAmount(), base.sub(money).getAmount());
	}

	@Test
	public void testAddWithdraw() {
		// assign to startMoney balance testAccount
		Money startMoney = testAccount.getBalance();

		// deposit
		Money deposit = new Money(150000, SEK);

		// deposit
		testAccount.withdraw(deposit);

		// final balance of deposit
		Money endMoney = testAccount.getBalance();

		// start != end money
		assertNotEquals("testAddWithdraw", startMoney, endMoney);

		// end = start sub deposit
		assertEquals("testAddWithdraw", endMoney, startMoney.sub(deposit));
	}

	@Test
	public void testGetBalance() {
		// *
		// Check the balance 
		assertEquals(testAccount.getBalance(), new Money(10000000, SEK));
	}
}
