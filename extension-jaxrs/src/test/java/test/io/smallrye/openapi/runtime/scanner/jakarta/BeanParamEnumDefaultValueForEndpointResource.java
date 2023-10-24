package test.io.smallrye.openapi.runtime.scanner.jakarta;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path(value = "/bean-param-enum-default-value-for-endpoint")
public class BeanParamEnumDefaultValueForEndpointResource {

    public static class SimpleBeanParam {
        @FormParam("petEnum")
        @DefaultValue("LIZARD")
        private PetEnum petEnum;

        public SimpleBeanParam() {
        }

        public PetEnum getPetEnum() {
            return petEnum;
        }

        public void setPetEnum(PetEnum petEnum) {
            this.petEnum = petEnum;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@BeanParam SimpleBeanParam beanParam) {
        return Response.ok(beanParam.getPetEnum().name()).build();
    }
}
