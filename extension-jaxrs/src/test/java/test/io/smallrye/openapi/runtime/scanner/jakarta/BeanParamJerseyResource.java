package test.io.smallrye.openapi.runtime.scanner.jakarta;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/bean-param-jersey")
@Produces(MediaType.APPLICATION_JSON)
public class BeanParamJerseyResource {

  public static class JerseyBean {

    @FormDataParam("primitiveString")
    String string;

    @FormDataParam("primitiveInteger")
    Integer integer;

    public String getString() {
      return string;
    }

    public void setString(String string) {
      this.string = string;
    }

    public Integer getInteger() {
      return integer;
    }

    public void setInteger(Integer integer) {
      this.integer = integer;
    }
  }

  @POST
  @Consumes("multipart/form-data")
  public String receiveMultipart(@BeanParam JerseyBean jerseyBean) throws WebApplicationException {
    return "OK";
  }
}
