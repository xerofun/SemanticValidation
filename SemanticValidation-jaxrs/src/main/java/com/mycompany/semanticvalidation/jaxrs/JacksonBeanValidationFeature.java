package com.mycompany.semanticvalidation.jaxrs;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import org.glassfish.jersey.CommonProperties;

/**
 *
 * @author Brian
 */
public class JacksonBeanValidationFeature implements Feature
{

    public boolean configure(final FeatureContext featureContext)
    {
        final String disableMoxy = CommonProperties.MOXY_JSON_FEATURE_DISABLE
                + "." + featureContext.getConfiguration().getRuntimeType().name().toLowerCase();
        
        featureContext.property(disableMoxy, true);
        featureContext.register(ValidatingJaxbJsonProvider.class, 
                MessageBodyReader.class, MessageBodyWriter.class);
        
        return true;
    }

}
