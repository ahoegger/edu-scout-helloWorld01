package com.bsiag.edu.helloworld.helloworld.server.sql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.CreateImmediately;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.exception.PlatformExceptionTranslator;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringArrayHolder;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.jdbc.derby.AbstractDerbySqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bsiag.edu.helloworld.helloworld.server.SuperUserRunContextProducer;

@CreateImmediately
public class DerbySqlService extends AbstractDerbySqlService {
  private static final Logger LOG = LoggerFactory.getLogger(DerbySqlService.class);

  @PostConstruct
  protected void setupDb() {
    try {
      BEANS.get(SuperUserRunContextProducer.class).produce().run(new IRunnable() {

        @Override
        public void run() throws Exception {
          createPersonTable(getExistingTables());
        }
      });
    }
    catch (RuntimeException e) {
      BEANS.get(ExceptionHandler.class).handle(e);
    }

  }

  @Override
  protected String getConfiguredJdbcMappingName() {
    return "jdbc:derby:memory:contacts-database;create=true";
  }

  /**
   * Drop in-memory database
   */
  public void dropDB() {
    try {
      DriverManager.getConnection("jdbc:derby:memory:contacts-database;drop=true");
    }
    catch (SQLException e) {
      BEANS.get(PlatformExceptionTranslator.class).translate(e);
    }
  }

  protected Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    selectInto(
        "SELECT   UPPER(tablename) "
            + "FROM     sys.systables "
            + "INTO     :result",
        new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  private void createPersonTable(Set<String> tables) {
    if (!tables.contains("PERSON")) {
      insert("CREATE   TABLE PERSON "
          + "         (person_id VARCHAR(64) NOT NULL CONSTRAINT PERSON_PK PRIMARY KEY, "
          + "          first_name VARCHAR(64), "
          + "          last_name VARCHAR(64), "
          + "          date_of_birth DATE )");
      LOG.info("Database table 'PERSON' created");

      createPersonEntry("Hugo", "Boss", new Date());
      createPersonEntry("Ralph", "Mueller", new Date());
      createPersonEntry("Heinz", "Mueller", new Date());

      LOG.info("Database table 'PERSON' populated with sample data");
    }

  }

  private void createPersonEntry(String firstName, String lastName, Date birthday) {
    insert(
        "INSERT   INTO PERSON "
            + "         (person_id, "
            + "          first_name, "
            + "          last_name, "
            + "          date_of_birth) "
            + "VALUES   (:person_id, "
            + "          :firstName, "
            + "          :lastName, "
            + "          :birthday "
            + "           )",
        new NVPair("person_id", UUID.randomUUID().toString()),
        new NVPair("firstName", firstName),
        new NVPair("lastName", lastName),
        new NVPair("birthday", birthday));
  }

}
