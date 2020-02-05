package org.example;

import spark.Request;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
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
