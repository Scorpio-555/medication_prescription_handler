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
            Exception takeMedLessThanOnceADay = new Exception("Number of times a day to take medication must be greater than zero");
            throw takeMedLessThanOnceADay;
        } else {
            _takeMedicationThisManyTimesADay = takeMedicationThisManyTimesADay;
        }
    }

    public void setMilligramDosageInASingleTablet(int milligramDosageInASinglePill) throws Exception{
        if(milligramDosageInASinglePill < 1){
            Exception singlePillDosageIsLessThanOneMg = new Exception("Dosage of a single pill must be greater than zero");
            throw singlePillDosageIsLessThanOneMg;
        } else {
            _milligramDosageInASingleTablet = milligramDosageInASinglePill;
        }
    }

    public void setTakeThisManyTabletsAtaTime(int takeThisManyTabletsAtaTime) throws Exception{
        if(takeThisManyTabletsAtaTime < 1){
            Exception NumberOfTabletsIsLessThanOne = new Exception("Number of Tablets must be greater than zero");
            throw NumberOfTabletsIsLessThanOne;
        } else {
            _takeThisManyTabletsAtaTime = takeThisManyTabletsAtaTime;
        }
    }

    public void setMaxPillCountInBottle(int maxPillCountInBottle) throws Exception{
        if(maxPillCountInBottle < 0){
            Exception maxPillCountIsLessThanZero = new Exception("number of tablets that comes in bottle can't be negative");
            throw maxPillCountIsLessThanZero;
        } else {
            _maxPillCountInBottle = maxPillCountInBottle;
        }
    }

    public void setPillsRemainingInBottle(int pillsRemainingInBottle) throws Exception{
        if(pillsRemainingInBottle < 0){
            Exception pillCountIsLessThanZero = new Exception("Pills remaining can't be negative");
            throw pillCountIsLessThanZero;
        } else {
            _pillsRemainingInBottle = pillsRemainingInBottle;
        }
    }

    public void setRefillsRemaining(int refillsRemaining) throws Exception{
        if(refillsRemaining < 0){
            Exception refillCountIsLessThanZero = new Exception("number of refills can't be negative");
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

        prescriptionName = prescriptionName.trim();

       if(prescriptionName.isEmpty()){
           Exception nullPrescriptionName = new Exception("Prescription Name must be filled out");
           throw nullPrescriptionName;
        }else if(prescriptionName.length() < 4) {
           Exception tooShortPrescriptionName = new Exception("Prescription Name must be at least four letters long");
           throw tooShortPrescriptionName;
        }else if(containsBadCharacters){
           Exception nonLetterPrescriptionName = new Exception("Prescription Name must only contain letters");
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
            Exception invalidDate = new Exception("Invalid date");
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

    public boolean isValid() throws Exception {
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
            Exception nullName = new Exception("Must enter prescription name");
            throw nullName;
        } else if(_prescriptionExpiration == null){
            Exception nullExpiration = new Exception("Must enter prescription expiration");
            throw nullExpiration;
        } else if(_takeThisManyTabletsAtaTime == 0){
            Exception enterNumTablets = new Exception("Must enter number of tablets to take");
            throw enterNumTablets;
        } else if(_takeMedicationThisManyTimesADay == 0){
            Exception enterNumTimesADay = new Exception("Must enter number of times a day to take medication");
            throw enterNumTimesADay;
        }else if(_milligramDosageInASingleTablet == 0){
            Exception enterDosage = new Exception("Must enter milligram dosage in a single tablet");
            throw enterDosage;
        } else if(_maxPillCountInBottle == -1){
            Exception enterTabletsRemaining = new Exception("Must enter number of tablets that come in bottle");
            throw enterTabletsRemaining;
        }

        return isValid;
    }
}
