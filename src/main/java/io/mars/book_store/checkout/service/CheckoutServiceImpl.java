package io.mars.book_store.checkout.service;

import io.mars.book_store.base.BaseService;
import io.mars.book_store.book.model.entity.Book;
import io.mars.book_store.checkout.model.entity.Checkout;
import io.mars.book_store.book.service.BookService;
import io.mars.book_store.checkout.data.CheckoutRepository;
import io.mars.book_store.checkout.model.viewmodel.CheckoutRequest;
import io.mars.book_store.common.exception.ContentNotFoundException;
import io.mars.book_store.common.exception.DuplicateRecordException;
import io.mars.book_store.customer.model.entity.Customer;
import io.mars.book_store.customer.service.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class CheckoutServiceImpl extends BaseService<Checkout> implements CheckoutService {

    private final CustomerService customerService;
    private final BookService bookService;
    private final CheckoutRepository checkoutRepository;


    private final JobLauncher jobLauncher;

    private final Job checkoutReportHistory;


    private static final String NO_SUCH_BOOK_FOUND = "No suh book found";
    private static final String NO_SUCH_CUSTOMER_FOUND = "No suh customer found";
    private static final String CHECKOUT_ALREADY_EXISTS = "check out status cannot be changed";


    public CheckoutServiceImpl(CustomerService customerService, BookService bookService,
                               CheckoutRepository checkoutRepository, Validator validator,
                               @Qualifier(value = "get-checkout-history") Job job, JobLauncher jobLauncher) {
        super(validator);
        this.customerService = customerService;
        this.bookService = bookService;
        this.checkoutRepository = checkoutRepository;

        this.jobLauncher = jobLauncher;
        this.checkoutReportHistory = job;
    }

    @Override
    public Checkout addCheckout(CheckoutRequest checkoutRequest) {
        Optional<Checkout> checkoutLookup = checkoutRepository
                .findByCustomerAndBook(checkoutRequest.getNationalCode(), checkoutRequest.getIsbn());
        if (checkoutLookup.isPresent()) throw new DuplicateRecordException(CHECKOUT_ALREADY_EXISTS);

        Customer customer = customerService.findByNationalCode(checkoutRequest.getNationalCode())
                .orElseThrow(() -> new ContentNotFoundException(NO_SUCH_CUSTOMER_FOUND));
        Book book = bookService.findByIsbn(checkoutRequest.getIsbn())
                .orElseThrow(() -> new ContentNotFoundException(NO_SUCH_BOOK_FOUND));

        book.decrementAvailableCount();
        customer.incrementCheckout();

        Checkout checkout = new Checkout(customer, book, LocalDate.now(),
                LocalDate.now().plusDays(7), false, UUID.randomUUID().toString());
        validate(checkout);
        return checkoutRepository.save(checkout);
    }


    @Override
    public Checkout refund(CheckoutRequest checkoutRequest) {
        return refund(checkoutRepository
                .findByCustomerAndBook(checkoutRequest.getNationalCode(), checkoutRequest.getIsbn())
                .orElseThrow(() -> new ContentNotFoundException(NO_SUCH_BOOK_FOUND)));
    }


    @Override
    public Checkout refund(String checkoutNumber) {
        return refund(checkoutRepository.findByCheckoutNumber(checkoutNumber)
                .orElseThrow(() -> new ContentNotFoundException(NO_SUCH_BOOK_FOUND)));
    }

    private Checkout refund(Checkout checkout) {

        if (checkout.isRefunded()) throw new DuplicateRecordException(CHECKOUT_ALREADY_EXISTS);

        Customer customer = checkout.getCustomer();
        Book book = checkout.getBook();
        customer.decrementCheckout();
        book.incrementAvailableCount();
        checkout.setEnd(LocalDate.now());
        checkout.setRefunded(true);
        validate(checkout);
        return checkoutRepository.save(checkout);
    }

    @Override
    public void report() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String dateTime = LocalDateTime.now().format(formatter);
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", dateTime).toJobParameters();
        try {
            jobLauncher.run(checkoutReportHistory, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    @Scheduled(cron = "0 0 0 1 * *")  //the first day of every month
//    public void reportScheduled() {
//        report();
//    }


    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * *") //the zeroth hour of everyday
    public void scoreCheckouts() {

        Predicate<Checkout> isRefunded = checkout ->
                !checkout.isRefunded();
        Predicate<Checkout> isPassedDeadLine = checkout ->
                checkout.getEnd().isAfter(checkout.getDeadline());
        Predicate<Checkout> isPassedNull = checkout ->
                checkout.getEnd() == null;

        List<Checkout> checkoutList = checkoutRepository.findAllInCheck().stream()
                .filter(isRefunded.and(isPassedNull.or(isPassedDeadLine)))
                .peek(checkout -> {
                    Customer customer = checkout.getCustomer();
                    checkout.setEnd(LocalDate.now());
                    customer.addToScore((int) ChronoUnit.DAYS
                            .between(checkout.getEnd(), checkout.getDeadline()));
                }).toList();
        checkoutRepository.saveAll(checkoutList);
    }
}
