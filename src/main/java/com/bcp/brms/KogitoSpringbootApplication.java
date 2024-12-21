package com.bcp.brms;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.api.core.DMNDecisionResult;

public class KogitoSpringbootApplication {

    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();

        DMNRuntime dmnRuntime = KieRuntimeFactory.of(kc.getKieBase()).get(DMNRuntime.class);

        String namespace = "https://kie.apache.org/dmn/_DFA23257-136C-42AE-B380-2E6712FD3B46";
        String modelName = "Age";
        DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);
        DMNContext dmnContext = dmnRuntime.newContext();
        dmnContext.set("Age", 15);
        DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

        for(DMNDecisionResult dr : dmnResult.getDecisionResults()) {
            System.out.println("Age: 15 - Decision: " + dr.getDecisionName() + " - Result: " + dr.getResult());
        }
    }
}
