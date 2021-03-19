package medication_prescription_handler;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleMedicationPrescriptionHandler {

    private LocalDate _prescriptionExpiration;
    private String _prescriptionName;
    private int _takeMedicationThisManyTimesADay;
    private int _milligramDosageInASingleTablet;
    private int _takeThisManyTabletsAtaTime;
    private int _maxPillCountInBottle;
    private int _pillsRemainingInBottle;
    private int _refillsRemaining;

    LocalDate _datePillCountAlertWasLastSent;
    LocalDate _dateExpirationAlertWasLastSent;

    public SingleMedicationPrescriptionHandler(){
        _maxPillCountInBottle = -1;
        _pillsRemainingInBottle = -1;
        _refillsRemaining = -1;
    }

    public String getPrescriptionName(){
        return _prescriptionName;
    }
    public LocalDate getPrescriptionExpiration() {
        return _prescriptionExpiration;
    }
    public int getTakeMedicationThisManyTimesADay(){return _takeMedicationThisManyTimesADay;}
    public int getMilligramDosageInASingleTablet(){return _milligramDosageInASingleTablet;}
    public int getTakeThisManyTabletsAtaTime(){return _takeThisManyTabletsAtaTime;}
    public int getMaxPillCountInBottle(){return _maxPillCountInBottle;}
    public int getPillsRemainingInBottle(){return _pillsRemainingInBottle;}
    public int getRefillsRemaining(){return _refillsRemaining;}

    public void setTakeMedicationThisManyTimesADay(int takeMedicationThisManyTimesADay) throws Exception{
        if(takeMedicationThisManyTimesADay < 1){
            MedHandlerException takeMedLessThanOnceADay = new MedHandlerException("Number of times a day to take medication must be greater than zero");
            System.out.println(takeMedLessThanOnceADay.getMessage());
            System.out.println();
            throw takeMedLessThanOnceADay;
        } else {
            _takeMedicationThisManyTimesADay = takeMedicationThisManyTimesADay;
        }
    }

    public void setMilligramDosageInASingleTablet(int milligramDosageInASinglePill) throws Exception{
        if(milligramDosageInASinglePill < 1){
            MedHandlerException singlePillDosageIsLessThanOneMg = new MedHandlerException("Dosage of a single pill must be greater than zero");
            System.out.println(singlePillDosageIsLessThanOneMg.getMessage());
            System.out.println();
            throw singlePillDosageIsLessThanOneMg;
        } else {
            _milligramDosageInASingleTablet = milligramDosageInASinglePill;
        }
    }

    public void setTakeThisManyTabletsAtaTime(int takeThisManyTabletsAtaTime) throws Exception{
        if(takeThisManyTabletsAtaTime < 1){
            MedHandlerException NumberOfTabletsIsLessThanOne = new MedHandlerException("Number of Tablets must be greater than zero");
            System.out.println(NumberOfTabletsIsLessThanOne.getMessage());
            System.out.println();
            throw NumberOfTabletsIsLessThanOne;
        } else {
            _takeThisManyTabletsAtaTime = takeThisManyTabletsAtaTime;
        }
    }

    public void setMaxPillCountInBottle(int maxPillCountInBottle) throws Exception{
        if(maxPillCountInBottle < 0){
            MedHandlerException maxPillCountIsLessThanZero = new MedHandlerException("number of tablets that comes in bottle can't be negative");
            System.out.println(maxPillCountIsLessThanZero.getMessage());
            System.out.println();
            throw maxPillCountIsLessThanZero;
        } else {
            _maxPillCountInBottle = maxPillCountInBottle;
        }
    }

    public void setPillsRemainingInBottle(int pillsRemainingInBottle) throws Exception{
        if(pillsRemainingInBottle < 0){
            MedHandlerException pillCountIsLessThanZero = new MedHandlerException("Pills remaining can't be negative");
            System.out.println(pillCountIsLessThanZero.getMessage());
            System.out.println();
            throw pillCountIsLessThanZero;
        } else {
            _pillsRemainingInBottle = pillsRemainingInBottle;
        }
    }

    public void setRefillsRemaining(int refillsRemaining) throws Exception{
        if(refillsRemaining < 0){
            MedHandlerException refillCountIsLessThanZero = new MedHandlerException("number of refills can't be negative");
            System.out.println(refillCountIsLessThanZero.getMessage());
            System.out.println();
            throw refillCountIsLessThanZero;
        } else {
            _refillsRemaining = refillsRemaining;
        }
    }

    public void setPrescriptionName(String prescriptionName) throws Exception{

        Pattern pattern = Pattern.compile("[^A-Za-z]");
        Matcher matcher = pattern.matcher(prescriptionName);
        boolean containsBadCharacters = matcher.find();

       if(prescriptionName.isEmpty() || prescriptionName.isBlank()){
           MedHandlerException nullPrescriptionName = new MedHandlerException("Prescription Name must be filled out");
           System.out.println(nullPrescriptionName.getMessage());
           System.out.println();
           throw nullPrescriptionName;
        }else if(prescriptionName.length() < 4) {
           MedHandlerException tooShortPrescriptionName = new MedHandlerException("Prescription Name must be at least four letters long");
           System.out.println(tooShortPrescriptionName.getMessage());
           System.out.println();
           throw tooShortPrescriptionName;
        }else if(containsBadCharacters){
           MedHandlerException nonLetterPrescriptionName = new MedHandlerException("Prescription Name must only contain letters");
           System.out.println(nonLetterPrescriptionName.getMessage());
           System.out.println();
           throw nonLetterPrescriptionName;
       }else{
           _prescriptionName = prescriptionName;
       }
    }

    public void setPrescriptionExpiration(int year, int month, int day) throws Exception{
        try {
            LocalDate prescriptionExpiration = LocalDate.of(year, month, day);
            _prescriptionExpiration = prescriptionExpiration;
        } catch (Exception e){
            MedHandlerException invalidDate = new MedHandlerException("Invalid date");
            System.out.println(invalidDate.getMessage());
            System.out.println();
            throw invalidDate;
        }
    }

    public void setDatePillCountAlertWasLastSent(LocalDate datePillCountAlertWasLastSent){
        _datePillCountAlertWasLastSent = datePillCountAlertWasLastSent;
    }

    public void setDateExpirationAlertWasLastSent(LocalDate dateExpirationAlertWasLastSent){
        _dateExpirationAlertWasLastSent = dateExpirationAlertWasLastSent;
    }

    public SingleMedicationPrescriptionHandler clone() {
        SingleMedicationPrescriptionHandler clone = new SingleMedicationPrescriptionHandler();

        try {
            clone.setPrescriptionName(_prescriptionName);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        LocalDate prescriptionExpiration = _prescriptionExpiration;
        int year = prescriptionExpiration.getYear();
        int month = prescriptionExpiration.getMonthValue();
        int day = prescriptionExpiration.getDayOfMonth();
        try {
            clone.setPrescriptionExpiration(year, month, day);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            clone.setTakeThisManyTabletsAtaTime(_takeThisManyTabletsAtaTime);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            clone.setTakeMedicationThisManyTimesADay(_takeMedicationThisManyTimesADay);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            clone.setMilligramDosageInASingleTablet(_milligramDosageInASingleTablet);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            clone.setMaxPillCountInBottle(_maxPillCountInBottle);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            clone.setPillsRemainingInBottle(_pillsRemainingInBottle);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try{
            clone.setRefillsRemaining(_refillsRemaining);
        } catch(Exception e){

        }

        return clone;
    }

    public boolean isCloseToRunningOut(){
        if(_pillsRemainingInBottle == -1){
         _pillsRemainingInBottle = _maxPillCountInBottle;
        }

        int pillsLeftFiveDaysFromNow = _pillsRemainingInBottle - (_takeThisManyTabletsAtaTime * _takeMedicationThisManyTimesADay * 5);
        if(pillsLeftFiveDaysFromNow < 1){
            return true;
        }else {
            return false;
        }
    }

    public boolean isCloseToPrescriptionExpiration(){
        boolean isCloseToPrescriptionExpiration = false;

        LocalDate today = LocalDate.now();

        Period timeLeft = Period.between(today, _prescriptionExpiration);
        int years = timeLeft.getYears();
        int months = timeLeft.getMonths();
        int days = timeLeft.getDays();

        if((years < 1) && (months < 1) && (days < 6)){
            isCloseToPrescriptionExpiration = true;
        }

        return isCloseToPrescriptionExpiration;
    }

    public String sendPrescriptionExpirationAlert() {
        LocalDate today = LocalDate.now();
        Period timeLeft = Period.between(today, _prescriptionExpiration);
        PrescriptionExpirationAlert alert = new PrescriptionExpirationAlert(timeLeft, _prescriptionName);
        return alert.sendAlert();
    }

    public String sendLowPillCountAlert(){
        String message = _prescriptionName + " pill count is running low";
        if(_refillsRemaining == 0){
            message = message + " and you have no more refills";
        }
        return message;
    }

    public boolean haveNotAlreadySentPillCountAlertToday(){
        if(_datePillCountAlertWasLastSent == null){
            return true;
        } else {
            Period allZeros = Period.ZERO;
            Period timeSinceLastAlert = Period.between(LocalDate.now(), _datePillCountAlertWasLastSent);
            if(timeSinceLastAlert.equals(allZeros)){
                return false;
            } else{
                return true;
            }
        }
    }

    public boolean haveNotAlreadySentExpirationAlertToday(){
        if(_dateExpirationAlertWasLastSent == null){
            return true;
        } else {
            Period allZeros = Period.ZERO;
            Period timeSinceLastAlert = Period.between(LocalDate.now(), _dateExpirationAlertWasLastSent);
            if(timeSinceLastAlert.equals(allZeros)){
                return false;
            } else{
                return true;
            }
        }
    }

    public boolean isValid() throws MedHandlerException {
        boolean isValid = false;

        if(     (_prescriptionName != null) &&
                (_prescriptionExpiration != null) &&
                (_takeThisManyTabletsAtaTime != 0) &&
                (_milligramDosageInASingleTablet != 0) &&
                (_takeMedicationThisManyTimesADay != 0) &&
                (_maxPillCountInBottle != -1)
        ){
            if(_pillsRemainingInBottle == -1){
                _pillsRemainingInBottle = _maxPillCountInBottle;
            }
            isValid = true;
        } else if(_prescriptionName == null){
            MedHandlerException nullName = new MedHandlerException("Must enter prescription name");
            throw nullName;
        } else if(_prescriptionExpiration == null){
            MedHandlerException nullExpiration = new MedHandlerException("Must enter prescription expiration");
            throw nullExpiration;
        } else if(_takeThisManyTabletsAtaTime == 0){
            MedHandlerException enterNumTablets = new MedHandlerException("Must enter number of tablets to take");
            throw enterNumTablets;
        } else if(_takeMedicationThisManyTimesADay == 0){
            MedHandlerException enterNumTimesADay = new MedHandlerException("Must enter number of times a day to take medication");
            throw enterNumTimesADay;
        }else if(_milligramDosageInASingleTablet == 0){
            MedHandlerException enterDosage = new MedHandlerException("Must enter milligram dosage in a single tablet");
            throw enterDosage;
        } else if(_maxPillCountInBottle == -1){
            MedHandlerException enterTabletsRemaining = new MedHandlerException("Must enter number of tablets that come in bottle");
            throw enterTabletsRemaining;
        }

        return isValid;
    }
}
