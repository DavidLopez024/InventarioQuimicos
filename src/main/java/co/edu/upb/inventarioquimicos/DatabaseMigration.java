package co.edu.upb.inventarioquimicos;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DatabaseMigration implements ApplicationRunner {

    private static final Logger LOGGER = Logger.getLogger(DatabaseMigration.class.getName());

    private final JdbcTemplate jdbcTemplate;

    public DatabaseMigration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            dropObsoleteInventarioIdColumn();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                "DatabaseMigration failed — app may crash later if inventario_id column " +
                "still has NOT NULL constraint. Run manually: " +
                "ALTER TABLE movimientos_inv DROP FOREIGN KEY <name>; " +
                "ALTER TABLE movimientos_inv DROP COLUMN inventario_id;", e);
        }
        try {
            dropObsoleteLoteIdColumn();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                "DatabaseMigration failed — app may crash if lote_id column " +
                "still has NOT NULL constraint. Run manually: " +
                "ALTER TABLE movimientos_inv DROP FOREIGN KEY <name>; " +
                "ALTER TABLE movimientos_inv DROP COLUMN lote_id;", e);
        }
        try {
            makeProveedorIdNullable();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                "DatabaseMigration failed — proveedor_id may still be NOT NULL in lotes table. " +
                "Run manually: ALTER TABLE lotes MODIFY COLUMN proveedor_id BIGINT NULL;", e);
        }
    }

    private void dropObsoleteInventarioIdColumn() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA = DATABASE() " +
                "AND TABLE_NAME = 'movimientos_inv' " +
                "AND COLUMN_NAME = 'inventario_id'",
                Integer.class);

        if (count == null || count == 0) return;

        var constraints = jdbcTemplate.queryForList(
                "SELECT CONSTRAINT_NAME FROM information_schema.KEY_COLUMN_USAGE " +
                "WHERE TABLE_SCHEMA = DATABASE() " +
                "AND TABLE_NAME = 'movimientos_inv' " +
                "AND COLUMN_NAME = 'inventario_id' " +
                "AND REFERENCED_TABLE_NAME IS NOT NULL",
                String.class);

        for (String fk : constraints) {
            try {
                jdbcTemplate.execute("ALTER TABLE movimientos_inv DROP FOREIGN KEY `" + fk + "`");
                LOGGER.info("Dropped FK constraint " + fk);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Could not drop FK constraint " + fk + " — it may already be gone", e);
            }
        }

        jdbcTemplate.execute("ALTER TABLE movimientos_inv DROP COLUMN inventario_id");
        LOGGER.info("Dropped obsolete column inventario_id from movimientos_inv");
    }

    private void dropObsoleteLoteIdColumn() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA = DATABASE() " +
                "AND TABLE_NAME = 'movimientos_inv' " +
                "AND COLUMN_NAME = 'lote_id'",
                Integer.class);

        if (count == null || count == 0) return;

        var constraints = jdbcTemplate.queryForList(
                "SELECT CONSTRAINT_NAME FROM information_schema.KEY_COLUMN_USAGE " +
                "WHERE TABLE_SCHEMA = DATABASE() " +
                "AND TABLE_NAME = 'movimientos_inv' " +
                "AND COLUMN_NAME = 'lote_id' " +
                "AND REFERENCED_TABLE_NAME IS NOT NULL",
                String.class);

        for (String fk : constraints) {
            try {
                jdbcTemplate.execute("ALTER TABLE movimientos_inv DROP FOREIGN KEY `" + fk + "`");
                LOGGER.info("Dropped FK constraint " + fk);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Could not drop FK constraint " + fk + " — it may already be gone", e);
            }
        }

        jdbcTemplate.execute("ALTER TABLE movimientos_inv DROP COLUMN lote_id");
        LOGGER.info("Dropped obsolete column lote_id from movimientos_inv");
    }

    private void makeProveedorIdNullable() {
        String isNullable = jdbcTemplate.queryForObject(
                "SELECT IS_NULLABLE FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA = DATABASE() " +
                "AND TABLE_NAME = 'lotes' " +
                "AND COLUMN_NAME = 'proveedor_id'",
                String.class);

        if ("YES".equals(isNullable)) return; // already nullable

        jdbcTemplate.execute(
                "ALTER TABLE lotes MODIFY COLUMN proveedor_id BIGINT NULL");
        LOGGER.info("Made lotes.proveedor_id nullable (proveedor no longer required on lote)");
    }
}
