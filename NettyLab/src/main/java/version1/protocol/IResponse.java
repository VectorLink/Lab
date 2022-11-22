package version1.protocol;

import lombok.Data;
import version1.common.APIId;
@Data
public abstract class IResponse<T> {
  APIId apiId;
}
