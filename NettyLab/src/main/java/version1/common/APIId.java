package version1.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class APIId implements Serializable {
    private String namespace;
    private String name;
    private int version;

    public APIId(String namespace, String name) {
        this(namespace, name, 1);
    }
}
