package utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyCookie{
    private String domain;
    @JsonIgnore
    private Date expirationDate;
    @JsonIgnore
    private Boolean hostOnly;
    private boolean httpOnly;
    private String path;
    @JsonIgnore
    private String sameSite;
    private String name;
    private boolean secure;
    @JsonIgnore
    private  boolean session;
    @JsonIgnore
    private String storeId;
    private String value;
    @JsonIgnore
    private long id;
}
