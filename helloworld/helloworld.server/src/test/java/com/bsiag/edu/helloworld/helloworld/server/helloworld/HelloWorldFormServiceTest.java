package com.bsiag.edu.helloworld.helloworld.server.helloworld;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bsiag.edu.helloworld.helloworld.server.ServerSession;
import com.bsiag.edu.helloworld.helloworld.shared.helloworld.HelloWorldFormData;
import com.bsiag.edu.helloworld.helloworld.shared.helloworld.IHelloWorldFormService;

/**
 * <h3>{@link HelloWorldFormServiceTest}</h3>
 *
 * @author aho
 */
@RunWith(ServerTestRunner.class)
@RunWithSubject(HelloWorldFormServiceTest.SUBJECT_NAME)
@RunWithServerSession(ServerSession.class)
public class HelloWorldFormServiceTest {
  public static final String SUBJECT_NAME = "test_subject";

  @Test
  public void testMessageContainsSubjectName() {
    HelloWorldFormData input = new HelloWorldFormData();
    input = BEANS.get(IHelloWorldFormService.class).load(input);

    Assert.assertNotNull(input.getMessage());
    Assert.assertEquals("Hello " + SUBJECT_NAME + "!", input.getMessage().getValue());
  }
}
