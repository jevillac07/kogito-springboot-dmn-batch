package com.bcp.brms;

import com.bcp.brms.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

public class KogitoSpringbootApplication {

    public static void main(String[] args) throws JsonProcessingException {
        if (args.length != 0) {
            ObjectMapper objectMapper = new ObjectMapper();

            KieServices ks = KieServices.Factory.get();
            KieContainer kc = ks.getKieClasspathContainer();

            DMNRuntime dmnRuntime = KieRuntimeFactory.of(kc.getKieBase()).get(DMNRuntime.class);

            String namespace = "https://kie.apache.org/dmn/_3538F99F-C3AD-4A15-9892-31F044EA7B2C";
            String modelName = "Age2";
            DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);

            if (dmnModel == null) {
                System.err.println("There isn't a DMN model");
                return;
            }

            for (String inputJson : args) {
                Customer customer = objectMapper.readValue(inputJson, Customer.class);
                DMNContext dmnContext = dmnRuntime.newContext();
                dmnContext.set("Customer", customer);
                DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

                if (dmnResult.hasErrors()) {
                    System.err.println("Error in the DMN model for input: " + inputJson);
                    dmnResult.getMessages().forEach(System.err::println);
                } else {
                    System.out.println("Response DMN: " + dmnResult.getContext().get("Type"));
                }
            }
        }
    }
}
