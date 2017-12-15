package org.fennel.users;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(value = "userCreationProcess", collectionRelation = "userCreationProcesses")
public class UserCreationProcessResource extends ResourceSupport {

  private String displayName;
  private String username;
  private String password;
  private String state;

}
