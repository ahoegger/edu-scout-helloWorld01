package com.bsiag.edu.helloworld.helloworld.client.person;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;

import com.bsiag.edu.helloworld.helloworld.client.person.PersonForm.MainBox.BirthdayField;
import com.bsiag.edu.helloworld.helloworld.client.person.PersonForm.MainBox.CloseButton;
import com.bsiag.edu.helloworld.helloworld.client.person.PersonForm.MainBox.FirstnameField;
import com.bsiag.edu.helloworld.helloworld.client.person.PersonForm.MainBox.ModifyButton;
import com.bsiag.edu.helloworld.helloworld.client.person.PersonForm.MainBox.NameField;
import com.bsiag.edu.helloworld.helloworld.client.person.PersonForm.MainBox.OkButton;
import com.bsiag.edu.helloworld.helloworld.shared.person.IPersonService;
import com.bsiag.edu.helloworld.helloworld.shared.person.PersonFormData;

/**
 * <h3>{@link PersonForm}</h3>
 *
 * @author aho
 */
@FormData(value = PersonFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class PersonForm extends AbstractForm {

  private String m_personId;

  @FormData
  public String getPersonId() {
    return m_personId;
  }

  @FormData
  public void setPersonId(String personId) {
    m_personId = personId;
  }

  @Override
  public Object computeExclusiveKey() {
    return getPersonId();
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return IForm.DISPLAY_HINT_VIEW;
  }

  public void startDetailPage() {
    startInternalExclusive(new DetailPageHandler());
  }

  public void startModify() {
    startInternalExclusive(new ModifyHandler());
  }

  /**
   * @return
   */
  public BirthdayField getBirthdayField() {
    return getFieldByClass(BirthdayField.class);
  }

  /**
   * @return
   */
  public ModifyButton getModifyButton() {
    return getFieldByClass(ModifyButton.class);
  }

  /**
   * @return
   */
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  /**
   * @return
   */
  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  /**
   * @return
   */
  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  /**
   * @return
   */
  public FirstnameField getFirstnameField() {
    return getFieldByClass(FirstnameField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected String getConfiguredBorderDecoration() {
      return IGroupBox.BORDER_DECORATION_EMPTY;
    }

    @Order(1000.0)
    public class FirstnameField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Firstname");
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 128;
      }
    }

    @Order(2000.0)
    public class NameField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Name");
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 128;
      }
    }

    @Order(3000.0)
    public class BirthdayField extends AbstractDateField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Birthday");
      }

      @Override
      protected Date execValidateValue(Date rawValue) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(rawValue);
        Calendar now = new GregorianCalendar();
        int res = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if (res < 18) {

        }
        return super.execValidateValue(rawValue);
      }
    }

    @Order(4000.0)
    public class ModifyButton extends AbstractButton {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Modify");
      }

      @Override
      protected void execClickAction() {
        PersonForm form = new PersonForm();
        form.setPersonId(getPersonId());
        form.startModify();
      }
    }

    @Order(4500.0)
    public class OkButton extends AbstractOkButton {

    }

    @Order(5000.0)
    public class CloseButton extends AbstractCancelButton {

      @Override
      protected void execClickAction() {
      }
    }

  }

  public class ModifyHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      PersonFormData data = new PersonFormData();
      exportFormData(data);
      data = BEANS.get(IPersonService.class).load(data);
      importFormData(data);
      setTitle(data.getFirstname().getValue() + " " + data.getName().getValue());
      getModifyButton().setVisible(false);

    }

    @Override
    protected void execStore() {
      PersonFormData data = new PersonFormData();
      exportFormData(data);
      BEANS.get(IPersonService.class).store(data);
    }

    @Override
    protected boolean getConfiguredOpenExclusive() {
      return true;
    }
  }

  public class DetailPageHandler extends AbstractFormHandler {
    @Override
    protected void execLoad() {
      PersonFormData data = new PersonFormData();
      exportFormData(data);
      importFormData(BEANS.get(IPersonService.class).load(data));
      setAllEnabled(false);
      getModifyButton().setEnabled(true);
      getCloseButton().setVisible(false);
      getOkButton().setVisible(false);
    }

  }

}
