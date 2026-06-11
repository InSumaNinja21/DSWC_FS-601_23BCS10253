package Day6.Q2;
package com.medscan.radiology;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Lazy
class ImageRenderingEngine {

    public ImageRenderingEngine() {
        System.out.println("ImageRenderingEngine Initialized");
    }

    public void renderImage() {
        System.out.println("Rendering MRI Image...");
    }
}

@Component
@Scope("prototype")
class PatientContext {

    private String patientId;

    public PatientContext() {
        System.out.println("New PatientContext Created : " + this);
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }
}

@Service
class ScanOrchestrator {

    private final ImageRenderingEngine renderingEngine;
    private final ObjectProvider<PatientContext> patientContextProvider;

    public ScanOrchestrator(
            @Lazy ImageRenderingEngine renderingEngine,
            ObjectProvider<PatientContext> patientContextProvider) {

        this.renderingEngine = renderingEngine;
        this.patientContextProvider = patientContextProvider;
    }

    public void processMRI(String patientId) {

        PatientContext context = patientContextProvider.getObject();

        context.setPatientId(patientId);

        System.out.println("Processing Scan For Patient : "
                + context.getPatientId());

        renderingEngine.renderImage();

        System.out.println("Using Context Instance : "
                + context);
    }
}
