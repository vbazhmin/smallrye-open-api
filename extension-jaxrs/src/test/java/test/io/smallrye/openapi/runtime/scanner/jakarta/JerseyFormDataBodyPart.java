package test.io.smallrye.openapi.runtime.scanner.jakarta;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import java.util.Collection;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path(value = "/jersey-form-data-body-part")
@Produces(MediaType.TEXT_PLAIN)
public class JerseyFormDataBodyPart {

  @POST
  @Consumes("multipart/form-data")
  public String receiveBodyParts(@FormDataParam("collectionOfBodyParts") Collection<FormDataBodyPart> bodyParts) throws WebApplicationException {
    return "OK";
  }
}
