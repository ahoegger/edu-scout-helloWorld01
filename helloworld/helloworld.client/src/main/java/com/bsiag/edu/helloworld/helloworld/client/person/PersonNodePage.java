package com.bsiag.edu.helloworld.helloworld.client.person;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;

/**
 * <h3>{@link PersonNodePage}</h3>
 *
 * @author aho
 */
public class PersonNodePage extends AbstractPageWithNodes {

  private String m_personId;

  /**
   * @param value
   */
  public void setPersonId(String value) {
    m_personId = value;
  }

  public String getPersonId() {
    return m_personId;
  }

  @Override
  protected String getConfiguredTitle() {
    return "person";
  }

  @Override
  public PersonForm getDetailForm() {
    return (PersonForm) super.getDetailForm();
  }

  @Override
  protected void execInitDetailForm() {
    super.execInitDetailForm();
    getDetailForm().setPersonId(getPersonId());
    getDetailForm().startDetailPage();
  }

  @Override
  protected Class<? extends IForm> getConfiguredDetailForm() {
    return PersonForm.class;
  }

}
