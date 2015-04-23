package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.*;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest {

	public BookKeeper bookKeeper;

	@Test
	public void testCase_1_DemandFactureWithOnePostition_ShouldReturnFactureWithOnePosition() {

		Money money = new Money(20);
		Id id = new Id("1");
		ClientData clientData = new ClientData(id, "klient1");
		
		BookKeeper book;
		
		TaxPolicy taxPolicy = mock(TaxPolicy.class);

		InvoiceFactory InvoiceFactoryMock = mock(InvoiceFactory.class);
		when(InvoiceFactoryMock.create(clientData)).thenReturn(
				new Invoice(id, clientData));
		book = new BookKeeper(InvoiceFactoryMock);

		InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);

		when(taxPolicy.calculateTax(ProductType.FOOD, money)).thenReturn(
				new Tax(money, "opis"));

		ProductData productData = new ProductData(id, money, "book",
				ProductType.FOOD, new Date(0));

		RequestItem requestItem = new RequestItem(productData, 20, money);
		invoiceRequest.add(requestItem);
		
		Invoice invoiceResult = book.issuance(invoiceRequest, taxPolicy);
		int result = invoiceResult.getItems().size();

		assertThat(result, is(1));
	}

		
	@Test
	public void Test_case_2_PositionInvoiceRequest_callCalculateTaxTwice() {
	
		// given
		Id id = new Id("1");
		Money moneyEveryItem = new Money(1);
		ProductType productTypeEveryItem = ProductType.FOOD;
		ClientData clientData = new ClientData(id, "client");
		ProductData productData = new ProductData(id, moneyEveryItem,
				"book", productTypeEveryItem, new Date());
		RequestItem requestItem = new RequestItem(productData, 4,
				moneyEveryItem);

		InvoiceFactory mockInvoiceFactory = mock(InvoiceFactory.class);
		bookKeeper = new BookKeeper(mockInvoiceFactory);
		when(mockInvoiceFactory.create(clientData)).thenReturn(
				new Invoice(id, clientData));
		TaxPolicy taxPolicy = mock(TaxPolicy.class);
		when(taxPolicy.calculateTax(productTypeEveryItem, moneyEveryItem))
				.thenReturn(new Tax(moneyEveryItem, "content"));

		InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
		invoiceRequest.add(requestItem);
		invoiceRequest.add(requestItem);

		// when
		Invoice invoiceResult = bookKeeper.issuance(invoiceRequest, taxPolicy);

		// then
		Mockito.verify(taxPolicy, Mockito.times(2)).calculateTax(
				productTypeEveryItem, moneyEveryItem);
	}
	
}
