package medication_prescription_handler;

import java.util.List;

public class MedicationPrescriptionGeneralHandler {
    private List<SingleMedicationPrescriptionHandler> prescriptionList;

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

    public void run(){
        sendTakePillAlerts();
        sendPrescriptionExpirationAlerts();
    }
}
