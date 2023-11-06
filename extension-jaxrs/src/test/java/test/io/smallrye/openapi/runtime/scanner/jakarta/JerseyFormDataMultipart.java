package test.io.smallrye.openapi.runtime.scanner.jakarta;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path(value = "/jersey-form-data-multi-part")
@Produces(MediaType.TEXT_PLAIN)
public class JerseyFormDataMultipart {

  @POST
  @Consumes("multipart/form-data")
  public String receiveMultipart(@FormDataParam("multipartView") FormDataMultiPart multipartView) throws WebApplicationException {
    return "OK";
  }
}
