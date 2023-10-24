package io.mars.book_store.checkout.report;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Data
public class CheckoutRowMapper implements RowMapper<CheckoutCSVDTO> {

    @Override
    public CheckoutCSVDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        String startCheckout = rs.getString("start");
        String deadlineCheckout = rs.getString("deadline");
        String endCheckout = rs.getString("end");
        String checkoutDuration = endCheckout == null ? "pending" : String.valueOf(ChronoUnit.DAYS.between(LocalDate.parse(startCheckout),
                        LocalDate.parse(endCheckout)));

        String customerFullName = rs.getString("name")
                + " " + rs.getString("last_name");
        String bookInfo = rs.getString("title")
                + " " + rs.getString("isbn");

        return new CheckoutCSVDTO(customerFullName, bookInfo, checkoutDuration, startCheckout, deadlineCheckout);
    }
}
