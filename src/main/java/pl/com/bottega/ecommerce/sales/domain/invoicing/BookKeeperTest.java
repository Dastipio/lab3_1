package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Date;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest {

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

		// @Test
		// public void
		// testCase_2_DemandFactureWithOnePostition_ShouldReturnFactureWithOnePosition(){
		//
		// }
		//
	}
}
