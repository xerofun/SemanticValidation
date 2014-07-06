package com.mycompany.semanticvalidation.jaxrs;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

/**
 *
 * @author Brian
 */
public class ValidatingJaxbJsonProvider extends JacksonJaxbJsonProvider
{
    private final ValidatorFactory validationFactory =
            Validation.buildDefaultValidatorFactory();

    @Override
    public Object readFrom(Class<Object> type, Type genericType, 
            Annotation[] annotations, MediaType mediaType, 
            MultivaluedMap<String,String> httpHeaders, InputStream entityStream) 
            throws IOException
    {
        // Deserialize the entity
        Object unmarshalledObject = super.readFrom(type, genericType, 
                annotations, mediaType, httpHeaders, entityStream);
        
        // Validate entity
        Validator validator = validationFactory.getValidator();
        
        Set<ConstraintViolation<Object>> constraintViolations =
                validator.validate(unmarshalledObject);
        
        if (constraintViolations.isEmpty())
        {   // Successfully validated
            // TODO: Remove sysout
            System.out.println("Validation Success");
            return unmarshalledObject;
        }
        else
        {
            for (ConstraintViolation<Object> violation : constraintViolations)
            {   // TODO: replace sysout with logging
                System.out.print("Field " + violation.getPropertyPath());
                System.out.print(" is not a valid ");
                System.out.println(violation.getMessage());
            }
            
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
        }
        
    }
    
    
}
