package medication_prescription_handler;

import java.time.Period;

public class PrescriptionExpirationAlert {
    private Period _timeLeft;
    private String _prescriptionName;

    PrescriptionExpirationAlert(Period timeLeft, String prescriptionName){
        _timeLeft = timeLeft;
        _prescriptionName = prescriptionName;
    }

    public String sendAlert(){
        String alert = "";
        int years = _timeLeft.getYears();
        int months = _timeLeft.getMonths();
        int days = _timeLeft.getDays();

        if((years >= 0) && (months >= 0) && (days >= 0)){
            alert = "Your prescription for " + _prescriptionName + " expires in ";

            if(years != 0){
                if(years == 1){
                    alert += years + " year";
                }else{
                    alert += years + " years";
                }

            }

            if(months != 0){
                if(years != 0) alert += ", ";

                if(months == 1){
                    alert += months + " month";
                } else{
                    alert += months + " months";
                }

            }

            if(days != 0){
                if(years != 0 || months != 0) alert += ", ";

                if(days == 1) {
                    alert += days + " day";
                } else{
                    alert += days + " days";
                }

            }

            if((years == 0) && (months == 0) && (days == 0)){
                alert = "Your prescription for " + _prescriptionName + " expires today!";
            }

        }else {
            alert = "Your prescription for " + _prescriptionName + " is overdue by ";

            if(years != 0){
                if(years == -1){
                    alert += (years * -1) + " year";
                }else{
                    alert += (years * -1) + " years";
                }
            }

            if(months != 0){
                if(years != 0) alert += ", ";

                if(months == -1){
                    alert += (months * -1) + " month";
                } else{
                    alert += (months * -1) + " months";
                }
            }

            if(days != 0){
                if(years != 0 || months != 0) alert += ", ";

                if(days == -1) {
                    alert += (days * -1)+ " day";
                } else{
                    alert += (days * -1) + " days";
                }
            }

            alert += "!";
        }

        System.out.println(alert);
        System.out.println();

        return alert;
    }
}
