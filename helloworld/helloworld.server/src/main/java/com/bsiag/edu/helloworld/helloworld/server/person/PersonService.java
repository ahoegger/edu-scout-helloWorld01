/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package com.bsiag.edu.helloworld.helloworld.server.person;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.bsiag.edu.helloworld.helloworld.shared.person.IPersonService;
import com.bsiag.edu.helloworld.helloworld.shared.person.PersonFormData;
import com.bsiag.edu.helloworld.helloworld.shared.person.PersonTablePageData;

public class PersonService implements IPersonService {

  @Override
  public PersonTablePageData getTableData(SearchFilter filter) {
    PersonTablePageData pageData = new PersonTablePageData();
//    PersonSearchFormData searchData = (PersonSearchFormData) filter.getFormData();

    StringBuilder sqlSelect = new StringBuilder("SELECT   person_id, "
        + "         first_name, "
        + "         last_name "
        + "FROM     PERSON "
        + " INTO     :{page.personId}, "
        + "         :{page.firstname}, "
        + "         :{page.name}");

//    if (searchData != null) {
//      addToWhere(sqlWhere, searchData.getFirstName().getValue(), "first_name", "firstName");
//      addToWhere(sqlWhere, searchData.getLastName().getValue(), "last_name", "lastName");
//      addToWhere(sqlWhere, searchData.getLocation().getCity().getValue(), "city", "location.city");
//      addToWhere(sqlWhere, searchData.getLocation().getCountry().getValue(), "country", "location.country");
//      addToWhere(sqlWhere, searchData.getOrganization().getValue(), "organization_id", "organization");
//      addToWhere(sqlWhere, organizationId, "organization_id", "organizationId");
//    }

    SQL.selectInto(sqlSelect.toString(), new NVPair("page", pageData));

    return pageData;
  }

  @Override
  public PersonFormData load(PersonFormData data) {

    SQL.selectInto(
        "SELECT   first_name, "
            + "         last_name, "
            + "         date_of_birth "
            + "FROM     PERSON "
            + "WHERE    person_id = :personId "
            + "INTO     :firstname, "
            + "         :name, "
            + "         :birthday",
        data);
    return data;
  }

  @Override
  public PersonFormData store(PersonFormData data) {
    Calendar cal = new GregorianCalendar();
    if (data.getBirthday().getValue() != null) {
      cal.setTime(data.getBirthday().getValue());
    }
    Calendar now = new GregorianCalendar();
    int res = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
    if (res < 18) {
      throw new VetoException("Age violation").withTitle("Persons must be over 18!").withSeverity(IStatus.WARNING);
    }
    return null;
  }

}
