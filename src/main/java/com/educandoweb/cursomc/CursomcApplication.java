package com.educandoweb.cursomc;

import com.educandoweb.cursomc.domain.*;
import com.educandoweb.cursomc.domain.enums.ClientType;
import com.educandoweb.cursomc.domain.enums.PaymentStatus;
import com.educandoweb.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PurchaseRepository purchaseRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PurchaseItemRepository purchaseItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informatics");
		Category cat2 = new Category(null, "Office");
		Category cat3 = new Category(null, "Linens");
		Category cat4 = new Category(null, "Electronics");
		Category cat5 = new Category(null, "Gardening");
		Category cat6 = new Category(null, "Decor");
		Category cat7 = new Category(null, "Perfumery");

		Product p1 = new Product(null, "Computer", 2000.00);
		Product p2 = new Product(null, "Printer", 800.00);
		Product p3 = new Product(null, "Mouse", 80.00);
		Product p4 = new Product(null, "Office Desk", 300.00);
		Product p5 = new Product(null, "Towel", 50.00);
		Product p6 = new Product(null, "Quilt", 200.00);
		Product p7 = new Product(null, "TV True Color", 1200.00);
		Product p8 = new Product(null, "Lawn Mower", 800.00);
		Product p9 = new Product(null, "Desk Lamp", 100.00);
		Product p10 = new Product(null, "Hanging Lamp", 180.00);
		Product p11 = new Product(null, "Shampoo", 90.00);

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().addAll(Arrays.asList(p2, p4));
		cat3.getProducts().addAll(Arrays.asList(p5, p6));
		cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProducts().addAll(Arrays.asList(p8));
		cat6.getProducts().addAll(Arrays.asList(p9, p10));
		cat7.getProducts().addAll(Arrays.asList(p11));

		p1.getCategories().addAll(Arrays.asList(cat1, cat4));
		p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategories().addAll(Arrays.asList(cat1, cat4));
		p4.getCategories().addAll(Arrays.asList(cat2));
		p5.getCategories().addAll(Arrays.asList(cat3));
		p6.getCategories().addAll(Arrays.asList(cat3));
		p7.getCategories().addAll(Arrays.asList(cat4));
		p8.getCategories().addAll(Arrays.asList(cat5));
		p9.getCategories().addAll(Arrays.asList(cat6));
		p10.getCategories().addAll(Arrays.asList(cat6));
		p11.getCategories().addAll(Arrays.asList(cat7));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");

		City c1 = new City(null, "Uberlândia", st1);
		City c2 = new City(null, "São Paulo", st2);
		City c3 = new City(null, "Campinas", st2);

		st1.getCities().addAll(Arrays.asList(c1));
		st2.getCities().addAll(Arrays.asList(c2, c3));

		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));

		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "36378912377", ClientType.NATURALPERSON);

		cli1.getPhoneNumbers().addAll(Arrays.asList("27363323", "93838393"));

		Address ad1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Address ad2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getAddresses().addAll(Arrays.asList(ad1, ad2));

		clientRepository.saveAll(Arrays.asList(cli1));
		addressRepository.saveAll(Arrays.asList(ad1, ad2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Purchase pur1 = new Purchase(null, sdf.parse("30/09/2017 10:32"), cli1, ad1);
		Purchase pur2 = new Purchase(null, sdf.parse("10/10/2017 19:35"), cli1, ad2);

		Payment pay1 = new PaymentWithCard(null, PaymentStatus.COMPLETED, pur1, 6);
		pur1.setPayment(pay1);

		Payment pay2 = new PaymentWithBill(null, PaymentStatus.PENDING, pur2, sdf.parse("20/10/2017 00:00") , null);
		pur2.setPayment(pay2);

		cli1.getPurchases().addAll(Arrays.asList(pur1, pur2));

		purchaseRepository.saveAll(Arrays.asList(pur1, pur2));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));

		PurchaseItem pit1 = new PurchaseItem(pur1, p1, 200.00, 1, 2000.00 );
		PurchaseItem pit2 = new PurchaseItem(pur1, p3, 0.00, 2, 80.00);
		PurchaseItem pit3 = new PurchaseItem(pur2, p2, 100.00, 1, 800.00);

		pur1.getItems().addAll(Arrays.asList(pit1, pit2));
		pur2.getItems().addAll(Arrays.asList(pit3));

		p1.getItems().addAll(Arrays.asList(pit1));
		p2.getItems().addAll(Arrays.asList(pit3));
		p3.getItems().addAll(Arrays.asList(pit2));

		purchaseItemRepository.saveAll(Arrays.asList(pit1, pit2, pit3));

	}
}
