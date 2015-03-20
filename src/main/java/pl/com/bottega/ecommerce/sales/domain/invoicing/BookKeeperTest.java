package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Date;
import org.junit.Test;
import pl.com.bottega.cqrs.command.handler.*;
import pl.com.bottega.ecommerce.canonicalmodel.*;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest {

	private BookKeeper book;
	private Invoice invoice;

	public void start() {
		// given
		InvoiceFactory factoryMock = mock(InvoiceFactory.class);
		BookKeeper book = new BookKeeper(factoryMock);
	}

	@Test
	public void testCase_1_DemandFactureWithOnePostition_ShouldReturnFactureWithOnePosition() {
		
		
		Money money = new Money(20);
		Id id = new Id("1");
		InvoiceRequest invoiceRequest = new InvoiceRequest(new ClientData(new Id("1"),"facture"));
		TaxPolicy taxPolicy = mock(TaxPolicy.class);
		ProductData productData = new ProductData(new Id("1"),new Money(10),"book",ProductType.DRUG,new Date(0));
	
		RequestItem requestItem = new RequestItem(productData, 20, new Money(20));
		
		book.issuance(invoiceRequest, taxPolicy);
		//when(requestItem.getQuantity());
		when(taxPolicy.calcuateTax(ProductType.DRUG, money)).thenReturn(new Tax(money,"spis"));
		invoiceRequest.add(RequestItem);

		@Test
		public void testCase_2_DemandFactureWithOnePostition_ShouldReturnFactureWithOnePosition()
		
	}
}
