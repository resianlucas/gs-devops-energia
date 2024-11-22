package br.com.reciclagem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReciclagemApplicationTests {

	@Test
	void contextLoads() {
	}

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testTableExists() {

        String tableName = "tb_usuario";

        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ? AND TABLE_SCHEMA = 'dbo'";

    	Integer count = jdbcTemplate.queryForObject(sql, Integer.class, tableName);

    	// Espera que a tabela exista
   		assertThat(count).isGreaterThan(0);
    }

}
