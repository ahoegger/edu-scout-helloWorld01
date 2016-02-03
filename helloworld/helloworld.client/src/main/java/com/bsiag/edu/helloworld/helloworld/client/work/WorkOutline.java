package com.bsiag.edu.helloworld.helloworld.client.work;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

import com.bsiag.edu.helloworld.helloworld.client.helloworld.HelloWorldPage;
import com.bsiag.edu.helloworld.helloworld.client.person.PersonTablePage;
import com.bsiag.edu.helloworld.helloworld.shared.Icons;

/**
 * <h3>{@link WorkOutline}</h3>
 *
 * @author aho
 */
@Order(1000)
public class WorkOutline extends AbstractOutline {

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    super.execCreateChildPages(pageList);
    pageList.add(new HelloWorldPage());
    pageList.add(new PersonTablePage());
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Work");
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.Pencil;
  }
}
