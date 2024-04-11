package ru.framuga.homework;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.framuga.homework.model.Book;
import ru.framuga.homework.model.Reader;
import ru.framuga.homework.model.Roles;
import ru.framuga.homework.model.User;
import ru.framuga.homework.repository.BookRepositoryJPA;
import ru.framuga.homework.repository.ReaderRepositoryJPA;
import ru.framuga.homework.repository.RoleRepository;
import ru.framuga.homework.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    /**
     * 1. Создать аннотацию замера времени исполнения метода (Timer). Она может ставиться над методами или классами.
     * Аннотация работает так: после исполнения метода (метода класса) с такой аннотацией, необходимо в лог записать следующее:
     * className - methodName #(количество секунд выполнения)
     *
     * 2.* Создать аннотацию RecoverException, которую можно ставить только над методами.
     *им
     *   @interface RecoverException {
     *     Class<? extends RuntimeException>[] noRecoverFor() default {};
     *   }
     *
     * У аннотации должен быть параметр noRecoverFor, в котором можно перечислить другие классы исключений.
     * Аннотация работает так: если во время исполнения метода был экспешн, то не прокидывать его выше и возвращать из метода значение по умолчанию (null, 0, false, ...).
     * При этом, если тип исключения входит в список перечисленных в noRecoverFor исключений, то исключение НЕ прерывается и прокидывается выше.
     * 3.*** Параметр noRecoverFor должен учитывать иерархию исключений.
     *
     * Сдавать ссылкой на файл-аспект в проекте на гитхабе.
     */

    // TODO: 21.01.2024 EXEPTIONS
    // TODO: 27.01.2024 add log


    static long id = 1L;

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        BookRepositoryJPA bookRepository = context.getBean(BookRepositoryJPA.class);
        ReaderRepositoryJPA readerRepository = context.getBean(ReaderRepositoryJPA.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        RoleRepository roleRepository = context.getBean(RoleRepository.class);

        Roles roleAdmin = new Roles();
        roleAdmin.setName("admin");
        roleRepository.save(roleAdmin);

        Roles roleReader = new Roles();
        roleReader.setName("reader");
        roleRepository.save(roleReader);


        for (int i = 1; i < 10; i++) {
            Reader reader = new Reader();
            reader.setName("Reader #" + i);
            readerRepository.save(reader);
            saveUser(userRepository, roleRepository, "reader"+i, List.of("reader"));
        }


        for (int i = 1; i < 10; i++) {
            Book book = new Book();
            book.setName("Book #" + i);
            bookRepository.save(book);
        }


        saveUser(userRepository, roleRepository, "admin", List.of("admin", "reader"));


    }


    private static void saveUser(UserRepository repository, RoleRepository roleRepository, String
            login, List<String> rolesList) {
        User user = new User();
        List<Roles> roles = new ArrayList<>();
        rolesList.forEach(it -> roles.add(roleRepository
                .findByName(it)
                .orElseThrow(RuntimeException::new)));
        user.setId(id++);
        user.setLogin(login);
        user.setPassword(login);
        user.setRoles(roles);
        repository.save(user);
    }
}

/**
 * JDBC
 * //		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
 * <p>
 * //		DataSource dataSource = context.getBean(DataSource.class);
 * //
 * //		try(Connection connection = dataSource.getConnection()){
 * //			try(Statement statement = connection.createStatement()){
 * //				statement.execute("create table if not exists test1(id bigint, name varchar(512))");
 * //			}
 * //
 * //			try(Statement statement = connection.createStatement()){
 * //				statement.execute("insert into test1(id, name) values(1,'Alex')");
 * //			}
 * //
 * //			try(Statement statement = connection.createStatement()){
 * //				ResultSet resultSet = statement.executeQuery("select id, name from test1");
 * //				while (resultSet.next()){
 * //					System.out.println(resultSet.getInt("id"));
 * //					System.out.println(resultSet.getString("name"));
 * //				}
 * //			}
 * //		}
 * //
 * //
 */
