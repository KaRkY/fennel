package org.fennel.users;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(value = "user", collectionRelation = "users")
public class UserResource extends ResourceSupport {

  private String  displayName;
  private String  username;
  private boolean locked;
}
