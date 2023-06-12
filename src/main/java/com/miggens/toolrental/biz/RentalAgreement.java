package com.miggens.toolrental.biz;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.miggens.toolrental.tools.Tool;
import com.miggens.toolrental.transaction.Discount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RentalAgreement {
    private Tool rentalTool; 
    private Integer rentalDaysCount; 
    private Integer chargableDaysCount; 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate checkoutDate; 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate returnDate; 
    private Discount discount; 
    private Integer preDiscountCharge; 
    private Integer finalCheckoutCharge; 
    private String asString;

    @Override
    public String toString() {
        String[] checkoutComponents = this.checkoutDate.toString().split("-");
        String[] returnDateComponents = this.returnDate.toString().split("-");
        StringBuilder sb = new StringBuilder(); 
        sb.append("Rental Agreement:\n");
        sb.append("----------------------------------------------------------\n\n");  
        sb.append("Tool code:\t\t\t" + this.rentalTool.getToolCode() + "\n");
        sb.append("Tool Brand:\t\t\t" + this.rentalTool.getToolBrand() + "\n"); 
        sb.append("Rental Days:\t\t\t" +this.rentalDaysCount+ "\n");
        sb.append("Checkout Date:\t\t\t" + String.format("%s/%s/%s", checkoutComponents[1], checkoutComponents[2], checkoutComponents[0].substring(2,4) ) + "\n");
        sb.append("Return Date:\t\t\t" + String.format("%s/%s/%s", returnDateComponents[1], returnDateComponents[2], returnDateComponents[0].substring(2,4)) + "\n");
        sb.append("Daily Charge:\t\t\t$" + String.format("%.2f", (this.rentalTool.getToolRate().getDailyRate() / 100.0f)) + "\n");
        sb.append("Charged Days:\t\t\t" + this.chargableDaysCount + "\n");
        sb.append("Prediscount Charge:\t\t$" + String.format("%.2f", (this.preDiscountCharge / 100.0f)) + "\n");
        sb.append("Discount:\t\t\t"+this.discount.getPercent() + "%\n" );
        sb.append("Discount Amount:\t\t$" + String.format("%.2f", (this.discount.getAmount()/100.0f)) +"\n");
        sb.append("Final Charge:\t\t\t$" + String.format("%.2f", (this.finalCheckoutCharge/100.0f)) +"\n");

        return sb.toString(); 
    }
}