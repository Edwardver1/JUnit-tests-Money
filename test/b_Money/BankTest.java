package b_Money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		// currencies
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);

		// bank
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);

		// Open bank account in SweBank for client
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");

		// Open bank account in Nordea for client
		Nordea.openAccount("Bob");

		// Open bank account in DanskeBank for client
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		// check if the name of the bank equal
		assertEquals(SweBank.getName(), "SweBank");
	}

	@Test
	public void testGetCurrency() {
		// check the currency of the bank
		assertEquals(SweBank.getCurrency(), SEK);
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		// open trial bank account
		SweBank.openAccount("John");
		SweBank.openAccount("Anna");


		// deposit for John 1000 DKK
		SweBank.deposit("John", new Money(1000, DKK));
	}

	@Test(expected = AccountExistsException.class)
	public void testOpenAccountException() throws AccountExistsException {
		SweBank.openAccount("John");
		SweBank.openAccount("John");
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		// deposit for Bob 10000 SEK
		SweBank.deposit("Bob", new Money(10000, SEK));

		// check balance of Bob after deposit
		// DOUBLE-----
		assertEquals(SweBank.getBalance("Bob"), (Integer) 10000);
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		// deposit for Bob 10000 SEK
		SweBank.deposit("Bob", new Money(10000, SEK));
		// minus deposit for Bob 10000 SEK
		SweBank.withdraw("Bob", new Money(3000, SEK));

		// check the value of the account for Bob
		assertEquals(SweBank.getBalance("Bob"), (Integer) 7000);
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		// check if Bob has 0
		assertEquals(SweBank.getBalance("Bob"), (Integer) 0);
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		// deposit of 10000 for Bob in SEK
		SweBank.deposit("Bob", new Money(10000, SEK));
		// transfer Bob --> Ulrika 10000 SEK
		SweBank.transfer("Bob", "Ulrika", new Money(10000, SEK));
		// check if Bob transfer the money
		assertEquals(SweBank.getBalance("Bob"), (Integer) 0);
		// check if Ulriki gets the money
		assertEquals(SweBank.getBalance("Ulrika"), (Integer) 10000);
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {

		// deposit for Bob 10000 SEK
		SweBank.deposit("Bob", new Money(10000, SEK));
		// account_id, pay_id, interval, next, Money amount, Bank tobank,
		// toaccount
		SweBank.addTimedPayment("Bob", "1", 2, 3, new Money(10000, SEK), Nordea, "Bob");
		SweBank.tick();

		// Bob's money != 10000
		assertNotEquals(Nordea.getBalance("Bob"), (Integer) 10000);
		SweBank.tick();
		assertNotEquals(Nordea.getBalance("Bob"), (Integer) 10000);
		SweBank.tick();
		assertNotEquals(Nordea.getBalance("Bob"), (Integer) 10000);
		SweBank.tick();
		// Bob's money = 10000
		assertEquals(Nordea.getBalance("Bob"), (Integer) 10000);
		SweBank.tick();
		// Bob's money = 10000
		assertEquals(Nordea.getBalance("Bob"), (Integer) 10000);

	}
}
