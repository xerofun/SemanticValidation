package com.mycompany.boundary;

import com.mycompany.entities.User;
import com.mycompany.service.UserServiceLocal;
import java.net.URI;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author Brian
 */
@Path("User")
public class UserResource
{
    private UserServiceLocal userService;

    @Context
    private UriInfo uriInfo;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource()
    {
        loadUserService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers()
    {
        return Response.ok(userService.getUsers()).build();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") final long id)
    {
        return Response.ok(userService.getUser(id)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user)
    {
        userService.createUser(user);

        // Build URI to newly created resource
        URI userURI = uriInfo.getRequestUriBuilder().path("{id}").build(user.getId());
        
        return Response.created(userURI).build();
    }
    
    
    private void loadUserService()
    {
        System.out.println("User Service: " + userService);
        
        try
        {
            javax.naming.Context initialContext = new InitialContext();
            
            userService = (UserServiceLocal) initialContext.lookup("java:/comp/env/ejb/UserServiceLocal");
            
            System.out.println("User Service: " + userService);
        }
        catch (NamingException ex)
        {
            System.out.println("Naming Exception in UserResource");
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
