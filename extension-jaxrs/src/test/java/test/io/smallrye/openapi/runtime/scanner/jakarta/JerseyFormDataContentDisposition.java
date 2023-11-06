package test.io.smallrye.openapi.runtime.scanner.jakarta;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path(value = "/jersey-form-data-content-disposition")
@Produces(MediaType.TEXT_PLAIN)
public class JerseyFormDataContentDisposition {

  @POST
  @Consumes("multipart/form-data")
  public String receiveFileAndContentDisposition(
      @FormDataParam("file") InputStream file,
      @FormDataParam("file") FormDataContentDisposition fileContentDispositionHeader
  ) throws WebApplicationException {
    return "OK";
  }
}
