package tec.bd.app.database.set;

import tec.bd.app.domain.Entity;

import java.util.Map;
import java.util.Set;

public class SetDB {

    private Map<Class<? extends Entity>, Set<Row>> tables;

    public SetDB(Map<Class<? extends Entity>, Set<Row>> tables) {
        this.tables = tables;
    }

    public <T> Set<Row> getTableByClassName(Class<T> clazz) {
        return this.tables.get(clazz);
    }
}
