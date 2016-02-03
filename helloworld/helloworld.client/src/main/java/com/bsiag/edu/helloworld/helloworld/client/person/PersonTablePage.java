package com.bsiag.edu.helloworld.helloworld.client.person;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class PersonTablePage extends AbstractPageWithTable<PersonTablePage.PersonTable> {
	@Override
	protected String getConfiguredTitle() {
		return "Persons";
	}

	@Override
	protected void execInitPage() {

	}
	@Override
	protected void execLoadData(SearchFilter filter) {
		getTable().deleteAllRows();
		List<ITableRow> rows = new ArrayList<ITableRow>();
		rows.add(getTable().createRow(new Object[]{0,"Hugo", "Boss"}));
		rows.add(getTable().createRow(new Object[]{1,"Erich", "Gamma"}));
		rows.add(getTable().createRow(new Object[]{2,"Ralph", "Mueller"}));
		rows.add(getTable().createRow(new Object[]{3,"Heinz", "Mueller"}));
		getTable().addRows(rows);
	}
	
	public class PersonTable extends AbstractTable {
		@Order(10)
		public class PrimaryKeyColumn extends AbstractIntegerColumn {
			@Override
			protected boolean getConfiguredVisible() {
				return false;
			}
		}

		@Order(20)
		public class NameColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return "Name";
			}
		}

		@Order(30)
		public class FirstnameColumn extends AbstractStringColumn {

			@Override
			protected String getConfiguredHeaderText() {
				return "Firstname";
			}
		}
	}
}
