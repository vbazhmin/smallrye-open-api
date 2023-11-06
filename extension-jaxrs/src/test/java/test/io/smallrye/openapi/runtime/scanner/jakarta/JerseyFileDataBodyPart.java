package test.io.smallrye.openapi.runtime.scanner.jakarta;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

@Path(value = "/jersey-file-data-body-part")
@Produces(MediaType.TEXT_PLAIN)
public class JerseyFileDataBodyPart {

  @POST
  @Consumes("multipart/form-data")
  public String receiveFile(@FormDataParam("file") FileDataBodyPart fileDataBodyPart) throws WebApplicationException {
    return "OK";
  }
}
