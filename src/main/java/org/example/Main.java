package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spark.Request;
import spark.utils.SparkUtils;

import javax.servlet.ServletContextEvent;
import java.util.List;

import static spark.Spark.*;

// damit sagen wir spring, wo er nach "Bohnen" suchen soll
@Configuration // alle configurations werden vom "Komponenten scanner" angeschaut
@ComponentScan({"org.example"}) // hier stellen wir den scanner ein
public class Main {
    public static void main(String[] args) {

        // starte spring framework
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        // hol mir die DbTest bohne
        DbTest dbTest = context.getBean(DbTest.class);

        setupSparkServer(dbTest);
        // hol mir 'T odo' entries aus der datenbank
        List<String> todos = dbTest.getTodos();

        for (String todo : todos) {
            System.out.println(todo);
        }

        // beende spring sauber, wenn anwendung beendet wird
        context.registerShutdownHook();
    }

    private static void setupSparkServer(DbTest dbTest) {
        port(8080);
        get("/", (request, response) -> {
            String user = "User";
            if (existsAndNotEmpty("name", request)) {
                user = request.queryParams("name");
            }

            return "Hello " + user + "<hr><a href='/test'>Test</a>" +
                    "<hr>" +
                    "<form action='/'>" +
                    "<input type='text' name='name' placeholder='Wie heisst du?'>" +
                    "<button>OK</button>" +
                    "</form>";
        });
        get("/test", (request, response) -> {
            return "<u>das</u> ist <b>ein</b><br> <a href='/'>test</a>";
        });

        get("/todos", (request, response) -> {
            String html = "<ul>";

            List<String> todos = dbTest.getTodos();
            for (String todo : todos) {
                html += "<li>" + todo + "</li>";
            }

            html += "</ul>";

            return html;
        });

        get("/login", (request, response) -> {

            return "<form action='/login' method='post'>" +
                    "<input type='text' name='user' placeholder='Username'><br>" +
                    "<input type='password' name='password' placeholder='Password'><br>" +
                    "<button>Login!</button>" +
                    "</form>";
        });

        post("/login", (request, response) -> {
            boolean loggedIn = false;
            if (existsAndNotEmpty("user", request) && existsAndNotEmpty("password", request)) {
                String user = request.queryParams("user");
                String password = request.queryParams("password");

                // handle login logik...
                if (user.equals("admin") && password.equals("admin")) {
                    loggedIn = true;

                    return "Welcome Back " + user + "!";
                } else {
                    return "Falsche Zugangsdaten eingegeben du hast!";
                }
            } else {
                return "Zugangsdaten vergessen du hast!";
            }
        });
    }

    private static boolean existsAndNotEmpty(String parameterName, Request request) {
        return request.queryMap().hasKey(parameterName)
                && !request.queryParams(parameterName).isEmpty();
    }

}


