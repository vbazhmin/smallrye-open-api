package test.io.smallrye.openapi.runtime.scanner.jakarta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;

@Path(value = "/enum-default-bean-param")
public class DefaultEnumBeanParamTestResource {

    public static class UserGroupDto {
        @FormParam("sql")
        private String sql;
        @FormParam("contactSourceType")
        private Integer contactSourceType;
        @FormParam("archivationTime")
        private String archivationTime;
        @FormParam("type")
        private String type;
        @FormParam("employerIdsText")
        @DefaultValue("EMAIL_BY_SQL")
        private String employerIdsText;
        @FormParam("formType")
        @DefaultValue("EMAIL_BY_EXTERNAL_EMAIL_SOURCES")
        private UserGroupSubtype formType;
        @FormParam("externalEmailSourceCode")
        private String externalEmailSourceCode;
        @FormParam("externalEmailPandoraDoiCode")
        private String externalEmailPandoraDoiCode;
        @JsonIgnore
        @FormParam("file")
        private InputStream inputStream;

        public UserGroupDto() {
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Integer getContactSourceType() {
            return contactSourceType;
        }

        public void setContactSourceType(Integer contactSourceType) {
            this.contactSourceType = contactSourceType;
        }

        public String getArchivationTime() {
            return archivationTime;
        }

        public void setArchivationTime(String archivationTime) {
            this.archivationTime = archivationTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEmployerIdsText() {
            return employerIdsText;
        }

        public void setEmployerIdsText(String employerIdsText) {
            this.employerIdsText = employerIdsText;
        }

        public UserGroupSubtype getFormType() {
            return formType;
        }

        public void setFormType(UserGroupSubtype userGroupSubtype) {
            this.formType = userGroupSubtype;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public String getExternalEmailSourceCode() {
            return externalEmailSourceCode;
        }

        public void setExternalEmailSourceCode(String externalEmailSourceCode) {
            this.externalEmailSourceCode = externalEmailSourceCode;
        }

        public String getExternalEmailPandoraDoiCode() {
            return externalEmailPandoraDoiCode;
        }

        public void setExternalEmailPandoraDoiCode(String externalEmailPandoraDoiCode) {
            this.externalEmailPandoraDoiCode = externalEmailPandoraDoiCode;
        }

        public boolean isFormOfType(UserGroupSubtype userGroupSubtype) {
            return getFormType().equals(userGroupSubtype);
        }

    }

    public enum UserGroupSubtype {
        EMAIL_BY_SQL,
        EMAIL_BY_EMPLOYERS,
        EMAIL_BY_EXTERNAL_EMAIL_SOURCES,
        SMS_BY_SQL,
        PUSH_BY_SQL;
    }



    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@BeanParam UserGroupDto str) {
        return null;
    }

}
