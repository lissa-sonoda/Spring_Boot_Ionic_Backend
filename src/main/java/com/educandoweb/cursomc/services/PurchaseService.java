package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Client;
import com.educandoweb.cursomc.domain.PaymentWithBill;
import com.educandoweb.cursomc.domain.Purchase;
import com.educandoweb.cursomc.domain.PurchaseItem;
import com.educandoweb.cursomc.domain.enums.PaymentStatus;
import com.educandoweb.cursomc.repositories.PaymentRepository;
import com.educandoweb.cursomc.repositories.PurchaseItemRepository;
import com.educandoweb.cursomc.repositories.PurchaseRepository;
import com.educandoweb.cursomc.security.UserSS;
import com.educandoweb.cursomc.services.exceptions.AuthorizationException;
import com.educandoweb.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository repo;

    @Autowired
    private BillService billService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    public Purchase search(Integer id){
        Optional<Purchase> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Purchase.class.getName()
        ));
    }

    @Transactional
    public Purchase insert(Purchase obj){
        obj.setId(null);
        obj.setInstant(new Date());
        obj.setClient(clientService.search(obj.getClient().getId()));
        obj.getPayment().setStatus((PaymentStatus.PENDING));
        obj.getPayment().setPurchase(obj);
        if(obj.getPayment() instanceof PaymentWithBill){
            PaymentWithBill pgto = (PaymentWithBill) obj.getPayment();
            billService.fillPaymentWithBill(pgto, obj.getInstant());
        }
        obj = repo.save(obj);
        paymentRepository.save(obj.getPayment());
        for (PurchaseItem ip : obj.getItems()) {
            ip.setDiscount(0.0);
            ip.setProduct(productService.search(ip.getProduct().getId()));
            ip.setPrice(ip.getProduct().getPrice());
            ip.setPurchase(obj);
        }
        purchaseItemRepository.saveAll(obj.getItems());
        //System.out.println(obj);
        //emailService.sendOrderConfirmationEmail(obj);
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }

    public Page<Purchase> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        UserSS user = UserService.authenticated();
        if (user == null){
            throw new AuthorizationException("Access Denied!");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Client client = clientService.search(user.getId());
        return repo.findByClient(client, pageRequest);
    }
}
