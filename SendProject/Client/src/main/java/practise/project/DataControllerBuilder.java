package practise.project;

import DataController.DataControllerSql;
import DataController.IDataController;
import practise.Config;
import practise.ControllerType;

import java.sql.SQLException;

public class DataControllerBuilder {
    public IDataController DataController;

    public static IDataController getDataController() throws SQLException, ClassNotFoundException {
        if (Config.ControllerType == ControllerType.Sql)
            return new DataControllerSql();
        else
            return new DataControllerHttp();
    }

}
