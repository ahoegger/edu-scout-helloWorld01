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
package com.bsiag.edu.helloworld.helloworld.shared.person;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@ApplicationScoped
@TunnelToServer
public interface IPersonService {

  PersonTablePageData getTableData(SearchFilter filter);

  /**
   * @param data
   */
  PersonFormData load(PersonFormData data);

  /**
   * @param data
   * @return
   */
  PersonFormData store(PersonFormData data);

}
