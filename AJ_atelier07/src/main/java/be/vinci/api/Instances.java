package be.vinci.api;

import be.vinci.classes.User;
import be.vinci.instances.InstanceGraph1;
import be.vinci.services.ClassAnalyzer;
import be.vinci.services.InstancesAnalyzer;
import be.vinci.utils.InstanceGraphBuilder;
import jakarta.json.JsonStructure;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Send instances graph data to make object diagrams
 *
 * The instances graphs are initialized by a class containing the "initInstanceGraph" method,
 * building the instance graph, and returning it.
 *
 * The "instance builder class name" must be given and present into the "instances" package
 */
@Path("instances")
public class Instances {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonStructure getInstanceGraphInfo(@QueryParam("builderclassname") String builderClassname) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> builder;
        try {
            builder = Class.forName("be.vinci.instances." + builderClassname);
        } catch (ClassNotFoundException ex) {
            throw new WebApplicationException(404);
        }
        try {
            for (Method method : builder.getDeclaredMethods()) {
                if (method.isAnnotationPresent(InstanceGraphBuilder.class)) {
                    Object instanceGraph = method.invoke(builder.newInstance());
                    InstancesAnalyzer analyzer = new InstancesAnalyzer(instanceGraph);
                    return analyzer.getFullInfo();
                }
            }
            return null;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException ex) {
            throw new WebApplicationException(500);
        }
    }
}
