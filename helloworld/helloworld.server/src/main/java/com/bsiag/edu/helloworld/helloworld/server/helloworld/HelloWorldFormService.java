package com.bsiag.edu.helloworld.helloworld.server.helloworld;

import com.bsiag.edu.helloworld.helloworld.server.ServerSession;
import com.bsiag.edu.helloworld.helloworld.shared.helloworld.HelloWorldFormData;
import com.bsiag.edu.helloworld.helloworld.shared.helloworld.IHelloWorldFormService;

/**
 * <h3>{@link HelloWorldFormService}</h3>
 *
 * @author aho
 */
public class HelloWorldFormService implements IHelloWorldFormService {

  @Override
  public HelloWorldFormData load(HelloWorldFormData input) {
    StringBuilder msg = new StringBuilder();
    msg.append("Hello ").append(ServerSession.get().getUserId()).append("!");
    input.getMessage().setValue(msg.toString());
    return input;
  }
}
