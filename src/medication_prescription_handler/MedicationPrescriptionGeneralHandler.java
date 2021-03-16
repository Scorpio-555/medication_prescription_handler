package medication_prescription_handler;

import java.util.ArrayList;
import java.util.List;

public class MedicationPrescriptionGeneralHandler {
    private List<SingleMedicationPrescriptionHandler> prescriptionList;

    MedicationPrescriptionGeneralHandler(){
        prescriptionList = new ArrayList<>();
    }

    MedicationPrescriptionGeneralHandler(List<SingleMedicationPrescriptionHandler> list){
        prescriptionList = list;
    }

    private void sendTakePillAlerts(){
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            handler.sendTakePillAlert();
        }
    }

    private void sendPrescriptionExpirationAlerts(){
        for(SingleMedicationPrescriptionHandler handler: prescriptionList){
            if(handler.isCloseToPrescriptionExpiration()){
                handler.sendPrescriptionExpirationAlert();
            }
        }
    }

    public void addHandler(SingleMedicationPrescriptionHandler newHandler){
        if(newHandler.isValid()){

            boolean newHandlerIsUnique = true;
            for(SingleMedicationPrescriptionHandler existingHandler: prescriptionList){

                if(     newHandler.getPrescriptionName().equals(existingHandler.getPrescriptionName()) &&
                        newHandler.getMilligramDosageInASingleTablet() == existingHandler.getMilligramDosageInASingleTablet()
                ){
                    newHandlerIsUnique = false;
                }
            }

            if(newHandlerIsUnique){
                prescriptionList.add(newHandler);
            } else{
                System.out.println("Didn't save! You already have saved a prescription of the same name and dosage");
                System.out.println();
            }

        }

    }

    public void run(){
        sendTakePillAlerts();
        sendPrescriptionExpirationAlerts();
    }
}
