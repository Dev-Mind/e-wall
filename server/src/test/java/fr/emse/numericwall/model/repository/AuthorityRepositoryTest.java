package fr.emse.numericwall.model.repository;

import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import fr.emse.numericwall.model.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test de {@link AuthorityRepository}
 *
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 07/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceTestConfig.class)
public class AuthorityRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Before
    public void setUp() throws Exception {
        DbSetup dbSetup = new DbSetup(
                new DataSourceDestination(dataSource),
                sequenceOf(
                        DataTest.DELETE_ALL,
                        DataTest.INSERT_AUTHORITIES
                )
        );
        dbSetup.launch();
    }

    @Test
    public void should_load_authorities(){
        assertThat(authorityRepository.findAll()).hasSize(2);
    }

    @Test
    public void should_find_admin_role(){
        assertThat(authorityRepository.findByName(Role.ADMIN)).isNotNull();
    }
}