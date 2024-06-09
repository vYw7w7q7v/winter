package di_engine.db.annotation.configurator;

import di_engine.core.Application;
import di_engine.core.Configurator;
import di_engine.db.annotation.DBConnection;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionConfigurator implements Configurator {
    public DBConnectionConfigurator() {
    }

    @SneakyThrows
    public <T> void configure(T object) {
        Field[] var2 = object.getClass().getDeclaredFields();

        for (Field field : var2) {
            if (field.isAnnotationPresent(DBConnection.class)) {
                field.setAccessible(true);
                DBConnection DBConnectionFieldAnnotation = field.getAnnotation(DBConnection.class);
                String db_name = DBConnectionFieldAnnotation.db_name();
                Connection connection = Application.getAppContext().getConnection(String.format("%s_connection", db_name));
                if (connection == null) {
                    String db_url = Application.getAppContext().getConfig().getConfigValue(db_name + "_url");
                    String db_user = Application.getAppContext().getConfig().getConfigValue(db_name + "_user");
                    String db_password = Application.getAppContext().getConfig().getConfigValue(db_name + "_password");
                    if (db_url == null || db_user == null) {
                        throw new RuntimeException(String.format("Не удалось подключиться к базе данных %s!!!\nПроверьте корректность полей %s_url, %s_user, %s_password!!!", db_name, db_name, db_name, db_name));
                    }

                    db_password = db_password == null ? "" : db_password;

                    try {
                        connection = DriverManager.getConnection(db_url, db_user, db_password);
                    } catch (SQLException var13) {
                        throw new RuntimeException(String.format("Не удалось подключить к базе данных %s по указанному в конфиге url", db_name));
                    }

                    field.set(object, connection);
                    Application.getAppContext().putConnection(String.format("%s_connection", db_name), connection);
                }

                field.set(object, connection);
            }
        }

    }
}