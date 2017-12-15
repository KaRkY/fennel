package org.fennel.users;

import org.fennel.users.api.user.UserQueryObject;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler extends ResourceAssemblerSupport<UserQueryObject, UserResource> {

  public UserResourceAssembler() {
    super(UsersController.class, UserResource.class);
  }

  @Override
  public UserResource toResource(final UserQueryObject entity) {
    final UserResource resource = createResourceWithId(entity.getUserId(), entity);
    resource.setDisplayName(entity.getDisplayName());
    resource.setUsername(entity.getUsername());
    resource.setLocked(entity.isLocked());

    return resource;
  }

}
