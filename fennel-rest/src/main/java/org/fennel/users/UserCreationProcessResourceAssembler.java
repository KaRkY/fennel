package org.fennel.users;

import org.fennel.users.api.usercreationprocess.UserCreationProcessQueryObject;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserCreationProcessResourceAssembler
  extends ResourceAssemblerSupport<UserCreationProcessQueryObject, UserCreationProcessResource> {

  public UserCreationProcessResourceAssembler() {
    super(UserCreationProcessesController.class, UserCreationProcessResource.class);
  }

  @Override
  public UserCreationProcessResource toResource(final UserCreationProcessQueryObject entity) {
    final UserCreationProcessResource resource = createResourceWithId(entity.getProcessId(), entity);
    resource.setDisplayName(entity.getDisplayName());
    resource.setUsername(entity.getUsername());
    resource.setPassword(entity.getPassword());
    resource.setState(entity.getState());

    return resource;
  }

}
