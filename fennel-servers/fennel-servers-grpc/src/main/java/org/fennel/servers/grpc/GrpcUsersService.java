package org.fennel.servers.grpc;

import org.fennel.servers.users.grpc.CreateUserResponse;
import org.fennel.servers.users.grpc.UserRequest;
import org.fennel.servers.users.grpc.UsersGrpc.UsersImplBase;
import org.fennel.users.services.User;
import org.fennel.users.services.UserService;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;

public class GrpcUsersService extends UsersImplBase {
  private final UserService userService;

  public GrpcUsersService(final UserService userService) {
    this.userService = userService;
  }

  @Override
  public void createUser(final UserRequest request, final StreamObserver<CreateUserResponse> responseObserver) {
    Context.<String>key("userId").get(); // User populated in interceptor
    final String userId = userService.create(User.builder()
      .displayName(request.getDisplayName())
      .username(request.getUsername())
      .password(request.getPassword())
      .build(), null);

    responseObserver.onNext(CreateUserResponse.newBuilder()
      .setUserId(userId)
      .build());
  }
}
